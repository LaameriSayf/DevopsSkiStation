package tn.esprit.ouday_oueslati_4TWIN5.controllers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ouday_oueslati_4TWIN5.entities.Registration;
import tn.esprit.ouday_oueslati_4TWIN5.entities.Skier;
import tn.esprit.ouday_oueslati_4TWIN5.entities.TypeSubscription;
import tn.esprit.ouday_oueslati_4TWIN5.services.ISkierServices;
import java.time.LocalDate;
import java.util.List;

@Tag(name = "Gestion Skier")
@RequiredArgsConstructor
@RequestMapping("skier")
@RestController
public class SkierRestController {

    private final ISkierServices skierServices;

    @Operation(description = "Ajout d'un skier")
    @PostMapping("/add")
    public Skier saveSkier(@RequestBody Skier skier){
        return skierServices.addSkier(skier);
    }
    @Operation(description = "mise a jour d'un skier")
    @PutMapping("/update")
    public Skier updateSkier(@RequestBody Skier skier){
        return skierServices.updateSkier(skier);
    }
    @Operation(description = "récupérer un skier par  son numéro")
    @GetMapping("/get/{numSkier}")
    public Skier getSkier(@PathVariable Long numSkier){

        return skierServices.retrieveSkier(numSkier);
    }
    @Operation(description = "assigner un Skier")
    @PutMapping("/assignToPiste/{numSkier}/{numPiste}")
    public void assignSkier(@PathVariable Long numSkier, @PathVariable Long numPiste){
        skierServices.assignSkier(numSkier, numPiste);
    }
    @Operation(description = "récupérer un skier par firstNama et lastName")
    @GetMapping("/getBy/{firstName}/{lastName}")
        public Skier getByFLname(@PathVariable String firstName, @PathVariable String lastName)
    {
        return skierServices.getByFirstNameAndLastName(firstName,lastName);
    }
    @Operation(description = "récupérer un skier par birthdate")
    @GetMapping("/getByDate/{birthDate}")
    public Skier getbirthDate(@PathVariable LocalDate birthDate)
    {
        return skierServices.getBybirthDate(birthDate);
    }
    @Operation(description = "ajouter inscription et assigner au Skieur")
    @PutMapping("/addRegistration/{numSkier}")
    public Registration addRegistrationAndAssignToSkier(@RequestBody Registration registration,@PathVariable Long numSkier)
    {
        return skierServices.addRegistrationAndAssignToSkier(registration, numSkier);
    }
    @Operation(description = "ajouter Skier et assigner au Course avec numCourse")
    @PutMapping("/addSkierAndAssignToCourse/{numCourse}")
    public Skier addSkierAndAssignToCourse(@RequestBody Skier skier ,@PathVariable Long numCourse){
        return skierServices.addSkierAndAssignToCourse(skier, numCourse);
    }
    @Operation(description = "récupérer Skier avec typeSubscription")
    @GetMapping("/bySubscriptionType")
    public List<Skier> getSkiersBySubscriptionType(@RequestParam TypeSubscription typeSubscription) {
        return skierServices.retrieveSkiersBySubscriptionType(typeSubscription);
    }
}
