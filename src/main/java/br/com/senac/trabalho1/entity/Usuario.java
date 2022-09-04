package br.com.senac.trabalho1.entity;

public class Usuario {

    private long id;

    private String login;

    private String senha;

    public Usuario() {
    }

    public Usuario(long id, String login, String senha){
        this.id = id;
        this.login = login;
        this.senha = senha;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "Usuario +"+login;
    }
}
