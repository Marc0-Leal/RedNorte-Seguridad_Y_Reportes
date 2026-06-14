# Microservicio · Personas Atendidas

API REST en **Spring Boot 3.2 + PostgreSQL** con dashboard de gráfico de barras.

---

## Requisitos

- Java 17+
- Maven 3.8+
- PostgreSQL 14+

---

## Configuración

### 1. Crear la base de datos

```bash
psql -U postgres -f src/main/resources/data-init.sql
```

### 2. Editar credenciales

En `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/atencion_db
spring.datasource.username=postgres
spring.datasource.password=TU_PASSWORD
```

### 3. Levantar el servicio

```bash
mvn spring-boot:run
```

El servicio corre en **http://localhost:8080**

### 4. Abrir el dashboard

```
http://localhost:8080/index.html
```

---

## Endpoints REST

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST`   | `/api/atenciones` | Registrar nueva atención |
| `GET`    | `/api/atenciones` | Listar todas las atenciones |
| `GET`    | `/api/atenciones/{id}` | Obtener una por ID |
| `PUT`    | `/api/atenciones/{id}` | Actualizar una atención |
| `DELETE` | `/api/atenciones/{id}` | Eliminar una atención |
| `GET`    | `/api/atenciones/grafico/diario?desde=2025-06-01&hasta=2025-06-30` | **Datos gráfico por día** |
| `GET`    | `/api/atenciones/grafico/mensual?anio=2025` | **Datos gráfico por mes** |
| `GET`    | `/api/atenciones/resumen` | Totales y conteos |

---

## Ejemplos cURL

### Registrar una atención
```bash
curl -X POST http://localhost:8080/api/atenciones \
  -H "Content-Type: application/json" \
  -d '{
    "nombrePersona": "Juan Pérez",
    "tipoAtencion": "MEDICA",
    "estado": "COMPLETADO",
    "observaciones": "Control rutinario"
  }'
```

### Gráfico diario (últimos 7 días)
```bash
curl "http://localhost:8080/api/atenciones/grafico/diario?desde=2025-06-01&hasta=2025-06-07"
```

Respuesta:
```json
{
  "etiquetas": ["01/06", "02/06", "03/06"],
  "valores":   [5, 8, 3],
  "titulo":    "Personas atendidas por día",
  "totalAtenciones": 16,
  "series": null
}
```

### Gráfico mensual
```bash
curl "http://localhost:8080/api/atenciones/grafico/mensual?anio=2025"
```

---

## Estructura del proyecto

```
atencion-service/
├── pom.xml
└── src/main/
    ├── java/com/atencion/
    │   ├── AtencionServiceApplication.java   ← Main
    │   ├── model/Atencion.java               ← Entidad JPA
    │   ├── dto/
    │   │   ├── AtencionRequestDTO.java
    │   │   └── GraficoDTO.java
    │   ├── repository/AtencionRepository.java ← Queries JPA
    │   ├── service/AtencionService.java       ← Lógica de negocio
    │   ├── controller/AtencionController.java ← Endpoints REST
    │   └── config/GlobalExceptionHandler.java
    └── resources/
        ├── application.properties
        ├── data-init.sql                      ← Script BD + datos prueba
        └── static/
            └── index.html                     ← Dashboard Chart.js
```
