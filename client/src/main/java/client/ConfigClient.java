package client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;






import javax.annotation.PostConstruct;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import client.file.FileUtil;
import core.entity.Node;
import core.zookeeper.ClientFactory;
import core.zookeeper.ZKOperation;

@Component
public class ConfigClient {

	private static final Logger logger = Logger.getLogger(ConfigClient.class);
	
	private static final CuratorFramework client = ClientFactory.getClient(
			Integer.valueOf(FileUtil.getClientConfigProp().getProperty("connection.retry.times")),
			Integer.valueOf(FileUtil.getClientConfigProp().getProperty("sleepMS.between.retryes")),
			FileUtil.getClientConfigProp().getProperty("zookeeper.connection")
			);
	
	public static CuratorFramework getClient() {
		return client;
	}

	@Autowired
	private PathChildrenCacheListener pathChildrenCacheListener;
	

	public PathChildrenCacheListener getPathChildrenCacheListener() {
		return pathChildrenCacheListener;
	}

	public void setPathChildrenCacheListener(
			PathChildrenCacheListener pathChildrenCacheListener) {
		this.pathChildrenCacheListener = pathChildrenCacheListener;
	}

	private static final ZKOperation zkOperation = ClientFactory.getZKOperation(client);
	
	private static final Map<String, String> beanConfigData = new HashMap<String,String>();
	
	private static PathChildrenCache cache = null;
	
	private static final String PATH_PREFIX="/";
	
	public static PathChildrenCache getCache() {
		return cache;
	}

	@PostConstruct
	public void init() throws Exception{
		Properties properties = FileUtil.loadApplicationConfig();
		String rootNode = FileUtil.getClientConfigProp().get("app.name").toString();
		Iterator<Entry<Object, Object>> iterator = properties.entrySet().iterator();
		if(client.checkExists().forPath(PATH_PREFIX+rootNode) == null){
			client.create().forPath(PATH_PREFIX+rootNode);
			cache = new PathChildrenCache(client, PATH_PREFIX+rootNode, true);
			cache.getListenable().addListener(pathChildrenCacheListener);
			while (iterator.hasNext()) {
				Entry<Object, Object> entry = iterator.next();
				String key = entry.getKey().toString();
				String nodeName = key.substring(0, key.indexOf("."));
				Node node = new Node();
				node.setName(PATH_PREFIX+rootNode+PATH_PREFIX+nodeName);
				node.setData(getBeanConfigData(properties,nodeName));
				logger.debug("create node name="+node.getName()+",data="+node.getData());
				zkOperation.createNode(node);
				
			}
		}
		if(cache == null){
			cache = new PathChildrenCache(client, PATH_PREFIX+rootNode, true);
			cache.getListenable().addListener(pathChildrenCacheListener);
		}
		cache.start();
		logger.debug("start listening~~~~");
		cache.clearAndRefresh();
	}

	public void stop(){
		try {
			cache.close();
			getClient().close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
	}
	
	private static String getBeanConfigData(Properties properties,
			String nodeName) {
		if(beanConfigData.size()>0){
			return beanConfigData.get(nodeName);
		}
		String data = "";
		Iterator<Entry<Object, Object>> iterator = properties.entrySet().iterator();
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> tmp = new HashMap<String,Object>();
		while (iterator.hasNext()) {
			Entry<Object, Object> entry = iterator.next();
			if(entry.getKey().toString().startsWith(nodeName)){
				tmp.put(entry.getKey().toString(), entry.getValue());
			}
		}
		try {
			data = mapper.writeValueAsString(tmp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	
	
}
