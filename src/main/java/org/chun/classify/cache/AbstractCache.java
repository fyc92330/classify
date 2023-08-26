package org.chun.classify.cache;

import java.util.List;

public interface AbstractCache<T> {
	void init();

	void put();

	T get(String id);

	List<T> cacheInfo();

	int size();
}
