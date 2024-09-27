package ro.ps.showmgmtbackend.model;

import jakarta.persistence.*;
import lombok.*;
import ro.ps.showmgmtbackend.model.intermediate.CityShowEntity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Represents a city entity.
 */
@Entity
@Builder
@Table(name = "CITY")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID cityId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "city", cascade = CascadeType.REMOVE)
    private Set<CityShowEntity> cityShowEntitySet = new HashSet<>();
}
