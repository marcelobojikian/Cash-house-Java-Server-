
INSERT INTO USERS (USERNAME, PASSWORD, ENABLED, ROLES, FLATMATE_ID, DASHBOARD_ID) VALUES ('marcelo@mail.com', '{bcrypt}$2a$10$bsOuE7PE2ADW6vcrSEJ8MuW0eGcbtdBmwqmYrY1VkZe5tSWoH6/4G', true, 'ROLE_USER', 1, 1);

INSERT INTO FLATMATE (NICKNAME, FIRST_STEP, GUEST_STEP, PROFILE_ID) VALUES ('Marcelo', false, false, 1);

INSERT INTO DASHBOARD (OWNER_ID) VALUES (1);

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
('es', 'flatmate.createBy.not.found', 'Flatmate createBy {0} not found');


INSERT INTO ACL_SID (ID, PRINCIPAL, SID) VALUES
(1, 0, 'ROLE_ADMIN'),
(2, 0, 'ROLE_USER'),
(3, 1, 'marcelo@mail.com');

INSERT INTO ACL_CLASS (ID, CLASS) VALUES
(1, 'br.com.cashhouse.core.model.Dashboard'),
(2, 'br.com.cashhouse.core.model.Cashier'),
(3, 'br.com.cashhouse.core.model.Flatmate'),
(4, 'br.com.cashhouse.core.model.Transaction');

INSERT INTO ACL_OBJECT_IDENTITY (ID, OBJECT_ID_CLASS, OBJECT_ID_IDENTITY, PARENT_OBJECT, OWNER_SID, ENTRIES_INHERITING) VALUES
								( 1,               1,                  1,          NULL,         3,                  0),
								( 2,               3,                  1,          NULL,         3,                  0);

INSERT INTO ACL_ENTRY (ID, ACL_OBJECT_IDENTITY, ACE_ORDER, SID, MASK, GRANTING, AUDIT_SUCCESS, AUDIT_FAILURE) VALUES
					  ( 1,                   1,         0,   3,    1,        1,             1,             1),
					  ( 2,                   1,         1,   3,    2,        1,             1,             1),
					  ( 3,                   1,         2,   3,    4,        1,             1,             1),
					  ( 4,                   1,         3,   3,    8,        1,             1,             1),
					  ( 5,                   2,         0,   3,    1,        1,             1,             1),
					  ( 6,                   2,         1,   3,    2,        1,             1,             1),
					  ( 7,                   2,         2,   3,    4,        1,             1,             1),
					  ( 8,                   2,         3,   3,    8,        1,             1,             1);

