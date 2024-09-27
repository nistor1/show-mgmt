package ro.ps.showmgmtmail.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.ps.showmgmtmail.dto.mail.MailRequestDTO;
import ro.ps.showmgmtmail.dto.mail.MailResponseDTO;
import ro.ps.showmgmtmail.service.mail.MailService;

@Slf4j
@RestController
@RequestMapping("/mail/v1")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("sync")
    public ResponseEntity<MailResponseDTO> sendSyncMail(@RequestBody MailRequestDTO mailRequestDTO) {
        return new ResponseEntity<>(
                mailService.sendMail(mailRequestDTO),
                HttpStatus.OK
        );
    }
}
