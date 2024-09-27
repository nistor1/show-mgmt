package ro.ps.showmgmtbackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.ps.showmgmtbackend.model.UserEntity;

import java.util.Optional;
import java.util.UUID;

/**
 * user class that defines User CRUD operations
 */

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    /**
     * Finds a user by email.
     *
     * @param email The email address of the user to find
     * @return An Optional containing the user entity if found, otherwise empty
     */
    Optional<UserEntity> findByEmail(String email);

    /**
     * Finds all users matching a search criteria in a paged manner.
     *
     * @param page     The Pageable containing pagination information
     * @param searchBy The search criteria to filter users by
     * @return A Page containing a page of users matching the search criteria
     */
    @Query(
            nativeQuery = true,
            value = "SELECT u.* FROM USER u WHERE u.email LIKE :searchBy"
    )
    Page<UserEntity> findAll(Pageable page, @Param("searchBy") String searchBy);
}
