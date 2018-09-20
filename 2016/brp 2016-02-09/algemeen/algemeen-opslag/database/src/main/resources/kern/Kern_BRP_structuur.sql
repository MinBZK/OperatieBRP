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

