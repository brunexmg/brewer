package com.tequila.brewer.repository.filter;

import com.tequila.brewer.model.TipoPessoa;

public class ClienteFilter {

	private String nome;
	private String cpfOuCnpj;
	
	public String getNome() {
		return nome;
	}
	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}
	
	public Object getCpfCnpjSemFormatacao() {
		return TipoPessoa.removerFormatacao(this.cpfOuCnpj);
	}
	
}
