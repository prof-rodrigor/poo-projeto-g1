package com.gestaoNoticia.form.model;

import java.util.Map;

public interface Form {

    Map<String, ResultadoValidacao> validarCampos();
    ResultadoValidacao validarFormulario();
    void persistir();
}
