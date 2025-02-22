package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IInstructorRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class InstructorServicesImpl implements IInstructorServices{

    private IInstructorRepository instructorRepository;
    private ICourseRepository courseRepository;

    public InstructorServicesImpl() {

    }

    @Override
    public Instructor addInstructor(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    @Override
    public List<Instructor> retrieveAllInstructors() {
        return instructorRepository.findAll();
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
    public Instructor addInstructorAndAssignToCourse(Instructor instructor, Long numCourse) {
        Course course = courseRepository.findById(numCourse).orElse(null);

        if (course == null) {
            throw new IllegalArgumentException("Le cours avec l'ID " + numCourse + " n'existe pas !");
        }

        // 🔥 Vérifier si la liste des cours est déjà initialisée
        if (instructor.getCourses() == null) {
            instructor.setCourses(new HashSet<>());
        }

        // ✅ Ajouter le cours au lieu d'écraser la liste
        instructor.getCourses().add(course);

        // ✅ Sauvegarder l'instructeur avec le nouveau cours ajouté
        return instructorRepository.save(instructor);
    }



}
