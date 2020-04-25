
DROP TABLE IF EXISTS AUTHORITIES;
DROP TABLE IF EXISTS USERS;
DROP TABLE IF EXISTS oauth_client_details;

DROP TABLE IF EXISTS group_members;
DROP TABLE IF EXISTS group_authorities;
DROP TABLE IF EXISTS groups;
DROP TABLE IF EXISTS oauth_client_details;

DROP TABLE IF EXISTS oauth_client_token;
DROP TABLE IF EXISTS oauth_access_token;
DROP TABLE IF EXISTS oauth_refresh_token;
DROP TABLE IF EXISTS oauth_code;

create table oauth_client_details (
    client_id VARCHAR(256) PRIMARY KEY,
    resource_ids VARCHAR(256),
    client_secret VARCHAR(256),
    scope VARCHAR(256),
    authorized_grant_types VARCHAR(256),
    web_server_redirect_uri VARCHAR(256),
    authorities VARCHAR(256),
    access_token_validity INTEGER,
    refresh_token_validity INTEGER,
    additional_information VARCHAR(4096),
    autoapprove VARCHAR(256)
);

-- User Schema

CREATE TABLE USERS(
	USERNAME VARCHAR(50) NOT NULL primary key,
	PASSWORD VARCHAR(150) NOT NULL,
	ENABLED boolean NOT NULL
);

CREATE TABLE AUTHORITIES (
	USERNAME VARCHAR(50) NOT NULL,
	AUTHORITY VARCHAR(50) NOT NULL,
	CONSTRAINT FK_AUTHORITIES_USERS FOREIGN KEY(USERNAME) REFERENCES USERS(USERNAME)
);

-- Group Authorities

create table groups (
	id SERIAL primary key,
	group_name varchar(50) not null
);

create table group_authorities (
	group_id SERIAL not null,
	authority varchar(50) not null,
	constraint fk_group_authorities_group foreign key(group_id) references groups(id)
);

create table group_members (
	id SERIAL primary key,
	username varchar(50) not null,
	group_id bigint not null,
	constraint fk_group_members_group foreign key(group_id) references groups(id)
);

-- Oauth2 Token

CREATE TABLE oauth_client_token (
  token_id VARCHAR(256),
  token BYTEA,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name VARCHAR(256),
  client_id VARCHAR(256)
);

CREATE TABLE oauth_access_token (
  token_id VARCHAR(256),
  token BYTEA,
  authentication_id VARCHAR(256),
  user_name VARCHAR(256),
  client_id VARCHAR(256),
  authentication BYTEA,
  refresh_token VARCHAR(256)
);

CREATE TABLE oauth_refresh_token (
  token_id VARCHAR(256),
  token BYTEA,
  authentication BYTEA
);

CREATE TABLE oauth_code (
  code VARCHAR(256), authentication BYTEA
);

-- DATA

INSERT INTO oauth_client_details (
	client_id, 
	client_secret,
	scope, 
	authorized_grant_types,
    web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove)
VALUES (
	'cueva', 
	'{bcrypt}$2a$10$a.jUkhyX88ouqg4pNZk.ZOXRfjAlEDhwcyl35SifJzt6dKOH9tuAy', 
	'read,write',
	'password,authorization_code,refresh_token',
	null, null, 36000, 36000, null, true);

INSERT INTO USERS ( USERNAME, PASSWORD, ENABLED) VALUES ( 'marcelo@mail.com', '{bcrypt}$2a$10$PWZg7okfLhLcKMwUw.mOTOKA9vQTqFPp0ITOKx3gfSlLfOZe5iJVW', true );
INSERT INTO AUTHORITIES ( USERNAME, AUTHORITY) VALUES ( 'marcelo@mail.com', 'ROLE_ADMIN' );
INSERT INTO AUTHORITIES ( USERNAME, AUTHORITY) VALUES ( 'marcelo@mail.com', 'ROLE_USER' );

