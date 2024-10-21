package com.gestaoNoticia.form.model;

import java.util.ArrayList;
import java.util.List;

public abstract class ComponenteComposto implements ComponenteForm{

    protected List<ComponenteForm> componentes = new ArrayList<>();

    public void addComponente(ComponenteForm componenteForm) {
        this.componentes.add(componenteForm);
    }
}
