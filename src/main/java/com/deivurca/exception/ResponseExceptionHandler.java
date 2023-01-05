package com.deivurca.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

@ControllerAdvice
@RestController
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ProblemDetail> manejarTodasExcepciones(Exception ex, WebRequest request){
		return ResponseEntity.internalServerError().body(ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
																  HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		FieldError campoError = ex.getFieldError();
		String mensaje = "Petición invalida";

		if(Objects.nonNull(campoError)) {
			mensaje = String.format("El campo %s: %s", campoError.getField(), campoError.getDefaultMessage());
		}

		return ResponseEntity.badRequest().body(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, mensaje));
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ProblemDetail> manejarUsernameNotFoundException(UsernameNotFoundException ex,
																		WebRequest request) {
		return ResponseEntity.badRequest().body(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage()));
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ProblemDetail> manejarBadCredentialsException(BadCredentialsException ex,
																		WebRequest request) {
		return ResponseEntity.badRequest().body(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Usuario y contraseña incorrectos"));
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public final ResponseEntity<ProblemDetail> manejarDataIntegrityViolationException(Exception ex, WebRequest request){
		return ResponseEntity.internalServerError().body(ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Alguno de los campos no cumplen con los reglas de la base de datos"));
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ProblemDetail> manejarBussinessException(BusinessException ex,
																   WebRequest request) {
		return ResponseEntity.badRequest().body(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage()));
	}
}
