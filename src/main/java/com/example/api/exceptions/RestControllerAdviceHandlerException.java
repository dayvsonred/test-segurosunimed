package com.example.api.exceptions;

import com.example.api.DTO.ObjectOfError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestControllerAdvice
@ControllerAdvice
public class RestControllerAdviceHandlerException extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class, RuntimeException.class, Throwable.class})
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String msg = "";

        if(ex instanceof MethodArgumentNotValidException){
            List<ObjectError> listError = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();
            for (ObjectError objectError : listError){
                msg += objectError.getDefaultMessage() + "\n";
            }
        }else{
            msg = ex.getMessage();
        }

        ObjectOfError objectOfError = ObjectOfError.builder()
                .error(msg)
                .code(status.value() + " ==> " + status.getReasonPhrase())
                .build();

        return new ResponseEntity<>(objectOfError, headers, status);
    }
}
