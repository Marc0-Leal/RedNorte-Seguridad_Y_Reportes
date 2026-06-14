-- ============================================================
-- Script de inicialización: Base de datos de atenciones
-- ============================================================

-- Crear base de datos (ejecutar como superusuario)
CREATE DATABASE atencion_db;

-- Conectarse a la base de datos y crear tabla
\c atencion_db;

CREATE TABLE IF NOT EXISTS atenciones (
    id               BIGSERIAL PRIMARY KEY,
    nombre_persona   VARCHAR(255) NOT NULL,
    tipo_atencion    VARCHAR(100),
    estado           VARCHAR(50) DEFAULT 'COMPLETADO',
    fecha_atencion   TIMESTAMP NOT NULL DEFAULT NOW(),
    observaciones    TEXT
);

-- ── Datos de prueba (últimos 30 días) ────────────────────────────────────────
INSERT INTO atenciones (nombre_persona, tipo_atencion, estado, fecha_atencion) VALUES
('Ana García',       'MEDICA',          'COMPLETADO', NOW() - INTERVAL '1 day'),
('Luis Martínez',    'GENERAL',         'COMPLETADO', NOW() - INTERVAL '1 day'),
('Carmen López',     'PSICOLOGICA',     'COMPLETADO', NOW() - INTERVAL '2 days'),
('Pedro Soto',       'SOCIAL',          'COMPLETADO', NOW() - INTERVAL '2 days'),
('María Torres',     'MEDICA',          'COMPLETADO', NOW() - INTERVAL '2 days'),
('José Ramírez',     'ADMINISTRATIVA',  'COMPLETADO', NOW() - INTERVAL '3 days'),
('Elena Díaz',       'GENERAL',         'PENDIENTE',  NOW() - INTERVAL '3 days'),
('Carlos Fuentes',   'MEDICA',          'COMPLETADO', NOW() - INTERVAL '4 days'),
('Sofía Herrera',    'PSICOLOGICA',     'COMPLETADO', NOW() - INTERVAL '5 days'),
('Andrés Morales',   'GENERAL',         'COMPLETADO', NOW() - INTERVAL '5 days'),
('Isabel Vargas',    'SOCIAL',          'EN_PROCESO', NOW() - INTERVAL '6 days'),
('Miguel Castillo',  'MEDICA',          'COMPLETADO', NOW() - INTERVAL '7 days'),
('Paula Ramos',      'GENERAL',         'COMPLETADO', NOW() - INTERVAL '7 days'),
('Tomás Jiménez',    'ADMINISTRATIVA',  'COMPLETADO', NOW() - INTERVAL '8 days'),
('Valentina Silva',  'PSICOLOGICA',     'COMPLETADO', NOW() - INTERVAL '9 days'),
('Diego Muñoz',      'MEDICA',          'COMPLETADO', NOW() - INTERVAL '10 days'),
('Catalina Rojas',   'GENERAL',         'COMPLETADO', NOW() - INTERVAL '10 days'),
('Rodrigo Gómez',    'SOCIAL',          'COMPLETADO', NOW() - INTERVAL '11 days'),
('Francisca Pérez',  'MEDICA',          'COMPLETADO', NOW() - INTERVAL '12 days'),
('Sebastián Flores', 'GENERAL',         'PENDIENTE',  NOW() - INTERVAL '14 days'),
('Daniela Moya',     'PSICOLOGICA',     'COMPLETADO', NOW() - INTERVAL '15 days'),
('Matías Vera',      'ADMINISTRATIVA',  'COMPLETADO', NOW() - INTERVAL '16 days'),
('Camila Ríos',      'MEDICA',          'COMPLETADO', NOW() - INTERVAL '18 days'),
('Nicolás Aguilar',  'GENERAL',         'COMPLETADO', NOW() - INTERVAL '20 days'),
('Antonia Bravo',    'SOCIAL',          'COMPLETADO', NOW() - INTERVAL '22 days'),
('Felipe Cárdenas',  'MEDICA',          'COMPLETADO', NOW() - INTERVAL '25 days'),
('Javiera Pinto',    'PSICOLOGICA',     'COMPLETADO', NOW() - INTERVAL '27 days'),
('Ignacio Núñez',    'GENERAL',         'COMPLETADO', NOW() - INTERVAL '28 days'),
('Pilar Contreras',  'MEDICA',          'COMPLETADO', NOW() - INTERVAL '29 days'),
('Gonzalo Espinoza', 'ADMINISTRATIVA',  'COMPLETADO', NOW() - INTERVAL '30 days');
