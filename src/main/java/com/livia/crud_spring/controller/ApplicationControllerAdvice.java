package com.livia.crud_spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.livia.crud_spring.exceptions.RecordNotFoundException;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleRecordNotFoundException(RecordNotFoundException e) {
        return e.getMessage();
    }

    // Quando a validação de um @RequestBody falha, ou seja, quando um objeto
    // enviado em uma requisição POST/PUT não segue as regras definidas em anotações
    // como @NotNull, @NotBlank, @Length, etc.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    // Representação de uma exceção deste tipo: { "exception":
    // "MethodArgumentNotValidException", "message": "Validation failed",
    // "bindingResult": { ... } }
    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        // O método getBindingResult() só está disponível em exceções do tipo
        // MethodArgumentNotValidException e contém todos os erros de validação em um
        // @RequestBody.
        // Ex.: { "objectName": "userDTO", "fieldErrors": [ { ... }, { ... } ] }

        // O método getFieldErrors() retorna uma lista de objetos FieldError, ex.:
        // [ { "field": "name", "rejectedValue": "", "defaultMessage": "O nome não pode
        // estar em branco" },
        // { "field": "age", "rejectedValue": 10, "defaultMessage": "Idade mínima é 18
        // anos" } ]
        // Obs.: as defaultMessage são definidas por arquivos da biblioteca
        // hibernate-validator
        return e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + " " + error.getDefaultMessage())
                .reduce("", (acc, error) -> acc + error + "\n"); // Concatena as mensagens separadas por "\n"
    }

    // Quando a validação falha em um @RequestParam ou @PathVariable
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleConstraintViolationException(ConstraintViolationException e) {
        // getConstraintViolations() retorna o conjunto de erros de validação dos
        // parâmetros da URL
        // Ex.: [ { "propertyPath": "age", "invalidValue": 10, "message": "Idade mínima
        // é 18 anos" } ]
        return e.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + " " + violation.getMessage())
                .reduce("", (acc, violation) -> acc + violation + "\n");
    }

    // Quando um parâmetro da URL recebe um tipo de dado inesperado
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    // Representação de uma exceção deste tipo: { "exception":
    // "MethodArgumentTypeMismatchException", "message": "id should be of type
    // Long", "parameterName": "id", "requiredType": "Long" }
    public String handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        if (e.getRequiredType() != null) {
            String type = e.getRequiredType().getSimpleName();
            // e.getName() é um método de MethodArgumentTypeMismatchException que retorna o
            // valor de parameterName
            return e.getName() + " should be of type " + type;
        }
        return "Argument type not valid";
    }

}