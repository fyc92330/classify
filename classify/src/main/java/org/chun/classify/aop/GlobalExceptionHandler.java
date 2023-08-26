package org.chun.classify.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.chun.classify.exception.LineReplyMessageExpiredException;
import org.chun.classify.listener.event.LineReplyMessageExpiredEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

	private final ApplicationEventPublisher eventPublisher;

	@ExceptionHandler(LineReplyMessageExpiredException.class)
	private void handleLineReplyMessageExpired(LineReplyMessageExpiredException e) {
		eventPublisher.publishEvent(new LineReplyMessageExpiredEvent(e.getMessages(), e.getReceiptUserId()));
	}
}
