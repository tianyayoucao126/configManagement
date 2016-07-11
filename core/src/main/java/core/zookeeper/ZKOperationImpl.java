package core.zookeeper;

import java.util.ArrayList;
import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;

import core.entity.Node;

public class ZKOperationImpl implements ZKOperation {

	private CuratorFramework client = null;
	
	private static final Logger logger = Logger.getLogger(ZKOperationImpl.class);
	
	protected ZKOperationImpl(CuratorFramework client) {
		this.client = client;
	}
	
	@Override
	public void createNode(Node node) {
		try {
			client.create().withMode(CreateMode.PERSISTENT).forPath(node.getName(), node.getData().getBytes());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}

	@Override
	public void createNodeByRecursion(String nodeName) {
		String[] pathes = nodeName.split("/");
		try {
			for (int i = 0; i < pathes.length; i++) {
				client.create().withMode(CreateMode.PERSISTENT).forPath(pathes[i]);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}

	@Override
	public void updateNodeData(Node node) {
		try {
			client.setData().forPath(node.getName(), node.getData().getBytes());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}

	@Override
	public void deleteNode(Node node) {
		try {
			int childrenNmuber = client.getChildren().forPath(node.getName()).size();
			if(childrenNmuber == 0){
				client.delete().forPath(node.getName());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}

	@Override
	public List<Node> getFirstChildren(String nodeName) {
		List<Node> nodes = new ArrayList<Node>();
		Node node = null;
		try {
			List<String> pathes = client.getChildren().forPath(nodeName);
			for (String path : pathes) {
				node = new Node();
				node.setName(path);
				node.setData(new String(client.getData().forPath(path)));
				nodes.add(node);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return nodes;
	}

}
