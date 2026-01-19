package com.bryandev.apirestreactivo.model.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private String mensaje;
    private String codigo;
    private LocalDateTime timestamp;
}
