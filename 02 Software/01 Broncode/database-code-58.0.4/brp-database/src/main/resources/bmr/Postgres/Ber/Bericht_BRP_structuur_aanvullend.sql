--------------------------------------------------------------------------------
-- PostgreSQL SQL-DDL bestand voor Basisregistratie Personen (BRP)            --
-- PostgreSQL - Bericht Structuur Aanvullend                                  --
--------------------------------------------------------------------------------
-- 
-- Gegenereerd door BRP Meta Register Release 58.
-- Gegenereerd op: vrijdag 19 mei 2017 10:00:47
-- 
--------------------------------------------------------------------------------


-- Sequences van stamtabellen (her)zetten naar de juiste waarden

-- Statistieken
ALTER TABLE Ber.Ber SET (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 100000000, autovacuum_analyze_threshold = 100000000);
ALTER TABLE Ber.BerPers SET (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 100000000, autovacuum_analyze_threshold = 100000000);

-- Partitionering ----------------------------------------------------------------
-- Ber
CREATE TABLE Ber.Ber_2016 ( 
   CONSTRAINT pk_Ber_2016 PRIMARY KEY (ID),
   CHECK ( TsReg < TIMESTAMP WITH TIME ZONE '2017-01-01 00:00:00+0' )
) INHERITS (Ber.Ber) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 5000000, autovacuum_analyze_threshold = 5000000);
CREATE INDEX ixBer_2016_TsReg ON Ber.Ber_2016 USING BRIN (TsReg);

CREATE TABLE Ber.Ber_2017k1 ( 
   CONSTRAINT pk_Ber_2017k1 PRIMARY KEY (ID),
   CHECK ( TsReg >= TIMESTAMP WITH TIME ZONE '2017-01-01 00:00:00+0' AND TsReg < TIMESTAMP WITH TIME ZONE '2017-04-01 00:00:00+0' )
) INHERITS (Ber.Ber) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 5000000, autovacuum_analyze_threshold = 5000000);
CREATE INDEX ixBer_2017k1_TsReg ON Ber.Ber_2017k1 USING BRIN (TsReg);

CREATE TABLE Ber.Ber_2017k2 ( 
   CONSTRAINT pk_Ber_2017k2 PRIMARY KEY (ID),
   CHECK ( TsReg >= TIMESTAMP WITH TIME ZONE '2017-04-01 00:00:00+0' AND TsReg < TIMESTAMP WITH TIME ZONE '2017-07-01 00:00:00+0' )
) INHERITS (Ber.Ber) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 5000000, autovacuum_analyze_threshold = 5000000);
CREATE INDEX ixBer_2017k2_TsReg ON Ber.Ber_2017k2 USING BRIN (TsReg);

CREATE TABLE Ber.Ber_2017k3 ( 
   CONSTRAINT pk_Ber_2017k3 PRIMARY KEY (ID),
   CHECK ( TsReg >= TIMESTAMP WITH TIME ZONE '2017-07-01 00:00:00+0' AND TsReg < TIMESTAMP WITH TIME ZONE '2017-10-01 00:00:00+0' )
) INHERITS (Ber.Ber) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 5000000, autovacuum_analyze_threshold = 5000000);
CREATE INDEX ixBer_2017k3_TsReg ON Ber.Ber_2017k3 USING BRIN (TsReg);

CREATE TABLE Ber.Ber_2017k4 ( 
   CONSTRAINT pk_Ber_2017k4 PRIMARY KEY (ID),
   CHECK ( TsReg >= TIMESTAMP WITH TIME ZONE '2017-10-01 00:00:00+0' AND TsReg < TIMESTAMP WITH TIME ZONE '2018-01-01 00:00:00+0' )
) INHERITS (Ber.Ber) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 5000000, autovacuum_analyze_threshold = 5000000);
CREATE INDEX ixBer_2017k4_TsReg ON Ber.Ber_2017k4 USING BRIN (TsReg);

CREATE TABLE Ber.Ber_2018k1 ( 
   CONSTRAINT pk_Ber_2018k1 PRIMARY KEY (ID),
   CHECK ( TsReg >= TIMESTAMP WITH TIME ZONE '2018-01-01 00:00:00+0' AND TsReg < TIMESTAMP WITH TIME ZONE '2018-04-01 00:00:00+0' )
) INHERITS (Ber.Ber) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 5000000, autovacuum_analyze_threshold = 5000000);
CREATE INDEX ixBer_2018k1_TsReg ON Ber.Ber_2018k1 USING BRIN (TsReg);

CREATE TABLE Ber.Ber_2018k2 ( 
   CONSTRAINT pk_Ber_2018k2 PRIMARY KEY (ID),
   CHECK ( TsReg >= TIMESTAMP WITH TIME ZONE '2018-04-01 00:00:00+0' AND TsReg < TIMESTAMP WITH TIME ZONE '2018-07-01 00:00:00+0' )
) INHERITS (Ber.Ber) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 5000000, autovacuum_analyze_threshold = 5000000);
CREATE INDEX ixBer_2018k2_TsReg ON Ber.Ber_2018k2 USING BRIN (TsReg);

