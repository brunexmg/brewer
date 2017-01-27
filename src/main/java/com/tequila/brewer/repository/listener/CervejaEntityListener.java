package com.tequila.brewer.repository.listener;

import javax.persistence.PostLoad;

import com.tequila.brewer.model.Cerveja;
import com.tequila.brewer.storage.FotoStorage;

public class CervejaEntityListener {

//	@Autowired
//	private FotoStorage fotoStorage;
	
	@PostLoad
	public void postLoad(final Cerveja cerveja) {
//		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		cerveja.setUrlFoto(FotoStorage.URL + cerveja.getFotoOuMock());
		cerveja.setUrlThumbnailFoto(FotoStorage.URL + FotoStorage.THUMBNAIL_PREFIX + cerveja.getFotoOuMock());
	}
	
}
