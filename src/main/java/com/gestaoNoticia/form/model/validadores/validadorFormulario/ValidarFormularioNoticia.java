package com.gestaoNoticia.form.model.validadores.validadorFormulario;

import com.gestaoNoticia.form.model.Formulario;
import com.gestaoNoticia.form.model.ResultadoValidacao;
import com.gestaoNoticia.form.model.ValidadorFormulario;

public class ValidarFormularioNoticia implements ValidadorFormulario {
    @Override
    public ResultadoValidacao validar(Formulario form) {
        return new ResultadoValidacao();
    }
}
