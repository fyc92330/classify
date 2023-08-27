package org.chun.classify.listener.exchange;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.chun.classify.listener.LineReplyMessageExpiredListener;
import org.chun.classify.listener.base.AbstractMessageExchange;
import org.chun.classify.listener.event.LineReplyMessageExpiredEvent;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
@Component
public class LineReplyMessageExpiredExchange extends AbstractMessageExchange<LineReplyMessageExpiredEvent> {

	private final LineReplyMessageExpiredListener lineReplyMessageExpiredListener;

	@Override
	public Consumer<LineReplyMessageExpiredEvent> listener() {
		return this.lineReplyMessageExpiredListener::onHandle;
	}

	@Override
	public Class<LineReplyMessageExpiredEvent> eventType() {
		return LineReplyMessageExpiredEvent.class;
	}
}
