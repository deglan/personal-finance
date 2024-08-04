package com.example.finance.repository;

import com.example.finance.model.entity.UserAccountEntity;
import com.example.finance.model.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-unit-test.yml")
public class UserAccountRepositoryTest {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Test
    public void testFindByEmail() {
        // Prepare test data
        UserAccountEntity user = UserAccountEntity.builder()
                .userId(UUID.randomUUID())
                .login("user1")
                .password("$2a$16$xcGZYMOGay5xGVL2p3.vV.6mH4KCIgTKtKERz/M/rdRrNPM7EVR2S")
                .email("user1@example.com")
                .role(List.of(UserRole.USER))
                .active(true)
                .deleted(false)
                .build();
        userAccountRepository.save(user);

        // Test the repository method
        Optional<UserAccountEntity> foundUser = userAccountRepository.findByEmail("user1@example.com");

        // Assert the results
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("user1@example.com");
        assertThat(foundUser.get().getLogin()).isEqualTo("user1");
        assertThat(foundUser.get().getPassword()).isNotNull();
        assertThat(foundUser.get().getRole()).isNotEmpty();
    }
}
