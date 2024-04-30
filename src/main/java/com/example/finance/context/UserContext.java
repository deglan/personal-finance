package com.example.finance.context;

import com.example.finance.model.entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class UserContext {

    private final Long id;
    private final String userName;
    private final Set<RoleEntity> roleEntity;
}
