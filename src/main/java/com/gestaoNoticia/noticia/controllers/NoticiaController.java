package com.gestaoNoticia.noticia.controllers;

import com.gestaoNoticia.Keys;
import com.gestaoNoticia.noticia.model.Noticia;
import com.gestaoNoticia.noticia.service.NoticiaService;
import io.javalin.http.Context;

import java.time.LocalDateTime;
import java.util.Optional;

public class NoticiaController {

    public static void listarNoticias(Context ctx) {
        NoticiaService noticiaService = ctx.appData(Keys.NOTICIA_SERVICE.key());
        ctx.attribute("noticias", noticiaService.listarNoticias());
        ctx.render("/lista_noticias.html");
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

    public static void mostrarFormEditar(Context ctx) {
        NoticiaService noticiaService = ctx.appData(Keys.NOTICIA_SERVICE.key());
        String id = ctx.pathParam("id");
        Optional<Noticia> noticiaOptional = noticiaService.buscarNoticiaPorId(id);
        if (noticiaOptional.isPresent()) {
            Noticia noticia = noticiaOptional.get();
            ctx.attribute("noticia", noticia);
            ctx.render("/form_editarNoticia.html");
        } else {
            ctx.redirect("/noticias");
        }
    }

    public static void editarNoticia(Context ctx) {
        NoticiaService noticiaService = ctx.appData(Keys.NOTICIA_SERVICE.key());
        String id = ctx.formParam("id");
        Noticia noticia = new Noticia();
        noticia.setTitulo(ctx.formParam("titulo"));
        noticia.setSubtitulo(ctx.formParam("subtitulo"));
        noticia.setConteudo(ctx.formParam("conteudo"));
        noticia.setAutor(ctx.formParam("autor"));
        noticia.setCategoria(ctx.formParam("categoria"));
        noticiaService.editarNoticia(noticia, id);
        ctx.redirect("/noticias");
    }

    public static void verNoticia(Context ctx) {
        NoticiaService noticiaService = ctx.appData(Keys.NOTICIA_SERVICE.key());
        String id = ctx.pathParam("id");
        Optional<Noticia> noticiaOptional = noticiaService.buscarNoticiaPorId(id);
        if (noticiaOptional.isPresent()) {
            Noticia noticia = noticiaOptional.get();
            ctx.attribute("noticia", noticia);
            ctx.render("/noticia.html");
        } else {
            ctx.redirect("/noticias");
        }
    }

}
