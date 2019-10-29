package net.ukr.dreamsicle.service;

import net.ukr.dreamsicle.config.ApplicationConfig;
import net.ukr.dreamsicle.dto.user.UserDTO;
import net.ukr.dreamsicle.dto.user.UserMapper;
import net.ukr.dreamsicle.dto.user.UsernameAndPasswordDataDTO;
import net.ukr.dreamsicle.exception.CustomDataAlreadyExistsException;
import net.ukr.dreamsicle.exception.ResourceNotFoundException;
import net.ukr.dreamsicle.model.user.Role;
import net.ukr.dreamsicle.model.user.StatusType;
import net.ukr.dreamsicle.model.user.User;
import net.ukr.dreamsicle.repository.RoleRepository;
import net.ukr.dreamsicle.repository.UserRepository;
import net.ukr.dreamsicle.security.jwt.JwtProvider;
import net.ukr.dreamsicle.service.UserService;
import org.hibernate.TransactionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static net.ukr.dreamsicle.util.user.UserProvider.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private Page<UserDTO> userDTOPage;
    @Mock
    private Page<User> userPage;
    @Mock
    private Pageable pageable;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private ApplicationConfig applicationConfig;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtProvider tokenProvider;
    @Mock
    private Authentication authentication;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private Role role;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testFindAllUsers() {
        when(userRepository.findAllByStatus(pageable, STATUS_TYPE_ACTIVE)).thenReturn(userPage);
        when(userMapper.userToUserDTOs(userPage)).thenReturn(userDTOPage);

        Page<UserDTO> actualUsers = userService.findAllUsers(pageable);

        verify(userRepository).findAllByStatus(pageable, STATUS_TYPE_ACTIVE);
        assertNotNull(actualUsers);
        assertEquals(userDTOPage, actualUsers);
    }

    @Test
    void testFindUserById() {
        User user = getUserProvider(STATUS_TYPE_ACTIVE);
        UserDTO userDTO = getUserDtoProvider();
        when(userRepository.findByIdAndStatus(ID, STATUS_TYPE_ACTIVE)).thenReturn(Optional.of(user));
        when(userMapper.userToUserDto(user)).thenReturn(userDTO);

        UserDTO actualUser = userService.findUserById(ID);

        assertEquals(userDTO, actualUser);
        assertNotNull(actualUser);
        assertEquals(userDTO.getId(), actualUser.getId());
        assertEquals(userDTO.getName(), actualUser.getName());
        assertEquals(userDTO.getUsername(), actualUser.getUsername());
        assertEquals(userDTO.getEmail(), actualUser.getEmail());
        assertEquals(userDTO.getRole(), actualUser.getRole());
    }

    @Test
    void testFindUserByIdIsNotPresentInDb() {
        when(userRepository.findByIdAndStatus(ID, STATUS_TYPE_ACTIVE)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.findUserById(ID));
    }

    @Test
    void testFindUserByIdWithStatusDeleted() {
        when(userRepository.findByIdAndStatus(ID, STATUS_TYPE_DELETED)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.findUserById(ID));
    }

    @Test
    void testFindUserByIdWithStatusNotActive() {
        when(userRepository.findByIdAndStatus(ID, STATUS_TYPE_NOT_ACTIVE)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.findUserById(ID));
    }

    @Test
    void testUpdateUser() {
        User user = getUserProvider(STATUS_TYPE_ACTIVE);
        UserDTO userDTO = getUserDtoProvider();
        when(userRepository.findByIdAndStatus(ID, STATUS_TYPE_ACTIVE)).thenReturn(Optional.of(user));
        when(userRepository.existsByUsername(userDTO.getUsername())).thenReturn(Boolean.FALSE);
        when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(Boolean.FALSE);
        when(userMapper.userDtoToUser(userDTO)).thenReturn(user);
        when(roleRepository.findByName(ROLE_TYPE)).thenReturn(Optional.of(role));
        when(userRepository.saveAndFlush(user)).thenReturn(user);
        when(userMapper.userToUserDto(user)).thenReturn(userDTO);

        UserDTO actualUser = userService.updateUser(ID, userDTO);

        verify(userRepository).saveAndFlush(user);
        assertNotNull(actualUser);
        assertEquals(userDTO.getId(), actualUser.getId());
        assertEquals(userDTO.getName(), actualUser.getName());
        assertEquals(userDTO.getUsername(), actualUser.getUsername());
        assertEquals(userDTO.getEmail(), actualUser.getEmail());
        assertEquals(userDTO.getRole(), actualUser.getRole());
    }

    @Test
    void testUpdateUserIsNotPresentInDb() {
        UserDTO userDTO = getUserDtoProvider();
        when(userRepository.findByIdAndStatus(ID, STATUS_TYPE_ACTIVE)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(ID, userDTO));
    }

    @Test
    void testUpdateUserWithStatusDeleted() {
        UserDTO userDTO = getUserDtoProvider();
        when(userRepository.findByIdAndStatus(ID, STATUS_TYPE_DELETED)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(ID, userDTO));
    }

    @Test
    void testUpdateUserWithStatusNotActive() {
        UserDTO userDTO = getUserDtoProvider();
        when(userRepository.findByIdAndStatus(ID, STATUS_TYPE_NOT_ACTIVE)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(ID, userDTO));
    }

    @Test
    void testUpdateUserReturnedTransactionException() {
        User user = getUserProvider(STATUS_TYPE_ACTIVE);
        UserDTO userDTO = getUserDtoProvider();
        when(userRepository.findByIdAndStatus(ID, STATUS_TYPE_ACTIVE)).thenReturn(Optional.of(user));
        when(userMapper.userDtoToUser(userDTO)).thenReturn(user);
        when(roleRepository.findByName(ROLE_TYPE)).thenReturn(Optional.of(role));
        when(userRepository.saveAndFlush(user)).thenThrow(TransactionException.class);

        assertThrows(TransactionException.class, () -> userService.updateUser(ID, userDTO));
    }

    @Test
    void testUpdateUserIsUserNotUniqueInDB() {
        User user = getUserProvider(STATUS_TYPE_ACTIVE);
        UserDTO userDTO = getUserDtoProvider();
        userDTO.setUsername("qwerty");
        when(userRepository.findByIdAndStatus(ID, STATUS_TYPE_ACTIVE)).thenReturn(Optional.of(user));
        when(userRepository.existsByUsername(userDTO.getUsername())).thenReturn(Boolean.TRUE).thenThrow(CustomDataAlreadyExistsException.class);

        assertThrows(CustomDataAlreadyExistsException.class, () -> userService.updateUser(ID, userDTO));
    }

    @Test
    void testUpdateUserIsEmailNotUniqueInDB() {
        User user = getUserProvider(STATUS_TYPE_ACTIVE);
        UserDTO userDTO = getUserDtoProvider();
        userDTO.setEmail("qwerty@gmail.com");
        when(userRepository.findByIdAndStatus(ID, STATUS_TYPE_ACTIVE)).thenReturn(Optional.of(user));
        when(userRepository.existsByUsername(userDTO.getUsername())).thenReturn(Boolean.FALSE);
        when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(Boolean.TRUE).thenThrow(CustomDataAlreadyExistsException.class);

        assertThrows(CustomDataAlreadyExistsException.class, () -> userService.updateUser(ID, userDTO));
    }

    @Test
    void testDeleteUser() {
        User user = getUserProvider(STATUS_TYPE_ACTIVE);
        User userDeletedStatus = getUserProvider(STATUS_TYPE_DELETED);
        when(userRepository.findByIdAndStatus(ID, STATUS_TYPE_ACTIVE)).thenReturn(Optional.of(user));
        when(userRepository.saveAndFlush(userDeletedStatus)).thenReturn(userDeletedStatus);

        userService.deleteUser(ID);

        assertEquals(STATUS_TYPE_DELETED, userRepository.findByIdAndStatus(ID, StatusType.ACTIVE).get().getStatus());
    }

    @Test
    void testDeleteUserByIdNotPresentUserInDb() {
        when(userRepository.findByIdAndStatus(ID, STATUS_TYPE_ACTIVE)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(ID));
    }

    @Test
    void testDeleteUserByIdWithStatusDeleted() {
        when(userRepository.findByIdAndStatus(ID, STATUS_TYPE_DELETED)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(ID));
    }

    @Test
    void testDeleteUserByIdWithStatusNotActive() {
        when(userRepository.findByIdAndStatus(ID, STATUS_TYPE_NOT_ACTIVE)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(ID));
    }

    @Test
    void testCreateUser() {
        User user = getUserProvider(STATUS_TYPE_ACTIVE);
        UserDTO userDTO = getUserDtoProvider();
        when(userRepository.existsByUsername(userDTO.getUsername())).thenReturn(Boolean.FALSE);
        when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(Boolean.FALSE);
        when(applicationConfig.passwordEncoder()).thenReturn(bCryptPasswordEncoder);
        when(roleRepository.findByName(ROLE_TYPE)).thenReturn(Optional.of(role));
        when(userRepository.saveAndFlush(any(User.class))).thenReturn(user);
        when(userMapper.userToUserDto(user)).thenReturn(userDTO);

        UserDTO actualUser = userService.createUser(userDTO);

        assertNotNull(actualUser);
        assertEquals(userDTO.getId(), actualUser.getId());
        assertEquals(userDTO.getName(), actualUser.getName());
        assertEquals(userDTO.getUsername(), actualUser.getUsername());
        assertEquals(userDTO.getEmail(), actualUser.getEmail());
        assertEquals(userDTO.getRole(), actualUser.getRole());
    }

    @Test
    void testCreateUserUserNotUniqueInDB() {
        UserDTO userDTO = getUserDtoProvider();
        when(userRepository.existsByUsername(userDTO.getUsername())).thenReturn(Boolean.TRUE).thenThrow(CustomDataAlreadyExistsException.class);

        assertThrows(CustomDataAlreadyExistsException.class, () -> userService.createUser(userDTO));
    }

    @Test
    void testCreateUserEmailNotUniqueInDB() {
        UserDTO userDTO = getUserDtoProvider();
        when(userRepository.existsByUsername(userDTO.getUsername())).thenReturn(Boolean.FALSE);
        when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(Boolean.TRUE).thenThrow(CustomDataAlreadyExistsException.class);

        assertThrows(CustomDataAlreadyExistsException.class, () -> userService.createUser(userDTO));
    }

    @Test
    void testCreateUserNotPresentRoleInDb() {
        UserDTO userDTO = getUserDtoProvider();
        when(userRepository.existsByUsername(userDTO.getUsername())).thenReturn(Boolean.FALSE);
        when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(Boolean.FALSE);
        when(applicationConfig.passwordEncoder()).thenReturn(bCryptPasswordEncoder);
        when(roleRepository.findByName(ROLE_TYPE)).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> userService.createUser(userDTO));
    }

    @Test
    void testAuthenticateUser() {
        User user = getUserProvider(STATUS_TYPE_ACTIVE);
        UsernameAndPasswordDataDTO usernameAndPasswordDataDTO = getUsernameAndPasswordDataDTO();
        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken();
        when(userRepository.findByUsername(usernameAndPasswordDataDTO.getUsername())).thenReturn(Optional.of(user));
        when(userRepository.findByIdAndStatus(user.getId(), STATUS_TYPE_ACTIVE)).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(authenticationToken)).thenReturn(authentication);
        when(tokenProvider.createToken(authentication)).thenReturn(TOKEN_WITH_PASSED_DATE);

        String actualToken = userService.login(usernameAndPasswordDataDTO);

        assertNotNull(actualToken);
        assertEquals(BEARER + TOKEN_WITH_PASSED_DATE + CAUTION, actualToken);
    }

    @Test
    void testAuthenticateUserByUserNameNotPresentUserInDb() {
        UsernameAndPasswordDataDTO usernameAndPasswordDataDTO = getUsernameAndPasswordDataDTO();
        when(userRepository.findByUsername(usernameAndPasswordDataDTO.getUsername())).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> userService.login(usernameAndPasswordDataDTO));
    }

    @Test
    void testAuthenticateUserByIdNotPresentUserInDb() {
        User user = getUserProvider(STATUS_TYPE_ACTIVE);
        UsernameAndPasswordDataDTO usernameAndPasswordDataDTO = getUsernameAndPasswordDataDTO();
        when(userRepository.findByUsername(usernameAndPasswordDataDTO.getUsername())).thenReturn(Optional.of(user));
        when(userRepository.findByIdAndStatus(user.getId(), STATUS_TYPE_ACTIVE)).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> userService.login(usernameAndPasswordDataDTO));
    }

    @Test
    void testAssignPassword() {
        User user = getUserProvider(STATUS_TYPE_ACTIVE);
        UsernameAndPasswordDataDTO usernameAndPasswordDataDTO = getUsernameAndPasswordDataDTO();
        when(userRepository.findByUsername(usernameAndPasswordDataDTO.getUsername())).thenReturn(Optional.of(user));
        when(userMapper.usernameAndPasswordDataDTOToUser(usernameAndPasswordDataDTO)).thenReturn(user);
        when(applicationConfig.passwordEncoder()).thenReturn(bCryptPasswordEncoder);
        when(userRepository.saveAndFlush(user)).thenReturn(user);

        String assignPassword = userService.assignPassword(usernameAndPasswordDataDTO);

        verify(userRepository).saveAndFlush(user);
        assertNotNull(assignPassword);
        assertEquals(SUCCESSFULLY_COMPLETED, assignPassword);
    }

    @Test
    void testAssignPasswordNotPresentUserInDb() {
        UsernameAndPasswordDataDTO usernameAndPasswordDataDTO = getUsernameAndPasswordDataDTO();
        when(userRepository.findByUsername(usernameAndPasswordDataDTO.getUsername())).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> userService.assignPassword(usernameAndPasswordDataDTO));
    }

    @Test
    void testAssignPasswordReturnedTransactionException() {
        User user = getUserProvider(STATUS_TYPE_ACTIVE);
        UsernameAndPasswordDataDTO usernameAndPasswordDataDTO = getUsernameAndPasswordDataDTO();
        when(userRepository.findByUsername(usernameAndPasswordDataDTO.getUsername())).thenReturn(Optional.of(user));
        when(userMapper.usernameAndPasswordDataDTOToUser(usernameAndPasswordDataDTO)).thenReturn(user);
        when(applicationConfig.passwordEncoder()).thenReturn(bCryptPasswordEncoder);
        when(userRepository.saveAndFlush(user)).thenThrow(TransactionException.class);

        assertThrows(TransactionException.class, () -> userService.assignPassword(usernameAndPasswordDataDTO));
    }
}