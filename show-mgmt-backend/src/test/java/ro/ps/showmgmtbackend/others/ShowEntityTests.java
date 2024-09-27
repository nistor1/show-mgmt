package ro.ps.showmgmtbackend.others;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ro.ps.showmgmtbackend.dto.CollectionResponseDTO;
import ro.ps.showmgmtbackend.dto.show.ShowRequestDTO;
import ro.ps.showmgmtbackend.dto.show.ShowResponseDTO;
import ro.ps.showmgmtbackend.mapper.ShowMapper;
import ro.ps.showmgmtbackend.model.ShowEntity;
import ro.ps.showmgmtbackend.repository.ShowRepository;
import ro.ps.showmgmtbackend.service.show.ShowService;
import ro.ps.showmgmtbackend.dto.PageRequestDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Execution(ExecutionMode.SAME_THREAD)
public class ShowEntityTests {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private ShowService showService;

    @Autowired
    private ShowMapper showMapper;

    @Test
    public void testEntityMappings() {
        showService.deleteAll();

        ShowRequestDTO showRequestDTO = new ShowRequestDTO();
        showRequestDTO.setName("Show Stand-Up");
        showRequestDTO.setDescription("Primul show");
        showRequestDTO.setPrice(80.6f);
        showRequestDTO.setEventDate(LocalDate.now());
        showRequestDTO.setNumberOfTicketsLeft(5000);
        showRequestDTO.setLocation("Sala Palatului");

        assertEquals("Show Stand-Up", showRequestDTO.getName());
        assertEquals("Primul show", showRequestDTO.getDescription());
        assertEquals(80.6f, showRequestDTO.getPrice(), 0.01);
        assertNotNull(showRequestDTO.getEventDate());
        assertEquals(5000, showRequestDTO.getNumberOfTicketsLeft());
        assertEquals("Sala Palatului", showRequestDTO.getLocation());


    }
    @Test
    public void testShowRequestDTOToShowEntityToResponseDTO() {

        ShowRequestDTO showRequestDTO = new ShowRequestDTO();
        showRequestDTO.setName("Show Stand-Up");
        showRequestDTO.setDescription("Primul show");
        showRequestDTO.setPrice(80.6f);
        showRequestDTO.setEventDate(LocalDate.now());
        showRequestDTO.setNumberOfTicketsLeft(5000);
        showRequestDTO.setLocation("Sala Palatului");

        ShowEntity showEntity = showMapper.showRequestDTOToShowEntity(showRequestDTO);


        assertEquals("Show Stand-Up", showEntity.getName());
        assertEquals("Primul show", showEntity.getDescription());
        assertEquals(80.6f, showEntity.getPrice(), 0.01);
        assertNotNull(showEntity.getEventDate());
        assertEquals(5000, showEntity.getNumberOfTicketsLeft());
        assertEquals("Sala Palatului", showEntity.getLocation());

        ShowResponseDTO showResponseDTO = showMapper.showEntityToShowResponseDTO(showEntity);

        assertEquals("Show Stand-Up", showResponseDTO.getName());
        assertEquals("Primul show", showResponseDTO.getDescription());
        assertEquals(80.6f, showResponseDTO.getPrice(), 0.01);
        assertNotNull(showResponseDTO.getEventDate());
        assertEquals(5000, showResponseDTO.getNumberOfTicketsLeft());
        assertEquals("Sala Palatului", showResponseDTO.getLocation());
    }
    @Test
    public void testSave() {
        ShowRequestDTO requestDTO = new ShowRequestDTO();
        requestDTO.setName("Test Show");
        requestDTO.setDescription("Test Description");
        requestDTO.setPrice(50.0f);
        requestDTO.setEventDate(LocalDate.of(2024, 4, 10));
        requestDTO.setNumberOfTicketsLeft(100);
        requestDTO.setLocation("Test Location");

        ShowResponseDTO savedShow = showService.save(requestDTO);

        assertNotNull(savedShow);
        assertEquals(requestDTO.getName(), savedShow.getName());
        assertEquals(requestDTO.getDescription(), savedShow.getDescription());
        assertEquals(requestDTO.getPrice(), savedShow.getPrice());
        assertEquals(requestDTO.getEventDate(), savedShow.getEventDate());
        assertEquals(requestDTO.getNumberOfTicketsLeft(), savedShow.getNumberOfTicketsLeft());
        assertEquals(requestDTO.getLocation(), savedShow.getLocation());
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
    public void testSaveAndUpdateById() {
        ShowRequestDTO requestDTO = new ShowRequestDTO();
        requestDTO.setName("Test Show");
        requestDTO.setDescription("Test Description");
        requestDTO.setPrice(50.0f);
        requestDTO.setEventDate(LocalDate.of(2024, 4, 10));
        requestDTO.setNumberOfTicketsLeft(100);
        requestDTO.setLocation("Test Location");


        ShowResponseDTO savedShow = showService.save(requestDTO);
        UUID showId = savedShow.getShowId();

        ShowRequestDTO showRequestDTO = new ShowRequestDTO();
        showRequestDTO.setShowId(savedShow.getShowId());
        showRequestDTO.setName("Show Stand-Up");
        showRequestDTO.setDescription("Primul show");
        showRequestDTO.setEventDate(LocalDate.now());
        showRequestDTO.setNumberOfTicketsLeft(5000);
        showRequestDTO.setLocation("Sala Palatului");

        ShowResponseDTO updatedShow = showService.update(showRequestDTO);

        assertNotNull(updatedShow);
        assertEquals(showRequestDTO.getName(), updatedShow.getName());
        assertEquals(showRequestDTO.getDescription(), updatedShow.getDescription());
        assertEquals(savedShow.getPrice(), updatedShow.getPrice());
        assertEquals(showRequestDTO.getEventDate(), updatedShow.getEventDate());
        assertEquals(showRequestDTO.getNumberOfTicketsLeft(), updatedShow.getNumberOfTicketsLeft());
        assertEquals(showRequestDTO.getLocation(), updatedShow.getLocation());
    }

    @Test
    public void testFindAll() {
        showService.deleteAll();

        ShowRequestDTO show1 = new ShowRequestDTO();
        show1.setName("Show 1");
        show1.setDescription("Description for Show 1");
        show1.setPrice(50.0f);
        show1.setEventDate(LocalDate.of(2024, 4, 10));
        show1.setNumberOfTicketsLeft(100);
        show1.setLocation("Location 1");

        ShowRequestDTO show2 = new ShowRequestDTO();
        show2.setName("Show 2");
        show2.setDescription("Description for Show 2");
        show2.setPrice(60.0f);
        show2.setEventDate(LocalDate.of(2024, 4, 15));
        show2.setNumberOfTicketsLeft(200);
        show2.setLocation("Location 2");

        List<ShowResponseDTO> beforeSave = showService.findAll();

        assertEquals(0, beforeSave.size());

        showService.save(show1);
        showService.save(show2);

        List<ShowResponseDTO> allShows = showService.findAll();

        assertFalse(allShows.isEmpty());
        assertEquals(2, allShows.size());

        assertTrue(allShows.stream().anyMatch(show -> show.getName().equals(show1.getName())));
        assertTrue(allShows.stream().anyMatch(show -> show.getName().equals(show2.getName())));
        showService.deleteAll();

    }

    @Test
    public void testFindAllPaged() {
        showService.deleteAll();

        ShowRequestDTO show1 = new ShowRequestDTO();
        show1.setName("Show 1");
        show1.setDescription("Description for Show 1");
        show1.setPrice(50.0f);
        show1.setEventDate(LocalDate.of(2024, 4, 10));
        show1.setNumberOfTicketsLeft(100);
        show1.setLocation("Location 1");

        ShowRequestDTO show2 = new ShowRequestDTO();
        show2.setName("Show 2");
        show2.setDescription("Description for Show 2");
        show2.setPrice(60.0f);
        show2.setEventDate(LocalDate.of(2024, 4, 15));
        show2.setNumberOfTicketsLeft(200);
        show2.setLocation("Location 2");

        showService.save(show1);
        showService.save(show2);
        showService.save(show1);
        showService.save(show2);
        showService.save(show1);
        showService.save(show2);

        PageRequestDTO pageRequest = new PageRequestDTO();
        pageRequest.setPageNumber(0);
        pageRequest.setPageSize(5);

        CollectionResponseDTO<ShowResponseDTO> allShowsCollection = showService.findAllPaged(pageRequest);
        List<ShowResponseDTO> allShows = allShowsCollection.getItems();

        assertFalse(allShows.isEmpty());
        assertEquals(5, allShows.size());
        showService.deleteAll();

    }
    @Test
    public void testDeleteShow() {
        showService.deleteAll();

        ShowRequestDTO show1 = new ShowRequestDTO();
        show1.setName("Show de sters");
        show1.setDescription("Description for Show 1");
        show1.setPrice(50.0f);
        show1.setEventDate(LocalDate.of(2024, 4, 10));
        show1.setNumberOfTicketsLeft(100);
        show1.setLocation("Location 1");

        List<ShowResponseDTO> allShowsBeforeInsertAndDelete = showService.findAll();

        ShowResponseDTO savedShow = showService.save(show1);
        showService.deleteById(savedShow.getShowId());

        List<ShowResponseDTO> allShowsAfterInsertAndDelete = showService.findAll();


        assertEquals(allShowsBeforeInsertAndDelete.size(), allShowsAfterInsertAndDelete.size());

        showService.deleteAll();
    }

}
