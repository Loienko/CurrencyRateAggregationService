package net.ukr.dreamsicle.dto;

import net.ukr.dreamsicle.model.User;
import net.ukr.dreamsicle.model.UserDetails;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Mapper
@Service
public interface UserMapper {

    default UserDTO userToUserDto(User user) {
        if (user == null) {
            return null;
        }
        return UserDTO.builder().
                id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(Collections.singleton(user.getRoles().toString()))
                .build();
    }

    User userDtoToUser(UserDTO userDTO);

    default Page<UserDTO> userToUserDTOs(Page<User> users) {
        return users.map(this::userToUserDto);
    }

    User usernameAndPasswordDataDTOToUser(UsernameAndPasswordDataDTO usernameAndPasswordDataDTO);

    UsernameAndPasswordDataDTO userToUsernameAndPasswordDataDTO(User user);

    UserDetails userDetailsToUser(UserDetailsDTO userDetailsDTO);

    UserDetailsDTO userToUserDetailsDTO(UserDetails user);
}