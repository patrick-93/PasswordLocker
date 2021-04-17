package com.example.passwordlocker.RepositoryTests;

import com.example.passwordlocker.models.Account;
import com.example.passwordlocker.repositories.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class TestAccountRepository {

    @Autowired
    private TestEntityManager manager;

    @Autowired
    private AccountRepository repository;

    @Test
    public void repositoryTest() throws Exception {
        Account account = new Account();
        account.setUsername("test");
        this.manager.persist(account);

        Account test = this.repository.getAccountByUsername("test");
        assertEquals("test", test.getUsername());
    }
}
