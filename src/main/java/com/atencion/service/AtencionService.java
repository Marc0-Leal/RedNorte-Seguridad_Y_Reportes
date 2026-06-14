package com.atencion.service;

import com.atencion.dto.AtencionRequestDTO;
import com.atencion.dto.GraficoDTO;
import com.atencion.model.Atencion;
import com.atencion.repository.AtencionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AtencionService {

    private final AtencionRepository atencionRepository;

    // ─── CRUD ────────────────────────────────────────────────────────────────

    @Transactional
    public Atencion registrar(AtencionRequestDTO dto) {
        Atencion atencion = new Atencion();
        atencion.setNombrePersona(dto.getNombrePersona());
        atencion.setTipoAtencion(dto.getTipoAtencion() != null ? dto.getTipoAtencion() : "GENERAL");
        atencion.setEstado(dto.getEstado() != null ? dto.getEstado() : "COMPLETADO");
        atencion.setFechaAtencion(dto.getFechaAtencion() != null ? dto.getFechaAtencion() : LocalDateTime.now());
        atencion.setObservaciones(dto.getObservaciones());
        log.info("Registrando atención para: {}", atencion.getNombrePersona());
        return atencionRepository.save(atencion);
    }

    @Transactional(readOnly = true)
    public List<Atencion> listarTodas() {
        return atencionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Atencion> buscarPorId(Long id) {
        return atencionRepository.findById(id);
    }

    @Transactional
    public Atencion actualizar(Long id, AtencionRequestDTO dto) {
        Atencion atencion = atencionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Atención no encontrada con id: " + id));

        if (dto.getNombrePersona() != null) atencion.setNombrePersona(dto.getNombrePersona());
        if (dto.getTipoAtencion() != null) atencion.setTipoAtencion(dto.getTipoAtencion());
        if (dto.getEstado() != null) atencion.setEstado(dto.getEstado());
        if (dto.getObservaciones() != null) atencion.setObservaciones(dto.getObservaciones());

        return atencionRepository.save(atencion);
    }

    @Transactional
    public void eliminar(Long id) {
        atencionRepository.deleteById(id);
    }

    // ─── GRÁFICO POR DÍA ─────────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public GraficoDTO obtenerGraficoPorDia(LocalDate desde, LocalDate hasta) {
        LocalDateTime inicio = desde.atStartOfDay();
        LocalDateTime fin = hasta.atTime(23, 59, 59);

        List<Object[]> resultados = atencionRepository.countByDia(inicio, fin);

        List<String> etiquetas = new ArrayList<>();
        List<Long> valores = new ArrayList<>();

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM");

        for (Object[] row : resultados) {
            // row[0] = fecha (java.sql.Date o LocalDate), row[1] = count
            LocalDate fecha = ((java.sql.Date) row[0]).toLocalDate();
            etiquetas.add(fecha.format(fmt));
            valores.add((Long) row[1]);
        }

        long total = atencionRepository.countByFechaAtencionBetween(inicio, fin);

        return new GraficoDTO(
            etiquetas,
            valores,
            "Personas atendidas por día",
            total,
            null
        );
    }

    // ─── GRÁFICO POR MES ─────────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public GraficoDTO obtenerGraficoPorMes(int anio) {
        LocalDateTime inicio = LocalDate.of(anio, 1, 1).atStartOfDay();
        LocalDateTime fin = LocalDate.of(anio, 12, 31).atTime(23, 59, 59);

        List<Object[]> resultados = atencionRepository.countByMes(inicio, fin);

        Map<String, Long> mapaResultados = new LinkedHashMap<>();
        for (Object[] row : resultados) {
            mapaResultados.put((String) row[0], (Long) row[1]);
        }

        // Generar los 12 meses aunque no tengan datos
        String[] nombresMeses = {"Ene", "Feb", "Mar", "Abr", "May", "Jun",
                                  "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"};
        List<String> etiquetas = new ArrayList<>();
        List<Long> valores = new ArrayList<>();

        for (int mes = 1; mes <= 12; mes++) {
            String clave = String.format("%d-%02d", anio, mes);
            etiquetas.add(nombresMeses[mes - 1]);
            valores.add(mapaResultados.getOrDefault(clave, 0L));
        }

        long total = atencionRepository.countByFechaAtencionBetween(inicio, fin);

        return new GraficoDTO(
            etiquetas,
            valores,
            "Personas atendidas en " + anio,
            total,
            null
        );
    }

    // ─── RESUMEN GENERAL ─────────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public Map<String, Object> obtenerResumen() {
        Map<String, Object> resumen = new LinkedHashMap<>();
        resumen.put("totalAtenciones", atencionRepository.count());
        resumen.put("completadas", atencionRepository.findByEstado("COMPLETADO").size());
        resumen.put("pendientes", atencionRepository.findByEstado("PENDIENTE").size());
        resumen.put("enProceso", atencionRepository.findByEstado("EN_PROCESO").size());
        return resumen;
    }
}
