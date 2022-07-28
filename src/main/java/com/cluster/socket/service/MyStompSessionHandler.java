package com.cluster.socket.service;

import java.lang.reflect.Type;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.stereotype.Component;

import com.cluster.socket.model.DataFrame;
import com.cluster.socket.model.StockConfig;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component("MyStompSessionHandler")
@Slf4j
public class MyStompSessionHandler implements StompSessionHandler {

	@Autowired
	private ObjectMapper mapper;

//	@Autowired
//	private MessageChannel channel;

//	@Autowired
//	private DataFeedRepository dataFeedRepository;

	@Override
	public Type getPayloadType(StompHeaders headers) {
		return JsonNode.class;
	}

	@Override
	public void handleFrame(StompHeaders headers, Object payload) {
		JsonNode jsonNode = (JsonNode) payload;
		List<DataFrame> convertValue = mapper.convertValue(jsonNode, new TypeReference<List<DataFrame>>() {
		});
		// channel.linkedBlockingQueue().offer(convertValue);
		convertValue.forEach(data -> {
			// dataFeedRepository.save(data);
			StockConfig.dataFrame.put(data.getStockName(), data);
		});

	}

	@Override
	public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
		session.subscribe("/topic/feeds", this);
		session.send("/app/hello-stock-exchange", "");
		log.info("Connected");
	}

	@Override
	public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload,
			Throwable exception) {
		throw new RuntimeException(exception);

	}

	@Override
	public void handleTransportError(StompSession session, Throwable exception) {
		throw new RuntimeException(exception);

	}

}
