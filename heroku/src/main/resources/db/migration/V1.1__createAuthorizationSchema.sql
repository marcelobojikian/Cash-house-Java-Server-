DROP INDEX IF EXISTS IX_AUTH_USERNAME;
DROP TABLE IF EXISTS AUTHORITIES;
DROP TABLE IF EXISTS USERS;

/*
DROP TABLE IF EXISTS GROUP_MEMBERS;
DROP TABLE IF EXISTS GROUP_AUTHORITIES;
DROP TABLE IF EXISTS GROUPS;
*/

DROP TABLE IF EXISTS OAUTH_CLIENT_DETAILS;
DROP TABLE IF EXISTS OAUTH_CLIENT_TOKEN;
DROP TABLE IF EXISTS OAUTH_ACCESS_TOKEN;
DROP TABLE IF EXISTS OAUTH_REFRESH_TOKEN;
DROP TABLE IF EXISTS OAUTH_CODE;


CREATE TABLE OAUTH_CLIENT_DETAILS (
    CLIENT_ID VARCHAR(256) PRIMARY KEY,
    RESOURCE_IDS VARCHAR(256),
    CLIENT_SECRET VARCHAR(256),
    SCOPE VARCHAR(256),
    AUTHORIZED_GRANT_TYPES VARCHAR(256),
    WEB_SERVER_REDIRECT_URI VARCHAR(256),
    AUTHORITIES VARCHAR(256),
    ACCESS_TOKEN_VALIDITY INTEGER,
    REFRESH_TOKEN_VALIDITY INTEGER,
    ADDITIONAL_INFORMATION VARCHAR(4096),
    AUTOAPPROVE VARCHAR(256)
);

-- User Schema

CREATE TABLE USERS(
	USERNAME VARCHAR(50) NOT NULL PRIMARY KEY,
	PASSWORD VARCHAR(150) NOT NULL,
	ENABLED BOOLEAN NOT NULL
);

CREATE TABLE AUTHORITIES (
	USERNAME VARCHAR(50) NOT NULL,
	AUTHORITY VARCHAR(50) NOT NULL,
	CONSTRAINT FK_AUTHORITIES_USERS FOREIGN KEY(USERNAME) REFERENCES USERS(USERNAME)
);

CREATE UNIQUE INDEX IX_AUTH_USERNAME ON AUTHORITIES (USERNAME,AUTHORITY);

/*
-- Group Authorities

CREATE TABLE GROUPS (
	ID SERIAL PRIMARY KEY,
	GROUP_NAME VARCHAR(50) NOT NULL
);

CREATE TABLE GROUP_AUTHORITIES (
	GROUP_ID SERIAL NOT NULL,
	AUTHORITY VARCHAR(50) NOT NULL,
	CONSTRAINT FK_GROUP_AUTHORITIES_GROUP FOREIGN KEY(GROUP_ID) REFERENCES GROUPS(ID)
);

CREATE TABLE GROUP_MEMBERS (
	ID SERIAL PRIMARY KEY,
	USERNAME VARCHAR(50) NOT NULL,
	GROUP_ID BIGINT NOT NULL,
	CONSTRAINT FK_GROUP_MEMBERS_GROUP FOREIGN KEY(GROUP_ID) REFERENCES GROUPS(ID)
);

*/
-- Oauth2 Token

CREATE TABLE OAUTH_CLIENT_TOKEN (
  TOKEN_ID VARCHAR(256),
  TOKEN BYTEA,
  AUTHENTICATION_ID VARCHAR(256) PRIMARY KEY,
  USER_NAME VARCHAR(256),
  CLIENT_ID VARCHAR(256)
);

CREATE TABLE OAUTH_ACCESS_TOKEN (
  TOKEN_ID VARCHAR(256),
  TOKEN BYTEA,
  AUTHENTICATION_ID VARCHAR(256),
  USER_NAME VARCHAR(256),
  CLIENT_ID VARCHAR(256),
  AUTHENTICATION BYTEA,
  REFRESH_TOKEN VARCHAR(256)
);

CREATE TABLE OAUTH_REFRESH_TOKEN (
  TOKEN_ID VARCHAR(256),
  TOKEN BYTEA,
  AUTHENTICATION BYTEA
);

CREATE TABLE OAUTH_CODE (
  CODE VARCHAR(256), AUTHENTICATION BYTEA
);
