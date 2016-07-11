package client;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

public class Test {

	public static void main(String[] args) throws IOException,
			URISyntaxException {
//		Properties pro = new Properties();
//		System.out.println(pro.getProperty("key4"));
		test();
	}

	@SuppressWarnings("static-access")
	public static void test() throws IOException, URISyntaxException {
		InputStream ins = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("configManagement.properties");

		Properties properties = new Properties();
		InputStreamReader reader = new InputStreamReader(ins);
		properties.load(reader);
		// properties.load(ins);
		System.out.println(properties.getProperty("config.properties.file"));
		ins.close();

		URL url = Thread
				.currentThread()
				.getContextClassLoader()
				.getSystemResource(
						properties.getProperty("config.properties.file"));
		System.out.println(url.toURI());
		FileReader appReader = new FileReader(new File(url.toURI()));

		Properties appProp = new Properties();
		appProp.load(appReader);
		appReader.close();
		System.out.println("before change~~~");
		System.out.println("key3=" + appProp.getProperty("key3"));
		System.out.println("key4=" + appProp.getProperty("key4"));
		// appProp.setProperty("key3", "value3");
		// appProp.setProperty("key4", "value4");
		appProp.put("key3", "value3");

		FileWriter fw = new FileWriter(new File(url.toURI()));
		appProp.store(fw, null);
		System.out.println("after change~~~");
		System.out.println("key3=" + appProp.getProperty("key3"));
		System.out.println("key4=" + appProp.getProperty("key4"));
		fw.flush();
		fw.close();
		// FileOutputStream fos = new FileOutputStream(new File(pathname));
	}

}
