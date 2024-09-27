package ro.ps.showmgmtbackend.service.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ro.ps.showmgmtbackend.dto.CollectionResponseDTO;
import ro.ps.showmgmtbackend.dto.PageRequestDTO;
import ro.ps.showmgmtbackend.dto.comment.CommentRequestDTO;
import ro.ps.showmgmtbackend.dto.comment.CommentResponseDTO;
import ro.ps.showmgmtbackend.dto.show.ShowRequestDTO;
import ro.ps.showmgmtbackend.exception.ExceptionCode;
import ro.ps.showmgmtbackend.exception.NotFoundException;
import ro.ps.showmgmtbackend.mapper.CommentMapper;
import ro.ps.showmgmtbackend.mapper.ShowMapper;
import ro.ps.showmgmtbackend.mapper.UserMapper;
import ro.ps.showmgmtbackend.model.CommentEntity;
import ro.ps.showmgmtbackend.repository.CommentRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class CommentServiceBean implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserMapper userMapper;
    private final ShowMapper showMapper;

    private final String applicationName;

    @Override
    public CommentResponseDTO findById(UUID commentId) {
        CommentResponseDTO commentResponseDTO = commentRepository.findById(commentId)
                .map(commentMapper::commentEntityToCommentResponseDTO)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionCode.ERR001_SHOW_NOT_FOUND.getMessage(),
                        commentId
                )));

        return commentResponseDTO;
    }

    @Override
    public void deleteById(UUID commentId) {
        try {
            commentRepository.deleteById(commentId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(ExceptionCode.ERR001_SHOW_NOT_FOUND.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        try {
            commentRepository.deleteAll();
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(String.format(ExceptionCode.ERR001_SHOW_NOT_FOUND.getMessage()));
        }
    }

    @Override
    public List<CommentResponseDTO> findAll() {
        log.info("Getting all comments for application {}", applicationName);

        List<CommentEntity> commentEntityList = commentRepository.findAll();

        return commentMapper.commentEntityListToCommentResponseDTOList(commentEntityList);
    }

    @Override
    public CollectionResponseDTO<CommentResponseDTO> findAllPaged(PageRequestDTO page) {
        Page<CommentEntity> commentEntityList = commentRepository.findAll(PageRequest.of(
                page.getPageNumber(),
                page.getPageSize()
        ));
        List<CommentResponseDTO> commentResponseDTOList = commentMapper.commentEntityListToCommentResponseDTOList(commentEntityList.getContent());
        return new CollectionResponseDTO<>(commentResponseDTOList, commentEntityList.getTotalElements());
    }

    @Override
    public List<CommentResponseDTO> findByShow(ShowRequestDTO showRequestDTO) {
        log.info("Getting all comments for show with ID {} for application {}", showRequestDTO.getShowId(), applicationName);

        List<CommentEntity> commentEntityList = commentRepository.findByShow(showMapper.showRequestDTOToShowEntity(showRequestDTO));
        commentEntityList.sort(Comparator.comparing(CommentEntity::getCommentDate).reversed());

        return commentMapper.commentEntityListToCommentResponseDTOList(commentEntityList);
    }

    @Override
    public CollectionResponseDTO<CommentResponseDTO> findAllCommentsFromShowPaged(ShowRequestDTO showRequestDTO, PageRequestDTO page) {
        Page<CommentEntity> commentEntityList = commentRepository.findByShow(showMapper.showRequestDTOToShowEntity(showRequestDTO),
                PageRequest.of(
                        page.getPageNumber(),
                        page.getPageSize()
                ));
        List<CommentResponseDTO> commentResponseDTOList = commentMapper.commentEntityListToCommentResponseDTOList(commentEntityList.getContent());
        return new CollectionResponseDTO<>(commentResponseDTOList, commentEntityList.getTotalElements());
    }

    @Override
    public CommentResponseDTO save(CommentRequestDTO commentRequestDTO) {
        if (commentRequestDTO.getShow() == null || commentRequestDTO.getUser() == null) {
            throw new NotFoundException(String.format(ExceptionCode.ERR099_INVALID_CREDENTIALS.getMessage()));
        }
        commentRequestDTO.setCommentDate(LocalDate.now());


        CommentEntity commentToBeAdded = commentMapper.commentRequestDTOToCommentEntity(commentRequestDTO);

        CommentEntity commentAdded = commentRepository.save(commentToBeAdded);

        return commentMapper.commentEntityToCommentResponseDTO(commentAdded);
    }
}
