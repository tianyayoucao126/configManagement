package core.event;

import core.entity.Node;


public class AddEvent extends ConfigModifyEvent {

private final EventType eventType = EventType.ADD;
	
	public EventType getEventType() {
		return eventType;
	}
	
	public void addConfig(Node data){
		this.data = data;
		super.notifyAllListeners(this);
	}
}
