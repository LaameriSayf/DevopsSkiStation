package tn.esprit.ouday_oueslati_4TWIN5;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import tn.esprit.ouday_oueslati_4TWIN5.entities.Color;
import tn.esprit.ouday_oueslati_4TWIN5.entities.Piste;
import tn.esprit.ouday_oueslati_4TWIN5.repositries.IPisteRepository;
import tn.esprit.ouday_oueslati_4TWIN5.services.PisteServicesImpl;

@ExtendWith(MockitoExtension.class)
class OudayOueslatiApplicationTests {

    @Mock
    private IPisteRepository pisteRepository;

    @InjectMocks
    private PisteServicesImpl pisteService;

    private Piste piste;

    @BeforeEach
    void setUp() {
        piste = new Piste();
        piste.setNumPiste(1L);
        piste.setNamePiste("Piste Rouge");
        piste.setColor(Color.RED);
        piste.setLength(1200);
        piste.setSlope(35);
    }

    // Test pour addPiste
    @Test
    void shouldAddPisteSuccessfully() {
        when(pisteRepository.save(any(Piste.class))).thenReturn(piste);

        Piste savedPiste = pisteService.addPiste(piste);

        assertNotNull(savedPiste);
        assertEquals("Piste Rouge", savedPiste.getNamePiste());
        assertEquals(Color.RED, savedPiste.getColor());
        assertEquals(1200, savedPiste.getLength());
        assertEquals(35, savedPiste.getSlope());
        verify(pisteRepository, times(1)).save(piste);
    }

    // Test pour updatePiste
    @Test
    void shouldUpdatePisteSuccessfully() {
        when(pisteRepository.save(any(Piste.class))).thenReturn(piste);

        Piste updatedPiste = pisteService.updatePiste(piste);

        assertNotNull(updatedPiste);
        assertEquals("Piste Rouge", updatedPiste.getNamePiste());
        verify(pisteRepository, times(1)).save(piste);
    }

    // Test pour retrievePiste - Succès
    @Test
    void shouldRetrievePisteByIdSuccessfully() {
        when(pisteRepository.findById(1L)).thenReturn(Optional.of(piste));

        Piste retrievedPiste = pisteService.retrievePiste(1L);

        assertNotNull(retrievedPiste);
        assertEquals(1L, retrievedPiste.getNumPiste());
        assertEquals("Piste Rouge", retrievedPiste.getNamePiste());
        verify(pisteRepository, times(1)).findById(1L);
    }

    // Test pour retrievePiste - Échec (ID inexistant)
    @Test
    void shouldReturnNullWhenPisteNotFound() {
        when(pisteRepository.findById(999L)).thenReturn(Optional.empty());

        Piste retrievedPiste = pisteService.retrievePiste(999L);

        assertNull(retrievedPiste);
        verify(pisteRepository, times(1)).findById(999L);
    }

    // Test pour retrieveAll
    @Test
    void shouldRetrieveAllPistesSuccessfully() {
        List<Piste> pistes = Arrays.asList(piste);
        when(pisteRepository.findAll()).thenReturn(pistes);

        List<Piste> retrievedPistes = pisteService.retrieveAll();

        assertNotNull(retrievedPistes);
        assertEquals(1, retrievedPistes.size());
        assertEquals("Piste Rouge", retrievedPistes.get(0).getNamePiste());
        verify(pisteRepository, times(1)).findAll();
    }

    // Test pour retrieveAll - Liste vide
    @Test
    void shouldReturnEmptyListWhenNoPistes() {
        when(pisteRepository.findAll()).thenReturn(Arrays.asList());

        List<Piste> retrievedPistes = pisteService.retrieveAll();

        assertNotNull(retrievedPistes);
        assertTrue(retrievedPistes.isEmpty());
        verify(pisteRepository, times(1)).findAll();
    }

    // Test pour removePiste
    @Test
    void shouldRemovePisteSuccessfully() {
        doNothing().when(pisteRepository).deleteById(1L);

        pisteService.removePiste(1L);

        verify(pisteRepository, times(1)).deleteById(1L);
    }

    // Test pour filterPistes - Succès
    @Test
    void shouldFilterPistesSuccessfully() {
        List<Piste> pistes = Arrays.asList(piste);
        when(pisteRepository.filterPistes("Piste Rouge", Color.RED, 1000, 40)).thenReturn(pistes);

        List<Piste> filteredPistes = pisteService.filterPistes("Piste Rouge", Color.RED, 1000, 40);

        assertNotNull(filteredPistes);
        assertEquals(1, filteredPistes.size());
        assertEquals("Piste Rouge", filteredPistes.get(0).getNamePiste());
        verify(pisteRepository, times(1)).filterPistes("Piste Rouge", Color.RED, 1000, 40);
    }

    // Test pour filterPistes - Aucun résultat
    @Test
    void shouldReturnEmptyListWhenNoMatchingPistes() {
        when(pisteRepository.filterPistes("Piste Inexistante", Color.BLUE, 500, 20)).thenReturn(Arrays.asList());

        List<Piste> filteredPistes = pisteService.filterPistes("Piste Inexistante", Color.BLUE, 500, 20);

        assertNotNull(filteredPistes);
        assertTrue(filteredPistes.isEmpty());
        verify(pisteRepository, times(1)).filterPistes("Piste Inexistante", Color.BLUE, 500, 20);
    }

    // Test pour getPistesPaginated - Succès
    @Test
    void shouldGetPistesPaginatedSuccessfully() {
        Page<Piste> page = new PageImpl<>(Arrays.asList(piste));
        when(pisteRepository.findAll(any(PageRequest.class))).thenReturn(page);

        Page<Piste> paginatedPistes = pisteService.getPistesPaginated(0, 10);

        assertNotNull(paginatedPistes);
        assertEquals(1, paginatedPistes.getTotalElements());
        assertEquals("Piste Rouge", paginatedPistes.getContent().get(0).getNamePiste());
        verify(pisteRepository, times(1)).findAll(any(PageRequest.class));
    }

    // Test pour getPistesPaginated - Page vide
    @Test
    void shouldReturnEmptyPageWhenNoPistes() {
        Page<Piste> emptyPage = new PageImpl<>(Arrays.asList());
        when(pisteRepository.findAll(any(PageRequest.class))).thenReturn(emptyPage);

        Page<Piste> paginatedPistes = pisteService.getPistesPaginated(0, 10);

        assertNotNull(paginatedPistes);
        assertEquals(0, paginatedPistes.getTotalElements());
        verify(pisteRepository, times(1)).findAll(any(PageRequest.class));
    }
}
