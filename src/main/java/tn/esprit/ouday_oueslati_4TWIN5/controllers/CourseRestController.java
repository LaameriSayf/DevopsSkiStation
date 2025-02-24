package tn.esprit.ouday_oueslati_4TWIN5.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.ouday_oueslati_4TWIN5.entities.Course;
import tn.esprit.ouday_oueslati_4TWIN5.services.ICourseServices;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Gestion Course")
@RequiredArgsConstructor
@RequestMapping("course")
@RestController

public class CourseRestController {
    private final ICourseServices courseServices;
    @Operation(description = "sauvegarder Course")
    @PostMapping("/add")
    public Course saveCourse(Course course){
        return courseServices.addCourse(course);
    }
    @Operation(description = "mise a jour")
    @PutMapping("/update")
    public Course updateCourse(@RequestBody Course course){
        return courseServices.updateCourse(course);
    }
    @Operation(description = "récupérer course avec numCourse")
    @GetMapping("/get/{numCourse}")
    public Course getCourse(@PathVariable Long numCourse){

        return courseServices.retrieveCourse(numCourse);
    }
}
