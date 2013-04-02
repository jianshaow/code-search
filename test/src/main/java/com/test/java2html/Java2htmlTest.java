package com.test.java2html;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import de.java2html.JavaSourceConversionSettings;
import de.java2html.converter.IJavaSourceConverter;
import de.java2html.javasource.JavaSource;
import de.java2html.javasource.JavaSourceParser;

public class Java2htmlTest {

	public static void main(String[] args) throws IOException {
		final JarFile jarFile = new JarFile(
				"D:/Users/ejiowuu/.m2/repository/org/mule/mule-core/1.4.4/mule-core-1.4.4-sources.jar");
		final JarEntry jarEntry = jarFile
				.getJarEntry("org/mule/transaction/TransactionCallback.java");
//		final InputStream input = jarFile.getInputStream(jarEntry);
		final OutputStreamWriter writer = new OutputStreamWriter(System.out,"utf-8");
		System.out.println(writer.getEncoding());

		final JavaSourceConversionSettings settings = JavaSourceConversionSettings
				.getDefault();
		final FileInputStream input = new FileInputStream("src/com/test/Sample.java");
		final InputStreamReader reader = new InputStreamReader(input, "utf-8");
		System.out.println(reader.getEncoding());
		final JavaSource source = new JavaSourceParser(settings
				.getConversionOptions()).parse(reader);
		final IJavaSourceConverter converter = settings.createConverter();
		converter.convert(source, settings.getConversionOptions(), writer);
	}
}
