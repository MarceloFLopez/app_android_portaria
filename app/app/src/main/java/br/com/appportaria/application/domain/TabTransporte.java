package br.com.appportaria.application.domain;

import java.io.Serializable;


public class TabTransporte implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String modelo;
	private String placa;
	private String tipo;

	public TabTransporte() {
	}

	public TabTransporte(Long id, String modelo, String placa, String tipo) {
		super();
		this.id = id;
		this.modelo = modelo;
		this.placa = placa;
		this.tipo = tipo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "TabTransporte [id=" + id + ", modelo=" + modelo + ", placa=" + placa + ", tipo=" + tipo + "]";
	}

}
