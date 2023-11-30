package com.example.DoroServer.domain.educationApplicationClassGroup.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.DoroServer.domain.educationApplicationClassGroup.entity.ClassGroup;

import java.lang.reflect.Field;
import java.util.List;
import java.time.LocalDate;

@SpringBootTest
@Disabled
public class ClassGroupMapperTest {

    @Autowired
    private ClassGroupMapper mapper;

    private ClassGroupReq setUpClassGroupReq() {
        return ClassGroupReq.builder()
                .className("1학년 1반")
                .educationConcept("Q* 알고리즘")
                .numberOfStudents(15)
                .educationDates(List.of(
                        LocalDate.parse("2021-08-01"),
                        LocalDate.parse("2021-08-08"),
                        LocalDate.parse("2021-08-15")))
                .remark("오후 2시-4시")
                .unfixed(true)
                .build();
    }

    private ClassGroupRes setUpClassGroupRes() {
        return ClassGroupRes.builder()
                .id(1L)
                .className("1학년 1반")
                .educationConcept("Q* 알고리즘")
                .numberOfStudents(15)
                .educationDates(List.of(
                        LocalDate.parse("2021-08-01"),
                        LocalDate.parse("2021-08-08"),
                        LocalDate.parse("2021-08-15")))
                .remark("오후 2시-4시")
                .unfixed(true)
                .build();
    }

    private ClassGroupReq setUpUpdateClassGroupReq() {
        return ClassGroupReq.builder()
                .className("1학년 1반")
                .educationConcept("Q* 알고리즘")
                .numberOfStudents(20) // update
                .educationDates(List.of(
                        LocalDate.parse("2021-08-01"),
                        LocalDate.parse("2021-08-08"),
                        LocalDate.parse("2021-08-15")))
                .remark("오후 1시-3시") // update
                .unfixed(true)
                .build();
    }

    private ClassGroup setUpClassGroup() {
        return ClassGroup.builder()
                .id(1L)
                .className("1학년 1반")
                .educationConcept("Q* 알고리즘")
                .numberOfStudents(15)
                .educationDates(List.of(
                        LocalDate.parse("2021-08-01"),
                        LocalDate.parse("2021-08-08"),
                        LocalDate.parse("2021-08-15")))
                .remark("오후 2시-4시")
                .unfixed(true)
                .build();
    }

    private ClassGroup setUpUpdateClassGroup() {
        return ClassGroup.builder()
                .id(1L)
                .className("1학년 1반")
                .educationConcept("Q* 알고리즘")
                .numberOfStudents(20) // update
                .educationDates(List.of(
                        LocalDate.parse("2021-08-01"),
                        LocalDate.parse("2021-08-08"),
                        LocalDate.parse("2021-08-15")))
                .remark("오후 1시-3시") // update
                .unfixed(true)
                .build();
    }

    @DisplayName("학급정보 toEntity Mapper 테스트")
    @Test
    void testToEntity() {
        // given
        ClassGroupReq classGroupReq = setUpClassGroupReq();
        ClassGroup classGroup = setUpClassGroup();

        // when
        ClassGroup result = mapper.toEntity(classGroupReq);

        // then
        for (Field field : ClassGroup.class.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getName().equals("id")) {
                continue;
            }
            try {
                assertEquals(field.get(classGroup), field.get(result));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @DisplayName("학급정보 업데이트 toEntity Mapper 테스트")
    @Test
    void testToEntityWithDestination() {
        // given
        ClassGroupReq classGroupReq = setUpUpdateClassGroupReq();
        ClassGroup classGroup = setUpClassGroup();
        ClassGroup updateClassGroup = setUpUpdateClassGroup();

        // when
        ClassGroup result = mapper.toEntity(classGroupReq, classGroup);

        // then
        for (Field field : ClassGroup.class.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getName().equals("id")) {
                continue;
            }
            try {
                assertEquals(field.get(updateClassGroup), field.get(result));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        assertThat(result.getNumberOfStudents()).isEqualTo(20);
        assertThat(result.getRemark()).isEqualTo("오후 1시-3시");
    }

    @DisplayName("학급정보 toDTO Mapper 테스트")
    @Test
    void testToDTO() {
        // given
        ClassGroup classGroup = setUpClassGroup();
        ClassGroupRes classGroupRes = setUpClassGroupRes();

        // when
        ClassGroupRes result = mapper.toDTO(classGroup);

        // then
        for (Field field : ClassGroupRes.class.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                assertEquals(field.get(classGroupRes), field.get(result));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}