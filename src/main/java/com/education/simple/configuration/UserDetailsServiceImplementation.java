package com.education.simple.configuration;

import com.education.simple.DAO.interfaces.UserRepository;
import com.education.simple.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;


@Component("my")
public class UserDetailsServiceImplementation implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.getUserByIdEmail(s);
        if (user == null) {
            new UsernameNotFoundException("No user found with username " + s);
            return null;
        } else {

            System.out.println("User " + user.getMailAddress()+" was logging in.");


            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().toString()));
            return new org.springframework.security.core.userdetails.User(user.getMailAddress(),
                    user.getPassword(),
                    grantedAuthorities);
        }
    }
}