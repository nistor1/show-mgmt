package ro.ps.showmgmtbackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ro.ps.showmgmtbackend.dto.show.ShowRequestDTO;
import ro.ps.showmgmtbackend.dto.show.ShowResponseDTO;
import ro.ps.showmgmtbackend.model.ShowEntity;

import java.util.List;

/**
 * Represents a show mapper to ResponseDTO, to RequestDTO and to Entity
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.WARN)
public interface ShowMapper {

    /**
     * Converts a ShowEntity to a ShowResponseDTO.
     *
     * @param showEntity The ShowEntity to be converted
     * @return A ShowResponseDTO representing the converted show entity
     */
    ShowResponseDTO showEntityToShowResponseDTO(ShowEntity showEntity);

    /**
     * Converts a list of ShowEntity to a list of ShowResponseDTO.
     *
     * @param showEntityList The list of ShowEntity to be converted
     * @return A list of ShowResponseDTO representing the converted show entity list
     */
    List<ShowResponseDTO> showEntityListToShowResponseDTOList(List<ShowEntity> showEntityList);

    /**
     * Converts a ShowRequestDTO to a ShowEntity.
     *
     * @param showRequestDTO The ShowRequestDTO to be converted
     * @return A ShowEntity representing the converted show request DTO
     */
    ShowEntity showRequestDTOToShowEntity(ShowRequestDTO showRequestDTO);


}
