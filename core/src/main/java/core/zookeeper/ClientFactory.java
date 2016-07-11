package core.zookeeper;


import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;

/**
 * 创建client
 * @author Administrator
 *
 */
public class ClientFactory {

	private static CuratorFramework client = null;
	
	private static ZKOperation zkOperation = null;
	
	
	synchronized public static CuratorFramework  getClient(int connectionRetyTimes,int sleepMSBetweenRetryes,String zkConnectionStr){
		if(client == null){
			RetryNTimes retry = new RetryNTimes(connectionRetyTimes,sleepMSBetweenRetryes);
			client = CuratorFrameworkFactory.newClient(zkConnectionStr, retry);
			client.start();
		}
		return client;
	} 
	
	synchronized public static ZKOperation getZKOperation(CuratorFramework client){
		zkOperation = new ZKOperationImpl(client);
		return zkOperation;
	}
	
	/*@SuppressWarnings("static-access")
	private static Properties loadConfig(){
		Properties prop = new Properties();
		try {
			prop.load(Thread.currentThread().getContextClassLoader().getSystemResourceAsStream("configManagement.properties"));
		} catch (IOException e) {
			logger.error("file configManagement.properties not found!!!please check it!", e);
		}
		return prop;
	}*/
	
}
