package tn.esprit.ouday_oueslati_4TWIN5.services;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tn.esprit.ouday_oueslati_4TWIN5.entities.Color;
import tn.esprit.ouday_oueslati_4TWIN5.entities.Piste;
import tn.esprit.ouday_oueslati_4TWIN5.repositries.IPisteRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@RequiredArgsConstructor
@Service
public class PisteServicesImpl implements  IPisteServices{

    private final IPisteRepository pisteRepository ;

    public Piste addPiste(Piste piste){
        return pisteRepository.save(piste);
    }

    @Override
    public Piste updatePiste(Piste piste) {
        return pisteRepository.save(piste);
    }

    @Override
    public Piste retrievePiste(Long numPiste) {
        return pisteRepository.findById(numPiste).orElse(null);
    }

    @Override
    public List<Piste> retrieveAll() {
        return (List<Piste>) pisteRepository.findAll();
    }

    public void removePiste(Long numPiste) {
        pisteRepository.deleteById(numPiste);
    }

    @Override
    public List<Piste> filterPistes(String name, Color color, Integer minLength, Integer maxSlope) {
        return pisteRepository.filterPistes(name, color, minLength, maxSlope);
    }

    @Override
    public Page<Piste> getPistesPaginated(int page, int size) {
        return pisteRepository.findAll(PageRequest.of(page, size));
    }

}
