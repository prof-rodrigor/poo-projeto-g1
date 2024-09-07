package com.gestaoNoticia;

import com.gestaoNoticia.db.MongoDBRepository;
import com.gestaoNoticia.login.controller.LoginController;
import com.gestaoNoticia.login.controller.UsuarioController;
import com.gestaoNoticia.login.service.UsuarioService;
import com.gestaoNoticia.noticia.controllers.NoticiaController;
import com.gestaoNoticia.noticia.service.NoticiaService;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinThymeleaf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;
import java.util.function.Consumer;

public class App {
    private static final Logger logger = LogManager.getLogger();

    private static final int PORTA_PADRAO = 8000;
    private static final String PROP_PORTA_SERVIDOR = "porta.servidor";
    private static final String PROP_MONGODB_CONNECTION_STRING = "mongodb.connectionString";

    private final Properties propriedades;
    private MongoDBRepository mongoDBRepository = null;

    public App() {
        this.propriedades = carregarPropriedades();
    }

    public void iniciar() {
        Javalin app = inicializarJavalin();
        configurarPaginasDeErro(app);
        configurarRotas(app);

        // Lidando com exceções não tratadas
        app.exception(Exception.class, (e, ctx) -> {
            logger.error("Erro não tratado", e);
            ctx.status(500);
        });
    }
    private void registrarServicos(JavalinConfig config, MongoDBRepository mongoDBRepository) {
        NoticiaService noticiaService = new NoticiaService(mongoDBRepository);
        config.appData(Keys.NOTICIA_SERVICE.key(), noticiaService);
        config.appData(Keys.USUARIO_SEVICE.key(), new UsuarioService(mongoDBRepository));
    }

   private void configurarPaginasDeErro(Javalin app) {
       app.error(404, ctx -> ctx.render("/erro/erro_404.html"));
       app.error(500, ctx -> ctx.render("/erro/erro_500.html"));
   }

    private Javalin inicializarJavalin() {
        int porta = obterPortaServidor();

        logger.info("Iniciando aplicação na porta {}", porta);

        Consumer<JavalinConfig> configConsumer = this::configureJavalin;

        return Javalin.create(configConsumer).start(porta);
    }

    private void configureJavalin(JavalinConfig config) {
        TemplateEngine templateEngine = configurarThymeleaf();

        config.events(event -> {
            event.serverStarting(() -> {
                mongoDBRepository = inicializarMongoDB();
                config.appData(Keys.MONGO_DB.key(), mongoDBRepository);
                registrarServicos(config, mongoDBRepository);
            });
            event.serverStopping(() -> {
                if (mongoDBRepository == null) {
                    logger.error("MongoDBRepository não deveria ser nulo ao parar o servidor");
                } else {
                    mongoDBRepository.close();
                    logger.info("Conexão com o MongoDB encerrada com sucesso");
                }
            });
        });
        config.staticFiles.add(staticFileConfig -> {
            staticFileConfig.directory = "/public";
            staticFileConfig.location = Location.CLASSPATH;
        });
        config.fileRenderer(new JavalinThymeleaf(templateEngine));

    }

    private int obterPortaServidor() {
        if (propriedades.containsKey(PROP_PORTA_SERVIDOR)) {
            try {
                return Integer.parseInt(propriedades.getProperty(PROP_PORTA_SERVIDOR));
            } catch (NumberFormatException e) {
                logger.error("Porta definida no arquivo de propriedades não é um número válido: '{}'", propriedades.getProperty(PROP_PORTA_SERVIDOR));
                System.exit(1);
            }
        } else {
            logger.info("Porta não definida no arquivo de propriedades, utilizando porta padrão {}", PORTA_PADRAO);
        }
        return PORTA_PADRAO;
    }

    private TemplateEngine configurarThymeleaf() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine;
    }

    private MongoDBRepository inicializarMongoDB() {
        String connectionString = propriedades.getProperty(PROP_MONGODB_CONNECTION_STRING);
        logger.info("Lendo string de conexão ao MongoDB a partir do application.properties");
        if (connectionString == null) {
            logger.error("O string de conexão ao MongoDB não foi definido no arquivo /src/main/resources/application.properties");
            logger.error("Defina a propriedade '{}' no arquivo de propriedades", PROP_MONGODB_CONNECTION_STRING);
            System.exit(1);
        }

        logger.info("Conectando ao MongoDB");
        MongoDBRepository db = new MongoDBRepository(connectionString);
        if (db.conectado("config")) {
            logger.info("Conexão com o MongoDB estabelecida com sucesso");
        } else {
            logger.error("Falha ao conectar ao MongoDB");
            System.exit(1);
        }
        return db;
    }

    private void configurarRotas(Javalin app) {

        app.get("/", ctx -> ctx.redirect("/login"));
        app.get("/login", LoginController::mostrarPaginaLogin);
        app.post("/login", LoginController::processarLogin);
        app.get("/logout", LoginController::logout);

        app.get("/noticias", NoticiaController::getNoticias);
        app.get("/noticias/novo", NoticiaController::mostrarFormularioCadastro);
        app.post("/noticias", NoticiaController::adicionarNoticia);
        app.get("/lista", NoticiaController::listarNoticias);
        app.get("/noticias/{id}/view", NoticiaController::verNoticia);
        app.get("/noticias/{id}/remover", NoticiaController::removerNoticia);
        app.get("/noticias/{id}/editar", NoticiaController::mostrarFormEditar);
        app.post("/noticias/editar", NoticiaController::editarNoticia);

        app.get("/usuarios", UsuarioController::listarUsuarios);
        app.post("/usuarios/cadastrar", UsuarioController::cadastrarUsuario);
        app.get("/usuarios/novo", UsuarioController::mostrarFormularioCadastro);
        app.get("/usuarios/signup", UsuarioController::mostrarFormulario_signup);
        app.get("/usuarios/{id}/remover", UsuarioController::removerUsuario);

    }

    private Properties carregarPropriedades() {
        Properties prop = new Properties();
        try (InputStream input = App.class.getClassLoader().getResourceAsStream("application.properties")) {
            if(input == null){
                logger.error("Arquivo de propriedades /src/main/resources/application.properties não encontrado");
                logger.error("Use o arquivo application.properties.examplo como base para criar o arquivo application.properties");
                System.exit(1);
            }
            prop.load(input);
        } catch (IOException ex) {
            logger.error("Erro ao carregar o arquivo de propriedades /src/main/resources/application.properties", ex);
            System.exit(1);
        }
        return prop;
    }

    public static void main(String[] args) {
        try {
            new App().iniciar();
        } catch (Exception e) {
            logger.error("Erro ao iniciar a aplicação", e);
            System.exit(1);
        }
    }
}