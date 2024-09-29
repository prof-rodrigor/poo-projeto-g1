package com.gestaoNoticia.noticia.model;

import java.time.LocalDateTime;

public class Noticia {
    private String id, titulo, subtitulo, conteudo, autor, categoria;
    private LocalDateTime dataPublicacao;

    public Noticia(String id, String titulo, String subtitulo, String conteudo, String autor, String categoria, LocalDateTime dataPublicacao) {
        verificaDados(id, titulo, subtitulo, conteudo, autor, categoria, dataPublicacao);
        this.id = id;
        this.titulo = titulo;
        this.subtitulo = subtitulo;
        this.conteudo = conteudo;
        this.autor = autor;
        this.categoria = categoria;
        this.dataPublicacao = dataPublicacao;
    }

    public Noticia() {

    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) throw  new IllegalArgumentException();
        this.titulo = titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        if (subtitulo == null || subtitulo.trim().isEmpty()) throw  new IllegalArgumentException();
        this.subtitulo = subtitulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        if (conteudo == null || conteudo.trim().isEmpty()) throw new IllegalArgumentException();
        this.conteudo = conteudo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        if (autor == null || autor.trim().isEmpty()) throw  new IllegalArgumentException();
        this.autor = autor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        if (categoria == null || categoria.trim().isEmpty()) throw  new IllegalArgumentException();
        this.categoria = categoria;
    }

    public LocalDateTime getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(LocalDateTime dataPublicacao) {
        if (dataPublicacao == null) throw new IllegalArgumentException();
        this.dataPublicacao = dataPublicacao;
    }

    public void verificaDados(String id,String titulo, String subtitulo, String conteudo, String autor, String categoria, LocalDateTime dataPublicacao) {
        //if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException();
        if (titulo == null || titulo.trim().isEmpty()) throw new IllegalArgumentException();
        if (subtitulo == null || subtitulo.trim().isEmpty()) throw new IllegalArgumentException();
        if (conteudo == null || conteudo.trim().isEmpty()) throw new IllegalArgumentException();
        if (autor == null || autor.trim().isEmpty()) throw new IllegalArgumentException();
        if (categoria == null || categoria.trim().isEmpty()) throw new IllegalArgumentException();
        if (dataPublicacao == null) throw new IllegalArgumentException();
    }

    @Override
    public String toString() {
        return "Noticia{" +
                "id='" + id + '\'' +
                ", titulo='" + titulo + '\'' +
                ", subtitulo='" + subtitulo + '\'' +
                ", conteudo='" + conteudo + '\'' +
                ", autor='" + autor + '\'' +
                ", categoria='" + categoria + '\'' +
                ", dataPublicacao=" + dataPublicacao +
                '}';
    }
}
