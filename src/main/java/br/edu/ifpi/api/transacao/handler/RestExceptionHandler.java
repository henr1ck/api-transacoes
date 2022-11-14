package br.edu.ifpi.api.transacao.handler;

import br.edu.ifpi.api.transacao.handler.descriptor.MethodArgumentNotValidExceptionDescriptor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String fields = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getField).collect(Collectors.joining("; "));

        String fieldsMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));

        MethodArgumentNotValidExceptionDescriptor descriptor = MethodArgumentNotValidExceptionDescriptor.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .exception(ex.getClass().getSimpleName())
                .fields(fields)
                .fieldsMessage(fieldsMessage)
                .build();

        return new ResponseEntity<>(descriptor, status);
    }
}
