package com.cluster.socket.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.cluster.socket.model.DataFrame;
import com.cluster.socket.model.FXRate;
import com.cluster.socket.model.HoldingResponse;
import com.cluster.socket.model.Stock;
import com.cluster.socket.model.StockConfig;
import com.cluster.socket.model.UserHolding;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class HoldingService {

	/*
	 * @Autowired private MessageChannel channel;
	 */

	public Flux<HoldingResponse> getHoldingsByUser(String userId) {
		Mono<List<UserHolding>> userHoldings = getUserHoldings(userId);
		Mono<List<FXRate>> fxRate = getFxRate(userId);

		return Flux.interval(Duration.ZERO, Duration.ofSeconds(1))
				.flatMap(i -> geHoldingResponse(userId, userHoldings, fxRate));
	}

	private Flux<HoldingResponse> geHoldingResponse(String userId, Mono<List<UserHolding>> userHoldings,
			Mono<List<FXRate>> fxRate) {
		List<UserHolding> userHoldings1 = userHoldings.block();
		List<Stock> stocks = new ArrayList<>();

		for (UserHolding holding : userHoldings1) {
			DataFrame filterDataFrame = getMarketStock(holding.getStockName());
			String fxKey = holding.getStockCurrency()+ "-" + holding.getReferenceCurrency();
			Double exchangeRate = StockConfig.fxRate.get(fxKey);
			double profitOrLoss = (filterDataFrame.getPrice() - holding.getBuyPrice()) * holding.getQuantity()
					* exchangeRate;
			stocks.add(Stock.builder().buyPrice(holding.getBuyPrice()).marketPrice(filterDataFrame.getPrice())
					.quantity(holding.getQuantity()).stockName(holding.getStockName()).profitOrLoss(profitOrLoss)
					.referenceCurrency(holding.getReferenceCurrency()).targetCurrency(filterDataFrame.getCurrency())
					.build());
		}
		return Flux.just(HoldingResponse.builder().userId(userId).stocks(stocks).build());
	}

	private DataFrame getMarketStock(String stockName) {
		return StockConfig.dataFrame.get(stockName);
	}

	@SuppressWarnings("unused")
	private DataFrame getMarketStock(List<DataFrame> dataFrames, UserHolding holding) {
		DataFrame filterDataFrame = dataFrames.stream()
				.filter(dataFrame -> holding.getStockName().equals(dataFrame.getStockName())).findFirst().get();
		return filterDataFrame;
	}

	private Mono<List<FXRate>> getFxRate(String userId) {
		return Mono.just(IntStream.range(0, 1)
				.mapToObj(x -> FXRate.builder().exchangeRate(0.72).refCurrency("SGD").targetCurrency("USD").build())
				.collect(Collectors.toList()));
	}

	private Mono<List<UserHolding>> getUserHoldings(String userId) {
		return Mono.just(StockConfig.holdings.get(userId));
	}

}
