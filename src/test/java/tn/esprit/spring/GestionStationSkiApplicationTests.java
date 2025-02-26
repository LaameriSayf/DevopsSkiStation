package tn.esprit.spring;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.Registration;
import tn.esprit.spring.services.IRegistrationServices;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
	void testUpdateRegistrationById() {
		Long registrationId = 1L;
		Registration existingRegistration = new Registration();
		existingRegistration.setNumWeek(5);

		Registration updatedRegistration = new Registration();
		updatedRegistration.setNumWeek(7);

		when(registrationServices.updateRegistrationbyId(registrationId, updatedRegistration)).thenReturn(updatedRegistration);

		Registration result = registrationServices.updateRegistrationbyId(registrationId, updatedRegistration);
		assertNotNull(result);
		assertEquals(7, result.getNumWeek());
		verify(registrationServices, times(1)).updateRegistrationbyId(registrationId, updatedRegistration);
	}

	@Test
	void testAddRegistration() {
		Registration registration = new Registration();
		registration.setNumWeek(4);

		when(registrationServices.addRegistration(registration)).thenReturn(registration);

		Registration result = registrationServices.addRegistration(registration);
		assertNotNull(result);
		assertEquals(4, result.getNumWeek());
		verify(registrationServices, times(1)).addRegistration(registration);
	}

	@Test
	void testDeleteRegistration() {
		Long registrationId = 1L;
		when(registrationServices.deleteRegistration(registrationId)).thenReturn("registration deleted");

		String result = registrationServices.deleteRegistration(registrationId);
		assertEquals("registration deleted", result);
		verify(registrationServices, times(1)).deleteRegistration(registrationId);
	}

	@Test
	void testGetAllRegistrations() {
		Registration reg1 = new Registration();
		reg1.setNumWeek(3);

		Registration reg2 = new Registration();
		reg2.setNumWeek(5);

		List<Registration> registrations = Arrays.asList(reg1, reg2);

		when(registrationServices.getAllRegistrations()).thenReturn(registrations);

		List<Registration> result = registrationServices.getAllRegistrations();
		assertNotNull(result);
		assertEquals(2, result.size());
		verify(registrationServices, times(1)).getAllRegistrations();
	}

	@Test
	void testGetRegistrationsByWeek() {
		int numWeek = 5;
		Registration reg = new Registration();
		reg.setNumWeek(numWeek);

		List<Registration> registrations = Arrays.asList(reg);

		when(registrationServices.getRegistrationsByWeek(numWeek)).thenReturn(registrations);

		List<Registration> result = registrationServices.getRegistrationsByWeek(numWeek);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(5, result.get(0).getNumWeek());
		verify(registrationServices, times(1)).getRegistrationsByWeek(numWeek);
	}
}
