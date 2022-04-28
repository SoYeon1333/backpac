package com.example.demo.global.dto.response;

import lombok.Data;

@Data
public class ErrorData implements BackpacResponseData {

	private String errorMessage;
	
	public ErrorData(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public static ErrorData getErrorData(String message) {
		return new ErrorData(message);
	}
}