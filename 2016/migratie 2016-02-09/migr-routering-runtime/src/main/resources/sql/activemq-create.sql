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

CREATE TABLE activemq_lock (
    id bigint NOT NULL,
    time bigint,
    broker_name character varying(250)
);

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

ALTER TABLE activemq_acks
    ADD CONSTRAINT activemq_acks_pkey PRIMARY KEY (container, client_id, sub_name, priority);

ALTER TABLE activemq_lock
    ADD CONSTRAINT activemq_lock_pkey PRIMARY KEY (id);

ALTER TABLE activemq_msgs
    ADD CONSTRAINT activemq_msgs_pkey PRIMARY KEY (id);

CREATE INDEX activemq_acks_xidx ON activemq_acks (xid);
CREATE INDEX activemq_msgs_cidx ON activemq_msgs (container);
CREATE INDEX activemq_msgs_eidx ON activemq_msgs (expiration);
CREATE INDEX activemq_msgs_midx ON activemq_msgs (msgid_prod, msgid_seq);
CREATE INDEX activemq_msgs_pidx ON activemq_msgs (priority);
CREATE INDEX activemq_msgs_xidx ON activemq_msgs (xid);

INSERT INTO ACTIVEMQ_LOCK(ID) VALUES (1);
COMMIT;
