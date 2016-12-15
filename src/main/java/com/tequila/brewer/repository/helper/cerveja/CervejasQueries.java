package com.tequila.brewer.repository.helper.cerveja;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tequila.brewer.model.Cerveja;
import com.tequila.brewer.repository.filter.CervejaFilter;

public interface CervejasQueries {

	public Page<Cerveja> filtrar(CervejaFilter filtro, Pageable pageable);
	
}
