package com.example.DoroServer.domain.user.entity;

import com.example.DoroServer.domain.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Collection;
import com.example.DoroServer.domain.chat.entity.Chat;
import com.example.DoroServer.domain.userChat.entity.UserChat;
import com.example.DoroServer.domain.userLecture.entity.UserLecture;
import com.example.DoroServer.domain.userNotification.entity.UserNotification;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id; // PK

    @NotBlank(message = "아이디는 필수입니다.")
    private String account;
    
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password; // 사용자 비밀번호

    @NotBlank
    private String name; // 사용자 이름

    @NotNull
    private int age; // 사용자 나이

    private String gender; // 사용자 성별

    @NotBlank(message = "전화번호는 필수입니다.")
    private String phone; // 사용자 전화번호

    @Embedded
    private Degree degree; // 사용자 학교 정보 [school, studentId, major, studentStatus]

    @NotNull(message = "사용자 기수가 필요합니다.")
    private int generation; // 사용자 기수

    @Enumerated(EnumType.STRING)
    @NotNull(message = "사용자 직책이 필요합니다.")
    private UserRole role; // 사용자 직책

    private String profileImg; // 사용자 이미

    //== 연관관계 매핑 ==//

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<GrantedAuthority>();
        collect.add(() -> String.valueOf(this.getRole()));
        return collect;
    }

    @Override
    public String getUsername() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
