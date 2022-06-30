package br.com.appportaria.application.domain;

import java.io.Serializable;
import java.util.Date;



public class WRegistro implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Date date;

	private Usuario usuario;

	private TabCadastro cadastro;

	public WRegistro() {
	}

	public WRegistro(Long id, Date date, Usuario usuario, TabCadastro cadastro) {
		this.id = id;
		this.date = date;
		this.usuario = usuario;
		this.cadastro = cadastro;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public TabCadastro getCadastro() {
		return cadastro;
	}

	public void setCadastro(TabCadastro cadastro) {
		this.cadastro = cadastro;
	}

	@Override
	public String toString() {
		return "WRegistro [id=" + id + ", date=" + date + ", usuario=" + usuario + ", cadastro=" + cadastro + "]";
	}

}
