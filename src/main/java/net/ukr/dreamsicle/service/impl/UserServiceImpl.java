package net.ukr.dreamsicle.service.impl;

import net.ukr.dreamsicle.dto.UserDTO;
import net.ukr.dreamsicle.dto.UserMapper;
import net.ukr.dreamsicle.exception.ResourceNotFoundException;
import net.ukr.dreamsicle.model.Role;
import net.ukr.dreamsicle.model.RoleName;
import net.ukr.dreamsicle.model.User;
import net.ukr.dreamsicle.repository.RoleRepository;
import net.ukr.dreamsicle.repository.UserRepository;
import net.ukr.dreamsicle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.toUser(userDTO);

        user.setPassword(bCryptPasswordEncoder().encode(user.getPassword()));
        user.setRoles(getRoles());

        return userMapper.toUserDto(userRepository.saveAndFlush(user));
    }

    private List<Role> getRoles() {
        Role roleName = roleRepository.findByRoleName(RoleName.USER.getName());
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleName);
        return userRoles;
    }

    @Override
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public UserDTO updateUser(int id, UserDTO userDTO) {

        User userUpdateById = userRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        User actualUser = userMapper.toUser(userDTO);

        userUpdateById.setName(actualUser.getName());
        userUpdateById.setUsername(actualUser.getUsername());
        userUpdateById.setEmail(actualUser.getEmail());
        userUpdateById.setPassword(actualUser.getPassword());
        userUpdateById.setRoles(getRoles());

        return userMapper.toUserDto(userRepository.saveAndFlush(actualUser));
    }

    @Override
    public Page<UserDTO> findAllUsers(Pageable pageable) {
        return userMapper.toUserDTOs(userRepository.findAll(pageable));
    }

    @Override
    public UserDTO findUserById(int id) {
        return userRepository
                .findById(id)
                .map(userMapper::toUserDto)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public UserDTO findUserByUsername(String username) {
        return userRepository.findByUsername(username).map(userMapper::toUserDto).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void deleteUser(int id) {
        userRepository.deleteById(
                userRepository.findById(id)
                        .orElseThrow(ResourceNotFoundException::new)
                        .getId());
    }
}