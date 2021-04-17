package com.example.passwordlocker.ControllerTests.accounts;

import com.example.passwordlocker.controllers.accounts.DeleteAccount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class TestAccountDelete {

    @Autowired
    private DeleteAccount controller;

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }
}
