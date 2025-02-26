package tn.esprit.spring.services;

import tn.esprit.spring.entities.*;

import java.util.List;

public interface IRegistrationServices {

	Registration addRegistrationAndAssignToSkier(Registration registration, Long numSkier);
	Registration updateRegistrationbyId(Long id, Registration registration);
	Registration addRegistration(Registration registration);
	String deleteRegistration(Long id);
	List<Registration> getAllRegistrations();
	List<Registration> getRegistrationsByWeek(int numWeek);


}

