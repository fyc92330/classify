package org.chun.classify;

import com.linecorp.bot.model.event.CallbackRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.chun.classify.config.ClassifyEventPublisher;
import org.chun.classify.listener.base.MessageExchange;
import org.chun.classify.listener.event.LineServerConnectDemoEvent;
import org.chun.classify.listener.exchange.LineServerConnectDemoExchange;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/webhook")
@SpringBootApplication
public class ClassifyApplication implements CommandLineRunner {

	private final ClassifyEventPublisher classifyEventPublisher;

	public static void main(String[] args) {
		SpringApplication.run(ClassifyApplication.class, args);
	}

	@PostMapping("/line/callback")
	public void lineCallBack(@RequestBody CallbackRequest request, @RequestHeader(name = "x-line-signature") String signature) {
		log.info("CallbackRequest:{}", request);
	}

	@Override
	public void run(String... args) throws Exception {
		LineServerConnectDemoEvent lineServerConnectDemoEvent = new LineServerConnectDemoEvent("Uf003b5b19031f186c4bd889e6961a411");
		classifyEventPublisher.publishEvent(lineServerConnectDemoEvent);
	}

	@Scheduled(cron = "0 */10 * * * *")
	public void userCollectTask() {

	}

	@Bean
	ApplicationListener<ApplicationReadyEvent> readyEventApplicationListener() {
		//todo Notification Event
		return event -> log.info("{}", event);
	}


}
