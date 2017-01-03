package com.tequila.brewer.service.event.venda;

import com.tequila.brewer.model.Venda;

public class VendaEvent {
	
	private Venda venda;

	public VendaEvent(Venda venda) {
		this.venda = venda;
	}

	public Venda getVenda() {
		return venda;
	}

}
