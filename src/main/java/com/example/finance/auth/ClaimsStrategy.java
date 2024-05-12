package com.example.finance.auth;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.finance.model.entity.UserAccountEntity;
import com.example.finance.model.enums.UserRole;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public enum ClaimsStrategy {

    ID {
        @Override
        public String createClaim(UserAccountEntity user) {
            return user.getUserId().toString();
        }

        @Override
        public Object getClaim(DecodedJWT decodedJWT) {
            return super.decodeClaim(decodedJWT).asLong();
        }
    },
    LOGIN {
        @Override
        public String createClaim(UserAccountEntity user) {
            return user.getLogin();
        }

        @Override
        public Object getClaim(DecodedJWT decodedJWT) {
            return super.decodeClaim(decodedJWT).asString();
        }
    },
    ROLE {
        @Override
        public String createClaim(UserAccountEntity user) {
            return Optional.ofNullable(user.getRole())
                    .map(roles -> roles.stream()
                            .map(Enum::toString)
                            .collect(Collectors.joining(SEPARATOR)))
                    .orElse(null);
        }

        @Override
        public Object getClaim(DecodedJWT decodedJWT) {
            Claim claim = super.decodeClaim(decodedJWT);
            String rolesString = claim.asString();
            return Optional.ofNullable(rolesString)
                    .map(rs -> Arrays.stream(rs.split(SEPARATOR))
                            .map(UserRole::from)
                            .toList())
                    .orElse(null);
        }
    };

    private static final String SEPARATOR = "::";
    private static final ClaimsStrategy[] VALUES = values();

    public abstract String createClaim(UserAccountEntity user);

    public abstract Object getClaim(DecodedJWT decodedJWT);

    private Claim decodeClaim(DecodedJWT decodedJWT) {
        return decodedJWT.getClaim(name());
    }

    public static Map<String, Object> getClaims(UserAccountEntity user) {
        return Arrays.stream(VALUES)
                .collect(Collectors.toMap(Enum::name, claimsStrategy -> claimsStrategy.createClaim(user)));
    }
}
