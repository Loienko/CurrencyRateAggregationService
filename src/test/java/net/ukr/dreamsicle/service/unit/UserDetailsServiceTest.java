package net.ukr.dreamsicle.service.unit;

import net.ukr.dreamsicle.dto.userDetails.UserDetailsDTO;
import net.ukr.dreamsicle.dto.userDetails.UserDetailsMapper;
import net.ukr.dreamsicle.exception.ResourceNotFoundException;
import net.ukr.dreamsicle.model.user.User;
import net.ukr.dreamsicle.model.userDetails.UserDetails;
import net.ukr.dreamsicle.repository.UserDetailsRepository;
import net.ukr.dreamsicle.repository.UserRepository;
import net.ukr.dreamsicle.service.UserDetailsService;
import net.ukr.dreamsicle.util.userDetails.UserDetailsProvider;
import org.hibernate.TransactionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static net.ukr.dreamsicle.util.user.UserProvider.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
class UserDetailsServiceTest {

    @InjectMocks
    private UserDetailsService userDetailsService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserDetailsMapper userDetailsMapper;
    @Mock
    private UserDetailsRepository userDetailsRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateUserDetailsDataExistInDb() {
        User user = getUserProvider(STATUS_TYPE_ACTIVE);
        UserDetails userDetails = UserDetailsProvider.getUserDetailsProvider();
        UserDetailsDTO userDetailsDto = UserDetailsProvider.getUserDetailsDtoProvider();
        when(userDetailsMapper.userDetailsToUser(userDetailsDto)).thenReturn(userDetails);
        when(userRepository.findByIdAndStatus(ID, STATUS_TYPE_ACTIVE)).thenReturn(Optional.of(user));
        when(userDetailsRepository.findUserDetailsByUserId(user.getId())).thenReturn(Optional.of(userDetails));
        when(userDetailsRepository.saveAndFlush(userDetails)).thenReturn(userDetails);
        when(userDetailsMapper.userToUserDetailsDTO(userDetails)).thenReturn(userDetailsDto);

        UserDetailsDTO actualUser = userDetailsService.createUserDetails(ID, userDetailsDto);

        assertNotNull(actualUser);
        assertEquals(userDetailsDto.getSurname(), actualUser.getSurname());
        assertEquals(userDetailsDto.getPhone(), actualUser.getPhone());
        assertEquals(userDetailsDto.getDescription(), actualUser.getDescription());
    }

    @Test
    void testCreateUserDetailsNewDataInDb() {
        User user = getUserProvider(STATUS_TYPE_ACTIVE);
        UserDetails userDetails = UserDetailsProvider.getUserDetailsProvider();
        UserDetailsDTO userDetailsDto = UserDetailsProvider.getUserDetailsDtoProvider();
        when(userDetailsMapper.userDetailsToUser(userDetailsDto)).thenReturn(userDetails);
        when(userRepository.findByIdAndStatus(ID, STATUS_TYPE_ACTIVE)).thenReturn(Optional.of(user));
        when(userDetailsRepository.findUserDetailsByUserId(user.getId())).thenReturn(Optional.empty());
        when(userDetailsRepository.saveAndFlush(any())).thenAnswer((Answer<UserDetails>) answer -> {
            UserDetails argument = (UserDetails) answer.getArguments()[0];
            if (argument.getSurname().equals(UserDetailsProvider.SURNAME)) {
                return argument;
            } else {
                return null;
            }
        });
        when(userDetailsMapper.userToUserDetailsDTO(any())).thenAnswer((Answer<UserDetailsDTO>) answer -> {
            UserDetails argument = (UserDetails) answer.getArguments()[0];
            if (argument.getSurname().equals(UserDetailsProvider.SURNAME)) {
                return userDetailsDto;
            } else {
                return null;
            }
        });

        UserDetailsDTO actualUser = userDetailsService.createUserDetails(ID, userDetailsDto);

        assertNotNull(actualUser);
        assertEquals(userDetailsDto.getSurname(), actualUser.getSurname());
        assertEquals(userDetailsDto.getPhone(), actualUser.getPhone());
        assertEquals(userDetailsDto.getDescription(), actualUser.getDescription());
    }

    @Test
    void testCreateUserDetailsNotExistUserWithStatusActiveThrowResourceNotFoundException() {
        UserDetails userDetails = UserDetailsProvider.getUserDetailsProvider();
        UserDetailsDTO userDetailsDto = UserDetailsProvider.getUserDetailsDtoProvider();
        when(userDetailsMapper.userDetailsToUser(userDetailsDto)).thenReturn(userDetails);
        when(userRepository.findByIdAndStatus(ID, STATUS_TYPE_ACTIVE)).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> userDetailsService.createUserDetails(ID, userDetailsDto));
    }

    @Test
    void testCreateUserDetailsNotExistUserThrowResourceNotFoundException() {
        UserDetails userDetails = UserDetailsProvider.getUserDetailsProvider();
        UserDetailsDTO userDetailsDto = UserDetailsProvider.getUserDetailsDtoProvider();
        when(userDetailsMapper.userDetailsToUser(userDetailsDto)).thenReturn(userDetails);
        when(userRepository.findByIdAndStatus(ID, STATUS_TYPE_DELETED)).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> userDetailsService.createUserDetails(ID, userDetailsDto));
    }

    @Test
    void testCreateUserDetailsDataExistInDbThrowTransactionException() {
        User user = getUserProvider(STATUS_TYPE_ACTIVE);
        UserDetails userDetails = UserDetailsProvider.getUserDetailsProvider();
        UserDetailsDTO userDetailsDto = UserDetailsProvider.getUserDetailsDtoProvider();
        when(userDetailsMapper.userDetailsToUser(userDetailsDto)).thenReturn(userDetails);
        when(userRepository.findByIdAndStatus(ID, STATUS_TYPE_ACTIVE)).thenReturn(Optional.of(user));
        when(userDetailsRepository.findUserDetailsByUserId(ID)).thenReturn(Optional.of(userDetails));
        when(userDetailsRepository.saveAndFlush(userDetails)).thenThrow(TransactionException.class);

        assertThrows(TransactionException.class, () -> userDetailsService.createUserDetails(ID, userDetailsDto));
    }
}