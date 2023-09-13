package org.chun.classify.listener.event;

import com.linecorp.bot.model.event.Event;
import org.chun.classify.listener.base.CustomEvent;

public record CustomLineEvent(
		Event event
) implements CustomEvent {
}
