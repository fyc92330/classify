package org.chun.classify.listener.event;

import org.chun.classify.listener.base.CustomEvent;

public record LineMessageRichMenuEvent(
		String command,
		String userId
) implements CustomEvent {
}
