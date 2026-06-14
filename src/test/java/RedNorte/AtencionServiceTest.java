package RedNorte.Seguridad_Y_Reportes.atencion;

import com.atencion.dto.AtencionRequestDTO;
import com.atencion.model.Atencion;
import com.atencion.repository.AtencionRepository;
import com.atencion.service.AtencionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtencionServiceTest {

    @Mock
    private AtencionRepository atencionRepository;

    @InjectMocks
    private AtencionService atencionService;

    private Atencion atencion;
    private AtencionRequestDTO dto;

    @BeforeEach
    void setUp() {
        atencion = new Atencion();
        atencion.setId(1L);
        atencion.setNombrePersona("Juan Pérez");
        atencion.setTipoAtencion("MEDICA");
        atencion.setEstado("COMPLETADO");
        atencion.setFechaAtencion(LocalDateTime.now());

        dto = new AtencionRequestDTO();
        dto.setNombrePersona("Juan Pérez");
        dto.setTipoAtencion("MEDICA");
        dto.setEstado("COMPLETADO");
    }

    @Test
    void debeRegistrarAtencionCorrectamente() {
        when(atencionRepository.save(any(Atencion.class))).thenReturn(atencion);
        Atencion resultado = atencionService.registrar(dto);
        assertNotNull(resultado);
        assertEquals("Juan Pérez", resultado.getNombrePersona());
        assertEquals("MEDICA", resultado.getTipoAtencion());
        verify(atencionRepository, times(1)).save(any(Atencion.class));
    }

    @Test
    void debeListarTodasLasAtenciones() {
        when(atencionRepository.findAll()).thenReturn(List.of(atencion));
        List<Atencion> resultado = atencionService.listarTodas();
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(atencionRepository, times(1)).findAll();
    }

    @Test
    void debeBuscarAtencionPorId() {
        when(atencionRepository.findById(1L)).thenReturn(Optional.of(atencion));
        Optional<Atencion> resultado = atencionService.buscarPorId(1L);
        assertTrue(resultado.isPresent());
        assertEquals("Juan Pérez", resultado.get().getNombrePersona());
    }

    @Test
    void debeRetornarVacioCuandoNoExisteId() {
        when(atencionRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<Atencion> resultado = atencionService.buscarPorId(99L);
        assertFalse(resultado.isPresent());
    }

    @Test
    void debeEliminarAtencionPorId() {
        doNothing().when(atencionRepository).deleteById(1L);
        atencionService.eliminar(1L);
        verify(atencionRepository, times(1)).deleteById(1L);
    }
}