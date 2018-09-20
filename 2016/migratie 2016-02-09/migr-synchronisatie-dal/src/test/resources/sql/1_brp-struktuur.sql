
-- SQL script voor het aanmaken van de BRP database.
-- Dit script is gegenereerd op Thu Nov 26 10:41:23 CET 2015
--


-- SQL inhoud van bestand: bmr/Kern/Kern_BRP_structuur.sql
--------------------------------------------------------------------------------
-- PostgreSQL SQL-DDL bestand voor Basisregistratie Personen (BRP)            --
-- PostgreSQL - Kern Structuur DDL                                            --
--------------------------------------------------------------------------------
--
-- Gegenereerd door BRP Meta Register Release 44.
-- Gegenereerd op: dinsdag 24 nov 2015 13:59:27
--
--------------------------------------------------------------------------------

-- Schemas ---------------------------------------------------------------------
DROP SCHEMA IF EXISTS AutAut CASCADE;
CREATE SCHEMA AutAut;
DROP SCHEMA IF EXISTS BRM CASCADE;
CREATE SCHEMA BRM;
DROP SCHEMA IF EXISTS Conv CASCADE;
CREATE SCHEMA Conv;
DROP SCHEMA IF EXISTS IST CASCADE;
CREATE SCHEMA IST;
DROP SCHEMA IF EXISTS Kern CASCADE;
CREATE SCHEMA Kern;
DROP SCHEMA IF EXISTS MigBlok CASCADE;
CREATE SCHEMA MigBlok;
DROP SCHEMA IF EXISTS VerConv CASCADE;
CREATE SCHEMA VerConv;

-- Stamtabellen ----------------------------------------------------------------
CREATE SEQUENCE AutAut.seq_BijhautorisatieSrtAdmHnd START WITH 1;
CREATE TABLE AutAut.BijhautorisatieSrtAdmHnd (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_BijhautorisatieSrtAdmHnd')   /* BijhautorisatieSrtAdmHndID */,
   ToegangBijhautorisatie        Integer                       NOT NULL                                 /* ToegangBijhautorisatieID */,
   SrtAdmHnd                     Smallint                      NOT NULL                                 /* SrtAdmHndID */,
   CONSTRAINT pk_BijhautorisatieSrtAdmHnd PRIMARY KEY (ID),
   CONSTRAINT uc_BijhautorisatieSrtAdmHnd_ToegangBijhautorisatie_SrtAdmHnd UNIQUE (ToegangBijhautorisatie, SrtAdmHnd)
);
ALTER SEQUENCE AutAut.seq_BijhautorisatieSrtAdmHnd OWNED BY AutAut.BijhautorisatieSrtAdmHnd.ID;

CREATE SEQUENCE AutAut.seq_Dienst START WITH 1;
CREATE TABLE AutAut.Dienst (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_Dienst')   /* DienstID */,
   Dienstbundel                  Integer                       NOT NULL                                 /* DienstbundelID */,
   Srt                           Smallint                      NOT NULL                                 /* SrtDienstID */,
   EffectAfnemerindicaties       Smallint                                                               /* EffectAfnemerindicatiesID */,
   DatIngang                     Integer                                                                /* Dat */,
   DatEinde                      Integer                                                                /* Dat */,
   IndBlok                       Boolean                                 CHECK (IndBlok IN (true))      /* Ja */,
   Attenderingscriterium         Text                                                                   /* Attenderingscriterium */,
   EersteSelectiedat             Integer                                                                /* Dat */,
   SelectieperiodeInMaanden      Smallint                                                               /* SelectieperiodeInMaanden */,
   CONSTRAINT pk_Dienst PRIMARY KEY (ID)
);
ALTER SEQUENCE AutAut.seq_Dienst OWNED BY AutAut.Dienst.ID;

CREATE SEQUENCE AutAut.seq_Dienstbundel START WITH 1;
CREATE TABLE AutAut.Dienstbundel (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_Dienstbundel')   /* DienstbundelID */,
   Levsautorisatie               Integer                       NOT NULL                                 /* LevsautorisatieID */,
   Naam                          Varchar(80)                             CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   DatIngang                     Integer                                                                /* Dat */,
   DatEinde                      Integer                                                                /* Dat */,
   NaderePopulatiebeperking      Text                                                                   /* Populatiebeperking */,
   IndNaderePopbeperkingVolConv  Boolean                                 CHECK (IndNaderePopbeperkingVolConv IN (false))   /* Nee */,
   Toelichting                   Text                                                                   /* OnbeperkteOms */,
   IndBlok                       Boolean                                 CHECK (IndBlok IN (true))      /* Ja */,
   CONSTRAINT pk_Dienstbundel PRIMARY KEY (ID)
);
ALTER SEQUENCE AutAut.seq_Dienstbundel OWNED BY AutAut.Dienstbundel.ID;

CREATE SEQUENCE AutAut.seq_DienstbundelGroep START WITH 1;
CREATE TABLE AutAut.DienstbundelGroep (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_DienstbundelGroep')   /* DienstbundelGroepID */,
   Dienstbundel                  Integer                       NOT NULL                                 /* DienstbundelID */,
   Groep                         Integer                       NOT NULL                                 /* ElementID */,
   IndFormeleHistorie            Boolean                                                                /* JaNee */,
   IndMaterieleHistorie          Boolean                                                                /* JaNee */,
   IndVerantwoording             Boolean                                                                /* JaNee */,
   CONSTRAINT pk_DienstbundelGroep PRIMARY KEY (ID),
   CONSTRAINT uc_DienstbundelGroep_Dienstbundel_Groep UNIQUE (Dienstbundel, Groep)
);
ALTER SEQUENCE AutAut.seq_DienstbundelGroep OWNED BY AutAut.DienstbundelGroep.ID;

CREATE SEQUENCE AutAut.seq_DienstbundelGroepAttr START WITH 1;
CREATE TABLE AutAut.DienstbundelGroepAttr (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_DienstbundelGroepAttr')   /* DienstbundelGroepAttrID */,
   DienstbundelGroep             Integer                       NOT NULL                                 /* DienstbundelGroepID */,
   Attr                          Integer                       NOT NULL                                 /* ElementID */,
   CONSTRAINT pk_DienstbundelGroepAttr PRIMARY KEY (ID),
   CONSTRAINT uc_DienstbundelGroepAttr_DienstbundelGroep_Attr UNIQUE (DienstbundelGroep, Attr)
);
ALTER SEQUENCE AutAut.seq_DienstbundelGroepAttr OWNED BY AutAut.DienstbundelGroepAttr.ID;

CREATE SEQUENCE AutAut.seq_DienstbundelLO3Rubriek START WITH 1;
CREATE TABLE AutAut.DienstbundelLO3Rubriek (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_DienstbundelLO3Rubriek')   /* DienstbundelLO3RubriekID */,
   Dienstbundel                  Integer                       NOT NULL                                 /* DienstbundelID */,
   Rubr                          Integer                       NOT NULL                                 /* ConvLO3RubriekID */,
   CONSTRAINT pk_DienstbundelLO3Rubriek PRIMARY KEY (ID),
   CONSTRAINT uc_DienstbundelLO3Rubriek_Dienstbundel_Rubr UNIQUE (Dienstbundel, Rubr)
);
ALTER SEQUENCE AutAut.seq_DienstbundelLO3Rubriek OWNED BY AutAut.DienstbundelLO3Rubriek.ID;

CREATE SEQUENCE AutAut.seq_EffectAfnemerindicaties START WITH 1;
CREATE TABLE AutAut.EffectAfnemerindicaties (
   ID                            Smallint                      NOT NULL  DEFAULT nextval('AutAut.seq_EffectAfnemerindicaties')   /* EffectAfnemerindicatiesID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   CONSTRAINT pk_EffectAfnemerindicaties PRIMARY KEY (ID),
   CONSTRAINT uc_EffectAfnemerindicaties_Naam UNIQUE (Naam)
);
ALTER SEQUENCE AutAut.seq_EffectAfnemerindicaties OWNED BY AutAut.EffectAfnemerindicaties.ID;

CREATE SEQUENCE AutAut.seq_His_Dienst START WITH 1;
CREATE TABLE AutAut.His_Dienst (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_His_Dienst')   /* His_DienstID */,
   Dienst                        Integer                       NOT NULL                                 /* DienstID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                                                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   DatIngang                     Integer                       NOT NULL                                 /* Dat */,
   DatEinde                      Integer                                                                /* Dat */,
   IndBlok                       Boolean                                 CHECK (IndBlok IN (true))      /* Ja */,
   CONSTRAINT pk_His_Dienst PRIMARY KEY (ID)
);
ALTER SEQUENCE AutAut.seq_His_Dienst OWNED BY AutAut.His_Dienst.ID;

CREATE SEQUENCE AutAut.seq_His_DienstAttendering START WITH 1;
CREATE TABLE AutAut.His_DienstAttendering (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_His_DienstAttendering')   /* His_DienstAttenderingID */,
   Dienst                        Integer                       NOT NULL                                 /* DienstID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                                                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   Attenderingscriterium         Text                                                                   /* Attenderingscriterium */,
   CONSTRAINT pk_His_DienstAttendering PRIMARY KEY (ID)
);
ALTER SEQUENCE AutAut.seq_His_DienstAttendering OWNED BY AutAut.His_DienstAttendering.ID;

CREATE SEQUENCE AutAut.seq_His_DienstSelectie START WITH 1;
CREATE TABLE AutAut.His_DienstSelectie (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_His_DienstSelectie')   /* His_DienstSelectieID */,
   Dienst                        Integer                       NOT NULL                                 /* DienstID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                                                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   EersteSelectiedat             Integer                                                                /* Dat */,
   SelectieperiodeInMaanden      Smallint                                                               /* SelectieperiodeInMaanden */,
   CONSTRAINT pk_His_DienstSelectie PRIMARY KEY (ID)
);
ALTER SEQUENCE AutAut.seq_His_DienstSelectie OWNED BY AutAut.His_DienstSelectie.ID;

CREATE SEQUENCE AutAut.seq_His_Dienstbundel START WITH 1;
CREATE TABLE AutAut.His_Dienstbundel (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_His_Dienstbundel')   /* His_DienstbundelID */,
   Dienstbundel                  Integer                       NOT NULL                                 /* DienstbundelID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                                                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   DatIngang                     Integer                       NOT NULL                                 /* Dat */,
   DatEinde                      Integer                                                                /* Dat */,
   NaderePopulatiebeperking      Text                                                                   /* Populatiebeperking */,
   IndNaderePopbeperkingVolConv  Boolean                                 CHECK (IndNaderePopbeperkingVolConv IN (false))   /* Nee */,
   Toelichting                   Text                                                                   /* OnbeperkteOms */,
   IndBlok                       Boolean                                 CHECK (IndBlok IN (true))      /* Ja */,
   CONSTRAINT pk_His_Dienstbundel PRIMARY KEY (ID)
);
ALTER SEQUENCE AutAut.seq_His_Dienstbundel OWNED BY AutAut.His_Dienstbundel.ID;

CREATE SEQUENCE AutAut.seq_His_DienstbundelGroep START WITH 1;
CREATE TABLE AutAut.His_DienstbundelGroep (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_His_DienstbundelGroep')   /* His_DienstbundelGroepID */,
   DienstbundelGroep             Integer                       NOT NULL                                 /* DienstbundelGroepID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                                                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   IndFormeleHistorie            Boolean                       NOT NULL                                 /* JaNee */,
   IndMaterieleHistorie          Boolean                       NOT NULL                                 /* JaNee */,
   IndVerantwoording             Boolean                       NOT NULL                                 /* JaNee */,
   CONSTRAINT pk_His_DienstbundelGroep PRIMARY KEY (ID)
);
ALTER SEQUENCE AutAut.seq_His_DienstbundelGroep OWNED BY AutAut.His_DienstbundelGroep.ID;

CREATE SEQUENCE AutAut.seq_His_DienstbundelGroepAttr START WITH 1;
CREATE TABLE AutAut.His_DienstbundelGroepAttr (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_His_DienstbundelGroepAttr')   /* His_DienstbundelGroepAttrID */,
   DienstbundelGroepAttr         Integer                       NOT NULL                                 /* DienstbundelGroepAttrID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                                                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   CONSTRAINT pk_His_DienstbundelGroepAttr PRIMARY KEY (ID)
);
ALTER SEQUENCE AutAut.seq_His_DienstbundelGroepAttr OWNED BY AutAut.His_DienstbundelGroepAttr.ID;

CREATE SEQUENCE AutAut.seq_His_DienstbundelLO3Rubriek START WITH 1;
CREATE TABLE AutAut.His_DienstbundelLO3Rubriek (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_His_DienstbundelLO3Rubriek')   /* His_DienstbundelLO3RubriekID */,
   DienstbundelLO3Rubriek        Integer                       NOT NULL                                 /* DienstbundelLO3RubriekID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                                                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   CONSTRAINT pk_His_DienstbundelLO3Rubriek PRIMARY KEY (ID)
);
ALTER SEQUENCE AutAut.seq_His_DienstbundelLO3Rubriek OWNED BY AutAut.His_DienstbundelLO3Rubriek.ID;

CREATE SEQUENCE AutAut.seq_His_Levsautorisatie START WITH 1;
CREATE TABLE AutAut.His_Levsautorisatie (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_His_Levsautorisatie')   /* His_LevsautorisatieID */,
   Levsautorisatie               Integer                       NOT NULL                                 /* LevsautorisatieID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                                                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Protocolleringsniveau         Smallint                      NOT NULL                                 /* ProtocolleringsniveauID */,
   IndAliasSrtAdmHndLeveren      Boolean                       NOT NULL                                 /* JaNee */,
   DatIngang                     Integer                       NOT NULL                                 /* Dat */,
   DatEinde                      Integer                                                                /* Dat */,
   Populatiebeperking            Text                                                                   /* Populatiebeperking */,
   IndPopbeperkingVolConv        Boolean                                 CHECK (IndPopbeperkingVolConv IN (false))   /* Nee */,
   Toelichting                   Text                                                                   /* OnbeperkteOms */,
   IndBlok                       Boolean                                 CHECK (IndBlok IN (true))      /* Ja */,
   CONSTRAINT pk_His_Levsautorisatie PRIMARY KEY (ID)
);
ALTER SEQUENCE AutAut.seq_His_Levsautorisatie OWNED BY AutAut.His_Levsautorisatie.ID;

CREATE SEQUENCE AutAut.seq_His_PartijFiatuitz START WITH 1;
CREATE TABLE AutAut.His_PartijFiatuitz (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_His_PartijFiatuitz')   /* His_PartijFiatuitzID */,
   PartijFiatuitz                Integer                       NOT NULL                                 /* PartijFiatuitzID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                                                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   DatIngang                     Integer                       NOT NULL                                 /* Dat */,
   DatEinde                      Integer                                                                /* Dat */,
   PartijBijhvoorstel            Smallint                                                               /* PartijID */,
   SrtDoc                        Smallint                                                               /* SrtDocID */,
   SrtAdmHnd                     Smallint                                                               /* SrtAdmHndID */,
   IndBlok                       Boolean                                 CHECK (IndBlok IN (true))      /* Ja */,
   CONSTRAINT pk_His_PartijFiatuitz PRIMARY KEY (ID)
);
ALTER SEQUENCE AutAut.seq_His_PartijFiatuitz OWNED BY AutAut.His_PartijFiatuitz.ID;

CREATE SEQUENCE AutAut.seq_His_ToegangBijhautorisatie START WITH 1;
CREATE TABLE AutAut.His_ToegangBijhautorisatie (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_His_ToegangBijhautorisatie')   /* His_ToegangBijhautorisatieID */,
   ToegangBijhautorisatie        Integer                       NOT NULL                                 /* ToegangBijhautorisatieID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                                                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   DatIngang                     Integer                       NOT NULL                                 /* Dat */,
   DatEinde                      Integer                                                                /* Dat */,
   IndBlok                       Boolean                                 CHECK (IndBlok IN (true))      /* Ja */,
   CONSTRAINT pk_His_ToegangBijhautorisatie PRIMARY KEY (ID)
);
ALTER SEQUENCE AutAut.seq_His_ToegangBijhautorisatie OWNED BY AutAut.His_ToegangBijhautorisatie.ID;

CREATE SEQUENCE AutAut.seq_His_ToegangLevsautorisatie START WITH 1;
CREATE TABLE AutAut.His_ToegangLevsautorisatie (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_His_ToegangLevsautorisatie')   /* His_ToegangLevsautorisatieID */,
   ToegangLevsautorisatie        Integer                       NOT NULL                                 /* ToegangLevsautorisatieID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                                                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   DatIngang                     Integer                       NOT NULL                                 /* Dat */,
   DatEinde                      Integer                                                                /* Dat */,
   NaderePopulatiebeperking      Text                                                                   /* Populatiebeperking */,
   Afleverpunt                   Varchar(200)                            CHECK (Afleverpunt <> '' )     /* Uri */,
   IndBlok                       Boolean                                 CHECK (IndBlok IN (true))      /* Ja */,
   CONSTRAINT pk_His_ToegangLevsautorisatie PRIMARY KEY (ID)
);
ALTER SEQUENCE AutAut.seq_His_ToegangLevsautorisatie OWNED BY AutAut.His_ToegangLevsautorisatie.ID;

CREATE SEQUENCE AutAut.seq_Levsautorisatie START WITH 1;
CREATE TABLE AutAut.Levsautorisatie (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_Levsautorisatie')   /* LevsautorisatieID */,
   Stelsel                       Smallint                      NOT NULL                                 /* StelselID */,
   IndModelautorisatie           Boolean                       NOT NULL                                 /* JaNee */,
   Naam                          Varchar(80)                             CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Protocolleringsniveau         Smallint                                                               /* ProtocolleringsniveauID */,
   IndAliasSrtAdmHndLeveren      Boolean                                                                /* JaNee */,
   DatIngang                     Integer                                                                /* Dat */,
   DatEinde                      Integer                                                                /* Dat */,
   Populatiebeperking            Text                                                                   /* Populatiebeperking */,
   IndPopbeperkingVolConv        Boolean                                 CHECK (IndPopbeperkingVolConv IN (false))   /* Nee */,
   Toelichting                   Text                                                                   /* OnbeperkteOms */,
   IndBlok                       Boolean                                 CHECK (IndBlok IN (true))      /* Ja */,
   CONSTRAINT pk_Levsautorisatie PRIMARY KEY (ID),
   CONSTRAINT uc_Levsautorisatie_Naam UNIQUE (Naam)
);
ALTER SEQUENCE AutAut.seq_Levsautorisatie OWNED BY AutAut.Levsautorisatie.ID;

CREATE SEQUENCE AutAut.seq_PartijFiatuitz START WITH 1;
CREATE TABLE AutAut.PartijFiatuitz (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_PartijFiatuitz')   /* PartijFiatuitzID */,
   Partij                        Smallint                      NOT NULL                                 /* PartijID */,
   DatIngang                     Integer                                                                /* Dat */,
   DatEinde                      Integer                                                                /* Dat */,
   PartijBijhvoorstel            Smallint                                                               /* PartijID */,
   SrtDoc                        Smallint                                                               /* SrtDocID */,
   SrtAdmHnd                     Smallint                                                               /* SrtAdmHndID */,
   IndBlok                       Boolean                                 CHECK (IndBlok IN (true))      /* Ja */,
   CONSTRAINT pk_PartijFiatuitz PRIMARY KEY (ID)
);
ALTER SEQUENCE AutAut.seq_PartijFiatuitz OWNED BY AutAut.PartijFiatuitz.ID;

CREATE TABLE AutAut.Protocolleringsniveau (
   ID                            Smallint                      NOT NULL                                 /* ProtocolleringsniveauID */,
   Code                          Smallint                      NOT NULL                                 /* ProtocolleringsniveauCode */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   DatAanvGel                    Integer                                                                /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   CONSTRAINT pk_Protocolleringsniveau PRIMARY KEY (ID),
   CONSTRAINT uc_Protocolleringsniveau_Code UNIQUE (Code),
   CONSTRAINT uc_Protocolleringsniveau_Naam UNIQUE (Naam),
   CONSTRAINT uc_Protocolleringsniveau_Oms UNIQUE (Oms)
);

CREATE TABLE AutAut.SrtDienst (
   ID                            Smallint                      NOT NULL                                 /* SrtDienstID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   CONSTRAINT pk_SrtDienst PRIMARY KEY (ID),
   CONSTRAINT uc_SrtDienst_Naam UNIQUE (Naam)
);

CREATE SEQUENCE AutAut.seq_ToegangBijhautorisatie START WITH 1;
CREATE TABLE AutAut.ToegangBijhautorisatie (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_ToegangBijhautorisatie')   /* ToegangBijhautorisatieID */,
   Geautoriseerde                Integer                       NOT NULL                                 /* PartijRolID */,
   Ondertekenaar                 Smallint                                                               /* PartijID */,
   Transporteur                  Smallint                                                               /* PartijID */,
   DatIngang                     Integer                                                                /* Dat */,
   DatEinde                      Integer                                                                /* Dat */,
   IndBlok                       Boolean                                 CHECK (IndBlok IN (true))      /* Ja */,
   CONSTRAINT pk_ToegangBijhautorisatie PRIMARY KEY (ID)
);
ALTER SEQUENCE AutAut.seq_ToegangBijhautorisatie OWNED BY AutAut.ToegangBijhautorisatie.ID;

CREATE SEQUENCE AutAut.seq_ToegangLevsautorisatie START WITH 1;
CREATE TABLE AutAut.ToegangLevsautorisatie (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_ToegangLevsautorisatie')   /* ToegangLevsautorisatieID */,
   Geautoriseerde                Integer                       NOT NULL                                 /* PartijRolID */,
   Levsautorisatie               Integer                       NOT NULL                                 /* LevsautorisatieID */,
   Ondertekenaar                 Smallint                                                               /* PartijID */,
   Transporteur                  Smallint                                                               /* PartijID */,
   DatIngang                     Integer                                                                /* Dat */,
   DatEinde                      Integer                                                                /* Dat */,
   NaderePopulatiebeperking      Text                                                                   /* Populatiebeperking */,
   Afleverpunt                   Varchar(200)                            CHECK (Afleverpunt <> '' )     /* Uri */,
   IndBlok                       Boolean                                 CHECK (IndBlok IN (true))      /* Ja */,
   CONSTRAINT pk_ToegangLevsautorisatie PRIMARY KEY (ID)
);
ALTER SEQUENCE AutAut.seq_ToegangLevsautorisatie OWNED BY AutAut.ToegangLevsautorisatie.ID;

CREATE SEQUENCE BRM.seq_His_Regelsituatie START WITH 1;
CREATE TABLE BRM.His_Regelsituatie (
   ID                            Integer                       NOT NULL  DEFAULT nextval('BRM.seq_His_Regelsituatie')   /* His_RegelsituatieID */,
   Regelsituatie                 Integer                       NOT NULL                                 /* RegelsituatieID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                                                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   Bijhaard                      Smallint                                                               /* BijhaardID */,
   NadereBijhaard                Smallint                                                               /* NadereBijhaardID */,
   Effect                        Smallint                      NOT NULL                                 /* RegeleffectID */,
   IndActief                     Boolean                       NOT NULL                                 /* JaNee */,
   CONSTRAINT pk_His_Regelsituatie PRIMARY KEY (ID)
);
ALTER SEQUENCE BRM.seq_His_Regelsituatie OWNED BY BRM.His_Regelsituatie.ID;

CREATE TABLE BRM.RegelSrtBer (
   ID                            Integer                       NOT NULL                                 /* RegelSrtBerID */,
   Regel                         Integer                       NOT NULL                                 /* RegelID */,
   SrtBer                        Smallint                      NOT NULL                                 /* SrtBerID */,
   CONSTRAINT pk_RegelSrtBer PRIMARY KEY (ID),
   CONSTRAINT uc_RegelSrtBer_Regel_SrtBer UNIQUE (Regel, SrtBer)
);

CREATE TABLE BRM.Regeleffect (
   ID                            Smallint                      NOT NULL                                 /* RegeleffectID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   DatAanvGel                    Integer                                                                /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   CONSTRAINT pk_Regeleffect PRIMARY KEY (ID),
   CONSTRAINT uc_Regeleffect_Naam UNIQUE (Naam),
   CONSTRAINT uc_Regeleffect_Oms UNIQUE (Oms)
);

CREATE SEQUENCE BRM.seq_Regelsituatie START WITH 1;
CREATE TABLE BRM.Regelsituatie (
   ID                            Integer                       NOT NULL  DEFAULT nextval('BRM.seq_Regelsituatie')   /* RegelsituatieID */,
   RegelSrtBer                   Integer                       NOT NULL                                 /* RegelSrtBerID */,
   Bijhaard                      Smallint                                                               /* BijhaardID */,
   NadereBijhaard                Smallint                                                               /* NadereBijhaardID */,
   Effect                        Smallint                                                               /* RegeleffectID */,
   IndActief                     Boolean                                                                /* JaNee */,
   CONSTRAINT pk_Regelsituatie PRIMARY KEY (ID)
);
ALTER SEQUENCE BRM.seq_Regelsituatie OWNED BY BRM.Regelsituatie.ID;

CREATE TABLE BRM.SrtRegel (
   ID                            Smallint                      NOT NULL                                 /* SrtRegelID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   DatAanvGel                    Integer                                                                /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   CONSTRAINT pk_SrtRegel PRIMARY KEY (ID),
   CONSTRAINT uc_SrtRegel_Naam UNIQUE (Naam),
   CONSTRAINT uc_SrtRegel_Oms UNIQUE (Oms)
);

CREATE SEQUENCE Conv.seq_ConvLO3Rubriek START WITH 1;
CREATE TABLE Conv.ConvLO3Rubriek (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Conv.seq_ConvLO3Rubriek')   /* ConvLO3RubriekID */,
   Naam                          Varchar(8)                    NOT NULL  CHECK (Naam <> '' )            /* ConvLO3RubriekNaam */,
   CONSTRAINT pk_ConvLO3Rubriek PRIMARY KEY (ID),
   CONSTRAINT uc_ConvLO3Rubriek_Naam UNIQUE (Naam)
);
ALTER SEQUENCE Conv.seq_ConvLO3Rubriek OWNED BY Conv.ConvLO3Rubriek.ID;

CREATE SEQUENCE Conv.seq_ConvRNIDeelnemer START WITH 1;
CREATE TABLE Conv.ConvRNIDeelnemer (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Conv.seq_ConvRNIDeelnemer')   /* ConvID */,
   Rubr8811CodeRNIDeelnemer      Smallint                      NOT NULL                                 /* LO3RNIDeelnemer */,
   Partij                        Smallint                      NOT NULL                                 /* PartijID */,
   CONSTRAINT pk_ConvRNIDeelnemer PRIMARY KEY (ID),
   CONSTRAINT uc_ConvRNIDeelnemer_Rubr8811CodeRNIDeelnemer UNIQUE (Rubr8811CodeRNIDeelnemer),
   CONSTRAINT uc_ConvRNIDeelnemer_Partij UNIQUE (Partij)
);
ALTER SEQUENCE Conv.seq_ConvRNIDeelnemer OWNED BY Conv.ConvRNIDeelnemer.ID;

CREATE SEQUENCE Conv.seq_ConvAandInhingVermissingReis START WITH 1;
CREATE TABLE Conv.ConvAandInhingVermissingReis (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Conv.seq_ConvAandInhingVermissingReis')   /* ConvID */,
   Rubr3570AandInhingDanWelVerm  Varchar(1)                    NOT NULL  CHECK (Rubr3570AandInhingDanWelVerm <> '' )   /* LO3AandInhingDanWelVermissin */,
   AandInhingVermissingReisdoc   Smallint                      NOT NULL                                 /* AandInhingVermissingReisdocI */,
   CONSTRAINT pk_ConvAandInhingVermissingReis PRIMARY KEY (ID),
   CONSTRAINT uc_ConvAandInhingVermissingReis_Rubr3570AandInhingDanWelVerm UNIQUE (Rubr3570AandInhingDanWelVerm),
   CONSTRAINT uc_ConvAandInhingVermissingReis_AandInhingVermissingReisdoc UNIQUE (AandInhingVermissingReisdoc)
);
ALTER SEQUENCE Conv.seq_ConvAandInhingVermissingReis OWNED BY Conv.ConvAandInhingVermissingReis.ID;

CREATE SEQUENCE Conv.seq_ConvAangifteAdresh START WITH 1;
CREATE TABLE Conv.ConvAangifteAdresh (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Conv.seq_ConvAangifteAdresh')   /* ConvID */,
   Rubr7210OmsVanDeAangifteAdre  Varchar(1)                    NOT NULL  CHECK (Rubr7210OmsVanDeAangifteAdre <> '' )   /* LO3OmsVanDeAangifteAdresh */,
   Aang                          Smallint                                                               /* AangID */,
   RdnWijzVerblijf               Smallint                                                               /* RdnWijzVerblijfID */,
   CONSTRAINT pk_ConvAangifteAdresh PRIMARY KEY (ID),
   CONSTRAINT uc_ConvAangifteAdresh_Rubr7210OmsVanDeAangifteAdre UNIQUE (Rubr7210OmsVanDeAangifteAdre),
   CONSTRAINT uc_ConvAangifteAdresh_Aang_RdnWijzVerblijf UNIQUE (Aang, RdnWijzVerblijf)
);
ALTER SEQUENCE Conv.seq_ConvAangifteAdresh OWNED BY Conv.ConvAangifteAdresh.ID;

CREATE SEQUENCE Conv.seq_ConvAdellijkeTitelPredikaat START WITH 1;
CREATE TABLE Conv.ConvAdellijkeTitelPredikaat (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Conv.seq_ConvAdellijkeTitelPredikaat')   /* ConvID */,
   Rubr0221AdellijkeTitelPredik  Varchar(2)                    NOT NULL  CHECK (Rubr0221AdellijkeTitelPredik <> '' )   /* LO3AdellijkeTitelPredikaat */,
   Geslachtsaand                 Smallint                      NOT NULL                                 /* GeslachtsaandID */,
   AdellijkeTitel                Smallint                                                               /* AdellijkeTitelID */,
   Predicaat                     Smallint                                                               /* PredicaatID */,
   CONSTRAINT pk_ConvAdellijkeTitelPredikaat PRIMARY KEY (ID),
   CONSTRAINT uc_ConvAdellijkeTitelPredikaat_Rubr0221AdellijkeTitelPredik_Ges UNIQUE (Rubr0221AdellijkeTitelPredik, Geslachtsaand),
   CONSTRAINT uc_ConvAdellijkeTitelPredikaat_AdellijkeTitel_Predicaat_Geslach UNIQUE (AdellijkeTitel, Predicaat, Geslachtsaand)
);
ALTER SEQUENCE Conv.seq_ConvAdellijkeTitelPredikaat OWNED BY Conv.ConvAdellijkeTitelPredikaat.ID;

CREATE SEQUENCE Conv.seq_ConvRdnBeeindigenNation START WITH 1;
CREATE TABLE Conv.ConvRdnBeeindigenNation (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Conv.seq_ConvRdnBeeindigenNation')   /* ConvID */,
   Rubr6410RdnBeeindigenNation   Varchar(3)                    NOT NULL  CHECK (Rubr6410RdnBeeindigenNation <> '' )   /* ConvRdnBeeindigenNation */,
   RdnVerlies                    Smallint                                                               /* RdnVerliesNLNationID */,
   CONSTRAINT pk_ConvRdnBeeindigenNation PRIMARY KEY (ID),
   CONSTRAINT uc_ConvRdnBeeindigenNation_Rubr6410RdnBeeindigenNation UNIQUE (Rubr6410RdnBeeindigenNation),
   CONSTRAINT uc_ConvRdnBeeindigenNation_RdnVerlies UNIQUE (RdnVerlies)
);
ALTER SEQUENCE Conv.seq_ConvRdnBeeindigenNation OWNED BY Conv.ConvRdnBeeindigenNation.ID;

CREATE SEQUENCE Conv.seq_ConvRdnOntbindingHuwelijkGer START WITH 1;
CREATE TABLE Conv.ConvRdnOntbindingHuwelijkGer (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Conv.seq_ConvRdnOntbindingHuwelijkGer')   /* ConvID */,
   Rubr0741RdnOntbindingHuwelij  Varchar(1)                    NOT NULL  CHECK (Rubr0741RdnOntbindingHuwelij <> '' )   /* LO3RdnOntbindingNietigverkla */,
   RdnEindeRelatie               Smallint                      NOT NULL                                 /* RdnEindeRelatieID */,
   CONSTRAINT pk_ConvRdnOntbindingHuwelijkGer PRIMARY KEY (ID),
   CONSTRAINT uc_ConvRdnOntbindingHuwelijkGer_Rubr0741RdnOntbindingHuwelij UNIQUE (Rubr0741RdnOntbindingHuwelij),
   CONSTRAINT uc_ConvRdnOntbindingHuwelijkGer_RdnEindeRelatie UNIQUE (RdnEindeRelatie)
);
ALTER SEQUENCE Conv.seq_ConvRdnOntbindingHuwelijkGer OWNED BY Conv.ConvRdnOntbindingHuwelijkGer.ID;

CREATE SEQUENCE Conv.seq_ConvRdnOpnameNation START WITH 1;
CREATE TABLE Conv.ConvRdnOpnameNation (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Conv.seq_ConvRdnOpnameNation')   /* ConvID */,
   Rubr6310RdnOpnameNation       Varchar(3)                    NOT NULL  CHECK (Rubr6310RdnOpnameNation <> '' )   /* LO3RdnOpnameNation */,
   RdnVerk                       Smallint                                                               /* RdnVerkNLNationID */,
   CONSTRAINT pk_ConvRdnOpnameNation PRIMARY KEY (ID),
   CONSTRAINT uc_ConvRdnOpnameNation_Rubr6310RdnOpnameNation UNIQUE (Rubr6310RdnOpnameNation),
   CONSTRAINT uc_ConvRdnOpnameNation_RdnVerk UNIQUE (RdnVerk)
);
ALTER SEQUENCE Conv.seq_ConvRdnOpnameNation OWNED BY Conv.ConvRdnOpnameNation.ID;

CREATE SEQUENCE Conv.seq_ConvRdnOpschorting START WITH 1;
CREATE TABLE Conv.ConvRdnOpschorting (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Conv.seq_ConvRdnOpschorting')   /* ConvID */,
   Rubr6720OmsRdnOpschortingBij  Varchar(1)                    NOT NULL  CHECK (Rubr6720OmsRdnOpschortingBij <> '' )   /* LO3OmsRdnOpschortingBijhoudi */,
   NadereBijhaard                Smallint                      NOT NULL                                 /* NadereBijhaardID */,
   CONSTRAINT pk_ConvRdnOpschorting PRIMARY KEY (ID),
   CONSTRAINT uc_ConvRdnOpschorting_Rubr6720OmsRdnOpschortingBij UNIQUE (Rubr6720OmsRdnOpschortingBij),
   CONSTRAINT uc_ConvRdnOpschorting_NadereBijhaard UNIQUE (NadereBijhaard)
);
ALTER SEQUENCE Conv.seq_ConvRdnOpschorting OWNED BY Conv.ConvRdnOpschorting.ID;

CREATE SEQUENCE Conv.seq_ConvSrtNLReisdoc START WITH 1;
CREATE TABLE Conv.ConvSrtNLReisdoc (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Conv.seq_ConvSrtNLReisdoc')   /* ConvID */,
   Rubr3511NLReisdoc             Varchar(2)                    NOT NULL  CHECK (Rubr3511NLReisdoc <> '' )   /* LO3NLReisdoc */,
   SrtNLReisdoc                  Smallint                      NOT NULL                                 /* SrtNLReisdocID */,
   CONSTRAINT pk_ConvSrtNLReisdoc PRIMARY KEY (ID),
   CONSTRAINT uc_ConvSrtNLReisdoc_Rubr3511NLReisdoc UNIQUE (Rubr3511NLReisdoc),
   CONSTRAINT uc_ConvSrtNLReisdoc_SrtNLReisdoc UNIQUE (SrtNLReisdoc)
);
ALTER SEQUENCE Conv.seq_ConvSrtNLReisdoc OWNED BY Conv.ConvSrtNLReisdoc.ID;

CREATE SEQUENCE Conv.seq_ConvVoorvoegsel START WITH 1;
CREATE TABLE Conv.ConvVoorvoegsel (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Conv.seq_ConvVoorvoegsel')   /* ConvID */,
   Rubr0231Voorvoegsel           Varchar(10)                   NOT NULL  CHECK (Rubr0231Voorvoegsel <> '' )   /* LO3Voorvoegsel */,
   Voorvoegsel                   Varchar(80)                   NOT NULL  CHECK (Voorvoegsel <> '' )     /* NaamEnumeratiewaarde */,
   Scheidingsteken               Varchar(1)                    NOT NULL  CHECK (Scheidingsteken <> '' )   /* Scheidingsteken */,
   CONSTRAINT pk_ConvVoorvoegsel PRIMARY KEY (ID),
   CONSTRAINT uc_ConvVoorvoegsel_Rubr0231Voorvoegsel UNIQUE (Rubr0231Voorvoegsel),
   CONSTRAINT uc_ConvVoorvoegsel_Voorvoegsel_Scheidingsteken UNIQUE (Voorvoegsel, Scheidingsteken)
);
ALTER SEQUENCE Conv.seq_ConvVoorvoegsel OWNED BY Conv.ConvVoorvoegsel.ID;

CREATE SEQUENCE IST.seq_Autorisatietabel START WITH 1;
CREATE TABLE IST.Autorisatietabel (
   ID                            Integer                       NOT NULL  DEFAULT nextval('IST.seq_Autorisatietabel')   /* AutorisatietabelID */,
   Partijcode                    Integer                       NOT NULL                                 /* PartijCode */,
   DatIngangTabelregel           Integer                       NOT NULL                                 /* DatEvtDeelsOnbekend */,
   DatEindeTabelregel            Integer                                                                /* DatEvtDeelsOnbekend */,
   Selectiesrt                   Smallint                                                               /* LO3Selectiesrt */,
   Beraand                       Smallint                                                               /* LO3Beraand */,
   Afnemersverstr                Text                                                                   /* LO3Afnemersverstr */,
   CONSTRAINT pk_Autorisatietabel PRIMARY KEY (ID),
   CONSTRAINT uc_Autorisatietabel_Partijcode_DatIngangTabelregel_DatEindeTabe UNIQUE (Partijcode, DatIngangTabelregel, DatEindeTabelregel)
);
ALTER SEQUENCE IST.seq_Autorisatietabel OWNED BY IST.Autorisatietabel.ID;

CREATE SEQUENCE Kern.seq_AandInhingVermissingReisdoc START WITH 1;
CREATE TABLE Kern.AandInhingVermissingReisdoc (
   ID                            Smallint                      NOT NULL  DEFAULT nextval('Kern.seq_AandInhingVermissingReisdoc')   /* AandInhingVermissingReisdocI */,
   Code                          Varchar(1)                    NOT NULL  CHECK (Code <> '' )            /* AandInhingVermissingReisdocC */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   CONSTRAINT pk_AandInhingVermissingReisdoc PRIMARY KEY (ID),
   CONSTRAINT uc_AandInhingVermissingReisdoc_Code UNIQUE (Code),
   CONSTRAINT uc_AandInhingVermissingReisdoc_Naam UNIQUE (Naam)
);
ALTER SEQUENCE Kern.seq_AandInhingVermissingReisdoc OWNED BY Kern.AandInhingVermissingReisdoc.ID;

