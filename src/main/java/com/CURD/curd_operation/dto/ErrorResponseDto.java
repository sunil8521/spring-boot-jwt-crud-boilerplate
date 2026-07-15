package com.CURD.curd_operation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseDto {
    private int status;
    private String code;
    private String errorMessage;
    private Map<String, String> fieldErrors;
    private String debugMessage;
    private LocalDateTime timestamp;

    public ErrorResponseDto(int status, String code, String errorMessage) {
        this.status = status;
        this.code = code;
        this.errorMessage = errorMessage;
        this.timestamp = LocalDateTime.now();
    }
}
