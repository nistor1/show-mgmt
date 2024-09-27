package ro.ps.showmgmtbackend.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import ro.ps.showmgmtbackend.dto.CollectionResponseDTO;
import ro.ps.showmgmtbackend.dto.PageRequestDTO;
import ro.ps.showmgmtbackend.dto.user.UserRequestDTO;
import ro.ps.showmgmtbackend.dto.user.UserResponseDTO;
import ro.ps.showmgmtbackend.exception.ExceptionCode;
import ro.ps.showmgmtbackend.exception.NotFoundException;
import ro.ps.showmgmtbackend.mapper.UserMapper;
import ro.ps.showmgmtbackend.model.UserEntity;
import ro.ps.showmgmtbackend.repository.UserRepository;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
public class UserServiceBean implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final String applicationName;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO findById(UUID userId) {
        UserResponseDTO userResponseDTO = userRepository.findById(userId)
                .map(userMapper::userEntityToUserResponseDTO)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionCode.ERR001_SHOW_NOT_FOUND.getMessage(),
                        userId
                )));

        return userResponseDTO;
    }

    @Override
    public void deleteById(UUID userId) {
        try {
            userRepository.deleteById(userId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(String.format(ExceptionCode.ERR001_SHOW_NOT_FOUND.getMessage(), userId));
        }
    }

    @Override
    public void deleteAll() {
        try {
            userRepository.deleteAll();
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
    public UserResponseDTO update(UserRequestDTO userRequestDTO) {
        UserEntity userToBeUpdated = userRepository.findById(userRequestDTO.getUserId())
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionCode.ERR001_SHOW_NOT_FOUND.getMessage(),
                        userRequestDTO.getUserId()
                )));

        setIfNotNull(userRequestDTO.getUserId(), userToBeUpdated::setUserId);
        setIfNotNull(userRequestDTO.getName(), userToBeUpdated::setName);
        setIfNotNull(userRequestDTO.getRole(), userToBeUpdated::setRole);
        setIfNotNull(userRequestDTO.getEmail(), userToBeUpdated::setEmail);
        setIfNotNull(userRequestDTO.getAge(), userToBeUpdated::setAge);
        setIfNotNull(passwordEncoder.encode(userRequestDTO.getPassword()), userToBeUpdated::setPassword);

        UserEntity userUpdated = userRepository.save(userToBeUpdated);

        return userMapper.userEntityToUserResponseDTO(userUpdated);

    }

    @Override
    public List<UserResponseDTO> findAll() {
        log.info("Getting all shows for application {}", applicationName);

        List<UserEntity> userEntityList = userRepository.findAll();

        return userMapper.userEntityListToUserResponseDTOList(userEntityList);
    }

    @Override
    public CollectionResponseDTO<UserResponseDTO> findAllPaged(PageRequestDTO page) {
        Page<UserEntity> userEntityPage = userRepository.findAll(PageRequest.of(
                page.getPageNumber(),
                page.getPageSize()
        ));
        List<UserResponseDTO> users = userMapper.userEntityListToUserResponseDTOList(userEntityPage.getContent());
        return new CollectionResponseDTO<>(users, userEntityPage.getTotalElements());
    }

    @Override
    public List<UserResponseDTO> findAllSorted(String sortBy) {
        List<UserEntity> userEntityList = userRepository.findAll(Sort.by(sortBy).descending());

        return userMapper.userEntityListToUserResponseDTOList(userEntityList);
    }

    @Override
    public UserResponseDTO findByEmail(String email) {
        UserResponseDTO userResponseDTO = userRepository.findByEmail(email)
                .map(userMapper::userEntityToUserResponseDTO)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionCode.ERR001_SHOW_NOT_FOUND.getMessage(),
                        email
                )));
        return userResponseDTO;
    }

    @Override
    public UserResponseDTO save(UserRequestDTO userRequestDTO) {
        UserEntity userToBeAdded = userMapper.userRequestDTOToUserEntity(userRequestDTO);
        userToBeAdded.setPassword(passwordEncoder.encode(userToBeAdded.getPassword()));
        UserEntity userAdded = userRepository.save(userToBeAdded);

        return userMapper.userEntityToUserResponseDTO(userAdded);
    }

    @Override
    public CollectionResponseDTO<UserResponseDTO> findByIdPaged(String searchBy, PageRequestDTO pageRequestDTO) {
        Page<UserEntity> userEntityPage = userRepository.findAll(
                PageRequest.of(pageRequestDTO.getPageNumber(), pageRequestDTO.getPageSize()),
                searchBy
        );

        List<UserResponseDTO> users = userMapper.userEntityListToUserResponseDTOList(userEntityPage.getContent());
        return new CollectionResponseDTO<>(users, userEntityPage.getTotalElements());
    }

}
