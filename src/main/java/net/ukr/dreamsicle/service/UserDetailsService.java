package net.ukr.dreamsicle.service;

import lombok.AllArgsConstructor;
import net.ukr.dreamsicle.dto.UserDetailsDTO;
import net.ukr.dreamsicle.dto.UserMapper;
import net.ukr.dreamsicle.exception.ResourceNotFoundException;
import net.ukr.dreamsicle.model.StatusType;
import net.ukr.dreamsicle.model.User;
import net.ukr.dreamsicle.model.UserDetails;
import net.ukr.dreamsicle.repository.UserDetailsRepository;
import net.ukr.dreamsicle.repository.UserRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final UserDetailsRepository userDetailsRepository;

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public UserDetailsDTO createUserDetails(long id, UserDetailsDTO userDetailsDTO) {
        UserDetails userDetails = userMapper.userDetailsToUser(userDetailsDTO);
        User user = userRepository.findByIdAndStatus(id, StatusType.ACTIVE).orElseThrow(ResourceNotFoundException::new);

        Optional<UserDetails> userDetailsByUserId = userDetailsRepository.findUserDetailsByUserId(user.getId());

        if (userDetailsByUserId.isPresent()) {
            userDetailsByUserId.get().setSurname(userDetails.getSurname());
            userDetailsByUserId.get().setPhone(userDetails.getPhone());
            userDetailsByUserId.get().setDescription(userDetails.getDescription());
            return userMapper.userToUserDetailsDTO(userDetailsRepository.saveAndFlush(userDetailsByUserId.get()));
        } else {
            return userMapper.userToUserDetailsDTO(userDetailsRepository.saveAndFlush(
                    UserDetails.builder()
                            .surname(userDetails.getSurname())
                            .phone(userDetails.getPhone())
                            .description(userDetails.getDescription())
                            .user(user)
                            .build()));
        }
    }
}