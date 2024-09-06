package com.gestaoNoticia.login.controller;

import com.gestaoNoticia.Keys;
import com.gestaoNoticia.login.model.Usuario;
import com.gestaoNoticia.login.service.UsuarioService;
import io.javalin.http.Context;
import org.mindrot.jbcrypt.BCrypt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginController {

    private static final Logger logger = LogManager.getLogger(LoginController.class);

    public static void mostrarPaginaLogin(Context ctx) {
        String teste = ctx.queryParam("teste");
        if(teste != null){
            throw new RuntimeException("Erro de teste a partir do /login?teste=1");
        }

        ctx.render("/usuarios/login.html");
    }

    public static void processarLogin(Context ctx) {
        String login = ctx.formParam("login");
        String senha = ctx.formParam("senha");


        UsuarioService usuarioService = ctx.appData(Keys.USUARIO_SEVICE.key());
        Usuario usuario = usuarioService.buscarUsuarioPorLogin(login);
        if (usuario != null && BCrypt.checkpw(senha, usuario.getSenha())) {
            ctx.sessionAttribute("usuario", usuario);
            logger.info("Usuário '{}' autenticado com sucesso.", login);
            ctx.redirect("/usuarios");
        } else {
            logger.warn("Tentativa de login falhou para o usuário: {}", login);
            ctx.attribute("erro", "Usuário ou senha inválidos");
            ctx.render("/usuarios/login.html");
        }
    }

    public static void logout(Context ctx) {
        ctx.sessionAttribute("usuario", null);
        ctx.redirect("/login");
    }
}
