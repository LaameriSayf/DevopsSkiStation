package tn.esprit.ouday_oueslati_4TWIN5.repositries;

import org.springframework.data.repository.CrudRepository;
import tn.esprit.ouday_oueslati_4TWIN5.entities.Subscription;
import tn.esprit.ouday_oueslati_4TWIN5.entities.TypeSubscription;

import java.util.Set;

public interface ISubscriptionRepository extends CrudRepository<Subscription , Long> {
    Set<Subscription> findByTypeSub(TypeSubscription type);
}
