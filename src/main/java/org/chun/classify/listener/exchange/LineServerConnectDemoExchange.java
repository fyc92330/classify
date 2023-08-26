package org.chun.classify.listener.exchange;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.chun.classify.listener.LineServerConnectDemoListener;
import org.chun.classify.listener.base.AbstractMessageExchange;
import org.chun.classify.listener.event.LineServerConnectDemoEvent;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
@Component
public class LineServerConnectDemoExchange extends AbstractMessageExchange<LineServerConnectDemoEvent> {

	private final LineServerConnectDemoListener lineServerConnectDemoListener;

	@Override
	public Consumer<LineServerConnectDemoEvent> listener() {
		return this.lineServerConnectDemoListener::onHandle;
	}
}
