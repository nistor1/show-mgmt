package ro.ps.showmgmtbackend.dto.city;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CityResponseDTO {
    private UUID cityId;
    private String name;
    private String description;
}
