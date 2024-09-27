package ro.ps.showmgmtbackend.dto.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MailRequestDTO {

    private String from;
    private String to;
    private String subject;
    private String body;
}

