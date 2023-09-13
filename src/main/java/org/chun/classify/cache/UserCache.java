package org.chun.classify.cache;

import org.chun.classify.constants.SystemConst;
import org.chun.classify.model.UserProfile;
import org.chun.classify.utils.DocumentUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@Component
public class UserCache implements AbstractCache<UserProfile> {

	private static final String USER_PROFILE_DOC_PATH = SystemConst.DOC_PATH + "/userProfiles";

	// UserId, UserProfile
	private final Map<String, UserProfile> users = new ConcurrentHashMap<>();

	@Override
	public void init() {
		List<String[]> userProfileInfos = DocumentUtil.read(USER_PROFILE_DOC_PATH);
		Function<String, Boolean> getUserActive = SystemConst.Y_STR::equals;
		userProfileInfos.stream()
				.map(info -> new UserProfile(info[0], info[1], info[2], info[3], info[4], getUserActive.apply(info[5]), LocalDateTime.parse(info[6])))
				.forEach(this::put);
	}

	@Override
	public void put(UserProfile profile) {
		users.put(profile.getUserId(), profile);
	}

	@Override
	public UserProfile get(String id) {
		return users.get(id);
	}

	@Override
	public List<UserProfile> cacheInfo() {
		return users.values().stream().toList();
	}

	@Override
	public int size() {
		return users.size();
	}
}
