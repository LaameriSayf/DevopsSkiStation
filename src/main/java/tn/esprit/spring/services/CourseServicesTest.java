package tn.esprit.spring.services;

<<<<<<< Updated upstream
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.TypeCourse;
import tn.esprit.spring.entities.Support;
import tn.esprit.spring.repositories.ICourseRepository;
=======
>>>>>>> Stashed changes
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
<<<<<<< Updated upstream
=======
import org.springframework.dao.EmptyResultDataAccessException;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Support;
import tn.esprit.spring.entities.TypeCourse;
import tn.esprit.spring.repositories.ICourseRepository;
>>>>>>> Stashed changes

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

<<<<<<< Updated upstream
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
=======
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
>>>>>>> Stashed changes

@ExtendWith(MockitoExtension.class)
public class CourseServicesTest {

    @Mock
    private ICourseRepository courseRepository;

    @InjectMocks
    private CourseServicesImpl courseServicesImpl;

    private Course course;

    @BeforeEach
<<<<<<< Updated upstream
    public void setUp() {
=======
    void setUp() {
>>>>>>> Stashed changes
        course = new Course();
        course.setNumCourse(1L);
        course.setLevel(2);
        course.setTypeCourse(TypeCourse.COLLECTIVE_CHILDREN);
        course.setSupport(Support.SKI);
        course.setPrice(100.0f);
        course.setTimeSlot(10);
    }

    @Test
<<<<<<< Updated upstream
    public void testAddCourse() {
        when(courseRepository.save(course)).thenReturn(course);

        Course savedCourse = courseServicesImpl.addCourse(course);

        assertNotNull(savedCourse);
        assertEquals(course.getNumCourse(), savedCourse.getNumCourse());
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    public void testRetrieveAllCourses() {
=======
    void testAddCourse_ShouldReturnCourse() {
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        Course savedCourse = courseServicesImpl.addCourse(course);

        assertAll("Course properties",
                () -> assertNotNull(savedCourse, "Saved course should not be null"),
                () -> assertEquals(course.getNumCourse(), savedCourse.getNumCourse(), "Course number should match")
        );
    }

    @Test
    void testRetrieveAllCourses_ShouldReturnListOfCourses() {
>>>>>>> Stashed changes
        Course course1 = new Course(2L, 3, TypeCourse.INDIVIDUAL, Support.SNOWBOARD, 150.0f, 12, null);
        Course course2 = new Course(3L, 4, TypeCourse.COLLECTIVE_ADULT, Support.SNOWBOARD, 200.0f, 14, null);

        when(courseRepository.findAll()).thenReturn(Arrays.asList(course, course1, course2));

        List<Course> courses = courseServicesImpl.retrieveAllCourses();

<<<<<<< Updated upstream
        assertEquals(3, courses.size());
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateCourse() {
        Course updatedCourse = new Course(1L, 3, TypeCourse.INDIVIDUAL, Support.SKI, 120.0f, 12, null);

        when(courseRepository.save(updatedCourse)).thenReturn(updatedCourse);

        Course savedCourse = courseServicesImpl.updateCourse(updatedCourse);

        assertEquals(3, savedCourse.getLevel());
        assertEquals(120.0f, savedCourse.getPrice());
        verify(courseRepository, times(1)).save(updatedCourse);
    }

    @Test
    public void testRetrieveCourse() {
=======
        assertAll("Course list properties",
                () -> assertEquals(3, courses.size(), "Course list size should be 3"),
                () -> assertEquals(course.getNumCourse(), courses.get(0).getNumCourse(), "First course number should match")
        );
    }

    @Test
    void testUpdateCourse_ShouldReturnUpdatedCourse() {
        Course updatedCourse = new Course(1L, 3, TypeCourse.INDIVIDUAL, Support.SKI, 120.0f, 12, null);
        when(courseRepository.save(any(Course.class))).thenReturn(updatedCourse);

        Course savedCourse = courseServicesImpl.updateCourse(updatedCourse);

        assertAll("Updated course properties",
                () -> assertEquals(3, savedCourse.getLevel(), "Level should be updated"),
                () -> assertEquals(120.0f, savedCourse.getPrice(), "Price should be updated")
        );
    }

    @Test
    void testRetrieveCourse_ShouldReturnCourse() {
>>>>>>> Stashed changes
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        Course retrievedCourse = courseServicesImpl.retrieveCourse(1L);

<<<<<<< Updated upstream
        assertNotNull(retrievedCourse);
        assertEquals(course.getNumCourse(), retrievedCourse.getNumCourse());
        verify(courseRepository, times(1)).findById(1L);
    }

    @Test
    public void testFilterCoursesByLevel() {
=======
        assertAll("Retrieved course properties",
                () -> assertNotNull(retrievedCourse, "Retrieved course should not be null"),
                () -> assertEquals(course.getNumCourse(), retrievedCourse.getNumCourse(), "Course number should match")
        );
    }

    @Test
    void testFilterCoursesByLevel_ShouldReturnFilteredCourses() {
>>>>>>> Stashed changes
        Course course1 = new Course(2L, 2, TypeCourse.COLLECTIVE_CHILDREN, Support.SNOWBOARD, 100.0f, 11, null);
        when(courseRepository.findByLevel(2)).thenReturn(Arrays.asList(course, course1));

        List<Course> filteredCourses = courseServicesImpl.filterCoursesByLevel(2);

<<<<<<< Updated upstream
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


        Float totalPrice = courseServicesImpl.calculateTotalPrice(Arrays.asList(course, course1)); // Fixed method call

        assertEquals(250.0f, totalPrice);
    }

=======
        assertAll("Filtered courses properties",
                () -> assertEquals(2, filteredCourses.size(), "Filtered course list size should be 2"),
                () -> assertEquals(course.getNumCourse(), filteredCourses.get(0).getNumCourse(), "First course number should match")
        );
    }

    @Test
    void testSearchCoursesByType_ShouldReturnSearchedCourses() {
        Course course1 = new Course(3L, 1, TypeCourse.COLLECTIVE_CHILDREN, Support.SKI, 130.0f, 9, null);
        when(courseRepository.findByTypeCourse(TypeCourse.COLLECTIVE_CHILDREN)).thenReturn(Arrays.asList(course, course1));

        List<Course> searchedCourses = courseServicesImpl.searchCoursesByType(TypeCourse.COLLECTIVE_CHILDREN);

        assertAll("Searched courses properties",
                () -> assertEquals(2, searchedCourses.size(), "Searched course list size should be 2"),
                () -> assertEquals(course.getNumCourse(), searchedCourses.get(0).getNumCourse(), "First course number should match")
        );
    }

    @Test
    void testCalculateTotalPrice_ShouldReturnTotalPrice() {
        Course course1 = new Course(2L, 1, TypeCourse.INDIVIDUAL, Support.SNOWBOARD, 150.0f, 11, null);

        Float totalPrice = courseServicesImpl.calculateTotalPrice(Arrays.asList(course, course1));

        assertEquals(250.0f, totalPrice, "Total price should be 250.0");
    }

    @Test
    void testRetrieveCourse_ShouldThrowExceptionWhenCourseNotFound() {
        when(courseRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(EmptyResultDataAccessException.class, () -> {
            courseServicesImpl.retrieveCourse(1L);
        });
    }
>>>>>>> Stashed changes
}
