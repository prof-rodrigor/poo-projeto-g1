package com.gestaoNoticia.noticia.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gestaoNoticia.Keys;
import com.gestaoNoticia.noticia.model.Noticia;
import com.gestaoNoticia.noticia.service.NoticiaService;
import io.javalin.http.Context;

import java.time.LocalDateTime;
import java.util.*;


public class NoticiaController {

    private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public static void getNoticias(Context ctx) {

        if (!usuarioLogado(ctx)) {
            ctx.redirect("/login");
            return;
        }

        NoticiaService noticiaService = ctx.appData(Keys.NOTICIA_SERVICE.key());

        if (ctx.queryParam("id") != null) {
            String id = ctx.queryParam("id");
            if (id.length() == 24) {
                Optional<Noticia> noticiaOptional = noticiaService.buscarNoticiaPorId(id);
                if (noticiaOptional.isPresent()) {
                    Noticia noticia = noticiaOptional.get();
                    ctx.json(noticia);
                } else {
                    ctx.status(404);
                }
            } else {
                ctx.status(400);
            }
        }
        else if (!ctx.queryParamMap().isEmpty()) {
            if (verificaParametros(ctx.queryParamMap())) {
                List<Noticia> listaFiltrada = noticiaService.buscarNoticiasFiltradas(ctx.queryParamMap());
                if (listaFiltrada.size() > 0) {
                    ctx.json(listaFiltrada);
                } else {
                    ctx.status(204);
                }
            } else {
                ctx.status(400);
            }
        } else {
            List<Noticia> noticias = noticiaService.listarNoticias();
            if (noticias.size() > 0) {
                ctx.json(noticias);
            } else {
                ctx.status(204);
            }
        }
    }

    public static void mostrarFormularioCadastro(Context ctx) {
        if (!usuarioLogado(ctx)) {
            ctx.redirect("/login");
            return;
        }
        ctx.attribute("error", ctx.sessionAttribute("error"));
        ctx.render("/noticias/form_noticia.html");
    }

    public static void listarNoticias(Context ctx) {
        if (!usuarioLogado(ctx)) {
            ctx.redirect("/login");
            return;
        }
        NoticiaService noticiaService = ctx.appData(Keys.NOTICIA_SERVICE.key());
        List<Noticia> noticias = noticiaService.listarNoticias();
        ctx.attribute("noticias", noticias);
        ctx.render("/noticias/lista_noticias.html");
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
        ctx.redirect("/lista");
    }

    public static void removerNoticia(Context ctx) {
        if (!usuarioLogado(ctx)) {
            ctx.redirect("/login");
            return;
        }
        NoticiaService noticiaService = ctx.appData(Keys.NOTICIA_SERVICE.key());
        String id = ctx.pathParam("id");
        noticiaService.removerNoticia(id);
        ctx.redirect("/lista");
    }

    public static void mostrarFormEditar(Context ctx) {
        if (!usuarioLogado(ctx)) {
            ctx.redirect("/login");
            return;
        }
        NoticiaService noticiaService = ctx.appData(Keys.NOTICIA_SERVICE.key());
        String id = ctx.pathParam("id");
        Optional<Noticia> noticiaOptional = noticiaService.buscarNoticiaPorId(id);
        if (noticiaOptional.isPresent()) {
            Noticia noticia = noticiaOptional.get();
            ctx.attribute("noticia", noticia);
            ctx.render("/noticias/form_editarNoticia.html");
        } else {
            ctx.redirect("/lista");
        }
    }

    public static void editarNoticia(Context ctx) {
        NoticiaService noticiaService = ctx.appData(Keys.NOTICIA_SERVICE.key());
        String id = ctx.formParam("id");
        
        Noticia noticiaAtualizada = new Noticia();
        
        noticiaAtualizada.setTitulo(ctx.formParam("titulo"));
        noticiaAtualizada.setSubtitulo(ctx.formParam("subtitulo"));
        noticiaAtualizada.setConteudo(ctx.formParam("conteudo"));
        noticiaAtualizada.setAutor(ctx.formParam("autor"));
        noticiaAtualizada.setCategoria(ctx.formParam("categoria"));
        
        noticiaService.editarNoticia(noticiaAtualizada, id);
        ctx.redirect("/lista");
    }

    public static void addNoticia(Context ctx) {
        NoticiaService noticiaService = ctx.appData(Keys.NOTICIA_SERVICE.key());
        try {
            Noticia noticia = mapper.readValue(ctx.body(), Noticia.class);
                noticia.setDataPublicacao(LocalDateTime.now());
                System.out.println(noticia);
        } catch (JsonProcessingException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static void verNoticia(Context ctx) {
        NoticiaService noticiaService = ctx.appData(Keys.NOTICIA_SERVICE.key());
        String id = ctx.pathParam("id");
        Optional<Noticia> noticiaOptional = noticiaService.buscarNoticiaPorId(id);
        if (noticiaOptional.isPresent()) {
            Noticia noticia = noticiaOptional.get();
            ctx.attribute("noticia", noticia);
            ctx.render("/noticias/noticia.html");
        } else {
            ctx.redirect("/noticias");
        }
    }

    private static boolean verificaParametros(Map<String, List<String>> parametros) {
        Set<String> parametrosPermitidos = Set.of("titulo", "autor", "categoria");
        for (String key: parametros.keySet()) {
            if (!parametrosPermitidos.contains(key)) {
                return false;
            }
        }
        return true;
    }

    private static boolean usuarioLogado(Context ctx) {
        return ctx.sessionAttribute("usuario") != null;
    }
}
