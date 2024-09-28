package com.gestaoNoticia.login.controller;

import com.gestaoNoticia.Keys;
import com.gestaoNoticia.login.model.Usuario;
import com.gestaoNoticia.login.service.UsuarioService;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class UsuarioController {

    public static void mostrarFormularioCadastro(Context ctx) {
        ctx.render("/usuarios/formulario_usuario.html");
    }


    public static void mostrarFormulario_signup(Context ctx) {
        ctx.render("/usuarios/formulario_signup.html");
    }

    public static void cadastrarUsuario(Context ctx) {
        UsuarioService usuarioService = ctx.appData(Keys.USUARIO_SERVICE.key());
        String nome = ctx.formParam("nome");
        String email = ctx.formParam("login");
        String senha = ctx.formParam("senha");

        boolean signup = (ctx.formParam("signup") != null);
        String formSignup = "/usuarios/formulario_signup.html";
        String formCadastro = "/usuarios/formulario_usuario.html";

        if(usuarioService.buscarUsuarioPorLogin(email) != null){
            ctx.attribute("erro", "Já existe um usuário com o email cadastrado:"+email);
            ctx.render(signup?formSignup:formCadastro);
            return;
        }

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setLogin(email);
        usuario.setSenha(senha);

        usuarioService.cadastrarUsuario(usuario);
        if(signup){
            ctx.attribute("info", "Usuário cadastrado com sucesso!");
            ctx.render("/usuarios/login.html");
        }else{
            ctx.redirect("/usuarios");
        }
    }

    public static void listarUsuarios(Context ctx) {
        if (!usuarioLogado(ctx)) {
            ctx.redirect("/login");
            return;
        }
        UsuarioService usuarioService = ctx.appData(Keys.USUARIO_SERVICE.key());
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        ctx.attribute("usuarios", usuarios);
        ctx.render("/usuarios/lista_usuarios.html");
    }

    public static void removerUsuario(@NotNull Context ctx) {  // Fix: Add @NotNull annotation
        if (!usuarioLogado(ctx)) {
            ctx.redirect("/login");
            return;
        }
        UsuarioService usuarioService = ctx.appData(Keys.USUARIO_SERVICE.key());
        String id = ctx.pathParam("id");
        usuarioService.removerUsuario(id);
        ctx.redirect("/usuarios");

    }

    private static boolean usuarioLogado(Context ctx) {
        return ctx.sessionAttribute("usuario") != null;
    }

}
