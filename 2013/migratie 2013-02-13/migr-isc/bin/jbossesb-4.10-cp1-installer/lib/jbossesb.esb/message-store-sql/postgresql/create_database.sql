-- Database: jbossesb

-- DROP DATABASE jbossesb;

--CREATE DATABASE jbossesb
--WITH OWNER = postgres
--ENCODING = 'SQL_ASCII'
--TABLESPACE = pg_default;


CREATE TABLE message
(
  uuid text NOT NULL,
  "type" text NOT NULL,
  message text NOT NULL,
  delivered text NOT NULL,
  classification text,
  CONSTRAINT pk_uid PRIMARY KEY (uuid)
);
