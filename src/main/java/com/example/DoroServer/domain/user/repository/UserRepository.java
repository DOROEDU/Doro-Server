package com.example.DoroServer.domain.user.repository;

import com.example.DoroServer.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByAccount(String account);
    Boolean existsByAccount(String account);

    Boolean existsByPhone(String phone);

    Optional<User> findByPhone(String phone);

    Optional<User> findByAccountAndPhone(String account, String phone);

    @Transactional
    @Modifying
    @Query(value = "update User u set u.profileImg = :profile_img where u.id = :userId")
    void updateProfileImgById(@Param("userId") Long userId, @Param("profile_img") String profile_img);
}
