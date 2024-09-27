package ro.ps.showmgmtbackend.service.show;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import ro.ps.showmgmtbackend.dto.CollectionResponseDTO;
import ro.ps.showmgmtbackend.dto.PageRequestDTO;
import ro.ps.showmgmtbackend.dto.show.ShowRequestDTO;
import ro.ps.showmgmtbackend.dto.show.ShowResponseDTO;
import ro.ps.showmgmtbackend.exception.ExceptionCode;
import ro.ps.showmgmtbackend.exception.NotFoundException;
import ro.ps.showmgmtbackend.mapper.ShowMapper;
import ro.ps.showmgmtbackend.model.CommentEntity;
import ro.ps.showmgmtbackend.model.ShowEntity;
import ro.ps.showmgmtbackend.repository.ShowRepository;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
public class ShowServiceBean implements ShowService {
    private final ShowRepository showRepository;
    private final ShowMapper showMapper;
    private final String applicationName;

    @Override
    @Transactional
    public ShowResponseDTO findById(UUID showId) {
        ShowResponseDTO showResponseDTO = showRepository.findById(showId)
                .map(showMapper::showEntityToShowResponseDTO)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionCode.ERR001_SHOW_NOT_FOUND.getMessage(),
                        showId
                )));

        return showResponseDTO;
    }

    @Override
    @Transactional
    public void deleteById(UUID showId) {
        try {
            showRepository.deleteById(showId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(String.format(ExceptionCode.ERR001_SHOW_NOT_FOUND.getMessage(), showId));
        }
    }

    @Override
    @Transactional
    public void deleteAll() {
        try {
            showRepository.deleteAll();
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(String.format(ExceptionCode.ERR001_SHOW_NOT_FOUND.getMessage()));
        }
    }

    private <T> void setIfNotNull(T value, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }

    @Override
    @Transactional
    public ShowResponseDTO update(ShowRequestDTO showRequestDTO) {
        ShowEntity showToBeUpdated = showRepository.findById(showRequestDTO.getShowId())
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionCode.ERR001_SHOW_NOT_FOUND.getMessage(),
                        showRequestDTO.getShowId()
                )));

        setIfNotNull(showRequestDTO.getShowId(), showToBeUpdated::setShowId);
        setIfNotNull(showRequestDTO.getName(), showToBeUpdated::setName);
        setIfNotNull(showRequestDTO.getPrice(), showToBeUpdated::setPrice);
        setIfNotNull(showRequestDTO.getLocation(), showToBeUpdated::setLocation);
        setIfNotNull(showRequestDTO.getEventDate(), showToBeUpdated::setEventDate);
        setIfNotNull(showRequestDTO.getDescription(), showToBeUpdated::setDescription);
        setIfNotNull(showRequestDTO.getNumberOfTicketsLeft(), showToBeUpdated::setNumberOfTicketsLeft);

        ShowEntity showUpdated = showRepository.save(showToBeUpdated);

        return showMapper.showEntityToShowResponseDTO(showUpdated);

    }

    @Override
    @Transactional
    public List<ShowResponseDTO> findAll() {
        log.info("Getting all shows for application {}", applicationName);

        List<ShowEntity> showEntityList = showRepository.findAll();
        showEntityList.sort(Comparator.comparing(ShowEntity::getEventDate).reversed());

        return showMapper.showEntityListToShowResponseDTOList(showEntityList);
    }

    @Override
    @Transactional
    public CollectionResponseDTO<ShowResponseDTO> findAllPaged(PageRequestDTO page) {
        Page<ShowEntity> showEntityList = showRepository.findAll(PageRequest.of(
                page.getPageNumber(),
                page.getPageSize()
        ));
        List<ShowResponseDTO> shows = showMapper.showEntityListToShowResponseDTOList(showEntityList.getContent());
        return new CollectionResponseDTO<>(shows, showEntityList.getTotalElements());
    }

    @Override
    @Transactional
    public List<ShowResponseDTO> findAllSorted(String sortBy) {
        List<ShowEntity> showEntityList = showRepository.findAll(Sort.by(sortBy).descending());

        return showMapper.showEntityListToShowResponseDTOList(showEntityList);
    }

    /*
        @Override
        public List<ShowResponseDTO> findAllByCity(UUID cityId) {
            List<ShowEntity> showEntityList = showRepository.findAllByCity(cityId);

            return showMapper.showEntityListToShowResponseDTOList(showEntityList);
        }
    */
    @Override
    @Transactional
    public ShowResponseDTO save(ShowRequestDTO showRequestDTO) {
        ShowEntity showToBeAdded = showMapper.showRequestDTOToShowEntity(showRequestDTO);
        ShowEntity showAdded = showRepository.save(showToBeAdded);

        return showMapper.showEntityToShowResponseDTO(showAdded);
    }
}
