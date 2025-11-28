package org.formation.projet_aziaka_christian.service;

import org.formation.projet_aziaka_christian.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    Account createAccount(Long clientId, Account account);

    List<Account> getAccountsByClientId(Long clientId);

    Optional<Account> findById(Long id);

    Account save(Account account);

    Account credit(Long accountId, double amount);

}
