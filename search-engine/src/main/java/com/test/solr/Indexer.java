package com.test.solr;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.TypeDeclaration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Indexer {

	private static final Logger logger = LoggerFactory.getLogger(Indexer.class);

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			throw new Exception("Usage: java " + Indexer.class.getName()
					+ " <index engine url> <data dir>");
		}
		final File dataDir = new File(args[1]);

		final SolrServer solrServer = new HttpSolrServer(args[0]);

		final long start = System.currentTimeMillis();
		final int numIndexed = index(solrServer, dataDir);
		final long end = System.currentTimeMillis();

		System.out.println("Indexing " + numIndexed + " files took "
				+ (end - start) + " milliseconds");
		solrServer.shutdown();
	}

	public static int index(SolrServer solrServer, File dataDir)
			throws IOException, ParseException, SolrServerException {
		if (!dataDir.exists() || !dataDir.isDirectory()) {
			throw new IOException(dataDir
					+ " dose not exist or is not a directoty");
		}

		indexDirectory(solrServer, dataDir);

		final UpdateResponse response = solrServer.commit();
		return response.getResponse().size();
	}

	private static void indexDirectory(SolrServer solrServer, File dir)
			throws IOException, ParseException {
		final File[] files = dir.listFiles();

		for (final File f : files) {
			if (f.isDirectory()) {
				indexDirectory(solrServer, f);
			} else if (f.getName().endsWith("sources.jar")) {
				indexJarFile(solrServer, f);
			}
		}
	}

	private static void indexJarFile(SolrServer solrServer, File f)
			throws IOException, ParseException {
		if (f.isHidden() || !f.exists() || !f.canRead()) {
			return;
		}

		logger.info("Indexing " + f.getCanonicalPath());
		final JarFile jarFile = new JarFile(f);

		final Enumeration<JarEntry> entries = jarFile.entries();
		while (entries.hasMoreElements()) {
			final JarEntry jarEntry = entries.nextElement();
			if (jarEntry.getName().endsWith(".java")) {
				try {
					index(solrServer, f, jarFile, jarEntry);
				} catch (Throwable e) {
					logger.error("indexing " + jarEntry.getName() + " error...");
					logger.error(e.getMessage(), e);
					continue;
				}
			}
		}
		jarFile.close();
	}

	private static void index(SolrServer solrServer, File f,
			final JarFile jarFile, final JarEntry jarEntry) throws IOException,
			ParseException, SolrServerException {
		logger.info("Indexing [" + jarEntry.getName() + "]");

		final SolrInputDocument doc = new SolrInputDocument();
		final InputStream inputStream = jarFile.getInputStream(jarEntry);
		final InputStreamReader reader = new InputStreamReader(
				jarFile.getInputStream(jarEntry));
		final CompilationUnit compilationUnit = JavaParser.parse(inputStream);
		final List<TypeDeclaration> types = compilationUnit.getTypes();
		if (types != null) {
			for (final TypeDeclaration typeDeclaration : types) {
				if (typeDeclaration.getName() != null) {
					doc.addField("type", typeDeclaration.getName());
				}
			}
		}
		doc.addField("url", f.getCanonicalPath().replace("\\", "/"));
		doc.addField("contents", reader);
		doc.addField("jarFile", f.getName());
		doc.addField("jarEntry", jarEntry.getName());
		solrServer.add(doc);
	}
}
