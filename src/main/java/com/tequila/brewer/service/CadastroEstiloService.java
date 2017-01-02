package com.tequila.brewer.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tequila.brewer.model.Estilo;
import com.tequila.brewer.repository.Estilos;
import com.tequila.brewer.service.exception.ImpossivelExcluirEstiloException;
import com.tequila.brewer.service.exception.NomeEstiloJaCadastradoException;

@Service
public class CadastroEstiloService {

	@Autowired
	private Estilos estilos;
	
	@Transactional
	public Estilo salvar(Estilo estilo) {
		Optional<Estilo> estiloOptional = estilos.findByNomeIgnoreCase(estilo.getNome());
		if (estiloOptional.isPresent()) {
			throw new NomeEstiloJaCadastradoException("Nome do estilo já cadastrado");
		}
		return estilos.saveAndFlush(estilo);
	}

	@Transactional
	public void excluir(Estilo estilo) {
		try {
			estilos.delete(estilo);
			estilos.flush();
		} catch(PersistenceException e) {
			throw new ImpossivelExcluirEstiloException("Impossível excluir estilo. Possiu cervejas desse estilo cadastrado!");
		}
	}
	
}
