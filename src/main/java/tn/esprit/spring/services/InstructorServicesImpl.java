package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IInstructorRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class InstructorServicesImpl implements IInstructorServices{
    @Autowired
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

    // 1️⃣ Rechercher les instructeurs ayant plus de X années d'expérience
    public List<Instructor> findInstructorsWithMoreExperienceThan(int years) {
        LocalDate today = LocalDate.now();
        return instructorRepository.findAll().stream()
                .filter(instructor -> {
                    LocalDate hireDate = instructor.getDateOfHire();
                    int experience = today.getYear() - hireDate.getYear();
                    // Ajuste l'expérience si l'anniversaire n'est pas encore passé cette année
                    if (today.getMonthValue() < hireDate.getMonthValue() ||
                            (today.getMonthValue() == hireDate.getMonthValue() && today.getDayOfMonth() < hireDate.getDayOfMonth())) {
                        experience--;
                    }
                    return experience >= years;
                })
                .collect(Collectors.toList());
    }

    // 2️⃣ Obtenir la liste des cours d’un instructeur
    public Set<Course> getCoursesByInstructor(Long instructorId) {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));
        return instructor.getCourses();
    }

    // 3️⃣ Calculer la moyenne des prix des cours d’un instructeur
    public double calculateAverageCoursePrice(Long instructorId) {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));
        return instructor.getCourses().stream()
                .mapToDouble(Course::getPrice)
                .average()
                .orElse(0.0);
    }
}
