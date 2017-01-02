package com.tequila.brewer.service.exception;

public class ImpossivelExcluirUsuarioException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ImpossivelExcluirUsuarioException(String message) {
		super(message);
	}

}
