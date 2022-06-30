package br.com.appportaria.application.domain;

import java.io.Serializable;

public class TabEmpresa implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String cnpj;
	private String name;
	private String telefone;

	public TabEmpresa() {
	}

	public TabEmpresa(Long id, String cnpj, String name, String telefone) {
		this.id = id;
		this.cnpj = cnpj;
		this.name = name;
		this.telefone = telefone;
	}
	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "TabEmpresa [id=" + id + ", cnpj=" + cnpj + ", name=" + name + ", telefone=" + telefone + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TabEmpresa other = (TabEmpresa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
