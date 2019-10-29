package net.ukr.dreamsicle.service;

import lombok.AllArgsConstructor;
import net.ukr.dreamsicle.dto.userDetails.UserDetailsDTO;
import net.ukr.dreamsicle.dto.userDetails.UserDetailsMapper;
import net.ukr.dreamsicle.exception.ResourceNotFoundException;
import net.ukr.dreamsicle.model.user.StatusType;
import net.ukr.dreamsicle.model.user.User;
import net.ukr.dreamsicle.model.userDetails.UserDetails;
import net.ukr.dreamsicle.repository.UserDetailsRepository;
import net.ukr.dreamsicle.repository.UserRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.Optional;

/**
 * Business logic for user details object of work with methods createUserDetails
 *
 * @author yurii.loienko
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class UserDetailsService {

    private final UserDetailsMapper userDetailsMapper;
    private final UserRepository userRepository;
    private final UserDetailsRepository userDetailsRepository;

    /**
     * The method creates user details by {@code id} and status is {@code ACTIVE}. Saves an user details and flushes changes instantly.
     * If user data does not exist it will create a new one or update the last one, if any
     * If the user does not exist, will be thrown ResourceNotFoundException.
     *
     * @param id
     * @param userDetailsDTO
     * @return user details saved entity
     * @throws ResourceNotFoundException if {@code id} is not found
     */
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public UserDetailsDTO createUserDetails(long id, UserDetailsDTO userDetailsDTO) {
        UserDetails userDetails = userDetailsMapper.userDetailsToUser(userDetailsDTO);
        User user = userRepository.findByIdAndStatus(id, StatusType.ACTIVE).orElseThrow(ResourceNotFoundException::new);

        Optional<UserDetails> userDetailsByUserId = userDetailsRepository.findUserDetailsByUserId(user.getId());

        if (userDetailsByUserId.isPresent()) {
            userDetailsByUserId.get().setSurname(userDetails.getSurname());
            userDetailsByUserId.get().setPhone(userDetails.getPhone());
            userDetailsByUserId.get().setDescription(userDetails.getDescription());
            return userDetailsMapper.userToUserDetailsDTO(userDetailsRepository.saveAndFlush(userDetailsByUserId.get()));
        } else {
            return userDetailsMapper.userToUserDetailsDTO(userDetailsRepository.saveAndFlush(
                    UserDetails.builder()
                            .surname(userDetails.getSurname())
                            .phone(userDetails.getPhone())
                            .description(userDetails.getDescription())
                            .user(user)
                            .build()));
        }
    }
}