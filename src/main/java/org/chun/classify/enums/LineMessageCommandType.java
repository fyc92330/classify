package org.chun.classify.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.logging.log4j.util.Strings;
import org.chun.classify.constants.SystemConst;

import java.util.Arrays;
import java.util.regex.Pattern;

@Getter
@AllArgsConstructor
public enum LineMessageCommandType {
	NORMAL(SystemConst.NORMAL_COMMAND_PATTERN),
	RICH_MENU(SystemConst.RICE_MENU_COMMAND_PATTERN),
	IGNORE(Pattern.compile("^[a-zA-Z0-9\\p{Punct}]+$"));

	private final Pattern pattern;

	public static LineMessageCommandType getInstance(String command) {
		return Arrays.stream(values())
				.filter(e -> e.pattern.matcher(command).matches())
				.findAny()
				.orElseThrow(() -> new EnumConstantNotPresentException(LineMessageCommandType.class, command));
	}

	public String command(String text) {
		return this.pattern.matcher(text).group();
	}
}
