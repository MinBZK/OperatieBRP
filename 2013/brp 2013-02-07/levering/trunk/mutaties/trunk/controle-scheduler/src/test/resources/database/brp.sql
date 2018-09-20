--------------------------------------------------------------------------------
-- PostgreSQL SQL-DDL bestand voor Basisregistratie Personen (BRP)
--------------------------------------------------------------------------------
-- 
-- Gegenereerd door BRP Meta Register.
-- Gegenereerd op: Monday 14 Jan 2013 11:40:00
-- 
--------------------------------------------------------------------------------


-- Schemas ---------------------------------------------------------------------
DROP SCHEMA IF EXISTS AutAut CASCADE;
CREATE SCHEMA AutAut;
DROP SCHEMA IF EXISTS BRM CASCADE;
CREATE SCHEMA BRM;
DROP SCHEMA IF EXISTS Ber CASCADE;
CREATE SCHEMA Ber;
DROP SCHEMA IF EXISTS Kern CASCADE;
CREATE SCHEMA Kern;
DROP SCHEMA IF EXISTS Lev CASCADE;
CREATE SCHEMA Lev;


-- Stamtabellen ----------------------------------------------------------------
CREATE SEQUENCE AutAut.seq_Authenticatiemiddel;
CREATE TABLE AutAut.Authenticatiemiddel (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_Authenticatiemiddel')  /* AuthenticatiemiddelID */,
   Partij                        Smallint                      NOT NULL    /* PartijID */,
   Rol                           Smallint                                  /* RolID */,
   Functie                       Smallint                                  /* FunctieID */,
   CertificaatTbvSSL             Integer                                   /* CertificaatID */,
   CertificaatTbvOndertekening   Integer                                   /* CertificaatID */,
   IPAdres                       Varchar(15)                               /* IPAdres */,
   AuthenticatiemiddelStatusHis  Varchar(1) CHECK (AuthenticatiemiddelStatusHis IS NULL OR AuthenticatiemiddelStatusHis IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   CONSTRAINT R4497 PRIMARY KEY (ID)
);
ALTER SEQUENCE AutAut.seq_Authenticatiemiddel OWNED BY AutAut.Authenticatiemiddel.ID;
CREATE SEQUENCE AutAut.seq_Autorisatiebesluit;
CREATE TABLE AutAut.Autorisatiebesluit (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_Autorisatiebesluit')  /* AutorisatiebesluitID */,
   Srt                           Smallint                      NOT NULL    /* SrtAutorisatiebesluitID */,
   Besluittekst                  Text                          NOT NULL    /* TekstUitBesluit */,
   Autoriseerder                 Smallint                      NOT NULL    /* PartijID */,
   IndModelBesluit               Boolean                       NOT NULL    /* JaNee */,
   GebaseerdOp                   Integer                                   /* AutorisatiebesluitID */,
   IndIngetrokken                Boolean                                   /* JaNee */,
   DatBesluit                    Integer                                   /* Dat */,
   DatIngang                     Integer                                   /* Dat */,
   DatEinde                      Integer                                   /* Dat */,
   AutorisatiebesluitStatusHis   Varchar(1) CHECK (AutorisatiebesluitStatusHis IS NULL OR AutorisatiebesluitStatusHis IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   Toestand                      Smallint                                  /* ToestandsID */,
   BijhautorisatiebesluitStatus  Varchar(1) CHECK (BijhautorisatiebesluitStatus IS NULL OR BijhautorisatiebesluitStatus IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   CONSTRAINT R4978 PRIMARY KEY (ID)
);
ALTER SEQUENCE AutAut.seq_Autorisatiebesluit OWNED BY AutAut.Autorisatiebesluit.ID;
CREATE SEQUENCE AutAut.seq_Bijhautorisatie;
CREATE TABLE AutAut.Bijhautorisatie (
   ID                            Smallint                      NOT NULL  DEFAULT nextval('AutAut.seq_Bijhautorisatie')  /* BijhautorisatieID */,
   Bijhautorisatiebesluit        Integer                       NOT NULL    /* AutorisatiebesluitID */,
   SrtBevoegdheid                Smallint                                  /* SrtBijhoudingID */,
   GeautoriseerdeSrtPartij       Smallint                                  /* SrtPartijID */,
   GeautoriseerdePartij          Smallint                                  /* PartijID */,
   Toestand                      Smallint                                  /* ToestandsID */,
   CategoriePersonen             Smallint                                  /* CategoriePersonenID */,
   Oms                           Varchar(250)                              /* OmsEnumeratiewaarde */,
   DatIngang                     Integer                                   /* Dat */,
   DatEinde                      Integer                                   /* Dat */,
   BijhautorisatieStatusHis      Varchar(1) CHECK (BijhautorisatieStatusHis IS NULL OR BijhautorisatieStatusHis IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   CONSTRAINT R5747 PRIMARY KEY (ID)
);
ALTER SEQUENCE AutAut.seq_Bijhautorisatie OWNED BY AutAut.Bijhautorisatie.ID;
CREATE SEQUENCE AutAut.seq_Bijhsituatie;
CREATE TABLE AutAut.Bijhsituatie (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_Bijhsituatie')  /* BijhsituatieID */,
   Bijhautorisatie               Smallint                      NOT NULL    /* BijhautorisatieID */,
   SrtAdmHnd                     Smallint                                  /* SrtAdmHndID */,
   SrtDoc                        Smallint                                  /* SrtDocID */,
   BijhsituatieStatusHis         Varchar(1) CHECK (BijhsituatieStatusHis IS NULL OR BijhsituatieStatusHis IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   CONSTRAINT R5748 PRIMARY KEY (ID),
   CONSTRAINT R5749 UNIQUE (Bijhautorisatie, SrtAdmHnd, SrtDoc)
);
ALTER SEQUENCE AutAut.seq_Bijhsituatie OWNED BY AutAut.Bijhsituatie.ID;
CREATE SEQUENCE AutAut.seq_Certificaat;
CREATE TABLE AutAut.Certificaat (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_Certificaat')  /* CertificaatID */,
   Subject                       Varchar(1024)                 NOT NULL    /* Certificaatsubject */,
   Serial                        Varchar(32)                   NOT NULL    /* Certificaatserial */,
   Signature                     Varchar(1024)                 NOT NULL    /* PubliekeSleutel */,
   CONSTRAINT R4727 PRIMARY KEY (ID),
   CONSTRAINT R4728 UNIQUE (Subject, Serial),
   CONSTRAINT R5037 UNIQUE (Signature)
);
ALTER SEQUENCE AutAut.seq_Certificaat OWNED BY AutAut.Certificaat.ID;
CREATE SEQUENCE AutAut.seq_Doelbinding;
CREATE TABLE AutAut.Doelbinding (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_Doelbinding')  /* DoelbindingID */,
   Levsautorisatiebesluit        Integer                       NOT NULL    /* AutorisatiebesluitID */,
   Geautoriseerde                Smallint                      NOT NULL    /* PartijID */,
   Protocolleringsniveau         Smallint                                  /* ProtocolleringsniveauID */,
   TekstDoelbinding              Text                                      /* TekstDoelbinding */,
   Populatiecriterium            Text                                      /* Populatiecriterium */,
   IndVerstrbeperkingHonoreren   Boolean                                   /* JaNee */,
   DoelbindingStatusHis          Varchar(1) CHECK (DoelbindingStatusHis IS NULL OR DoelbindingStatusHis IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   CONSTRAINT R4979 PRIMARY KEY (ID),
   CONSTRAINT R4980 UNIQUE (Geautoriseerde, Levsautorisatiebesluit)
);
ALTER SEQUENCE AutAut.seq_Doelbinding OWNED BY AutAut.Doelbinding.ID;
CREATE SEQUENCE AutAut.seq_DoelbindingGegevenselement;
CREATE TABLE AutAut.DoelbindingGegevenselement (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_DoelbindingGegevenselement')  /* GegevenselementDoelbindingID */,
   Doelbinding                   Integer                       NOT NULL    /* DoelbindingID */,
   Gegevenselement               Integer                       NOT NULL    /* DbObjectID */,
   CONSTRAINT R4981 PRIMARY KEY (ID),
   CONSTRAINT R4982 UNIQUE (Doelbinding, Gegevenselement)
);
ALTER SEQUENCE AutAut.seq_DoelbindingGegevenselement OWNED BY AutAut.DoelbindingGegevenselement.ID;
CREATE TABLE AutAut.Functie (
   ID                            Smallint                      NOT NULL    /* FunctieID */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   CONSTRAINT R4725 PRIMARY KEY (ID)
);
CREATE SEQUENCE AutAut.seq_His_Authenticatiemiddel;
CREATE TABLE AutAut.His_Authenticatiemiddel (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_His_Authenticatiemiddel')  /* His_AuthenticatiemiddelID */,
   Authenticatiemiddel           Integer                                   /* AuthenticatiemiddelID */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   Functie                       Smallint                                  /* FunctieID */,
   CertificaatTbvSSL             Integer                       NOT NULL    /* CertificaatID */,
   CertificaatTbvOndertekening   Integer                       NOT NULL    /* CertificaatID */,
   IPAdres                       Varchar(15)                               /* IPAdres */,
   CONSTRAINT R4691 PRIMARY KEY (ID),
   CONSTRAINT R4687 UNIQUE (Authenticatiemiddel, TsReg)
);
ALTER SEQUENCE AutAut.seq_His_Authenticatiemiddel OWNED BY AutAut.His_Authenticatiemiddel.ID;
CREATE SEQUENCE AutAut.seq_His_Autorisatiebesluit;
CREATE TABLE AutAut.His_Autorisatiebesluit (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_His_Autorisatiebesluit')  /* His_AutorisatiebesluitID */,
   Autorisatiebesluit            Integer                                   /* AutorisatiebesluitID */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   IndIngetrokken                Boolean                       NOT NULL    /* JaNee */,
   DatBesluit                    Integer                       NOT NULL    /* Dat */,
   DatIngang                     Integer                       NOT NULL    /* Dat */,
   DatEinde                      Integer                                   /* Dat */,
   CONSTRAINT R5000 PRIMARY KEY (ID),
   CONSTRAINT R4965 UNIQUE (Autorisatiebesluit, TsReg)
);
ALTER SEQUENCE AutAut.seq_His_Autorisatiebesluit OWNED BY AutAut.His_Autorisatiebesluit.ID;
CREATE SEQUENCE AutAut.seq_His_AutorisatiebesluitBijhau;
CREATE TABLE AutAut.His_AutorisatiebesluitBijhau (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_His_AutorisatiebesluitBijhau')  /* His_AutorisatiebesluitBijhau */,
   Autorisatiebesluit            Integer                                   /* AutorisatiebesluitID */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   Toestand                      Smallint                      NOT NULL    /* ToestandsID */,
   CONSTRAINT R5754 PRIMARY KEY (ID),
   CONSTRAINT R5729 UNIQUE (Autorisatiebesluit, TsReg)
);
ALTER SEQUENCE AutAut.seq_His_AutorisatiebesluitBijhau OWNED BY AutAut.His_AutorisatiebesluitBijhau.ID;
CREATE SEQUENCE AutAut.seq_His_Bijhautorisatie;
CREATE TABLE AutAut.His_Bijhautorisatie (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_His_Bijhautorisatie')  /* His_BijhautorisatieID */,
   Bijhautorisatie               Smallint                                  /* BijhautorisatieID */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   SrtBevoegdheid                Smallint                      NOT NULL    /* SrtBijhoudingID */,
   GeautoriseerdeSrtPartij       Smallint                                  /* SrtPartijID */,
   GeautoriseerdePartij          Smallint                                  /* PartijID */,
   Toestand                      Smallint                      NOT NULL    /* ToestandsID */,
   CategoriePersonen             Smallint                      NOT NULL    /* CategoriePersonenID */,
   Oms                           Varchar(250)                  NOT NULL    /* OmsEnumeratiewaarde */,
   DatIngang                     Integer                       NOT NULL    /* Dat */,
   DatEinde                      Integer                       NOT NULL    /* Dat */,
   CONSTRAINT R5757 PRIMARY KEY (ID),
   CONSTRAINT R5739 UNIQUE (Bijhautorisatie, TsReg)
);
ALTER SEQUENCE AutAut.seq_His_Bijhautorisatie OWNED BY AutAut.His_Bijhautorisatie.ID;
CREATE SEQUENCE AutAut.seq_His_Bijhsituatie;
CREATE TABLE AutAut.His_Bijhsituatie (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_His_Bijhsituatie')  /* His_BijhsituatieID */,
   Bijhsituatie                  Integer                                   /* BijhsituatieID */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   CONSTRAINT R5760 PRIMARY KEY (ID),
   CONSTRAINT R5745 UNIQUE (Bijhsituatie, TsReg)
);
ALTER SEQUENCE AutAut.seq_His_Bijhsituatie OWNED BY AutAut.His_Bijhsituatie.ID;
CREATE SEQUENCE AutAut.seq_His_Doelbinding;
CREATE TABLE AutAut.His_Doelbinding (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_His_Doelbinding')  /* His_DoelbindingID */,
   Doelbinding                   Integer                                   /* DoelbindingID */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   ActieAanpGel                  BigInt                                    /* ActieID */,
   Protocolleringsniveau         Smallint                      NOT NULL    /* ProtocolleringsniveauID */,
   TekstDoelbinding              Text                          NOT NULL    /* TekstDoelbinding */,
   Populatiecriterium            Text                          NOT NULL    /* Populatiecriterium */,
   IndVerstrbeperkingHonoreren   Boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT R5003 PRIMARY KEY (ID),
   CONSTRAINT R4977 UNIQUE (Doelbinding, TsReg, DatAanvGel)
);
ALTER SEQUENCE AutAut.seq_His_Doelbinding OWNED BY AutAut.His_Doelbinding.ID;
CREATE SEQUENCE AutAut.seq_His_Uitgeslotene;
CREATE TABLE AutAut.His_Uitgeslotene (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_His_Uitgeslotene')  /* His_UitgesloteneID */,
   Uitgeslotene                  Integer                                   /* AuthorisatiebesluitGegevense */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   CONSTRAINT R5795 PRIMARY KEY (ID),
   CONSTRAINT R5789 UNIQUE (Uitgeslotene, TsReg)
);
ALTER SEQUENCE AutAut.seq_His_Uitgeslotene OWNED BY AutAut.His_Uitgeslotene.ID;
CREATE TABLE AutAut.Protocolleringsniveau (
   ID                            Smallint                      NOT NULL    /* ProtocolleringsniveauID */,
   Code                          Smallint                      NOT NULL    /* ProtocolleringsniveauCode */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL    /* OmsEnumeratiewaarde */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   CONSTRAINT R4990 PRIMARY KEY (ID),
   CONSTRAINT R4991 UNIQUE (Naam),
   CONSTRAINT R4992 UNIQUE (Oms)
);
CREATE TABLE AutAut.SrtAutorisatiebesluit (
   ID                            Smallint                      NOT NULL    /* SrtAutorisatiebesluitID */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL    /* OmsEnumeratiewaarde */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   CONSTRAINT R4987 PRIMARY KEY (ID),
   CONSTRAINT R4988 UNIQUE (Oms)
);
CREATE TABLE AutAut.SrtBevoegdheid (
   ID                            Smallint                      NOT NULL    /* SrtBijhoudingID */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL    /* OmsEnumeratiewaarde */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   CONSTRAINT R5750 PRIMARY KEY (ID)
);
CREATE TABLE AutAut.Toestand (
   ID                            Smallint                      NOT NULL    /* ToestandsID */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL    /* OmsEnumeratiewaarde */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   CONSTRAINT R5746 PRIMARY KEY (ID)
);
CREATE SEQUENCE AutAut.seq_Uitgeslotene;
CREATE TABLE AutAut.Uitgeslotene (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_Uitgeslotene')  /* AuthorisatiebesluitGegevense */,
   Bijhautorisatie               Smallint                      NOT NULL    /* BijhautorisatieID */,
   UitgeslotenPartij             Smallint                      NOT NULL    /* PartijID */,
   UitgesloteneStatusHis         Varchar(1) CHECK (UitgesloteneStatusHis IS NULL OR UitgesloteneStatusHis IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   CONSTRAINT R5791 PRIMARY KEY (ID),
   CONSTRAINT R5792 UNIQUE (Bijhautorisatie, UitgeslotenPartij)
);
ALTER SEQUENCE AutAut.seq_Uitgeslotene OWNED BY AutAut.Uitgeslotene.ID;
CREATE SEQUENCE BRM.seq_His_Regelsituatie;
CREATE TABLE BRM.His_Regelsituatie (
   ID                            Integer                       NOT NULL  DEFAULT nextval('BRM.seq_His_Regelsituatie')  /* His_RegelsituatieID */,
   Regelsituatie                 Integer                                   /* RegelimplementatiesituatieID */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   Bijhverantwoordelijkheid      Smallint                                  /* VerantwoordelijkeID */,
   IndOpgeschort                 Boolean                                   /* JaNee */,
   RdnOpschorting                Smallint                                  /* RdnOpschortingID */,
   Effect                        Smallint                      NOT NULL    /* RegeleffectID */,
   IndActief                     Boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT R5989 PRIMARY KEY (ID),
   CONSTRAINT R5972 UNIQUE (Regelsituatie, TsReg)
);
ALTER SEQUENCE BRM.seq_His_Regelsituatie OWNED BY BRM.His_Regelsituatie.ID;
CREATE TABLE BRM.RegelSrtBer (
   ID                            Integer                       NOT NULL    /* RegelimplementatieID */,
   Regel                         Integer                       NOT NULL    /* RegelID */,
   SrtBer                        Smallint                      NOT NULL    /* SrtBerID */,
   CONSTRAINT R6060 PRIMARY KEY (ID),
   CONSTRAINT R6062 UNIQUE (Regel, SrtBer)
);
CREATE TABLE BRM.Regeleffect (
   ID                            Smallint                      NOT NULL    /* RegeleffectID */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL    /* OmsEnumeratiewaarde */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   CONSTRAINT R5978 PRIMARY KEY (ID),
   CONSTRAINT R5980 UNIQUE (Oms)
);
CREATE SEQUENCE BRM.seq_Regelsituatie;
CREATE TABLE BRM.Regelsituatie (
   ID                            Integer                       NOT NULL  DEFAULT nextval('BRM.seq_Regelsituatie')  /* RegelimplementatiesituatieID */,
   Regelimplementatie            Integer                       NOT NULL    /* RegelimplementatieID */,
   Bijhverantwoordelijkheid      Smallint                                  /* VerantwoordelijkeID */,
   IndOpgeschort                 Boolean                                   /* JaNee */,
   RdnOpschorting                Smallint                                  /* RdnOpschortingID */,
   Effect                        Smallint                                  /* RegeleffectID */,
   IndActief                     Boolean                                   /* JaNee */,
   RegelsituatieStatusHis        Varchar(1) CHECK (RegelsituatieStatusHis IS NULL OR RegelsituatieStatusHis IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   CONSTRAINT R5976 PRIMARY KEY (ID)
);
ALTER SEQUENCE BRM.seq_Regelsituatie OWNED BY BRM.Regelsituatie.ID;
CREATE TABLE BRM.SrtRegel (
   ID                            Smallint                      NOT NULL    /* SrtRegelID */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL    /* OmsEnumeratiewaarde */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   CONSTRAINT R5973 PRIMARY KEY (ID),
   CONSTRAINT R5975 UNIQUE (Oms)
);
CREATE TABLE Ber.Bijhresultaat (
   ID                            Smallint                      NOT NULL    /* BijhresultaatID */,
   Code                          Varchar(1)                    NOT NULL    /* BijhresultaatCode */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   CONSTRAINT R6324 PRIMARY KEY (ID),
   CONSTRAINT R9425 UNIQUE (Naam)
);
CREATE TABLE Ber.BurgerzakenModule (
   ID                            Smallint                      NOT NULL    /* IDModule */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL    /* OmsEnumeratiewaarde */,
   CONSTRAINT R9528 PRIMARY KEY (ID)
);
CREATE TABLE Ber.Richting (
   ID                            Smallint                      NOT NULL    /* RichtingID */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL    /* OmsEnumeratiewaarde */,
   CONSTRAINT R5635 PRIMARY KEY (ID)
);
CREATE TABLE Ber.SrtBer (
   ID                            Smallint                      NOT NULL    /* SrtBerID */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   Module                        Smallint                      NOT NULL    /* IDModule */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   CONSTRAINT R5027 PRIMARY KEY (ID)
);
CREATE TABLE Ber.SrtMelding (
   ID                            Smallint                      NOT NULL    /* SrtMeldingID */,
   Code                          Varchar(1)                    NOT NULL    /* SrtMeldingCode */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   CONSTRAINT R6317 PRIMARY KEY (ID)
);
CREATE TABLE Ber.Verwerkingsresultaat (
   ID                            Smallint                      NOT NULL    /* VerwerkingsresultaatID */,
   Code                          Varchar(1)                    NOT NULL    /* VerwerkingsresultaatCode */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   CONSTRAINT R6322 PRIMARY KEY (ID)
);
CREATE TABLE Kern.AangAdresh (
   ID                            Smallint                      NOT NULL    /* AangAdreshID */,
   Code                          Varchar(1)                    NOT NULL    /* AangAdreshCode */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL    /* OmsEnumeratiewaarde */,
   CONSTRAINT R4438 PRIMARY KEY (ID),
   CONSTRAINT R4439 UNIQUE (Naam)
);
CREATE TABLE Kern.AdellijkeTitel (
   ID                            Smallint                      NOT NULL    /* AdellijkeTitelID */,
   Code                          Varchar(1)                    NOT NULL    /* AdellijkeTitelCode */,
   NaamMannelijk                 Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   NaamVrouwelijk                Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   CONSTRAINT R4410 PRIMARY KEY (ID),
   CONSTRAINT R4413 UNIQUE (NaamVrouwelijk),
   CONSTRAINT R4411 UNIQUE (NaamMannelijk)
);
CREATE TABLE Kern.AutVanAfgifteReisdoc (
   ID                            Integer                       NOT NULL    /* AutVanAfgifteReisdocID */,
   Code                          Varchar(6)                    NOT NULL    /* AutVanAfgifteReisdocCode */,
   Oms                           Varchar(250)                  NOT NULL    /* OmsEnumeratiewaarde */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   CONSTRAINT R4466 PRIMARY KEY (ID),
   CONSTRAINT R4468 UNIQUE (Oms)
);
CREATE TABLE Kern.Bijhaard (
   ID                            Smallint                      NOT NULL    /* VerantwoordelijkeID */,
   Code                          Varchar(1)                    NOT NULL    /* VerantwoordelijkeCode */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   CONSTRAINT R4443 PRIMARY KEY (ID),
   CONSTRAINT R4444 UNIQUE (Naam)
);
CREATE TABLE Kern.BVP (
   ID                            Smallint                      NOT NULL    /* RdnVrijstellingVreemdelingen */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL    /* OmsEnumeratiewaarde */,
   CONSTRAINT R9370 PRIMARY KEY (ID)
);
CREATE TABLE Kern.CategoriePersonen (
   ID                            Smallint                      NOT NULL    /* CategoriePersonenID */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL    /* OmsEnumeratiewaarde */,
   CONSTRAINT R6169 PRIMARY KEY (ID)
);
CREATE TABLE Kern.DbObject (
   ID                            Integer                       NOT NULL    /* DbObjectID */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   Srt                           Smallint                      NOT NULL    /* SrtDbObjectID */,
   Ouder                         Integer                                   /* DbObjectID */,
   JavaIdentifier                Varchar(80)                   NOT NULL    /* IdentifierLang */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   CONSTRAINT R5659 PRIMARY KEY (ID)
);
CREATE TABLE Kern.Element (
   ID                            Integer                       NOT NULL    /* ElementID */,
   Naam                          Varchar(80)                   NOT NULL    /* LangeNaamEnumeratiewaarde */,
   Srt                           Smallint                      NOT NULL    /* SrtElementID */,
   Ouder                         Integer                                   /* ElementID */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   CONSTRAINT R4422 PRIMARY KEY (ID),
   CONSTRAINT R4423 UNIQUE (Srt, Naam, Ouder)
);
CREATE TABLE Kern.FunctieAdres (
   ID                            Smallint                      NOT NULL    /* SrtAdresID */,
   Code                          Varchar(1)                    NOT NULL    /* SrtAdresCode */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   CONSTRAINT R4435 PRIMARY KEY (ID),
   CONSTRAINT R4436 UNIQUE (Naam)
);
CREATE TABLE Kern.Geslachtsaand (
   ID                            Smallint                      NOT NULL    /* GeslachtsaandID */,
   Code                          Varchar(1)                    NOT NULL    /* GeslachtsaandCode */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                              /* OmsEnumeratiewaarde */,
   CONSTRAINT R4482 PRIMARY KEY (ID),
   CONSTRAINT R4484 UNIQUE (Naam)
);
CREATE SEQUENCE Kern.seq_His_Partij;
CREATE TABLE Kern.His_Partij (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_Partij')  /* His_PartijID */,
   Partij                        Smallint                                  /* PartijID */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   DatEinde                      Integer                                   /* Dat */,
   DatAanv                       Integer                       NOT NULL    /* Dat */,
   Sector                        Smallint                                  /* SectorID */,
   CONSTRAINT R4631 PRIMARY KEY (ID),
   CONSTRAINT R4628 UNIQUE (Partij, TsReg)
);
ALTER SEQUENCE Kern.seq_His_Partij OWNED BY Kern.His_Partij.ID;
CREATE SEQUENCE Kern.seq_His_PartijGem;
CREATE TABLE Kern.His_PartijGem (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PartijGem')  /* His_PartijGemID */,
   Partij                        Smallint                                  /* PartijID */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   VoortzettendeGem              Smallint                                  /* PartijID */,
   OnderdeelVan                  Smallint                                  /* PartijID */,
   CONSTRAINT R6125 PRIMARY KEY (ID),
   CONSTRAINT R6122 UNIQUE (Partij, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PartijGem OWNED BY Kern.His_PartijGem.ID;
CREATE TABLE Kern.Land (
   ID                            Integer                       NOT NULL    /* LandID */,
   Code                          Smallint                      NOT NULL    /* Landcode */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   ISO31661Alpha2                Varchar(2)                                /* ISO31661Alpha2 */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   CONSTRAINT R4399 PRIMARY KEY (ID)
);
CREATE TABLE Kern.Nation (
   ID                            Integer                       NOT NULL    /* NationID */,
   Code                          Smallint                      NOT NULL    /* Nationcode */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   CONSTRAINT R4404 PRIMARY KEY (ID)
);
CREATE SEQUENCE Kern.seq_Partij;
CREATE TABLE Kern.Partij (
   ID                            Smallint                      NOT NULL  DEFAULT nextval('Kern.seq_Partij')  /* PartijID */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   Srt                           Smallint                                  /* SrtPartijID */,
   Code                          Smallint                                  /* GemCode */,
   DatEinde                      Integer                                   /* Dat */,
   DatAanv                       Integer                                   /* Dat */,
   Sector                        Smallint                                  /* SectorID */,
   PartijStatusHis               Varchar(1) CHECK (PartijStatusHis IS NULL OR PartijStatusHis IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   VoortzettendeGem              Smallint                                  /* PartijID */,
   OnderdeelVan                  Smallint                                  /* PartijID */,
   GemStatusHis                  Varchar(1) CHECK (GemStatusHis IS NULL OR GemStatusHis IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   CONSTRAINT R4417 PRIMARY KEY (ID),
   CONSTRAINT R4418 UNIQUE (Naam)
);
ALTER SEQUENCE Kern.seq_Partij OWNED BY Kern.Partij.ID;
CREATE SEQUENCE Kern.seq_PartijRol;
CREATE TABLE Kern.PartijRol (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_PartijRol')  /* PartijRolID */,
   Partij                        Smallint                      NOT NULL    /* PartijID */,
   Rol                           Smallint                      NOT NULL    /* RolID */,
   PartijRolStatusHis            Varchar(1) CHECK (PartijRolStatusHis IS NULL OR PartijRolStatusHis IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   CONSTRAINT R4495 PRIMARY KEY (ID),
   CONSTRAINT R4688 UNIQUE (Partij, Rol)
);
ALTER SEQUENCE Kern.seq_PartijRol OWNED BY Kern.PartijRol.ID;
CREATE TABLE Kern.Plaats (
   ID                            Integer                       NOT NULL    /* PlaatsID */,
   Code                          Smallint                      NOT NULL    /* Wplcode */,
   Naam                          Varchar(80)                   NOT NULL    /* LangeNaamEnumeratiewaarde */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   CONSTRAINT R4397 PRIMARY KEY (ID)
);
CREATE TABLE Kern.Predikaat (
   ID                            Smallint                      NOT NULL    /* PredikaatID */,
   Code                          Varchar(1)                    NOT NULL    /* PredikaatCode */,
   NaamMannelijk                 Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   NaamVrouwelijk                Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   CONSTRAINT R4406 PRIMARY KEY (ID),
   CONSTRAINT R4408 UNIQUE (NaamMannelijk),
   CONSTRAINT R4409 UNIQUE (NaamVrouwelijk)
);
CREATE TABLE Kern.Rechtsgrond (
   ID                            Smallint                      NOT NULL    /* RechtsgrondID */,
   Code                          Smallint                      NOT NULL    /* RechtsgrondCode */,
   Srt                           Smallint                      NOT NULL    /* SrtRechtsgrondID */,
   Oms                           Varchar(250)                  NOT NULL    /* OmsEnumeratiewaarde */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   CONSTRAINT R9177 PRIMARY KEY (ID)
);
CREATE TABLE Kern.RdnBeeindRelatie (
   ID                            Smallint                      NOT NULL    /* RdnBeeindRelatieID */,
   Code                          Varchar(1)                    NOT NULL    /* RdnBeeindRelatieCode */,
   Oms                           Varchar(250)                  NOT NULL    /* OmsEnumeratiewaarde */,
   CONSTRAINT R4428 PRIMARY KEY (ID),
   CONSTRAINT R4429 UNIQUE (Oms)
);
CREATE TABLE Kern.RdnOpschorting (
   ID                            Smallint                      NOT NULL    /* RdnOpschortingID */,
   Code                          Varchar(1)                    NOT NULL    /* RdnOpschortingCode */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   CONSTRAINT R4449 PRIMARY KEY (ID),
   CONSTRAINT R4451 UNIQUE (Naam)
);
CREATE TABLE Kern.RdnVerkNLNation (
   ID                            Smallint                      NOT NULL    /* RdnVerkNLNationID */,
   Code                          Smallint                      NOT NULL    /* RdnVerkCode */,
   Oms                           Varchar(250)                  NOT NULL    /* OmsEnumeratiewaarde */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   CONSTRAINT R4430 PRIMARY KEY (ID),
   CONSTRAINT R4431 UNIQUE (Oms)
);
CREATE TABLE Kern.RdnVerliesNLNation (
   ID                            Smallint                      NOT NULL    /* RdnVerliesNLNationID */,
   Code                          Smallint                      NOT NULL    /* RdnVerliesCode */,
   Oms                           Varchar(250)                  NOT NULL    /* OmsEnumeratiewaarde */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   CONSTRAINT R4432 PRIMARY KEY (ID)
);
CREATE TABLE Kern.RdnVervallenReisdoc (
   ID                            Smallint                      NOT NULL    /* RdnOntbrReisdocID */,
   Code                          Varchar(1)                    NOT NULL    /* RdnOntbrReisdocCode */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   CONSTRAINT R4469 PRIMARY KEY (ID),
   CONSTRAINT R4471 UNIQUE (Naam)
);
CREATE TABLE Kern.RdnWijzAdres (
   ID                            Smallint                      NOT NULL    /* RdnWijzAdresID */,
   Code                          Varchar(1)                    NOT NULL    /* RdnWijzAdresCode */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   CONSTRAINT R4460 PRIMARY KEY (ID),
   CONSTRAINT R4462 UNIQUE (Naam)
);
CREATE TABLE Kern.Regel (
   ID                            Integer                       NOT NULL    /* RegelID */,
   Srt                           Smallint                      NOT NULL    /* SrtRegelID */,
   Code                          Varchar(40)                   NOT NULL    /* RegelCode */,
   Oms                           Varchar(250)                  NOT NULL    /* OmsEnumeratiewaarde */,
   Specificatie                  Text                          NOT NULL    /* Regelspecificatie */,
   CONSTRAINT R5811 PRIMARY KEY (ID),
   CONSTRAINT R6051 UNIQUE (Specificatie),
   CONSTRAINT R6052 UNIQUE (Oms)
);
CREATE TABLE Kern.Rol (
   ID                            Smallint                      NOT NULL    /* RolID */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   CONSTRAINT R4491 PRIMARY KEY (ID)
);
CREATE SEQUENCE Kern.seq_Sector;
CREATE TABLE Kern.Sector (
   ID                            Smallint                      NOT NULL  DEFAULT nextval('Kern.seq_Sector')  /* SectorID */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   CONSTRAINT R4494 PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_Sector OWNED BY Kern.Sector.ID;
CREATE TABLE Kern.SrtNLReisdoc (
   ID                            Smallint                      NOT NULL    /* SrtNLReisdocID */,
   Code                          Varchar(2)                    NOT NULL    /* SrtNLReisdocCode */,
   Oms                           Varchar(250)                  NOT NULL    /* OmsEnumeratiewaarde */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   CONSTRAINT R4463 PRIMARY KEY (ID),
   CONSTRAINT R4465 UNIQUE (Oms)
);
CREATE TABLE Kern.SrtActie (
   ID                            Smallint                      NOT NULL    /* SrtActieID */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   CONSTRAINT R4401 PRIMARY KEY (ID)
);
CREATE TABLE Kern.SrtAdmHnd (
   ID                            Smallint                      NOT NULL    /* SrtAdmHndID */,
   Code                          Varchar(10)                   NOT NULL    /* CodeAdmHnd */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   Module                        Smallint                      NOT NULL    /* IDModule */,
   CONSTRAINT R9260 PRIMARY KEY (ID),
   CONSTRAINT R9261 UNIQUE (Naam)
);
CREATE TABLE Kern.SrtBetr (
   ID                            Smallint                      NOT NULL    /* SrtBetrID */,
   Code                          Varchar(1)                    NOT NULL    /* SrtBetrCode */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   CONSTRAINT R4476 PRIMARY KEY (ID),
   CONSTRAINT R4478 UNIQUE (Naam)
);
CREATE TABLE Kern.SrtDbObject (
   ID                            Smallint                      NOT NULL    /* SrtDbObjectID */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   CONSTRAINT R5660 PRIMARY KEY (ID)
);
CREATE TABLE Kern.SrtDoc (
   ID                            Smallint                      NOT NULL    /* SrtDocID */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL    /* OmsEnumeratiewaarde */,
   Rangorde                      Integer                                   /* Volgnr */,
   CONSTRAINT R4419 PRIMARY KEY (ID)
);
CREATE TABLE Kern.SrtElement (
   ID                            Smallint                      NOT NULL    /* SrtElementID */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   CONSTRAINT R4457 PRIMARY KEY (ID)
);
CREATE TABLE Kern.SrtIndicatie (
   ID                            Smallint                      NOT NULL    /* SrtIndicatieID */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   IndMaterieleHistorieVanToepa  Boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT R4447 PRIMARY KEY (ID)
);
CREATE TABLE Kern.SrtMultiRealiteitRegel (
   ID                            Smallint                      NOT NULL    /* SrtMultiRealiteitRegelID */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL    /* OmsEnumeratiewaarde */,
   CONSTRAINT R4489 PRIMARY KEY (ID)
);
CREATE TABLE Kern.SrtPartij (
   ID                            Smallint                      NOT NULL    /* SrtPartijID */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   CONSTRAINT R4493 PRIMARY KEY (ID)
);
CREATE TABLE Kern.SrtPers (
   ID                            Smallint                      NOT NULL    /* SrtPersID */,
   Code                          Varchar(1)                    NOT NULL    /* SrtPersCode */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                              /* OmsEnumeratiewaarde */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   CONSTRAINT R4485 PRIMARY KEY (ID),
   CONSTRAINT R4487 UNIQUE (Naam)
);
CREATE TABLE Kern.SrtRechtsgrond (
   ID                            Smallint                      NOT NULL    /* SrtRechtsgrondID */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   CONSTRAINT R9178 PRIMARY KEY (ID)
);
CREATE TABLE Kern.SrtRelatie (
   ID                            Smallint                      NOT NULL    /* SrtRelatieID */,
   Code                          Varchar(1)                    NOT NULL    /* SrtRelatieCode */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                              /* OmsEnumeratiewaarde */,
   CONSTRAINT R4425 PRIMARY KEY (ID),
   CONSTRAINT R4426 UNIQUE (Naam)
);
CREATE TABLE Kern.SrtVerificatie (
   ID                            Smallint                      NOT NULL    /* SrtVerificatieID */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   CONSTRAINT R4472 PRIMARY KEY (ID)
);
CREATE TABLE Kern.Verblijfstitel (
   ID                            Smallint                      NOT NULL    /* VerblijfsrID */,
   Code                          Smallint                      NOT NULL    /* CodeVerblijfstitel */,
   Oms                           Varchar(250)                  NOT NULL    /* OmsEnumeratiewaarde */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   CONSTRAINT R4441 PRIMARY KEY (ID),
   CONSTRAINT R4442 UNIQUE (Oms)
);
CREATE TABLE Kern.Voorvoegsel (
   ID                            Smallint                      NOT NULL    /* VoorvoegselID */,
   Voorvoegsel                   Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   Scheidingsteken               Varchar(1)                    NOT NULL    /* Scheidingsteken */,
   LO3Voorvoegsel                Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   CONSTRAINT R9270 PRIMARY KEY (ID)
);
CREATE TABLE Kern.WijzeGebruikGeslnaam (
   ID                            Smallint                      NOT NULL    /* WijzeGebruikGeslnaamID */,
   Code                          Varchar(1)                    NOT NULL    /* WijzeGebruikGeslnaamCode */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                              /* OmsEnumeratiewaarde */,
   CONSTRAINT R4452 PRIMARY KEY (ID),
   CONSTRAINT R4454 UNIQUE (Naam)
);
CREATE SEQUENCE Lev.seq_Abonnement;
CREATE TABLE Lev.Abonnement (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Lev.seq_Abonnement')  /* AbonnementID */,
   Doelbinding                   Integer                       NOT NULL    /* DoelbindingID */,
   SrtAbonnement                 Smallint                      NOT NULL    /* SrtAbonnementID */,
   Populatiecriterium            Text                                      /* Populatiecriterium */,
   AbonnementStatusHis           Varchar(1) CHECK (AbonnementStatusHis IS NULL OR AbonnementStatusHis IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   CONSTRAINT R4983 PRIMARY KEY (ID),
   CONSTRAINT R4984 UNIQUE (Doelbinding, SrtAbonnement)
);
ALTER SEQUENCE Lev.seq_Abonnement OWNED BY Lev.Abonnement.ID;
CREATE SEQUENCE Lev.seq_AbonnementGegevenselement;
CREATE TABLE Lev.AbonnementGegevenselement (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Lev.seq_AbonnementGegevenselement')  /* GegevenselementAbonnementID */,
   Abonnement                    Integer                       NOT NULL    /* AbonnementID */,
   Gegevenselement               Integer                       NOT NULL    /* DbObjectID */,
   CONSTRAINT R4985 PRIMARY KEY (ID),
   CONSTRAINT R4986 UNIQUE (Abonnement, Gegevenselement)
);
ALTER SEQUENCE Lev.seq_AbonnementGegevenselement OWNED BY Lev.AbonnementGegevenselement.ID;
CREATE SEQUENCE Lev.seq_AbonnementSrtBer;
CREATE TABLE Lev.AbonnementSrtBer (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Lev.seq_AbonnementSrtBer')  /* AbonnementSrtBerID */,
   Abonnement                    Integer                       NOT NULL    /* AbonnementID */,
   SrtBer                        Smallint                      NOT NULL    /* SrtBerID */,
   AbonnementSrtBerStatusHis     Varchar(1) CHECK (AbonnementSrtBerStatusHis IS NULL OR AbonnementSrtBerStatusHis IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   CONSTRAINT R5607 PRIMARY KEY (ID),
   CONSTRAINT R5608 UNIQUE (Abonnement, SrtBer)
);
ALTER SEQUENCE Lev.seq_AbonnementSrtBer OWNED BY Lev.AbonnementSrtBer.ID;
CREATE SEQUENCE Lev.seq_His_Abonnement;
CREATE TABLE Lev.His_Abonnement (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Lev.seq_His_Abonnement')  /* His_AbonnementID */,
   Abonnement                    Integer                                   /* AbonnementID */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   Populatiecriterium            Text                          NOT NULL    /* Populatiecriterium */,
   CONSTRAINT R4997 PRIMARY KEY (ID),
   CONSTRAINT R4958 UNIQUE (Abonnement, TsReg)
);
ALTER SEQUENCE Lev.seq_His_Abonnement OWNED BY Lev.His_Abonnement.ID;
CREATE SEQUENCE Lev.seq_His_AbonnementSrtBer;
CREATE TABLE Lev.His_AbonnementSrtBer (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Lev.seq_His_AbonnementSrtBer')  /* His_AbonnementSrtBerID */,
   AbonnementSrtBer              Integer                                   /* AbonnementSrtBerID */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   CONSTRAINT R5611 PRIMARY KEY (ID),
   CONSTRAINT R5606 UNIQUE (AbonnementSrtBer, TsReg)
);
ALTER SEQUENCE Lev.seq_His_AbonnementSrtBer OWNED BY Lev.His_AbonnementSrtBer.ID;
CREATE TABLE Lev.SrtAbonnement (
   ID                            Smallint                      NOT NULL    /* SrtAbonnementID */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   CONSTRAINT R4993 PRIMARY KEY (ID)
);
CREATE TABLE Lev.SrtLev (
   ID                            Smallint                      NOT NULL    /* SrtLevID */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   CONSTRAINT R4829 PRIMARY KEY (ID)
);
-- Actual tabellen--------------------------------------------------------------
CREATE TABLE Ber.AdmHndBijgehoudenPers (
   ID                            BigInt                        NOT NULL    /* BerBijgehoudenPersID */,
   AdmHnd                        BigInt                        NOT NULL    /* AdmHndID */,
   Pers                          Integer                       NOT NULL    /* PersID */
);
CREATE TABLE Ber.AdmHndDoc (
);
CREATE SEQUENCE Ber.seq_AdmHndGedeblokkeerdeMelding;
CREATE TABLE Ber.AdmHndGedeblokkeerdeMelding (
   ID                            BigInt                        NOT NULL  DEFAULT nextval('Ber.seq_AdmHndGedeblokkeerdeMelding')  /* BerOverruleID */,
   AdmHnd                        BigInt                        NOT NULL    /* AdmHndID */,
   GedeblokkeerdeMelding         BigInt                        NOT NULL    /* OverruleID */,
   CONSTRAINT R6321 PRIMARY KEY (ID)
);
ALTER SEQUENCE Ber.seq_AdmHndGedeblokkeerdeMelding OWNED BY Ber.AdmHndGedeblokkeerdeMelding.ID;
CREATE SEQUENCE Ber.seq_Ber;
CREATE TABLE Ber.Ber (
   ID                            BigInt                        NOT NULL  DEFAULT nextval('Ber.seq_Ber')  /* BerID */,
   Srt                           Smallint                      NOT NULL    /* SrtBerID */,
   AdmHnd                        BigInt                                    /* AdmHndID */,
   Data                          Text                          NOT NULL    /* Berdata */,
   TsOntv                        Timestamp                                 /* DatTijd */,
   TsVerzenden                   Timestamp                                 /* DatTijd */,
   AntwoordOp                    BigInt                                    /* BerID */,
   Richting                      Smallint                      NOT NULL    /* RichtingID */,
   Organisatie                   Varchar(200)                              /* Organisatienaam */,
   Applicatie                    Varchar(50)                               /* Applicatienaam */,
   Referentienr                  Varchar(40)                               /* Sleutelwaardetekst */,
   CrossReferentienr             Varchar(40)                               /* Sleutelwaardetekst */,
   Verwerkingswijze              Varchar(1) CHECK (Verwerkingswijze IS NULL OR Verwerkingswijze IN ('B','P'))              /* Verwerkingswijze */,
   PeilmomMaterieel              Integer                                   /* Dat */,
   PeilmomFormeel                Timestamp                                 /* DatTijd */,
   Aanschouwer                   Integer                                   /* BSN */,
   Verwerking                    Smallint                                  /* VerwerkingsresultaatID */,
   Bijhouding                    Smallint                                  /* BijhresultaatID */,
   HoogsteMeldingsniveau         Smallint                                  /* SrtMeldingID */,
   CONSTRAINT R4827 PRIMARY KEY (ID)
);
ALTER SEQUENCE Ber.seq_Ber OWNED BY Ber.Ber.ID;
CREATE SEQUENCE Ber.seq_BerMelding;
CREATE TABLE Ber.BerMelding (
   ID                            BigInt                        NOT NULL  DEFAULT nextval('Ber.seq_BerMelding')  /* BerMeldingID */,
   Ber                           BigInt                        NOT NULL    /* BerID */,
   Melding                       BigInt                        NOT NULL    /* MeldingID */,
   CONSTRAINT R6319 PRIMARY KEY (ID)
);
ALTER SEQUENCE Ber.seq_BerMelding OWNED BY Ber.BerMelding.ID;
CREATE SEQUENCE Ber.seq_GedeblokkeerdeMelding;
CREATE TABLE Ber.GedeblokkeerdeMelding (
   ID                            BigInt                        NOT NULL  DEFAULT nextval('Ber.seq_GedeblokkeerdeMelding')  /* OverruleID */,
   Regel                         Integer                       NOT NULL    /* RegelID */,
   Melding                       Varchar(200)                              /* Meldingtekst */,
   Attribuut                     Integer                                   /* ElementID */,
   CONSTRAINT R6320 PRIMARY KEY (ID)
);
ALTER SEQUENCE Ber.seq_GedeblokkeerdeMelding OWNED BY Ber.GedeblokkeerdeMelding.ID;
CREATE SEQUENCE Ber.seq_Melding;
CREATE TABLE Ber.Melding (
   ID                            BigInt                        NOT NULL  DEFAULT nextval('Ber.seq_Melding')  /* MeldingID */,
   Regel                         Integer                       NOT NULL    /* RegelID */,
   Srt                           Smallint                      NOT NULL    /* SrtMeldingID */,
   Melding                       Varchar(200)                  NOT NULL    /* Meldingtekst */,
   Attribuut                     Integer                                   /* ElementID */,
   CONSTRAINT R6316 PRIMARY KEY (ID)
);
ALTER SEQUENCE Ber.seq_Melding OWNED BY Ber.Melding.ID;
CREATE SEQUENCE Kern.seq_Actie;
CREATE TABLE Kern.Actie (
   ID                            BigInt                        NOT NULL  DEFAULT nextval('Kern.seq_Actie')  /* ActieID */,
   Srt                           Smallint                      NOT NULL    /* SrtActieID */,
   AdmHnd                        BigInt                        NOT NULL    /* AdmHndID */,
   Partij                        Smallint                      NOT NULL    /* PartijID */,
   TsReg                         Timestamp                     NOT NULL    /* DatTijd */,
   CONSTRAINT R4403 PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_Actie OWNED BY Kern.Actie.ID;
CREATE SEQUENCE Kern.seq_ActieBron;
CREATE TABLE Kern.ActieBron (
   ID                            BigInt                        NOT NULL  DEFAULT nextval('Kern.seq_ActieBron')  /* VerantwoordingsID */,
   Actie                         BigInt                        NOT NULL    /* ActieID */,
   Doc                           BigInt                                    /* DocID */,
   Rechtsgrond                   Smallint                                  /* RechtsgrondID */,
   CONSTRAINT R9175 PRIMARY KEY (ID),
   CONSTRAINT R9176 UNIQUE (Actie, Doc, Rechtsgrond)
);
ALTER SEQUENCE Kern.seq_ActieBron OWNED BY Kern.ActieBron.ID;
CREATE SEQUENCE Kern.seq_AdmHnd;
CREATE TABLE Kern.AdmHnd (
   ID                            BigInt                        NOT NULL  DEFAULT nextval('Kern.seq_AdmHnd')  /* AdmHndID */,
   Srt                           Smallint                      NOT NULL    /* SrtAdmHndID */,
   Partij                        Smallint                      NOT NULL    /* PartijID */,
   TsOntlening                   Timestamp                     NOT NULL    /* DatTijd */,
   ToelichtingOntlening          Text                                      /* Ontleningstoelichting */,
   CONSTRAINT R9179 PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_AdmHnd OWNED BY Kern.AdmHnd.ID;
CREATE SEQUENCE Kern.seq_Betr;
CREATE TABLE Kern.Betr (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_Betr')  /* BetrID */,
   Relatie                       Integer                       NOT NULL    /* RelatieID */,
   Rol                           Smallint                      NOT NULL    /* SrtBetrID */,
   Pers                          Integer                                   /* PersID */,
   IndOuder                      Boolean CHECK (IndOuder IS NULL OR IndOuder IN ('T'))              /* Ja */,
   OuderschapStatusHis           Varchar(1) CHECK (OuderschapStatusHis IS NULL OR OuderschapStatusHis IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   IndOuderHeeftGezag            Boolean                                   /* JaNee */,
   OuderlijkGezagStatusHis       Varchar(1) CHECK (OuderlijkGezagStatusHis IS NULL OR OuderlijkGezagStatusHis IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   CONSTRAINT R6104 PRIMARY KEY (ID),
   CONSTRAINT R6105 UNIQUE (Relatie, Pers)
);
ALTER SEQUENCE Kern.seq_Betr OWNED BY Kern.Betr.ID;
CREATE SEQUENCE Kern.seq_Doc;
CREATE TABLE Kern.Doc (
   ID                            BigInt                        NOT NULL  DEFAULT nextval('Kern.seq_Doc')  /* DocID */,
   Srt                           Smallint                      NOT NULL    /* SrtDocID */,
   Ident                         Varchar(20)                               /* DocIdent */,
   Aktenr                        Varchar(7)                                /* Aktenr */,
   Oms                           Varchar(40)                               /* DocOms */,
   Partij                        Smallint                                  /* PartijID */,
   DocStatusHis                  Varchar(1) CHECK (DocStatusHis IS NULL OR DocStatusHis IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   CONSTRAINT R4416 PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_Doc OWNED BY Kern.Doc.ID;
CREATE SEQUENCE Kern.seq_GegevenInOnderzoek;
CREATE TABLE Kern.GegevenInOnderzoek (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_GegevenInOnderzoek')  /* GegevenInOnderzoekID */,
   Onderzoek                     Integer                       NOT NULL    /* OnderzoekID */,
   SrtGegeven                    Integer                       NOT NULL    /* DbObjectID */,
   Ident                         BigInt                        NOT NULL    /* Sleutelwaarde */,
   CONSTRAINT R4507 PRIMARY KEY (ID),
   CONSTRAINT R4480 UNIQUE (Onderzoek, SrtGegeven)
);
ALTER SEQUENCE Kern.seq_GegevenInOnderzoek OWNED BY Kern.GegevenInOnderzoek.ID;
CREATE SEQUENCE Kern.seq_MultiRealiteitRegel;
CREATE TABLE Kern.MultiRealiteitRegel (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_MultiRealiteitRegel')  /* MultiRealiteitregelID */,
   GeldigVoor                    Integer                       NOT NULL    /* PersID */,
   Srt                           Smallint                      NOT NULL    /* SrtMultiRealiteitRegelID */,
   Pers                          Integer                                   /* PersID */,
   MultiRealiteitPers            Integer                                   /* PersID */,
   Relatie                       Integer                                   /* RelatieID */,
   Betr                          Integer                                   /* BetrID */,
   MultiRealiteitRegelStatusHis  Varchar(1) CHECK (MultiRealiteitRegelStatusHis IS NULL OR MultiRealiteitRegelStatusHis IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   CONSTRAINT R4488 PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_MultiRealiteitRegel OWNED BY Kern.MultiRealiteitRegel.ID;
CREATE SEQUENCE Kern.seq_Onderzoek;
CREATE TABLE Kern.Onderzoek (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_Onderzoek')  /* OnderzoekID */,
   DatBegin                      Integer                                   /* Dat */,
   DatEinde                      Integer                                   /* Dat */,
   Oms                           Text                                      /* OnderzoekOms */,
   OnderzoekStatusHis            Varchar(1) CHECK (OnderzoekStatusHis IS NULL OR OnderzoekStatusHis IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   CONSTRAINT R4421 PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_Onderzoek OWNED BY Kern.Onderzoek.ID;
CREATE SEQUENCE Kern.seq_Pers;
CREATE TABLE Kern.Pers (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_Pers')  /* PersID */,
   Srt                           Smallint                      NOT NULL    /* SrtPersID */,
   TsLaatsteWijz                 Timestamp                                 /* DatTijd */,
   IndGegevensInOnderzoek        Boolean                                   /* JaNee */,
   BSN                           Integer                                   /* BSN */,
   ANr                           Bigint                                    /* ANr */,
   IDsStatusHis                  Varchar(1) CHECK (IDsStatusHis IS NULL OR IDsStatusHis IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   IndAlgoritmischAfgeleid       Boolean                                   /* JaNee */,
   IndNreeks                     Boolean                                   /* JaNee */,
   Predikaat                     Smallint                                  /* PredikaatID */,
   Voornamen                     Varchar(200)                              /* Voornamen */,
   AdellijkeTitel                Smallint                                  /* AdellijkeTitelID */,
   Voorvoegsel                   Varchar(10)                               /* Voorvoegsel1 */,
   Scheidingsteken               Varchar(1)                                /* Scheidingsteken */,
   Geslnaam                      Varchar(200)                              /* Geslnaam */,
   SamengesteldeNaamStatusHis    Varchar(1) CHECK (SamengesteldeNaamStatusHis IS NULL OR SamengesteldeNaamStatusHis IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   DatGeboorte                   Integer                                   /* Dat */,
   GemGeboorte                   Smallint                                  /* PartijID */,
   WplGeboorte                   Integer                                   /* PlaatsID */,
   BLPlaatsGeboorte              Varchar(40)                               /* BLPlaats */,
   BLRegioGeboorte               Varchar(35)                               /* BLRegio */,
   OmsLocGeboorte                Varchar(40)                               /* LocOms */,
   LandGeboorte                  Integer                                   /* LandID */,
   GeboorteStatusHis             Varchar(1) CHECK (GeboorteStatusHis IS NULL OR GeboorteStatusHis IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   Geslachtsaand                 Smallint                                  /* GeslachtsaandID */,
   GeslachtsaandStatusHis        Varchar(1) CHECK (GeslachtsaandStatusHis IS NULL OR GeslachtsaandStatusHis IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   DatInschr                     Integer                                   /* Dat */,
   Versienr                      BigInt                                    /* Versienr */,
   VorigePers                    Integer                                   /* PersID */,
   VolgendePers                  Integer                                   /* PersID */,
   InschrStatusHis               Varchar(1) CHECK (InschrStatusHis IS NULL OR InschrStatusHis IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   Bijhaard                      Smallint                                  /* VerantwoordelijkeID */,
   BijhaardStatusHis             Varchar(1) CHECK (BijhaardStatusHis IS NULL OR BijhaardStatusHis IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   Bijhgem                       Smallint                                  /* PartijID */,
   DatInschrInGem                Integer                                   /* Dat */,
   IndOnverwDocAanw              Boolean                                   /* JaNee */,
   BijhgemStatusHis              Varchar(1) CHECK (BijhgemStatusHis IS NULL OR BijhgemStatusHis IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   RdnOpschortingBijhouding      Smallint                                  /* RdnOpschortingID */,
   OpschortingStatusHis          Varchar(1) CHECK (OpschortingStatusHis IS NULL OR OpschortingStatusHis IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   DatOverlijden                 Integer                                   /* Dat */,
   GemOverlijden                 Smallint                                  /* PartijID */,
   WplOverlijden                 Integer                                   /* PlaatsID */,
   BLPlaatsOverlijden            Varchar(40)                               /* BLPlaats */,
   BLRegioOverlijden             Varchar(35)                               /* BLRegio */,
   OmsLocOverlijden              Varchar(40)                               /* LocOms */,
   LandOverlijden                Integer                                   /* LandID */,
   OverlijdenStatusHis           Varchar(1) CHECK (OverlijdenStatusHis IS NULL OR OverlijdenStatusHis IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   Naamgebruik                   Smallint                                  /* WijzeGebruikGeslnaamID */,
   IndTitelsPredikatenBijAansch  Boolean                                   /* JaNee */,
   IndAanschrAlgoritmischAfgele  Boolean                                   /* JaNee */,
   PredikaatAanschr              Smallint                                  /* PredikaatID */,
   VoornamenAanschr              Varchar(200)                              /* Voornamen */,
   AdellijkeTitelAanschr         Smallint                                  /* AdellijkeTitelID */,
   VoorvoegselAanschr            Varchar(10)                               /* Voorvoegsel1 */,
   ScheidingstekenAanschr        Varchar(1)                                /* Scheidingsteken */,
   GeslnaamAanschr               Varchar(200)                              /* Geslnaam */,
   AanschrStatusHis              Varchar(1) CHECK (AanschrStatusHis IS NULL OR AanschrStatusHis IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   LandVanwaarGevestigd          Integer                                   /* LandID */,
   DatVestigingInNederland       Integer                                   /* Dat */,
   ImmigratieStatusHis           Varchar(1) CHECK (ImmigratieStatusHis IS NULL OR ImmigratieStatusHis IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   Verblijfstitel                Smallint                                  /* VerblijfsrID */,
   DatAanvVerblijfstitel         Integer                                   /* Dat */,
   DatVoorzEindeVerblijfstitel   Integer                                   /* Dat */,
   VerblijfstitelStatusHis       Varchar(1) CHECK (VerblijfstitelStatusHis IS NULL OR VerblijfstitelStatusHis IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   BVP                           Smallint                                  /* RdnVrijstellingVreemdelingen */,
   BVPStatusHis                  Varchar(1) CHECK (BVPStatusHis IS NULL OR BVPStatusHis IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   IndUitslNLKiesr               Boolean CHECK (IndUitslNLKiesr IS NULL OR IndUitslNLKiesr IN ('T'))              /* Ja */,
   DatEindeUitslNLKiesr          Integer                                   /* Dat */,
   UitslNLKiesrStatusHis         Varchar(1) CHECK (UitslNLKiesrStatusHis IS NULL OR UitslNLKiesrStatusHis IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   IndDeelnEUVerkiezingen        Boolean                                   /* JaNee */,
   DatAanlAanpDeelnEUVerkiezing  Integer                                   /* Dat */,
   DatEindeUitslEUKiesr          Integer                                   /* Dat */,
   EUVerkiezingenStatusHis       Varchar(1) CHECK (EUVerkiezingenStatusHis IS NULL OR EUVerkiezingenStatusHis IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   GemPK                         Smallint                                  /* PartijID */,
   IndPKVolledigGeconv           Boolean                                   /* JaNee */,
   PKStatusHis                   Varchar(1) CHECK (PKStatusHis IS NULL OR PKStatusHis IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   CONSTRAINT R4390 PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_Pers OWNED BY Kern.Pers.ID;
CREATE SEQUENCE Kern.seq_PersAdres;
CREATE TABLE Kern.PersAdres (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_PersAdres')  /* PersAdresID */,
   Pers                          Integer                       NOT NULL    /* PersID */,
   Srt                           Smallint                                  /* SrtAdresID */,
   RdnWijz                       Smallint                                  /* RdnWijzAdresID */,
   AangAdresh                    Smallint                                  /* AangAdreshID */,
   DatAanvAdresh                 Integer                                   /* Dat */,
   AdresseerbaarObject           Varchar(16)                               /* AandAdresseerbaarObject */,
   IdentcodeNraand               Varchar(16)                               /* IdentcodeNraand */,
   Gem                           Smallint                                  /* PartijID */,
   NOR                           Varchar(80)                               /* NOR */,
   AfgekorteNOR                  Varchar(24)                               /* AfgekorteNOR */,
   Gemdeel                       Varchar(24)                               /* Gemdeel */,
   Huisnr                        Integer                                   /* Huisnr */,
   Huisletter                    Varchar(1)                                /* Huisletter */,
   Huisnrtoevoeging              Varchar(4)                                /* Huisnrtoevoeging */,
   Postcode                      Varchar(6)                                /* Postcode */,
   Wpl                           Integer                                   /* PlaatsID */,
   LoctovAdres                   Varchar(2) CHECK (LoctovAdres IS NULL OR LoctovAdres IN ('by','to'))              /* AandBijHuisnr */,
   LocOms                        Varchar(40)                               /* LocOms */,
   DatVertrekUitNederland        Integer                                   /* Dat */,
   BLAdresRegel1                 Varchar(40)                               /* Adresregel */,
   BLAdresRegel2                 Varchar(40)                               /* Adresregel */,
   BLAdresRegel3                 Varchar(40)                               /* Adresregel */,
   BLAdresRegel4                 Varchar(40)                               /* Adresregel */,
   BLAdresRegel5                 Varchar(40)                               /* Adresregel */,
   BLAdresRegel6                 Varchar(40)                               /* Adresregel */,
   Land                          Integer                                   /* LandID */,
   IndPersNietAangetroffenOpAdr  Boolean                                   /* JaNee */,
   PersAdresStatusHis            Varchar(1) CHECK (PersAdresStatusHis IS NULL OR PersAdresStatusHis IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   CONSTRAINT R4434 PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_PersAdres OWNED BY Kern.PersAdres.ID;
CREATE SEQUENCE Kern.seq_PersGeslnaamcomp;
CREATE TABLE Kern.PersGeslnaamcomp (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_PersGeslnaamcomp')  /* PersGeslnaamID */,
   Pers                          Integer                       NOT NULL    /* PersID */,
   Volgnr                        Integer                       NOT NULL    /* Volgnr */,
   Predikaat                     Smallint                                  /* PredikaatID */,
   AdellijkeTitel                Smallint                                  /* AdellijkeTitelID */,
   Voorvoegsel                   Varchar(10)                               /* Voorvoegsel1 */,
   Scheidingsteken               Varchar(1)                                /* Scheidingsteken */,
   Naam                          Varchar(200)                              /* Geslnaamcomp */,
   PersGeslnaamcompStatusHis     Varchar(1) CHECK (PersGeslnaamcompStatusHis IS NULL OR PersGeslnaamcompStatusHis IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   CONSTRAINT R4391 PRIMARY KEY (ID),
   CONSTRAINT R4392 UNIQUE (Pers, Volgnr)
);
ALTER SEQUENCE Kern.seq_PersGeslnaamcomp OWNED BY Kern.PersGeslnaamcomp.ID;
CREATE SEQUENCE Kern.seq_PersIndicatie;
CREATE TABLE Kern.PersIndicatie (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_PersIndicatie')  /* PersIndicatieID */,
   Pers                          Integer                       NOT NULL    /* PersID */,
   Srt                           Smallint                      NOT NULL    /* SrtIndicatieID */,
   Waarde                        Boolean CHECK (Waarde IS NULL OR Waarde IN ('T'))              /* Ja */,
   PersIndicatieStatusHis        Varchar(1) CHECK (PersIndicatieStatusHis IS NULL OR PersIndicatieStatusHis IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   CONSTRAINT R4455 PRIMARY KEY (ID),
   CONSTRAINT R4456 UNIQUE (Pers, Srt)
);
ALTER SEQUENCE Kern.seq_PersIndicatie OWNED BY Kern.PersIndicatie.ID;
CREATE SEQUENCE Kern.seq_PersNation;
CREATE TABLE Kern.PersNation (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_PersNation')  /* PersNationID */,
   Pers                          Integer                       NOT NULL    /* PersID */,
   Nation                        Integer                       NOT NULL    /* NationID */,
   RdnVerk                       Smallint                                  /* RdnVerkNLNationID */,
   RdnVerlies                    Smallint                                  /* RdnVerliesNLNationID */,
   PersNationStatusHis           Varchar(1) CHECK (PersNationStatusHis IS NULL OR PersNationStatusHis IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   CONSTRAINT R4414 PRIMARY KEY (ID),
   CONSTRAINT R4415 UNIQUE (Pers, Nation)
);
ALTER SEQUENCE Kern.seq_PersNation OWNED BY Kern.PersNation.ID;
CREATE SEQUENCE Kern.seq_PersOnderzoek;
CREATE TABLE Kern.PersOnderzoek (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_PersOnderzoek')  /* PersOnderzoekID */,
   Pers                          Integer                       NOT NULL    /* PersID */,
   Onderzoek                     Integer                       NOT NULL    /* OnderzoekID */,
   CONSTRAINT R6137 PRIMARY KEY (ID),
   CONSTRAINT R6138 UNIQUE (Onderzoek, Pers)
);
ALTER SEQUENCE Kern.seq_PersOnderzoek OWNED BY Kern.PersOnderzoek.ID;
CREATE SEQUENCE Kern.seq_PersReisdoc;
CREATE TABLE Kern.PersReisdoc (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_PersReisdoc')  /* ReisdocID */,
   Pers                          Integer                       NOT NULL    /* PersID */,
   Srt                           Smallint                      NOT NULL    /* SrtNLReisdocID */,
   Nr                            Varchar(9)                                /* ReisdocNr */,
   LengteHouder                  Numeric(3)                                /* LengteInCm */,
   AutVanAfgifte                 Integer                                   /* AutVanAfgifteReisdocID */,
   DatIngangDoc                  Integer                                   /* Dat */,
   DatUitgifte                   Integer                                   /* Dat */,
   DatVoorzeEindeGel             Integer                                   /* Dat */,
   DatInhingVermissing           Integer                                   /* Dat */,
   RdnVervallen                  Smallint                                  /* RdnOntbrReisdocID */,
   PersReisdocStatusHis          Varchar(1) CHECK (PersReisdocStatusHis IS NULL OR PersReisdocStatusHis IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   CONSTRAINT R4446 PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_PersReisdoc OWNED BY Kern.PersReisdoc.ID;
CREATE SEQUENCE Kern.seq_PersVerificatie;
CREATE TABLE Kern.PersVerificatie (
   ID                            BigInt                        NOT NULL  DEFAULT nextval('Kern.seq_PersVerificatie')  /* VerificatieID */,
   Geverifieerde                 Integer                       NOT NULL    /* PersID */,
   Srt                           Smallint                                  /* SrtVerificatieID */,
   Dat                           Integer                                   /* Dat */,
   PersVerificatieStatusHis      Varchar(1) CHECK (PersVerificatieStatusHis IS NULL OR PersVerificatieStatusHis IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   CONSTRAINT R4459 PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_PersVerificatie OWNED BY Kern.PersVerificatie.ID;
CREATE SEQUENCE Kern.seq_PersVoornaam;
CREATE TABLE Kern.PersVoornaam (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_PersVoornaam')  /* PersVoornaamID */,
   Pers                          Integer                       NOT NULL    /* PersID */,
   Volgnr                        Integer                       NOT NULL    /* Volgnr */,
   Naam                          Varchar(40)                               /* Voornaam */,
   PersVoornaamStatusHis         Varchar(1) CHECK (PersVoornaamStatusHis IS NULL OR PersVoornaamStatusHis IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   CONSTRAINT R4393 PRIMARY KEY (ID),
   CONSTRAINT R4394 UNIQUE (Pers, Volgnr)
);
ALTER SEQUENCE Kern.seq_PersVoornaam OWNED BY Kern.PersVoornaam.ID;
CREATE SEQUENCE Kern.seq_Regelverantwoording;
CREATE TABLE Kern.Regelverantwoording (
   ID                            BigInt                        NOT NULL  DEFAULT nextval('Kern.seq_Regelverantwoording')  /* ActieRegelimplementatieID */,
   Actie                         BigInt                        NOT NULL    /* ActieID */,
   Regel                         Integer                       NOT NULL    /* RegelID */,
   CONSTRAINT R6158 PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_Regelverantwoording OWNED BY Kern.Regelverantwoording.ID;
CREATE SEQUENCE Kern.seq_Relatie;
CREATE TABLE Kern.Relatie (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_Relatie')  /* RelatieID */,
   Srt                           Smallint                      NOT NULL    /* SrtRelatieID */,
   DatAanv                       Integer                                   /* Dat */,
   GemAanv                       Smallint                                  /* PartijID */,
   WplAanv                       Integer                                   /* PlaatsID */,
   BLPlaatsAanv                  Varchar(40)                               /* BLPlaats */,
   BLRegioAanv                   Varchar(35)                               /* BLRegio */,
   OmsLocAanv                    Varchar(40)                               /* LocOms */,
   LandAanv                      Integer                                   /* LandID */,
   RdnEinde                      Smallint                                  /* RdnBeeindRelatieID */,
   DatEinde                      Integer                                   /* Dat */,
   GemEinde                      Smallint                                  /* PartijID */,
   WplEinde                      Integer                                   /* PlaatsID */,
   BLPlaatsEinde                 Varchar(40)                               /* BLPlaats */,
   BLRegioEinde                  Varchar(35)                               /* BLRegio */,
   OmsLocEinde                   Varchar(40)                               /* LocOms */,
   LandEinde                     Integer                                   /* LandID */,
   HuwelijkGeregistreerdPartner  Varchar(1) CHECK (HuwelijkGeregistreerdPartner IS NULL OR HuwelijkGeregistreerdPartner IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   DatNaamskeuzeOngeborenVrucht  Integer                                   /* Dat */,
   NaamskeuzeOngeborenVruchtSta  Varchar(1) CHECK (NaamskeuzeOngeborenVruchtSta IS NULL OR NaamskeuzeOngeborenVruchtSta IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   DatErkenningOngeborenVrucht   Integer                                   /* Dat */,
   ErkenningOngeborenVruchtStat  Varchar(1) CHECK (ErkenningOngeborenVruchtStat IS NULL OR ErkenningOngeborenVruchtStat IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   CONSTRAINT R4424 PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_Relatie OWNED BY Kern.Relatie.ID;
CREATE TABLE Kern.VerstrDerde (
   ID                            Integer                       NOT NULL    /* VerstrenDerdeID */,
   Pers                          Integer                       NOT NULL    /* PersID */,
   Derde                         Smallint                      NOT NULL    /* PartijID */,
   VerstrDerdeStatusHis          Varchar(1) CHECK (VerstrDerdeStatusHis IS NULL OR VerstrDerdeStatusHis IN ('A','T','M','F','X'))  NOT NULL    /* StatusHistorie */,
   CONSTRAINT R9371 PRIMARY KEY (ID)
);
CREATE SEQUENCE Lev.seq_Lev;
CREATE TABLE Lev.Lev (
   ID                            BigInt                        NOT NULL  DEFAULT nextval('Lev.seq_Lev')  /* LevID */,
   Srt                           Smallint                      NOT NULL    /* SrtLevID */,
   Authenticatiemiddel           Integer                       NOT NULL    /* AuthenticatiemiddelID */,
   Abonnement                    Integer                       NOT NULL    /* AbonnementID */,
   TsBesch                       Timestamp                                 /* DatTijd */,
   TsKlaarzettenLev              Timestamp                                 /* DatTijd */,
   GebaseerdOp                   BigInt                                    /* BerID */,
   CONSTRAINT R4828 PRIMARY KEY (ID)
);
ALTER SEQUENCE Lev.seq_Lev OWNED BY Lev.Lev.ID;
CREATE SEQUENCE Lev.seq_LevCommunicatie;
CREATE TABLE Lev.LevCommunicatie (
   ID                            BigInt                        NOT NULL  DEFAULT nextval('Lev.seq_LevCommunicatie')  /* LevBerID */,
   Lev                           BigInt                        NOT NULL    /* LevID */,
   UitgaandBer                   BigInt                        NOT NULL    /* BerID */,
   CONSTRAINT R4848 PRIMARY KEY (ID),
   CONSTRAINT R4849 UNIQUE (Lev, UitgaandBer)
);
ALTER SEQUENCE Lev.seq_LevCommunicatie OWNED BY Lev.LevCommunicatie.ID;
CREATE SEQUENCE Lev.seq_LevPers;
CREATE TABLE Lev.LevPers (
   ID                            BigInt                        NOT NULL  DEFAULT nextval('Lev.seq_LevPers')  /* LevPersID */,
   Lev                           BigInt                        NOT NULL    /* LevID */,
   Pers                          Integer                       NOT NULL    /* PersID */,
   CONSTRAINT R4850 PRIMARY KEY (ID),
   CONSTRAINT R4851 UNIQUE (Lev, Pers)
);
ALTER SEQUENCE Lev.seq_LevPers OWNED BY Lev.LevPers.ID;
-- Materiele historie tabellen -------------------------------------------------
CREATE SEQUENCE Kern.seq_His_Doc;
CREATE TABLE Kern.His_Doc (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_Doc')  /* His_DocID */,
   Doc                           BigInt                                    /* DocID */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   Ident                         Varchar(20)                               /* DocIdent */,
   Aktenr                        Varchar(7)                                /* Aktenr */,
   Oms                           Varchar(40)                               /* DocOms */,
   Partij                        Smallint                      NOT NULL    /* PartijID */,
   CONSTRAINT R4516 PRIMARY KEY (ID),
   CONSTRAINT R4052 UNIQUE (Doc, TsReg)
);
ALTER SEQUENCE Kern.seq_His_Doc OWNED BY Kern.His_Doc.ID;
CREATE SEQUENCE Kern.seq_His_ErkenningOngeborenVrucht;
CREATE TABLE Kern.His_ErkenningOngeborenVrucht (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_ErkenningOngeborenVrucht')  /* His_ErkenningOngeborenVrucht */,
   Relatie                       Integer                                   /* RelatieID */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   DatErkenningOngeborenVrucht   Integer                       NOT NULL    /* Dat */,
   CONSTRAINT R9434 PRIMARY KEY (ID),
   CONSTRAINT R9424 UNIQUE (Relatie, TsReg)
);
ALTER SEQUENCE Kern.seq_His_ErkenningOngeborenVrucht OWNED BY Kern.His_ErkenningOngeborenVrucht.ID;
CREATE SEQUENCE Kern.seq_His_HuwelijkGeregistreerdPar;
CREATE TABLE Kern.His_HuwelijkGeregistreerdPar (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_HuwelijkGeregistreerdPar')  /* His_HuwelijkGeregistreerdPar */,
   Relatie                       Integer                                   /* RelatieID */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   DatAanv                       Integer                       NOT NULL    /* Dat */,
   GemAanv                       Smallint                                  /* PartijID */,
   WplAanv                       Integer                                   /* PlaatsID */,
   BLPlaatsAanv                  Varchar(40)                               /* BLPlaats */,
   BLRegioAanv                   Varchar(35)                               /* BLRegio */,
   OmsLocAanv                    Varchar(40)                               /* LocOms */,
   LandAanv                      Integer                       NOT NULL    /* LandID */,
   RdnEinde                      Smallint                                  /* RdnBeeindRelatieID */,
   DatEinde                      Integer                                   /* Dat */,
   GemEinde                      Smallint                                  /* PartijID */,
   WplEinde                      Integer                                   /* PlaatsID */,
   BLPlaatsEinde                 Varchar(40)                               /* BLPlaats */,
   BLRegioEinde                  Varchar(35)                               /* BLRegio */,
   OmsLocEinde                   Varchar(40)                               /* LocOms */,
   LandEinde                     Integer                                   /* LandID */,
   CONSTRAINT R4597 PRIMARY KEY (ID),
   CONSTRAINT R4389 UNIQUE (Relatie, TsReg)
);
ALTER SEQUENCE Kern.seq_His_HuwelijkGeregistreerdPar OWNED BY Kern.His_HuwelijkGeregistreerdPar.ID;
CREATE SEQUENCE Kern.seq_His_MultiRealiteitRegel;
CREATE TABLE Kern.His_MultiRealiteitRegel (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_MultiRealiteitRegel')  /* His_MultiRealiteitRegelID */,
   MultiRealiteitRegel           Integer                                   /* MultiRealiteitregelID */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   CONSTRAINT R5480 PRIMARY KEY (ID),
   CONSTRAINT R5477 UNIQUE (MultiRealiteitRegel, TsReg)
);
ALTER SEQUENCE Kern.seq_His_MultiRealiteitRegel OWNED BY Kern.His_MultiRealiteitRegel.ID;
CREATE SEQUENCE Kern.seq_His_NaamskeuzeOngeborenVruch;
CREATE TABLE Kern.His_NaamskeuzeOngeborenVruch (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_NaamskeuzeOngeborenVruch')  /* His_NaamskeuzeOngeborenVruch */,
   Relatie                       Integer                                   /* RelatieID */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   DatNaamskeuzeOngeborenVrucht  Integer                       NOT NULL    /* Dat */,
   CONSTRAINT R9428 PRIMARY KEY (ID),
   CONSTRAINT R9410 UNIQUE (Relatie, TsReg)
);
ALTER SEQUENCE Kern.seq_His_NaamskeuzeOngeborenVruch OWNED BY Kern.His_NaamskeuzeOngeborenVruch.ID;
CREATE SEQUENCE Kern.seq_His_Onderzoek;
CREATE TABLE Kern.His_Onderzoek (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_Onderzoek')  /* His_OnderzoekID */,
   Onderzoek                     Integer                                   /* OnderzoekID */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   DatBegin                      Integer                       NOT NULL    /* Dat */,
   DatEinde                      Integer                                   /* Dat */,
   Oms                           Text                                      /* OnderzoekOms */,
   CONSTRAINT R4522 PRIMARY KEY (ID),
   CONSTRAINT R4076 UNIQUE (Onderzoek, TsReg)
);
ALTER SEQUENCE Kern.seq_His_Onderzoek OWNED BY Kern.His_Onderzoek.ID;
CREATE SEQUENCE Kern.seq_His_OuderOuderlijkGezag;
CREATE TABLE Kern.His_OuderOuderlijkGezag (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_OuderOuderlijkGezag')  /* His_OuderOuderlijkGezagID */,
   Betr                          Integer                                   /* BetrID */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   ActieAanpGel                  BigInt                                    /* ActieID */,
   IndOuderHeeftGezag            Boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT R4513 PRIMARY KEY (ID),
   CONSTRAINT R4042 UNIQUE (Betr, TsReg, DatAanvGel)
);
ALTER SEQUENCE Kern.seq_His_OuderOuderlijkGezag OWNED BY Kern.His_OuderOuderlijkGezag.ID;
CREATE SEQUENCE Kern.seq_His_OuderOuderschap;
CREATE TABLE Kern.His_OuderOuderschap (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_OuderOuderschap')  /* His_OuderOuderschapID */,
   Betr                          Integer                                   /* BetrID */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   ActieAanpGel                  BigInt                                    /* ActieID */,
   IndOuder                      Boolean CHECK (IndOuder IS NULL OR IndOuder IN ('T'))  NOT NULL    /* Ja */,
   CONSTRAINT R4510 PRIMARY KEY (ID),
   CONSTRAINT R4032 UNIQUE (Betr, TsReg, DatAanvGel)
);
ALTER SEQUENCE Kern.seq_His_OuderOuderschap OWNED BY Kern.His_OuderOuderschap.ID;
CREATE SEQUENCE Kern.seq_His_PersAanschr;
CREATE TABLE Kern.His_PersAanschr (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersAanschr')  /* His_PersAanschrID */,
   Pers                          Integer                                   /* PersID */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   Naamgebruik                   Smallint                      NOT NULL    /* WijzeGebruikGeslnaamID */,
   IndTitelsPredikatenBijAansch  Boolean                                   /* JaNee */,
   IndAanschrAlgoritmischAfgele  Boolean                       NOT NULL    /* JaNee */,
   PredikaatAanschr              Smallint                                  /* PredikaatID */,
   VoornamenAanschr              Varchar(200)                              /* Voornamen */,
   AdellijkeTitelAanschr         Smallint                                  /* AdellijkeTitelID */,
   VoorvoegselAanschr            Varchar(10)                               /* Voorvoegsel1 */,
   ScheidingstekenAanschr        Varchar(1)                                /* Scheidingsteken */,
   GeslnaamAanschr               Varchar(200)                  NOT NULL    /* Geslnaam */,
   CONSTRAINT R4534 PRIMARY KEY (ID),
   CONSTRAINT R4131 UNIQUE (Pers, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PersAanschr OWNED BY Kern.His_PersAanschr.ID;
CREATE SEQUENCE Kern.seq_His_PersBijhaard;
CREATE TABLE Kern.His_PersBijhaard (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersBijhaard')  /* His_PersBijhaardID */,
   Pers                          Integer                                   /* PersID */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   ActieAanpGel                  BigInt                                    /* ActieID */,
   Bijhaard                      Smallint                      NOT NULL    /* VerantwoordelijkeID */,
   CONSTRAINT R4552 PRIMARY KEY (ID),
   CONSTRAINT R4197 UNIQUE (Pers, TsReg, DatAanvGel)
);
ALTER SEQUENCE Kern.seq_His_PersBijhaard OWNED BY Kern.His_PersBijhaard.ID;
CREATE SEQUENCE Kern.seq_His_PersBijhgem;
CREATE TABLE Kern.His_PersBijhgem (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersBijhgem')  /* His_PersBijhgemID */,
   Pers                          Integer                                   /* PersID */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   ActieAanpGel                  BigInt                                    /* ActieID */,
   Bijhgem                       Smallint                      NOT NULL    /* PartijID */,
   DatInschrInGem                Integer                       NOT NULL    /* Dat */,
   IndOnverwDocAanw              Boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT R4558 PRIMARY KEY (ID),
   CONSTRAINT R4216 UNIQUE (Pers, TsReg, DatAanvGel)
);
ALTER SEQUENCE Kern.seq_His_PersBijhgem OWNED BY Kern.His_PersBijhgem.ID;
CREATE SEQUENCE Kern.seq_His_PersBVP;
CREATE TABLE Kern.His_PersBVP (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersBVP')  /* His_PersBVPID */,
   Pers                          Integer                                   /* PersID */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   BVP                           Smallint                                  /* RdnVrijstellingVreemdelingen */,
   CONSTRAINT R9374 PRIMARY KEY (ID),
   CONSTRAINT R9363 UNIQUE (Pers, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PersBVP OWNED BY Kern.His_PersBVP.ID;
CREATE SEQUENCE Kern.seq_His_PersEUVerkiezingen;
CREATE TABLE Kern.His_PersEUVerkiezingen (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersEUVerkiezingen')  /* His_PersEUVerkiezingenID */,
   Pers                          Integer                                   /* PersID */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   IndDeelnEUVerkiezingen        Boolean                       NOT NULL    /* JaNee */,
   DatAanlAanpDeelnEUVerkiezing  Integer                                   /* Dat */,
   DatEindeUitslEUKiesr          Integer                                   /* Dat */,
   CONSTRAINT R4549 PRIMARY KEY (ID),
   CONSTRAINT R4187 UNIQUE (Pers, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PersEUVerkiezingen OWNED BY Kern.His_PersEUVerkiezingen.ID;
CREATE SEQUENCE Kern.seq_His_PersGeboorte;
CREATE TABLE Kern.His_PersGeboorte (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersGeboorte')  /* His_PersGeboorteID */,
   Pers                          Integer                                   /* PersID */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   DatGeboorte                   Integer                       NOT NULL    /* Dat */,
   GemGeboorte                   Smallint                                  /* PartijID */,
   WplGeboorte                   Integer                                   /* PlaatsID */,
   BLPlaatsGeboorte              Varchar(40)                               /* BLPlaats */,
   BLRegioGeboorte               Varchar(35)                               /* BLRegio */,
   OmsLocGeboorte                Varchar(40)                               /* LocOms */,
   LandGeboorte                  Integer                       NOT NULL    /* LandID */,
   CONSTRAINT R4537 PRIMARY KEY (ID),
   CONSTRAINT R4144 UNIQUE (Pers, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PersGeboorte OWNED BY Kern.His_PersGeboorte.ID;
CREATE SEQUENCE Kern.seq_His_PersGeslachtsaand;
CREATE TABLE Kern.His_PersGeslachtsaand (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersGeslachtsaand')  /* His_PersGeslachtsaandID */,
   Pers                          Integer                                   /* PersID */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   ActieAanpGel                  BigInt                                    /* ActieID */,
   Geslachtsaand                 Smallint                      NOT NULL    /* GeslachtsaandID */,
   CONSTRAINT R4528 PRIMARY KEY (ID),
   CONSTRAINT R4097 UNIQUE (Pers, TsReg, DatAanvGel)
);
ALTER SEQUENCE Kern.seq_His_PersGeslachtsaand OWNED BY Kern.His_PersGeslachtsaand.ID;
CREATE SEQUENCE Kern.seq_His_PersIDs;
CREATE TABLE Kern.His_PersIDs (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersIDs')  /* His_PersIDsID */,
   Pers                          Integer                                   /* PersID */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   ActieAanpGel                  BigInt                                    /* ActieID */,
   BSN                           Integer                                   /* BSN */,
   ANr                           Bigint                                    /* ANr */,
   CONSTRAINT R4525 PRIMARY KEY (ID),
   CONSTRAINT R4087 UNIQUE (Pers, TsReg, DatAanvGel)
);
ALTER SEQUENCE Kern.seq_His_PersIDs OWNED BY Kern.His_PersIDs.ID;
CREATE SEQUENCE Kern.seq_His_PersImmigratie;
CREATE TABLE Kern.His_PersImmigratie (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersImmigratie')  /* His_PersImmigratieID */,
   Pers                          Integer                                   /* PersID */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   ActieAanpGel                  BigInt                                    /* ActieID */,
   LandVanwaarGevestigd          Integer                                   /* LandID */,
   DatVestigingInNederland       Integer                       NOT NULL    /* Dat */,
   CONSTRAINT R4564 PRIMARY KEY (ID),
   CONSTRAINT R4235 UNIQUE (Pers, TsReg, DatAanvGel)
);
ALTER SEQUENCE Kern.seq_His_PersImmigratie OWNED BY Kern.His_PersImmigratie.ID;
CREATE SEQUENCE Kern.seq_His_PersInschr;
CREATE TABLE Kern.His_PersInschr (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersInschr')  /* His_PersInschrID */,
   Pers                          Integer                                   /* PersID */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   DatInschr                     Integer                       NOT NULL    /* Dat */,
   Versienr                      BigInt                        NOT NULL    /* Versienr */,
   VorigePers                    Integer                                   /* PersID */,
   VolgendePers                  Integer                                   /* PersID */,
   CONSTRAINT R4567 PRIMARY KEY (ID),
   CONSTRAINT R4248 UNIQUE (Pers, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PersInschr OWNED BY Kern.His_PersInschr.ID;
CREATE SEQUENCE Kern.seq_His_PersOpschorting;
CREATE TABLE Kern.His_PersOpschorting (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersOpschorting')  /* His_PersOpschortingID */,
   Pers                          Integer                                   /* PersID */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   ActieAanpGel                  BigInt                                    /* ActieID */,
   RdnOpschortingBijhouding      Smallint                      NOT NULL    /* RdnOpschortingID */,
   CONSTRAINT R4555 PRIMARY KEY (ID),
   CONSTRAINT R4204 UNIQUE (Pers, TsReg, DatAanvGel)
);
ALTER SEQUENCE Kern.seq_His_PersOpschorting OWNED BY Kern.His_PersOpschorting.ID;
CREATE SEQUENCE Kern.seq_His_PersOverlijden;
CREATE TABLE Kern.His_PersOverlijden (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersOverlijden')  /* His_PersOverlijdenID */,
   Pers                          Integer                                   /* PersID */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   DatOverlijden                 Integer                       NOT NULL    /* Dat */,
   GemOverlijden                 Smallint                                  /* PartijID */,
   WplOverlijden                 Integer                                   /* PlaatsID */,
   BLPlaatsOverlijden            Varchar(40)                               /* BLPlaats */,
   BLRegioOverlijden             Varchar(35)                               /* BLRegio */,
   OmsLocOverlijden              Varchar(40)                               /* LocOms */,
   LandOverlijden                Integer                       NOT NULL    /* LandID */,
   CONSTRAINT R4540 PRIMARY KEY (ID),
   CONSTRAINT R4157 UNIQUE (Pers, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PersOverlijden OWNED BY Kern.His_PersOverlijden.ID;
CREATE SEQUENCE Kern.seq_His_PersPK;
CREATE TABLE Kern.His_PersPK (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersPK')  /* His_PersPKID */,
   Pers                          Integer                                   /* PersID */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   GemPK                         Smallint                                  /* PartijID */,
   IndPKVolledigGeconv           Boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT R4561 PRIMARY KEY (ID),
   CONSTRAINT R4224 UNIQUE (Pers, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PersPK OWNED BY Kern.His_PersPK.ID;
CREATE SEQUENCE Kern.seq_His_PersSamengesteldeNaam;
CREATE TABLE Kern.His_PersSamengesteldeNaam (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersSamengesteldeNaam')  /* His_PersSamengesteldeNaamID */,
   Pers                          Integer                                   /* PersID */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   ActieAanpGel                  BigInt                                    /* ActieID */,
   IndAlgoritmischAfgeleid       Boolean                       NOT NULL    /* JaNee */,
   IndNreeks                     Boolean                       NOT NULL    /* JaNee */,
   Predikaat                     Smallint                                  /* PredikaatID */,
   Voornamen                     Varchar(200)                              /* Voornamen */,
   AdellijkeTitel                Smallint                                  /* AdellijkeTitelID */,
   Voorvoegsel                   Varchar(10)                               /* Voorvoegsel1 */,
   Scheidingsteken               Varchar(1)                                /* Scheidingsteken */,
   Geslnaam                      Varchar(200)                  NOT NULL    /* Geslnaam */,
   CONSTRAINT R4531 PRIMARY KEY (ID),
   CONSTRAINT R4114 UNIQUE (Pers, TsReg, DatAanvGel)
);
ALTER SEQUENCE Kern.seq_His_PersSamengesteldeNaam OWNED BY Kern.His_PersSamengesteldeNaam.ID;
CREATE SEQUENCE Kern.seq_His_PersUitslNLKiesr;
CREATE TABLE Kern.His_PersUitslNLKiesr (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersUitslNLKiesr')  /* His_PersUitslNLKiesrID */,
   Pers                          Integer                                   /* PersID */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   IndUitslNLKiesr               Boolean CHECK (IndUitslNLKiesr IS NULL OR IndUitslNLKiesr IN ('T'))  NOT NULL    /* Ja */,
   DatEindeUitslNLKiesr          Integer                                   /* Dat */,
   CONSTRAINT R4546 PRIMARY KEY (ID),
   CONSTRAINT R4178 UNIQUE (Pers, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PersUitslNLKiesr OWNED BY Kern.His_PersUitslNLKiesr.ID;
CREATE SEQUENCE Kern.seq_His_PersVerblijfstitel;
CREATE TABLE Kern.His_PersVerblijfstitel (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersVerblijfstitel')  /* His_PersVerblijfstitelID */,
   Pers                          Integer                                   /* PersID */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   ActieAanpGel                  BigInt                                    /* ActieID */,
   Verblijfstitel                Smallint                      NOT NULL    /* VerblijfsrID */,
   DatAanvVerblijfstitel         Integer                       NOT NULL    /* Dat */,
   DatVoorzEindeVerblijfstitel   Integer                                   /* Dat */,
   CONSTRAINT R4543 PRIMARY KEY (ID),
   CONSTRAINT R4170 UNIQUE (Pers, TsReg, DatAanvGel)
);
ALTER SEQUENCE Kern.seq_His_PersVerblijfstitel OWNED BY Kern.His_PersVerblijfstitel.ID;
CREATE SEQUENCE Kern.seq_His_PersAdres;
CREATE TABLE Kern.His_PersAdres (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersAdres')  /* His_PersAdresID */,
   PersAdres                     Integer                                   /* PersAdresID */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   ActieAanpGel                  BigInt                                    /* ActieID */,
   Srt                           Smallint                                  /* SrtAdresID */,
   RdnWijz                       Smallint                                  /* RdnWijzAdresID */,
   AangAdresh                    Smallint                                  /* AangAdreshID */,
   DatAanvAdresh                 Integer                                   /* Dat */,
   AdresseerbaarObject           Varchar(16)                               /* AandAdresseerbaarObject */,
   IdentcodeNraand               Varchar(16)                               /* IdentcodeNraand */,
   Gem                           Smallint                                  /* PartijID */,
   NOR                           Varchar(80)                               /* NOR */,
   AfgekorteNOR                  Varchar(24)                               /* AfgekorteNOR */,
   Gemdeel                       Varchar(24)                               /* Gemdeel */,
   Huisnr                        Integer                                   /* Huisnr */,
   Huisletter                    Varchar(1)                                /* Huisletter */,
   Huisnrtoevoeging              Varchar(4)                                /* Huisnrtoevoeging */,
   Postcode                      Varchar(6)                                /* Postcode */,
   Wpl                           Integer                                   /* PlaatsID */,
   LoctovAdres                   Varchar(2) CHECK (LoctovAdres IS NULL OR LoctovAdres IN ('by','to'))              /* AandBijHuisnr */,
   LocOms                        Varchar(40)                               /* LocOms */,
   DatVertrekUitNederland        Integer                                   /* Dat */,
   BLAdresRegel1                 Varchar(40)                               /* Adresregel */,
   BLAdresRegel2                 Varchar(40)                               /* Adresregel */,
   BLAdresRegel3                 Varchar(40)                               /* Adresregel */,
   BLAdresRegel4                 Varchar(40)                               /* Adresregel */,
   BLAdresRegel5                 Varchar(40)                               /* Adresregel */,
   BLAdresRegel6                 Varchar(40)                               /* Adresregel */,
   Land                          Integer                       NOT NULL    /* LandID */,
   IndPersNietAangetroffenOpAdr  Boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT R6076 PRIMARY KEY (ID),
   CONSTRAINT R6073 UNIQUE (PersAdres, TsReg, DatAanvGel)
);
ALTER SEQUENCE Kern.seq_His_PersAdres OWNED BY Kern.His_PersAdres.ID;
CREATE SEQUENCE Kern.seq_His_PersGeslnaamcomp;
CREATE TABLE Kern.His_PersGeslnaamcomp (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersGeslnaamcomp')  /* His_PersGeslnaamcompID */,
   PersGeslnaamcomp              Integer                                   /* PersGeslnaamID */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   ActieAanpGel                  BigInt                                    /* ActieID */,
   Predikaat                     Smallint                                  /* PredikaatID */,
   AdellijkeTitel                Smallint                                  /* AdellijkeTitelID */,
   Voorvoegsel                   Varchar(10)                               /* Voorvoegsel1 */,
   Scheidingsteken               Varchar(1)                                /* Scheidingsteken */,
   Naam                          Varchar(200)                  NOT NULL    /* Geslnaamcomp */,
   CONSTRAINT R4579 PRIMARY KEY (ID),
   CONSTRAINT R4314 UNIQUE (PersGeslnaamcomp, TsReg, DatAanvGel)
);
ALTER SEQUENCE Kern.seq_His_PersGeslnaamcomp OWNED BY Kern.His_PersGeslnaamcomp.ID;
CREATE SEQUENCE Kern.seq_His_PersIndicatie;
CREATE TABLE Kern.His_PersIndicatie (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersIndicatie')  /* His_PersIndicatieID */,
   PersIndicatie                 Integer                                   /* PersIndicatieID */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   ActieAanpGel                  BigInt                                    /* ActieID */,
   Waarde                        Boolean CHECK (Waarde IS NULL OR Waarde IN ('T'))  NOT NULL    /* Ja */,
   CONSTRAINT R4582 PRIMARY KEY (ID),
   CONSTRAINT R4324 UNIQUE (PersIndicatie, TsReg, DatAanvGel)
);
ALTER SEQUENCE Kern.seq_His_PersIndicatie OWNED BY Kern.His_PersIndicatie.ID;
CREATE SEQUENCE Kern.seq_His_PersNation;
CREATE TABLE Kern.His_PersNation (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersNation')  /* His_PersNationID */,
   PersNation                    Integer                                   /* PersNationID */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   ActieAanpGel                  BigInt                                    /* ActieID */,
   RdnVerk                       Smallint                                  /* RdnVerkNLNationID */,
   RdnVerlies                    Smallint                                  /* RdnVerliesNLNationID */,
   CONSTRAINT R4585 PRIMARY KEY (ID),
   CONSTRAINT R4335 UNIQUE (PersNation, TsReg, DatAanvGel)
);
ALTER SEQUENCE Kern.seq_His_PersNation OWNED BY Kern.His_PersNation.ID;
CREATE SEQUENCE Kern.seq_His_PersReisdoc;
CREATE TABLE Kern.His_PersReisdoc (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersReisdoc')  /* His_PersReisdocID */,
   PersReisdoc                   Integer                                   /* ReisdocID */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   Nr                            Varchar(9)                    NOT NULL    /* ReisdocNr */,
   LengteHouder                  Numeric(3)                                /* LengteInCm */,
   AutVanAfgifte                 Integer                       NOT NULL    /* AutVanAfgifteReisdocID */,
   DatIngangDoc                  Integer                       NOT NULL    /* Dat */,
   DatUitgifte                   Integer                       NOT NULL    /* Dat */,
   DatVoorzeEindeGel             Integer                       NOT NULL    /* Dat */,
   DatInhingVermissing           Integer                                   /* Dat */,
   RdnVervallen                  Smallint                                  /* RdnOntbrReisdocID */,
   CONSTRAINT R4588 PRIMARY KEY (ID),
   CONSTRAINT R4351 UNIQUE (PersReisdoc, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PersReisdoc OWNED BY Kern.His_PersReisdoc.ID;
CREATE SEQUENCE Kern.seq_His_PersVerificatie;
CREATE TABLE Kern.His_PersVerificatie (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersVerificatie')  /* His_PersVerificatieID */,
   PersVerificatie               BigInt                                    /* VerificatieID */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   Dat                           Integer                       NOT NULL    /* Dat */,
   CONSTRAINT R4591 PRIMARY KEY (ID),
   CONSTRAINT R4358 UNIQUE (PersVerificatie, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PersVerificatie OWNED BY Kern.His_PersVerificatie.ID;
CREATE SEQUENCE Kern.seq_His_PersVoornaam;
CREATE TABLE Kern.His_PersVoornaam (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_PersVoornaam')  /* His_PersVoornaamID */,
   PersVoornaam                  Integer                                   /* PersVoornaamID */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   ActieAanpGel                  BigInt                                    /* ActieID */,
   Naam                          Varchar(40)                   NOT NULL    /* Voornaam */,
   CONSTRAINT R4594 PRIMARY KEY (ID),
   CONSTRAINT R4368 UNIQUE (PersVoornaam, TsReg, DatAanvGel)
);
ALTER SEQUENCE Kern.seq_His_PersVoornaam OWNED BY Kern.His_PersVoornaam.ID;
CREATE SEQUENCE Kern.seq_His_VerstrDerde;
CREATE TABLE Kern.His_VerstrDerde (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_His_VerstrDerde')  /* His_VerstrDerdeID */,
   VerstrDerde                   Integer                                   /* VerstrenDerdeID */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      BigInt                                    /* ActieID */,
   ActieVerval                   BigInt                                    /* ActieID */,
   CONSTRAINT R9377 PRIMARY KEY (ID),
   CONSTRAINT R9369 UNIQUE (VerstrDerde, TsReg)
);
ALTER SEQUENCE Kern.seq_His_VerstrDerde OWNED BY Kern.His_VerstrDerde.ID;

-- Foreign keys ----------------------------------------------------------------
ALTER TABLE AutAut.Authenticatiemiddel ADD CONSTRAINT FK2232 FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE AutAut.Authenticatiemiddel ADD CONSTRAINT FK2233 FOREIGN KEY (Rol) REFERENCES Kern.Rol (ID);
ALTER TABLE AutAut.Authenticatiemiddel ADD CONSTRAINT FK4665 FOREIGN KEY (Functie) REFERENCES AutAut.Functie (ID);
ALTER TABLE AutAut.Authenticatiemiddel ADD CONSTRAINT FK4667 FOREIGN KEY (CertificaatTbvSSL) REFERENCES AutAut.Certificaat (ID);
ALTER TABLE AutAut.Authenticatiemiddel ADD CONSTRAINT FK4668 FOREIGN KEY (CertificaatTbvOndertekening) REFERENCES AutAut.Certificaat (ID);
ALTER TABLE AutAut.Autorisatiebesluit ADD CONSTRAINT FK4863 FOREIGN KEY (Srt) REFERENCES AutAut.SrtAutorisatiebesluit (ID);
ALTER TABLE AutAut.Autorisatiebesluit ADD CONSTRAINT FK4866 FOREIGN KEY (Autoriseerder) REFERENCES Kern.Partij (ID);
ALTER TABLE AutAut.Autorisatiebesluit ADD CONSTRAINT FK4868 FOREIGN KEY (GebaseerdOp) REFERENCES AutAut.Autorisatiebesluit (ID);
ALTER TABLE AutAut.Autorisatiebesluit ADD CONSTRAINT FK5677 FOREIGN KEY (Toestand) REFERENCES AutAut.Toestand (ID);
ALTER TABLE AutAut.Bijhautorisatie ADD CONSTRAINT FK5691 FOREIGN KEY (Bijhautorisatiebesluit) REFERENCES AutAut.Autorisatiebesluit (ID);
ALTER TABLE AutAut.Bijhautorisatie ADD CONSTRAINT FK5713 FOREIGN KEY (SrtBevoegdheid) REFERENCES AutAut.SrtBevoegdheid (ID);
ALTER TABLE AutAut.Bijhautorisatie ADD CONSTRAINT FK5704 FOREIGN KEY (GeautoriseerdeSrtPartij) REFERENCES Kern.SrtPartij (ID);
ALTER TABLE AutAut.Bijhautorisatie ADD CONSTRAINT FK5703 FOREIGN KEY (GeautoriseerdePartij) REFERENCES Kern.Partij (ID);
ALTER TABLE AutAut.Bijhautorisatie ADD CONSTRAINT FK5689 FOREIGN KEY (Toestand) REFERENCES AutAut.Toestand (ID);
ALTER TABLE AutAut.Bijhautorisatie ADD CONSTRAINT FK6165 FOREIGN KEY (CategoriePersonen) REFERENCES Kern.CategoriePersonen (ID);
ALTER TABLE AutAut.Bijhsituatie ADD CONSTRAINT FK5701 FOREIGN KEY (Bijhautorisatie) REFERENCES AutAut.Bijhautorisatie (ID);
ALTER TABLE AutAut.Bijhsituatie ADD CONSTRAINT FK6039 FOREIGN KEY (SrtAdmHnd) REFERENCES Kern.SrtAdmHnd (ID);
ALTER TABLE AutAut.Bijhsituatie ADD CONSTRAINT FK5698 FOREIGN KEY (SrtDoc) REFERENCES Kern.SrtDoc (ID);
ALTER TABLE AutAut.Doelbinding ADD CONSTRAINT FK4885 FOREIGN KEY (Levsautorisatiebesluit) REFERENCES AutAut.Autorisatiebesluit (ID);
ALTER TABLE AutAut.Doelbinding ADD CONSTRAINT FK4884 FOREIGN KEY (Geautoriseerde) REFERENCES Kern.Partij (ID);
ALTER TABLE AutAut.Doelbinding ADD CONSTRAINT FK4883 FOREIGN KEY (Protocolleringsniveau) REFERENCES AutAut.Protocolleringsniveau (ID);
ALTER TABLE AutAut.DoelbindingGegevenselement ADD CONSTRAINT FK4892 FOREIGN KEY (Doelbinding) REFERENCES AutAut.Doelbinding (ID);
ALTER TABLE AutAut.DoelbindingGegevenselement ADD CONSTRAINT FK4893 FOREIGN KEY (Gegevenselement) REFERENCES Kern.DbObject (ID);
ALTER TABLE AutAut.His_Authenticatiemiddel ADD CONSTRAINT FK4678 FOREIGN KEY (Authenticatiemiddel) REFERENCES AutAut.Authenticatiemiddel (ID);
ALTER TABLE AutAut.His_Authenticatiemiddel ADD CONSTRAINT FK4681 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON AutAut.His_Authenticatiemiddel (ActieInh);
ALTER TABLE AutAut.His_Authenticatiemiddel ADD CONSTRAINT FK4682 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON AutAut.His_Authenticatiemiddel (ActieVerval);
ALTER TABLE AutAut.His_Authenticatiemiddel ADD CONSTRAINT FK4683 FOREIGN KEY (Functie) REFERENCES AutAut.Functie (ID);
ALTER TABLE AutAut.His_Authenticatiemiddel ADD CONSTRAINT FK4684 FOREIGN KEY (CertificaatTbvSSL) REFERENCES AutAut.Certificaat (ID);
ALTER TABLE AutAut.His_Authenticatiemiddel ADD CONSTRAINT FK4685 FOREIGN KEY (CertificaatTbvOndertekening) REFERENCES AutAut.Certificaat (ID);
ALTER TABLE AutAut.His_Autorisatiebesluit ADD CONSTRAINT FK4959 FOREIGN KEY (Autorisatiebesluit) REFERENCES AutAut.Autorisatiebesluit (ID);
ALTER TABLE AutAut.His_Autorisatiebesluit ADD CONSTRAINT FK4962 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON AutAut.His_Autorisatiebesluit (ActieInh);
ALTER TABLE AutAut.His_Autorisatiebesluit ADD CONSTRAINT FK4963 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON AutAut.His_Autorisatiebesluit (ActieVerval);
ALTER TABLE AutAut.His_AutorisatiebesluitBijhau ADD CONSTRAINT FK5723 FOREIGN KEY (Autorisatiebesluit) REFERENCES AutAut.Autorisatiebesluit (ID);
ALTER TABLE AutAut.His_AutorisatiebesluitBijhau ADD CONSTRAINT FK5726 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON AutAut.His_AutorisatiebesluitBijhau (ActieInh);
ALTER TABLE AutAut.His_AutorisatiebesluitBijhau ADD CONSTRAINT FK5727 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON AutAut.His_AutorisatiebesluitBijhau (ActieVerval);
ALTER TABLE AutAut.His_AutorisatiebesluitBijhau ADD CONSTRAINT FK5728 FOREIGN KEY (Toestand) REFERENCES AutAut.Toestand (ID);
ALTER TABLE AutAut.His_Bijhautorisatie ADD CONSTRAINT FK5730 FOREIGN KEY (Bijhautorisatie) REFERENCES AutAut.Bijhautorisatie (ID);
ALTER TABLE AutAut.His_Bijhautorisatie ADD CONSTRAINT FK5733 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON AutAut.His_Bijhautorisatie (ActieInh);
ALTER TABLE AutAut.His_Bijhautorisatie ADD CONSTRAINT FK5734 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON AutAut.His_Bijhautorisatie (ActieVerval);
ALTER TABLE AutAut.His_Bijhautorisatie ADD CONSTRAINT FK5790 FOREIGN KEY (SrtBevoegdheid) REFERENCES AutAut.SrtBevoegdheid (ID);
ALTER TABLE AutAut.His_Bijhautorisatie ADD CONSTRAINT FK5738 FOREIGN KEY (GeautoriseerdeSrtPartij) REFERENCES Kern.SrtPartij (ID);
ALTER TABLE AutAut.His_Bijhautorisatie ADD CONSTRAINT FK5737 FOREIGN KEY (GeautoriseerdePartij) REFERENCES Kern.Partij (ID);
ALTER TABLE AutAut.His_Bijhautorisatie ADD CONSTRAINT FK5735 FOREIGN KEY (Toestand) REFERENCES AutAut.Toestand (ID);
ALTER TABLE AutAut.His_Bijhautorisatie ADD CONSTRAINT FK6166 FOREIGN KEY (CategoriePersonen) REFERENCES Kern.CategoriePersonen (ID);
ALTER TABLE AutAut.His_Bijhsituatie ADD CONSTRAINT FK5740 FOREIGN KEY (Bijhsituatie) REFERENCES AutAut.Bijhsituatie (ID);
ALTER TABLE AutAut.His_Bijhsituatie ADD CONSTRAINT FK5743 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON AutAut.His_Bijhsituatie (ActieInh);
ALTER TABLE AutAut.His_Bijhsituatie ADD CONSTRAINT FK5744 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON AutAut.His_Bijhsituatie (ActieVerval);
ALTER TABLE AutAut.His_Doelbinding ADD CONSTRAINT FK4966 FOREIGN KEY (Doelbinding) REFERENCES AutAut.Doelbinding (ID);
ALTER TABLE AutAut.His_Doelbinding ADD CONSTRAINT FK4971 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON AutAut.His_Doelbinding (ActieInh);
ALTER TABLE AutAut.His_Doelbinding ADD CONSTRAINT FK4972 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON AutAut.His_Doelbinding (ActieVerval);
ALTER TABLE AutAut.His_Doelbinding ADD CONSTRAINT FK4973 FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
CREATE INDEX ON AutAut.His_Doelbinding (ActieAanpGel);
ALTER TABLE AutAut.His_Doelbinding ADD CONSTRAINT FK4974 FOREIGN KEY (Protocolleringsniveau) REFERENCES AutAut.Protocolleringsniveau (ID);
ALTER TABLE AutAut.His_Uitgeslotene ADD CONSTRAINT FK5797 FOREIGN KEY (Uitgeslotene) REFERENCES AutAut.Uitgeslotene (ID);
ALTER TABLE AutAut.His_Uitgeslotene ADD CONSTRAINT FK5787 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON AutAut.His_Uitgeslotene (ActieInh);
ALTER TABLE AutAut.His_Uitgeslotene ADD CONSTRAINT FK5788 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON AutAut.His_Uitgeslotene (ActieVerval);
ALTER TABLE AutAut.Uitgeslotene ADD CONSTRAINT FK5779 FOREIGN KEY (Bijhautorisatie) REFERENCES AutAut.Bijhautorisatie (ID);
ALTER TABLE AutAut.Uitgeslotene ADD CONSTRAINT FK5780 FOREIGN KEY (UitgeslotenPartij) REFERENCES Kern.Partij (ID);
ALTER TABLE BRM.His_Regelsituatie ADD CONSTRAINT FK6157 FOREIGN KEY (Regelsituatie) REFERENCES BRM.Regelsituatie (ID);
ALTER TABLE BRM.His_Regelsituatie ADD CONSTRAINT FK5968 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON BRM.His_Regelsituatie (ActieInh);
ALTER TABLE BRM.His_Regelsituatie ADD CONSTRAINT FK5969 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON BRM.His_Regelsituatie (ActieVerval);
ALTER TABLE BRM.His_Regelsituatie ADD CONSTRAINT FK6021 FOREIGN KEY (Bijhverantwoordelijkheid) REFERENCES Kern.Bijhaard (ID);
ALTER TABLE BRM.His_Regelsituatie ADD CONSTRAINT FK6022 FOREIGN KEY (RdnOpschorting) REFERENCES Kern.RdnOpschorting (ID);
ALTER TABLE BRM.His_Regelsituatie ADD CONSTRAINT FK5970 FOREIGN KEY (Effect) REFERENCES BRM.Regeleffect (ID);
ALTER TABLE BRM.RegelSrtBer ADD CONSTRAINT FK6058 FOREIGN KEY (Regel) REFERENCES Kern.Regel (ID);
ALTER TABLE BRM.RegelSrtBer ADD CONSTRAINT FK6059 FOREIGN KEY (SrtBer) REFERENCES Ber.SrtBer (ID);
ALTER TABLE BRM.Regelsituatie ADD CONSTRAINT FK5828 FOREIGN KEY (Regelimplementatie) REFERENCES BRM.RegelSrtBer (ID);
ALTER TABLE BRM.Regelsituatie ADD CONSTRAINT FK5999 FOREIGN KEY (Bijhverantwoordelijkheid) REFERENCES Kern.Bijhaard (ID);
ALTER TABLE BRM.Regelsituatie ADD CONSTRAINT FK6000 FOREIGN KEY (RdnOpschorting) REFERENCES Kern.RdnOpschorting (ID);
ALTER TABLE BRM.Regelsituatie ADD CONSTRAINT FK5829 FOREIGN KEY (Effect) REFERENCES BRM.Regeleffect (ID);
ALTER TABLE Ber.SrtBer ADD CONSTRAINT FK9518 FOREIGN KEY (Module) REFERENCES Ber.BurgerzakenModule (ID);
ALTER TABLE Kern.DbObject ADD CONSTRAINT FK5644 FOREIGN KEY (Srt) REFERENCES Kern.SrtDbObject (ID);
ALTER TABLE Kern.DbObject ADD CONSTRAINT FK5661 FOREIGN KEY (Ouder) REFERENCES Kern.DbObject (ID);
ALTER TABLE Kern.Element ADD CONSTRAINT FK3721 FOREIGN KEY (Srt) REFERENCES Kern.SrtElement (ID);
ALTER TABLE Kern.Element ADD CONSTRAINT FK5663 FOREIGN KEY (Ouder) REFERENCES Kern.Element (ID);
ALTER TABLE Kern.His_Partij ADD CONSTRAINT FK4620 FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.His_Partij ADD CONSTRAINT FK4623 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_Partij (ActieInh);
ALTER TABLE Kern.His_Partij ADD CONSTRAINT FK4624 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_Partij (ActieVerval);
ALTER TABLE Kern.His_Partij ADD CONSTRAINT FK4627 FOREIGN KEY (Sector) REFERENCES Kern.Sector (ID);
ALTER TABLE Kern.His_PartijGem ADD CONSTRAINT FK6117 FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.His_PartijGem ADD CONSTRAINT FK6120 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PartijGem (ActieInh);
ALTER TABLE Kern.His_PartijGem ADD CONSTRAINT FK6121 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PartijGem (ActieVerval);
ALTER TABLE Kern.His_PartijGem ADD CONSTRAINT FK4611 FOREIGN KEY (VoortzettendeGem) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.His_PartijGem ADD CONSTRAINT FK4613 FOREIGN KEY (OnderdeelVan) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.Partij ADD CONSTRAINT FK2195 FOREIGN KEY (Srt) REFERENCES Kern.SrtPartij (ID);
ALTER TABLE Kern.Partij ADD CONSTRAINT FK2196 FOREIGN KEY (Sector) REFERENCES Kern.Sector (ID);
ALTER TABLE Kern.Partij ADD CONSTRAINT FK2005 FOREIGN KEY (VoortzettendeGem) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.Partij ADD CONSTRAINT FK4602 FOREIGN KEY (OnderdeelVan) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.PartijRol ADD CONSTRAINT FK2185 FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.PartijRol ADD CONSTRAINT FK2186 FOREIGN KEY (Rol) REFERENCES Kern.Rol (ID);
ALTER TABLE Kern.Rechtsgrond ADD CONSTRAINT FK8141 FOREIGN KEY (Srt) REFERENCES Kern.SrtRechtsgrond (ID);
ALTER TABLE Kern.Regel ADD CONSTRAINT FK5990 FOREIGN KEY (Srt) REFERENCES BRM.SrtRegel (ID);
ALTER TABLE Kern.SrtAdmHnd ADD CONSTRAINT FK9532 FOREIGN KEY (Module) REFERENCES Ber.BurgerzakenModule (ID);
ALTER TABLE Lev.Abonnement ADD CONSTRAINT FK4902 FOREIGN KEY (Doelbinding) REFERENCES AutAut.Doelbinding (ID);
ALTER TABLE Lev.Abonnement ADD CONSTRAINT FK4903 FOREIGN KEY (SrtAbonnement) REFERENCES Lev.SrtAbonnement (ID);
ALTER TABLE Lev.AbonnementGegevenselement ADD CONSTRAINT FK4907 FOREIGN KEY (Abonnement) REFERENCES Lev.Abonnement (ID);
ALTER TABLE Lev.AbonnementGegevenselement ADD CONSTRAINT FK4908 FOREIGN KEY (Gegevenselement) REFERENCES Kern.DbObject (ID);
ALTER TABLE Lev.AbonnementSrtBer ADD CONSTRAINT FK5596 FOREIGN KEY (Abonnement) REFERENCES Lev.Abonnement (ID);
ALTER TABLE Lev.AbonnementSrtBer ADD CONSTRAINT FK5597 FOREIGN KEY (SrtBer) REFERENCES Ber.SrtBer (ID);
ALTER TABLE Lev.His_Abonnement ADD CONSTRAINT FK4952 FOREIGN KEY (Abonnement) REFERENCES Lev.Abonnement (ID);
ALTER TABLE Lev.His_Abonnement ADD CONSTRAINT FK4955 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Lev.His_Abonnement (ActieInh);
ALTER TABLE Lev.His_Abonnement ADD CONSTRAINT FK4956 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Lev.His_Abonnement (ActieVerval);
ALTER TABLE Lev.His_AbonnementSrtBer ADD CONSTRAINT FK5601 FOREIGN KEY (AbonnementSrtBer) REFERENCES Lev.AbonnementSrtBer (ID);
ALTER TABLE Lev.His_AbonnementSrtBer ADD CONSTRAINT FK5604 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Lev.His_AbonnementSrtBer (ActieInh);
ALTER TABLE Lev.His_AbonnementSrtBer ADD CONSTRAINT FK5605 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Lev.His_AbonnementSrtBer (ActieVerval);
ALTER TABLE Ber.AdmHndBijgehoudenPers ADD CONSTRAINT FK6252 FOREIGN KEY (AdmHnd) REFERENCES Kern.AdmHnd (ID);
CREATE INDEX ON Ber.AdmHndBijgehoudenPers (AdmHnd);
ALTER TABLE Ber.AdmHndBijgehoudenPers ADD CONSTRAINT FK6253 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
CREATE INDEX ON Ber.AdmHndBijgehoudenPers (Pers);
ALTER TABLE Ber.AdmHndGedeblokkeerdeMelding ADD CONSTRAINT FK6226 FOREIGN KEY (AdmHnd) REFERENCES Kern.AdmHnd (ID);
CREATE INDEX ON Ber.AdmHndGedeblokkeerdeMelding (AdmHnd);
ALTER TABLE Ber.AdmHndGedeblokkeerdeMelding ADD CONSTRAINT FK6227 FOREIGN KEY (GedeblokkeerdeMelding) REFERENCES Ber.GedeblokkeerdeMelding (ID);
CREATE INDEX ON Ber.AdmHndGedeblokkeerdeMelding (GedeblokkeerdeMelding);
ALTER TABLE Ber.Ber ADD CONSTRAINT FK6183 FOREIGN KEY (Srt) REFERENCES Ber.SrtBer (ID);
ALTER TABLE Ber.Ber ADD CONSTRAINT FK9301 FOREIGN KEY (AdmHnd) REFERENCES Kern.AdmHnd (ID);
CREATE INDEX ON Ber.Ber (AdmHnd);
ALTER TABLE Ber.Ber ADD CONSTRAINT FK5612 FOREIGN KEY (AntwoordOp) REFERENCES Ber.Ber (ID);
CREATE INDEX ON Ber.Ber (AntwoordOp);
ALTER TABLE Ber.Ber ADD CONSTRAINT FK5633 FOREIGN KEY (Richting) REFERENCES Ber.Richting (ID);
ALTER TABLE Ber.Ber ADD CONSTRAINT FK6230 FOREIGN KEY (Verwerking) REFERENCES Ber.Verwerkingsresultaat (ID);
ALTER TABLE Ber.Ber ADD CONSTRAINT FK6245 FOREIGN KEY (Bijhouding) REFERENCES Ber.Bijhresultaat (ID);
ALTER TABLE Ber.Ber ADD CONSTRAINT FK6246 FOREIGN KEY (HoogsteMeldingsniveau) REFERENCES Ber.SrtMelding (ID);
ALTER TABLE Ber.BerMelding ADD CONSTRAINT FK6214 FOREIGN KEY (Ber) REFERENCES Ber.Ber (ID);
CREATE INDEX ON Ber.BerMelding (Ber);
ALTER TABLE Ber.BerMelding ADD CONSTRAINT FK6215 FOREIGN KEY (Melding) REFERENCES Ber.Melding (ID);
CREATE INDEX ON Ber.BerMelding (Melding);
ALTER TABLE Ber.GedeblokkeerdeMelding ADD CONSTRAINT FK6220 FOREIGN KEY (Regel) REFERENCES Kern.Regel (ID);
ALTER TABLE Ber.GedeblokkeerdeMelding ADD CONSTRAINT FK6221 FOREIGN KEY (Attribuut) REFERENCES Kern.Element (ID);
ALTER TABLE Ber.Melding ADD CONSTRAINT FK6198 FOREIGN KEY (Regel) REFERENCES Kern.Regel (ID);
ALTER TABLE Ber.Melding ADD CONSTRAINT FK6206 FOREIGN KEY (Srt) REFERENCES Ber.SrtMelding (ID);
ALTER TABLE Ber.Melding ADD CONSTRAINT FK6209 FOREIGN KEY (Attribuut) REFERENCES Kern.Element (ID);
ALTER TABLE Kern.Actie ADD CONSTRAINT FK3055 FOREIGN KEY (Srt) REFERENCES Kern.SrtActie (ID);
ALTER TABLE Kern.Actie ADD CONSTRAINT FK9023 FOREIGN KEY (AdmHnd) REFERENCES Kern.AdmHnd (ID);
CREATE INDEX ON Kern.Actie (AdmHnd);
ALTER TABLE Kern.Actie ADD CONSTRAINT FK3209 FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.ActieBron ADD CONSTRAINT FK8122 FOREIGN KEY (Actie) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.ActieBron (Actie);
ALTER TABLE Kern.ActieBron ADD CONSTRAINT FK8123 FOREIGN KEY (Doc) REFERENCES Kern.Doc (ID);
CREATE INDEX ON Kern.ActieBron (Doc);
ALTER TABLE Kern.ActieBron ADD CONSTRAINT FK8124 FOREIGN KEY (Rechtsgrond) REFERENCES Kern.Rechtsgrond (ID);
ALTER TABLE Kern.AdmHnd ADD CONSTRAINT FK9208 FOREIGN KEY (Srt) REFERENCES Kern.SrtAdmHnd (ID);
ALTER TABLE Kern.AdmHnd ADD CONSTRAINT FK9172 FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.Betr ADD CONSTRAINT FK3860 FOREIGN KEY (Relatie) REFERENCES Kern.Relatie (ID);
CREATE INDEX ON Kern.Betr (Relatie);
ALTER TABLE Kern.Betr ADD CONSTRAINT FK3861 FOREIGN KEY (Rol) REFERENCES Kern.SrtBetr (ID);
ALTER TABLE Kern.Betr ADD CONSTRAINT FK3859 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
CREATE INDEX ON Kern.Betr (Pers);
ALTER TABLE Kern.Doc ADD CONSTRAINT FK3157 FOREIGN KEY (Srt) REFERENCES Kern.SrtDoc (ID);
ALTER TABLE Kern.Doc ADD CONSTRAINT FK3139 FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.GegevenInOnderzoek ADD CONSTRAINT FK3865 FOREIGN KEY (Onderzoek) REFERENCES Kern.Onderzoek (ID);
CREATE INDEX ON Kern.GegevenInOnderzoek (Onderzoek);
ALTER TABLE Kern.GegevenInOnderzoek ADD CONSTRAINT FK3866 FOREIGN KEY (SrtGegeven) REFERENCES Kern.DbObject (ID);
ALTER TABLE Kern.MultiRealiteitRegel ADD CONSTRAINT FK2050 FOREIGN KEY (GeldigVoor) REFERENCES Kern.Pers (ID);
CREATE INDEX ON Kern.MultiRealiteitRegel (GeldigVoor);
ALTER TABLE Kern.MultiRealiteitRegel ADD CONSTRAINT FK2051 FOREIGN KEY (Srt) REFERENCES Kern.SrtMultiRealiteitRegel (ID);
ALTER TABLE Kern.MultiRealiteitRegel ADD CONSTRAINT FK2053 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
CREATE INDEX ON Kern.MultiRealiteitRegel (Pers);
ALTER TABLE Kern.MultiRealiteitRegel ADD CONSTRAINT FK2054 FOREIGN KEY (MultiRealiteitPers) REFERENCES Kern.Pers (ID);
CREATE INDEX ON Kern.MultiRealiteitRegel (MultiRealiteitPers);
ALTER TABLE Kern.MultiRealiteitRegel ADD CONSTRAINT FK2055 FOREIGN KEY (Relatie) REFERENCES Kern.Relatie (ID);
CREATE INDEX ON Kern.MultiRealiteitRegel (Relatie);
ALTER TABLE Kern.MultiRealiteitRegel ADD CONSTRAINT FK2056 FOREIGN KEY (Betr) REFERENCES Kern.Betr (ID);
CREATE INDEX ON Kern.MultiRealiteitRegel (Betr);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK1997 FOREIGN KEY (Srt) REFERENCES Kern.SrtPers (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK1969 FOREIGN KEY (Predikaat) REFERENCES Kern.Predikaat (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK1968 FOREIGN KEY (AdellijkeTitel) REFERENCES Kern.AdellijkeTitel (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK3675 FOREIGN KEY (GemGeboorte) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK3676 FOREIGN KEY (WplGeboorte) REFERENCES Kern.Plaats (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK3543 FOREIGN KEY (LandGeboorte) REFERENCES Kern.Land (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK3031 FOREIGN KEY (Geslachtsaand) REFERENCES Kern.Geslachtsaand (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK3666 FOREIGN KEY (VorigePers) REFERENCES Kern.Pers (ID);
CREATE INDEX ON Kern.Pers (VorigePers);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK3667 FOREIGN KEY (VolgendePers) REFERENCES Kern.Pers (ID);
CREATE INDEX ON Kern.Pers (VolgendePers);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK3568 FOREIGN KEY (Bijhaard) REFERENCES Kern.Bijhaard (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK3573 FOREIGN KEY (Bijhgem) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK3663 FOREIGN KEY (RdnOpschortingBijhouding) REFERENCES Kern.RdnOpschorting (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK3551 FOREIGN KEY (GemOverlijden) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK3544 FOREIGN KEY (WplOverlijden) REFERENCES Kern.Plaats (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK3558 FOREIGN KEY (LandOverlijden) REFERENCES Kern.Land (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK3593 FOREIGN KEY (Naamgebruik) REFERENCES Kern.WijzeGebruikGeslnaam (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK3703 FOREIGN KEY (PredikaatAanschr) REFERENCES Kern.Predikaat (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK6113 FOREIGN KEY (AdellijkeTitelAanschr) REFERENCES Kern.AdellijkeTitel (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK3579 FOREIGN KEY (LandVanwaarGevestigd) REFERENCES Kern.Land (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK3310 FOREIGN KEY (Verblijfstitel) REFERENCES Kern.Verblijfstitel (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK9322 FOREIGN KEY (BVP) REFERENCES Kern.BVP (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK3233 FOREIGN KEY (GemPK) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.PersAdres ADD CONSTRAINT FK3241 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
CREATE INDEX ON Kern.PersAdres (Pers);
ALTER TABLE Kern.PersAdres ADD CONSTRAINT FK3263 FOREIGN KEY (Srt) REFERENCES Kern.FunctieAdres (ID);
ALTER TABLE Kern.PersAdres ADD CONSTRAINT FK3715 FOREIGN KEY (RdnWijz) REFERENCES Kern.RdnWijzAdres (ID);
ALTER TABLE Kern.PersAdres ADD CONSTRAINT FK3301 FOREIGN KEY (AangAdresh) REFERENCES Kern.AangAdresh (ID);
ALTER TABLE Kern.PersAdres ADD CONSTRAINT FK3788 FOREIGN KEY (Gem) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.PersAdres ADD CONSTRAINT FK3282 FOREIGN KEY (Wpl) REFERENCES Kern.Plaats (ID);
ALTER TABLE Kern.PersAdres ADD CONSTRAINT FK3289 FOREIGN KEY (Land) REFERENCES Kern.Land (ID);
ALTER TABLE Kern.PersGeslnaamcomp ADD CONSTRAINT FK3024 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
CREATE INDEX ON Kern.PersGeslnaamcomp (Pers);
ALTER TABLE Kern.PersGeslnaamcomp ADD CONSTRAINT FK3117 FOREIGN KEY (Predikaat) REFERENCES Kern.Predikaat (ID);
ALTER TABLE Kern.PersGeslnaamcomp ADD CONSTRAINT FK3118 FOREIGN KEY (AdellijkeTitel) REFERENCES Kern.AdellijkeTitel (ID);
ALTER TABLE Kern.PersIndicatie ADD CONSTRAINT FK3657 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
CREATE INDEX ON Kern.PersIndicatie (Pers);
ALTER TABLE Kern.PersIndicatie ADD CONSTRAINT FK3658 FOREIGN KEY (Srt) REFERENCES Kern.SrtIndicatie (ID);
ALTER TABLE Kern.PersNation ADD CONSTRAINT FK3130 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
CREATE INDEX ON Kern.PersNation (Pers);
ALTER TABLE Kern.PersNation ADD CONSTRAINT FK3131 FOREIGN KEY (Nation) REFERENCES Kern.Nation (ID);
ALTER TABLE Kern.PersNation ADD CONSTRAINT FK3229 FOREIGN KEY (RdnVerk) REFERENCES Kern.RdnVerkNLNation (ID);
ALTER TABLE Kern.PersNation ADD CONSTRAINT FK3230 FOREIGN KEY (RdnVerlies) REFERENCES Kern.RdnVerliesNLNation (ID);
ALTER TABLE Kern.PersOnderzoek ADD CONSTRAINT FK6131 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
CREATE INDEX ON Kern.PersOnderzoek (Pers);
ALTER TABLE Kern.PersOnderzoek ADD CONSTRAINT FK6132 FOREIGN KEY (Onderzoek) REFERENCES Kern.Onderzoek (ID);
CREATE INDEX ON Kern.PersOnderzoek (Onderzoek);
ALTER TABLE Kern.PersReisdoc ADD CONSTRAINT FK3752 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
CREATE INDEX ON Kern.PersReisdoc (Pers);
ALTER TABLE Kern.PersReisdoc ADD CONSTRAINT FK3739 FOREIGN KEY (Srt) REFERENCES Kern.SrtNLReisdoc (ID);
ALTER TABLE Kern.PersReisdoc ADD CONSTRAINT FK3744 FOREIGN KEY (AutVanAfgifte) REFERENCES Kern.AutVanAfgifteReisdoc (ID);
ALTER TABLE Kern.PersReisdoc ADD CONSTRAINT FK3747 FOREIGN KEY (RdnVervallen) REFERENCES Kern.RdnVervallenReisdoc (ID);
ALTER TABLE Kern.PersVerificatie ADD CONSTRAINT FK2142 FOREIGN KEY (Geverifieerde) REFERENCES Kern.Pers (ID);
CREATE INDEX ON Kern.PersVerificatie (Geverifieerde);
ALTER TABLE Kern.PersVerificatie ADD CONSTRAINT FK3779 FOREIGN KEY (Srt) REFERENCES Kern.SrtVerificatie (ID);
ALTER TABLE Kern.PersVoornaam ADD CONSTRAINT FK3023 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
CREATE INDEX ON Kern.PersVoornaam (Pers);
ALTER TABLE Kern.Regelverantwoording ADD CONSTRAINT FK6149 FOREIGN KEY (Actie) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.Regelverantwoording (Actie);
ALTER TABLE Kern.Regelverantwoording ADD CONSTRAINT FK6152 FOREIGN KEY (Regel) REFERENCES Kern.Regel (ID);
ALTER TABLE Kern.Relatie ADD CONSTRAINT FK3198 FOREIGN KEY (Srt) REFERENCES Kern.SrtRelatie (ID);
ALTER TABLE Kern.Relatie ADD CONSTRAINT FK3755 FOREIGN KEY (GemAanv) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.Relatie ADD CONSTRAINT FK3756 FOREIGN KEY (WplAanv) REFERENCES Kern.Plaats (ID);
ALTER TABLE Kern.Relatie ADD CONSTRAINT FK3760 FOREIGN KEY (LandAanv) REFERENCES Kern.Land (ID);
ALTER TABLE Kern.Relatie ADD CONSTRAINT FK3207 FOREIGN KEY (RdnEinde) REFERENCES Kern.RdnBeeindRelatie (ID);
ALTER TABLE Kern.Relatie ADD CONSTRAINT FK3763 FOREIGN KEY (GemEinde) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.Relatie ADD CONSTRAINT FK3764 FOREIGN KEY (WplEinde) REFERENCES Kern.Plaats (ID);
ALTER TABLE Kern.Relatie ADD CONSTRAINT FK3768 FOREIGN KEY (LandEinde) REFERENCES Kern.Land (ID);
ALTER TABLE Kern.VerstrDerde ADD CONSTRAINT FK9351 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
CREATE INDEX ON Kern.VerstrDerde (Pers);
ALTER TABLE Kern.VerstrDerde ADD CONSTRAINT FK9352 FOREIGN KEY (Derde) REFERENCES Kern.Partij (ID);
ALTER TABLE Lev.Lev ADD CONSTRAINT FK4820 FOREIGN KEY (Srt) REFERENCES Lev.SrtLev (ID);
ALTER TABLE Lev.Lev ADD CONSTRAINT FK4821 FOREIGN KEY (Authenticatiemiddel) REFERENCES AutAut.Authenticatiemiddel (ID);
ALTER TABLE Lev.Lev ADD CONSTRAINT FK5626 FOREIGN KEY (Abonnement) REFERENCES Lev.Abonnement (ID);
ALTER TABLE Lev.Lev ADD CONSTRAINT FK5670 FOREIGN KEY (GebaseerdOp) REFERENCES Ber.Ber (ID);
CREATE INDEX ON Lev.Lev (GebaseerdOp);
ALTER TABLE Lev.LevCommunicatie ADD CONSTRAINT FK4835 FOREIGN KEY (Lev) REFERENCES Lev.Lev (ID);
CREATE INDEX ON Lev.LevCommunicatie (Lev);
ALTER TABLE Lev.LevCommunicatie ADD CONSTRAINT FK4836 FOREIGN KEY (UitgaandBer) REFERENCES Ber.Ber (ID);
CREATE INDEX ON Lev.LevCommunicatie (UitgaandBer);
ALTER TABLE Lev.LevPers ADD CONSTRAINT FK4842 FOREIGN KEY (Lev) REFERENCES Lev.Lev (ID);
CREATE INDEX ON Lev.LevPers (Lev);
ALTER TABLE Lev.LevPers ADD CONSTRAINT FK4843 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
CREATE INDEX ON Lev.LevPers (Pers);
ALTER TABLE Kern.His_Doc ADD CONSTRAINT FK4043 FOREIGN KEY (Doc) REFERENCES Kern.Doc (ID);
CREATE INDEX ON Kern.His_Doc (Doc);
ALTER TABLE Kern.His_Doc ADD CONSTRAINT FK4046 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_Doc (ActieInh);
ALTER TABLE Kern.His_Doc ADD CONSTRAINT FK4047 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_Doc (ActieVerval);
ALTER TABLE Kern.His_Doc ADD CONSTRAINT FK4051 FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.His_ErkenningOngeborenVrucht ADD CONSTRAINT FK9418 FOREIGN KEY (Relatie) REFERENCES Kern.Relatie (ID);
CREATE INDEX ON Kern.His_ErkenningOngeborenVrucht (Relatie);
ALTER TABLE Kern.His_ErkenningOngeborenVrucht ADD CONSTRAINT FK9421 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_ErkenningOngeborenVrucht (ActieInh);
ALTER TABLE Kern.His_ErkenningOngeborenVrucht ADD CONSTRAINT FK9422 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_ErkenningOngeborenVrucht (ActieVerval);
ALTER TABLE Kern.His_HuwelijkGeregistreerdPar ADD CONSTRAINT FK4369 FOREIGN KEY (Relatie) REFERENCES Kern.Relatie (ID);
CREATE INDEX ON Kern.His_HuwelijkGeregistreerdPar (Relatie);
ALTER TABLE Kern.His_HuwelijkGeregistreerdPar ADD CONSTRAINT FK4372 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_HuwelijkGeregistreerdPar (ActieInh);
ALTER TABLE Kern.His_HuwelijkGeregistreerdPar ADD CONSTRAINT FK4373 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_HuwelijkGeregistreerdPar (ActieVerval);
ALTER TABLE Kern.His_HuwelijkGeregistreerdPar ADD CONSTRAINT FK4375 FOREIGN KEY (GemAanv) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.His_HuwelijkGeregistreerdPar ADD CONSTRAINT FK4376 FOREIGN KEY (WplAanv) REFERENCES Kern.Plaats (ID);
ALTER TABLE Kern.His_HuwelijkGeregistreerdPar ADD CONSTRAINT FK4379 FOREIGN KEY (LandAanv) REFERENCES Kern.Land (ID);
ALTER TABLE Kern.His_HuwelijkGeregistreerdPar ADD CONSTRAINT FK4381 FOREIGN KEY (RdnEinde) REFERENCES Kern.RdnBeeindRelatie (ID);
ALTER TABLE Kern.His_HuwelijkGeregistreerdPar ADD CONSTRAINT FK4383 FOREIGN KEY (GemEinde) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.His_HuwelijkGeregistreerdPar ADD CONSTRAINT FK4384 FOREIGN KEY (WplEinde) REFERENCES Kern.Plaats (ID);
ALTER TABLE Kern.His_HuwelijkGeregistreerdPar ADD CONSTRAINT FK4387 FOREIGN KEY (LandEinde) REFERENCES Kern.Land (ID);
ALTER TABLE Kern.His_MultiRealiteitRegel ADD CONSTRAINT FK5472 FOREIGN KEY (MultiRealiteitRegel) REFERENCES Kern.MultiRealiteitRegel (ID);
CREATE INDEX ON Kern.His_MultiRealiteitRegel (MultiRealiteitRegel);
ALTER TABLE Kern.His_MultiRealiteitRegel ADD CONSTRAINT FK5475 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_MultiRealiteitRegel (ActieInh);
ALTER TABLE Kern.His_MultiRealiteitRegel ADD CONSTRAINT FK5476 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_MultiRealiteitRegel (ActieVerval);
ALTER TABLE Kern.His_NaamskeuzeOngeborenVruch ADD CONSTRAINT FK9404 FOREIGN KEY (Relatie) REFERENCES Kern.Relatie (ID);
CREATE INDEX ON Kern.His_NaamskeuzeOngeborenVruch (Relatie);
ALTER TABLE Kern.His_NaamskeuzeOngeborenVruch ADD CONSTRAINT FK9407 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_NaamskeuzeOngeborenVruch (ActieInh);
ALTER TABLE Kern.His_NaamskeuzeOngeborenVruch ADD CONSTRAINT FK9408 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_NaamskeuzeOngeborenVruch (ActieVerval);
ALTER TABLE Kern.His_Onderzoek ADD CONSTRAINT FK4068 FOREIGN KEY (Onderzoek) REFERENCES Kern.Onderzoek (ID);
CREATE INDEX ON Kern.His_Onderzoek (Onderzoek);
ALTER TABLE Kern.His_Onderzoek ADD CONSTRAINT FK4071 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_Onderzoek (ActieInh);
ALTER TABLE Kern.His_Onderzoek ADD CONSTRAINT FK4072 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_Onderzoek (ActieVerval);
ALTER TABLE Kern.His_OuderOuderlijkGezag ADD CONSTRAINT FK4033 FOREIGN KEY (Betr) REFERENCES Kern.Betr (ID);
CREATE INDEX ON Kern.His_OuderOuderlijkGezag (Betr);
ALTER TABLE Kern.His_OuderOuderlijkGezag ADD CONSTRAINT FK4038 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_OuderOuderlijkGezag (ActieInh);
ALTER TABLE Kern.His_OuderOuderlijkGezag ADD CONSTRAINT FK4039 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_OuderOuderlijkGezag (ActieVerval);
ALTER TABLE Kern.His_OuderOuderlijkGezag ADD CONSTRAINT FK4040 FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_OuderOuderlijkGezag (ActieAanpGel);
ALTER TABLE Kern.His_OuderOuderschap ADD CONSTRAINT FK4025 FOREIGN KEY (Betr) REFERENCES Kern.Betr (ID);
CREATE INDEX ON Kern.His_OuderOuderschap (Betr);
ALTER TABLE Kern.His_OuderOuderschap ADD CONSTRAINT FK4028 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_OuderOuderschap (ActieInh);
ALTER TABLE Kern.His_OuderOuderschap ADD CONSTRAINT FK4029 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_OuderOuderschap (ActieVerval);
ALTER TABLE Kern.His_OuderOuderschap ADD CONSTRAINT FK6091 FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_OuderOuderschap (ActieAanpGel);
ALTER TABLE Kern.His_PersAanschr ADD CONSTRAINT FK4115 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
CREATE INDEX ON Kern.His_PersAanschr (Pers);
ALTER TABLE Kern.His_PersAanschr ADD CONSTRAINT FK4120 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersAanschr (ActieInh);
ALTER TABLE Kern.His_PersAanschr ADD CONSTRAINT FK4121 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersAanschr (ActieVerval);
ALTER TABLE Kern.His_PersAanschr ADD CONSTRAINT FK4123 FOREIGN KEY (Naamgebruik) REFERENCES Kern.WijzeGebruikGeslnaam (ID);
ALTER TABLE Kern.His_PersAanschr ADD CONSTRAINT FK4126 FOREIGN KEY (PredikaatAanschr) REFERENCES Kern.Predikaat (ID);
ALTER TABLE Kern.His_PersAanschr ADD CONSTRAINT FK6114 FOREIGN KEY (AdellijkeTitelAanschr) REFERENCES Kern.AdellijkeTitel (ID);
ALTER TABLE Kern.His_PersBijhaard ADD CONSTRAINT FK4188 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
CREATE INDEX ON Kern.His_PersBijhaard (Pers);
ALTER TABLE Kern.His_PersBijhaard ADD CONSTRAINT FK4193 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersBijhaard (ActieInh);
ALTER TABLE Kern.His_PersBijhaard ADD CONSTRAINT FK4194 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersBijhaard (ActieVerval);
ALTER TABLE Kern.His_PersBijhaard ADD CONSTRAINT FK4195 FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersBijhaard (ActieAanpGel);
ALTER TABLE Kern.His_PersBijhaard ADD CONSTRAINT FK4196 FOREIGN KEY (Bijhaard) REFERENCES Kern.Bijhaard (ID);
ALTER TABLE Kern.His_PersBijhgem ADD CONSTRAINT FK4205 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
CREATE INDEX ON Kern.His_PersBijhgem (Pers);
ALTER TABLE Kern.His_PersBijhgem ADD CONSTRAINT FK4210 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersBijhgem (ActieInh);
ALTER TABLE Kern.His_PersBijhgem ADD CONSTRAINT FK4211 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersBijhgem (ActieVerval);
ALTER TABLE Kern.His_PersBijhgem ADD CONSTRAINT FK4212 FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersBijhgem (ActieAanpGel);
ALTER TABLE Kern.His_PersBijhgem ADD CONSTRAINT FK4213 FOREIGN KEY (Bijhgem) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.His_PersBVP ADD CONSTRAINT FK9357 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
CREATE INDEX ON Kern.His_PersBVP (Pers);
ALTER TABLE Kern.His_PersBVP ADD CONSTRAINT FK9360 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersBVP (ActieInh);
ALTER TABLE Kern.His_PersBVP ADD CONSTRAINT FK9361 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersBVP (ActieVerval);
ALTER TABLE Kern.His_PersBVP ADD CONSTRAINT FK9362 FOREIGN KEY (BVP) REFERENCES Kern.BVP (ID);
ALTER TABLE Kern.His_PersEUVerkiezingen ADD CONSTRAINT FK4179 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
CREATE INDEX ON Kern.His_PersEUVerkiezingen (Pers);
ALTER TABLE Kern.His_PersEUVerkiezingen ADD CONSTRAINT FK4182 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersEUVerkiezingen (ActieInh);
ALTER TABLE Kern.His_PersEUVerkiezingen ADD CONSTRAINT FK4183 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersEUVerkiezingen (ActieVerval);
ALTER TABLE Kern.His_PersGeboorte ADD CONSTRAINT FK4132 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
CREATE INDEX ON Kern.His_PersGeboorte (Pers);
ALTER TABLE Kern.His_PersGeboorte ADD CONSTRAINT FK4135 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersGeboorte (ActieInh);
ALTER TABLE Kern.His_PersGeboorte ADD CONSTRAINT FK4136 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersGeboorte (ActieVerval);
ALTER TABLE Kern.His_PersGeboorte ADD CONSTRAINT FK4138 FOREIGN KEY (GemGeboorte) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.His_PersGeboorte ADD CONSTRAINT FK4139 FOREIGN KEY (WplGeboorte) REFERENCES Kern.Plaats (ID);
ALTER TABLE Kern.His_PersGeboorte ADD CONSTRAINT FK4142 FOREIGN KEY (LandGeboorte) REFERENCES Kern.Land (ID);
ALTER TABLE Kern.His_PersGeslachtsaand ADD CONSTRAINT FK4088 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
CREATE INDEX ON Kern.His_PersGeslachtsaand (Pers);
ALTER TABLE Kern.His_PersGeslachtsaand ADD CONSTRAINT FK4093 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersGeslachtsaand (ActieInh);
ALTER TABLE Kern.His_PersGeslachtsaand ADD CONSTRAINT FK4094 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersGeslachtsaand (ActieVerval);
ALTER TABLE Kern.His_PersGeslachtsaand ADD CONSTRAINT FK4095 FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersGeslachtsaand (ActieAanpGel);
ALTER TABLE Kern.His_PersGeslachtsaand ADD CONSTRAINT FK4096 FOREIGN KEY (Geslachtsaand) REFERENCES Kern.Geslachtsaand (ID);
ALTER TABLE Kern.His_PersIDs ADD CONSTRAINT FK4077 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
CREATE INDEX ON Kern.His_PersIDs (Pers);
ALTER TABLE Kern.His_PersIDs ADD CONSTRAINT FK4082 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersIDs (ActieInh);
ALTER TABLE Kern.His_PersIDs ADD CONSTRAINT FK4083 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersIDs (ActieVerval);
ALTER TABLE Kern.His_PersIDs ADD CONSTRAINT FK4084 FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersIDs (ActieAanpGel);
ALTER TABLE Kern.His_PersImmigratie ADD CONSTRAINT FK4225 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
CREATE INDEX ON Kern.His_PersImmigratie (Pers);
ALTER TABLE Kern.His_PersImmigratie ADD CONSTRAINT FK4230 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersImmigratie (ActieInh);
ALTER TABLE Kern.His_PersImmigratie ADD CONSTRAINT FK4231 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersImmigratie (ActieVerval);
ALTER TABLE Kern.His_PersImmigratie ADD CONSTRAINT FK4232 FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersImmigratie (ActieAanpGel);
ALTER TABLE Kern.His_PersImmigratie ADD CONSTRAINT FK4233 FOREIGN KEY (LandVanwaarGevestigd) REFERENCES Kern.Land (ID);
ALTER TABLE Kern.His_PersInschr ADD CONSTRAINT FK4236 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
CREATE INDEX ON Kern.His_PersInschr (Pers);
ALTER TABLE Kern.His_PersInschr ADD CONSTRAINT FK4241 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersInschr (ActieInh);
ALTER TABLE Kern.His_PersInschr ADD CONSTRAINT FK4242 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersInschr (ActieVerval);
ALTER TABLE Kern.His_PersInschr ADD CONSTRAINT FK4246 FOREIGN KEY (VorigePers) REFERENCES Kern.Pers (ID);
CREATE INDEX ON Kern.His_PersInschr (VorigePers);
ALTER TABLE Kern.His_PersInschr ADD CONSTRAINT FK4247 FOREIGN KEY (VolgendePers) REFERENCES Kern.Pers (ID);
CREATE INDEX ON Kern.His_PersInschr (VolgendePers);
ALTER TABLE Kern.His_PersOpschorting ADD CONSTRAINT FK4198 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
CREATE INDEX ON Kern.His_PersOpschorting (Pers);
ALTER TABLE Kern.His_PersOpschorting ADD CONSTRAINT FK4201 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersOpschorting (ActieInh);
ALTER TABLE Kern.His_PersOpschorting ADD CONSTRAINT FK4202 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersOpschorting (ActieVerval);
ALTER TABLE Kern.His_PersOpschorting ADD CONSTRAINT FK5006 FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersOpschorting (ActieAanpGel);
ALTER TABLE Kern.His_PersOpschorting ADD CONSTRAINT FK4203 FOREIGN KEY (RdnOpschortingBijhouding) REFERENCES Kern.RdnOpschorting (ID);
ALTER TABLE Kern.His_PersOverlijden ADD CONSTRAINT FK4145 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
CREATE INDEX ON Kern.His_PersOverlijden (Pers);
ALTER TABLE Kern.His_PersOverlijden ADD CONSTRAINT FK4148 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersOverlijden (ActieInh);
ALTER TABLE Kern.His_PersOverlijden ADD CONSTRAINT FK4149 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersOverlijden (ActieVerval);
ALTER TABLE Kern.His_PersOverlijden ADD CONSTRAINT FK4151 FOREIGN KEY (GemOverlijden) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.His_PersOverlijden ADD CONSTRAINT FK4152 FOREIGN KEY (WplOverlijden) REFERENCES Kern.Plaats (ID);
ALTER TABLE Kern.His_PersOverlijden ADD CONSTRAINT FK4155 FOREIGN KEY (LandOverlijden) REFERENCES Kern.Land (ID);
ALTER TABLE Kern.His_PersPK ADD CONSTRAINT FK4217 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
CREATE INDEX ON Kern.His_PersPK (Pers);
ALTER TABLE Kern.His_PersPK ADD CONSTRAINT FK4220 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersPK (ActieInh);
ALTER TABLE Kern.His_PersPK ADD CONSTRAINT FK4221 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersPK (ActieVerval);
ALTER TABLE Kern.His_PersPK ADD CONSTRAINT FK4222 FOREIGN KEY (GemPK) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.His_PersSamengesteldeNaam ADD CONSTRAINT FK4098 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
CREATE INDEX ON Kern.His_PersSamengesteldeNaam (Pers);
ALTER TABLE Kern.His_PersSamengesteldeNaam ADD CONSTRAINT FK4103 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersSamengesteldeNaam (ActieInh);
ALTER TABLE Kern.His_PersSamengesteldeNaam ADD CONSTRAINT FK4104 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersSamengesteldeNaam (ActieVerval);
ALTER TABLE Kern.His_PersSamengesteldeNaam ADD CONSTRAINT FK4105 FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersSamengesteldeNaam (ActieAanpGel);
ALTER TABLE Kern.His_PersSamengesteldeNaam ADD CONSTRAINT FK4106 FOREIGN KEY (Predikaat) REFERENCES Kern.Predikaat (ID);
ALTER TABLE Kern.His_PersSamengesteldeNaam ADD CONSTRAINT FK4110 FOREIGN KEY (AdellijkeTitel) REFERENCES Kern.AdellijkeTitel (ID);
ALTER TABLE Kern.His_PersUitslNLKiesr ADD CONSTRAINT FK4171 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
CREATE INDEX ON Kern.His_PersUitslNLKiesr (Pers);
ALTER TABLE Kern.His_PersUitslNLKiesr ADD CONSTRAINT FK4174 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersUitslNLKiesr (ActieInh);
ALTER TABLE Kern.His_PersUitslNLKiesr ADD CONSTRAINT FK4175 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersUitslNLKiesr (ActieVerval);
ALTER TABLE Kern.His_PersVerblijfstitel ADD CONSTRAINT FK4158 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
CREATE INDEX ON Kern.His_PersVerblijfstitel (Pers);
ALTER TABLE Kern.His_PersVerblijfstitel ADD CONSTRAINT FK4163 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersVerblijfstitel (ActieInh);
ALTER TABLE Kern.His_PersVerblijfstitel ADD CONSTRAINT FK4164 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersVerblijfstitel (ActieVerval);
ALTER TABLE Kern.His_PersVerblijfstitel ADD CONSTRAINT FK4165 FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersVerblijfstitel (ActieAanpGel);
ALTER TABLE Kern.His_PersVerblijfstitel ADD CONSTRAINT FK4166 FOREIGN KEY (Verblijfstitel) REFERENCES Kern.Verblijfstitel (ID);
ALTER TABLE Kern.His_PersAdres ADD CONSTRAINT FK6065 FOREIGN KEY (PersAdres) REFERENCES Kern.PersAdres (ID);
CREATE INDEX ON Kern.His_PersAdres (PersAdres);
ALTER TABLE Kern.His_PersAdres ADD CONSTRAINT FK6070 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersAdres (ActieInh);
ALTER TABLE Kern.His_PersAdres ADD CONSTRAINT FK6071 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersAdres (ActieVerval);
ALTER TABLE Kern.His_PersAdres ADD CONSTRAINT FK6072 FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersAdres (ActieAanpGel);
ALTER TABLE Kern.His_PersAdres ADD CONSTRAINT FK4257 FOREIGN KEY (Srt) REFERENCES Kern.FunctieAdres (ID);
ALTER TABLE Kern.His_PersAdres ADD CONSTRAINT FK4258 FOREIGN KEY (RdnWijz) REFERENCES Kern.RdnWijzAdres (ID);
ALTER TABLE Kern.His_PersAdres ADD CONSTRAINT FK4259 FOREIGN KEY (AangAdresh) REFERENCES Kern.AangAdresh (ID);
ALTER TABLE Kern.His_PersAdres ADD CONSTRAINT FK4272 FOREIGN KEY (Gem) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.His_PersAdres ADD CONSTRAINT FK4280 FOREIGN KEY (Wpl) REFERENCES Kern.Plaats (ID);
ALTER TABLE Kern.His_PersAdres ADD CONSTRAINT FK4298 FOREIGN KEY (Land) REFERENCES Kern.Land (ID);
ALTER TABLE Kern.His_PersGeslnaamcomp ADD CONSTRAINT FK4301 FOREIGN KEY (PersGeslnaamcomp) REFERENCES Kern.PersGeslnaamcomp (ID);
CREATE INDEX ON Kern.His_PersGeslnaamcomp (PersGeslnaamcomp);
ALTER TABLE Kern.His_PersGeslnaamcomp ADD CONSTRAINT FK4306 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersGeslnaamcomp (ActieInh);
ALTER TABLE Kern.His_PersGeslnaamcomp ADD CONSTRAINT FK4307 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersGeslnaamcomp (ActieVerval);
ALTER TABLE Kern.His_PersGeslnaamcomp ADD CONSTRAINT FK4308 FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersGeslnaamcomp (ActieAanpGel);
ALTER TABLE Kern.His_PersGeslnaamcomp ADD CONSTRAINT FK4312 FOREIGN KEY (Predikaat) REFERENCES Kern.Predikaat (ID);
ALTER TABLE Kern.His_PersGeslnaamcomp ADD CONSTRAINT FK4313 FOREIGN KEY (AdellijkeTitel) REFERENCES Kern.AdellijkeTitel (ID);
ALTER TABLE Kern.His_PersIndicatie ADD CONSTRAINT FK4315 FOREIGN KEY (PersIndicatie) REFERENCES Kern.PersIndicatie (ID);
CREATE INDEX ON Kern.His_PersIndicatie (PersIndicatie);
ALTER TABLE Kern.His_PersIndicatie ADD CONSTRAINT FK4320 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersIndicatie (ActieInh);
ALTER TABLE Kern.His_PersIndicatie ADD CONSTRAINT FK4321 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersIndicatie (ActieVerval);
ALTER TABLE Kern.His_PersIndicatie ADD CONSTRAINT FK4322 FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersIndicatie (ActieAanpGel);
ALTER TABLE Kern.His_PersNation ADD CONSTRAINT FK4325 FOREIGN KEY (PersNation) REFERENCES Kern.PersNation (ID);
CREATE INDEX ON Kern.His_PersNation (PersNation);
ALTER TABLE Kern.His_PersNation ADD CONSTRAINT FK4330 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersNation (ActieInh);
ALTER TABLE Kern.His_PersNation ADD CONSTRAINT FK4331 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersNation (ActieVerval);
ALTER TABLE Kern.His_PersNation ADD CONSTRAINT FK4332 FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersNation (ActieAanpGel);
ALTER TABLE Kern.His_PersNation ADD CONSTRAINT FK4334 FOREIGN KEY (RdnVerk) REFERENCES Kern.RdnVerkNLNation (ID);
ALTER TABLE Kern.His_PersNation ADD CONSTRAINT FK4333 FOREIGN KEY (RdnVerlies) REFERENCES Kern.RdnVerliesNLNation (ID);
ALTER TABLE Kern.His_PersReisdoc ADD CONSTRAINT FK4336 FOREIGN KEY (PersReisdoc) REFERENCES Kern.PersReisdoc (ID);
CREATE INDEX ON Kern.His_PersReisdoc (PersReisdoc);
ALTER TABLE Kern.His_PersReisdoc ADD CONSTRAINT FK4341 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersReisdoc (ActieInh);
ALTER TABLE Kern.His_PersReisdoc ADD CONSTRAINT FK4342 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersReisdoc (ActieVerval);
ALTER TABLE Kern.His_PersReisdoc ADD CONSTRAINT FK4346 FOREIGN KEY (AutVanAfgifte) REFERENCES Kern.AutVanAfgifteReisdoc (ID);
ALTER TABLE Kern.His_PersReisdoc ADD CONSTRAINT FK4349 FOREIGN KEY (RdnVervallen) REFERENCES Kern.RdnVervallenReisdoc (ID);
ALTER TABLE Kern.His_PersVerificatie ADD CONSTRAINT FK4352 FOREIGN KEY (PersVerificatie) REFERENCES Kern.PersVerificatie (ID);
CREATE INDEX ON Kern.His_PersVerificatie (PersVerificatie);
ALTER TABLE Kern.His_PersVerificatie ADD CONSTRAINT FK4355 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersVerificatie (ActieInh);
ALTER TABLE Kern.His_PersVerificatie ADD CONSTRAINT FK4356 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersVerificatie (ActieVerval);
ALTER TABLE Kern.His_PersVoornaam ADD CONSTRAINT FK4359 FOREIGN KEY (PersVoornaam) REFERENCES Kern.PersVoornaam (ID);
CREATE INDEX ON Kern.His_PersVoornaam (PersVoornaam);
ALTER TABLE Kern.His_PersVoornaam ADD CONSTRAINT FK4364 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersVoornaam (ActieInh);
ALTER TABLE Kern.His_PersVoornaam ADD CONSTRAINT FK4365 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersVoornaam (ActieVerval);
ALTER TABLE Kern.His_PersVoornaam ADD CONSTRAINT FK4366 FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_PersVoornaam (ActieAanpGel);
ALTER TABLE Kern.His_VerstrDerde ADD CONSTRAINT FK9364 FOREIGN KEY (VerstrDerde) REFERENCES Kern.VerstrDerde (ID);
CREATE INDEX ON Kern.His_VerstrDerde (VerstrDerde);
ALTER TABLE Kern.His_VerstrDerde ADD CONSTRAINT FK9367 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_VerstrDerde (ActieInh);
ALTER TABLE Kern.His_VerstrDerde ADD CONSTRAINT FK9368 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
CREATE INDEX ON Kern.His_VerstrDerde (ActieVerval);

-- Handmatige toevoeging ------------------------------------------
CREATE TABLE Kern.PersVol (
   ID                            integer                       NOT NULL,
   Gegevens                      bytea                         NOT NULL,
   Checksum                      Varchar(40)                   NOT NULL,
   CONSTRAINT PK0001 PRIMARY KEY (ID),
   CONSTRAINT FK0001 FOREIGN KEY (ID) REFERENCES Kern.Pers (ID) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);


