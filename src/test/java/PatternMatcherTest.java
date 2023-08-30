import org.apache.logging.log4j.util.Strings;
import org.chun.classify.constants.SystemConst;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

public class PatternMatcherTest {

	@Test
	void normalPatternMatcher(){
		String command = "$$create$$";
		Assertions.assertTrue(SystemConst.NORMAL_COMMAND_PATTERN.matcher(command).matches());
	}

	@Test
	void richMenuPatternMatcher(){
		String command = "##change##";
		Assertions.assertTrue(SystemConst.RICE_MENU_COMMAND_PATTERN.matcher(command).matches());
	}
	@Test
	void ignorePatternMatcher(){
		String command = "Abcde$@#";
		Assertions.assertTrue(Pattern.compile("^[a-zA-Z0-9\\p{Punct}]+$").matcher(command).matches());
	}
}
