package tn.esprit.ouday_oueslati_4TWIN5.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Entity

public class Skier implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idSkier;
    String firstName;
    String lastName;
    LocalDate birthDate;
    String city;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    Subscription subscription;
    @OneToMany(mappedBy = "skier", fetch = FetchType.EAGER)
    Set<Registration> registrations;
    @ManyToMany(mappedBy = "skiers")
    Set<Piste> pistes;
}
