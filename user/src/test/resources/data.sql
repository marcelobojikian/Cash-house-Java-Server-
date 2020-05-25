
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

INSERT INTO ACL_CLASS (ID, CLASS) VALUES
(1, 'br.com.cashhouse.core.model.Profile'),
(2, 'br.com.cashhouse.core.model.Dashboard'),
(3, 'br.com.cashhouse.core.model.Cashier'),
(4, 'br.com.cashhouse.core.model.Flatmate'),
(5, 'br.com.cashhouse.core.model.Transaction');

INSERT INTO USERS(ID, USERNAME, PASSWORD, ENABLED, ROLES) VALUES
				 (1, 'marcelo@mail.com',   '{bcrypt}$2a$10$YE3uTYrhiFaM45/m7uey/.DXWsodRKIIbAy01yUuf6rBkrveDpkFm', TRUE, 'ROLE_USER'),
				 (2, 'biro@mail.com',      '{bcrypt}$2a$10$MKegvrrWaXTaX738UJPd0OUfdWhkZkjtY22kp8M/B0iSz7iRRFtsa', TRUE, 'ROLE_USER'),
				 (3, 'eduardo@mail.com',   '{bcrypt}$2a$10$nhTrA4G255bjzNkKaujUcu9jT1bN.yjcZFrLuLKgZO2YF1I.SAOfi', TRUE, 'ROLE_USER'),
				 (4, 'will@mail.com',      '{bcrypt}$2a$10$rDFf7Mlqx7YmK/X5WIFwHeKxL3.uy5f25ytwuH3hmBt5DcC3kanKu', TRUE, 'ROLE_USER'),
				 (5, 'aimee@mail.com',     '{bcrypt}$2a$10$eHRnl2pvUBaxPqYOjmsBPeR6tTNzB0e.Nc5Vv5jrfmV3AN6zFfbXW', TRUE, 'ROLE_USER'),
				 (6, 'carol@mail.com',     '{bcrypt}$2a$10$8qyxqXxTj/qnUgwoaQMTo.Kwbi39Lax0zqKV1gsg7LBrgbblTcRaO', TRUE, 'ROLE_USER'),
				 (7, 'rai@mail.com',       '{bcrypt}$2a$10$UevCJGhVFTL2qd3/CkwpaesNVPiwcg5v1pOUzkWlVKKiiaIpSr5TK', TRUE, 'ROLE_USER'),
				 (8, 'jean@mail.com',      '{bcrypt}$2a$10$lmO5lgXg4C4ZvyttDAnkoedLCnGCot1jzqkFIAPcKhuVd6I30ViAK', TRUE, 'ROLE_USER'),
				 (9, 'gretchen@mail.com',  '{bcrypt}$2a$10$Yt8w2iZBC1WsebwKw83Gse2AXOzFOBE9GPmgWOVvAuOaAdrL.ckMi', TRUE, 'ROLE_USER'),
				 (10, 'fernando@mail.com', '{bcrypt}$2a$10$Pa2xNvOt4IATUugPZoa2l.yj5G4U5vDPS12jp/EHMEPorRMH6KqCq', TRUE, 'ROLE_USER');

INSERT INTO DASHBOARD(ID) VALUES
					 ( 1),
					 ( 2),
					 ( 3),
					 ( 4),
					 ( 5),
					 ( 6),
					 ( 7),
					 ( 8),
					 ( 9),
					 (10);

UPDATE USERS SET DASHBOARD_ID = 1  WHERE ID = 1;
UPDATE USERS SET DASHBOARD_ID = 2  WHERE ID = 2;
UPDATE USERS SET DASHBOARD_ID = 3  WHERE ID = 3;
UPDATE USERS SET DASHBOARD_ID = 4  WHERE ID = 4;
UPDATE USERS SET DASHBOARD_ID = 5  WHERE ID = 5;
UPDATE USERS SET DASHBOARD_ID = 6  WHERE ID = 6;
UPDATE USERS SET DASHBOARD_ID = 7  WHERE ID = 7;
UPDATE USERS SET DASHBOARD_ID = 8  WHERE ID = 8;
UPDATE USERS SET DASHBOARD_ID = 9  WHERE ID = 9;
UPDATE USERS SET DASHBOARD_ID = 10 WHERE ID = 10;

