package ro.ps.showmgmtbackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Represents a show entity.
 */
@Entity
@Builder
@Table(name = "SHOW_ENTITY")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShowEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID showId;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Float price;

    @Column(name = "location")
    private String location;

    @Column(name = "event_date")
    private LocalDate eventDate;

    @Column(name = "description")
    private String description;

    @Column(name = "number_of_tickets_left")
    private Integer numberOfTicketsLeft;

    @OneToMany(mappedBy = "show", cascade = CascadeType.REMOVE)
    private Set<OrderEntity> userShowEntitySet = new HashSet<>();

    @OneToMany(mappedBy = "show", cascade = CascadeType.REMOVE)
    private Set<CommentEntity> commentEntitySet = new HashSet<>();

}


