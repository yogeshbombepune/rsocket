package com.cluster.socket.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserHolding {
	private String stockName;
	private int quantity; 
	private double buyPrice;
	private String stockCurrency;
	private String referenceCurrency;
}
