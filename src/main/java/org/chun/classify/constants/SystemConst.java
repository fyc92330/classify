package org.chun.classify.constants;

import java.util.regex.Pattern;

public class SystemConst {
	public static final String Y_STR = "Y";
	public static final String N_STR = "N";
	public static final String DOC_PATH = System.getProperty("user.dir") + "/doc";
	public static final Pattern NORMAL_COMMAND_PATTERN = Pattern.compile("\\$\\$\\w+?\\$\\$");
	public static final Pattern RICE_MENU_COMMAND_PATTERN = Pattern.compile("##\\w+?##");
}
