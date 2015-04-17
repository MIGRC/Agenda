package br.edu.ucpel.agenda.bean;

import java.io.Serializable;

/**
 *
 * @author Miguel Aguiar Barbosa
 */
public class Contato implements Serializable {

    private static final long serialVersionUID = 1L;
    private long id;
    private String nome;
    private String endereco;
    private String telefone;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String value) {
        this.nome = value;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String value) {
        this.endereco = value;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String value) {
        this.telefone = value;
    }

    @Override
    public String toString() {
        return nome;
    }
}
