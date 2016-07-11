package core.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;

public class CuratorClient {

	
	public static void main(String[] args) throws Exception {
		RetryNTimes retry = new RetryNTimes(10, 100000);
		CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", retry);
		client.start();
		for(String str:client.getChildren().forPath("/")){
			System.out.println(str);
		}
		Thread.sleep(Integer.MAX_VALUE);
	}

}
