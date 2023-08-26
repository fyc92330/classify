package org.chun.classify.exception;

public class ServerUnKnownException extends Exception implements ProxyServerError{

	public ServerUnKnownException(String message){
		super(message);
	}
}
