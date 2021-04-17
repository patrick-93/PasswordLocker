package com.example.passwordlocker.RepositoryTests;

import com.example.passwordlocker.models.Log;
import com.example.passwordlocker.repositories.LogRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
public class TestLogRepository {
    @Autowired
    private TestEntityManager manager;

    @Autowired
    private LogRepository logRepository;

    @Test
    public void repositoryTest() throws Exception {
        Log log = new Log("test log");
        this.manager.persist(log);

        Log test = this.logRepository.getLogByContent("test log");

        assertEquals("test log", test.getContent());
    }
}
