package com.server.websocket.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.server.websocket.configuration.UserAgentSessionRegistry;

@Service
public class ClientServerService {
	private static final String WS_MESSAGE_TRANSFER_DESTINATION = "/queue/client";
	public final Map<String, String> map = new HashMap<>();
	@Autowired
	private  SimpMessagingTemplate simpMessagingTemplate;
	@Autowired
	UserAgentSessionRegistry registory;

	private MessageHeaders createHeaders(String sessionId) {
		SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
		headerAccessor.setSessionId(sessionId);
		headerAccessor.setLeaveMutable(true);
		return headerAccessor.getMessageHeaders();
	}

	public void sendMessages(final String msg) {
		for (Entry<String, String> m : registory.getMap().entrySet()) {
			simpMessagingTemplate.convertAndSendToUser(m.getValue(), WS_MESSAGE_TRANSFER_DESTINATION,"Hi "+ m.getKey()+" " +msg, createHeaders(m.getValue()));
		}
	}

	public void storeUserName(final String sessionId, final String name) {
		map.put(sessionId, name);
	}
}
