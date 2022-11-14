package br.edu.ifpi.api.transacao.handler.descriptor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class MethodArgumentNotValidExceptionDescriptor {
    private LocalDateTime timestamp;
    private Integer status;
    private String exception;
    private String fields;
    private String fieldsMessage;
}
