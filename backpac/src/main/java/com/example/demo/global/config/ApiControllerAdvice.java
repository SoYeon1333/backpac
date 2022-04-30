package com.example.demo.global.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.global.dto.response.BackpacResponseBody;
import com.example.demo.global.dto.response.SimpleResponseBody;

@RestControllerAdvice
public class ApiControllerAdvice {

	MessageSource messageSource;
	
	public ApiControllerAdvice(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	/**
	 * MethodArgumentNotValidException 파라미터 에러 처리
	 * @param ex
	 * @return
	 */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BackpacResponseBody handleValidationExceptions(HttpServletRequest request, MethodArgumentNotValidException ex){
    	
    	ObjectError error = ex.getBindingResult().getAllErrors().get(0);
    	
		String code = error.getCodes()[0];
		String message = messageSource.getMessage(code, null, error.getDefaultMessage(), null);
		return SimpleResponseBody.getFailBody(message);
    }
    
	/**
	 * Exception
	 * @param ex
	 * @return
	 */
    @ExceptionHandler(Exception.class)
    public BackpacResponseBody handleExceptions(Exception ex){
    	ex.printStackTrace();
        return SimpleResponseBody.getFailBody("Unknown error");
    }

}