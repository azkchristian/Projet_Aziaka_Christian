    package org.formation.projet_aziaka_christian.service.impl;

    import lombok.RequiredArgsConstructor;
    import org.formation.projet_aziaka_christian.model.Account;
    import org.formation.projet_aziaka_christian.model.AccountType;
    import org.formation.projet_aziaka_christian.model.Client;
    import org.formation.projet_aziaka_christian.repository.AccountRepository;
    import org.formation.projet_aziaka_christian.repository.ClientRepository;
    import org.formation.projet_aziaka_christian.service.AccountService;
    import org.springframework.stereotype.Service;

    import java.time.LocalDate;
    import java.util.List;
    import java.util.Optional;

    @Service
    @RequiredArgsConstructor
    public class AccountServiceImpl implements AccountService {

        private final AccountRepository accountRepository;
        private final ClientRepository clientRepository;

        public Account findByAccountNumber(String accountNumber) {
            return accountRepository.findByAccountNumber(accountNumber);
        }

        @Override
        public Account createAccount(Long clientId, Account account) {

            Account existing = accountRepository.findByAccountNumber(account.getAccountNumber());
            if (existing != null) {
                throw new RuntimeException("Account number already exists: " + account.getAccountNumber());
            }

            Client client = clientRepository.findById(clientId)
                    .orElseThrow(() -> new RuntimeException("Client not found with id: " + clientId));


            if (account.getType().name().equals("CURRENT")) {
                account.setOverdraftLimit(java.math.BigDecimal.valueOf(1000));
                account.setInterestRate(null);
            }

            if (account.getType().name().equals("SAVINGS")) {
                account.setOverdraftLimit(null);
                account.setInterestRate(null);
            }

            account.setClient(client);
            account.setOpeningDate(LocalDate.now());

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
        public Account debit(Long accountId, double amount) {

            Account account = accountRepository.findById(accountId)
                    .orElseThrow(() -> new RuntimeException("Account not found with id: " + accountId));

            double balance = account.getBalance().doubleValue();
            double newBalance = balance - amount;

            if (account.getType() == AccountType.CURRENT) {

                double overdraft = 0;

                if (account.getOverdraftLimit() != null) {
                    overdraft = account.getOverdraftLimit().doubleValue();
                }

                if (newBalance < -overdraft) {
                    throw new RuntimeException("Overdraft limit exceeded for account: " + accountId);
                }

                account.setBalance(java.math.BigDecimal.valueOf(newBalance));
                return accountRepository.save(account);
            }


            else if (account.getType() == AccountType.SAVINGS) {

                if (newBalance < 0) {
                    throw new RuntimeException("Savings accounts cannot go negative: " + accountId);
                }

                account.setBalance(java.math.BigDecimal.valueOf(newBalance));
                return accountRepository.save(account);
            }

            else {
                throw new RuntimeException("Unknown account type for account: " + accountId);
            }
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

        @Override
        public Account transfer(Long transfererId, Long receiverId, double amount) {

            if (transfererId.equals(receiverId)) {
                throw new RuntimeException("Cannot transfer balance to the same account");
            }

            Account transferer = accountRepository.findById(transfererId)
                    .orElseThrow(() -> new RuntimeException("Account not found with id: " + transfererId));

            Account receiver = accountRepository.findById(receiverId)
                    .orElseThrow(() -> new RuntimeException("Account not found with id: " + receiverId));

            double sourceBalance = transferer.getBalance().doubleValue();
            double newBalance = sourceBalance - amount;

            if (transferer.getType().name().equals("CURRENT")) {

                double overdraft = transferer.getOverdraftLimit().doubleValue();

                if (newBalance < -overdraft) {
                    throw new RuntimeException("Overdraft limit exceeded for account: " + transfererId);
                }

                transferer.setBalance(java.math.BigDecimal.valueOf(newBalance));
            } else if (transferer.getType().name().equals("SAVINGS")) {

                if (newBalance < 0) {
                    throw new RuntimeException("Savings accounts cannot go negative: " + transfererId);
                }

                transferer.setBalance(java.math.BigDecimal.valueOf(newBalance));
            }

            receiver.setBalance(receiver.getBalance().add(java.math.BigDecimal.valueOf(amount)));

            accountRepository.save(transferer);
            return accountRepository.save(receiver);
        }
    }
