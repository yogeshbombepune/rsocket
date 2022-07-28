package com.cluster.socket.repository;

import java.util.Date;

import org.springframework.data.domain.Range;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;

import com.cluster.socket.model.DataFrame;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@Slf4j
public class DataFeedRepository {

	private final ReactiveRedisOperations<String, DataFrame> redisTemplate;

	public DataFeedRepository(ReactiveRedisOperations<String, DataFrame> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public Mono<Void> save(DataFrame dataFrame) {
		log.debug("DataFeedRepository->save->{}", dataFrame);
		return Mono.when(
				redisTemplate.<String, DataFrame>opsForHash().put("dataFrame", dataFrame.getStockName(), dataFrame),
				redisTemplate.opsForZSet().add(dataFrame.getStockName().toLowerCase().replaceAll("\\s", ""), dataFrame,
						new Date().getTime()))
				.then();
	}

	public Flux<DataFrame> getStockByName(String stock) {
		return redisTemplate.opsForZSet().reverseRange(stock, Range.unbounded());
	}

}
