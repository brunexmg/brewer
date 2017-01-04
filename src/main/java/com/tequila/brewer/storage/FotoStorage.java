package com.tequila.brewer.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorage {

//	public String salvarTemporariamente(MultipartFile[] files);
	public String salvar(MultipartFile[] files);

//	public byte[] recuperarFotoTemporaria(String nome);

//	public void apagarFotoTemporaria(String nome);
	
//	public void salvar(String foto);

	public byte[] recuperar(String foto);
	
	public byte[] recuperarThumbnail(String foto);

	public void apagarFoto(String foto);

	public String getUrl(String foto);
	
}
