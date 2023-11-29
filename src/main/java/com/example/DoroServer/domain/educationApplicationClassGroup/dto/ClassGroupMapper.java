package com.example.DoroServer.domain.educationApplicationClassGroup.dto;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.DoroServer.domain.educationApplicationClassGroup.entity.ClassGroup;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ClassGroupMapper {
    private final ModelMapper mapper;

    public ClassGroup toEntity(ClassGroupReq source) {
        return mapper.map(source, ClassGroup.class);
    }

    public ClassGroup toEntity(ClassGroupReq source, ClassGroup destination) {
        mapper.map(source, destination);
        return destination;
    }

    public ClassGroupRes toDTO(ClassGroup source) {
        return mapper.map(source, ClassGroupRes.class);
    }

}
