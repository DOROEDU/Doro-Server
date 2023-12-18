package com.example.DoroServer.domain.lectureContentImage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.DoroServer.domain.lectureContentImage.entity.LectureContentImage;

public interface LectureContentImageRepository extends JpaRepository<LectureContentImage, Long> {
}