CREATE TABLE Ber.Ber_2018k3 ( 
   CONSTRAINT pk_Ber_2018k3 PRIMARY KEY (ID),
   CHECK ( TsReg >= TIMESTAMP WITH TIME ZONE '2018-07-01 00:00:00+0' AND TsReg < TIMESTAMP WITH TIME ZONE '2018-10-01 00:00:00+0' )
) INHERITS (Ber.Ber) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 5000000, autovacuum_analyze_threshold = 5000000);
CREATE INDEX ixBer_2018k3_TsReg ON Ber.Ber_2018k3 USING BRIN (TsReg);

CREATE TABLE Ber.Ber_2018k4 ( 
   CONSTRAINT pk_Ber_2018k4 PRIMARY KEY (ID),
   CHECK ( TsReg >= TIMESTAMP WITH TIME ZONE '2018-10-01 00:00:00+0' AND TsReg < TIMESTAMP WITH TIME ZONE '2019-01-01 00:00:00+0' )
) INHERITS (Ber.Ber) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 5000000, autovacuum_analyze_threshold = 5000000);
CREATE INDEX ixBer_2018k4_TsReg ON Ber.Ber_2018k4 USING BRIN (TsReg);

CREATE TABLE Ber.Ber_2019k1 ( 
   CONSTRAINT pk_Ber_2019k1 PRIMARY KEY (ID),
   CHECK ( TsReg >= TIMESTAMP WITH TIME ZONE '2019-01-01 00:00:00+0' AND TsReg < TIMESTAMP WITH TIME ZONE '2019-04-01 00:00:00+0' )
) INHERITS (Ber.Ber) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 5000000, autovacuum_analyze_threshold = 5000000);
CREATE INDEX ixBer_2019k1_TsReg ON Ber.Ber_2019k1 USING BRIN (TsReg);

CREATE TABLE Ber.Ber_2019k2 ( 
   CONSTRAINT pk_Ber_2019k2 PRIMARY KEY (ID),
   CHECK ( TsReg >= TIMESTAMP WITH TIME ZONE '2019-04-01 00:00:00+0' AND TsReg < TIMESTAMP WITH TIME ZONE '2019-07-01 00:00:00+0' )
) INHERITS (Ber.Ber) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 5000000, autovacuum_analyze_threshold = 5000000);
CREATE INDEX ixBer_2019k2_TsReg ON Ber.Ber_2019k2 USING BRIN (TsReg);

CREATE TABLE Ber.Ber_2019k3 ( 
   CONSTRAINT pk_Ber_2019k3 PRIMARY KEY (ID),
   CHECK ( TsReg >= TIMESTAMP WITH TIME ZONE '2019-07-01 00:00:00+0' AND TsReg < TIMESTAMP WITH TIME ZONE '2019-10-01 00:00:00+0' )
) INHERITS (Ber.Ber) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 5000000, autovacuum_analyze_threshold = 5000000);
CREATE INDEX ixBer_2019k3_TsReg ON Ber.Ber_2019k3 USING BRIN (TsReg);

CREATE TABLE Ber.Ber_2019k4 ( 
   CONSTRAINT pk_Ber_2019k4 PRIMARY KEY (ID),
   CHECK ( TsReg >= TIMESTAMP WITH TIME ZONE '2019-10-01 00:00:00+0' AND TsReg < TIMESTAMP WITH TIME ZONE '2020-01-01 00:00:00+0' )
) INHERITS (Ber.Ber) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 5000000, autovacuum_analyze_threshold = 5000000);
CREATE INDEX ixBer_2019k4_TsReg ON Ber.Ber_2019k4 USING BRIN (TsReg);

CREATE TABLE Ber.Ber_2020k1 ( 
   CONSTRAINT pk_Ber_2020k1 PRIMARY KEY (ID),
   CHECK ( TsReg >= TIMESTAMP WITH TIME ZONE '2020-01-01 00:00:00+0' AND TsReg < TIMESTAMP WITH TIME ZONE '2020-04-01 00:00:00+0' )
) INHERITS (Ber.Ber) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 5000000, autovacuum_analyze_threshold = 5000000);
CREATE INDEX ixBer_2020k1_TsReg ON Ber.Ber_2020k1 USING BRIN (TsReg);

CREATE TABLE Ber.Ber_2020k2 ( 
   CONSTRAINT pk_Ber_2020k2 PRIMARY KEY (ID),
   CHECK ( TsReg >= TIMESTAMP WITH TIME ZONE '2020-04-01 00:00:00+0' AND TsReg < TIMESTAMP WITH TIME ZONE '2020-07-01 00:00:00+0' )
) INHERITS (Ber.Ber) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 5000000, autovacuum_analyze_threshold = 5000000);
CREATE INDEX ixBer_2020k2_TsReg ON Ber.Ber_2020k2 USING BRIN (TsReg);

CREATE TABLE Ber.Ber_2020k3 ( 
   CONSTRAINT pk_Ber_2020k3 PRIMARY KEY (ID),
   CHECK ( TsReg >= TIMESTAMP WITH TIME ZONE '2020-07-01 00:00:00+0' AND TsReg < TIMESTAMP WITH TIME ZONE '2020-10-01 00:00:00+0' )
) INHERITS (Ber.Ber) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 5000000, autovacuum_analyze_threshold = 5000000);
CREATE INDEX ixBer_2020k3_TsReg ON Ber.Ber_2020k3 USING BRIN (TsReg);

