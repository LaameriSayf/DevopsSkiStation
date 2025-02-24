package tn.esprit.ouday_oueslati_4TWIN5.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.ouday_oueslati_4TWIN5.entities.Instructor;
import tn.esprit.ouday_oueslati_4TWIN5.services.IInstructorServices;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Gestion Instructor")
@RequiredArgsConstructor
@RequestMapping("instructor")
@RestController
public class InstructorRestController {

    private final IInstructorServices instructorServices;
    @Operation(description = "enregistrer instructeur")
    @PostMapping("/add")
    public Instructor saveInstructor(@RequestBody  Instructor instructor){
        return instructorServices.addInstructor(instructor);
    }
    @Operation(description = "mise a jour instructeur")
    @PutMapping("/update")
    public Instructor updateInstructor(@RequestBody Instructor instructor){
        return instructorServices.updateInstructor(instructor);
    }
    @Operation(description = "suppression instructeur")
    @DeleteMapping ("/delete/{numInstructor}")
    public Instructor removeInstructor(@PathVariable Long numInstructor){
        return instructorServices.removeInstructor(numInstructor);
    }
    @Operation(description = "récupérer instructeur avec numInstructor")
    @GetMapping("/get/{numInstructor}")
    public Instructor getPInstructor(@PathVariable Long numInstructor){

        return instructorServices.retrieveInstructor(numInstructor);
    }
    @PutMapping("/addAndAssignToCourses/{numCourse}")
    public  Instructor addAndAssignToCourses(@RequestBody Instructor instructor, @RequestParam List<Long> numCourses) {
        return instructorServices.addAndAssignToCourse(instructor, numCourses);
    }
    @PutMapping("/addAndAssignCourses")
    public Instructor addAndAssignCourses(@RequestBody Instructor instructor){
        return instructorServices.addAndAssignCourses(instructor);
    }
}
