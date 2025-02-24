package tn.esprit.ouday_oueslati_4TWIN5.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.ouday_oueslati_4TWIN5.entities.Registration;
import tn.esprit.ouday_oueslati_4TWIN5.services.ICourseServices;
import tn.esprit.ouday_oueslati_4TWIN5.services.IRegistrationServices;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Gestion Registration")
@RequiredArgsConstructor
@RequestMapping("registration")
@RestController
public class RegistrationRestController {
    private final IRegistrationServices registrationServices;
    private final ICourseServices courseServices;
    @Operation(description = "sauvagarder Registration")
    @PostMapping("/add")
    public Registration saveRegistration(Registration registration){
        return registrationServices.addRegistration(registration);
    }
    @Operation(description = "mise a jour Registration")
    @PutMapping("/update")
    public Registration updateRegistration(@RequestBody Registration registration){
        return registrationServices.updateRegistration(registration);
    }
    @Operation(description = "récupérer Registration avec numRegistration")
    @GetMapping("/get/{numRegistration}")
    public Registration getRegistration(@PathVariable Long numRegistration){

        return registrationServices.retrieveRegistration(numRegistration);
    }
    @Operation(description = "ajouter Registration Et Assigner Au Course")
    @PutMapping("/assignToCourse/{numCourse}")
        public Registration addRegistrationAndAssignToCourse(@RequestBody Registration registration,@PathVariable Long numCourse){
        return registrationServices.addRegistrationAndAssignToCourse(registration , numCourse);
    }
}
