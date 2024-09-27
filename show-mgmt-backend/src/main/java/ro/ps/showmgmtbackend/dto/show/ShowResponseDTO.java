package ro.ps.showmgmtbackend.dto.show;

import lombok.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a show ResponseDTO
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShowResponseDTO {
    private UUID showId;
    private String name;
    private Float price;
    private String location;
    private LocalDate eventDate;
    private String description;
    private Integer numberOfTicketsLeft;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShowResponseDTO that = (ShowResponseDTO) o;
        return Objects.equals(showId, that.showId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(location, that.location) &&
                Objects.equals(eventDate, that.eventDate) &&
                Objects.equals(price, that.price) &&
                Objects.equals(description, that.description) &&
                Objects.equals(numberOfTicketsLeft, that.numberOfTicketsLeft);
    }

    @Override
    public int hashCode() {
        return Objects.hash(showId, name, location, eventDate, price, description, numberOfTicketsLeft);
    }
}
