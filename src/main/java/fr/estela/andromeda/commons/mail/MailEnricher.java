package fr.estela.andromeda.commons.mail;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.apache.camel.Exchange;

public interface MailEnricher {
	
	public void enrichMessageFromExchange(Message message, Exchange exchange) throws MessagingException;
}