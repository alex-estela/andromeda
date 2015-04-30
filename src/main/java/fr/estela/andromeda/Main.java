package fr.estela.andromeda;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	
	private static Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws Exception {
		
		logger.info("Starting Andromeda");
		logger.info("Press CTRL+C to terminate the JVM");
		
		org.apache.camel.spring.Main main = new org.apache.camel.spring.Main();
		main.enableHangupSupport();
		main.setApplicationContextUri("applicationContext.xml");
		main.run();
	}
}