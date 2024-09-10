package com.gestaoNoticia.noticia.controllers;

import com.gestaoNoticia.Keys;
import com.gestaoNoticia.noticia.model.Noticia;
import com.gestaoNoticia.noticia.service.NoticiaService;
import io.javalin.http.Context;

import java.time.LocalDateTime;
import java.util.*;


public class NoticiaController {

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
        ctx.sessionAttribute("error", null);
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
        String titulo = ctx.formParam("titulo");
        String subtitulo = ctx.formParam("subtitulo");
        String conteudo = ctx.formParam("conteudo");
        String autor = ctx.formParam("autor");
        String categoria = ctx.formParam("categoria");


        if (verificaDadosNoticia(titulo, subtitulo, conteudo, autor, categoria)) {
            noticia.setTitulo(titulo);
            noticia.setSubtitulo(subtitulo);
            noticia.setConteudo(conteudo);
            noticia.setAutor(autor);
            noticia.setCategoria(categoria);
            noticia.setDataPublicacao(LocalDateTime.now());
            noticiaService.adicionarNoticia(noticia);
            ctx.redirect("/lista");
        } else {
            ctx.sessionAttribute("error", "Preencha todos os campos corretamente");
            ctx.redirect("/noticias/novo");
        }
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
            ctx.attribute("error", ctx.sessionAttribute("error"));
            ctx.sessionAttribute("error", null);
            ctx.render("/noticias/form_editarNoticia.html");
        } else {
            ctx.redirect("/lista");
        }
    }

    public static void editarNoticia(Context ctx) {
        NoticiaService noticiaService = ctx.appData(Keys.NOTICIA_SERVICE.key());
        String id = ctx.formParam("id");
        
        Noticia noticiaAtualizada = new Noticia();
        String titulo = ctx.formParam("titulo");
        String subtitulo = ctx.formParam("subtitulo");
        String conteudo =ctx.formParam("conteudo");
        String autor = ctx.formParam("autor");
        String categoria = ctx.formParam("categoria");

        if (verificaDadosNoticia(titulo, subtitulo, conteudo, autor, categoria)) {
            noticiaAtualizada.setTitulo(titulo);
            noticiaAtualizada.setSubtitulo(subtitulo);
            noticiaAtualizada.setConteudo(conteudo);
            noticiaAtualizada.setAutor(autor);
            noticiaAtualizada.setCategoria(categoria);
            noticiaService.editarNoticia(noticiaAtualizada, id);
            ctx.redirect("/lista");
        } else {
            ctx.sessionAttribute("error", "Preencha todos os campos corretamente");
            ctx.redirect("/noticias/"+id+"/editar");
        }

    }

    public static void verNoticia(Context ctx) {
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

    private static boolean verificaDadosNoticia(String titulo, String subtitulo, String conteudo, String autor, String categoria) {
        if (titulo.length() < 10 || titulo.length() > 150) return false;
        if (subtitulo.length() < 10 || subtitulo.length() > 150) return false;
        if (conteudo.length() < 100 || conteudo.length() > 7000) return false;
        if(autor.length() < 2 || autor.length() > 100) return false;
        if(categoria.length() < 2 || categoria.length() > 100) return false;
        return true;
    }

    private static boolean usuarioLogado(Context ctx) {
        return ctx.sessionAttribute("usuario") != null;
    }
}
