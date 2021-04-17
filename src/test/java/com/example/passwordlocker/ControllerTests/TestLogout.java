package com.example.passwordlocker.ControllerTests;

import com.example.passwordlocker.controllers.LogoutController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class TestLogout {
    @Autowired
    private LogoutController logoutController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(logoutController).isNotNull();
    }
}
