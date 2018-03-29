--------------------------------------------------------------------------------
-- PostgreSQL SQL-DDL bestand voor Basisregistratie Personen (BRP)            --
-- PostgreSQL - Protocol Structuur DDL                                        --
--------------------------------------------------------------------------------
-- 
-- Versie: Release 59
-- Laatste wijziging: donderdag 10 aug 2017 09:46:45
-- 
--------------------------------------------------------------------------------

-- Schemas ---------------------------------------------------------------------
DROP SCHEMA IF EXISTS Prot CASCADE;
CREATE SCHEMA Prot;

-- Stamtabellen ----------------------------------------------------------------

-- Actual tabellen--------------------------------------------------------------

CREATE SEQUENCE Prot.seq_Levsaantek START WITH 1;
CREATE TABLE Prot.Levsaantek (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Prot.seq_Levsaantek')   /* LevsaantekID */,
   ToegangLevsautorisatie        Integer                       NOT NULL                                 /* ToegangLevsautorisatieID */,
   Dienst                        Integer                       NOT NULL                                 /* DienstID */,
   TsKlaarzettenLev              Timestamp with time zone      NOT NULL                                 /* Ts */,
   DatAanvMaterielePeriodeRes    Integer                                 CHECK (to_char(to_date(DatAanvMaterielePeriodeRes::text, 'yyyymmdd'), 'yyyymmdd')=DatAanvMaterielePeriodeRes::text)   /* Dat */,
   DatEindeMaterielePeriodeRes   Integer                                 CHECK (to_char(to_date(DatEindeMaterielePeriodeRes::text, 'yyyymmdd'), 'yyyymmdd')=DatEindeMaterielePeriodeRes::text)   /* Dat */,
   TsAanvFormelePeriodeRes       Timestamp with time zone                                               /* Ts */,
   TsEindeFormelePeriodeRes      Timestamp with time zone      NOT NULL                                 /* Ts */,
   AdmHnd                        Bigint                                                                 /* AdmHndID */,
   SrtSynchronisatie             Smallint                                                               /* SrtSynchronisatieID */,
   ScopePatroon                  Integer                                                                /* ScopePatroonID */,
   CONSTRAINT pk_Levsaantek PRIMARY KEY (ID)
);
ALTER SEQUENCE Prot.seq_Levsaantek OWNED BY Prot.Levsaantek.ID;

CREATE TABLE Prot.LevsaantekPers (
   Levsaantek                    Bigint                        NOT NULL                                 /* LevsaantekID */,
   TsKlaarzettenLev              Timestamp with time zone      NOT NULL                                 /* Ts */,
   Pers                          Bigint                        NOT NULL                                 /* PersID */,
   DatAanvMaterielePeriode       Integer                                 CHECK (to_char(to_date(DatAanvMaterielePeriode::text, 'yyyymmdd'), 'yyyymmdd')=DatAanvMaterielePeriode::text)   /* Dat */,
   TsLaatsteWijzPers             Timestamp with time zone      NOT NULL                                 /* Ts */
);

CREATE SEQUENCE Prot.seq_ScopePatroon START WITH 1;
CREATE TABLE Prot.ScopePatroon (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Prot.seq_ScopePatroon')   /* ScopePatroonID */,
   CONSTRAINT pk_ScopePatroon PRIMARY KEY (ID)
);
ALTER SEQUENCE Prot.seq_ScopePatroon OWNED BY Prot.ScopePatroon.ID;

CREATE SEQUENCE Prot.seq_ScopePatroonElement START WITH 1;
CREATE TABLE Prot.ScopePatroonElement (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Prot.seq_ScopePatroonElement')   /* ScopePatroonElementID */,
   ScopePatroon                  Integer                       NOT NULL                                 /* ScopePatroonID */,
   Element                       Integer                       NOT NULL                                 /* ElementID */,
   CONSTRAINT pk_ScopePatroonElement PRIMARY KEY (ID),
   CONSTRAINT uc_ScopePatroonElement_ScopePatroon_Element UNIQUE (ScopePatroon, Element)
);
ALTER SEQUENCE Prot.seq_ScopePatroonElement OWNED BY Prot.ScopePatroonElement.ID;

-- Materiele historie tabellen -------------------------------------------------

-- Foreign keys ----------------------------------------------------------------
-- Onderdrukte foreign key: ALTER TABLE Prot.Levsaantek ADD CONSTRAINT fk_Levsaantek_ToegangLevsautorisatie_ToegangLevsautorisatie FOREIGN KEY (ToegangLevsautorisatie) REFERENCES AutAut.ToegangLevsautorisatie (ID);
-- Onderdrukte foreign key: ALTER TABLE Prot.Levsaantek ADD CONSTRAINT fk_Levsaantek_Dienst_Dienst FOREIGN KEY (Dienst) REFERENCES AutAut.Dienst (ID);
-- Onderdrukte foreign key: ALTER TABLE Prot.Levsaantek ADD CONSTRAINT fk_Levsaantek_AdmHnd_AdmHnd FOREIGN KEY (AdmHnd) REFERENCES Kern.AdmHnd (ID);
-- Onderdrukte foreign key: ALTER TABLE Prot.Levsaantek ADD CONSTRAINT fk_Levsaantek_SrtSynchronisatie_SrtSynchronisatie FOREIGN KEY (SrtSynchronisatie) REFERENCES Kern.SrtSynchronisatie (ID);
ALTER TABLE Prot.Levsaantek ADD CONSTRAINT fk_Levsaantek_ScopePatroon_ScopePatroon FOREIGN KEY (ScopePatroon) REFERENCES Prot.ScopePatroon (ID);
ALTER TABLE Prot.LevsaantekPers ADD CONSTRAINT fk_LevsaantekPers_Levsaantek_Levsaantek FOREIGN KEY (Levsaantek) REFERENCES Prot.Levsaantek (ID);
-- Onderdrukte foreign key: ALTER TABLE Prot.LevsaantekPers ADD CONSTRAINT fk_LevsaantekPers_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Prot.ScopePatroonElement ADD CONSTRAINT fk_ScopePatroonElement_ScopePatroon_ScopePatroon FOREIGN KEY (ScopePatroon) REFERENCES Prot.ScopePatroon (ID);
-- Onderdrukte foreign key: ALTER TABLE Prot.ScopePatroonElement ADD CONSTRAINT fk_ScopePatroonElement_Element_Element FOREIGN KEY (Element) REFERENCES Kern.Element (ID);

