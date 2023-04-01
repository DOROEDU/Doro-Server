package com.example.DoroServer.domain.base;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseEntity{

    @CreatedBy
    @Column(updatable = false)
    private String createdBy; // 생성 날짜

    @LastModifiedBy
    private String lastModifiedBy; // 수정 날짜
}
