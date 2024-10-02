package com.gestaoNoticia.form.controller;

import com.gestaoNoticia.Keys;
import com.gestaoNoticia.form.model.Formulario;
import com.gestaoNoticia.form.model.ResultadoValidacao;
import com.gestaoNoticia.form.service.FormService;
import io.javalin.http.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

public class FormController {

    private static final Logger logger = LogManager.getLogger(FormController.class);

    public void abrirFormulario(Context ctx) {
        if (!usuarioLogado(ctx)){
            ctx.redirect("/login");
            return;
        }
        FormService formService = ctx.appData(Keys.FORM_SERVICE.key());
        String formId = ctx.pathParam("formId");
        Formulario form = formService.getFormulario(formId);
        ctx.attribute("form", form);
        ctx.render("/forms/formulario.html");
    }

    public void validarFormulario(@NotNull Context context) {
        FormService formService = context.appData(Keys.FORM_SERVICE.key());
        String formId = context.pathParam("formId");
        logger.debug("formId: {}", formId);
        Formulario form = formService.getFormulario(formId);
        logger.debug("form: {}", form);
        if (form == null) {
            context.status(404).result("Formulário não encontrado");
            return;
        }

        form.getCampos().forEach(campo -> {
            String valor = context.formParam(campo.getId());
            campo.setValor(valor);
        });
        Map<String, ResultadoValidacao> erros = form.validarCampos();

        if (erros.isEmpty()) {
            if (form.validarFormulario().ok()) {
                form.persistir();
                form.getCampos().forEach(campo -> campo.setValor(null));
                context.redirect(form.getRedirecionar());
            } else {
                erros.put("formularioValido", form.validarFormulario());
                context.attribute("form", form);
                context.attribute("erros", erros);
                context.render("/forms/formulario.html");
            }
        } else {
            context.attribute("form", form);
            context.attribute("erros", erros);
            context.render("/forms/formulario.html");
        }
    }

    private static boolean usuarioLogado(Context ctx){return ctx.sessionAttribute("usuario") != null;}
}