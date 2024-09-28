package com.gestaoNoticia.form.model.validadores;

import com.gestaoNoticia.form.model.ResultadoValidacao;
import com.gestaoNoticia.form.model.ValidadorCampo;

public class ValidadorTexto implements ValidadorCampo {

    private int min;
    private int max;

    public ValidadorTexto(int min, int max){
        this.min = min;
        this.max = max;
    }

    @Override
    public ResultadoValidacao validarCampo(String valor) {
        if(valor.length() < min || valor.length() > max){
            return new ResultadoValidacao( "O valor deve ter entre " + min + " e " + max + " caracteres");
        }
        return new ResultadoValidacao();
    }
}