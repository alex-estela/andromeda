package fr.estela.andromeda.twitter.mail;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.apache.camel.Exchange;

import twitter4j.Status;
import fr.estela.andromeda.commons.mail.MailEnricher;

public class TwitterMailEnricher implements MailEnricher {

	public void enrichMessageFromExchange(Message message, Exchange exchange) throws MessagingException {
		
		Status status = (Status) exchange.getIn().getBody();
		
		String title = (status.getUser() != null ? status.getUser().getName() : "unknown") + ": " + status.getText();

		String content = title;
		if (status.getUser() != null) {
			String url= "https://twitter.com/" + status.getUser().getScreenName() + "/status/" + status.getId();
			content += "<br/><br/>Source: " + url;
		}
		
		message.setSubject(title);
		message.setContent(content, "text/html; charset=utf-8");
	}

}