package core.zookeeper;


import java.util.List;

import core.entity.Node;

/**
 * zk节点相关操作
 * @author Administrator
 *
 */
public interface ZKOperation {

	/**
	 * 创建节点
	 * @param node 节点信息
	 */
	void createNode(Node node);
	
	/**
	 * 递归创建节点,节点数据为null
	 * @param nodeName 节点名称，以/分隔
	 */
	void createNodeByRecursion(String nodeName);
	
	/**
	 * 更新节点数据
	 * @param node 要更新的节点信息
	 */
	void updateNodeData(Node node);
	
	/**
	 * 删除节点，只能删除叶子节点
	 * @param node 节点
	 * 
	 */
	void deleteNode(Node node);
	
	/**
	 * 获取指定节点的第一级所有子节点
	 * @param nodeName 节点名称
	 * @return
	 */
	List<Node> getFirstChildren(String nodeName);
	
}
