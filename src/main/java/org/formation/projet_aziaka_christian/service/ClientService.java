package org.formation.projet_aziaka_christian.service;

import org.formation.projet_aziaka_christian.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    List<Client> findAll();

    Client findById(Long id);

    Client save(Client client);

    void deleteById(Long id);
}