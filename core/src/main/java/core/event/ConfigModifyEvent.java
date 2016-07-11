package core.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import core.entity.Node;
import core.listener.EventListener;


public abstract class ConfigModifyEvent {

	private List<EventListener> listeners = Collections.synchronizedList(new ArrayList<EventListener>());
	
	protected Node data;
	
	public Node getData() {
		return data;
	}

	public void addListener(EventListener listener){
		listeners.add(listener);
	}
	
	public void deleteListener(EventListener listener){
		listeners.remove(listener);
	}
	
	public void notifyAllListeners(ConfigModifyEvent  event){
		for (EventListener eventListener : listeners) {
			eventListener.update(event);
		}
	}
	
	enum EventType{
		UPDATE,
		ADD,
		DELETE
	}
}
