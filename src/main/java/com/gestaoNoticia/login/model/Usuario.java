package com.gestaoNoticia.login.model;

public class Usuario {

    private String id;
    private String login;
    private String nome;
    private String senha;

    public Usuario(String id, String login, String nome, String senha) {
        verificaDados(login, nome, senha, id);
        this.id = id;
        this.login = login;
        this.nome = nome;
        this.senha = senha;
    }

    public Usuario() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        if (login == null || login.trim().isEmpty()) throw new IllegalArgumentException();
        this.login = login;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) throw new IllegalArgumentException();
        this.nome = nome;
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

     private static void verificaDados(String login, String nome, String senha, String id) {
         if (login == null || login.trim().isEmpty() || nome == null || nome.trim().isEmpty() || senha == null || senha.trim().isEmpty() || id == null || id.trim().isEmpty()) throw new IllegalArgumentException();
     }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", login='" + login + '\'' +
                ", nome='" + nome + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }
}
