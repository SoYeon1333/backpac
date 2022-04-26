package com.example.demo.global.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataResponseBody implements BackpacResponseBody {

	private String status;
	private String message;
	private Object data;
	
	public DataResponseBody(String status, String message, Object data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}
	
	public static BackpacResponseBody getSuccessBody(String message, Object data) {
		return new DataResponseBody("success", message, data);
	}

	public static BackpacResponseBody getFailBody(String message, Object data) {
		return new DataResponseBody("fail", message, data);
	}
	
}
