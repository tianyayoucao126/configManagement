package client.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import client.file.FileUtil;
import client.spring.SpringBeanRefresh;
import core.event.ConfigModifyEvent;
import core.handler.Handler;

@Component("configUpdateHandler")
public class ConfigUpdateHandler implements Handler {

	@Autowired
	private SpringBeanRefresh springBeanRefresh;
	
	public SpringBeanRefresh getSpringBeanRefresh() {
		return springBeanRefresh;
	}

	public void setSpringBeanRefresh(SpringBeanRefresh springBeanRefresh) {
		this.springBeanRefresh = springBeanRefresh;
	}
	
	@Override
	public void handle(ConfigModifyEvent event) {
		//TODO 更新到文件
		FileUtil.saveApplicationConfig(event);
		//TODO spring容器中重新注册bean,暂时先不刷新spring中的bean，后续用jvm agent来实现
		springBeanRefresh.refreshBean(event);
	}

}
