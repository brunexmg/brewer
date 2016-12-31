package com.tequila.brewer.service;

import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tequila.brewer.model.Cidade;
import com.tequila.brewer.repository.Cidades;
import com.tequila.brewer.service.exception.ImpossivelExcluirCidadeException;
import com.tequila.brewer.service.exception.NomeCidadeJaCadastradaException;

@Service
public class CadastroCidadeService {
	
	@Autowired
	private Cidades cidades;
	
	@Transactional(readOnly = true)
	public List<Cidade> procurarPorCodigoEstado(Long codigoEstado) {
		return cidades.findByEstadoCodigo(codigoEstado);
	}
	
	@Transactional
	public void salvar(Cidade cidade) {
		List<Cidade> cidadeExistente = cidades.procuraCidadePorEstado(cidade.getNome(), cidade.getEstado());
		if (cidade.isNova() && !cidadeExistente.isEmpty()) {
			throw new NomeCidadeJaCadastradaException("Nome de cidade já cadastrado");
		}
		cidades.save(cidade);
	}
	
	@Transactional
	public void excluir(Long codigo) {
		try {
			cidades.delete(codigo);
			cidades.flush();
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirCidadeException("Impossível excluir cidade. Já está sendo usado.");
		}
	}
	
}
