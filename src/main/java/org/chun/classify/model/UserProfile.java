package org.chun.classify.model;

import com.linecorp.bot.model.profile.UserProfileResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.chun.classify.constants.SystemConst;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {
	private String userId;
	private String name;
	private String pictureUrl;
	private String statusMessage;
	private String language;
	private boolean isActive;
	private LocalDateTime lastLoginTime;

	public UserProfile(UserProfileResponse userProfileResponse) {
		this.userId = userProfileResponse.getUserId();
		this.name = userProfileResponse.getDisplayName();
		this.pictureUrl = String.valueOf(userProfileResponse.getPictureUrl());
		this.statusMessage = userProfileResponse.getStatusMessage();
		this.language = userProfileResponse.getLanguage();
		this.isActive = true;
		this.lastLoginTime = LocalDateTime.now();
	}

	@Override
	public int hashCode() {
		return this.userId.hashCode();
	}
}
