package tn.esprit.ouday_oueslati_4TWIN5.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.ouday_oueslati_4TWIN5.entities.*;
import tn.esprit.ouday_oueslati_4TWIN5.repositries.*;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class SkierServicesImpl implements ISkierServices{

    private final ISkierRepository skierRepository;
    private final IPisteRepository pisteRepository;
    private final ISubscriptionRepository subscriptionRepository;
    private final IRegistrationRepository registrationRepository;
    private final ICourseRepository courseRepository;


    public Skier addSkier (Skier skier){
        Subscription subscription = skier.getSubscription();
        subscriptionRepository.save(subscription);
        skier.setSubscription(subscription);
        return skierRepository.save(skier);
    }

    @Override
    public Skier updateSkier(Skier skier) {
        return skierRepository.save(skier);
    }

    @Override
    public Skier retrieveSkier(Long numSkier) {
        return skierRepository.findById(numSkier).orElse(null);
    }

    @Override
    public List<Skier> retrieveAll() {
        return (List<Skier>) skierRepository.findAll();
    }

    @Override
    public void removeSkier(Long numSkier) {
        skierRepository.deleteById(numSkier);
    }

    @Override
    public void assignSkier(Long numSkier, Long numPiste) {
        Skier skier = skierRepository.findById(numSkier).orElse(null);
        Piste piste = pisteRepository.findById(numPiste).orElse(null);
        assert piste != null;
        piste.getSkiers().add(skier);
        pisteRepository.save(piste);
    }
    @Override
    public Skier getByFirstNameAndLastName(String firstName, String lastName) {
        return skierRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public Skier getBybirthDate(LocalDate birthDate) {
        return skierRepository.findByBirthDate(birthDate);
    }
    @Override
     public Registration addRegistrationAndAssignToSkier(Registration registration, Long numSkier){
        Skier skier = skierRepository.findById(numSkier).orElse(null);
        registration.setSkier(skier);
        return registrationRepository.save(registration);
    }
    @Override
    public Skier addSkierAndAssignToCourse(Skier skier , Long numCourse){

        Registration registration = registrationRepository.findById(numCourse).orElse(null);
        Course course = courseRepository.findById(numCourse).orElse(null);
        registration.setSkier(skier);
        return skierRepository.save(skier);
    }
    @Override
    public List<Skier> retrieveSkiersBySubscriptionType(TypeSubscription typeSubscription) {
        return skierRepository.findBySubscription_TypeSub(typeSubscription);
    }
    @Scheduled(cron = "0 */1 * * * *")
    @Override
    public void getSkiersNotif() {
        // System.out.println("Bonjour !");
        log.info("Bonjour !");
        log.debug("in method : getSkiersNotif !");
        log.warn("warning !!");
        log.error("c'est une exception !");
    }
}
