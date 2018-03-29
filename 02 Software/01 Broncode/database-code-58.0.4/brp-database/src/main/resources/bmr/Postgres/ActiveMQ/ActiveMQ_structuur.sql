--------------------------------------------------------------------------------
-- PostgreSQL SQL-DDL bestand voor Basisregistratie Personen (BRP)            --
-- PostgreSQL - ActiveMQ Structuur DDL                                        --
--------------------------------------------------------------------------------
-- 
-- Gegenereerd door BRP Meta Register Release 58.
-- Gegenereerd op: vrijdag 19 mei 2017 10:00:47
-- 
--------------------------------------------------------------------------------

--
-- Standaard ActiveMQ structuur. Zie:
-- http://activemq.apache.org/maven/apidocs/src-html/org/apache/activemq/store/jdbc/Statements.html
--
--------------------------------------------------------------------------------

DROP TABLE IF EXISTS activemq_acks;
CREATE TABLE activemq_acks (
    container character varying(250) NOT NULL,
    sub_dest character varying(250),
    client_id character varying(250) NOT NULL,
    sub_name character varying(250) NOT NULL,
    selector character varying(250),
    last_acked_id bigint,
    priority bigint DEFAULT 5 NOT NULL,
    xid character varying(250)
);
ALTER TABLE ONLY activemq_acks
    ADD CONSTRAINT activemq_acks_pkey PRIMARY KEY (container, client_id, sub_name, priority);

CREATE INDEX activemq_acks_xidx ON activemq_acks USING btree (xid);


DROP TABLE IF EXISTS activemq_lock;
CREATE TABLE activemq_lock (
    id bigint NOT NULL,
    "time" bigint,
    broker_name character varying(250)
);
ALTER TABLE ONLY activemq_lock
    ADD CONSTRAINT activemq_lock_pkey PRIMARY KEY (id);


DROP TABLE IF EXISTS activemq_msgs;
CREATE TABLE activemq_msgs (
    id bigint NOT NULL,
    container character varying(250),
    msgid_prod character varying(250),
    msgid_seq bigint,
    expiration bigint,
    msg bytea,
    priority bigint,
    xid character varying(250)
);

ALTER TABLE ONLY activemq_msgs
    ADD CONSTRAINT activemq_msgs_pkey PRIMARY KEY (id);

CREATE INDEX activemq_msgs_cidx ON activemq_msgs USING btree (container);
CREATE INDEX activemq_msgs_eidx ON activemq_msgs USING btree (expiration);
CREATE INDEX activemq_msgs_midx ON activemq_msgs USING btree (msgid_prod, msgid_seq);
CREATE INDEX activemq_msgs_pidx ON activemq_msgs USING btree (priority);
CREATE INDEX activemq_msgs_xidx ON activemq_msgs USING btree (xid);
-- Rollen aanmaken:
CREATE OR REPLACE FUNCTION CREATE_ROLE_IF_NOT_EXISTS(rolename TEXT) RETURNS VOID AS
$$
BEGIN
   IF NOT EXISTS (SELECT * FROM pg_roles WHERE lower(rolname) = lower(rolename)) THEN
      EXECUTE 'CREATE ROLE ' || rolename;
      RAISE NOTICE 'Role % created.', rolename;
   ELSE
      RAISE NOTICE 'Role % already exists.', rolename;
   END IF;
END;
$$ LANGUAGE plpgsql;

SELECT CREATE_ROLE_IF_NOT_EXISTS('BRP_ActiveMQ');
DROP FUNCTION CREATE_ROLE_IF_NOT_EXISTS(rolename TEXT);

GRANT INSERT, UPDATE, DELETE ON activemq_acks, activemq_lock, activemq_msgs TO BRP_ActiveMQ;
