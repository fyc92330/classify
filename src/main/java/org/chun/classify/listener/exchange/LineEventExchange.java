package org.chun.classify.listener.exchange;

import lombok.RequiredArgsConstructor;
import org.chun.classify.listener.LineEventListener;
import org.chun.classify.listener.base.AbstractMessageExchange;
import org.chun.classify.listener.base.CustomEvent;
import org.chun.classify.listener.event.CustomLineEvent;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@RequiredArgsConstructor
@Component
public class LineEventExchange extends AbstractMessageExchange<CustomLineEvent> {
	private final LineEventListener lineEventListener;
	@Override
	public Consumer<CustomLineEvent> listener() {
		return lineEventListener::onHandle;
	}

	@Override
	public Class<? extends CustomEvent> eventType() {
		return CustomLineEvent.class;
	}
}
