package ro.ps.showmgmtbackend.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import ro.ps.showmgmtbackend.model.*;
import ro.ps.showmgmtbackend.repository.CommentRepository;
import ro.ps.showmgmtbackend.repository.OrderRepository;
import ro.ps.showmgmtbackend.repository.ShowRepository;
import ro.ps.showmgmtbackend.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * class that defines the initial data that will be inserted in the database
 */
@Configuration
public class StartupConfig {
    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository, CommentRepository commentRepository,
                                        ShowRepository showRepository, OrderRepository orderRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            UserEntity u1 = UserEntity
                    .builder()
                    .name("A")
                    .email("a.a@gmail.com")
                    .password(passwordEncoder.encode("a"))
                    .age(40)
                    .role(Role.CLIENT)
                    .build();

            UserEntity u2 = UserEntity
                    .builder()
                    .name("B")
                    .email("b.b@gmail.com")
                    .age(41)
                    .role(Role.CLIENT)
                    .build();

            UserEntity u3 = UserEntity
                    .builder()
                    .name("C")
                    .email("c.c@gmail.com")
                    .age(43)
                    .role(Role.CLIENT)
                    .build();

            UserEntity u4 = UserEntity
                    .builder()
                    .name("D")
                    .email("d.d@gmail.com")
                    .age(43)
                    .role(Role.CLIENT)
                    .build();

            UserEntity u5 = UserEntity
                    .builder()
                    .name("E")
                    .email("E.E@gmail.com")
                    .age(44)
                    .password(passwordEncoder.encode("e"))
                    .role(Role.EMPLOYEE)
                    .build();

            UserEntity u6 = UserEntity
                    .builder()
                    .name("F")
                    .email("F.F@gmail.com")
                    .age(45)
                    .role(Role.EMPLOYEE)
                    .build();

            UserEntity u7 = UserEntity
                    .builder()
                    .name("admin")
                    .email("admin@gmail.com")
                    .password(passwordEncoder.encode("admin"))
                    .age(44)
                    .role(Role.ADMIN)
                    .build();

            List<UserEntity> userEntityList = userRepository.saveAll(List.of(u1, u2, u3, u4, u5, u6, u7));

            ShowEntity s1 = ShowEntity
                    .builder()
                    .name("Show Stand-Up 1")
                    .location("Sala Palatului")
                    .price(120.3f)
                    .eventDate(LocalDate.now())
                    .description("Maine e smecherie")
                    .numberOfTicketsLeft(8000)
                    .build();

            ShowEntity s2 = ShowEntity
                    .builder()
                    .name("Show Stand-Up 2")
                    .location("Sala Polivalenta")
                    .price(120.3f)
                    .eventDate(LocalDate.now())
                    .description("Maine e smecherie")
                    .numberOfTicketsLeft(8000)
                    .build();

            ShowEntity s3 = ShowEntity
                    .builder()
                    .name("Show Stand-Up 3")
                    .location("Arena Nationala")
                    .price(120.3f)
                    .eventDate(LocalDate.now())
                    .description("Maine e smecherie")
                    .numberOfTicketsLeft(80000)
                    .build();

            List<ShowEntity> showEntityList = showRepository.saveAll(List.of(s1, s2, s3));

            CommentEntity c1 = CommentEntity
                    .builder()
                    .commentDate(LocalDate.now())
                    .description("A la show 1")
                    .show(showEntityList.get(0))
                    .user(userEntityList.get(0))
                    .build();

            CommentEntity c2 = CommentEntity
                    .builder()
                    .commentDate(LocalDate.now())
                    .description("C la show 1")
                    .show(showEntityList.get(0))
                    .user(userEntityList.get(2))
                    .build();

            CommentEntity c3 = CommentEntity
                    .builder()
                    .commentDate(LocalDate.now())
                    .description("C la show 3")
                    .show(showEntityList.get(2))
                    .user(userEntityList.get(2))
                    .build();

            CommentEntity c4 = CommentEntity
                    .builder()
                    .commentDate(LocalDate.now())
                    .description("D la show 2")
                    .show(showEntityList.get(1))
                    .user(userEntityList.get(3))
                    .build();

            CommentEntity c5 = CommentEntity
                    .builder()
                    .commentDate(LocalDate.now())
                    .description("B la show 3")
                    .show(showEntityList.get(2))
                    .user(userEntityList.get(1))
                    .build();

            commentRepository.saveAll(List.of(c1, c2, c3, c4, c5));

            OrderEntity o1 = OrderEntity.builder()
                    .orderDate(LocalDate.now())
                    .numberOfTicketsToBuy(10)
                    .show(s1)
                    .user(u1)
                    .build();

            orderRepository.save(o1);

        };
    }
}
