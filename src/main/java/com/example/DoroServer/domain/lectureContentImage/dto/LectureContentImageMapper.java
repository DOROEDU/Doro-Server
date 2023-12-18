package com.example.DoroServer.domain.lectureContentImage.dto;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.DoroServer.domain.lectureContentImage.entity.LectureContentImage;

@Mapper(componentModel = "spring")
public interface LectureContentImageMapper {

    LectureContentImageRes toLectureContentImageRes(LectureContentImage lectureContentImage);

    List<LectureContentImageRes> toLectureContentImageResList(List<LectureContentImage> lectureContentImageList);
}
