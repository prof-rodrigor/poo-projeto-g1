package com.gestaoNoticia.form.model.validadores;

import com.gestaoNoticia.form.model.ResultadoValidacao;
import com.gestaoNoticia.form.model.ValidadorCampo;

import java.util.ArrayList;
import java.util.List;

public class ValidadorComposto implements ValidadorCampo {

    private final List<ValidadorCampo> validadores = new ArrayList<>();

    public void adicionarValidador(ValidadorCampo validador){
        validadores.add(validador);
    }

    @Override
    public ResultadoValidacao validarCampo(String valor) {
        for (ValidadorCampo validador : validadores){
            ResultadoValidacao resultado = validador.validarCampo(valor);
            if(!resultado.ok()){
                return resultado;
            }
        }
        return new ResultadoValidacao();
    }

}
