package com.example.demo.global.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleResponseBody implements BackpacResponseBody {

	private String status;
	private String message;
	
	public SimpleResponseBody(String status, String message) {
		this.status = status;
		this.message = message;
	}

	public static SimpleResponseBody getSuccessBody(String message) {
		return new SimpleResponseBody("success", message);
	}

	public static SimpleResponseBody getFailBody(String message) {
		return new SimpleResponseBody("fail", message);
	}
	
}
