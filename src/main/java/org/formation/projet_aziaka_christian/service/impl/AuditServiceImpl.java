package org.formation.projet_aziaka_christian.service.impl;

import lombok.RequiredArgsConstructor;
import org.formation.projet_aziaka_christian.model.Account;
import org.formation.projet_aziaka_christian.repository.AccountRepository;
import org.formation.projet_aziaka_christian.service.AuditService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AccountRepository accountRepository;

    @Override
    public double getTotalBalance() {
        double total = 0;

        for (Account account : accountRepository.findAll()) {
            total += account.getBalance().doubleValue();
        }

        return total;
    }

    @Override
    public long countPositiveAccounts() {
        long count = 0;

        for (Account account : accountRepository.findAll()) {
            if (account.getBalance().doubleValue() > 0) {
                count++;
            }
        }

        return count;
    }

    @Override
    public long countNegativeAccounts() {
        long count = 0;

        for (Account account : accountRepository.findAll()) {
            if (account.getBalance().doubleValue() < 0) {
                count++;
            }
        }

        return count;
    }

    @Override
    public List<Account> getPositiveAccounts() {
        return accountRepository.findAll()
                .stream()
                .filter(a -> a.getBalance().doubleValue() > 0)
                .toList();
    }

    @Override
    public List<Account> getNegativeAccounts() {
        return accountRepository.findAll()
                .stream()
                .filter(a -> a.getBalance().doubleValue() < 0)
                .toList();
    }
}
