package ro.ps.showmgmtbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ro.ps.showmgmtbackend.dto.mail.MailRequestDTO;
import ro.ps.showmgmtbackend.dto.mail.MailResponseDTO;
import ro.ps.showmgmtbackend.exception.ExceptionBody;
import ro.ps.showmgmtbackend.service.mail.MailService;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping("/mail/v1")
public class MailController {

    private final MailService syncMailServiceBean;
    private final MailService asyncMailServiceBean;

    public MailController(
            @Qualifier("syncMailServiceBean") MailService syncMailServiceBean,
            @Qualifier("asyncMailServiceBean") MailService asyncMailServiceBean
    ) {
        this.syncMailServiceBean = syncMailServiceBean;
        this.asyncMailServiceBean = asyncMailServiceBean;
    }

    @PostMapping("sync")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MailResponseDTO> sendSyncMail(@RequestBody MailRequestDTO mailRequestDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();
        mailRequestDTO.setFrom(email);

        return new ResponseEntity<>(
                syncMailServiceBean.sendMail(mailRequestDTO),
                HttpStatus.OK
        );
    }

    @PostMapping("async")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MailResponseDTO> sendAsyncMail(@RequestBody MailRequestDTO mailRequestDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();
        mailRequestDTO.setFrom(email);

        return new ResponseEntity<>(
                asyncMailServiceBean.sendMail(mailRequestDTO),
                HttpStatus.OK
        );
    }

    @GetMapping("/message")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE', 'CLIENT')")
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
}