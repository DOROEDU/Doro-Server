package com.example.DoroServer.domain.lectureContent.dto;

import com.example.DoroServer.domain.lectureContent.entity.LectureContent;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LectureContentMapper {
    LectureContent toLectureContent(LectureContentDto lectureContentDto);

    LectureContentDto toLectureContentDto(LectureContent lectureContent);

    LectureContentRes toLectureContentRes(LectureContent lectureContent);

    List<LectureContentRes> toLectureContentResList(List<LectureContent> lectureContentList);

}
