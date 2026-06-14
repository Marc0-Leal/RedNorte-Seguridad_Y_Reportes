package com.atencion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "atenciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Atencion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String nombrePersona;

    @Column
    private String tipoAtencion;

    @Column
    private String estado; // PENDIENTE, EN_PROCESO, COMPLETADO

    @Column(nullable = false)
    private LocalDateTime fechaAtencion;

    @Column
    private String observaciones;

    @PrePersist
    protected void onCreate() {
        if (fechaAtencion == null) {
            fechaAtencion = LocalDateTime.now();
        }
        if (estado == null) {
            estado = "COMPLETADO";
        }
    }
}
