package org.chun.classify.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.chun.classify.config.ClassifyEventPublisher;
import org.chun.classify.enums.LineMessageCommandType;
import org.chun.classify.listener.base.CustomEvent;
import org.chun.classify.listener.base.CustomListener;
import org.chun.classify.listener.event.LineMessageEvent;
import org.chun.classify.listener.event.LineMessageNormalCommandEvent;
import org.chun.classify.listener.event.LineMessageRichMenuEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LineMessageListener implements CustomListener<LineMessageEvent> {

	private final ClassifyEventPublisher classifyEventPublisher;

	@Override
	public void onHandle(LineMessageEvent message) {
		// 處理 TextMessage 判斷指令: RichMenu, Command
		String textContent = message.textContent();
		String userId = message.userId();
		LineMessageCommandType commandType = LineMessageCommandType.getInstance(textContent);
		textContent = commandType.command(textContent);
		CustomEvent event = switch (commandType) {
			case NORMAL -> new LineMessageNormalCommandEvent(textContent, userId, message.replyToken());
			case RICH_MENU -> new LineMessageRichMenuEvent(textContent, userId);
			case IGNORE -> null;
		};

		if (event != null) {
			classifyEventPublisher.publishEvent(event);
		}
	}
}
