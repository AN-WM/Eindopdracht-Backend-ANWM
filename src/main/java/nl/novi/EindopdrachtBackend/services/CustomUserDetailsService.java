package nl.novi.EindopdrachtBackend.services;

import nl.novi.EindopdrachtBackend.dtos.UserDto;
import nl.novi.EindopdrachtBackend.models.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) {
        Long employeeId = Long.valueOf(username);
        UserDto userDto = userService.getUser(employeeId);

        String password = userDto.getPassword();

        Set<Authority> authorities = userDto.getAuthorities();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Authority authority: authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
        }

        return new org.springframework.security.core.userdetails.User(String.valueOf(employeeId), password, grantedAuthorities);
    }

}