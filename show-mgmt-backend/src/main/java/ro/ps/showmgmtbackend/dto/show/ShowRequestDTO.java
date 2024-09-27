package ro.ps.showmgmtbackend.dto.show;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Represents a show RequestDTO
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShowRequestDTO {
    private UUID showId;
    private String name;
    private Float price;
    private String location;
    private LocalDate eventDate;
    private String description;
    private Integer numberOfTicketsLeft;
}
