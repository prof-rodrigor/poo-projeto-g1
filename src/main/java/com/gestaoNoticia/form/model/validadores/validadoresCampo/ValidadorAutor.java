package com.gestaoNoticia.form.model.validadores.validadoresCampo;

import com.gestaoNoticia.form.model.ResultadoValidacao;
import com.gestaoNoticia.form.model.ValidadorCampo;
import com.gestaoNoticia.form.model.validadores.ValidadorDecorator;

public class ValidadorAutor extends ValidadorDecorator {

    public ValidadorAutor(ValidadorCampo validadorDecorado) {
        super(validadorDecorado);
    }

    @Override
    public ResultadoValidacao validarCampo(String valor) {
        ResultadoValidacao resultado = super.validarCampo(valor);
        if (!resultado.ok()) {
            return resultado;
        }

        if (valor.matches(".*\\d.*")) {
            return new ResultadoValidacao("O nome do autor não pode conter números");
        }

        return new ResultadoValidacao();
    }
}
