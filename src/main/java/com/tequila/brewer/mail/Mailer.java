package com.tequila.brewer.mail;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.tequila.brewer.model.Cerveja;
import com.tequila.brewer.model.ItemVenda;
import com.tequila.brewer.model.Venda;
import com.tequila.brewer.storage.FotoStorage;

@Component
public class Mailer {

	private static Logger logger = LoggerFactory.getLogger(Mailer.class);
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private TemplateEngine thymeleaf;
	
	@Autowired
	private FotoStorage fotoStorage;
	
	@Async
	public void enviar(Venda venda) {
		Context context = new Context(new Locale("pt", "BR"));
		context.setVariable("venda", venda);
		context.setVariable("logo", "logo");
		
		Map<String, String> fotos = new HashMap<>();
		boolean adicionarMockCerveja = false;
		for(ItemVenda item : venda.getItens()) {
			Cerveja cerveja = item.getCerveja();
			
			/* #### feito por mim #### */
			String foto = !cerveja.getFoto().isEmpty() ? cerveja.getFoto() : "cerveja-mock.png";
			String contentType = !cerveja.getContentType().isEmpty() ? cerveja.getContentType() : "image/png";
			String cid = "foto-" + cerveja.getCodigo();
			context.setVariable(cid, cid);
			fotos.put(cid, foto + "|" + contentType);
			/* ####				#### */
			
//			if (!StringUtils.isEmpty(cerveja.getFoto())) {
//				String cid = "foto-" + cerveja.getCodigo();
//				context.setVariable(cid, cid);
//				
//				fotos.put(cid, cerveja.getFoto() + "|" + cerveja.getContentType());
//			} else {
//				adicionarMockCerveja = true;
//				context.setVariable("mockCerveja", "mockCerveja");
//			}
			
		}
		
		try {
			String email = thymeleaf.process("mail/ResumoVenda", context);
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			helper.setFrom("brunoliveirabatista@gmail.com");
			helper.setTo(venda.getCliente().getEmail());
			helper.setSubject(String.format("Brewer - Venda nº %d", venda.getCodigo()));
			helper.setText(email, true);
			
			helper.addInline("logo", new ClassPathResource("static/images/logo-gray.png"));
			if (adicionarMockCerveja) {
				helper.addInline("logo", new ClassPathResource("static/images/cerveja-mock.png"));
			}
			
			for (String cid : fotos.keySet()) {		
				String[] fotoContentType = fotos.get(cid).split("\\|");
				String foto = fotoContentType[0];
				String contentType = fotoContentType[1];
				byte[] arrayFoto = fotoStorage.recuperarThumbnail(foto);
				helper.addInline(cid, new ByteArrayResource(arrayFoto), contentType);
			}
			
			
			mailSender.send(mimeMessage);
		}catch (MessagingException e) {
			logger.error("Erro enviando e-mail", e);
		}
			
	}
	
}
