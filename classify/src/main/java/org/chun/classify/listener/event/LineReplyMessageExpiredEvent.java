package org.chun.classify.listener.event;

import com.linecorp.bot.model.message.Message;
import org.chun.classify.listener.base.CustomEvent;

import java.util.List;

public record LineReplyMessageExpiredEvent(List<Message> messages, String userId) implements CustomEvent {

}
