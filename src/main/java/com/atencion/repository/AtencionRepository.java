package com.atencion.repository;

import com.atencion.model.Atencion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AtencionRepository extends JpaRepository<Atencion, Long> {

    // Conteo de atenciones por DÍA en un rango de fechas
    @Query("""
        SELECT CAST(a.fechaAtencion AS date), COUNT(a)
        FROM Atencion a
        WHERE a.fechaAtencion BETWEEN :inicio AND :fin
        GROUP BY CAST(a.fechaAtencion AS date)
        ORDER BY CAST(a.fechaAtencion AS date)
    """)
    List<Object[]> countByDia(
        @Param("inicio") LocalDateTime inicio,
        @Param("fin") LocalDateTime fin
    );

    // Conteo de atenciones por MES en un rango de fechas
    @Query("""
        SELECT FUNCTION('TO_CHAR', a.fechaAtencion, 'YYYY-MM'), COUNT(a)
        FROM Atencion a
        WHERE a.fechaAtencion BETWEEN :inicio AND :fin
        GROUP BY FUNCTION('TO_CHAR', a.fechaAtencion, 'YYYY-MM')
        ORDER BY FUNCTION('TO_CHAR', a.fechaAtencion, 'YYYY-MM')
    """)
    List<Object[]> countByMes(
        @Param("inicio") LocalDateTime inicio,
        @Param("fin") LocalDateTime fin
    );

    // Conteo por tipo de atención y día
    @Query("""
        SELECT CAST(a.fechaAtencion AS date), a.tipoAtencion, COUNT(a)
        FROM Atencion a
        WHERE a.fechaAtencion BETWEEN :inicio AND :fin
        GROUP BY CAST(a.fechaAtencion AS date), a.tipoAtencion
        ORDER BY CAST(a.fechaAtencion AS date)
    """)
    List<Object[]> countByDiaYTipo(
        @Param("inicio") LocalDateTime inicio,
        @Param("fin") LocalDateTime fin
    );

    // Total en rango
    long countByFechaAtencionBetween(LocalDateTime inicio, LocalDateTime fin);

    // Buscar por estado
    List<Atencion> findByEstado(String estado);

    // Buscar por rango de fechas
    List<Atencion> findByFechaAtencionBetweenOrderByFechaAtencionDesc(
        LocalDateTime inicio, LocalDateTime fin
    );
}
