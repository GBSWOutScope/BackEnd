package com.example.outscope.repository;

import com.example.outscope.entity.GoOut;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface GoOutRepository extends JpaRepository<GoOut, Long> {

    void deleteByTime(String time);

    @Query("SELECT g FROM GoOut g WHERE g.time = :time OR g.time = :period")
    List<GoOut> findAllByTime(@Param("time") String time, @Param("period") String period);


}
