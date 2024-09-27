package ro.ps.showmgmtbackend.util;

import lombok.experimental.UtilityClass;
import ro.ps.showmgmtbackend.dto.mail.MailRequestDTO;
import ro.ps.showmgmtbackend.dto.mail.MailResponseDTO;
import ro.ps.showmgmtbackend.dto.mail.SendingStatus;

@UtilityClass
public class MailUtils {

    public MailResponseDTO getMailResponseDTO(MailRequestDTO mailRequestDTO, SendingStatus status) {
        return MailResponseDTO.builder()
                .from(mailRequestDTO.getFrom())
                .to(mailRequestDTO.getTo())
                .status(status)
                .build();
    }
}