package net.ukr.dreamsicle.dto.userDetails;

import lombok.Lombok;
import net.ukr.dreamsicle.model.userDetails.UserDetails;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

/**
 * The generation of a implementation of {@link UserDetailsDTO} to {@link UserDetails} and back via MapStruct.
 * Used by {@link Lombok} to create template methods of an object like getters. setters, etc.
 *
 * @author yurii.loienko
 * @version 1.0
 */
@Mapper
@Service
public interface UserDetailsMapper {
    UserDetails userDetailsToUser(UserDetailsDTO userDetailsDTO);

    UserDetailsDTO userToUserDetailsDTO(UserDetails user);
}