CREATE SEQUENCE Kern.seq_AandVerblijfsr START WITH 1;
CREATE TABLE Kern.AandVerblijfsr (
   ID                            Smallint                      NOT NULL  DEFAULT nextval('Kern.seq_AandVerblijfsr')   /* AandVerblijfsrID */,
   Code                          Smallint                      NOT NULL                                 /* AandVerblijfsrCode */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   DatAanvGel                    Integer                                                                /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   CONSTRAINT pk_AandVerblijfsr PRIMARY KEY (ID),
   CONSTRAINT uc_AandVerblijfsr_Code UNIQUE (Code),
   CONSTRAINT uc_AandVerblijfsr_Oms UNIQUE (Oms)
);
ALTER SEQUENCE Kern.seq_AandVerblijfsr OWNED BY Kern.AandVerblijfsr.ID;

CREATE SEQUENCE Kern.seq_Aang START WITH 1;
CREATE TABLE Kern.Aang (
   ID                            Smallint                      NOT NULL  DEFAULT nextval('Kern.seq_Aang')   /* AangID */,
   Code                          Varchar(1)                    NOT NULL  CHECK (Code <> '' )            /* AangCode */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   CONSTRAINT pk_Aang PRIMARY KEY (ID),
   CONSTRAINT uc_Aang_Code UNIQUE (Code),
   CONSTRAINT uc_Aang_Naam UNIQUE (Naam)
);
ALTER SEQUENCE Kern.seq_Aang OWNED BY Kern.Aang.ID;

CREATE SEQUENCE Kern.seq_AdellijkeTitel START WITH 1;
CREATE TABLE Kern.AdellijkeTitel (
   ID                            Smallint                      NOT NULL  DEFAULT nextval('Kern.seq_AdellijkeTitel')   /* AdellijkeTitelID */,
   Code                          Varchar(1)                    NOT NULL  CHECK (Code <> '' )            /* AdellijkeTitelCode */,
   NaamMannelijk                 Varchar(80)                   NOT NULL  CHECK (NaamMannelijk <> '' )   /* NaamEnumeratiewaarde */,
   NaamVrouwelijk                Varchar(80)                   NOT NULL  CHECK (NaamVrouwelijk <> '' )   /* NaamEnumeratiewaarde */,
   CONSTRAINT pk_AdellijkeTitel PRIMARY KEY (ID),
   CONSTRAINT uc_AdellijkeTitel_Code UNIQUE (Code),
   CONSTRAINT uc_AdellijkeTitel_NaamMannelijk UNIQUE (NaamMannelijk),
   CONSTRAINT uc_AdellijkeTitel_NaamVrouwelijk UNIQUE (NaamVrouwelijk)
);
ALTER SEQUENCE Kern.seq_AdellijkeTitel OWNED BY Kern.AdellijkeTitel.ID;

CREATE SEQUENCE Kern.seq_AuttypeVanAfgifteReisdoc START WITH 1;
CREATE TABLE Kern.AuttypeVanAfgifteReisdoc (
   ID                            Smallint                      NOT NULL  DEFAULT nextval('Kern.seq_AuttypeVanAfgifteReisdoc')   /* AuttypeVanAfgifteReisdocID */,
   Code                          Varchar(2)                    NOT NULL  CHECK (Code <> '' )            /* AuttypeVanAfgifteReisdocCode */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   DatAanvGel                    Integer                                                                /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   CONSTRAINT pk_AuttypeVanAfgifteReisdoc PRIMARY KEY (ID),
   CONSTRAINT uc_AuttypeVanAfgifteReisdoc_Code UNIQUE (Code),
   CONSTRAINT uc_AuttypeVanAfgifteReisdoc_Naam UNIQUE (Naam)
);
ALTER SEQUENCE Kern.seq_AuttypeVanAfgifteReisdoc OWNED BY Kern.AuttypeVanAfgifteReisdoc.ID;

CREATE TABLE Kern.Bijhaard (
   ID                            Smallint                      NOT NULL                                 /* BijhaardID */,
   Code                          Varchar(1)                    NOT NULL  CHECK (Code <> '' )            /* BijhaardCode */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   CONSTRAINT pk_Bijhaard PRIMARY KEY (ID),
   CONSTRAINT uc_Bijhaard_Code UNIQUE (Code),
   CONSTRAINT uc_Bijhaard_Naam UNIQUE (Naam)
);

CREATE TABLE Kern.BurgerzakenModule (
   ID                            Smallint                      NOT NULL                                 /* IDModule */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   CONSTRAINT pk_BurgerzakenModule PRIMARY KEY (ID),
   CONSTRAINT uc_BurgerzakenModule_Naam UNIQUE (Naam)
);

CREATE TABLE Kern.CategorieAdmHnd (
   ID                            Smallint                      NOT NULL                                 /* CategorieAdmHndID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   CONSTRAINT pk_CategorieAdmHnd PRIMARY KEY (ID),
   CONSTRAINT uc_CategorieAdmHnd_Naam UNIQUE (Naam)
);

CREATE TABLE Kern.Element (
   ID                            Integer                       NOT NULL                                 /* ElementID */,
   Naam                          Varchar(160)                  NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaardeLang */,
   Srt                           Smallint                      NOT NULL                                 /* SrtElementID */,
   ElementNaam                   Varchar(80)                   NOT NULL  CHECK (ElementNaam <> '' )     /* NaamEnumeratiewaarde */,
   Objecttype                    Integer                                                                /* ElementID */,
   Groep                         Integer                                                                /* ElementID */,
   Volgnr                        Integer                       NOT NULL                                 /* Volgnr */,
   AliasVan                      Integer                                                                /* ElementID */,
   Expressie                     Varchar(1024)                           CHECK (Expressie <> '' )       /* Expressietekst */,
   Autorisatie                   Smallint                                                               /* SrtElementAutorisatieID */,
   Tabel                         Integer                                                                /* ElementID */,
   IdentDb                       Varchar(80)                             CHECK (IdentDb <> '' )         /* IdentifierLang */,
   HisTabel                      Integer                                                                /* ElementID */,
   HisIdentDB                    Varchar(80)                             CHECK (HisIdentDB <> '' )      /* IdentifierLang */,
   DbObject                      Integer                                                                /* ElementID */,
   HisDbObject                   Integer                                                                /* ElementID */,
   LeverenAlsStamgegeven         Boolean                                 CHECK (LeverenAlsStamgegeven IN (true))   /* Ja */,
   DatAanvGel                    Integer                                                                /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   CONSTRAINT pk_Element PRIMARY KEY (ID),
   CONSTRAINT uc_Element_Naam UNIQUE (Naam),
   CONSTRAINT uc_Element_Expressie UNIQUE (Expressie)
);

CREATE TABLE Kern.FunctieAdres (
   ID                            Smallint                      NOT NULL                                 /* SrtAdresID */,
   Code                          Varchar(1)                    NOT NULL  CHECK (Code <> '' )            /* SrtAdresCode */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   CONSTRAINT pk_FunctieAdres PRIMARY KEY (ID),
   CONSTRAINT uc_FunctieAdres_Code UNIQUE (Code),
   CONSTRAINT uc_FunctieAdres_Naam UNIQUE (Naam)
);

CREATE SEQUENCE Kern.seq_Gem START WITH 1;
CREATE TABLE Kern.Gem (
   ID                            Smallint                      NOT NULL  DEFAULT nextval('Kern.seq_Gem')   /* GemID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Code                          Smallint                      NOT NULL                                 /* GemCode */,
   Partij                        Smallint                      NOT NULL                                 /* PartijID */,
   VoortzettendeGem              Smallint                                                               /* GemID */,
   DatAanvGel                    Integer                                                                /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   CONSTRAINT pk_Gem PRIMARY KEY (ID),
   CONSTRAINT uc_Gem_Code UNIQUE (Code)
);
ALTER SEQUENCE Kern.seq_Gem OWNED BY Kern.Gem.ID;

CREATE TABLE Kern.Geslachtsaand (
   ID                            Smallint                      NOT NULL                                 /* GeslachtsaandID */,
   Code                          Varchar(1)                    NOT NULL  CHECK (Code <> '' )            /* GeslachtsaandCode */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                            CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   CONSTRAINT pk_Geslachtsaand PRIMARY KEY (ID),
   CONSTRAINT uc_Geslachtsaand_Code UNIQUE (Code),
   CONSTRAINT uc_Geslachtsaand_Naam UNIQUE (Naam)
);

