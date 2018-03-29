--------------------------------------------------------------------------------
-- PostgreSQL SQL-DDL bestand voor Basisregistratie Personen (BRP)            --
-- PostgreSQL - Kern Structuur DDL                                            --
--------------------------------------------------------------------------------
-- 
-- Gegenereerd door BRP Meta Register Release 58.
-- Gegenereerd op: vrijdag 19 mei 2017 10:00:47
-- 
--------------------------------------------------------------------------------

-- Schemas ---------------------------------------------------------------------
DROP SCHEMA IF EXISTS AutAut CASCADE;
CREATE SCHEMA AutAut;
DROP SCHEMA IF EXISTS Beh CASCADE;
CREATE SCHEMA Beh;
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
CREATE SEQUENCE AutAut.seq_BijhouderFiatuitz START WITH 1;
CREATE TABLE AutAut.BijhouderFiatuitz (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_BijhouderFiatuitz')   /* BijhouderFiatuitzID */,
   Bijhouder                     Integer                       NOT NULL                                 /* PartijRolID */,
   DatIngang                     Integer                                 CHECK (to_char(to_date(DatIngang::text, 'yyyymmdd'), 'yyyymmdd')=DatIngang::text)   /* Dat */,
   DatEinde                      Integer                                 CHECK (to_char(to_date(DatEinde::text, 'yyyymmdd'), 'yyyymmdd')=DatEinde::text)   /* Dat */,
   BijhouderBijhvoorstel         Integer                                                                /* PartijRolID */,
   SrtDoc                        Smallint                                                               /* SrtDocID */,
   SrtAdmHnd                     Smallint                                                               /* SrtAdmHndID */,
   IndBlok                       Boolean                                 CHECK (IndBlok IN (true))      /* Ja */,
   IndAG                         Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   CONSTRAINT pk_BijhouderFiatuitz PRIMARY KEY (ID)
);
ALTER SEQUENCE AutAut.seq_BijhouderFiatuitz OWNED BY AutAut.BijhouderFiatuitz.ID;

CREATE SEQUENCE AutAut.seq_Bijhautorisatie START WITH 1;
CREATE TABLE AutAut.Bijhautorisatie (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_Bijhautorisatie')   /* BijhautorisatieID */,
   IndModelautorisatie           Boolean                       NOT NULL                                 /* JaNee */,
   Naam                          Varchar(80)                             CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   DatIngang                     Integer                                 CHECK (to_char(to_date(DatIngang::text, 'yyyymmdd'), 'yyyymmdd')=DatIngang::text)   /* Dat */,
   DatEinde                      Integer                                 CHECK (to_char(to_date(DatEinde::text, 'yyyymmdd'), 'yyyymmdd')=DatEinde::text)   /* Dat */,
   IndBlok                       Boolean                                 CHECK (IndBlok IN (true))      /* Ja */,
   IndAG                         Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   CONSTRAINT pk_Bijhautorisatie PRIMARY KEY (ID),
   CONSTRAINT uc_Bijhautorisatie_Naam UNIQUE (Naam)
);
ALTER SEQUENCE AutAut.seq_Bijhautorisatie OWNED BY AutAut.Bijhautorisatie.ID;

CREATE SEQUENCE AutAut.seq_BijhautorisatieSrtAdmHnd START WITH 1;
CREATE TABLE AutAut.BijhautorisatieSrtAdmHnd (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_BijhautorisatieSrtAdmHnd')   /* BijhautorisatieSrtAdmHndID */,
   Bijhautorisatie               Integer                       NOT NULL                                 /* BijhautorisatieID */,
   SrtAdmHnd                     Smallint                      NOT NULL                                 /* SrtAdmHndID */,
   IndAG                         Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   CONSTRAINT pk_BijhautorisatieSrtAdmHnd PRIMARY KEY (ID),
   CONSTRAINT uc_BijhautorisatieSrtAdmHnd_Bijhautorisatie_SrtAdmHnd UNIQUE (Bijhautorisatie, SrtAdmHnd)
);
ALTER SEQUENCE AutAut.seq_BijhautorisatieSrtAdmHnd OWNED BY AutAut.BijhautorisatieSrtAdmHnd.ID;

CREATE SEQUENCE AutAut.seq_Dienst START WITH 1;
CREATE TABLE AutAut.Dienst (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_Dienst')   /* DienstID */,
   Dienstbundel                  Integer                       NOT NULL                                 /* DienstbundelID */,
   Srt                           Smallint                      NOT NULL                                 /* SrtDienstID */,
   EffectAfnemerindicaties       Smallint                                                               /* EffectAfnemerindicatiesID */,
   DatIngang                     Integer                                 CHECK (to_char(to_date(DatIngang::text, 'yyyymmdd'), 'yyyymmdd')=DatIngang::text)   /* Dat */,
   DatEinde                      Integer                                 CHECK (to_char(to_date(DatEinde::text, 'yyyymmdd'), 'yyyymmdd')=DatEinde::text)   /* Dat */,
   IndBlok                       Boolean                                 CHECK (IndBlok IN (true))      /* Ja */,
   IndAG                         Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   Attenderingscriterium         Text                                                                   /* Attenderingscriterium */,
   IndAGAttendering              Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   MaxAantalZoekresultaten       Integer                                                                /* AantalResultaten */,
   IndAGZoeken                   Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   SrtSel                        Smallint                                                               /* SrtSelID */,
   EersteSeldat                  Integer                                 CHECK (to_char(to_date(EersteSeldat::text, 'yyyymmdd'), 'yyyymmdd')=EersteSeldat::text)   /* Dat */,
   Selinterval                   Smallint                                                               /* Selinterval */,
   EenheidSelinterval            Smallint                                                               /* EenheidSelintervalID */,
   NadereSelcriterium            Text                                                                   /* Populatiebeperking */,
   SelPeilmomMaterieelResultaat  Integer                                 CHECK (to_char(to_date(SelPeilmomMaterieelResultaat::text, 'yyyymmdd'), 'yyyymmdd')=SelPeilmomMaterieelResultaat::text)   /* Dat */,
   SelPeilmomFormeelResultaat    Timestamp with time zone                                               /* Ts */,
   HistorievormSel               Smallint                                                               /* HistorievormID */,
   IndSelresultaatControleren    Boolean                                                                /* JaNee */,
   MaxAantalPersPerSelbestand    Integer                                                                /* AantalResultaten */,
   MaxGrootteSelbestand          Integer                                                                /* AantalKilobytes */,
   IndVerzVolBerBijWijzAfniNaSe  Boolean                                                                /* JaNee */,
   LeverwijzeSel                 Smallint                                                               /* LeverwijzeSelID */,
   IndAGSel                      Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   CONSTRAINT pk_Dienst PRIMARY KEY (ID)
);
ALTER SEQUENCE AutAut.seq_Dienst OWNED BY AutAut.Dienst.ID;

CREATE SEQUENCE AutAut.seq_Dienstbundel START WITH 1;
CREATE TABLE AutAut.Dienstbundel (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_Dienstbundel')   /* DienstbundelID */,
   Levsautorisatie               Integer                       NOT NULL                                 /* LevsautorisatieID */,
   Naam                          Varchar(80)                             CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   DatIngang                     Integer                                 CHECK (to_char(to_date(DatIngang::text, 'yyyymmdd'), 'yyyymmdd')=DatIngang::text)   /* Dat */,
   DatEinde                      Integer                                 CHECK (to_char(to_date(DatEinde::text, 'yyyymmdd'), 'yyyymmdd')=DatEinde::text)   /* Dat */,
   NaderePopulatiebeperking      Text                                                                   /* Populatiebeperking */,
   IndNaderePopbeperkingVolConv  Boolean                                 CHECK (IndNaderePopbeperkingVolConv IN (false))   /* Nee */,
   Toelichting                   Text                                                                   /* OnbeperkteOms */,
   IndBlok                       Boolean                                 CHECK (IndBlok IN (true))      /* Ja */,
   IndAG                         Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   CONSTRAINT pk_Dienstbundel PRIMARY KEY (ID)
);
ALTER SEQUENCE AutAut.seq_Dienstbundel OWNED BY AutAut.Dienstbundel.ID;

