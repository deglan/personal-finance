package com.example.finance.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

@AllArgsConstructor
public class MockMvcHelper {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    private <T> T readValue(Class<T> responseType, String content) {
        return objectMapper.readValue(content, responseType);
    }

    @SneakyThrows
    public <T> T mapResponse(Class<T> responseType, MvcResult result) {
        if (Void.class.equals(responseType)) {
            return null;
        }
        String contentAsString = result.getResponse()
                .getContentAsString();
        if (String.class.equals(responseType)) {
            return (T) contentAsString;
        }
        return Optional.of(contentAsString)
                .filter(StringUtils::isNotBlank)
                .map(content -> readValue(responseType, content))
                .orElse(null);
    }

    @SneakyThrows
    public <R> String getContent(R requestBody) {
        return objectMapper.writeValueAsString(requestBody);
    }
}
