package net.ukr.dreamsicle.service;

import lombok.AllArgsConstructor;
import net.ukr.dreamsicle.config.ApplicationConfig;
import net.ukr.dreamsicle.dto.UserDTO;
import net.ukr.dreamsicle.dto.UserLoginDto;
import net.ukr.dreamsicle.dto.UserMapper;
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
import java.util.Set;
import java.util.stream.Collectors;

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

        User users = User.builder()
                .name(userDTO.getName())
                .username(userDTO.getUsername())
                .password(applicationConfig.passwordEncoder().encode(userDTO.getPassword()))
                .roles(acquireRoles(userDTO))
                .build();
        return userMapper.toUserDto(userRepository.save(users));
    }

    private Set<Role> acquireRoles(UserDTO user) {
        return user.getRole().stream()
                .map(role -> roleRepository.findByName(RoleType.getEnumFromString(role)).orElseThrow(ResourceNotFoundException::new))
                .collect(Collectors.toSet());
    }

    public String authenticateUser(UserLoginDto userLoginDto) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginDto.getUsername(),
                        userLoginDto.getPassword()
                )
        );
        return BEARER + tokenProvider.createToken(authenticate);
    }

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public UserDTO updateUser(long id, UserDTO userDTO) {
        User userUpdateById = userRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        User actualUser = userMapper.toUser(userDTO);

        userUpdateById.setName(actualUser.getName());
        userUpdateById.setUsername(actualUser.getUsername());
        userUpdateById.setPassword(actualUser.getPassword());
        userUpdateById.setRoles(actualUser.getRoles());

        return userMapper.toUserDto(userRepository.saveAndFlush(userUpdateById));
    }

    public Page<UserDTO> findAllUsers(Pageable pageable) {
        return userMapper.toUserDTOs(userRepository.findAll(pageable));
    }

    public UserDTO findUserById(long id) {
        return userRepository
                .findById(id)
                .map(userMapper::toUserDto)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void deleteUser(long id) {
        userRepository.deleteById(
                userRepository.findById(id)
                        .orElseThrow(ResourceNotFoundException::new)
                        .getId());
    }
}