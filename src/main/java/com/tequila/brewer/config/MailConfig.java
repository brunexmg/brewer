package com.tequila.brewer.config;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.tequila.brewer.mail.Mailer;

@Configuration
@ComponentScan(basePackageClasses = Mailer.class)
@PropertySources({
	@PropertySource({ "classpath:env/mail-${ambiente:local}.properties" }),
	@PropertySource(value = { "file:///${USERPROFILE}/.brewer-mail.properties" }, ignoreResourceNotFound = true)
})
public class MailConfig {

	@Autowired
	private Environment env;
	
	private static final Logger logger = LoggerFactory.getLogger(MailConfig.class);
	
	@Bean
	public JavaMailSender mailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.sendgrid.net");
		mailSender.setPort(587);
		mailSender.setUsername(env.getProperty("SENDGRID_USERNAME"));
		mailSender.setPassword(env.getProperty("SENDGRID_PASSWORD"));
		
		if (logger.isDebugEnabled()) {
			logger.debug("Login and Password for Mail SMTP");
		}
		
		Properties props= new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp,starttls.enable", true);
		props.put("mail.debug", false);
		props.put("mail.smtp.connectiontimeout", 10000); //miliseconds

		mailSender.setJavaMailProperties(props);
		
		return mailSender;
	}
	
}
