package org.chun.classify.listener.event;

import org.chun.classify.listener.base.CustomEvent;

public record LineMessageNormalCommandEvent (
		String command,
		String userId,
		String replyToken
) implements CustomEvent {
}
