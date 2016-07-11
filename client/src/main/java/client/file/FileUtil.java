package client.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

import org.apache.log4j.Logger;



import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;





import core.event.ConfigModifyEvent;

public class FileUtil {

	private static Properties clientConfigProp = new Properties();
	
	private static final Logger logger = Logger.getLogger(FileUtil.class);
	
	public static Properties getClientConfigProp() {
		try {
			if(clientConfigProp.size() == 0){
				ClassPathResource rs = new ClassPathResource("/configManagement.properties");
				clientConfigProp.putAll(PropertiesLoaderUtils.loadProperties(rs));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return clientConfigProp;
	}

	
	public static Properties loadApplicationConfig(){
		Properties prop = new Properties();
		try {
			Resource rs = new ClassPathResource("/"+clientConfigProp.getProperty("config.properties.file"));
			prop.putAll(PropertiesLoaderUtils.loadProperties(rs));
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return prop;
	}
	
	public static void saveApplicationConfig(ConfigModifyEvent event){
		try {
			Properties prop = loadApplicationConfig();
			ObjectMapper mapper = new ObjectMapper();
			JsonNode tree = mapper.reader().readTree(event.getData().getData());
			Iterator<String> itr = tree.getFieldNames();
			while(itr.hasNext()){
				String key = itr.next();
				prop.setProperty(key, tree.get(key).getTextValue());
			}
			ClassPathResource rs = new ClassPathResource("/"+clientConfigProp.getProperty("config.properties.file"));
			FileWriter writer = new FileWriter(new File(rs.getURI()));
			prop.store(writer, null);
			writer.close();
			logger.debug("config "+event.getData().getData()+",saved!");
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
