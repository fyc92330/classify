package org.chun.classify.listener.exchange;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.chun.classify.listener.LineMessageListener;
import org.chun.classify.listener.base.AbstractMessageExchange;
import org.chun.classify.listener.base.CustomEvent;
import org.chun.classify.listener.event.LineMessageEvent;
import org.chun.classify.listener.event.LineReplyMessageExpiredEvent;
import org.springframework.stereotype.Component;

import javax.sound.sampled.LineEvent;
import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
@Component
public class LineMessageExchange extends AbstractMessageExchange<LineMessageEvent> {

	private final LineMessageListener lineMessageListener;

	@Override
	public Consumer<LineMessageEvent> listener() {
		return lineMessageListener::onHandle;
	}

	@Override
	public Class<? extends CustomEvent> eventType() {
		return LineMessageEvent.class;
	}
}
