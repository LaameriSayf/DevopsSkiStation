package tn.esprit.spring;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.repositories.IInstructorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.services.InstructorServicesImpl;

import java.time.LocalDate;
import java.util.*;
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class InstructorServiceMockitoTest {

    @Mock
    private IInstructorRepository instructorRepository;

    @InjectMocks
    private InstructorServicesImpl instructorService;

    private Instructor instructor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        instructor = new Instructor();
        instructor.setNumInstructor(1L);
        instructor.setFirstName("John");
        instructor.setLastName("Doe");
        instructor.setDateOfHire(LocalDate.of(2015, 1, 1));

        Course course1 = new Course();
        course1.setPrice(100.0f);

        Course course2 = new Course();
        course2.setPrice(200.0f);

        instructor.setCourses(new HashSet<>(Arrays.asList(course1, course2)));
    }

    @Test
    void testFindInstructorsWithMoreExperienceThan() {
        when(instructorRepository.findAll()).thenReturn(Collections.singletonList(instructor));

        List<Instructor> result = instructorService.findInstructorsWithMoreExperienceThan(5);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void testGetCoursesByInstructor() {
        when(instructorRepository.findById(1L)).thenReturn(Optional.of(instructor));

        Set<Course> courses = instructorService.getCoursesByInstructor(1L);

        assertEquals(2, courses.size());
    }

    @Test
    void testCalculateAverageCoursePrice() {
        when(instructorRepository.findById(1L)).thenReturn(Optional.of(instructor));

        double avgPrice = instructorService.calculateAverageCoursePrice(1L);

        assertEquals(150.0, avgPrice);
    }
}
