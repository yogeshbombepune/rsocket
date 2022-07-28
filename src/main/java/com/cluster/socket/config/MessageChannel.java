package com.cluster.socket.config;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cluster.socket.model.DataFrame;

@Configuration
public class MessageChannel {

	@Bean
	public BlockingQueue<List<DataFrame>> linkedBlockingQueue() {
		return new LinkedBlockingQueue<>(10);
	}

}
