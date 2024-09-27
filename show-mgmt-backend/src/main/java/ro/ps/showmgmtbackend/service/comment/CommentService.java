package ro.ps.showmgmtbackend.service.comment;

import ro.ps.showmgmtbackend.dto.CollectionResponseDTO;
import ro.ps.showmgmtbackend.dto.PageRequestDTO;
import ro.ps.showmgmtbackend.dto.comment.CommentRequestDTO;
import ro.ps.showmgmtbackend.dto.comment.CommentResponseDTO;
import ro.ps.showmgmtbackend.dto.show.ShowRequestDTO;

import java.util.List;
import java.util.UUID;

/**
 * service class that defines Comment CRUD operations
 * This includes:
 * - Finding a comment by id
 * - Deleting a comment by id
 * - Deleting all comments
 * - Updating a comment by id
 * - Finding comments From Show
 * - Finding all comments
 * - Saving a comment
 */
public interface CommentService {

    /**
     * Finds a comment by its ID.
     *
     * @param commentId The ID of the comment to find
     * @return A CommentResponseDTO representing the found comment
     */
    CommentResponseDTO findById(UUID commentId);

    /**
     * Deletes a comment by its ID.
     *
     * @param commentId The ID of the comment to delete
     */
    void deleteById(UUID commentId);

    /**
     * Deletes all comments.
     */
    void deleteAll();

    /**
     * Retrieves all comments.
     *
     * @return A list of all comments
     */
    List<CommentResponseDTO> findAll();

    /**
     * Retrieves a paged list of comments.
     *
     * @param page The PageRequestDTO containing pagination information
     * @return A CollectionResponseDTO containing a page of comments
     */
    CollectionResponseDTO<CommentResponseDTO> findAllPaged(PageRequestDTO page);

    /**
     * Finds comments by show.
     *
     * @param showRequestDTO The ShowRequestDTO representing the show to search for comments
     * @return A list of comments associated with the specified show
     */
    List<CommentResponseDTO> findByShow(ShowRequestDTO showRequestDTO);

    /**
     * Retrieves a paged list of comments associated with a specific show.
     *
     * @param showRequestDTO The ShowRequestDTO representing the show
     * @param page           The PageRequestDTO containing pagination information
     * @return A CollectionResponseDTO containing a page of comments associated with the specified show
     */
    CollectionResponseDTO<CommentResponseDTO> findAllCommentsFromShowPaged(ShowRequestDTO showRequestDTO, PageRequestDTO page);

    /**
     * Saves a new comment.
     *
     * @param commentRequestDTO The CommentRequestDTO containing data for the new comment
     * @return A CommentResponseDTO representing the saved comment
     */
    CommentResponseDTO save(CommentRequestDTO commentRequestDTO);
}
