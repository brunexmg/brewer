package com.tequila.brewer.repository.helper.venda;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.Year;
import java.util.List;
import java.util.Optional;

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

import com.tequila.brewer.dto.VendaMes;
import com.tequila.brewer.model.StatusVenda;
import com.tequila.brewer.model.TipoPessoa;
import com.tequila.brewer.model.Venda;
import com.tequila.brewer.repository.filter.VendaFilter;
import com.tequila.brewer.repository.paginacao.PaginacaoUtil;

public class VendasImpl implements VendasQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Page<Venda> filtrar(VendaFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Venda.class);
		
		paginacaoUtil.preparar(criteria, pageable);
		criteria.addOrder(Order.asc("codigo"));
		adicionarFiltro(filtro, criteria);
		
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}
	
	@Transactional(readOnly = true)
	@Override
	public Venda buscarComItens(Long codigo) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Venda.class); 
		criteria.createAlias("itens", "i", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("codigo", codigo));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return (Venda) criteria.uniqueResult();
	}
	
	@Transactional(readOnly = true)
	@Override
	public BigDecimal valorTotalNoAno() {
		Optional<BigDecimal> optional = Optional.ofNullable(
				manager.createQuery("select sum(valorTotal) from Venda where year(dataCriacao) = :ano "
						+ "and status =:status", BigDecimal.class)
					.setParameter("ano", Year.now().getValue())
					.setParameter("status", StatusVenda.EMITIDA)
				.getSingleResult());
		return optional.orElse(BigDecimal.ZERO);
	}
	
	@Transactional(readOnly = true)
	@Override
	public BigDecimal valorTotalNoMes() {
		Optional<BigDecimal> optional = Optional.ofNullable(
				manager.createQuery(
						"select sum(valorTotal) from Venda where year(dataCriacao) = :ano and "
						+ "month(dataCriacao) = :mes "
						+ "and status = :status", BigDecimal.class)
					.setParameter("ano", Year.now().getValue())
					.setParameter("mes", MonthDay.now().getMonthValue())
					.setParameter("status", StatusVenda.EMITIDA)
				.getSingleResult());
				
				return optional.orElse(BigDecimal.ZERO);
	}

	@Transactional(readOnly = true)
	@Override
	public BigDecimal valorTicketMedioNoAno() {
		Optional<BigDecimal> optional = Optional.ofNullable(
				manager.createQuery("select sum(valorTotal)/count(*) from Venda where year(dataCriacao) = :ano "
						+ "and status =:status", BigDecimal.class)
					.setParameter("ano", Year.now().getValue())
					.setParameter("status", StatusVenda.EMITIDA)
				.getSingleResult());
		
		return optional.orElse(BigDecimal.ZERO);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VendaMes> totalPorMes() {
		return manager.createNamedQuery("Venda.totalPorMes").getResultList();
	}
	
	private Long total(VendaFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Venda.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}
	
	private void adicionarFiltro(VendaFilter filtro, Criteria criteria) {
		criteria.createAlias("cliente", "c");
//		.add(Restrictions.sqlRestriction("TRANSLATE(c1_.nome,'áàãéèíóòúç','aaaeeioouc') ILIKE  '%" +filtro.getNomeCliente() + "%'"));
		
		if (filtro != null) {
			if (!StringUtils.isEmpty(filtro.getCodigo())) {
				criteria.add(Restrictions.eq("codigo", filtro.getCodigo()));
			}
			
			if (filtro.getStatus() != null) {
				criteria.add(Restrictions.eq("status", filtro.getStatus()));
			}
			
			if (filtro.getDesde() != null) {
				LocalDateTime desde = LocalDateTime.of(filtro.getDesde(), LocalTime.of(0, 0));
				criteria.add(Restrictions.ge("dataCriacao", desde));
			}
			
			if (filtro.getAte() != null) {
				LocalDateTime ate = LocalDateTime.of(filtro.getAte(), LocalTime.of(23,59));
				criteria.add(Restrictions.le("dataCriacao", ate));
			}
			
			if (filtro.getValorMinimo() != null) {
				criteria.add(Restrictions.ge("valorTotal", filtro.getValorMinimo()));
			}
			
			if (filtro.getValorMaximo() != null) {
				criteria.add(Restrictions.le("valorTotal", filtro.getValorMaximo()));
			}
			
			if (!StringUtils.isEmpty(filtro.getNomeCliente())) {
				criteria.add(Restrictions.sqlRestriction("TRANSLATE(c1_.nome,'áàãéèíóòúç','aaaeeioouc') ILIKE  '%" +filtro.getNomeCliente() + "%'"));
			}
			
			if (!StringUtils.isEmpty(filtro.getCpfOuCnpjCliente())) {
				criteria.add(Restrictions.eqOrIsNull("c.cpfOuCnpj", TipoPessoa.removerFormatacao(filtro.getCpfOuCnpjCliente())));
			}
		}
	}

}
