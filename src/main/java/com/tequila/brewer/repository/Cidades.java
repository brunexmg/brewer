package com.tequila.brewer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tequila.brewer.model.Cidade;
import com.tequila.brewer.model.Estado;
import com.tequila.brewer.repository.helper.cidade.CidadesQueries;

@Repository
public interface Cidades extends JpaRepository<Cidade, Long>, CidadesQueries {

	public List<Cidade> findByEstadoCodigo(Long codigo);
	
	public Optional<Cidade> findByNomeIgnoreCaseAndEstado(String nome, Estado estado); 
	
}
