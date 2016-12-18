package com.tequila.brewer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tequila.brewer.model.Cidade;
import com.tequila.brewer.repository.Cidades;
import com.tequila.brewer.service.exception.NomeCidadeJaCadastradaException;

@Service
public class CadastroCidadeService {
	
	@Autowired
	private Cidades cidades;
	
	@Transactional
	public void salvar(Cidade cidade) {
		List<Cidade> cidadeExistente = cidades.procuraCidadePorEstado(cidade.getNome(), cidade.getEstado());
		if (!cidadeExistente.isEmpty()) {
			throw new NomeCidadeJaCadastradaException("Nome de cidade já cadastrado");
		}
		cidades.save(cidade);
	}
	
}
