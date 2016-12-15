package com.tequila.brewer.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class Endereco implements Serializable {

	private static final long serialVersionUID = 1L;

	private String logradouro;
	
	private String numero;
	
	private String complemento;
	
	private String cep;
	
	@ManyToOne
	@JoinColumn(name = "codigo_cidade")
	private Cidade cidade;
	
	public String getLogradouro() {
		return logradouro;
	}
	public String getNumero() {
		return numero;
	}
	public String getComplemento() {
		return complemento;
	}
	public String getCep() {
		return cep;
	}
	public Cidade getCidade() {
		return cidade;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	
}
