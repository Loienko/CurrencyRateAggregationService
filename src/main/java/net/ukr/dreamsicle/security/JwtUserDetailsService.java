package net.ukr.dreamsicle.security;

import net.ukr.dreamsicle.dto.UserDTO;
import net.ukr.dreamsicle.dto.UserMapper;
import net.ukr.dreamsicle.security.jwt.JwtUserFactory;
import net.ukr.dreamsicle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public JwtUserDetailsService(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserDTO userByUsername = userService.findUserByUsername(username);

        if (userByUsername == null) {
            throw new UsernameNotFoundException("User with " + username + " not found");
        }

        return JwtUserFactory.create(userMapper.toUser(userByUsername));
    }
}
