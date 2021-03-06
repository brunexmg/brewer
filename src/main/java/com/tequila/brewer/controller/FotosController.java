package com.tequila.brewer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import com.tequila.brewer.dto.FotoDTO;
import com.tequila.brewer.storage.FotoStorage;
import com.tequila.brewer.storage.FotoStorageRunnable;

@RestController
@RequestMapping("/fotos")
public class FotosController {

	@Autowired
	private FotoStorage fotoStorage;
	
	@PostMapping
	public DeferredResult<FotoDTO> upload(@RequestParam("files[]") MultipartFile[] files) {
		 DeferredResult<FotoDTO> resultado = new  DeferredResult<>();
		 
		 Thread thread = new Thread(new FotoStorageRunnable(files, resultado, fotoStorage));
		 thread.start();

		 return resultado;
	}
	
	@GetMapping("/{nome:.*}")
	public byte[] recuperar(@PathVariable String nome) {
		return fotoStorage.recuperar(nome);
	}
	
	@RequestMapping(path = "/{nome:.*}", method = { RequestMethod.DELETE })
	public void apagarFoto(@PathVariable String nome) {
		fotoStorage.apagarFoto(nome);
	}
	
}
