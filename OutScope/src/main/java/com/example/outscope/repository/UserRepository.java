package com.example.outscope.repository;

import com.example.outscope.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByAccount(String account);

    Optional<User> findByClassNumber(String number);

}