CREATE TABLE Ber.Ber_2020k4 ( 
   CONSTRAINT pk_Ber_2020k4 PRIMARY KEY (ID),
   CHECK ( TsReg >= TIMESTAMP WITH TIME ZONE '2020-10-01 00:00:00+0' AND TsReg < TIMESTAMP WITH TIME ZONE '2021-01-01 00:00:00+0' )
) INHERITS (Ber.Ber) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 5000000, autovacuum_analyze_threshold = 5000000);
CREATE INDEX ixBer_2020k4_TsReg ON Ber.Ber_2020k4 USING BRIN (TsReg);

CREATE TABLE Ber.Ber_2021k1 ( 
   CONSTRAINT pk_Ber_2021k1 PRIMARY KEY (ID),
   CHECK ( TsReg >= TIMESTAMP WITH TIME ZONE '2021-01-01 00:00:00+0' AND TsReg < TIMESTAMP WITH TIME ZONE '2021-04-01 00:00:00+0' )
) INHERITS (Ber.Ber) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 5000000, autovacuum_analyze_threshold = 5000000);
CREATE INDEX ixBer_2021k1_TsReg ON Ber.Ber_2021k1 USING BRIN (TsReg);

CREATE TABLE Ber.Ber_2021k2 ( 
   CONSTRAINT pk_Ber_2021k2 PRIMARY KEY (ID),
   CHECK ( TsReg >= TIMESTAMP WITH TIME ZONE '2021-04-01 00:00:00+0' AND TsReg < TIMESTAMP WITH TIME ZONE '2021-07-01 00:00:00+0' )
) INHERITS (Ber.Ber) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 5000000, autovacuum_analyze_threshold = 5000000);
CREATE INDEX ixBer_2021k2_TsReg ON Ber.Ber_2021k2 USING BRIN (TsReg);

CREATE TABLE Ber.Ber_2021k3 ( 
   CONSTRAINT pk_Ber_2021k3 PRIMARY KEY (ID),
   CHECK ( TsReg >= TIMESTAMP WITH TIME ZONE '2021-07-01 00:00:00+0' AND TsReg < TIMESTAMP WITH TIME ZONE '2021-10-01 00:00:00+0' )
) INHERITS (Ber.Ber) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 5000000, autovacuum_analyze_threshold = 5000000);
CREATE INDEX ixBer_2021k3_TsReg ON Ber.Ber_2021k3 USING BRIN (TsReg);

CREATE TABLE Ber.Ber_2021k4 ( 
   CONSTRAINT pk_Ber_2021k4 PRIMARY KEY (ID),
   CHECK ( TsReg >= TIMESTAMP WITH TIME ZONE '2021-10-01 00:00:00+0' AND TsReg < TIMESTAMP WITH TIME ZONE '2022-01-01 00:00:00+0' )
) INHERITS (Ber.Ber) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 5000000, autovacuum_analyze_threshold = 5000000);
CREATE INDEX ixBer_2021k4_TsReg ON Ber.Ber_2021k4 USING BRIN (TsReg);


