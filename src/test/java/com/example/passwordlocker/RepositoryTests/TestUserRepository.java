package com.example.passwordlocker.RepositoryTests;

import com.example.passwordlocker.models.User;
import com.example.passwordlocker.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class TestUserRepository {
    @Autowired
    private TestEntityManager manager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void repositoryTest() throws Exception {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        this.manager.persist(user);
        User checkUser = this.userRepository.getUserByUsername("test");

        assertEquals("test", checkUser.getUsername());
        assertEquals("test", checkUser.getPassword());
    }
}
