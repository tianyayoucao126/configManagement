package core.event;

import core.handler.Handler;


public class UpdateHandlerImpl implements Handler {

	@Override
	public void handle(ConfigModifyEvent source) {
		System.out.println(source.getData().getData());
	}

}
