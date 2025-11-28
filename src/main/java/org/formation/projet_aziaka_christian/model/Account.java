package org.formation.projet_aziaka_christian.model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountNumber;

    private BigDecimal balance;
    private LocalDate openingDate;

    @Enumerated(EnumType.STRING)
    private AccountType type;

    private BigDecimal overdraftLimit;

    private BigDecimal interestRate;

    @ManyToOne
    @JsonIgnore
    private Client client;

}