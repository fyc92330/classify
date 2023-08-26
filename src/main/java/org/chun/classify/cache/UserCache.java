package org.chun.classify.cache;

import org.chun.classify.model.UserProfile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserCache implements AbstractCache<UserProfile> {

	private final Map<String, UserProfile> users = new ConcurrentHashMap<>();

	@Override
	public void init() {

	}

	@Override
	public void put() {

	}

	@Override
	public UserProfile get(String id) {
		return null;
	}

	@Override
	public List<UserProfile> cacheInfo() {
		return null;
	}

	@Override
	public int size() {
		return users.size();
	}
}
