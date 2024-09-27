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
import ro.ps.showmgmtbackend.dto.order.OrderRequestDTO;
import ro.ps.showmgmtbackend.dto.order.OrderResponseDTO;
import ro.ps.showmgmtbackend.dto.show.ShowRequestDTO;
import ro.ps.showmgmtbackend.dto.user.UserRequestDTO;
import ro.ps.showmgmtbackend.exception.ExceptionBody;
import ro.ps.showmgmtbackend.service.order.OrderService;

import java.util.List;
import java.util.UUID;

/**
 * rest controller class that defines Order end-points
 * This includes:
 * - Finding an order by id
 * - Deleting an order by id
 * - Deleting all orders
 * - Finding all orders
 * - Finding all orders from a show
 * - Finding all orders from a user
 * - Saving an order
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping("/order/v1")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE', 'CLIENT')")
    @Operation(summary = "Gets orders by ID", description = "Order must exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CommentRequestDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Show not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<OrderResponseDTO> findById(@PathVariable("id") UUID orderId) {
        return new ResponseEntity<>(orderService.findById(orderId),
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
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @Operation(summary = "Gets all orders", description = "Shows all orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CommentRequestDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<List<OrderResponseDTO>> findAll() {
        return new ResponseEntity<>(
                orderService.findAll(),
                HttpStatus.OK
        );
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Deletes all orders", description = "Deletes all orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders deleted",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CommentRequestDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<Void> deleteAll() {
        orderService.deleteAll();
        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @Operation(summary = "Deletes order by ID", description = "Deletes order by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order deleted",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CommentRequestDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<Void> deleteById(
            @PathVariable("id") UUID id
    ) {
        orderService.deleteById(id);
        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }

    @GetMapping("/all-paged")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Gets all orders paged", description = "Shows all orders paged")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CollectionResponseDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<CollectionResponseDTO<OrderResponseDTO>> findAllPaged(@Valid PageRequestDTO page) {
        return new ResponseEntity<>(
                orderService.findAllPaged(page),
                HttpStatus.OK
        );
    }

    @GetMapping("all-from-show")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @Operation(summary = "Gets all orders from show", description = "Shows all orders from show")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CollectionResponseDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<List<OrderResponseDTO>> findByShow(
            @RequestBody ShowRequestDTO showRequestDTO
    ) {
        return new ResponseEntity<>(
                orderService.findByShow(showRequestDTO),
                HttpStatus.OK
        );
    }

    @GetMapping("/all-from-show-paged")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @Operation(summary = "Gets all orders from show paged", description = "Shows all orders from show paged")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CollectionResponseDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<CollectionResponseDTO<OrderResponseDTO>> findAllFromShowPaged(
            @RequestBody ShowRequestDTO showRequestDTO,
            @Valid PageRequestDTO page
    ) {
        return new ResponseEntity<>(
                orderService.findAllOrdersFromShowPaged(showRequestDTO, page),
                HttpStatus.OK
        );
    }

    @GetMapping("all-from-user/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE', 'CLIENT')")
    @Operation(summary = "Gets all orders from user", description = "Shows all orders from user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CollectionResponseDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<List<OrderResponseDTO>> findByUser(
            @PathVariable("id") UUID userId
    ) {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUserId(userId);
        return new ResponseEntity<>(
                orderService.findByUser(userRequestDTO),
                HttpStatus.OK
        );
    }

    @GetMapping("/all-from-user-paged")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE', 'CLIENT')")
    @Operation(summary = "Gets all orders from user paged", description = "Shows all orders from user paged")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CollectionResponseDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<CollectionResponseDTO<OrderResponseDTO>> findAllFromUserPaged(
            @RequestBody UserRequestDTO userRequestDTO,
            @Valid PageRequestDTO page
    ) {
        return new ResponseEntity<>(
                orderService.findAllOrdersFromUserPaged(userRequestDTO, page),
                HttpStatus.OK
        );
    }

    @PostMapping
    @Operation(summary = "Saves an order", description = "Saves an order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order saved",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CommentRequestDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<OrderResponseDTO> save(
            @RequestBody OrderRequestDTO orderRequestDTO
    ) {
        return new ResponseEntity<>(
                orderService.save(orderRequestDTO),
                HttpStatus.CREATED
        );
    }
}
