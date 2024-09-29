package com.gestaoNoticia;

import com.gestaoNoticia.db.MongoDBRepository;
import com.gestaoNoticia.form.service.FormService;
import com.gestaoNoticia.login.service.UsuarioService;
import com.gestaoNoticia.noticia.service.NoticiaService;
import io.javalin.config.Key;

public enum Keys {
    MONGO_DB(new Key<MongoDBRepository>("mongo-db")),
    NOTICIA_SERVICE(new Key<NoticiaService>("noticia-service")),
    USUARIO_SERVICE(new Key<UsuarioService>("usuario-service")),
    FORM_SERVICE(new Key<NoticiaService>("form-service"));

    private final Key<?> k;

    <T> Keys(Key<T> key) {
        this.k = key;
    }

    public <T> Key<T> key() {
        @SuppressWarnings("unchecked")
        Key<T> typedKey = (Key<T>) this.k;
        return typedKey;
    }
}
