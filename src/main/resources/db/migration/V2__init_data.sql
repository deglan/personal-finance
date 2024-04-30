INSERT INTO USER_ACCOUNT (USER_ID, LOGIN, PASSWORD, EMAIL, CREATED_DATE, LAST_LOGIN, DELETED, ACTIVE) VALUES
--password : password1
(uuid_generate_v4(), 'user1', '$2a$16$xcGZYMOGay5xGVL2p3.vV.6mH4KCIgTKtKERz/M/rdRrNPM7EVR2S', 'user1@example.com', NOW(), NOW(), FALSE, TRUE),
(uuid_generate_v4(), 'user2', 'password2', 'user2@example.com', NOW(), NOW(), FALSE, TRUE),
(uuid_generate_v4(), 'user3', 'password3', 'user3@example.com', NOW(), NOW(), FALSE, TRUE),
(uuid_generate_v4(), 'user4', 'password4', 'user4@example.com', NOW(), NOW(), FALSE, TRUE),
(uuid_generate_v4(), 'user5', 'password5', 'user5@example.com', NOW(), NOW(), FALSE, TRUE);

INSERT INTO CATEGORY (CATEGORY_ID, NAME, TYPE, DESCRIPTION) VALUES
(uuid_generate_v4(), 'Groceries', 'Expense', 'Weekly food and supplies'),
(uuid_generate_v4(), 'Salary', 'Income', 'Monthly salary'),
(uuid_generate_v4(), 'Utilities', 'Expense', 'Monthly bills for utilities'),
(uuid_generate_v4(), 'Investments', 'Income', 'Investment returns'),
(uuid_generate_v4(), 'Entertainment', 'Expense', 'Expenses for entertainment');

INSERT INTO ROLE (ROLE_ID, ROLE) VALUES
(uuid_generate_v4(), 'Administrator'),
(uuid_generate_v4(), 'User'),
(uuid_generate_v4(), 'Viewer'),
(uuid_generate_v4(), 'Editor'),
(uuid_generate_v4(), 'Auditor');

INSERT INTO BUDGET (USER_ID, AMOUNT, MONTH, YEAR) VALUES
((SELECT USER_ID FROM USER_ACCOUNT WHERE LOGIN = 'user1'), 500.00, 9, 2023),
((SELECT USER_ID FROM USER_ACCOUNT WHERE LOGIN = 'user2'), 1500.00, 9, 2023),
((SELECT USER_ID FROM USER_ACCOUNT WHERE LOGIN = 'user3'), 750.00, 9, 2023),
((SELECT USER_ID FROM USER_ACCOUNT WHERE LOGIN = 'user4'), 1000.00, 9, 2023),
((SELECT USER_ID FROM USER_ACCOUNT WHERE LOGIN = 'user5'), 600.00, 9, 2023);

INSERT INTO BUDGET_CATEGORY (BUDGET_ID, CATEGORY_ID) VALUES
((SELECT BUDGET_ID FROM BUDGET WHERE USER_ID = (SELECT USER_ID FROM USER_ACCOUNT WHERE LOGIN = 'user1')), (SELECT CATEGORY_ID FROM CATEGORY WHERE NAME = 'Groceries')),
((SELECT BUDGET_ID FROM BUDGET WHERE USER_ID = (SELECT USER_ID FROM USER_ACCOUNT WHERE LOGIN = 'user1')), (SELECT CATEGORY_ID FROM CATEGORY WHERE NAME = 'Salary'));

INSERT INTO TRANSACTIONS (USER_ID, CATEGORY_ID, AMOUNT, TYPE, DATE, DESCRIPTION) VALUES
((SELECT USER_ID FROM USER_ACCOUNT WHERE LOGIN = 'user1'), (SELECT CATEGORY_ID FROM CATEGORY WHERE NAME = 'Groceries'), 150.50, 'Expense', '2023-09-01', 'Grocery shopping'),
((SELECT USER_ID FROM USER_ACCOUNT WHERE LOGIN = 'user2'), (SELECT CATEGORY_ID FROM CATEGORY WHERE NAME = 'Salary'), 2000.00, 'Income', '2023-09-05', 'Received salary');

INSERT INTO REPORT (USER_ID, REPORT_TYPE, START_DATE, END_DATE, GENERATED_DATE) VALUES
((SELECT USER_ID FROM USER_ACCOUNT WHERE LOGIN = 'user1'), 'Monthly', '2023-09-01', '2023-09-30', '2023-09-30'),
((SELECT USER_ID FROM USER_ACCOUNT WHERE LOGIN = 'user2'), 'Quarterly', '2023-07-01', '2023-09-30', '2023-09-30');

INSERT INTO USER_ACCOUNT_ROLE (USER_ID, ROLE_ID) VALUES
((SELECT USER_ID FROM USER_ACCOUNT WHERE LOGIN = 'user1'), (SELECT ROLE_ID FROM ROLE WHERE ROLE = 'Administrator')),
((SELECT USER_ID FROM USER_ACCOUNT WHERE LOGIN = 'user2'), (SELECT ROLE_ID FROM ROLE WHERE ROLE = 'User'));