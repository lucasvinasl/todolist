package com.todolist.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Getter
@Setter
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private final int status;
    private final String message;
    private String stackTrace;
    private List<ValidationErro> erros;

    @Getter
    @Setter
    @RequiredArgsConstructor
    private static class ValidationErro{
        private final String field;
        private final String message;
    }

    public void addValidationError(String field, String message){
        if(Objects.isNull(erros)){
            this.erros = new ArrayList<>();
        }
        this.erros.add(new ValidationErro( field, message));
    }


}
