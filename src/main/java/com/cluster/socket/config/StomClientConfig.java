package com.cluster.socket.config;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class StomClientConfig {

	@Autowired
	@Qualifier("MyStompSessionHandler")
	private StompSessionHandler handler;

	@Value("${websocket.mock.data.host}")
	private String server;

	@Bean
	public WebSocketStompClient stompClient() {
		log.info("Stared stomp client initialization->{}", server);
		WebSocketContainer container = ContainerProvider.getWebSocketContainer();
		container.setDefaultMaxTextMessageBufferSize(20 * 1024 * 1024);
		WebSocketClient socketClient = new StandardWebSocketClient(container);
		WebSocketStompClient stompClient = new WebSocketStompClient(socketClient);
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());
		stompClient.connect(server, handler);
		stompClient.start();
		log.info("Initialization Complete:{}", stompClient.isRunning());
		return stompClient;
	}
}
