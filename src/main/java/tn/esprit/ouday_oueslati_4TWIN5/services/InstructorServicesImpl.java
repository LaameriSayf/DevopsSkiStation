package tn.esprit.ouday_oueslati_4TWIN5.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.ouday_oueslati_4TWIN5.entities.Course;
import tn.esprit.ouday_oueslati_4TWIN5.entities.Instructor;
import tn.esprit.ouday_oueslati_4TWIN5.repositries.ICourseRepository;
import tn.esprit.ouday_oueslati_4TWIN5.repositries.IInstructorRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public  class InstructorServicesImpl implements IInstructorServices{

    private final IInstructorRepository instructorRepository;
    private final ICourseRepository courseRepository;

    public Instructor addInstructor(Instructor instructor){
        return instructorRepository.save(instructor);
    }

    @Override
    public Instructor updateInstructor(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    @Override
    public Instructor retrieveInstructor(Long numInstructor) {
        return instructorRepository.findById(numInstructor).orElse(null);
    }

    @Override
    public List<Instructor> retrieveAll() {
        return (List<Instructor>) instructorRepository.findAll();
    }

    @Override
    public Instructor removeInstructor(Long numInstructor) {
        instructorRepository.deleteById(numInstructor);
        return null;
    }

    @Override
    public Instructor addAndAssignToCourse(Instructor instructor, List<Long> numCourses){
        List<Course> courses = (List<Course>) courseRepository.findAllById(numCourses);
        for (Course course : courses) {
            instructor.getCourses().add(course);
        }
        return instructorRepository.save(instructor);
    }
    @Override
    public Instructor addAndAssignCourses(Instructor instructor){
        Set<Course> courseList=  instructor.getCourses();
        Set<Course> courseSet = new HashSet<Course>();
        for (Course course : courseList){
            course = courseRepository.findById(course.getNumCourse()).orElse(null);
            courseSet.add(course);
        }
        return  instructorRepository.save(instructor);
    }
}




