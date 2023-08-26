package org.chun.classify.config.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "line.client")
public class LineBotProperties {
	private String id;
	private String secret;
	private String accessToken;
	private String apiUrl;
	private String botBasicId;
}
