package com.example.DoroServer.domain.lecture.repository;

import java.util.Arrays;
import java.util.Collections;
import com.example.DoroServer.domain.lecture.entity.Lecture;
import com.example.DoroServer.domain.lectureContent.entity.LectureContent;
import com.example.DoroServer.domain.lectureContent.repository.LectureContentRepository;
import com.example.DoroServer.global.config.QueryDslConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({ QueryDslConfig.class })
class LectureRepositoryTest {

    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    LectureContentRepository lectureContentRepository;

    @PersistenceContext
    EntityManager em;

    LectureContent lectureContent;

    @BeforeEach
    void setUp() {
        lectureContent = LectureContent.builder().kit("테스트 키트").detail("세부사항").requirement("고졸").build();
        lectureContentRepository.save(lectureContent);
    }

    @DisplayName("findLectureById_Success_Test")
    @Test
    void findLectureByIdTest() {
        // given
        Lecture lecture = Lecture.builder()
                .mainTitle("강의 제목")
                .lectureContent(lectureContent)
                .build();
        Lecture saved = lectureRepository.save(lecture);

        // when
        Optional<Lecture> optionalLecture = lectureRepository.findLectureById(saved.getId());

        // then
        assertThat(em.getEntityManagerFactory().getPersistenceUnitUtil()
                .isLoaded(optionalLecture.get().getLectureContent())).isTrue();
    }

    @DisplayName("findLecturesByFinishedDate_Success_Test")
    @Test
    void findLecturesByFinishedDateTest() {
        // given
        List<List<LocalDate>> localDatesList = Arrays.asList(
                Arrays.asList(LocalDate.of(2023, 10, 10), LocalDate.of(2023, 10, 11)),
                Arrays.asList(LocalDate.of(2023, 10, 10), LocalDate.of(2023, 10, 9)),
                Collections.singletonList(LocalDate.of(2023, 10, 10)));
        for (List<LocalDate> inputList : localDatesList) {
            Lecture lecture = Lecture.builder()
                    .lectureDates(inputList)
                    .lectureContent(lectureContent)
                    .build();
            lectureRepository.save(lecture);
        }
        Integer finishedLecturesCount = 2;
        LocalDate finishedDate = LocalDate.of(2023, 10, 10);

        // when
        List<Lecture> lecturesByFinishedDate = lectureRepository.findLecturesByFinishedDate(finishedDate);

        // then
        assertThat(lecturesByFinishedDate.size()).isEqualTo(finishedLecturesCount);
    }

}