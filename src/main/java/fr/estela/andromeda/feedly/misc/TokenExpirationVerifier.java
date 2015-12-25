package fr.estela.andromeda.feedly.misc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import fr.estela.andromeda.Main;

@Component
public class TokenExpirationVerifier implements Processor {

	private static Logger logger = LoggerFactory.getLogger(Main.class);

	@Value("${feedly.tokenExpirationDate}")
	private String tokenExpirationDate;
	
	public void process(Exchange exchange) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(tokenExpirationDate);

		long day = 24 * 60 * 60 * 1000;
		long alertDate = date.getTime() - 7 * day;
		
		if (new Date().getTime() > alertDate) {
			
			logger.info("Feedly token close to expiration: " + tokenExpirationDate);
			
			exchange.getIn().setBody("Feedly token is about to expire or already expired: " + tokenExpirationDate);
		}
	}
}
