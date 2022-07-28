package com.cluster.socket.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HoldingResponse {
	private String userId;
	private List<Stock> stocks;
}
