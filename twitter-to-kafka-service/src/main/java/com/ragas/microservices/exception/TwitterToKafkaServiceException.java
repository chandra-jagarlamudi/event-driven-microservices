package com.ragas.microservices.exception;

public class TwitterToKafkaServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TwitterToKafkaServiceException() {
		super();
	}

	public TwitterToKafkaServiceException(String messsage) {

	}

	public TwitterToKafkaServiceException(String message, Throwable exception) {

	}

}
