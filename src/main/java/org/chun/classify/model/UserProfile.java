package org.chun.classify.model;

public record UserProfile(
		String userId,
		String name,
		String pictureUrl,
		String statusMessage,
		String language
) {
}
