package com.gestaoNoticia.form.model;

public class Secao extends ComponenteComposto{
    @Override
    public String getConteudo() {
        String conteudo = "";
        for (ComponenteForm componente : componentes) {
            conteudo += componente.getConteudo();
        }
        return conteudo;
    }
}
