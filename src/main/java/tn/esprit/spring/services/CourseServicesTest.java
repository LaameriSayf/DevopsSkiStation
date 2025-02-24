package tn.esprit.spring.services;

import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.TypeCourse;
import tn.esprit.spring.entities.Support;
import tn.esprit.spring.repositories.ICourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CourseServicesTest {

    @Mock
    private ICourseRepository courseRepository;

    @InjectMocks
    private CourseServicesImpl courseServicesImpl; // Fixed: Using the correct service class

    private Course course;

    @BeforeEach
    public void setUp() {
        course = new Course();
        course.setNumCourse(1L);
        course.setLevel(2);
        course.setTypeCourse(TypeCourse.COLLECTIVE_CHILDREN);
        course.setSupport(Support.SKI); // Fixed: Using enum instead of String
        course.setPrice(100.0f);
        course.setTimeSlot(10);
    }

    @Test
    public void testAddCourse() {
        when(courseRepository.save(course)).thenReturn(course);

        Course savedCourse = courseServicesImpl.addCourse(course); // Fixed method call

        assertNotNull(savedCourse);
        assertEquals(course.getNumCourse(), savedCourse.getNumCourse());
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    public void testRetrieveAllCourses() {
        Course course1 = new Course(2L, 3, TypeCourse.INDIVIDUAL, Support.SNOWBOARD, 150.0f, 12, null);
        Course course2 = new Course(3L, 4, TypeCourse.COLLECTIVE_ADULT, Support.SNOWBOARD, 200.0f, 14, null);

        when(courseRepository.findAll()).thenReturn(Arrays.asList(course, course1, course2));

        List<Course> courses = courseServicesImpl.retrieveAllCourses(); // Fixed method call

        assertEquals(3, courses.size());
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateCourse() {
        Course updatedCourse = new Course(1L, 3, TypeCourse.INDIVIDUAL, Support.SKI, 120.0f, 12, null);

        when(courseRepository.save(updatedCourse)).thenReturn(updatedCourse);

        Course savedCourse = courseServicesImpl.updateCourse(updatedCourse); // Fixed method call

        assertEquals(3, savedCourse.getLevel());
        assertEquals(120.0f, savedCourse.getPrice());
        verify(courseRepository, times(1)).save(updatedCourse);
    }

    @Test
    public void testRetrieveCourse() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        Course retrievedCourse = courseServicesImpl.retrieveCourse(1L); // Fixed method call

        assertNotNull(retrievedCourse);
        assertEquals(course.getNumCourse(), retrievedCourse.getNumCourse());
        verify(courseRepository, times(1)).findById(1L);
    }

    @Test
    public void testFilterCoursesByLevel() {
        Course course1 = new Course(2L, 2, TypeCourse.COLLECTIVE_CHILDREN, Support.SNOWBOARD, 100.0f, 11, null);
        when(courseRepository.findByLevel(2)).thenReturn(Arrays.asList(course, course1));

        List<Course> filteredCourses = courseServicesImpl.filterCoursesByLevel(2); // Fixed method call

        assertEquals(2, filteredCourses.size());
        verify(courseRepository, times(1)).findByLevel(2);
    }

    @Test
    public void testSearchCoursesByType() {
        Course course1 = new Course(3L, 1, TypeCourse.COLLECTIVE_CHILDREN, Support.SKI, 130.0f, 9, null);
        when(courseRepository.findByTypeCourse(TypeCourse.COLLECTIVE_CHILDREN)).thenReturn(Arrays.asList(course, course1));

        List<Course> searchedCourses = courseServicesImpl.searchCoursesByType(TypeCourse.COLLECTIVE_CHILDREN); // Fixed method call

        assertEquals(2, searchedCourses.size());
        verify(courseRepository, times(1)).findByTypeCourse(TypeCourse.COLLECTIVE_CHILDREN);
    }

    @Test
    public void testCalculateTotalPrice() {
        Course course1 = new Course(2L, 1, TypeCourse.INDIVIDUAL, Support.SNOWBOARD, 150.0f, 11, null);

        // No need for stubbing courseRepository.findAll() if it's not used in the method

        Float totalPrice = courseServicesImpl.calculateTotalPrice(Arrays.asList(course, course1)); // Fixed method call

        assertEquals(250.0f, totalPrice);
    }

}
