package com.gestaoNoticia.noticia.model;

import com.gestaoNoticia.form.model.Formulario;
import com.gestaoNoticia.form.model.PersistenciaFormulario;
import com.gestaoNoticia.noticia.service.NoticiaService;

import java.time.LocalDateTime;

public class PersistenciaNoticia implements PersistenciaFormulario {

    private NoticiaService noticiaService;

    public PersistenciaNoticia(NoticiaService noticiaService){
        this.noticiaService = noticiaService;
    }

    @Override
    public void persistir(Formulario formulario) {
        String titulo = formulario.getCampo("titulo").getValor();
        String subtitulo = formulario.getCampo("subtitulo").getValor();
        String conteudo = formulario.getCampo("conteudo").getValor();
        String categoria = formulario.getCampo("categoria").getValor();
        String autor = formulario.getCampo("autor").getValor();
        LocalDateTime data = LocalDateTime.now();
        noticiaService.adicionarNoticia(new Noticia(null, titulo, subtitulo, conteudo, categoria, autor, data));
    }
}