CREATE SEQUENCE AutAut.seq_DienstbundelGroep START WITH 1;
CREATE TABLE AutAut.DienstbundelGroep (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_DienstbundelGroep')   /* DienstbundelGroepID */,
   Dienstbundel                  Integer                       NOT NULL                                 /* DienstbundelID */,
   Groep                         Integer                       NOT NULL                                 /* ElementID */,
   IndFormeleHistorie            Boolean                       NOT NULL                                 /* JaNee */,
   IndMaterieleHistorie          Boolean                       NOT NULL                                 /* JaNee */,
   IndVerantwoording             Boolean                       NOT NULL                                 /* JaNee */,
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

CREATE SEQUENCE AutAut.seq_EenheidSelinterval START WITH 1;
CREATE TABLE AutAut.EenheidSelinterval (
   ID                            Smallint                      NOT NULL  DEFAULT nextval('AutAut.seq_EenheidSelinterval')   /* EenheidSelintervalID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   CONSTRAINT pk_EenheidSelinterval PRIMARY KEY (ID),
   CONSTRAINT uc_EenheidSelinterval_Naam UNIQUE (Naam)
);
ALTER SEQUENCE AutAut.seq_EenheidSelinterval OWNED BY AutAut.EenheidSelinterval.ID;

CREATE SEQUENCE AutAut.seq_EffectAfnemerindicaties START WITH 1;
CREATE TABLE AutAut.EffectAfnemerindicaties (
   ID                            Smallint                      NOT NULL  DEFAULT nextval('AutAut.seq_EffectAfnemerindicaties')   /* EffectAfnemerindicatiesID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   CONSTRAINT pk_EffectAfnemerindicaties PRIMARY KEY (ID),
   CONSTRAINT uc_EffectAfnemerindicaties_Naam UNIQUE (Naam)
);
ALTER SEQUENCE AutAut.seq_EffectAfnemerindicaties OWNED BY AutAut.EffectAfnemerindicaties.ID;

CREATE SEQUENCE AutAut.seq_His_BijhouderFiatuitz START WITH 1;
CREATE TABLE AutAut.His_BijhouderFiatuitz (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_His_BijhouderFiatuitz')   /* His_BijhouderFiatuitzID */,
   BijhouderFiatuitz             Integer                       NOT NULL                                 /* BijhouderFiatuitzID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   DatIngang                     Integer                       NOT NULL  CHECK (to_char(to_date(DatIngang::text, 'yyyymmdd'), 'yyyymmdd')=DatIngang::text)   /* Dat */,
   DatEinde                      Integer                                 CHECK (to_char(to_date(DatEinde::text, 'yyyymmdd'), 'yyyymmdd')=DatEinde::text)   /* Dat */,
   BijhouderBijhvoorstel         Integer                                                                /* PartijRolID */,
   SrtDoc                        Smallint                                                               /* SrtDocID */,
   SrtAdmHnd                     Smallint                                                               /* SrtAdmHndID */,
   IndBlok                       Boolean                                 CHECK (IndBlok IN (true))      /* Ja */,
   CONSTRAINT pk_His_BijhouderFiatuitz PRIMARY KEY (ID),
   CONSTRAINT uc_His_BijhouderFiatuitz_BijhouderFiatuitz_TsReg UNIQUE (BijhouderFiatuitz, TsReg)
);
ALTER SEQUENCE AutAut.seq_His_BijhouderFiatuitz OWNED BY AutAut.His_BijhouderFiatuitz.ID;

CREATE SEQUENCE AutAut.seq_His_Bijhautorisatie START WITH 1;
CREATE TABLE AutAut.His_Bijhautorisatie (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_His_Bijhautorisatie')   /* His_BijhautorisatieID */,
   Bijhautorisatie               Integer                       NOT NULL                                 /* BijhautorisatieID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   DatIngang                     Integer                       NOT NULL  CHECK (to_char(to_date(DatIngang::text, 'yyyymmdd'), 'yyyymmdd')=DatIngang::text)   /* Dat */,
   DatEinde                      Integer                                 CHECK (to_char(to_date(DatEinde::text, 'yyyymmdd'), 'yyyymmdd')=DatEinde::text)   /* Dat */,
   IndBlok                       Boolean                                 CHECK (IndBlok IN (true))      /* Ja */,
   CONSTRAINT pk_His_Bijhautorisatie PRIMARY KEY (ID),
   CONSTRAINT uc_His_Bijhautorisatie_Bijhautorisatie_TsReg UNIQUE (Bijhautorisatie, TsReg)
);
ALTER SEQUENCE AutAut.seq_His_Bijhautorisatie OWNED BY AutAut.His_Bijhautorisatie.ID;

CREATE SEQUENCE AutAut.seq_His_BijhautorisatieSrtAdmHnd START WITH 1;
CREATE TABLE AutAut.His_BijhautorisatieSrtAdmHnd (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_His_BijhautorisatieSrtAdmHnd')   /* His_BijhautorisatieSrtAdmHnd */,
   BijhautorisatieSrtAdmHnd      Integer                       NOT NULL                                 /* BijhautorisatieSrtAdmHndID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   CONSTRAINT pk_His_BijhautorisatieSrtAdmHnd PRIMARY KEY (ID),
   CONSTRAINT uc_His_BijhautorisatieSrtAdmHnd_BijhautorisatieSrtAdmHnd_TsReg UNIQUE (BijhautorisatieSrtAdmHnd, TsReg)
);
ALTER SEQUENCE AutAut.seq_His_BijhautorisatieSrtAdmHnd OWNED BY AutAut.His_BijhautorisatieSrtAdmHnd.ID;

CREATE SEQUENCE AutAut.seq_His_Dienst START WITH 1;
CREATE TABLE AutAut.His_Dienst (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_His_Dienst')   /* His_DienstID */,
   Dienst                        Integer                       NOT NULL                                 /* DienstID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   DatIngang                     Integer                       NOT NULL  CHECK (to_char(to_date(DatIngang::text, 'yyyymmdd'), 'yyyymmdd')=DatIngang::text)   /* Dat */,
   DatEinde                      Integer                                 CHECK (to_char(to_date(DatEinde::text, 'yyyymmdd'), 'yyyymmdd')=DatEinde::text)   /* Dat */,
   IndBlok                       Boolean                                 CHECK (IndBlok IN (true))      /* Ja */,
   CONSTRAINT pk_His_Dienst PRIMARY KEY (ID),
   CONSTRAINT uc_His_Dienst_Dienst_TsReg UNIQUE (Dienst, TsReg)
);
ALTER SEQUENCE AutAut.seq_His_Dienst OWNED BY AutAut.His_Dienst.ID;

CREATE SEQUENCE AutAut.seq_His_DienstAttendering START WITH 1;
CREATE TABLE AutAut.His_DienstAttendering (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_His_DienstAttendering')   /* His_DienstAttenderingID */,
   Dienst                        Integer                       NOT NULL                                 /* DienstID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   Attenderingscriterium         Text                                                                   /* Attenderingscriterium */,
   CONSTRAINT pk_His_DienstAttendering PRIMARY KEY (ID),
   CONSTRAINT uc_His_DienstAttendering_Dienst_TsReg UNIQUE (Dienst, TsReg)
);
ALTER SEQUENCE AutAut.seq_His_DienstAttendering OWNED BY AutAut.His_DienstAttendering.ID;

CREATE SEQUENCE AutAut.seq_His_DienstSel START WITH 1;
CREATE TABLE AutAut.His_DienstSel (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_His_DienstSel')   /* His_DienstSelID */,
   Dienst                        Integer                       NOT NULL                                 /* DienstID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   SrtSel                        Smallint                      NOT NULL                                 /* SrtSelID */,
   EersteSeldat                  Integer                                 CHECK (to_char(to_date(EersteSeldat::text, 'yyyymmdd'), 'yyyymmdd')=EersteSeldat::text)   /* Dat */,
   Selinterval                   Smallint                                                               /* Selinterval */,
   EenheidSelinterval            Smallint                                                               /* EenheidSelintervalID */,
   NadereSelcriterium            Text                                                                   /* Populatiebeperking */,
   SelPeilmomMaterieelResultaat  Integer                                 CHECK (to_char(to_date(SelPeilmomMaterieelResultaat::text, 'yyyymmdd'), 'yyyymmdd')=SelPeilmomMaterieelResultaat::text)   /* Dat */,
   SelPeilmomFormeelResultaat    Timestamp with time zone                                               /* Ts */,
   HistorievormSel               Smallint                      NOT NULL                                 /* HistorievormID */,
   IndSelresultaatControleren    Boolean                       NOT NULL                                 /* JaNee */,
   MaxAantalPersPerSelbestand    Integer                                                                /* AantalResultaten */,
   MaxGrootteSelbestand          Integer                                                                /* AantalKilobytes */,
   IndVerzVolBerBijWijzAfniNaSe  Boolean                                                                /* JaNee */,
   LeverwijzeSel                 Smallint                                                               /* LeverwijzeSelID */,
   CONSTRAINT pk_His_DienstSel PRIMARY KEY (ID),
   CONSTRAINT uc_His_DienstSel_Dienst_TsReg UNIQUE (Dienst, TsReg)
);
ALTER SEQUENCE AutAut.seq_His_DienstSel OWNED BY AutAut.His_DienstSel.ID;

CREATE SEQUENCE AutAut.seq_His_DienstZoeken START WITH 1;
CREATE TABLE AutAut.His_DienstZoeken (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_His_DienstZoeken')   /* His_DienstZoekenID */,
   Dienst                        Integer                       NOT NULL                                 /* DienstID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   MaxAantalZoekresultaten       Integer                       NOT NULL                                 /* AantalResultaten */,
   CONSTRAINT pk_His_DienstZoeken PRIMARY KEY (ID),
   CONSTRAINT uc_His_DienstZoeken_Dienst_TsReg UNIQUE (Dienst, TsReg)
);
ALTER SEQUENCE AutAut.seq_His_DienstZoeken OWNED BY AutAut.His_DienstZoeken.ID;

CREATE SEQUENCE AutAut.seq_His_Dienstbundel START WITH 1;
CREATE TABLE AutAut.His_Dienstbundel (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_His_Dienstbundel')   /* His_DienstbundelID */,
   Dienstbundel                  Integer                       NOT NULL                                 /* DienstbundelID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   DatIngang                     Integer                       NOT NULL  CHECK (to_char(to_date(DatIngang::text, 'yyyymmdd'), 'yyyymmdd')=DatIngang::text)   /* Dat */,
   DatEinde                      Integer                                 CHECK (to_char(to_date(DatEinde::text, 'yyyymmdd'), 'yyyymmdd')=DatEinde::text)   /* Dat */,
   NaderePopulatiebeperking      Text                                                                   /* Populatiebeperking */,
   IndNaderePopbeperkingVolConv  Boolean                                 CHECK (IndNaderePopbeperkingVolConv IN (false))   /* Nee */,
   Toelichting                   Text                                                                   /* OnbeperkteOms */,
   IndBlok                       Boolean                                 CHECK (IndBlok IN (true))      /* Ja */,
   CONSTRAINT pk_His_Dienstbundel PRIMARY KEY (ID),
   CONSTRAINT uc_His_Dienstbundel_Dienstbundel_TsReg UNIQUE (Dienstbundel, TsReg)
);
ALTER SEQUENCE AutAut.seq_His_Dienstbundel OWNED BY AutAut.His_Dienstbundel.ID;

CREATE SEQUENCE AutAut.seq_His_Levsautorisatie START WITH 1;
CREATE TABLE AutAut.His_Levsautorisatie (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_His_Levsautorisatie')   /* His_LevsautorisatieID */,
   Levsautorisatie               Integer                       NOT NULL                                 /* LevsautorisatieID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Protocolleringsniveau         Smallint                      NOT NULL                                 /* ProtocolleringsniveauID */,
   IndAliasSrtAdmHndLeveren      Boolean                       NOT NULL                                 /* JaNee */,
   DatIngang                     Integer                       NOT NULL  CHECK (to_char(to_date(DatIngang::text, 'yyyymmdd'), 'yyyymmdd')=DatIngang::text)   /* Dat */,
   DatEinde                      Integer                                 CHECK (to_char(to_date(DatEinde::text, 'yyyymmdd'), 'yyyymmdd')=DatEinde::text)   /* Dat */,
   Populatiebeperking            Text                                                                   /* Populatiebeperking */,
   Toelichting                   Text                                                                   /* OnbeperkteOms */,
   IndBlok                       Boolean                                 CHECK (IndBlok IN (true))      /* Ja */,
   CONSTRAINT pk_His_Levsautorisatie PRIMARY KEY (ID),
   CONSTRAINT uc_His_Levsautorisatie_Levsautorisatie_TsReg UNIQUE (Levsautorisatie, TsReg)
);
ALTER SEQUENCE AutAut.seq_His_Levsautorisatie OWNED BY AutAut.His_Levsautorisatie.ID;

CREATE SEQUENCE AutAut.seq_His_Seltaak START WITH 1;
CREATE TABLE AutAut.His_Seltaak (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_His_Seltaak')   /* His_SeltaakID */,
   Seltaak                       Integer                       NOT NULL                                 /* SeltaakID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   DatPlanning                   Integer                                 CHECK (to_char(to_date(DatPlanning::text, 'yyyymmdd'), 'yyyymmdd')=DatPlanning::text)   /* Dat */,
   DatUitvoer                    Integer                                 CHECK (to_char(to_date(DatUitvoer::text, 'yyyymmdd'), 'yyyymmdd')=DatUitvoer::text)   /* Dat */,
   PeilmomMaterieel              Integer                                 CHECK (to_char(to_date(PeilmomMaterieel::text, 'yyyymmdd'), 'yyyymmdd')=PeilmomMaterieel::text)   /* Dat */,
   PeilmomMaterieelResultaat     Integer                                 CHECK (to_char(to_date(PeilmomMaterieelResultaat::text, 'yyyymmdd'), 'yyyymmdd')=PeilmomMaterieelResultaat::text)   /* Dat */,
   PeilmomFormeelResultaat       Timestamp with time zone                                               /* Ts */,
   IndSelLijstGebruiken          Boolean                       NOT NULL                                 /* JaNee */,
   CONSTRAINT pk_His_Seltaak PRIMARY KEY (ID),
   CONSTRAINT uc_His_Seltaak_Seltaak_TsReg UNIQUE (Seltaak, TsReg)
);
ALTER SEQUENCE AutAut.seq_His_Seltaak OWNED BY AutAut.His_Seltaak.ID;

CREATE SEQUENCE AutAut.seq_His_SeltaakStatus START WITH 1;
CREATE TABLE AutAut.His_SeltaakStatus (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_His_SeltaakStatus')   /* His_SeltaakStatusID */,
   Seltaak                       Integer                       NOT NULL                                 /* SeltaakID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   Status                        Smallint                      NOT NULL                                 /* SeltaakStatusID */,
   StatusGewijzigdDoor           Varchar(80)                   NOT NULL  CHECK (StatusGewijzigdDoor <> '' )   /* NaamEnumeratiewaarde */,
   StatusToelichting             Text                                                                   /* OnbeperkteOms */,
   CONSTRAINT pk_His_SeltaakStatus PRIMARY KEY (ID),
   CONSTRAINT uc_His_SeltaakStatus_Seltaak_TsReg UNIQUE (Seltaak, TsReg)
);
ALTER SEQUENCE AutAut.seq_His_SeltaakStatus OWNED BY AutAut.His_SeltaakStatus.ID;

CREATE SEQUENCE AutAut.seq_His_ToegangBijhautorisatie START WITH 1;
CREATE TABLE AutAut.His_ToegangBijhautorisatie (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_His_ToegangBijhautorisatie')   /* His_ToegangBijhautorisatieID */,
   ToegangBijhautorisatie        Integer                       NOT NULL                                 /* ToegangBijhautorisatieID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   DatIngang                     Integer                       NOT NULL  CHECK (to_char(to_date(DatIngang::text, 'yyyymmdd'), 'yyyymmdd')=DatIngang::text)   /* Dat */,
   DatEinde                      Integer                                 CHECK (to_char(to_date(DatEinde::text, 'yyyymmdd'), 'yyyymmdd')=DatEinde::text)   /* Dat */,
   Afleverpunt                   Varchar(200)                            CHECK (Afleverpunt <> '' )     /* Uri */,
   IndBlok                       Boolean                                 CHECK (IndBlok IN (true))      /* Ja */,
   CONSTRAINT pk_His_ToegangBijhautorisatie PRIMARY KEY (ID),
   CONSTRAINT uc_His_ToegangBijhautorisatie_ToegangBijhautorisatie_TsReg UNIQUE (ToegangBijhautorisatie, TsReg)
);
ALTER SEQUENCE AutAut.seq_His_ToegangBijhautorisatie OWNED BY AutAut.His_ToegangBijhautorisatie.ID;

CREATE SEQUENCE AutAut.seq_His_ToegangLevsautorisatie START WITH 1;
CREATE TABLE AutAut.His_ToegangLevsautorisatie (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_His_ToegangLevsautorisatie')   /* His_ToegangLevsautorisatieID */,
   ToegangLevsautorisatie        Integer                       NOT NULL                                 /* ToegangLevsautorisatieID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   DatIngang                     Integer                       NOT NULL  CHECK (to_char(to_date(DatIngang::text, 'yyyymmdd'), 'yyyymmdd')=DatIngang::text)   /* Dat */,
   DatEinde                      Integer                                 CHECK (to_char(to_date(DatEinde::text, 'yyyymmdd'), 'yyyymmdd')=DatEinde::text)   /* Dat */,
   NaderePopulatiebeperking      Text                                                                   /* Populatiebeperking */,
   Afleverpunt                   Varchar(200)                            CHECK (Afleverpunt <> '' )     /* Uri */,
   IndBlok                       Boolean                                 CHECK (IndBlok IN (true))      /* Ja */,
   CONSTRAINT pk_His_ToegangLevsautorisatie PRIMARY KEY (ID),
   CONSTRAINT uc_His_ToegangLevsautorisatie_ToegangLevsautorisatie_TsReg UNIQUE (ToegangLevsautorisatie, TsReg)
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
   DatIngang                     Integer                                 CHECK (to_char(to_date(DatIngang::text, 'yyyymmdd'), 'yyyymmdd')=DatIngang::text)   /* Dat */,
   DatEinde                      Integer                                 CHECK (to_char(to_date(DatEinde::text, 'yyyymmdd'), 'yyyymmdd')=DatEinde::text)   /* Dat */,
   Populatiebeperking            Text                                                                   /* Populatiebeperking */,
   Toelichting                   Text                                                                   /* OnbeperkteOms */,
   IndBlok                       Boolean                                 CHECK (IndBlok IN (true))      /* Ja */,
   IndAG                         Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   CONSTRAINT pk_Levsautorisatie PRIMARY KEY (ID),
   CONSTRAINT uc_Levsautorisatie_Naam UNIQUE (Naam)
);
ALTER SEQUENCE AutAut.seq_Levsautorisatie OWNED BY AutAut.Levsautorisatie.ID;

CREATE SEQUENCE AutAut.seq_LeverwijzeSel START WITH 1;
CREATE TABLE AutAut.LeverwijzeSel (
   ID                            Smallint                      NOT NULL  DEFAULT nextval('AutAut.seq_LeverwijzeSel')   /* LeverwijzeSelID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   CONSTRAINT pk_LeverwijzeSel PRIMARY KEY (ID),
   CONSTRAINT uc_LeverwijzeSel_Naam UNIQUE (Naam)
);
ALTER SEQUENCE AutAut.seq_LeverwijzeSel OWNED BY AutAut.LeverwijzeSel.ID;

CREATE TABLE AutAut.Protocolleringsniveau (
   ID                            Smallint                      NOT NULL                                 /* ProtocolleringsniveauID */,
   Code                          Smallint                      NOT NULL                                 /* ProtocolleringsniveauCode */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   DatAanvGel                    Integer                                 CHECK (to_char(to_date(DatAanvGel::text, 'yyyymmdd'), 'yyyymmdd')=DatAanvGel::text)   /* Dat */,
   DatEindeGel                   Integer                                 CHECK (to_char(to_date(DatEindeGel::text, 'yyyymmdd'), 'yyyymmdd')=DatEindeGel::text)   /* Dat */,
   CONSTRAINT pk_Protocolleringsniveau PRIMARY KEY (ID),
   CONSTRAINT uc_Protocolleringsniveau_Code UNIQUE (Code),
   CONSTRAINT uc_Protocolleringsniveau_Naam UNIQUE (Naam),
   CONSTRAINT uc_Protocolleringsniveau_Oms UNIQUE (Oms)
);

CREATE SEQUENCE AutAut.seq_Selrun START WITH 1;
CREATE TABLE AutAut.Selrun (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_Selrun')   /* SelrunID */,
   TsStart                       Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsGereed                      Timestamp with time zone                                               /* Ts */,
   CONSTRAINT pk_Selrun PRIMARY KEY (ID)
);
ALTER SEQUENCE AutAut.seq_Selrun OWNED BY AutAut.Selrun.ID;

CREATE SEQUENCE AutAut.seq_Seltaak START WITH 1;
CREATE TABLE AutAut.Seltaak (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_Seltaak')   /* SeltaakID */,
   Dienst                        Integer                       NOT NULL                                 /* DienstID */,
   ToegangLevsautorisatie        Integer                       NOT NULL                                 /* ToegangLevsautorisatieID */,
   Volgnr                        Integer                       NOT NULL                                 /* Volgnr */,
   DatPlanning                   Integer                                 CHECK (to_char(to_date(DatPlanning::text, 'yyyymmdd'), 'yyyymmdd')=DatPlanning::text)   /* Dat */,
   DatUitvoer                    Integer                                 CHECK (to_char(to_date(DatUitvoer::text, 'yyyymmdd'), 'yyyymmdd')=DatUitvoer::text)   /* Dat */,
   PeilmomMaterieel              Integer                                 CHECK (to_char(to_date(PeilmomMaterieel::text, 'yyyymmdd'), 'yyyymmdd')=PeilmomMaterieel::text)   /* Dat */,
   PeilmomMaterieelResultaat     Integer                                 CHECK (to_char(to_date(PeilmomMaterieelResultaat::text, 'yyyymmdd'), 'yyyymmdd')=PeilmomMaterieelResultaat::text)   /* Dat */,
   PeilmomFormeelResultaat       Timestamp with time zone                                               /* Ts */,
   IndSelLijstGebruiken          Boolean                                                                /* JaNee */,
   IndAG                         Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   Status                        Smallint                                                               /* SeltaakStatusID */,
   StatusGewijzigdDoor           Varchar(80)                             CHECK (StatusGewijzigdDoor <> '' )   /* NaamEnumeratiewaarde */,
   StatusToelichting             Text                                                                   /* OnbeperkteOms */,
   IndAGStatus                   Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   UitgevoerdIn                  Integer                                                                /* SelrunID */,
   CONSTRAINT pk_Seltaak PRIMARY KEY (ID),
   CONSTRAINT uc_Seltaak_Dienst_ToegangLevsautorisatie_Volgnr UNIQUE (Dienst, ToegangLevsautorisatie, Volgnr)
);
ALTER SEQUENCE AutAut.seq_Seltaak OWNED BY AutAut.Seltaak.ID;

CREATE SEQUENCE AutAut.seq_SeltaakStatus START WITH 1;
CREATE TABLE AutAut.SeltaakStatus (
   ID                            Smallint                      NOT NULL  DEFAULT nextval('AutAut.seq_SeltaakStatus')   /* SeltaakStatusID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   CONSTRAINT pk_SeltaakStatus PRIMARY KEY (ID),
   CONSTRAINT uc_SeltaakStatus_Naam UNIQUE (Naam)
);
ALTER SEQUENCE AutAut.seq_SeltaakStatus OWNED BY AutAut.SeltaakStatus.ID;

CREATE TABLE AutAut.SrtDienst (
   ID                            Smallint                      NOT NULL                                 /* SrtDienstID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   CONSTRAINT pk_SrtDienst PRIMARY KEY (ID),
   CONSTRAINT uc_SrtDienst_Naam UNIQUE (Naam)
);

CREATE SEQUENCE AutAut.seq_SrtSel START WITH 1;
CREATE TABLE AutAut.SrtSel (
   ID                            Smallint                      NOT NULL  DEFAULT nextval('AutAut.seq_SrtSel')   /* SrtSelID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   CONSTRAINT pk_SrtSel PRIMARY KEY (ID),
   CONSTRAINT uc_SrtSel_Naam UNIQUE (Naam)
);
ALTER SEQUENCE AutAut.seq_SrtSel OWNED BY AutAut.SrtSel.ID;

CREATE SEQUENCE AutAut.seq_ToegangBijhautorisatie START WITH 1;
CREATE TABLE AutAut.ToegangBijhautorisatie (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_ToegangBijhautorisatie')   /* ToegangBijhautorisatieID */,
   Geautoriseerde                Integer                       NOT NULL                                 /* PartijRolID */,
   Bijhautorisatie               Integer                       NOT NULL                                 /* BijhautorisatieID */,
   Ondertekenaar                 Smallint                                                               /* PartijID */,
   Transporteur                  Smallint                                                               /* PartijID */,
   DatIngang                     Integer                                 CHECK (to_char(to_date(DatIngang::text, 'yyyymmdd'), 'yyyymmdd')=DatIngang::text)   /* Dat */,
   DatEinde                      Integer                                 CHECK (to_char(to_date(DatEinde::text, 'yyyymmdd'), 'yyyymmdd')=DatEinde::text)   /* Dat */,
   Afleverpunt                   Varchar(200)                            CHECK (Afleverpunt <> '' )     /* Uri */,
   IndBlok                       Boolean                                 CHECK (IndBlok IN (true))      /* Ja */,
   IndAG                         Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   CONSTRAINT pk_ToegangBijhautorisatie PRIMARY KEY (ID),
   CONSTRAINT uc_ToegangBijhautorisatie_Geautoriseerde_Bijhautorisatie_Ondert UNIQUE (Geautoriseerde, Bijhautorisatie, Ondertekenaar, Transporteur)
);
ALTER SEQUENCE AutAut.seq_ToegangBijhautorisatie OWNED BY AutAut.ToegangBijhautorisatie.ID;

CREATE SEQUENCE AutAut.seq_ToegangLevsautorisatie START WITH 1;
CREATE TABLE AutAut.ToegangLevsautorisatie (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_ToegangLevsautorisatie')   /* ToegangLevsautorisatieID */,
   Geautoriseerde                Integer                       NOT NULL                                 /* PartijRolID */,
   Levsautorisatie               Integer                       NOT NULL                                 /* LevsautorisatieID */,
   Ondertekenaar                 Smallint                                                               /* PartijID */,
   Transporteur                  Smallint                                                               /* PartijID */,
   DatIngang                     Integer                                 CHECK (to_char(to_date(DatIngang::text, 'yyyymmdd'), 'yyyymmdd')=DatIngang::text)   /* Dat */,
   DatEinde                      Integer                                 CHECK (to_char(to_date(DatEinde::text, 'yyyymmdd'), 'yyyymmdd')=DatEinde::text)   /* Dat */,
   NaderePopulatiebeperking      Text                                                                   /* Populatiebeperking */,
   Afleverpunt                   Varchar(200)                            CHECK (Afleverpunt <> '' )     /* Uri */,
   IndBlok                       Boolean                                 CHECK (IndBlok IN (true))      /* Ja */,
   IndAG                         Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   CONSTRAINT pk_ToegangLevsautorisatie PRIMARY KEY (ID),
   CONSTRAINT uc_ToegangLevsautorisatie_Geautoriseerde_Levsautorisatie_Ondert UNIQUE (Geautoriseerde, Levsautorisatie, Ondertekenaar, Transporteur)
);
ALTER SEQUENCE AutAut.seq_ToegangLevsautorisatie OWNED BY AutAut.ToegangLevsautorisatie.ID;

CREATE SEQUENCE Beh.seq_SrtBerVrijBer START WITH 1;
CREATE TABLE Beh.SrtBerVrijBer (
   ID                            Smallint                      NOT NULL  DEFAULT nextval('Beh.seq_SrtBerVrijBer')   /* SrtBerVrijBerID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   SrtBer                        Smallint                      NOT NULL                                 /* SrtBerID */,
   CONSTRAINT pk_SrtBerVrijBer PRIMARY KEY (ID),
   CONSTRAINT uc_SrtBerVrijBer_Naam UNIQUE (Naam)
);
ALTER SEQUENCE Beh.seq_SrtBerVrijBer OWNED BY Beh.SrtBerVrijBer.ID;

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
   Rubr8811CodeRNIDeelnemer      Char(4)                       NOT NULL  CHECK (Rubr8811CodeRNIDeelnemer ~ '^[0-9]+$' AND Length(Rubr8811CodeRNIDeelnemer) >= 4)   /* LO3RNIDeelnemer */,
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
   Rubr6410RdnBeeindigenNation   Varchar(3)                    NOT NULL  CHECK (Rubr6410RdnBeeindigenNation <> '' )   /* LO3RdnBeeindigenNation */,
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
   Partijcode                    Char(6)                       NOT NULL  CHECK (Partijcode ~ '^[0-9]+$' AND Length(Partijcode) >= 6)   /* PartijCode */,
   DatIngangTabelregel           Integer                       NOT NULL  CHECK (to_char(to_date(DatIngangTabelregel::text, 'yyyymmdd'), 'yyyymmdd')=DatIngangTabelregel::text)   /* Dat */,
   DatEindeTabelregel            Integer                                 CHECK (to_char(to_date(DatEindeTabelregel::text, 'yyyymmdd'), 'yyyymmdd')=DatEindeTabelregel::text)   /* Dat */,
   Selectiesrt                   Char(1)                                 CHECK (Selectiesrt ~ '^[0-9]+$')   /* LO3Selsrt */,
   Beraand                       Char(1)                                 CHECK (Beraand ~ '^[0-9]+$')   /* LO3Beraand */,
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
   Code                          Char(2)                       NOT NULL  CHECK (Code ~ '^[0-9]+$' AND Length(Code) >= 2)   /* AandVerblijfsrCode */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   DatAanvGel                    Integer                                 CHECK (to_char(to_date(DatAanvGel::text, 'yyyymmdd'), 'yyyymmdd')=DatAanvGel::text)   /* Dat */,
   DatEindeGel                   Integer                                 CHECK (to_char(to_date(DatEindeGel::text, 'yyyymmdd'), 'yyyymmdd')=DatEindeGel::text)   /* Dat */,
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

CREATE SEQUENCE Kern.seq_AutVanAfgifteBLPersnr START WITH 1;
CREATE TABLE Kern.AutVanAfgifteBLPersnr (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_AutVanAfgifteBLPersnr')   /* AutVanAfgifteBLPersnrID */,
   Code                          Char(4)                       NOT NULL  CHECK (Code ~ '^[0-9]+$' AND Length(Code) >= 4)   /* AutVanAfgifteBLPersnrCode */,
   LandGebied                    Integer                                                                /* LandGebiedID */,
   Nation                        Integer                                                                /* NationID */,
   DatAanvGel                    Integer                                 CHECK (to_char(to_date(DatAanvGel::text, 'yyyymmdd'), 'yyyymmdd')=DatAanvGel::text)   /* Dat */,
   DatEindeGel                   Integer                                 CHECK (to_char(to_date(DatEindeGel::text, 'yyyymmdd'), 'yyyymmdd')=DatEindeGel::text)   /* Dat */,
   CONSTRAINT pk_AutVanAfgifteBLPersnr PRIMARY KEY (ID),
   CONSTRAINT uc_AutVanAfgifteBLPersnr_Code UNIQUE (Code)
);
ALTER SEQUENCE Kern.seq_AutVanAfgifteBLPersnr OWNED BY Kern.AutVanAfgifteBLPersnr.ID;

CREATE SEQUENCE Kern.seq_AuttypeVanAfgifteReisdoc START WITH 1;
CREATE TABLE Kern.AuttypeVanAfgifteReisdoc (
   ID                            Smallint                      NOT NULL  DEFAULT nextval('Kern.seq_AuttypeVanAfgifteReisdoc')   /* AuttypeVanAfgifteReisdocID */,
   Code                          Varchar(2)                    NOT NULL  CHECK (Code <> '' )            /* AuttypeVanAfgifteReisdocCode */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   DatAanvGel                    Integer                                 CHECK (to_char(to_date(DatAanvGel::text, 'yyyymmdd'), 'yyyymmdd')=DatAanvGel::text)   /* Dat */,
   DatEindeGel                   Integer                                 CHECK (to_char(to_date(DatEindeGel::text, 'yyyymmdd'), 'yyyymmdd')=DatEindeGel::text)   /* Dat */,
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

CREATE TABLE Kern.Bijhresultaat (
   ID                            Smallint                      NOT NULL                                 /* BijhresultaatID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   CONSTRAINT pk_Bijhresultaat PRIMARY KEY (ID),
   CONSTRAINT uc_Bijhresultaat_Naam UNIQUE (Naam)
);

CREATE TABLE Kern.Bijhsituatie (
   ID                            Smallint                      NOT NULL                                 /* BijhsituatieID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   IndVerwerkbaar                Boolean                       NOT NULL                                 /* JaNee */,
   IndHeeftInvlOpBijhresultaat   Boolean                       NOT NULL                                 /* JaNee */,
   CONSTRAINT pk_Bijhsituatie PRIMARY KEY (ID),
   CONSTRAINT uc_Bijhsituatie_Naam UNIQUE (Naam)
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
   Naam                          Varchar(200)                  NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaardeLang */,
   Srt                           Smallint                      NOT NULL                                 /* SrtElementID */,
   ElementNaam                   Varchar(80)                   NOT NULL  CHECK (ElementNaam <> '' )     /* NaamEnumeratiewaarde */,
   Alias                         Varchar(200)                            CHECK (Alias <> '' )           /* NaamEnumeratiewaardeLang */,
   Objecttype                    Integer                                                                /* ElementID */,
   Groep                         Integer                                                                /* ElementID */,
   Volgnr                        Integer                       NOT NULL                                 /* Volgnr */,
   AliasVan                      Integer                                                                /* ElementID */,
   Autorisatie                   Smallint                                                               /* SrtElementAutorisatieID */,
   Tabel                         Integer                                                                /* ElementID */,
   IdentDbSchema                 Varchar(80)                             CHECK (IdentDbSchema <> '' )   /* IdentifierLang */,
   IdentDb                       Varchar(80)                             CHECK (IdentDb <> '' )         /* IdentifierLang */,
   HisTabel                      Integer                                                                /* ElementID */,
   HisIdentDB                    Varchar(80)                             CHECK (HisIdentDB <> '' )      /* IdentifierLang */,
   DbObject                      Integer                                                                /* ElementID */,
   HisDbObject                   Integer                                                                /* ElementID */,
   HistoriePatroon               Varchar(10)                             CHECK (HistoriePatroon IN ('F','F_M','F_M1','F1','G','M1'))   /* HistoriePatroon */,
   VerantwoordingCategorie       Varchar(1)                              CHECK (VerantwoordingCategorie IN ('A','D','G'))   /* VerantwoordingCategorie */,
   Type                          Integer                                                                /* ElementID */,
   TypeIdentXSD                  Varchar(80)                             CHECK (TypeIdentXSD <> '' )    /* IdentifierLang */,
   TypeIdentDb                   Varchar(80)                             CHECK (TypeIdentDb <> '' )     /* IdentifierLang */,
   ExpressieBasistype            Varchar(80)                             CHECK (ExpressieBasistype <> '' )   /* IdentifierLang */,
   SrtInh                        Varchar(1)                              CHECK (SrtInh IN ('D','S','X'))   /* ObjecttypeSrtInh */,
   InverseAssociatieIdentCode    Varchar(80)                             CHECK (InverseAssociatieIdentCode <> '' )   /* IdentifierLang */,
   InBer                         Boolean                                 CHECK (InBer IN (true))        /* Ja */,
   IdentXSD                      Varchar(80)                             CHECK (IdentXSD <> '' )        /* IdentifierLang */,
   MinimumLengte                 Integer                                                                /* ElementLengte */,
   MaximumLengte                 Integer                                                                /* ElementLengte */,
   Sorteervolgorde               Smallint                                                               /* Sorteervolgorde */,
   AGAttr                        Integer                                                                /* ElementID */,
   IdentExpressie                Varchar(80)                             CHECK (IdentExpressie <> '' )   /* IdentifierLang */,
   DatAanvGel                    Integer                                 CHECK (to_char(to_date(DatAanvGel::text, 'yyyymmdd'), 'yyyymmdd')=DatAanvGel::text)   /* Dat */,
   DatEindeGel                   Integer                                 CHECK (to_char(to_date(DatEindeGel::text, 'yyyymmdd'), 'yyyymmdd')=DatEindeGel::text)   /* Dat */,
   CONSTRAINT pk_Element PRIMARY KEY (ID),
   CONSTRAINT uc_Element_Naam UNIQUE (Naam)
);

CREATE SEQUENCE Kern.seq_Gem START WITH 1;
CREATE TABLE Kern.Gem (
   ID                            Smallint                      NOT NULL  DEFAULT nextval('Kern.seq_Gem')   /* GemID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Code                          Char(4)                       NOT NULL  CHECK (Code ~ '^[0-9]+$' AND Length(Code) >= 4)   /* GemCode */,
   Partij                        Smallint                      NOT NULL                                 /* PartijID */,
   VoortzettendeGem              Smallint                                                               /* GemID */,
   DatAanvGel                    Integer                                 CHECK (to_char(to_date(DatAanvGel::text, 'yyyymmdd'), 'yyyymmdd')=DatAanvGel::text)   /* Dat */,
   DatEindeGel                   Integer                                 CHECK (to_char(to_date(DatEindeGel::text, 'yyyymmdd'), 'yyyymmdd')=DatEindeGel::text)   /* Dat */,
   CONSTRAINT pk_Gem PRIMARY KEY (ID),
   CONSTRAINT uc_Gem_Code UNIQUE (Code)
);
ALTER SEQUENCE Kern.seq_Gem OWNED BY Kern.Gem.ID;

CREATE TABLE Kern.Geslachtsaand (
   ID                            Smallint                      NOT NULL                                 /* GeslachtsaandID */,
   Code                          Varchar(1)                    NOT NULL  CHECK (Code <> '' )            /* GeslachtsaandCode */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
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
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   DatIngang                     Integer                       NOT NULL  CHECK (to_char(to_date(DatIngang::text, 'yyyymmdd'), 'yyyymmdd')=DatIngang::text)   /* Dat */,
   DatEinde                      Integer                                 CHECK (to_char(to_date(DatEinde::text, 'yyyymmdd'), 'yyyymmdd')=DatEinde::text)   /* Dat */,
   OIN                           Varchar(40)                             CHECK (OIN <> '' )             /* OIN */,
   Srt                           Smallint                                                               /* SrtPartijID */,
   IndVerstrbeperkingMogelijk    Boolean                       NOT NULL                                 /* JaNee */,
   DatOvergangNaarBRP            Integer                                 CHECK (to_char(to_date(DatOvergangNaarBRP::text, 'yyyymmdd'), 'yyyymmdd')=DatOvergangNaarBRP::text)   /* Dat */,
   CONSTRAINT pk_His_Partij PRIMARY KEY (ID),
   CONSTRAINT uc_His_Partij_Partij_TsReg UNIQUE (Partij, TsReg)
);
ALTER SEQUENCE Kern.seq_His_Partij OWNED BY Kern.His_Partij.ID;

CREATE SEQUENCE Kern.seq_His_PartijBijhouding START WITH 1;
CREATE TABLE Kern.His_PartijBijhouding (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PartijBijhouding')   /* His_PartijBijhoudingID */,
   Partij                        Smallint                      NOT NULL                                 /* PartijID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   IndAutoFiat                   Boolean                       NOT NULL                                 /* JaNee */,
   CONSTRAINT pk_His_PartijBijhouding PRIMARY KEY (ID),
   CONSTRAINT uc_His_PartijBijhouding_Partij_TsReg UNIQUE (Partij, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PartijBijhouding OWNED BY Kern.His_PartijBijhouding.ID;

CREATE SEQUENCE Kern.seq_His_PartijVrijBer START WITH 1;
CREATE TABLE Kern.His_PartijVrijBer (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PartijVrijBer')   /* His_PartijVrijBerID */,
   Partij                        Smallint                      NOT NULL                                 /* PartijID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   OndertekenaarVrijBer          Smallint                                                               /* PartijID */,
   TransporteurVrijBer           Smallint                                                               /* PartijID */,
   DatIngangVrijBer              Integer                       NOT NULL  CHECK (to_char(to_date(DatIngangVrijBer::text, 'yyyymmdd'), 'yyyymmdd')=DatIngangVrijBer::text)   /* Dat */,
   DatEindeVrijBer               Integer                                 CHECK (to_char(to_date(DatEindeVrijBer::text, 'yyyymmdd'), 'yyyymmdd')=DatEindeVrijBer::text)   /* Dat */,
   AfleverpuntVrijBer            Varchar(200)                            CHECK (AfleverpuntVrijBer <> '' )   /* Uri */,
   IndBlokVrijBer                Boolean                                 CHECK (IndBlokVrijBer IN (true))   /* Ja */,
   CONSTRAINT pk_His_PartijVrijBer PRIMARY KEY (ID),
   CONSTRAINT uc_His_PartijVrijBer_Partij_TsReg UNIQUE (Partij, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PartijVrijBer OWNED BY Kern.His_PartijVrijBer.ID;

CREATE SEQUENCE Kern.seq_His_PartijRol START WITH 1;
CREATE TABLE Kern.His_PartijRol (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PartijRol')   /* His_PartijRolID */,
   PartijRol                     Integer                       NOT NULL                                 /* PartijRolID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   DatIngang                     Integer                       NOT NULL  CHECK (to_char(to_date(DatIngang::text, 'yyyymmdd'), 'yyyymmdd')=DatIngang::text)   /* Dat */,
   DatEinde                      Integer                                 CHECK (to_char(to_date(DatEinde::text, 'yyyymmdd'), 'yyyymmdd')=DatEinde::text)   /* Dat */,
   CONSTRAINT pk_His_PartijRol PRIMARY KEY (ID),
   CONSTRAINT uc_His_PartijRol_PartijRol_TsReg UNIQUE (PartijRol, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PartijRol OWNED BY Kern.His_PartijRol.ID;

CREATE TABLE Kern.Historievorm (
   ID                            Smallint                      NOT NULL                                 /* HistorievormID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   CONSTRAINT pk_Historievorm PRIMARY KEY (ID),
   CONSTRAINT uc_Historievorm_Naam UNIQUE (Naam)
);

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
   Code                          Char(4)                       NOT NULL  CHECK (Code ~ '^[0-9]+$' AND Length(Code) >= 4)   /* LandGebiedCode */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   ISO31661Alpha2                Varchar(2)                              CHECK (ISO31661Alpha2 <> '' )   /* ISO31661Alpha2 */,
   DatAanvGel                    Integer                                 CHECK (to_char(to_date(DatAanvGel::text, 'yyyymmdd'), 'yyyymmdd')=DatAanvGel::text)   /* Dat */,
   DatEindeGel                   Integer                                 CHECK (to_char(to_date(DatEindeGel::text, 'yyyymmdd'), 'yyyymmdd')=DatEindeGel::text)   /* Dat */,
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
   Code                          Char(4)                       NOT NULL  CHECK (Code ~ '^[0-9]+$' AND Length(Code) >= 4)   /* NationCode */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   DatAanvGel                    Integer                                 CHECK (to_char(to_date(DatAanvGel::text, 'yyyymmdd'), 'yyyymmdd')=DatAanvGel::text)   /* Dat */,
   DatEindeGel                   Integer                                 CHECK (to_char(to_date(DatEindeGel::text, 'yyyymmdd'), 'yyyymmdd')=DatEindeGel::text)   /* Dat */,
   CONSTRAINT pk_Nation PRIMARY KEY (ID),
   CONSTRAINT uc_Nation_Code UNIQUE (Code)
);
ALTER SEQUENCE Kern.seq_Nation OWNED BY Kern.Nation.ID;

CREATE SEQUENCE Kern.seq_Partij START WITH 1;
CREATE TABLE Kern.Partij (
   ID                            Smallint                      NOT NULL  DEFAULT nextval('Kern.seq_Partij')   /* PartijID */,
   Code                          Char(6)                       NOT NULL  CHECK (Code ~ '^[0-9]+$' AND Length(Code) >= 6)   /* PartijCode */,
   Naam                          Varchar(80)                             CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   DatIngang                     Integer                                 CHECK (to_char(to_date(DatIngang::text, 'yyyymmdd'), 'yyyymmdd')=DatIngang::text)   /* Dat */,
   DatEinde                      Integer                                 CHECK (to_char(to_date(DatEinde::text, 'yyyymmdd'), 'yyyymmdd')=DatEinde::text)   /* Dat */,
   OIN                           Varchar(40)                             CHECK (OIN <> '' )             /* OIN */,
   Srt                           Smallint                                                               /* SrtPartijID */,
   IndVerstrbeperkingMogelijk    Boolean                                                                /* JaNee */,
   DatOvergangNaarBRP            Integer                                 CHECK (to_char(to_date(DatOvergangNaarBRP::text, 'yyyymmdd'), 'yyyymmdd')=DatOvergangNaarBRP::text)   /* Dat */,
   IndAG                         Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   IndAutoFiat                   Boolean                                                                /* JaNee */,
   IndAGBijhouding               Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   OndertekenaarVrijBer          Smallint                                                               /* PartijID */,
   TransporteurVrijBer           Smallint                                                               /* PartijID */,
   DatIngangVrijBer              Integer                                 CHECK (to_char(to_date(DatIngangVrijBer::text, 'yyyymmdd'), 'yyyymmdd')=DatIngangVrijBer::text)   /* Dat */,
   DatEindeVrijBer               Integer                                 CHECK (to_char(to_date(DatEindeVrijBer::text, 'yyyymmdd'), 'yyyymmdd')=DatEindeVrijBer::text)   /* Dat */,
   AfleverpuntVrijBer            Varchar(200)                            CHECK (AfleverpuntVrijBer <> '' )   /* Uri */,
   IndBlokVrijBer                Boolean                                 CHECK (IndBlokVrijBer IN (true))   /* Ja */,
   IndAGVrijBer                  Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   CONSTRAINT pk_Partij PRIMARY KEY (ID),
   CONSTRAINT uc_Partij_Code UNIQUE (Code)
);
ALTER SEQUENCE Kern.seq_Partij OWNED BY Kern.Partij.ID;

CREATE SEQUENCE Kern.seq_PartijRol START WITH 1;
CREATE TABLE Kern.PartijRol (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_PartijRol')   /* PartijRolID */,
   Partij                        Smallint                      NOT NULL                                 /* PartijID */,
   Rol                           Smallint                      NOT NULL                                 /* RolID */,
   DatIngang                     Integer                                 CHECK (to_char(to_date(DatIngang::text, 'yyyymmdd'), 'yyyymmdd')=DatIngang::text)   /* Dat */,
   DatEinde                      Integer                                 CHECK (to_char(to_date(DatEinde::text, 'yyyymmdd'), 'yyyymmdd')=DatEinde::text)   /* Dat */,
   IndAG                         Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   CONSTRAINT pk_PartijRol PRIMARY KEY (ID),
   CONSTRAINT uc_PartijRol_Partij_Rol UNIQUE (Partij, Rol)
);
ALTER SEQUENCE Kern.seq_PartijRol OWNED BY Kern.PartijRol.ID;

CREATE SEQUENCE Kern.seq_Plaats START WITH 1;
CREATE TABLE Kern.Plaats (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_Plaats')   /* PlaatsID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   CONSTRAINT pk_Plaats PRIMARY KEY (ID),
   CONSTRAINT uc_Plaats_Naam UNIQUE (Naam)
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
   Code                          Char(3)                       NOT NULL  CHECK (Code ~ '^[0-9]+$' AND Length(Code) >= 3)   /* RechtsgrondCode */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   IndLeidtTotStrijdigheid       Boolean                                 CHECK (IndLeidtTotStrijdigheid IN (true))   /* Ja */,
   DatAanvGel                    Integer                                 CHECK (to_char(to_date(DatAanvGel::text, 'yyyymmdd'), 'yyyymmdd')=DatAanvGel::text)   /* Dat */,
   DatEindeGel                   Integer                                 CHECK (to_char(to_date(DatEindeGel::text, 'yyyymmdd'), 'yyyymmdd')=DatEindeGel::text)   /* Dat */,
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
   Code                          Char(3)                       NOT NULL  CHECK (Code ~ '^[0-9]+$' AND Length(Code) >= 3)   /* RdnVerkCode */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   DatAanvGel                    Integer                                 CHECK (to_char(to_date(DatAanvGel::text, 'yyyymmdd'), 'yyyymmdd')=DatAanvGel::text)   /* Dat */,
   DatEindeGel                   Integer                                 CHECK (to_char(to_date(DatEindeGel::text, 'yyyymmdd'), 'yyyymmdd')=DatEindeGel::text)   /* Dat */,
   CONSTRAINT pk_RdnVerkNLNation PRIMARY KEY (ID),
   CONSTRAINT uc_RdnVerkNLNation_Code UNIQUE (Code),
   CONSTRAINT uc_RdnVerkNLNation_Oms UNIQUE (Oms)
);
ALTER SEQUENCE Kern.seq_RdnVerkNLNation OWNED BY Kern.RdnVerkNLNation.ID;

CREATE SEQUENCE Kern.seq_RdnVerliesNLNation START WITH 1;
CREATE TABLE Kern.RdnVerliesNLNation (
   ID                            Smallint                      NOT NULL  DEFAULT nextval('Kern.seq_RdnVerliesNLNation')   /* RdnVerliesNLNationID */,
   Code                          Char(3)                       NOT NULL  CHECK (Code ~ '^[0-9]+$' AND Length(Code) >= 3)   /* RdnVerliesCode */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   DatAanvGel                    Integer                                 CHECK (to_char(to_date(DatAanvGel::text, 'yyyymmdd'), 'yyyymmdd')=DatAanvGel::text)   /* Dat */,
   DatEindeGel                   Integer                                 CHECK (to_char(to_date(DatEindeGel::text, 'yyyymmdd'), 'yyyymmdd')=DatEindeGel::text)   /* Dat */,
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
   Code                          Varchar(10)                   NOT NULL  CHECK (Code <> '' )            /* RegelCode */,
   SrtMelding                    Smallint                                                               /* SrtMeldingID */,
   Melding                       Varchar(200)                            CHECK (Melding <> '' )         /* Meldingtekst */,
   DatAanvGel                    Integer                                 CHECK (to_char(to_date(DatAanvGel::text, 'yyyymmdd'), 'yyyymmdd')=DatAanvGel::text)   /* Dat */,
   DatEindeGel                   Integer                                 CHECK (to_char(to_date(DatEindeGel::text, 'yyyymmdd'), 'yyyymmdd')=DatEindeGel::text)   /* Dat */,
   CONSTRAINT pk_Regel PRIMARY KEY (ID),
   CONSTRAINT uc_Regel_Code UNIQUE (Code)
);

CREATE TABLE Kern.Richting (
   ID                            Smallint                      NOT NULL                                 /* RichtingID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   CONSTRAINT pk_Richting PRIMARY KEY (ID),
   CONSTRAINT uc_Richting_Naam UNIQUE (Naam)
);

CREATE TABLE Kern.Rol (
   ID                            Smallint                      NOT NULL                                 /* RolID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   DatAanvGel                    Integer                                 CHECK (to_char(to_date(DatAanvGel::text, 'yyyymmdd'), 'yyyymmdd')=DatAanvGel::text)   /* Dat */,
   DatEindeGel                   Integer                                 CHECK (to_char(to_date(DatEindeGel::text, 'yyyymmdd'), 'yyyymmdd')=DatEindeGel::text)   /* Dat */,
   CONSTRAINT pk_Rol PRIMARY KEY (ID),
   CONSTRAINT uc_Rol_Naam UNIQUE (Naam)
);

CREATE SEQUENCE Kern.seq_SrtNLReisdoc START WITH 1;
CREATE TABLE Kern.SrtNLReisdoc (
   ID                            Smallint                      NOT NULL  DEFAULT nextval('Kern.seq_SrtNLReisdoc')   /* SrtNLReisdocID */,
   Code                          Varchar(2)                    NOT NULL  CHECK (Code <> '' )            /* SrtNLReisdocCode */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   DatAanvGel                    Integer                                 CHECK (to_char(to_date(DatAanvGel::text, 'yyyymmdd'), 'yyyymmdd')=DatAanvGel::text)   /* Dat */,
   DatEindeGel                   Integer                                 CHECK (to_char(to_date(DatEindeGel::text, 'yyyymmdd'), 'yyyymmdd')=DatEindeGel::text)   /* Dat */,
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

CREATE SEQUENCE Kern.seq_SrtActieBrongebruik START WITH 1;
CREATE TABLE Kern.SrtActieBrongebruik (
   ID                            Smallint                      NOT NULL  DEFAULT nextval('Kern.seq_SrtActieBrongebruik')   /* SrtActieBrongebruikID */,
   SrtActie                      Smallint                      NOT NULL                                 /* SrtActieID */,
   SrtAdmHnd                     Smallint                      NOT NULL                                 /* SrtAdmHndID */,
   SrtDoc                        Smallint                      NOT NULL                                 /* SrtDocID */,
   DatAanvGel                    Integer                                 CHECK (to_char(to_date(DatAanvGel::text, 'yyyymmdd'), 'yyyymmdd')=DatAanvGel::text)   /* Dat */,
   DatEindeGel                   Integer                                 CHECK (to_char(to_date(DatEindeGel::text, 'yyyymmdd'), 'yyyymmdd')=DatEindeGel::text)   /* Dat */,
   CONSTRAINT pk_SrtActieBrongebruik PRIMARY KEY (ID),
   CONSTRAINT uc_SrtActieBrongebruik_SrtActie_SrtAdmHnd_SrtDoc UNIQUE (SrtActie, SrtAdmHnd, SrtDoc)
);
ALTER SEQUENCE Kern.seq_SrtActieBrongebruik OWNED BY Kern.SrtActieBrongebruik.ID;

CREATE TABLE Kern.SrtAdmHnd (
   ID                            Smallint                      NOT NULL                                 /* SrtAdmHndID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   CategorieAdmHnd               Smallint                                                               /* CategorieAdmHndID */,
   Koppelvlak                    Smallint                      NOT NULL                                 /* KoppelvlakID */,
   Module                        Smallint                                                               /* IDModule */,
   Alias                         Smallint                                                               /* SrtAdmHndID */,
   CONSTRAINT pk_SrtAdmHnd PRIMARY KEY (ID),
   CONSTRAINT uc_SrtAdmHnd_Naam UNIQUE (Naam)
);

CREATE TABLE Kern.SrtAdres (
   ID                            Smallint                      NOT NULL                                 /* SrtAdresID */,
   Code                          Varchar(1)                    NOT NULL  CHECK (Code <> '' )            /* SrtAdresCode */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   CONSTRAINT pk_SrtAdres PRIMARY KEY (ID),
   CONSTRAINT uc_SrtAdres_Code UNIQUE (Code),
   CONSTRAINT uc_SrtAdres_Naam UNIQUE (Naam)
);

CREATE TABLE Kern.SrtBer (
   ID                            Smallint                      NOT NULL                                 /* SrtBerID */,
   Identifier                    Varchar(80)                   NOT NULL  CHECK (Identifier <> '' )      /* IdentifierLang */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Module                        Smallint                                                               /* IDModule */,
   Koppelvlak                    Smallint                      NOT NULL                                 /* KoppelvlakID */,
   DatAanvGel                    Integer                                 CHECK (to_char(to_date(DatAanvGel::text, 'yyyymmdd'), 'yyyymmdd')=DatAanvGel::text)   /* Dat */,
   DatEindeGel                   Integer                                 CHECK (to_char(to_date(DatEindeGel::text, 'yyyymmdd'), 'yyyymmdd')=DatEindeGel::text)   /* Dat */,
   CONSTRAINT pk_SrtBer PRIMARY KEY (ID),
   CONSTRAINT uc_SrtBer_Identifier UNIQUE (Identifier)
);

CREATE SEQUENCE Kern.seq_SrtBerElement START WITH 1;
CREATE TABLE Kern.SrtBerElement (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_SrtBerElement')   /* SrtBerElementID */,
   SrtBer                        Smallint                      NOT NULL                                 /* SrtBerID */,
   Element                       Integer                       NOT NULL                                 /* ElementID */,
   CONSTRAINT pk_SrtBerElement PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_SrtBerElement OWNED BY Kern.SrtBerElement.ID;

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
   Registersrt                   Char(1)                                 CHECK (Registersrt ~ '^[0-9]+$')   /* Registersrt */,
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

CREATE TABLE Kern.SrtMelding (
   ID                            Smallint                      NOT NULL                                 /* SrtMeldingID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   Niveau                        Integer                       NOT NULL                                 /* Volgnr */,
   CONSTRAINT pk_SrtMelding PRIMARY KEY (ID),
   CONSTRAINT uc_SrtMelding_Naam UNIQUE (Naam)
);

CREATE TABLE Kern.SrtMigratie (
   ID                            Smallint                      NOT NULL                                 /* SrtMigratieID */,
   Code                          Varchar(1)                    NOT NULL  CHECK (Code <> '' )            /* SrtMigratieCode */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   CONSTRAINT pk_SrtMigratie PRIMARY KEY (ID),
   CONSTRAINT uc_SrtMigratie_Code UNIQUE (Code),
   CONSTRAINT uc_SrtMigratie_Naam UNIQUE (Naam)
);

CREATE SEQUENCE Kern.seq_SrtPartij START WITH 1;
CREATE TABLE Kern.SrtPartij (
   ID                            Smallint                      NOT NULL  DEFAULT nextval('Kern.seq_SrtPartij')   /* SrtPartijID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   DatAanvGel                    Integer                                 CHECK (to_char(to_date(DatAanvGel::text, 'yyyymmdd'), 'yyyymmdd')=DatAanvGel::text)   /* Dat */,
   DatEindeGel                   Integer                                 CHECK (to_char(to_date(DatEindeGel::text, 'yyyymmdd'), 'yyyymmdd')=DatEindeGel::text)   /* Dat */,
   CONSTRAINT pk_SrtPartij PRIMARY KEY (ID),
   CONSTRAINT uc_SrtPartij_Naam UNIQUE (Naam)
);
ALTER SEQUENCE Kern.seq_SrtPartij OWNED BY Kern.SrtPartij.ID;

CREATE TABLE Kern.SrtPers (
   ID                            Smallint                      NOT NULL                                 /* SrtPersID */,
   Code                          Varchar(1)                    NOT NULL  CHECK (Code <> '' )            /* SrtPersCode */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   DatAanvGel                    Integer                                 CHECK (to_char(to_date(DatAanvGel::text, 'yyyymmdd'), 'yyyymmdd')=DatAanvGel::text)   /* Dat */,
   DatEindeGel                   Integer                                 CHECK (to_char(to_date(DatEindeGel::text, 'yyyymmdd'), 'yyyymmdd')=DatEindeGel::text)   /* Dat */,
   CONSTRAINT pk_SrtPers PRIMARY KEY (ID),
   CONSTRAINT uc_SrtPers_Code UNIQUE (Code),
   CONSTRAINT uc_SrtPers_Naam UNIQUE (Naam)
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

CREATE TABLE Kern.SrtSynchronisatie (
   ID                            Smallint                      NOT NULL                                 /* SrtSynchronisatieID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   CONSTRAINT pk_SrtSynchronisatie PRIMARY KEY (ID),
   CONSTRAINT uc_SrtSynchronisatie_Naam UNIQUE (Naam)
);

CREATE SEQUENCE Kern.seq_SrtVrijBer START WITH 1;
CREATE TABLE Kern.SrtVrijBer (
   ID                            Smallint                      NOT NULL  DEFAULT nextval('Kern.seq_SrtVrijBer')   /* SrtVrijBerID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   DatAanvGel                    Integer                                 CHECK (to_char(to_date(DatAanvGel::text, 'yyyymmdd'), 'yyyymmdd')=DatAanvGel::text)   /* Dat */,
   DatEindeGel                   Integer                                 CHECK (to_char(to_date(DatEindeGel::text, 'yyyymmdd'), 'yyyymmdd')=DatEindeGel::text)   /* Dat */,
   CONSTRAINT pk_SrtVrijBer PRIMARY KEY (ID),
   CONSTRAINT uc_SrtVrijBer_Naam UNIQUE (Naam)
);
ALTER SEQUENCE Kern.seq_SrtVrijBer OWNED BY Kern.SrtVrijBer.ID;

CREATE TABLE Kern.StatusLevAdmHnd (
   ID                            Smallint                      NOT NULL                                 /* StatusLevAdmHndID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   CONSTRAINT pk_StatusLevAdmHnd PRIMARY KEY (ID),
   CONSTRAINT uc_StatusLevAdmHnd_Naam UNIQUE (Naam)
);

CREATE TABLE Kern.StatusOnderzoek (
   ID                            Smallint                      NOT NULL                                 /* StatusOnderzoekID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   CONSTRAINT pk_StatusOnderzoek PRIMARY KEY (ID),
   CONSTRAINT uc_StatusOnderzoek_Naam UNIQUE (Naam)
);

CREATE TABLE Kern.Stelsel (
   ID                            Smallint                      NOT NULL                                 /* StelselID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   CONSTRAINT pk_Stelsel PRIMARY KEY (ID),
   CONSTRAINT uc_Stelsel_Naam UNIQUE (Naam)
);

CREATE TABLE Kern.Verantwoording (
   ID                            Smallint                      NOT NULL                                 /* VerantwoordingID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   CONSTRAINT pk_Verantwoording PRIMARY KEY (ID),
   CONSTRAINT uc_Verantwoording_Naam UNIQUE (Naam)
);

CREATE SEQUENCE Kern.seq_VersieStufBG START WITH 1;
CREATE TABLE Kern.VersieStufBG (
   ID                            Smallint                      NOT NULL  DEFAULT nextval('Kern.seq_VersieStufBG')   /* VersieStufBGID */,
   Nr                            Varchar(4)                    NOT NULL  CHECK (Nr <> '' )              /* VersieStufBGNr */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   DatAanvGel                    Integer                                 CHECK (to_char(to_date(DatAanvGel::text, 'yyyymmdd'), 'yyyymmdd')=DatAanvGel::text)   /* Dat */,
   DatEindeGel                   Integer                                 CHECK (to_char(to_date(DatEindeGel::text, 'yyyymmdd'), 'yyyymmdd')=DatEindeGel::text)   /* Dat */,
   CONSTRAINT pk_VersieStufBG PRIMARY KEY (ID),
   CONSTRAINT uc_VersieStufBG_Nr UNIQUE (Nr)
);
ALTER SEQUENCE Kern.seq_VersieStufBG OWNED BY Kern.VersieStufBG.ID;

CREATE SEQUENCE Kern.seq_VertalingBersrtBRP START WITH 1;
CREATE TABLE Kern.VertalingBersrtBRP (
   ID                            Smallint                      NOT NULL  DEFAULT nextval('Kern.seq_VertalingBersrtBRP')   /* VertalingBersrtBRPID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   DatAanvGel                    Integer                                 CHECK (to_char(to_date(DatAanvGel::text, 'yyyymmdd'), 'yyyymmdd')=DatAanvGel::text)   /* Dat */,
   DatEindeGel                   Integer                                 CHECK (to_char(to_date(DatEindeGel::text, 'yyyymmdd'), 'yyyymmdd')=DatEindeGel::text)   /* Dat */,
   CONSTRAINT pk_VertalingBersrtBRP PRIMARY KEY (ID),
   CONSTRAINT uc_VertalingBersrtBRP_Naam UNIQUE (Naam)
);
ALTER SEQUENCE Kern.seq_VertalingBersrtBRP OWNED BY Kern.VertalingBersrtBRP.ID;

CREATE TABLE Kern.Verwerkingsresultaat (
   ID                            Smallint                      NOT NULL                                 /* VerwerkingsresultaatID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   CONSTRAINT pk_Verwerkingsresultaat PRIMARY KEY (ID),
   CONSTRAINT uc_Verwerkingsresultaat_Naam UNIQUE (Naam)
);

CREATE TABLE Kern.Verwerkingswijze (
   ID                            Smallint                      NOT NULL                                 /* VerwerkingswijzeID */,
   Naam                          Varchar(80)                   NOT NULL  CHECK (Naam <> '' )            /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL  CHECK (Oms <> '' )             /* OmsEnumeratiewaarde */,
   CONSTRAINT pk_Verwerkingswijze PRIMARY KEY (ID),
   CONSTRAINT uc_Verwerkingswijze_Naam UNIQUE (Naam)
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
   ID                            Bigint                        NOT NULL  DEFAULT nextval('AutAut.seq_PersAfnemerindicatie')   /* PersAfnemerindicatieID */,
   Pers                          Bigint                        NOT NULL                                 /* PersID */,
   Partij                        Smallint                      NOT NULL                                 /* PartijID */,
   Levsautorisatie               Integer                       NOT NULL                                 /* LevsautorisatieID */,
   DatAanvMaterielePeriode       Integer                                 CHECK (to_char(to_date(DatAanvMaterielePeriode::text, 'yyyymmdd'), 'yyyymmdd')=DatAanvMaterielePeriode::text)   /* Dat */,
   DatEindeVolgen                Integer                                 CHECK (to_char(to_date(DatEindeVolgen::text, 'yyyymmdd'), 'yyyymmdd')=DatEindeVolgen::text)   /* Dat */,
   IndAG                         Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   CONSTRAINT pk_PersAfnemerindicatie PRIMARY KEY (ID),
   CONSTRAINT uc_PersAfnemerindicatie_Pers_Partij_Levsautorisatie UNIQUE (Pers, Partij, Levsautorisatie)
);
ALTER SEQUENCE AutAut.seq_PersAfnemerindicatie OWNED BY AutAut.PersAfnemerindicatie.ID;

CREATE SEQUENCE Beh.seq_VrijBer START WITH 1;
CREATE TABLE Beh.VrijBer (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Beh.seq_VrijBer')   /* VrijBerID */,
   SrtBer                        Smallint                      NOT NULL                                 /* SrtBerVrijBerID */,
   Srt                           Smallint                      NOT NULL                                 /* SrtVrijBerID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   Data                          Varchar(99999)                NOT NULL  CHECK (Data <> '' )            /* VrijBerData */,
   IndGelezen                    Boolean                                                                /* JaNee */,
   CONSTRAINT pk_VrijBer PRIMARY KEY (ID)
);
ALTER SEQUENCE Beh.seq_VrijBer OWNED BY Beh.VrijBer.ID;

CREATE SEQUENCE Beh.seq_VrijBerPartij START WITH 1;
CREATE TABLE Beh.VrijBerPartij (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Beh.seq_VrijBerPartij')   /* VrijBerPartijID */,
   VrijBer                       Bigint                        NOT NULL                                 /* VrijBerID */,
   Partij                        Smallint                      NOT NULL                                 /* PartijID */,
   CONSTRAINT pk_VrijBerPartij PRIMARY KEY (ID),
   CONSTRAINT uc_VrijBerPartij_VrijBer_Partij UNIQUE (VrijBer, Partij)
);
ALTER SEQUENCE Beh.seq_VrijBerPartij OWNED BY Beh.VrijBerPartij.ID;

CREATE SEQUENCE IST.seq_Stapel START WITH 1;
CREATE TABLE IST.Stapel (
   ID                            Integer                       NOT NULL  DEFAULT nextval('IST.seq_Stapel')   /* StapelID */,
   Pers                          Bigint                        NOT NULL                                 /* PersID */,
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
   Relatie                       Bigint                        NOT NULL                                 /* RelatieID */,
   CONSTRAINT pk_StapelRelatie PRIMARY KEY (ID),
   CONSTRAINT uc_StapelRelatie_Stapel_Relatie UNIQUE (Stapel, Relatie)
);
ALTER SEQUENCE IST.seq_StapelRelatie OWNED BY IST.StapelRelatie.ID;

CREATE SEQUENCE IST.seq_StapelVoorkomen START WITH 1;
CREATE TABLE IST.StapelVoorkomen (
   ID                            Integer                       NOT NULL  DEFAULT nextval('IST.seq_StapelVoorkomen')   /* StapelVoorkomenID */,
   Stapel                        Integer                       NOT NULL                                 /* StapelID */,
   Volgnr                        Integer                       NOT NULL                                 /* Volgnr */,
   AdmHnd                        Bigint                        NOT NULL                                 /* AdmHndID */,
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
   ANr                           Char(10)                                CHECK (ANr ~ '^[0-9]+$' AND Length(ANr) >= 10)   /* ANr */,
   BSN                           Char(9)                                 CHECK (BSN ~ '^[0-9]+$' AND Length(BSN) >= 9)   /* BSN */,
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
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_Actie')   /* ActieID */,
   Srt                           Smallint                      NOT NULL                                 /* SrtActieID */,
   AdmHnd                        Bigint                        NOT NULL                                 /* AdmHndID */,
   Partij                        Smallint                      NOT NULL                                 /* PartijID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   DatOntlening                  Integer                                                                /* DatEvtDeelsOnbekend */,
   CONSTRAINT pk_Actie PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_Actie OWNED BY Kern.Actie.ID;

CREATE SEQUENCE Kern.seq_ActieBron START WITH 1;
CREATE TABLE Kern.ActieBron (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_ActieBron')   /* ActieBronID */,
   Actie                         Bigint                        NOT NULL                                 /* ActieID */,
   Doc                           Bigint                                                                 /* DocID */,
   Rechtsgrond                   Smallint                                                               /* RechtsgrondID */,
   Rechtsgrondoms                Varchar(250)                            CHECK (Rechtsgrondoms <> '' )   /* OmsEnumeratiewaarde */,
   CONSTRAINT pk_ActieBron PRIMARY KEY (ID),
   CONSTRAINT uc_ActieBron_Actie_Doc_Rechtsgrond_Rechtsgrondoms UNIQUE (Actie, Doc, Rechtsgrond, Rechtsgrondoms)
);
ALTER SEQUENCE Kern.seq_ActieBron OWNED BY Kern.ActieBron.ID;

CREATE SEQUENCE Kern.seq_AdmHnd START WITH 1;
CREATE TABLE Kern.AdmHnd (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_AdmHnd')   /* AdmHndID */,
   Srt                           Smallint                      NOT NULL                                 /* SrtAdmHndID */,
   Partij                        Smallint                      NOT NULL                                 /* PartijID */,
   ToelichtingOntlening          Varchar(400)                            CHECK (ToelichtingOntlening <> '' )   /* Ontleningstoelichting */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsLev                         Timestamp with time zone                                               /* Ts */,
   StatusLev                     Smallint                      NOT NULL                                 /* StatusLevAdmHndID */,
   CONSTRAINT pk_AdmHnd PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_AdmHnd OWNED BY Kern.AdmHnd.ID;

CREATE SEQUENCE Kern.seq_AdmHndGedeblokkeerdeRegel START WITH 1;
CREATE TABLE Kern.AdmHndGedeblokkeerdeRegel (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_AdmHndGedeblokkeerdeRegel')   /* AdmHndGedeblokkeerdeMeldingI */,
   AdmHnd                        Bigint                        NOT NULL                                 /* AdmHndID */,
   Regel                         Integer                       NOT NULL                                 /* RegelID */,
   CONSTRAINT pk_AdmHndGedeblokkeerdeRegel PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_AdmHndGedeblokkeerdeRegel OWNED BY Kern.AdmHndGedeblokkeerdeRegel.ID;

CREATE SEQUENCE Kern.seq_Betr START WITH 1;
CREATE TABLE Kern.Betr (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_Betr')   /* BetrID */,
   Relatie                       Bigint                        NOT NULL                                 /* RelatieID */,
   Rol                           Smallint                      NOT NULL                                 /* SrtBetrID */,
   Pers                          Bigint                                                                 /* PersID */,
   IndAG                         Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   IndOuderUitWieKindIsGeboren   Boolean                                                                /* JaNee */,
   IndAGOuderschap               Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   IndOuderHeeftGezag            Boolean                                                                /* JaNee */,
   IndAGOuderlijkGezag           Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   CONSTRAINT pk_Betr PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_Betr OWNED BY Kern.Betr.ID;

CREATE SEQUENCE Kern.seq_Doc START WITH 1;
CREATE TABLE Kern.Doc (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_Doc')   /* DocID */,
   Srt                           Smallint                      NOT NULL                                 /* SrtDocID */,
   Aktenr                        Varchar(7)                              CHECK (Aktenr <> '' )          /* Aktenr */,
   Oms                           Varchar(40)                             CHECK (Oms <> '' )             /* DocOms */,
   Partij                        Smallint                      NOT NULL                                 /* PartijID */,
   CONSTRAINT pk_Doc PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_Doc OWNED BY Kern.Doc.ID;

CREATE SEQUENCE Kern.seq_GegevenInOnderzoek START WITH 1;
CREATE TABLE Kern.GegevenInOnderzoek (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_GegevenInOnderzoek')   /* GegevenInOnderzoekID */,
   Onderzoek                     Integer                       NOT NULL                                 /* OnderzoekID */,
   Element                       Integer                       NOT NULL                                 /* ElementID */,
   ObjectSleutelGegeven          Bigint                                                                 /* Sleutelwaarde */,
   VoorkomenSleutelGegeven       Bigint                                                                 /* Sleutelwaarde */,
   IndAG                         Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   CONSTRAINT pk_GegevenInOnderzoek PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_GegevenInOnderzoek OWNED BY Kern.GegevenInOnderzoek.ID;

CREATE SEQUENCE Kern.seq_Onderzoek START WITH 1;
CREATE TABLE Kern.Onderzoek (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_Onderzoek')   /* OnderzoekID */,
   Pers                          Bigint                        NOT NULL                                 /* PersID */,
   Partij                        Smallint                      NOT NULL                                 /* PartijID */,
   DatAanv                       Integer                                                                /* DatEvtDeelsOnbekend */,
   DatEinde                      Integer                                                                /* DatEvtDeelsOnbekend */,
   Oms                           Text                                                                   /* OnderzoekOms */,
   Status                        Smallint                                                               /* StatusOnderzoekID */,
   IndAG                         Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   CONSTRAINT pk_Onderzoek PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_Onderzoek OWNED BY Kern.Onderzoek.ID;

CREATE SEQUENCE Kern.seq_Pers START WITH 1;
CREATE TABLE Kern.Pers (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_Pers')   /* PersID */,
   Srt                           Smallint                      NOT NULL                                 /* SrtPersID */,
   LockVersie                    Bigint                        NOT NULL  DEFAULT 0                      /* Versienr */,
   AdmHnd                        Bigint                                                                 /* AdmHndID */,
   TsLaatsteWijz                 Timestamp with time zone                                               /* Ts */,
   Sorteervolgorde               Smallint                                                               /* Sorteervolgorde */,
   TsLaatsteWijzGBASystematiek   Timestamp with time zone                                               /* Ts */,
   IndAGAfgeleidAdministratief   Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   BSN                           Char(9)                                 CHECK (BSN ~ '^[0-9]+$' AND Length(BSN) >= 9)   /* BSN */,
   ANr                           Char(10)                                CHECK (ANr ~ '^[0-9]+$' AND Length(ANr) >= 10)   /* ANr */,
   IndAGIDs                      Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   IndAfgeleid                   Boolean                                                                /* JaNee */,
   IndNreeks                     Boolean                                                                /* JaNee */,
   Predicaat                     Smallint                                                               /* PredicaatID */,
   Voornamen                     Varchar(200)                            CHECK (Voornamen <> '' )       /* Voornamen */,
   AdellijkeTitel                Smallint                                                               /* AdellijkeTitelID */,
   Voorvoegsel                   Varchar(10)                             CHECK (Voorvoegsel <> '' )     /* Voorvoegsel */,
   Scheidingsteken               Varchar(1)                              CHECK (Scheidingsteken <> '' )   /* Scheidingsteken */,
   Geslnaamstam                  Varchar(200)                            CHECK (Geslnaamstam <> '' )    /* Geslnaamstam */,
   IndAGSamengesteldeNaam        Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   DatGeboorte                   Integer                                                                /* DatEvtDeelsOnbekend */,
   GemGeboorte                   Smallint                                                               /* GemID */,
   WplnaamGeboorte               Varchar(80)                             CHECK (WplnaamGeboorte <> '' )   /* NaamEnumeratiewaarde */,
   BLPlaatsGeboorte              Varchar(40)                             CHECK (BLPlaatsGeboorte <> '' )   /* BLPlaats */,
   BLRegioGeboorte               Varchar(35)                             CHECK (BLRegioGeboorte <> '' )   /* BLRegio */,
   OmsLocGeboorte                Varchar(40)                             CHECK (OmsLocGeboorte <> '' )   /* Locoms */,
   LandGebiedGeboorte            Integer                                                                /* LandGebiedID */,
   IndAGGeboorte                 Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   Geslachtsaand                 Smallint                                                               /* GeslachtsaandID */,
   IndAGGeslachtsaand            Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   DatInschr                     Integer                                                                /* DatEvtDeelsOnbekend */,
   Versienr                      Bigint                                                                 /* Versienr */,
   Dattijdstempel                Timestamp with time zone                                               /* Ts */,
   IndAGInschr                   Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   VorigeBSN                     Char(9)                                 CHECK (VorigeBSN ~ '^[0-9]+$' AND Length(VorigeBSN) >= 9)   /* BSN */,
   VolgendeBSN                   Char(9)                                 CHECK (VolgendeBSN ~ '^[0-9]+$' AND Length(VolgendeBSN) >= 9)   /* BSN */,
   VorigeANr                     Char(10)                                CHECK (VorigeANr ~ '^[0-9]+$' AND Length(VorigeANr) >= 10)   /* ANr */,
   VolgendeANr                   Char(10)                                CHECK (VolgendeANr ~ '^[0-9]+$' AND Length(VolgendeANr) >= 10)   /* ANr */,
   IndAGNrverwijzing             Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   Bijhpartij                    Smallint                                                               /* PartijID */,
   Bijhaard                      Smallint                                                               /* BijhaardID */,
   NadereBijhaard                Smallint                                                               /* NadereBijhaardID */,
   IndAGBijhouding               Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   DatOverlijden                 Integer                                                                /* DatEvtDeelsOnbekend */,
   GemOverlijden                 Smallint                                                               /* GemID */,
   WplnaamOverlijden             Varchar(80)                             CHECK (WplnaamOverlijden <> '' )   /* NaamEnumeratiewaarde */,
   BLPlaatsOverlijden            Varchar(40)                             CHECK (BLPlaatsOverlijden <> '' )   /* BLPlaats */,
   BLRegioOverlijden             Varchar(35)                             CHECK (BLRegioOverlijden <> '' )   /* BLRegio */,
   OmsLocOverlijden              Varchar(40)                             CHECK (OmsLocOverlijden <> '' )   /* Locoms */,
   LandGebiedOverlijden          Integer                                                                /* LandGebiedID */,
   IndAGOverlijden               Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   Naamgebruik                   Smallint                                                               /* NaamgebruikID */,
   IndNaamgebruikAfgeleid        Boolean                                                                /* JaNee */,
   PredicaatNaamgebruik          Smallint                                                               /* PredicaatID */,
   VoornamenNaamgebruik          Varchar(200)                            CHECK (VoornamenNaamgebruik <> '' )   /* Voornamen */,
   AdellijkeTitelNaamgebruik     Smallint                                                               /* AdellijkeTitelID */,
   VoorvoegselNaamgebruik        Varchar(10)                             CHECK (VoorvoegselNaamgebruik <> '' )   /* Voorvoegsel */,
   ScheidingstekenNaamgebruik    Varchar(1)                              CHECK (ScheidingstekenNaamgebruik <> '' )   /* Scheidingsteken */,
   GeslnaamstamNaamgebruik       Varchar(200)                            CHECK (GeslnaamstamNaamgebruik <> '' )   /* Geslnaamstam */,
   IndAGNaamgebruik              Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
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
   IndAGMigratie                 Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   AandVerblijfsr                Smallint                                                               /* AandVerblijfsrID */,
   DatAanvVerblijfsr             Integer                                                                /* DatEvtDeelsOnbekend */,
   DatMededelingVerblijfsr       Integer                                                                /* DatEvtDeelsOnbekend */,
   DatVoorzEindeVerblijfsr       Integer                                                                /* DatEvtDeelsOnbekend */,
   IndAGVerblijfsr               Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   IndUitslKiesr                 Boolean                                 CHECK (IndUitslKiesr IN (true))   /* Ja */,
   DatVoorzEindeUitslKiesr       Integer                                                                /* DatEvtDeelsOnbekend */,
   IndAGUitslKiesr               Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   IndDeelnEUVerkiezingen        Boolean                                                                /* JaNee */,
   DatAanlAanpDeelnEUVerkiezing  Integer                                                                /* DatEvtDeelsOnbekend */,
   DatVoorzEindeUitslEUVerkiezi  Integer                                                                /* DatEvtDeelsOnbekend */,
   IndAGDeelnEUVerkiezingen      Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   GemPK                         Smallint                                                               /* PartijID */,
   IndPKVolledigGeconv           Boolean                                                                /* JaNee */,
   IndAGPK                       Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   CONSTRAINT pk_Pers PRIMARY KEY (ID)
);
CREATE UNIQUE INDEX uc_Pers_ANr ON Kern.Pers (ANr) WHERE Srt = 1 AND NadereBijhaard NOT IN (7, 8, 9);
CREATE UNIQUE INDEX uc_Pers_BSN ON Kern.Pers (BSN) WHERE Srt = 1 AND NadereBijhaard NOT IN (7, 8, 9);
ALTER SEQUENCE Kern.seq_Pers OWNED BY Kern.Pers.ID;

CREATE SEQUENCE Kern.seq_PersAdres START WITH 1;
CREATE TABLE Kern.PersAdres (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_PersAdres')   /* PersAdresID */,
   Pers                          Bigint                        NOT NULL                                 /* PersID */,
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
   IndAG                         Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   CONSTRAINT pk_PersAdres PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_PersAdres OWNED BY Kern.PersAdres.ID;

CREATE SEQUENCE Kern.seq_PersBLPersnr START WITH 1;
CREATE TABLE Kern.PersBLPersnr (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_PersBLPersnr')   /* PersBLPersnrID */,
   Pers                          Bigint                        NOT NULL                                 /* PersID */,
   AutVanAfgifte                 Integer                       NOT NULL                                 /* AutVanAfgifteBLPersnrID */,
   Nr                            Varchar(40)                   NOT NULL  CHECK (Nr <> '' )              /* BLPersnr */,
   IndAG                         Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   CONSTRAINT pk_PersBLPersnr PRIMARY KEY (ID),
   CONSTRAINT uc_PersBLPersnr_Pers_AutVanAfgifte_Nr UNIQUE (Pers, AutVanAfgifte, Nr)
);
ALTER SEQUENCE Kern.seq_PersBLPersnr OWNED BY Kern.PersBLPersnr.ID;

CREATE SEQUENCE Kern.seq_PersGeslnaamcomp START WITH 1;
CREATE TABLE Kern.PersGeslnaamcomp (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_PersGeslnaamcomp')   /* PersGeslnaamID */,
   Pers                          Bigint                        NOT NULL                                 /* PersID */,
   Volgnr                        Integer                       NOT NULL                                 /* Volgnr */,
   Predicaat                     Smallint                                                               /* PredicaatID */,
   AdellijkeTitel                Smallint                                                               /* AdellijkeTitelID */,
   Voorvoegsel                   Varchar(10)                             CHECK (Voorvoegsel <> '' )     /* Voorvoegsel */,
   Scheidingsteken               Varchar(1)                              CHECK (Scheidingsteken <> '' )   /* Scheidingsteken */,
   Stam                          Varchar(200)                            CHECK (Stam <> '' )            /* Geslnaamstam */,
   IndAG                         Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   CONSTRAINT pk_PersGeslnaamcomp PRIMARY KEY (ID),
   CONSTRAINT uc_PersGeslnaamcomp_Pers_Volgnr UNIQUE (Pers, Volgnr)
);
ALTER SEQUENCE Kern.seq_PersGeslnaamcomp OWNED BY Kern.PersGeslnaamcomp.ID;

CREATE SEQUENCE Kern.seq_PersIndicatie START WITH 1;
CREATE TABLE Kern.PersIndicatie (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_PersIndicatie')   /* PersIndicatieID */,
   Pers                          Bigint                        NOT NULL                                 /* PersID */,
   Srt                           Smallint                      NOT NULL                                 /* SrtIndicatieID */,
   Waarde                        Boolean                                 CHECK (Waarde IN (true))       /* Ja */,
   MigrRdnOpnameNation           Varchar(3)                              CHECK (MigrRdnOpnameNation <> '' )   /* LO3RdnOpnameNation */,
   MigrRdnBeeindigenNation       Varchar(3)                              CHECK (MigrRdnBeeindigenNation <> '' )   /* LO3RdnBeeindigenNation */,
   IndAG                         Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   CONSTRAINT pk_PersIndicatie PRIMARY KEY (ID),
   CONSTRAINT uc_PersIndicatie_Pers_Srt UNIQUE (Pers, Srt)
);
ALTER SEQUENCE Kern.seq_PersIndicatie OWNED BY Kern.PersIndicatie.ID;

CREATE SEQUENCE Kern.seq_PersNation START WITH 1;
CREATE TABLE Kern.PersNation (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_PersNation')   /* PersNationID */,
   Pers                          Bigint                        NOT NULL                                 /* PersID */,
   Nation                        Integer                       NOT NULL                                 /* NationID */,
   RdnVerk                       Smallint                                                               /* RdnVerkNLNationID */,
   RdnVerlies                    Smallint                                                               /* RdnVerliesNLNationID */,
   IndBijhoudingBeeindigd        Boolean                                 CHECK (IndBijhoudingBeeindigd IN (true))   /* Ja */,
   MigrRdnOpnameNation           Varchar(3)                              CHECK (MigrRdnOpnameNation <> '' )   /* LO3RdnOpnameNation */,
   MigrRdnBeeindigenNation       Varchar(3)                              CHECK (MigrRdnBeeindigenNation <> '' )   /* LO3RdnBeeindigenNation */,
   MigrDatEindeBijhouding        Integer                                                                /* DatEvtDeelsOnbekend */,
   IndAG                         Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   CONSTRAINT pk_PersNation PRIMARY KEY (ID),
   CONSTRAINT uc_PersNation_Pers_Nation UNIQUE (Pers, Nation)
);
ALTER SEQUENCE Kern.seq_PersNation OWNED BY Kern.PersNation.ID;

CREATE SEQUENCE Kern.seq_PersReisdoc START WITH 1;
CREATE TABLE Kern.PersReisdoc (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_PersReisdoc')   /* PersReisdocID */,
   Pers                          Bigint                        NOT NULL                                 /* PersID */,
   Srt                           Smallint                      NOT NULL                                 /* SrtNLReisdocID */,
   Nr                            Varchar(9)                              CHECK (Nr <> '' )              /* ReisdocNr */,
   AutVanAfgifte                 Varchar(6)                              CHECK (AutVanAfgifte <> '' )   /* AutVanAfgifteReisdocCode */,
   DatIngangDoc                  Integer                                                                /* DatEvtDeelsOnbekend */,
   DatEindeDoc                   Integer                                                                /* DatEvtDeelsOnbekend */,
   DatUitgifte                   Integer                                                                /* DatEvtDeelsOnbekend */,
   DatInhingVermissing           Integer                                                                /* DatEvtDeelsOnbekend */,
   AandInhingVermissing          Smallint                                                               /* AandInhingVermissingReisdocI */,
   IndAG                         Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   CONSTRAINT pk_PersReisdoc PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_PersReisdoc OWNED BY Kern.PersReisdoc.ID;

CREATE SEQUENCE Kern.seq_PersVerificatie START WITH 1;
CREATE TABLE Kern.PersVerificatie (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_PersVerificatie')   /* PersVerificatieID */,
   Pers                          Bigint                        NOT NULL                                 /* PersID */,
   Partij                        Smallint                      NOT NULL                                 /* PartijID */,
   Srt                           Varchar(80)                   NOT NULL  CHECK (Srt <> '' )             /* NaamEnumeratiewaarde */,
   Dat                           Integer                                                                /* DatEvtDeelsOnbekend */,
   IndAG                         Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   CONSTRAINT pk_PersVerificatie PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_PersVerificatie OWNED BY Kern.PersVerificatie.ID;

CREATE SEQUENCE Kern.seq_PersVerstrbeperking START WITH 1;
CREATE TABLE Kern.PersVerstrbeperking (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_PersVerstrbeperking')   /* PersVerstrbeperkingID */,
   Pers                          Bigint                        NOT NULL                                 /* PersID */,
   Partij                        Smallint                                                               /* PartijID */,
   OmsDerde                      Varchar(250)                            CHECK (OmsDerde <> '' )        /* OmsEnumeratiewaarde */,
   GemVerordening                Smallint                                                               /* PartijID */,
   IndAG                         Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   CONSTRAINT pk_PersVerstrbeperking PRIMARY KEY (ID),
   CONSTRAINT uc_PersVerstrbeperking_Pers_Partij_OmsDerde_GemVerordening UNIQUE (Pers, Partij, OmsDerde, GemVerordening)
);
ALTER SEQUENCE Kern.seq_PersVerstrbeperking OWNED BY Kern.PersVerstrbeperking.ID;

CREATE SEQUENCE Kern.seq_PersVoornaam START WITH 1;
CREATE TABLE Kern.PersVoornaam (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_PersVoornaam')   /* PersVoornaamID */,
   Pers                          Bigint                        NOT NULL                                 /* PersID */,
   Volgnr                        Integer                       NOT NULL                                 /* Volgnr */,
   Naam                          Varchar(200)                            CHECK (Naam <> '' )            /* Voornaam */,
   IndAG                         Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   CONSTRAINT pk_PersVoornaam PRIMARY KEY (ID),
   CONSTRAINT uc_PersVoornaam_Pers_Volgnr UNIQUE (Pers, Volgnr)
);
ALTER SEQUENCE Kern.seq_PersVoornaam OWNED BY Kern.PersVoornaam.ID;

CREATE SEQUENCE Kern.seq_PersCache START WITH 1;
CREATE TABLE Kern.PersCache (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_PersCache')   /* PersID */,
   Pers                          Bigint                        NOT NULL                                 /* PersID */,
   Versienr                      Smallint                      NOT NULL                                 /* VersienrKlein */,
   PersHistorieVolledigGegevens  bytea                                                                  /* Byteaopslag */,
   LockversieAfnemerindicatieGe  Bigint                                                                 /* Versienr */,
   AfnemerindicatieGegevens      bytea                                                                  /* Byteaopslag */,
   CONSTRAINT pk_PersCache PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_PersCache OWNED BY Kern.PersCache.ID;

CREATE SEQUENCE Kern.seq_Relatie START WITH 1;
CREATE TABLE Kern.Relatie (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_Relatie')   /* RelatieID */,
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
   IndAG                         Boolean                       NOT NULL  DEFAULT false                  /* JaNee */,
   CONSTRAINT pk_Relatie PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_Relatie OWNED BY Kern.Relatie.ID;

CREATE SEQUENCE MigBlok.seq_Blokkering START WITH 1;
CREATE TABLE MigBlok.Blokkering (
   ID                            Integer                       NOT NULL  DEFAULT nextval('MigBlok.seq_Blokkering')   /* BlokkeringID */,
   ANr                           Char(10)                      NOT NULL  CHECK (ANr ~ '^[0-9]+$' AND Length(ANr) >= 10)   /* ANr */,
   RdnBlokkering                 Smallint                                                               /* RdnBlokkeringID */,
   ProcessInstantieID            Bigint                                                                 /* ProcessInstantieID */,
   LO3GemVestiging               Char(6)                                 CHECK (LO3GemVestiging ~ '^[0-9]+$' AND Length(LO3GemVestiging) >= 6)   /* LO3GemCode */,
   LO3GemReg                     Char(6)                                 CHECK (LO3GemReg ~ '^[0-9]+$' AND Length(LO3GemReg) >= 6)   /* LO3GemCode */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   CONSTRAINT pk_Blokkering PRIMARY KEY (ID),
   CONSTRAINT uc_Blokkering_ANr UNIQUE (ANr)
);
ALTER SEQUENCE MigBlok.seq_Blokkering OWNED BY MigBlok.Blokkering.ID;

CREATE SEQUENCE VerConv.seq_LO3AandOuder START WITH 1;
CREATE TABLE VerConv.LO3AandOuder (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('VerConv.seq_LO3AandOuder')   /* LO3AandOuderID */,
   Ouder                         Bigint                        NOT NULL                                 /* BetrID */,
   Srt                           Smallint                      NOT NULL                                 /* LO3SrtAandOuderID */,
   CONSTRAINT pk_LO3AandOuder PRIMARY KEY (ID),
   CONSTRAINT uc_LO3AandOuder_Ouder_Srt UNIQUE (Ouder, Srt)
);
ALTER SEQUENCE VerConv.seq_LO3AandOuder OWNED BY VerConv.LO3AandOuder.ID;

CREATE SEQUENCE VerConv.seq_LO3Ber START WITH 1;
CREATE TABLE VerConv.LO3Ber (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('VerConv.seq_LO3Ber')   /* LO3BerID */,
   IndBersrtOnderdeelLO3Stelsel  Boolean                       NOT NULL                                 /* JaNee */,
   Referentie                    Varchar(36)                   NOT NULL  CHECK (Referentie <> '' )      /* LO3Referentie */,
   Bron                          Integer                       NOT NULL                                 /* ConvID */,
   ANr                           Char(10)                                CHECK (ANr ~ '^[0-9]+$' AND Length(ANr) >= 10)   /* ANr */,
   Pers                          Bigint                                                                 /* PersID */,
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
   ID                            Bigint                        NOT NULL  DEFAULT nextval('VerConv.seq_LO3Melding')   /* LO3MeldingID */,
   LO3Voorkomen                  Bigint                        NOT NULL                                 /* LO3VoorkomenID */,
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
   ID                            Bigint                        NOT NULL  DEFAULT nextval('VerConv.seq_LO3Voorkomen')   /* LO3VoorkomenID */,
   LO3Ber                        Bigint                        NOT NULL                                 /* LO3BerID */,
   LO3Categorie                  Varchar(2)                    NOT NULL  CHECK (LO3Categorie <> '' )    /* LO3Categorie */,
   LO3Stapelvolgnr               Integer                                                                /* Volgnr */,
   LO3Voorkomenvolgnr            Integer                                                                /* Volgnr */,
   Actie                         Bigint                                                                 /* ActieID */,
   LO3ConversieSortering         Smallint                                                               /* Sorteervolgorde */,
   CONSTRAINT pk_LO3Voorkomen PRIMARY KEY (ID),
   CONSTRAINT uc_LO3Voorkomen_LO3Ber_LO3Categorie_LO3Stapelvolgnr_LO3Voorkome UNIQUE (LO3Ber, LO3Categorie, LO3Stapelvolgnr, LO3Voorkomenvolgnr),
   CONSTRAINT uc_LO3Voorkomen_Actie UNIQUE (Actie) DEFERRABLE INITIALLY DEFERRED
);
ALTER SEQUENCE VerConv.seq_LO3Voorkomen OWNED BY VerConv.LO3Voorkomen.ID;

-- Materiele historie tabellen -------------------------------------------------
CREATE SEQUENCE AutAut.seq_His_PersAfnemerindicatie START WITH 1;
CREATE TABLE AutAut.His_PersAfnemerindicatie (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('AutAut.seq_His_PersAfnemerindicatie')   /* His_PersAfnemerindicatieID */,
   PersAfnemerindicatie          Bigint                        NOT NULL                                 /* PersAfnemerindicatieID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   DienstInh                     Integer                                                                /* DienstID */,
   DienstVerval                  Integer                                                                /* DienstID */,
   DatAanvMaterielePeriode       Integer                                 CHECK (to_char(to_date(DatAanvMaterielePeriode::text, 'yyyymmdd'), 'yyyymmdd')=DatAanvMaterielePeriode::text)   /* Dat */,
   DatEindeVolgen                Integer                                 CHECK (to_char(to_date(DatEindeVolgen::text, 'yyyymmdd'), 'yyyymmdd')=DatEindeVolgen::text)   /* Dat */,
   CONSTRAINT pk_His_PersAfnemerindicatie PRIMARY KEY (ID),
   CONSTRAINT uc_His_PersAfnemerindicatie_PersAfnemerindicatie_TsReg UNIQUE (PersAfnemerindicatie, TsReg)
);
ALTER SEQUENCE AutAut.seq_His_PersAfnemerindicatie OWNED BY AutAut.His_PersAfnemerindicatie.ID;

CREATE SEQUENCE Kern.seq_His_Betr START WITH 1;
CREATE TABLE Kern.His_Betr (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_Betr')   /* His_BetrID */,
   Betr                          Bigint                        NOT NULL                                 /* BetrID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   ActieInh                      Bigint                        NOT NULL                                 /* ActieID */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   ActieVerval                   Bigint                                                                 /* ActieID */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieVervalTbvLevMuts         Bigint                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   CONSTRAINT pk_His_Betr PRIMARY KEY (ID),
   CONSTRAINT uc_His_Betr_Betr UNIQUE (Betr)
);
ALTER SEQUENCE Kern.seq_His_Betr OWNED BY Kern.His_Betr.ID;

CREATE SEQUENCE Kern.seq_His_GegevenInOnderzoek START WITH 1;
CREATE TABLE Kern.His_GegevenInOnderzoek (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_GegevenInOnderzoek')   /* His_GegevenInOnderzoekID */,
   GegevenInOnderzoek            Bigint                        NOT NULL                                 /* GegevenInOnderzoekID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   ActieInh                      Bigint                        NOT NULL                                 /* ActieID */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   ActieVerval                   Bigint                                                                 /* ActieID */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieVervalTbvLevMuts         Bigint                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   CONSTRAINT pk_His_GegevenInOnderzoek PRIMARY KEY (ID),
   CONSTRAINT uc_His_GegevenInOnderzoek_GegevenInOnderzoek UNIQUE (GegevenInOnderzoek)
);
ALTER SEQUENCE Kern.seq_His_GegevenInOnderzoek OWNED BY Kern.His_GegevenInOnderzoek.ID;

CREATE SEQUENCE Kern.seq_His_Onderzoek START WITH 1;
CREATE TABLE Kern.His_Onderzoek (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_Onderzoek')   /* His_OnderzoekID */,
   Onderzoek                     Integer                       NOT NULL                                 /* OnderzoekID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   ActieInh                      Bigint                        NOT NULL                                 /* ActieID */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   ActieVerval                   Bigint                                                                 /* ActieID */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieVervalTbvLevMuts         Bigint                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   DatAanv                       Integer                       NOT NULL                                 /* DatEvtDeelsOnbekend */,
   DatEinde                      Integer                                                                /* DatEvtDeelsOnbekend */,
   Oms                           Text                                                                   /* OnderzoekOms */,
   Status                        Smallint                      NOT NULL                                 /* StatusOnderzoekID */,
   CONSTRAINT pk_His_Onderzoek PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_Onderzoek OWNED BY Kern.His_Onderzoek.ID;

CREATE SEQUENCE Kern.seq_His_OuderOuderlijkGezag START WITH 1;
CREATE TABLE Kern.His_OuderOuderlijkGezag (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_OuderOuderlijkGezag')   /* His_OuderOuderlijkGezagID */,
   Betr                          Bigint                        NOT NULL                                 /* BetrID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   ActieInh                      Bigint                        NOT NULL                                 /* ActieID */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   ActieVerval                   Bigint                                                                 /* ActieID */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieVervalTbvLevMuts         Bigint                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   DatAanvGel                    Integer                       NOT NULL                                 /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   ActieAanpGel                  Bigint                                                                 /* ActieID */,
   IndOuderHeeftGezag            Boolean                       NOT NULL                                 /* JaNee */,
   CONSTRAINT pk_His_OuderOuderlijkGezag PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_OuderOuderlijkGezag OWNED BY Kern.His_OuderOuderlijkGezag.ID;

CREATE SEQUENCE Kern.seq_His_OuderOuderschap START WITH 1;
CREATE TABLE Kern.His_OuderOuderschap (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_OuderOuderschap')   /* His_OuderOuderschapID */,
   Betr                          Bigint                        NOT NULL                                 /* BetrID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   ActieInh                      Bigint                        NOT NULL                                 /* ActieID */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   ActieVerval                   Bigint                                                                 /* ActieID */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieVervalTbvLevMuts         Bigint                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   DatAanvGel                    Integer                       NOT NULL                                 /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   ActieAanpGel                  Bigint                                                                 /* ActieID */,
   IndOuderUitWieKindIsGeboren   Boolean                                                                /* JaNee */,
   CONSTRAINT pk_His_OuderOuderschap PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_OuderOuderschap OWNED BY Kern.His_OuderOuderschap.ID;

CREATE SEQUENCE Kern.seq_His_PersAfgeleidAdministrati START WITH 1;
CREATE TABLE Kern.His_PersAfgeleidAdministrati (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersAfgeleidAdministrati')   /* His_PersAfgeleidAdministrati */,
   Pers                          Bigint                        NOT NULL                                 /* PersID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   ActieInh                      Bigint                        NOT NULL                                 /* ActieID */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   ActieVerval                   Bigint                                                                 /* ActieID */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieVervalTbvLevMuts         Bigint                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   AdmHnd                        Bigint                        NOT NULL                                 /* AdmHndID */,
   TsLaatsteWijz                 Timestamp with time zone      NOT NULL                                 /* Ts */,
   Sorteervolgorde               Smallint                      NOT NULL                                 /* Sorteervolgorde */,
   TsLaatsteWijzGBASystematiek   Timestamp with time zone                                               /* Ts */,
   CONSTRAINT pk_His_PersAfgeleidAdministrati PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_PersAfgeleidAdministrati OWNED BY Kern.His_PersAfgeleidAdministrati.ID;

CREATE SEQUENCE Kern.seq_His_PersBijhouding START WITH 1;
CREATE TABLE Kern.His_PersBijhouding (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersBijhouding')   /* His_PersBijhoudingID */,
   Pers                          Bigint                        NOT NULL                                 /* PersID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   ActieInh                      Bigint                        NOT NULL                                 /* ActieID */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   ActieVerval                   Bigint                                                                 /* ActieID */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieVervalTbvLevMuts         Bigint                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   DatAanvGel                    Integer                       NOT NULL                                 /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   ActieAanpGel                  Bigint                                                                 /* ActieID */,
   Bijhpartij                    Smallint                      NOT NULL                                 /* PartijID */,
   Bijhaard                      Smallint                      NOT NULL                                 /* BijhaardID */,
   NadereBijhaard                Smallint                      NOT NULL                                 /* NadereBijhaardID */,
   CONSTRAINT pk_His_PersBijhouding PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_PersBijhouding OWNED BY Kern.His_PersBijhouding.ID;

CREATE SEQUENCE Kern.seq_His_PersDeelnEUVerkiezingen START WITH 1;
CREATE TABLE Kern.His_PersDeelnEUVerkiezingen (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersDeelnEUVerkiezingen')   /* His_PersDeelnEUVerkiezingenI */,
   Pers                          Bigint                        NOT NULL                                 /* PersID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   ActieInh                      Bigint                        NOT NULL                                 /* ActieID */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   ActieVerval                   Bigint                                                                 /* ActieID */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieVervalTbvLevMuts         Bigint                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   IndDeelnEUVerkiezingen        Boolean                       NOT NULL                                 /* JaNee */,
   DatAanlAanpDeelnEUVerkiezing  Integer                                                                /* DatEvtDeelsOnbekend */,
   DatVoorzEindeUitslEUVerkiezi  Integer                                                                /* DatEvtDeelsOnbekend */,
   CONSTRAINT pk_His_PersDeelnEUVerkiezingen PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_PersDeelnEUVerkiezingen OWNED BY Kern.His_PersDeelnEUVerkiezingen.ID;

CREATE SEQUENCE Kern.seq_His_PersGeboorte START WITH 1;
CREATE TABLE Kern.His_PersGeboorte (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersGeboorte')   /* His_PersGeboorteID */,
   Pers                          Bigint                        NOT NULL                                 /* PersID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   ActieInh                      Bigint                        NOT NULL                                 /* ActieID */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   ActieVerval                   Bigint                                                                 /* ActieID */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieVervalTbvLevMuts         Bigint                                                                 /* ActieID */,
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
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersGeslachtsaand')   /* His_PersGeslachtsaandID */,
   Pers                          Bigint                        NOT NULL                                 /* PersID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   ActieInh                      Bigint                        NOT NULL                                 /* ActieID */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   ActieVerval                   Bigint                                                                 /* ActieID */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieVervalTbvLevMuts         Bigint                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   DatAanvGel                    Integer                       NOT NULL                                 /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   ActieAanpGel                  Bigint                                                                 /* ActieID */,
   Geslachtsaand                 Smallint                      NOT NULL                                 /* GeslachtsaandID */,
   CONSTRAINT pk_His_PersGeslachtsaand PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_PersGeslachtsaand OWNED BY Kern.His_PersGeslachtsaand.ID;

CREATE SEQUENCE Kern.seq_His_PersIDs START WITH 1;
CREATE TABLE Kern.His_PersIDs (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersIDs')   /* His_PersIDsID */,
   Pers                          Bigint                        NOT NULL                                 /* PersID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   ActieInh                      Bigint                        NOT NULL                                 /* ActieID */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   ActieVerval                   Bigint                                                                 /* ActieID */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieVervalTbvLevMuts         Bigint                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   DatAanvGel                    Integer                       NOT NULL                                 /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   ActieAanpGel                  Bigint                                                                 /* ActieID */,
   BSN                           Char(9)                                 CHECK (BSN ~ '^[0-9]+$' AND Length(BSN) >= 9)   /* BSN */,
   ANr                           Char(10)                                CHECK (ANr ~ '^[0-9]+$' AND Length(ANr) >= 10)   /* ANr */,
   CONSTRAINT pk_His_PersIDs PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_PersIDs OWNED BY Kern.His_PersIDs.ID;

CREATE SEQUENCE Kern.seq_His_PersInschr START WITH 1;
CREATE TABLE Kern.His_PersInschr (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersInschr')   /* His_PersInschrID */,
   Pers                          Bigint                        NOT NULL                                 /* PersID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   ActieInh                      Bigint                        NOT NULL                                 /* ActieID */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   ActieVerval                   Bigint                                                                 /* ActieID */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieVervalTbvLevMuts         Bigint                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   DatInschr                     Integer                       NOT NULL                                 /* DatEvtDeelsOnbekend */,
   Versienr                      Bigint                        NOT NULL                                 /* Versienr */,
   Dattijdstempel                Timestamp with time zone      NOT NULL                                 /* Ts */,
   CONSTRAINT pk_His_PersInschr PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_PersInschr OWNED BY Kern.His_PersInschr.ID;

CREATE SEQUENCE Kern.seq_His_PersMigratie START WITH 1;
CREATE TABLE Kern.His_PersMigratie (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersMigratie')   /* His_PersMigratieID */,
   Pers                          Bigint                        NOT NULL                                 /* PersID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   ActieInh                      Bigint                        NOT NULL                                 /* ActieID */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   ActieVerval                   Bigint                                                                 /* ActieID */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieVervalTbvLevMuts         Bigint                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   DatAanvGel                    Integer                       NOT NULL                                 /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   ActieAanpGel                  Bigint                                                                 /* ActieID */,
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
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersNaamgebruik')   /* His_PersNaamgebruikID */,
   Pers                          Bigint                        NOT NULL                                 /* PersID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   ActieInh                      Bigint                        NOT NULL                                 /* ActieID */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   ActieVerval                   Bigint                                                                 /* ActieID */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieVervalTbvLevMuts         Bigint                                                                 /* ActieID */,
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
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersNrverwijzing')   /* His_PersNrverwijzingID */,
   Pers                          Bigint                        NOT NULL                                 /* PersID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   ActieInh                      Bigint                        NOT NULL                                 /* ActieID */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   ActieVerval                   Bigint                                                                 /* ActieID */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieVervalTbvLevMuts         Bigint                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   DatAanvGel                    Integer                       NOT NULL                                 /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   ActieAanpGel                  Bigint                                                                 /* ActieID */,
   VorigeBSN                     Char(9)                                 CHECK (VorigeBSN ~ '^[0-9]+$' AND Length(VorigeBSN) >= 9)   /* BSN */,
   VolgendeBSN                   Char(9)                                 CHECK (VolgendeBSN ~ '^[0-9]+$' AND Length(VolgendeBSN) >= 9)   /* BSN */,
   VorigeANr                     Char(10)                                CHECK (VorigeANr ~ '^[0-9]+$' AND Length(VorigeANr) >= 10)   /* ANr */,
   VolgendeANr                   Char(10)                                CHECK (VolgendeANr ~ '^[0-9]+$' AND Length(VolgendeANr) >= 10)   /* ANr */,
   CONSTRAINT pk_His_PersNrverwijzing PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_PersNrverwijzing OWNED BY Kern.His_PersNrverwijzing.ID;

CREATE SEQUENCE Kern.seq_His_PersOverlijden START WITH 1;
CREATE TABLE Kern.His_PersOverlijden (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersOverlijden')   /* His_PersOverlijdenID */,
   Pers                          Bigint                        NOT NULL                                 /* PersID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   ActieInh                      Bigint                        NOT NULL                                 /* ActieID */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   ActieVerval                   Bigint                                                                 /* ActieID */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieVervalTbvLevMuts         Bigint                                                                 /* ActieID */,
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
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersPK')   /* His_PersPKID */,
   Pers                          Bigint                        NOT NULL                                 /* PersID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   ActieInh                      Bigint                        NOT NULL                                 /* ActieID */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   ActieVerval                   Bigint                                                                 /* ActieID */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieVervalTbvLevMuts         Bigint                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   GemPK                         Smallint                                                               /* PartijID */,
   IndPKVolledigGeconv           Boolean                       NOT NULL                                 /* JaNee */,
   CONSTRAINT pk_His_PersPK PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_PersPK OWNED BY Kern.His_PersPK.ID;

CREATE SEQUENCE Kern.seq_His_PersSamengesteldeNaam START WITH 1;
CREATE TABLE Kern.His_PersSamengesteldeNaam (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersSamengesteldeNaam')   /* His_PersSamengesteldeNaamID */,
   Pers                          Bigint                        NOT NULL                                 /* PersID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   ActieInh                      Bigint                        NOT NULL                                 /* ActieID */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   ActieVerval                   Bigint                                                                 /* ActieID */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieVervalTbvLevMuts         Bigint                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   DatAanvGel                    Integer                       NOT NULL                                 /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   ActieAanpGel                  Bigint                                                                 /* ActieID */,
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
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersUitslKiesr')   /* His_PersUitslKiesrID */,
   Pers                          Bigint                        NOT NULL                                 /* PersID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   ActieInh                      Bigint                        NOT NULL                                 /* ActieID */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   ActieVerval                   Bigint                                                                 /* ActieID */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieVervalTbvLevMuts         Bigint                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   IndUitslKiesr                 Boolean                       NOT NULL  CHECK (IndUitslKiesr IN (true))   /* Ja */,
   DatVoorzEindeUitslKiesr       Integer                                                                /* DatEvtDeelsOnbekend */,
   CONSTRAINT pk_His_PersUitslKiesr PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_PersUitslKiesr OWNED BY Kern.His_PersUitslKiesr.ID;

CREATE SEQUENCE Kern.seq_His_PersVerblijfsr START WITH 1;
CREATE TABLE Kern.His_PersVerblijfsr (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersVerblijfsr')   /* His_PersVerblijfsrID */,
   Pers                          Bigint                        NOT NULL                                 /* PersID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   ActieInh                      Bigint                        NOT NULL                                 /* ActieID */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   ActieVerval                   Bigint                                                                 /* ActieID */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieVervalTbvLevMuts         Bigint                                                                 /* ActieID */,
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
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersAdres')   /* His_PersAdresID */,
   PersAdres                     Bigint                        NOT NULL                                 /* PersAdresID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   ActieInh                      Bigint                        NOT NULL                                 /* ActieID */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   ActieVerval                   Bigint                                                                 /* ActieID */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieVervalTbvLevMuts         Bigint                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   DatAanvGel                    Integer                       NOT NULL                                 /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   ActieAanpGel                  Bigint                                                                 /* ActieID */,
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

CREATE SEQUENCE Kern.seq_His_PersBLPersnr START WITH 1;
CREATE TABLE Kern.His_PersBLPersnr (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersBLPersnr')   /* His_PersBLPersnrID */,
   PersBLPersnr                  Bigint                        NOT NULL                                 /* PersBLPersnrID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   ActieInh                      Bigint                        NOT NULL                                 /* ActieID */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   ActieVerval                   Bigint                                                                 /* ActieID */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieVervalTbvLevMuts         Bigint                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   CONSTRAINT pk_His_PersBLPersnr PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_PersBLPersnr OWNED BY Kern.His_PersBLPersnr.ID;

CREATE SEQUENCE Kern.seq_His_PersGeslnaamcomp START WITH 1;
CREATE TABLE Kern.His_PersGeslnaamcomp (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersGeslnaamcomp')   /* His_PersGeslnaamcompID */,
   PersGeslnaamcomp              Bigint                        NOT NULL                                 /* PersGeslnaamID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   ActieInh                      Bigint                        NOT NULL                                 /* ActieID */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   ActieVerval                   Bigint                                                                 /* ActieID */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieVervalTbvLevMuts         Bigint                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   DatAanvGel                    Integer                       NOT NULL                                 /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   ActieAanpGel                  Bigint                                                                 /* ActieID */,
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
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersIndicatie')   /* His_PersIndicatieID */,
   PersIndicatie                 Bigint                        NOT NULL                                 /* PersIndicatieID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   ActieInh                      Bigint                        NOT NULL                                 /* ActieID */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   ActieVerval                   Bigint                                                                 /* ActieID */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieVervalTbvLevMuts         Bigint                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   DatAanvGel                    Integer                                                                /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   ActieAanpGel                  Bigint                                                                 /* ActieID */,
   Waarde                        Boolean                       NOT NULL  CHECK (Waarde IN (true))       /* Ja */,
   MigrRdnOpnameNation           Varchar(3)                              CHECK (MigrRdnOpnameNation <> '' )   /* LO3RdnOpnameNation */,
   MigrRdnBeeindigenNation       Varchar(3)                              CHECK (MigrRdnBeeindigenNation <> '' )   /* LO3RdnBeeindigenNation */,
   CONSTRAINT pk_His_PersIndicatie PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_PersIndicatie OWNED BY Kern.His_PersIndicatie.ID;

CREATE SEQUENCE Kern.seq_His_PersNation START WITH 1;
CREATE TABLE Kern.His_PersNation (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersNation')   /* His_PersNationID */,
   PersNation                    Bigint                        NOT NULL                                 /* PersNationID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   ActieInh                      Bigint                        NOT NULL                                 /* ActieID */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   ActieVerval                   Bigint                                                                 /* ActieID */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieVervalTbvLevMuts         Bigint                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   DatAanvGel                    Integer                       NOT NULL                                 /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   ActieAanpGel                  Bigint                                                                 /* ActieID */,
   RdnVerk                       Smallint                                                               /* RdnVerkNLNationID */,
   RdnVerlies                    Smallint                                                               /* RdnVerliesNLNationID */,
   IndBijhoudingBeeindigd        Boolean                                 CHECK (IndBijhoudingBeeindigd IN (true))   /* Ja */,
   MigrRdnOpnameNation           Varchar(3)                              CHECK (MigrRdnOpnameNation <> '' )   /* LO3RdnOpnameNation */,
   MigrRdnBeeindigenNation       Varchar(3)                              CHECK (MigrRdnBeeindigenNation <> '' )   /* LO3RdnBeeindigenNation */,
   MigrDatEindeBijhouding        Integer                                                                /* DatEvtDeelsOnbekend */,
   CONSTRAINT pk_His_PersNation PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_PersNation OWNED BY Kern.His_PersNation.ID;

CREATE SEQUENCE Kern.seq_His_PersReisdoc START WITH 1;
CREATE TABLE Kern.His_PersReisdoc (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersReisdoc')   /* His_PersReisdocID */,
   PersReisdoc                   Bigint                        NOT NULL                                 /* PersReisdocID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   ActieInh                      Bigint                        NOT NULL                                 /* ActieID */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   ActieVerval                   Bigint                                                                 /* ActieID */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieVervalTbvLevMuts         Bigint                                                                 /* ActieID */,
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
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersVerificatie')   /* His_PersVerificatieID */,
   PersVerificatie               Bigint                        NOT NULL                                 /* PersVerificatieID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   ActieInh                      Bigint                        NOT NULL                                 /* ActieID */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   ActieVerval                   Bigint                                                                 /* ActieID */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieVervalTbvLevMuts         Bigint                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   Dat                           Integer                       NOT NULL                                 /* DatEvtDeelsOnbekend */,
   CONSTRAINT pk_His_PersVerificatie PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_PersVerificatie OWNED BY Kern.His_PersVerificatie.ID;

CREATE SEQUENCE Kern.seq_His_PersVerstrbeperking START WITH 1;
CREATE TABLE Kern.His_PersVerstrbeperking (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersVerstrbeperking')   /* His_PersVerstrbeperkingID */,
   PersVerstrbeperking           Bigint                        NOT NULL                                 /* PersVerstrbeperkingID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   ActieInh                      Bigint                        NOT NULL                                 /* ActieID */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   ActieVerval                   Bigint                                                                 /* ActieID */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieVervalTbvLevMuts         Bigint                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   CONSTRAINT pk_His_PersVerstrbeperking PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_PersVerstrbeperking OWNED BY Kern.His_PersVerstrbeperking.ID;

CREATE SEQUENCE Kern.seq_His_PersVoornaam START WITH 1;
CREATE TABLE Kern.His_PersVoornaam (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersVoornaam')   /* His_PersVoornaamID */,
   PersVoornaam                  Bigint                        NOT NULL                                 /* PersVoornaamID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   ActieInh                      Bigint                        NOT NULL                                 /* ActieID */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   ActieVerval                   Bigint                                                                 /* ActieID */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieVervalTbvLevMuts         Bigint                                                                 /* ActieID */,
   IndVoorkomenTbvLevMuts        Boolean                                 CHECK (IndVoorkomenTbvLevMuts IN (true))   /* Ja */,
   DatAanvGel                    Integer                       NOT NULL                                 /* DatEvtDeelsOnbekend */,
   DatEindeGel                   Integer                                                                /* DatEvtDeelsOnbekend */,
   ActieAanpGel                  Bigint                                                                 /* ActieID */,
   Naam                          Varchar(200)                  NOT NULL  CHECK (Naam <> '' )            /* Voornaam */,
   CONSTRAINT pk_His_PersVoornaam PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_His_PersVoornaam OWNED BY Kern.His_PersVoornaam.ID;

CREATE SEQUENCE Kern.seq_His_Relatie START WITH 1;
CREATE TABLE Kern.His_Relatie (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_Relatie')   /* His_RelatieID */,
   Relatie                       Bigint                        NOT NULL                                 /* RelatieID */,
   TsReg                         Timestamp with time zone      NOT NULL                                 /* Ts */,
   ActieInh                      Bigint                        NOT NULL                                 /* ActieID */,
   TsVerval                      Timestamp with time zone                                               /* Ts */,
   ActieVerval                   Bigint                                                                 /* ActieID */,
   NadereAandVerval              Varchar(1)                              CHECK (NadereAandVerval IN ('O','S'))   /* NadereAandVerval */,
   ActieVervalTbvLevMuts         Bigint                                                                 /* ActieID */,
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

-- Foreign keys ----------------------------------------------------------------
ALTER TABLE AutAut.BijhouderFiatuitz ADD CONSTRAINT fk_BijhouderFiatuitz_Bijhouder_PartijRol FOREIGN KEY (Bijhouder) REFERENCES Kern.PartijRol (ID);
ALTER TABLE AutAut.BijhouderFiatuitz ADD CONSTRAINT fk_BijhouderFiatuitz_BijhouderBijhvoorstel_PartijRol FOREIGN KEY (BijhouderBijhvoorstel) REFERENCES Kern.PartijRol (ID);
ALTER TABLE AutAut.BijhouderFiatuitz ADD CONSTRAINT fk_BijhouderFiatuitz_SrtDoc_SrtDoc FOREIGN KEY (SrtDoc) REFERENCES Kern.SrtDoc (ID);
ALTER TABLE AutAut.BijhouderFiatuitz ADD CONSTRAINT fk_BijhouderFiatuitz_SrtAdmHnd_SrtAdmHnd FOREIGN KEY (SrtAdmHnd) REFERENCES Kern.SrtAdmHnd (ID);
ALTER TABLE AutAut.BijhautorisatieSrtAdmHnd ADD CONSTRAINT fk_BijhautorisatieSrtAdmHnd_Bijhautorisatie_Bijhautorisatie FOREIGN KEY (Bijhautorisatie) REFERENCES AutAut.Bijhautorisatie (ID);
ALTER TABLE AutAut.BijhautorisatieSrtAdmHnd ADD CONSTRAINT fk_BijhautorisatieSrtAdmHnd_SrtAdmHnd_SrtAdmHnd FOREIGN KEY (SrtAdmHnd) REFERENCES Kern.SrtAdmHnd (ID);
ALTER TABLE AutAut.Dienst ADD CONSTRAINT fk_Dienst_Dienstbundel_Dienstbundel FOREIGN KEY (Dienstbundel) REFERENCES AutAut.Dienstbundel (ID);
ALTER TABLE AutAut.Dienst ADD CONSTRAINT fk_Dienst_Srt_SrtDienst FOREIGN KEY (Srt) REFERENCES AutAut.SrtDienst (ID);
ALTER TABLE AutAut.Dienst ADD CONSTRAINT fk_Dienst_EffectAfnemerindicaties_EffectAfnemerindicaties FOREIGN KEY (EffectAfnemerindicaties) REFERENCES AutAut.EffectAfnemerindicaties (ID);
ALTER TABLE AutAut.Dienst ADD CONSTRAINT fk_Dienst_SrtSel_SrtSel FOREIGN KEY (SrtSel) REFERENCES AutAut.SrtSel (ID);
ALTER TABLE AutAut.Dienst ADD CONSTRAINT fk_Dienst_EenheidSelinterval_EenheidSelinterval FOREIGN KEY (EenheidSelinterval) REFERENCES AutAut.EenheidSelinterval (ID);
ALTER TABLE AutAut.Dienst ADD CONSTRAINT fk_Dienst_HistorievormSel_Historievorm FOREIGN KEY (HistorievormSel) REFERENCES Kern.Historievorm (ID);
ALTER TABLE AutAut.Dienst ADD CONSTRAINT fk_Dienst_LeverwijzeSel_LeverwijzeSel FOREIGN KEY (LeverwijzeSel) REFERENCES AutAut.LeverwijzeSel (ID);
ALTER TABLE AutAut.Dienstbundel ADD CONSTRAINT fk_Dienstbundel_Levsautorisatie_Levsautorisatie FOREIGN KEY (Levsautorisatie) REFERENCES AutAut.Levsautorisatie (ID);
ALTER TABLE AutAut.DienstbundelGroep ADD CONSTRAINT fk_DienstbundelGroep_Dienstbundel_Dienstbundel FOREIGN KEY (Dienstbundel) REFERENCES AutAut.Dienstbundel (ID);
ALTER TABLE AutAut.DienstbundelGroep ADD CONSTRAINT fk_DienstbundelGroep_Groep_Element FOREIGN KEY (Groep) REFERENCES Kern.Element (ID);
ALTER TABLE AutAut.DienstbundelGroepAttr ADD CONSTRAINT fk_DienstbundelGroepAttr_DienstbundelGroep_DienstbundelGroep FOREIGN KEY (DienstbundelGroep) REFERENCES AutAut.DienstbundelGroep (ID);
ALTER TABLE AutAut.DienstbundelGroepAttr ADD CONSTRAINT fk_DienstbundelGroepAttr_Attr_Element FOREIGN KEY (Attr) REFERENCES Kern.Element (ID);
ALTER TABLE AutAut.DienstbundelLO3Rubriek ADD CONSTRAINT fk_DienstbundelLO3Rubriek_Dienstbundel_Dienstbundel FOREIGN KEY (Dienstbundel) REFERENCES AutAut.Dienstbundel (ID);
ALTER TABLE AutAut.DienstbundelLO3Rubriek ADD CONSTRAINT fk_DienstbundelLO3Rubriek_Rubr_ConvLO3Rubriek FOREIGN KEY (Rubr) REFERENCES Conv.ConvLO3Rubriek (ID);
ALTER TABLE AutAut.His_BijhouderFiatuitz ADD CONSTRAINT fk_His_BijhouderFiatuitz_BijhouderFiatuitz_BijhouderFiatuitz FOREIGN KEY (BijhouderFiatuitz) REFERENCES AutAut.BijhouderFiatuitz (ID);
ALTER TABLE AutAut.His_BijhouderFiatuitz ADD CONSTRAINT fk_His_BijhouderFiatuitz_BijhouderBijhvoorstel_PartijRol FOREIGN KEY (BijhouderBijhvoorstel) REFERENCES Kern.PartijRol (ID);
ALTER TABLE AutAut.His_BijhouderFiatuitz ADD CONSTRAINT fk_His_BijhouderFiatuitz_SrtDoc_SrtDoc FOREIGN KEY (SrtDoc) REFERENCES Kern.SrtDoc (ID);
ALTER TABLE AutAut.His_BijhouderFiatuitz ADD CONSTRAINT fk_His_BijhouderFiatuitz_SrtAdmHnd_SrtAdmHnd FOREIGN KEY (SrtAdmHnd) REFERENCES Kern.SrtAdmHnd (ID);
ALTER TABLE AutAut.His_Bijhautorisatie ADD CONSTRAINT fk_His_Bijhautorisatie_Bijhautorisatie_Bijhautorisatie FOREIGN KEY (Bijhautorisatie) REFERENCES AutAut.Bijhautorisatie (ID);
ALTER TABLE AutAut.His_BijhautorisatieSrtAdmHnd ADD CONSTRAINT fk_His_BijhautorisatieSrtAdmHnd_BijhautorisatieSrtAdmHnd_Bijhau FOREIGN KEY (BijhautorisatieSrtAdmHnd) REFERENCES AutAut.BijhautorisatieSrtAdmHnd (ID);
ALTER TABLE AutAut.His_Dienst ADD CONSTRAINT fk_His_Dienst_Dienst_Dienst FOREIGN KEY (Dienst) REFERENCES AutAut.Dienst (ID);
ALTER TABLE AutAut.His_DienstAttendering ADD CONSTRAINT fk_His_DienstAttendering_Dienst_Dienst FOREIGN KEY (Dienst) REFERENCES AutAut.Dienst (ID);
ALTER TABLE AutAut.His_DienstSel ADD CONSTRAINT fk_His_DienstSel_Dienst_Dienst FOREIGN KEY (Dienst) REFERENCES AutAut.Dienst (ID);
ALTER TABLE AutAut.His_DienstSel ADD CONSTRAINT fk_His_DienstSel_SrtSel_SrtSel FOREIGN KEY (SrtSel) REFERENCES AutAut.SrtSel (ID);
ALTER TABLE AutAut.His_DienstSel ADD CONSTRAINT fk_His_DienstSel_EenheidSelinterval_EenheidSelinterval FOREIGN KEY (EenheidSelinterval) REFERENCES AutAut.EenheidSelinterval (ID);
ALTER TABLE AutAut.His_DienstSel ADD CONSTRAINT fk_His_DienstSel_HistorievormSel_Historievorm FOREIGN KEY (HistorievormSel) REFERENCES Kern.Historievorm (ID);
ALTER TABLE AutAut.His_DienstSel ADD CONSTRAINT fk_His_DienstSel_LeverwijzeSel_LeverwijzeSel FOREIGN KEY (LeverwijzeSel) REFERENCES AutAut.LeverwijzeSel (ID);
ALTER TABLE AutAut.His_DienstZoeken ADD CONSTRAINT fk_His_DienstZoeken_Dienst_Dienst FOREIGN KEY (Dienst) REFERENCES AutAut.Dienst (ID);
ALTER TABLE AutAut.His_Dienstbundel ADD CONSTRAINT fk_His_Dienstbundel_Dienstbundel_Dienstbundel FOREIGN KEY (Dienstbundel) REFERENCES AutAut.Dienstbundel (ID);
ALTER TABLE AutAut.His_Levsautorisatie ADD CONSTRAINT fk_His_Levsautorisatie_Levsautorisatie_Levsautorisatie FOREIGN KEY (Levsautorisatie) REFERENCES AutAut.Levsautorisatie (ID);
ALTER TABLE AutAut.His_Levsautorisatie ADD CONSTRAINT fk_His_Levsautorisatie_Protocolleringsniveau_Protocolleringsniv FOREIGN KEY (Protocolleringsniveau) REFERENCES AutAut.Protocolleringsniveau (ID);
ALTER TABLE AutAut.His_Seltaak ADD CONSTRAINT fk_His_Seltaak_Seltaak_Seltaak FOREIGN KEY (Seltaak) REFERENCES AutAut.Seltaak (ID);
ALTER TABLE AutAut.His_SeltaakStatus ADD CONSTRAINT fk_His_SeltaakStatus_Seltaak_Seltaak FOREIGN KEY (Seltaak) REFERENCES AutAut.Seltaak (ID);
ALTER TABLE AutAut.His_SeltaakStatus ADD CONSTRAINT fk_His_SeltaakStatus_Status_SeltaakStatus FOREIGN KEY (Status) REFERENCES AutAut.SeltaakStatus (ID);
ALTER TABLE AutAut.His_ToegangBijhautorisatie ADD CONSTRAINT fk_His_ToegangBijhautorisatie_ToegangBijhautorisatie_ToegangBij FOREIGN KEY (ToegangBijhautorisatie) REFERENCES AutAut.ToegangBijhautorisatie (ID);
ALTER TABLE AutAut.His_ToegangLevsautorisatie ADD CONSTRAINT fk_His_ToegangLevsautorisatie_ToegangLevsautorisatie_ToegangLev FOREIGN KEY (ToegangLevsautorisatie) REFERENCES AutAut.ToegangLevsautorisatie (ID);
ALTER TABLE AutAut.Levsautorisatie ADD CONSTRAINT fk_Levsautorisatie_Stelsel_Stelsel FOREIGN KEY (Stelsel) REFERENCES Kern.Stelsel (ID);
ALTER TABLE AutAut.Levsautorisatie ADD CONSTRAINT fk_Levsautorisatie_Protocolleringsniveau_Protocolleringsniveau FOREIGN KEY (Protocolleringsniveau) REFERENCES AutAut.Protocolleringsniveau (ID);
ALTER TABLE AutAut.Seltaak ADD CONSTRAINT fk_Seltaak_Dienst_Dienst FOREIGN KEY (Dienst) REFERENCES AutAut.Dienst (ID);
ALTER TABLE AutAut.Seltaak ADD CONSTRAINT fk_Seltaak_ToegangLevsautorisatie_ToegangLevsautorisatie FOREIGN KEY (ToegangLevsautorisatie) REFERENCES AutAut.ToegangLevsautorisatie (ID);
ALTER TABLE AutAut.Seltaak ADD CONSTRAINT fk_Seltaak_Status_SeltaakStatus FOREIGN KEY (Status) REFERENCES AutAut.SeltaakStatus (ID);
ALTER TABLE AutAut.Seltaak ADD CONSTRAINT fk_Seltaak_UitgevoerdIn_Selrun FOREIGN KEY (UitgevoerdIn) REFERENCES AutAut.Selrun (ID);
ALTER TABLE AutAut.ToegangBijhautorisatie ADD CONSTRAINT fk_ToegangBijhautorisatie_Geautoriseerde_PartijRol FOREIGN KEY (Geautoriseerde) REFERENCES Kern.PartijRol (ID);
ALTER TABLE AutAut.ToegangBijhautorisatie ADD CONSTRAINT fk_ToegangBijhautorisatie_Bijhautorisatie_Bijhautorisatie FOREIGN KEY (Bijhautorisatie) REFERENCES AutAut.Bijhautorisatie (ID);
ALTER TABLE AutAut.ToegangBijhautorisatie ADD CONSTRAINT fk_ToegangBijhautorisatie_Ondertekenaar_Partij FOREIGN KEY (Ondertekenaar) REFERENCES Kern.Partij (ID);
ALTER TABLE AutAut.ToegangBijhautorisatie ADD CONSTRAINT fk_ToegangBijhautorisatie_Transporteur_Partij FOREIGN KEY (Transporteur) REFERENCES Kern.Partij (ID);
ALTER TABLE AutAut.ToegangLevsautorisatie ADD CONSTRAINT fk_ToegangLevsautorisatie_Geautoriseerde_PartijRol FOREIGN KEY (Geautoriseerde) REFERENCES Kern.PartijRol (ID);
ALTER TABLE AutAut.ToegangLevsautorisatie ADD CONSTRAINT fk_ToegangLevsautorisatie_Levsautorisatie_Levsautorisatie FOREIGN KEY (Levsautorisatie) REFERENCES AutAut.Levsautorisatie (ID);
ALTER TABLE AutAut.ToegangLevsautorisatie ADD CONSTRAINT fk_ToegangLevsautorisatie_Ondertekenaar_Partij FOREIGN KEY (Ondertekenaar) REFERENCES Kern.Partij (ID);
ALTER TABLE AutAut.ToegangLevsautorisatie ADD CONSTRAINT fk_ToegangLevsautorisatie_Transporteur_Partij FOREIGN KEY (Transporteur) REFERENCES Kern.Partij (ID);
ALTER TABLE Beh.SrtBerVrijBer ADD CONSTRAINT fk_SrtBerVrijBer_SrtBer_SrtBer FOREIGN KEY (SrtBer) REFERENCES Kern.SrtBer (ID);
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
ALTER TABLE Kern.AutVanAfgifteBLPersnr ADD CONSTRAINT fk_AutVanAfgifteBLPersnr_LandGebied_LandGebied FOREIGN KEY (LandGebied) REFERENCES Kern.LandGebied (ID);
ALTER TABLE Kern.AutVanAfgifteBLPersnr ADD CONSTRAINT fk_AutVanAfgifteBLPersnr_Nation_Nation FOREIGN KEY (Nation) REFERENCES Kern.Nation (ID);
ALTER TABLE Kern.Element ADD CONSTRAINT fk_Element_Srt_SrtElement FOREIGN KEY (Srt) REFERENCES Kern.SrtElement (ID);
ALTER TABLE Kern.Element ADD CONSTRAINT fk_Element_Objecttype_Element FOREIGN KEY (Objecttype) REFERENCES Kern.Element (ID);
ALTER TABLE Kern.Element ADD CONSTRAINT fk_Element_Groep_Element FOREIGN KEY (Groep) REFERENCES Kern.Element (ID);
ALTER TABLE Kern.Element ADD CONSTRAINT fk_Element_AliasVan_Element FOREIGN KEY (AliasVan) REFERENCES Kern.Element (ID);
ALTER TABLE Kern.Element ADD CONSTRAINT fk_Element_Autorisatie_SrtElementAutorisatie FOREIGN KEY (Autorisatie) REFERENCES Kern.SrtElementAutorisatie (ID);
ALTER TABLE Kern.Element ADD CONSTRAINT fk_Element_Tabel_Element FOREIGN KEY (Tabel) REFERENCES Kern.Element (ID);
ALTER TABLE Kern.Element ADD CONSTRAINT fk_Element_HisTabel_Element FOREIGN KEY (HisTabel) REFERENCES Kern.Element (ID);
ALTER TABLE Kern.Element ADD CONSTRAINT fk_Element_AGAttr_Element FOREIGN KEY (AGAttr) REFERENCES Kern.Element (ID);
ALTER TABLE Kern.Gem ADD CONSTRAINT fk_Gem_Partij_Partij FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.Gem ADD CONSTRAINT fk_Gem_VoortzettendeGem_Gem FOREIGN KEY (VoortzettendeGem) REFERENCES Kern.Gem (ID);
ALTER TABLE Kern.His_Partij ADD CONSTRAINT fk_His_Partij_Partij_Partij FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.His_Partij ADD CONSTRAINT fk_His_Partij_Srt_SrtPartij FOREIGN KEY (Srt) REFERENCES Kern.SrtPartij (ID);
ALTER TABLE Kern.His_PartijBijhouding ADD CONSTRAINT fk_His_PartijBijhouding_Partij_Partij FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.His_PartijVrijBer ADD CONSTRAINT fk_His_PartijVrijBer_Partij_Partij FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.His_PartijVrijBer ADD CONSTRAINT fk_His_PartijVrijBer_OndertekenaarVrijBer_Partij FOREIGN KEY (OndertekenaarVrijBer) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.His_PartijVrijBer ADD CONSTRAINT fk_His_PartijVrijBer_TransporteurVrijBer_Partij FOREIGN KEY (TransporteurVrijBer) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.His_PartijRol ADD CONSTRAINT fk_His_PartijRol_PartijRol_PartijRol FOREIGN KEY (PartijRol) REFERENCES Kern.PartijRol (ID);
ALTER TABLE Kern.Koppelvlak ADD CONSTRAINT fk_Koppelvlak_Stelsel_Stelsel FOREIGN KEY (Stelsel) REFERENCES Kern.Stelsel (ID);
ALTER TABLE Kern.Partij ADD CONSTRAINT fk_Partij_Srt_SrtPartij FOREIGN KEY (Srt) REFERENCES Kern.SrtPartij (ID);
ALTER TABLE Kern.Partij ADD CONSTRAINT fk_Partij_OndertekenaarVrijBer_Partij FOREIGN KEY (OndertekenaarVrijBer) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.Partij ADD CONSTRAINT fk_Partij_TransporteurVrijBer_Partij FOREIGN KEY (TransporteurVrijBer) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.PartijRol ADD CONSTRAINT fk_PartijRol_Partij_Partij FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.PartijRol ADD CONSTRAINT fk_PartijRol_Rol_Rol FOREIGN KEY (Rol) REFERENCES Kern.Rol (ID);
ALTER TABLE Kern.Regel ADD CONSTRAINT fk_Regel_SrtMelding_SrtMelding FOREIGN KEY (SrtMelding) REFERENCES Kern.SrtMelding (ID);
ALTER TABLE Kern.SrtActieBrongebruik ADD CONSTRAINT fk_SrtActieBrongebruik_SrtActie_SrtActie FOREIGN KEY (SrtActie) REFERENCES Kern.SrtActie (ID);
ALTER TABLE Kern.SrtActieBrongebruik ADD CONSTRAINT fk_SrtActieBrongebruik_SrtAdmHnd_SrtAdmHnd FOREIGN KEY (SrtAdmHnd) REFERENCES Kern.SrtAdmHnd (ID);
ALTER TABLE Kern.SrtActieBrongebruik ADD CONSTRAINT fk_SrtActieBrongebruik_SrtDoc_SrtDoc FOREIGN KEY (SrtDoc) REFERENCES Kern.SrtDoc (ID);
ALTER TABLE Kern.SrtAdmHnd ADD CONSTRAINT fk_SrtAdmHnd_CategorieAdmHnd_CategorieAdmHnd FOREIGN KEY (CategorieAdmHnd) REFERENCES Kern.CategorieAdmHnd (ID);
ALTER TABLE Kern.SrtAdmHnd ADD CONSTRAINT fk_SrtAdmHnd_Koppelvlak_Koppelvlak FOREIGN KEY (Koppelvlak) REFERENCES Kern.Koppelvlak (ID);
ALTER TABLE Kern.SrtAdmHnd ADD CONSTRAINT fk_SrtAdmHnd_Module_BurgerzakenModule FOREIGN KEY (Module) REFERENCES Kern.BurgerzakenModule (ID);
ALTER TABLE Kern.SrtAdmHnd ADD CONSTRAINT fk_SrtAdmHnd_Alias_SrtAdmHnd FOREIGN KEY (Alias) REFERENCES Kern.SrtAdmHnd (ID);
ALTER TABLE Kern.SrtBer ADD CONSTRAINT fk_SrtBer_Module_BurgerzakenModule FOREIGN KEY (Module) REFERENCES Kern.BurgerzakenModule (ID);
ALTER TABLE Kern.SrtBer ADD CONSTRAINT fk_SrtBer_Koppelvlak_Koppelvlak FOREIGN KEY (Koppelvlak) REFERENCES Kern.Koppelvlak (ID);
ALTER TABLE Kern.SrtBerElement ADD CONSTRAINT fk_SrtBerElement_SrtBer_SrtBer FOREIGN KEY (SrtBer) REFERENCES Kern.SrtBer (ID);
ALTER TABLE Kern.SrtBerElement ADD CONSTRAINT fk_SrtBerElement_Element_Element FOREIGN KEY (Element) REFERENCES Kern.Element (ID);
ALTER TABLE VerConv.LO3SrtMelding ADD CONSTRAINT fk_LO3SrtMelding_CategorieMelding_LO3CategorieMelding FOREIGN KEY (CategorieMelding) REFERENCES VerConv.LO3CategorieMelding (ID);
ALTER TABLE AutAut.PersAfnemerindicatie ADD CONSTRAINT fk_PersAfnemerindicatie_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE AutAut.PersAfnemerindicatie ADD CONSTRAINT fk_PersAfnemerindicatie_Partij_Partij FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE AutAut.PersAfnemerindicatie ADD CONSTRAINT fk_PersAfnemerindicatie_Levsautorisatie_Levsautorisatie FOREIGN KEY (Levsautorisatie) REFERENCES AutAut.Levsautorisatie (ID);
ALTER TABLE Beh.VrijBer ADD CONSTRAINT fk_VrijBer_SrtBer_SrtBerVrijBer FOREIGN KEY (SrtBer) REFERENCES Beh.SrtBerVrijBer (ID);
ALTER TABLE Beh.VrijBer ADD CONSTRAINT fk_VrijBer_Srt_SrtVrijBer FOREIGN KEY (Srt) REFERENCES Kern.SrtVrijBer (ID);
ALTER TABLE Beh.VrijBerPartij ADD CONSTRAINT fk_VrijBerPartij_VrijBer_VrijBer FOREIGN KEY (VrijBer) REFERENCES Beh.VrijBer (ID);
ALTER TABLE Beh.VrijBerPartij ADD CONSTRAINT fk_VrijBerPartij_Partij_Partij FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
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
ALTER TABLE Kern.AdmHnd ADD CONSTRAINT fk_AdmHnd_StatusLev_StatusLevAdmHnd FOREIGN KEY (StatusLev) REFERENCES Kern.StatusLevAdmHnd (ID);
ALTER TABLE Kern.AdmHndGedeblokkeerdeRegel ADD CONSTRAINT fk_AdmHndGedeblokkeerdeRegel_AdmHnd_AdmHnd FOREIGN KEY (AdmHnd) REFERENCES Kern.AdmHnd (ID);
ALTER TABLE Kern.AdmHndGedeblokkeerdeRegel ADD CONSTRAINT fk_AdmHndGedeblokkeerdeRegel_Regel_Regel FOREIGN KEY (Regel) REFERENCES Kern.Regel (ID);
ALTER TABLE Kern.Betr ADD CONSTRAINT fk_Betr_Relatie_Relatie FOREIGN KEY (Relatie) REFERENCES Kern.Relatie (ID);
ALTER TABLE Kern.Betr ADD CONSTRAINT fk_Betr_Rol_SrtBetr FOREIGN KEY (Rol) REFERENCES Kern.SrtBetr (ID);
ALTER TABLE Kern.Betr ADD CONSTRAINT fk_Betr_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.Doc ADD CONSTRAINT fk_Doc_Srt_SrtDoc FOREIGN KEY (Srt) REFERENCES Kern.SrtDoc (ID);
ALTER TABLE Kern.Doc ADD CONSTRAINT fk_Doc_Partij_Partij FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.GegevenInOnderzoek ADD CONSTRAINT fk_GegevenInOnderzoek_Onderzoek_Onderzoek FOREIGN KEY (Onderzoek) REFERENCES Kern.Onderzoek (ID);
ALTER TABLE Kern.GegevenInOnderzoek ADD CONSTRAINT fk_GegevenInOnderzoek_Element_Element FOREIGN KEY (Element) REFERENCES Kern.Element (ID);
ALTER TABLE Kern.Onderzoek ADD CONSTRAINT fk_Onderzoek_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.Onderzoek ADD CONSTRAINT fk_Onderzoek_Partij_Partij FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.Onderzoek ADD CONSTRAINT fk_Onderzoek_Status_StatusOnderzoek FOREIGN KEY (Status) REFERENCES Kern.StatusOnderzoek (ID);
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
ALTER TABLE Kern.PersAdres ADD CONSTRAINT fk_PersAdres_Srt_SrtAdres FOREIGN KEY (Srt) REFERENCES Kern.SrtAdres (ID);
ALTER TABLE Kern.PersAdres ADD CONSTRAINT fk_PersAdres_RdnWijz_RdnWijzVerblijf FOREIGN KEY (RdnWijz) REFERENCES Kern.RdnWijzVerblijf (ID);
ALTER TABLE Kern.PersAdres ADD CONSTRAINT fk_PersAdres_AangAdresh_Aang FOREIGN KEY (AangAdresh) REFERENCES Kern.Aang (ID);
ALTER TABLE Kern.PersAdres ADD CONSTRAINT fk_PersAdres_Gem_Gem FOREIGN KEY (Gem) REFERENCES Kern.Gem (ID);
ALTER TABLE Kern.PersAdres ADD CONSTRAINT fk_PersAdres_LandGebied_LandGebied FOREIGN KEY (LandGebied) REFERENCES Kern.LandGebied (ID);
ALTER TABLE Kern.PersBLPersnr ADD CONSTRAINT fk_PersBLPersnr_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.PersBLPersnr ADD CONSTRAINT fk_PersBLPersnr_AutVanAfgifte_AutVanAfgifteBLPersnr FOREIGN KEY (AutVanAfgifte) REFERENCES Kern.AutVanAfgifteBLPersnr (ID);
ALTER TABLE Kern.PersGeslnaamcomp ADD CONSTRAINT fk_PersGeslnaamcomp_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.PersGeslnaamcomp ADD CONSTRAINT fk_PersGeslnaamcomp_Predicaat_Predicaat FOREIGN KEY (Predicaat) REFERENCES Kern.Predicaat (ID);
ALTER TABLE Kern.PersGeslnaamcomp ADD CONSTRAINT fk_PersGeslnaamcomp_AdellijkeTitel_AdellijkeTitel FOREIGN KEY (AdellijkeTitel) REFERENCES Kern.AdellijkeTitel (ID);
ALTER TABLE Kern.PersIndicatie ADD CONSTRAINT fk_PersIndicatie_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.PersIndicatie ADD CONSTRAINT fk_PersIndicatie_Srt_SrtIndicatie FOREIGN KEY (Srt) REFERENCES Kern.SrtIndicatie (ID);
ALTER TABLE Kern.PersNation ADD CONSTRAINT fk_PersNation_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.PersNation ADD CONSTRAINT fk_PersNation_Nation_Nation FOREIGN KEY (Nation) REFERENCES Kern.Nation (ID);
ALTER TABLE Kern.PersNation ADD CONSTRAINT fk_PersNation_RdnVerk_RdnVerkNLNation FOREIGN KEY (RdnVerk) REFERENCES Kern.RdnVerkNLNation (ID);
ALTER TABLE Kern.PersNation ADD CONSTRAINT fk_PersNation_RdnVerlies_RdnVerliesNLNation FOREIGN KEY (RdnVerlies) REFERENCES Kern.RdnVerliesNLNation (ID);
ALTER TABLE Kern.PersReisdoc ADD CONSTRAINT fk_PersReisdoc_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.PersReisdoc ADD CONSTRAINT fk_PersReisdoc_Srt_SrtNLReisdoc FOREIGN KEY (Srt) REFERENCES Kern.SrtNLReisdoc (ID);
ALTER TABLE Kern.PersReisdoc ADD CONSTRAINT fk_PersReisdoc_AandInhingVermissing_AandInhingVermissingReisdoc FOREIGN KEY (AandInhingVermissing) REFERENCES Kern.AandInhingVermissingReisdoc (ID);
ALTER TABLE Kern.PersVerificatie ADD CONSTRAINT fk_PersVerificatie_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.PersVerificatie ADD CONSTRAINT fk_PersVerificatie_Partij_Partij FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.PersVerstrbeperking ADD CONSTRAINT fk_PersVerstrbeperking_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.PersVerstrbeperking ADD CONSTRAINT fk_PersVerstrbeperking_Partij_Partij FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.PersVerstrbeperking ADD CONSTRAINT fk_PersVerstrbeperking_GemVerordening_Partij FOREIGN KEY (GemVerordening) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.PersVoornaam ADD CONSTRAINT fk_PersVoornaam_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.PersCache ADD CONSTRAINT fk_PersCache_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.Relatie ADD CONSTRAINT fk_Relatie_Srt_SrtRelatie FOREIGN KEY (Srt) REFERENCES Kern.SrtRelatie (ID);
ALTER TABLE Kern.Relatie ADD CONSTRAINT fk_Relatie_GemAanv_Gem FOREIGN KEY (GemAanv) REFERENCES Kern.Gem (ID);
ALTER TABLE Kern.Relatie ADD CONSTRAINT fk_Relatie_LandGebiedAanv_LandGebied FOREIGN KEY (LandGebiedAanv) REFERENCES Kern.LandGebied (ID);
ALTER TABLE Kern.Relatie ADD CONSTRAINT fk_Relatie_RdnEinde_RdnEindeRelatie FOREIGN KEY (RdnEinde) REFERENCES Kern.RdnEindeRelatie (ID);
ALTER TABLE Kern.Relatie ADD CONSTRAINT fk_Relatie_GemEinde_Gem FOREIGN KEY (GemEinde) REFERENCES Kern.Gem (ID);
ALTER TABLE Kern.Relatie ADD CONSTRAINT fk_Relatie_LandGebiedEinde_LandGebied FOREIGN KEY (LandGebiedEinde) REFERENCES Kern.LandGebied (ID);
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
ALTER TABLE Kern.His_GegevenInOnderzoek ADD CONSTRAINT fk_His_GegevenInOnderzoek_GegevenInOnderzoek_GegevenInOnderzoek FOREIGN KEY (GegevenInOnderzoek) REFERENCES Kern.GegevenInOnderzoek (ID);
ALTER TABLE Kern.His_GegevenInOnderzoek ADD CONSTRAINT fk_His_GegevenInOnderzoek_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_GegevenInOnderzoek ADD CONSTRAINT fk_His_GegevenInOnderzoek_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_GegevenInOnderzoek ADD CONSTRAINT fk_His_GegevenInOnderzoek_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Onderzoek ADD CONSTRAINT fk_His_Onderzoek_Onderzoek_Onderzoek FOREIGN KEY (Onderzoek) REFERENCES Kern.Onderzoek (ID);
ALTER TABLE Kern.His_Onderzoek ADD CONSTRAINT fk_His_Onderzoek_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Onderzoek ADD CONSTRAINT fk_His_Onderzoek_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Onderzoek ADD CONSTRAINT fk_His_Onderzoek_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Onderzoek ADD CONSTRAINT fk_His_Onderzoek_Status_StatusOnderzoek FOREIGN KEY (Status) REFERENCES Kern.StatusOnderzoek (ID);
ALTER TABLE Kern.His_OuderOuderlijkGezag ADD CONSTRAINT fk_His_OuderOuderlijkGezag_Betr_Betr FOREIGN KEY (Betr) REFERENCES Kern.Betr (ID);
ALTER TABLE Kern.His_OuderOuderlijkGezag ADD CONSTRAINT fk_His_OuderOuderlijkGezag_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_OuderOuderlijkGezag ADD CONSTRAINT fk_His_OuderOuderlijkGezag_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_OuderOuderlijkGezag ADD CONSTRAINT fk_His_OuderOuderlijkGezag_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_OuderOuderlijkGezag ADD CONSTRAINT fk_His_OuderOuderlijkGezag_ActieAanpGel_Actie FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_OuderOuderschap ADD CONSTRAINT fk_His_OuderOuderschap_Betr_Betr FOREIGN KEY (Betr) REFERENCES Kern.Betr (ID);
ALTER TABLE Kern.His_OuderOuderschap ADD CONSTRAINT fk_His_OuderOuderschap_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_OuderOuderschap ADD CONSTRAINT fk_His_OuderOuderschap_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_OuderOuderschap ADD CONSTRAINT fk_His_OuderOuderschap_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_OuderOuderschap ADD CONSTRAINT fk_His_OuderOuderschap_ActieAanpGel_Actie FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersAfgeleidAdministrati ADD CONSTRAINT fk_His_PersAfgeleidAdministrati_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_PersAfgeleidAdministrati ADD CONSTRAINT fk_His_PersAfgeleidAdministrati_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersAfgeleidAdministrati ADD CONSTRAINT fk_His_PersAfgeleidAdministrati_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersAfgeleidAdministrati ADD CONSTRAINT fk_His_PersAfgeleidAdministrati_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersAfgeleidAdministrati ADD CONSTRAINT fk_His_PersAfgeleidAdministrati_AdmHnd_AdmHnd FOREIGN KEY (AdmHnd) REFERENCES Kern.AdmHnd (ID);
ALTER TABLE Kern.His_PersBijhouding ADD CONSTRAINT fk_His_PersBijhouding_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_PersBijhouding ADD CONSTRAINT fk_His_PersBijhouding_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersBijhouding ADD CONSTRAINT fk_His_PersBijhouding_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersBijhouding ADD CONSTRAINT fk_His_PersBijhouding_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersBijhouding ADD CONSTRAINT fk_His_PersBijhouding_ActieAanpGel_Actie FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
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
ALTER TABLE Kern.His_PersGeslachtsaand ADD CONSTRAINT fk_His_PersGeslachtsaand_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersGeslachtsaand ADD CONSTRAINT fk_His_PersGeslachtsaand_ActieAanpGel_Actie FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersGeslachtsaand ADD CONSTRAINT fk_His_PersGeslachtsaand_Geslachtsaand_Geslachtsaand FOREIGN KEY (Geslachtsaand) REFERENCES Kern.Geslachtsaand (ID);
ALTER TABLE Kern.His_PersIDs ADD CONSTRAINT fk_His_PersIDs_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_PersIDs ADD CONSTRAINT fk_His_PersIDs_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersIDs ADD CONSTRAINT fk_His_PersIDs_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersIDs ADD CONSTRAINT fk_His_PersIDs_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersIDs ADD CONSTRAINT fk_His_PersIDs_ActieAanpGel_Actie FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersInschr ADD CONSTRAINT fk_His_PersInschr_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_PersInschr ADD CONSTRAINT fk_His_PersInschr_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersInschr ADD CONSTRAINT fk_His_PersInschr_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersInschr ADD CONSTRAINT fk_His_PersInschr_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersMigratie ADD CONSTRAINT fk_His_PersMigratie_Pers_Pers FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_PersMigratie ADD CONSTRAINT fk_His_PersMigratie_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersMigratie ADD CONSTRAINT fk_His_PersMigratie_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersMigratie ADD CONSTRAINT fk_His_PersMigratie_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersMigratie ADD CONSTRAINT fk_His_PersMigratie_ActieAanpGel_Actie FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
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
ALTER TABLE Kern.His_PersNrverwijzing ADD CONSTRAINT fk_His_PersNrverwijzing_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersNrverwijzing ADD CONSTRAINT fk_His_PersNrverwijzing_ActieAanpGel_Actie FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
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
ALTER TABLE Kern.His_PersSamengesteldeNaam ADD CONSTRAINT fk_His_PersSamengesteldeNaam_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersSamengesteldeNaam ADD CONSTRAINT fk_His_PersSamengesteldeNaam_ActieAanpGel_Actie FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
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
ALTER TABLE Kern.His_PersAdres ADD CONSTRAINT fk_His_PersAdres_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersAdres ADD CONSTRAINT fk_His_PersAdres_ActieAanpGel_Actie FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersAdres ADD CONSTRAINT fk_His_PersAdres_Srt_SrtAdres FOREIGN KEY (Srt) REFERENCES Kern.SrtAdres (ID);
ALTER TABLE Kern.His_PersAdres ADD CONSTRAINT fk_His_PersAdres_RdnWijz_RdnWijzVerblijf FOREIGN KEY (RdnWijz) REFERENCES Kern.RdnWijzVerblijf (ID);
ALTER TABLE Kern.His_PersAdres ADD CONSTRAINT fk_His_PersAdres_AangAdresh_Aang FOREIGN KEY (AangAdresh) REFERENCES Kern.Aang (ID);
ALTER TABLE Kern.His_PersAdres ADD CONSTRAINT fk_His_PersAdres_Gem_Gem FOREIGN KEY (Gem) REFERENCES Kern.Gem (ID);
ALTER TABLE Kern.His_PersAdres ADD CONSTRAINT fk_His_PersAdres_LandGebied_LandGebied FOREIGN KEY (LandGebied) REFERENCES Kern.LandGebied (ID);
ALTER TABLE Kern.His_PersBLPersnr ADD CONSTRAINT fk_His_PersBLPersnr_PersBLPersnr_PersBLPersnr FOREIGN KEY (PersBLPersnr) REFERENCES Kern.PersBLPersnr (ID);
ALTER TABLE Kern.His_PersBLPersnr ADD CONSTRAINT fk_His_PersBLPersnr_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersBLPersnr ADD CONSTRAINT fk_His_PersBLPersnr_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersBLPersnr ADD CONSTRAINT fk_His_PersBLPersnr_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersGeslnaamcomp ADD CONSTRAINT fk_His_PersGeslnaamcomp_PersGeslnaamcomp_PersGeslnaamcomp FOREIGN KEY (PersGeslnaamcomp) REFERENCES Kern.PersGeslnaamcomp (ID);
ALTER TABLE Kern.His_PersGeslnaamcomp ADD CONSTRAINT fk_His_PersGeslnaamcomp_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersGeslnaamcomp ADD CONSTRAINT fk_His_PersGeslnaamcomp_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersGeslnaamcomp ADD CONSTRAINT fk_His_PersGeslnaamcomp_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersGeslnaamcomp ADD CONSTRAINT fk_His_PersGeslnaamcomp_ActieAanpGel_Actie FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersGeslnaamcomp ADD CONSTRAINT fk_His_PersGeslnaamcomp_Predicaat_Predicaat FOREIGN KEY (Predicaat) REFERENCES Kern.Predicaat (ID);
ALTER TABLE Kern.His_PersGeslnaamcomp ADD CONSTRAINT fk_His_PersGeslnaamcomp_AdellijkeTitel_AdellijkeTitel FOREIGN KEY (AdellijkeTitel) REFERENCES Kern.AdellijkeTitel (ID);
ALTER TABLE Kern.His_PersIndicatie ADD CONSTRAINT fk_His_PersIndicatie_PersIndicatie_PersIndicatie FOREIGN KEY (PersIndicatie) REFERENCES Kern.PersIndicatie (ID);
ALTER TABLE Kern.His_PersIndicatie ADD CONSTRAINT fk_His_PersIndicatie_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersIndicatie ADD CONSTRAINT fk_His_PersIndicatie_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersIndicatie ADD CONSTRAINT fk_His_PersIndicatie_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersIndicatie ADD CONSTRAINT fk_His_PersIndicatie_ActieAanpGel_Actie FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersNation ADD CONSTRAINT fk_His_PersNation_PersNation_PersNation FOREIGN KEY (PersNation) REFERENCES Kern.PersNation (ID);
ALTER TABLE Kern.His_PersNation ADD CONSTRAINT fk_His_PersNation_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersNation ADD CONSTRAINT fk_His_PersNation_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersNation ADD CONSTRAINT fk_His_PersNation_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersNation ADD CONSTRAINT fk_His_PersNation_ActieAanpGel_Actie FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersNation ADD CONSTRAINT fk_His_PersNation_RdnVerk_RdnVerkNLNation FOREIGN KEY (RdnVerk) REFERENCES Kern.RdnVerkNLNation (ID);
ALTER TABLE Kern.His_PersNation ADD CONSTRAINT fk_His_PersNation_RdnVerlies_RdnVerliesNLNation FOREIGN KEY (RdnVerlies) REFERENCES Kern.RdnVerliesNLNation (ID);
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
ALTER TABLE Kern.His_PersVoornaam ADD CONSTRAINT fk_His_PersVoornaam_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersVoornaam ADD CONSTRAINT fk_His_PersVoornaam_ActieAanpGel_Actie FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Relatie ADD CONSTRAINT fk_His_Relatie_Relatie_Relatie FOREIGN KEY (Relatie) REFERENCES Kern.Relatie (ID);
ALTER TABLE Kern.His_Relatie ADD CONSTRAINT fk_His_Relatie_ActieInh_Actie FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Relatie ADD CONSTRAINT fk_His_Relatie_ActieVerval_Actie FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Relatie ADD CONSTRAINT fk_His_Relatie_ActieVervalTbvLevMuts_Actie FOREIGN KEY (ActieVervalTbvLevMuts) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Relatie ADD CONSTRAINT fk_His_Relatie_GemAanv_Gem FOREIGN KEY (GemAanv) REFERENCES Kern.Gem (ID);
ALTER TABLE Kern.His_Relatie ADD CONSTRAINT fk_His_Relatie_LandGebiedAanv_LandGebied FOREIGN KEY (LandGebiedAanv) REFERENCES Kern.LandGebied (ID);
ALTER TABLE Kern.His_Relatie ADD CONSTRAINT fk_His_Relatie_RdnEinde_RdnEindeRelatie FOREIGN KEY (RdnEinde) REFERENCES Kern.RdnEindeRelatie (ID);
ALTER TABLE Kern.His_Relatie ADD CONSTRAINT fk_His_Relatie_GemEinde_Gem FOREIGN KEY (GemEinde) REFERENCES Kern.Gem (ID);
ALTER TABLE Kern.His_Relatie ADD CONSTRAINT fk_His_Relatie_LandGebiedEinde_LandGebied FOREIGN KEY (LandGebiedEinde) REFERENCES Kern.LandGebied (ID);

