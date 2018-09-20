
CREATE TABLE message
(
  uuid VARCHAR NOT NULL,
  type VARCHAR NOT NULL,
  message LONGVARCHAR NOT NULL,
  delivered VARCHAR NOT NULL,
  classification VARCHAR,
  CONSTRAINT pk_uid PRIMARY KEY (uuid)
);
