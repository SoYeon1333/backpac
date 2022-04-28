package com.example.demo.global.dto.response;

import lombok.Data;

@Data
public class SuccessData implements BackpacResponseData {

	private String successMessage;
	
	public SuccessData(String successMessage) {
		this.successMessage = successMessage;
	}
	
	public static SuccessData getSuccessData(String message) {
		return new SuccessData(message);
	}
}
