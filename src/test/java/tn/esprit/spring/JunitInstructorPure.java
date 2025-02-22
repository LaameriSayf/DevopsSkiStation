package tn.esprit.spring;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.services.FakeCourseRepository;
import tn.esprit.spring.services.FakeInstructorRepository;
import tn.esprit.spring.services.InstructorServicesImpl;

import java.time.LocalDate;
import java.util.HashSet;

public class JunitInstructorPure{

    private InstructorServicesImpl instructorService;
    private FakeInstructorRepository fakeInstructorRepository;
    private FakeCourseRepository fakeCourseRepository;

    private Instructor instructor;
    private Course course;

    @BeforeEach
    void setUp() {
        // ✅ Initialisation des faux repositories
        fakeInstructorRepository = new FakeInstructorRepository();
        fakeCourseRepository = new FakeCourseRepository();

        // ✅ Initialisation du service avec les faux repositories
        instructorService = new InstructorServicesImpl(fakeInstructorRepository, fakeCourseRepository);

        // ✅ Création d'un Instructor fictif
        instructor = new Instructor();
        instructor.setNumInstructor(1L);
        instructor.setFirstName("John");
        instructor.setLastName("Doe");
        instructor.setDateOfHire(LocalDate.now());
        instructor.setCourses(new HashSet<>());

        // ✅ Création d'un Course fictif
        course = new Course();
        course.setNumCourse(1L);
        fakeCourseRepository.save(course); // ✅ Ajout du cours dans le faux repo
    }

    @Test
    void testAddInstructorAndAssignToCourse() {
        // ✅ Appeler la méthode avec le faux repository
        Instructor updatedInstructor = instructorService.addInstructorAndAssignToCourse(instructor, 1L);

        // ✅ Vérifications
        assertNotNull(updatedInstructor);
        assertEquals(1, updatedInstructor.getCourses().size()); // ✅ Vérifie que le cours est bien ajouté
        assertTrue(updatedInstructor.getCourses().contains(course));
    }
}
