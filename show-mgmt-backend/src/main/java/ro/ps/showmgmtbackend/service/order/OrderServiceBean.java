package ro.ps.showmgmtbackend.service.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import ro.ps.showmgmtbackend.dto.CollectionResponseDTO;
import ro.ps.showmgmtbackend.dto.PageRequestDTO;
import ro.ps.showmgmtbackend.dto.mail.MailRequestDTO;
import ro.ps.showmgmtbackend.dto.order.OrderRequestDTO;
import ro.ps.showmgmtbackend.dto.order.OrderResponseDTO;
import ro.ps.showmgmtbackend.dto.show.ShowRequestDTO;
import ro.ps.showmgmtbackend.dto.user.UserRequestDTO;
import ro.ps.showmgmtbackend.exception.ExceptionCode;
import ro.ps.showmgmtbackend.exception.NotFoundException;
import ro.ps.showmgmtbackend.mapper.OrderMapper;
import ro.ps.showmgmtbackend.mapper.ShowMapper;
import ro.ps.showmgmtbackend.mapper.UserMapper;
import ro.ps.showmgmtbackend.model.OrderEntity;
import ro.ps.showmgmtbackend.repository.OrderRepository;
import ro.ps.showmgmtbackend.service.mail.MailService;
import ro.ps.showmgmtbackend.service.show.ShowService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class OrderServiceBean implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserMapper userMapper;
    private final ShowMapper showMapper;
    private final ShowService showService;
    private final MailService mailService;

    private final String applicationName;

    @Override
    public OrderResponseDTO findById(UUID orderId) {
        OrderResponseDTO orderResponseDTO = orderRepository.findById(orderId)
                .map(orderMapper::orderEntityToOrderResponseDTO)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionCode.ERR001_SHOW_NOT_FOUND.getMessage(),
                        orderId
                )));

        return orderResponseDTO;
    }

    @Override
    public void deleteById(UUID orderId) {
        try {
            orderRepository.deleteById(orderId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(ExceptionCode.ERR001_SHOW_NOT_FOUND.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        try {
            orderRepository.deleteAll();
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(String.format(ExceptionCode.ERR001_SHOW_NOT_FOUND.getMessage()));
        }
    }

    @Override
    public List<OrderResponseDTO> findAll() {
        log.info("Getting all comments for application {}", applicationName);

        List<OrderEntity> orderEntityList = orderRepository.findAll();

        return orderMapper.orderEntityListToOrderResponseDTOList(orderEntityList);
    }

    @Override
    public CollectionResponseDTO<OrderResponseDTO> findAllPaged(PageRequestDTO page) {
        Page<OrderEntity> orderEntityPage = orderRepository.findAll(PageRequest.of(
                page.getPageNumber(),
                page.getPageSize()
        ));
        List<OrderResponseDTO> orderResponseDTOList = orderMapper.orderEntityListToOrderResponseDTOList(orderEntityPage.getContent());
        return new CollectionResponseDTO<>(orderResponseDTOList, orderEntityPage.getTotalElements());
    }

    @Override
    public List<OrderResponseDTO> findByShow(ShowRequestDTO showRequestDTO) {
        log.info("Getting all comments for show with ID {} for application {}", showRequestDTO.getShowId(), applicationName);

        List<OrderEntity> orderEntityList = orderRepository.findByShow(showMapper.showRequestDTOToShowEntity(showRequestDTO));

        return orderMapper.orderEntityListToOrderResponseDTOList(orderEntityList);
    }

    @Override
    public List<OrderResponseDTO> findByUser(UserRequestDTO userRequestDTO) {
        log.info("Getting all comments for show with ID {} for application {}", userRequestDTO.getUserId(), applicationName);

        List<OrderEntity> orderEntityList = orderRepository.findByUser(userMapper.userRequestDTOToUserEntity(userRequestDTO));

        return orderMapper.orderEntityListToOrderResponseDTOList(orderEntityList);
    }

    @Override
    public CollectionResponseDTO<OrderResponseDTO> findAllOrdersFromShowPaged(ShowRequestDTO showRequestDTO, PageRequestDTO page) {
        Page<OrderEntity> orderEntityPage = orderRepository.findByShow(showMapper.showRequestDTOToShowEntity(showRequestDTO),
                PageRequest.of(
                        page.getPageNumber(),
                        page.getPageSize()
                ));
        List<OrderResponseDTO> orderResponseDTOList = orderMapper.orderEntityListToOrderResponseDTOList(orderEntityPage.getContent());
        return new CollectionResponseDTO<>(orderResponseDTOList, orderEntityPage.getTotalElements());
    }

    @Override
    public CollectionResponseDTO<OrderResponseDTO> findAllOrdersFromUserPaged(UserRequestDTO userRequestDTO, PageRequestDTO page) {
        Page<OrderEntity> orderEntityPage = orderRepository.findByUser(userMapper.userRequestDTOToUserEntity(userRequestDTO),
                PageRequest.of(
                        page.getPageNumber(),
                        page.getPageSize()
                ));
        List<OrderResponseDTO> orderResponseDTOList = orderMapper.orderEntityListToOrderResponseDTOList(orderEntityPage.getContent());
        return new CollectionResponseDTO<>(orderResponseDTOList, orderEntityPage.getTotalElements());
    }

    @Override
    public OrderResponseDTO save(OrderRequestDTO orderRequestDTO) {
        /*TODO
        MODIFICA SI IN CELELATE METODE LA FEL CA MAI JOS
         */
        OrderEntity orderToBeAdded = orderMapper.orderRequestDTOToOrderEntity(orderRequestDTO);
        orderToBeAdded.setOrderDate(LocalDate.now());

        ShowRequestDTO showRequestDTO = orderRequestDTO.getShow();
        UserRequestDTO userRequestDTO = orderRequestDTO.getUser();
        Integer numberOfTicketsLeft = showRequestDTO.getNumberOfTicketsLeft();
        if ((numberOfTicketsLeft - orderRequestDTO.getNumberOfTicketsToBuy()) < 0) {
            throw new NotFoundException(String.format(ExceptionCode.ERR099_INVALID_CREDENTIALS.getMessage()));
        }
        showRequestDTO.setNumberOfTicketsLeft(numberOfTicketsLeft - orderRequestDTO.getNumberOfTicketsToBuy());
        showService.update(showRequestDTO);

        OrderEntity orderAdded = orderRepository.save(orderToBeAdded);
        if(orderAdded != null) {
            MailRequestDTO mailRequestDTO = new MailRequestDTO();
            mailRequestDTO.setFrom("admin@stand-up.com");
            String subject = "Stand-up comedy order for " + showRequestDTO.getName();
            String body = "Dear " + userRequestDTO.getName() + ",<br><br>"
                    + "Thank you for your order.<br>"
                    + "Order Details:<br>"
                    + "Order Number: " + orderAdded.getOrderId().toString().substring(0,5) + "<br>"
                    + "Show: " + showRequestDTO.getName() + "<br>"
                    + "Date: " + showRequestDTO.getEventDate() + "<br>"
                    + "Location: " + showRequestDTO.getLocation() + "<br>"
                    + "Number of Tickets: " + orderAdded.getNumberOfTicketsToBuy() + "<br>"
                    + "Order Date: " + orderAdded.getOrderDate() + "<br><br>"
                    + "Best regards,<br>"
                    + "Stand-up Comedy Team";
            mailRequestDTO.setBody(body);
            mailRequestDTO.setSubject(subject);
            mailRequestDTO.setTo(userRequestDTO.getEmail());
            mailService.sendMail(mailRequestDTO);
        }

        return orderMapper.orderEntityToOrderResponseDTO(orderAdded);
    }
}
