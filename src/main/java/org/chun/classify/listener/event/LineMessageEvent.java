package org.chun.classify.listener.event;

import com.linecorp.bot.model.message.Message;
import org.chun.classify.listener.base.CustomEvent;

public record LineMessageEvent(
		Message message
) implements CustomEvent {
}
