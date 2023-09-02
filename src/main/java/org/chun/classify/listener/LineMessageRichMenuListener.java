package org.chun.classify.listener;

import org.chun.classify.listener.base.CustomListener;
import org.chun.classify.listener.event.LineMessageRichMenuEvent;
import org.springframework.stereotype.Component;

@Component
public class LineMessageRichMenuListener implements CustomListener<LineMessageRichMenuEvent> {
	@Override
	public void onHandle(LineMessageRichMenuEvent message) {

	}
}
