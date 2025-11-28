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

    @PostMapping("/client/{clientId}")
    public ResponseEntity<?> createAccount(
            @PathVariable Long clientId,
            @RequestBody Account account
    ) {
        try {
            return ResponseEntity.ok(accountService.createAccount(clientId, account));
        } catch (RuntimeException e) {

            if (e.getMessage().contains("Client not found")) {
                return ResponseEntity.status(404).body(e.getMessage());
            }

            if (e.getMessage().contains("Account number already exists")) {
                return ResponseEntity.status(400).body(e.getMessage());
            }

            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<?> listAccounts(@PathVariable Long clientId) {
        try {
            List<Account> accounts = accountService.getAccountsByClientId(clientId);
            return ResponseEntity.ok(accounts);
        } catch (RuntimeException e) {

            if (e.getMessage().contains("Client not found")) {
                return ResponseEntity.status(404).body(e.getMessage());
            }

            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping("/{accountId}/credit")
    public ResponseEntity<?> credit(
            @PathVariable Long accountId,
            @RequestParam double amount
    ) {
        try {
            return ResponseEntity.ok(accountService.credit(accountId, amount));
        } catch (RuntimeException e) {

            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(404).body(e.getMessage());
            }

            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping("/{accountId}/debit")
    public ResponseEntity<?> debit(
            @PathVariable Long accountId,
            @RequestParam double amount
    ) {
        try {
            return ResponseEntity.ok(accountService.debit(accountId, amount));
        } catch (RuntimeException e) {

            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(404).body(e.getMessage());
            }

            return ResponseEntity.status(400).body(e.getMessage());
        }
    }


    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(
            @RequestParam Long fromId,
            @RequestParam Long toId,
            @RequestParam double amount
    ) {
        try {
            return ResponseEntity.ok(accountService.transfer(fromId, toId, amount));
        } catch (RuntimeException e) {

            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(404).body(e.getMessage());
            }

            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
