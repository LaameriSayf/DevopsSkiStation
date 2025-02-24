package tn.esprit.ouday_oueslati_4TWIN5.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.ouday_oueslati_4TWIN5.entities.Subscription;
import tn.esprit.ouday_oueslati_4TWIN5.entities.TypeSubscription;
import tn.esprit.ouday_oueslati_4TWIN5.services.ISubscriptionServices;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Tag(name = "Gestion Subscription")
@RequiredArgsConstructor
@RequestMapping("subscription")
@RestController
public class SubscriptionRestController {

    private final ISubscriptionServices subscriptionServices;
    @Operation(description = "sauvgarder Subscription")
    @PostMapping("/add")
    public Subscription saveSubscription(Subscription subscription){
        return subscriptionServices.addSubscription(subscription);
    }
    @Operation(description = "mise a jour Subscription")
    @PutMapping("/update")
    public Subscription updateSubscription(@RequestBody Subscription subscription){
        return subscriptionServices.updateSubscription(subscription);
    }
    @Operation(description = "récupérer Subscription avec numSub")
    @GetMapping("/get/{numSub}")
    public Subscription getSubscription(@PathVariable Long numSub){

        return subscriptionServices.retrieveSubscription(numSub);
    }
    @Operation(description = "récupérer Subscription avec TypeSubscription")
    @GetMapping("/byType")
    public Set<Subscription> getSubscriptionsByType(@RequestParam TypeSubscription type) {
        return subscriptionServices.getSubscriptionByType(type);
    }
}
