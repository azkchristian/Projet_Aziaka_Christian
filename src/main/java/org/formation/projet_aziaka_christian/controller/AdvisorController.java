package org.formation.projet_aziaka_christian.controller;

import lombok.RequiredArgsConstructor;
import org.formation.projet_aziaka_christian.dto.AdvisorDTO;
import org.formation.projet_aziaka_christian.dto.ClientDTO;
import org.formation.projet_aziaka_christian.mapper.AdvisorMapper;
import org.formation.projet_aziaka_christian.mapper.ClientMapper;
import org.formation.projet_aziaka_christian.model.Advisor;
import org.formation.projet_aziaka_christian.model.Client;
import org.formation.projet_aziaka_christian.service.AdvisorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/advisors")
public class AdvisorController {

    private final AdvisorService advisorService;

    @GetMapping("/{id}/clients")
    public ResponseEntity<?> getClients(@PathVariable Long id) {
        try {
            List<Client> clients = advisorService.  getClientsForAdvisor(id);
            List<ClientDTO> result = new ArrayList<>();
            for (Client c : clients) {
                result.add(ClientMapper.toDTO(c));
            }
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Advisor advisor) {
        Advisor saved = advisorService.createAdvisor(advisor);
        return ResponseEntity.ok(AdvisorMapper.toDTO(saved));
    }

    @GetMapping
    public ResponseEntity<?> list() {
        List<Advisor> advisors = advisorService.findAll();
        List<AdvisorDTO> result = new ArrayList<>();
        for (Advisor a : advisors) {
            result.add(AdvisorMapper.toDTO(a));
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        try {
            Advisor advisor = advisorService.findById(id);
            return ResponseEntity.ok(AdvisorMapper.toDTO(advisor));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            advisorService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
