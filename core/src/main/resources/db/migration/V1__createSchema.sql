DROP TABLE IF EXISTS DASHBOARD_CASHIER;
DROP TABLE IF EXISTS DASHBOARD_GUEST;
DROP TABLE IF EXISTS DASHBOARD_TRANSACTION;
DROP TABLE IF EXISTS DASHBOARD;
DROP TABLE IF EXISTS TRANSACTION;
DROP TABLE IF EXISTS CASHIER;
DROP TABLE IF EXISTS FLATMATE;
DROP TABLE IF EXISTS LANGUAGE;

CREATE TABLE FLATMATE (
	ID SERIAL PRIMARY KEY,
	EMAIL VARCHAR(255) UNIQUE,
	NICKNAME VARCHAR(255),
	FIRST_STEP BOOLEAN,
	GUEST_STEP BOOLEAN
);

CREATE TABLE DASHBOARD (
	ID SERIAL PRIMARY KEY,
	OWNER_ID BIGINT,
	FOREIGN KEY (OWNER_ID) REFERENCES FLATMATE(ID)
);

CREATE TABLE CASHIER (
	ID SERIAL PRIMARY KEY,
	NAME VARCHAR(200),
	BALANCE DECIMAL(12,2),
	STARTED DECIMAL(12,2),
	OWNER_ID BIGINT,
	FOREIGN KEY (OWNER_ID) REFERENCES FLATMATE(ID)
);

CREATE TABLE TRANSACTION (
	ID SERIAL PRIMARY KEY,
	CREATE_BY_ID BIGINT,
	ASSIGNED_ID BIGINT,
	CASHIER_ID BIGINT,
	STATUS VARCHAR(20),
	ACTION VARCHAR(20),
	VALUE DECIMAL(12,2),
	CREATED_AT TIMESTAMP,
	UPDATED_AT TIMESTAMP,
	FOREIGN KEY (CREATE_BY_ID) REFERENCES FLATMATE(ID),
	FOREIGN KEY (ASSIGNED_ID) REFERENCES FLATMATE(ID),
	FOREIGN KEY (CASHIER_ID) REFERENCES CASHIER(ID)
);

CREATE TABLE DASHBOARD_CASHIER (
	ID_DASHBOARD BIGINT,
	ID_CASHIER BIGINT,
	FOREIGN KEY (ID_DASHBOARD) REFERENCES DASHBOARD(ID),
	FOREIGN KEY (ID_CASHIER) REFERENCES CASHIER(ID)
);

CREATE TABLE DASHBOARD_GUEST (
	ID_DASHBOARD BIGINT,
	ID_FLATMATE BIGINT,
	FOREIGN KEY (ID_DASHBOARD) REFERENCES DASHBOARD(ID),
	FOREIGN KEY (ID_FLATMATE) REFERENCES FLATMATE(ID)
);

CREATE TABLE DASHBOARD_TRANSACTION (
	ID_DASHBOARD BIGINT,
	ID_TRANSACTION BIGINT,
	FOREIGN KEY (ID_DASHBOARD) REFERENCES DASHBOARD(ID),
	FOREIGN KEY (ID_TRANSACTION) REFERENCES TRANSACTION(ID)
);

CREATE TABLE LANGUAGE (
	ID SERIAL PRIMARY KEY,
	LOCALE VARCHAR(5), 
	MESSAGE_KEY VARCHAR(255),
	MESSAGE_CONTENT VARCHAR(255)
);