UPDATE DASHBOARD SET OWNER_ID = 1  WHERE ID = 1;
UPDATE DASHBOARD SET OWNER_ID = 2  WHERE ID = 2;
UPDATE DASHBOARD SET OWNER_ID = 3  WHERE ID = 3;
UPDATE DASHBOARD SET OWNER_ID = 4  WHERE ID = 4;
UPDATE DASHBOARD SET OWNER_ID = 5  WHERE ID = 5;
UPDATE DASHBOARD SET OWNER_ID = 6  WHERE ID = 6;
UPDATE DASHBOARD SET OWNER_ID = 7  WHERE ID = 7;
UPDATE DASHBOARD SET OWNER_ID = 8  WHERE ID = 8;
UPDATE DASHBOARD SET OWNER_ID = 9  WHERE ID = 9;
UPDATE DASHBOARD SET OWNER_ID = 10 WHERE ID = 10;
							
INSERT INTO ACL_SID(ID, PRINCIPAL, SID) VALUES
				   ( 1,         0, 'ROLE_ADMIN'),
				   ( 2,         0, 'ROLE_USER'),
				   ( 3,         1, 'system'),
				   ( 4,         1, 'marcelo@mail.com'),
				   ( 5,         1, 'biro@mail.com'),
				   ( 6,         1, 'eduardo@mail.com'),
				   ( 7,         1, 'will@mail.com'),
				   ( 8,         1, 'aimee@mail.com'),
				   ( 9,         1, 'carol@mail.com'),
				   (10,         1, 'rai@mail.com'),
				   (11,         1, 'jean@mail.com'),
				   (12,         1, 'gretchen@mail.com'),
				   (13,         1, 'fernando@mail.com');

INSERT INTO ACL_OBJECT_IDENTITY(ID, OBJECT_ID_CLASS, OBJECT_ID_IDENTITY, PARENT_OBJECT, OWNER_SID, ENTRIES_INHERITING) VALUES
							   ( 1,               1,                  1,          NULL,         4, 1),
							   ( 2,               2,                  1,          NULL,         4, 1),
							   ( 3,               1,                  2,          NULL,         5, 1),
							   ( 4,               2,                  2,          NULL,         5, 1),
							   ( 5,               1,                  3,          NULL,         6, 1),
							   ( 6,               2,                  3,          NULL,         6, 1),
							   ( 7,               1,                  4,          NULL,         7, 1),
							   ( 8,               2,                  4,          NULL,         7, 1),
							   ( 9,               1,                  5,          NULL,         8, 1),
							   (10,               2,                  5,          NULL,         8, 1),
							   (11,               1,                  6,          NULL,         9, 1),
							   (12,               2,                  6,          NULL,         9, 1),
							   (13,               1,                  7,          NULL,        10, 1),
							   (14,               2,                  7,          NULL,        10, 1),
							   (15,               1,                  8,          NULL,        11, 1),
							   (16,               2,                  8,          NULL,        11, 1),
							   (17,               1,                  9,          NULL,        12, 1),
							   (18,               2,                  9,          NULL,        12, 1),
							   (19,               1,                 10,          NULL,        13, 1),
							   (20,               2,                 10,          NULL,        13, 1);

