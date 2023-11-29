package com.example.DoroServer.domain.educationApplication.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.DoroServer.domain.educationApplication.entity.EducationApplication;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EducationApplicationMapper {

    private final ModelMapper modelMapper;

    public EducationApplication toEntity(EducationApplicationReq source) {
        return modelMapper.map(source, EducationApplication.class);
    }

    public EducationApplication toEntity(EducationApplicationReq source, EducationApplication destination) {
        modelMapper.map(source, destination);
        return destination;
    }

    public EducationApplicationRes toDTO(EducationApplication source) {
        return modelMapper.map(source, EducationApplicationRes.class);
    }

    public List<EducationApplicationRes> toDTO(List<EducationApplication> source) {
        return source.stream().map(this::toDTO).collect(Collectors.toList());
    }

}
