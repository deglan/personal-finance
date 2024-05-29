package com.example.finance.controller;

import com.example.finance.business.ApiEndpoints;
import com.example.finance.configuration.TestMockConfiguration;
import com.example.finance.factory.TransactionMockFactory;
import com.example.finance.factory.UserMockFactory;
import com.example.finance.helper.MockMvcHelper;
import com.example.finance.model.dto.TransactionDto;
import com.example.finance.model.dto.UserAccountDto;
import com.example.finance.service.TransactionService;
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

@ActiveProfiles(value = "unit-test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestMockConfiguration.class})
public class TransactionControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    MockMvc mockMvc;
    @Autowired
    TransactionService transactionService;
    @Autowired
    ObjectMapper objectMapper;
    MockMvcHelper mockMvcHelper;

    @PostConstruct
    public void postConstruct() {
        mockMvcHelper = new MockMvcHelper(objectMapper);
    }

    @SneakyThrows
    @Test
    void transaction_getTransactionById_success() {
        //GIVEN
        String url = TestControllerUtil.getUrl(ApiEndpoints.TRANSACTIONS_GET_BY_ID.getPath(), port);
        //WHEN
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url, TestConstants.TRANSACTION_UUID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        TransactionDto transactionDto = mockMvcHelper.mapResponse(TransactionDto.class, mvcResult);
        //THEN
        Assertions.assertNotNull(transactionDto);
    }

    @SneakyThrows
    @Test
    void transaction_createTransaction_success() {
        //GIVEN
        TransactionDto transactionDto = TransactionMockFactory.createTransactionDtoWithoutTransactionUUID();
        String url = TestControllerUtil.getUrl(ApiEndpoints.TRANSACTIONS_CREATE.getPath(), port);
        //WHEN
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mockMvcHelper.getContent(transactionDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
        TransactionDto savedTransactionDto = mockMvcHelper.mapResponse(TransactionDto.class, mvcResult);
        //THEN
        Assertions.assertNotNull(savedTransactionDto);
        Assertions.assertNotNull(savedTransactionDto.transactionId());
    }

    @SneakyThrows
    @Test
    void transaction_updateTransaction_success() {
        //GIVEN
        TransactionDto transactionDto = TransactionMockFactory.createTransactionDto(TestConstants.TRANSACTION_TEST_DESCRIPTION);
        String url = TestControllerUtil.getUrl(ApiEndpoints.TRANSACTIONS_UPDATE.getPath(), port);
        //WHEN
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(url, TestConstants.TRANSACTION_UUID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mockMvcHelper.getContent(transactionDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        TransactionDto savedTransactionDto = mockMvcHelper.mapResponse(TransactionDto.class, mvcResult);
        //THEN
        Assertions.assertNotNull(savedTransactionDto);
        Assertions.assertNotNull(savedTransactionDto.transactionId());
        Assertions.assertEquals(transactionDto.description(), savedTransactionDto.description());
    }

    @SneakyThrows
    @Test
    void transaction_deleteTransaction_success() {
        //GIVEN
        String url = TestControllerUtil.getUrl(ApiEndpoints.TRANSACTIONS_DELETE.getPath(), port);
        //WHEN
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(url, TestConstants.TRANSACTION_UUID))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andReturn();
        //THEN
        TransactionDto savedTransactionDto = mockMvcHelper.mapResponse(TransactionDto.class, mvcResult);
        Assertions.assertNull(savedTransactionDto);
    }
}
