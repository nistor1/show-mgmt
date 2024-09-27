package ro.ps.showmgmtbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.ps.showmgmtbackend.dto.CollectionResponseDTO;
import ro.ps.showmgmtbackend.dto.PageRequestDTO;
import ro.ps.showmgmtbackend.dto.comment.CommentRequestDTO;
import ro.ps.showmgmtbackend.dto.comment.CommentResponseDTO;
import ro.ps.showmgmtbackend.dto.show.ShowRequestDTO;
import ro.ps.showmgmtbackend.exception.ExceptionBody;
import ro.ps.showmgmtbackend.service.comment.CommentService;

import java.util.List;
import java.util.UUID;


/**
 * rest controller class that defines Comment end-points
 * This includes:
 * - Finding a comment by id
 * - Deleting a comment by id
 * - Deleting all comments
 * - Finding all comments
 * - Saving a comment
 * - Finding all comments from a show
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping("/comment/v1")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{id}")
    @Operation(summary = "Gets comments by ID", description = "Comment must exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CommentRequestDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Show not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<CommentResponseDTO> findById(@PathVariable("id") UUID commentId) {
        return new ResponseEntity<>(commentService.findById(commentId),
                HttpStatus.OK
        );
    }

    @GetMapping("/message")
    @Operation(summary = "Shows a message", description = "Shows a message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message shown",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<String> showMessage() {
        String mesaj = "Mergi BA.";
        return ResponseEntity.ok(mesaj);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE', 'CLIENT')")
    @Operation(summary = "Gets all comments", description = "All comments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comments found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CommentResponseDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<List<CommentResponseDTO>> findAll() {
        return new ResponseEntity<>(
                commentService.findAll(),
                HttpStatus.OK
        );
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Deletes all comments", description = "Deletes all comments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All comments deleted",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<Void> deleteAll() {
        commentService.deleteAll();
        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @Operation(summary = "Deletes comment by ID", description = "Comment must exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment deleted",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))}),
            @ApiResponse(responseCode = "404", description = "Comment not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<Void> deleteById(
            @PathVariable("id") UUID id
    ) {
        commentService.deleteById(id);
        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }

    @GetMapping("/all-paged")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Gets all comments paged", description = "All comments paged")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comments found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CollectionResponseDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<CollectionResponseDTO<CommentResponseDTO>> findAllPaged(@Valid PageRequestDTO page) {
        return new ResponseEntity<>(
                commentService.findAllPaged(page),
                HttpStatus.OK
        );
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE', 'CLIENT')")
    @Operation(summary = "Saves a comment", description = "Saves a comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment saved",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CommentRequestDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid comment",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<CommentResponseDTO> save(
            @RequestBody CommentRequestDTO commentRequestDTO
    ) {
        return new ResponseEntity<>(
                commentService.save(commentRequestDTO),
                HttpStatus.CREATED
        );
    }

    @GetMapping("all-from-show/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE', 'CLIENT')")
    @Operation(summary = "Gets all comments from show", description = "All comments from show")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comments found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CommentResponseDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<List<CommentResponseDTO>> findByShow(
            @PathVariable("id") UUID showId
    ) {
        ShowRequestDTO showRequestDTO = new ShowRequestDTO();
        showRequestDTO.setShowId(showId);
        return new ResponseEntity<>(
                commentService.findByShow(showRequestDTO),
                HttpStatus.OK
        );
    }

    @GetMapping("/all-from-show-paged")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @Operation(summary = "Gets all comments from show paged", description = "All comments from show paged")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comments found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CollectionResponseDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<CollectionResponseDTO<CommentResponseDTO>> findAllFromShowPaged(
            @RequestBody ShowRequestDTO showRequestDTO,
            @Valid PageRequestDTO page
    ) {
        return new ResponseEntity<>(
                commentService.findAllCommentsFromShowPaged(showRequestDTO, page),
                HttpStatus.OK
        );
    }
}
