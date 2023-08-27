package org.chun.classify.model;

import java.util.List;

public record ActivityInfo(
		String hostUserId,
		List<UserProfile> participants
) {
}
