package org.formation.projet_aziaka_christian.controller;

import lombok.RequiredArgsConstructor;
import org.formation.projet_aziaka_christian.dto.AdvisorDTO;
import org.formation.projet_aziaka_christian.dto.ClientDTO;
import org.formation.projet_aziaka_christian.mapper.AdvisorMapper;
import org.formation.projet_aziaka_christian.mapper.ClientMapper;
import org.formation.projet_aziaka_christian.model.Account;
import org.formation.projet_aziaka_christian.model.Advisor;
import org.formation.projet_aziaka_christian.model.Client;
import org.formation.projet_aziaka_christian.service.AccountService;
import org.formation.projet_aziaka_christian.service.AdvisorService;
import org.formation.projet_aziaka_christian.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/advisors")
public class AdvisorController {

    private final AdvisorService advisorService;
    private final AccountService accountService;
    private final ClientService clientService;

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
    @PostMapping("/{advisorId}/transfer")
    public ResponseEntity<?> advisorTransfer(
            @PathVariable Long advisorId,
            @RequestParam Long fromAccountId,
            @RequestParam Long toAccountId,
            @RequestParam double amount
    ) {
        try {
                return ResponseEntity.ok(accountService.advisorTransfer(advisorId, fromAccountId, toAccountId, amount));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Advisor advisor) {
        Advisor saved = advisorService.createAdvisor(advisor);
        return ResponseEntity.ok(AdvisorMapper.toDTO(saved));
    }

    @PutMapping("/{advisorId}/clients/{clientId}")
    public ResponseEntity<?> updateClient(
            @PathVariable Long advisorId,
            @PathVariable Long clientId,
            @RequestBody Client updated
    ) {
        try {
            Client client = clientService.findById(clientId);

            if (client.getAdvisor() == null || !client.getAdvisor().getId().equals(advisorId)) {
                return ResponseEntity.status(403).body("Advisor not allowed");
            }

            client.setFirstName(updated.getFirstName());
            client.setLastName(updated.getLastName());
            client.setCity(updated.getCity());
            client.setAddress(updated.getAddress());
            client.setZipCode(updated.getZipCode());

            Client saved = clientService.save(client);
            return ResponseEntity.ok(saved);

        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
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

    @DeleteMapping("/{advisorId}/clients/{clientId}")
    public ResponseEntity<?> advisorDeleteClient(
            @PathVariable Long advisorId,
            @PathVariable Long clientId
    ) {
        try {
            Client client = clientService.findById(clientId);

            if (client.getAdvisor() == null || !client.getAdvisor().getId().equals(advisorId)) {
                return ResponseEntity.status(403).body("Advisor not allowed");
            }

            boolean hasNonZeroBalance = false;

            for (Account a : client.getAccounts()) {
                double bal = a.getBalance().doubleValue();
                if (bal != 0) {
                    hasNonZeroBalance = true;
                    break;
                }
            }

            if (hasNonZeroBalance) {
                return ResponseEntity.status(400).body("please empty client account then delete it");
            }

            clientService.deleteById(clientId);
            return ResponseEntity.noContent().build();

        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
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
