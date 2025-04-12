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

import tn.esprit.ouday_oueslati_4TWIN5.entities.Piste;
import tn.esprit.ouday_oueslati_4TWIN5.entities.Color;
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

    @Test
    void testAddPiste() {
        when(pisteRepository.save(any(Piste.class))).thenReturn(piste);

        Piste savedPiste = pisteService.addPiste(piste);

        assertNotNull(savedPiste);
        assertEquals("Piste Rouge", savedPiste.getNamePiste());
        verify(pisteRepository, times(1)).save(piste);
    }

    @Test
    void testUpdatePiste() {
        when(pisteRepository.save(any(Piste.class))).thenReturn(piste);

        Piste updatedPiste = pisteService.updatePiste(piste);

        assertNotNull(updatedPiste);
        assertEquals("Piste Rouge", updatedPiste.getNamePiste()); 
        verify(pisteRepository, times(1)).save(piste);
    }


    @Test
    void testRetrievePiste() {
        when(pisteRepository.findById(1L)).thenReturn(Optional.of(piste));

        Piste retrievedPiste = pisteService.retrievePiste(1L);

        assertNotNull(retrievedPiste);
        assertEquals(1L, retrievedPiste.getNumPiste());
        verify(pisteRepository, times(1)).findById(1L);
    }

    @Test
    void testRetrieveAllPistes() {
        List<Piste> pistes = Arrays.asList(piste);
        when(pisteRepository.findAll()).thenReturn(pistes);

        List<Piste> retrievedPistes = pisteService.retrieveAll();

        assertNotNull(retrievedPistes);
        assertEquals(1, retrievedPistes.size());
        verify(pisteRepository, times(1)).findAll();
    }

    @Test
    void testRemovePiste() {
        doNothing().when(pisteRepository).deleteById(1L);

        pisteService.removePiste(1L);

        verify(pisteRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFilterPistes() {
        List<Piste> pistes = Arrays.asList(piste);
        when(pisteRepository.filterPistes("Piste Rouge", Color.RED, 1000, 40)).thenReturn(pistes);

        List<Piste> filteredPistes = pisteService.filterPistes("Piste Rouge", Color.RED, 1000, 40);

        assertNotNull(filteredPistes);
        assertEquals(1, filteredPistes.size());
        verify(pisteRepository, times(1)).filterPistes("Piste Rouge", Color.RED, 1000, 40);
    }

    @Test
    void testGetPistesPaginated() {
        Page<Piste> page = new PageImpl<>(Arrays.asList(piste));
        when(pisteRepository.findAll(any(PageRequest.class))).thenReturn(page);

        Page<Piste> paginatedPistes = pisteService.getPistesPaginated(0, 10);

        assertNotNull(paginatedPistes);
        assertEquals(1, paginatedPistes.getTotalElements());
        verify(pisteRepository, times(1)).findAll(any(PageRequest.class));
    }
}
