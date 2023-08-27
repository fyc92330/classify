package org.chun.classify.cache;

import org.chun.classify.model.Activity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ActivityCache implements AbstractCache<Activity> {

	//ActivityCode, Activity
	Map<String, Activity> onlineActivityMap = new ConcurrentHashMap<>();


	@Override
	public void init() {

	}

	@Override
	public void put(Activity info) {
		onlineActivityMap.put(info.activityCode(), info);
	}

	@Override
	public Activity get(String id) {
		return onlineActivityMap.get(id);
	}

	@Override
	public List<Activity> cacheInfo() {
		return onlineActivityMap.values().stream().toList();
	}

	@Override
	public int size() {
		return onlineActivityMap.size();
	}
}
