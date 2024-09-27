package ro.ps.showmgmtbackend.dto.city;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CityRequestDTO {
    private UUID cityID;
}
