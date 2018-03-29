--------------------------------------------------------------------------------
-- PostgreSQL SQL-DDL bestand voor Basisregistratie Personen (BRP)            --
-- PostgreSQL - Bericht Structuur DDL                                         --
--------------------------------------------------------------------------------
-- 
-- Gegenereerd door BRP Meta Register Release 58.
-- Gegenereerd op: vrijdag 19 mei 2017 10:00:47
-- 
--------------------------------------------------------------------------------

-- Schemas ---------------------------------------------------------------------
DROP SCHEMA IF EXISTS Ber CASCADE;
CREATE SCHEMA Ber;

-- Stamtabellen ----------------------------------------------------------------

-- Actual tabellen--------------------------------------------------------------

CREATE SEQUENCE Ber.seq_Ber START WITH 1;
CREATE TABLE Ber.Ber (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Ber.seq_Ber')   /* BerID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   Srt                           Smallint                                                               /* SrtBerID */,
   Richting                      Smallint                      NOT NULL                                 /* RichtingID */,
   ZendendePartij                Smallint                                                               /* PartijID */,
   ZendendeSysteem               Varchar(50)                             CHECK (ZendendeSysteem <> '' )   /* SysteemNaam */,
   OntvangendePartij             Smallint                                                               /* PartijID */,
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
   AdmHnd                        Bigint                                                                 /* AdmHndID */,
   Data                          Text                                                                   /* Berdata */,
   CONSTRAINT pk_Ber PRIMARY KEY (ID)
);
ALTER SEQUENCE Ber.seq_Ber OWNED BY Ber.Ber.ID;

CREATE TABLE Ber.BerPers (
   Ber                           Bigint                        NOT NULL                                 /* BerID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   Pers                          Bigint                        NOT NULL                                 /* PersID */
);

-- Materiele historie tabellen -------------------------------------------------

-- Foreign keys ----------------------------------------------------------------
-- Onderdrukte foreign key: ALTER TABLE Ber.Ber ADD CONSTRAINT fk_Ber_Srt_SrtBer FOREIGN KEY (Srt) REFERENCES Kern.SrtBer (ID);
-- Onderdrukte foreign key: ALTER TABLE Ber.Ber ADD CONSTRAINT fk_Ber_Richting_Richting FOREIGN KEY (Richting) REFERENCES Kern.Richting (ID);
-- Onderdrukte foreign key: ALTER TABLE Ber.Ber ADD CONSTRAINT fk_Ber_ZendendePartij_Partij FOREIGN KEY (ZendendePartij) REFERENCES Kern.Partij (ID);
-- Onderdrukte foreign key: ALTER TABLE Ber.Ber ADD CONSTRAINT fk_Ber_OntvangendePartij_Partij FOREIGN KEY (OntvangendePartij) REFERENCES Kern.Partij (ID);
-- Onderdrukte foreign key: ALTER TABLE Ber.Ber ADD CONSTRAINT fk_Ber_Verwerkingswijze_Verwerkingswijze FOREIGN KEY (Verwerkingswijze) REFERENCES Kern.Verwerkingswijze (ID);
-- Onderdrukte foreign key: ALTER TABLE Ber.Ber ADD CONSTRAINT fk_Ber_Rol_Rol FOREIGN KEY (Rol) REFERENCES Kern.Rol (ID);
-- Onderdrukte foreign key: ALTER TABLE Ber.Ber ADD CONSTRAINT fk_Ber_SrtSynchronisatie_SrtSynchronisatie FOREIGN KEY (SrtSynchronisatie) REFERENCES Kern.SrtSynchronisatie (ID);
-- Onderdrukte foreign key: ALTER TABLE Ber.Ber ADD CONSTRAINT fk_Ber_Levsautorisatie_Levsautorisatie FOREIGN KEY (Levsautorisatie) REFERENCES AutAut.Levsautorisatie (ID);
-- Onderdrukte foreign key: ALTER TABLE Ber.Ber ADD CONSTRAINT fk_Ber_Dienst_Dienst FOREIGN KEY (Dienst) REFERENCES AutAut.Dienst (ID);
-- Onderdrukte foreign key: ALTER TABLE Ber.Ber ADD CONSTRAINT fk_Ber_Verwerking_Verwerkingsresultaat FOREIGN KEY (Verwerking) REFERENCES Kern.Verwerkingsresultaat (ID);
-- Onderdrukte foreign key: ALTER TABLE Ber.Ber ADD CONSTRAINT fk_Ber_Bijhouding_Bijhresultaat FOREIGN KEY (Bijhouding) REFERENCES Kern.Bijhresultaat (ID);
-- Onderdrukte foreign key: ALTER TABLE Ber.Ber ADD CONSTRAINT fk_Ber_HoogsteMeldingsniveau_SrtMelding FOREIGN KEY (HoogsteMeldingsniveau) REFERENCES Kern.SrtMelding (ID);
-- Onderdrukte foreign key: ALTER TABLE Ber.Ber ADD CONSTRAINT fk_Ber_AdmHnd_AdmHnd FOREIGN KEY (AdmHnd) REFERENCES Kern.AdmHnd (ID);
ALTER TABLE Ber.BerPers ADD CONSTRAINT fk_BerPers_Ber_Ber FOREIGN KEY (Ber) REFERENCES Ber.Ber (ID);
-- Onderdrukte foreign key: ALTER TABLE Ber.BerPers ADD CONSTRAINT fk_BerPers_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);

