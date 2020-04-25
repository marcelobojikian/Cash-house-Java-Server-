
INSERT INTO FLATMATE (NICKNAME, EMAIL, FIRST_STEP, GUEST_STEP) VALUES ('Marcelo', 'marcelo@mail.com', false, false);
INSERT INTO FLATMATE (NICKNAME, EMAIL, FIRST_STEP, GUEST_STEP) VALUES ('Biro', 'biro@mail.com', false, true);
INSERT INTO FLATMATE (NICKNAME, EMAIL, FIRST_STEP, GUEST_STEP) VALUES ('Eduardo', 'eduardo@mail.com', false, true);
INSERT INTO FLATMATE (NICKNAME, EMAIL, FIRST_STEP, GUEST_STEP) VALUES ('Willian', 'will@mail.com', false, true);
INSERT INTO FLATMATE (NICKNAME, EMAIL, FIRST_STEP, GUEST_STEP) VALUES ('Aime', 'aime@mail.com', false, true);
INSERT INTO FLATMATE (NICKNAME, EMAIL, FIRST_STEP, GUEST_STEP) VALUES ('Carol', 'carol@mail.com', false, true);
INSERT INTO FLATMATE (NICKNAME, EMAIL, FIRST_STEP, GUEST_STEP) VALUES ('Raissa', 'rai@mail.com', false, true);
INSERT INTO FLATMATE (NICKNAME, EMAIL, FIRST_STEP, GUEST_STEP) VALUES ('Jean (test)', 'jean@mail.com', false, false);
INSERT INTO FLATMATE (NICKNAME, EMAIL, FIRST_STEP, GUEST_STEP) VALUES ('Gretchen (test)', 'gretchen@mail.com', false, true);
INSERT INTO FLATMATE (NICKNAME, EMAIL, FIRST_STEP, GUEST_STEP) VALUES ('Fernando (test)', 'fernando@mail.com', true, true);

INSERT INTO DASHBOARD (OWNER_ID) VALUES (1);
INSERT INTO DASHBOARD (OWNER_ID) VALUES (8);

INSERT INTO CASHIER (OWNER_ID, BALANCE, NAME, STARTED) VALUES (1, 32.54, 'Energy & bin', 0);
INSERT INTO CASHIER (OWNER_ID, BALANCE, NAME, STARTED) VALUES (1, 120, 'Geral', 23);
INSERT INTO CASHIER (OWNER_ID, BALANCE, NAME, STARTED) VALUES (8, 3.11, 'Rent & Clean', 12.45);

INSERT INTO DASHBOARD_CASHIER (ID_DASHBOARD, ID_CASHIER) VALUES (1, 1);
INSERT INTO DASHBOARD_CASHIER (ID_DASHBOARD, ID_CASHIER) VALUES (1, 2);
INSERT INTO DASHBOARD_CASHIER (ID_DASHBOARD, ID_CASHIER) VALUES (2, 3);

INSERT INTO DASHBOARD_GUEST (ID_DASHBOARD, ID_FLATMATE) VALUES (1, 2);
INSERT INTO DASHBOARD_GUEST (ID_DASHBOARD, ID_FLATMATE) VALUES (1, 3);
INSERT INTO DASHBOARD_GUEST (ID_DASHBOARD, ID_FLATMATE) VALUES (1, 4);
INSERT INTO DASHBOARD_GUEST (ID_DASHBOARD, ID_FLATMATE) VALUES (1, 5);
INSERT INTO DASHBOARD_GUEST (ID_DASHBOARD, ID_FLATMATE) VALUES (1, 6);
INSERT INTO DASHBOARD_GUEST (ID_DASHBOARD, ID_FLATMATE) VALUES (1, 7);
INSERT INTO DASHBOARD_GUEST (ID_DASHBOARD, ID_FLATMATE) VALUES (2, 9);
INSERT INTO DASHBOARD_GUEST (ID_DASHBOARD, ID_FLATMATE) VALUES (2, 10);

INSERT INTO DASHBOARD_GUEST (ID_DASHBOARD, ID_FLATMATE) VALUES (2, 1);

INSERT INTO LANGUAGE (LOCALE, MESSAGE_KEY, MESSAGE_CONTENT) VALUES
('en', 'cashier.not.found','Cashier {0} not found'),
('en', 'flatmate.not.found', 'Flatmate {0} not found'),
('en', 'transaction.not.found', 'Transaction {0} not found'),
('en', 'transaction.status.invalid.operation', 'Invalid operation, Transaction {0} with status equal to {1}'),
('en', 'flatmate.access.denied', 'Flatmate {0} does not have permissions for the resource'),
('en', 'flatmate.access.field.denied', 'Flatmate {0} does not have permissions for field {1}'),
('en', 'flatmate.assigned.not.found', 'Flatmate assigned {0} not found'),
('en', 'flatmate.createBy.not.found', 'Flatmate createBy {0} not found'),

('pt', 'cashier.not.found','Caixinha {0} não encontrada'),
('pt', 'flatmate.not.found', 'Flatmate {0} não encontrado'),
('pt', 'transaction.not.found', 'Transação {0} não encontrada'),
('pt', 'transaction.status.invalid.operation', 'Operação invalid, Transação {0} com status igual a {1}'),
('pt', 'flatmate.access.denied', 'Flatmate {0} não tem permissão para esse recurso'),
('pt', 'flatmate.access.field.denied', 'Flatmate {0} não tem permissão para o recurso {1}'),
('pt', 'flatmate.assigned.not.found', 'Flatmate assinado {0} não encontrado para a Transação'),
('pt', 'flatmate.createBy.not.found', 'Flatmate criador {0} não encontrado para a Transação'),

('es', 'cashier.not.found','Cashier {0} not found'),
('es', 'flatmate.not.found', 'Flatmate {0} not found'),
('es', 'transaction.not.found', 'Transaction {0} not found'),
('es', 'transaction.status.invalid.operation', 'Invalid operation, Transaction {0} with status equal to {1}'),
('es', 'flatmate.access.denied', 'Flatmate {0} does not have permissions for the resource'),
('es', 'flatmate.access.field.denied', 'Flatmate {0} does not have permissions for field {1}'),
('es', 'flatmate.assigned.not.found', 'Flatmate assigned {0} not found'),
('es', 'flatmate.createBy.not.found', 'Flatmate createBy {0} not found')
;
