package com.delivery.api.exception;

import java.util.List;

import lombok.Data;

@Data
public class ApiValidationExceptionResponse {

	private Integer status;
	private String message;
	private List<Error> errors;

	@Data
	public static class Error {
		private String field;
		private String message;
	}
}
