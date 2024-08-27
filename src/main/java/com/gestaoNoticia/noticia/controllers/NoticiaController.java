package com.gestaoNoticia.noticia.controllers;

import com.gestaoNoticia.Keys;
import com.gestaoNoticia.noticia.model.Noticia;
import com.gestaoNoticia.noticia.service.NoticiaService;
import io.javalin.http.Context;

import java.time.LocalDateTime;

public class NoticiaController {

    public static void listarNoticias(Context ctx) {
        NoticiaService noticiaService = ctx.appData(Keys.NOTICIA_SERVICE.key());
        ctx.attribute("noticias", noticiaService.listarNoticias());
        ctx.render("/noticias.html");
    }

    public static void mostrarFormularioCadastro(Context ctx) {
        ctx.render("/form_noticia.html");
    }

    public static void adicionarNoticia(Context ctx) {
        NoticiaService noticiaService = ctx.appData(Keys.NOTICIA_SERVICE.key());
        Noticia noticia = new Noticia();
        noticia.setTitulo(ctx.formParam("titulo"));
        noticia.setSubtitulo(ctx.formParam("subtitulo"));
        noticia.setConteudo(ctx.formParam("conteudo"));
        noticia.setAutor(ctx.formParam("autor"));
        noticia.setCategoria(ctx.formParam("categoria"));
        noticia.setDataPublicacao(LocalDateTime.now());

        noticiaService.adicionarNoticia(noticia);
        ctx.redirect("/noticias");
    }

    public static void removerNoticia(Context ctx) {
        NoticiaService noticiaService = ctx.appData(Keys.NOTICIA_SERVICE.key());
        String id = ctx.pathParam("id");
        noticiaService.removerNoticia(id);
        ctx.redirect("/noticias");
    }

}
