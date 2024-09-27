package ro.ps.showmgmtbackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.ps.showmgmtbackend.model.OrderEntity;
import ro.ps.showmgmtbackend.model.ShowEntity;
import ro.ps.showmgmtbackend.model.UserEntity;

import java.util.List;
import java.util.UUID;

/**
 * order class that defines Order CRUD operations
 */
@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    List<OrderEntity> findByShow(ShowEntity show);

    Page<OrderEntity> findByShow(ShowEntity show, PageRequest pageRequest);

    List<OrderEntity> findByUser(UserEntity user);

    Page<OrderEntity> findByUser(UserEntity user, PageRequest pageRequest);
}
