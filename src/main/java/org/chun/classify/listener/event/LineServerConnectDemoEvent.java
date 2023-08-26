package org.chun.classify.listener.event;

import org.chun.classify.listener.base.CustomEvent;

public record LineServerConnectDemoEvent(String demoUserId) implements CustomEvent {
}
