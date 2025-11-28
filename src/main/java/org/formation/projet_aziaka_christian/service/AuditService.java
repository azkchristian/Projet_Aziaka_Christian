package org.formation.projet_aziaka_christian.service;

import org.formation.projet_aziaka_christian.model.Account;
import java.util.List;

public interface AuditService {

    double getTotalBalance();

    long countPositiveAccounts();

    long countNegativeAccounts();

    List<Account> getPositiveAccounts();

    List<Account> getNegativeAccounts();
}
