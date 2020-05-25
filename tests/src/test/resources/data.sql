
INSERT INTO OAUTH_CLIENT_DETAILS (
	CLIENT_ID, 
	CLIENT_SECRET,
	SCOPE, 
	AUTHORIZED_GRANT_TYPES,
    WEB_SERVER_REDIRECT_URI, AUTHORITIES, ACCESS_TOKEN_VALIDITY, REFRESH_TOKEN_VALIDITY, ADDITIONAL_INFORMATION, AUTOAPPROVE)
VALUES (
	'cueva', 
	'{bcrypt}$2a$10$a.jUkhyX88ouqg4pNZk.ZOXRfjAlEDhwcyl35SifJzt6dKOH9tuAy', 
	'read,write',
	'password,authorization_code,refresh_token',
	null, null, 36000, 36000, null, true);
	
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

