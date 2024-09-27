package ro.ps.showmgmtbackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.ps.showmgmtbackend.model.CommentEntity;
import ro.ps.showmgmtbackend.model.ShowEntity;

import java.util.List;
import java.util.UUID;

/**
 * comment class that defines Comment CRUD operations
 */
@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, UUID> {
    /**
     * Finds comments associated with a specific show.
     *
     * @param show The show entity to filter comments by
     * @return A list of comments associated with the specified show
     */
    List<CommentEntity> findByShow(ShowEntity show);

    /**
     * Finds comments associated with a specific show in a paged manner.
     *
     * @param show        The show entity to filter comments by
     * @param pageRequest The PageRequest containing pagination information
     * @return A Page containing a page of comments associated with the specified show
     */
    Page<CommentEntity> findByShow(ShowEntity show, PageRequest pageRequest);
}
