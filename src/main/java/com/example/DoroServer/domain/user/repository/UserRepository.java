package com.example.DoroServer.domain.user.repository;

import com.example.DoroServer.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByAccount(String account);
    Boolean existsByAccount(String account);

    Boolean existsByPhone(String phone);

    Optional<User> findByPhone(String phone);

    Optional<User> findByAccountAndPhone(String account, String phone);
}
