package ro.ps.showmgmtbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.ps.showmgmtbackend.model.ShowEntity;

import java.util.UUID;

/**
 * show class that defines Show CRUD operations
 */
@Repository
public interface ShowRepository extends JpaRepository<ShowEntity, UUID> {
    //List<ShowEntity> findAllByCity(UUID cityId);

}
