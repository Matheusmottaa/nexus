package com.matheus.mota.nexus.common;

public record ProblemDetails(
        String title,
        Integer code,
        String status,
        String detail ,
        String instance
) {
}
