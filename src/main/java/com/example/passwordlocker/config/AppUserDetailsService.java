package com.example.passwordlocker.config;

import com.example.passwordlocker.models.User;
import com.example.passwordlocker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.getUserByUsername(username);
        System.out.println("\n\nFound a user with username: " + username + "\n\n");
        System.out.println("User's bcrypt encoded password is " + user.getPassword() + "\n\n");
        if (user == null) {
            throw new UsernameNotFoundException("Unable to find user with username " + username);
        }
        return new UserDetailsConfig(user);
    }
}
