package com.example.passwordlocker.ControllerTests;

import com.example.passwordlocker.controllers.IndexController;
import com.example.passwordlocker.models.User;
import com.example.passwordlocker.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestIndex {
    @Autowired
    private IndexController indexController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() throws Exception {
        assertThat(indexController).isNotNull();
    }

    @Test
    public void indexWebCheck() throws Exception {
        User admin = userRepository.findById(1L).orElse(new User());
        if (admin.getUsername() != null) {
            this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                    .andExpect(content().string(containsString("Password Locker")));
        }
    }
}
