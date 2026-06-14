package com.atencion.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AtencionRequestDTO {

    @NotBlank(message = "El nombre de la persona es obligatorio")
    private String nombrePersona;

    private String tipoAtencion;

    private String estado;

    private LocalDateTime fechaAtencion;

    private String observaciones;
}
