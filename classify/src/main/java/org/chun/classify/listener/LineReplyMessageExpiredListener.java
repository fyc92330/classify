package org.chun.classify.listener;

import com.linecorp.bot.model.PushMessage;
import lombok.RequiredArgsConstructor;
import org.chun.classify.listener.base.CustomListener;
import org.chun.classify.listener.event.LineReplyMessageExpiredEvent;
import org.chun.linebot.ILineBotService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LineReplyMessageExpiredListener implements CustomListener<LineReplyMessageExpiredEvent> {

	private final ILineBotService lineBotService;

	@Override
	public void onHandle(LineReplyMessageExpiredEvent event) {
		PushMessage pushMessage = new PushMessage(event.userId(), event.messages());
		lineBotService.push(pushMessage);
	}
}
