package com.gestaoNoticia.login.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Usuario {

    private String id;
    private String email;
    private String username;
    private String senha;

    public Usuario(String email, String username, String senha) {
        verificaDados(email, username, senha);
        this.email = email;
        this.username = username;
        this.senha = senha;
    }

    public Usuario() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) throw new IllegalArgumentException();
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) throw new IllegalArgumentException();
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        if (senha == null || senha.trim().isEmpty()) throw new IllegalArgumentException();
        this.senha = senha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException();
        this.id = id;
    }

     private static void verificaDados(String email, String username, String senha) {
         if (email == null || email.trim().isEmpty() || username == null || username.trim().isEmpty() || senha == null || senha.trim().isEmpty()) throw new IllegalArgumentException();
     }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", login='" + email + '\'' +
                ", nome='" + username + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }
}
