package com.cluster.socket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.cluster.socket.model.HoldingRequest;
import com.cluster.socket.model.HoldingResponse;
import com.cluster.socket.model.Message;
import com.cluster.socket.service.HoldingService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Controller
@Slf4j
public class RSocketController {
	
	@Autowired
	private HoldingService holdingService;

    @MessageMapping("request-response")
    Message requestResponse(Message request) {
            log.info("Received request-response request: {}", request);
            return new Message("SERVER", "RESPONSE");
    }
    
    @MessageMapping("holdings-by-user")
    public Flux<HoldingResponse> holdingsByUser(HoldingRequest request) {
            log.info("Received request-response request: {}", request);
            return holdingService.getHoldingsByUser(request.getUserId());
    }
}
