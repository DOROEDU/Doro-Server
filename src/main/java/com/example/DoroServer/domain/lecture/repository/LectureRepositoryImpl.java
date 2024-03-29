package com.example.DoroServer.domain.lecture.repository;

import static com.example.DoroServer.domain.lecture.entity.QLecture.*;

import com.example.DoroServer.domain.lecture.dto.FindAllLecturesCond;
import com.example.DoroServer.domain.lecture.entity.Lecture;
import com.example.DoroServer.domain.lecture.entity.LectureStatus;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class LectureRepositoryImpl implements LectureRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    public Page<Lecture> findAllLecturesWithFilter(FindAllLecturesCond condition, Pageable pageable){
        Long total=queryFactory
                .select(lecture.count())
                .from(lecture)
                .where(lectureStatus(condition.getLectureStatus()))
                .fetchOne();

        List<Lecture> results= queryFactory
                .select(lecture)
                .from(lecture)
                .where(
                        containCity(condition.getCity()),
                        betweenDate(condition.getStartDate(),condition.getEndDate()),
                        lectureStatus(condition.getLectureStatus())
                )
                .orderBy(lecture.lectureDate.enrollEndDate.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(results,pageable,total);

    }
   private BooleanBuilder containCity(List<String> cities){
        if(cities==null){
            return null;
        }
       BooleanBuilder booleanBuilder = new BooleanBuilder();
       for (String city: cities){
            booleanBuilder.or(lecture.city.contains(city));
        }
        return booleanBuilder;
   }
   private BooleanExpression betweenDate(LocalDate startDate, LocalDate endDate){
        if(startDate !=null && endDate != null){
            return lecture.lectureDates.any().between(startDate, endDate);
        }
        return null;

   }
   private  BooleanExpression lectureStatus(LectureStatus lectureStatus){
        if(lectureStatus!=null){
            return lecture.status.eq(lectureStatus);
        }
        return null;
   }



}
