package org.formation.projet_aziaka_christian.dto;

import lombok.Data;

@Data
public class ClientDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String city;
    private String address;
    private String zipCode;
}
