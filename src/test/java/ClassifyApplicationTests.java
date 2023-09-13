import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.action.CameraAction;
import com.linecorp.bot.model.action.CameraRollAction;
import com.linecorp.bot.model.action.LocationAction;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;
import org.chun.classify.ClassifyApplication;
import org.chun.linebot.ILineBotService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@ComponentScan(value = {"org.chun.linebot", "org.chun.classify", "org.chun.classify.config"})
@SpringBootTest(classes = ClassifyApplication.class)
class ClassifyApplicationTests {

	@Autowired
	private ILineBotService lineBotService;

	@Test
	void contextLoads() {
		TextMessage textMessage = TextMessage.builder().text("Application Start!!").build();
		PushMessage pushMessage = new PushMessage("Uf003b5b19031f186c4bd889e6961a411", Arrays.asList(textMessage, templateMessage()));
		lineBotService.push(pushMessage);
	}

	private TemplateMessage templateMessage() {
		List<CarouselColumn> columns = Arrays.asList(
				CarouselColumn.builder()
						.thumbnailImageUrl(URI.create("http://127.0.0.1"))
						.title("第一頁測試模板訊息")
						.text("第一頁測試模板")
						.defaultAction(new MessageAction("1", "1"))
						.actions(
								Arrays.asList(
										new MessageAction("文字訊息標題", "文字訊息"),
										new PostbackAction("標題", "數據", "你的訊息"),
										new PostbackAction("標題2", "數據2"),
										new URIAction("手機Google,Mac是FB", URI.create("https://www.google.com.tw"), new URIAction.AltUri(URI.create("https://www.facebook.com.tw")))
								)
						)
						.build(),
				CarouselColumn.builder()
						.thumbnailImageUrl(URI.create("http://127.0.0.1"))
						.title("第二頁測試模板訊息")
						.text("沒看過的操作")
						.defaultAction(new MessageAction("2", "2"))
						.actions(
								Arrays.asList(
										LocationAction.withLabel("Location"),
										CameraAction.withLabel("Camera"),
										CameraRollAction.withLabel("CameraRoll")
								)
						)
						.build(),
				CarouselColumn.builder()
						.thumbnailImageUrl(URI.create("http://127.0.0.1"))
						.title("第三頁測試模板訊息")
						.text("RichMenuOperation")
						.defaultAction(new MessageAction("3", "3"))
						.actions(
								Arrays.asList(
										new PostbackAction("打開RichMenu", PostbackAction.InputOptionType.OPEN_RICH_MENU.name(), null, "打開RichMenu", PostbackAction.InputOptionType.OPEN_RICH_MENU, "fill1"),
										new PostbackAction("打開音量", PostbackAction.InputOptionType.OPEN_VOICE.name(), null, "打開音量", PostbackAction.InputOptionType.OPEN_VOICE, "fill2"),
										new PostbackAction("打開鍵盤", PostbackAction.InputOptionType.OPEN_KEYBOARD.name(), null, "打開鍵盤", PostbackAction.InputOptionType.OPEN_KEYBOARD, "fill3"),
										new PostbackAction("關閉RichMenu", PostbackAction.InputOptionType.CLOSE_RICH_MENU.name(), null, "關閉RichMenu", PostbackAction.InputOptionType.CLOSE_RICH_MENU, "fill4")
								)
						)
						.build()
		);

		return TemplateMessage.builder()
				.template(
						CarouselTemplate.builder()
								.columns(columns)
								.imageAspectRatio("rectangle")
								.imageSize("cover")
								.build()
				)
				.altText("HI, this is a carousel template")
				.build();
	}
}
