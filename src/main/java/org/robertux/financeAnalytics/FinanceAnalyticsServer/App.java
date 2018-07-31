package org.robertux.financeAnalytics.FinanceAnalyticsServer;

import org.apache.logging.log4j.LogManager;
import org.robertux.financeAnalytics.FinanceAnalyticsServer.data.DataSourceConfigurator;

/**
 * Main app
 *
 */
public class App {
	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			LogManager.getLogger(App.class).error("Faltan parámetros necesarios: 1-Ruta del archivo de configuración para Log4j2. 2-Ruta del archivo de configuración para la conexión JDBC");
			return;
		}
		System.setProperty("log4j.configurationFile", args[0]);
		System.setProperty(DataSourceConfigurator.CONF_FILE_PROP, args[1]);
		
//		URI baseUri = UriBuilder.fromUri("http://localhost/").port(9998).build();
//		ResourceConfig config = new ResourceConfig(AccountsController.class);
//		Server server = JettyHttpContainerFactory.createServer(baseUri, config);
//		server.start();
	}
}
