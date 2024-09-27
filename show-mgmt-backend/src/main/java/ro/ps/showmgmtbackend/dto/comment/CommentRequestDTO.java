package ro.ps.showmgmtbackend.dto.comment;

import lombok.*;
import ro.ps.showmgmtbackend.dto.show.ShowRequestDTO;
import ro.ps.showmgmtbackend.dto.user.UserRequestDTO;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Represents a comment RequestDTO
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDTO {
    private UUID commentId;
    private UserRequestDTO user;
    private ShowRequestDTO show;
    private String description;
    private LocalDate commentDate;

}
