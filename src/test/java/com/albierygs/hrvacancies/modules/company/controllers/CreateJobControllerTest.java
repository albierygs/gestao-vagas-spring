package com.albierygs.hrvacancies.modules.company.controllers;

import com.albierygs.hrvacancies.modules.company.dto.CreateJobDTO;
import com.albierygs.hrvacancies.modules.company.entities.CompanyEntity;
import com.albierygs.hrvacancies.modules.company.repositories.CompanyRepository;
import com.albierygs.hrvacancies.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CreateJobControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CompanyRepository companyRepository;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(SecurityMockMvcConfigurers.springSecurity()).build();
    }

    @Test
    public void should_be_able_to_create_a_new_job() throws Exception {

        var company = CompanyEntity.builder()
                .email("test@gmail.com")
                .name("company_test")
                .username("company_test")
                .description("test")
                .password("123456")
                .website("https://test.com.br")
                .build();

        company = this.companyRepository.saveAndFlush(company);

        var job = CreateJobDTO.builder().benefits("benefits_test").level("level_test").description("description_tets").build();

        mockMvc.perform(
                MockMvcRequestBuilders.post("/company/job/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.objectToJSON(job))
                        .header("Authorization", TestUtils.generateToken(company.getId()))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void should_not_be_able_to_create_a_new_job_if_company_not_found() throws Exception {
        var job = CreateJobDTO.builder().benefits("benefits_test").level("level_test").description("description_tets").build();
        mockMvc.perform(
                MockMvcRequestBuilders.post("/company/job/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.objectToJSON(job))
                        .header("Authorization", TestUtils.generateToken(UUID.randomUUID()))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
