--------------------------------------------------------------------------------
-- PostgreSQL SQL-DDL bestand voor Basisregistratie Personen (BRP)            --
-- PostgreSQL - Bericht Structuur DDL                                         --
--------------------------------------------------------------------------------
-- 
-- Gegenereerd door BRP Meta Register Release 44.
-- Gegenereerd op: dinsdag 24 nov 2015 13:59:27
-- 
--------------------------------------------------------------------------------

-- Schemas ---------------------------------------------------------------------
DROP SCHEMA IF EXISTS Ber CASCADE;
CREATE SCHEMA Ber;

-- Stamtabellen ----------------------------------------------------------------

CREATE TABLE Ber.Bijhresultaat (
   ID                            Smallint                      NOT NULL                                 /* BijhresultaatID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   CONSTRAINT pk_Bijhresultaat PRIMARY KEY (ID),
   CONSTRAINT uc_Bijhresultaat_Naam UNIQUE (Naam)
);

CREATE TABLE Ber.Bijhsituatie (
   ID                            Smallint                      NOT NULL                                 /* BijhsituatieID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   CONSTRAINT pk_Bijhsituatie PRIMARY KEY (ID),
   CONSTRAINT uc_Bijhsituatie_Naam UNIQUE (Naam)
);

CREATE TABLE Ber.Historievorm (
   ID                            Smallint                      NOT NULL                                 /* HistorievormID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   CONSTRAINT pk_Historievorm PRIMARY KEY (ID),
   CONSTRAINT uc_Historievorm_Naam UNIQUE (Naam)
);

CREATE TABLE Ber.Richting (
   ID                            Smallint                      NOT NULL                                 /* RichtingID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   CONSTRAINT pk_Richting PRIMARY KEY (ID),
   CONSTRAINT uc_Richting_Naam UNIQUE (Naam)
);

CREATE TABLE Ber.SrtBer (
   ID                            Smallint                      NOT NULL                                 /* SrtBerID */,
   Identifier                    Varchar(80)                   NOT NULL  CHECK (Identifier <> '' )      /* IdentifierLang */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Module                        Smallint                                                               /* IDModule */,
   Koppelvlak                    Smallint                      NOT NULL                                 /* KoppelvlakID */,
   DatAanvGel                    Integer                                                                /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   CONSTRAINT pk_SrtBer PRIMARY KEY (ID),
   CONSTRAINT uc_SrtBer_Identifier UNIQUE (Identifier)
);

CREATE TABLE Ber.SrtMelding (
   ID                            Smallint                      NOT NULL                                 /* SrtMeldingID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   CONSTRAINT pk_SrtMelding PRIMARY KEY (ID),
   CONSTRAINT uc_SrtMelding_Naam UNIQUE (Naam)
);

CREATE TABLE Ber.SrtSynchronisatie (
   ID                            Smallint                      NOT NULL                                 /* SrtSynchronisatieID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   CONSTRAINT pk_SrtSynchronisatie PRIMARY KEY (ID),
   CONSTRAINT uc_SrtSynchronisatie_Naam UNIQUE (Naam)
);

CREATE TABLE Ber.Verwerkingsresultaat (
   ID                            Smallint                      NOT NULL                                 /* VerwerkingsresultaatID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   CONSTRAINT pk_Verwerkingsresultaat PRIMARY KEY (ID),
   CONSTRAINT uc_Verwerkingsresultaat_Naam UNIQUE (Naam)
);

CREATE TABLE Ber.Verwerkingswijze (
   ID                            Smallint                      NOT NULL                                 /* VerwerkingswijzeID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   CONSTRAINT pk_Verwerkingswijze PRIMARY KEY (ID),
   CONSTRAINT uc_Verwerkingswijze_Naam UNIQUE (Naam)
);

-- Actual tabellen--------------------------------------------------------------

CREATE SEQUENCE Ber.seq_Ber START WITH 1;
CREATE TABLE Ber.Ber (
   ID                            BigInt                        NOT NULL  DEFAULT nextval('Ber.seq_Ber')   /* BerID */,
   Srt                           Smallint                                                               /* SrtBerID */,
   Richting                      Smallint                      NOT NULL                                 /* RichtingID */,
   ZendendePartij                Smallint                                                               /* PartijID */,
   ZendendeSysteem               Varchar(50)                             CHECK (ZendendeSysteem <> '' )   /* SysteemNaam */,
   OntvangendePartij             Smallint                                                               /* PartijID */,
   OntvangendeSysteem            Varchar(50)                             CHECK (OntvangendeSysteem <> '' )   /* SysteemNaam */,
   Referentienr                  Varchar(36)                             CHECK (Referentienr <> '' )    /* Referentienr */,
   CrossReferentienr             Varchar(36)                             CHECK (CrossReferentienr <> '' )   /* Referentienr */,
   TsVerzending                  Timestamp with time zone                                               /* Ts */,
   TsOntv                        Timestamp with time zone                                               /* Ts */,
   Verwerkingswijze              Smallint                                                               /* VerwerkingswijzeID */,
   Rol                           Smallint                                                               /* RolID */,
   SrtSynchronisatie             Smallint                                                               /* SrtSynchronisatieID */,
   Levsautorisatie               Integer                                                                /* LevsautorisatieID */,
   Dienst                        Integer                                                                /* DienstID */,
   Verwerking                    Smallint                                                               /* VerwerkingsresultaatID */,
   Bijhouding                    Smallint                                                               /* BijhresultaatID */,
   HoogsteMeldingsniveau         Smallint                                                               /* SrtMeldingID */,
   AdmHnd                        BigInt                                                                 /* AdmHndID */,
   Data                          Text                                                                   /* Berdata */,
   AntwoordOp                    BigInt                                                                 /* BerID */,
   CONSTRAINT pk_Ber PRIMARY KEY (ID)
);
ALTER SEQUENCE Ber.seq_Ber OWNED BY Ber.Ber.ID;

