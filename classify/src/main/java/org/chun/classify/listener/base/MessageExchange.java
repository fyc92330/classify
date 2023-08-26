package org.chun.classify.listener.base;


import java.util.function.Consumer;

public interface MessageExchange<T extends CustomEvent> {

	void handle(T message);

	Consumer<T> listener();
}
