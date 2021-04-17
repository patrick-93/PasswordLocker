package com.example.passwordlocker.ControllerTests.users;

import com.example.passwordlocker.controllers.users.ReadUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class TestUserRead {

    @Autowired
    private ReadUser userController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(userController).isNotNull();
    }
}
