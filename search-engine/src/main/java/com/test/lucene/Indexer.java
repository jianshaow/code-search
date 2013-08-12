package com.test.lucene;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.TypeDeclaration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.lucene.analysis.JavaAnalyzer;

public class Indexer {

	private static final Logger logger = LoggerFactory.getLogger(Indexer.class);

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			throw new Exception("Usage: java " + Indexer.class.getName()
					+ " <index dir> <target dir>");
		}
		final File indexDir = new File(args[0]);
		final File targetDir = new File(args[1]);

		final long start = System.currentTimeMillis();
		final int numIndexed = indexDirection(indexDir, targetDir);
		final long end = System.currentTimeMillis();

		logger.trace("Indexing " + numIndexed + " files took " + (end - start)
				+ " milliseconds");
	}

	public static int indexDirection(File indexDir, File targetDir)
			throws IOException, ParseException {
		if (!targetDir.exists() || !targetDir.isDirectory()) {
			throw new IOException(targetDir
					+ " dose not exist or is not a directoty");
		}

		final Directory dir = FSDirectory.open(indexDir);
		final JavaAnalyzer analyzer = new JavaAnalyzer();
		final IndexWriterConfig config = new IndexWriterConfig(
				Version.LUCENE_42, analyzer);
		config.setOpenMode(OpenMode.CREATE);
		final IndexWriter writer = new IndexWriter(dir, config);
		index(writer, targetDir);
		writer.commit();
		final int numIndexed = writer.numDocs();
		writer.close();
		return numIndexed;
	}

	public static void index(IndexWriter writer, File file) throws IOException,
			ParseException {
		if (file.isDirectory()) {
			final File[] subFiles = file.listFiles();
			for (final File subFile : subFiles) {
				index(writer, subFile);
			}
		} else if (file.getName().endsWith("sources.jar")) {
			indexJarFile(writer, file);
		} else if (file.getName().endsWith("java")) {
			indexJavaFile(writer, file);
		}
	}

	public static void indexJavaFile(IndexWriter writer, File file) {
		if (file.isHidden() || !file.exists() || !file.canRead()) {
			return;
		}
		try {
			final Document doc = new Document();
			final InputStream inputStream = new FileInputStream(file);
			final InputStreamReader reader = new InputStreamReader(
					new FileInputStream(file));
			final CompilationUnit compilationUnit = JavaParser
					.parse(inputStream);
			final List<TypeDeclaration> types = compilationUnit.getTypes();
			if (types != null) {
				for (final TypeDeclaration typeDeclaration : types) {
					if (typeDeclaration.getName() != null) {
						final Field field = new Field("type",
								typeDeclaration.getName(), buildType(true,
										true, false, false));
						doc.add(field);
					}
				}
			}
			final String uri = "file:///"
					+ file.getCanonicalPath().replace("\\", "/");
			doc.add(new Field("uri", uri, buildType(true, false, false, false)));
			doc.add(new Field("contents", reader, buildType(false, true, true,
					true)));
			doc.add(new Field("file", file.getName(), buildType(true, false,
					false, false)));
			writer.addDocument(doc);
		} catch (Throwable e) {
			logger.error("indexing " + file.getName() + " error...");
			logger.error(e.getMessage(), e);
		}

	}

	public static void indexJarFile(IndexWriter writer, File file)
			throws IOException, ParseException {
		if (file.isHidden() || !file.exists() || !file.canRead()) {
			return;
		}

		logger.info("Indexing " + file.getCanonicalPath());

		final JarFile jarFile = new JarFile(file);

		final Enumeration<JarEntry> entries = jarFile.entries();
		while (entries.hasMoreElements()) {
			final JarEntry jarEntry = entries.nextElement();
			if (jarEntry.getName().endsWith(".java")) {
				try {
					index(writer, file, jarFile, jarEntry);
				} catch (Throwable e) {
					logger.error("indexing " + jarEntry.getName() + " error...");
					logger.error(e.getMessage(), e);
					continue;
				}
			}
			writer.commit();
		}
		jarFile.close();
	}

	private static void index(IndexWriter writer, File file,
			final JarFile jarFile, final JarEntry jarEntry) throws IOException,
			ParseException, CorruptIndexException {
		logger.info("Indexing [" + jarEntry.getName() + "]");
		final Document doc = new Document();
		final InputStream inputStream = jarFile.getInputStream(jarEntry);
		final CompilationUnit compilationUnit = JavaParser.parse(inputStream);
		final List<TypeDeclaration> types = compilationUnit.getTypes();
		if (types != null) {
			for (final TypeDeclaration typeDeclaration : types) {
				if (typeDeclaration.getName() != null) {
					final Field field = new Field("type",
							typeDeclaration.getName(), buildType(true, true,
									false, false));
					doc.add(field);
				}
			}
		}
		final String uri = "jar:file:///"
				+ file.getCanonicalPath().replace("\\", "/") + "!/"
				+ jarEntry.getName();
		doc.add(new Field("uri", uri, buildType(true, false, false, false)));
		final InputStreamReader reader = new InputStreamReader(
				jarFile.getInputStream(jarEntry));
		doc.add(new Field("contents", reader,
				buildType(false, true, true, true)));
		doc.add(new Field("file", file.getName(), buildType(true, false, false,
				false)));
		writer.addDocument(doc);
	}

	private static FieldType buildType(final boolean stored,
			final boolean indexed, final boolean tokenized,
			boolean storeTermVectors) {
		final FieldType type = new FieldType();
		type.setIndexed(indexed);
		type.setStored(stored);
		type.setTokenized(tokenized);
		type.setStoreTermVectors(storeTermVectors);
		type.setStoreTermVectorOffsets(storeTermVectors);
		return type;
	}
}
