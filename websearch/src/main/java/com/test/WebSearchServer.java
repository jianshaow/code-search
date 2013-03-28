package com.test;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;

public class WebSearchServer {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		final Server server = new Server();
		final Connector connector = new SelectChannelConnector();
		connector.setPort(9090);
		server.setConnectors(new Connector[] { connector });
		final WebAppContext webapp = new WebAppContext();
		webapp.setContextPath("/codeSearch");
		webapp.setWar("src/main/webapp");
		server.setHandler(webapp);
		server.start();
	}
}
