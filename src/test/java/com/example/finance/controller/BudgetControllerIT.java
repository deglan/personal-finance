package com.example.finance.controller;

import com.example.finance.configuration.SecurityConfig;
import com.example.finance.service.BudgetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BudgetController.class)
@Import(SecurityConfig.class)
public class BudgetControllerIT {

    @Autowired
    WebApplicationContext wac;
    MockMvc mockMvc;
    @MockBean
    BudgetService budgetService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    @Test
    void getById_getBudgetById_unauthorized() throws Exception {
        mockMvc.perform(get("/api/budget/{id}", UUID.randomUUID()))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBudget() throws Exception {
        mockMvc.perform(delete("/api/budget/{id}", UUID.randomUUID())
                        .header("Api-Key", "spring")
                        .header("Api-Secret", "guru"))
                .andExpect(status().isOk());
    }

//    @Test
//    @WithMockUser("spring")
//    void getById_getBudgetById_success() throws Exception {
//        mockMvc.perform(get("/api/budget/{id}", UUID.randomUUID()))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void getById_getBudgetById_success_with_http() throws Exception {
//        mockMvc.perform(get("/api/budget/{id}", UUID.randomUUID()).with(httpBasic("spring","guru")))
//                .andExpect(status().isOk());
//    }
}
