package com.tequila.brewer.repository.helper.cidade;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tequila.brewer.model.Cidade;
import com.tequila.brewer.model.Estado;
import com.tequila.brewer.repository.filter.CidadeFilter;
import com.tequila.brewer.repository.paginacao.PaginacaoUtil;

public class CidadesImpl implements CidadesQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Cidade> procuraCidadePorEstado(String nome, Estado estado) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cidade.class);
		
		adicionarFiltro(nome, estado, criteria);
		
		return criteria.list();
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public Page<Cidade> filtrar(CidadeFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cidade.class);
		
		paginacaoUtil.preparar(criteria, pageable);
		
		adicionarFiltro(filtro, criteria);
		criteria.createAlias("estado", "e");
		
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}

	private Long total(CidadeFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cidade.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}


	private void adicionarFiltro(CidadeFilter filtro, Criteria criteria) {
		if (filtro != null) {			
			if (filtro.getEstado() != null) {
				criteria.add(Restrictions.eq("estado", filtro.getEstado()));
			}
			if (!StringUtils.isEmpty(filtro.getNome())) {
				criteria.add(Restrictions.sqlRestriction("TRANSLATE({alias}.nome,'áàãéèíóòúç','aaaeeioouc') ILIKE  '%" +filtro.getNome() + "%'"));
			}
		}
	}


	private void adicionarFiltro(String nome, Estado estado, Criteria criteria) {		
		if (!StringUtils.isEmpty(nome)) {
			criteria.add(Restrictions.sqlRestriction("TRANSLATE({alias}.nome,'áàãéèíóòúç','aaaeeioouc') ILIKE  '%" + nome + "%'"));
		}
		if (estado.getCodigo() != null) {
			criteria.add(Restrictions.eq("estado", estado));
		}
		
	}
	
}
