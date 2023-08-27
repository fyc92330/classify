package org.chun.classify.listener;

import lombok.RequiredArgsConstructor;
import org.chun.classify.listener.base.CustomListener;
import org.chun.classify.listener.event.LineMessageEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LineMessageListener implements CustomListener<LineMessageEvent> {
	@Override
	public void onHandle(LineMessageEvent message) {

	}
}
