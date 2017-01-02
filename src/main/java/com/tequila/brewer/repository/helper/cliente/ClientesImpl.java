package com.tequila.brewer.repository.helper.cliente;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tequila.brewer.model.Cliente;
import com.tequila.brewer.repository.filter.ClienteFilter;
import com.tequila.brewer.repository.paginacao.PaginacaoUtil;

public class ClientesImpl implements ClientesQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> filtrar(ClienteFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cliente.class);
	
		criteria.addOrder(Order.asc("codigo"));
		paginacaoUtil.preparar(criteria, pageable);
		
		adicionarFiltro(filtro, criteria);
		criteria.createAlias("endereco.cidade", "c", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("c.estado", "e", JoinType.LEFT_OUTER_JOIN);
	
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}

	@Transactional(readOnly = true)
	@Override
	public Cliente buscarClienteComCidadeEstado(Cliente cliente) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cliente.class);
		criteria.createAlias("endereco.cidade", "c", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("c.estado", "e",JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("codigo", cliente.getCodigo()));
		
		return (Cliente) criteria.uniqueResult();
	}
	
	private Long total(ClienteFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cliente.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(ClienteFilter filtro, Criteria criteria) {
		if (filtro != null) {		
			if (!StringUtils.isEmpty(filtro.getNome())) {
				criteria.add(Restrictions.sqlRestriction("TRANSLATE({alias}.nome,'áàãéèíóòúç','aaaeeioouc') ILIKE  '%" +filtro.getNome() + "%'"));
//				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
			}
			if (!StringUtils.isEmpty(filtro.getCpfOuCnpj())) {
				criteria.add(Restrictions.eq("cpfOuCnpj", filtro.getCpfCnpjSemFormatacao()));
			}	
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> pesquisaNomeRapida(String nome) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cliente.class);
		
		if (!StringUtils.isEmpty(nome)) {
			criteria.add(Restrictions.sqlRestriction("TRANSLATE({alias}.nome,'áàãéèíóòúç','aaaeeioouc') ILIKE  '%" +nome + "%'"));
		}
		
		return criteria.list();
	}
	
}
