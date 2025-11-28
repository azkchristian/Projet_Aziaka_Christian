package org.formation.projet_aziaka_christian.mapper;

import org.formation.projet_aziaka_christian.dto.ClientDTO;
import org.formation.projet_aziaka_christian.model.Client;

public class ClientMapper {

    public static ClientDTO toDTO(Client client) {
        ClientDTO dto = new ClientDTO();
        dto.setId(client.getId());
        dto.setFirstName(client.getFirstName());
        dto.setLastName(client.getLastName());
        dto.setCity(client.getCity());
        dto.setAddress(client.getAddress());
        dto.setZipCode(client.getZipCode());
        return dto;
    }
}