CREATE SEQUENCE Kern.seq_His_Partij START WITH 1;
CREATE TABLE Kern.His_Partij (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_Partij')   /* His_PartijID */,
   Partij                        Smallint                      NOT NULL                                 /* PartijID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                                                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   DatIngang                     Integer                       NOT NULL                                 /* DatEvtDeelsOnbekend */,
   DatEinde                      Integer                                                                /* DatEvtDeelsOnbekend */,
   OIN                           Varchar(40)                             CHECK (OIN <> '' )             /* OIN */,
   Srt                           Smallint                                                               /* SrtPartijID */,
   IndVerstrbeperkingMogelijk    Boolean                       NOT NULL                                 /* JaNee */,
   CONSTRAINT pk_His_Partij PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_Partij OWNED BY Kern.His_Partij.ID;

CREATE SEQUENCE Kern.seq_His_PartijBijhouding START WITH 1;
CREATE TABLE Kern.His_PartijBijhouding (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PartijBijhouding')   /* His_PartijBijhoudingID */,
   Partij                        Smallint                      NOT NULL                                 /* PartijID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                                                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   IndAutoFiat                   Boolean                       NOT NULL                                 /* JaNee */,
   DatOvergangNaarBRP            Integer                       NOT NULL                                 /* Dat */,
   CONSTRAINT pk_His_PartijBijhouding PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_PartijBijhouding OWNED BY Kern.His_PartijBijhouding.ID;

CREATE SEQUENCE Kern.seq_His_PartijRol START WITH 1;
CREATE TABLE Kern.His_PartijRol (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PartijRol')   /* His_PartijRolID */,
   PartijRol                     Integer                       NOT NULL                                 /* PartijRolID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                                                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   DatIngang                     Integer                       NOT NULL                                 /* Dat */,
   DatEinde                      Integer                                                                /* Dat */,
   CONSTRAINT pk_His_PartijRol PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_PartijRol OWNED BY Kern.His_PartijRol.ID;

CREATE SEQUENCE Kern.seq_Koppelvlak START WITH 1;
CREATE TABLE Kern.Koppelvlak (
   ID                            Smallint                      NOT NULL  DEFAULT nextval('Kern.seq_Koppelvlak')   /* KoppelvlakID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Stelsel                       Smallint                      NOT NULL                                 /* StelselID */,
   CONSTRAINT pk_Koppelvlak PRIMARY KEY (ID),
   CONSTRAINT uc_Koppelvlak_Naam UNIQUE (Naam)
);
ALTER SEQUENCE Kern.seq_Koppelvlak OWNED BY Kern.Koppelvlak.ID;

CREATE SEQUENCE Kern.seq_LandGebied START WITH 1;
CREATE TABLE Kern.LandGebied (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_LandGebied')   /* LandGebiedID */,
   Code                          Smallint                      NOT NULL                                 /* LandGebiedCode */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   ISO31661Alpha2                Varchar(2)                              CHECK (ISO31661Alpha2 <> '' )   /* ISO31661Alpha2 */,
   DatAanvGel                    Integer                                                                /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   CONSTRAINT pk_LandGebied PRIMARY KEY (ID),
   CONSTRAINT uc_LandGebied_Code UNIQUE (Code)
);
ALTER SEQUENCE Kern.seq_LandGebied OWNED BY Kern.LandGebied.ID;

CREATE TABLE Kern.Naamgebruik (
   ID                            Smallint                      NOT NULL                                 /* NaamgebruikID */,
   Code                          Varchar(1)                    NOT NULL  CHECK (Code <> '' )            /* NaamgebruikCode */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                            CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   CONSTRAINT pk_Naamgebruik PRIMARY KEY (ID),
   CONSTRAINT uc_Naamgebruik_Code UNIQUE (Code),
   CONSTRAINT uc_Naamgebruik_Naam UNIQUE (Naam)
);

CREATE TABLE Kern.NadereBijhaard (
   ID                            Smallint                      NOT NULL                                 /* NadereBijhaardID */,
   Code                          Varchar(1)                    NOT NULL  CHECK (Code <> '' )            /* NadereBijhaardCode */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   CONSTRAINT pk_NadereBijhaard PRIMARY KEY (ID),
   CONSTRAINT uc_NadereBijhaard_Code UNIQUE (Code),
   CONSTRAINT uc_NadereBijhaard_Naam UNIQUE (Naam)
);

CREATE SEQUENCE Kern.seq_Nation START WITH 1;
CREATE TABLE Kern.Nation (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_Nation')   /* NationID */,
   Code                          Smallint                      NOT NULL                                 /* Nationcode */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   DatAanvGel                    Integer                                                                /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   CONSTRAINT pk_Nation PRIMARY KEY (ID),
   CONSTRAINT uc_Nation_Code UNIQUE (Code)
);
ALTER SEQUENCE Kern.seq_Nation OWNED BY Kern.Nation.ID;

CREATE SEQUENCE Kern.seq_Partij START WITH 1;
CREATE TABLE Kern.Partij (
   ID                            Smallint                      NOT NULL  DEFAULT nextval('Kern.seq_Partij')   /* PartijID */,
   Code                          Integer                       NOT NULL                                 /* PartijCode */,
   Naam                          Varchar(80)                             CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   DatIngang                     Integer                                                                /* DatEvtDeelsOnbekend */,
   DatEinde                      Integer                                                                /* DatEvtDeelsOnbekend */,
   OIN                           Varchar(40)                             CHECK (OIN <> '' )             /* OIN */,
   Srt                           Smallint                                                               /* SrtPartijID */,
   IndVerstrbeperkingMogelijk    Boolean                                                                /* JaNee */,
   IndAutoFiat                   Boolean                                                                /* JaNee */,
   DatOvergangNaarBRP            Integer                                                                /* Dat */,
   CONSTRAINT pk_Partij PRIMARY KEY (ID),
   CONSTRAINT uc_Partij_Code UNIQUE (Code),
   CONSTRAINT uc_Partij_Naam UNIQUE (Naam)
);
ALTER SEQUENCE Kern.seq_Partij OWNED BY Kern.Partij.ID;

CREATE SEQUENCE Kern.seq_PartijRol START WITH 1;
CREATE TABLE Kern.PartijRol (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_PartijRol')   /* PartijRolID */,
   Partij                        Smallint                      NOT NULL                                 /* PartijID */,
   Rol                           Smallint                      NOT NULL                                 /* RolID */,
   DatIngang                     Integer                                                                /* Dat */,
   DatEinde                      Integer                                                                /* Dat */,
   CONSTRAINT pk_PartijRol PRIMARY KEY (ID),
   CONSTRAINT uc_PartijRol_Partij_Rol UNIQUE (Partij, Rol)
);
ALTER SEQUENCE Kern.seq_PartijRol OWNED BY Kern.PartijRol.ID;

CREATE SEQUENCE Kern.seq_Plaats START WITH 1;
CREATE TABLE Kern.Plaats (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_Plaats')   /* PlaatsID */,
   Code                          Smallint                      NOT NULL                                 /* Wplcode */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   DatAanvGel                    Integer                                                                /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   CONSTRAINT pk_Plaats PRIMARY KEY (ID),
   CONSTRAINT uc_Plaats_Code UNIQUE (Code)
);
ALTER SEQUENCE Kern.seq_Plaats OWNED BY Kern.Plaats.ID;

CREATE SEQUENCE Kern.seq_Predicaat START WITH 1;
CREATE TABLE Kern.Predicaat (
   ID                            Smallint                      NOT NULL  DEFAULT nextval('Kern.seq_Predicaat')   /* PredicaatID */,
   Code                          Varchar(1)                    NOT NULL  CHECK (Code <> '' )            /* PredicaatCode */,
   NaamMannelijk                 Varchar(80)                   NOT NULL  CHECK (NaamMannelijk <> '' )   /* NaamEnumeratiewaarde */,
   NaamVrouwelijk                Varchar(80)                   NOT NULL  CHECK (NaamVrouwelijk <> '' )   /* NaamEnumeratiewaarde */,
   CONSTRAINT pk_Predicaat PRIMARY KEY (ID),
   CONSTRAINT uc_Predicaat_Code UNIQUE (Code),
   CONSTRAINT uc_Predicaat_NaamMannelijk UNIQUE (NaamMannelijk),
   CONSTRAINT uc_Predicaat_NaamVrouwelijk UNIQUE (NaamVrouwelijk)
);
ALTER SEQUENCE Kern.seq_Predicaat OWNED BY Kern.Predicaat.ID;

CREATE SEQUENCE Kern.seq_Rechtsgrond START WITH 1;
CREATE TABLE Kern.Rechtsgrond (
   ID                            Smallint                      NOT NULL  DEFAULT nextval('Kern.seq_Rechtsgrond')   /* RechtsgrondID */,
   Code                          Smallint                      NOT NULL                                 /* RechtsgrondCode */,
   Srt                           Smallint                      NOT NULL                                 /* SrtRechtsgrondID */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   IndLeidtTotStrijdigheid       Boolean                                 CHECK (IndLeidtTotStrijdigheid IN (true))   /* Ja */,
   DatAanvGel                    Integer                                                                /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   CONSTRAINT pk_Rechtsgrond PRIMARY KEY (ID),
   CONSTRAINT uc_Rechtsgrond_Code UNIQUE (Code)
);
ALTER SEQUENCE Kern.seq_Rechtsgrond OWNED BY Kern.Rechtsgrond.ID;

CREATE SEQUENCE Kern.seq_RdnEindeRelatie START WITH 1;
CREATE TABLE Kern.RdnEindeRelatie (
   ID                            Smallint                      NOT NULL  DEFAULT nextval('Kern.seq_RdnEindeRelatie')   /* RdnEindeRelatieID */,
   Code                          Varchar(1)                    NOT NULL  CHECK (Code <> '' )            /* RdnEindeRelatieCode */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   CONSTRAINT pk_RdnEindeRelatie PRIMARY KEY (ID),
   CONSTRAINT uc_RdnEindeRelatie_Code UNIQUE (Code),
   CONSTRAINT uc_RdnEindeRelatie_Oms UNIQUE (Oms)
);
ALTER SEQUENCE Kern.seq_RdnEindeRelatie OWNED BY Kern.RdnEindeRelatie.ID;

CREATE SEQUENCE Kern.seq_RdnVerkNLNation START WITH 1;
CREATE TABLE Kern.RdnVerkNLNation (
   ID                            Smallint                      NOT NULL  DEFAULT nextval('Kern.seq_RdnVerkNLNation')   /* RdnVerkNLNationID */,
   Code                          Smallint                      NOT NULL                                 /* RdnVerkCode */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   DatAanvGel                    Integer                                                                /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   CONSTRAINT pk_RdnVerkNLNation PRIMARY KEY (ID),
   CONSTRAINT uc_RdnVerkNLNation_Code UNIQUE (Code),
   CONSTRAINT uc_RdnVerkNLNation_Oms UNIQUE (Oms)
);
ALTER SEQUENCE Kern.seq_RdnVerkNLNation OWNED BY Kern.RdnVerkNLNation.ID;

CREATE SEQUENCE Kern.seq_RdnVerliesNLNation START WITH 1;
CREATE TABLE Kern.RdnVerliesNLNation (
   ID                            Smallint                      NOT NULL  DEFAULT nextval('Kern.seq_RdnVerliesNLNation')   /* RdnVerliesNLNationID */,
   Code                          Smallint                      NOT NULL                                 /* RdnVerliesCode */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   DatAanvGel                    Integer                                                                /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   CONSTRAINT pk_RdnVerliesNLNation PRIMARY KEY (ID),
   CONSTRAINT uc_RdnVerliesNLNation_Code UNIQUE (Code)
);
ALTER SEQUENCE Kern.seq_RdnVerliesNLNation OWNED BY Kern.RdnVerliesNLNation.ID;

CREATE SEQUENCE Kern.seq_RdnWijzVerblijf START WITH 1;
CREATE TABLE Kern.RdnWijzVerblijf (
   ID                            Smallint                      NOT NULL  DEFAULT nextval('Kern.seq_RdnWijzVerblijf')   /* RdnWijzVerblijfID */,
   Code                          Varchar(1)                    NOT NULL  CHECK (Code <> '' )            /* RdnWijzVerblijfCode */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   CONSTRAINT pk_RdnWijzVerblijf PRIMARY KEY (ID),
   CONSTRAINT uc_RdnWijzVerblijf_Code UNIQUE (Code),
   CONSTRAINT uc_RdnWijzVerblijf_Naam UNIQUE (Naam)
);
ALTER SEQUENCE Kern.seq_RdnWijzVerblijf OWNED BY Kern.RdnWijzVerblijf.ID;

CREATE TABLE Kern.Regel (
   ID                            Integer                       NOT NULL                                 /* RegelID */,
   Srt                           Smallint                      NOT NULL                                 /* SrtRegelID */,
   Code                          Varchar(10)                   NOT NULL  CHECK (Code <> '' )            /* RegelCode */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   Specificatie                  Text                          NOT NULL                                 /* Regelspecificatie */,
   CONSTRAINT pk_Regel PRIMARY KEY (ID),
   CONSTRAINT uc_Regel_Code UNIQUE (Code),
   CONSTRAINT uc_Regel_Oms UNIQUE (Oms),
   CONSTRAINT uc_Regel_Specificatie UNIQUE (Specificatie)
);

CREATE TABLE Kern.Rol (
   ID                            Smallint                      NOT NULL                                 /* RolID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   DatAanvGel                    Integer                                                                /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   CONSTRAINT pk_Rol PRIMARY KEY (ID),
   CONSTRAINT uc_Rol_Naam UNIQUE (Naam)
);

CREATE SEQUENCE Kern.seq_SrtNLReisdoc START WITH 1;
CREATE TABLE Kern.SrtNLReisdoc (
   ID                            Smallint                      NOT NULL  DEFAULT nextval('Kern.seq_SrtNLReisdoc')   /* SrtNLReisdocID */,
   Code                          Varchar(2)                    NOT NULL  CHECK (Code <> '' )            /* SrtNLReisdocCode */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   DatAanvGel                    Integer                                                                /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   CONSTRAINT pk_SrtNLReisdoc PRIMARY KEY (ID),
   CONSTRAINT uc_SrtNLReisdoc_Code UNIQUE (Code),
   CONSTRAINT uc_SrtNLReisdoc_Oms UNIQUE (Oms)
);
ALTER SEQUENCE Kern.seq_SrtNLReisdoc OWNED BY Kern.SrtNLReisdoc.ID;

CREATE TABLE Kern.SrtActie (
   ID                            Smallint                      NOT NULL                                 /* SrtActieID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   CONSTRAINT pk_SrtActie PRIMARY KEY (ID),
   CONSTRAINT uc_SrtActie_Naam UNIQUE (Naam)
);

CREATE TABLE Kern.SrtAdmHnd (
   ID                            Smallint                      NOT NULL                                 /* SrtAdmHndID */,
   Code                          Varchar(10)                   NOT NULL  CHECK (Code <> '' )            /* AdmHndCode */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   CategorieAdmHnd               Smallint                                                               /* CategorieAdmHndID */,
   Koppelvlak                    Smallint                      NOT NULL                                 /* KoppelvlakID */,
   Module                        Smallint                                                               /* IDModule */,
   Alias                         Varchar(80)                   NOT NULL  CHECK (Alias <> '' )           /* NaamEnumeratiewaarde */,
   CONSTRAINT pk_SrtAdmHnd PRIMARY KEY (ID),
   CONSTRAINT uc_SrtAdmHnd_Naam UNIQUE (Naam),
   CONSTRAINT uc_SrtAdmHnd_Code UNIQUE (Code)
);

CREATE TABLE Kern.SrtBetr (
   ID                            Smallint                      NOT NULL                                 /* SrtBetrID */,
   Code                          Varchar(1)                    NOT NULL  CHECK (Code <> '' )            /* SrtBetrCode */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   CONSTRAINT pk_SrtBetr PRIMARY KEY (ID),
   CONSTRAINT uc_SrtBetr_Code UNIQUE (Code),
   CONSTRAINT uc_SrtBetr_Naam UNIQUE (Naam)
);

CREATE SEQUENCE Kern.seq_SrtDoc START WITH 1;
CREATE TABLE Kern.SrtDoc (
   ID                            Smallint                      NOT NULL  DEFAULT nextval('Kern.seq_SrtDoc')   /* SrtDocID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   Rangorde                      Integer                                                                /* Volgnr */,
   CONSTRAINT pk_SrtDoc PRIMARY KEY (ID),
   CONSTRAINT uc_SrtDoc_Naam UNIQUE (Naam)
);
ALTER SEQUENCE Kern.seq_SrtDoc OWNED BY Kern.SrtDoc.ID;

CREATE TABLE Kern.SrtElement (
   ID                            Smallint                      NOT NULL                                 /* SrtElementID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   CONSTRAINT pk_SrtElement PRIMARY KEY (ID),
   CONSTRAINT uc_SrtElement_Naam UNIQUE (Naam)
);

CREATE TABLE Kern.SrtElementAutorisatie (
   ID                            Smallint                      NOT NULL                                 /* SrtElementAutorisatieID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   CONSTRAINT pk_SrtElementAutorisatie PRIMARY KEY (ID),
   CONSTRAINT uc_SrtElementAutorisatie_Naam UNIQUE (Naam)
);

CREATE TABLE Kern.SrtIndicatie (
   ID                            Smallint                      NOT NULL                                 /* SrtIndicatieID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   IndMaterieleHistorieVanToepa  Boolean                       NOT NULL                                 /* JaNee */,
   CONSTRAINT pk_SrtIndicatie PRIMARY KEY (ID),
   CONSTRAINT uc_SrtIndicatie_Naam UNIQUE (Naam)
);

CREATE TABLE Kern.SrtMigratie (
   ID                            Smallint                      NOT NULL                                 /* SrtMigratieID */,
   Code                          Varchar(1)                    NOT NULL  CHECK (Code <> '' )            /* SrtMigratieCode */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   CONSTRAINT pk_SrtMigratie PRIMARY KEY (ID),
   CONSTRAINT uc_SrtMigratie_Code UNIQUE (Code),
   CONSTRAINT uc_SrtMigratie_Naam UNIQUE (Naam)
);

CREATE TABLE Kern.SrtPartij (
   ID                            Smallint                      NOT NULL                                 /* SrtPartijID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   DatAanvGel                    Integer                                                                /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   CONSTRAINT pk_SrtPartij PRIMARY KEY (ID),
   CONSTRAINT uc_SrtPartij_Naam UNIQUE (Naam)
);

CREATE TABLE Kern.SrtPartijOnderzoek (
   ID                            Smallint                      NOT NULL                                 /* SrtPartijOnderzoekID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   CONSTRAINT pk_SrtPartijOnderzoek PRIMARY KEY (ID),
   CONSTRAINT uc_SrtPartijOnderzoek_Naam UNIQUE (Naam)
);

CREATE TABLE Kern.SrtPers (
   ID                            Smallint                      NOT NULL                                 /* SrtPersID */,
   Code                          Varchar(1)                    NOT NULL  CHECK (Code <> '' )            /* SrtPersCode */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                            CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   DatAanvGel                    Integer                                                                /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   CONSTRAINT pk_SrtPers PRIMARY KEY (ID),
   CONSTRAINT uc_SrtPers_Code UNIQUE (Code),
   CONSTRAINT uc_SrtPers_Naam UNIQUE (Naam)
);

CREATE TABLE Kern.SrtPersOnderzoek (
   ID                            Smallint                      NOT NULL                                 /* SrtPersOnderzoekID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   CONSTRAINT pk_SrtPersOnderzoek PRIMARY KEY (ID),
   CONSTRAINT uc_SrtPersOnderzoek_Naam UNIQUE (Naam)
);

CREATE TABLE Kern.SrtRechtsgrond (
   ID                            Smallint                      NOT NULL                                 /* SrtRechtsgrondID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   CONSTRAINT pk_SrtRechtsgrond PRIMARY KEY (ID),
   CONSTRAINT uc_SrtRechtsgrond_Naam UNIQUE (Naam)
);

CREATE TABLE Kern.SrtRelatie (
   ID                            Smallint                      NOT NULL                                 /* SrtRelatieID */,
   Code                          Varchar(1)                    NOT NULL  CHECK (Code <> '' )            /* SrtRelatieCode */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                            CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   CONSTRAINT pk_SrtRelatie PRIMARY KEY (ID),
   CONSTRAINT uc_SrtRelatie_Code UNIQUE (Code),
   CONSTRAINT uc_SrtRelatie_Naam UNIQUE (Naam)
);

CREATE TABLE Kern.StatusOnderzoek (
   ID                            Smallint                      NOT NULL                                 /* StatusOnderzoekID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   CONSTRAINT pk_StatusOnderzoek PRIMARY KEY (ID),
   CONSTRAINT uc_StatusOnderzoek_Naam UNIQUE (Naam)
);

CREATE TABLE Kern.StatusTerugmelding (
   ID                            Smallint                      NOT NULL                                 /* StatusTerugmeldingID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   CONSTRAINT pk_StatusTerugmelding PRIMARY KEY (ID),
   CONSTRAINT uc_StatusTerugmelding_Naam UNIQUE (Naam)
);

CREATE TABLE Kern.Stelsel (
   ID                            Smallint                      NOT NULL                                 /* StelselID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   CONSTRAINT pk_Stelsel PRIMARY KEY (ID),
   CONSTRAINT uc_Stelsel_Naam UNIQUE (Naam)
);

CREATE SEQUENCE Kern.seq_Voorvoegsel START WITH 1;
CREATE TABLE Kern.Voorvoegsel (
   ID                            Smallint                      NOT NULL  DEFAULT nextval('Kern.seq_Voorvoegsel')   /* VoorvoegselID */,
   Voorvoegsel                   Varchar(10)                   NOT NULL  CHECK (Voorvoegsel <> '' )     /* Voorvoegsel */,
   Scheidingsteken               Varchar(1)                    NOT NULL  CHECK (Scheidingsteken <> '' )   /* Scheidingsteken */,
   CONSTRAINT pk_Voorvoegsel PRIMARY KEY (ID),
   CONSTRAINT uc_Voorvoegsel_Voorvoegsel_Scheidingsteken UNIQUE (Voorvoegsel, Scheidingsteken)
);
ALTER SEQUENCE Kern.seq_Voorvoegsel OWNED BY Kern.Voorvoegsel.ID;

CREATE SEQUENCE MigBlok.seq_RdnBlokkering START WITH 1;
CREATE TABLE MigBlok.RdnBlokkering (
   ID                            Smallint                      NOT NULL  DEFAULT nextval('MigBlok.seq_RdnBlokkering')   /* RdnBlokkeringID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   CONSTRAINT pk_RdnBlokkering PRIMARY KEY (ID)
);
ALTER SEQUENCE MigBlok.seq_RdnBlokkering OWNED BY MigBlok.RdnBlokkering.ID;

CREATE TABLE VerConv.LO3BerBron (
   ID                            Integer                       NOT NULL                                 /* ConvID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   CONSTRAINT pk_LO3BerBron PRIMARY KEY (ID),
   CONSTRAINT uc_LO3BerBron_Naam UNIQUE (Naam)
);

CREATE TABLE VerConv.LO3CategorieMelding (
   ID                            Integer                       NOT NULL                                 /* ConvID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   CONSTRAINT pk_LO3CategorieMelding PRIMARY KEY (ID)
);

CREATE TABLE VerConv.LO3Severity (
   ID                            Integer                       NOT NULL                                 /* ConvID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   CONSTRAINT pk_LO3Severity PRIMARY KEY (ID),
   CONSTRAINT uc_LO3Severity_Naam UNIQUE (Naam)
);

CREATE TABLE VerConv.LO3SrtAandOuder (
   ID                            Smallint                      NOT NULL                                 /* LO3SrtAandOuderID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   CONSTRAINT pk_LO3SrtAandOuder PRIMARY KEY (ID),
   CONSTRAINT uc_LO3SrtAandOuder_Naam UNIQUE (Naam)
);

CREATE TABLE VerConv.LO3SrtMelding (
   ID                            Integer                       NOT NULL                                 /* ConvID */,
   Code                          Varchar(20)                   NOT NULL  CHECK (Code <> '' )            /* LO3Meldingcode */,
   Oms                           Text                          NOT NULL                                 /* OnbeperkteOms */,
   CategorieMelding              Integer                       NOT NULL                                 /* ConvID */,
   CONSTRAINT pk_LO3SrtMelding PRIMARY KEY (ID)
);

-- Actual tabellen--------------------------------------------------------------
CREATE SEQUENCE AutAut.seq_PersAfnemerindicatie START WITH 1;
CREATE TABLE AutAut.PersAfnemerindicatie (
   ID                            BigInt                        NOT NULL  DEFAULT nextval('AutAut.seq_PersAfnemerindicatie')   /* PersAfnemerindicatieID */,
   Pers                          Integer                       NOT NULL                                 /* PersID */,
   Afnemer                       Smallint                      NOT NULL                                 /* PartijID */,
   Levsautorisatie               Integer                       NOT NULL                                 /* LevsautorisatieID */,
   DatAanvMaterielePeriode       Integer                                                                /* DatEvtDeelsOnbekend */,
   DatEindeVolgen                Integer                                                                /* Dat */,
   CONSTRAINT pk_PersAfnemerindicatie PRIMARY KEY (ID),
   CONSTRAINT uc_PersAfnemerindicatie_Pers_Afnemer_Levsautorisatie UNIQUE (Pers, Afnemer, Levsautorisatie)
);
ALTER SEQUENCE AutAut.seq_PersAfnemerindicatie OWNED BY AutAut.PersAfnemerindicatie.ID;

CREATE SEQUENCE IST.seq_Stapel START WITH 1;
CREATE TABLE IST.Stapel (
   ID                            Integer                       NOT NULL  DEFAULT nextval('IST.seq_Stapel')   /* StapelID */,
   Pers                          Integer                       NOT NULL                                 /* PersID */,
   Categorie                     Varchar(2)                    NOT NULL  CHECK (Categorie <> '' )       /* LO3Categorie */,
   Volgnr                        Integer                       NOT NULL                                 /* Volgnr */,
   CONSTRAINT pk_Stapel PRIMARY KEY (ID),
   CONSTRAINT uc_Stapel_Pers_Categorie_Volgnr UNIQUE (Pers, Categorie, Volgnr)
);
ALTER SEQUENCE IST.seq_Stapel OWNED BY IST.Stapel.ID;

CREATE SEQUENCE IST.seq_StapelRelatie START WITH 1;
CREATE TABLE IST.StapelRelatie (
   ID                            Integer                       NOT NULL  DEFAULT nextval('IST.seq_StapelRelatie')   /* StapelRelatieID */,
   Stapel                        Integer                       NOT NULL                                 /* StapelID */,
   Relatie                       Integer                       NOT NULL                                 /* RelatieID */,
   CONSTRAINT pk_StapelRelatie PRIMARY KEY (ID),
   CONSTRAINT uc_StapelRelatie_Stapel_Relatie UNIQUE (Stapel, Relatie)
);
ALTER SEQUENCE IST.seq_StapelRelatie OWNED BY IST.StapelRelatie.ID;

CREATE SEQUENCE IST.seq_StapelVoorkomen START WITH 1;
CREATE TABLE IST.StapelVoorkomen (
   ID                            Integer                       NOT NULL  DEFAULT nextval('IST.seq_StapelVoorkomen')   /* StapelVoorkomenID */,
   Stapel                        Integer                       NOT NULL                                 /* StapelID */,
   Volgnr                        Integer                       NOT NULL                                 /* Volgnr */,
   AdmHnd                        BigInt                        NOT NULL                                 /* AdmHndID */,
   SrtDoc                        Smallint                                                               /* SrtDocID */,
   Partij                        Smallint                                                               /* PartijID */,
   Rubr8220DatDoc                Integer                                                                /* DatEvtDeelsOnbekend */,
   DocOms                        Varchar(40)                             CHECK (DocOms <> '' )          /* DocOms */,
   Rubr8310AandGegevensInOnderz  Integer                                                                /* LO3RubriekInclCategorieEnGro */,
   Rubr8320DatIngangOnderzoek    Integer                                                                /* DatEvtDeelsOnbekend */,
   Rubr8330DatEindeOnderzoek     Integer                                                                /* DatEvtDeelsOnbekend */,
   Rubr8410OnjuistStrijdigOpenb  Varchar(1)                              CHECK (Rubr8410OnjuistStrijdigOpenb <> '' )   /* LO3CoderingOnjuist */,
   Rubr8510IngangsdatGel         Integer                                                                /* DatEvtDeelsOnbekend */,
   Rubr8610DatVanOpneming        Integer                                                                /* DatEvtDeelsOnbekend */,
   Rubr6210DatIngangFamilierech  Integer                                                                /* DatEvtDeelsOnbekend */,
   Aktenr                        Varchar(7)                              CHECK (Aktenr <> '' )          /* Aktenr */,
   ANr                           Bigint                                                                 /* ANr */,
   BSN                           Integer                                                                /* BSN */,
   Voornamen                     Varchar(200)                            CHECK (Voornamen <> '' )       /* Voornamen */,
   Predicaat                     Smallint                                                               /* PredicaatID */,
   AdellijkeTitel                Smallint                                                               /* AdellijkeTitelID */,
   GeslachtBijAdellijkeTitelPre  Smallint                                                               /* GeslachtsaandID */,
   Voorvoegsel                   Varchar(10)                             CHECK (Voorvoegsel <> '' )     /* Voorvoegsel */,
   Scheidingsteken               Varchar(1)                              CHECK (Scheidingsteken <> '' )   /* Scheidingsteken */,
   Geslnaamstam                  Varchar(200)                            CHECK (Geslnaamstam <> '' )    /* Geslnaamstam */,
   DatGeboorte                   Integer                                                                /* DatEvtDeelsOnbekend */,
   GemGeboorte                   Smallint                                                               /* GemID */,
   BLPlaatsGeboorte              Varchar(40)                             CHECK (BLPlaatsGeboorte <> '' )   /* BLPlaats */,
   OmsLocGeboorte                Varchar(40)                             CHECK (OmsLocGeboorte <> '' )   /* Locoms */,
   LandGebiedGeboorte            Integer                                                                /* LandGebiedID */,
   Geslachtsaand                 Smallint                                                               /* GeslachtsaandID */,
   DatAanv                       Integer                                                                /* DatEvtDeelsOnbekend */,
   GemAanv                       Smallint                                                               /* GemID */,
   BLPlaatsAanv                  Varchar(40)                             CHECK (BLPlaatsAanv <> '' )    /* BLPlaats */,
   OmsLocAanv                    Varchar(40)                             CHECK (OmsLocAanv <> '' )      /* Locoms */,
   LandGebiedAanv                Integer                                                                /* LandGebiedID */,
   RdnEinde                      Smallint                                                               /* RdnEindeRelatieID */,
   DatEinde                      Integer                                                                /* DatEvtDeelsOnbekend */,
   GemEinde                      Smallint                                                               /* GemID */,
   BLPlaatsEinde                 Varchar(40)                             CHECK (BLPlaatsEinde <> '' )   /* BLPlaats */,
   OmsLocEinde                   Varchar(40)                             CHECK (OmsLocEinde <> '' )     /* Locoms */,
   LandGebiedEinde               Integer                                                                /* LandGebiedID */,
   SrtRelatie                    Smallint                                                               /* SrtRelatieID */,
   IndOuder1HeeftGezag           Boolean                                 CHECK (IndOuder1HeeftGezag IN (true))   /* Ja */,
   IndOuder2HeeftGezag           Boolean                                 CHECK (IndOuder2HeeftGezag IN (true))   /* Ja */,
   IndDerdeHeeftGezag            Boolean                                 CHECK (IndDerdeHeeftGezag IN (true))   /* Ja */,
   IndOnderCuratele              Boolean                                 CHECK (IndOnderCuratele IN (true))   /* Ja */,
   CONSTRAINT pk_StapelVoorkomen PRIMARY KEY (ID),
   CONSTRAINT uc_StapelVoorkomen_Stapel_Volgnr UNIQUE (Stapel, Volgnr)
);
ALTER SEQUENCE IST.seq_StapelVoorkomen OWNED BY IST.StapelVoorkomen.ID;

CREATE SEQUENCE Kern.seq_Actie START WITH 1;
CREATE TABLE Kern.Actie (
   ID                            BigInt                        NOT NULL  DEFAULT nextval('Kern.seq_Actie')   /* ActieID */,
   Srt                           Smallint                      NOT NULL                                 /* SrtActieID */,
   AdmHnd                        BigInt                        NOT NULL                                 /* AdmHndID */,
   Partij                        Smallint                      NOT NULL                                 /* PartijID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   DatOntlening                  Integer                                                                /* DatEvtDeelsOnbekend */,
   CONSTRAINT pk_Actie PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_Actie OWNED BY Kern.Actie.ID;

CREATE SEQUENCE Kern.seq_ActieBron START WITH 1;
CREATE TABLE Kern.ActieBron (
   ID                            BigInt                        NOT NULL  DEFAULT nextval('Kern.seq_ActieBron')   /* ActieBronID */,
   Actie                         BigInt                        NOT NULL                                 /* ActieID */,
   Doc                           BigInt                                                                 /* DocID */,
   Rechtsgrond                   Smallint                                                               /* RechtsgrondID */,
   Rechtsgrondoms                Varchar(250)                            CHECK (Rechtsgrondoms <> '' )   /* OmsEnumeratiewaarde */,
   CONSTRAINT pk_ActieBron PRIMARY KEY (ID),
   CONSTRAINT uc_ActieBron_Actie_Doc_Rechtsgrond_Rechtsgrondoms UNIQUE (Actie, Doc, Rechtsgrond, Rechtsgrondoms)
);
ALTER SEQUENCE Kern.seq_ActieBron OWNED BY Kern.ActieBron.ID;

CREATE SEQUENCE Kern.seq_AdmHnd START WITH 1;
CREATE TABLE Kern.AdmHnd (
   ID                            BigInt                        NOT NULL  DEFAULT nextval('Kern.seq_AdmHnd')   /* AdmHndID */,
   Srt                           Smallint                      NOT NULL                                 /* SrtAdmHndID */,
   Partij                        Smallint                      NOT NULL                                 /* PartijID */,
   ToelichtingOntlening          Varchar(400)                            CHECK (ToelichtingOntlening <> '' )   /* Ontleningstoelichting */,
   TsReg                         Timestamp with time zone                                               /* Ts */,
   TsLev                         Timestamp with time zone                                               /* Ts */,
   CONSTRAINT pk_AdmHnd PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_AdmHnd OWNED BY Kern.AdmHnd.ID;

CREATE SEQUENCE Kern.seq_AdmHndGedeblokkeerdeMelding START WITH 1;
CREATE TABLE Kern.AdmHndGedeblokkeerdeMelding (
   ID                            BigInt                        NOT NULL  DEFAULT nextval('Kern.seq_AdmHndGedeblokkeerdeMelding')   /* AdmHndGedeblokkeerdeMeldingI */,
   AdmHnd                        BigInt                        NOT NULL                                 /* AdmHndID */,
   GedeblokkeerdeMelding         BigInt                        NOT NULL                                 /* GedeblokkeerdeMeldingID */,
   CONSTRAINT pk_AdmHndGedeblokkeerdeMelding PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_AdmHndGedeblokkeerdeMelding OWNED BY Kern.AdmHndGedeblokkeerdeMelding.ID;

CREATE SEQUENCE Kern.seq_Betr START WITH 1;
CREATE TABLE Kern.Betr (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_Betr')   /* BetrID */,
   Relatie                       Integer                       NOT NULL                                 /* RelatieID */,
   Rol                           Smallint                      NOT NULL                                 /* SrtBetrID */,
   Pers                          Integer                                                                /* PersID */,
   IndOuder                      Boolean                                 CHECK (IndOuder IN (true))     /* Ja */,
   IndOuderUitWieKindIsGeboren   Boolean                                                                /* JaNee */,
   IndOuderHeeftGezag            Boolean                                                                /* JaNee */,
   CONSTRAINT pk_Betr PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_Betr OWNED BY Kern.Betr.ID;

CREATE SEQUENCE Kern.seq_Doc START WITH 1;
CREATE TABLE Kern.Doc (
   ID                            BigInt                        NOT NULL  DEFAULT nextval('Kern.seq_Doc')   /* DocID */,
   Srt                           Smallint                      NOT NULL                                 /* SrtDocID */,
   Ident                         Varchar(20)                             CHECK (Ident <> '' )           /* DocIdent */,
   Aktenr                        Varchar(7)                              CHECK (Aktenr <> '' )          /* Aktenr */,
   Oms                           Varchar(40)                             CHECK (Oms <> '' )             /* DocOms */,
   Partij                        Smallint                                                               /* PartijID */,
   CONSTRAINT pk_Doc PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_Doc OWNED BY Kern.Doc.ID;

CREATE SEQUENCE Kern.seq_GedeblokkeerdeMelding START WITH 1;
CREATE TABLE Kern.GedeblokkeerdeMelding (
   ID                            BigInt                        NOT NULL  DEFAULT nextval('Kern.seq_GedeblokkeerdeMelding')   /* GedeblokkeerdeMeldingID */,
   Regel                         Integer                       NOT NULL                                 /* RegelID */,
   Melding                       Varchar(200)                            CHECK (Melding <> '' )         /* Meldingtekst */,
   CONSTRAINT pk_GedeblokkeerdeMelding PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_GedeblokkeerdeMelding OWNED BY Kern.GedeblokkeerdeMelding.ID;

CREATE SEQUENCE Kern.seq_GegevenInOnderzoek START WITH 1;
CREATE TABLE Kern.GegevenInOnderzoek (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_GegevenInOnderzoek')   /* GegevenInOnderzoekID */,
   Onderzoek                     Integer                       NOT NULL                                 /* OnderzoekID */,
   Element                       Integer                       NOT NULL                                 /* ElementID */,
   ObjectSleutelGegeven          BigInt                                                                 /* Sleutelwaarde */,
   VoorkomenSleutelGegeven       BigInt                                                                 /* Sleutelwaarde */,
   CONSTRAINT pk_GegevenInOnderzoek PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_GegevenInOnderzoek OWNED BY Kern.GegevenInOnderzoek.ID;

CREATE SEQUENCE Kern.seq_GegevenInTerugmelding START WITH 1;
CREATE TABLE Kern.GegevenInTerugmelding (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_GegevenInTerugmelding')   /* GegevenInTerugmeldingID */,
   Terugmelding                  Integer                       NOT NULL                                 /* TerugmeldingID */,
   Element                       Integer                       NOT NULL                                 /* ElementID */,
   BetwijfeldeWaarde             Varchar(200)                            CHECK (BetwijfeldeWaarde <> '' )   /* Gegevenswaarde */,
   VerwachteWaarde               Varchar(200)                            CHECK (VerwachteWaarde <> '' )   /* Gegevenswaarde */,
   CONSTRAINT pk_GegevenInTerugmelding PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_GegevenInTerugmelding OWNED BY Kern.GegevenInTerugmelding.ID;

CREATE SEQUENCE Kern.seq_Onderzoek START WITH 1;
CREATE TABLE Kern.Onderzoek (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_Onderzoek')   /* OnderzoekID */,
   DatAanv                       Integer                                                                /* DatEvtDeelsOnbekend */,
   VerwachteAfhandeldat          Integer                                                                /* DatEvtDeelsOnbekend */,
   DatEinde                      Integer                                                                /* DatEvtDeelsOnbekend */,
   Oms                           Text                                                                   /* OnderzoekOms */,
   Status                        Smallint                                                               /* StatusOnderzoekID */,
   AdmHnd                        BigInt                                                                 /* AdmHndID */,
   CONSTRAINT pk_Onderzoek PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_Onderzoek OWNED BY Kern.Onderzoek.ID;

CREATE SEQUENCE Kern.seq_PartijOnderzoek START WITH 1;
CREATE TABLE Kern.PartijOnderzoek (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_PartijOnderzoek')   /* PartijOnderzoekID */,
   Partij                        Smallint                      NOT NULL                                 /* PartijID */,
   Onderzoek                     Integer                       NOT NULL                                 /* OnderzoekID */,
   Rol                           Smallint                                                               /* SrtPartijOnderzoekID */,
   CONSTRAINT pk_PartijOnderzoek PRIMARY KEY (ID),
   CONSTRAINT uc_PartijOnderzoek_Onderzoek_Partij UNIQUE (Onderzoek, Partij)
);
ALTER SEQUENCE Kern.seq_PartijOnderzoek OWNED BY Kern.PartijOnderzoek.ID;

CREATE SEQUENCE Kern.seq_Pers START WITH 1;
CREATE TABLE Kern.Pers (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_Pers')   /* PersID */,
   Srt                           Smallint                      NOT NULL                                 /* SrtPersID */,
   AdmHnd                        BigInt                                                                 /* AdmHndID */,
   TsLaatsteWijz                 Timestamp with time zone                                               /* Ts */,
   Sorteervolgorde               Smallint                                                               /* Sorteervolgorde */,
   IndOnverwBijhvoorstelNietIng  Boolean                                                                /* JaNee */,
   TsLaatsteWijzGBASystematiek   Timestamp with time zone                                               /* Ts */,
   BSN                           Integer                                                                /* BSN */,
   ANr                           Bigint                                                                 /* ANr */,
   IndAfgeleid                   Boolean                                                                /* JaNee */,
   IndNreeks                     Boolean                                                                /* JaNee */,
   Predicaat                     Smallint                                                               /* PredicaatID */,
   Voornamen                     Varchar(200)                            CHECK (Voornamen <> '' )       /* Voornamen */,
   AdellijkeTitel                Smallint                                                               /* AdellijkeTitelID */,
   Voorvoegsel                   Varchar(10)                             CHECK (Voorvoegsel <> '' )     /* Voorvoegsel */,
   Scheidingsteken               Varchar(1)                              CHECK (Scheidingsteken <> '' )   /* Scheidingsteken */,
   Geslnaamstam                  Varchar(200)                            CHECK (Geslnaamstam <> '' )    /* Geslnaamstam */,
   DatGeboorte                   Integer                                                                /* DatEvtDeelsOnbekend */,
   GemGeboorte                   Smallint                                                               /* GemID */,
   WplnaamGeboorte               Varchar(80)                             CHECK (WplnaamGeboorte <> '' )   /* NaamEnumeratiewaarde */,
   BLPlaatsGeboorte              Varchar(40)                             CHECK (BLPlaatsGeboorte <> '' )   /* BLPlaats */,
   BLRegioGeboorte               Varchar(35)                             CHECK (BLRegioGeboorte <> '' )   /* BLRegio */,
   OmsLocGeboorte                Varchar(40)                             CHECK (OmsLocGeboorte <> '' )   /* Locoms */,
   LandGebiedGeboorte            Integer                                                                /* LandGebiedID */,
   Geslachtsaand                 Smallint                                                               /* GeslachtsaandID */,
   DatInschr                     Integer                                                                /* DatEvtDeelsOnbekend */,
   Versienr                      BigInt                                                                 /* Versienr */,
   Dattijdstempel                Timestamp with time zone                                               /* Ts */,
   VorigeBSN                     Integer                                                                /* BSN */,
   VolgendeBSN                   Integer                                                                /* BSN */,
   VorigeANr                     Bigint                                                                 /* ANr */,
   VolgendeANr                   Bigint                                                                 /* ANr */,
   Bijhpartij                    Smallint                                                               /* PartijID */,
   Bijhaard                      Smallint                                                               /* BijhaardID */,
   NadereBijhaard                Smallint                                                               /* NadereBijhaardID */,
   IndOnverwDocAanw              Boolean                                                                /* JaNee */,
   DatOverlijden                 Integer                                                                /* DatEvtDeelsOnbekend */,
   GemOverlijden                 Smallint                                                               /* GemID */,
   WplnaamOverlijden             Varchar(80)                             CHECK (WplnaamOverlijden <> '' )   /* NaamEnumeratiewaarde */,
   BLPlaatsOverlijden            Varchar(40)                             CHECK (BLPlaatsOverlijden <> '' )   /* BLPlaats */,
   BLRegioOverlijden             Varchar(35)                             CHECK (BLRegioOverlijden <> '' )   /* BLRegio */,
   OmsLocOverlijden              Varchar(40)                             CHECK (OmsLocOverlijden <> '' )   /* Locoms */,
   LandGebiedOverlijden          Integer                                                                /* LandGebiedID */,
   Naamgebruik                   Smallint                                                               /* NaamgebruikID */,
   IndNaamgebruikAfgeleid        Boolean                                                                /* JaNee */,
   PredicaatNaamgebruik          Smallint                                                               /* PredicaatID */,
   VoornamenNaamgebruik          Varchar(200)                            CHECK (VoornamenNaamgebruik <> '' )   /* Voornamen */,
   AdellijkeTitelNaamgebruik     Smallint                                                               /* AdellijkeTitelID */,
   VoorvoegselNaamgebruik        Varchar(10)                             CHECK (VoorvoegselNaamgebruik <> '' )   /* Voorvoegsel */,
   ScheidingstekenNaamgebruik    Varchar(1)                              CHECK (ScheidingstekenNaamgebruik <> '' )   /* Scheidingsteken */,
   GeslnaamstamNaamgebruik       Varchar(200)                            CHECK (GeslnaamstamNaamgebruik <> '' )   /* Geslnaamstam */,
   SrtMigratie                   Smallint                                                               /* SrtMigratieID */,
   RdnWijzMigratie               Smallint                                                               /* RdnWijzVerblijfID */,
   AangMigratie                  Smallint                                                               /* AangID */,
   LandGebiedMigratie            Integer                                                                /* LandGebiedID */,
   BLAdresRegel1Migratie         Varchar(40)                             CHECK (BLAdresRegel1Migratie <> '' )   /* Adresregel */,
   BLAdresRegel2Migratie         Varchar(40)                             CHECK (BLAdresRegel2Migratie <> '' )   /* Adresregel */,
   BLAdresRegel3Migratie         Varchar(40)                             CHECK (BLAdresRegel3Migratie <> '' )   /* Adresregel */,
   BLAdresRegel4Migratie         Varchar(40)                             CHECK (BLAdresRegel4Migratie <> '' )   /* Adresregel */,
   BLAdresRegel5Migratie         Varchar(40)                             CHECK (BLAdresRegel5Migratie <> '' )   /* Adresregel */,
   BLAdresRegel6Migratie         Varchar(40)                             CHECK (BLAdresRegel6Migratie <> '' )   /* Adresregel */,
   AandVerblijfsr                Smallint                                                               /* AandVerblijfsrID */,
   DatAanvVerblijfsr             Integer                                                                /* DatEvtDeelsOnbekend */,
   DatMededelingVerblijfsr       Integer                                                                /* DatEvtDeelsOnbekend */,
   DatVoorzEindeVerblijfsr       Integer                                                                /* DatEvtDeelsOnbekend */,
   IndUitslKiesr                 Boolean                                 CHECK (IndUitslKiesr IN (true))   /* Ja */,
   DatVoorzEindeUitslKiesr       Integer                                                                /* DatEvtDeelsOnbekend */,
   IndDeelnEUVerkiezingen        Boolean                                                                /* JaNee */,
   DatAanlAanpDeelnEUVerkiezing  Integer                                                                /* DatEvtDeelsOnbekend */,
   DatVoorzEindeUitslEUVerkiezi  Integer                                                                /* DatEvtDeelsOnbekend */,
   GemPK                         Smallint                                                               /* PartijID */,
   IndPKVolledigGeconv           Boolean                                                                /* JaNee */,
   CONSTRAINT pk_Pers PRIMARY KEY (ID)
);
CREATE UNIQUE INDEX uc_Pers_ANr ON Kern.Pers (ANr) WHERE Srt = 1 AND NadereBijhaard NOT IN (7, 8);
ALTER SEQUENCE Kern.seq_Pers OWNED BY Kern.Pers.ID;

CREATE SEQUENCE Kern.seq_PersAdres START WITH 1;
CREATE TABLE Kern.PersAdres (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_PersAdres')   /* PersAdresID */,
   Pers                          Integer                       NOT NULL                                 /* PersID */,
   Srt                           Smallint                                                               /* SrtAdresID */,
   RdnWijz                       Smallint                                                               /* RdnWijzVerblijfID */,
   AangAdresh                    Smallint                                                               /* AangID */,
   DatAanvAdresh                 Integer                                                                /* DatEvtDeelsOnbekend */,
   IdentcodeAdresseerbaarObject  Varchar(16)                             CHECK (IdentcodeAdresseerbaarObject <> '' )   /* IdentcodeAdresseerbaarObject */,
   IdentcodeNraand               Varchar(16)                             CHECK (IdentcodeNraand <> '' )   /* IdentcodeNraand */,
   Gem                           Smallint                                                               /* GemID */,
   NOR                           Varchar(80)                             CHECK (NOR <> '' )             /* NOR */,
   AfgekorteNOR                  Varchar(24)                             CHECK (AfgekorteNOR <> '' )    /* AfgekorteNOR */,
   Gemdeel                       Varchar(24)                             CHECK (Gemdeel <> '' )         /* Gemdeel */,
   Huisnr                        Integer                                                                /* Huisnr */,
   Huisletter                    Varchar(1)                              CHECK (Huisletter <> '' )      /* Huisletter */,
   Huisnrtoevoeging              Varchar(4)                              CHECK (Huisnrtoevoeging <> '' )   /* Huisnrtoevoeging */,
   Postcode                      Varchar(6)                              CHECK (Postcode <> '' )        /* Postcode */,
   Wplnaam                       Varchar(80)                             CHECK (Wplnaam <> '' )         /* NaamEnumeratiewaarde */,
   LocTenOpzichteVanAdres        Varchar(2)                              CHECK (LocTenOpzichteVanAdres IN ('by','to'))   /* LocTenOpzichteVanAdres */,
   Locoms                        Varchar(40)                             CHECK (Locoms <> '' )          /* Locoms */,
   BLAdresRegel1                 Varchar(40)                             CHECK (BLAdresRegel1 <> '' )   /* Adresregel */,
   BLAdresRegel2                 Varchar(40)                             CHECK (BLAdresRegel2 <> '' )   /* Adresregel */,
   BLAdresRegel3                 Varchar(40)                             CHECK (BLAdresRegel3 <> '' )   /* Adresregel */,
   BLAdresRegel4                 Varchar(40)                             CHECK (BLAdresRegel4 <> '' )   /* Adresregel */,
   BLAdresRegel5                 Varchar(40)                             CHECK (BLAdresRegel5 <> '' )   /* Adresregel */,
   BLAdresRegel6                 Varchar(40)                             CHECK (BLAdresRegel6 <> '' )   /* Adresregel */,
   LandGebied                    Integer                                                                /* LandGebiedID */,
   IndPersAangetroffenOpAdres    Boolean                                 CHECK (IndPersAangetroffenOpAdres IN (false))   /* Nee */,
   CONSTRAINT pk_PersAdres PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_PersAdres OWNED BY Kern.PersAdres.ID;

CREATE SEQUENCE Kern.seq_PersGeslnaamcomp START WITH 1;
CREATE TABLE Kern.PersGeslnaamcomp (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_PersGeslnaamcomp')   /* PersGeslnaamID */,
   Pers                          Integer                       NOT NULL                                 /* PersID */,
   Volgnr                        Integer                       NOT NULL                                 /* Volgnr */,
   Predicaat                     Smallint                                                               /* PredicaatID */,
   AdellijkeTitel                Smallint                                                               /* AdellijkeTitelID */,
   Voorvoegsel                   Varchar(10)                             CHECK (Voorvoegsel <> '' )     /* Voorvoegsel */,
   Scheidingsteken               Varchar(1)                              CHECK (Scheidingsteken <> '' )   /* Scheidingsteken */,
   Stam                          Varchar(200)                            CHECK (Stam <> '' )            /* Geslnaamstam */,
   CONSTRAINT pk_PersGeslnaamcomp PRIMARY KEY (ID),
   CONSTRAINT uc_PersGeslnaamcomp_Pers_Volgnr UNIQUE (Pers, Volgnr)
);
ALTER SEQUENCE Kern.seq_PersGeslnaamcomp OWNED BY Kern.PersGeslnaamcomp.ID;

CREATE SEQUENCE Kern.seq_PersIndicatie START WITH 1;
CREATE TABLE Kern.PersIndicatie (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_PersIndicatie')   /* PersIndicatieID */,
   Pers                          Integer                       NOT NULL                                 /* PersID */,
   Srt                           Smallint                      NOT NULL                                 /* SrtIndicatieID */,
   Waarde                        Boolean                                 CHECK (Waarde IN (true))       /* Ja */,
   MigrRdnOpnameNation           Varchar(3)                              CHECK (MigrRdnOpnameNation <> '' )   /* LO3RdnOpnameNation */,
   MigrRdnBeeindigenNation       Varchar(3)                              CHECK (MigrRdnBeeindigenNation <> '' )   /* ConvRdnBeeindigenNation */,
   CONSTRAINT pk_PersIndicatie PRIMARY KEY (ID),
   CONSTRAINT uc_PersIndicatie_Pers_Srt UNIQUE (Pers, Srt)
);
ALTER SEQUENCE Kern.seq_PersIndicatie OWNED BY Kern.PersIndicatie.ID;

CREATE SEQUENCE Kern.seq_PersNation START WITH 1;
CREATE TABLE Kern.PersNation (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_PersNation')   /* PersNationID */,
   Pers                          Integer                       NOT NULL                                 /* PersID */,
   Nation                        Integer                       NOT NULL                                 /* NationID */,
   RdnVerk                       Smallint                                                               /* RdnVerkNLNationID */,
   RdnVerlies                    Smallint                                                               /* RdnVerliesNLNationID */,
   IndBijhoudingBeeindigd        Boolean                                 CHECK (IndBijhoudingBeeindigd IN (true))   /* Ja */,
   MigrRdnOpnameNation           Varchar(3)                              CHECK (MigrRdnOpnameNation <> '' )   /* LO3RdnOpnameNation */,
   MigrRdnBeeindigenNation       Varchar(3)                              CHECK (MigrRdnBeeindigenNation <> '' )   /* ConvRdnBeeindigenNation */,
   MigrDatEindeBijhouding        Integer                                                                /* DatEvtDeelsOnbekend */,
   CONSTRAINT pk_PersNation PRIMARY KEY (ID),
   CONSTRAINT uc_PersNation_Pers_Nation UNIQUE (Pers, Nation)
);
ALTER SEQUENCE Kern.seq_PersNation OWNED BY Kern.PersNation.ID;

CREATE SEQUENCE Kern.seq_PersOnderzoek START WITH 1;
CREATE TABLE Kern.PersOnderzoek (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_PersOnderzoek')   /* PersOnderzoekID */,
   Pers                          Integer                       NOT NULL                                 /* PersID */,
   Onderzoek                     Integer                       NOT NULL                                 /* OnderzoekID */,
   Rol                           Smallint                                                               /* SrtPersOnderzoekID */,
   CONSTRAINT pk_PersOnderzoek PRIMARY KEY (ID),
   CONSTRAINT uc_PersOnderzoek_Onderzoek_Pers UNIQUE (Onderzoek, Pers)
);
ALTER SEQUENCE Kern.seq_PersOnderzoek OWNED BY Kern.PersOnderzoek.ID;

CREATE SEQUENCE Kern.seq_PersReisdoc START WITH 1;
CREATE TABLE Kern.PersReisdoc (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_PersReisdoc')   /* PersReisdocID */,
   Pers                          Integer                       NOT NULL                                 /* PersID */,
   Srt                           Smallint                      NOT NULL                                 /* SrtNLReisdocID */,
   Nr                            Varchar(9)                              CHECK (Nr <> '' )              /* ReisdocNr */,
   AutVanAfgifte                 Varchar(6)                              CHECK (AutVanAfgifte <> '' )   /* AutVanAfgifteReisdocCode */,
   DatIngangDoc                  Integer                                                                /* DatEvtDeelsOnbekend */,
   DatEindeDoc                   Integer                                                                /* DatEvtDeelsOnbekend */,
   DatUitgifte                   Integer                                                                /* DatEvtDeelsOnbekend */,
   DatInhingVermissing           Integer                                                                /* DatEvtDeelsOnbekend */,
   AandInhingVermissing          Smallint                                                               /* AandInhingVermissingReisdocI */,
   CONSTRAINT pk_PersReisdoc PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_PersReisdoc OWNED BY Kern.PersReisdoc.ID;

CREATE SEQUENCE Kern.seq_PersVerificatie START WITH 1;
CREATE TABLE Kern.PersVerificatie (
   ID                            BigInt                        NOT NULL  DEFAULT nextval('Kern.seq_PersVerificatie')   /* PersVerificatieID */,
   Geverifieerde                 Integer                       NOT NULL                                 /* PersID */,
   Partij                        Smallint                      NOT NULL                                 /* PartijID */,
   Srt                           Varchar(80)                   NOT NULL  CHECK (Srt <> '' )             /* NaamEnumeratiewaarde */,
   Dat                           Integer                                                                /* DatEvtDeelsOnbekend */,
   CONSTRAINT pk_PersVerificatie PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_PersVerificatie OWNED BY Kern.PersVerificatie.ID;

CREATE SEQUENCE Kern.seq_PersVerstrbeperking START WITH 1;
CREATE TABLE Kern.PersVerstrbeperking (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_PersVerstrbeperking')   /* PersVerstrbeperkingID */,
   Pers                          Integer                       NOT NULL                                 /* PersID */,
   Partij                        Smallint                                                               /* PartijID */,
   OmsDerde                      Varchar(250)                            CHECK (OmsDerde <> '' )        /* OmsEnumeratiewaarde */,
   GemVerordening                Smallint                                                               /* PartijID */,
   CONSTRAINT pk_PersVerstrbeperking PRIMARY KEY (ID),
   CONSTRAINT uc_PersVerstrbeperking_Pers_Partij_OmsDerde_GemVerordening UNIQUE (Pers, Partij, OmsDerde, GemVerordening)
);
ALTER SEQUENCE Kern.seq_PersVerstrbeperking OWNED BY Kern.PersVerstrbeperking.ID;

CREATE SEQUENCE Kern.seq_PersVoornaam START WITH 1;
CREATE TABLE Kern.PersVoornaam (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_PersVoornaam')   /* PersVoornaamID */,
   Pers                          Integer                       NOT NULL                                 /* PersID */,
   Volgnr                        Integer                       NOT NULL                                 /* Volgnr */,
   Naam                          Varchar(200)                            CHECK (Naam <> '' )            /* Voornaam */,
   CONSTRAINT pk_PersVoornaam PRIMARY KEY (ID),
   CONSTRAINT uc_PersVoornaam_Pers_Volgnr UNIQUE (Pers, Volgnr)
);
ALTER SEQUENCE Kern.seq_PersVoornaam OWNED BY Kern.PersVoornaam.ID;

CREATE SEQUENCE Kern.seq_PersCache START WITH 1;
CREATE TABLE Kern.PersCache (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_PersCache')   /* PersID */,
   Pers                          Integer                       NOT NULL                                 /* PersID */,
   Versienr                      Smallint                      NOT NULL                                 /* VersienrKlein */,
   PersHistorieVolledigChecksum  Varchar(40)                             CHECK (PersHistorieVolledigChecksum <> '' )   /* Checksum */,
   PersHistorieVolledigGegevens  bytea                                                                  /* Byteaopslag */,
   AfnemerindicatieChecksum      Varchar(40)                             CHECK (AfnemerindicatieChecksum <> '' )   /* Checksum */,
   AfnemerindicatieGegevens      bytea                                                                  /* Byteaopslag */,
   CONSTRAINT pk_PersCache PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_PersCache OWNED BY Kern.PersCache.ID;

CREATE SEQUENCE Kern.seq_Regelverantwoording START WITH 1;
CREATE TABLE Kern.Regelverantwoording (
   ID                            BigInt                        NOT NULL  DEFAULT nextval('Kern.seq_Regelverantwoording')   /* RegelverantwoordingID */,
   Actie                         BigInt                        NOT NULL                                 /* ActieID */,
   Regel                         Integer                       NOT NULL                                 /* RegelID */,
   CONSTRAINT pk_Regelverantwoording PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_Regelverantwoording OWNED BY Kern.Regelverantwoording.ID;

CREATE SEQUENCE Kern.seq_Relatie START WITH 1;
CREATE TABLE Kern.Relatie (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_Relatie')   /* RelatieID */,
   Srt                           Smallint                      NOT NULL                                 /* SrtRelatieID */,
   DatAanv                       Integer                                                                /* DatEvtDeelsOnbekend */,
   GemAanv                       Smallint                                                               /* GemID */,
   WplnaamAanv                   Varchar(80)                             CHECK (WplnaamAanv <> '' )     /* NaamEnumeratiewaarde */,
   BLPlaatsAanv                  Varchar(40)                             CHECK (BLPlaatsAanv <> '' )    /* BLPlaats */,
   BLRegioAanv                   Varchar(35)                             CHECK (BLRegioAanv <> '' )     /* BLRegio */,
   OmsLocAanv                    Varchar(40)                             CHECK (OmsLocAanv <> '' )      /* Locoms */,
   LandGebiedAanv                Integer                                                                /* LandGebiedID */,
   RdnEinde                      Smallint                                                               /* RdnEindeRelatieID */,
   DatEinde                      Integer                                                                /* DatEvtDeelsOnbekend */,
   GemEinde                      Smallint                                                               /* GemID */,
   WplnaamEinde                  Varchar(80)                             CHECK (WplnaamEinde <> '' )    /* NaamEnumeratiewaarde */,
   BLPlaatsEinde                 Varchar(40)                             CHECK (BLPlaatsEinde <> '' )   /* BLPlaats */,
   BLRegioEinde                  Varchar(35)                             CHECK (BLRegioEinde <> '' )    /* BLRegio */,
   OmsLocEinde                   Varchar(40)                             CHECK (OmsLocEinde <> '' )     /* Locoms */,
   LandGebiedEinde               Integer                                                                /* LandGebiedID */,
   CONSTRAINT pk_Relatie PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_Relatie OWNED BY Kern.Relatie.ID;

CREATE SEQUENCE Kern.seq_Terugmelding START WITH 1;
CREATE TABLE Kern.Terugmelding (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_Terugmelding')   /* TerugmeldingID */,
   TerugmeldendePartij           Smallint                      NOT NULL                                 /* PartijID */,
   Pers                          Integer                                                                /* PersID */,
   Bijhgem                       Smallint                                                               /* PartijID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   Onderzoek                     Integer                                                                /* OnderzoekID */,
   Status                        Smallint                                                               /* StatusTerugmeldingID */,
   Toelichting                   Text                                                                   /* TekstTerugmelding */,
   KenmerkMeldendePartij         Varchar(40)                             CHECK (KenmerkMeldendePartij <> '' )   /* KenmerkTerugmelding */,
   Email                         Varchar(254)                            CHECK (Email <> '' )           /* Emailadres */,
   Telefoonnr                    Varchar(20)                             CHECK (Telefoonnr <> '' )      /* Telefoonnr */,
   Voornamen                     Varchar(200)                            CHECK (Voornamen <> '' )       /* Voornamen */,
   Voorvoegsel                   Varchar(10)                             CHECK (Voorvoegsel <> '' )     /* Voorvoegsel */,
   Scheidingsteken               Varchar(1)                              CHECK (Scheidingsteken <> '' )   /* Scheidingsteken */,
   Geslnaamstam                  Varchar(200)                            CHECK (Geslnaamstam <> '' )    /* Geslnaamstam */,
   CONSTRAINT pk_Terugmelding PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_Terugmelding OWNED BY Kern.Terugmelding.ID;

CREATE SEQUENCE MigBlok.seq_Blokkering START WITH 1;
CREATE TABLE MigBlok.Blokkering (
   ID                            Integer                       NOT NULL  DEFAULT nextval('MigBlok.seq_Blokkering')   /* BlokkeringID */,
   ANr                           Bigint                        NOT NULL                                 /* ANr */,
   RdnBlokkering                 Smallint                                                               /* RdnBlokkeringID */,
   ProcessInstantieID            BigInt                                                                 /* ProcessInstantieID */,
   LO3GemVestiging               Varchar(4)                              CHECK (LO3GemVestiging <> '' )   /* LO3GemCode */,
   LO3GemReg                     Varchar(4)                              CHECK (LO3GemReg <> '' )       /* LO3GemCode */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   CONSTRAINT pk_Blokkering PRIMARY KEY (ID),
   CONSTRAINT uc_Blokkering_ANr UNIQUE (ANr)
);
ALTER SEQUENCE MigBlok.seq_Blokkering OWNED BY MigBlok.Blokkering.ID;

CREATE SEQUENCE VerConv.seq_LO3AandOuder START WITH 1;
CREATE TABLE VerConv.LO3AandOuder (
   ID                            BigInt                        NOT NULL  DEFAULT nextval('VerConv.seq_LO3AandOuder')   /* LO3AandOuderID */,
   Ouder                         Integer                       NOT NULL                                 /* BetrID */,
   Srt                           Smallint                      NOT NULL                                 /* LO3SrtAandOuderID */,
   CONSTRAINT pk_LO3AandOuder PRIMARY KEY (ID),
   CONSTRAINT uc_LO3AandOuder_Ouder_Srt UNIQUE (Ouder, Srt)
);
ALTER SEQUENCE VerConv.seq_LO3AandOuder OWNED BY VerConv.LO3AandOuder.ID;

CREATE SEQUENCE VerConv.seq_LO3Ber START WITH 1;
CREATE TABLE VerConv.LO3Ber (
   ID                            BigInt                        NOT NULL  DEFAULT nextval('VerConv.seq_LO3Ber')   /* LO3BerID */,
   IndBersrtOnderdeelLO3Stelsel  Boolean                       NOT NULL                                 /* JaNee */,
   Referentie                    Varchar(36)                   NOT NULL  CHECK (Referentie <> '' )      /* LO3Referentie */,
   Bron                          Integer                       NOT NULL                                 /* ConvID */,
   ANr                           Bigint                                                                 /* ANr */,
   Pers                          Integer                                                                /* PersID */,
   Berdata                       bytea                         NOT NULL                                 /* Byteaopslag */,
   Checksum                      Varchar(40)                   NOT NULL  CHECK (Checksum <> '' )        /* Checksum */,
   TsConversie                   Timestamp with time zone      NOT NULL                                 /* Ts */,
   Foutcode                      Varchar(200)                            CHECK (Foutcode <> '' )        /* LO3Foutcode */,
   Verwerkingsmelding            Text                                                                   /* LO3Verwerkingsmelding */,
   CONSTRAINT pk_LO3Ber PRIMARY KEY (ID)
);
ALTER SEQUENCE VerConv.seq_LO3Ber OWNED BY VerConv.LO3Ber.ID;

CREATE SEQUENCE VerConv.seq_LO3Melding START WITH 1;
CREATE TABLE VerConv.LO3Melding (
   ID                            BigInt                        NOT NULL  DEFAULT nextval('VerConv.seq_LO3Melding')   /* LO3MeldingID */,
   LO3Voorkomen                  BigInt                        NOT NULL                                 /* LO3VoorkomenID */,
   Srt                           Integer                       NOT NULL                                 /* ConvID */,
   LogSeverity                   Integer                       NOT NULL                                 /* ConvID */,
   Groep                         Varchar(2)                              CHECK (Groep <> '' )           /* LO3Groep */,
   Rubr                          Varchar(2)                              CHECK (Rubr <> '' )            /* LO3RubriekExclCategorieEnGro */,
   CONSTRAINT pk_LO3Melding PRIMARY KEY (ID),
   CONSTRAINT uc_LO3Melding_LO3Voorkomen_Srt_Groep_Rubr UNIQUE (LO3Voorkomen, Srt, Groep, Rubr)
);
ALTER SEQUENCE VerConv.seq_LO3Melding OWNED BY VerConv.LO3Melding.ID;

CREATE SEQUENCE VerConv.seq_LO3Voorkomen START WITH 1;
CREATE TABLE VerConv.LO3Voorkomen (
   ID                            BigInt                        NOT NULL  DEFAULT nextval('VerConv.seq_LO3Voorkomen')   /* LO3VoorkomenID */,
   LO3Ber                        BigInt                        NOT NULL                                 /* LO3BerID */,
   LO3Categorie                  Varchar(2)                    NOT NULL  CHECK (LO3Categorie <> '' )    /* LO3Categorie */,
   LO3Stapelvolgnr               Integer                                                                /* Volgnr */,
   LO3Voorkomenvolgnr            Integer                                                                /* Volgnr */,
   Actie                         BigInt                                                                 /* ActieID */,
   LO3ConversieSortering         Smallint                                                               /* Sorteervolgorde */,
   CONSTRAINT pk_LO3Voorkomen PRIMARY KEY (ID),
   CONSTRAINT uc_LO3Voorkomen_LO3Ber_LO3Categorie_LO3Stapelvolgnr_LO3Voorkome UNIQUE (LO3Ber, LO3Categorie, LO3Stapelvolgnr, LO3Voorkomenvolgnr),
   CONSTRAINT uc_LO3Voorkomen_Actie UNIQUE (Actie) DEFERRABLE INITIALLY DEFERRED
);
ALTER SEQUENCE VerConv.seq_LO3Voorkomen OWNED BY VerConv.LO3Voorkomen.ID;

-- Materiele historie tabellen -------------------------------------------------
CREATE SEQUENCE AutAut.seq_His_PersAfnemerindicatie START WITH 1;
CREATE TABLE AutAut.His_PersAfnemerindicatie (
   ID                            BigInt                        NOT NULL  DEFAULT nextval('AutAut.seq_His_PersAfnemerindicatie')   /* His_PersAfnemerindicatieID */,
   PersAfnemerindicatie          BigInt                        NOT NULL                                 /* PersAfnemerindicatieID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   DienstInh                     Integer                                                                /* DienstID */,
   DienstVerval                  Integer                                                                /* DienstID */,
   DatAanvMaterielePeriode       Integer                                                                /* DatEvtDeelsOnbekend */,
   DatEindeVolgen                Integer                                                                /* Dat */,
   CONSTRAINT pk_His_PersAfnemerindicatie PRIMARY KEY (ID),
   CONSTRAINT uc_His_PersAfnemerindicatie_PersAfnemerindicatie_TsReg UNIQUE (PersAfnemerindicatie, TsReg)
);
ALTER SEQUENCE AutAut.seq_His_PersAfnemerindicatie OWNED BY AutAut.His_PersAfnemerindicatie.ID;

CREATE SEQUENCE Kern.seq_His_Betr START WITH 1;
CREATE TABLE Kern.His_Betr (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_Betr')   /* His_BetrID */,
   Betr                          Integer                       NOT NULL                                 /* BetrID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                        NOT NULL                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   CONSTRAINT pk_His_Betr PRIMARY KEY (ID),
   CONSTRAINT uc_His_Betr_Betr UNIQUE (Betr)
);
ALTER SEQUENCE Kern.seq_His_Betr OWNED BY Kern.His_Betr.ID;

CREATE SEQUENCE Kern.seq_His_Doc START WITH 1;
CREATE TABLE Kern.His_Doc (
   ID                            BigInt                        NOT NULL  DEFAULT nextval('Kern.seq_His_Doc')   /* His_DocID */,
   Doc                           BigInt                        NOT NULL                                 /* DocID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                        NOT NULL                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   Ident                         Varchar(20)                             CHECK (Ident <> '' )           /* DocIdent */,
   Aktenr                        Varchar(7)                              CHECK (Aktenr <> '' )          /* Aktenr */,
   Oms                           Varchar(40)                             CHECK (Oms <> '' )             /* DocOms */,
   Partij                        Smallint                      NOT NULL                                 /* PartijID */,
   CONSTRAINT pk_His_Doc PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_Doc OWNED BY Kern.His_Doc.ID;

CREATE SEQUENCE Kern.seq_His_Onderzoek START WITH 1;
CREATE TABLE Kern.His_Onderzoek (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_Onderzoek')   /* His_OnderzoekID */,
   Onderzoek                     Integer                       NOT NULL                                 /* OnderzoekID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                        NOT NULL                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   DatAanv                       Integer                       NOT NULL                                 /* DatEvtDeelsOnbekend */,
   VerwachteAfhandeldat          Integer                       NOT NULL                                 /* DatEvtDeelsOnbekend */,
   DatEinde                      Integer                                                                /* DatEvtDeelsOnbekend */,
   Oms                           Text                                                                   /* OnderzoekOms */,
   Status                        Smallint                      NOT NULL                                 /* StatusOnderzoekID */,
   CONSTRAINT pk_His_Onderzoek PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_Onderzoek OWNED BY Kern.His_Onderzoek.ID;

CREATE SEQUENCE Kern.seq_His_OnderzoekAfgeleidAdminis START WITH 1;
CREATE TABLE Kern.His_OnderzoekAfgeleidAdminis (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_OnderzoekAfgeleidAdminis')   /* His_OnderzoekAfgeleidAdminis */,
   Onderzoek                     Integer                       NOT NULL                                 /* OnderzoekID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                        NOT NULL                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   AdmHnd                        BigInt                        NOT NULL                                 /* AdmHndID */,
   CONSTRAINT pk_His_OnderzoekAfgeleidAdminis PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_OnderzoekAfgeleidAdminis OWNED BY Kern.His_OnderzoekAfgeleidAdminis.ID;

CREATE SEQUENCE Kern.seq_His_OuderOuderlijkGezag START WITH 1;
CREATE TABLE Kern.His_OuderOuderlijkGezag (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_OuderOuderlijkGezag')   /* His_OuderOuderlijkGezagID */,
   Betr                          Integer                       NOT NULL                                 /* BetrID */,
   DatAanvGel                    Integer                       NOT NULL                                 /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                        NOT NULL                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieAanpGel                  BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   IndOuderHeeftGezag            Boolean                       NOT NULL                                 /* JaNee */,
   CONSTRAINT pk_His_OuderOuderlijkGezag PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_OuderOuderlijkGezag OWNED BY Kern.His_OuderOuderlijkGezag.ID;

CREATE SEQUENCE Kern.seq_His_OuderOuderschap START WITH 1;
CREATE TABLE Kern.His_OuderOuderschap (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_OuderOuderschap')   /* His_OuderOuderschapID */,
   Betr                          Integer                       NOT NULL                                 /* BetrID */,
   DatAanvGel                    Integer                       NOT NULL                                 /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                        NOT NULL                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieAanpGel                  BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   IndOuder                      Boolean                       NOT NULL  CHECK (IndOuder IN (true))     /* Ja */,
   IndOuderUitWieKindIsGeboren   Boolean                                                                /* JaNee */,
   CONSTRAINT pk_His_OuderOuderschap PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_OuderOuderschap OWNED BY Kern.His_OuderOuderschap.ID;

CREATE SEQUENCE Kern.seq_His_PartijOnderzoek START WITH 1;
CREATE TABLE Kern.His_PartijOnderzoek (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PartijOnderzoek')   /* His_PartijOnderzoekID */,
   PartijOnderzoek               Integer                       NOT NULL                                 /* PartijOnderzoekID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                                                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   Rol                           Smallint                      NOT NULL                                 /* SrtPartijOnderzoekID */,
   CONSTRAINT pk_His_PartijOnderzoek PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_PartijOnderzoek OWNED BY Kern.His_PartijOnderzoek.ID;

CREATE SEQUENCE Kern.seq_His_PersAfgeleidAdministrati START WITH 1;
CREATE TABLE Kern.His_PersAfgeleidAdministrati (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersAfgeleidAdministrati')   /* His_PersAfgeleidAdministrati */,
   Pers                          Integer                       NOT NULL                                 /* PersID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                        NOT NULL                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   AdmHnd                        BigInt                        NOT NULL                                 /* AdmHndID */,
   TsLaatsteWijz                 Timestamp with time zone      NOT NULL                                 /* Ts */,
   Sorteervolgorde               Smallint                      NOT NULL                                 /* Sorteervolgorde */,
   IndOnverwBijhvoorstelNietIng  Boolean                       NOT NULL                                 /* JaNee */,
   TsLaatsteWijzGBASystematiek   Timestamp with time zone                                               /* Ts */,
   CONSTRAINT pk_His_PersAfgeleidAdministrati PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_PersAfgeleidAdministrati OWNED BY Kern.His_PersAfgeleidAdministrati.ID;

CREATE SEQUENCE Kern.seq_His_PersBijhouding START WITH 1;
CREATE TABLE Kern.His_PersBijhouding (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersBijhouding')   /* His_PersBijhoudingID */,
   Pers                          Integer                       NOT NULL                                 /* PersID */,
   DatAanvGel                    Integer                       NOT NULL                                 /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                        NOT NULL                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieAanpGel                  BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   Bijhpartij                    Smallint                      NOT NULL                                 /* PartijID */,
   Bijhaard                      Smallint                      NOT NULL                                 /* BijhaardID */,
   NadereBijhaard                Smallint                      NOT NULL                                 /* NadereBijhaardID */,
   IndOnverwDocAanw              Boolean                       NOT NULL                                 /* JaNee */,
   CONSTRAINT pk_His_PersBijhouding PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_PersBijhouding OWNED BY Kern.His_PersBijhouding.ID;

CREATE SEQUENCE Kern.seq_His_PersDeelnEUVerkiezingen START WITH 1;
CREATE TABLE Kern.His_PersDeelnEUVerkiezingen (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersDeelnEUVerkiezingen')   /* His_PersDeelnEUVerkiezingenI */,
   Pers                          Integer                       NOT NULL                                 /* PersID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                        NOT NULL                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   IndDeelnEUVerkiezingen        Boolean                       NOT NULL                                 /* JaNee */,
   DatAanlAanpDeelnEUVerkiezing  Integer                                                                /* DatEvtDeelsOnbekend */,
   DatVoorzEindeUitslEUVerkiezi  Integer                                                                /* DatEvtDeelsOnbekend */,
   CONSTRAINT pk_His_PersDeelnEUVerkiezingen PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_PersDeelnEUVerkiezingen OWNED BY Kern.His_PersDeelnEUVerkiezingen.ID;

CREATE SEQUENCE Kern.seq_His_PersGeboorte START WITH 1;
CREATE TABLE Kern.His_PersGeboorte (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersGeboorte')   /* His_PersGeboorteID */,
   Pers                          Integer                       NOT NULL                                 /* PersID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                        NOT NULL                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   DatGeboorte                   Integer                       NOT NULL                                 /* DatEvtDeelsOnbekend */,
   GemGeboorte                   Smallint                                                               /* GemID */,
   WplnaamGeboorte               Varchar(80)                             CHECK (WplnaamGeboorte <> '' )   /* NaamEnumeratiewaarde */,
   BLPlaatsGeboorte              Varchar(40)                             CHECK (BLPlaatsGeboorte <> '' )   /* BLPlaats */,
   BLRegioGeboorte               Varchar(35)                             CHECK (BLRegioGeboorte <> '' )   /* BLRegio */,
   OmsLocGeboorte                Varchar(40)                             CHECK (OmsLocGeboorte <> '' )   /* Locoms */,
   LandGebiedGeboorte            Integer                       NOT NULL                                 /* LandGebiedID */,
   CONSTRAINT pk_His_PersGeboorte PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_PersGeboorte OWNED BY Kern.His_PersGeboorte.ID;

CREATE SEQUENCE Kern.seq_His_PersGeslachtsaand START WITH 1;
CREATE TABLE Kern.His_PersGeslachtsaand (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersGeslachtsaand')   /* His_PersGeslachtsaandID */,
   Pers                          Integer                       NOT NULL                                 /* PersID */,
   DatAanvGel                    Integer                       NOT NULL                                 /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                        NOT NULL                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieAanpGel                  BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   Geslachtsaand                 Smallint                      NOT NULL                                 /* GeslachtsaandID */,
   CONSTRAINT pk_His_PersGeslachtsaand PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_PersGeslachtsaand OWNED BY Kern.His_PersGeslachtsaand.ID;

CREATE SEQUENCE Kern.seq_His_PersIDs START WITH 1;
CREATE TABLE Kern.His_PersIDs (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersIDs')   /* His_PersIDsID */,
   Pers                          Integer                       NOT NULL                                 /* PersID */,
   DatAanvGel                    Integer                       NOT NULL                                 /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                        NOT NULL                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieAanpGel                  BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   BSN                           Integer                                                                /* BSN */,
   ANr                           Bigint                                                                 /* ANr */,
   CONSTRAINT pk_His_PersIDs PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_PersIDs OWNED BY Kern.His_PersIDs.ID;

CREATE SEQUENCE Kern.seq_His_PersInschr START WITH 1;
CREATE TABLE Kern.His_PersInschr (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersInschr')   /* His_PersInschrID */,
   Pers                          Integer                       NOT NULL                                 /* PersID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                        NOT NULL                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   DatInschr                     Integer                       NOT NULL                                 /* DatEvtDeelsOnbekend */,
   Versienr                      BigInt                        NOT NULL                                 /* Versienr */,
   Dattijdstempel                Timestamp with time zone      NOT NULL                                 /* Ts */,
   CONSTRAINT pk_His_PersInschr PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_PersInschr OWNED BY Kern.His_PersInschr.ID;

CREATE SEQUENCE Kern.seq_His_PersMigratie START WITH 1;
CREATE TABLE Kern.His_PersMigratie (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersMigratie')   /* His_PersMigratieID */,
   Pers                          Integer                       NOT NULL                                 /* PersID */,
   DatAanvGel                    Integer                       NOT NULL                                 /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                        NOT NULL                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieAanpGel                  BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   SrtMigratie                   Smallint                      NOT NULL                                 /* SrtMigratieID */,
   RdnWijzMigratie               Smallint                                                               /* RdnWijzVerblijfID */,
   AangMigratie                  Smallint                                                               /* AangID */,
   LandGebiedMigratie            Integer                                                                /* LandGebiedID */,
   BLAdresRegel1Migratie         Varchar(40)                             CHECK (BLAdresRegel1Migratie <> '' )   /* Adresregel */,
   BLAdresRegel2Migratie         Varchar(40)                             CHECK (BLAdresRegel2Migratie <> '' )   /* Adresregel */,
   BLAdresRegel3Migratie         Varchar(40)                             CHECK (BLAdresRegel3Migratie <> '' )   /* Adresregel */,
   BLAdresRegel4Migratie         Varchar(40)                             CHECK (BLAdresRegel4Migratie <> '' )   /* Adresregel */,
   BLAdresRegel5Migratie         Varchar(40)                             CHECK (BLAdresRegel5Migratie <> '' )   /* Adresregel */,
   BLAdresRegel6Migratie         Varchar(40)                             CHECK (BLAdresRegel6Migratie <> '' )   /* Adresregel */,
   CONSTRAINT pk_His_PersMigratie PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_PersMigratie OWNED BY Kern.His_PersMigratie.ID;

CREATE SEQUENCE Kern.seq_His_PersNaamgebruik START WITH 1;
CREATE TABLE Kern.His_PersNaamgebruik (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersNaamgebruik')   /* His_PersNaamgebruikID */,
   Pers                          Integer                       NOT NULL                                 /* PersID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                        NOT NULL                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   Naamgebruik                   Smallint                      NOT NULL                                 /* NaamgebruikID */,
   IndNaamgebruikAfgeleid        Boolean                       NOT NULL                                 /* JaNee */,
   PredicaatNaamgebruik          Smallint                                                               /* PredicaatID */,
   VoornamenNaamgebruik          Varchar(200)                            CHECK (VoornamenNaamgebruik <> '' )   /* Voornamen */,
   AdellijkeTitelNaamgebruik     Smallint                                                               /* AdellijkeTitelID */,
   VoorvoegselNaamgebruik        Varchar(10)                             CHECK (VoorvoegselNaamgebruik <> '' )   /* Voorvoegsel */,
   ScheidingstekenNaamgebruik    Varchar(1)                              CHECK (ScheidingstekenNaamgebruik <> '' )   /* Scheidingsteken */,
   GeslnaamstamNaamgebruik       Varchar(200)                  NOT NULL  CHECK (GeslnaamstamNaamgebruik <> '' )   /* Geslnaamstam */,
   CONSTRAINT pk_His_PersNaamgebruik PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_PersNaamgebruik OWNED BY Kern.His_PersNaamgebruik.ID;

CREATE SEQUENCE Kern.seq_His_PersNrverwijzing START WITH 1;
CREATE TABLE Kern.His_PersNrverwijzing (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersNrverwijzing')   /* His_PersNrverwijzingID */,
   Pers                          Integer                       NOT NULL                                 /* PersID */,
   DatAanvGel                    Integer                       NOT NULL                                 /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                        NOT NULL                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieAanpGel                  BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   VorigeBSN                     Integer                                                                /* BSN */,
   VolgendeBSN                   Integer                                                                /* BSN */,
   VorigeANr                     Bigint                                                                 /* ANr */,
   VolgendeANr                   Bigint                                                                 /* ANr */,
   CONSTRAINT pk_His_PersNrverwijzing PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_PersNrverwijzing OWNED BY Kern.His_PersNrverwijzing.ID;

CREATE SEQUENCE Kern.seq_His_PersOverlijden START WITH 1;
CREATE TABLE Kern.His_PersOverlijden (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersOverlijden')   /* His_PersOverlijdenID */,
   Pers                          Integer                       NOT NULL                                 /* PersID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                        NOT NULL                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   DatOverlijden                 Integer                       NOT NULL                                 /* DatEvtDeelsOnbekend */,
   GemOverlijden                 Smallint                                                               /* GemID */,
   WplnaamOverlijden             Varchar(80)                             CHECK (WplnaamOverlijden <> '' )   /* NaamEnumeratiewaarde */,
   BLPlaatsOverlijden            Varchar(40)                             CHECK (BLPlaatsOverlijden <> '' )   /* BLPlaats */,
   BLRegioOverlijden             Varchar(35)                             CHECK (BLRegioOverlijden <> '' )   /* BLRegio */,
   OmsLocOverlijden              Varchar(40)                             CHECK (OmsLocOverlijden <> '' )   /* Locoms */,
   LandGebiedOverlijden          Integer                       NOT NULL                                 /* LandGebiedID */,
   CONSTRAINT pk_His_PersOverlijden PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_PersOverlijden OWNED BY Kern.His_PersOverlijden.ID;

CREATE SEQUENCE Kern.seq_His_PersPK START WITH 1;
CREATE TABLE Kern.His_PersPK (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersPK')   /* His_PersPKID */,
   Pers                          Integer                       NOT NULL                                 /* PersID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                        NOT NULL                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   GemPK                         Smallint                                                               /* PartijID */,
   IndPKVolledigGeconv           Boolean                       NOT NULL                                 /* JaNee */,
   CONSTRAINT pk_His_PersPK PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_PersPK OWNED BY Kern.His_PersPK.ID;

CREATE SEQUENCE Kern.seq_His_PersSamengesteldeNaam START WITH 1;
CREATE TABLE Kern.His_PersSamengesteldeNaam (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersSamengesteldeNaam')   /* His_PersSamengesteldeNaamID */,
   Pers                          Integer                       NOT NULL                                 /* PersID */,
   DatAanvGel                    Integer                       NOT NULL                                 /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                        NOT NULL                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieAanpGel                  BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   IndAfgeleid                   Boolean                       NOT NULL                                 /* JaNee */,
   IndNreeks                     Boolean                       NOT NULL                                 /* JaNee */,
   Predicaat                     Smallint                                                               /* PredicaatID */,
   Voornamen                     Varchar(200)                            CHECK (Voornamen <> '' )       /* Voornamen */,
   AdellijkeTitel                Smallint                                                               /* AdellijkeTitelID */,
   Voorvoegsel                   Varchar(10)                             CHECK (Voorvoegsel <> '' )     /* Voorvoegsel */,
   Scheidingsteken               Varchar(1)                              CHECK (Scheidingsteken <> '' )   /* Scheidingsteken */,
   Geslnaamstam                  Varchar(200)                  NOT NULL  CHECK (Geslnaamstam <> '' )    /* Geslnaamstam */,
   CONSTRAINT pk_His_PersSamengesteldeNaam PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_PersSamengesteldeNaam OWNED BY Kern.His_PersSamengesteldeNaam.ID;

CREATE SEQUENCE Kern.seq_His_PersUitslKiesr START WITH 1;
CREATE TABLE Kern.His_PersUitslKiesr (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersUitslKiesr')   /* His_PersUitslKiesrID */,
   Pers                          Integer                       NOT NULL                                 /* PersID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                        NOT NULL                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   IndUitslKiesr                 Boolean                       NOT NULL  CHECK (IndUitslKiesr IN (true))   /* Ja */,
   DatVoorzEindeUitslKiesr       Integer                                                                /* DatEvtDeelsOnbekend */,
   CONSTRAINT pk_His_PersUitslKiesr PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_PersUitslKiesr OWNED BY Kern.His_PersUitslKiesr.ID;

CREATE SEQUENCE Kern.seq_His_PersVerblijfsr START WITH 1;
CREATE TABLE Kern.His_PersVerblijfsr (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersVerblijfsr')   /* His_PersVerblijfsrID */,
   Pers                          Integer                       NOT NULL                                 /* PersID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                        NOT NULL                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   AandVerblijfsr                Smallint                      NOT NULL                                 /* AandVerblijfsrID */,
   DatAanvVerblijfsr             Integer                       NOT NULL                                 /* DatEvtDeelsOnbekend */,
   DatMededelingVerblijfsr       Integer                       NOT NULL                                 /* DatEvtDeelsOnbekend */,
   DatVoorzEindeVerblijfsr       Integer                                                                /* DatEvtDeelsOnbekend */,
   CONSTRAINT pk_His_PersVerblijfsr PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_PersVerblijfsr OWNED BY Kern.His_PersVerblijfsr.ID;

CREATE SEQUENCE Kern.seq_His_PersAdres START WITH 1;
CREATE TABLE Kern.His_PersAdres (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersAdres')   /* His_PersAdresID */,
   PersAdres                     Integer                       NOT NULL                                 /* PersAdresID */,
   DatAanvGel                    Integer                       NOT NULL                                 /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                        NOT NULL                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieAanpGel                  BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   Srt                           Smallint                      NOT NULL                                 /* SrtAdresID */,
   RdnWijz                       Smallint                      NOT NULL                                 /* RdnWijzVerblijfID */,
   AangAdresh                    Smallint                                                               /* AangID */,
   DatAanvAdresh                 Integer                                                                /* DatEvtDeelsOnbekend */,
   IdentcodeAdresseerbaarObject  Varchar(16)                             CHECK (IdentcodeAdresseerbaarObject <> '' )   /* IdentcodeAdresseerbaarObject */,
   IdentcodeNraand               Varchar(16)                             CHECK (IdentcodeNraand <> '' )   /* IdentcodeNraand */,
   Gem                           Smallint                                                               /* GemID */,
   NOR                           Varchar(80)                             CHECK (NOR <> '' )             /* NOR */,
   AfgekorteNOR                  Varchar(24)                             CHECK (AfgekorteNOR <> '' )    /* AfgekorteNOR */,
   Gemdeel                       Varchar(24)                             CHECK (Gemdeel <> '' )         /* Gemdeel */,
   Huisnr                        Integer                                                                /* Huisnr */,
   Huisletter                    Varchar(1)                              CHECK (Huisletter <> '' )      /* Huisletter */,
   Huisnrtoevoeging              Varchar(4)                              CHECK (Huisnrtoevoeging <> '' )   /* Huisnrtoevoeging */,
   Postcode                      Varchar(6)                              CHECK (Postcode <> '' )        /* Postcode */,
   Wplnaam                       Varchar(80)                             CHECK (Wplnaam <> '' )         /* NaamEnumeratiewaarde */,
   LocTenOpzichteVanAdres        Varchar(2)                              CHECK (LocTenOpzichteVanAdres IN ('by','to'))   /* LocTenOpzichteVanAdres */,
   Locoms                        Varchar(40)                             CHECK (Locoms <> '' )          /* Locoms */,
   BLAdresRegel1                 Varchar(40)                             CHECK (BLAdresRegel1 <> '' )   /* Adresregel */,
   BLAdresRegel2                 Varchar(40)                             CHECK (BLAdresRegel2 <> '' )   /* Adresregel */,
   BLAdresRegel3                 Varchar(40)                             CHECK (BLAdresRegel3 <> '' )   /* Adresregel */,
   BLAdresRegel4                 Varchar(40)                             CHECK (BLAdresRegel4 <> '' )   /* Adresregel */,
   BLAdresRegel5                 Varchar(40)                             CHECK (BLAdresRegel5 <> '' )   /* Adresregel */,
   BLAdresRegel6                 Varchar(40)                             CHECK (BLAdresRegel6 <> '' )   /* Adresregel */,
   LandGebied                    Integer                       NOT NULL                                 /* LandGebiedID */,
   IndPersAangetroffenOpAdres    Boolean                                 CHECK (IndPersAangetroffenOpAdres IN (false))   /* Nee */,
   CONSTRAINT pk_His_PersAdres PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_PersAdres OWNED BY Kern.His_PersAdres.ID;

CREATE SEQUENCE Kern.seq_His_PersGeslnaamcomp START WITH 1;
CREATE TABLE Kern.His_PersGeslnaamcomp (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersGeslnaamcomp')   /* His_PersGeslnaamcompID */,
   PersGeslnaamcomp              Integer                       NOT NULL                                 /* PersGeslnaamID */,
   DatAanvGel                    Integer                       NOT NULL                                 /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                        NOT NULL                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieAanpGel                  BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   Predicaat                     Smallint                                                               /* PredicaatID */,
   AdellijkeTitel                Smallint                                                               /* AdellijkeTitelID */,
   Voorvoegsel                   Varchar(10)                             CHECK (Voorvoegsel <> '' )     /* Voorvoegsel */,
   Scheidingsteken               Varchar(1)                              CHECK (Scheidingsteken <> '' )   /* Scheidingsteken */,
   Stam                          Varchar(200)                  NOT NULL  CHECK (Stam <> '' )            /* Geslnaamstam */,
   CONSTRAINT pk_His_PersGeslnaamcomp PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_PersGeslnaamcomp OWNED BY Kern.His_PersGeslnaamcomp.ID;

CREATE SEQUENCE Kern.seq_His_PersIndicatie START WITH 1;
CREATE TABLE Kern.His_PersIndicatie (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersIndicatie')   /* His_PersIndicatieID */,
   PersIndicatie                 Integer                       NOT NULL                                 /* PersIndicatieID */,
   DatAanvGel                    Integer                                                                /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                                                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieAanpGel                  BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   Waarde                        Boolean                       NOT NULL  CHECK (Waarde IN (true))       /* Ja */,
   MigrRdnOpnameNation           Varchar(3)                              CHECK (MigrRdnOpnameNation <> '' )   /* LO3RdnOpnameNation */,
   MigrRdnBeeindigenNation       Varchar(3)                              CHECK (MigrRdnBeeindigenNation <> '' )   /* ConvRdnBeeindigenNation */,
   CONSTRAINT pk_His_PersIndicatie PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_PersIndicatie OWNED BY Kern.His_PersIndicatie.ID;

CREATE SEQUENCE Kern.seq_His_PersNation START WITH 1;
CREATE TABLE Kern.His_PersNation (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersNation')   /* His_PersNationID */,
   PersNation                    Integer                       NOT NULL                                 /* PersNationID */,
   DatAanvGel                    Integer                       NOT NULL                                 /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                        NOT NULL                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieAanpGel                  BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   RdnVerk                       Smallint                                                               /* RdnVerkNLNationID */,
   RdnVerlies                    Smallint                                                               /* RdnVerliesNLNationID */,
   IndBijhoudingBeeindigd        Boolean                                 CHECK (IndBijhoudingBeeindigd IN (true))   /* Ja */,
   MigrRdnOpnameNation           Varchar(3)                              CHECK (MigrRdnOpnameNation <> '' )   /* LO3RdnOpnameNation */,
   MigrRdnBeeindigenNation       Varchar(3)                              CHECK (MigrRdnBeeindigenNation <> '' )   /* ConvRdnBeeindigenNation */,
   MigrDatEindeBijhouding        Integer                                                                /* DatEvtDeelsOnbekend */,
   CONSTRAINT pk_His_PersNation PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_PersNation OWNED BY Kern.His_PersNation.ID;

CREATE SEQUENCE Kern.seq_His_PersOnderzoek START WITH 1;
CREATE TABLE Kern.His_PersOnderzoek (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersOnderzoek')   /* His_PersOnderzoekID */,
   PersOnderzoek                 Integer                       NOT NULL                                 /* PersOnderzoekID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                                                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   Rol                           Smallint                      NOT NULL                                 /* SrtPersOnderzoekID */,
   CONSTRAINT pk_His_PersOnderzoek PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_PersOnderzoek OWNED BY Kern.His_PersOnderzoek.ID;

CREATE SEQUENCE Kern.seq_His_PersReisdoc START WITH 1;
CREATE TABLE Kern.His_PersReisdoc (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersReisdoc')   /* His_PersReisdocID */,
   PersReisdoc                   Integer                       NOT NULL                                 /* PersReisdocID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                        NOT NULL                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   Nr                            Varchar(9)                    NOT NULL  CHECK (Nr <> '' )              /* ReisdocNr */,
   AutVanAfgifte                 Varchar(6)                    NOT NULL  CHECK (AutVanAfgifte <> '' )   /* AutVanAfgifteReisdocCode */,
   DatIngangDoc                  Integer                       NOT NULL                                 /* DatEvtDeelsOnbekend */,
   DatEindeDoc                   Integer                       NOT NULL                                 /* DatEvtDeelsOnbekend */,
   DatUitgifte                   Integer                       NOT NULL                                 /* DatEvtDeelsOnbekend */,
   DatInhingVermissing           Integer                                                                /* DatEvtDeelsOnbekend */,
   AandInhingVermissing          Smallint                                                               /* AandInhingVermissingReisdocI */,
   CONSTRAINT pk_His_PersReisdoc PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_PersReisdoc OWNED BY Kern.His_PersReisdoc.ID;

CREATE SEQUENCE Kern.seq_His_PersVerificatie START WITH 1;
CREATE TABLE Kern.His_PersVerificatie (
   ID                            BigInt                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersVerificatie')   /* His_PersVerificatieID */,
   PersVerificatie               BigInt                        NOT NULL                                 /* PersVerificatieID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                        NOT NULL                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   Dat                           Integer                       NOT NULL                                 /* DatEvtDeelsOnbekend */,
   CONSTRAINT pk_His_PersVerificatie PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_PersVerificatie OWNED BY Kern.His_PersVerificatie.ID;

CREATE SEQUENCE Kern.seq_His_PersVerstrbeperking START WITH 1;
CREATE TABLE Kern.His_PersVerstrbeperking (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersVerstrbeperking')   /* His_PersVerstrbeperkingID */,
   PersVerstrbeperking           Integer                       NOT NULL                                 /* PersVerstrbeperkingID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                        NOT NULL                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   CONSTRAINT pk_His_PersVerstrbeperking PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_PersVerstrbeperking OWNED BY Kern.His_PersVerstrbeperking.ID;

CREATE SEQUENCE Kern.seq_His_PersVoornaam START WITH 1;
CREATE TABLE Kern.His_PersVoornaam (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersVoornaam')   /* His_PersVoornaamID */,
   PersVoornaam                  Integer                       NOT NULL                                 /* PersVoornaamID */,
   DatAanvGel                    Integer                       NOT NULL                                 /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                        NOT NULL                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieAanpGel                  BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   Naam                          Varchar(200)                  NOT NULL  CHECK (Naam <> '' )            /* Voornaam */,
   CONSTRAINT pk_His_PersVoornaam PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_PersVoornaam OWNED BY Kern.His_PersVoornaam.ID;

CREATE SEQUENCE Kern.seq_His_Relatie START WITH 1;
CREATE TABLE Kern.His_Relatie (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_Relatie')   /* His_RelatieID */,
   Relatie                       Integer                       NOT NULL                                 /* RelatieID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                        NOT NULL                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   DatAanv                       Integer                                                                /* DatEvtDeelsOnbekend */,
   GemAanv                       Smallint                                                               /* GemID */,
   WplnaamAanv                   Varchar(80)                             CHECK (WplnaamAanv <> '' )     /* NaamEnumeratiewaarde */,
   BLPlaatsAanv                  Varchar(40)                             CHECK (BLPlaatsAanv <> '' )    /* BLPlaats */,
   BLRegioAanv                   Varchar(35)                             CHECK (BLRegioAanv <> '' )     /* BLRegio */,
   OmsLocAanv                    Varchar(40)                             CHECK (OmsLocAanv <> '' )      /* Locoms */,
   LandGebiedAanv                Integer                                                                /* LandGebiedID */,
   RdnEinde                      Smallint                                                               /* RdnEindeRelatieID */,
   DatEinde                      Integer                                                                /* DatEvtDeelsOnbekend */,
   GemEinde                      Smallint                                                               /* GemID */,
   WplnaamEinde                  Varchar(80)                             CHECK (WplnaamEinde <> '' )    /* NaamEnumeratiewaarde */,
   BLPlaatsEinde                 Varchar(40)                             CHECK (BLPlaatsEinde <> '' )   /* BLPlaats */,
   BLRegioEinde                  Varchar(35)                             CHECK (BLRegioEinde <> '' )    /* BLRegio */,
   OmsLocEinde                   Varchar(40)                             CHECK (OmsLocEinde <> '' )     /* Locoms */,
   LandGebiedEinde               Integer                                                                /* LandGebiedID */,
   CONSTRAINT pk_His_Relatie PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_Relatie OWNED BY Kern.His_Relatie.ID;

CREATE SEQUENCE Kern.seq_His_Terugmelding START WITH 1;
CREATE TABLE Kern.His_Terugmelding (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_Terugmelding')   /* His_TerugmeldingID */,
   Terugmelding                  Integer                       NOT NULL                                 /* TerugmeldingID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                                                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   Onderzoek                     Integer                                                                /* OnderzoekID */,
   Status                        Smallint                      NOT NULL                                 /* StatusTerugmeldingID */,
   Toelichting                   Text                          NOT NULL                                 /* TekstTerugmelding */,
   KenmerkMeldendePartij         Varchar(40)                   NOT NULL  CHECK (KenmerkMeldendePartij <> '' )   /* KenmerkTerugmelding */,
   CONSTRAINT pk_His_Terugmelding PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_Terugmelding OWNED BY Kern.His_Terugmelding.ID;

CREATE SEQUENCE Kern.seq_His_TerugmeldingContactpers START WITH 1;
CREATE TABLE Kern.His_TerugmeldingContactpers (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_TerugmeldingContactpers')   /* His_TerugmeldingContactpersI */,
   Terugmelding                  Integer                       NOT NULL                                 /* TerugmeldingID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieInh                      BigInt                                                                 /* ActieID */,
   ActieVerval                   BigInt                                                                 /* ActieID */,
   ActieVervalTbvLevMuts         BigInt                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   Email                         Varchar(254)                            CHECK (Email <> '' )           /* Emailadres */,
   Telefoonnr                    Varchar(20)                             CHECK (Telefoonnr <> '' )      /* Telefoonnr */,
   Voornamen                     Varchar(200)                            CHECK (Voornamen <> '' )       /* Voornamen */,
   Voorvoegsel                   Varchar(10)                             CHECK (Voorvoegsel <> '' )     /* Voorvoegsel */,
   Scheidingsteken               Varchar(1)                              CHECK (Scheidingsteken <> '' )   /* Scheidingsteken */,
   Geslnaamstam                  Varchar(200)                            CHECK (Geslnaamstam <> '' )    /* Geslnaamstam */,
   CONSTRAINT pk_His_TerugmeldingContactpers PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_TerugmeldingContactpers OWNED BY Kern.His_TerugmeldingContactpers.ID;

-- Foreign keys ----------------------------------------------------------------
ALTER TABLE AutAut.BijhautorisatieSrtAdmHnd ADD CONSTRAINT fk_BijhautorisatieSrtAdmHnd_ToegangBijhautorisatie_ToegangBijha FOREIGN KEY (ToegangBijhautorisatie) REFERENCES AutAut.ToegangBijhautorisatie (ID);
ALTER TABLE AutAut.BijhautorisatieSrtAdmHnd ADD CONSTRAINT fk_BijhautorisatieSrtAdmHnd_SrtAdmHnd_SrtAdmHnd FOREIGN KEY (SrtAdmHnd) REFERENCES Kern.SrtAdmHnd (ID);
ALTER TABLE AutAut.Dienst ADD CONSTRAINT fk_Dienst_Dienstbundel_Dienstbundel FOREIGN KEY (Dienstbundel) REFERENCES AutAut.Dienstbundel (ID);
ALTER TABLE AutAut.Dienst ADD CONSTRAINT fk_Dienst_Srt_SrtDienst FOREIGN KEY (Srt) REFERENCES AutAut.SrtDienst (ID);
ALTER TABLE AutAut.Dienst ADD CONSTRAINT fk_Dienst_EffectAfnemerindicaties_EffectAfnemerindicaties FOREIGN KEY (EffectAfnemerindicaties) REFERENCES AutAut.EffectAfnemerindicaties (ID);
ALTER TABLE AutAut.Dienstbundel ADD CONSTRAINT fk_Dienstbundel_Levsautorisatie_Levsautorisatie FOREIGN KEY (Levsautorisatie) REFERENCES AutAut.Levsautorisatie (ID);
ALTER TABLE AutAut.DienstbundelGroep ADD CONSTRAINT fk_DienstbundelGroep_Dienstbundel_Dienstbundel FOREIGN KEY (Dienstbundel) REFERENCES AutAut.Dienstbundel (ID);
ALTER TABLE AutAut.DienstbundelGroep ADD CONSTRAINT fk_DienstbundelGroep_Groep_Element FOREIGN KEY (Groep) REFERENCES Kern.Element (ID);
ALTER TABLE AutAut.DienstbundelGroepAttr ADD CONSTRAINT fk_DienstbundelGroepAttr_DienstbundelGroep_DienstbundelGroep FOREIGN KEY (DienstbundelGroep) REFERENCES AutAut.DienstbundelGroep (ID);
ALTER TABLE AutAut.DienstbundelGroepAttr ADD CONSTRAINT fk_DienstbundelGroepAttr_Attr_Element FOREIGN KEY (Attr) REFERENCES Kern.Element (ID);
ALTER TABLE AutAut.DienstbundelLO3Rubriek ADD CONSTRAINT fk_DienstbundelLO3Rubriek_Dienstbundel_Dienstbundel FOREIGN KEY (Dienstbundel) REFERENCES AutAut.Dienstbundel (ID);
ALTER TABLE AutAut.DienstbundelLO3Rubriek ADD CONSTRAINT fk_DienstbundelLO3Rubriek_Rubr_ConvLO3Rubriek FOREIGN KEY (Rubr) REFERENCES Conv.ConvLO3Rubriek (ID);
ALTER TABLE AutAut.His_Dienst ADD CONSTRAINT fk_His_Dienst_Dienst_Dienst FOREIGN KEY (Dienst) REFERENCES AutAut.Dienst (ID);
ALTER TABLE AutAut.His_Dienst ADD CONSTRAINT fk_His_Dienst_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE AutAut.His_Dienst ADD CONSTRAINT fk_His_Dienst_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE AutAut.His_Dienst ADD CONSTRAINT fk_His_Dienst_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE AutAut.His_DienstAttendering ADD CONSTRAINT fk_His_DienstAttendering_Dienst_Dienst FOREIGN KEY (Dienst) REFERENCES AutAut.Dienst (ID);
ALTER TABLE AutAut.His_DienstAttendering ADD CONSTRAINT fk_His_DienstAttendering_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE AutAut.His_DienstAttendering ADD CONSTRAINT fk_His_DienstAttendering_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE AutAut.His_DienstAttendering ADD CONSTRAINT fk_His_DienstAttendering_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE AutAut.His_DienstSelectie ADD CONSTRAINT fk_His_DienstSelectie_Dienst_Dienst FOREIGN KEY (Dienst) REFERENCES AutAut.Dienst (ID);
ALTER TABLE AutAut.His_DienstSelectie ADD CONSTRAINT fk_His_DienstSelectie_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE AutAut.His_DienstSelectie ADD CONSTRAINT fk_His_DienstSelectie_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE AutAut.His_DienstSelectie ADD CONSTRAINT fk_His_DienstSelectie_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE AutAut.His_Dienstbundel ADD CONSTRAINT fk_His_Dienstbundel_Dienstbundel_Dienstbundel FOREIGN KEY (Dienstbundel) REFERENCES AutAut.Dienstbundel (ID);
ALTER TABLE AutAut.His_Dienstbundel ADD CONSTRAINT fk_His_Dienstbundel_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE AutAut.His_Dienstbundel ADD CONSTRAINT fk_His_Dienstbundel_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE AutAut.His_Dienstbundel ADD CONSTRAINT fk_His_Dienstbundel_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE AutAut.His_DienstbundelGroep ADD CONSTRAINT fk_His_DienstbundelGroep_DienstbundelGroep_DienstbundelGroep FOREIGN KEY (DienstbundelGroep) REFERENCES AutAut.DienstbundelGroep (ID);
ALTER TABLE AutAut.His_DienstbundelGroep ADD CONSTRAINT fk_His_DienstbundelGroep_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE AutAut.His_DienstbundelGroep ADD CONSTRAINT fk_His_DienstbundelGroep_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE AutAut.His_DienstbundelGroep ADD CONSTRAINT fk_His_DienstbundelGroep_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE AutAut.His_DienstbundelGroepAttr ADD CONSTRAINT fk_His_DienstbundelGroepAttr_DienstbundelGroepAttr_Dienstbundel FOREIGN KEY (DienstbundelGroepAttr) REFERENCES AutAut.DienstbundelGroepAttr (ID);
ALTER TABLE AutAut.His_DienstbundelGroepAttr ADD CONSTRAINT fk_His_DienstbundelGroepAttr_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE AutAut.His_DienstbundelGroepAttr ADD CONSTRAINT fk_His_DienstbundelGroepAttr_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE AutAut.His_DienstbundelGroepAttr ADD CONSTRAINT fk_His_DienstbundelGroepAttr_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE AutAut.His_DienstbundelLO3Rubriek ADD CONSTRAINT fk_His_DienstbundelLO3Rubriek_DienstbundelLO3Rubriek_Dienstbund FOREIGN KEY (DienstbundelLO3Rubriek) REFERENCES AutAut.DienstbundelLO3Rubriek (ID);
ALTER TABLE AutAut.His_DienstbundelLO3Rubriek ADD CONSTRAINT fk_His_DienstbundelLO3Rubriek_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE AutAut.His_DienstbundelLO3Rubriek ADD CONSTRAINT fk_His_DienstbundelLO3Rubriek_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE AutAut.His_DienstbundelLO3Rubriek ADD CONSTRAINT fk_His_DienstbundelLO3Rubriek_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE AutAut.His_Levsautorisatie ADD CONSTRAINT fk_His_Levsautorisatie_Levsautorisatie_Levsautorisatie FOREIGN KEY (Levsautorisatie) REFERENCES AutAut.Levsautorisatie (ID);
ALTER TABLE AutAut.His_Levsautorisatie ADD CONSTRAINT fk_His_Levsautorisatie_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE AutAut.His_Levsautorisatie ADD CONSTRAINT fk_His_Levsautorisatie_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE AutAut.His_Levsautorisatie ADD CONSTRAINT fk_His_Levsautorisatie_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE AutAut.His_Levsautorisatie ADD CONSTRAINT fk_His_Levsautorisatie_Protocolleringsniveau_Protocolleringsniv FOREIGN KEY (Protocolleringsniveau) REFERENCES AutAut.Protocolleringsniveau (ID);
ALTER TABLE AutAut.His_PartijFiatuitz ADD CONSTRAINT fk_His_PartijFiatuitz_PartijFiatuitz_PartijFiatuitz FOREIGN KEY (PartijFiatuitz) REFERENCES AutAut.PartijFiatuitz (ID);
ALTER TABLE AutAut.His_PartijFiatuitz ADD CONSTRAINT fk_His_PartijFiatuitz_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE AutAut.His_PartijFiatuitz ADD CONSTRAINT fk_His_PartijFiatuitz_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE AutAut.His_PartijFiatuitz ADD CONSTRAINT fk_His_PartijFiatuitz_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE AutAut.His_PartijFiatuitz ADD CONSTRAINT fk_His_PartijFiatuitz_PartijBijhvoorstel_Partij FOREIGN KEY (PartijBijhvoorstel) REFERENCES Kern.Partij (ID);
ALTER TABLE AutAut.His_PartijFiatuitz ADD CONSTRAINT fk_His_PartijFiatuitz_SrtDoc_SrtDoc FOREIGN KEY (SrtDoc) REFERENCES Kern.SrtDoc (ID);
ALTER TABLE AutAut.His_PartijFiatuitz ADD CONSTRAINT fk_His_PartijFiatuitz_SrtAdmHnd_SrtAdmHnd FOREIGN KEY (SrtAdmHnd) REFERENCES Kern.SrtAdmHnd (ID);
ALTER TABLE AutAut.His_ToegangBijhautorisatie ADD CONSTRAINT fk_His_ToegangBijhautorisatie_ToegangBijhautorisatie_ToegangBij FOREIGN KEY (ToegangBijhautorisatie) REFERENCES AutAut.ToegangBijhautorisatie (ID);
ALTER TABLE AutAut.His_ToegangBijhautorisatie ADD CONSTRAINT fk_His_ToegangBijhautorisatie_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE AutAut.His_ToegangBijhautorisatie ADD CONSTRAINT fk_His_ToegangBijhautorisatie_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE AutAut.His_ToegangBijhautorisatie ADD CONSTRAINT fk_His_ToegangBijhautorisatie_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE AutAut.His_ToegangLevsautorisatie ADD CONSTRAINT fk_His_ToegangLevsautorisatie_ToegangLevsautorisatie_ToegangLev FOREIGN KEY (ToegangLevsautorisatie) REFERENCES AutAut.ToegangLevsautorisatie (ID);
ALTER TABLE AutAut.His_ToegangLevsautorisatie ADD CONSTRAINT fk_His_ToegangLevsautorisatie_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE AutAut.His_ToegangLevsautorisatie ADD CONSTRAINT fk_His_ToegangLevsautorisatie_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE AutAut.His_ToegangLevsautorisatie ADD CONSTRAINT fk_His_ToegangLevsautorisatie_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE AutAut.Levsautorisatie ADD CONSTRAINT fk_Levsautorisatie_Stelsel_Stelsel FOREIGN KEY (Stelsel) REFERENCES Kern.Stelsel (ID);
ALTER TABLE AutAut.Levsautorisatie ADD CONSTRAINT fk_Levsautorisatie_Protocolleringsniveau_Protocolleringsniveau FOREIGN KEY (Protocolleringsniveau) REFERENCES AutAut.Protocolleringsniveau (ID);
ALTER TABLE AutAut.PartijFiatuitz ADD CONSTRAINT fk_PartijFiatuitz_Partij_Partij FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE AutAut.PartijFiatuitz ADD CONSTRAINT fk_PartijFiatuitz_PartijBijhvoorstel_Partij FOREIGN KEY (PartijBijhvoorstel) REFERENCES Kern.Partij (ID);
ALTER TABLE AutAut.PartijFiatuitz ADD CONSTRAINT fk_PartijFiatuitz_SrtDoc_SrtDoc FOREIGN KEY (SrtDoc) REFERENCES Kern.SrtDoc (ID);
ALTER TABLE AutAut.PartijFiatuitz ADD CONSTRAINT fk_PartijFiatuitz_SrtAdmHnd_SrtAdmHnd FOREIGN KEY (SrtAdmHnd) REFERENCES Kern.SrtAdmHnd (ID);
ALTER TABLE AutAut.ToegangBijhautorisatie ADD CONSTRAINT fk_ToegangBijhautorisatie_Geautoriseerde_PartijRol FOREIGN KEY (Geautoriseerde) REFERENCES Kern.PartijRol (ID);
ALTER TABLE AutAut.ToegangBijhautorisatie ADD CONSTRAINT fk_ToegangBijhautorisatie_Ondertekenaar_Partij FOREIGN KEY (Ondertekenaar) REFERENCES Kern.Partij (ID);
ALTER TABLE AutAut.ToegangBijhautorisatie ADD CONSTRAINT fk_ToegangBijhautorisatie_Transporteur_Partij FOREIGN KEY (Transporteur) REFERENCES Kern.Partij (ID);
ALTER TABLE AutAut.ToegangLevsautorisatie ADD CONSTRAINT fk_ToegangLevsautorisatie_Geautoriseerde_PartijRol FOREIGN KEY (Geautoriseerde) REFERENCES Kern.PartijRol (ID);
ALTER TABLE AutAut.ToegangLevsautorisatie ADD CONSTRAINT fk_ToegangLevsautorisatie_Levsautorisatie_Levsautorisatie FOREIGN KEY (Levsautorisatie) REFERENCES AutAut.Levsautorisatie (ID);
ALTER TABLE AutAut.ToegangLevsautorisatie ADD CONSTRAINT fk_ToegangLevsautorisatie_Ondertekenaar_Partij FOREIGN KEY (Ondertekenaar) REFERENCES Kern.Partij (ID);
ALTER TABLE AutAut.ToegangLevsautorisatie ADD CONSTRAINT fk_ToegangLevsautorisatie_Transporteur_Partij FOREIGN KEY (Transporteur) REFERENCES Kern.Partij (ID);
ALTER TABLE BRM.His_Regelsituatie ADD CONSTRAINT fk_His_Regelsituatie_Regelsituatie_Regelsituatie FOREIGN KEY (Regelsituatie) REFERENCES BRM.Regelsituatie (ID);
ALTER TABLE BRM.His_Regelsituatie ADD CONSTRAINT fk_His_Regelsituatie_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE BRM.His_Regelsituatie ADD CONSTRAINT fk_His_Regelsituatie_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE BRM.His_Regelsituatie ADD CONSTRAINT fk_His_Regelsituatie_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE BRM.His_Regelsituatie ADD CONSTRAINT fk_His_Regelsituatie_Bijhaard_Bijhaard FOREIGN KEY (Bijhaard) REFERENCES Kern.Bijhaard (ID);
ALTER TABLE BRM.His_Regelsituatie ADD CONSTRAINT fk_His_Regelsituatie_NadereBijhaard_NadereBijhaard FOREIGN KEY (NadereBijhaard) REFERENCES Kern.NadereBijhaard (ID);
ALTER TABLE BRM.His_Regelsituatie ADD CONSTRAINT fk_His_Regelsituatie_Effect_Regeleffect FOREIGN KEY (Effect) REFERENCES BRM.Regeleffect (ID);
ALTER TABLE BRM.RegelSrtBer ADD CONSTRAINT fk_RegelSrtBer_Regel_Regel FOREIGN KEY (Regel) REFERENCES Kern.Regel (ID);
-- Onderdrukte foreign key: ALTER TABLE BRM.RegelSrtBer ADD CONSTRAINT fk_RegelSrtBer_SrtBer_SrtBer FOREIGN KEY (SrtBer) REFERENCES Ber.SrtBer (ID);
ALTER TABLE BRM.Regelsituatie ADD CONSTRAINT fk_Regelsituatie_RegelSrtBer_RegelSrtBer FOREIGN KEY (RegelSrtBer) REFERENCES BRM.RegelSrtBer (ID);
ALTER TABLE BRM.Regelsituatie ADD CONSTRAINT fk_Regelsituatie_Bijhaard_Bijhaard FOREIGN KEY (Bijhaard) REFERENCES Kern.Bijhaard (ID);
ALTER TABLE BRM.Regelsituatie ADD CONSTRAINT fk_Regelsituatie_NadereBijhaard_NadereBijhaard FOREIGN KEY (NadereBijhaard) REFERENCES Kern.NadereBijhaard (ID);
ALTER TABLE BRM.Regelsituatie ADD CONSTRAINT fk_Regelsituatie_Effect_Regeleffect FOREIGN KEY (Effect) REFERENCES BRM.Regeleffect (ID);
ALTER TABLE Conv.ConvRNIDeelnemer ADD CONSTRAINT fk_ConvRNIDeelnemer_Partij_Partij FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE Conv.ConvAandInhingVermissingReis ADD CONSTRAINT fk_ConvAandInhingVermissingReis_AandInhingVermissingReisdoc_Aan FOREIGN KEY (AandInhingVermissingReisdoc) REFERENCES Kern.AandInhingVermissingReisdoc (ID);
ALTER TABLE Conv.ConvAangifteAdresh ADD CONSTRAINT fk_ConvAangifteAdresh_Aang_Aang FOREIGN KEY (Aang) REFERENCES Kern.Aang (ID);
ALTER TABLE Conv.ConvAangifteAdresh ADD CONSTRAINT fk_ConvAangifteAdresh_RdnWijzVerblijf_RdnWijzVerblijf FOREIGN KEY (RdnWijzVerblijf) REFERENCES Kern.RdnWijzVerblijf (ID);
ALTER TABLE Conv.ConvAdellijkeTitelPredikaat ADD CONSTRAINT fk_ConvAdellijkeTitelPredikaat_Geslachtsaand_Geslachtsaand FOREIGN KEY (Geslachtsaand) REFERENCES Kern.Geslachtsaand (ID);
ALTER TABLE Conv.ConvAdellijkeTitelPredikaat ADD CONSTRAINT fk_ConvAdellijkeTitelPredikaat_AdellijkeTitel_AdellijkeTitel FOREIGN KEY (AdellijkeTitel) REFERENCES Kern.AdellijkeTitel (ID);
ALTER TABLE Conv.ConvAdellijkeTitelPredikaat ADD CONSTRAINT fk_ConvAdellijkeTitelPredikaat_Predicaat_Predicaat FOREIGN KEY (Predicaat) REFERENCES Kern.Predicaat (ID);
ALTER TABLE Conv.ConvRdnBeeindigenNation ADD CONSTRAINT fk_ConvRdnBeeindigenNation_RdnVerlies_RdnVerliesNLNation FOREIGN KEY (RdnVerlies) REFERENCES Kern.RdnVerliesNLNation (ID);
ALTER TABLE Conv.ConvRdnOntbindingHuwelijkGer ADD CONSTRAINT fk_ConvRdnOntbindingHuwelijkGer_RdnEindeRelatie_RdnEindeRelatie FOREIGN KEY (RdnEindeRelatie) REFERENCES Kern.RdnEindeRelatie (ID);
ALTER TABLE Conv.ConvRdnOpnameNation ADD CONSTRAINT fk_ConvRdnOpnameNation_RdnVerk_RdnVerkNLNation FOREIGN KEY (RdnVerk) REFERENCES Kern.RdnVerkNLNation (ID);
ALTER TABLE Conv.ConvRdnOpschorting ADD CONSTRAINT fk_ConvRdnOpschorting_NadereBijhaard_NadereBijhaard FOREIGN KEY (NadereBijhaard) REFERENCES Kern.NadereBijhaard (ID);
ALTER TABLE Conv.ConvSrtNLReisdoc ADD CONSTRAINT fk_ConvSrtNLReisdoc_SrtNLReisdoc_SrtNLReisdoc FOREIGN KEY (SrtNLReisdoc) REFERENCES Kern.SrtNLReisdoc (ID);
ALTER TABLE Kern.Element ADD CONSTRAINT fk_Element_Srt_SrtElement FOREIGN KEY (Srt) REFERENCES Kern.SrtElement (ID);
ALTER TABLE Kern.Element ADD CONSTRAINT fk_Element_Objecttype_Element FOREIGN KEY (Objecttype) REFERENCES Kern.Element (ID);
ALTER TABLE Kern.Element ADD CONSTRAINT fk_Element_Groep_Element FOREIGN KEY (Groep) REFERENCES Kern.Element (ID);
ALTER TABLE Kern.Element ADD CONSTRAINT fk_Element_AliasVan_Element FOREIGN KEY (AliasVan) REFERENCES Kern.Element (ID);
ALTER TABLE Kern.Element ADD CONSTRAINT fk_Element_Autorisatie_SrtElementAutorisatie FOREIGN KEY (Autorisatie) REFERENCES Kern.SrtElementAutorisatie (ID);
ALTER TABLE Kern.Element ADD CONSTRAINT fk_Element_Tabel_Element FOREIGN KEY (Tabel) REFERENCES Kern.Element (ID);
ALTER TABLE Kern.Element ADD CONSTRAINT fk_Element_HisTabel_Element FOREIGN KEY (HisTabel) REFERENCES Kern.Element (ID);
ALTER TABLE Kern.Gem ADD CONSTRAINT fk_Gem_Partij_Partij FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.Gem ADD CONSTRAINT fk_Gem_VoortzettendeGem_Gem FOREIGN KEY (VoortzettendeGem) REFERENCES Kern.Gem (ID);
ALTER TABLE Kern.His_Partij ADD CONSTRAINT fk_His_Partij_Partij_Partij FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.His_Partij ADD CONSTRAINT fk_His_Partij_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Partij ADD CONSTRAINT fk_His_Partij_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Partij ADD CONSTRAINT fk_His_Partij_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Partij ADD CONSTRAINT fk_His_Partij_Srt_SrtPartij FOREIGN KEY (Srt) REFERENCES Kern.SrtPartij (ID);
ALTER TABLE Kern.His_PartijBijhouding ADD CONSTRAINT fk_His_PartijBijhouding_Partij_Partij FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.His_PartijBijhouding ADD CONSTRAINT fk_His_PartijBijhouding_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PartijBijhouding ADD CONSTRAINT fk_His_PartijBijhouding_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PartijBijhouding ADD CONSTRAINT fk_His_PartijBijhouding_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PartijRol ADD CONSTRAINT fk_His_PartijRol_PartijRol_PartijRol FOREIGN KEY (PartijRol) REFERENCES Kern.PartijRol (ID);
ALTER TABLE Kern.His_PartijRol ADD CONSTRAINT fk_His_PartijRol_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PartijRol ADD CONSTRAINT fk_His_PartijRol_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PartijRol ADD CONSTRAINT fk_His_PartijRol_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.Koppelvlak ADD CONSTRAINT fk_Koppelvlak_Stelsel_Stelsel FOREIGN KEY (Stelsel) REFERENCES Kern.Stelsel (ID);
ALTER TABLE Kern.Partij ADD CONSTRAINT fk_Partij_Srt_SrtPartij FOREIGN KEY (Srt) REFERENCES Kern.SrtPartij (ID);
ALTER TABLE Kern.PartijRol ADD CONSTRAINT fk_PartijRol_Partij_Partij FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.PartijRol ADD CONSTRAINT fk_PartijRol_Rol_Rol FOREIGN KEY (Rol) REFERENCES Kern.Rol (ID);
ALTER TABLE Kern.Rechtsgrond ADD CONSTRAINT fk_Rechtsgrond_Srt_SrtRechtsgrond FOREIGN KEY (Srt) REFERENCES Kern.SrtRechtsgrond (ID);
ALTER TABLE Kern.Regel ADD CONSTRAINT fk_Regel_Srt_SrtRegel FOREIGN KEY (Srt) REFERENCES BRM.SrtRegel (ID);
ALTER TABLE Kern.SrtAdmHnd ADD CONSTRAINT fk_SrtAdmHnd_CategorieAdmHnd_CategorieAdmHnd FOREIGN KEY (CategorieAdmHnd) REFERENCES Kern.CategorieAdmHnd (ID);
ALTER TABLE Kern.SrtAdmHnd ADD CONSTRAINT fk_SrtAdmHnd_Koppelvlak_Koppelvlak FOREIGN KEY (Koppelvlak) REFERENCES Kern.Koppelvlak (ID);
ALTER TABLE Kern.SrtAdmHnd ADD CONSTRAINT fk_SrtAdmHnd_Module_BurgerzakenModule FOREIGN KEY (Module) REFERENCES Kern.BurgerzakenModule (ID);
ALTER TABLE VerConv.LO3SrtMelding ADD CONSTRAINT fk_LO3SrtMelding_CategorieMelding_LO3CategorieMelding FOREIGN KEY (CategorieMelding) REFERENCES VerConv.LO3CategorieMelding (ID);
ALTER TABLE AutAut.PersAfnemerindicatie ADD CONSTRAINT fk_PersAfnemerindicatie_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE AutAut.PersAfnemerindicatie ADD CONSTRAINT fk_PersAfnemerindicatie_Afnemer_Partij FOREIGN KEY (Afnemer) REFERENCES Kern.Partij (ID);
ALTER TABLE AutAut.PersAfnemerindicatie ADD CONSTRAINT fk_PersAfnemerindicatie_Levsautorisatie_Levsautorisatie FOREIGN KEY (Levsautorisatie) REFERENCES AutAut.Levsautorisatie (ID);
ALTER TABLE IST.Stapel ADD CONSTRAINT fk_Stapel_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE IST.StapelRelatie ADD CONSTRAINT fk_StapelRelatie_Stapel_Stapel FOREIGN KEY (Stapel) REFERENCES IST.Stapel (ID);
ALTER TABLE IST.StapelRelatie ADD CONSTRAINT fk_StapelRelatie_Relatie_Relatie FOREIGN KEY (Relatie) REFERENCES Kern.Relatie (ID);
ALTER TABLE IST.StapelVoorkomen ADD CONSTRAINT fk_StapelVoorkomen_Stapel_Stapel FOREIGN KEY (Stapel) REFERENCES IST.Stapel (ID);
ALTER TABLE IST.StapelVoorkomen ADD CONSTRAINT fk_StapelVoorkomen_AdmHnd_AdmHnd FOREIGN KEY (AdmHnd) REFERENCES Kern.AdmHnd (ID);
ALTER TABLE IST.StapelVoorkomen ADD CONSTRAINT fk_StapelVoorkomen_SrtDoc_SrtDoc FOREIGN KEY (SrtDoc) REFERENCES Kern.SrtDoc (ID);
ALTER TABLE IST.StapelVoorkomen ADD CONSTRAINT fk_StapelVoorkomen_Partij_Partij FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE IST.StapelVoorkomen ADD CONSTRAINT fk_StapelVoorkomen_Predicaat_Predicaat FOREIGN KEY (Predicaat) REFERENCES Kern.Predicaat (ID);
ALTER TABLE IST.StapelVoorkomen ADD CONSTRAINT fk_StapelVoorkomen_AdellijkeTitel_AdellijkeTitel FOREIGN KEY (AdellijkeTitel) REFERENCES Kern.AdellijkeTitel (ID);
ALTER TABLE IST.StapelVoorkomen ADD CONSTRAINT fk_StapelVoorkomen_GeslachtBijAdellijkeTitelPre_Geslachtsaand FOREIGN KEY (GeslachtBijAdellijkeTitelPre) REFERENCES Kern.Geslachtsaand (ID);
ALTER TABLE IST.StapelVoorkomen ADD CONSTRAINT fk_StapelVoorkomen_GemGeboorte_Gem FOREIGN KEY (GemGeboorte) REFERENCES Kern.Gem (ID);
ALTER TABLE IST.StapelVoorkomen ADD CONSTRAINT fk_StapelVoorkomen_LandGebiedGeboorte_LandGebied FOREIGN KEY (LandGebiedGeboorte) REFERENCES Kern.LandGebied (ID);
ALTER TABLE IST.StapelVoorkomen ADD CONSTRAINT fk_StapelVoorkomen_Geslachtsaand_Geslachtsaand FOREIGN KEY (Geslachtsaand) REFERENCES Kern.Geslachtsaand (ID);
ALTER TABLE IST.StapelVoorkomen ADD CONSTRAINT fk_StapelVoorkomen_GemAanv_Gem FOREIGN KEY (GemAanv) REFERENCES Kern.Gem (ID);
ALTER TABLE IST.StapelVoorkomen ADD CONSTRAINT fk_StapelVoorkomen_LandGebiedAanv_LandGebied FOREIGN KEY (LandGebiedAanv) REFERENCES Kern.LandGebied (ID);
ALTER TABLE IST.StapelVoorkomen ADD CONSTRAINT fk_StapelVoorkomen_RdnEinde_RdnEindeRelatie FOREIGN KEY (RdnEinde) REFERENCES Kern.RdnEindeRelatie (ID);
ALTER TABLE IST.StapelVoorkomen ADD CONSTRAINT fk_StapelVoorkomen_GemEinde_Gem FOREIGN KEY (GemEinde) REFERENCES Kern.Gem (ID);
ALTER TABLE IST.StapelVoorkomen ADD CONSTRAINT fk_StapelVoorkomen_LandGebiedEinde_LandGebied FOREIGN KEY (LandGebiedEinde) REFERENCES Kern.LandGebied (ID);
ALTER TABLE IST.StapelVoorkomen ADD CONSTRAINT fk_StapelVoorkomen_SrtRelatie_SrtRelatie FOREIGN KEY (SrtRelatie) REFERENCES Kern.SrtRelatie (ID);
ALTER TABLE Kern.Actie ADD CONSTRAINT fk_Actie_Srt_SrtActie FOREIGN KEY (Srt) REFERENCES Kern.SrtActie (ID);
ALTER TABLE Kern.Actie ADD CONSTRAINT fk_Actie_AdmHnd_AdmHnd FOREIGN KEY (AdmHnd) REFERENCES Kern.AdmHnd (ID);
ALTER TABLE Kern.Actie ADD CONSTRAINT fk_Actie_Partij_Partij FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.ActieBron ADD CONSTRAINT fk_ActieBron_Actie_Actie FOREIGN KEY (Actie) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.ActieBron ADD CONSTRAINT fk_ActieBron_Doc_Doc FOREIGN KEY (Doc) REFERENCES Kern.Doc (ID);
ALTER TABLE Kern.ActieBron ADD CONSTRAINT fk_ActieBron_Rechtsgrond_Rechtsgrond FOREIGN KEY (Rechtsgrond) REFERENCES Kern.Rechtsgrond (ID);
ALTER TABLE Kern.AdmHnd ADD CONSTRAINT fk_AdmHnd_Srt_SrtAdmHnd FOREIGN KEY (Srt) REFERENCES Kern.SrtAdmHnd (ID);
ALTER TABLE Kern.AdmHnd ADD CONSTRAINT fk_AdmHnd_Partij_Partij FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.AdmHndGedeblokkeerdeMelding ADD CONSTRAINT fk_AdmHndGedeblokkeerdeMelding_AdmHnd_AdmHnd FOREIGN KEY (AdmHnd) REFERENCES Kern.AdmHnd (ID);
ALTER TABLE Kern.AdmHndGedeblokkeerdeMelding ADD CONSTRAINT fk_AdmHndGedeblokkeerdeMelding_GedeblokkeerdeMelding_Gedeblokke FOREIGN KEY (GedeblokkeerdeMelding) REFERENCES Kern.GedeblokkeerdeMelding (ID);
ALTER TABLE Kern.Betr ADD CONSTRAINT fk_Betr_Relatie_Relatie FOREIGN KEY (Relatie) REFERENCES Kern.Relatie (ID);
ALTER TABLE Kern.Betr ADD CONSTRAINT fk_Betr_Rol_SrtBetr FOREIGN KEY (Rol) REFERENCES Kern.SrtBetr (ID);
ALTER TABLE Kern.Betr ADD CONSTRAINT fk_Betr_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.Doc ADD CONSTRAINT fk_Doc_Srt_SrtDoc FOREIGN KEY (Srt) REFERENCES Kern.SrtDoc (ID);
ALTER TABLE Kern.Doc ADD CONSTRAINT fk_Doc_Partij_Partij FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.GedeblokkeerdeMelding ADD CONSTRAINT fk_GedeblokkeerdeMelding_Regel_Regel FOREIGN KEY (Regel) REFERENCES Kern.Regel (ID);
ALTER TABLE Kern.GegevenInOnderzoek ADD CONSTRAINT fk_GegevenInOnderzoek_Onderzoek_Onderzoek FOREIGN KEY (Onderzoek) REFERENCES Kern.Onderzoek (ID);
ALTER TABLE Kern.GegevenInOnderzoek ADD CONSTRAINT fk_GegevenInOnderzoek_Element_Element FOREIGN KEY (Element) REFERENCES Kern.Element (ID);
ALTER TABLE Kern.GegevenInTerugmelding ADD CONSTRAINT fk_GegevenInTerugmelding_Terugmelding_Terugmelding FOREIGN KEY (Terugmelding) REFERENCES Kern.Terugmelding (ID);
ALTER TABLE Kern.GegevenInTerugmelding ADD CONSTRAINT fk_GegevenInTerugmelding_Element_Element FOREIGN KEY (Element) REFERENCES Kern.Element (ID);
ALTER TABLE Kern.Onderzoek ADD CONSTRAINT fk_Onderzoek_Status_StatusOnderzoek FOREIGN KEY (Status) REFERENCES Kern.StatusOnderzoek (ID);
ALTER TABLE Kern.Onderzoek ADD CONSTRAINT fk_Onderzoek_AdmHnd_AdmHnd FOREIGN KEY (AdmHnd) REFERENCES Kern.AdmHnd (ID);
ALTER TABLE Kern.PartijOnderzoek ADD CONSTRAINT fk_PartijOnderzoek_Partij_Partij FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.PartijOnderzoek ADD CONSTRAINT fk_PartijOnderzoek_Onderzoek_Onderzoek FOREIGN KEY (Onderzoek) REFERENCES Kern.Onderzoek (ID);
ALTER TABLE Kern.PartijOnderzoek ADD CONSTRAINT fk_PartijOnderzoek_Rol_SrtPartijOnderzoek FOREIGN KEY (Rol) REFERENCES Kern.SrtPartijOnderzoek (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT fk_Pers_Srt_SrtPers FOREIGN KEY (Srt) REFERENCES Kern.SrtPers (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT fk_Pers_AdmHnd_AdmHnd FOREIGN KEY (AdmHnd) REFERENCES Kern.AdmHnd (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT fk_Pers_Predicaat_Predicaat FOREIGN KEY (Predicaat) REFERENCES Kern.Predicaat (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT fk_Pers_AdellijkeTitel_AdellijkeTitel FOREIGN KEY (AdellijkeTitel) REFERENCES Kern.AdellijkeTitel (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT fk_Pers_GemGeboorte_Gem FOREIGN KEY (GemGeboorte) REFERENCES Kern.Gem (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT fk_Pers_LandGebiedGeboorte_LandGebied FOREIGN KEY (LandGebiedGeboorte) REFERENCES Kern.LandGebied (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT fk_Pers_Geslachtsaand_Geslachtsaand FOREIGN KEY (Geslachtsaand) REFERENCES Kern.Geslachtsaand (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT fk_Pers_Bijhpartij_Partij FOREIGN KEY (Bijhpartij) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT fk_Pers_Bijhaard_Bijhaard FOREIGN KEY (Bijhaard) REFERENCES Kern.Bijhaard (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT fk_Pers_NadereBijhaard_NadereBijhaard FOREIGN KEY (NadereBijhaard) REFERENCES Kern.NadereBijhaard (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT fk_Pers_GemOverlijden_Gem FOREIGN KEY (GemOverlijden) REFERENCES Kern.Gem (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT fk_Pers_LandGebiedOverlijden_LandGebied FOREIGN KEY (LandGebiedOverlijden) REFERENCES Kern.LandGebied (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT fk_Pers_Naamgebruik_Naamgebruik FOREIGN KEY (Naamgebruik) REFERENCES Kern.Naamgebruik (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT fk_Pers_PredicaatNaamgebruik_Predicaat FOREIGN KEY (PredicaatNaamgebruik) REFERENCES Kern.Predicaat (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT fk_Pers_AdellijkeTitelNaamgebruik_AdellijkeTitel FOREIGN KEY (AdellijkeTitelNaamgebruik) REFERENCES Kern.AdellijkeTitel (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT fk_Pers_SrtMigratie_SrtMigratie FOREIGN KEY (SrtMigratie) REFERENCES Kern.SrtMigratie (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT fk_Pers_RdnWijzMigratie_RdnWijzVerblijf FOREIGN KEY (RdnWijzMigratie) REFERENCES Kern.RdnWijzVerblijf (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT fk_Pers_AangMigratie_Aang FOREIGN KEY (AangMigratie) REFERENCES Kern.Aang (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT fk_Pers_LandGebiedMigratie_LandGebied FOREIGN KEY (LandGebiedMigratie) REFERENCES Kern.LandGebied (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT fk_Pers_AandVerblijfsr_AandVerblijfsr FOREIGN KEY (AandVerblijfsr) REFERENCES Kern.AandVerblijfsr (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT fk_Pers_GemPK_Partij FOREIGN KEY (GemPK) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.PersAdres ADD CONSTRAINT fk_PersAdres_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.PersAdres ADD CONSTRAINT fk_PersAdres_Srt_FunctieAdres FOREIGN KEY (Srt) REFERENCES Kern.FunctieAdres (ID);
ALTER TABLE Kern.PersAdres ADD CONSTRAINT fk_PersAdres_RdnWijz_RdnWijzVerblijf FOREIGN KEY (RdnWijz) REFERENCES Kern.RdnWijzVerblijf (ID);
ALTER TABLE Kern.PersAdres ADD CONSTRAINT fk_PersAdres_AangAdresh_Aang FOREIGN KEY (AangAdresh) REFERENCES Kern.Aang (ID);
ALTER TABLE Kern.PersAdres ADD CONSTRAINT fk_PersAdres_Gem_Gem FOREIGN KEY (Gem) REFERENCES Kern.Gem (ID);
ALTER TABLE Kern.PersAdres ADD CONSTRAINT fk_PersAdres_LandGebied_LandGebied FOREIGN KEY (LandGebied) REFERENCES Kern.LandGebied (ID);
ALTER TABLE Kern.PersGeslnaamcomp ADD CONSTRAINT fk_PersGeslnaamcomp_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.PersGeslnaamcomp ADD CONSTRAINT fk_PersGeslnaamcomp_Predicaat_Predicaat FOREIGN KEY (Predicaat) REFERENCES Kern.Predicaat (ID);
ALTER TABLE Kern.PersGeslnaamcomp ADD CONSTRAINT fk_PersGeslnaamcomp_AdellijkeTitel_AdellijkeTitel FOREIGN KEY (AdellijkeTitel) REFERENCES Kern.AdellijkeTitel (ID);
ALTER TABLE Kern.PersIndicatie ADD CONSTRAINT fk_PersIndicatie_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.PersIndicatie ADD CONSTRAINT fk_PersIndicatie_Srt_SrtIndicatie FOREIGN KEY (Srt) REFERENCES Kern.SrtIndicatie (ID);
ALTER TABLE Kern.PersNation ADD CONSTRAINT fk_PersNation_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.PersNation ADD CONSTRAINT fk_PersNation_Nation_Nation FOREIGN KEY (Nation) REFERENCES Kern.Nation (ID);
ALTER TABLE Kern.PersNation ADD CONSTRAINT fk_PersNation_RdnVerk_RdnVerkNLNation FOREIGN KEY (RdnVerk) REFERENCES Kern.RdnVerkNLNation (ID);
ALTER TABLE Kern.PersNation ADD CONSTRAINT fk_PersNation_RdnVerlies_RdnVerliesNLNation FOREIGN KEY (RdnVerlies) REFERENCES Kern.RdnVerliesNLNation (ID);
ALTER TABLE Kern.PersOnderzoek ADD CONSTRAINT fk_PersOnderzoek_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.PersOnderzoek ADD CONSTRAINT fk_PersOnderzoek_Onderzoek_Onderzoek FOREIGN KEY (Onderzoek) REFERENCES Kern.Onderzoek (ID);
ALTER TABLE Kern.PersOnderzoek ADD CONSTRAINT fk_PersOnderzoek_Rol_SrtPersOnderzoek FOREIGN KEY (Rol) REFERENCES Kern.SrtPersOnderzoek (ID);
ALTER TABLE Kern.PersReisdoc ADD CONSTRAINT fk_PersReisdoc_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.PersReisdoc ADD CONSTRAINT fk_PersReisdoc_Srt_SrtNLReisdoc FOREIGN KEY (Srt) REFERENCES Kern.SrtNLReisdoc (ID);
ALTER TABLE Kern.PersReisdoc ADD CONSTRAINT fk_PersReisdoc_AandInhingVermissing_AandInhingVermissingReisdoc FOREIGN KEY (AandInhingVermissing) REFERENCES Kern.AandInhingVermissingReisdoc (ID);
ALTER TABLE Kern.PersVerificatie ADD CONSTRAINT fk_PersVerificatie_Geverifieerde_Pers FOREIGN KEY (Geverifieerde) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.PersVerificatie ADD CONSTRAINT fk_PersVerificatie_Partij_Partij FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.PersVerstrbeperking ADD CONSTRAINT fk_PersVerstrbeperking_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.PersVerstrbeperking ADD CONSTRAINT fk_PersVerstrbeperking_Partij_Partij FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.PersVerstrbeperking ADD CONSTRAINT fk_PersVerstrbeperking_GemVerordening_Partij FOREIGN KEY (GemVerordening) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.PersVoornaam ADD CONSTRAINT fk_PersVoornaam_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.PersCache ADD CONSTRAINT fk_PersCache_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.Regelverantwoording ADD CONSTRAINT fk_Regelverantwoording_Actie_Actie FOREIGN KEY (Actie) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.Regelverantwoording ADD CONSTRAINT fk_Regelverantwoording_Regel_Regel FOREIGN KEY (Regel) REFERENCES Kern.Regel (ID);
ALTER TABLE Kern.Relatie ADD CONSTRAINT fk_Relatie_Srt_SrtRelatie FOREIGN KEY (Srt) REFERENCES Kern.SrtRelatie (ID);
ALTER TABLE Kern.Relatie ADD CONSTRAINT fk_Relatie_GemAanv_Gem FOREIGN KEY (GemAanv) REFERENCES Kern.Gem (ID);
ALTER TABLE Kern.Relatie ADD CONSTRAINT fk_Relatie_LandGebiedAanv_LandGebied FOREIGN KEY (LandGebiedAanv) REFERENCES Kern.LandGebied (ID);
ALTER TABLE Kern.Relatie ADD CONSTRAINT fk_Relatie_RdnEinde_RdnEindeRelatie FOREIGN KEY (RdnEinde) REFERENCES Kern.RdnEindeRelatie (ID);
ALTER TABLE Kern.Relatie ADD CONSTRAINT fk_Relatie_GemEinde_Gem FOREIGN KEY (GemEinde) REFERENCES Kern.Gem (ID);
ALTER TABLE Kern.Relatie ADD CONSTRAINT fk_Relatie_LandGebiedEinde_LandGebied FOREIGN KEY (LandGebiedEinde) REFERENCES Kern.LandGebied (ID);
ALTER TABLE Kern.Terugmelding ADD CONSTRAINT fk_Terugmelding_TerugmeldendePartij_Partij FOREIGN KEY (TerugmeldendePartij) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.Terugmelding ADD CONSTRAINT fk_Terugmelding_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.Terugmelding ADD CONSTRAINT fk_Terugmelding_Bijhgem_Partij FOREIGN KEY (Bijhgem) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.Terugmelding ADD CONSTRAINT fk_Terugmelding_Onderzoek_Onderzoek FOREIGN KEY (Onderzoek) REFERENCES Kern.Onderzoek (ID);
ALTER TABLE Kern.Terugmelding ADD CONSTRAINT fk_Terugmelding_Status_StatusTerugmelding FOREIGN KEY (Status) REFERENCES Kern.StatusTerugmelding (ID);
ALTER TABLE MigBlok.Blokkering ADD CONSTRAINT fk_Blokkering_RdnBlokkering_RdnBlokkering FOREIGN KEY (RdnBlokkering) REFERENCES MigBlok.RdnBlokkering (ID);
ALTER TABLE VerConv.LO3AandOuder ADD CONSTRAINT fk_LO3AandOuder_Ouder_Betr FOREIGN KEY (Ouder) REFERENCES Kern.Betr (ID);
ALTER TABLE VerConv.LO3AandOuder ADD CONSTRAINT fk_LO3AandOuder_Srt_LO3SrtAandOuder FOREIGN KEY (Srt) REFERENCES VerConv.LO3SrtAandOuder (ID);
ALTER TABLE VerConv.LO3Ber ADD CONSTRAINT fk_LO3Ber_Bron_LO3BerBron FOREIGN KEY (Bron) REFERENCES VerConv.LO3BerBron (ID);
ALTER TABLE VerConv.LO3Ber ADD CONSTRAINT fk_LO3Ber_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE VerConv.LO3Melding ADD CONSTRAINT fk_LO3Melding_LO3Voorkomen_LO3Voorkomen FOREIGN KEY (LO3Voorkomen) REFERENCES VerConv.LO3Voorkomen (ID);
ALTER TABLE VerConv.LO3Melding ADD CONSTRAINT fk_LO3Melding_Srt_LO3SrtMelding FOREIGN KEY (Srt) REFERENCES VerConv.LO3SrtMelding (ID);
ALTER TABLE VerConv.LO3Melding ADD CONSTRAINT fk_LO3Melding_LogSeverity_LO3Severity FOREIGN KEY (LogSeverity) REFERENCES VerConv.LO3Severity (ID);
ALTER TABLE VerConv.LO3Voorkomen ADD CONSTRAINT fk_LO3Voorkomen_LO3Ber_LO3Ber FOREIGN KEY (LO3Ber) REFERENCES VerConv.LO3Ber (ID);
ALTER TABLE VerConv.LO3Voorkomen ADD CONSTRAINT fk_LO3Voorkomen_Actie_Actie FOREIGN KEY (Actie) REFERENCES Kern.Actie (ID);
ALTER TABLE AutAut.His_PersAfnemerindicatie ADD CONSTRAINT fk_His_PersAfnemerindicatie_PersAfnemerindicatie_PersAfnemerind FOREIGN KEY (PersAfnemerindicatie) REFERENCES AutAut.PersAfnemerindicatie (ID);
ALTER TABLE AutAut.His_PersAfnemerindicatie ADD CONSTRAINT fk_His_PersAfnemerindicatie_DienstInh_Dienst FOREIGN KEY (DienstInh) REFERENCES AutAut.Dienst (ID);
ALTER TABLE AutAut.His_PersAfnemerindicatie ADD CONSTRAINT fk_His_PersAfnemerindicatie_DienstVerval_Dienst FOREIGN KEY (DienstVerval) REFERENCES AutAut.Dienst (ID);
ALTER TABLE Kern.His_Betr ADD CONSTRAINT fk_His_Betr_Betr_Betr FOREIGN KEY (Betr) REFERENCES Kern.Betr (ID);
ALTER TABLE Kern.His_Betr ADD CONSTRAINT fk_His_Betr_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Betr ADD CONSTRAINT fk_His_Betr_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Betr ADD CONSTRAINT fk_His_Betr_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Doc ADD CONSTRAINT fk_His_Doc_Doc_Doc FOREIGN KEY (Doc) REFERENCES Kern.Doc (ID);
ALTER TABLE Kern.His_Doc ADD CONSTRAINT fk_His_Doc_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Doc ADD CONSTRAINT fk_His_Doc_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Doc ADD CONSTRAINT fk_His_Doc_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Doc ADD CONSTRAINT fk_His_Doc_Partij_Partij FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.His_Onderzoek ADD CONSTRAINT fk_His_Onderzoek_Onderzoek_Onderzoek FOREIGN KEY (Onderzoek) REFERENCES Kern.Onderzoek (ID);
ALTER TABLE Kern.His_Onderzoek ADD CONSTRAINT fk_His_Onderzoek_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Onderzoek ADD CONSTRAINT fk_His_Onderzoek_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Onderzoek ADD CONSTRAINT fk_His_Onderzoek_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Onderzoek ADD CONSTRAINT fk_His_Onderzoek_Status_StatusOnderzoek FOREIGN KEY (Status) REFERENCES Kern.StatusOnderzoek (ID);
ALTER TABLE Kern.His_OnderzoekAfgeleidAdminis ADD CONSTRAINT fk_His_OnderzoekAfgeleidAdminis_Onderzoek_Onderzoek FOREIGN KEY (Onderzoek) REFERENCES Kern.Onderzoek (ID);
ALTER TABLE Kern.His_OnderzoekAfgeleidAdminis ADD CONSTRAINT fk_His_OnderzoekAfgeleidAdminis_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_OnderzoekAfgeleidAdminis ADD CONSTRAINT fk_His_OnderzoekAfgeleidAdminis_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_OnderzoekAfgeleidAdminis ADD CONSTRAINT fk_His_OnderzoekAfgeleidAdminis_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_OnderzoekAfgeleidAdminis ADD CONSTRAINT fk_His_OnderzoekAfgeleidAdminis_AdmHnd_AdmHnd FOREIGN KEY (AdmHnd) REFERENCES Kern.AdmHnd (ID);
ALTER TABLE Kern.His_OuderOuderlijkGezag ADD CONSTRAINT fk_His_OuderOuderlijkGezag_Betr_Betr FOREIGN KEY (Betr) REFERENCES Kern.Betr (ID);
ALTER TABLE Kern.His_OuderOuderlijkGezag ADD CONSTRAINT fk_His_OuderOuderlijkGezag_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_OuderOuderlijkGezag ADD CONSTRAINT fk_His_OuderOuderlijkGezag_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_OuderOuderlijkGezag ADD CONSTRAINT fk_His_OuderOuderlijkGezag_ActieAanpGel_Actie FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_OuderOuderlijkGezag ADD CONSTRAINT fk_His_OuderOuderlijkGezag_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_OuderOuderschap ADD CONSTRAINT fk_His_OuderOuderschap_Betr_Betr FOREIGN KEY (Betr) REFERENCES Kern.Betr (ID);
ALTER TABLE Kern.His_OuderOuderschap ADD CONSTRAINT fk_His_OuderOuderschap_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_OuderOuderschap ADD CONSTRAINT fk_His_OuderOuderschap_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_OuderOuderschap ADD CONSTRAINT fk_His_OuderOuderschap_ActieAanpGel_Actie FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_OuderOuderschap ADD CONSTRAINT fk_His_OuderOuderschap_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PartijOnderzoek ADD CONSTRAINT fk_His_PartijOnderzoek_PartijOnderzoek_PartijOnderzoek FOREIGN KEY (PartijOnderzoek) REFERENCES Kern.PartijOnderzoek (ID);
ALTER TABLE Kern.His_PartijOnderzoek ADD CONSTRAINT fk_His_PartijOnderzoek_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PartijOnderzoek ADD CONSTRAINT fk_His_PartijOnderzoek_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PartijOnderzoek ADD CONSTRAINT fk_His_PartijOnderzoek_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PartijOnderzoek ADD CONSTRAINT fk_His_PartijOnderzoek_Rol_SrtPartijOnderzoek FOREIGN KEY (Rol) REFERENCES Kern.SrtPartijOnderzoek (ID);
ALTER TABLE Kern.His_PersAfgeleidAdministrati ADD CONSTRAINT fk_His_PersAfgeleidAdministrati_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_PersAfgeleidAdministrati ADD CONSTRAINT fk_His_PersAfgeleidAdministrati_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersAfgeleidAdministrati ADD CONSTRAINT fk_His_PersAfgeleidAdministrati_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersAfgeleidAdministrati ADD CONSTRAINT fk_His_PersAfgeleidAdministrati_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersAfgeleidAdministrati ADD CONSTRAINT fk_His_PersAfgeleidAdministrati_AdmHnd_AdmHnd FOREIGN KEY (AdmHnd) REFERENCES Kern.AdmHnd (ID);
ALTER TABLE Kern.His_PersBijhouding ADD CONSTRAINT fk_His_PersBijhouding_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_PersBijhouding ADD CONSTRAINT fk_His_PersBijhouding_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersBijhouding ADD CONSTRAINT fk_His_PersBijhouding_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersBijhouding ADD CONSTRAINT fk_His_PersBijhouding_ActieAanpGel_Actie FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersBijhouding ADD CONSTRAINT fk_His_PersBijhouding_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersBijhouding ADD CONSTRAINT fk_His_PersBijhouding_Bijhpartij_Partij FOREIGN KEY (Bijhpartij) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.His_PersBijhouding ADD CONSTRAINT fk_His_PersBijhouding_Bijhaard_Bijhaard FOREIGN KEY (Bijhaard) REFERENCES Kern.Bijhaard (ID);
ALTER TABLE Kern.His_PersBijhouding ADD CONSTRAINT fk_His_PersBijhouding_NadereBijhaard_NadereBijhaard FOREIGN KEY (NadereBijhaard) REFERENCES Kern.NadereBijhaard (ID);
ALTER TABLE Kern.His_PersDeelnEUVerkiezingen ADD CONSTRAINT fk_His_PersDeelnEUVerkiezingen_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_PersDeelnEUVerkiezingen ADD CONSTRAINT fk_His_PersDeelnEUVerkiezingen_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersDeelnEUVerkiezingen ADD CONSTRAINT fk_His_PersDeelnEUVerkiezingen_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersDeelnEUVerkiezingen ADD CONSTRAINT fk_His_PersDeelnEUVerkiezingen_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersGeboorte ADD CONSTRAINT fk_His_PersGeboorte_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_PersGeboorte ADD CONSTRAINT fk_His_PersGeboorte_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersGeboorte ADD CONSTRAINT fk_His_PersGeboorte_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersGeboorte ADD CONSTRAINT fk_His_PersGeboorte_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersGeboorte ADD CONSTRAINT fk_His_PersGeboorte_GemGeboorte_Gem FOREIGN KEY (GemGeboorte) REFERENCES Kern.Gem (ID);
ALTER TABLE Kern.His_PersGeboorte ADD CONSTRAINT fk_His_PersGeboorte_LandGebiedGeboorte_LandGebied FOREIGN KEY (LandGebiedGeboorte) REFERENCES Kern.LandGebied (ID);
ALTER TABLE Kern.His_PersGeslachtsaand ADD CONSTRAINT fk_His_PersGeslachtsaand_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_PersGeslachtsaand ADD CONSTRAINT fk_His_PersGeslachtsaand_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersGeslachtsaand ADD CONSTRAINT fk_His_PersGeslachtsaand_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersGeslachtsaand ADD CONSTRAINT fk_His_PersGeslachtsaand_ActieAanpGel_Actie FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersGeslachtsaand ADD CONSTRAINT fk_His_PersGeslachtsaand_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersGeslachtsaand ADD CONSTRAINT fk_His_PersGeslachtsaand_Geslachtsaand_Geslachtsaand FOREIGN KEY (Geslachtsaand) REFERENCES Kern.Geslachtsaand (ID);
ALTER TABLE Kern.His_PersIDs ADD CONSTRAINT fk_His_PersIDs_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_PersIDs ADD CONSTRAINT fk_His_PersIDs_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersIDs ADD CONSTRAINT fk_His_PersIDs_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersIDs ADD CONSTRAINT fk_His_PersIDs_ActieAanpGel_Actie FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersIDs ADD CONSTRAINT fk_His_PersIDs_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersInschr ADD CONSTRAINT fk_His_PersInschr_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_PersInschr ADD CONSTRAINT fk_His_PersInschr_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersInschr ADD CONSTRAINT fk_His_PersInschr_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersInschr ADD CONSTRAINT fk_His_PersInschr_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersMigratie ADD CONSTRAINT fk_His_PersMigratie_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_PersMigratie ADD CONSTRAINT fk_His_PersMigratie_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersMigratie ADD CONSTRAINT fk_His_PersMigratie_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersMigratie ADD CONSTRAINT fk_His_PersMigratie_ActieAanpGel_Actie FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersMigratie ADD CONSTRAINT fk_His_PersMigratie_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersMigratie ADD CONSTRAINT fk_His_PersMigratie_SrtMigratie_SrtMigratie FOREIGN KEY (SrtMigratie) REFERENCES Kern.SrtMigratie (ID);
ALTER TABLE Kern.His_PersMigratie ADD CONSTRAINT fk_His_PersMigratie_RdnWijzMigratie_RdnWijzVerblijf FOREIGN KEY (RdnWijzMigratie) REFERENCES Kern.RdnWijzVerblijf (ID);
ALTER TABLE Kern.His_PersMigratie ADD CONSTRAINT fk_His_PersMigratie_AangMigratie_Aang FOREIGN KEY (AangMigratie) REFERENCES Kern.Aang (ID);
ALTER TABLE Kern.His_PersMigratie ADD CONSTRAINT fk_His_PersMigratie_LandGebiedMigratie_LandGebied FOREIGN KEY (LandGebiedMigratie) REFERENCES Kern.LandGebied (ID);
ALTER TABLE Kern.His_PersNaamgebruik ADD CONSTRAINT fk_His_PersNaamgebruik_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_PersNaamgebruik ADD CONSTRAINT fk_His_PersNaamgebruik_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersNaamgebruik ADD CONSTRAINT fk_His_PersNaamgebruik_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersNaamgebruik ADD CONSTRAINT fk_His_PersNaamgebruik_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersNaamgebruik ADD CONSTRAINT fk_His_PersNaamgebruik_Naamgebruik_Naamgebruik FOREIGN KEY (Naamgebruik) REFERENCES Kern.Naamgebruik (ID);
ALTER TABLE Kern.His_PersNaamgebruik ADD CONSTRAINT fk_His_PersNaamgebruik_PredicaatNaamgebruik_Predicaat FOREIGN KEY (PredicaatNaamgebruik) REFERENCES Kern.Predicaat (ID);
ALTER TABLE Kern.His_PersNaamgebruik ADD CONSTRAINT fk_His_PersNaamgebruik_AdellijkeTitelNaamgebruik_AdellijkeTitel FOREIGN KEY (AdellijkeTitelNaamgebruik) REFERENCES Kern.AdellijkeTitel (ID);
ALTER TABLE Kern.His_PersNrverwijzing ADD CONSTRAINT fk_His_PersNrverwijzing_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_PersNrverwijzing ADD CONSTRAINT fk_His_PersNrverwijzing_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersNrverwijzing ADD CONSTRAINT fk_His_PersNrverwijzing_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersNrverwijzing ADD CONSTRAINT fk_His_PersNrverwijzing_ActieAanpGel_Actie FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersNrverwijzing ADD CONSTRAINT fk_His_PersNrverwijzing_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersOverlijden ADD CONSTRAINT fk_His_PersOverlijden_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_PersOverlijden ADD CONSTRAINT fk_His_PersOverlijden_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersOverlijden ADD CONSTRAINT fk_His_PersOverlijden_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersOverlijden ADD CONSTRAINT fk_His_PersOverlijden_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersOverlijden ADD CONSTRAINT fk_His_PersOverlijden_GemOverlijden_Gem FOREIGN KEY (GemOverlijden) REFERENCES Kern.Gem (ID);
ALTER TABLE Kern.His_PersOverlijden ADD CONSTRAINT fk_His_PersOverlijden_LandGebiedOverlijden_LandGebied FOREIGN KEY (LandGebiedOverlijden) REFERENCES Kern.LandGebied (ID);
ALTER TABLE Kern.His_PersPK ADD CONSTRAINT fk_His_PersPK_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_PersPK ADD CONSTRAINT fk_His_PersPK_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersPK ADD CONSTRAINT fk_His_PersPK_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersPK ADD CONSTRAINT fk_His_PersPK_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersPK ADD CONSTRAINT fk_His_PersPK_GemPK_Partij FOREIGN KEY (GemPK) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.His_PersSamengesteldeNaam ADD CONSTRAINT fk_His_PersSamengesteldeNaam_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_PersSamengesteldeNaam ADD CONSTRAINT fk_His_PersSamengesteldeNaam_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersSamengesteldeNaam ADD CONSTRAINT fk_His_PersSamengesteldeNaam_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersSamengesteldeNaam ADD CONSTRAINT fk_His_PersSamengesteldeNaam_ActieAanpGel_Actie FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersSamengesteldeNaam ADD CONSTRAINT fk_His_PersSamengesteldeNaam_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersSamengesteldeNaam ADD CONSTRAINT fk_His_PersSamengesteldeNaam_Predicaat_Predicaat FOREIGN KEY (Predicaat) REFERENCES Kern.Predicaat (ID);
ALTER TABLE Kern.His_PersSamengesteldeNaam ADD CONSTRAINT fk_His_PersSamengesteldeNaam_AdellijkeTitel_AdellijkeTitel FOREIGN KEY (AdellijkeTitel) REFERENCES Kern.AdellijkeTitel (ID);
ALTER TABLE Kern.His_PersUitslKiesr ADD CONSTRAINT fk_His_PersUitslKiesr_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_PersUitslKiesr ADD CONSTRAINT fk_His_PersUitslKiesr_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersUitslKiesr ADD CONSTRAINT fk_His_PersUitslKiesr_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersUitslKiesr ADD CONSTRAINT fk_His_PersUitslKiesr_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersVerblijfsr ADD CONSTRAINT fk_His_PersVerblijfsr_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_PersVerblijfsr ADD CONSTRAINT fk_His_PersVerblijfsr_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersVerblijfsr ADD CONSTRAINT fk_His_PersVerblijfsr_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersVerblijfsr ADD CONSTRAINT fk_His_PersVerblijfsr_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersVerblijfsr ADD CONSTRAINT fk_His_PersVerblijfsr_AandVerblijfsr_AandVerblijfsr FOREIGN KEY (AandVerblijfsr) REFERENCES Kern.AandVerblijfsr (ID);
ALTER TABLE Kern.His_PersAdres ADD CONSTRAINT fk_His_PersAdres_PersAdres_PersAdres FOREIGN KEY (PersAdres) REFERENCES Kern.PersAdres (ID);
ALTER TABLE Kern.His_PersAdres ADD CONSTRAINT fk_His_PersAdres_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersAdres ADD CONSTRAINT fk_His_PersAdres_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersAdres ADD CONSTRAINT fk_His_PersAdres_ActieAanpGel_Actie FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersAdres ADD CONSTRAINT fk_His_PersAdres_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersAdres ADD CONSTRAINT fk_His_PersAdres_Srt_FunctieAdres FOREIGN KEY (Srt) REFERENCES Kern.FunctieAdres (ID);
ALTER TABLE Kern.His_PersAdres ADD CONSTRAINT fk_His_PersAdres_RdnWijz_RdnWijzVerblijf FOREIGN KEY (RdnWijz) REFERENCES Kern.RdnWijzVerblijf (ID);
ALTER TABLE Kern.His_PersAdres ADD CONSTRAINT fk_His_PersAdres_AangAdresh_Aang FOREIGN KEY (AangAdresh) REFERENCES Kern.Aang (ID);
ALTER TABLE Kern.His_PersAdres ADD CONSTRAINT fk_His_PersAdres_Gem_Gem FOREIGN KEY (Gem) REFERENCES Kern.Gem (ID);
ALTER TABLE Kern.His_PersAdres ADD CONSTRAINT fk_His_PersAdres_LandGebied_LandGebied FOREIGN KEY (LandGebied) REFERENCES Kern.LandGebied (ID);
ALTER TABLE Kern.His_PersGeslnaamcomp ADD CONSTRAINT fk_His_PersGeslnaamcomp_PersGeslnaamcomp_PersGeslnaamcomp FOREIGN KEY (PersGeslnaamcomp) REFERENCES Kern.PersGeslnaamcomp (ID);
ALTER TABLE Kern.His_PersGeslnaamcomp ADD CONSTRAINT fk_His_PersGeslnaamcomp_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersGeslnaamcomp ADD CONSTRAINT fk_His_PersGeslnaamcomp_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersGeslnaamcomp ADD CONSTRAINT fk_His_PersGeslnaamcomp_ActieAanpGel_Actie FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersGeslnaamcomp ADD CONSTRAINT fk_His_PersGeslnaamcomp_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersGeslnaamcomp ADD CONSTRAINT fk_His_PersGeslnaamcomp_Predicaat_Predicaat FOREIGN KEY (Predicaat) REFERENCES Kern.Predicaat (ID);
ALTER TABLE Kern.His_PersGeslnaamcomp ADD CONSTRAINT fk_His_PersGeslnaamcomp_AdellijkeTitel_AdellijkeTitel FOREIGN KEY (AdellijkeTitel) REFERENCES Kern.AdellijkeTitel (ID);
ALTER TABLE Kern.His_PersIndicatie ADD CONSTRAINT fk_His_PersIndicatie_PersIndicatie_PersIndicatie FOREIGN KEY (PersIndicatie) REFERENCES Kern.PersIndicatie (ID);
ALTER TABLE Kern.His_PersIndicatie ADD CONSTRAINT fk_His_PersIndicatie_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersIndicatie ADD CONSTRAINT fk_His_PersIndicatie_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersIndicatie ADD CONSTRAINT fk_His_PersIndicatie_ActieAanpGel_Actie FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersIndicatie ADD CONSTRAINT fk_His_PersIndicatie_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersNation ADD CONSTRAINT fk_His_PersNation_PersNation_PersNation FOREIGN KEY (PersNation) REFERENCES Kern.PersNation (ID);
ALTER TABLE Kern.His_PersNation ADD CONSTRAINT fk_His_PersNation_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersNation ADD CONSTRAINT fk_His_PersNation_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersNation ADD CONSTRAINT fk_His_PersNation_ActieAanpGel_Actie FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersNation ADD CONSTRAINT fk_His_PersNation_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersNation ADD CONSTRAINT fk_His_PersNation_RdnVerk_RdnVerkNLNation FOREIGN KEY (RdnVerk) REFERENCES Kern.RdnVerkNLNation (ID);
ALTER TABLE Kern.His_PersNation ADD CONSTRAINT fk_His_PersNation_RdnVerlies_RdnVerliesNLNation FOREIGN KEY (RdnVerlies) REFERENCES Kern.RdnVerliesNLNation (ID);
ALTER TABLE Kern.His_PersOnderzoek ADD CONSTRAINT fk_His_PersOnderzoek_PersOnderzoek_PersOnderzoek FOREIGN KEY (PersOnderzoek) REFERENCES Kern.PersOnderzoek (ID);
ALTER TABLE Kern.His_PersOnderzoek ADD CONSTRAINT fk_His_PersOnderzoek_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersOnderzoek ADD CONSTRAINT fk_His_PersOnderzoek_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersOnderzoek ADD CONSTRAINT fk_His_PersOnderzoek_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersOnderzoek ADD CONSTRAINT fk_His_PersOnderzoek_Rol_SrtPersOnderzoek FOREIGN KEY (Rol) REFERENCES Kern.SrtPersOnderzoek (ID);
ALTER TABLE Kern.His_PersReisdoc ADD CONSTRAINT fk_His_PersReisdoc_PersReisdoc_PersReisdoc FOREIGN KEY (PersReisdoc) REFERENCES Kern.PersReisdoc (ID);
ALTER TABLE Kern.His_PersReisdoc ADD CONSTRAINT fk_His_PersReisdoc_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersReisdoc ADD CONSTRAINT fk_His_PersReisdoc_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersReisdoc ADD CONSTRAINT fk_His_PersReisdoc_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersReisdoc ADD CONSTRAINT fk_His_PersReisdoc_AandInhingVermissing_AandInhingVermissingRei FOREIGN KEY (AandInhingVermissing) REFERENCES Kern.AandInhingVermissingReisdoc (ID);
ALTER TABLE Kern.His_PersVerificatie ADD CONSTRAINT fk_His_PersVerificatie_PersVerificatie_PersVerificatie FOREIGN KEY (PersVerificatie) REFERENCES Kern.PersVerificatie (ID);
ALTER TABLE Kern.His_PersVerificatie ADD CONSTRAINT fk_His_PersVerificatie_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersVerificatie ADD CONSTRAINT fk_His_PersVerificatie_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersVerificatie ADD CONSTRAINT fk_His_PersVerificatie_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersVerstrbeperking ADD CONSTRAINT fk_His_PersVerstrbeperking_PersVerstrbeperking_PersVerstrbeperk FOREIGN KEY (PersVerstrbeperking) REFERENCES Kern.PersVerstrbeperking (ID);
ALTER TABLE Kern.His_PersVerstrbeperking ADD CONSTRAINT fk_His_PersVerstrbeperking_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersVerstrbeperking ADD CONSTRAINT fk_His_PersVerstrbeperking_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersVerstrbeperking ADD CONSTRAINT fk_His_PersVerstrbeperking_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersVoornaam ADD CONSTRAINT fk_His_PersVoornaam_PersVoornaam_PersVoornaam FOREIGN KEY (PersVoornaam) REFERENCES Kern.PersVoornaam (ID);
ALTER TABLE Kern.His_PersVoornaam ADD CONSTRAINT fk_His_PersVoornaam_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersVoornaam ADD CONSTRAINT fk_His_PersVoornaam_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersVoornaam ADD CONSTRAINT fk_His_PersVoornaam_ActieAanpGel_Actie FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersVoornaam ADD CONSTRAINT fk_His_PersVoornaam_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Relatie ADD CONSTRAINT fk_His_Relatie_Relatie_Relatie FOREIGN KEY (Relatie) REFERENCES Kern.Relatie (ID);
ALTER TABLE Kern.His_Relatie ADD CONSTRAINT fk_His_Relatie_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Relatie ADD CONSTRAINT fk_His_Relatie_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Relatie ADD CONSTRAINT fk_His_Relatie_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Relatie ADD CONSTRAINT fk_His_Relatie_GemAanv_Gem FOREIGN KEY (GemAanv) REFERENCES Kern.Gem (ID);
ALTER TABLE Kern.His_Relatie ADD CONSTRAINT fk_His_Relatie_LandGebiedAanv_LandGebied FOREIGN KEY (LandGebiedAanv) REFERENCES Kern.LandGebied (ID);
ALTER TABLE Kern.His_Relatie ADD CONSTRAINT fk_His_Relatie_RdnEinde_RdnEindeRelatie FOREIGN KEY (RdnEinde) REFERENCES Kern.RdnEindeRelatie (ID);
ALTER TABLE Kern.His_Relatie ADD CONSTRAINT fk_His_Relatie_GemEinde_Gem FOREIGN KEY (GemEinde) REFERENCES Kern.Gem (ID);
ALTER TABLE Kern.His_Relatie ADD CONSTRAINT fk_His_Relatie_LandGebiedEinde_LandGebied FOREIGN KEY (LandGebiedEinde) REFERENCES Kern.LandGebied (ID);
ALTER TABLE Kern.His_Terugmelding ADD CONSTRAINT fk_His_Terugmelding_Terugmelding_Terugmelding FOREIGN KEY (Terugmelding) REFERENCES Kern.Terugmelding (ID);
ALTER TABLE Kern.His_Terugmelding ADD CONSTRAINT fk_His_Terugmelding_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Terugmelding ADD CONSTRAINT fk_His_Terugmelding_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Terugmelding ADD CONSTRAINT fk_His_Terugmelding_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Terugmelding ADD CONSTRAINT fk_His_Terugmelding_Onderzoek_Onderzoek FOREIGN KEY (Onderzoek) REFERENCES Kern.Onderzoek (ID);
ALTER TABLE Kern.His_Terugmelding ADD CONSTRAINT fk_His_Terugmelding_Status_StatusTerugmelding FOREIGN KEY (Status) REFERENCES Kern.StatusTerugmelding (ID);
ALTER TABLE Kern.His_TerugmeldingContactpers ADD CONSTRAINT fk_His_TerugmeldingContactpers_Terugmelding_Terugmelding FOREIGN KEY (Terugmelding) REFERENCES Kern.Terugmelding (ID);
ALTER TABLE Kern.His_TerugmeldingContactpers ADD CONSTRAINT fk_His_TerugmeldingContactpers_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_TerugmeldingContactpers ADD CONSTRAINT fk_His_TerugmeldingContactpers_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_TerugmeldingContactpers ADD CONSTRAINT fk_His_TerugmeldingContactpers_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);


-- SQL inhoud van bestand: bmr/Kern/Kern_BRP_structuur_aanvullend.sql
--------------------------------------------------------------------------------
-- PostgreSQL SQL-DDL bestand voor Basisregistratie Personen (BRP)            --
-- PostgreSQL - Kern Structuur Aanvullend                                     --
--------------------------------------------------------------------------------
--
-- Gegenereerd door BRP Meta Register Release 44.
-- Gegenereerd op: dinsdag 24 nov 2015 13:59:27
--
--------------------------------------------------------------------------------

-- Index op OGM velden
CREATE INDEX ix_His_PersAfnemerindicatie_TsReg_TsVerval ON AutAut.His_PersAfnemerindicatie (TsReg, TsVerval);

-- Door het gebruikt van standaard ORM software om het opslaan en lezen van objecten uit de database te realiseren,
-- is er voor GegevenInOnderzoek behoefte aan een extra kolom 'TblGegeven' die een 1-op-1 mapping representeert
-- tussen entiteit classen in de software en een waarde in de TblGegeven kolom in de database.
-- Deze kolom voegt geen extra informatie toe ten opzichte van de SrtGegeven kolom, en wordt daarom gerealiseerd
-- als een 'writeable view' gebaseerd op de tabellen GegevenInOnderzoek en Element.

CREATE VIEW Kern.GegevenInOnderzoekOrmMapping (ID, Onderzoek, Element, ObjectSleutelGegeven, VoorkomenSleutelGegeven, TblObject, TblVoorkomen) AS
  SELECT ID, Onderzoek, Element, ObjectSleutelGegeven, VoorkomenSleutelGegeven,
           COALESCE( (SELECT Tabel FROM Kern.Element E WHERE E.ID = GIO.Element), Element) TblObject,
           COALESCE( (SELECT HisTabel FROM Kern.Element E WHERE E.ID = GIO.Element), Element) TblVoorkomen
  FROM Kern.GegevenInOnderzoek GIO;

ALTER TABLE Kern.GegevenInOnderzoekOrmMapping ALTER COLUMN Id SET DEFAULT nextval('kern.seq_gegeveninonderzoek');

CREATE OR REPLACE RULE GegevenInOnderzoekOrmMapping_INSERT AS ON INSERT TO Kern.GegevenInOnderzoekOrmMapping DO INSTEAD (
   INSERT INTO Kern.GegevenInOnderzoek (Id, Onderzoek, Element, ObjectSleutelGegeven, VoorkomenSleutelGegeven) VALUES (NEW.Id, NEW.Onderzoek, NEW.Element, NEW.ObjectSleutelGegeven, NEW.VoorkomenSleutelGegeven)
);
CREATE OR REPLACE RULE GegevenInOnderzoekOrmMapping_DELETE AS ON DELETE TO Kern.GegevenInOnderzoekOrmMapping DO INSTEAD (
  DELETE FROM kern.GegevenInOnderzoek WHERE Id = OLD.Id
);
CREATE OR REPLACE RULE GegevenInOnderzoekOrmMapping_UPDATE AS ON UPDATE TO Kern.GegevenInOnderzoekOrmMapping DO INSTEAD (
  UPDATE kern.GegevenInOnderzoek SET Onderzoek = NEW.Onderzoek, Element = NEW.Element, ObjectSleutelGegeven = NEW.ObjectSleutelGegeven, VoorkomenSleutelGegeven = NEW.VoorkomenSleutelGegeven, Id = NEW.Id WHERE Id = OLD.Id
);


-- Opschonen objecten (onterecht) gemaakt in public schema door vorige releases (<BMR 35).
CREATE OR REPLACE FUNCTION BMR_drop_oude_objecten() RETURNS void AS $$
   DECLARE SQL TEXT;
BEGIN
   FOR SQL IN
      SELECT 'DROP FUNCTION IF EXISTS ' || ns.nspname || '.' || proname || '(' || oidvectortypes(proargtypes) || ');'
      FROM pg_proc proc
           JOIN pg_namespace ns ON (proc.pronamespace = ns.oid)
      WHERE
         ns.nspname = 'public'
         AND proname like 'block_dubbel\_%'
         AND NOT EXISTS(select * from pg_depend dep where dep.refobjid = proc.oid)
      ORDER BY proname
   LOOP
      EXECUTE SQL;
   END LOOP;
END $$ LANGUAGE plpgsql;

SELECT BMR_drop_oude_objecten();

DROP FUNCTION BMR_drop_oude_objecten();

-- Sequences van stamtabellen (her)zetten naar de juiste waarden
SELECT setval('AutAut.seq_BijhautorisatieSrtAdmHnd', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.BijhautorisatieSrtAdmHnd), false);
SELECT setval('AutAut.seq_Dienst', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.Dienst), false);
SELECT setval('AutAut.seq_Dienstbundel', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.Dienstbundel), false);
SELECT setval('AutAut.seq_DienstbundelGroep', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.DienstbundelGroep), false);
SELECT setval('AutAut.seq_DienstbundelGroepAttr', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.DienstbundelGroepAttr), false);
SELECT setval('AutAut.seq_DienstbundelLO3Rubriek', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.DienstbundelLO3Rubriek), false);
SELECT setval('AutAut.seq_EffectAfnemerindicaties', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.EffectAfnemerindicaties), false);
SELECT setval('AutAut.seq_His_Dienst', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.His_Dienst), false);
SELECT setval('AutAut.seq_His_DienstAttendering', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.His_DienstAttendering), false);
SELECT setval('AutAut.seq_His_DienstSelectie', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.His_DienstSelectie), false);
SELECT setval('AutAut.seq_His_Dienstbundel', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.His_Dienstbundel), false);
SELECT setval('AutAut.seq_His_DienstbundelGroep', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.His_DienstbundelGroep), false);
SELECT setval('AutAut.seq_His_DienstbundelGroepAttr', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.His_DienstbundelGroepAttr), false);
SELECT setval('AutAut.seq_His_DienstbundelLO3Rubriek', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.His_DienstbundelLO3Rubriek), false);
SELECT setval('AutAut.seq_His_Levsautorisatie', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.His_Levsautorisatie), false);
SELECT setval('AutAut.seq_His_PartijFiatuitz', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.His_PartijFiatuitz), false);
SELECT setval('AutAut.seq_His_ToegangBijhautorisatie', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.His_ToegangBijhautorisatie), false);
SELECT setval('AutAut.seq_His_ToegangLevsautorisatie', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.His_ToegangLevsautorisatie), false);
SELECT setval('AutAut.seq_Levsautorisatie', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.Levsautorisatie), false);
SELECT setval('AutAut.seq_PartijFiatuitz', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.PartijFiatuitz), false);
SELECT setval('AutAut.seq_ToegangBijhautorisatie', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.ToegangBijhautorisatie), false);
SELECT setval('AutAut.seq_ToegangLevsautorisatie', (SELECT COALESCE(MAX(ID)+1, 1) FROM AutAut.ToegangLevsautorisatie), false);
SELECT setval('BRM.seq_His_Regelsituatie', (SELECT COALESCE(MAX(ID)+1, 1) FROM BRM.His_Regelsituatie), false);
SELECT setval('BRM.seq_Regelsituatie', (SELECT COALESCE(MAX(ID)+1, 1) FROM BRM.Regelsituatie), false);
SELECT setval('Conv.seq_ConvAandInhingVermissingReis', (SELECT COALESCE(MAX(ID)+1, 1) FROM Conv.ConvAandInhingVermissingReis), false);
SELECT setval('Conv.seq_ConvAangifteAdresh', (SELECT COALESCE(MAX(ID)+1, 1) FROM Conv.ConvAangifteAdresh), false);
SELECT setval('Conv.seq_ConvAdellijkeTitelPredikaat', (SELECT COALESCE(MAX(ID)+1, 1) FROM Conv.ConvAdellijkeTitelPredikaat), false);
SELECT setval('Conv.seq_ConvLO3Rubriek', (SELECT COALESCE(MAX(ID)+1, 1) FROM Conv.ConvLO3Rubriek), false);
SELECT setval('Conv.seq_ConvRNIDeelnemer', (SELECT COALESCE(MAX(ID)+1, 1) FROM Conv.ConvRNIDeelnemer), false);
SELECT setval('Conv.seq_ConvRdnBeeindigenNation', (SELECT COALESCE(MAX(ID)+1, 1) FROM Conv.ConvRdnBeeindigenNation), false);
SELECT setval('Conv.seq_ConvRdnOntbindingHuwelijkGer', (SELECT COALESCE(MAX(ID)+1, 1) FROM Conv.ConvRdnOntbindingHuwelijkGer), false);
SELECT setval('Conv.seq_ConvRdnOpnameNation', (SELECT COALESCE(MAX(ID)+1, 1) FROM Conv.ConvRdnOpnameNation), false);
SELECT setval('Conv.seq_ConvRdnOpschorting', (SELECT COALESCE(MAX(ID)+1, 1) FROM Conv.ConvRdnOpschorting), false);
SELECT setval('Conv.seq_ConvSrtNLReisdoc', (SELECT COALESCE(MAX(ID)+1, 1) FROM Conv.ConvSrtNLReisdoc), false);
SELECT setval('Conv.seq_ConvVoorvoegsel', (SELECT COALESCE(MAX(ID)+1, 1) FROM Conv.ConvVoorvoegsel), false);
SELECT setval('IST.seq_Autorisatietabel', (SELECT COALESCE(MAX(ID)+1, 1) FROM IST.Autorisatietabel), false);
SELECT setval('Kern.seq_AandInhingVermissingReisdoc', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.AandInhingVermissingReisdoc), false);
SELECT setval('Kern.seq_AandVerblijfsr', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.AandVerblijfsr), false);
SELECT setval('Kern.seq_Aang', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.Aang), false);
SELECT setval('Kern.seq_AdellijkeTitel', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.AdellijkeTitel), false);
SELECT setval('Kern.seq_AuttypeVanAfgifteReisdoc', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.AuttypeVanAfgifteReisdoc), false);
SELECT setval('Kern.seq_Gem', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.Gem), false);
SELECT setval('Kern.seq_His_Partij', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.His_Partij), false);
SELECT setval('Kern.seq_His_PartijBijhouding', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.His_PartijBijhouding), false);
SELECT setval('Kern.seq_His_PartijRol', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.His_PartijRol), false);
SELECT setval('Kern.seq_Koppelvlak', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.Koppelvlak), false);
SELECT setval('Kern.seq_LandGebied', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.LandGebied), false);
SELECT setval('Kern.seq_Nation', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.Nation), false);
SELECT setval('Kern.seq_Partij', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.Partij), false);
SELECT setval('Kern.seq_PartijRol', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.PartijRol), false);
SELECT setval('Kern.seq_Plaats', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.Plaats), false);
SELECT setval('Kern.seq_Predicaat', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.Predicaat), false);
SELECT setval('Kern.seq_RdnEindeRelatie', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.RdnEindeRelatie), false);
SELECT setval('Kern.seq_RdnVerkNLNation', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.RdnVerkNLNation), false);
SELECT setval('Kern.seq_RdnVerliesNLNation', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.RdnVerliesNLNation), false);
SELECT setval('Kern.seq_RdnWijzVerblijf', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.RdnWijzVerblijf), false);
SELECT setval('Kern.seq_Rechtsgrond', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.Rechtsgrond), false);
SELECT setval('Kern.seq_SrtDoc', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.SrtDoc), false);
SELECT setval('Kern.seq_SrtNLReisdoc', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.SrtNLReisdoc), false);
SELECT setval('Kern.seq_Voorvoegsel', (SELECT COALESCE(MAX(ID)+1, 1) FROM Kern.Voorvoegsel), false);
SELECT setval('MigBlok.seq_RdnBlokkering', (SELECT COALESCE(MAX(ID)+1, 1) FROM MigBlok.RdnBlokkering), false);

-- SQL inhoud van bestand: bmr/Kern/Kern_BRP_custom_changes.sql
--------------------------------------------------------------------------------
-- PostgreSQL SQL-DDL bestand voor Basisregistratie Personen (BRP)            --
-- PostgreSQL - Kern Custom changes                                           --
--------------------------------------------------------------------------------
--
-- Gegenereerd door BRP Meta Register Release 44.
-- Gegenereerd op: dinsdag 24 nov 2015 13:59:27
--
--------------------------------------------------------------------------------


-- Handmatige database aanpassingen toegevoegd door de ontwikkelteams.
-- Dit zijn bijvoorbeeld aanpassingen die nog niet in BMR opgenomen zijn. Doelstelling is dat dit bestand (uiteindelijk) leeg blijft.


-- SQL inhoud van bestand: bmr/Ber/Bericht_BRP_structuur.sql
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


-- SQL inhoud van bestand: bmr/Ber/Bericht_BRP_structuur_aanvullend.sql
--------------------------------------------------------------------------------
-- PostgreSQL SQL-DDL bestand voor Basisregistratie Personen (BRP)            --
-- PostgreSQL - Bericht Structuur Aanvullend                                  --
--------------------------------------------------------------------------------
--
-- Gegenereerd door BRP Meta Register Release 44.
-- Gegenereerd op: dinsdag 24 nov 2015 13:59:27
--
--------------------------------------------------------------------------------


-- Sequences van stamtabellen (her)zetten naar de juiste waarden

-- SQL inhoud van bestand: bmr/Ber/Bericht_BRP_custom_changes.sql
--------------------------------------------------------------------------------
-- PostgreSQL SQL-DDL bestand voor Basisregistratie Personen (BRP)            --
-- PostgreSQL - Bericht Custom changes                                        --
--------------------------------------------------------------------------------
--
-- Gegenereerd door BRP Meta Register Release 44.
-- Gegenereerd op: dinsdag 24 nov 2015 13:59:27
--
--------------------------------------------------------------------------------


-- Handmatige database aanpassingen toegevoegd door de ontwikkelteams.
-- Dit zijn bijvoorbeeld aanpassingen die nog niet in BMR opgenomen zijn. Doelstelling is dat dit bestand (uiteindelijk) leeg blijft.


-- SQL inhoud van bestand: bmr/Prot/Protocol_BRP_structuur.sql
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


-- SQL inhoud van bestand: bmr/Prot/Protocol_BRP_structuur_aanvullend.sql
--------------------------------------------------------------------------------
-- PostgreSQL SQL-DDL bestand voor Basisregistratie Personen (BRP)            --
-- PostgreSQL - Protocol Structuur Aanvullend                                 --
--------------------------------------------------------------------------------
--
-- Gegenereerd door BRP Meta Register Release 44.
-- Gegenereerd op: dinsdag 24 nov 2015 13:59:27
--
--------------------------------------------------------------------------------


-- Sequences van stamtabellen (her)zetten naar de juiste waarden

-- SQL inhoud van bestand: bmr/Prot/Protocol_BRP_custom_changes.sql
--------------------------------------------------------------------------------
-- PostgreSQL SQL-DDL bestand voor Basisregistratie Personen (BRP)            --
-- PostgreSQL - Protocol Custom changes                                       --
--------------------------------------------------------------------------------
--
-- Gegenereerd door BRP Meta Register Release 44.
-- Gegenereerd op: dinsdag 24 nov 2015 13:59:27
--
--------------------------------------------------------------------------------


-- Handmatige database aanpassingen toegevoegd door de ontwikkelteams.
-- Dit zijn bijvoorbeeld aanpassingen die nog niet in BMR opgenomen zijn. Doelstelling is dat dit bestand (uiteindelijk) leeg blijft.


-- SQL inhoud van bestand: bmr/Alg/Algemeen_BRP_structuur_aanvullend.sql
--------------------------------------------------------------------------------
-- PostgreSQL SQL-DDL bestand voor Basisregistratie Personen (BRP)            --
-- PostgreSQL - Algemeen Structuur Aanvullend                                 --
--------------------------------------------------------------------------------
--
-- Gegenereerd door BRP Meta Register Release 44.
-- Gegenereerd op: dinsdag 24 nov 2015 13:59:27
--
--------------------------------------------------------------------------------

DROP TABLE IF EXISTS public.DBversion;
CREATE TABLE public.DBversion (
   ID             Smallint       NOT NULL,
   FullVersion    Varchar(200)   NOT NULL,    /* de volledige omschrijving van de versie om het moment dat de data generator werd gebouwd. */
   ReleaseVersion Varchar(64),                /* de (svn) release versie. */
   BuildTimestamp Varchar(32),                /* de timestamp dat de generator werd gedraaid */
   CONSTRAINT PK_DBVersion PRIMARY KEY (ID)
);

INSERT INTO public.DBversion (ID, FullVersion, ReleaseVersion, BuildTimestamp) VALUES (1, '${versie} ${build.qualifier} #${build.number}', '${versie}', '${built.on}');

-- Statistieken bijwerken
ANALYZE;

-- Lev schema is overgegaan naar Prot schema. Opruimen oude schema
DROP SCHEMA IF EXISTS Lev CASCADE;


-- SQL inhoud van bestand: bmr/Alg/Algemeen_BRP_custom_changes.sql
--------------------------------------------------------------------------------
-- PostgreSQL SQL-DDL bestand voor Basisregistratie Personen (BRP)            --
-- PostgreSQL - Algemeen Custom changes                                       --
--------------------------------------------------------------------------------
--
-- Gegenereerd door BRP Meta Register Release 44.
-- Gegenereerd op: dinsdag 24 nov 2015 13:59:27
--
--------------------------------------------------------------------------------


-- Handmatige database aanpassingen toegevoegd door de ontwikkelteams.
-- Dit zijn bijvoorbeeld aanpassingen die nog niet in BMR opgenomen zijn. Doelstelling is dat dit bestand (uiteindelijk) leeg blijft.


-- SQL inhoud van bestand: bmr/Kern/Kern_BRP_indexen.sql
--------------------------------------------------------------------------------
-- PostgreSQL SQL-DDL bestand voor Basisregistratie Personen (BRP)            --
-- PostgreSQL - Kern Indexen DDL                                              --
--------------------------------------------------------------------------------
--
-- Gegenereerd door BRP Meta Register Release 44.
-- Gegenereerd op: dinsdag 24 nov 2015 13:59:27
--
--------------------------------------------------------------------------------

CREATE INDEX ix_Dienst_Dienstbundel ON AutAut.Dienst (Dienstbundel); -- Index door foreign key
CREATE INDEX ix_Dienstbundel_Levsautorisatie ON AutAut.Dienstbundel (Levsautorisatie); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_DienstbundelGroep_Dienstbundel ON AutAut.DienstbundelGroep (Dienstbundel); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_DienstbundelGroepAttr_DienstbundelGroep ON AutAut.DienstbundelGroepAttr (DienstbundelGroep); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_DienstbundelLO3Rubriek_Dienstbundel ON AutAut.DienstbundelLO3Rubriek (Dienstbundel); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_Dienst_Dienst ON AutAut.His_Dienst (Dienst); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Dienst_ActieInh ON AutAut.His_Dienst (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Dienst_ActieVerval ON AutAut.His_Dienst (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Dienst_ActieVervalTbvLevMuts ON AutAut.His_Dienst (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_Dienst_Dienst_TsReg ON AutAut.His_Dienst (Dienst, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION AutAut.uc_His_Dienst_Dienst_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM AutAut.His_Dienst
            WHERE
               ID <> NEW.ID
               AND Dienst = NEW.Dienst
               AND TsReg = NEW.TsReg
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Dienst, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_Dienst_Dienst_TsReg_tr ON AutAut.His_Dienst;
CREATE CONSTRAINT TRIGGER uc_His_Dienst_Dienst_TsReg_tr AFTER INSERT OR UPDATE ON AutAut.His_Dienst DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE AutAut.uc_His_Dienst_Dienst_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_DienstAttendering_Dienst ON AutAut.His_DienstAttendering (Dienst); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_DienstAttendering_ActieInh ON AutAut.His_DienstAttendering (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_DienstAttendering_ActieVerval ON AutAut.His_DienstAttendering (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_DienstAttendering_ActieVervalTbvLevMuts ON AutAut.His_DienstAttendering (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_DienstAttendering_Dienst_TsReg ON AutAut.His_DienstAttendering (Dienst, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION AutAut.uc_His_DienstAttendering_Dienst_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM AutAut.His_DienstAttendering
            WHERE
               ID <> NEW.ID
               AND Dienst = NEW.Dienst
               AND TsReg = NEW.TsReg
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Dienst, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_DienstAttendering_Dienst_TsReg_tr ON AutAut.His_DienstAttendering;
CREATE CONSTRAINT TRIGGER uc_His_DienstAttendering_Dienst_TsReg_tr AFTER INSERT OR UPDATE ON AutAut.His_DienstAttendering DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE AutAut.uc_His_DienstAttendering_Dienst_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_DienstSelectie_Dienst ON AutAut.His_DienstSelectie (Dienst); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_DienstSelectie_ActieInh ON AutAut.His_DienstSelectie (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_DienstSelectie_ActieVerval ON AutAut.His_DienstSelectie (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_DienstSelectie_ActieVervalTbvLevMuts ON AutAut.His_DienstSelectie (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_DienstSelectie_Dienst_TsReg ON AutAut.His_DienstSelectie (Dienst, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION AutAut.uc_His_DienstSelectie_Dienst_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM AutAut.His_DienstSelectie
            WHERE
               ID <> NEW.ID
               AND Dienst = NEW.Dienst
               AND TsReg = NEW.TsReg
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Dienst, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_DienstSelectie_Dienst_TsReg_tr ON AutAut.His_DienstSelectie;
CREATE CONSTRAINT TRIGGER uc_His_DienstSelectie_Dienst_TsReg_tr AFTER INSERT OR UPDATE ON AutAut.His_DienstSelectie DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE AutAut.uc_His_DienstSelectie_Dienst_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_Dienstbundel_Dienstbundel ON AutAut.His_Dienstbundel (Dienstbundel); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Dienstbundel_ActieInh ON AutAut.His_Dienstbundel (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Dienstbundel_ActieVerval ON AutAut.His_Dienstbundel (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Dienstbundel_ActieVervalTbvLevMuts ON AutAut.His_Dienstbundel (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_Dienstbundel_Dienstbundel_TsReg ON AutAut.His_Dienstbundel (Dienstbundel, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION AutAut.uc_His_Dienstbundel_Dienstbundel_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM AutAut.His_Dienstbundel
            WHERE
               ID <> NEW.ID
               AND Dienstbundel = NEW.Dienstbundel
               AND TsReg = NEW.TsReg
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Dienstbundel, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_Dienstbundel_Dienstbundel_TsReg_tr ON AutAut.His_Dienstbundel;
CREATE CONSTRAINT TRIGGER uc_His_Dienstbundel_Dienstbundel_TsReg_tr AFTER INSERT OR UPDATE ON AutAut.His_Dienstbundel DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE AutAut.uc_His_Dienstbundel_Dienstbundel_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_DienstbundelGroep_DienstbundelGroep ON AutAut.His_DienstbundelGroep (DienstbundelGroep); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_DienstbundelGroep_ActieInh ON AutAut.His_DienstbundelGroep (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_DienstbundelGroep_ActieVerval ON AutAut.His_DienstbundelGroep (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_DienstbundelGroep_ActieVervalTbvLevMuts ON AutAut.His_DienstbundelGroep (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_DienstbundelGroep_DienstbundelGroep_TsReg ON AutAut.His_DienstbundelGroep (DienstbundelGroep, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION AutAut.uc_His_DienstbundelGroep_DienstbundelGroep_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM AutAut.His_DienstbundelGroep
            WHERE
               ID <> NEW.ID
               AND DienstbundelGroep = NEW.DienstbundelGroep
               AND TsReg = NEW.TsReg
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.DienstbundelGroep, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_DienstbundelGroep_DienstbundelGroep_TsReg_tr ON AutAut.His_DienstbundelGroep;
CREATE CONSTRAINT TRIGGER uc_His_DienstbundelGroep_DienstbundelGroep_TsReg_tr AFTER INSERT OR UPDATE ON AutAut.His_DienstbundelGroep DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE AutAut.uc_His_DienstbundelGroep_DienstbundelGroep_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_DienstbundelGroepAttr_DienstbundelGroepAttr ON AutAut.His_DienstbundelGroepAttr (DienstbundelGroepAttr); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_DienstbundelGroepAttr_ActieInh ON AutAut.His_DienstbundelGroepAttr (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_DienstbundelGroepAttr_ActieVerval ON AutAut.His_DienstbundelGroepAttr (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_DienstbundelGroepAttr_ActieVervalTbvLevMuts ON AutAut.His_DienstbundelGroepAttr (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_DienstbundelGroepAttr_DienstbundelGroepAttr_TsReg ON AutAut.His_DienstbundelGroepAttr (DienstbundelGroepAttr, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION AutAut.uc_His_DienstbundelGroepAttr_DienstbundelGroepAttr_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM AutAut.His_DienstbundelGroepAttr
            WHERE
               ID <> NEW.ID
               AND DienstbundelGroepAttr = NEW.DienstbundelGroepAttr
               AND TsReg = NEW.TsReg
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.DienstbundelGroepAttr, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_DienstbundelGroepAttr_DienstbundelGroepAttr_TsReg_tr ON AutAut.His_DienstbundelGroepAttr;
CREATE CONSTRAINT TRIGGER uc_His_DienstbundelGroepAttr_DienstbundelGroepAttr_TsReg_tr AFTER INSERT OR UPDATE ON AutAut.His_DienstbundelGroepAttr DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE AutAut.uc_His_DienstbundelGroepAttr_DienstbundelGroepAttr_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_DienstbundelLO3Rubriek_DienstbundelLO3Rubriek ON AutAut.His_DienstbundelLO3Rubriek (DienstbundelLO3Rubriek); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_DienstbundelLO3Rubriek_ActieInh ON AutAut.His_DienstbundelLO3Rubriek (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_DienstbundelLO3Rubriek_ActieVerval ON AutAut.His_DienstbundelLO3Rubriek (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_DienstbundelLO3Rubriek_ActieVervalTbvLevMuts ON AutAut.His_DienstbundelLO3Rubriek (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_DienstbundelLO3Rubriek_DienstbundelLO3Rubriek_TsReg ON AutAut.His_DienstbundelLO3Rubriek (DienstbundelLO3Rubriek, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION AutAut.uc_His_DienstbundelLO3Rubriek_DienstbundelLO3Rubriek_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM AutAut.His_DienstbundelLO3Rubriek
            WHERE
               ID <> NEW.ID
               AND DienstbundelLO3Rubriek = NEW.DienstbundelLO3Rubriek
               AND TsReg = NEW.TsReg
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.DienstbundelLO3Rubriek, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_DienstbundelLO3Rubriek_DienstbundelLO3Rubriek_TsReg_tr ON AutAut.His_DienstbundelLO3Rubriek;
CREATE CONSTRAINT TRIGGER uc_His_DienstbundelLO3Rubriek_DienstbundelLO3Rubriek_TsReg_tr AFTER INSERT OR UPDATE ON AutAut.His_DienstbundelLO3Rubriek DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE AutAut.uc_His_DienstbundelLO3Rubriek_DienstbundelLO3Rubriek_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_Levsautorisatie_Levsautorisatie ON AutAut.His_Levsautorisatie (Levsautorisatie); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Levsautorisatie_ActieInh ON AutAut.His_Levsautorisatie (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Levsautorisatie_ActieVerval ON AutAut.His_Levsautorisatie (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Levsautorisatie_ActieVervalTbvLevMuts ON AutAut.His_Levsautorisatie (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_Levsautorisatie_Levsautorisatie_TsReg ON AutAut.His_Levsautorisatie (Levsautorisatie, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION AutAut.uc_His_Levsautorisatie_Levsautorisatie_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM AutAut.His_Levsautorisatie
            WHERE
               ID <> NEW.ID
               AND Levsautorisatie = NEW.Levsautorisatie
               AND TsReg = NEW.TsReg
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Levsautorisatie, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_Levsautorisatie_Levsautorisatie_TsReg_tr ON AutAut.His_Levsautorisatie;
CREATE CONSTRAINT TRIGGER uc_His_Levsautorisatie_Levsautorisatie_TsReg_tr AFTER INSERT OR UPDATE ON AutAut.His_Levsautorisatie DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE AutAut.uc_His_Levsautorisatie_Levsautorisatie_TsReg();

-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PartijFiatuitz_ActieInh ON AutAut.His_PartijFiatuitz (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PartijFiatuitz_ActieVerval ON AutAut.His_PartijFiatuitz (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PartijFiatuitz_ActieVervalTbvLevMuts ON AutAut.His_PartijFiatuitz (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX ix_His_PartijFiatuitz_PartijBijhvoorstel ON AutAut.His_PartijFiatuitz (PartijBijhvoorstel); -- Index door foreign key
CREATE INDEX uc_His_PartijFiatuitz_PartijFiatuitz_TsReg ON AutAut.His_PartijFiatuitz (PartijFiatuitz, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION AutAut.uc_His_PartijFiatuitz_PartijFiatuitz_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM AutAut.His_PartijFiatuitz
            WHERE
               ID <> NEW.ID
               AND PartijFiatuitz = NEW.PartijFiatuitz
               AND TsReg = NEW.TsReg
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.PartijFiatuitz, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PartijFiatuitz_PartijFiatuitz_TsReg_tr ON AutAut.His_PartijFiatuitz;
CREATE CONSTRAINT TRIGGER uc_His_PartijFiatuitz_PartijFiatuitz_TsReg_tr AFTER INSERT OR UPDATE ON AutAut.His_PartijFiatuitz DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE AutAut.uc_His_PartijFiatuitz_PartijFiatuitz_TsReg();

-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_ToegangBijhautorisatie_ActieInh ON AutAut.His_ToegangBijhautorisatie (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_ToegangBijhautorisatie_ActieVerval ON AutAut.His_ToegangBijhautorisatie (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_ToegangBijhautorisatie_ActieVervalTbvLevMuts ON AutAut.His_ToegangBijhautorisatie (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_ToegangBijhautorisatie_ToegangBijhautorisatie_TsReg ON AutAut.His_ToegangBijhautorisatie (ToegangBijhautorisatie, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION AutAut.uc_His_ToegangBijhautorisatie_ToegangBijhautorisatie_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM AutAut.His_ToegangBijhautorisatie
            WHERE
               ID <> NEW.ID
               AND ToegangBijhautorisatie = NEW.ToegangBijhautorisatie
               AND TsReg = NEW.TsReg
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.ToegangBijhautorisatie, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_ToegangBijhautorisatie_ToegangBijhautorisatie_TsReg_tr ON AutAut.His_ToegangBijhautorisatie;
CREATE CONSTRAINT TRIGGER uc_His_ToegangBijhautorisatie_ToegangBijhautorisatie_TsReg_tr AFTER INSERT OR UPDATE ON AutAut.His_ToegangBijhautorisatie DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE AutAut.uc_His_ToegangBijhautorisatie_ToegangBijhautorisatie_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_ToegangLevsautorisatie_ToegangLevsautorisatie ON AutAut.His_ToegangLevsautorisatie (ToegangLevsautorisatie); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_ToegangLevsautorisatie_ActieInh ON AutAut.His_ToegangLevsautorisatie (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_ToegangLevsautorisatie_ActieVerval ON AutAut.His_ToegangLevsautorisatie (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_ToegangLevsautorisatie_ActieVervalTbvLevMuts ON AutAut.His_ToegangLevsautorisatie (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_ToegangLevsautorisatie_ToegangLevsautorisatie_TsReg ON AutAut.His_ToegangLevsautorisatie (ToegangLevsautorisatie, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION AutAut.uc_His_ToegangLevsautorisatie_ToegangLevsautorisatie_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM AutAut.His_ToegangLevsautorisatie
            WHERE
               ID <> NEW.ID
               AND ToegangLevsautorisatie = NEW.ToegangLevsautorisatie
               AND TsReg = NEW.TsReg
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.ToegangLevsautorisatie, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_ToegangLevsautorisatie_ToegangLevsautorisatie_TsReg_tr ON AutAut.His_ToegangLevsautorisatie;
CREATE CONSTRAINT TRIGGER uc_His_ToegangLevsautorisatie_ToegangLevsautorisatie_TsReg_tr AFTER INSERT OR UPDATE ON AutAut.His_ToegangLevsautorisatie DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE AutAut.uc_His_ToegangLevsautorisatie_ToegangLevsautorisatie_TsReg();

CREATE OR REPLACE FUNCTION AutAut.uc_Levsautorisatie_Naam() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.Naam IS NULL THEN
            SELECT COUNT(*)
            FROM AutAut.Levsautorisatie
            WHERE
               ID <> NEW.ID
               AND Naam IS NOT DISTINCT FROM NEW.Naam
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Naam;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_Levsautorisatie_Naam_tr ON AutAut.Levsautorisatie;
CREATE CONSTRAINT TRIGGER uc_Levsautorisatie_Naam_tr AFTER INSERT OR UPDATE ON AutAut.Levsautorisatie DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE AutAut.uc_Levsautorisatie_Naam();

CREATE INDEX ix_PartijFiatuitz_Partij ON AutAut.PartijFiatuitz (Partij); -- Index door foreign key
CREATE INDEX ix_PartijFiatuitz_PartijBijhvoorstel ON AutAut.PartijFiatuitz (PartijBijhvoorstel); -- Index door foreign key
CREATE INDEX ix_ToegangBijhautorisatie_Geautoriseerde ON AutAut.ToegangBijhautorisatie (Geautoriseerde); -- Index door foreign key
CREATE INDEX ix_ToegangBijhautorisatie_Ondertekenaar ON AutAut.ToegangBijhautorisatie (Ondertekenaar); -- Index door foreign key
CREATE INDEX ix_ToegangBijhautorisatie_Transporteur ON AutAut.ToegangBijhautorisatie (Transporteur); -- Index door foreign key
CREATE INDEX ix_ToegangLevsautorisatie_Geautoriseerde ON AutAut.ToegangLevsautorisatie (Geautoriseerde); -- Index door foreign key
CREATE INDEX ix_ToegangLevsautorisatie_Levsautorisatie ON AutAut.ToegangLevsautorisatie (Levsautorisatie); -- Index door foreign key
CREATE INDEX ix_ToegangLevsautorisatie_Ondertekenaar ON AutAut.ToegangLevsautorisatie (Ondertekenaar); -- Index door foreign key
CREATE INDEX ix_ToegangLevsautorisatie_Transporteur ON AutAut.ToegangLevsautorisatie (Transporteur); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Regelsituatie_ActieInh ON BRM.His_Regelsituatie (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Regelsituatie_ActieVerval ON BRM.His_Regelsituatie (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Regelsituatie_ActieVervalTbvLevMuts ON BRM.His_Regelsituatie (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_Regelsituatie_Regelsituatie_TsReg ON BRM.His_Regelsituatie (Regelsituatie, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION BRM.uc_His_Regelsituatie_Regelsituatie_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM BRM.His_Regelsituatie
            WHERE
               ID <> NEW.ID
               AND Regelsituatie = NEW.Regelsituatie
               AND TsReg = NEW.TsReg
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Regelsituatie, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_Regelsituatie_Regelsituatie_TsReg_tr ON BRM.His_Regelsituatie;
CREATE CONSTRAINT TRIGGER uc_His_Regelsituatie_Regelsituatie_TsReg_tr AFTER INSERT OR UPDATE ON BRM.His_Regelsituatie DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE BRM.uc_His_Regelsituatie_Regelsituatie_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_ConvRNIDeelnemer_Partij ON Conv.ConvRNIDeelnemer (Partij); -- Index door foreign key
CREATE OR REPLACE FUNCTION Conv.uc_ConvAangifteAdresh_Aang_RdnWijzVerblijf() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.Aang IS NULL AND NEW.RdnWijzVerblijf IS NULL THEN
            SELECT COUNT(*)
            FROM Conv.ConvAangifteAdresh
            WHERE
               ID <> NEW.ID
               AND Aang IS NOT DISTINCT FROM NEW.Aang
               AND RdnWijzVerblijf IS NOT DISTINCT FROM NEW.RdnWijzVerblijf
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Aang, NEW.RdnWijzVerblijf;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_ConvAangifteAdresh_Aang_RdnWijzVerblijf_tr ON Conv.ConvAangifteAdresh;
CREATE CONSTRAINT TRIGGER uc_ConvAangifteAdresh_Aang_RdnWijzVerblijf_tr AFTER INSERT OR UPDATE ON Conv.ConvAangifteAdresh DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Conv.uc_ConvAangifteAdresh_Aang_RdnWijzVerblijf();

CREATE OR REPLACE FUNCTION Conv.uc_ConvAdellijkeTitelPredikaat_AdellijkeTitel_Predicaat_Geslach() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.AdellijkeTitel IS NULL AND NEW.Predicaat IS NULL THEN
            SELECT COUNT(*)
            FROM Conv.ConvAdellijkeTitelPredikaat
            WHERE
               ID <> NEW.ID
               AND Geslachtsaand = NEW.Geslachtsaand
               AND AdellijkeTitel IS NOT DISTINCT FROM NEW.AdellijkeTitel
               AND Predicaat IS NOT DISTINCT FROM NEW.Predicaat
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Geslachtsaand, NEW.AdellijkeTitel, NEW.Predicaat;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_ConvAdellijkeTitelPredikaat_AdellijkeTitel_Predicaat_Gesl_tr ON Conv.ConvAdellijkeTitelPredikaat;
CREATE CONSTRAINT TRIGGER uc_ConvAdellijkeTitelPredikaat_AdellijkeTitel_Predicaat_Gesl_tr AFTER INSERT OR UPDATE ON Conv.ConvAdellijkeTitelPredikaat DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Conv.uc_ConvAdellijkeTitelPredikaat_AdellijkeTitel_Predicaat_Geslach();

-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Partij_ActieInh ON Kern.His_Partij (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Partij_ActieVerval ON Kern.His_Partij (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Partij_ActieVervalTbvLevMuts ON Kern.His_Partij (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_Partij_Partij_TsReg ON Kern.His_Partij (Partij, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_Partij_Partij_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM Kern.His_Partij
            WHERE
               ID <> NEW.ID
               AND Partij = NEW.Partij
               AND TsReg = NEW.TsReg
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Partij, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_Partij_Partij_TsReg_tr ON Kern.His_Partij;
CREATE CONSTRAINT TRIGGER uc_His_Partij_Partij_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_Partij DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_Partij_Partij_TsReg();

-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PartijBijhouding_ActieInh ON Kern.His_PartijBijhouding (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PartijBijhouding_ActieVerval ON Kern.His_PartijBijhouding (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PartijBijhouding_ActieVervalTbvLevMuts ON Kern.His_PartijBijhouding (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PartijBijhouding_Partij_TsReg ON Kern.His_PartijBijhouding (Partij, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PartijBijhouding_Partij_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM Kern.His_PartijBijhouding
            WHERE
               ID <> NEW.ID
               AND Partij = NEW.Partij
               AND TsReg = NEW.TsReg
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Partij, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PartijBijhouding_Partij_TsReg_tr ON Kern.His_PartijBijhouding;
CREATE CONSTRAINT TRIGGER uc_His_PartijBijhouding_Partij_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_PartijBijhouding DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PartijBijhouding_Partij_TsReg();

-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PartijRol_ActieInh ON Kern.His_PartijRol (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PartijRol_ActieVerval ON Kern.His_PartijRol (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PartijRol_ActieVervalTbvLevMuts ON Kern.His_PartijRol (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PartijRol_PartijRol_TsReg ON Kern.His_PartijRol (PartijRol, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PartijRol_PartijRol_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM Kern.His_PartijRol
            WHERE
               ID <> NEW.ID
               AND PartijRol = NEW.PartijRol
               AND TsReg = NEW.TsReg
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.PartijRol, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PartijRol_PartijRol_TsReg_tr ON Kern.His_PartijRol;
CREATE CONSTRAINT TRIGGER uc_His_PartijRol_PartijRol_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_PartijRol DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PartijRol_PartijRol_TsReg();

CREATE INDEX ix_Partij_OIN ON Kern.Partij (OIN); -- Index door expliciete index in model
CREATE OR REPLACE FUNCTION Kern.uc_Partij_Naam() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.Naam IS NULL THEN
            SELECT COUNT(*)
            FROM Kern.Partij
            WHERE
               ID <> NEW.ID
               AND Naam IS NOT DISTINCT FROM NEW.Naam
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Naam;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_Partij_Naam_tr ON Kern.Partij;
CREATE CONSTRAINT TRIGGER uc_Partij_Naam_tr AFTER INSERT OR UPDATE ON Kern.Partij DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_Partij_Naam();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_PersAfnemerindicatie_Pers ON AutAut.PersAfnemerindicatie (Pers); -- Index door foreign key
CREATE INDEX ix_PersAfnemerindicatie_Afnemer ON AutAut.PersAfnemerindicatie (Afnemer); -- Index door foreign key
CREATE INDEX ix_PersAfnemerindicatie_Levsautorisatie ON AutAut.PersAfnemerindicatie (Levsautorisatie); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_Stapel_Pers ON IST.Stapel (Pers); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_StapelRelatie_Stapel ON IST.StapelRelatie (Stapel); -- Index door foreign key
CREATE INDEX ix_StapelRelatie_Relatie ON IST.StapelRelatie (Relatie); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_StapelVoorkomen_Stapel ON IST.StapelVoorkomen (Stapel); -- Index door foreign key
CREATE INDEX ix_StapelVoorkomen_AdmHnd ON IST.StapelVoorkomen (AdmHnd); -- Index door foreign key
CREATE INDEX ix_StapelVoorkomen_Partij ON IST.StapelVoorkomen (Partij); -- Index door foreign key
CREATE INDEX ix_Actie_AdmHnd ON Kern.Actie (AdmHnd); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_ActieBron_Actie ON Kern.ActieBron (Actie); -- Index door foreign key
CREATE INDEX ix_ActieBron_Doc ON Kern.ActieBron (Doc); -- Index door foreign key
CREATE OR REPLACE FUNCTION Kern.uc_ActieBron_Actie_Doc_Rechtsgrond_Rechtsgrondoms() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.Doc IS NULL AND NEW.Rechtsgrond IS NULL AND NEW.Rechtsgrondoms IS NULL THEN
            SELECT COUNT(*)
            FROM Kern.ActieBron
            WHERE
               ID <> NEW.ID
               AND Actie = NEW.Actie
               AND Doc IS NOT DISTINCT FROM NEW.Doc
               AND Rechtsgrond IS NOT DISTINCT FROM NEW.Rechtsgrond
               AND Rechtsgrondoms IS NOT DISTINCT FROM NEW.Rechtsgrondoms
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Actie, NEW.Doc, NEW.Rechtsgrond, NEW.Rechtsgrondoms;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_ActieBron_Actie_Doc_Rechtsgrond_Rechtsgrondoms_tr ON Kern.ActieBron;
CREATE CONSTRAINT TRIGGER uc_ActieBron_Actie_Doc_Rechtsgrond_Rechtsgrondoms_tr AFTER INSERT OR UPDATE ON Kern.ActieBron DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_ActieBron_Actie_Doc_Rechtsgrond_Rechtsgrondoms();

CREATE INDEX ix_AdmHnd_TsLev ON Kern.AdmHnd (TsLev); -- Index door expliciete index in model
CREATE INDEX ix_AdmHnd_TsReg ON Kern.AdmHnd (TsReg); -- Index door expliciete index in model
CREATE INDEX ix_AdmHndGedeblokkeerdeMelding_AdmHnd ON Kern.AdmHndGedeblokkeerdeMelding (AdmHnd); -- Index door foreign key
CREATE INDEX ix_AdmHndGedeblokkeerdeMelding_GedeblokkeerdeMelding ON Kern.AdmHndGedeblokkeerdeMelding (GedeblokkeerdeMelding); -- Index door foreign key
CREATE INDEX ix_Betr_Relatie ON Kern.Betr (Relatie); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_Betr_Pers ON Kern.Betr (Pers); -- Index door foreign key
CREATE INDEX ix_Betr_Pers ON Kern.Betr (Pers); -- Index door expliciete index in model
CREATE INDEX ix_GegevenInOnderzoek_Onderzoek ON Kern.GegevenInOnderzoek (Onderzoek); -- Index door foreign key
CREATE INDEX ix_GegevenInTerugmelding_Terugmelding ON Kern.GegevenInTerugmelding (Terugmelding); -- Index door foreign key
CREATE INDEX ix_Onderzoek_AdmHnd ON Kern.Onderzoek (AdmHnd); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_PartijOnderzoek_Onderzoek ON Kern.PartijOnderzoek (Onderzoek); -- Index door foreign key
CREATE INDEX ix_Pers_AdmHnd ON Kern.Pers (AdmHnd); -- Index door foreign key
CREATE INDEX ix_Pers_ANr ON Kern.Pers (ANr); -- Index door expliciete index in model
CREATE INDEX ix_Pers_BSN ON Kern.Pers (BSN); -- Index door expliciete index in model
CREATE INDEX ix_Pers_GeslnaamstamU ON Kern.Pers (UPPER(Geslnaamstam)); -- Index door expliciete index in model
CREATE INDEX ix_PersAdres_Pers ON Kern.PersAdres (Pers); -- Index door foreign key
CREATE INDEX ix_PersAdres_IdentcodeNraand ON Kern.PersAdres (IdentcodeNraand); -- Index door expliciete index in model
CREATE INDEX ix_PersAdres_Gem_NOR_Huisnr_Huisletter_Huisnrtoevoeging_SrtU ON Kern.PersAdres (Gem, UPPER(NOR), Huisnr, UPPER(Huisletter), UPPER(Huisnrtoevoeging), Srt); -- Index door expliciete index in model
CREATE INDEX ix_PersAdres_Postcode_Huisnr_Huisletter_Huisnrtoevoeging_SrtU ON Kern.PersAdres (UPPER(Postcode), Huisnr, UPPER(Huisletter), UPPER(Huisnrtoevoeging), Srt); -- Index door expliciete index in model
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_PersGeslnaamcomp_Pers ON Kern.PersGeslnaamcomp (Pers); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_PersIndicatie_Pers ON Kern.PersIndicatie (Pers); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_PersNation_Pers ON Kern.PersNation (Pers); -- Index door foreign key
CREATE INDEX ix_PersOnderzoek_Pers ON Kern.PersOnderzoek (Pers); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_PersOnderzoek_Onderzoek ON Kern.PersOnderzoek (Onderzoek); -- Index door foreign key
CREATE INDEX ix_PersReisdoc_Pers ON Kern.PersReisdoc (Pers); -- Index door foreign key
CREATE INDEX ix_PersVerificatie_Geverifieerde ON Kern.PersVerificatie (Geverifieerde); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_PersVerstrbeperking_Pers ON Kern.PersVerstrbeperking (Pers); -- Index door foreign key
CREATE OR REPLACE FUNCTION Kern.uc_PersVerstrbeperking_Pers_Partij_OmsDerde_GemVerordening() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.Partij IS NULL AND NEW.OmsDerde IS NULL AND NEW.GemVerordening IS NULL THEN
            SELECT COUNT(*)
            FROM Kern.PersVerstrbeperking
            WHERE
               ID <> NEW.ID
               AND Pers = NEW.Pers
               AND Partij IS NOT DISTINCT FROM NEW.Partij
               AND OmsDerde IS NOT DISTINCT FROM NEW.OmsDerde
               AND GemVerordening IS NOT DISTINCT FROM NEW.GemVerordening
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Pers, NEW.Partij, NEW.OmsDerde, NEW.GemVerordening;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_PersVerstrbeperking_Pers_Partij_OmsDerde_GemVerordening_tr ON Kern.PersVerstrbeperking;
CREATE CONSTRAINT TRIGGER uc_PersVerstrbeperking_Pers_Partij_OmsDerde_GemVerordening_tr AFTER INSERT OR UPDATE ON Kern.PersVerstrbeperking DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_PersVerstrbeperking_Pers_Partij_OmsDerde_GemVerordening();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_PersVoornaam_Pers ON Kern.PersVoornaam (Pers); -- Index door foreign key
CREATE INDEX ix_PersCache_Pers ON Kern.PersCache (Pers); -- Index door foreign key
CREATE INDEX ix_Regelverantwoording_Actie ON Kern.Regelverantwoording (Actie); -- Index door foreign key
CREATE INDEX ix_Terugmelding_Pers ON Kern.Terugmelding (Pers); -- Index door foreign key
CREATE INDEX ix_Terugmelding_Onderzoek ON Kern.Terugmelding (Onderzoek); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_LO3AandOuder_Ouder ON VerConv.LO3AandOuder (Ouder); -- Index door foreign key
CREATE INDEX ix_LO3Ber_Pers ON VerConv.LO3Ber (Pers); -- Index door foreign key
CREATE INDEX ix_LO3Ber_ANr ON VerConv.LO3Ber (ANr); -- Index door expliciete index in model
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_LO3Melding_LO3Voorkomen ON VerConv.LO3Melding (LO3Voorkomen); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_LO3Voorkomen_LO3Ber ON VerConv.LO3Voorkomen (LO3Ber); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_LO3Voorkomen_Actie ON VerConv.LO3Voorkomen (Actie); -- Index door foreign key
CREATE OR REPLACE FUNCTION VerConv.uc_LO3Voorkomen_LO3Ber_LO3Categorie_LO3Stapelvolgnr_LO3Voorkome() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.LO3Stapelvolgnr IS NULL AND NEW.LO3Voorkomenvolgnr IS NULL THEN
            SELECT COUNT(*)
            FROM VerConv.LO3Voorkomen
            WHERE
               ID <> NEW.ID
               AND LO3Ber = NEW.LO3Ber
               AND LO3Categorie = NEW.LO3Categorie
               AND LO3Stapelvolgnr IS NOT DISTINCT FROM NEW.LO3Stapelvolgnr
               AND LO3Voorkomenvolgnr IS NOT DISTINCT FROM NEW.LO3Voorkomenvolgnr
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.LO3Ber, NEW.LO3Categorie, NEW.LO3Stapelvolgnr, NEW.LO3Voorkomenvolgnr;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_LO3Voorkomen_LO3Ber_LO3Categorie_LO3Stapelvolgnr_LO3Voork_tr ON VerConv.LO3Voorkomen;
CREATE CONSTRAINT TRIGGER uc_LO3Voorkomen_LO3Ber_LO3Categorie_LO3Stapelvolgnr_LO3Voork_tr AFTER INSERT OR UPDATE ON VerConv.LO3Voorkomen DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE VerConv.uc_LO3Voorkomen_LO3Ber_LO3Categorie_LO3Stapelvolgnr_LO3Voorkome();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersAfnemerindicatie_PersAfnemerindicatie ON AutAut.His_PersAfnemerindicatie (PersAfnemerindicatie); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersAfnemerindicatie_DienstInh ON AutAut.His_PersAfnemerindicatie (DienstInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersAfnemerindicatie_DienstVerval ON AutAut.His_PersAfnemerindicatie (DienstVerval); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_Betr_Betr ON Kern.His_Betr (Betr); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Betr_ActieInh ON Kern.His_Betr (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Betr_ActieVerval ON Kern.His_Betr (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Betr_ActieVervalTbvLevMuts ON Kern.His_Betr (ActieVervalTbvLevMuts); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_Doc_Doc ON Kern.His_Doc (Doc); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Doc_ActieInh ON Kern.His_Doc (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Doc_ActieVerval ON Kern.His_Doc (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Doc_ActieVervalTbvLevMuts ON Kern.His_Doc (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_Doc_Doc_TsReg ON Kern.His_Doc (Doc, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_Doc_Doc_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM Kern.His_Doc
            WHERE
               ID <> NEW.ID
               AND Doc = NEW.Doc
               AND TsReg = NEW.TsReg
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Doc, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_Doc_Doc_TsReg_tr ON Kern.His_Doc;
CREATE CONSTRAINT TRIGGER uc_His_Doc_Doc_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_Doc DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_Doc_Doc_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_Onderzoek_Onderzoek ON Kern.His_Onderzoek (Onderzoek); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Onderzoek_ActieInh ON Kern.His_Onderzoek (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Onderzoek_ActieVerval ON Kern.His_Onderzoek (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Onderzoek_ActieVervalTbvLevMuts ON Kern.His_Onderzoek (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_Onderzoek_Onderzoek_TsReg ON Kern.His_Onderzoek (Onderzoek, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_Onderzoek_Onderzoek_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM Kern.His_Onderzoek
            WHERE
               ID <> NEW.ID
               AND Onderzoek = NEW.Onderzoek
               AND TsReg = NEW.TsReg
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Onderzoek, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_Onderzoek_Onderzoek_TsReg_tr ON Kern.His_Onderzoek;
CREATE CONSTRAINT TRIGGER uc_His_Onderzoek_Onderzoek_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_Onderzoek DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_Onderzoek_Onderzoek_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_OnderzoekAfgeleidAdminis_Onderzoek ON Kern.His_OnderzoekAfgeleidAdminis (Onderzoek); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_OnderzoekAfgeleidAdminis_ActieInh ON Kern.His_OnderzoekAfgeleidAdminis (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_OnderzoekAfgeleidAdminis_ActieVerval ON Kern.His_OnderzoekAfgeleidAdminis (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_OnderzoekAfgeleidAdminis_ActieVervalTbvLevMuts ON Kern.His_OnderzoekAfgeleidAdminis (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX ix_His_OnderzoekAfgeleidAdminis_AdmHnd ON Kern.His_OnderzoekAfgeleidAdminis (AdmHnd); -- Index door foreign key
CREATE INDEX uc_His_OnderzoekAfgeleidAdminis_Onderzoek_TsReg ON Kern.His_OnderzoekAfgeleidAdminis (Onderzoek, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_OnderzoekAfgeleidAdminis_Onderzoek_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM Kern.His_OnderzoekAfgeleidAdminis
            WHERE
               ID <> NEW.ID
               AND Onderzoek = NEW.Onderzoek
               AND TsReg = NEW.TsReg
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Onderzoek, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_OnderzoekAfgeleidAdminis_Onderzoek_TsReg_tr ON Kern.His_OnderzoekAfgeleidAdminis;
CREATE CONSTRAINT TRIGGER uc_His_OnderzoekAfgeleidAdminis_Onderzoek_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_OnderzoekAfgeleidAdminis DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_OnderzoekAfgeleidAdminis_Onderzoek_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_OuderOuderlijkGezag_Betr ON Kern.His_OuderOuderlijkGezag (Betr); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_OuderOuderlijkGezag_ActieInh ON Kern.His_OuderOuderlijkGezag (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_OuderOuderlijkGezag_ActieVerval ON Kern.His_OuderOuderlijkGezag (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_OuderOuderlijkGezag_ActieAanpGel ON Kern.His_OuderOuderlijkGezag (ActieAanpGel); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_OuderOuderlijkGezag_ActieVervalTbvLevMuts ON Kern.His_OuderOuderlijkGezag (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_OuderOuderlijkGezag_Betr_TsReg_DatAanvGel ON Kern.His_OuderOuderlijkGezag (Betr, TsReg, DatAanvGel); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_OuderOuderlijkGezag_Betr_TsReg_DatAanvGel() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM Kern.His_OuderOuderlijkGezag
            WHERE
               ID <> NEW.ID
               AND Betr = NEW.Betr
               AND TsReg = NEW.TsReg
               AND DatAanvGel = NEW.DatAanvGel
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Betr, NEW.TsReg, NEW.DatAanvGel, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_OuderOuderlijkGezag_Betr_TsReg_DatAanvGel_tr ON Kern.His_OuderOuderlijkGezag;
CREATE CONSTRAINT TRIGGER uc_His_OuderOuderlijkGezag_Betr_TsReg_DatAanvGel_tr AFTER INSERT OR UPDATE ON Kern.His_OuderOuderlijkGezag DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_OuderOuderlijkGezag_Betr_TsReg_DatAanvGel();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_OuderOuderschap_Betr ON Kern.His_OuderOuderschap (Betr); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_OuderOuderschap_ActieInh ON Kern.His_OuderOuderschap (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_OuderOuderschap_ActieVerval ON Kern.His_OuderOuderschap (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_OuderOuderschap_ActieAanpGel ON Kern.His_OuderOuderschap (ActieAanpGel); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_OuderOuderschap_ActieVervalTbvLevMuts ON Kern.His_OuderOuderschap (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_OuderOuderschap_Betr_TsReg_DatAanvGel ON Kern.His_OuderOuderschap (Betr, TsReg, DatAanvGel); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_OuderOuderschap_Betr_TsReg_DatAanvGel() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM Kern.His_OuderOuderschap
            WHERE
               ID <> NEW.ID
               AND Betr = NEW.Betr
               AND TsReg = NEW.TsReg
               AND DatAanvGel = NEW.DatAanvGel
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Betr, NEW.TsReg, NEW.DatAanvGel, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_OuderOuderschap_Betr_TsReg_DatAanvGel_tr ON Kern.His_OuderOuderschap;
CREATE CONSTRAINT TRIGGER uc_His_OuderOuderschap_Betr_TsReg_DatAanvGel_tr AFTER INSERT OR UPDATE ON Kern.His_OuderOuderschap DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_OuderOuderschap_Betr_TsReg_DatAanvGel();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PartijOnderzoek_PartijOnderzoek ON Kern.His_PartijOnderzoek (PartijOnderzoek); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PartijOnderzoek_ActieInh ON Kern.His_PartijOnderzoek (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PartijOnderzoek_ActieVerval ON Kern.His_PartijOnderzoek (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PartijOnderzoek_ActieVervalTbvLevMuts ON Kern.His_PartijOnderzoek (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PartijOnderzoek_PartijOnderzoek_TsReg ON Kern.His_PartijOnderzoek (PartijOnderzoek, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PartijOnderzoek_PartijOnderzoek_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM Kern.His_PartijOnderzoek
            WHERE
               ID <> NEW.ID
               AND PartijOnderzoek = NEW.PartijOnderzoek
               AND TsReg = NEW.TsReg
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.PartijOnderzoek, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PartijOnderzoek_PartijOnderzoek_TsReg_tr ON Kern.His_PartijOnderzoek;
CREATE CONSTRAINT TRIGGER uc_His_PartijOnderzoek_PartijOnderzoek_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_PartijOnderzoek DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PartijOnderzoek_PartijOnderzoek_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersAfgeleidAdministrati_Pers ON Kern.His_PersAfgeleidAdministrati (Pers); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersAfgeleidAdministrati_ActieInh ON Kern.His_PersAfgeleidAdministrati (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersAfgeleidAdministrati_ActieVerval ON Kern.His_PersAfgeleidAdministrati (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersAfgeleidAdministrati_ActieVervalTbvLevMuts ON Kern.His_PersAfgeleidAdministrati (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX ix_His_PersAfgeleidAdministrati_AdmHnd ON Kern.His_PersAfgeleidAdministrati (AdmHnd); -- Index door foreign key
CREATE INDEX uc_His_PersAfgeleidAdministrati_Pers_TsReg ON Kern.His_PersAfgeleidAdministrati (Pers, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersAfgeleidAdministrati_Pers_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM Kern.His_PersAfgeleidAdministrati
            WHERE
               ID <> NEW.ID
               AND Pers = NEW.Pers
               AND TsReg = NEW.TsReg
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Pers, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersAfgeleidAdministrati_Pers_TsReg_tr ON Kern.His_PersAfgeleidAdministrati;
CREATE CONSTRAINT TRIGGER uc_His_PersAfgeleidAdministrati_Pers_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_PersAfgeleidAdministrati DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersAfgeleidAdministrati_Pers_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersBijhouding_Pers ON Kern.His_PersBijhouding (Pers); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersBijhouding_ActieInh ON Kern.His_PersBijhouding (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersBijhouding_ActieVerval ON Kern.His_PersBijhouding (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersBijhouding_ActieAanpGel ON Kern.His_PersBijhouding (ActieAanpGel); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersBijhouding_ActieVervalTbvLevMuts ON Kern.His_PersBijhouding (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersBijhouding_Pers_TsReg_DatAanvGel ON Kern.His_PersBijhouding (Pers, TsReg, DatAanvGel); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersBijhouding_Pers_TsReg_DatAanvGel() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM Kern.His_PersBijhouding
            WHERE
               ID <> NEW.ID
               AND Pers = NEW.Pers
               AND TsReg = NEW.TsReg
               AND DatAanvGel = NEW.DatAanvGel
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Pers, NEW.TsReg, NEW.DatAanvGel, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersBijhouding_Pers_TsReg_DatAanvGel_tr ON Kern.His_PersBijhouding;
CREATE CONSTRAINT TRIGGER uc_His_PersBijhouding_Pers_TsReg_DatAanvGel_tr AFTER INSERT OR UPDATE ON Kern.His_PersBijhouding DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersBijhouding_Pers_TsReg_DatAanvGel();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersDeelnEUVerkiezingen_Pers ON Kern.His_PersDeelnEUVerkiezingen (Pers); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersDeelnEUVerkiezingen_ActieInh ON Kern.His_PersDeelnEUVerkiezingen (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersDeelnEUVerkiezingen_ActieVerval ON Kern.His_PersDeelnEUVerkiezingen (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersDeelnEUVerkiezingen_ActieVervalTbvLevMuts ON Kern.His_PersDeelnEUVerkiezingen (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersDeelnEUVerkiezingen_Pers_TsReg ON Kern.His_PersDeelnEUVerkiezingen (Pers, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersDeelnEUVerkiezingen_Pers_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM Kern.His_PersDeelnEUVerkiezingen
            WHERE
               ID <> NEW.ID
               AND Pers = NEW.Pers
               AND TsReg = NEW.TsReg
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Pers, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersDeelnEUVerkiezingen_Pers_TsReg_tr ON Kern.His_PersDeelnEUVerkiezingen;
CREATE CONSTRAINT TRIGGER uc_His_PersDeelnEUVerkiezingen_Pers_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_PersDeelnEUVerkiezingen DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersDeelnEUVerkiezingen_Pers_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersGeboorte_Pers ON Kern.His_PersGeboorte (Pers); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersGeboorte_ActieInh ON Kern.His_PersGeboorte (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersGeboorte_ActieVerval ON Kern.His_PersGeboorte (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersGeboorte_ActieVervalTbvLevMuts ON Kern.His_PersGeboorte (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersGeboorte_Pers_TsReg ON Kern.His_PersGeboorte (Pers, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersGeboorte_Pers_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM Kern.His_PersGeboorte
            WHERE
               ID <> NEW.ID
               AND Pers = NEW.Pers
               AND TsReg = NEW.TsReg
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Pers, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersGeboorte_Pers_TsReg_tr ON Kern.His_PersGeboorte;
CREATE CONSTRAINT TRIGGER uc_His_PersGeboorte_Pers_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_PersGeboorte DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersGeboorte_Pers_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersGeslachtsaand_Pers ON Kern.His_PersGeslachtsaand (Pers); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersGeslachtsaand_ActieInh ON Kern.His_PersGeslachtsaand (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersGeslachtsaand_ActieVerval ON Kern.His_PersGeslachtsaand (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersGeslachtsaand_ActieAanpGel ON Kern.His_PersGeslachtsaand (ActieAanpGel); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersGeslachtsaand_ActieVervalTbvLevMuts ON Kern.His_PersGeslachtsaand (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersGeslachtsaand_Pers_TsReg_DatAanvGel ON Kern.His_PersGeslachtsaand (Pers, TsReg, DatAanvGel); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersGeslachtsaand_Pers_TsReg_DatAanvGel() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM Kern.His_PersGeslachtsaand
            WHERE
               ID <> NEW.ID
               AND Pers = NEW.Pers
               AND TsReg = NEW.TsReg
               AND DatAanvGel = NEW.DatAanvGel
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Pers, NEW.TsReg, NEW.DatAanvGel, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersGeslachtsaand_Pers_TsReg_DatAanvGel_tr ON Kern.His_PersGeslachtsaand;
CREATE CONSTRAINT TRIGGER uc_His_PersGeslachtsaand_Pers_TsReg_DatAanvGel_tr AFTER INSERT OR UPDATE ON Kern.His_PersGeslachtsaand DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersGeslachtsaand_Pers_TsReg_DatAanvGel();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersIDs_Pers ON Kern.His_PersIDs (Pers); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersIDs_ActieInh ON Kern.His_PersIDs (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersIDs_ActieVerval ON Kern.His_PersIDs (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersIDs_ActieAanpGel ON Kern.His_PersIDs (ActieAanpGel); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersIDs_ActieVervalTbvLevMuts ON Kern.His_PersIDs (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersIDs_Pers_TsReg_DatAanvGel ON Kern.His_PersIDs (Pers, TsReg, DatAanvGel); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE INDEX ix_His_PersIDs_ANr ON Kern.His_PersIDs (ANr); -- Index door expliciete index in model
CREATE INDEX ix_His_PersIDs_BSN ON Kern.His_PersIDs (BSN); -- Index door expliciete index in model
CREATE OR REPLACE FUNCTION Kern.uc_His_PersIDs_Pers_TsReg_DatAanvGel() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM Kern.His_PersIDs
            WHERE
               ID <> NEW.ID
               AND Pers = NEW.Pers
               AND TsReg = NEW.TsReg
               AND DatAanvGel = NEW.DatAanvGel
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Pers, NEW.TsReg, NEW.DatAanvGel, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersIDs_Pers_TsReg_DatAanvGel_tr ON Kern.His_PersIDs;
CREATE CONSTRAINT TRIGGER uc_His_PersIDs_Pers_TsReg_DatAanvGel_tr AFTER INSERT OR UPDATE ON Kern.His_PersIDs DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersIDs_Pers_TsReg_DatAanvGel();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersInschr_Pers ON Kern.His_PersInschr (Pers); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersInschr_ActieInh ON Kern.His_PersInschr (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersInschr_ActieVerval ON Kern.His_PersInschr (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersInschr_ActieVervalTbvLevMuts ON Kern.His_PersInschr (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersInschr_Pers_TsReg ON Kern.His_PersInschr (Pers, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersInschr_Pers_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM Kern.His_PersInschr
            WHERE
               ID <> NEW.ID
               AND Pers = NEW.Pers
               AND TsReg = NEW.TsReg
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Pers, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersInschr_Pers_TsReg_tr ON Kern.His_PersInschr;
CREATE CONSTRAINT TRIGGER uc_His_PersInschr_Pers_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_PersInschr DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersInschr_Pers_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersMigratie_Pers ON Kern.His_PersMigratie (Pers); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersMigratie_ActieInh ON Kern.His_PersMigratie (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersMigratie_ActieVerval ON Kern.His_PersMigratie (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersMigratie_ActieAanpGel ON Kern.His_PersMigratie (ActieAanpGel); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersMigratie_ActieVervalTbvLevMuts ON Kern.His_PersMigratie (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersMigratie_Pers_TsReg_DatAanvGel ON Kern.His_PersMigratie (Pers, TsReg, DatAanvGel); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersMigratie_Pers_TsReg_DatAanvGel() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM Kern.His_PersMigratie
            WHERE
               ID <> NEW.ID
               AND Pers = NEW.Pers
               AND TsReg = NEW.TsReg
               AND DatAanvGel = NEW.DatAanvGel
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Pers, NEW.TsReg, NEW.DatAanvGel, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersMigratie_Pers_TsReg_DatAanvGel_tr ON Kern.His_PersMigratie;
CREATE CONSTRAINT TRIGGER uc_His_PersMigratie_Pers_TsReg_DatAanvGel_tr AFTER INSERT OR UPDATE ON Kern.His_PersMigratie DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersMigratie_Pers_TsReg_DatAanvGel();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersNaamgebruik_Pers ON Kern.His_PersNaamgebruik (Pers); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersNaamgebruik_ActieInh ON Kern.His_PersNaamgebruik (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersNaamgebruik_ActieVerval ON Kern.His_PersNaamgebruik (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersNaamgebruik_ActieVervalTbvLevMuts ON Kern.His_PersNaamgebruik (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersNaamgebruik_Pers_TsReg ON Kern.His_PersNaamgebruik (Pers, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersNaamgebruik_Pers_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM Kern.His_PersNaamgebruik
            WHERE
               ID <> NEW.ID
               AND Pers = NEW.Pers
               AND TsReg = NEW.TsReg
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Pers, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersNaamgebruik_Pers_TsReg_tr ON Kern.His_PersNaamgebruik;
CREATE CONSTRAINT TRIGGER uc_His_PersNaamgebruik_Pers_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_PersNaamgebruik DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersNaamgebruik_Pers_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersNrverwijzing_Pers ON Kern.His_PersNrverwijzing (Pers); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersNrverwijzing_ActieInh ON Kern.His_PersNrverwijzing (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersNrverwijzing_ActieVerval ON Kern.His_PersNrverwijzing (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersNrverwijzing_ActieAanpGel ON Kern.His_PersNrverwijzing (ActieAanpGel); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersNrverwijzing_ActieVervalTbvLevMuts ON Kern.His_PersNrverwijzing (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersNrverwijzing_Pers_TsReg_DatAanvGel ON Kern.His_PersNrverwijzing (Pers, TsReg, DatAanvGel); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersNrverwijzing_Pers_TsReg_DatAanvGel() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM Kern.His_PersNrverwijzing
            WHERE
               ID <> NEW.ID
               AND Pers = NEW.Pers
               AND TsReg = NEW.TsReg
               AND DatAanvGel = NEW.DatAanvGel
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Pers, NEW.TsReg, NEW.DatAanvGel, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersNrverwijzing_Pers_TsReg_DatAanvGel_tr ON Kern.His_PersNrverwijzing;
CREATE CONSTRAINT TRIGGER uc_His_PersNrverwijzing_Pers_TsReg_DatAanvGel_tr AFTER INSERT OR UPDATE ON Kern.His_PersNrverwijzing DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersNrverwijzing_Pers_TsReg_DatAanvGel();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersOverlijden_Pers ON Kern.His_PersOverlijden (Pers); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersOverlijden_ActieInh ON Kern.His_PersOverlijden (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersOverlijden_ActieVerval ON Kern.His_PersOverlijden (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersOverlijden_ActieVervalTbvLevMuts ON Kern.His_PersOverlijden (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersOverlijden_Pers_TsReg ON Kern.His_PersOverlijden (Pers, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersOverlijden_Pers_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM Kern.His_PersOverlijden
            WHERE
               ID <> NEW.ID
               AND Pers = NEW.Pers
               AND TsReg = NEW.TsReg
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Pers, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersOverlijden_Pers_TsReg_tr ON Kern.His_PersOverlijden;
CREATE CONSTRAINT TRIGGER uc_His_PersOverlijden_Pers_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_PersOverlijden DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersOverlijden_Pers_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersPK_Pers ON Kern.His_PersPK (Pers); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersPK_ActieInh ON Kern.His_PersPK (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersPK_ActieVerval ON Kern.His_PersPK (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersPK_ActieVervalTbvLevMuts ON Kern.His_PersPK (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersPK_Pers_TsReg ON Kern.His_PersPK (Pers, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersPK_Pers_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM Kern.His_PersPK
            WHERE
               ID <> NEW.ID
               AND Pers = NEW.Pers
               AND TsReg = NEW.TsReg
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Pers, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersPK_Pers_TsReg_tr ON Kern.His_PersPK;
CREATE CONSTRAINT TRIGGER uc_His_PersPK_Pers_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_PersPK DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersPK_Pers_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersSamengesteldeNaam_Pers ON Kern.His_PersSamengesteldeNaam (Pers); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersSamengesteldeNaam_ActieInh ON Kern.His_PersSamengesteldeNaam (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersSamengesteldeNaam_ActieVerval ON Kern.His_PersSamengesteldeNaam (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersSamengesteldeNaam_ActieAanpGel ON Kern.His_PersSamengesteldeNaam (ActieAanpGel); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersSamengesteldeNaam_ActieVervalTbvLevMuts ON Kern.His_PersSamengesteldeNaam (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersSamengesteldeNaam_Pers_TsReg_DatAanvGel ON Kern.His_PersSamengesteldeNaam (Pers, TsReg, DatAanvGel); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE INDEX ix_His_PersSamengesteldeNaam_GeslnaamstamU ON Kern.His_PersSamengesteldeNaam (UPPER(Geslnaamstam)); -- Index door expliciete index in model
CREATE OR REPLACE FUNCTION Kern.uc_His_PersSamengesteldeNaam_Pers_TsReg_DatAanvGel() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM Kern.His_PersSamengesteldeNaam
            WHERE
               ID <> NEW.ID
               AND Pers = NEW.Pers
               AND TsReg = NEW.TsReg
               AND DatAanvGel = NEW.DatAanvGel
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Pers, NEW.TsReg, NEW.DatAanvGel, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersSamengesteldeNaam_Pers_TsReg_DatAanvGel_tr ON Kern.His_PersSamengesteldeNaam;
CREATE CONSTRAINT TRIGGER uc_His_PersSamengesteldeNaam_Pers_TsReg_DatAanvGel_tr AFTER INSERT OR UPDATE ON Kern.His_PersSamengesteldeNaam DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersSamengesteldeNaam_Pers_TsReg_DatAanvGel();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersUitslKiesr_Pers ON Kern.His_PersUitslKiesr (Pers); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersUitslKiesr_ActieInh ON Kern.His_PersUitslKiesr (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersUitslKiesr_ActieVerval ON Kern.His_PersUitslKiesr (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersUitslKiesr_ActieVervalTbvLevMuts ON Kern.His_PersUitslKiesr (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersUitslKiesr_Pers_TsReg ON Kern.His_PersUitslKiesr (Pers, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersUitslKiesr_Pers_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM Kern.His_PersUitslKiesr
            WHERE
               ID <> NEW.ID
               AND Pers = NEW.Pers
               AND TsReg = NEW.TsReg
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Pers, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersUitslKiesr_Pers_TsReg_tr ON Kern.His_PersUitslKiesr;
CREATE CONSTRAINT TRIGGER uc_His_PersUitslKiesr_Pers_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_PersUitslKiesr DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersUitslKiesr_Pers_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersVerblijfsr_Pers ON Kern.His_PersVerblijfsr (Pers); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersVerblijfsr_ActieInh ON Kern.His_PersVerblijfsr (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersVerblijfsr_ActieVerval ON Kern.His_PersVerblijfsr (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersVerblijfsr_ActieVervalTbvLevMuts ON Kern.His_PersVerblijfsr (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersVerblijfsr_Pers_TsReg ON Kern.His_PersVerblijfsr (Pers, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersVerblijfsr_Pers_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM Kern.His_PersVerblijfsr
            WHERE
               ID <> NEW.ID
               AND Pers = NEW.Pers
               AND TsReg = NEW.TsReg
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Pers, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersVerblijfsr_Pers_TsReg_tr ON Kern.His_PersVerblijfsr;
CREATE CONSTRAINT TRIGGER uc_His_PersVerblijfsr_Pers_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_PersVerblijfsr DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersVerblijfsr_Pers_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersAdres_PersAdres ON Kern.His_PersAdres (PersAdres); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersAdres_ActieInh ON Kern.His_PersAdres (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersAdres_ActieVerval ON Kern.His_PersAdres (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersAdres_ActieAanpGel ON Kern.His_PersAdres (ActieAanpGel); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersAdres_ActieVervalTbvLevMuts ON Kern.His_PersAdres (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersAdres_PersAdres_TsReg_DatAanvGel ON Kern.His_PersAdres (PersAdres, TsReg, DatAanvGel); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersAdres_PersAdres_TsReg_DatAanvGel() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM Kern.His_PersAdres
            WHERE
               ID <> NEW.ID
               AND PersAdres = NEW.PersAdres
               AND TsReg = NEW.TsReg
               AND DatAanvGel = NEW.DatAanvGel
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.PersAdres, NEW.TsReg, NEW.DatAanvGel, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersAdres_PersAdres_TsReg_DatAanvGel_tr ON Kern.His_PersAdres;
CREATE CONSTRAINT TRIGGER uc_His_PersAdres_PersAdres_TsReg_DatAanvGel_tr AFTER INSERT OR UPDATE ON Kern.His_PersAdres DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersAdres_PersAdres_TsReg_DatAanvGel();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersGeslnaamcomp_PersGeslnaamcomp ON Kern.His_PersGeslnaamcomp (PersGeslnaamcomp); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersGeslnaamcomp_ActieInh ON Kern.His_PersGeslnaamcomp (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersGeslnaamcomp_ActieVerval ON Kern.His_PersGeslnaamcomp (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersGeslnaamcomp_ActieAanpGel ON Kern.His_PersGeslnaamcomp (ActieAanpGel); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersGeslnaamcomp_ActieVervalTbvLevMuts ON Kern.His_PersGeslnaamcomp (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersGeslnaamcomp_PersGeslnaamcomp_TsReg_DatAanvGel ON Kern.His_PersGeslnaamcomp (PersGeslnaamcomp, TsReg, DatAanvGel); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersGeslnaamcomp_PersGeslnaamcomp_TsReg_DatAanvGel() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM Kern.His_PersGeslnaamcomp
            WHERE
               ID <> NEW.ID
               AND PersGeslnaamcomp = NEW.PersGeslnaamcomp
               AND TsReg = NEW.TsReg
               AND DatAanvGel = NEW.DatAanvGel
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.PersGeslnaamcomp, NEW.TsReg, NEW.DatAanvGel, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersGeslnaamcomp_PersGeslnaamcomp_TsReg_DatAanvGel_tr ON Kern.His_PersGeslnaamcomp;
CREATE CONSTRAINT TRIGGER uc_His_PersGeslnaamcomp_PersGeslnaamcomp_TsReg_DatAanvGel_tr AFTER INSERT OR UPDATE ON Kern.His_PersGeslnaamcomp DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersGeslnaamcomp_PersGeslnaamcomp_TsReg_DatAanvGel();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersIndicatie_PersIndicatie ON Kern.His_PersIndicatie (PersIndicatie); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersIndicatie_ActieInh ON Kern.His_PersIndicatie (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersIndicatie_ActieVerval ON Kern.His_PersIndicatie (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersIndicatie_ActieAanpGel ON Kern.His_PersIndicatie (ActieAanpGel); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersIndicatie_ActieVervalTbvLevMuts ON Kern.His_PersIndicatie (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersIndicatie_PersIndicatie_TsReg_DatAanvGel ON Kern.His_PersIndicatie (PersIndicatie, TsReg, DatAanvGel); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersIndicatie_PersIndicatie_TsReg_DatAanvGel() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM Kern.His_PersIndicatie
            WHERE
               ID <> NEW.ID
               AND PersIndicatie = NEW.PersIndicatie
               AND TsReg = NEW.TsReg
               AND DatAanvGel = NEW.DatAanvGel
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.PersIndicatie, NEW.TsReg, NEW.DatAanvGel, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersIndicatie_PersIndicatie_TsReg_DatAanvGel_tr ON Kern.His_PersIndicatie;
CREATE CONSTRAINT TRIGGER uc_His_PersIndicatie_PersIndicatie_TsReg_DatAanvGel_tr AFTER INSERT OR UPDATE ON Kern.His_PersIndicatie DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersIndicatie_PersIndicatie_TsReg_DatAanvGel();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersNation_PersNation ON Kern.His_PersNation (PersNation); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersNation_ActieInh ON Kern.His_PersNation (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersNation_ActieVerval ON Kern.His_PersNation (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersNation_ActieAanpGel ON Kern.His_PersNation (ActieAanpGel); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersNation_ActieVervalTbvLevMuts ON Kern.His_PersNation (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersNation_PersNation_TsReg_DatAanvGel ON Kern.His_PersNation (PersNation, TsReg, DatAanvGel); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersNation_PersNation_TsReg_DatAanvGel() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM Kern.His_PersNation
            WHERE
               ID <> NEW.ID
               AND PersNation = NEW.PersNation
               AND TsReg = NEW.TsReg
               AND DatAanvGel = NEW.DatAanvGel
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.PersNation, NEW.TsReg, NEW.DatAanvGel, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersNation_PersNation_TsReg_DatAanvGel_tr ON Kern.His_PersNation;
CREATE CONSTRAINT TRIGGER uc_His_PersNation_PersNation_TsReg_DatAanvGel_tr AFTER INSERT OR UPDATE ON Kern.His_PersNation DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersNation_PersNation_TsReg_DatAanvGel();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersOnderzoek_PersOnderzoek ON Kern.His_PersOnderzoek (PersOnderzoek); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersOnderzoek_ActieInh ON Kern.His_PersOnderzoek (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersOnderzoek_ActieVerval ON Kern.His_PersOnderzoek (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersOnderzoek_ActieVervalTbvLevMuts ON Kern.His_PersOnderzoek (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersOnderzoek_PersOnderzoek_TsReg ON Kern.His_PersOnderzoek (PersOnderzoek, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersOnderzoek_PersOnderzoek_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM Kern.His_PersOnderzoek
            WHERE
               ID <> NEW.ID
               AND PersOnderzoek = NEW.PersOnderzoek
               AND TsReg = NEW.TsReg
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.PersOnderzoek, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersOnderzoek_PersOnderzoek_TsReg_tr ON Kern.His_PersOnderzoek;
CREATE CONSTRAINT TRIGGER uc_His_PersOnderzoek_PersOnderzoek_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_PersOnderzoek DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersOnderzoek_PersOnderzoek_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersReisdoc_PersReisdoc ON Kern.His_PersReisdoc (PersReisdoc); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersReisdoc_ActieInh ON Kern.His_PersReisdoc (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersReisdoc_ActieVerval ON Kern.His_PersReisdoc (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersReisdoc_ActieVervalTbvLevMuts ON Kern.His_PersReisdoc (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersReisdoc_PersReisdoc_TsReg ON Kern.His_PersReisdoc (PersReisdoc, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersReisdoc_PersReisdoc_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM Kern.His_PersReisdoc
            WHERE
               ID <> NEW.ID
               AND PersReisdoc = NEW.PersReisdoc
               AND TsReg = NEW.TsReg
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.PersReisdoc, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersReisdoc_PersReisdoc_TsReg_tr ON Kern.His_PersReisdoc;
CREATE CONSTRAINT TRIGGER uc_His_PersReisdoc_PersReisdoc_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_PersReisdoc DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersReisdoc_PersReisdoc_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersVerificatie_PersVerificatie ON Kern.His_PersVerificatie (PersVerificatie); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersVerificatie_ActieInh ON Kern.His_PersVerificatie (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersVerificatie_ActieVerval ON Kern.His_PersVerificatie (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersVerificatie_ActieVervalTbvLevMuts ON Kern.His_PersVerificatie (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersVerificatie_PersVerificatie_TsReg ON Kern.His_PersVerificatie (PersVerificatie, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersVerificatie_PersVerificatie_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM Kern.His_PersVerificatie
            WHERE
               ID <> NEW.ID
               AND PersVerificatie = NEW.PersVerificatie
               AND TsReg = NEW.TsReg
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.PersVerificatie, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersVerificatie_PersVerificatie_TsReg_tr ON Kern.His_PersVerificatie;
CREATE CONSTRAINT TRIGGER uc_His_PersVerificatie_PersVerificatie_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_PersVerificatie DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersVerificatie_PersVerificatie_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersVerstrbeperking_PersVerstrbeperking ON Kern.His_PersVerstrbeperking (PersVerstrbeperking); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersVerstrbeperking_ActieInh ON Kern.His_PersVerstrbeperking (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersVerstrbeperking_ActieVerval ON Kern.His_PersVerstrbeperking (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersVerstrbeperking_ActieVervalTbvLevMuts ON Kern.His_PersVerstrbeperking (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersVerstrbeperking_PersVerstrbeperking_TsReg ON Kern.His_PersVerstrbeperking (PersVerstrbeperking, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersVerstrbeperking_PersVerstrbeperking_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM Kern.His_PersVerstrbeperking
            WHERE
               ID <> NEW.ID
               AND PersVerstrbeperking = NEW.PersVerstrbeperking
               AND TsReg = NEW.TsReg
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.PersVerstrbeperking, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersVerstrbeperking_PersVerstrbeperking_TsReg_tr ON Kern.His_PersVerstrbeperking;
CREATE CONSTRAINT TRIGGER uc_His_PersVerstrbeperking_PersVerstrbeperking_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_PersVerstrbeperking DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersVerstrbeperking_PersVerstrbeperking_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_PersVoornaam_PersVoornaam ON Kern.His_PersVoornaam (PersVoornaam); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersVoornaam_ActieInh ON Kern.His_PersVoornaam (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersVoornaam_ActieVerval ON Kern.His_PersVoornaam (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersVoornaam_ActieAanpGel ON Kern.His_PersVoornaam (ActieAanpGel); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_PersVoornaam_ActieVervalTbvLevMuts ON Kern.His_PersVoornaam (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_PersVoornaam_PersVoornaam_TsReg_DatAanvGel ON Kern.His_PersVoornaam (PersVoornaam, TsReg, DatAanvGel); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_PersVoornaam_PersVoornaam_TsReg_DatAanvGel() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM Kern.His_PersVoornaam
            WHERE
               ID <> NEW.ID
               AND PersVoornaam = NEW.PersVoornaam
               AND TsReg = NEW.TsReg
               AND DatAanvGel = NEW.DatAanvGel
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.PersVoornaam, NEW.TsReg, NEW.DatAanvGel, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_PersVoornaam_PersVoornaam_TsReg_DatAanvGel_tr ON Kern.His_PersVoornaam;
CREATE CONSTRAINT TRIGGER uc_His_PersVoornaam_PersVoornaam_TsReg_DatAanvGel_tr AFTER INSERT OR UPDATE ON Kern.His_PersVoornaam DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_PersVoornaam_PersVoornaam_TsReg_DatAanvGel();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_Relatie_Relatie ON Kern.His_Relatie (Relatie); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Relatie_ActieInh ON Kern.His_Relatie (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Relatie_ActieVerval ON Kern.His_Relatie (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Relatie_ActieVervalTbvLevMuts ON Kern.His_Relatie (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_Relatie_Relatie_TsReg ON Kern.His_Relatie (Relatie, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_Relatie_Relatie_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM Kern.His_Relatie
            WHERE
               ID <> NEW.ID
               AND Relatie = NEW.Relatie
               AND TsReg = NEW.TsReg
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Relatie, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_Relatie_Relatie_TsReg_tr ON Kern.His_Relatie;
CREATE CONSTRAINT TRIGGER uc_His_Relatie_Relatie_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_Relatie DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_Relatie_Relatie_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_Terugmelding_Terugmelding ON Kern.His_Terugmelding (Terugmelding); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Terugmelding_ActieInh ON Kern.His_Terugmelding (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Terugmelding_ActieVerval ON Kern.His_Terugmelding (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_Terugmelding_ActieVervalTbvLevMuts ON Kern.His_Terugmelding (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX ix_His_Terugmelding_Onderzoek ON Kern.His_Terugmelding (Onderzoek); -- Index door foreign key
CREATE INDEX uc_His_Terugmelding_Terugmelding_TsReg ON Kern.His_Terugmelding (Terugmelding, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_Terugmelding_Terugmelding_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM Kern.His_Terugmelding
            WHERE
               ID <> NEW.ID
               AND Terugmelding = NEW.Terugmelding
               AND TsReg = NEW.TsReg
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Terugmelding, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_Terugmelding_Terugmelding_TsReg_tr ON Kern.His_Terugmelding;
CREATE CONSTRAINT TRIGGER uc_His_Terugmelding_Terugmelding_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_Terugmelding DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_Terugmelding_Terugmelding_TsReg();

-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_His_TerugmeldingContactpers_Terugmelding ON Kern.His_TerugmeldingContactpers (Terugmelding); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_TerugmeldingContactpers_ActieInh ON Kern.His_TerugmeldingContactpers (ActieInh); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_TerugmeldingContactpers_ActieVerval ON Kern.His_TerugmeldingContactpers (ActieVerval); -- Index door foreign key
-- Onderdrukte index door foreign key naar verantwoording vanuit HIS laag: CREATE INDEX ix_His_TerugmeldingContactpers_ActieVervalTbvLevMuts ON Kern.His_TerugmeldingContactpers (ActieVervalTbvLevMuts); -- Index door foreign key
CREATE INDEX uc_His_TerugmeldingContactpers_Terugmelding_TsReg ON Kern.His_TerugmeldingContactpers (Terugmelding, TsReg); -- UC omgezet naar index als gevolg van IndVoorkomenTbvLevMuts
CREATE OR REPLACE FUNCTION Kern.uc_His_TerugmeldingContactpers_Terugmelding_TsReg() RETURNS TRIGGER AS $BODY$
DECLARE aantal_rijen integer;
BEGIN
    aantal_rijen = 0;
    IF (TG_OP IN ('INSERT', 'UPDATE')) THEN
        IF NEW.IndVoorkomenTbvLevMuts IS NULL THEN
            SELECT COUNT(*)
            FROM Kern.His_TerugmeldingContactpers
            WHERE
               ID <> NEW.ID
               AND Terugmelding = NEW.Terugmelding
               AND TsReg = NEW.TsReg
               AND IndVoorkomenTbvLevMuts IS NOT DISTINCT FROM NEW.IndVoorkomenTbvLevMuts
            INTO aantal_rijen;
        END IF;
        IF aantal_rijen > 0 THEN
            RAISE EXCEPTION 'duplicate value violates unique constraint % in table %.% (%, %, %)', TG_NAME, TG_TABLE_SCHEMA, TG_TABLE_NAME, NEW.Terugmelding, NEW.TsReg, NEW.IndVoorkomenTbvLevMuts;
        ELSE
            RETURN NEW;
        END IF;
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS uc_His_TerugmeldingContactpers_Terugmelding_TsReg_tr ON Kern.His_TerugmeldingContactpers;
CREATE CONSTRAINT TRIGGER uc_His_TerugmeldingContactpers_Terugmelding_TsReg_tr AFTER INSERT OR UPDATE ON Kern.His_TerugmeldingContactpers DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE Kern.uc_His_TerugmeldingContactpers_Terugmelding_TsReg();


-- SQL inhoud van bestand: bmr/Ber/Bericht_BRP_indexen.sql
--------------------------------------------------------------------------------
-- PostgreSQL SQL-DDL bestand voor Basisregistratie Personen (BRP)            --
-- PostgreSQL - Bericht Indexen DDL                                           --
--------------------------------------------------------------------------------
--
-- Gegenereerd door BRP Meta Register Release 44.
-- Gegenereerd op: dinsdag 24 nov 2015 13:59:27
--
--------------------------------------------------------------------------------

CREATE INDEX ix_Ber_ZendendePartij ON Ber.Ber (ZendendePartij); -- Index door foreign key
CREATE INDEX ix_Ber_OntvangendePartij ON Ber.Ber (OntvangendePartij); -- Index door foreign key
CREATE INDEX ix_Ber_Levsautorisatie ON Ber.Ber (Levsautorisatie); -- Index door foreign key
CREATE INDEX ix_Ber_Dienst ON Ber.Ber (Dienst); -- Index door foreign key
CREATE INDEX ix_Ber_AdmHnd ON Ber.Ber (AdmHnd); -- Index door foreign key
CREATE INDEX ix_Ber_AntwoordOp ON Ber.Ber (AntwoordOp); -- Index door foreign key
CREATE INDEX ix_Ber_Referentienr ON Ber.Ber (Referentienr); -- Index door expliciete index in model
CREATE INDEX ix_Ber_TsOntv ON Ber.Ber (TsOntv); -- Index door expliciete index in model
CREATE INDEX ix_Ber_TsVerzending ON Ber.Ber (TsVerzending); -- Index door expliciete index in model
CREATE INDEX ix_Ber_ReferentienrU ON Ber.Ber (UPPER(Referentienr)); -- Index door expliciete index in model
CREATE INDEX ix_BerPers_Ber ON Ber.BerPers (Ber); -- Index door foreign key
CREATE INDEX ix_BerPers_Pers ON Ber.BerPers (Pers); -- Index door foreign key

-- SQL inhoud van bestand: bmr/Prot/Protocol_BRP_indexen.sql
--------------------------------------------------------------------------------
-- PostgreSQL SQL-DDL bestand voor Basisregistratie Personen (BRP)            --
-- PostgreSQL - Protocol Indexen DDL                                          --
--------------------------------------------------------------------------------
--
-- Gegenereerd door BRP Meta Register Release 44.
-- Gegenereerd op: dinsdag 24 nov 2015 13:59:27
--
--------------------------------------------------------------------------------

CREATE INDEX ix_Levsaantek_ToegangLevsautorisatie ON Prot.Levsaantek (ToegangLevsautorisatie); -- Index door foreign key
CREATE INDEX ix_Levsaantek_Dienst ON Prot.Levsaantek (Dienst); -- Index door foreign key
CREATE INDEX ix_Levsaantek_AdmHnd ON Prot.Levsaantek (AdmHnd); -- Index door foreign key
-- Onderdrukte index door foreign key vanwege reeds bestaande index: CREATE INDEX ix_LevsaantekPers_Levsaantek ON Prot.LevsaantekPers (Levsaantek); -- Index door foreign key
CREATE INDEX ix_LevsaantekPers_Pers ON Prot.LevsaantekPers (Pers); -- Index door foreign key

-- Einde
