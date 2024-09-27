package ro.ps.showmgmtbackend.jms;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import ro.ps.showmgmtbackend.dto.mail.SendingStatus;

@Slf4j
public abstract class JmsMessageSenderBase<Request> implements MessageSender<Request> {

    protected final String destination;
    protected final JmsTemplate jmsTemplate;
    protected final ObjectMapper objectMapper;

    protected JmsMessageSenderBase(String destination, JmsTemplate jmsTemplate, ObjectMapper objectMapper) {
        this.destination = destination;
        this.jmsTemplate = jmsTemplate;
        this.objectMapper = objectMapper;
    }

    public SendingStatus sendMessage(Request request) {
        log.info("Sending message <{}> to queue <{}>", request, destination);

        try {
            String payload = objectMapper.writeValueAsString(request);
            jmsTemplate.send(destination, messageCreator -> messageCreator.createTextMessage(payload));

            return SendingStatus.SUCCESS;
        } catch (Exception e) {
            log.error("Failed to send message <{}> to queue <{}>", request, destination);
        }

        return SendingStatus.FAILURE;
    }
}

