package com.gestaoNoticia.form.model.validadores.validadoresCampo;

import com.gestaoNoticia.form.model.ResultadoValidacao;
import com.gestaoNoticia.form.model.ValidadorCampo;
import com.gestaoNoticia.form.model.validadores.ValidadorDecorator;

public class ValidadorSemNumero extends ValidadorDecorator {

    public ValidadorSemNumero(ValidadorCampo validadorDecorado) {
        super(validadorDecorado);
    }

    @Override
    public ResultadoValidacao validarCampo(String valor) {
        ResultadoValidacao resultado = super.validarCampo(valor);
        if (!resultado.ok()) {
            return resultado;
        }

        if (valor.matches(".*\\d.*")) {
            return new ResultadoValidacao("Esse campo não pode conter números");
        }

        return new ResultadoValidacao();
    }
}
