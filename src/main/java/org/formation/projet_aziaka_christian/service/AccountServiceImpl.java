package org.formation.projet_aziaka_christian.service;

import lombok.RequiredArgsConstructor;
import org.formation.projet_aziaka_christian.model.Account;
import org.formation.projet_aziaka_christian.model.Client;
import org.formation.projet_aziaka_christian.repository.AccountRepository;
import org.formation.projet_aziaka_christian.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    @Override
    public Account createAccount(Long clientId, Account account) {

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        account.setClient(client);
        account.setOpeningDate(LocalDate.now());
        account.setBalance(account.getBalance());
        return accountRepository.save(account);
    }

    @Override
    public Account credit(Long accountId, double amount) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("No accoutn with ID: " + accountId));

        account.setBalance(account.getBalance().add(java.math.BigDecimal.valueOf(amount)));

        return accountRepository.save(account);
    }


    @Override
    public List<Account> getAccountsByClientId(Long clientId) {

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("No client with ID:" + clientId)); //erreurr 500, fix
        return client.getAccounts();
    }


    @Override
    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }
}
