package ro.ps.showmgmtbackend.dto.comment;

import lombok.*;
import ro.ps.showmgmtbackend.dto.show.ShowResponseDTO;
import ro.ps.showmgmtbackend.dto.user.UserResponseDTO;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Represents a comment ResponseDTO
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDTO {
    private UUID commentId;
    private UserResponseDTO user;
    private ShowResponseDTO show;
    private String description;
    private LocalDate commentDate;
}
