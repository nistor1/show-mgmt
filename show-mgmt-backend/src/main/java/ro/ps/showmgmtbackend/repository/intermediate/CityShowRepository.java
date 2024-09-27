package ro.ps.showmgmtbackend.repository.intermediate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.ps.showmgmtbackend.model.intermediate.CityShowEntity;

import java.util.UUID;

@Repository
public interface CityShowRepository extends JpaRepository<CityShowEntity, UUID> {

}
