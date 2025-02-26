package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.mapping.Set;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IRegistrationRepository;
import tn.esprit.spring.repositories.ISkierRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
@Slf4j
@AllArgsConstructor
@Service
public class RegistrationServicesImpl implements  IRegistrationServices{

    private IRegistrationRepository registrationRepository;
    private ISkierRepository skierRepository;


    @Override
    public Registration addRegistrationAndAssignToSkier(Registration registration, Long numSkier) {
        Skier skier = skierRepository.findById(numSkier).orElse(null);

        if (skier == null) {
            throw new RuntimeException("Skier not found with ID: " + numSkier);
        }

        registration.setSkier(skier);

        if (skier.getRegistrations() == null) {
            skier.setRegistrations(new HashSet<>());
        }

        skier.getRegistrations().add(registration);

        // Save both entities
        registrationRepository.save(registration);
        skierRepository.save(skier);

        return registration;
    }



    //CRUD
    @Override
    public Registration updateRegistrationbyId(Long id, Registration registration) {
        if(registrationRepository.findById(id).isPresent()){
            Registration existingRegistration = registrationRepository.findById(id).get();
            existingRegistration.setNumWeek(registration.getNumWeek());
            return registrationRepository.save(existingRegistration);
        }else return null;

    }
    @Override
    public Registration addRegistration(Registration registration) {
        return registrationRepository.save(registration);
    }

    @Override
    public String deleteRegistration(Long id) {
        Registration r = registrationRepository.findById(id).orElse(null);
        registrationRepository.delete(r);
        return "registrtaion deleted";
    }

    @Override
    public List<Registration> getAllRegistrations() {
        return  (List<Registration>) registrationRepository.findAll();
    }

    @Override
    public List<Registration> getRegistrationsByWeek(int numWeek) {
        return registrationRepository.findByNumWeek(numWeek);
    }




}
