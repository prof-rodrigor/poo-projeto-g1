package com.gestaoNoticia.form.model.validadores.validadoresCampo;

import com.gestaoNoticia.form.model.ResultadoValidacao;
import com.gestaoNoticia.form.model.ValidadorCampo;

public class ValidadorCaptalizacao implements ValidadorCampo {
    @Override
    public ResultadoValidacao validarCampo(String valor) {
        if (!Character.isUpperCase(valor.charAt(0))){
            return new ResultadoValidacao("O primeiro caractere deve ser maiúsculo!");
        }
        return new ResultadoValidacao();
    }
}
