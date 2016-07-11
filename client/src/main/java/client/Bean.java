package client;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("bean")
@Scope("singleton")
public class Bean {

	private String name="1";
	
	private String connString;
	
	private Integer age=1;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getConnString() {
		return connString;
	}

	public void setConnString(String connString) {
		this.connString = connString;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
}
