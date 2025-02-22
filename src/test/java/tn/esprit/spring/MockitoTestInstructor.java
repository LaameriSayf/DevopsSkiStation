package tn.esprit.spring;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IInstructorRepository;
import tn.esprit.spring.services.IInstructorServices;
import tn.esprit.spring.services.InstructorServicesImpl;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class MockitoTestInstructor {


    @Mock
    private IInstructorRepository instructorRepository;

    @Mock
    private ICourseRepository courseRepository;

    @InjectMocks
    private InstructorServicesImpl instructorService;

    private Instructor instructor;
    private Course course;

    @BeforeEach
    void setUp() {
        // Création d'un Instructor fictif
        instructor = new Instructor();
        instructor.setNumInstructor(1L);
        instructor.setFirstName("John");
        instructor.setLastName("Doe");
        instructor.setDateOfHire(LocalDate.now());

        // Création d'un Course fictif
        course = new Course();
        course.setNumCourse(1L);
    }

    @Test
    void testAddInstructorAndAssignToCourse() {
        // Mock: Simuler la récupération du cours depuis la BDD
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        // Mock: Simuler l'enregistrement de l'instructeur
        when(instructorRepository.save(any(Instructor.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Appel de la méthode à tester
        Instructor result = instructorService.addInstructorAndAssignToCourse(instructor, 1L);

        // Vérifications
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertTrue(result.getCourses().contains(course));

        // Vérification des interactions avec les mocks
        verify(courseRepository, times(1)).findById(1L);
        verify(instructorRepository, times(1)).save(instructor);
    }
}


