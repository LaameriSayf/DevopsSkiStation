package tn.esprit.ouday_oueslati_4TWIN5.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.ouday_oueslati_4TWIN5.entities.Course;
import tn.esprit.ouday_oueslati_4TWIN5.entities.Registration;
import tn.esprit.ouday_oueslati_4TWIN5.repositries.ICourseRepository;
import tn.esprit.ouday_oueslati_4TWIN5.repositries.IRegistrationRepository;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RegistrationServicesImpl implements IRegistrationServices{

    private final IRegistrationRepository registrationRepository;
    private final ICourseRepository courseRepository;

    public  Registration addRegistration(Registration registration){
        return registrationRepository.save(registration);
    }

    @Override
    public Registration updateRegistration(Registration registration) {
        return registrationRepository.save(registration);
    }

    @Override
    public Registration retrieveRegistration(Long numRegistration) {
        return registrationRepository.findById(numRegistration).orElse(null);
    }

    @Override
    public List<Registration> retrieveAll() {
        return (List<Registration>) registrationRepository.findAll();
    }


    public void removeRegistration(Long numRegistration) {
        registrationRepository.deleteById(numRegistration);
    }

    @Override
    public  Registration addRegistrationAndAssignToCourse( Registration registration,  Long numCourse){
        Course course = courseRepository.findById(numCourse).orElse(null);
        registration.setCourse(course);
        return registrationRepository.save(registration);
    }
}
