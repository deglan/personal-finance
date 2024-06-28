package com.example.finance.controller;

import com.example.finance.business.ApiEndpoints;
import com.example.finance.configuration.TestMockConfiguration;
import com.example.finance.factory.BudgetMockFactory;
import com.example.finance.helper.MockMvcHelper;
import com.example.finance.model.dto.BudgetDto;
import com.example.finance.service.BudgetService;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.annotation.PostConstruct;

import static org.mockito.Mockito.when;

@ActiveProfiles(value = "unit-test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestMockConfiguration.class})
class BudgetControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    MockMvc mockMvc;
    @Autowired
    BudgetService budgetService;
    @Autowired
    ObjectMapper objectMapper;
    MockMvcHelper mockMvcHelper;

    @PostConstruct
    public void postConstruct() {
        mockMvcHelper = new MockMvcHelper(objectMapper);
    }

    @SneakyThrows
    @Test
    void getById_getBudgetById_success() {
        //GIVEN
        String url = TestControllerUtil.getUrl(ApiEndpoints.BUDGETS_GET_BY_ID.getPath(), port);
        //WHEN
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url, TestConstants.BUDGET_UUID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        BudgetDto budgetDto = mockMvcHelper.mapResponse(BudgetDto.class, mvcResult);
        //THEN
        Assertions.assertNotNull(budgetDto);
    }

    @SneakyThrows
    @Test
    void getByUserId_getBudgetsByUserId_success() {
        //GIVEN
        String url = TestControllerUtil.getUrl(ApiEndpoints.BUDGETS_GET_BY_ID.getPath(), port);
        //WHEN
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url, TestConstants.USER_UUID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        BudgetDto budgetDto = mockMvcHelper.mapResponse(BudgetDto.class, mvcResult);
        //THEN
        Assertions.assertNotNull(budgetDto);
    }

    @SneakyThrows
    @Test
    void create_createBudget_success() {
        //GIVEN
        String url = TestControllerUtil.getUrl(ApiEndpoints.BUDGETS_CREATE.getPath(), port);
        BudgetDto budgetDto = BudgetMockFactory.createBudgetDtoWithoutUUID();
        //WHEN
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mockMvcHelper.getContent(budgetDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
        BudgetDto savedBudgetDto = mockMvcHelper.mapResponse(BudgetDto.class, mvcResult);
        //THEN
        Assertions.assertNotNull(savedBudgetDto);
        Assertions.assertNotNull(savedBudgetDto.budgetId());
    }

    @SneakyThrows
    @Test
    void update_updateBudget_success() {
        //GIVEN
        BudgetDto budgetDto = BudgetMockFactory.createBudgetDto(TestConstants.BUDGET_TEST_MONTH);
        String url = TestControllerUtil.getUrl(ApiEndpoints.Endpoints.API + ApiEndpoints.Endpoints.BUDGET + "/" + budgetDto.budgetId(), port);
        System.out.println("URL: " + url);

        //WHEN
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(url, TestConstants.BUDGET_UUID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mockMvcHelper.getContent(budgetDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        BudgetDto savedBudgetDto = mockMvcHelper.mapResponse(BudgetDto.class, mvcResult);
        System.out.println("Response: " + mvcResult.getResponse().getContentAsString());

        //THEN
        Assertions.assertNotNull(savedBudgetDto);
        Assertions.assertNotNull(savedBudgetDto.budgetId());
        Assertions.assertEquals(budgetDto.month(), savedBudgetDto.month());
    }

    @SneakyThrows
    @Test
    void delete_deleteBudget_success() {
        String url = TestControllerUtil.getUrl(ApiEndpoints.BUDGETS_DELETE.getPath(), port);
        //WHEN
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(url, TestConstants.BUDGET_UUID))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andReturn();
        //THEN
        BudgetDto savedBudgetDto = mockMvcHelper.mapResponse(BudgetDto.class, mvcResult);
        Assertions.assertNull(savedBudgetDto);
    }
}