package com.example.finance.auth;

import com.example.finance.model.enums.UserRole;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class RoleConverterTest {

    private final RoleConverter roleConverter = new RoleConverter();

    @Test
    public void testConvertToDatabaseColumn() {
        List<UserRole> roles = Arrays.asList(UserRole.ADMINISTRATOR, UserRole.USER);
        String dbData = roleConverter.convertToDatabaseColumn(roles);
        assertThat(dbData).isEqualTo("ADMINISTRATOR::USER");
    }

    @Test
    public void testConvertToEntityAttribute() {
        String dbData = "ADMINISTRATOR::USER";
        List<UserRole> roles = roleConverter.convertToEntityAttribute(dbData);
        assertThat(roles).containsExactly(UserRole.ADMINISTRATOR, UserRole.USER);
    }
}
