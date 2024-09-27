package ro.ps.showmgmtbackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ro.ps.showmgmtbackend.dto.order.OrderRequestDTO;
import ro.ps.showmgmtbackend.dto.order.OrderResponseDTO;
import ro.ps.showmgmtbackend.model.OrderEntity;

import java.util.List;

/**
 * Represents an order mapper to ResponseDTO, to RequestDTO and to Entity
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.WARN)
public interface OrderMapper {
    /**
     * Converts an OrderEntity to an OrderResponseDTO.
     *
     * @param orderEntity The OrderEntity to be converted
     * @return An OrderResponseDTO representing the converted order entity
     */
    public OrderResponseDTO orderEntityToOrderResponseDTO(OrderEntity orderEntity);

    /**
     * Converts a list of OrderEntity to a list of OrderResponseDTO.
     *
     * @param orderEntityList The list of OrderEntity to be converted
     * @return A list of OrderResponseDTO representing the converted order entity list
     */
    public List<OrderResponseDTO> orderEntityListToOrderResponseDTOList(List<OrderEntity> orderEntityList);

    /**
     * Converts an OrderRequestDTO to an OrderEntity.
     *
     * @param orderRequestDTO The OrderRequestDTO to be converted
     * @return An OrderEntity representing the converted order request DTO
     */
    public OrderEntity orderRequestDTOToOrderEntity(OrderRequestDTO orderRequestDTO);
}
