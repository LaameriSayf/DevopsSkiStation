package tn.esprit.spring.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.services.IInstructorServices;
import tn.esprit.spring.services.InstructorServicesImpl;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Tag(name = "\uD83D\uDC69\u200D\uD83C\uDFEB Instructor Management")
@RestController
@RequestMapping("/instructor")
@RequiredArgsConstructor
public class InstructorRestController {

    private final IInstructorServices instructorServices;
    @Autowired
    private InstructorServicesImpl is;

    @Operation(description = "Add Instructor")
    @PostMapping("/add")
    public Instructor addInstructor(@RequestBody Instructor instructor){
        return  instructorServices.addInstructor(instructor);
    }
    @Operation(description = "Add Instructor and Assign To Course")
    @PutMapping("/addAndAssignToCourse/{numCourse}")
    public Instructor addAndAssignToInstructor(@RequestBody Instructor instructor, @PathVariable("numCourse")Long numCourse){
        return  instructorServices.addInstructorAndAssignToCourse(instructor,numCourse);
    }
    @Operation(description = "Retrieve all Instructors")
    @GetMapping("/all")
    public List<Instructor> getAllInstructors(){
        return instructorServices.retrieveAllInstructors();
    }

    @Operation(description = "Update Instructor ")
    @PutMapping("/update")
    public Instructor updateInstructor(@RequestBody Instructor Instructor){
        return  instructorServices.updateInstructor(Instructor);
    }

    @Operation(description = "Retrieve Instructor by Id")
    @GetMapping("/get/{id-instructor}")
    public Instructor getById(@PathVariable("id-instructor") Long numInstructor){
        return instructorServices.retrieveInstructor(numInstructor);
    }

    // 1️⃣ GET avec paramètre dans le chemin
    @GetMapping("/experience/{years}")
    public ResponseEntity<List<Instructor>> getExperiencedInstructors(@PathVariable int years) {
        return ResponseEntity.ok(is.findInstructorsWithMoreExperienceThan(years));
    }

    // 2️⃣ GET avec ID dans le chemin
    @GetMapping("/{instructorId}/courses")
    public ResponseEntity<Set<Course>> getInstructorCourses(@PathVariable Long instructorId) {
        return ResponseEntity.ok(is.getCoursesByInstructor(instructorId));
    }

    // 3️⃣ GET avec ID dans le chemin
    @GetMapping("/{instructorId}/average-price")
    public ResponseEntity<Double> getAveragePrice(@PathVariable Long instructorId) {
        return ResponseEntity.ok(is.calculateAverageCoursePrice(instructorId));
    }
}
