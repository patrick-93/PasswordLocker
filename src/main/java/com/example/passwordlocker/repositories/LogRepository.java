package com.example.passwordlocker.repositories;

import com.example.passwordlocker.models.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
    public List<Log> findAllByOrderByTimestampDesc();

    @Query("Select l from Log l WHERE l.content = :content")
    public Log getLogByContent(@Param("content") String content);
}
