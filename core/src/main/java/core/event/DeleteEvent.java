package core.event;

import core.entity.Node;


public class DeleteEvent extends ConfigModifyEvent {

private final EventType eventType = EventType.DELETE;
	
	public EventType getEventType() {
		return eventType;
	}
	
	public void deleteConfig(Node data){
		this.data = data;
		super.notifyAllListeners(this);
	}
	
}
