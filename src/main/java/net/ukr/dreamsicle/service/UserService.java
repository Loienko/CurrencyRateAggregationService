package net.ukr.dreamsicle.service;

import net.ukr.dreamsicle.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    Page<UserDTO> findAllUsers(Pageable pageable);

    UserDTO findUserById(int id);

    UserDTO findUserByUsername(String username);

    void deleteUser(int id);

    UserDTO createUser(UserDTO userDTO);

    UserDTO updateUser(int id, UserDTO userDTO);
}
