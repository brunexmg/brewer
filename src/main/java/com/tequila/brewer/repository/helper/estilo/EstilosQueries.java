package com.tequila.brewer.repository.helper.estilo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tequila.brewer.model.Estilo;
import com.tequila.brewer.repository.filter.EstiloFilter;

public interface EstilosQueries {

	public Page<Estilo> filtrar(EstiloFilter filtro, Pageable pageable);
	
}
