
	
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
(3, 'br.com.cashhouse.core.model.Flatmate'),
(4, 'br.com.cashhouse.core.model.Cashier'),
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

INSERT INTO DASHBOARD_GUEST(ID_DASHBOARD, ID_PROFILE) VALUES
							(1, 2),
							(1, 3),
							(1, 4),
							(1, 5),
							(1, 6),
							(1, 7),
							(8, 9),
							(8, 10);


INSERT INTO FLATMATE(ID, NICKNAME) VALUES
					(1, 'Biro'),
					(2, 'Eduardo'),
					(3, 'Willian'),
					(4, 'Marcelo'),
					(5, 'Aimee'),
					(6, 'Carol'),
					(7, 'Raissa'),
					(8, 'Jean'),
					(9, 'Gretchen'),
					(10, 'Fernando');

INSERT INTO DASHBOARD_FLATMATE(ID_DASHBOARD, ID_FLATMATE) VALUES
							  (1, 1),
							  (1, 2),
							  (1, 3),
							  (1, 4),
							  (1, 5),
							  (1, 6),
							  (1, 7),
							  (8, 8),
							  (8, 9),
							  (8, 10);

INSERT INTO CASHIER(ID, NAME, BALANCE, STARTED) VALUES
						(1, 'Energy & bin', 32.54, 0.00),
						(2, 'Geral', 120.00, 23.00),
						(3, 'Rent & Clean', 3.11, 12.45);

INSERT INTO DASHBOARD_CASHIER(ID_DASHBOARD, ID_CASHIER) VALUES
							(1, 1),
							(1, 2),
							(8, 3);

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
(1, 1, 1, NULL, 4, 1),
(2, 2, 1, NULL, 4, 1),
(3, 1, 2, NULL, 5, 1),
(4, 2, 2, NULL, 5, 1),
(5, 1, 3, NULL, 6, 1),
(6, 2, 3, NULL, 6, 1),
(7, 1, 4, NULL, 7, 1),
(8, 2, 4, NULL, 7, 1),
(9, 1, 5, NULL, 8, 1),
(10, 2, 5, NULL, 8, 1),
(11, 1, 6, NULL, 9, 1),
(12, 2, 6, NULL, 9, 1),
(13, 1, 7, NULL, 10, 1),
(14, 2, 7, NULL, 10, 1),
(15, 1, 8, NULL, 11, 1),
(16, 2, 8, NULL, 11, 1),
(17, 1, 9, NULL, 12, 1),
(18, 2, 9, NULL, 12, 1),
(19, 1, 10, NULL, 13, 1),
(20, 2, 10, NULL, 13, 1),
(21, 3, 1, NULL, 4, 1),
(22, 3, 2, NULL, 4, 1),
(23, 3, 3, NULL, 4, 1),
(24, 3, 4, NULL, 4, 1),
(25, 3, 5, NULL, 4, 1),
(26, 3, 6, NULL, 4, 1),
(27, 3, 7, NULL, 4, 1),
(28, 3, 8, NULL, 11, 1),
(29, 3, 9, NULL, 11, 1),
(30, 3, 10, NULL, 11, 1),
(31, 4, 1, NULL, 4, 1),
(32, 4, 2, NULL, 4, 1),
(33, 4, 3, NULL, 11, 1);

