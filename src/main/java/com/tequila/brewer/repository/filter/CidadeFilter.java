package com.tequila.brewer.repository.filter;

import com.tequila.brewer.model.Estado;

public class CidadeFilter {

	public Estado estado;
	public String nome;
	
	public Estado getEstado() {
		return estado;
	}
	public String getNome() {
		return nome;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
		
}
