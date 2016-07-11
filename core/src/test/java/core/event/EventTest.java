package core.event;

import core.entity.Node;
import core.handler.Handler;
import core.listener.EventListenerImpl;

public class EventTest {

	public static void main(String[] args) {
		UpdateEvent update = new UpdateEvent();
		Node data = new Node();
		data.setData("update");
		data.setName("conf");
		update.updateConfig(data);
		Handler handler = new UpdateHandlerImpl();
		handler.handle(update);
		EventListenerImpl listener = new EventListenerImpl(handler);
		update.addListener(listener);
	}
	
}
