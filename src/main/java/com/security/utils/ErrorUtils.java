package com.security.utils;

import com.security.dto.ErrorDto;

public final class ErrorUtils {

    public static ErrorDto getErrorDto(String message) {
        return ErrorDto.builder().message(message).build();
    }
}
