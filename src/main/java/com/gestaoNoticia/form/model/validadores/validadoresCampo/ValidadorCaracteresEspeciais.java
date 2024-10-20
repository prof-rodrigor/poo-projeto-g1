package com.gestaoNoticia.form.model.validadores.validadoresCampo;

import com.gestaoNoticia.form.model.ResultadoValidacao;
import com.gestaoNoticia.form.model.ValidadorCampo;

public class ValidadorCaracteresEspeciais implements ValidadorCampo {
    @Override
    public ResultadoValidacao validarCampo(String valor) {
        if (valor.matches(".*[!@#$%^&*(),.?\":{}|<>].*")){
            return new ResultadoValidacao("Esse campo não deve conter caracteres especiais");
        }
        return new ResultadoValidacao();
    }
}
