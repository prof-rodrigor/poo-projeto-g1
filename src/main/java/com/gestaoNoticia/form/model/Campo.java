package com.gestaoNoticia.form.model;

public class Campo {

    private String id;
    private String label;
    private String valor;
    private String entrada;
    private ValidadorCampo validador;
    private boolean obrigatorio;


    public Campo(String id, String label, String entrada, ValidadorCampo validador, boolean obrigatorio){
        this.id = id;
        this.label = label;
        this.entrada = entrada;
        this.validador = validador;
        this.obrigatorio = obrigatorio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getEntrada() {
        return entrada;
    }

    public void setEntrada(String entrada) {
        this.entrada = entrada;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public ResultadoValidacao validar(){
        if(this.obrigatorio && (this.valor == null || this.valor.isEmpty())){
            return new ResultadoValidacao("Campo obrigatório");
        }
        return validador.validarCampo(this.valor);
    }


    public void setValidador(ValidadorCampo validador) {
        this.validador = validador;
    }

    public boolean isObrigatorio() {
        return obrigatorio;
    }

    public void setObrigatorio(boolean obrigatorio) {
        this.obrigatorio = obrigatorio;
    }
}