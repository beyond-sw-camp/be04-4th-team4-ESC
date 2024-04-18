package org.highfives.esc.chat.config;

import org.highfives.esc.chat.controller.ChatHandler;
import org.highfives.esc.chat.interceptor.HSInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatHandler chatHandler;
    private final HSInterceptor hsInterceptor;

    @Autowired
    public WebSocketConfig(ChatHandler chatHandler,
                           HSInterceptor hsInterceptor) {
        this.chatHandler = chatHandler;
        this.hsInterceptor = hsInterceptor;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        registry.addHandler(chatHandler, "/chat/*")
                .setAllowedOriginPatterns("*")
                .addInterceptors(hsInterceptor);
    }

}
