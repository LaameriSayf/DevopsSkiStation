package tn.esprit.spring;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.Registration;
import tn.esprit.spring.entities.Support;
import tn.esprit.spring.services.IRegistrationServices;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GestionStationSkiApplicationTests {

	@Mock
	private IRegistrationServices registrationServices;

	@Test
	void testAddRegistrationAndAssignToSkier() {
		Registration registration = new Registration();
		Long numSkier = 1L;

		when(registrationServices.addRegistrationAndAssignToSkier(registration, numSkier)).thenReturn(registration);

		Registration result = registrationServices.addRegistrationAndAssignToSkier(registration, numSkier);
		assertNotNull(result);
		verify(registrationServices, times(1)).addRegistrationAndAssignToSkier(registration, numSkier);
	}

	@Test
	void testAssignRegistrationToCourse() {
		Long numRegistration = 1L;
		Long numCourse = 2L;
		Registration registration = new Registration();

		when(registrationServices.assignRegistrationToCourse(numRegistration, numCourse)).thenReturn(registration);

		Registration result = registrationServices.assignRegistrationToCourse(numRegistration, numCourse);
		assertNotNull(result);
		verify(registrationServices, times(1)).assignRegistrationToCourse(numRegistration, numCourse);
	}

	@Test
	void testAddRegistrationAndAssignToSkierAndCourse() {
		Registration registration = new Registration();
		Long numSkieur = 1L;
		Long numCours = 2L;

		when(registrationServices.addRegistrationAndAssignToSkierAndCourse(registration, numSkieur, numCours)).thenReturn(registration);

		Registration result = registrationServices.addRegistrationAndAssignToSkierAndCourse(registration, numSkieur, numCours);
		assertNotNull(result);
		verify(registrationServices, times(1)).addRegistrationAndAssignToSkierAndCourse(registration, numSkieur, numCours);
	}

	@Test
	void testNumWeeksCourseOfInstructorBySupport() {
		Long numInstructor = 1L;
		Support support = Support.SKI;
		List<Integer> expectedWeeks = Arrays.asList(1, 2, 3);

		when(registrationServices.numWeeksCourseOfInstructorBySupport(numInstructor, support)).thenReturn(expectedWeeks);

		List<Integer> result = registrationServices.numWeeksCourseOfInstructorBySupport(numInstructor, support);
		assertNotNull(result);
		assertEquals(3, result.size());
		assertEquals(expectedWeeks, result);
		verify(registrationServices, times(1)).numWeeksCourseOfInstructorBySupport(numInstructor, support);
	}
}
