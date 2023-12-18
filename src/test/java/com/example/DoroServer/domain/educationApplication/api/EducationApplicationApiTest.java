package com.example.DoroServer.domain.educationApplication.api;

import static com.example.DoroServer.domain.educationApplication.EducationApplicationTestSetup.getEducationApplication;
import static com.example.DoroServer.domain.educationApplication.EducationApplicationTestSetup.getEducationApplicationReq;
import static com.example.DoroServer.domain.educationApplication.EducationApplicationTestSetup.getEducationApplicationRes;
import static com.example.DoroServer.domain.educationApplication.EducationApplicationTestSetup.getRetrieveApplicationReq;
import static com.example.DoroServer.domain.educationApplication.EducationApplicationTestSetup.getUpdateEducationApplicationReq;
import static com.example.DoroServer.domain.educationApplication.EducationApplicationTestSetup.getUpdateEducationApplicationRes;
import static com.example.DoroServer.domain.educationApplicationClassGroup.ClassGroupTestSetup.getClassGroupReq;
import static com.example.DoroServer.domain.educationApplicationClassGroup.ClassGroupTestSetup.getClassGroupRes;
import static com.example.DoroServer.domain.educationApplicationClassGroup.ClassGroupTestSetup.getUpdateClassGroupReq;
import static com.example.DoroServer.domain.educationApplicationClassGroup.ClassGroupTestSetup.getUpdateClassGroupRes;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.DoroServer.domain.educationApplication.dto.EducationApplicationReq;
import com.example.DoroServer.domain.educationApplication.dto.EducationApplicationRes;
import com.example.DoroServer.domain.educationApplication.dto.RetrieveApplicationReq;
import com.example.DoroServer.domain.educationApplication.service.EducationApplicationService;
import com.example.DoroServer.domain.educationApplicationClassGroup.dto.ClassGroupReq;
import com.example.DoroServer.domain.educationApplicationClassGroup.dto.ClassGroupRes;
import com.example.DoroServer.domain.educationApplicationClassGroup.service.ClassGroupService;
import com.example.DoroServer.global.jwt.RedisService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EducationApplicationApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RedisService redisService;

    @MockBean
    private EducationApplicationService educationApplicationService;

    @MockBean
    private ClassGroupService classGroupService;

    private static ObjectMapper mapper;

    @BeforeAll
    static void setUp() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("MockMvc를 통한 교육 신청 작성 테스트")
    public void createEducationApplicationTest() throws Exception {
        // given
        EducationApplicationReq applicationReq = getEducationApplicationReq();
        EducationApplicationRes educationApplicationRes = getEducationApplicationRes();
        String phoneNumber = applicationReq.getPhoneNumber();

        given(redisService.getValues("some-session-id")).willReturn(phoneNumber);
        given(educationApplicationService.save(any(EducationApplicationReq.class), anyString()))
                .willReturn(educationApplicationRes);

        // when & then
        mockMvc.perform(post("/education-application")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Session-Id", "some-session-id")
                .content(mapper.writeValueAsString(applicationReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(educationApplicationRes.getId()))
                .andExpect(jsonPath("$.data.name").value(educationApplicationRes.getName()))
                .andExpect(jsonPath("$.data.institutionName")
                        .value(educationApplicationRes.getInstitutionName()))
                .andExpect(jsonPath("$.data.position").value(educationApplicationRes.getPosition()))
                .andExpect(jsonPath("$.data.phoneNumber")
                        .value(educationApplicationRes.getPhoneNumber()))
                .andExpect(jsonPath("$.data.email").value(educationApplicationRes.getEmail()))
                .andExpect(jsonPath("$.data.numberOfStudents")
                        .value(educationApplicationRes.getNumberOfStudents()))
                .andExpect(jsonPath("$.data.studentRank")
                        .value(educationApplicationRes.getStudentRank()))
                .andExpect(jsonPath("$.data.budget").value(educationApplicationRes.getBudget()))
                .andExpect(jsonPath("$.data.overallRemark")
                        .value(educationApplicationRes.getOverallRemark()));
    }

    @Test
    @DisplayName("MockMvc를 통한 신청한 교육 불러오기  테스트")
    public void retrieveApplicationTest() throws Exception {
        // given
        RetrieveApplicationReq retrieveApplicationReq = getRetrieveApplicationReq();
        EducationApplicationRes educationApplicationRes = getEducationApplicationRes();
        String phoneNumber = retrieveApplicationReq.getPhoneNumber();

        given(redisService.getValues("some-session-id")).willReturn(phoneNumber);
        given(educationApplicationService.findByPhoneNumber(any(RetrieveApplicationReq.class), anyString()))
                .willReturn(List.of(educationApplicationRes));

        // when & then
        mockMvc.perform(get("/education-application")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Session-Id", "some-session-id")
                .content(mapper.writeValueAsString(retrieveApplicationReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(educationApplicationRes.getId()))
                .andExpect(jsonPath("$.data[0].name").value(educationApplicationRes.getName()))
                .andExpect(jsonPath("$.data[0].institutionName")
                        .value(educationApplicationRes.getInstitutionName()))
                .andExpect(jsonPath("$.data[0].position").value(educationApplicationRes.getPosition()))
                .andExpect(jsonPath("$.data[0].phoneNumber")
                        .value(educationApplicationRes.getPhoneNumber()))
                .andExpect(jsonPath("$.data[0].email").value(educationApplicationRes.getEmail()))
                .andExpect(jsonPath("$.data[0].numberOfStudents")
                        .value(educationApplicationRes.getNumberOfStudents()))
                .andExpect(jsonPath("$.data[0].studentRank")
                        .value(educationApplicationRes.getStudentRank()))
                .andExpect(jsonPath("$.data[0].budget").value(educationApplicationRes.getBudget()))
                .andExpect(jsonPath("$.data[0].overallRemark")
                        .value(educationApplicationRes.getOverallRemark()));

    }

    @Test
    @DisplayName("MockMvc를 통한 신청한 교육 수정 테스트")
    void updateEducationApplicationTest() throws Exception {
        // given
        EducationApplicationReq applicationReq = getUpdateEducationApplicationReq();
        EducationApplicationRes educationApplicationRes = getUpdateEducationApplicationRes();
        String phoneNumber = applicationReq.getPhoneNumber();

        given(redisService.getValues("some-session-id")).willReturn(phoneNumber);
        given(educationApplicationService.update(anyLong(), any(EducationApplicationReq.class), anyString()))
                .willReturn(educationApplicationRes);

        // when & then
        mockMvc.perform(put("/education-application/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Session-Id", "some-session-id")
                .content(mapper.writeValueAsString(applicationReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(educationApplicationRes.getId()))
                .andExpect(jsonPath("$.data.name").value(educationApplicationRes.getName()))
                .andExpect(jsonPath("$.data.institutionName")
                        .value(educationApplicationRes.getInstitutionName()))
                .andExpect(jsonPath("$.data.position").value(educationApplicationRes.getPosition()))
                .andExpect(jsonPath("$.data.phoneNumber")
                        .value(educationApplicationRes.getPhoneNumber()))
                .andExpect(jsonPath("$.data.email").value(educationApplicationRes.getEmail()))
                .andExpect(jsonPath("$.data.numberOfStudents")
                        .value(educationApplicationRes.getNumberOfStudents()))
                .andExpect(jsonPath("$.data.studentRank")
                        .value(educationApplicationRes.getStudentRank()))
                .andExpect(jsonPath("$.data.budget").value(educationApplicationRes.getBudget()))
                .andExpect(jsonPath("$.data.overallRemark")
                        .value(educationApplicationRes.getOverallRemark()));
    }

    @Test
    @DisplayName("MockMvc를 통한 신청한 교육에 학급정보 추가 테스트")
    void addClassGroupToApplicationTest() throws Exception {
        // given
        ClassGroupReq classGroupReq = getClassGroupReq();
        ClassGroupRes classGroupRes = getClassGroupRes();
        String phoneNumber = getEducationApplication().getPhoneNumber();

        given(redisService.getValues("some-session-id")).willReturn(phoneNumber);
        given(classGroupService.addClassGroupToApplication(any(Long.class), any(ClassGroupReq.class), anyString()))
                .willReturn(classGroupRes);

        // when & then
        mockMvc.perform(post("/education-application/1/classGroup")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Session-Id", "some-session-id")
                .content(mapper.writeValueAsString(classGroupReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(classGroupRes.getId()))
                .andExpect(jsonPath("$.data.className").value(classGroupRes.getClassName()))
                .andExpect(jsonPath("$.data.educationConcept")
                        .value(classGroupRes.getEducationConcept()))
                .andExpect(jsonPath("$.data.numberOfStudents")
                        .value(classGroupRes.getNumberOfStudents()))
                .andExpect(jsonPath("$.data.remark").value(classGroupRes.getRemark()))
                .andExpect(jsonPath("$.data.unfixed").value(classGroupRes.isUnfixed()));
    }

    @Test
    @DisplayName("MockMvc를 통한 신청한 교육에 학급정보 업데이트 테스트")
    void updateClassGroupTest() throws Exception {
        // given
        ClassGroupReq classGroupReq = getUpdateClassGroupReq();
        ClassGroupRes classGroupRes = getUpdateClassGroupRes();
        String phoneNumber = getEducationApplication().getPhoneNumber();

        given(redisService.getValues("some-session-id")).willReturn(phoneNumber);
        given(classGroupService.updateClassGroup(any(Long.class), any(Long.class), any(ClassGroupReq.class),
                anyString()))
                .willReturn(classGroupRes);

        // when & then
        mockMvc.perform(put("/education-application/1/classGroup/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Session-Id", "some-session-id")
                .content(mapper.writeValueAsString(classGroupReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(classGroupRes.getId()))
                .andExpect(jsonPath("$.data.className").value(classGroupRes.getClassName()))
                .andExpect(jsonPath("$.data.educationConcept")
                        .value(classGroupRes.getEducationConcept()))
                .andExpect(jsonPath("$.data.numberOfStudents")
                        .value(classGroupRes.getNumberOfStudents()))
                .andExpect(jsonPath("$.data.remark").value(classGroupRes.getRemark()))
                .andExpect(jsonPath("$.data.unfixed").value(classGroupRes.isUnfixed()));
    }

    @Test
    @DisplayName("MockMvc를 통한 교육 신청서 작성 세션헤더 없이 접근 테스트")
    public void createEducationApplicationWithoutAuthenticationTest() throws Exception {
        // given
        EducationApplicationReq applicationReq = getEducationApplicationReq();

        // when & then
        mockMvc.perform(post("/education-application")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(applicationReq)))
                .andExpect(status().isUnauthorized());
    }

}
