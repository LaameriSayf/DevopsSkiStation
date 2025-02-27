package tn.esprit.spring;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.repositories.IInstructorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class InstructorServiceJUnitTest {

    @Qualifier("IInstructorRepository")
    @Autowired
    private IInstructorRepository instructorRepository;

    private Instructor instructor1, instructor2;

    @BeforeEach
    void setUp() {
        instructor1 = new Instructor();
        instructor1.setFirstName("Alice");
        instructor1.setLastName("Smith");
        instructor1.setDateOfHire(LocalDate.of(2010, 5, 10));

        instructor2 = new Instructor();
        instructor2.setFirstName("Bob");
        instructor2.setLastName("Brown");
        instructor2.setDateOfHire(LocalDate.of(2018, 8, 20));

        instructorRepository.save(instructor1);
        instructorRepository.save(instructor2);
    }

    @Test
    void testFindInstructorsWithMoreExperienceThan() {
        List<Instructor> experiencedInstructors = instructorRepository.findAll();
        assertFalse(experiencedInstructors.isEmpty());
    }
}
