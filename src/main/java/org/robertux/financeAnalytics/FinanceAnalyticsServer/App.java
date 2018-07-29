package org.robertux.financeAnalytics.FinanceAnalyticsServer;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.robertux.financeAnalytics.FinanceAnalyticsServer.controllers.AccountsController;

/**
 * Main app
 *
 */
public class App {
	public static void main(String[] args) throws Exception {
		URI baseUri = UriBuilder.fromUri("http://localhost/").port(9998).build();
		ResourceConfig config = new ResourceConfig(AccountsController.class);
		Server server = JettyHttpContainerFactory.createServer(baseUri, config);
		server.start();
	}
}
