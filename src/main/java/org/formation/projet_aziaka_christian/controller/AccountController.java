package org.formation.projet_aziaka_christian.controller;

import lombok.RequiredArgsConstructor;
import org.formation.projet_aziaka_christian.dto.AccountDTO;
import org.formation.projet_aziaka_christian.mapper.AccountMapper;
import org.formation.projet_aziaka_christian.model.Account;
import org.formation.projet_aziaka_christian.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
            Account created = accountService.createAccount(clientId, account);
            return ResponseEntity.ok(AccountMapper.toDTO(created));
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
            List<AccountDTO> result = new ArrayList<>();
            for (Account a : accounts) {
                result.add(AccountMapper.toDTO(a));
            }
            return ResponseEntity.ok(result);
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
            Account updated = accountService.credit(accountId, amount);
            return ResponseEntity.ok(AccountMapper.toDTO(updated));
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
            Account updated = accountService.debit(accountId, amount);
            return ResponseEntity.ok(AccountMapper.toDTO(updated));
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
            Account receiver = accountService.transfer(fromId, toId, amount);
            return ResponseEntity.ok(AccountMapper.toDTO(receiver));
        } catch (RuntimeException e) {

            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(404).body(e.getMessage());
            }

            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
