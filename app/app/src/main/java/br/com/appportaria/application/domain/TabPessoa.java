package br.com.appportaria.application.domain;

import java.io.Serializable;
import java.util.Objects;

public class TabPessoa implements Serializable {

    private Long id;
    private String name;
    private String telefone;
    private String cpf;

    public TabPessoa(Long id, String name, String telefone, String cpf) {
        this.id = id;
        this.name = name;
        this.telefone = telefone;
        this.cpf = cpf;
    }

    public TabPessoa() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TabPessoa tabPessoa = (TabPessoa) o;
        return id.equals(tabPessoa.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}