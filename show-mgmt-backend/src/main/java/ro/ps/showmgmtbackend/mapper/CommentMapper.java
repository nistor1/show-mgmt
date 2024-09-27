package ro.ps.showmgmtbackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ro.ps.showmgmtbackend.dto.comment.CommentRequestDTO;
import ro.ps.showmgmtbackend.dto.comment.CommentResponseDTO;
import ro.ps.showmgmtbackend.model.CommentEntity;

import java.util.List;

/**
 * Represents a comment mapper to ResponseDTO, to RequestDTO and to Entity
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.WARN)
public interface CommentMapper {
    /**
     * Converts a CommentEntity to a CommentResponseDTO.
     *
     * @param commentEntity The CommentEntity to be converted
     * @return A CommentResponseDTO representing the converted comment entity
     */
    CommentResponseDTO commentEntityToCommentResponseDTO(CommentEntity commentEntity);

    /**
     * Converts a list of CommentEntity to a list of CommentResponseDTO.
     *
     * @param commentEntityList The list of CommentEntity to be converted
     * @return A list of CommentResponseDTO representing the converted comment entity list
     */
    List<CommentResponseDTO> commentEntityListToCommentResponseDTOList(List<CommentEntity> commentEntityList);

    /**
     * Converts a CommentRequestDTO to a CommentEntity.
     *
     * @param commentRequestDTO The CommentRequestDTO to be converted
     * @return A CommentEntity representing the converted comment request DTO
     */
    CommentEntity commentRequestDTOToCommentEntity(CommentRequestDTO commentRequestDTO);
}
