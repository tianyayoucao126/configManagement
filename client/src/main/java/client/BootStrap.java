package client;

import java.io.IOException;

public class BootStrap {

	public static void start() throws Exception{
//		ConfigClient.init();
	}
	
	public static void main(String[] args) throws Exception {
		start();
		Thread.sleep(Integer.MAX_VALUE);
	}
	
	public void stop() throws IOException{
		ConfigClient.getCache().close();
		ConfigClient.getClient().close();
	}

}