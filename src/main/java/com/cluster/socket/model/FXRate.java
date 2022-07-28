package com.cluster.socket.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FXRate {
	private String refCurrency;
	private String targetCurrency;
	private double exchangeRate;
}
