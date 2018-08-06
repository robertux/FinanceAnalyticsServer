package org.robertux.financeAnalytics.FinanceAnalyticsServer;

import org.apache.logging.log4j.LogManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			LogManager.getLogger(App.class).error("Faltan parámetros necesarios: 1-Ruta del archivo de configuración para Log4j2. 2-Ruta del archivo de configuración para Spring");
			return;
		}
		System.setProperty("log4j.configurationFile", args[0]);
		System.setProperty("spring.config.location", args[1]);
		
		SpringApplication.run(App.class, args);
	}
}
