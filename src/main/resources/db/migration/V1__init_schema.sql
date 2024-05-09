CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE USER_ACCOUNT (
    USER_ID UUID NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),
    LOGIN VARCHAR(50) NOT NULL UNIQUE,
    PASSWORD VARCHAR(255) NOT NULL,
    EMAIL VARCHAR(255) NOT NULL UNIQUE,
    CREATED_DATE TIMESTAMP NOT NULL,
    LAST_LOGIN TIMESTAMP,
    ROLE VARCHAR(50),
    DELETED BOOLEAN NOT NULL DEFAULT FALSE,
    ACTIVE BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE CATEGORY (
    CATEGORY_ID UUID NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),
    USER_ID UUID NOT NULL,
    NAME VARCHAR(255) NOT NULL,
    TYPE VARCHAR(50) NOT NULL,
    DESCRIPTION TEXT,
    FOREIGN KEY (USER_ID) REFERENCES USER_ACCOUNT(USER_ID) ON DELETE CASCADE
);

CREATE TABLE BUDGET (
    BUDGET_ID UUID NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),
    USER_ID UUID NOT NULL,
    CATEGORY_ID UUID NOT NULL,
    AMOUNT DOUBLE PRECISION NOT NULL,
    MONTH INT NOT NULL,
    YEAR INT NOT NULL,
    FOREIGN KEY (USER_ID) REFERENCES USER_ACCOUNT(USER_ID) ON DELETE CASCADE,
    FOREIGN KEY (CATEGORY_ID) REFERENCES category(CATEGORY_ID) ON DELETE CASCADE
);

CREATE TABLE REPORT (
    REPORT_ID UUID NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),
    USER_ID UUID NOT NULL,
    REPORT_TYPE VARCHAR(255) NOT NULL,
    START_DATE DATE NOT NULL,
    END_DATE DATE NOT NULL,
    GENERATED_DATE DATE NOT NULL,
    FOREIGN KEY (USER_ID) REFERENCES USER_ACCOUNT(USER_ID) ON DELETE CASCADE
);

CREATE TABLE TRANSACTIONS (
    TRANSACTION_ID UUID NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),
    USER_ID UUID NOT NULL,
    CATEGORY_ID UUID NOT NULL,
    AMOUNT DOUBLE PRECISION NOT NULL,
    TYPE VARCHAR(255) NOT NULL,
    DATE DATE NOT NULL,
    DESCRIPTION TEXT,
    FOREIGN KEY (USER_ID) REFERENCES USER_ACCOUNT(USER_ID) ON DELETE CASCADE,
    FOREIGN KEY (CATEGORY_ID) REFERENCES category(CATEGORY_ID) ON DELETE CASCADE
);
