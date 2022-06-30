package br.com.appportaria.application.domain;

import java.io.Serializable;


public class TabCadastro implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private TabEmpresa empresa;
	private TabOperacao operacao;
	private TabTransporte transporte;
	private TabPessoa pessoa;

	public TabCadastro(Long id, TabEmpresa empresa, TabOperacao operacao, TabTransporte transporte, TabPessoa pessoa) {
		this.id = id;
		this.empresa = empresa;
		this.operacao = operacao;
		this.transporte = transporte;
		this.pessoa = pessoa;
	}

	public TabCadastro() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TabEmpresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(TabEmpresa empresa) {
		this.empresa = empresa;
	}

	public TabOperacao getOperacao() {
		return operacao;
	}

	public void setOperacao(TabOperacao operacao) {
		this.operacao = operacao;
	}

	public TabTransporte getTransporte() {
		return transporte;
	}

	public void setTransporte(TabTransporte transporte) {
		this.transporte = transporte;
	}

	public TabPessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(TabPessoa pessoa) {
		this.pessoa = pessoa;
	}

	@Override
	public String toString() {
		return "TabCadastro [id=" + id + ", empresa=" + empresa + ", operacao=" + operacao + ", transporte="
				+ transporte + ", pessoa=" + pessoa + "]";
	}

}
