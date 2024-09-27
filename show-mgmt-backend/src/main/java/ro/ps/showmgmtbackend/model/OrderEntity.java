package ro.ps.showmgmtbackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Represents a relation between a user and a show.
 */
@Entity
@Builder
@Table(name = "ORDER_ENTITY")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {
    /**
     * The unique identifier for the user-show relation.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID orderId;

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

    @Column(name = "number_of_tickets_to_buy")
    private Integer numberOfTicketsToBuy;

    @Column(name = "order_date")
    private LocalDate orderDate;

}
