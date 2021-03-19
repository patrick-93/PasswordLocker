package com.example.passwordlocker.repositories;

import com.example.passwordlocker.models.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

//    @Override
//    List<Account> findAll();
}
