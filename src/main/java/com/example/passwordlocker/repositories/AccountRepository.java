package com.example.passwordlocker.repositories;

import com.example.passwordlocker.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("Select a from Account a WHERE a.username = :username")
    public Account getAccountByUsername(@Param("username") String username);
}
