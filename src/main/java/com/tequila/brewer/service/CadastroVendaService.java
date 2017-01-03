package com.tequila.brewer.service;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tequila.brewer.model.StatusVenda;
import com.tequila.brewer.model.Venda;
import com.tequila.brewer.repository.Vendas;
import com.tequila.brewer.service.event.venda.CancelaVendaEvent;
import com.tequila.brewer.service.event.venda.VendaEvent;

@Service
public class CadastroVendaService {

	@Autowired
	private Vendas vendas;
	
	@Autowired
	private ApplicationEventPublisher publisherEmite;
	
	@Autowired
	private ApplicationEventPublisher publisherCancela;
	
	@Transactional
	public Venda salvar(Venda venda) {
		if (venda.isSalvarProibido()) {
			throw new RuntimeException("Usuário tentando salvar uma venda proibida");
		}
		
		if (venda.isNova()) {
			venda.setDataCriacao(LocalDateTime.now());
		} else {
			Venda vendaExistente = vendas.getOne(venda.getCodigo());
			venda.setDataCriacao(vendaExistente.getDataCriacao());
		}
		
		if (venda.getDataEntrega() != null) {
			venda.setDataHoraEntrega(LocalDateTime.of(venda.getDataEntrega(), venda.getHorarioEntrega() != null ? venda.getHorarioEntrega() : LocalTime.NOON ));
		}
				
		return vendas.saveAndFlush(venda);	
	}

	@Transactional
	public void emitir(Venda venda) {
		venda.setStatus(StatusVenda.EMITIDA);
		venda = salvar(venda);
		
		publisherEmite.publishEvent(new VendaEvent(venda));	
	}

	@PreAuthorize("#venda.usuario == principal.usuario or hasAuthority('CANCELAR_VENDA')")
	@Transactional
	public void cancelar(Venda venda) {
		Venda vendaExistente = vendas.getOne(venda.getCodigo());
		boolean retornaItens = false;
		if (vendaExistente.getStatus().equals(StatusVenda.EMITIDA)) {
			retornaItens = true;
		}
		vendaExistente.setStatus(StatusVenda.CANCELADA);
		vendas.save(vendaExistente);
		
		if (retornaItens){
			publisherCancela.publishEvent(new CancelaVendaEvent(vendaExistente));
		}
	}

}
