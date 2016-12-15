package com.tequila.brewer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tequila.brewer.model.Cerveja;
import com.tequila.brewer.model.TipoPessoa;
import com.tequila.brewer.repository.Estados;

@Controller
@RequestMapping("/clientes")
public class ClientesController {
	
	@Autowired
	private Estados estados;
	
	@GetMapping("/novo")
	public ModelAndView novo(Cerveja cerveja) {
		ModelAndView mv = new ModelAndView("cliente/CadastroCliente");
		mv.addObject("tiposPessoa", TipoPessoa.values());
		mv.addObject("estados", estados.findAll());
		return mv;
	}
	
}
