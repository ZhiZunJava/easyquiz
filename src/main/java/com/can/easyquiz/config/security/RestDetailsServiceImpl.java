package com.can.easyquiz.config.security;

import com.can.easyquiz.enums.Role;
import com.can.easyquiz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class RestDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    /**
     * Instantiates a new Rest details service.
     *
     * @param userService the user service
     */
    @Autowired
    public RestDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.can.easyquiz.domain.User user = userService.getUserByUserName(username);

        if (user == null) {
            throw new UsernameNotFoundException("Username not found.");
        }

        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(Role.fromCode(user.getRole()).getRoleName()));

        return new User(user.getUserName(), user.getPassword(), grantedAuthorities);
    }
}
