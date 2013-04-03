package com.test.servlet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.jar.JarFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.util.Version;

import com.test.lucene.analysis.JavaAnalyzer;

public class ShowHighlightCodeServlet extends HttpServlet {

	private static final long serialVersionUID = -7417472204914390926L;

	public ShowHighlightCodeServlet() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			final String url = request.getParameter("url");
			final String entry = request.getParameter("entry");
			final String query = request.getParameter("query");

			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
			out.println("<HTML>");
			out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
			out.println("  <BODY>");
			out.println("    <PRE>");

			writeCode(out, query, url, entry);

			out.println("    </PRE>");
			out.println("  </BODY>");
			out.println("</HTML>");
			out.flush();
			out.close();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void writeCode(final PrintWriter out, final String queryString,
			final String url, final String entry) throws Exception {
		final File file = new File(new URI(url.replaceAll(" ", "%20")));
		InputStream inputStream = null;
		JarFile jarFile = null;
		if (url.endsWith(".jar")) {
			jarFile = new JarFile(file);
			inputStream = jarFile.getInputStream(jarFile.getJarEntry(entry));
		} else {
			inputStream = new FileInputStream(file);
		}

		final Analyzer analyzer = new JavaAnalyzer();
		SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter(
				"<font color='red'>", "</font>");
		final QueryParser parser = new QueryParser(Version.LUCENE_42,
				"contents", analyzer);
		final Query query = parser.parse(queryString);
		final QueryScorer scorer = new QueryScorer(query);
		Highlighter highlighter = new Highlighter(simpleHTMLFormatter, scorer);
		highlighter.setTextFragmenter(new SimpleSpanFragmenter(scorer, 1000));

		final String content = this.inputStream2String(inputStream);

		// out.println(highlighter.getBestFragment(analyzer, "contents",
		// content));
		final String[] fragments = highlighter.getBestFragments(analyzer,
				"contents", content, 10);
		for (String fragment : fragments) {
			out.println(fragment);
			out.println("-------------------------------");
			out.println("......");
			out.println("-------------------------------");
		}
		if (jarFile != null) {
			jarFile.close();
		}
	}

	private String inputStream2String(InputStream in) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[4096];
		int count = -1;
		while ((count = in.read(data, 0, 4096)) != -1) {
			outStream.write(data, 0, count);
		}

		return new String(outStream.toByteArray(), "UTF-8");
	}

	public void init() throws ServletException {
	}
}
