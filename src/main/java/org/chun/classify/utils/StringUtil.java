package org.chun.classify.utils;

import org.chun.classify.enums.MethodPrefixType;
import org.springframework.util.StringUtils;

public class StringUtil {

	public static String methodName(String field, MethodPrefixType prefixType) {
		return prefixType.getPrefix() + StringUtils.capitalize(field);
	}

}
