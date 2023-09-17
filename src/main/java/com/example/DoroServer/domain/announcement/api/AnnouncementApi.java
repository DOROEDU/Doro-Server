package com.example.DoroServer.domain.announcement.api;

import com.example.DoroServer.domain.announcement.dto.AnnouncementMultipartReq;
import com.example.DoroServer.domain.announcement.dto.AnnouncementRes;
import com.example.DoroServer.domain.announcement.service.AnnouncementService;
import com.example.DoroServer.domain.notification.dto.NotificationContentReq;
import com.example.DoroServer.domain.notification.entity.NotificationType;
import com.example.DoroServer.domain.notification.service.NotificationServiceRefact;
import com.example.DoroServer.global.common.SuccessResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "공지📋")
@RestController
@RequestMapping("/announcements")
@RequiredArgsConstructor
public class AnnouncementApi {

    private final AnnouncementService announcementService;

    // 모든 공지를 찾아 반환
    @ApiOperation(value = "공지 글 전체 조회", notes = "생성되어있는 모든 공지를 조회합니다. 파라미터로 page랑 size 전달하시면 페이징 됩니다. 이게 Swagger가 잘 안돼서 Postman으로 테스트 해보시는게 나을거에요 Swagger는 이상하게 page랑 size를 인식못하네요")
    @GetMapping
    public SuccessResponse findAllAnnouncement(
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Direction.DESC) Pageable pageable) {
        List<AnnouncementRes> announcements = announcementService.findAllAnnouncements(pageable);
        return SuccessResponse.successResponse(announcements);
    }

    // 공지 생성 후 생성 확인 알림 전송 Version1
//    @ApiOperation(value = "공지 글 생성", notes = "공지 제목(title), 내용(body), 이미지(image)를 입력받아 공지를 생성합니다.")
//    @Secured("ROLE_ADMIN")
//    //@PostMapping
//    public SuccessResponse createAnnouncement(
//            @RequestPart(value = "announcementReq") @Valid AnnouncementReq announcementReq,
//            @RequestPart(value = "picture", required = false) MultipartFile picture) {
//        Long announcementId;
//        if (picture != null) {
//            announcementId = announcementService.createAnnouncement(announcementReq, picture);
//        } else {
//            announcementId = announcementService.createAnnouncement(announcementReq);
//        }
//        notificationService.sendNotificationToAll(NotificationContentReq.builder()
//                .title("새로운 공지가 올라왔습니다!")
//                .body(announcementReq.getTitle())
//                .build(), NotificationType.ANNOUNCEMENT,announcementId);
//        return SuccessResponse.successResponse("announcement created " + announcementId);
//    }

    /**
     * axios - verision multipart 문제로 인해 만든 Version2
     */
    @ApiOperation(value = "공지 글 생성 ModelAttribute 이용", notes = "DTO내에 모든 값이 들어갑니다")
    @Secured("ROLE_ADMIN")
    @PostMapping
    public SuccessResponse createAnnouncement(
        @ModelAttribute @Valid AnnouncementMultipartReq announcementMultipartReq) {
        Long announcementId = announcementService.createAnnouncement(announcementMultipartReq);
        return SuccessResponse.successResponse("announcement created " + announcementId);
    }

    // id에 해당하는 공지 하나 조회
    @ApiOperation(value = "공지 글 단일 조회", notes = "id에 해당하는 공지 글을 조회합니다.")
    @GetMapping("/{id}")
    public SuccessResponse findAnnouncement(@PathVariable("id") Long id) {
        AnnouncementRes announcementRes = announcementService.findById(id);
        return SuccessResponse.successResponse(announcementRes);
    }

//    // id에 해당하는 공지 수정
//    @ApiOperation(value = "공지 글 수정", notes = "id에 해당하는 공지 글을 수정합니다.")
//    @Secured("ROLE_ADMIN")
//    @PatchMapping("/{id}")
//    public SuccessResponse editAnnouncement(@PathVariable("id") Long id,
//            @RequestPart(value = "announcementReq") @Valid AnnouncementReq announcementReq,
//            @RequestPart(value = "picture", required = false) MultipartFile picture) {
//        if (picture != null) {
//            announcementService.updateAnnouncement(id, announcementReq, picture);
//        } else {
//            announcementService.updateAnnouncement(id, announcementReq);
//        }
//        return SuccessResponse.successResponse("update complete");
//    }

    /**
     * axios - verision multipart 문제로 인해 만든 Version2
     */
    @ApiOperation(value = "공지 글 수정", notes = "id에 해당하는 공지 글을 수정합니다.")
    @Secured("ROLE_ADMIN")
    @PatchMapping("/{id}")
    public SuccessResponse editAnnouncement(@PathVariable("id") Long id,
        @ModelAttribute @Valid AnnouncementMultipartReq announcementMultipartReq) {
        Long announcementId = announcementService.updateAnnouncement(id, announcementMultipartReq);

        return SuccessResponse.successResponse("업데이트 완료 = " + announcementId);
    }

    // id에 해당하는 공지 삭제
    @ApiOperation(value = "공지 글 삭제", notes = "id에 해당하는 공지글을 삭제합니다.")
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public SuccessResponse deleteAnnouncement(@PathVariable("id") Long id) {
        announcementService.deleteAnnouncement(id);
        return SuccessResponse.successResponse("delete complete");
    }
}
