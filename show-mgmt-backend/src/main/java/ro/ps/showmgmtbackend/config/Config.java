package ro.ps.showmgmtbackend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import ro.ps.showmgmtbackend.mapper.CommentMapper;
import ro.ps.showmgmtbackend.mapper.OrderMapper;
import ro.ps.showmgmtbackend.mapper.ShowMapper;
import ro.ps.showmgmtbackend.mapper.UserMapper;
import ro.ps.showmgmtbackend.repository.CommentRepository;
import ro.ps.showmgmtbackend.repository.OrderRepository;
import ro.ps.showmgmtbackend.repository.ShowRepository;
import ro.ps.showmgmtbackend.repository.UserRepository;
import ro.ps.showmgmtbackend.service.comment.CommentService;
import ro.ps.showmgmtbackend.service.comment.CommentServiceBean;
import ro.ps.showmgmtbackend.service.mail.AsyncMailServiceBean;
import ro.ps.showmgmtbackend.service.mail.MailService;
import ro.ps.showmgmtbackend.service.mail.SyncMailServiceBean;
import ro.ps.showmgmtbackend.service.order.OrderService;
import ro.ps.showmgmtbackend.service.order.OrderServiceBean;
import ro.ps.showmgmtbackend.service.show.ShowService;
import ro.ps.showmgmtbackend.service.show.ShowServiceBean;
import ro.ps.showmgmtbackend.service.user.UserService;
import ro.ps.showmgmtbackend.service.user.UserServiceBean;

@Configuration
public class Config {
    @Bean
    public ShowService showServiceBean(
            ShowRepository showRepository,
            ShowMapper showMapper,
            @Value("${spring.application.name:BACKEND}") String applicationName
    ) {
        return new ShowServiceBean(showRepository, showMapper, applicationName);
    }

    @Bean
    public UserService userServiceBean(
            UserRepository userRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder,
            @Value("${spring.application.name:BACKEND}") String applicationName
    ) {
        return new UserServiceBean(userRepository, userMapper, applicationName, passwordEncoder);
    }

    @Bean
    public CommentService commentServiceBean(
            CommentRepository commentRepository,
            CommentMapper commentMapper,
            UserMapper userMapper,
            ShowMapper showMapper,
            @Value("${spring.application.name:BACKEND}") String applicationName
    ) {
        return new CommentServiceBean(commentRepository, commentMapper, userMapper, showMapper, applicationName);
    }

    @Bean
    public OrderService orderServiceBean(
            ShowService showService,
            OrderRepository orderRepository,
            OrderMapper orderMapper,
            UserMapper userMapper,
            ShowMapper showMapper,
            @Qualifier("asyncMailServiceBean") MailService mailService,
            @Value("${spring.application.name:BACKEND}") String applicationName
    ) {
        return new OrderServiceBean(orderRepository, orderMapper, userMapper, showMapper, showService, mailService, applicationName);
    }

    @Bean
    public MailService syncMailServiceBean(
            @Value("${mail-sender-app.url}") String url,
            RestTemplateBuilder restTemplateBuilder
    ) {
        return new SyncMailServiceBean(url, restTemplateBuilder.build());
    }
//DE aici nu stiu
    @Bean
    public MailService asyncMailServiceBean(
            @Value("${queues.async-mail-sender-request}") String destination,
            JmsTemplate jmsTemplate,
            ObjectMapper objectMapper
    ) {
        return new AsyncMailServiceBean(destination, jmsTemplate, objectMapper);
    }

    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL("tcp://localhost:61616");
        factory.setUserName("admin");
        factory.setPassword("admin");
        return factory;
    }

    @Bean
    public JmsTemplate jmsTemplate(ActiveMQConnectionFactory connectionFactory) {
        return new JmsTemplate(connectionFactory);
    }
}
