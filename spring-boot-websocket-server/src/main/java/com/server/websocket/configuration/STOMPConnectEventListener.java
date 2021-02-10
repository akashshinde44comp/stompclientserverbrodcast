package com.server.websocket.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionConnectEvent;

@Service
public class STOMPConnectEventListener implements ApplicationListener<SessionConnectEvent> {

@Autowired
//this is basically a concurrent map for storing pairs "sessionId - login"
UserAgentSessionRegistry webAgentSessionRegistry;

@Override
public void onApplicationEvent(SessionConnectEvent event) {
    StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());

    String agentId = sha.getNativeHeader("Login").get(0);
    String sessionId = sha.getSessionId();

    /** add new session to registry */
    webAgentSessionRegistry.getSessionmap(agentId,sessionId);

    //debug: show connected to stdout
    webAgentSessionRegistry.dispayMap();

}
}