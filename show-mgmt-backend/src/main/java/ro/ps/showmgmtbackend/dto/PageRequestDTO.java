package ro.ps.showmgmtbackend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Represents a page request DTO
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageRequestDTO {
    @NotNull(message = "Page number is requested")
    private Integer pageNumber;

    private Integer pageSize = 20;
}
