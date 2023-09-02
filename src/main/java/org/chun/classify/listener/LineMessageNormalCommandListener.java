package org.chun.classify.listener;

import org.chun.classify.listener.base.CustomListener;
import org.chun.classify.listener.event.LineMessageNormalCommandEvent;
import org.springframework.stereotype.Component;

@Component
public class LineMessageNormalCommandListener implements CustomListener<LineMessageNormalCommandEvent> {
	@Override
	public void onHandle(LineMessageNormalCommandEvent message) {

	}
}
