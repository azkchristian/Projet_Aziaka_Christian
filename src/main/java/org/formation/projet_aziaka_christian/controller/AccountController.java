package org.formation.projet_aziaka_christian.controller;

import lombok.RequiredArgsConstructor;
import org.formation.projet_aziaka_christian.model.Account;
import org.formation.projet_aziaka_christian.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    // Créer un compte pour un client
    @PostMapping("/client/{clientId}")
    public ResponseEntity<Account> createAccount(
            @PathVariable Long clientId,
            @RequestBody Account account
    ) {
        return ResponseEntity.ok(accountService.createAccount(clientId, account));
    }

    // Lister les comptes d'un client
    @GetMapping("/client/{clientId}")
    public ResponseEntity<?> listAccounts(@PathVariable Long clientId) {
        try {
            return ResponseEntity.ok(accountService.getAccountsByClientId(clientId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
