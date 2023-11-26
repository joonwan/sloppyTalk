package com.toro.sloppyTalk.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import java.util.List;

@Configuration
@Slf4j
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WsConfig implements WebSocketMessageBrokerConfigurer {

    private final ActiveUserManager userManager;
    private final static String CHAT_SCREEN = "CHAT_SCREEN";

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {

                MessageHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                MessageHeaders messageHeaders = accessor.getMessageHeaders();

                for (String key : messageHeaders.keySet()) {
                    log.info("{} = {}", key, messageHeaders.get(key));
                }

                Object stompCommand = accessor.getHeader("stompCommand");
                
                log.info("stomp Command = {}", stompCommand);



                if(StompCommand.CONNECT.equals(stompCommand)){

                    String screenName = getScreenName(accessor);

                    if(CHAT_SCREEN.equals(screenName)){
                        Long memberId = getMemberId(accessor);
                        log.info("memberId = {}", memberId);

                        Long chatRoomId = getChatRoomId(accessor);
                        log.info("chatRoomId = {}", chatRoomId);

                        String sessionId = (String) accessor.getHeader("simpSessionId");
                        userManager.addUser(sessionId, memberId, chatRoomId);
                        log.info("memberId = {} sessionId = {} is active !!", memberId, sessionId);
                    }



                } else if (StompCommand.DISCONNECT.equals(stompCommand)) {

                    String sessionId = (String) accessor.getHeader("simpSessionId");
                    userManager.removeUser(sessionId);
                    log.info("memberId = {} is removed", sessionId);
                }

                log.info("active user number = {}", userManager.getActiveUserCount());

                return message;
            }

            private Long getMemberId(MessageHeaderAccessor accessor) {
                LinkedMultiValueMap<String,List> nativeHeaders = (LinkedMultiValueMap<String,List>) accessor.getHeader("nativeHeaders");
                return Long.valueOf(String.valueOf(nativeHeaders.get("memberId").get(0)));
            }

            private Long getChatRoomId(MessageHeaderAccessor accessor) {
                LinkedMultiValueMap<String,List> nativeHeaders = (LinkedMultiValueMap<String,List>) accessor.getHeader("nativeHeaders");
                return Long.valueOf(String.valueOf(nativeHeaders.get("chatRoomId").get(0)));
            }

            private String getScreenName(MessageHeaderAccessor accessor) {
                LinkedMultiValueMap<String,List> nativeHeaders = (LinkedMultiValueMap<String,List>) accessor.getHeader("nativeHeaders");
                return String.valueOf(String.valueOf(nativeHeaders.get("screenName").get(0)));
            }

        });
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/sloppy-gate")
                .setHandshakeHandler(new UserHandShakeHandler())
                .setAllowedOrigins("http://localhost:19006")
                .withSockJS();
        registry.addEndpoint("/alert")
                .setAllowedOrigins("http://localhost:19006")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/chat_room","/private_alert");
        registry.setApplicationDestinationPrefixes("/ws");
    }



}
