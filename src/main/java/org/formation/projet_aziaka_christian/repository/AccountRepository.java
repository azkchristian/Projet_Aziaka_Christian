package org.formation.projet_aziaka_christian.repository;

import org.formation.projet_aziaka_christian.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}