package com.example.passwordlocker.ControllerTests.users;

import com.example.passwordlocker.controllers.users.UpdateUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class TestUserUpdate {

    @Autowired
    private UpdateUser userController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(userController).isNotNull();
    }
}
