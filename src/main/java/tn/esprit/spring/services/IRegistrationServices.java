package tn.esprit.spring.services;

import tn.esprit.spring.entities.*;

import java.util.List;

public interface IRegistrationServices {

	Registration addRegistrationAndAssignToSkier(Registration registration, Long numSkier);
	Registration assignRegistrationToCourse(Long numRegistration, Long numCourse);
	Registration addRegistrationAndAssignToSkierAndCourse(Registration registration, Long numSkieur, Long numCours);
	List<Integer> numWeeksCourseOfInstructorBySupport(Long numInstructor, Support support);
	Registration updateRegistrationbyId(Long id, Registration registration);
	Registration addRegistration(Registration registration);
	String deleteRegistration(Long id);
	List<Registration> getAllRegistrations();
	List<Registration> getRegistrationsByWeek(int numWeek);
	void deleteRegistrationsBySkierId(Long skierId);


}

