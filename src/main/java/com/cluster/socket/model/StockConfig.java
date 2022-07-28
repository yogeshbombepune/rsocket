package com.cluster.socket.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StockConfig {
	public static Map<String, DataFrame> dataFrame = new ConcurrentHashMap<>();
	public static final Map<String, List<UserHolding>> holdings = init();
	public static final Map<String, Double> fxRate = initFxRate();

	public static Map<String, List<UserHolding>> init() {
		String[] USD_STOCKS = { "AAPL", "MSFT", "AMZN", "TSLA", "GOOGL", "GOOG", "NVDA", "BRK.B", "META", "UNH" };
		String[] INR_STOCKS = { "SBIN", "BOB", "TCS", "WIPROW", "INFY", "ICICIBANK", "HDFCBANK", "AXIXBANK", "IDBI",
				"PAYTM" };
		String[] SGD_STOCKS = { "DBS", "OCBC BANK", "UOB", "SIGTEL", "WILMAR INTL", "SIA", "SGX", "UOL", "SATS",
				"STARHUB" };

		Map<String, List<UserHolding>> holdings = new ConcurrentHashMap<String, List<UserHolding>>();
		Random r = new Random();

		List<UserHolding> yogeshHoldings = IntStream.range(2, 6)
				.mapToObj(x -> UserHolding.builder().quantity(r.nextInt(50 - 20 + 1) + 20).referenceCurrency("USD")
						.stockCurrency("USD").buyPrice(500 + (2000 - 500) * r.nextDouble()).stockName(USD_STOCKS[x])
						.build())
				.collect(Collectors.toList());
		holdings.put("Yogesh", yogeshHoldings);

		List<UserHolding> avijitHoldings = IntStream.range(3, 9)
				.mapToObj(x -> UserHolding.builder().quantity(r.nextInt(19 - 10 + 1) + 10).referenceCurrency("SGD")
						.stockCurrency("SGD").buyPrice(50 + (200 - 50) * r.nextDouble()).stockName(SGD_STOCKS[x])
						.build())
				.collect(Collectors.toList());
		holdings.put("Avijit", avijitHoldings);

		List<UserHolding> vashmiHoldings = IntStream.range(3, 7)
				.mapToObj(x -> UserHolding.builder().quantity(r.nextInt(8 - 1 + 1) + 1).referenceCurrency("INR")
						.stockCurrency("INR").buyPrice(100 + (200 - 100) * r.nextDouble()).stockName(INR_STOCKS[x])
						.build())
				.collect(Collectors.toList());
		holdings.put("Vamshi", vashmiHoldings);

		List<UserHolding> inrToUsdUser = IntStream.range(1, 4)
				.mapToObj(x -> UserHolding.builder().quantity(r.nextInt(8 - 1 + 1) + 1).referenceCurrency("INR")
						.stockCurrency("USD").buyPrice(50 + (200 - 50) * r.nextDouble()).stockName(USD_STOCKS[x])
						.build())
				.collect(Collectors.toList());
		
		holdings.put("INR-USD-USER", inrToUsdUser);

		log.info("{}", holdings);
		return holdings;
	}

	private static Map<String, Double> initFxRate() {
		Map<String, Double> fxRate = new HashMap<>();
		fxRate.put("INR-INR", 1.00);
		fxRate.put("INR-USD", 0.013);
		fxRate.put("INR-SGD", 0.017);

		fxRate.put("USD-USD", 1.00);
		fxRate.put("USD-INR", 79.65);
		fxRate.put("USD-SGD", 1.38);

		fxRate.put("SGD-SGD", 1.00);
		fxRate.put("SGD-USD", 0.72);
		fxRate.put("SGD-INR", 57.66);

		return fxRate;
	}

}
