package ro.ps.showmgmtbackend.service.mail;

import ro.ps.showmgmtbackend.dto.mail.MailRequestDTO;
import ro.ps.showmgmtbackend.dto.mail.MailResponseDTO;

public interface MailService {

    MailResponseDTO sendMail(MailRequestDTO mailRequestDTO);
}

