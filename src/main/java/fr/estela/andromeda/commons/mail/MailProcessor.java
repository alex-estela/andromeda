package fr.estela.andromeda.commons.mail;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class MailProcessor implements Processor {
	
	private Properties smtpProperties;
	private String sourceId;
	private String targetEmailAdress;
	private MailEnricher mailEnricher;

	public MailProcessor(String smtpPropertiesFilePath, String sourceId, String targetEmailAdress) throws IOException {
		
		smtpProperties = new Properties();
		smtpProperties.load(this.getClass().getResourceAsStream(smtpPropertiesFilePath));
		
		this.sourceId = sourceId;
		this.targetEmailAdress = targetEmailAdress;
	}
	
	public MailProcessor(String smtpPropertiesFilePath, String sourceId, String targetEmailAdress, MailEnricher mailEnricher) throws IOException {
		
		smtpProperties = new Properties();
		smtpProperties.load(this.getClass().getResourceAsStream(smtpPropertiesFilePath));
		
		this.sourceId = sourceId;
		this.targetEmailAdress = targetEmailAdress;
		this.mailEnricher = mailEnricher;
	}
	
	public void process(Exchange exchange) throws AddressException, MessagingException, UnsupportedEncodingException {
		
		final String auth = smtpProperties.getProperty("mail.smtp.auth");
		final String username = smtpProperties.getProperty("mail.smtp.user");
		final String password = smtpProperties.getProperty("mail.smtp.password");
		final String debug = smtpProperties.getProperty("mail.smtp.debug");
		
		Session session = null;
		if (auth != null && auth.trim().equalsIgnoreCase("true")) 
			session = Session.getInstance(smtpProperties,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				}
			);
		else session = Session.getInstance(smtpProperties);
		if (debug != null && debug.trim().equalsIgnoreCase("true")) session.setDebug(true);
		
		Message message = new MimeMessage(session);
		
		// set sender source
		message.setFrom(new InternetAddress(username, sourceId));
		
		// set target
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(targetEmailAdress));

		// custom enrich subject and text properties 
		if (mailEnricher != null) mailEnricher.enrichMessageFromExchange(message, exchange);
		else if (exchange.getIn().getBody() != null) {
			message.setSubject(exchange.getIn().getBody().toString());
			message.setContent(exchange.getIn().getBody().toString(), "text/html; charset=utf-8");
		}

		Transport.send(message);
	}
}