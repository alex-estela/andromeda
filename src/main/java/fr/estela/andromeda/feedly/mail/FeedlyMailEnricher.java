package fr.estela.andromeda.feedly.mail;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.apache.camel.Exchange;

import fr.estela.andromeda.commons.mail.MailEnricher;
import fr.estela.andromeda.feedly.api.Feed;

public class FeedlyMailEnricher implements MailEnricher {

	public void enrichMessageFromExchange(Message message, Exchange exchange) throws MessagingException {
		
		Feed item = (Feed) exchange.getIn().getBody();
		String title = item.getTitle();
		StringBuilder contentBuilder = new StringBuilder();
		contentBuilder.append(item.getContent() != null ? item.getContent().getContent() : 
						 (item.getSummary() != null ? item.getSummary().getContent() : ""));
		contentBuilder.append("<br/>Source: " + (item.getOrigin() != null ? item.getOrigin().getHtmlUrl() : item.getOriginId()));
		
		message.setSubject(title);
		message.setContent(contentBuilder.toString(), "text/html; charset=utf-8");
	}

}