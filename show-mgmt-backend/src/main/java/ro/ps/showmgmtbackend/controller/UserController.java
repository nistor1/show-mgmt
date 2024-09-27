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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ro.ps.showmgmtbackend.dto.CollectionResponseDTO;
import ro.ps.showmgmtbackend.dto.PageRequestDTO;
import ro.ps.showmgmtbackend.dto.user.UserRequestDTO;
import ro.ps.showmgmtbackend.dto.user.UserResponseDTO;
import ro.ps.showmgmtbackend.exception.ExceptionBody;
import ro.ps.showmgmtbackend.service.user.UserService;

import java.util.List;
import java.util.UUID;

/**
 * rest controller class that defines User end-points
 * This includes:
 * - Finding a user by id
 * - Deleting a user by id
 * - Deleting all users
 * - Finding all users
 * - Saving a user
 * - Updating a user
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping("/user/v1")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Gets users by ID", description = "User must exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserRequestDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Show not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<UserResponseDTO> findById(@PathVariable("id") UUID userId) {
        return new ResponseEntity<>(userService.findById(userId),
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
    @Operation(summary = "Gets all users", description = "All users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserRequestDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        return new ResponseEntity<>(
                userService.findAll(),
                HttpStatus.OK
        );
    }

    @Operation(summary = "Gets logged user info", description = "Logged user info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserRequestDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    @GetMapping("/info")
    public ResponseEntity<UserResponseDTO> getLoggedUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();

        return new ResponseEntity<>(
                userService.findByEmail(email),
                HttpStatus.OK
        );
    }

    @GetMapping("/all-paged")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Gets all users paged", description = "All users paged")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserRequestDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<CollectionResponseDTO<UserResponseDTO>> findAllPaged(@Valid PageRequestDTO page) {
        return new ResponseEntity<>(
                userService.findAllPaged(page),
                HttpStatus.OK
        );
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Deletes all users", description = "All users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users deleted",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<Void> deleteAll() {
        userService.deleteAll();
        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @Operation(summary = "Deletes user by ID", description = "User must exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))}),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<Void> deleteById(
            @PathVariable("id") UUID id
    ) {
        userService.deleteById(id);
        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE', 'CLIENT')")
    @Operation(summary = "Updates user", description = "User must exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User updated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserRequestDTO.class))}),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<UserResponseDTO> update(
            @RequestBody UserRequestDTO userRequestDTO
    ) {
        return new ResponseEntity<>(
                userService.update(userRequestDTO),
                HttpStatus.CREATED
        );
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @Operation(summary = "Saves user", description = "User must not exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User saved",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserRequestDTO.class))}),
            @ApiResponse(responseCode = "409", description = "User already exists",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<UserResponseDTO> save(
            @RequestBody UserRequestDTO userRequestDTO
    ) {
        return new ResponseEntity<>(
                userService.save(userRequestDTO),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/email")
    @Operation(summary = "Gets users by email", description = "User must exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserRequestDTO.class))}),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<?> getAll(
            @RequestParam(value = "email", required = false, defaultValue = "") String email,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize
    ) {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

}
