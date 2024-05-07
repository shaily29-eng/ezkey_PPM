package com.ezkey.projects.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponse<T> {
    private T payload;
    private String message;
    private Integer status;

    public static <T> GenericResponse<T> success(T data) {
        return GenericResponse.<T>builder()
                .message(null)
                .status(200)
                .payload(data)
                .build();
    }

    public static <T> GenericResponse<T> error(String message, Integer status) {
        return GenericResponse.<T>builder()
                .message(message)
                .status(200)
                .payload(null)
                .build();
    }

}
