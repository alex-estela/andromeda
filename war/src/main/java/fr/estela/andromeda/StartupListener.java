package fr.estela.andromeda;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartupListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent sce) {
	}

	public void contextInitialized(ServletContextEvent sce) {
		try {
			fr.estela.andromeda.Main.main(null);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}