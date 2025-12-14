package org.saul.ciudadelas.in.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ExecuteDistrictHabilityDTO {
    private UUID gameId;
    private Long districtId;
}
