package org.chun.classify.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MethodPrefixType {
	GET("get"),
	SET("set");

	private final String prefix;
}
