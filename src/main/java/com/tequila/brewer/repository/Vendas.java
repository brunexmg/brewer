package com.tequila.brewer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tequila.brewer.model.Venda;
import com.tequila.brewer.repository.helper.venda.VendasQueries;

public interface Vendas extends JpaRepository<Venda, Long>, VendasQueries {


}
