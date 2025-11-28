package org.formation.projet_aziaka_christian.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Advisor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "advisor")
    private List<Client> clients = new ArrayList<>();

    public String getName() {
        return firstName + " " + lastName;
    }
}
