package ro.ps.showmgmtbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.ps.showmgmtbackend.exception.ExceptionBody;


/**
 * rest controller class that defines Auth end-points
 * This includes:
 * - Login
 */
@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping("/auth/v1")
@RequiredArgsConstructor
public class AuthController {

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Login request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login request",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<Void> login() {
        log.info("Login request detected...");

        return ResponseEntity.ok().build();
    }

}
