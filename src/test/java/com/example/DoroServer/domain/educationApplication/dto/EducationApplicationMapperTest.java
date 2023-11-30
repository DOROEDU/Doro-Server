package com.example.DoroServer.domain.educationApplication.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.example.DoroServer.domain.educationApplication.entity.EducationApplication;

@SpringBootTest
@Disabled
public class EducationApplicationMapperTest {

    @Autowired
    private EducationApplicationMapper mapper;

    private EducationApplicationReq setUpEducationApplicationReq() {
        return EducationApplicationReq.builder()
                .name("홍길동")
                .phoneNumber("010-1234-5678")
                .institutionName("냉장고등학교")
                .position("교장선생님")
                .email("gildong@naver.com")
                .studentRank("고등학교 1학년")
                .numberOfStudents(200)
                .budget(1000000)
                .overallRemark("특이사항 없음")
                .build();
    }

    private EducationApplicationReq setUpUpdateEducationApplicationReq() {
        return EducationApplicationReq.builder()
                .name("고길동") // update
                .phoneNumber("010-1234-5678")
                .institutionName("냉장고등학교")
                .position("교감선생님") // update
                .email("gildong@naver.com")
                .studentRank("고등학교 1학년")
                .numberOfStudents(200)
                .budget(1000000)
                .overallRemark("둘리 조심") // update
                .build();
    }

    private EducationApplication setUpEducationApplication() {
        return EducationApplication.builder()
                .id(1L)
                .name("홍길동")
                .phoneNumber("010-1234-5678")
                .institutionName("냉장고등학교")
                .position("교장선생님")
                .email("gildong@naver.com")
                .studentRank("고등학교 1학년")
                .numberOfStudents(200)
                .budget(1000000)
                .overallRemark("특이사항 없음")
                .build();
    }

    private EducationApplication setUpUpdateEducationApplication() {
        return EducationApplication.builder()
                .id(1L)
                .name("고길동") // update
                .phoneNumber("010-1234-5678")
                .institutionName("냉장고등학교")
                .position("교감선생님") // update
                .email("gildong@naver.com")
                .studentRank("고등학교 1학년")
                .numberOfStudents(200)
                .budget(1000000)
                .overallRemark("둘리 조심") // update
                .build();
    }

    private EducationApplicationRes setUpEducationApplicationRes() {
        return EducationApplicationRes.builder()
                .id(1L)
                .name("홍길동")
                .phoneNumber("010-1234-5678")
                .institutionName("냉장고등학교")
                .position("교장선생님")
                .email("gildong@naver.com")
                .studentRank("고등학교 1학년")
                .numberOfStudents(200)
                .budget(1000000)
                .overallRemark("특이사항 없음")
                .classGroups(new ArrayList<>())
                .build();
    }

    private List<EducationApplication> setUpEducationApplications() {
        List<EducationApplication> educationApplications = new ArrayList<>();
        educationApplications.add(setUpEducationApplication());
        return educationApplications;
    }

    private List<EducationApplicationRes> setUpEducationApplicationResList() {
        List<EducationApplicationRes> educationApplicationResList = new ArrayList<>();
        educationApplicationResList.add(setUpEducationApplicationRes());
        return educationApplicationResList;
    }

    @DisplayName("교육 신청 toEntity Mapper 테스트")
    @Test
    void testToEntity() {
        // given
        EducationApplicationReq applicationReq = setUpEducationApplicationReq();
        EducationApplication educationApplication = setUpEducationApplication();

        // when
        EducationApplication result = mapper.toEntity(applicationReq);

        // then
        for (Field field : EducationApplication.class.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getName().equals("id")) {
                continue;
            }
            try {
                assertEquals(field.get(educationApplication), field.get(result));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @DisplayName("교육 신청 업데이트 toEntity Mapper 테스트")
    @Test
    void testToEntityWithDestination() {
        // given
        EducationApplicationReq updateApplicationReq = setUpUpdateEducationApplicationReq();
        EducationApplication educationApplication = setUpEducationApplication();
        EducationApplication updateEducationApplication = setUpUpdateEducationApplication();

        // when
        EducationApplication result = mapper.toEntity(updateApplicationReq, educationApplication);

        // then
        for (Field field : EducationApplication.class.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getName().equals("id")) {
                continue;
            }
            try {
                assertEquals(field.get(updateEducationApplication), field.get(result));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        // 업데이트 된 내용이 맞는지 assertThat으로 확인
        assertThat(result.getName()).isEqualTo("고길동");
        assertThat(result.getPosition()).isEqualTo("교감선생님");
        assertThat(result.getOverallRemark()).isEqualTo("둘리 조심");
    }

    @DisplayName("교육 신청 toDTO Mapper 테스트")
    @Test
    void testToDTO() {
        // given
        EducationApplication educationApplication = setUpEducationApplication();
        EducationApplicationRes educationApplicationRes = setUpEducationApplicationRes();

        // when
        EducationApplicationRes result = mapper.toDTO(educationApplication);

        // then
        for (Field field : EducationApplicationRes.class.getDeclaredFields()) {
            field.setAccessible(true);

            try {
                assertEquals(field.get(educationApplicationRes), field.get(result));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @DisplayName("교육 신청 toDTO Mapper 테스트 (List 파라미터)")
    @Test
    void testToDTOList() {
        // given
        List<EducationApplication> educationApplications = setUpEducationApplications();
        List<EducationApplicationRes> educationApplicationResList = setUpEducationApplicationResList();

        // when
        List<EducationApplicationRes> result = mapper.toDTO(educationApplications);

        // then
        assertEquals(educationApplicationResList.size(), result.size());
        for (int i = 0; i < result.size(); i++) {
            for (Field field : EducationApplicationRes.class.getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    assertEquals(field.get(educationApplicationResList.get(i)), field.get(result.get(i)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}