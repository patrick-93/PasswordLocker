package com.example.passwordlocker.config;

import com.example.passwordlocker.models.User;
import com.example.passwordlocker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsImpl implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Unable to find user with username " + username);
        }
        return new UserDetailsConfig(user);
    }
}
