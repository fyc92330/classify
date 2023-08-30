package org.chun.classify.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.chun.classify.listener.base.CustomEvent;
import org.chun.classify.listener.base.MessageExchange;
import org.chun.classify.listener.exchange.LineMessageExchange;
import org.chun.classify.listener.exchange.LineReplyMessageExpiredExchange;
import org.chun.classify.listener.exchange.LineServerConnectDemoExchange;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Component
public class ClassifyEventPublisher {

	private final LineServerConnectDemoExchange lineServerConnectDemoExchange;
	private final LineReplyMessageExpiredExchange lineReplyMessageExpiredExchange;
	private final LineMessageExchange lineMessageExchange;

	private final Map<Class<? extends CustomEvent>, MessageExchange<? extends CustomEvent>> registry = new ConcurrentHashMap<>();

	@PostConstruct
	void init() {
		registry.put(lineServerConnectDemoExchange.eventType(), lineServerConnectDemoExchange);
		registry.put(lineReplyMessageExpiredExchange.eventType(), lineReplyMessageExpiredExchange);
		registry.put(lineMessageExchange.eventType(), lineMessageExchange);
	}

	public <T extends CustomEvent> void publishEvent(T message) {
		if (message == null) return;
		Class<?> messageType = message.getClass();
		MessageExchange<T> messageExchange = (MessageExchange<T>) registry.get(messageType);
		messageExchange.handle(message);
	}
}
