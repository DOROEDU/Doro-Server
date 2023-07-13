package com.example.DoroServer.domain.userLecture.dto;

import com.example.DoroServer.domain.userLecture.entity.TutorRole;
import java.util.List;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class SelectTutorReq {
    @NotNull
    private Long userId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TutorRole tutorRole;


}
