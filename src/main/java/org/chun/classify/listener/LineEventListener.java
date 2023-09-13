package org.chun.classify.listener;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.UnfollowEvent;
import com.linecorp.bot.model.event.message.MessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.event.source.UserSource;
import lombok.RequiredArgsConstructor;
import org.chun.classify.cache.UserCache;
import org.chun.classify.config.ClassifyEventPublisher;
import org.chun.classify.listener.base.CustomListener;
import org.chun.classify.listener.event.CustomLineEvent;
import org.chun.classify.listener.event.LineMessageEvent;
import org.chun.classify.model.UserProfile;
import org.chun.linebot.ILineBotService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class LineEventListener implements CustomListener<CustomLineEvent> {
	private final ILineBotService lineBotService;
	private final ClassifyEventPublisher classifyEventPublisher;
	private final UserCache userCache;

	@Override
	public void onHandle(CustomLineEvent message) {
		Event event = message.event();
		Source eventSource = event.getSource();
		String userId = eventSource.getUserId();
		if (eventSource instanceof UserSource) {
			this.handleUserSource(userId);
		}

		if (event instanceof MessageEvent<?>) {
			MessageContent messageContent = ((MessageEvent<?>) event).getMessage();
			if (messageContent instanceof TextMessageContent) {
				String text = ((TextMessageContent) messageContent).getText();
				LineMessageEvent lineMessageEvent = new LineMessageEvent(userId, ((MessageEvent<?>) event).getReplyToken(), text);
				classifyEventPublisher.publishEvent(lineMessageEvent);
			}
		} else if (event instanceof UnfollowEvent) {
			Optional.ofNullable(userCache.get(userId))
					.map(user -> {
						user.setActive(false);
						return user;
					})
					.ifPresent(userCache::put);
		}
	}

	private void handleUserSource(String userId) {
		UserProfile profile = Optional.ofNullable(userCache.get(userId))
				.map(user -> {
					user.setLastLoginTime(LocalDateTime.now());
					return user;
				})
				.orElse(new UserProfile(lineBotService.profile(userId)));
		userCache.put(profile);
	}
}
