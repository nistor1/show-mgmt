package ro.ps.showmgmtbackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ro.ps.showmgmtbackend.dto.city.CityRequestDTO;
import ro.ps.showmgmtbackend.dto.city.CityResponseDTO;
import ro.ps.showmgmtbackend.model.CityEntity;

@Mapper(unmappedSourcePolicy = ReportingPolicy.WARN)
public interface CityMapper {
    /**
     * Converts a CityEntity to a CityResponseDTO.
     *
     * @param cityEntity The CityEntity to be converted
     * @return A CityResponseDTO representing the converted city entity
     */
    CityResponseDTO cityEntityToCityResponseDTO(CityEntity cityEntity);

    /**
     * Converts a CityRequestDTO to a CityEntity.
     *
     * @param cityRequestDTO The CityRequestDTO to be converted
     * @return A CityEntity representing the converted city request DTO
     */
    CityEntity cityRequestDTOToCityEntity(CityRequestDTO cityRequestDTO);
}