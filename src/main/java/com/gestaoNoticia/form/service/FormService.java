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
        Formulario form = new Formulario("usuario", "Cadastro de Usuário");
        form.addCampo(new Campo("nome", "Nome", new ValidadorTexto(3, 100), true));
        formularios.put(form.getId(), form);
    }
}