package com.example.finance.security;

import com.example.finance.model.entity.UserAccountEntity;
import com.example.finance.model.enums.UserRole;
import com.example.finance.repository.UserAccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Test
    public void testUserAuthentication() throws Exception {
        UserAccountEntity user = UserAccountEntity.builder()
                .email("test@example.com")
                .password(new BCryptPasswordEncoder().encode("password"))
                .login("testuser")
                .role(List.of(UserRole.USER))
                .build();

        userAccountRepository.save(user);

        mockMvc.perform(formLogin().user("test@example.com").password("password"))
                .andExpect(authenticated())
                .andExpect(status().isOk());
    }
}
