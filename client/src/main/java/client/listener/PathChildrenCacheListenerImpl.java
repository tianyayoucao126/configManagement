package client.listener;


import javax.annotation.PostConstruct;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import client.handler.ConfigAddHandler;
import client.handler.ConfigDeleteHandler;
import client.handler.ConfigUpdateHandler;
import client.spring.SpringBeanUtils;
import core.entity.Node;
import core.event.AddEvent;
import core.event.DeleteEvent;
import core.event.UpdateEvent;
import core.listener.EventListener;
import core.listener.EventListenerImpl;

@Component("pathChildrenCacheListener")
public class PathChildrenCacheListenerImpl implements PathChildrenCacheListener {
	
	private static final Logger logger = Logger.getLogger(PathChildrenCacheListenerImpl.class);

	private  AddEvent addevent = new AddEvent();
	private  DeleteEvent  delEvent = new DeleteEvent();
	private  UpdateEvent updateEvent = new UpdateEvent();
	
	@Autowired
	private SpringBeanUtils springBeanUtils;
	
	@Autowired
	private  ConfigAddHandler configAddHandler ;
	@Autowired
	private  ConfigDeleteHandler configDeleteHandler ;
	@Autowired
	private  ConfigUpdateHandler configUpdateHandler ;
	
	
	public ConfigAddHandler getConfigAddHandler() {
		return configAddHandler;
	}


	public void setConfigAddHandler(ConfigAddHandler configAddHandler) {
		this.configAddHandler = configAddHandler;
	}


	public ConfigDeleteHandler getConfigDeleteHandler() {
		return configDeleteHandler;
	}


	public void setConfigDeleteHandler(ConfigDeleteHandler configDeleteHandler) {
		this.configDeleteHandler = configDeleteHandler;
	}


	public ConfigUpdateHandler getConfigUpdateHandler() {
		return configUpdateHandler;
	}


	public void setConfigUpdateHandler(ConfigUpdateHandler configUpdateHandler) {
		this.configUpdateHandler = configUpdateHandler;
	}

	@PostConstruct
	public void init(){
		EventListener addlistener = new EventListenerImpl(configAddHandler);
		EventListener deleteListener = new EventListenerImpl(configDeleteHandler);
		EventListener updateListener = new EventListenerImpl(configUpdateHandler);
		
		addevent.addListener(addlistener);
		delEvent.addListener(deleteListener);
		updateEvent.addListener(updateListener);
	}
	

	@Override
	public void childEvent(CuratorFramework client, PathChildrenCacheEvent event)
			throws Exception {
		Node data = new Node();
		data.setName(event.getData().getPath());
		data.setData(new String(event.getData().getData()));
		logger.debug("eventType+"+event.getType().toString()
				+",data="+data.getData()
				+",path="+data.getName());
		switch (event.getType()) {
		case CHILD_ADDED:
			// 添加子节点
			addevent.addConfig(data);
			break;
		case CHILD_UPDATED:
			// 子节点数据更新
			updateEvent.updateConfig(data);
			break;
		case CHILD_REMOVED:
			// 子节点删除
			delEvent.deleteConfig(data);
			break;
		default:
			break;
		}
	}

}
