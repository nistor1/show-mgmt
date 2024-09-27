package ro.ps.showmgmtbackend.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import ro.ps.showmgmtbackend.dto.show.ShowResponseDTO;
import ro.ps.showmgmtbackend.mapper.ShowMapper;
import ro.ps.showmgmtbackend.model.ShowEntity;

@SpringBootTest
public class ShowMapperTest {

    private ShowMapper underTest;

    private static final UUID SHOW_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    @BeforeEach
    void setUp() {
        underTest = Mappers.getMapper(ShowMapper.class);
    }

    @Test
    void givenValidShowEntity_whenMapperCalled_thenReturnValidShowResponseDTO() {
        final var request = getShowEntity();
        final var expected = getShowResponseDTO();

        final var response = underTest.showEntityToShowResponseDTO(request);

        assertThat(response).isEqualTo(expected);
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
