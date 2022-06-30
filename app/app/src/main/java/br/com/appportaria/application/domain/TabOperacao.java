package br.com.appportaria.application.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class TabOperacao implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String obervacao;

	public TabOperacao(Long id, String name, String obervacao) {
		super();
		this.id = id;
		this.name = name;
		this.obervacao = obervacao;
	}
	public TabOperacao() {
		super();
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
	public String getObervacao() {
		return obervacao;
	}
	public void setObervacao(String obervacao) {
		this.obervacao = obervacao;
	}

	@Override
	public String toString() {
		return "TabOperacao [id=" + id + ", name=" + name + ", obervacao=" + obervacao + "]";
	}
}
