package org.chun.classify.exception;

import com.linecorp.bot.model.error.ErrorResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import okhttp3.Request;

@Getter
@Setter
@ToString
public class LineServerException extends RuntimeException implements ProxyServerError {

	private Request request;
	private ErrorResponse errorResponse;
	private String message;

	public LineServerException(Request request, ErrorResponse errorResponse){
		this.request = request;
		this.errorResponse = errorResponse;
	}

	public LineServerException(String message){
		this.message = message;
	}
}
