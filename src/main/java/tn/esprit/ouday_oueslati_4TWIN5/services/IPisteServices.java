package tn.esprit.ouday_oueslati_4TWIN5.services;
import org.springframework.data.domain.Page;
import tn.esprit.ouday_oueslati_4TWIN5.entities.Color;
import tn.esprit.ouday_oueslati_4TWIN5.entities.Piste;

import java.util.List;

public interface IPisteServices {

    Piste addPiste(Piste piste);
    Piste updatePiste(Piste piste);
    Piste retrievePiste(Long numPiste);
    List<Piste> retrieveAll();
    void removePiste(Long numPiste);
    public List<Piste> filterPistes(String name, Color color, Integer minLength, Integer maxSlope);
    public Page<Piste> getPistesPaginated(int page, int size);
}
