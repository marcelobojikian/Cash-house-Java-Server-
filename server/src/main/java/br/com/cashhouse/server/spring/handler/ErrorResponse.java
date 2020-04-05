package br.com.cashhouse.server.spring.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ErrorResponse {
	
    private LocalDateTime timestamp;

    private Integer status;
    private String error;
    private String message;
    
	public ErrorResponse(HttpStatus status, String message) {
		super();
		this.status = status.value();
		this.error = status.getReasonPhrase();
		this.message = message;
		this.timestamp = LocalDateTime.now();
	}
    
    

}