CREATE OR REPLACE FUNCTION Ber.Ber_insert_function()
RETURNS TRIGGER AS $$
BEGIN
   IF (NEW.TsReg < TIMESTAMP WITH TIME ZONE '2017-01-01 00:00:00+0') THEN
      INSERT INTO Ber.Ber_2016 VALUES (NEW.*);
   ELSIF (NEW.TsReg >= TIMESTAMP WITH TIME ZONE '2017-01-01 00:00:00+0' AND
       NEW.TsReg < TIMESTAMP WITH TIME ZONE '2017-04-01 00:00:00+0') THEN
      INSERT INTO Ber.Ber_2017k1 VALUES (NEW.*);
   ELSIF (NEW.TsReg >= TIMESTAMP WITH TIME ZONE '2017-04-01 00:00:00+0' AND
       NEW.TsReg < TIMESTAMP WITH TIME ZONE '2017-07-01 00:00:00+0') THEN
      INSERT INTO Ber.Ber_2017k2 VALUES (NEW.*);
   ELSIF (NEW.TsReg >= TIMESTAMP WITH TIME ZONE '2017-07-01 00:00:00+0' AND
       NEW.TsReg < TIMESTAMP WITH TIME ZONE '2017-10-01 00:00:00+0') THEN
      INSERT INTO Ber.Ber_2017k3 VALUES (NEW.*);
   ELSIF (NEW.TsReg >= TIMESTAMP WITH TIME ZONE '2017-10-01 00:00:00+0' AND
       NEW.TsReg < TIMESTAMP WITH TIME ZONE '2018-01-01 00:00:00+0') THEN
      INSERT INTO Ber.Ber_2017k4 VALUES (NEW.*);
   ELSIF (NEW.TsReg >= TIMESTAMP WITH TIME ZONE '2018-01-01 00:00:00+0' AND
       NEW.TsReg < TIMESTAMP WITH TIME ZONE '2018-04-01 00:00:00+0') THEN
      INSERT INTO Ber.Ber_2018k1 VALUES (NEW.*);
   ELSIF (NEW.TsReg >= TIMESTAMP WITH TIME ZONE '2018-04-01 00:00:00+0' AND
       NEW.TsReg < TIMESTAMP WITH TIME ZONE '2018-07-01 00:00:00+0') THEN
      INSERT INTO Ber.Ber_2018k2 VALUES (NEW.*);
   ELSIF (NEW.TsReg >= TIMESTAMP WITH TIME ZONE '2018-07-01 00:00:00+0' AND
       NEW.TsReg < TIMESTAMP WITH TIME ZONE '2018-10-01 00:00:00+0') THEN
      INSERT INTO Ber.Ber_2018k3 VALUES (NEW.*);
   ELSIF (NEW.TsReg >= TIMESTAMP WITH TIME ZONE '2018-10-01 00:00:00+0' AND
       NEW.TsReg < TIMESTAMP WITH TIME ZONE '2019-01-01 00:00:00+0') THEN
      INSERT INTO Ber.Ber_2018k4 VALUES (NEW.*);
   ELSIF (NEW.TsReg >= TIMESTAMP WITH TIME ZONE '2019-01-01 00:00:00+0' AND
       NEW.TsReg < TIMESTAMP WITH TIME ZONE '2019-04-01 00:00:00+0') THEN
      INSERT INTO Ber.Ber_2019k1 VALUES (NEW.*);
   ELSIF (NEW.TsReg >= TIMESTAMP WITH TIME ZONE '2019-04-01 00:00:00+0' AND
       NEW.TsReg < TIMESTAMP WITH TIME ZONE '2019-07-01 00:00:00+0') THEN
      INSERT INTO Ber.Ber_2019k2 VALUES (NEW.*);
   ELSIF (NEW.TsReg >= TIMESTAMP WITH TIME ZONE '2019-07-01 00:00:00+0' AND
       NEW.TsReg < TIMESTAMP WITH TIME ZONE '2019-10-01 00:00:00+0') THEN
      INSERT INTO Ber.Ber_2019k3 VALUES (NEW.*);
   ELSIF (NEW.TsReg >= TIMESTAMP WITH TIME ZONE '2019-10-01 00:00:00+0' AND
       NEW.TsReg < TIMESTAMP WITH TIME ZONE '2020-01-01 00:00:00+0') THEN
      INSERT INTO Ber.Ber_2019k4 VALUES (NEW.*);
   ELSIF (NEW.TsReg >= TIMESTAMP WITH TIME ZONE '2020-01-01 00:00:00+0' AND
       NEW.TsReg < TIMESTAMP WITH TIME ZONE '2020-04-01 00:00:00+0') THEN
      INSERT INTO Ber.Ber_2020k1 VALUES (NEW.*);
   ELSIF (NEW.TsReg >= TIMESTAMP WITH TIME ZONE '2020-04-01 00:00:00+0' AND
       NEW.TsReg < TIMESTAMP WITH TIME ZONE '2020-07-01 00:00:00+0') THEN
      INSERT INTO Ber.Ber_2020k2 VALUES (NEW.*);
   ELSIF (NEW.TsReg >= TIMESTAMP WITH TIME ZONE '2020-07-01 00:00:00+0' AND
       NEW.TsReg < TIMESTAMP WITH TIME ZONE '2020-10-01 00:00:00+0') THEN
      INSERT INTO Ber.Ber_2020k3 VALUES (NEW.*);
   ELSIF (NEW.TsReg >= TIMESTAMP WITH TIME ZONE '2020-10-01 00:00:00+0' AND
       NEW.TsReg < TIMESTAMP WITH TIME ZONE '2021-01-01 00:00:00+0') THEN
      INSERT INTO Ber.Ber_2020k4 VALUES (NEW.*);
   ELSIF (NEW.TsReg >= TIMESTAMP WITH TIME ZONE '2021-01-01 00:00:00+0' AND
       NEW.TsReg < TIMESTAMP WITH TIME ZONE '2021-04-01 00:00:00+0') THEN
      INSERT INTO Ber.Ber_2021k1 VALUES (NEW.*);
   ELSIF (NEW.TsReg >= TIMESTAMP WITH TIME ZONE '2021-04-01 00:00:00+0' AND
       NEW.TsReg < TIMESTAMP WITH TIME ZONE '2021-07-01 00:00:00+0') THEN
      INSERT INTO Ber.Ber_2021k2 VALUES (NEW.*);
   ELSIF (NEW.TsReg >= TIMESTAMP WITH TIME ZONE '2021-07-01 00:00:00+0' AND
       NEW.TsReg < TIMESTAMP WITH TIME ZONE '2021-10-01 00:00:00+0') THEN
      INSERT INTO Ber.Ber_2021k3 VALUES (NEW.*);
   ELSIF (NEW.TsReg >= TIMESTAMP WITH TIME ZONE '2021-10-01 00:00:00+0' AND
       NEW.TsReg < TIMESTAMP WITH TIME ZONE '2022-01-01 00:00:00+0') THEN
      INSERT INTO Ber.Ber_2021k4 VALUES (NEW.*);
   ELSE
      RAISE EXCEPTION 'Geen partitie voor Ber.TsReg.';
   END IF;
   RETURN NULL;
END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER Ber_insert_trigger
   BEFORE INSERT ON Ber.Ber
      FOR EACH ROW EXECUTE PROCEDURE Ber.Ber_insert_function();


