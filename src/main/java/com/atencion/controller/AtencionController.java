package com.atencion.controller;

import com.atencion.dto.AtencionRequestDTO;
import com.atencion.dto.GraficoDTO;
import com.atencion.model.Atencion;
import com.atencion.service.AtencionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/atenciones")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AtencionController {

    private final AtencionService atencionService;

    // ─── POST /api/atenciones ────────────────────────────────────────────────
    // Registrar nueva atención
    @PostMapping
    public ResponseEntity<Atencion> registrar(@Valid @RequestBody AtencionRequestDTO dto) {
        Atencion nueva = atencionService.registrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    // ─── GET /api/atenciones ─────────────────────────────────────────────────
    // Listar todas las atenciones
    @GetMapping
    public ResponseEntity<List<Atencion>> listar() {
        return ResponseEntity.ok(atencionService.listarTodas());
    }

    // ─── GET /api/atenciones/{id} ────────────────────────────────────────────
    // Obtener una atención por ID
    @GetMapping("/{id}")
    public ResponseEntity<Atencion> obtener(@PathVariable Long id) {
        return atencionService.buscarPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // ─── PUT /api/atenciones/{id} ────────────────────────────────────────────
    // Actualizar una atención
    @PutMapping("/{id}")
    public ResponseEntity<Atencion> actualizar(
        @PathVariable Long id,
        @RequestBody AtencionRequestDTO dto
    ) {
        try {
            return ResponseEntity.ok(atencionService.actualizar(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ─── DELETE /api/atenciones/{id} ─────────────────────────────────────────
    // Eliminar una atención
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        atencionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ─── GET /api/atenciones/grafico/diario ──────────────────────────────────
    // Datos para gráfico de barras por DÍA
    // Ejemplo: /api/atenciones/grafico/diario?desde=2025-06-01&hasta=2025-06-30
    @GetMapping("/grafico/diario")
    public ResponseEntity<GraficoDTO> graficoDiario(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta
    ) {
        if (desde == null) desde = LocalDate.now().minusDays(30);
        if (hasta == null) hasta = LocalDate.now();
        return ResponseEntity.ok(atencionService.obtenerGraficoPorDia(desde, hasta));
    }

    // ─── GET /api/atenciones/grafico/mensual ─────────────────────────────────
    // Datos para gráfico de barras por MES
    // Ejemplo: /api/atenciones/grafico/mensual?anio=2025
    @GetMapping("/grafico/mensual")
    public ResponseEntity<GraficoDTO> graficoMensual(
        @RequestParam(defaultValue = "#{T(java.time.LocalDate).now().getYear()}") int anio
    ) {
        return ResponseEntity.ok(atencionService.obtenerGraficoPorMes(anio));
    }

    // ─── GET /api/atenciones/resumen ─────────────────────────────────────────
    // Resumen general de estadísticas
    @GetMapping("/resumen")
    public ResponseEntity<Map<String, Object>> resumen() {
        return ResponseEntity.ok(atencionService.obtenerResumen());
    }
}
