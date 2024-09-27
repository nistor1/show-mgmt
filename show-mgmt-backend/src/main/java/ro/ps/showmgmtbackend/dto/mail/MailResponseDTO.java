package ro.ps.showmgmtbackend.dto.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailResponseDTO {

    private String from;
    private String to;
    private SendingStatus status;
}
