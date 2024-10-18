package com.gestaoNoticia.form.model.validadores;

import com.gestaoNoticia.form.model.ResultadoValidacao;
import com.gestaoNoticia.form.model.ValidadorCampo;

public abstract class ValidadorDecorator implements ValidadorCampo {

    protected ValidadorCampo validadorDecorado;

    public ValidadorDecorator(ValidadorCampo validadorDecorado) {
        this.validadorDecorado = validadorDecorado;
    }

    @Override
    public ResultadoValidacao validarCampo(String valor) {
        if (validadorDecorado != null) {
            return validadorDecorado.validarCampo(valor);
        }
        return new ResultadoValidacao(); // Retorna sucesso se não houver validador decorado
    }

}
