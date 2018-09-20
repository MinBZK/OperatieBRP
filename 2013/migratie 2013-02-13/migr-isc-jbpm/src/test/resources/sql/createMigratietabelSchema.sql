-- Bij het gebruik van de in-memory DB wordt het schema gegenereerd vanuit de JPA-metadata.
-- Hierbij is het helaas (nog) niet mogelijk ook automatisch het schema te laten aanmaken, vandaar dat we dat via dit script regelen.
CREATE SCHEMA MIGRATIETABEL;
-- Om de compatibiliteit met PostgreSQL te vergroten zetten we de volgende HSQLDB-configuratie-opties aan:  
-- (zie http://hsqldb.org/doc/2.0/guide/guide.pdf)
SET DATABASE TRANSACTION CONTROL MVCC;
SET DATABASE SQL SYNTAX PGS TRUE;
SET DATABASE REFERENTIAL INTEGRITY TRUE;

-- Hieronder worden alle tabellen aangemaakt:

CREATE TABLE migratietabel.gemeente
(
  gemeenteCode Integer NOT NULL,
  datumBrp Integer,
  CONSTRAINT gemeenteCode_pkey PRIMARY KEY (gemeenteCode)
);