INSERT INTO ACL_ENTRY(ID, ACL_OBJECT_IDENTITY, ACE_ORDER, SID, MASK, GRANTING, AUDIT_SUCCESS, AUDIT_FAILURE) VALUES
					(1, 1, 0, 4, 1, 1, 0, 0),
					(2, 1, 1, 4, 2, 1, 0, 0),
					(3, 1, 2, 4, 4, 1, 0, 0),
					(4, 1, 3, 4, 8, 1, 0, 0),
					(5, 2, 0, 4, 1, 1, 0, 0),
					(6, 2, 1, 4, 2, 1, 0, 0),
					(7, 2, 2, 4, 4, 1, 0, 0),
					(8, 2, 3, 4, 8, 1, 0, 0),
					(9, 3, 0, 5, 1, 1, 0, 0),
					(10, 3, 1, 5, 2, 1, 0, 0),
					(11, 3, 2, 5, 4, 1, 0, 0),
					(12, 3, 3, 5, 8, 1, 0, 0),
					(13, 4, 0, 5, 1, 1, 0, 0),
					(14, 4, 1, 5, 2, 1, 0, 0),
					(15, 4, 2, 5, 4, 1, 0, 0),
					(16, 4, 3, 5, 8, 1, 0, 0),
					(17, 5, 0, 6, 1, 1, 0, 0),
					(18, 5, 1, 6, 2, 1, 0, 0),
					(19, 5, 2, 6, 4, 1, 0, 0),
					(20, 5, 3, 6, 8, 1, 0, 0),
					(21, 6, 0, 6, 1, 1, 0, 0),
					(22, 6, 1, 6, 2, 1, 0, 0),
					(23, 6, 2, 6, 4, 1, 0, 0),
					(24, 6, 3, 6, 8, 1, 0, 0),
					(25, 7, 0, 7, 1, 1, 0, 0),
					(26, 7, 1, 7, 2, 1, 0, 0),
					(27, 7, 2, 7, 4, 1, 0, 0),
					(28, 7, 3, 7, 8, 1, 0, 0),
					(29, 8, 0, 7, 1, 1, 0, 0),
					(30, 8, 1, 7, 2, 1, 0, 0),
					(31, 8, 2, 7, 4, 1, 0, 0),
					(32, 8, 3, 7, 8, 1, 0, 0),
					(33, 9, 0, 8, 1, 1, 0, 0),
					(34, 9, 1, 8, 2, 1, 0, 0),
					(35, 9, 2, 8, 4, 1, 0, 0),
					(36, 9, 3, 8, 8, 1, 0, 0),
					(37, 10, 0, 8, 1, 1, 0, 0),
					(38, 10, 1, 8, 2, 1, 0, 0),
					(39, 10, 2, 8, 4, 1, 0, 0),
					(40, 10, 3, 8, 8, 1, 0, 0),
					(41, 11, 0, 9, 1, 1, 0, 0),
					(42, 11, 1, 9, 2, 1, 0, 0),
					(43, 11, 2, 9, 4, 1, 0, 0),
					(44, 11, 3, 9, 8, 1, 0, 0),
					(45, 12, 0, 9, 1, 1, 0, 0),
					(46, 12, 1, 9, 2, 1, 0, 0),
					(47, 12, 2, 9, 4, 1, 0, 0),
					(48, 12, 3, 9, 8, 1, 0, 0),
					(49, 13, 0, 10, 1, 1, 0, 0),
					(50, 13, 1, 10, 2, 1, 0, 0),
					(51, 13, 2, 10, 4, 1, 0, 0),
					(52, 13, 3, 10, 8, 1, 0, 0),
					(53, 14, 0, 10, 1, 1, 0, 0),
					(54, 14, 1, 10, 2, 1, 0, 0),
					(55, 14, 2, 10, 4, 1, 0, 0),
					(56, 14, 3, 10, 8, 1, 0, 0),
					(57, 15, 0, 11, 1, 1, 0, 0),
					(58, 15, 1, 11, 2, 1, 0, 0),
					(59, 15, 2, 11, 4, 1, 0, 0),
					(60, 15, 3, 11, 8, 1, 0, 0),
					(61, 16, 0, 11, 1, 1, 0, 0),
					(62, 16, 1, 11, 2, 1, 0, 0),
					(63, 16, 2, 11, 4, 1, 0, 0),
					(64, 16, 3, 11, 8, 1, 0, 0),
					(65, 17, 0, 12, 1, 1, 0, 0),
					(66, 17, 1, 12, 2, 1, 0, 0),
					(67, 17, 2, 12, 4, 1, 0, 0),
					(68, 17, 3, 12, 8, 1, 0, 0),
					(69, 18, 0, 12, 1, 1, 0, 0),
					(70, 18, 1, 12, 2, 1, 0, 0),
					(71, 18, 2, 12, 4, 1, 0, 0),
					(72, 18, 3, 12, 8, 1, 0, 0),
					(73, 19, 0, 13, 1, 1, 0, 0),
					(74, 19, 1, 13, 2, 1, 0, 0),
					(75, 19, 2, 13, 4, 1, 0, 0),
					(76, 19, 3, 13, 8, 1, 0, 0),
					(77, 20, 0, 13, 1, 1, 0, 0),
					(78, 20, 1, 13, 2, 1, 0, 0),
					(79, 20, 2, 13, 4, 1, 0, 0),
					(80, 20, 3, 13, 8, 1, 0, 0);

