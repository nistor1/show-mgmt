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
import ro.ps.showmgmtbackend.dto.show.ShowRequestDTO;
import ro.ps.showmgmtbackend.dto.show.ShowResponseDTO;
import ro.ps.showmgmtbackend.dto.user.UserRequestDTO;
import ro.ps.showmgmtbackend.exception.ExceptionBody;
import ro.ps.showmgmtbackend.service.show.ShowService;

import java.util.List;
import java.util.UUID;

/**
 * rest controller class that defines Show end-points
 * This includes:
 * - Finding a show by id
 * - Deleting a show by id
 * - Deleting all shows
 * - Finding all shows
 * - Saving a show
 * - Updating a show
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping("/show/v1")
@RequiredArgsConstructor
public class ShowController {

    private final ShowService showService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE', 'CLIENT')")
    @Operation(summary = "Gets show by ID", description = "Show must exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Show found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ShowResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Show not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<ShowResponseDTO> findById(@PathVariable("id") UUID showId) {
        return new ResponseEntity<>(showService.findById(showId),
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
    @Operation(summary = "Gets all shows", description = "Shows all shows")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shows found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserRequestDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<List<ShowResponseDTO>> findAll() {
        return new ResponseEntity<>(
                showService.findAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/all-paged")
    @Operation(summary = "Gets all shows paged", description = "Shows all shows paged")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shows found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CollectionResponseDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<CollectionResponseDTO<ShowResponseDTO>> findAllPaged(@Valid PageRequestDTO page) {
        return new ResponseEntity<>(
                showService.findAllPaged(page),
                HttpStatus.OK
        );
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Deletes all shows", description = "Deletes all shows")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shows deleted",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<Void> deleteAll() {
        showService.deleteAll();
        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @Operation(summary = "Deletes show by ID", description = "Show must exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Show deleted",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))}),
            @ApiResponse(responseCode = "404", description = "Show not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<Void> deleteById(
            @PathVariable("id") UUID id
    ) {
        showService.deleteById(id);
        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @Operation(summary = "Updates show", description = "Show must exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Show updated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ShowResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Show not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
        public ResponseEntity<ShowResponseDTO> update(
            @RequestBody ShowRequestDTO showRequestDTO
    ) {
        return new ResponseEntity<>(
                showService.update(showRequestDTO),
                HttpStatus.CREATED
        );
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @Operation(summary = "Saves show", description = "Saves show")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Show saved",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ShowResponseDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<ShowResponseDTO> save(
            @RequestBody ShowRequestDTO showRequestDTO
    ) {
        return new ResponseEntity<>(
                showService.save(showRequestDTO),
                HttpStatus.CREATED
        );
    }

}