INSERT INTO PUBLIC.ACL_ENTRY(ID, ACL_OBJECT_IDENTITY, ACE_ORDER, SID, MASK, GRANTING, AUDIT_SUCCESS, AUDIT_FAILURE) VALUES
(1, 1, 0, 4, 1, 1, 0, 0),
(2, 1, 1, 4, 2, 1, 0, 0),
(3, 1, 2, 4, 4, 1, 0, 0),
(4, 1, 3, 4, 8, 1, 0, 0),
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
(80, 20, 3, 13, 8, 1, 0, 0),
(269, 2, 0, 4, 1, 1, 0, 0),
(270, 2, 1, 4, 2, 1, 0, 0),
(271, 2, 2, 4, 4, 1, 0, 0),
(272, 2, 3, 4, 8, 1, 0, 0),
(273, 2, 4, 5, 1, 1, 0, 0),
(274, 2, 5, 5, 2, 1, 0, 0),
(275, 2, 6, 5, 4, 0, 0, 0),
(276, 2, 7, 5, 8, 0, 0, 0),
(277, 2, 8, 6, 1, 1, 0, 0),
(278, 2, 9, 6, 2, 1, 0, 0),
(279, 2, 10, 6, 4, 0, 0, 0),
(280, 2, 11, 6, 8, 0, 0, 0),
(281, 2, 12, 7, 1, 1, 0, 0),
(282, 2, 13, 7, 2, 1, 0, 0),
(283, 2, 14, 7, 4, 0, 0, 0),
(284, 2, 15, 7, 8, 0, 0, 0),
(285, 2, 16, 8, 1, 1, 0, 0),
(286, 2, 17, 8, 2, 1, 0, 0),
(287, 2, 18, 8, 4, 0, 0, 0),
(288, 2, 19, 8, 8, 0, 0, 0),
(289, 2, 20, 9, 1, 1, 0, 0),
(290, 2, 21, 9, 2, 1, 0, 0),
(291, 2, 22, 9, 4, 0, 0, 0),
(292, 2, 23, 9, 8, 0, 0, 0),
(293, 2, 24, 10, 1, 1, 0, 0),
(294, 2, 25, 10, 2, 1, 0, 0),
(295, 2, 26, 10, 4, 0, 0, 0),
(296, 2, 27, 10, 8, 0, 0, 0),
(325, 16, 0, 11, 1, 1, 0, 0),
(326, 16, 1, 11, 2, 1, 0, 0),
(327, 16, 2, 11, 4, 1, 0, 0),
(328, 16, 3, 11, 8, 1, 0, 0),
(329, 16, 4, 12, 1, 1, 0, 0),
(330, 16, 5, 12, 2, 0, 0, 0),
(331, 16, 6, 12, 4, 0, 0, 0),
(332, 16, 7, 12, 8, 0, 0, 0),
(333, 16, 8, 13, 1, 1, 0, 0),
(334, 16, 9, 13, 2, 0, 0, 0),
(335, 16, 10, 13, 4, 0, 0, 0),
(336, 16, 11, 13, 8, 0, 0, 0),
(421, 24, 0, 4, 1, 1, 0, 0),
(422, 24, 1, 4, 2, 1, 0, 0),
(423, 24, 2, 4, 4, 1, 0, 0),
(424, 24, 3, 4, 8, 1, 0, 0),
(425, 24, 4, 5, 1, 1, 0, 0),
(426, 24, 5, 5, 2, 0, 0, 0),
(427, 24, 6, 5, 4, 0, 0, 0),
(428, 24, 7, 5, 8, 0, 0, 0),
(429, 24, 8, 6, 1, 1, 0, 0),
(430, 24, 9, 6, 2, 0, 0, 0),
(431, 24, 10, 6, 4, 0, 0, 0),
(432, 24, 11, 6, 8, 0, 0, 0),
(433, 24, 12, 7, 1, 1, 0, 0),
(434, 24, 13, 7, 2, 0, 0, 0),
(435, 24, 14, 7, 4, 0, 0, 0),
(436, 24, 15, 7, 8, 0, 0, 0),
(437, 24, 16, 8, 1, 1, 0, 0),
(438, 24, 17, 8, 2, 0, 0, 0),
(439, 24, 18, 8, 4, 0, 0, 0),
(440, 24, 19, 8, 8, 0, 0, 0),
(441, 24, 20, 9, 1, 1, 0, 0),
(442, 24, 21, 9, 2, 0, 0, 0),
(443, 24, 22, 9, 4, 0, 0, 0),
(444, 24, 23, 9, 8, 0, 0, 0),
(445, 24, 24, 10, 1, 1, 0, 0),
(446, 24, 25, 10, 2, 0, 0, 0),
(447, 24, 26, 10, 4, 0, 0, 0),
(448, 24, 27, 10, 8, 0, 0, 0),
(533, 28, 0, 11, 1, 1, 0, 0),
(534, 28, 1, 11, 2, 1, 0, 0),
(535, 28, 2, 11, 4, 1, 0, 0),
(536, 28, 3, 11, 8, 1, 0, 0),
(537, 28, 4, 12, 1, 1, 0, 0),
(538, 28, 5, 12, 2, 0, 0, 0),
(539, 28, 6, 12, 4, 0, 0, 0),
(540, 28, 7, 12, 8, 0, 0, 0),
(541, 28, 8, 13, 1, 1, 0, 0),
(542, 28, 9, 13, 2, 0, 0, 0),
(543, 28, 10, 13, 4, 0, 0, 0),
(544, 28, 11, 13, 8, 0, 0, 0),
(569, 21, 0, 4, 1, 1, 0, 0),
(570, 21, 1, 4, 2, 1, 0, 0),
(571, 21, 2, 4, 4, 1, 0, 0),
(572, 21, 3, 4, 8, 1, 0, 0),
(573, 21, 4, 5, 1, 1, 0, 0),
(574, 21, 5, 5, 2, 1, 0, 0),
(575, 21, 6, 5, 4, 0, 0, 0),
(576, 21, 7, 5, 8, 0, 0, 0),
(577, 21, 8, 6, 1, 1, 0, 0),
(578, 21, 9, 6, 2, 0, 0, 0),
(579, 21, 10, 6, 4, 0, 0, 0),
(580, 21, 11, 6, 8, 0, 0, 0),
(581, 21, 12, 7, 1, 1, 0, 0),
(582, 21, 13, 7, 2, 0, 0, 0),
(583, 21, 14, 7, 4, 0, 0, 0),
(584, 21, 15, 7, 8, 0, 0, 0),
(585, 21, 16, 8, 1, 1, 0, 0),
(586, 21, 17, 8, 2, 0, 0, 0),
(587, 21, 18, 8, 4, 0, 0, 0),
(588, 21, 19, 8, 8, 0, 0, 0),
(589, 21, 20, 9, 1, 1, 0, 0),
(590, 21, 21, 9, 2, 0, 0, 0),
(591, 21, 22, 9, 4, 0, 0, 0),
(592, 21, 23, 9, 8, 0, 0, 0),
(593, 21, 24, 10, 1, 1, 0, 0),
(594, 21, 25, 10, 2, 0, 0, 0),
(595, 21, 26, 10, 4, 0, 0, 0),
(596, 21, 27, 10, 8, 0, 0, 0),
(597, 22, 0, 4, 1, 1, 0, 0),
(598, 22, 1, 4, 2, 1, 0, 0),
(599, 22, 2, 4, 4, 1, 0, 0),
(600, 22, 3, 4, 8, 1, 0, 0),
(601, 22, 4, 5, 1, 1, 0, 0),
(602, 22, 5, 5, 2, 0, 0, 0),
(603, 22, 6, 5, 4, 0, 0, 0),
(604, 22, 7, 5, 8, 0, 0, 0),
(605, 22, 8, 6, 1, 1, 0, 0),
(606, 22, 9, 6, 2, 1, 0, 0),
(607, 22, 10, 6, 4, 0, 0, 0),
(608, 22, 11, 6, 8, 0, 0, 0),
(609, 22, 12, 7, 1, 1, 0, 0),
(610, 22, 13, 7, 2, 0, 0, 0),
(611, 22, 14, 7, 4, 0, 0, 0),
(612, 22, 15, 7, 8, 0, 0, 0),
(613, 22, 16, 8, 1, 1, 0, 0),
(614, 22, 17, 8, 2, 0, 0, 0),
(615, 22, 18, 8, 4, 0, 0, 0),
(616, 22, 19, 8, 8, 0, 0, 0),
(617, 22, 20, 9, 1, 1, 0, 0),
(618, 22, 21, 9, 2, 0, 0, 0),
(619, 22, 22, 9, 4, 0, 0, 0),
(620, 22, 23, 9, 8, 0, 0, 0),
(621, 22, 24, 10, 1, 1, 0, 0),
(622, 22, 25, 10, 2, 0, 0, 0),
(623, 22, 26, 10, 4, 0, 0, 0),
(624, 22, 27, 10, 8, 0, 0, 0),
(625, 23, 0, 4, 1, 1, 0, 0),
(626, 23, 1, 4, 2, 1, 0, 0),
(627, 23, 2, 4, 4, 1, 0, 0),
(628, 23, 3, 4, 8, 1, 0, 0),
(629, 23, 4, 5, 1, 1, 0, 0),
(630, 23, 5, 5, 2, 0, 0, 0),
(631, 23, 6, 5, 4, 0, 0, 0),
(632, 23, 7, 5, 8, 0, 0, 0),
(633, 23, 8, 6, 1, 1, 0, 0),
(634, 23, 9, 6, 2, 0, 0, 0),
(635, 23, 10, 6, 4, 0, 0, 0),
(636, 23, 11, 6, 8, 0, 0, 0),
(637, 23, 12, 7, 1, 1, 0, 0),
(638, 23, 13, 7, 2, 1, 0, 0),
(639, 23, 14, 7, 4, 0, 0, 0),
(640, 23, 15, 7, 8, 0, 0, 0),
(641, 23, 16, 8, 1, 1, 0, 0),
(642, 23, 17, 8, 2, 0, 0, 0),
(643, 23, 18, 8, 4, 0, 0, 0),
(644, 23, 19, 8, 8, 0, 0, 0),
(645, 23, 20, 9, 1, 1, 0, 0),
(646, 23, 21, 9, 2, 0, 0, 0),
(647, 23, 22, 9, 4, 0, 0, 0),
(648, 23, 23, 9, 8, 0, 0, 0),
(649, 23, 24, 10, 1, 1, 0, 0),
(650, 23, 25, 10, 2, 0, 0, 0),
(651, 23, 26, 10, 4, 0, 0, 0),
(652, 23, 27, 10, 8, 0, 0, 0),
(653, 25, 0, 4, 1, 1, 0, 0),
(654, 25, 1, 4, 2, 1, 0, 0),
(655, 25, 2, 4, 4, 1, 0, 0),
(656, 25, 3, 4, 8, 1, 0, 0),
(657, 25, 4, 5, 1, 1, 0, 0),
(658, 25, 5, 5, 2, 0, 0, 0),
(659, 25, 6, 5, 4, 0, 0, 0),
(660, 25, 7, 5, 8, 0, 0, 0),
(661, 25, 8, 6, 1, 1, 0, 0),
(662, 25, 9, 6, 2, 0, 0, 0),
(663, 25, 10, 6, 4, 0, 0, 0),
(664, 25, 11, 6, 8, 0, 0, 0),
(665, 25, 12, 7, 1, 1, 0, 0),
(666, 25, 13, 7, 2, 0, 0, 0),
(667, 25, 14, 7, 4, 0, 0, 0),
(668, 25, 15, 7, 8, 0, 0, 0),
(669, 25, 16, 8, 1, 1, 0, 0),
(670, 25, 17, 8, 2, 1, 0, 0),
(671, 25, 18, 8, 4, 0, 0, 0),
(672, 25, 19, 8, 8, 0, 0, 0),
(673, 25, 20, 9, 1, 1, 0, 0),
(674, 25, 21, 9, 2, 0, 0, 0),
(675, 25, 22, 9, 4, 0, 0, 0),
(676, 25, 23, 9, 8, 0, 0, 0),
(677, 25, 24, 10, 1, 1, 0, 0),
(678, 25, 25, 10, 2, 0, 0, 0),
(679, 25, 26, 10, 4, 0, 0, 0),
(680, 25, 27, 10, 8, 0, 0, 0),
(681, 26, 0, 4, 1, 1, 0, 0),
(682, 26, 1, 4, 2, 1, 0, 0),
(683, 26, 2, 4, 4, 1, 0, 0),
(684, 26, 3, 4, 8, 1, 0, 0),
(685, 26, 4, 5, 1, 1, 0, 0),
(686, 26, 5, 5, 2, 0, 0, 0),
(687, 26, 6, 5, 4, 0, 0, 0),
(688, 26, 7, 5, 8, 0, 0, 0),
(689, 26, 8, 6, 1, 1, 0, 0),
(690, 26, 9, 6, 2, 0, 0, 0),
(691, 26, 10, 6, 4, 0, 0, 0),
(692, 26, 11, 6, 8, 0, 0, 0),
(693, 26, 12, 7, 1, 1, 0, 0),
(694, 26, 13, 7, 2, 0, 0, 0),
(695, 26, 14, 7, 4, 0, 0, 0),
(696, 26, 15, 7, 8, 0, 0, 0),
(697, 26, 16, 8, 1, 1, 0, 0),
(698, 26, 17, 8, 2, 0, 0, 0),
(699, 26, 18, 8, 4, 0, 0, 0),
(700, 26, 19, 8, 8, 0, 0, 0),
(701, 26, 20, 9, 1, 1, 0, 0),
(702, 26, 21, 9, 2, 1, 0, 0),
(703, 26, 22, 9, 4, 0, 0, 0),
(704, 26, 23, 9, 8, 0, 0, 0),
(705, 26, 24, 10, 1, 1, 0, 0),
(706, 26, 25, 10, 2, 0, 0, 0),
(707, 26, 26, 10, 4, 0, 0, 0),
(708, 26, 27, 10, 8, 0, 0, 0),
(709, 27, 0, 4, 1, 1, 0, 0),
(710, 27, 1, 4, 2, 1, 0, 0),
(711, 27, 2, 4, 4, 1, 0, 0),
(712, 27, 3, 4, 8, 1, 0, 0),
(713, 27, 4, 5, 1, 1, 0, 0),
(714, 27, 5, 5, 2, 0, 0, 0),
(715, 27, 6, 5, 4, 0, 0, 0),
(716, 27, 7, 5, 8, 0, 0, 0),
(717, 27, 8, 6, 1, 1, 0, 0),
(718, 27, 9, 6, 2, 0, 0, 0),
(719, 27, 10, 6, 4, 0, 0, 0),
(720, 27, 11, 6, 8, 0, 0, 0),
(721, 27, 12, 7, 1, 1, 0, 0),
(722, 27, 13, 7, 2, 0, 0, 0),
(723, 27, 14, 7, 4, 0, 0, 0),
(724, 27, 15, 7, 8, 0, 0, 0),
(725, 27, 16, 8, 1, 1, 0, 0),
(726, 27, 17, 8, 2, 0, 0, 0),
(727, 27, 18, 8, 4, 0, 0, 0),
(728, 27, 19, 8, 8, 0, 0, 0),
(729, 27, 20, 9, 1, 1, 0, 0),
(730, 27, 21, 9, 2, 0, 0, 0),
(731, 27, 22, 9, 4, 0, 0, 0),
(732, 27, 23, 9, 8, 0, 0, 0),
(733, 27, 24, 10, 1, 1, 0, 0),
(734, 27, 25, 10, 2, 1, 0, 0),
(735, 27, 26, 10, 4, 0, 0, 0),
(736, 27, 27, 10, 8, 0, 0, 0),
(737, 29, 0, 11, 1, 1, 0, 0),
(738, 29, 1, 11, 2, 1, 0, 0),
(739, 29, 2, 11, 4, 1, 0, 0),
(740, 29, 3, 11, 8, 1, 0, 0),
(741, 29, 4, 12, 1, 1, 0, 0),
(742, 29, 5, 12, 2, 1, 0, 0),
(743, 29, 6, 12, 4, 0, 0, 0),
(744, 29, 7, 12, 8, 0, 0, 0),
(745, 29, 8, 13, 1, 1, 0, 0),
(746, 29, 9, 13, 2, 0, 0, 0),
(747, 29, 10, 13, 4, 0, 0, 0),
(748, 29, 11, 13, 8, 0, 0, 0),
(749, 30, 0, 11, 1, 1, 0, 0),
(750, 30, 1, 11, 2, 1, 0, 0),
(751, 30, 2, 11, 4, 1, 0, 0),
(752, 30, 3, 11, 8, 1, 0, 0),
(753, 30, 4, 12, 1, 1, 0, 0),
(754, 30, 5, 12, 2, 0, 0, 0),
(755, 30, 6, 12, 4, 0, 0, 0),
(756, 30, 7, 12, 8, 0, 0, 0),
(757, 30, 8, 13, 1, 1, 0, 0),
(758, 30, 9, 13, 2, 1, 0, 0),
(759, 30, 10, 13, 4, 0, 0, 0),
(760, 30, 11, 13, 8, 0, 0, 0),
(761, 31, 0, 4, 1, 1, 0, 0),
(762, 31, 1, 4, 2, 1, 0, 0),
(763, 31, 2, 4, 4, 1, 0, 0),
(764, 31, 3, 4, 8, 1, 0, 0),
(765, 31, 4, 5, 1, 1, 0, 0),
(766, 31, 5, 5, 2, 0, 0, 0),
(767, 31, 6, 5, 4, 0, 0, 0),
(768, 31, 7, 5, 8, 0, 0, 0),
(769, 31, 8, 6, 1, 1, 0, 0),
(770, 31, 9, 6, 2, 0, 0, 0),
(771, 31, 10, 6, 4, 0, 0, 0),
(772, 31, 11, 6, 8, 0, 0, 0),
(773, 31, 12, 7, 1, 1, 0, 0),
(774, 31, 13, 7, 2, 0, 0, 0),
(775, 31, 14, 7, 4, 0, 0, 0),
(776, 31, 15, 7, 8, 0, 0, 0),
(777, 31, 16, 8, 1, 1, 0, 0),
(778, 31, 17, 8, 2, 0, 0, 0),
(779, 31, 18, 8, 4, 0, 0, 0),
(780, 31, 19, 8, 8, 0, 0, 0),
(781, 31, 20, 9, 1, 1, 0, 0),
(782, 31, 21, 9, 2, 0, 0, 0),
(783, 31, 22, 9, 4, 0, 0, 0),
(784, 31, 23, 9, 8, 0, 0, 0),
(785, 31, 24, 10, 1, 1, 0, 0),
(786, 31, 25, 10, 2, 0, 0, 0),
(787, 31, 26, 10, 4, 0, 0, 0),
(788, 31, 27, 10, 8, 0, 0, 0),
(789, 32, 0, 4, 1, 1, 0, 0),
(790, 32, 1, 4, 2, 1, 0, 0),
(791, 32, 2, 4, 4, 1, 0, 0),
(792, 32, 3, 4, 8, 1, 0, 0),
(793, 32, 4, 5, 1, 1, 0, 0),
(794, 32, 5, 5, 2, 0, 0, 0),
(795, 32, 6, 5, 4, 0, 0, 0),
(796, 32, 7, 5, 8, 0, 0, 0),
(797, 32, 8, 6, 1, 1, 0, 0),
(798, 32, 9, 6, 2, 0, 0, 0),
(799, 32, 10, 6, 4, 0, 0, 0),
(800, 32, 11, 6, 8, 0, 0, 0),
(801, 32, 12, 7, 1, 1, 0, 0),
(802, 32, 13, 7, 2, 0, 0, 0),
(803, 32, 14, 7, 4, 0, 0, 0),
(804, 32, 15, 7, 8, 0, 0, 0),
(805, 32, 16, 8, 1, 1, 0, 0),
(806, 32, 17, 8, 2, 0, 0, 0),
(807, 32, 18, 8, 4, 0, 0, 0),
(808, 32, 19, 8, 8, 0, 0, 0),
(809, 32, 20, 9, 1, 1, 0, 0),
(810, 32, 21, 9, 2, 0, 0, 0),
(811, 32, 22, 9, 4, 0, 0, 0),
(812, 32, 23, 9, 8, 0, 0, 0),
(813, 32, 24, 10, 1, 1, 0, 0),
(814, 32, 25, 10, 2, 0, 0, 0),
(815, 32, 26, 10, 4, 0, 0, 0),
(816, 32, 27, 10, 8, 0, 0, 0),
(817, 33, 0, 11, 1, 1, 0, 0),
(818, 33, 1, 11, 2, 1, 0, 0),
(819, 33, 2, 11, 4, 1, 0, 0),
(820, 33, 3, 11, 8, 1, 0, 0),
(821, 33, 4, 12, 1, 1, 0, 0),
(822, 33, 5, 12, 2, 0, 0, 0),
(823, 33, 6, 12, 4, 0, 0, 0),
(824, 33, 7, 12, 8, 0, 0, 0),
(825, 33, 8, 13, 1, 1, 0, 0),
(826, 33, 9, 13, 2, 0, 0, 0),
(827, 33, 10, 13, 4, 0, 0, 0),
(828, 33, 11, 13, 8, 0, 0, 0);