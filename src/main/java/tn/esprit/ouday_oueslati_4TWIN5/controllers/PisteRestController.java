package tn.esprit.ouday_oueslati_4TWIN5.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import tn.esprit.ouday_oueslati_4TWIN5.entities.Color;
import tn.esprit.ouday_oueslati_4TWIN5.entities.Piste;
import tn.esprit.ouday_oueslati_4TWIN5.repositries.IPisteRepository;
import tn.esprit.ouday_oueslati_4TWIN5.services.IPisteServices;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Gestion Piste")
@RequiredArgsConstructor
@RequestMapping("/piste")
@RestController
public class PisteRestController {
    private final IPisteServices pisteServices;
    @Getter
    private final IPisteRepository Piste;
    @Operation(description = "enregistrer Piste")
    @PostMapping("/add")
    public Piste savePiste(Piste piste){
        return pisteServices.addPiste(piste);
    }
    @Operation(description = "mise a jour Piste")
    @PutMapping("/update")
    public Piste updatePiste(@RequestBody Piste piste){
        return pisteServices.updatePiste(piste);
    }
    @Operation(description = "récupérer Piste avec numPiste")
    @GetMapping("/get/{numPiste}")
    public Piste getPiste(@PathVariable Long numPiste){
        return pisteServices.retrievePiste(numPiste);
    }

    @DeleteMapping("/DeletePiste/{numPiste}")
    public void removePiste(@PathVariable Long numPiste) {
        pisteServices.removePiste(numPiste);
    }

    @GetMapping("/RetrieveAll")
    public List<Piste> retrieveAll() {
        return pisteServices.retrieveAll();
    }
    @GetMapping("/filter")
    public List<Piste> filterPistes(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Color color,
            @RequestParam(required = false) Integer minLength,
            @RequestParam(required = false) Integer maxSlope) {
        return pisteServices.filterPistes(name, color, minLength, maxSlope);
    }

    @GetMapping("/paginated")
    public Page<Piste> getPistesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return pisteServices.getPistesPaginated(page, size);
    }
}
