package com.example.finance.controller;

import com.example.finance.business.ApiEndpoints;
import com.example.finance.configuration.TestMockConfiguration;
import com.example.finance.factory.UserMockFactory;
import com.example.finance.helper.MockMvcHelper;
import com.example.finance.model.dto.UserAccountDto;
import com.example.finance.service.UserAccountService;
import com.example.finance.utils.TestConstants;
import com.example.finance.utils.TestControllerUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.annotation.PostConstruct;

@ActiveProfiles(value = "unit-test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestMockConfiguration.class})
public class UserAccountControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserAccountService userAccountService;
    @Autowired
    ObjectMapper objectMapper;
    MockMvcHelper mockMvcHelper;

    @PostConstruct
    public void postConstruct() {
        mockMvcHelper = new MockMvcHelper(objectMapper);
    }

    @SneakyThrows
    @Test
    void user_getById_success() {
        //GIVEN
        String url = TestControllerUtil.getUrl(ApiEndpoints.USER_GET_BY_ID.getPath(), port);
        //WHEN
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .param("id", TestConstants.USER_UUID.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        UserAccountDto userAccountDto = mockMvcHelper.mapResponse(UserAccountDto.class, mvcResult);
        //THEN
        Assertions.assertNotNull(userAccountDto);
    }
}
