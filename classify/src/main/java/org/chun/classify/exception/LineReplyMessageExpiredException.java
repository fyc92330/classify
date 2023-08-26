package org.chun.classify.exception;

import com.linecorp.bot.model.message.Message;
import lombok.Getter;

import java.util.List;

@Getter
public class LineReplyMessageExpiredException extends RuntimeException implements ProxyServerError {

	private final List<Message> messages;
	private final String receiptUserId;

	public LineReplyMessageExpiredException(List<Message> messages, String receiptUserId) {
		super();
		this.messages = messages;
		this.receiptUserId = receiptUserId;
	}

}
