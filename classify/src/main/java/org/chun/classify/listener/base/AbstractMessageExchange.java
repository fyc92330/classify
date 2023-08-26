package org.chun.classify.listener.base;

public abstract class AbstractMessageExchange<T extends CustomEvent> implements MessageExchange<T> {

	@Override
	public void handle(T message){
		this.listener().accept(message);
	}
}
