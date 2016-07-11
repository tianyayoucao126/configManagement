package core.listener;

import core.event.ConfigModifyEvent;

public interface EventListener {

	public void update(ConfigModifyEvent event);
	
}
