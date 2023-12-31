package org.chun.classify.listener.event;

import org.chun.classify.listener.base.CustomEvent;

public record LineMessageEvent(
		String userId,
		String replyToken,
		String textContent
) implements CustomEvent {
}
