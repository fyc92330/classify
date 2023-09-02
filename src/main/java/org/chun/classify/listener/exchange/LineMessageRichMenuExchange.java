package org.chun.classify.listener.exchange;

import lombok.RequiredArgsConstructor;
import org.chun.classify.listener.LineMessageRichMenuListener;
import org.chun.classify.listener.base.AbstractMessageExchange;
import org.chun.classify.listener.base.CustomEvent;
import org.chun.classify.listener.event.LineMessageRichMenuEvent;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@RequiredArgsConstructor
@Component
public class LineMessageRichMenuExchange extends AbstractMessageExchange<LineMessageRichMenuEvent> {
	private final LineMessageRichMenuListener lineMessageRichMenuListener;

	@Override
	public Consumer<LineMessageRichMenuEvent> listener() {
		return this.lineMessageRichMenuListener::onHandle;
	}

	@Override
	public Class<? extends CustomEvent> eventType() {
		return null;
	}
}
