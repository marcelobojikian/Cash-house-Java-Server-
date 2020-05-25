
INSERT INTO LANGUAGE (LOCALE, MESSAGE_KEY, MESSAGE_CONTENT) VALUES
('en', 'profile.not.found','Profile {0} not found'),
('en', 'dashboard.not.found','Dashboard {0} not found'),
('en', 'NotBlank.flatmate.nickname','Nickname cannot be blank'),
('en', 'NotBlank.cashier.name','Name cannot be blank'),
('en', 'typeMismatch.cashier.started','Started must be a floating point value'),
('en', 'typeMismatch.cashier.balance','Balance must be a floating point value'),
/*
('en', 'cashier.not.found','Cashier {0} not found'),
('en', 'flatmate.not.found', 'Flatmate {0} not found'),
('en', 'transaction.not.found', 'Transaction {0} not found'),
('en', 'transaction.status.invalid.operation', 'Invalid operation, Transaction {0} with status equal to {1}'),
('en', 'flatmate.access.denied', 'Flatmate {0} does not have permissions for the resource'),
('en', 'flatmate.access.field.denied', 'Flatmate {0} does not have permissions for field {1}'),
('en', 'flatmate.assigned.not.found', 'Flatmate assigned {0} not found'),
('en', 'flatmate.createBy.not.found', 'Flatmate createBy {0} not found'),
*/
('pt', 'profile.not.found','Perfil {0} não encontrado'),
('pt', 'dashboard.not.found','Dashboard {0} não encontrado'),
('pt', 'NotBlank.flatmate.nickname','Apelido não pode estar em branco'),
('pt', 'NotBlank.cashier.name','Nome não pode estar em branco'),
('pt', 'typeMismatch.cashier.started','Valor inicial deve ser um valor de ponto flutuante'),
('pt', 'typeMismatch.cashier.balance','Saldo deve ser um valor de ponto flutuante'),
/*
('pt', 'cashier.not.found','Caixinha {0} não encontrada'),
('pt', 'flatmate.not.found', 'Flatmate {0} não encontrado'),
('pt', 'transaction.not.found', 'Transação {0} não encontrada'),
('pt', 'transaction.status.invalid.operation', 'Operação invalid, Transação {0} com status igual a {1}'),
('pt', 'flatmate.access.denied', 'Flatmate {0} não tem permissão para esse recurso'),
('pt', 'flatmate.access.field.denied', 'Flatmate {0} não tem permissão para o recurso {1}'),
('pt', 'flatmate.assigned.not.found', 'Flatmate assinado {0} não encontrado para a Transação'),
('pt', 'flatmate.createBy.not.found', 'Flatmate criador {0} não encontrado para a Transação'),
*/
('es', 'profile.not.found','Profile {0} not found'),
('es', 'dashboard.not.found','Dashboard {0} not found'),
('es', 'NotBlank.flatmate.nickname','Nickname cannot be blank'),
('es', 'NotBlank.cashier.name','Name cannot be blank'),
('es', 'typeMismatch.cashier.started','Started must be a floating point value'),
('es', 'typeMismatch.cashier.balance','Balance must be a floating point value');
/*
('es', 'cashier.not.found','Cashier {0} not found'),
('es', 'flatmate.not.found', 'Flatmate {0} not found'),
('es', 'transaction.not.found', 'Transaction {0} not found'),
('es', 'transaction.status.invalid.operation', 'Invalid operation, Transaction {0} with status equal to {1}'),
('es', 'flatmate.access.denied', 'Flatmate {0} does not have permissions for the resource'),
('es', 'flatmate.access.field.denied', 'Flatmate {0} does not have permissions for field {1}'),
('es', 'flatmate.assigned.not.found', 'Flatmate assigned {0} not found'),
('es', 'flatmate.createBy.not.found', 'Flatmate createBy {0} not found');
*/


INSERT INTO ACL_SID (PRINCIPAL, SID) VALUES
(false, 'ROLE_ADMIN'),
(false, 'ROLE_USER');

INSERT INTO ACL_CLASS (CLASS) VALUES
('br.com.cashhouse.core.model.Profile'),
('br.com.cashhouse.core.model.Dashboard'),
('br.com.cashhouse.core.model.Flatmate'),
('br.com.cashhouse.core.model.Cashier'),
('br.com.cashhouse.core.model.Transaction');
