package org.chun.classify.config;

import org.chun.classify.listener.base.CustomEvent;
import org.chun.classify.listener.base.MessageExchange;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class EventExchangeRegistryConfig {

	private final Map<Class<? extends CustomEvent>, Class<? extends MessageExchange<? extends CustomEvent>>> registry = new ConcurrentHashMap<>();


}
