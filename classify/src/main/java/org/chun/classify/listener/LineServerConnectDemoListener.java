package org.chun.classify.listener;

import com.linecorp.bot.model.profile.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.chun.classify.listener.base.CustomListener;
import org.chun.classify.listener.event.LineServerConnectDemoEvent;
import org.chun.linebot.ILineBotService;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class LineServerConnectDemoListener implements CustomListener<LineServerConnectDemoEvent> {
	private final ILineBotService lineBotService;

	@Override
	public void onHandle(LineServerConnectDemoEvent message) {
		UserProfileResponse response = lineBotService.profile(message.demoUserId());
		log.info(">> Demo UserProfile Success:{}", response);
	}
}
