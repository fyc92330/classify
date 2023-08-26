package org.chun.classify.config;

import lombok.RequiredArgsConstructor;
import org.chun.classify.config.properties.LineBotProperties;
import org.chun.linebot.ILineBotService;
import org.chun.linebot.LineBotService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@EnableConfigurationProperties(LineBotProperties.class)
@Configuration
public class LineBotConfig {

	private final LineBotProperties lineBotProperties;

	@Bean
	public ILineBotService lineBotService() {
		return new LineBotService(lineBotProperties.getApiUrl(), lineBotProperties.getAccessToken());
	}

}