-- BerPers
CREATE TABLE Ber.BerPers_2016 ( 
   CHECK ( TsReg < TIMESTAMP WITH TIME ZONE '2017-01-01 00:00:00+0' )
) INHERITS (Ber.BerPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 5000000, autovacuum_analyze_threshold = 5000000);
CREATE INDEX ixBerPers_2016_TsReg ON Ber.BerPers_2016 USING BRIN (TsReg);
CREATE INDEX ixBerPers_2016_Ber ON Ber.BerPers_2016 USING BRIN (Ber);

CREATE TABLE Ber.BerPers_2017k1 ( 
   CHECK ( TsReg >= TIMESTAMP WITH TIME ZONE '2017-01-01 00:00:00+0' AND TsReg < TIMESTAMP WITH TIME ZONE '2017-04-01 00:00:00+0' )
) INHERITS (Ber.BerPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 5000000, autovacuum_analyze_threshold = 5000000);
CREATE INDEX ixBerPers_2017k1_TsReg ON Ber.BerPers_2017k1 USING BRIN (TsReg);
CREATE INDEX ixBerPers_2017k1_Ber ON Ber.BerPers_2017k1 USING BRIN (Ber);

CREATE TABLE Ber.BerPers_2017k2 ( 
   CHECK ( TsReg >= TIMESTAMP WITH TIME ZONE '2017-04-01 00:00:00+0' AND TsReg < TIMESTAMP WITH TIME ZONE '2017-07-01 00:00:00+0' )
) INHERITS (Ber.BerPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 5000000, autovacuum_analyze_threshold = 5000000);
CREATE INDEX ixBerPers_2017k2_TsReg ON Ber.BerPers_2017k2 USING BRIN (TsReg);
CREATE INDEX ixBerPers_2017k2_Ber ON Ber.BerPers_2017k2 USING BRIN (Ber);

CREATE TABLE Ber.BerPers_2017k3 ( 
   CHECK ( TsReg >= TIMESTAMP WITH TIME ZONE '2017-07-01 00:00:00+0' AND TsReg < TIMESTAMP WITH TIME ZONE '2017-10-01 00:00:00+0' )
) INHERITS (Ber.BerPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 5000000, autovacuum_analyze_threshold = 5000000);
CREATE INDEX ixBerPers_2017k3_TsReg ON Ber.BerPers_2017k3 USING BRIN (TsReg);
CREATE INDEX ixBerPers_2017k3_Ber ON Ber.BerPers_2017k3 USING BRIN (Ber);

CREATE TABLE Ber.BerPers_2017k4 ( 
   CHECK ( TsReg >= TIMESTAMP WITH TIME ZONE '2017-10-01 00:00:00+0' AND TsReg < TIMESTAMP WITH TIME ZONE '2018-01-01 00:00:00+0' )
) INHERITS (Ber.BerPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 5000000, autovacuum_analyze_threshold = 5000000);
CREATE INDEX ixBerPers_2017k4_TsReg ON Ber.BerPers_2017k4 USING BRIN (TsReg);
CREATE INDEX ixBerPers_2017k4_Ber ON Ber.BerPers_2017k4 USING BRIN (Ber);

CREATE TABLE Ber.BerPers_2018k1 ( 
   CHECK ( TsReg >= TIMESTAMP WITH TIME ZONE '2018-01-01 00:00:00+0' AND TsReg < TIMESTAMP WITH TIME ZONE '2018-04-01 00:00:00+0' )
) INHERITS (Ber.BerPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 5000000, autovacuum_analyze_threshold = 5000000);
CREATE INDEX ixBerPers_2018k1_TsReg ON Ber.BerPers_2018k1 USING BRIN (TsReg);
CREATE INDEX ixBerPers_2018k1_Ber ON Ber.BerPers_2018k1 USING BRIN (Ber);

CREATE TABLE Ber.BerPers_2018k2 ( 
   CHECK ( TsReg >= TIMESTAMP WITH TIME ZONE '2018-04-01 00:00:00+0' AND TsReg < TIMESTAMP WITH TIME ZONE '2018-07-01 00:00:00+0' )
) INHERITS (Ber.BerPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 5000000, autovacuum_analyze_threshold = 5000000);
CREATE INDEX ixBerPers_2018k2_TsReg ON Ber.BerPers_2018k2 USING BRIN (TsReg);
CREATE INDEX ixBerPers_2018k2_Ber ON Ber.BerPers_2018k2 USING BRIN (Ber);

CREATE TABLE Ber.BerPers_2018k3 ( 
   CHECK ( TsReg >= TIMESTAMP WITH TIME ZONE '2018-07-01 00:00:00+0' AND TsReg < TIMESTAMP WITH TIME ZONE '2018-10-01 00:00:00+0' )
) INHERITS (Ber.BerPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 5000000, autovacuum_analyze_threshold = 5000000);
CREATE INDEX ixBerPers_2018k3_TsReg ON Ber.BerPers_2018k3 USING BRIN (TsReg);
CREATE INDEX ixBerPers_2018k3_Ber ON Ber.BerPers_2018k3 USING BRIN (Ber);

