package com.example.DoroServer.domain.lectureContent.api;

import com.example.DoroServer.domain.lectureContent.dto.CreateLectureContentReq;
import com.example.DoroServer.domain.lectureContent.dto.LectureContentRes;
import com.example.DoroServer.domain.lectureContent.dto.UpdateLectureContentReq;
import com.example.DoroServer.domain.lectureContent.service.LectureContentService;
import com.example.DoroServer.domain.lectureContentImage.dto.LectureContentImageReq;
import com.example.DoroServer.domain.lectureContentImage.dto.LectureContentImageRes;
import com.example.DoroServer.domain.lectureContentImage.service.LectureContentImageService;
import com.example.DoroServer.global.common.SuccessResponse;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "강의 컨텐츠")
@RestController
@RequestMapping("/lecture-contents")
@RequiredArgsConstructor
@Validated
public class LectureContentApi {
    private final LectureContentService lectureContentService;
    private final LectureContentImageService lectureContentImageService;

    @ApiOperation(value = "모든 강의 컨텐츠 조회", notes = "인증된 사용자만 조회 가능합니다.")
    @GetMapping
    public SuccessResponse<?> findAllLectureContents() {
        List<LectureContentRes> allLectureContents = lectureContentService.findAllLectureContents();
        return SuccessResponse.successResponse(allLectureContents);
    }

    @ApiOperation(value = "강의 컨텐츠 추가", notes = "관리자만 생성 가능합니다. 이미지는 1개라도 MultipartFile[]로 보내야 합니다.")
    @Secured("ROLE_ADMIN")
    @PostMapping
    public SuccessResponse<?> createLectureContent(@ModelAttribute @Valid CreateLectureContentReq lectureContentReq) {
        LectureContentRes createdLectureContent = lectureContentService.createLectureContent(lectureContentReq);
        return SuccessResponse.successResponse(createdLectureContent);
    }

    @ApiOperation(value = "강의 컨텐츠 수정", notes = "관리자만 수정 가능합니다. 해당 엔드포인트에선 이미지를 제외한 나머지 필드들만 수정 가능합니다.")
    @Secured("ROLE_ADMIN")
    @PatchMapping("/{id}")
    public SuccessResponse<?> updateLectureContent(
            @PathVariable("id") Long id,
            @ModelAttribute UpdateLectureContentReq updateLectureContentReq) {
        LectureContentRes updatedLectureContent = lectureContentService.updateLectureContent(id,
                updateLectureContentReq);
        return SuccessResponse.successResponse(updatedLectureContent);
    }

    @ApiOperation(value = "강의 컨텐츠 삭제", notes = "관리자만 삭제 가능합니다.")
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public SuccessResponse<?> deleteLectureContent(@PathVariable("id") Long id) {
        lectureContentService.deleteLectureContent(id);
        return SuccessResponse.successResponse("강의 자료 삭제 성공");
    }

    /* 강의 컨텐츠의 이미지 추가 및 삭제 API */

    @ApiOperation(value = "강의 컨텐츠 이미지 추가", notes = "관리자만 추가 가능합니다. 이미지는 1개라도 MultipartFile[]로 보내야 합니다.")
    @Secured("ROLE_ADMIN")
    @PostMapping("/{id}/images")
    public SuccessResponse<?> addLectureContentImages(@PathVariable("id") Long id,
            @ModelAttribute @Valid LectureContentImageReq lectureContentImageReq) {
        List<LectureContentImageRes> addedLectureContentImages = lectureContentImageService.addLectureContentImages(id,
                lectureContentImageReq);
        return SuccessResponse.successResponse(addedLectureContentImages);
    }

    @ApiOperation(value = "강의 컨텐츠 이미지 삭제", notes = "관리자만 삭제 가능합니다. 강의 컨텐츠와 강의 컨텐츠의 이미지 id를 통해 삭제합니다.")
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}/images/{imageId}")
    public SuccessResponse<?> deleteLectureContentImage(@PathVariable("id") Long id,
            @PathVariable("imageId") Long imageId) {
        lectureContentImageService.deleteLectureContentImage(id, imageId);
        return SuccessResponse.successResponse("강의 컨텐츠 이미지 삭제 성공");
    }

}
