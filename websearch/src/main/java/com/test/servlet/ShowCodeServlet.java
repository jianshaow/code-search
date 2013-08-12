package com.test.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.java2html.JavaSourceConversionSettings;
import de.java2html.converter.IJavaSourceConverter;
import de.java2html.javasource.JavaSource;
import de.java2html.javasource.JavaSourceParser;
import de.java2html.options.JavaSourceConversionOptions;

public class ShowCodeServlet extends HttpServlet {

	private static final long serialVersionUID = -7417472204914390926L;

	public ShowCodeServlet() {
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

			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
			out.println("<HTML>");
			out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
			out.println("  <BODY>");

			writeCode(out, uri);

			out.println("  </BODY>");
			out.println("</HTML>");
			out.flush();
			out.close();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	private void writeCode(PrintWriter out, final String uri)
			throws URISyntaxException, IOException,
			UnsupportedEncodingException {
		final InputStream inputStream = new URL(uri).openStream();

		final JavaSourceConversionSettings settings = JavaSourceConversionSettings
				.getDefault();
		final JavaSourceConversionOptions conversionOptions = settings
				.getConversionOptions();
		conversionOptions.setShowLineNumbers(true);
		conversionOptions.setTabSize(4);
		conversionOptions.setLineAnchorPrefix("line-");
		conversionOptions.setAddLineAnchors(true);
		final InputStreamReader reader = new InputStreamReader(inputStream,
				"utf8");
		final JavaSource source = new JavaSourceParser(conversionOptions)
				.parse(reader);
		final IJavaSourceConverter converter = settings.createConverter();
		converter.convert(source, conversionOptions, out);
		inputStream.close();
	}

	public void init() throws ServletException {
	}
}
