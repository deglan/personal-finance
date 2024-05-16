package com.example.finance.context;

import com.example.finance.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class UserContext {

    private final Long id;
    private final String userName;
    private final List<UserRole> role;
}
