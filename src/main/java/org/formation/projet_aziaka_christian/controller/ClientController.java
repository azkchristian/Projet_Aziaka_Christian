package org.formation.projet_aziaka_christian.controller;

import lombok.RequiredArgsConstructor;
import org.formation.projet_aziaka_christian.model.Advisor;
import org.formation.projet_aziaka_christian.model.Client;
import org.formation.projet_aziaka_christian.service.AdvisorService;
import org.formation.projet_aziaka_christian.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;
    private final AdvisorService advisorService;

    @GetMapping
    public List<Client> getAllClients() {
        return clientService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClient(@PathVariable Long id) {
        try {
            Client client = clientService.findById(id);
            return ResponseEntity.ok(client);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping("/{clientId}/advisor/{advisorId}")
    public ResponseEntity<?> assignAdvisor(
            @PathVariable Long clientId,
            @PathVariable Long advisorId
    ) {
        try {
            Client client = clientService.findById(clientId);
            Advisor advisor = advisorService.findById(advisorId);

            if (advisor.getClients().size() >= 10) {
                return ResponseEntity.status(400)
                        .body("This advisor already manages 10 clients.");
            }

            client.setAdvisor(advisor);
            clientService.save(client);

            return ResponseEntity.ok(client);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createClient(@RequestBody Client client) {
        try {
            return ResponseEntity.ok(clientService.save(client));
        } catch (RuntimeException e) {

            if (e.getMessage().contains("Client already exists")) {
                return ResponseEntity.status(400).body(e.getMessage());
            }
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
        try {
            clientService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {

            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(404).body(e.getMessage());
            }

            if (e.getMessage().contains("has non-zero balance")) {
                return ResponseEntity.status(400).body(e.getMessage());
            }

            return ResponseEntity.status(400).body(e.getMessage());
        }
    }


}