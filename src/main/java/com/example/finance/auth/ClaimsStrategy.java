package com.example.finance.auth;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.finance.model.entity.UserAccountEntity;

import java.util.Arrays;
import java.util.Map;
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
            return user.getRolesEntities().toString();
        }

        @Override
        public Object getClaim(DecodedJWT decodedJWT) {
            return super.decodeClaim(decodedJWT).asString();
        }
    };


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
