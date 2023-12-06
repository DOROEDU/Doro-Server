package com.example.DoroServer.domain.educationApplication.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.example.DoroServer.domain.educationApplication.dto.EducationApplicationReq;
import com.example.DoroServer.domain.educationApplication.dto.EducationApplicationRes;
import com.example.DoroServer.domain.educationApplication.dto.RetrieveApplicationReq;
import com.example.DoroServer.domain.educationApplicationClassGroup.dto.ClassGroupReq;
import com.example.DoroServer.domain.educationApplicationClassGroup.dto.ClassGroupRes;

import com.example.DoroServer.domain.educationApplicationClassGroup.service.ClassGroupService;
import com.example.DoroServer.global.common.SuccessResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.example.DoroServer.domain.educationApplication.service.EducationApplicationService;

@Api(tags = "교육 신청 ✍️")
@RestController
@RequestMapping("/education-application")
@RequiredArgsConstructor
public class EducationApplicationApi {

    private final EducationApplicationService applicationService;
    private final ClassGroupService classGroupService;

    /* Education Application */

    @ApiOperation(value = "교육 신청 작성", notes = "교육을 신청합니다.")
    @PostMapping
    public SuccessResponse<?> create(@RequestBody @Valid EducationApplicationReq applicationReq) {
        EducationApplicationRes savedApplication = applicationService.save(applicationReq);
        return SuccessResponse.successResponse(savedApplication);
    }

    @ApiOperation(value = "휴대폰 번호로 작성한 교육 신청 조회", notes = "신청한 교육을 핸드폰 번호를 통해 조회합니다.")
    @GetMapping
    public SuccessResponse<?> read(@RequestBody @Valid RetrieveApplicationReq retrieveApplicationReq) {
        List<EducationApplicationRes> applications = applicationService
                .findByPhoneNumber(retrieveApplicationReq);
        return SuccessResponse.successResponse(applications);
    }

    @ApiOperation(value = "신청한 교육 수정", notes = "신청한 교육을 수정합니다.")
    @PutMapping("/{applicationId}")
    public SuccessResponse<?> update(@PathVariable Long applicationId,
            @RequestBody @Valid EducationApplicationReq applicationReq) {
        EducationApplicationRes updatedApplication = applicationService.update(applicationId, applicationReq);
        return SuccessResponse.successResponse(updatedApplication);
    }

    @ApiOperation(value = "신청한 교육 삭제", notes = "신청한 교육을 삭제합니다. 학급정보도 같이 삭제됩니다.")
    @DeleteMapping("/{applicationId}")
    public SuccessResponse<?> delete(@PathVariable Long applicationId) {
        applicationService.delete(applicationId);
        return SuccessResponse.successResponse("신청이 삭제되었습니다.");
    }

    /* Class Group */

    @ApiOperation(value = "신청한 교육에 학급정보 추가", notes = "우선 교육을 신청해야 합니다.")
    @PostMapping("/{applicationId}/classGroup")
    public SuccessResponse<?> addClassGroup(@PathVariable Long applicationId,
            @RequestBody @Valid ClassGroupReq classGroupReq) {
        ClassGroupRes savedClassGroup = classGroupService.addClassGroupToApplication(applicationId, classGroupReq);
        return SuccessResponse.successResponse(savedClassGroup);
    }

    @ApiOperation(value = "학급정보 업데이트", notes = "우선 교육을 신청해야 합니다.")
    @PutMapping("/{applicationId}/classGroup/{classGroupId}")
    public SuccessResponse<?> updateClassGroup(@PathVariable Long applicationId, @PathVariable Long classGroupId,
            @RequestBody @Valid ClassGroupReq classGroupReq) {
        ClassGroupRes updatedClassGroup = classGroupService.updateClassGroup(applicationId, classGroupId,
                classGroupReq);
        return SuccessResponse.successResponse(updatedClassGroup);
    }

    @ApiOperation(value = "학급정보 삭제", notes = "학급정보가 삭제됩니다.")
    @DeleteMapping("/{applicationId}/classGroup/{classGroupId}")
    public SuccessResponse<?> deleteClassGroup(@PathVariable Long applicationId, @PathVariable Long classGroupId) {
        classGroupService.deleteClassGroup(applicationId, classGroupId);
        return SuccessResponse.successResponse("학급정보가 삭제되었습니다.");
    }
}