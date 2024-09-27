package ro.ps.showmgmtbackend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ro.ps.showmgmtbackend.dto.show.ShowResponseDTO;
import ro.ps.showmgmtbackend.exception.NotFoundException;
import ro.ps.showmgmtbackend.mapper.ShowMapper;
import ro.ps.showmgmtbackend.model.ShowEntity;
import ro.ps.showmgmtbackend.repository.ShowRepository;
import ro.ps.showmgmtbackend.service.show.ShowServiceBean;

@SpringBootTest
class ShowServiceBeanTest {

    private static final UUID SHOW_ID = UUID.randomUUID();

    private ShowServiceBean underTest;

    @Mock
    private ShowRepository showRepositoryMock;

    @Mock
    private ShowMapper showMapperMock;

    @BeforeEach
    void setUp() {
        this.underTest = new ShowServiceBean(
                showRepositoryMock,
                showMapperMock,
                null
        );
    }

    @Test
    void givenValidShowId_whenFindByIdIsCalled_thenReturnShowResponseDTO() {
        final var showEntity = getShowEntity();
        final var showResponseDTO = getShowResponseDTO();
        when(showRepositoryMock.findById(any(UUID.class))).thenReturn(Optional.of(showEntity));
        when(showMapperMock.showEntityToShowResponseDTO(any(ShowEntity.class))).thenReturn(showResponseDTO);

        final var response = underTest.findById(SHOW_ID);

        assertThat(response).isEqualTo(showResponseDTO);
        verify(showRepositoryMock).findById(any(UUID.class));
        verify(showMapperMock).showEntityToShowResponseDTO(any(ShowEntity.class));
    }

    @Test
    void givenInvalidShowId_whenFindByIdIsCalled_thenThrowException() {
        when(showRepositoryMock.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.findById(SHOW_ID))
                .isInstanceOf(NotFoundException.class);
        verify(showRepositoryMock).findById(any(UUID.class));
        verify(showMapperMock, never()).showEntityToShowResponseDTO(any(ShowEntity.class));
    }

    private ShowEntity getShowEntity() {
        LocalDate localDate = LocalDate.of(2027, 5, 14);
        return ShowEntity.builder()
                .showId(SHOW_ID)
                .name("Stand-up comedy SHOW")
                .price(120.0f)
                .eventDate(localDate)
                .location("Sala Palatului")
                .description("E mare smecherie")
                .numberOfTicketsLeft(8000)
                .userShowEntitySet(null)
                .commentEntitySet(null)
                .build();
    }

    private ShowResponseDTO getShowResponseDTO() {
        LocalDate localDate = LocalDate.of(2027, 5, 14);
        return ShowResponseDTO.builder()
                .showId(SHOW_ID)
                .name("Stand-up comedy SHOW")
                .price(120.0f)
                .location("Sala Palatului")
                .eventDate(localDate)
                .description("E mare smecherie")
                .numberOfTicketsLeft(8000)
                .build();
    }
}