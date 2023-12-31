package org.chun.classify;

import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.event.CallbackRequest;
import com.linecorp.bot.model.message.TextMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.chun.classify.cache.UserCache;
import org.chun.classify.config.ClassifyEventPublisher;
import org.chun.classify.constants.SystemConst;
import org.chun.classify.enums.CryptoType;
import org.chun.classify.listener.event.CustomLineEvent;
import org.chun.classify.listener.event.LineServerConnectDemoEvent;
import org.chun.classify.utils.CryptoUtil;
import org.chun.classify.utils.DocumentUtil;
import org.chun.linebot.ILineBotService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Slf4j
@EnableScheduling
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/webhook")
@SpringBootApplication
public class ClassifyApplication implements CommandLineRunner {

	@Value("${owner.line.user-id}")
	private String ownerLineUserId;

	private final ILineBotService lineBotService;
	private final ClassifyEventPublisher classifyEventPublisher;
	private final UserCache userCache;

	public static void main(String[] args) {
		SpringApplication.run(ClassifyApplication.class, args);
	}

	@PostMapping("/line/callback")
	public void lineCallBack(@RequestBody CallbackRequest request, @RequestHeader(name = "x-line-signature") String signature) {
		log.info("[CallbackRequest] >> {}, Signature:{}", request, signature);
		request.getEvents().stream()
				.map(CustomLineEvent::new)
				.forEach(classifyEventPublisher::publishEvent);
	}

	@Override
	public void run(String... args) throws Exception {
		LineServerConnectDemoEvent lineServerConnectDemoEvent = new LineServerConnectDemoEvent(ownerLineUserId);
		classifyEventPublisher.publishEvent(lineServerConnectDemoEvent);
	}

	@Scheduled(cron = "0 */10 * * * *")
	public void userCollectTask() {
		log.info("Save UserProfile Start, time:{}", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		File docFolder = new File(SystemConst.DOC_PATH);
		if (!docFolder.exists()) {
			docFolder.mkdir();
		}

		// Distinct Data
		String userProfilePath = SystemConst.DOC_PATH + "/userProfiles";
		List<String[]> profiles = DocumentUtil.isExists(userProfilePath)
				? DocumentUtil.read(userProfilePath)
				: Collections.emptyList();
		userCache.cacheInfo().stream()
				.filter(profile -> profiles.stream().noneMatch(info -> info[0].equals(profile.getUserId())))
				.map(profile -> {
					String[] userInfo = new String[5];
					userInfo[0] = profile.getUserId();
					userInfo[1] = profile.getName();
					userInfo[2] = profile.getPictureUrl();
					userInfo[3] = profile.getStatusMessage();
					userInfo[4] = profile.getLanguage();
					userInfo[5] = profile.isActive() ? SystemConst.Y_STR : SystemConst.N_STR;
					userInfo[6] = profile.getLastLoginTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
					return userInfo;
				})
				.forEach(profiles::add);

		// Write
		StringBuilder stringBuilder = new StringBuilder();
		profiles.stream()
				.map(info -> String.join(",", info))
				.forEach(stringBuilder::append);
		DocumentUtil.write(userProfilePath, stringBuilder.toString());
	}

	//	@Bean todo 測試環節不要打開, 要錢
	ApplicationListener<ApplicationReadyEvent> readyEventApplicationListener() {
		TextMessage textMessage = TextMessage.builder().text("Application Start!!").build();
		PushMessage pushMessage = new PushMessage(ownerLineUserId, textMessage);
		return event -> lineBotService.push(pushMessage);
	}


}
