package com.cluster.socket.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Stock {
	private String stockName;
	private double buyPrice;
	private double marketPrice;
	private int quantity;
	private String referenceCurrency;
	private String targetCurrency;
	private double profitOrLoss;
}
