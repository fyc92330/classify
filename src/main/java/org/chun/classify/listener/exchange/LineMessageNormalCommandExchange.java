package org.chun.classify.listener.exchange;

import lombok.RequiredArgsConstructor;
import org.chun.classify.listener.LineMessageNormalCommandListener;
import org.chun.classify.listener.base.AbstractMessageExchange;
import org.chun.classify.listener.base.CustomEvent;
import org.chun.classify.listener.event.LineMessageNormalCommandEvent;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@RequiredArgsConstructor
@Component
public class LineMessageNormalCommandExchange extends AbstractMessageExchange<LineMessageNormalCommandEvent> {

	private final LineMessageNormalCommandListener lineMessageNormalCommandListener;

	@Override
	public Consumer<LineMessageNormalCommandEvent> listener() {
		return this.lineMessageNormalCommandListener::onHandle;
	}

	@Override
	public Class<? extends CustomEvent> eventType() {
		return LineMessageNormalCommandEvent.class;
	}
}
