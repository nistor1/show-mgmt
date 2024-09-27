package ro.ps.showmgmtmail.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import ro.ps.showmgmtmail.jms.MailMessageReceiverBean;
import ro.ps.showmgmtmail.jms.MessageReceiver;
import ro.ps.showmgmtmail.service.mail.MailService;
import ro.ps.showmgmtmail.service.mail.MailServiceBean;

@Configuration
public class Config {

    @Bean
    public MailService mailService(JavaMailSender javaMailSender) {
        return new MailServiceBean(javaMailSender);
    }

    @Bean
    public MessageReceiver mailMessageReceiver(MailService mailService, ObjectMapper objectMapper) {
        return new MailMessageReceiverBean(mailService, objectMapper);
    }
}
