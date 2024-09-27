package ro.ps.showmgmtmail.dto.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MailRequestDTO {

    private String from;
    private String to;
    private String subject;
    private String body;
}
