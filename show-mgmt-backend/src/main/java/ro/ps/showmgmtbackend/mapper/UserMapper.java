package ro.ps.showmgmtbackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ro.ps.showmgmtbackend.dto.user.UserRequestDTO;
import ro.ps.showmgmtbackend.dto.user.UserResponseDTO;
import ro.ps.showmgmtbackend.model.UserEntity;

import java.util.List;

/**
 * Represents a user mapper to ResponseDTO, to RequestDTO and to Entity
 */
@Mapper(unmappedSourcePolicy = ReportingPolicy.WARN)
public interface UserMapper {
    /**
     * Converts a UserEntity to a UserResponseDTO.
     *
     * @param userEntity The UserEntity to be converted
     * @return A UserResponseDTO representing the converted user entity
     */
    UserResponseDTO userEntityToUserResponseDTO(UserEntity userEntity);

    /**
     * Converts a list of UserEntity to a list of UserResponseDTO.
     *
     * @param userEntityList The list of UserEntity to be converted
     * @return A list of UserResponseDTO representing the converted user entity list
     */
    List<UserResponseDTO> userEntityListToUserResponseDTOList(List<UserEntity> userEntityList);

    /**
     * Converts a UserRequestDTO to a UserEntity.
     *
     * @param userRequestDTO The UserRequestDTO to be converted
     * @return A UserEntity representing the converted user request DTO
     */
    UserEntity userRequestDTOToUserEntity(UserRequestDTO userRequestDTO);
}
