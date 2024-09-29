package com.gestaoNoticia.form.service;

import com.gestaoNoticia.form.model.Campo;
import com.gestaoNoticia.form.model.Formulario;
import com.gestaoNoticia.form.model.validadores.ValidadorTexto;
import java.util.LinkedHashMap;
import java.util.Map;

public class FormService {

    private final Map<String, Formulario> formularios = new LinkedHashMap<>();

    public FormService(){
        iniciarFormularios();
    }

    public Formulario getFormulario(String id){
        return formularios.get(id);
    }

    public void iniciarFormularios(){
        Formulario form = new Formulario("noticia", "Cadastro de Noticia");
        form.addCampo(new Campo("titulo", "Titulo", "input", new ValidadorTexto(10, 150), true));
        form.addCampo(new Campo("subtitulo", "Subtitulo","input", new ValidadorTexto(10, 150), true));
        form.addCampo(new Campo("conteudo", "Conteudo","textarea", new ValidadorTexto(100, 7000), true));
        form.addCampo(new Campo("categoria", "Categoria","input", new ValidadorTexto(2, 30), true));
        form.addCampo(new Campo("autor", "Autor","input", new ValidadorTexto(2, 80), true));
        formularios.put(form.getId(), form);
    }
}