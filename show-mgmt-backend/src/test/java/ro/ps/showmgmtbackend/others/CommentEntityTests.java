package ro.ps.showmgmtbackend.others;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ro.ps.showmgmtbackend.dto.CollectionResponseDTO;
import ro.ps.showmgmtbackend.dto.PageRequestDTO;
import ro.ps.showmgmtbackend.dto.comment.CommentRequestDTO;
import ro.ps.showmgmtbackend.dto.comment.CommentResponseDTO;
import ro.ps.showmgmtbackend.dto.show.ShowRequestDTO;
import ro.ps.showmgmtbackend.dto.show.ShowResponseDTO;
import ro.ps.showmgmtbackend.dto.user.UserRequestDTO;
import ro.ps.showmgmtbackend.mapper.CommentMapper;
import ro.ps.showmgmtbackend.mapper.ShowMapper;
import ro.ps.showmgmtbackend.model.CommentEntity;
import ro.ps.showmgmtbackend.model.Role;
import ro.ps.showmgmtbackend.repository.CommentRepository;
import ro.ps.showmgmtbackend.repository.ShowRepository;
import ro.ps.showmgmtbackend.repository.UserRepository;
import ro.ps.showmgmtbackend.service.comment.CommentService;
import ro.ps.showmgmtbackend.service.show.ShowService;
import ro.ps.showmgmtbackend.service.user.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Execution(ExecutionMode.SAME_THREAD)
public class CommentEntityTests {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private ShowService showService;

    @Autowired
    private ShowMapper showMapper;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentService commentService;


    @Test
    public void testEntityMappings() {

        ShowRequestDTO showRequestDTO = new ShowRequestDTO();
        showRequestDTO.setName("Show Stand-Up");
        showRequestDTO.setDescription("Primul show");
        showRequestDTO.setPrice(80.6f);
        showRequestDTO.setEventDate(LocalDate.now());
        showRequestDTO.setNumberOfTicketsLeft(5000);
        showRequestDTO.setLocation("Sala Palatului");

        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setAge(35);
        userRequestDTO.setRole(Role.EMPLOYEE);
        userRequestDTO.setEmail("mariana@bor.com");
        userRequestDTO.setName("Mariana");

        assertEquals("Show Stand-Up", showRequestDTO.getName());
        assertEquals("Primul show", showRequestDTO.getDescription());
        assertEquals(80.6f, showRequestDTO.getPrice(), 0.01);
        assertNotNull(showRequestDTO.getEventDate());
        assertEquals(5000, showRequestDTO.getNumberOfTicketsLeft());
        assertEquals("Sala Palatului", showRequestDTO.getLocation());

        assertEquals(35, userRequestDTO.getAge());
        assertEquals(Role.EMPLOYEE, userRequestDTO.getRole());
        assertEquals("mariana@bor.com", userRequestDTO.getEmail());
        assertEquals("Mariana", userRequestDTO.getName());

        CommentRequestDTO commentRequestDTO = new CommentRequestDTO();
        commentRequestDTO.setDescription("mesaj");
        commentRequestDTO.setUser(userRequestDTO);
        commentRequestDTO.setShow(showRequestDTO);

        assertEquals("mesaj", commentRequestDTO.getDescription());
        assertEquals(userRequestDTO.getEmail(), commentRequestDTO.getUser().getEmail());
        assertEquals(showRequestDTO.getName(), commentRequestDTO.getShow().getName());

    }

    ShowRequestDTO makeShowRequestDTO() {
        ShowRequestDTO showRequestDTO = new ShowRequestDTO();
        showRequestDTO.setName("Show Stand-Up");
        showRequestDTO.setDescription("Primul show");
        showRequestDTO.setPrice(80.6f);
        showRequestDTO.setEventDate(LocalDate.now());
        showRequestDTO.setNumberOfTicketsLeft(5000);
        showRequestDTO.setLocation("Sala Palatului");
        return showRequestDTO;
    }
    UserRequestDTO makeUserRequestDTO() {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setAge(35);
        userRequestDTO.setRole(Role.EMPLOYEE);
        userRequestDTO.setEmail("mariana@bor.com");
        userRequestDTO.setName("Mariana");

        return userRequestDTO;
    }

