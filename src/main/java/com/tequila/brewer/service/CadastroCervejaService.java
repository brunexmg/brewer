package com.tequila.brewer.service;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tequila.brewer.model.Cerveja;
import com.tequila.brewer.repository.Cervejas;
import com.tequila.brewer.service.event.cerveja.CervejaSalvaEvent;
import com.tequila.brewer.service.exception.ImpossivelExlcuirEntidadeException;
import com.tequila.brewer.storage.FotoStorage;

@Service
public class CadastroCervejaService {

	@Autowired
	private Cervejas cervejas;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private FotoStorage fotoStorage;
	
	@Transactional
	public void salvar(Cerveja cerveja) {
		cervejas.save(cerveja);
		
		publisher.publishEvent(new CervejaSalvaEvent(cerveja));
	}
	
	@Transactional
	public void excluir(Cerveja cerveja) {
		try {
			String foto = cerveja.getFoto();
			cervejas.delete(cerveja);
			cervejas.flush();
			fotoStorage.apagarFoto(foto);
		} catch (PersistenceException e) {
			throw new ImpossivelExlcuirEntidadeException("Impossivel apagar cerveja. Já foi utilizada em alguma venda.");
		}
	}
	 
}
