package ro.ps.showmgmtmail.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import lombok.experimental.UtilityClass;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import ro.ps.showmgmtmail.dto.mail.MailRequestDTO;

@UtilityClass
public class MailUtils {

    private final String EMAIL_TEMPLATE_LOCATION = "templates/email/report.html";
    private final String EMAIL_EMAIL = "###EMAIL###";
    private final String EMAIL_SUBJECT = "###SUBJECT###";
    private final String EMAIL_BODY = "###BODY###";

    public String getEmail(MailRequestDTO mailRequestDTO) throws IOException {
        String emailContent = getEmailContent();
        Map<String, String> emailProperties = getEmailProperties(mailRequestDTO);

        return emailProperties.entrySet().stream()
                .reduce(
                        emailContent,
                        (content, entry) -> content.replace(entry.getKey(), entry.getValue()),
                        (s1, s2) -> s2
                );
    }

    private String getEmailContent() throws IOException {
        Resource resource = new ClassPathResource(EMAIL_TEMPLATE_LOCATION);

        return IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
    }

    private Map<String, String> getEmailProperties(MailRequestDTO mailRequestDTO) {
        return Map.ofEntries(
                Map.entry(EMAIL_EMAIL, mailRequestDTO.getTo()),
                Map.entry(EMAIL_SUBJECT, mailRequestDTO.getSubject()),
                Map.entry(EMAIL_BODY, mailRequestDTO.getBody())
        );
    }
}
