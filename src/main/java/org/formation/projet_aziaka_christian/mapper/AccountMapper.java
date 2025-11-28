package org.formation.projet_aziaka_christian.mapper;

import org.formation.projet_aziaka_christian.dto.AccountDTO;
import org.formation.projet_aziaka_christian.model.Account;

public class AccountMapper {

    public static AccountDTO toDTO(Account account) {
        AccountDTO dto = new AccountDTO();
        dto.setId(account.getId());
        dto.setAccountNumber(account.getAccountNumber());
        dto.setBalance(account.getBalance().doubleValue());
        dto.setType(account.getType().name());
        return dto;
    }
}
