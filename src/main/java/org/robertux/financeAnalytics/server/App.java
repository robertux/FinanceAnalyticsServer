package org.robertux.financeAnalytics.server;

import org.apache.logging.log4j.LogManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
	public static void main(String[] args) throws Exception {
		if (args.length < 1) {
			LogManager.getLogger(App.class).error("Faltan parámetros necesarios: 1-Ruta del archivo de configuración para Spring");
			return;
		}
		System.setProperty("spring.config.location", args[0]);
		
		SpringApplication.run(App.class, args);
	}
}
