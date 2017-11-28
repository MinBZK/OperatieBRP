--------------------------------------------------------------------------------
-- PostgreSQL SQL-DDL bestand voor Basisregistratie Personen (BRP)            --
-- PostgreSQL - Protocol Structuur Aanvullend                                 --
--------------------------------------------------------------------------------
-- 
-- Gegenereerd door BRP Meta Register Release 58.
-- Gegenereerd op: vrijdag 19 mei 2017 10:00:47
-- 
--------------------------------------------------------------------------------


-- Sequences van stamtabellen (her)zetten naar de juiste waarden

-- Statistieken
ALTER TABLE Prot.Levsaantek SET (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 10000000, autovacuum_analyze_threshold = 10000000);
ALTER TABLE Prot.LevsaantekPers SET (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 10000000, autovacuum_analyze_threshold = 10000000);
ALTER TABLE Prot.ScopePatroon SET (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2);
ALTER TABLE Prot.ScopePatroonElement SET (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2);

-- Partitionering ----------------------------------------------------------------
-- LevsAantek
CREATE TABLE Prot.Levsaantek_2005 ( 
   CONSTRAINT pk_Levsaantek_2005 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2006-01-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2005_TsKlaarzettenLev ON Prot.Levsaantek_2005 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2006k1 ( 
   CONSTRAINT pk_Levsaantek_2006k1 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2006-01-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2006-04-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2006k1_TsKlaarzettenLev ON Prot.Levsaantek_2006k1 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2006k2 ( 
   CONSTRAINT pk_Levsaantek_2006k2 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2006-04-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2006-07-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2006k2_TsKlaarzettenLev ON Prot.Levsaantek_2006k2 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2006k3 ( 
   CONSTRAINT pk_Levsaantek_2006k3 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2006-07-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2006-10-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2006k3_TsKlaarzettenLev ON Prot.Levsaantek_2006k3 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2006k4 ( 
   CONSTRAINT pk_Levsaantek_2006k4 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2006-10-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2007-01-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2006k4_TsKlaarzettenLev ON Prot.Levsaantek_2006k4 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2007k1 ( 
   CONSTRAINT pk_Levsaantek_2007k1 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2007-01-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2007-04-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2007k1_TsKlaarzettenLev ON Prot.Levsaantek_2007k1 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2007k2 ( 
   CONSTRAINT pk_Levsaantek_2007k2 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2007-04-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2007-07-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2007k2_TsKlaarzettenLev ON Prot.Levsaantek_2007k2 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2007k3 ( 
   CONSTRAINT pk_Levsaantek_2007k3 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2007-07-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2007-10-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2007k3_TsKlaarzettenLev ON Prot.Levsaantek_2007k3 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2007k4 ( 
   CONSTRAINT pk_Levsaantek_2007k4 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2007-10-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2008-01-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2007k4_TsKlaarzettenLev ON Prot.Levsaantek_2007k4 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2008k1 ( 
   CONSTRAINT pk_Levsaantek_2008k1 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2008-01-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2008-04-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2008k1_TsKlaarzettenLev ON Prot.Levsaantek_2008k1 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2008k2 ( 
   CONSTRAINT pk_Levsaantek_2008k2 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2008-04-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2008-07-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2008k2_TsKlaarzettenLev ON Prot.Levsaantek_2008k2 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2008k3 ( 
   CONSTRAINT pk_Levsaantek_2008k3 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2008-07-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2008-10-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2008k3_TsKlaarzettenLev ON Prot.Levsaantek_2008k3 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2008k4 ( 
   CONSTRAINT pk_Levsaantek_2008k4 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2008-10-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2009-01-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2008k4_TsKlaarzettenLev ON Prot.Levsaantek_2008k4 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2009k1 ( 
   CONSTRAINT pk_Levsaantek_2009k1 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2009-01-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2009-04-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2009k1_TsKlaarzettenLev ON Prot.Levsaantek_2009k1 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2009k2 ( 
   CONSTRAINT pk_Levsaantek_2009k2 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2009-04-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2009-07-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2009k2_TsKlaarzettenLev ON Prot.Levsaantek_2009k2 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2009k3 ( 
   CONSTRAINT pk_Levsaantek_2009k3 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2009-07-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2009-10-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2009k3_TsKlaarzettenLev ON Prot.Levsaantek_2009k3 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2009k4 ( 
   CONSTRAINT pk_Levsaantek_2009k4 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2009-10-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2010-01-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2009k4_TsKlaarzettenLev ON Prot.Levsaantek_2009k4 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2010k1 ( 
   CONSTRAINT pk_Levsaantek_2010k1 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2010-01-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2010-04-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2010k1_TsKlaarzettenLev ON Prot.Levsaantek_2010k1 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2010k2 ( 
   CONSTRAINT pk_Levsaantek_2010k2 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2010-04-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2010-07-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2010k2_TsKlaarzettenLev ON Prot.Levsaantek_2010k2 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2010k3 ( 
   CONSTRAINT pk_Levsaantek_2010k3 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2010-07-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2010-10-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2010k3_TsKlaarzettenLev ON Prot.Levsaantek_2010k3 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2010k4 ( 
   CONSTRAINT pk_Levsaantek_2010k4 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2010-10-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2011-01-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2010k4_TsKlaarzettenLev ON Prot.Levsaantek_2010k4 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2011k1 ( 
   CONSTRAINT pk_Levsaantek_2011k1 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2011-01-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2011-04-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2011k1_TsKlaarzettenLev ON Prot.Levsaantek_2011k1 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2011k2 ( 
   CONSTRAINT pk_Levsaantek_2011k2 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2011-04-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2011-07-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2011k2_TsKlaarzettenLev ON Prot.Levsaantek_2011k2 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2011k3 ( 
   CONSTRAINT pk_Levsaantek_2011k3 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2011-07-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2011-10-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2011k3_TsKlaarzettenLev ON Prot.Levsaantek_2011k3 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2011k4 ( 
   CONSTRAINT pk_Levsaantek_2011k4 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2011-10-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2012-01-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2011k4_TsKlaarzettenLev ON Prot.Levsaantek_2011k4 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2012k1 ( 
   CONSTRAINT pk_Levsaantek_2012k1 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2012-01-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2012-04-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2012k1_TsKlaarzettenLev ON Prot.Levsaantek_2012k1 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2012k2 ( 
   CONSTRAINT pk_Levsaantek_2012k2 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2012-04-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2012-07-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2012k2_TsKlaarzettenLev ON Prot.Levsaantek_2012k2 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2012k3 ( 
   CONSTRAINT pk_Levsaantek_2012k3 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2012-07-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2012-10-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2012k3_TsKlaarzettenLev ON Prot.Levsaantek_2012k3 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2012k4 ( 
   CONSTRAINT pk_Levsaantek_2012k4 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2012-10-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2013-01-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2012k4_TsKlaarzettenLev ON Prot.Levsaantek_2012k4 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2013k1 ( 
   CONSTRAINT pk_Levsaantek_2013k1 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2013-01-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2013-04-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2013k1_TsKlaarzettenLev ON Prot.Levsaantek_2013k1 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2013k2 ( 
   CONSTRAINT pk_Levsaantek_2013k2 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2013-04-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2013-07-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2013k2_TsKlaarzettenLev ON Prot.Levsaantek_2013k2 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2013k3 ( 
   CONSTRAINT pk_Levsaantek_2013k3 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2013-07-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2013-10-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2013k3_TsKlaarzettenLev ON Prot.Levsaantek_2013k3 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2013k4 ( 
   CONSTRAINT pk_Levsaantek_2013k4 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2013-10-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2014-01-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2013k4_TsKlaarzettenLev ON Prot.Levsaantek_2013k4 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2014k1 ( 
   CONSTRAINT pk_Levsaantek_2014k1 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2014-01-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2014-04-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2014k1_TsKlaarzettenLev ON Prot.Levsaantek_2014k1 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2014k2 ( 
   CONSTRAINT pk_Levsaantek_2014k2 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2014-04-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2014-07-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2014k2_TsKlaarzettenLev ON Prot.Levsaantek_2014k2 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2014k3 ( 
   CONSTRAINT pk_Levsaantek_2014k3 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2014-07-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2014-10-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2014k3_TsKlaarzettenLev ON Prot.Levsaantek_2014k3 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2014k4 ( 
   CONSTRAINT pk_Levsaantek_2014k4 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2014-10-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2015-01-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2014k4_TsKlaarzettenLev ON Prot.Levsaantek_2014k4 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2015k1 ( 
   CONSTRAINT pk_Levsaantek_2015k1 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2015-01-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2015-04-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2015k1_TsKlaarzettenLev ON Prot.Levsaantek_2015k1 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2015k2 ( 
   CONSTRAINT pk_Levsaantek_2015k2 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2015-04-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2015-07-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2015k2_TsKlaarzettenLev ON Prot.Levsaantek_2015k2 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2015k3 ( 
   CONSTRAINT pk_Levsaantek_2015k3 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2015-07-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2015-10-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2015k3_TsKlaarzettenLev ON Prot.Levsaantek_2015k3 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2015k4 ( 
   CONSTRAINT pk_Levsaantek_2015k4 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2015-10-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2016-01-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2015k4_TsKlaarzettenLev ON Prot.Levsaantek_2015k4 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2016k1 ( 
   CONSTRAINT pk_Levsaantek_2016k1 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2016-01-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2016-04-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2016k1_TsKlaarzettenLev ON Prot.Levsaantek_2016k1 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2016k2 ( 
   CONSTRAINT pk_Levsaantek_2016k2 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2016-04-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2016-07-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2016k2_TsKlaarzettenLev ON Prot.Levsaantek_2016k2 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2016k3 ( 
   CONSTRAINT pk_Levsaantek_2016k3 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2016-07-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2016-10-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2016k3_TsKlaarzettenLev ON Prot.Levsaantek_2016k3 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2016k4 ( 
   CONSTRAINT pk_Levsaantek_2016k4 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2016-10-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2017-01-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2016k4_TsKlaarzettenLev ON Prot.Levsaantek_2016k4 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2017k1 ( 
   CONSTRAINT pk_Levsaantek_2017k1 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2017-01-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2017-04-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2017k1_TsKlaarzettenLev ON Prot.Levsaantek_2017k1 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2017k2 ( 
   CONSTRAINT pk_Levsaantek_2017k2 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2017-04-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2017-07-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2017k2_TsKlaarzettenLev ON Prot.Levsaantek_2017k2 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2017k3 ( 
   CONSTRAINT pk_Levsaantek_2017k3 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2017-07-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2017-10-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2017k3_TsKlaarzettenLev ON Prot.Levsaantek_2017k3 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2017k4 ( 
   CONSTRAINT pk_Levsaantek_2017k4 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2017-10-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2018-01-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2017k4_TsKlaarzettenLev ON Prot.Levsaantek_2017k4 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2018k1 ( 
   CONSTRAINT pk_Levsaantek_2018k1 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2018-01-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2018-04-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2018k1_TsKlaarzettenLev ON Prot.Levsaantek_2018k1 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2018k2 ( 
   CONSTRAINT pk_Levsaantek_2018k2 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2018-04-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2018-07-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2018k2_TsKlaarzettenLev ON Prot.Levsaantek_2018k2 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2018k3 ( 
   CONSTRAINT pk_Levsaantek_2018k3 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2018-07-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2018-10-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2018k3_TsKlaarzettenLev ON Prot.Levsaantek_2018k3 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2018k4 ( 
   CONSTRAINT pk_Levsaantek_2018k4 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2018-10-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2019-01-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2018k4_TsKlaarzettenLev ON Prot.Levsaantek_2018k4 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2019k1 ( 
   CONSTRAINT pk_Levsaantek_2019k1 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2019-01-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2019-04-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2019k1_TsKlaarzettenLev ON Prot.Levsaantek_2019k1 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2019k2 ( 
   CONSTRAINT pk_Levsaantek_2019k2 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2019-04-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2019-07-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2019k2_TsKlaarzettenLev ON Prot.Levsaantek_2019k2 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2019k3 ( 
   CONSTRAINT pk_Levsaantek_2019k3 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2019-07-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2019-10-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2019k3_TsKlaarzettenLev ON Prot.Levsaantek_2019k3 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2019k4 ( 
   CONSTRAINT pk_Levsaantek_2019k4 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2019-10-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2020-01-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2019k4_TsKlaarzettenLev ON Prot.Levsaantek_2019k4 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2020k1 ( 
   CONSTRAINT pk_Levsaantek_2020k1 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2020-01-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2020-04-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2020k1_TsKlaarzettenLev ON Prot.Levsaantek_2020k1 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2020k2 ( 
   CONSTRAINT pk_Levsaantek_2020k2 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2020-04-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2020-07-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2020k2_TsKlaarzettenLev ON Prot.Levsaantek_2020k2 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2020k3 ( 
   CONSTRAINT pk_Levsaantek_2020k3 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2020-07-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2020-10-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2020k3_TsKlaarzettenLev ON Prot.Levsaantek_2020k3 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2020k4 ( 
   CONSTRAINT pk_Levsaantek_2020k4 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2020-10-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2021-01-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2020k4_TsKlaarzettenLev ON Prot.Levsaantek_2020k4 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2021k1 ( 
   CONSTRAINT pk_Levsaantek_2021k1 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2021-01-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2021-04-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2021k1_TsKlaarzettenLev ON Prot.Levsaantek_2021k1 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2021k2 ( 
   CONSTRAINT pk_Levsaantek_2021k2 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2021-04-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2021-07-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2021k2_TsKlaarzettenLev ON Prot.Levsaantek_2021k2 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2021k3 ( 
   CONSTRAINT pk_Levsaantek_2021k3 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2021-07-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2021-10-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2021k3_TsKlaarzettenLev ON Prot.Levsaantek_2021k3 USING BRIN (TsKlaarzettenLev);

CREATE TABLE Prot.Levsaantek_2021k4 ( 
   CONSTRAINT pk_Levsaantek_2021k4 PRIMARY KEY (ID),
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2021-10-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2022-01-01 00:00:00+0' )
) INHERITS (Prot.Levsaantek) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantek_2021k4_TsKlaarzettenLev ON Prot.Levsaantek_2021k4 USING BRIN (TsKlaarzettenLev);


CREATE OR REPLACE FUNCTION Prot.Levsaantek_insert_function()
RETURNS TRIGGER AS $$
BEGIN
   IF (NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2006-01-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2005 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2006-01-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2006-04-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2006k1 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2006-04-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2006-07-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2006k2 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2006-07-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2006-10-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2006k3 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2006-10-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2007-01-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2006k4 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2007-01-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2007-04-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2007k1 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2007-04-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2007-07-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2007k2 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2007-07-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2007-10-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2007k3 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2007-10-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2008-01-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2007k4 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2008-01-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2008-04-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2008k1 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2008-04-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2008-07-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2008k2 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2008-07-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2008-10-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2008k3 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2008-10-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2009-01-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2008k4 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2009-01-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2009-04-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2009k1 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2009-04-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2009-07-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2009k2 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2009-07-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2009-10-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2009k3 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2009-10-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2010-01-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2009k4 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2010-01-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2010-04-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2010k1 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2010-04-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2010-07-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2010k2 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2010-07-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2010-10-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2010k3 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2010-10-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2011-01-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2010k4 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2011-01-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2011-04-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2011k1 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2011-04-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2011-07-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2011k2 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2011-07-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2011-10-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2011k3 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2011-10-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2012-01-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2011k4 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2012-01-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2012-04-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2012k1 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2012-04-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2012-07-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2012k2 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2012-07-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2012-10-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2012k3 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2012-10-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2013-01-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2012k4 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2013-01-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2013-04-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2013k1 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2013-04-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2013-07-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2013k2 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2013-07-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2013-10-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2013k3 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2013-10-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2014-01-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2013k4 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2014-01-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2014-04-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2014k1 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2014-04-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2014-07-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2014k2 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2014-07-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2014-10-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2014k3 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2014-10-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2015-01-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2014k4 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2015-01-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2015-04-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2015k1 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2015-04-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2015-07-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2015k2 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2015-07-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2015-10-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2015k3 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2015-10-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2016-01-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2015k4 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2016-01-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2016-04-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2016k1 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2016-04-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2016-07-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2016k2 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2016-07-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2016-10-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2016k3 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2016-10-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2017-01-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2016k4 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2017-01-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2017-04-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2017k1 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2017-04-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2017-07-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2017k2 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2017-07-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2017-10-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2017k3 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2017-10-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2018-01-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2017k4 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2018-01-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2018-04-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2018k1 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2018-04-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2018-07-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2018k2 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2018-07-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2018-10-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2018k3 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2018-10-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2019-01-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2018k4 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2019-01-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2019-04-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2019k1 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2019-04-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2019-07-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2019k2 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2019-07-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2019-10-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2019k3 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2019-10-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2020-01-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2019k4 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2020-01-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2020-04-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2020k1 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2020-04-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2020-07-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2020k2 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2020-07-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2020-10-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2020k3 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2020-10-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2021-01-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2020k4 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2021-01-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2021-04-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2021k1 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2021-04-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2021-07-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2021k2 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2021-07-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2021-10-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2021k3 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2021-10-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2022-01-01 00:00:00+0') THEN
      INSERT INTO Prot.Levsaantek_2021k4 VALUES (NEW.*);
   ELSE
      RAISE EXCEPTION 'Geen partitie voor Levsaantek.TsKlaarzettenLev.';
   END IF;
   RETURN NULL;
END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER Levsaantek_insert_trigger
   BEFORE INSERT ON Prot.Levsaantek
      FOR EACH ROW EXECUTE PROCEDURE Prot.Levsaantek_insert_function();


-- LevsAantekPers
CREATE TABLE Prot.LevsaantekPers_2005 ( 
   CHECK ( TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2006-01-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2005_TsKlaarzettenLev ON Prot.LevsaantekPers_2005 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2005_Levsaantek ON Prot.LevsaantekPers_2005 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2005_Pers ON Prot.LevsaantekPers_2005 (Pers);

CREATE TABLE Prot.LevsaantekPers_2006k1 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2006-01-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2006-04-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2006k1_TsKlaarzettenLev ON Prot.LevsaantekPers_2006k1 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2006k1_Levsaantek ON Prot.LevsaantekPers_2006k1 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2006k1_Pers ON Prot.LevsaantekPers_2006k1 (Pers);

CREATE TABLE Prot.LevsaantekPers_2006k2 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2006-04-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2006-07-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2006k2_TsKlaarzettenLev ON Prot.LevsaantekPers_2006k2 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2006k2_Levsaantek ON Prot.LevsaantekPers_2006k2 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2006k2_Pers ON Prot.LevsaantekPers_2006k2 (Pers);

CREATE TABLE Prot.LevsaantekPers_2006k3 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2006-07-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2006-10-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2006k3_TsKlaarzettenLev ON Prot.LevsaantekPers_2006k3 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2006k3_Levsaantek ON Prot.LevsaantekPers_2006k3 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2006k3_Pers ON Prot.LevsaantekPers_2006k3 (Pers);

CREATE TABLE Prot.LevsaantekPers_2006k4 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2006-10-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2007-01-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2006k4_TsKlaarzettenLev ON Prot.LevsaantekPers_2006k4 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2006k4_Levsaantek ON Prot.LevsaantekPers_2006k4 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2006k4_Pers ON Prot.LevsaantekPers_2006k4 (Pers);

CREATE TABLE Prot.LevsaantekPers_2007k1 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2007-01-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2007-04-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2007k1_TsKlaarzettenLev ON Prot.LevsaantekPers_2007k1 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2007k1_Levsaantek ON Prot.LevsaantekPers_2007k1 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2007k1_Pers ON Prot.LevsaantekPers_2007k1 (Pers);

CREATE TABLE Prot.LevsaantekPers_2007k2 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2007-04-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2007-07-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2007k2_TsKlaarzettenLev ON Prot.LevsaantekPers_2007k2 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2007k2_Levsaantek ON Prot.LevsaantekPers_2007k2 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2007k2_Pers ON Prot.LevsaantekPers_2007k2 (Pers);

CREATE TABLE Prot.LevsaantekPers_2007k3 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2007-07-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2007-10-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2007k3_TsKlaarzettenLev ON Prot.LevsaantekPers_2007k3 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2007k3_Levsaantek ON Prot.LevsaantekPers_2007k3 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2007k3_Pers ON Prot.LevsaantekPers_2007k3 (Pers);

CREATE TABLE Prot.LevsaantekPers_2007k4 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2007-10-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2008-01-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2007k4_TsKlaarzettenLev ON Prot.LevsaantekPers_2007k4 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2007k4_Levsaantek ON Prot.LevsaantekPers_2007k4 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2007k4_Pers ON Prot.LevsaantekPers_2007k4 (Pers);

CREATE TABLE Prot.LevsaantekPers_2008k1 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2008-01-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2008-04-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2008k1_TsKlaarzettenLev ON Prot.LevsaantekPers_2008k1 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2008k1_Levsaantek ON Prot.LevsaantekPers_2008k1 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2008k1_Pers ON Prot.LevsaantekPers_2008k1 (Pers);

CREATE TABLE Prot.LevsaantekPers_2008k2 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2008-04-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2008-07-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2008k2_TsKlaarzettenLev ON Prot.LevsaantekPers_2008k2 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2008k2_Levsaantek ON Prot.LevsaantekPers_2008k2 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2008k2_Pers ON Prot.LevsaantekPers_2008k2 (Pers);

CREATE TABLE Prot.LevsaantekPers_2008k3 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2008-07-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2008-10-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2008k3_TsKlaarzettenLev ON Prot.LevsaantekPers_2008k3 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2008k3_Levsaantek ON Prot.LevsaantekPers_2008k3 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2008k3_Pers ON Prot.LevsaantekPers_2008k3 (Pers);

CREATE TABLE Prot.LevsaantekPers_2008k4 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2008-10-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2009-01-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2008k4_TsKlaarzettenLev ON Prot.LevsaantekPers_2008k4 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2008k4_Levsaantek ON Prot.LevsaantekPers_2008k4 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2008k4_Pers ON Prot.LevsaantekPers_2008k4 (Pers);

CREATE TABLE Prot.LevsaantekPers_2009k1 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2009-01-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2009-04-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2009k1_TsKlaarzettenLev ON Prot.LevsaantekPers_2009k1 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2009k1_Levsaantek ON Prot.LevsaantekPers_2009k1 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2009k1_Pers ON Prot.LevsaantekPers_2009k1 (Pers);

CREATE TABLE Prot.LevsaantekPers_2009k2 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2009-04-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2009-07-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2009k2_TsKlaarzettenLev ON Prot.LevsaantekPers_2009k2 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2009k2_Levsaantek ON Prot.LevsaantekPers_2009k2 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2009k2_Pers ON Prot.LevsaantekPers_2009k2 (Pers);

CREATE TABLE Prot.LevsaantekPers_2009k3 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2009-07-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2009-10-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2009k3_TsKlaarzettenLev ON Prot.LevsaantekPers_2009k3 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2009k3_Levsaantek ON Prot.LevsaantekPers_2009k3 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2009k3_Pers ON Prot.LevsaantekPers_2009k3 (Pers);

CREATE TABLE Prot.LevsaantekPers_2009k4 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2009-10-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2010-01-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2009k4_TsKlaarzettenLev ON Prot.LevsaantekPers_2009k4 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2009k4_Levsaantek ON Prot.LevsaantekPers_2009k4 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2009k4_Pers ON Prot.LevsaantekPers_2009k4 (Pers);

CREATE TABLE Prot.LevsaantekPers_2010k1 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2010-01-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2010-04-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2010k1_TsKlaarzettenLev ON Prot.LevsaantekPers_2010k1 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2010k1_Levsaantek ON Prot.LevsaantekPers_2010k1 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2010k1_Pers ON Prot.LevsaantekPers_2010k1 (Pers);

CREATE TABLE Prot.LevsaantekPers_2010k2 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2010-04-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2010-07-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2010k2_TsKlaarzettenLev ON Prot.LevsaantekPers_2010k2 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2010k2_Levsaantek ON Prot.LevsaantekPers_2010k2 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2010k2_Pers ON Prot.LevsaantekPers_2010k2 (Pers);

CREATE TABLE Prot.LevsaantekPers_2010k3 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2010-07-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2010-10-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2010k3_TsKlaarzettenLev ON Prot.LevsaantekPers_2010k3 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2010k3_Levsaantek ON Prot.LevsaantekPers_2010k3 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2010k3_Pers ON Prot.LevsaantekPers_2010k3 (Pers);

CREATE TABLE Prot.LevsaantekPers_2010k4 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2010-10-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2011-01-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2010k4_TsKlaarzettenLev ON Prot.LevsaantekPers_2010k4 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2010k4_Levsaantek ON Prot.LevsaantekPers_2010k4 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2010k4_Pers ON Prot.LevsaantekPers_2010k4 (Pers);

CREATE TABLE Prot.LevsaantekPers_2011k1 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2011-01-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2011-04-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2011k1_TsKlaarzettenLev ON Prot.LevsaantekPers_2011k1 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2011k1_Levsaantek ON Prot.LevsaantekPers_2011k1 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2011k1_Pers ON Prot.LevsaantekPers_2011k1 (Pers);

CREATE TABLE Prot.LevsaantekPers_2011k2 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2011-04-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2011-07-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2011k2_TsKlaarzettenLev ON Prot.LevsaantekPers_2011k2 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2011k2_Levsaantek ON Prot.LevsaantekPers_2011k2 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2011k2_Pers ON Prot.LevsaantekPers_2011k2 (Pers);

CREATE TABLE Prot.LevsaantekPers_2011k3 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2011-07-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2011-10-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2011k3_TsKlaarzettenLev ON Prot.LevsaantekPers_2011k3 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2011k3_Levsaantek ON Prot.LevsaantekPers_2011k3 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2011k3_Pers ON Prot.LevsaantekPers_2011k3 (Pers);

CREATE TABLE Prot.LevsaantekPers_2011k4 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2011-10-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2012-01-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2011k4_TsKlaarzettenLev ON Prot.LevsaantekPers_2011k4 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2011k4_Levsaantek ON Prot.LevsaantekPers_2011k4 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2011k4_Pers ON Prot.LevsaantekPers_2011k4 (Pers);

CREATE TABLE Prot.LevsaantekPers_2012k1 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2012-01-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2012-04-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2012k1_TsKlaarzettenLev ON Prot.LevsaantekPers_2012k1 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2012k1_Levsaantek ON Prot.LevsaantekPers_2012k1 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2012k1_Pers ON Prot.LevsaantekPers_2012k1 (Pers);

CREATE TABLE Prot.LevsaantekPers_2012k2 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2012-04-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2012-07-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2012k2_TsKlaarzettenLev ON Prot.LevsaantekPers_2012k2 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2012k2_Levsaantek ON Prot.LevsaantekPers_2012k2 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2012k2_Pers ON Prot.LevsaantekPers_2012k2 (Pers);

CREATE TABLE Prot.LevsaantekPers_2012k3 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2012-07-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2012-10-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2012k3_TsKlaarzettenLev ON Prot.LevsaantekPers_2012k3 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2012k3_Levsaantek ON Prot.LevsaantekPers_2012k3 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2012k3_Pers ON Prot.LevsaantekPers_2012k3 (Pers);

CREATE TABLE Prot.LevsaantekPers_2012k4 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2012-10-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2013-01-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2012k4_TsKlaarzettenLev ON Prot.LevsaantekPers_2012k4 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2012k4_Levsaantek ON Prot.LevsaantekPers_2012k4 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2012k4_Pers ON Prot.LevsaantekPers_2012k4 (Pers);

CREATE TABLE Prot.LevsaantekPers_2013k1 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2013-01-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2013-04-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2013k1_TsKlaarzettenLev ON Prot.LevsaantekPers_2013k1 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2013k1_Levsaantek ON Prot.LevsaantekPers_2013k1 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2013k1_Pers ON Prot.LevsaantekPers_2013k1 (Pers);

CREATE TABLE Prot.LevsaantekPers_2013k2 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2013-04-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2013-07-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2013k2_TsKlaarzettenLev ON Prot.LevsaantekPers_2013k2 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2013k2_Levsaantek ON Prot.LevsaantekPers_2013k2 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2013k2_Pers ON Prot.LevsaantekPers_2013k2 (Pers);

CREATE TABLE Prot.LevsaantekPers_2013k3 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2013-07-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2013-10-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2013k3_TsKlaarzettenLev ON Prot.LevsaantekPers_2013k3 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2013k3_Levsaantek ON Prot.LevsaantekPers_2013k3 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2013k3_Pers ON Prot.LevsaantekPers_2013k3 (Pers);

CREATE TABLE Prot.LevsaantekPers_2013k4 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2013-10-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2014-01-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2013k4_TsKlaarzettenLev ON Prot.LevsaantekPers_2013k4 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2013k4_Levsaantek ON Prot.LevsaantekPers_2013k4 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2013k4_Pers ON Prot.LevsaantekPers_2013k4 (Pers);

CREATE TABLE Prot.LevsaantekPers_2014k1 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2014-01-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2014-04-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2014k1_TsKlaarzettenLev ON Prot.LevsaantekPers_2014k1 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2014k1_Levsaantek ON Prot.LevsaantekPers_2014k1 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2014k1_Pers ON Prot.LevsaantekPers_2014k1 (Pers);

CREATE TABLE Prot.LevsaantekPers_2014k2 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2014-04-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2014-07-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2014k2_TsKlaarzettenLev ON Prot.LevsaantekPers_2014k2 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2014k2_Levsaantek ON Prot.LevsaantekPers_2014k2 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2014k2_Pers ON Prot.LevsaantekPers_2014k2 (Pers);

CREATE TABLE Prot.LevsaantekPers_2014k3 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2014-07-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2014-10-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2014k3_TsKlaarzettenLev ON Prot.LevsaantekPers_2014k3 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2014k3_Levsaantek ON Prot.LevsaantekPers_2014k3 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2014k3_Pers ON Prot.LevsaantekPers_2014k3 (Pers);

CREATE TABLE Prot.LevsaantekPers_2014k4 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2014-10-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2015-01-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2014k4_TsKlaarzettenLev ON Prot.LevsaantekPers_2014k4 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2014k4_Levsaantek ON Prot.LevsaantekPers_2014k4 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2014k4_Pers ON Prot.LevsaantekPers_2014k4 (Pers);

CREATE TABLE Prot.LevsaantekPers_2015k1 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2015-01-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2015-04-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2015k1_TsKlaarzettenLev ON Prot.LevsaantekPers_2015k1 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2015k1_Levsaantek ON Prot.LevsaantekPers_2015k1 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2015k1_Pers ON Prot.LevsaantekPers_2015k1 (Pers);

CREATE TABLE Prot.LevsaantekPers_2015k2 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2015-04-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2015-07-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2015k2_TsKlaarzettenLev ON Prot.LevsaantekPers_2015k2 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2015k2_Levsaantek ON Prot.LevsaantekPers_2015k2 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2015k2_Pers ON Prot.LevsaantekPers_2015k2 (Pers);

CREATE TABLE Prot.LevsaantekPers_2015k3 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2015-07-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2015-10-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2015k3_TsKlaarzettenLev ON Prot.LevsaantekPers_2015k3 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2015k3_Levsaantek ON Prot.LevsaantekPers_2015k3 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2015k3_Pers ON Prot.LevsaantekPers_2015k3 (Pers);

CREATE TABLE Prot.LevsaantekPers_2015k4 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2015-10-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2016-01-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2015k4_TsKlaarzettenLev ON Prot.LevsaantekPers_2015k4 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2015k4_Levsaantek ON Prot.LevsaantekPers_2015k4 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2015k4_Pers ON Prot.LevsaantekPers_2015k4 (Pers);

CREATE TABLE Prot.LevsaantekPers_2016k1 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2016-01-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2016-04-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2016k1_TsKlaarzettenLev ON Prot.LevsaantekPers_2016k1 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2016k1_Levsaantek ON Prot.LevsaantekPers_2016k1 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2016k1_Pers ON Prot.LevsaantekPers_2016k1 (Pers);

CREATE TABLE Prot.LevsaantekPers_2016k2 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2016-04-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2016-07-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2016k2_TsKlaarzettenLev ON Prot.LevsaantekPers_2016k2 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2016k2_Levsaantek ON Prot.LevsaantekPers_2016k2 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2016k2_Pers ON Prot.LevsaantekPers_2016k2 (Pers);

CREATE TABLE Prot.LevsaantekPers_2016k3 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2016-07-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2016-10-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2016k3_TsKlaarzettenLev ON Prot.LevsaantekPers_2016k3 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2016k3_Levsaantek ON Prot.LevsaantekPers_2016k3 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2016k3_Pers ON Prot.LevsaantekPers_2016k3 (Pers);

CREATE TABLE Prot.LevsaantekPers_2016k4 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2016-10-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2017-01-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2016k4_TsKlaarzettenLev ON Prot.LevsaantekPers_2016k4 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2016k4_Levsaantek ON Prot.LevsaantekPers_2016k4 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2016k4_Pers ON Prot.LevsaantekPers_2016k4 (Pers);

CREATE TABLE Prot.LevsaantekPers_2017k1 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2017-01-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2017-04-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2017k1_TsKlaarzettenLev ON Prot.LevsaantekPers_2017k1 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2017k1_Levsaantek ON Prot.LevsaantekPers_2017k1 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2017k1_Pers ON Prot.LevsaantekPers_2017k1 (Pers);

CREATE TABLE Prot.LevsaantekPers_2017k2 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2017-04-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2017-07-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2017k2_TsKlaarzettenLev ON Prot.LevsaantekPers_2017k2 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2017k2_Levsaantek ON Prot.LevsaantekPers_2017k2 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2017k2_Pers ON Prot.LevsaantekPers_2017k2 (Pers);

CREATE TABLE Prot.LevsaantekPers_2017k3 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2017-07-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2017-10-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2017k3_TsKlaarzettenLev ON Prot.LevsaantekPers_2017k3 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2017k3_Levsaantek ON Prot.LevsaantekPers_2017k3 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2017k3_Pers ON Prot.LevsaantekPers_2017k3 (Pers);

CREATE TABLE Prot.LevsaantekPers_2017k4 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2017-10-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2018-01-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2017k4_TsKlaarzettenLev ON Prot.LevsaantekPers_2017k4 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2017k4_Levsaantek ON Prot.LevsaantekPers_2017k4 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2017k4_Pers ON Prot.LevsaantekPers_2017k4 (Pers);

CREATE TABLE Prot.LevsaantekPers_2018k1 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2018-01-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2018-04-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2018k1_TsKlaarzettenLev ON Prot.LevsaantekPers_2018k1 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2018k1_Levsaantek ON Prot.LevsaantekPers_2018k1 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2018k1_Pers ON Prot.LevsaantekPers_2018k1 (Pers);

CREATE TABLE Prot.LevsaantekPers_2018k2 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2018-04-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2018-07-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2018k2_TsKlaarzettenLev ON Prot.LevsaantekPers_2018k2 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2018k2_Levsaantek ON Prot.LevsaantekPers_2018k2 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2018k2_Pers ON Prot.LevsaantekPers_2018k2 (Pers);

CREATE TABLE Prot.LevsaantekPers_2018k3 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2018-07-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2018-10-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2018k3_TsKlaarzettenLev ON Prot.LevsaantekPers_2018k3 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2018k3_Levsaantek ON Prot.LevsaantekPers_2018k3 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2018k3_Pers ON Prot.LevsaantekPers_2018k3 (Pers);

CREATE TABLE Prot.LevsaantekPers_2018k4 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2018-10-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2019-01-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2018k4_TsKlaarzettenLev ON Prot.LevsaantekPers_2018k4 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2018k4_Levsaantek ON Prot.LevsaantekPers_2018k4 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2018k4_Pers ON Prot.LevsaantekPers_2018k4 (Pers);

CREATE TABLE Prot.LevsaantekPers_2019k1 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2019-01-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2019-04-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2019k1_TsKlaarzettenLev ON Prot.LevsaantekPers_2019k1 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2019k1_Levsaantek ON Prot.LevsaantekPers_2019k1 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2019k1_Pers ON Prot.LevsaantekPers_2019k1 (Pers);

CREATE TABLE Prot.LevsaantekPers_2019k2 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2019-04-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2019-07-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2019k2_TsKlaarzettenLev ON Prot.LevsaantekPers_2019k2 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2019k2_Levsaantek ON Prot.LevsaantekPers_2019k2 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2019k2_Pers ON Prot.LevsaantekPers_2019k2 (Pers);

CREATE TABLE Prot.LevsaantekPers_2019k3 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2019-07-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2019-10-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2019k3_TsKlaarzettenLev ON Prot.LevsaantekPers_2019k3 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2019k3_Levsaantek ON Prot.LevsaantekPers_2019k3 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2019k3_Pers ON Prot.LevsaantekPers_2019k3 (Pers);

CREATE TABLE Prot.LevsaantekPers_2019k4 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2019-10-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2020-01-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2019k4_TsKlaarzettenLev ON Prot.LevsaantekPers_2019k4 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2019k4_Levsaantek ON Prot.LevsaantekPers_2019k4 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2019k4_Pers ON Prot.LevsaantekPers_2019k4 (Pers);

CREATE TABLE Prot.LevsaantekPers_2020k1 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2020-01-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2020-04-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2020k1_TsKlaarzettenLev ON Prot.LevsaantekPers_2020k1 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2020k1_Levsaantek ON Prot.LevsaantekPers_2020k1 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2020k1_Pers ON Prot.LevsaantekPers_2020k1 (Pers);

CREATE TABLE Prot.LevsaantekPers_2020k2 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2020-04-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2020-07-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2020k2_TsKlaarzettenLev ON Prot.LevsaantekPers_2020k2 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2020k2_Levsaantek ON Prot.LevsaantekPers_2020k2 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2020k2_Pers ON Prot.LevsaantekPers_2020k2 (Pers);

CREATE TABLE Prot.LevsaantekPers_2020k3 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2020-07-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2020-10-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2020k3_TsKlaarzettenLev ON Prot.LevsaantekPers_2020k3 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2020k3_Levsaantek ON Prot.LevsaantekPers_2020k3 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2020k3_Pers ON Prot.LevsaantekPers_2020k3 (Pers);

CREATE TABLE Prot.LevsaantekPers_2020k4 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2020-10-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2021-01-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2020k4_TsKlaarzettenLev ON Prot.LevsaantekPers_2020k4 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2020k4_Levsaantek ON Prot.LevsaantekPers_2020k4 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2020k4_Pers ON Prot.LevsaantekPers_2020k4 (Pers);

CREATE TABLE Prot.LevsaantekPers_2021k1 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2021-01-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2021-04-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2021k1_TsKlaarzettenLev ON Prot.LevsaantekPers_2021k1 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2021k1_Levsaantek ON Prot.LevsaantekPers_2021k1 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2021k1_Pers ON Prot.LevsaantekPers_2021k1 (Pers);

CREATE TABLE Prot.LevsaantekPers_2021k2 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2021-04-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2021-07-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2021k2_TsKlaarzettenLev ON Prot.LevsaantekPers_2021k2 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2021k2_Levsaantek ON Prot.LevsaantekPers_2021k2 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2021k2_Pers ON Prot.LevsaantekPers_2021k2 (Pers);

CREATE TABLE Prot.LevsaantekPers_2021k3 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2021-07-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2021-10-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2021k3_TsKlaarzettenLev ON Prot.LevsaantekPers_2021k3 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2021k3_Levsaantek ON Prot.LevsaantekPers_2021k3 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2021k3_Pers ON Prot.LevsaantekPers_2021k3 (Pers);

CREATE TABLE Prot.LevsaantekPers_2021k4 ( 
   CHECK ( TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2021-10-01 00:00:00+0' AND TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2022-01-01 00:00:00+0' )
) INHERITS (Prot.LevsaantekPers) WITH (fillfactor = 100, autovacuum_vacuum_scale_factor = 0.2, autovacuum_vacuum_threshold = 500000, autovacuum_analyze_threshold = 500000);
CREATE INDEX ixLevsaantekPers_2021k4_TsKlaarzettenLev ON Prot.LevsaantekPers_2021k4 USING BRIN (TsKlaarzettenLev);
CREATE INDEX ixLevsaantekPers_2021k4_Levsaantek ON Prot.LevsaantekPers_2021k4 USING BRIN (Levsaantek);
CREATE INDEX ixLevsaantekPers_2021k4_Pers ON Prot.LevsaantekPers_2021k4 (Pers);


CREATE OR REPLACE FUNCTION Prot.LevsaantekPers_insert_function()
RETURNS TRIGGER AS $$
BEGIN
   IF (NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2006-01-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2005 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2006-01-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2006-04-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2006k1 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2006-04-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2006-07-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2006k2 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2006-07-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2006-10-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2006k3 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2006-10-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2007-01-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2006k4 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2007-01-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2007-04-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2007k1 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2007-04-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2007-07-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2007k2 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2007-07-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2007-10-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2007k3 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2007-10-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2008-01-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2007k4 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2008-01-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2008-04-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2008k1 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2008-04-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2008-07-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2008k2 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2008-07-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2008-10-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2008k3 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2008-10-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2009-01-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2008k4 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2009-01-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2009-04-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2009k1 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2009-04-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2009-07-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2009k2 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2009-07-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2009-10-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2009k3 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2009-10-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2010-01-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2009k4 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2010-01-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2010-04-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2010k1 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2010-04-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2010-07-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2010k2 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2010-07-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2010-10-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2010k3 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2010-10-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2011-01-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2010k4 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2011-01-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2011-04-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2011k1 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2011-04-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2011-07-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2011k2 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2011-07-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2011-10-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2011k3 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2011-10-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2012-01-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2011k4 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2012-01-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2012-04-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2012k1 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2012-04-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2012-07-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2012k2 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2012-07-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2012-10-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2012k3 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2012-10-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2013-01-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2012k4 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2013-01-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2013-04-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2013k1 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2013-04-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2013-07-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2013k2 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2013-07-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2013-10-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2013k3 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2013-10-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2014-01-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2013k4 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2014-01-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2014-04-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2014k1 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2014-04-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2014-07-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2014k2 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2014-07-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2014-10-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2014k3 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2014-10-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2015-01-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2014k4 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2015-01-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2015-04-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2015k1 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2015-04-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2015-07-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2015k2 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2015-07-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2015-10-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2015k3 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2015-10-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2016-01-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2015k4 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2016-01-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2016-04-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2016k1 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2016-04-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2016-07-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2016k2 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2016-07-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2016-10-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2016k3 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2016-10-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2017-01-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2016k4 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2017-01-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2017-04-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2017k1 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2017-04-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2017-07-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2017k2 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2017-07-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2017-10-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2017k3 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2017-10-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2018-01-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2017k4 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2018-01-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2018-04-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2018k1 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2018-04-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2018-07-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2018k2 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2018-07-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2018-10-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2018k3 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2018-10-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2019-01-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2018k4 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2019-01-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2019-04-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2019k1 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2019-04-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2019-07-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2019k2 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2019-07-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2019-10-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2019k3 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2019-10-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2020-01-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2019k4 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2020-01-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2020-04-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2020k1 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2020-04-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2020-07-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2020k2 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2020-07-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2020-10-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2020k3 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2020-10-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2021-01-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2020k4 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2021-01-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2021-04-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2021k1 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2021-04-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2021-07-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2021k2 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2021-07-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2021-10-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2021k3 VALUES (NEW.*);
   ELSIF (NEW.TsKlaarzettenLev >= TIMESTAMP WITH TIME ZONE '2021-10-01 00:00:00+0' AND
       NEW.TsKlaarzettenLev < TIMESTAMP WITH TIME ZONE '2022-01-01 00:00:00+0') THEN
      INSERT INTO Prot.LevsaantekPers_2021k4 VALUES (NEW.*);
   ELSE
      RAISE EXCEPTION 'Geen partitie voor LevsaantekPers.TsKlaarzettenLev.';
   END IF;
   RETURN NULL;
END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER LevsaantekPers_insert_trigger
   BEFORE INSERT ON Prot.LevsaantekPers
      FOR EACH ROW EXECUTE PROCEDURE Prot.LevsaantekPers_insert_function();

