package net.ukr.dreamsicle.dto;

import net.ukr.dreamsicle.model.User;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Mapper
@Service
public interface UserMapper {

    UserDTO userToUserDto(User user);

    User userDtoToUser(UserDTO userDTO);

    default Page<UserDTO> userToUserDTOs(Page<User> users) {
        return users.map(this::userToUserDto);
    }

    User userLoginDtoToUser(UserLoginDto userLoginDto);

    UserLoginDto userToUserLoginDto(User user);

    User userDetailsToUser(UserDetailsDTO userDetailsDTO);

    UserDetailsDTO userToUserDetailsDTO(User user);
}
