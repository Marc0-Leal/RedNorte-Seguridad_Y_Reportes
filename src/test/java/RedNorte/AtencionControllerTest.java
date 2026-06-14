package RedNorte.Seguridad_Y_Reportes.atencion;

import com.atencion.controller.AtencionController;
import com.atencion.dto.AtencionRequestDTO;
import com.atencion.model.Atencion;
import com.atencion.service.AtencionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtencionControllerTest {

    @Mock
    private AtencionService atencionService;

    @InjectMocks
    private AtencionController atencionController;

    private Atencion atencion;
    private AtencionRequestDTO dto;

    @BeforeEach
    void setUp() {
        atencion = new Atencion();
        atencion.setId(1L);
        atencion.setNombrePersona("María López");
        atencion.setTipoAtencion("GENERAL");
        atencion.setEstado("COMPLETADO");
        atencion.setFechaAtencion(LocalDateTime.now());

        dto = new AtencionRequestDTO();
        dto.setNombrePersona("María López");
        dto.setTipoAtencion("GENERAL");
        dto.setEstado("COMPLETADO");
    }

    @Test
    void debeRegistrarYRetornar201() {
        when(atencionService.registrar(any(AtencionRequestDTO.class))).thenReturn(atencion);

        ResponseEntity<Atencion> respuesta = atencionController.registrar(dto);

        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals("María López", respuesta.getBody().getNombrePersona());
    }

    @Test
    void debeListarTodasYRetornar200() {
        when(atencionService.listarTodas()).thenReturn(List.of(atencion));

        ResponseEntity<List<Atencion>> respuesta = atencionController.listar();

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, respuesta.getBody().size());
    }

    @Test
    void debeObtenerPorIdYRetornar200() {
        when(atencionService.buscarPorId(1L)).thenReturn(Optional.of(atencion));

        ResponseEntity<Atencion> respuesta = atencionController.obtener(1L);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("María López", respuesta.getBody().getNombrePersona());
    }

    @Test
    void debeRetornar404CuandoNoExiste() {
        when(atencionService.buscarPorId(99L)).thenReturn(Optional.empty());

        ResponseEntity<Atencion> respuesta = atencionController.obtener(99L);

        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    @Test
    void debeEliminarYRetornar204() {
        doNothing().when(atencionService).eliminar(1L);

        ResponseEntity<Void> respuesta = atencionController.eliminar(1L);

        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
        verify(atencionService, times(1)).eliminar(1L);
    }
}