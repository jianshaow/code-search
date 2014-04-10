package com.test.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.net.URL;

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
import org.apache.lucene.search.highlight.TextFragment;
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
			final String uri = request.getParameter("uri");
			final String query = request.getParameter("query");

			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
			out.println("<HTML>");
			out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
			out.println("  <BODY>");
			out.println("    <PRE>");

			writeCode(out, query, uri);

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
			final String uri) throws Exception {
		final InputStream inputStream = new URL(uri).openStream();

		final Analyzer analyzer = new JavaAnalyzer();
		SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter(
				"<font color='red'>", "</font>");
		final QueryParser parser = new QueryParser(Version.LUCENE_47,
				"contents", analyzer);
		final Query query = parser.parse(queryString);
		final QueryScorer scorer = new QueryScorer(query);
		Highlighter highlighter = new Highlighter(simpleHTMLFormatter, scorer);
		highlighter.setTextFragmenter(new SimpleSpanFragmenter(scorer, 100));

		final String content = this.inputStream2String(inputStream);

		final TextFragment[] bestTextFragments = highlighter
				.getBestTextFragments(analyzer.tokenStream("contents",
						new StringReader(content)), content, true, 10);
		for (TextFragment fragment : bestTextFragments) {
			out.println(fragment.getFragNum());
			out.println(fragment);
			out.println("......");
			out.println();
		}
		inputStream.close();
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
