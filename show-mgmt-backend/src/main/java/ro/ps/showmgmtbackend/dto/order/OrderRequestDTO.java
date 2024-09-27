package ro.ps.showmgmtbackend.dto.order;

import lombok.*;
import ro.ps.showmgmtbackend.dto.city.CityRequestDTO;
import ro.ps.showmgmtbackend.dto.show.ShowRequestDTO;
import ro.ps.showmgmtbackend.dto.user.UserRequestDTO;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Represents an RequestDTO
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {

    private UUID orderId;
    private UserRequestDTO user;
    private ShowRequestDTO show;
    private CityRequestDTO city;
    private LocalDate orderDate;
    private Integer numberOfTicketsToBuy;

}
