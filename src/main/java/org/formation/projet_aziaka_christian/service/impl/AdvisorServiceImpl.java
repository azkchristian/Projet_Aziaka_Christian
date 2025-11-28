package org.formation.projet_aziaka_christian.service.impl;

import lombok.RequiredArgsConstructor;
import org.formation.projet_aziaka_christian.model.Advisor;
import org.formation.projet_aziaka_christian.model.Client;
import org.formation.projet_aziaka_christian.repository.AdvisorRepository;
import org.formation.projet_aziaka_christian.service.AdvisorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvisorServiceImpl implements AdvisorService {

    private final AdvisorRepository advisorRepository;

    @Override
    public Advisor createAdvisor(Advisor advisor) {
        return advisorRepository.save(advisor);
    }

    @Override
    public List<Advisor> findAll() {
        return advisorRepository.findAll();
    }

    @Override
    public Advisor findById(Long id) {
        return advisorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Advisor not found with id " + id));
    }

    @Override
    public List<Client> getClientsForAdvisor(Long advisorId) {

        Advisor advisor = advisorRepository.findById(advisorId)
                .orElseThrow(() -> new RuntimeException("Advisor not found with id: " + advisorId));

        return advisor.getClients();
    }



    @Override
    public void deleteById(Long id) {
        boolean exists = advisorRepository.existsById(id);
        if (!exists) {
            throw new RuntimeException("Advisor not found with id " + id);
        }
        advisorRepository.deleteById(id);
    }
}
