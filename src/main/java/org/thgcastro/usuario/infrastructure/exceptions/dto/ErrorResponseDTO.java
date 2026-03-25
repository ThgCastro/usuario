package org.thgcastro.usuario.infrastructure.exceptions.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponseDTO {

    private LocalDateTime timeStamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
