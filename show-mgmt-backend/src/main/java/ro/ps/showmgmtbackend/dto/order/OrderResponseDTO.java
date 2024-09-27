package ro.ps.showmgmtbackend.dto.order;

import lombok.*;
import ro.ps.showmgmtbackend.dto.city.CityResponseDTO;
import ro.ps.showmgmtbackend.dto.show.ShowResponseDTO;
import ro.ps.showmgmtbackend.dto.user.UserResponseDTO;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Represents an order ResponseDTO
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {

    private UUID orderId;
    private UserResponseDTO user;
    private ShowResponseDTO show;
    private CityResponseDTO city;
    private LocalDate orderDate;
    private Integer numberOfTicketsToBuy;
}