CREATE SEQUENCE Ber.seq_BerPers START WITH 1;
CREATE TABLE Ber.BerPers (
   ID                            BigInt                        NOT NULL  DEFAULT nextval('Ber.seq_BerPers')   /* BerPersID */,
   Ber                           BigInt                        NOT NULL                                 /* BerID */,
   Pers                          Integer                       NOT NULL                                 /* PersID */,
   CONSTRAINT pk_BerPers PRIMARY KEY (ID)
);
ALTER SEQUENCE Ber.seq_BerPers OWNED BY Ber.BerPers.ID;

-- Materiele historie tabellen -------------------------------------------------

-- Foreign keys ----------------------------------------------------------------
-- Onderdrukte foreign key: ALTER TABLE Ber.SrtBer ADD CONSTRAINT fk_SrtBer_Module_BurgerzakenModule FOREIGN KEY (Module) REFERENCES Kern.BurgerzakenModule (ID);
-- Onderdrukte foreign key: ALTER TABLE Ber.SrtBer ADD CONSTRAINT fk_SrtBer_Koppelvlak_Koppelvlak FOREIGN KEY (Koppelvlak) REFERENCES Kern.Koppelvlak (ID);
ALTER TABLE Ber.Ber ADD CONSTRAINT fk_Ber_Srt_SrtBer FOREIGN KEY (Srt) REFERENCES Ber.SrtBer (ID);
ALTER TABLE Ber.Ber ADD CONSTRAINT fk_Ber_Richting_Richting FOREIGN KEY (Richting) REFERENCES Ber.Richting (ID);
-- Onderdrukte foreign key: ALTER TABLE Ber.Ber ADD CONSTRAINT fk_Ber_ZendendePartij_Partij FOREIGN KEY (ZendendePartij) REFERENCES Kern.Partij (ID);
-- Onderdrukte foreign key: ALTER TABLE Ber.Ber ADD CONSTRAINT fk_Ber_OntvangendePartij_Partij FOREIGN KEY (OntvangendePartij) REFERENCES Kern.Partij (ID);
ALTER TABLE Ber.Ber ADD CONSTRAINT fk_Ber_Verwerkingswijze_Verwerkingswijze FOREIGN KEY (Verwerkingswijze) REFERENCES Ber.Verwerkingswijze (ID);
-- Onderdrukte foreign key: ALTER TABLE Ber.Ber ADD CONSTRAINT fk_Ber_Rol_Rol FOREIGN KEY (Rol) REFERENCES Kern.Rol (ID);
ALTER TABLE Ber.Ber ADD CONSTRAINT fk_Ber_SrtSynchronisatie_SrtSynchronisatie FOREIGN KEY (SrtSynchronisatie) REFERENCES Ber.SrtSynchronisatie (ID);
-- Onderdrukte foreign key: ALTER TABLE Ber.Ber ADD CONSTRAINT fk_Ber_Levsautorisatie_Levsautorisatie FOREIGN KEY (Levsautorisatie) REFERENCES AutAut.Levsautorisatie (ID);
-- Onderdrukte foreign key: ALTER TABLE Ber.Ber ADD CONSTRAINT fk_Ber_Dienst_Dienst FOREIGN KEY (Dienst) REFERENCES AutAut.Dienst (ID);
ALTER TABLE Ber.Ber ADD CONSTRAINT fk_Ber_Verwerking_Verwerkingsresultaat FOREIGN KEY (Verwerking) REFERENCES Ber.Verwerkingsresultaat (ID);
ALTER TABLE Ber.Ber ADD CONSTRAINT fk_Ber_Bijhouding_Bijhresultaat FOREIGN KEY (Bijhouding) REFERENCES Ber.Bijhresultaat (ID);
ALTER TABLE Ber.Ber ADD CONSTRAINT fk_Ber_HoogsteMeldingsniveau_SrtMelding FOREIGN KEY (HoogsteMeldingsniveau) REFERENCES Ber.SrtMelding (ID);
-- Onderdrukte foreign key: ALTER TABLE Ber.Ber ADD CONSTRAINT fk_Ber_AdmHnd_AdmHnd FOREIGN KEY (AdmHnd) REFERENCES Kern.AdmHnd (ID);
ALTER TABLE Ber.Ber ADD CONSTRAINT fk_Ber_AntwoordOp_Ber FOREIGN KEY (AntwoordOp) REFERENCES Ber.Ber (ID);
ALTER TABLE Ber.BerPers ADD CONSTRAINT fk_BerPers_Ber_Ber FOREIGN KEY (Ber) REFERENCES Ber.Ber (ID);
-- Onderdrukte foreign key: ALTER TABLE Ber.BerPers ADD CONSTRAINT fk_BerPers_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);

