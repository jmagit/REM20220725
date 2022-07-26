package com.example;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.exceptions.BadRequestException;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;

@RestControllerAdvice
public class ApiExceptionHandler {
	/**
	 * Datos del error
	 */
	public static class ErrorMessage implements Serializable {
		private static final long serialVersionUID = 1L;
		/**
		 * Motivo del error
		 */
		private String error;
		/**
		 * Información complemetaria del error
		 */
		private String message;
		public ErrorMessage(String error, String message) {
			this.error = error;
			this.message = message;
		}
		public String getError() { return error; }
		public void setError(String error) { this.error = error; }
		public String getMessage() { return message; }
		public void setMessage(String message) { this.message = message; }
	}

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundException.class, EmptyResultDataAccessException.class})
    public ErrorMessage notFoundRequest(HttpServletRequest request, Exception exception) {
        return new ErrorMessage(exception.getMessage(), request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ BadRequestException.class, DuplicateKeyException.class })
    public ErrorMessage badRequest(Exception exception) {
        return new ErrorMessage(exception.getMessage(), "");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ InvalidDataException.class, MethodArgumentNotValidException.class })
    public ErrorMessage invalidData(Exception exception) {
        return new ErrorMessage("Invalid data", exception.getMessage());
    }
}
