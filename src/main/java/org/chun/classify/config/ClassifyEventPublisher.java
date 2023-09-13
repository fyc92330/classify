package org.chun.classify.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.internal.connection.Exchange;
import org.chun.classify.exception.ServerUnKnownException;
import org.chun.classify.listener.base.CustomEvent;
import org.chun.classify.listener.base.MessageExchange;
import org.chun.classify.listener.exchange.LineMessageExchange;
import org.chun.classify.listener.exchange.LineReplyMessageExpiredExchange;
import org.chun.classify.listener.exchange.LineServerConnectDemoExchange;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class ClassifyEventPublisher {

	private final ApplicationContext applicationContext;
	private final Map<Class<? extends CustomEvent>, MessageExchange<? extends CustomEvent>> registry = new ConcurrentHashMap<>();

	@SneakyThrows
	@PostConstruct
	void init() {
		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
		scanner.addIncludeFilter(new AssignableTypeFilter(MessageExchange.class));
		for (BeanDefinition component : scanner.findCandidateComponents("org.chun.classify.listener")) {
			Class<? extends MessageExchange<?>> exchangeClass = (Class<? extends MessageExchange<?>>) Class.forName(component.getBeanClassName());
			MessageExchange<?> messageExchange = applicationContext.getBean(exchangeClass);
			registry.put(messageExchange.eventType(), messageExchange);
		}
	}

	public <T extends CustomEvent> void publishEvent(T message) {
		if (message == null) return;
		Class<?> messageType = message.getClass();
		MessageExchange<T> messageExchange = (MessageExchange<T>) registry.get(messageType);
		messageExchange.handle(message);
	}

}
