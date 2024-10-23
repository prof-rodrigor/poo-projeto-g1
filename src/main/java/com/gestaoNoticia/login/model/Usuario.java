package com.gestaoNoticia.login.model;

public class Usuario {

    private String id;
    private String email;
    private String username;
    private String senha;

    public Usuario(String email, String username, String senha) {
        //verificaDados(login, nome, senha);
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

     //private static void verificaDados(String login, String nome, String senha) {
     //    if (login == null || login.trim().isEmpty() || nome == null || nome.trim().isEmpty() || senha == null || senha.trim().isEmpty()) throw new IllegalArgumentException();
     //}

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
