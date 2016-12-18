package com.tequila.brewer.repository.helper.cidade;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tequila.brewer.model.Cidade;
import com.tequila.brewer.model.Estado;
import com.tequila.brewer.repository.filter.CidadeFilter;

public interface CidadesQueries {

	public List<Cidade> procuraCidadePorEstado(String nome, Estado estado);
	
	public Page<Cidade> filtrar(CidadeFilter filtro, Pageable pageable);
	
}
