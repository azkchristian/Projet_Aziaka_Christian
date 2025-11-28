package org.formation.projet_aziaka_christian.repository;

import org.formation.projet_aziaka_christian.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByFirstNameAndLastName(String firstName, String lastName);
}