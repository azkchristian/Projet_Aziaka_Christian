package org.formation.projet_aziaka_christian.controller;

import lombok.RequiredArgsConstructor;
import org.formation.projet_aziaka_christian.model.Advisor;
import org.formation.projet_aziaka_christian.service.AdvisorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/advisors")
public class AdvisorController {

    private final AdvisorService advisorService;
    @GetMapping("/{id}/clients")
    public ResponseEntity<?> getClients(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(advisorService.getClientsForAdvisor(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Advisor advisor) {
        return ResponseEntity.ok(advisorService.createAdvisor(advisor));
    }

    @GetMapping
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(advisorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(advisorService.findById(id));
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
