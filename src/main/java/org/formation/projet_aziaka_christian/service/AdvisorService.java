package org.formation.projet_aziaka_christian.service;

import org.formation.projet_aziaka_christian.model.Advisor;
import org.formation.projet_aziaka_christian.model.Client;

import java.util.List;

public interface AdvisorService {

    Advisor createAdvisor(Advisor advisor);

    List<Advisor> findAll();

    Advisor findById(Long id);
    List<Client> getClientsForAdvisor(Long advisorId);

    void deleteById(Long id);
}
