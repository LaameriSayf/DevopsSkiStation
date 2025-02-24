package tn.esprit.ouday_oueslati_4TWIN5.services;

import tn.esprit.ouday_oueslati_4TWIN5.entities.Instructor;
import java.util.List;

public interface IInstructorServices {

    Instructor addInstructor(Instructor instructor);
    Instructor updateInstructor(Instructor instructor);
    Instructor retrieveInstructor(Long numInstructor);
    List<Instructor> retrieveAll();
    Instructor removeInstructor(Long numInstructor);
    Instructor addAndAssignToCourse(Instructor instructor, List<Long> numCourses);
    Instructor addAndAssignCourses(Instructor instructor);
}
