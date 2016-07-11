package core.event;

import core.entity.Node;

public class UpdateEvent extends ConfigModifyEvent {

	private final EventType eventType = EventType.UPDATE;
	
	public EventType getEventType() {
		return eventType;
	}

	public void updateConfig(Node data){
		this.data = data;
		super.notifyAllListeners(this);
	}
}
