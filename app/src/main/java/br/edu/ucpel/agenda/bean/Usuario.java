package br.edu.ucpel.agenda.bean;

/**
 *
 * @author Miguel Aguiar Barbosa
 */
public class Usuario {

    private static Integer _id;
    private static String nome, login, senha;

    public Usuario() {

    }

    public Usuario(Integer id, String nome, String login, String senha) {
        this._id = id;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
    }

    public static Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public static String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public static String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public static String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
