package com.test.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;

public class Searcher {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			throw new Exception("Usage: java " + Searcher.class.getName()
					+ " <index engine url> <query>");
		}

		final String baseURL = args[0];
		final SolrServer solrServer = new HttpSolrServer(baseURL);
		final String q = args[1];

		if (solrServer.ping().getStatus() != 200) {
			throw new IOException(baseURL + " is not available");
		}

		search(solrServer, q);
	}

	private static void search(SolrServer solrServer, String q)
			throws IOException, SolrServerException {

		final SolrQuery params = new SolrQuery(q);

		System.out.println(params);
		final long start = System.currentTimeMillis();
		final QueryResponse response = solrServer.query(params);
		long end = System.currentTimeMillis();

		System.out.println("Found " + response.getResults().getNumFound()
				+ " document(s) (in " + (end - start)
				+ " milliseconds) that matched query '" + q + "':");

		for (SolrDocument doc : response.getResults()) {
			System.out.println("url=" + doc.get("url"));
			System.out.println("jarFile=" + doc.get("jarFile"));
			System.out.println("jarEntry=" + doc.get("jarEntry"));
		}
	}
}