CREATE TABLE Ber.BerPers_2018k4 ( 
   CHECK ( TsReg >= TIMESTAMP WITH TIME ZONE '2018-10-01 00:00:00+0' AND TsReg < TIMESTAMP WITH TIME ZONE '2019-01-01 00:00:00+0' )
) INHERITS (Ber.BerPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 5000000, autovacuum_analyze_threshold = 5000000);
CREATE INDEX ixBerPers_2018k4_TsReg ON Ber.BerPers_2018k4 USING BRIN (TsReg);
CREATE INDEX ixBerPers_2018k4_Ber ON Ber.BerPers_2018k4 USING BRIN (Ber);

CREATE TABLE Ber.BerPers_2019k1 ( 
   CHECK ( TsReg >= TIMESTAMP WITH TIME ZONE '2019-01-01 00:00:00+0' AND TsReg < TIMESTAMP WITH TIME ZONE '2019-04-01 00:00:00+0' )
) INHERITS (Ber.BerPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 5000000, autovacuum_analyze_threshold = 5000000);
CREATE INDEX ixBerPers_2019k1_TsReg ON Ber.BerPers_2019k1 USING BRIN (TsReg);
CREATE INDEX ixBerPers_2019k1_Ber ON Ber.BerPers_2019k1 USING BRIN (Ber);

CREATE TABLE Ber.BerPers_2019k2 ( 
   CHECK ( TsReg >= TIMESTAMP WITH TIME ZONE '2019-04-01 00:00:00+0' AND TsReg < TIMESTAMP WITH TIME ZONE '2019-07-01 00:00:00+0' )
) INHERITS (Ber.BerPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 5000000, autovacuum_analyze_threshold = 5000000);
CREATE INDEX ixBerPers_2019k2_TsReg ON Ber.BerPers_2019k2 USING BRIN (TsReg);
CREATE INDEX ixBerPers_2019k2_Ber ON Ber.BerPers_2019k2 USING BRIN (Ber);

CREATE TABLE Ber.BerPers_2019k3 ( 
   CHECK ( TsReg >= TIMESTAMP WITH TIME ZONE '2019-07-01 00:00:00+0' AND TsReg < TIMESTAMP WITH TIME ZONE '2019-10-01 00:00:00+0' )
) INHERITS (Ber.BerPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 5000000, autovacuum_analyze_threshold = 5000000);
CREATE INDEX ixBerPers_2019k3_TsReg ON Ber.BerPers_2019k3 USING BRIN (TsReg);
CREATE INDEX ixBerPers_2019k3_Ber ON Ber.BerPers_2019k3 USING BRIN (Ber);

CREATE TABLE Ber.BerPers_2019k4 ( 
   CHECK ( TsReg >= TIMESTAMP WITH TIME ZONE '2019-10-01 00:00:00+0' AND TsReg < TIMESTAMP WITH TIME ZONE '2020-01-01 00:00:00+0' )
) INHERITS (Ber.BerPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 5000000, autovacuum_analyze_threshold = 5000000);
CREATE INDEX ixBerPers_2019k4_TsReg ON Ber.BerPers_2019k4 USING BRIN (TsReg);
CREATE INDEX ixBerPers_2019k4_Ber ON Ber.BerPers_2019k4 USING BRIN (Ber);

CREATE TABLE Ber.BerPers_2020k1 ( 
   CHECK ( TsReg >= TIMESTAMP WITH TIME ZONE '2020-01-01 00:00:00+0' AND TsReg < TIMESTAMP WITH TIME ZONE '2020-04-01 00:00:00+0' )
) INHERITS (Ber.BerPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 5000000, autovacuum_analyze_threshold = 5000000);
CREATE INDEX ixBerPers_2020k1_TsReg ON Ber.BerPers_2020k1 USING BRIN (TsReg);
CREATE INDEX ixBerPers_2020k1_Ber ON Ber.BerPers_2020k1 USING BRIN (Ber);

CREATE TABLE Ber.BerPers_2020k2 ( 
   CHECK ( TsReg >= TIMESTAMP WITH TIME ZONE '2020-04-01 00:00:00+0' AND TsReg < TIMESTAMP WITH TIME ZONE '2020-07-01 00:00:00+0' )
) INHERITS (Ber.BerPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 5000000, autovacuum_analyze_threshold = 5000000);
CREATE INDEX ixBerPers_2020k2_TsReg ON Ber.BerPers_2020k2 USING BRIN (TsReg);
CREATE INDEX ixBerPers_2020k2_Ber ON Ber.BerPers_2020k2 USING BRIN (Ber);

CREATE TABLE Ber.BerPers_2020k3 ( 
   CHECK ( TsReg >= TIMESTAMP WITH TIME ZONE '2020-07-01 00:00:00+0' AND TsReg < TIMESTAMP WITH TIME ZONE '2020-10-01 00:00:00+0' )
) INHERITS (Ber.BerPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 5000000, autovacuum_analyze_threshold = 5000000);
CREATE INDEX ixBerPers_2020k3_TsReg ON Ber.BerPers_2020k3 USING BRIN (TsReg);
CREATE INDEX ixBerPers_2020k3_Ber ON Ber.BerPers_2020k3 USING BRIN (Ber);

