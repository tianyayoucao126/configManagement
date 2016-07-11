package core.listener;

import core.event.ConfigModifyEvent;
import core.handler.Handler;

public class EventListenerImpl implements EventListener {

	private Handler handler;
	
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public EventListenerImpl(Handler handler) {
		this.handler = handler;
	}
	
	@Override
	public void update(ConfigModifyEvent event) {
		handler.handle(event);
	}

}
