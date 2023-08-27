package org.chun.classify.model;

import java.util.Objects;

public record Activity(
		String activityCode,

		ActivityInfo activityInfo
) {

	@Override
	public int hashCode() {
		return Objects.hash(this.activityCode);
	}

	@Override
	public boolean equals(Object obj) {
		return this.hashCode() == obj.hashCode();
	}
}
