package com.atencion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GraficoDTO {

    private List<String> etiquetas;   // Ej: ["01/06", "02/06", ...]
    private List<Long> valores;        // Ej: [5, 8, 3, ...]
    private String titulo;
    private Long totalAtenciones;

    // Datos por tipo de atención (para gráfico apilado o múltiple)
    private List<SerieDTO> series;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SerieDTO {
        private String nombre;
        private List<Long> datos;
    }
}
