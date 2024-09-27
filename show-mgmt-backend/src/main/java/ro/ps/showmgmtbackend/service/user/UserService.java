package ro.ps.showmgmtbackend.service.user;

import ro.ps.showmgmtbackend.dto.CollectionResponseDTO;
import ro.ps.showmgmtbackend.dto.PageRequestDTO;
import ro.ps.showmgmtbackend.dto.user.UserRequestDTO;
import ro.ps.showmgmtbackend.dto.user.UserResponseDTO;

import java.util.List;
import java.util.UUID;

/**
 * Service interface that defines User operations
 * This includes:
 * - Finding a user by id
 * - Deleting a user by id
 * - Deleting all users
 * - Finding all users
 * - Saving a user
 * - Updating a user
 */
public interface UserService {
    /**
     * Method that returns a user by id
     *
     * @param userId - reference id
     * @return user with requested id
     */
    UserResponseDTO findById(UUID userId);

    /**
     * Method that deletes a user by id
     *
     * @param userId - reference id
     */
    void deleteById(UUID userId);

    /**
     * Method that updates a user by id
     *
     * @param userRequestDTO - reference user
     * @return updated user
     */
    UserResponseDTO update(UserRequestDTO userRequestDTO);

    /**
     * Method that returns all users
     *
     * @return all users
     */
    List<UserResponseDTO> findAll();

    /**
     * Method that returns all users paged
     *
     * @param page - reference page
     * @return all users paged
     */
    CollectionResponseDTO<UserResponseDTO> findAllPaged(PageRequestDTO page);

    /**
     * Method that deletes all users
     */
    void deleteAll();

    List<UserResponseDTO> findAllSorted(String sortBy);

    /**
     * Method that returns all users by email
     *
     * @param email - reference email
     * @return user with requested email
     */
    UserResponseDTO findByEmail(String email);

    /**
     * Method that saves a user
     *
     * @param userRequestDTO - reference user
     * @return saved user
     */
    UserResponseDTO save(UserRequestDTO userRequestDTO);

    /**
     * Method that returns all users by email
     *
     * @param searchBy - reference searchBy
     * @param page     - reference page
     * @return user with requested email
     */
    CollectionResponseDTO<UserResponseDTO> findByIdPaged(String searchBy, PageRequestDTO page);


}
