--------------------------------------------------------------------------------
-- PostgreSQL SQL-DDL bestand voor Basisregistratie Personen (BRP)            --
-- PostgreSQL - Protocol Structuur DDL                                        --
--------------------------------------------------------------------------------
-- 
-- Gegenereerd door BRP Meta Register Release 44.
-- Gegenereerd op: dinsdag 24 nov 2015 13:59:27
-- 
--------------------------------------------------------------------------------

-- Schemas ---------------------------------------------------------------------
DROP SCHEMA IF EXISTS Prot CASCADE;
CREATE SCHEMA Prot;

-- Stamtabellen ----------------------------------------------------------------

-- Actual tabellen--------------------------------------------------------------

CREATE SEQUENCE Prot.seq_Levsaantek START WITH 1;
CREATE TABLE Prot.Levsaantek (
   ID                            BigInt                        NOT NULL  DEFAULT nextval('Prot.seq_Levsaantek')   /* LevsaantekID */,
   ToegangLevsautorisatie        Integer                       NOT NULL                                 /* ToegangLevsautorisatieID */,
   Dienst                        Integer                       NOT NULL                                 /* DienstID */,
   TsKlaarzettenLev              Timestamp with time zone      NOT NULL                                 /* Ts */,
   DatMaterieelSelectie          Integer                                                                /* Dat */,
   DatAanvMaterielePeriodeRes    Integer                                                                /* Dat */,
   DatEindeMaterielePeriodeRes   Integer                                                                /* Dat */,
   TsAanvFormelePeriodeRes       Timestamp with time zone                                               /* Ts */,
   TsEindeFormelePeriodeRes      Timestamp with time zone      NOT NULL                                 /* Ts */,
   AdmHnd                        BigInt                                                                 /* AdmHndID */,
   SrtSynchronisatie             Smallint                                                               /* SrtSynchronisatieID */,
   CONSTRAINT pk_Levsaantek PRIMARY KEY (ID)
);
ALTER SEQUENCE Prot.seq_Levsaantek OWNED BY Prot.Levsaantek.ID;

CREATE SEQUENCE Prot.seq_LevsaantekPers START WITH 1;
CREATE TABLE Prot.LevsaantekPers (
   ID                            BigInt                        NOT NULL  DEFAULT nextval('Prot.seq_LevsaantekPers')   /* LevsaantekPersID */,
   Levsaantek                    BigInt                        NOT NULL                                 /* LevsaantekID */,
   Pers                          Integer                       NOT NULL                                 /* PersID */,
   CONSTRAINT pk_LevsaantekPers PRIMARY KEY (ID),
   CONSTRAINT uc_LevsaantekPers_Levsaantek_Pers UNIQUE (Levsaantek, Pers)
);
ALTER SEQUENCE Prot.seq_LevsaantekPers OWNED BY Prot.LevsaantekPers.ID;

-- Materiele historie tabellen -------------------------------------------------

-- Foreign keys ----------------------------------------------------------------
-- Onderdrukte foreign key: ALTER TABLE Prot.Levsaantek ADD CONSTRAINT fk_Levsaantek_ToegangLevsautorisatie_ToegangLevsautorisatie FOREIGN KEY (ToegangLevsautorisatie) REFERENCES AutAut.ToegangLevsautorisatie (ID);
-- Onderdrukte foreign key: ALTER TABLE Prot.Levsaantek ADD CONSTRAINT fk_Levsaantek_Dienst_Dienst FOREIGN KEY (Dienst) REFERENCES AutAut.Dienst (ID);
-- Onderdrukte foreign key: ALTER TABLE Prot.Levsaantek ADD CONSTRAINT fk_Levsaantek_AdmHnd_AdmHnd FOREIGN KEY (AdmHnd) REFERENCES Kern.AdmHnd (ID);
-- Onderdrukte foreign key: ALTER TABLE Prot.Levsaantek ADD CONSTRAINT fk_Levsaantek_SrtSynchronisatie_SrtSynchronisatie FOREIGN KEY (SrtSynchronisatie) REFERENCES Ber.SrtSynchronisatie (ID);
ALTER TABLE Prot.LevsaantekPers ADD CONSTRAINT fk_LevsaantekPers_Levsaantek_Levsaantek FOREIGN KEY (Levsaantek) REFERENCES Prot.Levsaantek (ID);
-- Onderdrukte foreign key: ALTER TABLE Prot.LevsaantekPers ADD CONSTRAINT fk_LevsaantekPers_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);