CREATE TABLE Ber.BerPers_2020k4 ( 
   CHECK ( TsReg >= TIMESTAMP WITH TIME ZONE '2020-10-01 00:00:00+0' AND TsReg < TIMESTAMP WITH TIME ZONE '2021-01-01 00:00:00+0' )
) INHERITS (Ber.BerPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 5000000, autovacuum_analyze_threshold = 5000000);
CREATE INDEX ixBerPers_2020k4_TsReg ON Ber.BerPers_2020k4 USING BRIN (TsReg);
CREATE INDEX ixBerPers_2020k4_Ber ON Ber.BerPers_2020k4 USING BRIN (Ber);

CREATE TABLE Ber.BerPers_2021k1 ( 
   CHECK ( TsReg >= TIMESTAMP WITH TIME ZONE '2021-01-01 00:00:00+0' AND TsReg < TIMESTAMP WITH TIME ZONE '2021-04-01 00:00:00+0' )
) INHERITS (Ber.BerPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 5000000, autovacuum_analyze_threshold = 5000000);
CREATE INDEX ixBerPers_2021k1_TsReg ON Ber.BerPers_2021k1 USING BRIN (TsReg);
CREATE INDEX ixBerPers_2021k1_Ber ON Ber.BerPers_2021k1 USING BRIN (Ber);

CREATE TABLE Ber.BerPers_2021k2 ( 
   CHECK ( TsReg >= TIMESTAMP WITH TIME ZONE '2021-04-01 00:00:00+0' AND TsReg < TIMESTAMP WITH TIME ZONE '2021-07-01 00:00:00+0' )
) INHERITS (Ber.BerPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 5000000, autovacuum_analyze_threshold = 5000000);
CREATE INDEX ixBerPers_2021k2_TsReg ON Ber.BerPers_2021k2 USING BRIN (TsReg);
CREATE INDEX ixBerPers_2021k2_Ber ON Ber.BerPers_2021k2 USING BRIN (Ber);

CREATE TABLE Ber.BerPers_2021k3 ( 
   CHECK ( TsReg >= TIMESTAMP WITH TIME ZONE '2021-07-01 00:00:00+0' AND TsReg < TIMESTAMP WITH TIME ZONE '2021-10-01 00:00:00+0' )
) INHERITS (Ber.BerPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 5000000, autovacuum_analyze_threshold = 5000000);
CREATE INDEX ixBerPers_2021k3_TsReg ON Ber.BerPers_2021k3 USING BRIN (TsReg);
CREATE INDEX ixBerPers_2021k3_Ber ON Ber.BerPers_2021k3 USING BRIN (Ber);

CREATE TABLE Ber.BerPers_2021k4 ( 
   CHECK ( TsReg >= TIMESTAMP WITH TIME ZONE '2021-10-01 00:00:00+0' AND TsReg < TIMESTAMP WITH TIME ZONE '2022-01-01 00:00:00+0' )
) INHERITS (Ber.BerPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 5000000, autovacuum_analyze_threshold = 5000000);
CREATE INDEX ixBerPers_2021k4_TsReg ON Ber.BerPers_2021k4 USING BRIN (TsReg);
CREATE INDEX ixBerPers_2021k4_Ber ON Ber.BerPers_2021k4 USING BRIN (Ber);


