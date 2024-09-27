package ro.ps.showmgmtbackend.service.show;

import ro.ps.showmgmtbackend.dto.CollectionResponseDTO;
import ro.ps.showmgmtbackend.dto.PageRequestDTO;
import ro.ps.showmgmtbackend.dto.show.ShowRequestDTO;
import ro.ps.showmgmtbackend.dto.show.ShowResponseDTO;

import java.util.List;
import java.util.UUID;

/**
 * Service interface that defines Show operations
 * This includes:
 * - Finding a show by id
 * - Deleting a show by id
 * - Deleting all shows
 * - Finding all shows
 * - Saving a show
 * - Updating a show
 */
public interface ShowService {

    /**
     * Finds a show by its ID.
     *
     * @param showId The ID of the show to find
     * @return A ShowResponseDTO representing the found show
     */
    ShowResponseDTO findById(UUID showId);

    /**
     * Deletes a show by its ID.
     *
     * @param showId The ID of the show to delete
     */
    void deleteById(UUID showId);

    /**
     * Updates a show with new data.
     *
     * @param showRequestDTO The ShowRequestDTO containing the updated data
     * @return A ShowResponseDTO representing the updated show
     */
    ShowResponseDTO update(ShowRequestDTO showRequestDTO);

    /**
     * Retrieves all shows.
     *
     * @return A list of all shows
     */
    List<ShowResponseDTO> findAll();

    /**
     * Retrieves a paged list of shows.
     *
     * @param page The PageRequestDTO containing pagination information
     * @return A CollectionResponseDTO containing a page of shows
     */
    CollectionResponseDTO<ShowResponseDTO> findAllPaged(PageRequestDTO page);

    /**
     * Deletes all shows.
     */
    void deleteAll();

    /**
     * Retrieves all shows sorted by a given criterion.
     *
     * @param sortBy The criterion by which to sort the shows
     * @return A list of shows sorted by the given criterion
     */
    List<ShowResponseDTO> findAllSorted(String sortBy);

    /**
     * Finds all shows in a given city.
     *
     * @param cityId The ID of the city to search for shows in
     * @return A list of shows in the specified city
     */
    // List<ShowResponseDTO> findAllByCity(UUID cityId);

    /**
     * Saves a new show.
     *
     * @param showRequestDTO The ShowRequestDTO containing data for the new show
     * @return A ShowResponseDTO representing the saved show
     */
    ShowResponseDTO save(ShowRequestDTO showRequestDTO);

}
