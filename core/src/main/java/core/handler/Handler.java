package core.handler;

import core.event.ConfigModifyEvent;

public interface Handler {

	void handle(ConfigModifyEvent event);
	
}
