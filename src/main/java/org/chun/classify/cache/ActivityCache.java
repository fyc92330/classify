package org.chun.classify.cache;

import org.chun.classify.model.Activity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ActivityCache implements AbstractCache<Activity> {

	Map<String, Activity> onlineActivityMap = new ConcurrentHashMap<>();

	@Override
	public void init() {

	}

	@Override
	public void put() {

	}

	@Override
	public Activity get(String id) {
		return null;
	}

	@Override
	public List<Activity> cacheInfo() {
		return null;
	}

	@Override
	public int size() {
		return onlineActivityMap.size();
	}
}
