DROP TABLE IF EXISTS FLATMATE;
DROP TABLE IF EXISTS DASHBOARD;
DROP TABLE IF EXISTS CASHIER;
DROP TABLE IF EXISTS TRANSACTION;
DROP TABLE IF EXISTS DASHBOARD_CASHIER;
DROP TABLE IF EXISTS DASHBOARD_GUEST;
DROP TABLE IF EXISTS DASHBOARD_TRANSACTION;

CREATE TABLE IF NOT EXISTS FLATMATE (
	ID BIGINT PRIMARY KEY AUTO_INCREMENT(11,1),
	EMAIL VARCHAR(255) UNIQUE,
	NICKNAME VARCHAR(255),
	PASSWORD VARCHAR(255),
	ROLES VARCHAR(50),
	ENABLED BOOLEAN,
	FIRST_STEP BOOLEAN,
	GUEST_STEP BOOLEAN
);

CREATE TABLE IF NOT EXISTS DASHBOARD (
	ID BIGINT PRIMARY KEY AUTO_INCREMENT(11,1),
	OWNER_ID BIGINT,
	FOREIGN KEY (OWNER_ID) REFERENCES FLATMATE(ID)
);

CREATE TABLE IF NOT EXISTS CASHIER (
	ID BIGINT PRIMARY KEY AUTO_INCREMENT(11,1),
	NAME VARCHAR(200),
	BALANCE DECIMAL(12,2),
	STARTED DECIMAL(12,2),
	OWNER_ID BIGINT,
	FOREIGN KEY (OWNER_ID) REFERENCES FLATMATE(ID)
);

CREATE TABLE IF NOT EXISTS TRANSACTION (
	ID BIGINT PRIMARY KEY AUTO_INCREMENT(11,1),
	CREATE_BY_ID BIGINT,
	ASSIGNED_ID BIGINT,
	CASHIER_ID BIGINT,
	STATUS VARCHAR(20),
	ACTION VARCHAR(20),
	VALUE DECIMAL(12,2),
	CREATED_AT TIMESTAMP(26, 6),
	UPDATED_AT TIMESTAMP(26, 6),
	FOREIGN KEY (CREATE_BY_ID) REFERENCES FLATMATE(ID),
	FOREIGN KEY (ASSIGNED_ID) REFERENCES FLATMATE(ID),
	FOREIGN KEY (CASHIER_ID) REFERENCES CASHIER(ID)
);

CREATE TABLE IF NOT EXISTS DASHBOARD_CASHIER (
	ID_DASHBOARD BIGINT,
	ID_CASHIER BIGINT,
	FOREIGN KEY (ID_DASHBOARD) REFERENCES DASHBOARD(ID),
	FOREIGN KEY (ID_CASHIER) REFERENCES CASHIER(ID),
);

CREATE TABLE IF NOT EXISTS DASHBOARD_GUEST (
	ID_DASHBOARD BIGINT,
	ID_FLATMATE BIGINT,
	FOREIGN KEY (ID_DASHBOARD) REFERENCES DASHBOARD(ID),
	FOREIGN KEY (ID_FLATMATE) REFERENCES FLATMATE(ID),
);

CREATE TABLE IF NOT EXISTS DASHBOARD_TRANSACTION (
	ID_DASHBOARD BIGINT,
	ID_TRANSACTION BIGINT,
	FOREIGN KEY (ID_DASHBOARD) REFERENCES DASHBOARD(ID),
	FOREIGN KEY (ID_TRANSACTION) REFERENCES TRANSACTION(ID),
);

