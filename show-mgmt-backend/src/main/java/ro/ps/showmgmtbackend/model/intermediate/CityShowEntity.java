package ro.ps.showmgmtbackend.model.intermediate;

import jakarta.persistence.*;
import lombok.*;
import ro.ps.showmgmtbackend.model.CityEntity;
import ro.ps.showmgmtbackend.model.ShowEntity;

import java.util.UUID;

/**
 * Represents a relation between a city and a show.
 */
@Entity
@Builder
@Table(name = "CITY_SHOW_ENTITY")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CityShowEntity {
    /**
     * The unique identifier for the city-show relation.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID cityShowId;

    /**
     * The show associated with this relation.
     */
    @ManyToOne
    @JoinColumn(name = "showId")
    private ShowEntity show;

    /**
     * The city associated with this relation.
     */
    @ManyToOne
    @JoinColumn(name = "cityId")
    private CityEntity city;

}