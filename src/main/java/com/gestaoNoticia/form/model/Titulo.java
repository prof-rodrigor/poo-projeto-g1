package com.gestaoNoticia.form.model;

public class Titulo implements ComponenteForm{

    private String conteudo;

    public Titulo(String conteudo) {
        this.conteudo = conteudo;
    }

    @Override
    public String getConteudo() {
        return "<div><h2>"+conteudo+"</h2></div>";
    }
}
