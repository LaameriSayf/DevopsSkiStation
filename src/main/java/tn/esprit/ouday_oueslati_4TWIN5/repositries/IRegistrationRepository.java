package tn.esprit.ouday_oueslati_4TWIN5.repositries;

import org.springframework.data.repository.CrudRepository;
import tn.esprit.ouday_oueslati_4TWIN5.entities.Registration;

public interface IRegistrationRepository extends CrudRepository<Registration , Long> {
}
