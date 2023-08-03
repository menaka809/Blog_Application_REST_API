package com.example.blogapprestapi.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.blogapprestapi.payload.ErrorDetail;

@ControllerAdvice
public class GlobalExceptionHandle extends ResponseEntityExceptionHandler{
	
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetail> handleResourceNotFoundException(ResourceNotFoundException exception,WebRequest webrequest){
		ErrorDetail errorDetail= new ErrorDetail(new Date(), exception.getMessage(), webrequest.getDescription(false));
		
		return new ResponseEntity<ErrorDetail>(errorDetail,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BlogApiException.class)
	public ResponseEntity<ErrorDetail> handleResourceNotFoundException(BlogApiException exception,WebRequest webrequest){
		ErrorDetail errorDetail= new ErrorDetail(new Date(), exception.getMessage(), webrequest.getDescription(false));
		
		return new ResponseEntity<ErrorDetail>(errorDetail,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetail> handleGlobalException(Exception exception,WebRequest webrequest){
		ErrorDetail errorDetail= new ErrorDetail(new Date(), exception.getMessage(), webrequest.getDescription(false));
		
		return new ResponseEntity<ErrorDetail>(errorDetail,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorDetail> handleGlobalException(AccessDeniedException exception,WebRequest webrequest){
		ErrorDetail errorDetail= new ErrorDetail(new Date(), exception.getMessage(), webrequest.getDescription(false));
		
		return new ResponseEntity<ErrorDetail>(errorDetail,HttpStatus.UNAUTHORIZED);
	}

//	cutomize error mesages response relavnt to validation
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		// TODO Auto-generated method stub
		Map<String, String>  errors= new HashMap<String, String>();
		ex.getBindingResult().getAllErrors().forEach((error)->{
			String fieldName=((FieldError)error).getField();
			String message=error.getDefaultMessage();
			errors.put(fieldName, message);
		});
		return new ResponseEntity<Object>(errors,HttpStatus.BAD_REQUEST);
	}
	
	

}
