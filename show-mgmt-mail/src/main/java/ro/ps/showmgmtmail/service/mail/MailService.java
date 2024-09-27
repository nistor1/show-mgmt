package ro.ps.showmgmtmail.service.mail;

import ro.ps.showmgmtmail.dto.mail.MailRequestDTO;
import ro.ps.showmgmtmail.dto.mail.MailResponseDTO;

public interface MailService {

    MailResponseDTO sendMail(MailRequestDTO mailRequestDTO);
}