INSERT INTO USERS ( USERNAME, PASSWORD, ENABLED) VALUES ( 'biro@mail.com', '{bcrypt}$2a$10$PWZg7okfLhLcKMwUw.mOTOKA9vQTqFPp0ITOKx3gfSlLfOZe5iJVW', true );
INSERT INTO AUTHORITIES ( USERNAME, AUTHORITY) VALUES ( 'biro@mail.com', 'ROLE_USER' );

INSERT INTO USERS ( USERNAME, PASSWORD, ENABLED) VALUES ( 'eduardo@mail.com', '{bcrypt}$2a$10$PWZg7okfLhLcKMwUw.mOTOKA9vQTqFPp0ITOKx3gfSlLfOZe5iJVW', true );
INSERT INTO AUTHORITIES ( USERNAME, AUTHORITY) VALUES ( 'eduardo@mail.com', 'ROLE_USER' );

INSERT INTO USERS ( USERNAME, PASSWORD, ENABLED) VALUES ( 'will@mail.com', '{bcrypt}$2a$10$PWZg7okfLhLcKMwUw.mOTOKA9vQTqFPp0ITOKx3gfSlLfOZe5iJVW', true );
INSERT INTO AUTHORITIES ( USERNAME, AUTHORITY) VALUES ( 'will@mail.com', 'ROLE_USER' );

INSERT INTO USERS ( USERNAME, PASSWORD, ENABLED) VALUES ( 'gabi@mail.com', '{bcrypt}$2a$10$PWZg7okfLhLcKMwUw.mOTOKA9vQTqFPp0ITOKx3gfSlLfOZe5iJVW', true );
INSERT INTO AUTHORITIES ( USERNAME, AUTHORITY) VALUES ( 'gabi@mail.com', 'ROLE_USER' );

INSERT INTO USERS ( USERNAME, PASSWORD, ENABLED) VALUES ( 'carol@mail.com', '{bcrypt}$2a$10$PWZg7okfLhLcKMwUw.mOTOKA9vQTqFPp0ITOKx3gfSlLfOZe5iJVW', true );
INSERT INTO AUTHORITIES ( USERNAME, AUTHORITY) VALUES ( 'carol@mail.com', 'ROLE_USER' );

INSERT INTO USERS ( USERNAME, PASSWORD, ENABLED) VALUES ( 'rai@mail.com', '{bcrypt}$2a$10$PWZg7okfLhLcKMwUw.mOTOKA9vQTqFPp0ITOKx3gfSlLfOZe5iJVW', true );
INSERT INTO AUTHORITIES ( USERNAME, AUTHORITY) VALUES ( 'rai@mail.com', 'ROLE_USER' );

INSERT INTO USERS ( USERNAME, PASSWORD, ENABLED) VALUES ( 'jean@mail.com', '{bcrypt}$2a$10$PWZg7okfLhLcKMwUw.mOTOKA9vQTqFPp0ITOKx3gfSlLfOZe5iJVW', true );
INSERT INTO AUTHORITIES ( USERNAME, AUTHORITY) VALUES ( 'jean@mail.com', 'ROLE_USER' );

INSERT INTO USERS ( USERNAME, PASSWORD, ENABLED) VALUES ( 'gretchen@mail.com', '{bcrypt}$2a$10$PWZg7okfLhLcKMwUw.mOTOKA9vQTqFPp0ITOKx3gfSlLfOZe5iJVW', true );
INSERT INTO AUTHORITIES ( USERNAME, AUTHORITY) VALUES ( 'gretchen@mail.com', 'ROLE_USER' );

INSERT INTO USERS ( USERNAME, PASSWORD, ENABLED) VALUES ( 'fernando@mail.com', '{bcrypt}$2a$10$PWZg7okfLhLcKMwUw.mOTOKA9vQTqFPp0ITOKx3gfSlLfOZe5iJVW', true );
INSERT INTO AUTHORITIES ( USERNAME, AUTHORITY) VALUES ( 'fernando@mail.com', 'ROLE_USER' );

