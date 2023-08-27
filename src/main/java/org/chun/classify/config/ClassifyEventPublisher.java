package org.chun.classify.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.chun.classify.listener.base.CustomEvent;
import org.chun.classify.listener.base.MessageExchange;
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

	private final Map<Class<? extends CustomEvent>, MessageExchange<? extends CustomEvent>> registry = new ConcurrentHashMap<>();

	@PostConstruct
	void init() {
		registry.put(lineServerConnectDemoExchange.eventType(), lineServerConnectDemoExchange);
		registry.put(lineReplyMessageExpiredExchange.eventType(), lineReplyMessageExpiredExchange);
	}

	public <T extends CustomEvent> void publishEvent(T message) {
		Class<?> messageType = message.getClass();
		MessageExchange messageExchange = registry.get(messageType);
		messageExchange.handle(message);
	}
}
