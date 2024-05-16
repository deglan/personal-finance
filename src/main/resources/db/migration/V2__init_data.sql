INSERT INTO USER_ACCOUNT (USER_ID, LOGIN, PASSWORD, EMAIL, CREATED_DATE, LAST_LOGIN, ROLE, DELETED, ACTIVE) VALUES
-- password : password1
('6a6b9409-b2cb-4479-b718-99edc1d05341', 'user1', '$2a$16$xcGZYMOGay5xGVL2p3.vV.6mH4KCIgTKtKERz/M/rdRrNPM7EVR2S', 'user1@example.com', NOW(), NOW(), 'ADMINISTRATOR::USER', FALSE, TRUE),
('6a6b9409-b2cb-4479-b718-99edc1d05342', 'user2', '$2a$16$xcGZYMOGay5xGVL2p3.vV.6mH4KCIgTKtKERz/M/rdRrNPM7EVR2S', 'user2@example.com', NOW(), NOW(), 'USER', FALSE, TRUE),
('6a6b9409-b2cb-4479-b718-99edc1d05343', 'user3', '$2a$16$xcGZYMOGay5xGVL2p3.vV.6mH4KCIgTKtKERz/M/rdRrNPM7EVR2S', 'user3@example.com', NOW(), NOW(), 'USER', FALSE, TRUE),
('6a6b9409-b2cb-4479-b718-99edc1d05344', 'user4', '$2a$16$xcGZYMOGay5xGVL2p3.vV.6mH4KCIgTKtKERz/M/rdRrNPM7EVR2S', 'user4@example.com', NOW(), NOW(), 'USER', FALSE, TRUE),
('6a6b9409-b2cb-4479-b718-99edc1d05345', 'user5', '$2a$16$xcGZYMOGay5xGVL2p3.vV.6mH4KCIgTKtKERz/M/rdRrNPM7EVR2S', 'user5@example.com', NOW(), NOW(), 'USER', FALSE, TRUE);

INSERT INTO CATEGORY (CATEGORY_ID, USER_ID, NAME, TYPE, DESCRIPTION) VALUES
('6a6b9409-b2cb-4479-b718-99edc1d05346', '6a6b9409-b2cb-4479-b718-99edc1d05341', 'Groceries', 'EXPENSE', 'Weekly food and supplies'),
('6a6b9409-b2cb-4479-b718-99edc1d05347', '6a6b9409-b2cb-4479-b718-99edc1d05341', 'Salary', 'INCOME', 'Monthly salary'),
('6a6b9409-b2cb-4479-b718-99edc1d05348', '6a6b9409-b2cb-4479-b718-99edc1d05341', 'Utilities', 'EXPENSE', 'Monthly bills for utilities'),
('6a6b9409-b2cb-4479-b718-99edc1d05349', '6a6b9409-b2cb-4479-b718-99edc1d05341', 'Investments', 'INCOME', 'Investment returns'),
('6a6b9409-b2cb-4479-b718-99edc1d05350', '6a6b9409-b2cb-4479-b718-99edc1d05341', 'Entertainment', 'EXPENSE', 'Expenses for entertainment');

INSERT INTO BUDGET (BUDGET_ID, USER_ID, CATEGORY_ID, AMOUNT, MONTH, YEAR) VALUES
( '6a6b9409-b2cb-4479-b718-99edc1d05355','6a6b9409-b2cb-4479-b718-99edc1d05341', '6a6b9409-b2cb-4479-b718-99edc1d05346', 500.00, 9, 2023),
( '6a6b9409-b2cb-4479-b718-99edc1d05356','6a6b9409-b2cb-4479-b718-99edc1d05341', '6a6b9409-b2cb-4479-b718-99edc1d05346', 1500.00, 9, 2023),
( '6a6b9409-b2cb-4479-b718-99edc1d05357','6a6b9409-b2cb-4479-b718-99edc1d05341', '6a6b9409-b2cb-4479-b718-99edc1d05346', 750.00, 9, 2023),
( '6a6b9409-b2cb-4479-b718-99edc1d05358','6a6b9409-b2cb-4479-b718-99edc1d05341', '6a6b9409-b2cb-4479-b718-99edc1d05346', 1000.00, 9, 2023),
( '6a6b9409-b2cb-4479-b718-99edc1d05359','6a6b9409-b2cb-4479-b718-99edc1d05341', '6a6b9409-b2cb-4479-b718-99edc1d05346', 600.00, 9, 2023);

INSERT INTO TRANSACTIONS (TRANSACTION_ID, USER_ID, CATEGORY_ID, AMOUNT, TYPE, DATE, DESCRIPTION) VALUES
( '6a6b9409-b2cb-4479-b718-99edc1d05360','6a6b9409-b2cb-4479-b718-99edc1d05341', '6a6b9409-b2cb-4479-b718-99edc1d05346', 150.50, 'EXPENSE', '2023-09-01', 'Grocery shopping'),
( '6a6b9409-b2cb-4479-b718-99edc1d05361','6a6b9409-b2cb-4479-b718-99edc1d05341', '6a6b9409-b2cb-4479-b718-99edc1d05346', 2000.00, 'INCOME', '2023-09-05', 'Received salary');

INSERT INTO REPORT (REPORT_ID, USER_ID, REPORT_TYPE, START_DATE, END_DATE, GENERATED_DATE) VALUES
('6a6b9409-b2cb-4479-b718-99edc1d05351', '6a6b9409-b2cb-4479-b718-99edc1d05341', 'Monthly Expense Report', '2023-09-01', '2023-09-30', NOW()),
('6a6b9409-b2cb-4479-b718-99edc1d05352', '6a6b9409-b2cb-4479-b718-99edc1d05341', 'Annual Income Report', '2023-01-01', '2023-12-31', NOW()),
('6a6b9409-b2cb-4479-b718-99edc1d05353', '6a6b9409-b2cb-4479-b718-99edc1d05341', 'Quarterly Savings Report', '2023-07-01', '2023-09-30', NOW()),
('6a6b9409-b2cb-4479-b718-99edc1d05354', '6a6b9409-b2cb-4479-b718-99edc1d05341', 'Investment Performance Report', '2023-01-01', '2023-12-31', NOW());
