package com.example.finance.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

@UtilityClass
public class TestControllerUtil {

    public static String getUrl(String path, int port) {
        return "http://localhost:" + port + path;
    }
}
