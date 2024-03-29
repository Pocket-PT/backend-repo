package com.madeyepeople.pocketpt.global.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class ErrorResponse {
    private String code;
    private String message;
    private List<FieldError> errors;

    private ErrorResponse(ErrorCode code, List<FieldError> fieldErrors) {
        this.code = code.getCode();
        this.message = code.getMessage();
        this.errors = fieldErrors;
    }

    private ErrorResponse(ErrorCode code) {
        this.code = code.getCode();
        this.message = code.getMessage();
        this.errors = new ArrayList<>();
    }

    private ErrorResponse(ErrorCode code, Exception e) {
        this.code = code.getCode();
        this.message = code.getMessage(e);
        this.errors = new ArrayList<>();
    }

    public static ErrorResponse of(final ErrorCode code, final BindingResult bindingResult) {
        return new ErrorResponse(code, FieldError.of(bindingResult));
    }

    public static ErrorResponse of(ErrorCode code) {
        return new ErrorResponse(code);
    }

    public static ErrorResponse of(ErrorCode code, Exception e) {
        return new ErrorResponse(code, e);
    }

    @Getter
    @AllArgsConstructor
    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        private static List<FieldError> of(final BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors =
                    bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(
                            error ->
                                    new FieldError(
                                            error.getField(),
                                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
    }

    public String toJSonString() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }
}
