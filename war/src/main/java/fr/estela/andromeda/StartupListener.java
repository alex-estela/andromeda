package fr.estela.andromeda;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartupListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent sce) {
	}

	public void contextInitialized(ServletContextEvent sce) {
		try {
			// TODO
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}