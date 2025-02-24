package tn.esprit.ouday_oueslati_4TWIN5.entities;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Entity
public class Subscription implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int numSub;
    LocalDate startDate;
    LocalDate endDate;
    float price;
    @Enumerated(EnumType.STRING)
    TypeSubscription typeSub;

}
