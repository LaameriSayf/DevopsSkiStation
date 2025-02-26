package tn.esprit.spring.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.entities.Registration;
import tn.esprit.spring.entities.Support;
import tn.esprit.spring.entities.TypeSubscription;
import tn.esprit.spring.services.IRegistrationServices;

import java.util.List;

@Tag(name = "\uD83D\uDDD3️Registration Management")
@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationRestController {
    private final IRegistrationServices registrationServices;

    @Operation(description = "Add Registration and Assign to Skier")
    @PutMapping("/addAndAssignToSkier/{numSkieur}")
    public Registration addAndAssignToSkier(@RequestBody Registration registration,
                                                     @PathVariable("numSkieur") Long numSkieur)
    {
        return  registrationServices.addRegistrationAndAssignToSkier(registration,numSkieur);
    }
    @Operation(description = "Assign Registration to Course")
    @PutMapping("/assignToCourse/{numRegis}/{numSkieur}")
    public Registration assignToCourse( @PathVariable("numRegis") Long numRegistration,
                                        @PathVariable("numSkieur") Long numCourse){
        return registrationServices.assignRegistrationToCourse(numRegistration, numCourse);
    }


    @Operation(description = "Add Registration and Assign to Skier and Course")
    @PutMapping("/addAndAssignToSkierAndCourse/{numSkieur}/{numCourse}")
    public Registration addAndAssignToSkierAndCourse(@RequestBody Registration registration,
                                                     @PathVariable("numSkieur") Long numSkieur,
                                                     @PathVariable("numCourse") Long numCourse)
    {
        return  registrationServices.addRegistrationAndAssignToSkierAndCourse(registration,numSkieur,numCourse);
    }

    @Operation(description = "Numbers of the weeks when an instructor has given lessons in a given support")
    @GetMapping("/numWeeks/{numInstructor}/{support}")
    public List<Integer> numWeeksCourseOfInstructorBySupport(@PathVariable("numInstructor")Long numInstructor,
                                                                  @PathVariable("support") Support support) {
        return registrationServices.numWeeksCourseOfInstructorBySupport(numInstructor,support);
    }
    @Operation(description = "Adding a new registration")
    @PutMapping("/addRegistration")
    public Registration addRegistration(@RequestBody Registration registration) {
        return registrationServices.addRegistration(registration);
    }

    @Operation(description = "deleting a registration if it exisits")
    @DeleteMapping("/deleteRegistration/{id}")
    public String deleteRegistration(@PathVariable("id") Long id) {
         return registrationServices.deleteRegistration(id);
    }


    @Operation(description = "updating a registration if it existis")
    @PutMapping("/updateRegistration/{id}")
    public Registration updateRegistration(@RequestBody Registration registration, @PathVariable("id") Long id) {
        return registrationServices.updateRegistrationbyId(id, registration);
    }

    @Operation(description = "retrieve all subscriptions")
    @GetMapping("/getAllSubs")
    public List<Registration> getAllSubs() {return registrationServices.getAllRegistrations();}

    @Operation(description = "retreive subscriptions by number of the week")
    @GetMapping("/registrations/week/{numWeek}")
    public List<Registration> getRegistrationsByWeek(@PathVariable int numWeek) {
        return registrationServices.getRegistrationsByWeek(numWeek);
    }

    @DeleteMapping("/registrations/skier/{skierId}")
    public ResponseEntity<String> deleteRegistrationsBySkierId(@PathVariable Long skierId) {
        registrationServices.deleteRegistrationsBySkierId(skierId);
        return ResponseEntity.ok("registrations for Skier ID " + skierId + " have been deleted.");
    }


}
