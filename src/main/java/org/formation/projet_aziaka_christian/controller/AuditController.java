package org.formation.projet_aziaka_christian.controller;

import lombok.RequiredArgsConstructor;
import org.formation.projet_aziaka_christian.service.AuditService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(auditService.getPositiveAccounts());
    }

    @GetMapping("/negative")
    public ResponseEntity<?> getNegativeAccounts() {
        return ResponseEntity.ok(auditService.getNegativeAccounts());
    }
}
