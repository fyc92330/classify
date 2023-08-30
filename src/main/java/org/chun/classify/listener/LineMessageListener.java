package org.chun.classify.listener;

import com.linecorp.bot.model.message.TextMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.chun.classify.config.ClassifyEventPublisher;
import org.chun.classify.enums.LineMessageCommandType;
import org.chun.classify.listener.base.CustomEvent;
import org.chun.classify.listener.base.CustomListener;
import org.chun.classify.listener.event.LineMessageEvent;
import org.chun.classify.listener.event.LineMessageNormalCommandEvent;
import org.chun.classify.listener.event.LineMessageRichMenuEvent;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LineMessageListener implements CustomListener<LineMessageEvent> {

	private final ClassifyEventPublisher classifyEventPublisher;
	@Override
	public void onHandle(LineMessageEvent message) {
		// 只處理 TextMessage 判斷指令: RichMenu, Command
		if(message.message() instanceof TextMessage textMessage){
			String textContent = textMessage.getText();
			LineMessageCommandType commandType = LineMessageCommandType.getInstance(textContent);
			textContent = commandType.command(textContent);
			CustomEvent event = switch (commandType){
				case NORMAL -> new LineMessageNormalCommandEvent(textContent, "", "");
				case RICH_MENU ->  new LineMessageRichMenuEvent(textContent, "");
				case IGNORE -> null;
			};
		}
	}
}
