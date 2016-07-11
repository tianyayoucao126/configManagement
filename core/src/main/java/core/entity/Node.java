package core.entity;

/**
 * 节点数据
 * @author Administrator
 *
 */
public class Node {

	//节点名称，即path
	private String name;
	//节点的数据,json
	private String data;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
