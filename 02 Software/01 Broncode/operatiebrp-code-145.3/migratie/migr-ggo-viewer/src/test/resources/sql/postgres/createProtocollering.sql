-- Schemas ---------------------------------------------------------------------
drop schema IF EXISTS viewer CASCADE;
CREATE SCHEMA viewer;


-- Hieronder worden alle tabellen aangemaakt:
CREATE SEQUENCE viewer.seq_protocollering;
CREATE TABLE viewer.protocollering
(
  id bigint NOT NULL DEFAULT nextval('viewer.seq_protocollering'),
  gebruikersnaam character varying(36) NOT NULL,
  datumtijd timestamp NOT NULL,
  a_nummer character(10),
  geautoriseerd Boolean NOT NULL,
  CONSTRAINT protocollering_pkey PRIMARY KEY (id)
);
ALTER SEQUENCE viewer.seq_protocollering OWNED BY viewer.protocollering.id;

COMMIT;
