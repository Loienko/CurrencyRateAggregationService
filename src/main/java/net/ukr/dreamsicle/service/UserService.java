package net.ukr.dreamsicle.service;

import lombok.AllArgsConstructor;
import net.ukr.dreamsicle.config.ApplicationConfig;
import net.ukr.dreamsicle.dto.UserDTO;
import net.ukr.dreamsicle.dto.UserMapper;
import net.ukr.dreamsicle.dto.UsernameAndPasswordDataDTO;
import net.ukr.dreamsicle.exception.CustomDataAlreadyExistsException;
import net.ukr.dreamsicle.exception.ResourceNotFoundException;
import net.ukr.dreamsicle.model.Role;
import net.ukr.dreamsicle.model.RoleType;
import net.ukr.dreamsicle.model.User;
import net.ukr.dreamsicle.repository.RoleRepository;
import net.ukr.dreamsicle.repository.UserRepository;
import net.ukr.dreamsicle.security.jwt.JwtProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static net.ukr.dreamsicle.model.StatusType.ACTIVE;
import static net.ukr.dreamsicle.model.StatusType.DELETED;

@Service
@AllArgsConstructor
public class UserService {

    private static final String BEARER = "Bearer ";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final JwtProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final ApplicationConfig applicationConfig;

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public UserDTO createUser(UserDTO userDTO) {
        if (Boolean.TRUE.equals(userRepository.existsByUsername(userDTO.getUsername()))) {
            throw new CustomDataAlreadyExistsException("Username is already in use!");
        }

        if (Boolean.TRUE.equals(userRepository.existsByEmail(userDTO.getEmail()))) {
            throw new CustomDataAlreadyExistsException("Email is already in use!");
        }

        User user = User.builder()
                .name(userDTO.getName())
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .password(applicationConfig.passwordEncoder().encode(UUID.randomUUID().toString()))
                .roles(acquireRoles(userDTO))
                .created(Timestamp.valueOf(LocalDateTime.now()))
                .updated(Timestamp.valueOf(LocalDateTime.now()))
                .status(ACTIVE)
                .build();
        return userMapper.userToUserDto(userRepository.saveAndFlush(user));
    }

    private Set<Role> acquireRoles(UserDTO user) {
        return user.getRole().stream()
                .map(role -> roleRepository.findByName(RoleType.getEnumFromString(role)).orElseThrow(ResourceNotFoundException::new))
                .collect(Collectors.toSet());
    }

    public String authenticateUser(UsernameAndPasswordDataDTO usernameAndPasswordDataDTO) {
        User userByUsername = userRepository.findByUsername(usernameAndPasswordDataDTO.getUsername()).orElseThrow(ResourceNotFoundException::new);
        User user = userRepository.findByIdAndStatus(userByUsername.getId(), ACTIVE).orElseThrow(ResourceNotFoundException::new);

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        usernameAndPasswordDataDTO.getPassword()
                )
        );
        return BEARER + tokenProvider.createToken(authenticate);
    }

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public UserDTO updateUser(long id, UserDTO userDTO) {
        User userUpdateById = userRepository.findByIdAndStatus(id, ACTIVE).orElseThrow(ResourceNotFoundException::new);

        User actualUser = userMapper.userDtoToUser(userDTO);

        userUpdateById.setName(actualUser.getName());
        userUpdateById.setUsername(actualUser.getUsername());
        userUpdateById.setEmail(actualUser.getEmail());
        userUpdateById.setRoles(actualUser.getRoles());

        return userMapper.userToUserDto(userRepository.saveAndFlush(userUpdateById));
    }

    public Page<UserDTO> findAllUsers(Pageable pageable) {
        return userMapper.userToUserDTOs(userRepository.findAllByStatus(pageable, ACTIVE));
    }

    public UserDTO findUserById(long id) {
        return userRepository
                .findByIdAndStatus(id, ACTIVE)
                .map(userMapper::userToUserDto)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void deleteUser(long id) {
        User user = userRepository.findByIdAndStatus(id, ACTIVE)
                .orElseThrow(ResourceNotFoundException::new);
        user.setStatus(DELETED);
        userRepository.saveAndFlush(user);
    }

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public UsernameAndPasswordDataDTO assignPassword(UsernameAndPasswordDataDTO usernameAndPasswordDataDTO) {
        User user = userRepository.findByUsername(usernameAndPasswordDataDTO.getUsername()).orElseThrow(ResourceNotFoundException::new);

        User actualUser = userMapper.usernameAndPasswordDataDTOToUser(usernameAndPasswordDataDTO);
        user.setPassword(applicationConfig.passwordEncoder().encode(actualUser.getPassword()));
        user.setUpdated(Timestamp.valueOf(LocalDateTime.now()));

        return userMapper.userToUsernameAndPasswordDataDTO(userRepository.saveAndFlush(user));
    }
}