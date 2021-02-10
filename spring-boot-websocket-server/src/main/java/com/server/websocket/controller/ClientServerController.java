package com.server.websocket.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import com.server.websocket.configuration.UserAgentSessionRegistry;
import com.server.websocket.dto.Greeting;
import com.server.websocket.dto.HelloMessage;
import com.server.websocket.service.ClientServerService;

@RestController
public class ClientServerController {
	@Autowired
	private ClientServerService service;
	@Autowired
	private UserAgentSessionRegistry webAgentSessionRegistry;

	@MessageMapping("/chat")
	@SendTo("/topic/client/msg")
	public Greeting processMessageFromClient(@Payload HelloMessage message, Principal priciple) throws Exception {
		// String sessionId =
		// headerAccessor.getSessionAttributes().get("sessionId").toString();
		service.storeUserName(priciple.getName(), message.getName());
		return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
	}

	@PostMapping(value = "/simulate", consumes = "application/json", produces = "application/json")
	public void brodcastMeassge(@RequestBody String message) throws Exception {
		service.sendMessages(message);
		System.out.println("message received " + message);
	}
}
