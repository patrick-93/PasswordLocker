package com.example.passwordlocker.repositories;

import com.example.passwordlocker.models.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
    public List<Log> findAllByOrderByTimestampDesc();
}
