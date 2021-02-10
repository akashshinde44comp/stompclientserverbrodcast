package com.server.websocket.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;

@Component
public class UserAgentSessionRegistry {

	private Map<String, String> map = new HashMap<>();

	public Map<String, String> getSessionmap(String key, String value) {
		map.put(key, value);
		return map;
	}
	
	public Map<String, String> getMap() {
		return map;
	}
	
	public void dispayMap() {
		for (Entry<String, String> m : map.entrySet()) {
			System.out.println(m.getKey() + " value " + m.getValue());
		}
	}
}
