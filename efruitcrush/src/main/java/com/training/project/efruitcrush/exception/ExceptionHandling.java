package com.training.project.efruitcrush.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import java.util.Objects;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.training.project.efruitcrush.response.HttpResponse;

@RestControllerAdvice
public class ExceptionHandling implements ErrorController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	private static final String ORDER_NOT_FOUND = "We cannot find order you requessted.";
	private static final String FRUIT_NOT_FOUND = "We cannot find fruit you requested.";
	private static final String CART_ITEM_NOT_FOUND = "We cannot find cart item you requested.";
	private static final String ACCOUNT_LOCKED = "Your account has been locked.";
	private static final String METHOD_IS_NOT_ALLOWED = "This request method is not allowed on this endpoint.";
	private static final String INTERNAL_SERVER_ERROR_MSG = "An error occurred while processing the request.";
	private static final String INCORRECT_CREDENTIALS = "Invalid credentials. Please try again.";
	private static final String ACCOUNT_DISABLED = "Your account has been disabled. If this is an error, please contact administration.";
	private static final String NOT_ENOUGH_PERMISSION = "You do not have enough permission to perform this action.";
	public static final String ERROR_PATH = "/page-not-found";

	@ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<HttpResponse> orderNotFoundException() {
		return createHttpResponse(NOT_FOUND, ORDER_NOT_FOUND);
	}

	@ExceptionHandler(FruitNotFoundException.class)
	public ResponseEntity<HttpResponse> fruitNotFoundException() {
		return createHttpResponse(NOT_FOUND, FRUIT_NOT_FOUND);
	}

	@ExceptionHandler(CartItemNotFoundException.class)
	public ResponseEntity<HttpResponse> cartItemNotFoundException() {
		return createHttpResponse(NOT_FOUND, CART_ITEM_NOT_FOUND);
	}

	@ExceptionHandler(DisabledException.class)
	public ResponseEntity<HttpResponse> accountDisabledException() {
		return createHttpResponse(BAD_REQUEST, ACCOUNT_DISABLED);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<HttpResponse> badCredentialsException() {
		return createHttpResponse(BAD_REQUEST, INCORRECT_CREDENTIALS);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<HttpResponse> accessDeniedException() {
		return createHttpResponse(FORBIDDEN, NOT_ENOUGH_PERMISSION);
	}

	@ExceptionHandler(LockedException.class)
	public ResponseEntity<HttpResponse> lockedException() {
		return createHttpResponse(UNAUTHORIZED, ACCOUNT_LOCKED);
	}

	@ExceptionHandler(TokenExpiredException.class)
	public ResponseEntity<HttpResponse> tokenExpiredException(TokenExpiredException exception) {
		return createHttpResponse(UNAUTHORIZED, exception.getMessage());
	}

	@ExceptionHandler(EmailExistException.class)
	public ResponseEntity<HttpResponse> emailExistException(EmailExistException exception) {
		return createHttpResponse(BAD_REQUEST, exception.getMessage());
	}

	@ExceptionHandler(UsernameExistException.class)
	public ResponseEntity<HttpResponse> usernameExistException(UsernameExistException exception) {
		return createHttpResponse(BAD_REQUEST, exception.getMessage());
	}

	@ExceptionHandler(EmailNotFoundException.class)
	public ResponseEntity<HttpResponse> emailNotFoundException(EmailNotFoundException exception) {
		return createHttpResponse(BAD_REQUEST, exception.getMessage());
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<HttpResponse> userNotFoundException(UserNotFoundException exception) {
		return createHttpResponse(BAD_REQUEST, exception.getMessage());
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<HttpResponse> methodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
		HttpMethod supportedMethod = Objects.requireNonNull(exception.getSupportedHttpMethods()).iterator().next();
		return createHttpResponse(METHOD_NOT_ALLOWED, String.format(METHOD_IS_NOT_ALLOWED, supportedMethod));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<HttpResponse> internalServerErrorException(Exception exception) {
		LOGGER.error(exception.getMessage());
		return createHttpResponse(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MSG);
	}

	@ExceptionHandler(NoResultException.class)
	public ResponseEntity<HttpResponse> notFoundException(NoResultException exception) {
		LOGGER.error(exception.getMessage());
		return createHttpResponse(NOT_FOUND, exception.getMessage());
	}

	private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
		return new ResponseEntity<>(
				new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(), message),
				httpStatus);
	}

	@RequestMapping(ERROR_PATH)
	public ResponseEntity<HttpResponse> notFound404() {
		return createHttpResponse(NOT_FOUND, "There is no mapping for this URL");
	}

	public String getErrorPath() {
		return ERROR_PATH;
	}
}
