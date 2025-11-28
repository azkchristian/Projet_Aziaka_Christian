package org.formation.projet_aziaka_christian.mapper;

import org.formation.projet_aziaka_christian.dto.AdvisorDTO;
import org.formation.projet_aziaka_christian.model.Advisor;

public class AdvisorMapper {

    public static AdvisorDTO toDTO(Advisor advisor) {
        AdvisorDTO dto = new AdvisorDTO();
        dto.setId(advisor.getId());
        dto.setName(advisor.getName());
        return dto;
    }
}