CREATE OR REPLACE FUNCTION Ber.BerPers_insert_function()
RETURNS TRIGGER AS $$
BEGIN
   IF (NEW.TsReg < TIMESTAMP WITH TIME ZONE '2017-01-01 00:00:00+0') THEN
      INSERT INTO Ber.BerPers_2016 VALUES (NEW.*);
   ELSIF (NEW.TsReg >= TIMESTAMP WITH TIME ZONE '2017-01-01 00:00:00+0' AND
       NEW.TsReg < TIMESTAMP WITH TIME ZONE '2017-04-01 00:00:00+0') THEN
      INSERT INTO Ber.BerPers_2017k1 VALUES (NEW.*);
   ELSIF (NEW.TsReg >= TIMESTAMP WITH TIME ZONE '2017-04-01 00:00:00+0' AND
       NEW.TsReg < TIMESTAMP WITH TIME ZONE '2017-07-01 00:00:00+0') THEN
      INSERT INTO Ber.BerPers_2017k2 VALUES (NEW.*);
   ELSIF (NEW.TsReg >= TIMESTAMP WITH TIME ZONE '2017-07-01 00:00:00+0' AND
       NEW.TsReg < TIMESTAMP WITH TIME ZONE '2017-10-01 00:00:00+0') THEN
      INSERT INTO Ber.BerPers_2017k3 VALUES (NEW.*);
   ELSIF (NEW.TsReg >= TIMESTAMP WITH TIME ZONE '2017-10-01 00:00:00+0' AND
       NEW.TsReg < TIMESTAMP WITH TIME ZONE '2018-01-01 00:00:00+0') THEN
      INSERT INTO Ber.BerPers_2017k4 VALUES (NEW.*);
   ELSIF (NEW.TsReg >= TIMESTAMP WITH TIME ZONE '2018-01-01 00:00:00+0' AND
       NEW.TsReg < TIMESTAMP WITH TIME ZONE '2018-04-01 00:00:00+0') THEN
      INSERT INTO Ber.BerPers_2018k1 VALUES (NEW.*);
   ELSIF (NEW.TsReg >= TIMESTAMP WITH TIME ZONE '2018-04-01 00:00:00+0' AND
       NEW.TsReg < TIMESTAMP WITH TIME ZONE '2018-07-01 00:00:00+0') THEN
      INSERT INTO Ber.BerPers_2018k2 VALUES (NEW.*);
   ELSIF (NEW.TsReg >= TIMESTAMP WITH TIME ZONE '2018-07-01 00:00:00+0' AND
       NEW.TsReg < TIMESTAMP WITH TIME ZONE '2018-10-01 00:00:00+0') THEN
      INSERT INTO Ber.BerPers_2018k3 VALUES (NEW.*);
   ELSIF (NEW.TsReg >= TIMESTAMP WITH TIME ZONE '2018-10-01 00:00:00+0' AND
       NEW.TsReg < TIMESTAMP WITH TIME ZONE '2019-01-01 00:00:00+0') THEN
      INSERT INTO Ber.BerPers_2018k4 VALUES (NEW.*);
   ELSIF (NEW.TsReg >= TIMESTAMP WITH TIME ZONE '2019-01-01 00:00:00+0' AND
       NEW.TsReg < TIMESTAMP WITH TIME ZONE '2019-04-01 00:00:00+0') THEN
      INSERT INTO Ber.BerPers_2019k1 VALUES (NEW.*);
   ELSIF (NEW.TsReg >= TIMESTAMP WITH TIME ZONE '2019-04-01 00:00:00+0' AND
       NEW.TsReg < TIMESTAMP WITH TIME ZONE '2019-07-01 00:00:00+0') THEN
      INSERT INTO Ber.BerPers_2019k2 VALUES (NEW.*);
   ELSIF (NEW.TsReg >= TIMESTAMP WITH TIME ZONE '2019-07-01 00:00:00+0' AND
       NEW.TsReg < TIMESTAMP WITH TIME ZONE '2019-10-01 00:00:00+0') THEN
      INSERT INTO Ber.BerPers_2019k3 VALUES (NEW.*);
   ELSIF (NEW.TsReg >= TIMESTAMP WITH TIME ZONE '2019-10-01 00:00:00+0' AND
       NEW.TsReg < TIMESTAMP WITH TIME ZONE '2020-01-01 00:00:00+0') THEN
      INSERT INTO Ber.BerPers_2019k4 VALUES (NEW.*);
   ELSIF (NEW.TsReg >= TIMESTAMP WITH TIME ZONE '2020-01-01 00:00:00+0' AND
       NEW.TsReg < TIMESTAMP WITH TIME ZONE '2020-04-01 00:00:00+0') THEN
      INSERT INTO Ber.BerPers_2020k1 VALUES (NEW.*);
   ELSIF (NEW.TsReg >= TIMESTAMP WITH TIME ZONE '2020-04-01 00:00:00+0' AND
       NEW.TsReg < TIMESTAMP WITH TIME ZONE '2020-07-01 00:00:00+0') THEN
      INSERT INTO Ber.BerPers_2020k2 VALUES (NEW.*);
   ELSIF (NEW.TsReg >= TIMESTAMP WITH TIME ZONE '2020-07-01 00:00:00+0' AND
       NEW.TsReg < TIMESTAMP WITH TIME ZONE '2020-10-01 00:00:00+0') THEN
      INSERT INTO Ber.BerPers_2020k3 VALUES (NEW.*);
   ELSIF (NEW.TsReg >= TIMESTAMP WITH TIME ZONE '2020-10-01 00:00:00+0' AND
       NEW.TsReg < TIMESTAMP WITH TIME ZONE '2021-01-01 00:00:00+0') THEN
      INSERT INTO Ber.BerPers_2020k4 VALUES (NEW.*);
   ELSIF (NEW.TsReg >= TIMESTAMP WITH TIME ZONE '2021-01-01 00:00:00+0' AND
       NEW.TsReg < TIMESTAMP WITH TIME ZONE '2021-04-01 00:00:00+0') THEN
      INSERT INTO Ber.BerPers_2021k1 VALUES (NEW.*);
   ELSIF (NEW.TsReg >= TIMESTAMP WITH TIME ZONE '2021-04-01 00:00:00+0' AND
       NEW.TsReg < TIMESTAMP WITH TIME ZONE '2021-07-01 00:00:00+0') THEN
      INSERT INTO Ber.BerPers_2021k2 VALUES (NEW.*);
   ELSIF (NEW.TsReg >= TIMESTAMP WITH TIME ZONE '2021-07-01 00:00:00+0' AND
       NEW.TsReg < TIMESTAMP WITH TIME ZONE '2021-10-01 00:00:00+0') THEN
      INSERT INTO Ber.BerPers_2021k3 VALUES (NEW.*);
   ELSIF (NEW.TsReg >= TIMESTAMP WITH TIME ZONE '2021-10-01 00:00:00+0' AND
       NEW.TsReg < TIMESTAMP WITH TIME ZONE '2022-01-01 00:00:00+0') THEN
      INSERT INTO Ber.BerPers_2021k4 VALUES (NEW.*);
   ELSE
      RAISE EXCEPTION 'Geen partitie voor BerPers.TsReg.';
   END IF;
   RETURN NULL;
END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER BerPers_insert_trigger
   BEFORE INSERT ON Ber.BerPers
      FOR EACH ROW EXECUTE PROCEDURE Ber.BerPers_insert_function();

