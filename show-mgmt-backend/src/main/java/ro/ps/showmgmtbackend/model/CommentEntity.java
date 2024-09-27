package ro.ps.showmgmtbackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Represents a comment entity.
 */
@Entity
@Builder
@Table(name = "COMMENT_ENTITY")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentEntity {
    /**
     * The unique identifier for the comment entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID commentId;

    @Column(name = "comment_date")
    private LocalDate commentDate;

    @Column(name = "description")
    private String description;

    /**
     * The user associated with this relation.
     */
    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity user;

    /**
     * The show associated with this relation.
     */
    @ManyToOne
    @JoinColumn(name = "showId")
    private ShowEntity show;

}
