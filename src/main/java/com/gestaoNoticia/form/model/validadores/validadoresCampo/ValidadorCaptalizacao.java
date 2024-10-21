package com.gestaoNoticia.form.model.validadores.validadoresCampo;

import com.gestaoNoticia.form.model.ResultadoValidacao;
import com.gestaoNoticia.form.model.ValidadorCampo;
import com.gestaoNoticia.form.model.validadores.ValidadorDecorator;

public class ValidadorCaptalizacao extends ValidadorDecorator {

    public ValidadorCaptalizacao(ValidadorCampo validadorDecorado) {
        super(validadorDecorado);
    }

    @Override
    public ResultadoValidacao validarCampo(String valor) {
        if (!Character.isUpperCase(valor.charAt(0))){
            return new ResultadoValidacao("O primeiro caractere deve ser maiúsculo!");
        }
        return new ResultadoValidacao();
    }
}