    @Test
    public void testShowRequestDTOToShowEntityToResponseDTO() {

        ShowRequestDTO showRequestDTO = makeShowRequestDTO();

        UserRequestDTO userRequestDTO = makeUserRequestDTO();


        CommentRequestDTO commentRequestDTO = new CommentRequestDTO();
        commentRequestDTO.setDescription("mesaj");
        commentRequestDTO.setUser(userRequestDTO);
        commentRequestDTO.setShow(showRequestDTO);
        commentRequestDTO.setCommentDate(LocalDate.now());

        CommentEntity commentEntity = commentMapper.commentRequestDTOToCommentEntity(commentRequestDTO);


        assertEquals(commentRequestDTO.getDescription(), commentEntity.getDescription());
        assertEquals(commentRequestDTO.getShow().getName(), commentEntity.getShow().getName());
        assertEquals(commentRequestDTO.getUser().getName(), commentEntity.getUser().getName());
        assertEquals(commentRequestDTO.getCommentDate(), commentEntity.getCommentDate());
        CommentResponseDTO commentResponseDTO = commentMapper.commentEntityToCommentResponseDTO(commentEntity);

        assertEquals(commentEntity.getDescription(), commentResponseDTO.getDescription());
        assertEquals(commentEntity.getShow().getName(), commentResponseDTO.getShow().getName());
        assertEquals(commentEntity.getUser().getName(), commentResponseDTO.getUser().getName());
        assertEquals(commentEntity.getCommentDate(), commentResponseDTO.getCommentDate());
    }
    @Test
    public void testSave() {

        ShowRequestDTO showRequestDTO = makeShowRequestDTO();

        UserRequestDTO userRequestDTO = makeUserRequestDTO();

        UUID showId = showService.save(showRequestDTO).getShowId();
        UUID userId = userService.save(userRequestDTO).getUserId();

        userRequestDTO.setUserId(userId);
        showRequestDTO.setShowId(showId);

        CommentRequestDTO commentRequestDTO = new CommentRequestDTO();
        commentRequestDTO.setDescription("mesaj");
        commentRequestDTO.setUser(userRequestDTO);
        commentRequestDTO.setShow(showRequestDTO);
        commentRequestDTO.setCommentDate(LocalDate.now());



        CommentResponseDTO commentResponseDTO = commentService.save(commentRequestDTO);

        assertNotNull(commentResponseDTO);
        assertEquals(commentRequestDTO.getDescription(), commentResponseDTO.getDescription());
        assertEquals(commentRequestDTO.getShow().getName(), commentResponseDTO.getShow().getName());
        assertEquals(commentRequestDTO.getUser().getName(), commentResponseDTO.getUser().getName());
        assertEquals(commentRequestDTO.getCommentDate(), commentResponseDTO.getCommentDate());
    }

    @Test
    public void testSaveAndFindById() {
        ShowRequestDTO requestDTO = new ShowRequestDTO();
        requestDTO.setName("Test Show");
        requestDTO.setDescription("Test Description");
        requestDTO.setPrice(50.0f);
        requestDTO.setEventDate(LocalDate.of(2024, 4, 10));
        requestDTO.setNumberOfTicketsLeft(100);
        requestDTO.setLocation("Test Location");

        ShowResponseDTO savedShow = showService.save(requestDTO);
        UUID showId = savedShow.getShowId();

        ShowResponseDTO foundShow = showService.findById(showId);

        assertNotNull(foundShow);
        assertEquals(requestDTO.getName(), foundShow.getName());
        assertEquals(requestDTO.getDescription(), foundShow.getDescription());
        assertEquals(requestDTO.getPrice(), foundShow.getPrice());
        assertEquals(requestDTO.getEventDate(), foundShow.getEventDate());
        assertEquals(requestDTO.getNumberOfTicketsLeft(), foundShow.getNumberOfTicketsLeft());
        assertEquals(requestDTO.getLocation(), foundShow.getLocation());
    }

    @Test
    public void testFindAll() {

        List<CommentResponseDTO> allComments = commentService.findAll();

        assertFalse(allComments.isEmpty());
        assertEquals(5, allComments.size());

    }

    @Test
    public void testFindAllPaged() {

        PageRequestDTO pageRequest = new PageRequestDTO();
        pageRequest.setPageNumber(0);
        pageRequest.setPageSize(4);

        CollectionResponseDTO<CommentResponseDTO> allCommentsCollection = commentService.findAllPaged(pageRequest);
        List<CommentResponseDTO> allComments = allCommentsCollection.getItems();

        assertFalse(allComments.isEmpty());
        assertEquals(4, allComments.size());
    }
    @Test
    public void testDeleteById() {

        ShowRequestDTO showRequestDTO = makeShowRequestDTO();

        UserRequestDTO userRequestDTO = makeUserRequestDTO();

        UUID showId = showService.save(showRequestDTO).getShowId();
        UUID userId = userService.save(userRequestDTO).getUserId();

        userRequestDTO.setUserId(userId);
        showRequestDTO.setShowId(showId);

        CommentRequestDTO commentRequestDTO = new CommentRequestDTO();
        commentRequestDTO.setDescription("DE STERS");
        commentRequestDTO.setUser(userRequestDTO);
        commentRequestDTO.setShow(showRequestDTO);
        commentRequestDTO.setCommentDate(LocalDate.now());

        List<CommentResponseDTO> allComments = commentService.findAll();

        assertFalse(allComments.isEmpty());
        assertEquals(5, allComments.size());


        CommentResponseDTO commentResponseDTO = commentService.save(commentRequestDTO);

        commentRequestDTO.setCommentId(commentResponseDTO.getCommentId());

        commentService.deleteById(commentRequestDTO.getCommentId());

        allComments = commentService.findAll();

        assertFalse(allComments.isEmpty());
        assertEquals(5, allComments.size());
    }

}
