package org.formation.projet_aziaka_christian.controller;

import lombok.RequiredArgsConstructor;
import org.formation.projet_aziaka_christian.dto.AccountDTO;
import org.formation.projet_aziaka_christian.mapper.AccountMapper;
import org.formation.projet_aziaka_christian.model.Account;
import org.formation.projet_aziaka_christian.service.AuditService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/audit")
public class AuditController {

    private final AuditService auditService;

    @GetMapping("/total")
    public ResponseEntity<?> getTotalBalance() {
        return ResponseEntity.ok(auditService.getTotalBalance());
    }

    @GetMapping("/positive/count")
    public ResponseEntity<?> getPositiveCount() {
        return ResponseEntity.ok(auditService.countPositiveAccounts());
    }

    @GetMapping("/negative/count")
    public ResponseEntity<?> getNegativeCount() {
        return ResponseEntity.ok(auditService.countNegativeAccounts());
    }

    @GetMapping("/positive")
    public ResponseEntity<?> getPositiveAccounts() {
        List<Account> accounts = auditService.getPositiveAccounts();
        List<AccountDTO> result = new ArrayList<>();
        for (Account a : accounts) {
            result.add(AccountMapper.toDTO(a));
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/negative")
    public ResponseEntity<?> getNegativeAccounts() {
        List<Account> accounts = auditService.getNegativeAccounts();
        List<AccountDTO> result = new ArrayList<>();
        for (Account a : accounts) {
            result.add(AccountMapper.toDTO(a));
        }
        return ResponseEntity.ok(result);
    }
}
