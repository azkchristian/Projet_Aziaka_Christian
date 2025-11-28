package org.formation.projet_aziaka_christian.dto;

import lombok.Data;

@Data
public class AccountDTO {

    private Long id;
    private String accountNumber;
    private double balance;
    private String type;
}
