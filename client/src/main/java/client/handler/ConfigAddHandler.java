package client.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import client.file.FileUtil;
import client.spring.SpringBeanRefresh;
import core.event.ConfigModifyEvent;
import core.handler.Handler;

@Component("configAddHandler")
public class ConfigAddHandler implements Handler {

	@Autowired
	private SpringBeanRefresh springBeanRefresh;
	

	@Override
	public void handle(ConfigModifyEvent event) {
		// 更新到文件
		FileUtil.saveApplicationConfig(event);
		// spring容器中重新注册bean,暂时先不刷新spring中的bean，后续用jvm agent来实现
		springBeanRefresh.refreshBean(event);
	}
	
	
	public SpringBeanRefresh getSpringBeanRefresh() {
		return springBeanRefresh;
	}

	public void setSpringBeanRefresh(SpringBeanRefresh springBeanRefresh) {
		this.springBeanRefresh = springBeanRefresh;
	}
}