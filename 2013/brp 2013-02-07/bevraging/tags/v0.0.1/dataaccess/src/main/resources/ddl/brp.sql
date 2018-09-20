--------------------------------------------------------------------------------
-- PostgreSQL SQL-DDL bestand voor Basisregistratie Personen (BRP)
--------------------------------------------------------------------------------
-- 
-- Gegenereerd door BRP Meta Register.
-- Gegenereerd op: Wednesday 12 Oct 2011 11:19:25
-- 
--------------------------------------------------------------------------------


-- Schemas ---------------------------------------------------------------------
drop schema IF EXISTS AutAut CASCADE;
CREATE SCHEMA AutAut;
drop schema IF EXISTS Ber CASCADE;
CREATE SCHEMA Ber;
drop schema IF EXISTS Kern CASCADE;
CREATE SCHEMA Kern;
drop schema IF EXISTS Lev CASCADE;
CREATE SCHEMA Lev;


-- Actual tabellen--------------------------------------------------------------
CREATE SEQUENCE AutAut.seq_Authenticatiemiddel;
CREATE TABLE AutAut.Authenticatiemiddel (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_Authenticatiemiddel')  /* AuthenticatiemiddelID */,
   Partij                        Integer                       NOT NULL    /* PartijID */,
   Rol                           Integer                                   /* RolID */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   Functie                       Smallint                                  /* FunctieID */,
   CertificaatTbvSSL             Integer                                   /* CertificaatID */,
   CertificaatTbvOndertekening   Integer                                   /* CertificaatID */,
   IPAdres                       inet                                      /* IPAdres */,
   IndAuthenticatiemiddelMateri  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4497 PRIMARY KEY (ID)
);
ALTER SEQUENCE AutAut.seq_Authenticatiemiddel OWNED BY AutAut.Authenticatiemiddel.ID;

CREATE SEQUENCE AutAut.seq_Certificaat;
CREATE TABLE AutAut.Certificaat (
   ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_Certificaat')  /* CertificaatID */,
   Subject                       Varchar(1024)                 NOT NULL    /* Certificaatsubject */,
   Serial                        Smallint                      NOT NULL    /* Certificaatserial */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4727 PRIMARY KEY (ID),
   CONSTRAINT BR4728 UNIQUE (Subject, Serial)
);
ALTER SEQUENCE AutAut.seq_Certificaat OWNED BY AutAut.Certificaat.ID;

CREATE TABLE AutAut.Functie (
   ID                            Smallint                      NOT NULL    /* FunctieID */,
   Naam                          Varchar(40)                   NOT NULL    /* NaamEnumeratiewaarde */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4725 PRIMARY KEY (ID),
   CONSTRAINT BR4726 UNIQUE (Naam)
);

CREATE SEQUENCE Ber.seq_Ber;
CREATE TABLE Ber.Ber (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Ber.seq_Ber')  /* BerID */,
   Data                          Text                          NOT NULL    /* Berdata */,
   TsOntv                        Timestamp                     NOT NULL    /* Ts */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4827 PRIMARY KEY (ID)
);
ALTER SEQUENCE Ber.seq_Ber OWNED BY Ber.Ber.ID;

CREATE TABLE Kern.AangAdresh (
   ID                            Smallint                      NOT NULL    /* AangAdreshID */,
   Code                          Varchar(1)                    NOT NULL    /* AangAdreshCode */,
   Naam                          Varchar(40)                   NOT NULL    /* NaamEnumeratiewaarde */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4438 PRIMARY KEY (ID),
   CONSTRAINT BR4439 UNIQUE (Naam),
   CONSTRAINT BR4440 UNIQUE (Code)
);

CREATE TABLE Kern.AdellijkeTitel (
   ID                            Smallint                      NOT NULL    /* AdellijkeTitelID */,
   Code                          Varchar(1)                    NOT NULL    /* AdellijkeTitelCode */,
   NaamMannelijk                 Varchar(40)                   NOT NULL    /* NaamEnumeratiewaarde */,
   NaamVrouwelijk                Varchar(40)                   NOT NULL    /* NaamEnumeratiewaarde */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4410 PRIMARY KEY (ID),
   CONSTRAINT BR4411 UNIQUE (NaamMannelijk),
   CONSTRAINT BR4412 UNIQUE (Code),
   CONSTRAINT BR4413 UNIQUE (NaamVrouwelijk)
);

CREATE TABLE Kern.AutVanAfgifteReisdoc (
   ID                            Integer                       NOT NULL    /* AutVanAfgifteReisdocID */,
   Code                          Varchar(6)                    NOT NULL    /* AutVanAfgifteReisdocCode */,
   Oms                           Varchar(200)                  NOT NULL    /* OmsEnumeratiewaarde */,
   DatAanvGel                    Numeric(8,0)                              /* Dat */,
   DatEindeGel                   Numeric(8,0)                              /* Dat */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4466 PRIMARY KEY (ID),
   CONSTRAINT BR4467 UNIQUE (Code),
   CONSTRAINT BR4468 UNIQUE (Oms)
);

CREATE SEQUENCE Kern.seq_Actie;
CREATE TABLE Kern.Actie (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_Actie')  /* ActieID */,
   Srt                           Integer                       NOT NULL    /* SrtActieID */,
   Partij                        Integer                       NOT NULL    /* PartijID */,
   Verdrag                       Integer                                   /* VerdragID */,
   TsOntlening                   Timestamp                                 /* Ts */,
   TsReg                         Timestamp                     NOT NULL    /* Ts */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4403 PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_Actie OWNED BY Kern.Actie.ID;

CREATE SEQUENCE Kern.seq_Betr;
CREATE TABLE Kern.Betr (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_Betr')  /* BetrID */,
   Relatie                       Bigint                        NOT NULL    /* RelatieID */,
   Rol                           Smallint                      NOT NULL    /* SrtBetrID */,
   Betrokkene                    Bigint                                    /* PersID */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   DatAanv                       Numeric(8,0)                              /* Dat */,
   DatEinde                      Numeric(8,0)                              /* Dat */,
   IndBetrMaterieel              boolean                       NOT NULL    /* JaNee */,
   IndOuderHeeftGezag            boolean                                   /* JaNee */,
   IndOuderlijkGezagMaterieel    boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4501 PRIMARY KEY (ID),
   CONSTRAINT BR4479 UNIQUE (Relatie, Betrokkene)
);
ALTER SEQUENCE Kern.seq_Betr OWNED BY Kern.Betr.ID;

CREATE SEQUENCE Kern.seq_Bron;
CREATE TABLE Kern.Bron (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_Bron')  /* BronID */,
   Actie                         Bigint                        NOT NULL    /* ActieID */,
   Doc                           Bigint                        NOT NULL    /* DocID */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4504 PRIMARY KEY (ID),
   CONSTRAINT BR4481 UNIQUE (Actie, Doc)
);
ALTER SEQUENCE Kern.seq_Bron OWNED BY Kern.Bron.ID;

CREATE SEQUENCE Kern.seq_Doc;
CREATE TABLE Kern.Doc (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_Doc')  /* DocID */,
   Srt                           Integer                       NOT NULL    /* SrtDocID */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   Ident                         Varchar(20)                               /* DocIdent */,
   Aktenr                        Varchar(7)                                /* Aktenr */,
   Oms                           Varchar(80)                               /* DocOms */,
   Partij                        Integer                                   /* PartijID */,
   IndDocMaterieel               boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4416 PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_Doc OWNED BY Kern.Doc.ID;

CREATE TABLE Kern.Element (
   ID                            Integer                       NOT NULL    /* ElementID */,
   Srt                           Smallint                      NOT NULL    /* SrtElementID */,
   Naam                          Varchar(40)                   NOT NULL    /* NaamEnumeratiewaarde */,
   DatAanvGel                    Numeric(8,0)                              /* Dat */,
   DatEindeGel                   Numeric(8,0)                              /* Dat */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4422 PRIMARY KEY (ID),
   CONSTRAINT BR4423 UNIQUE (Srt, Naam)
);

CREATE TABLE Kern.FunctieAdres (
   ID                            Smallint                      NOT NULL    /* SrtAdresID */,
   Code                          Varchar(1)                    NOT NULL    /* SrtAdresCode */,
   Naam                          Varchar(40)                   NOT NULL    /* NaamEnumeratiewaarde */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4435 PRIMARY KEY (ID),
   CONSTRAINT BR4436 UNIQUE (Naam),
   CONSTRAINT BR4437 UNIQUE (Code)
);

CREATE SEQUENCE Kern.seq_GegevenInOnderzoek;
CREATE TABLE Kern.GegevenInOnderzoek (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_GegevenInOnderzoek')  /* GegevenInOnderzoekID */,
   Onderzoek                     Bigint                        NOT NULL    /* OnderzoekID */,
   SrtGegeven                    Integer                       NOT NULL    /* ElementID */,
   Ident                         Bigint                        NOT NULL    /* Sleutelwaarde */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4507 PRIMARY KEY (ID),
   CONSTRAINT BR4480 UNIQUE (Onderzoek, SrtGegeven)
);
ALTER SEQUENCE Kern.seq_GegevenInOnderzoek OWNED BY Kern.GegevenInOnderzoek.ID;

CREATE TABLE Kern.Geslachtsaand (
   ID                            Smallint                      NOT NULL    /* GeslachtsaandID */,
   Code                          Varchar(1)                    NOT NULL    /* GeslachtsaandCode */,
   Naam                          Varchar(40)                   NOT NULL    /* NaamEnumeratiewaarde */,
   Oms                           Varchar(200)                              /* OmsEnumeratiewaarde */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4482 PRIMARY KEY (ID),
   CONSTRAINT BR4483 UNIQUE (Code),
   CONSTRAINT BR4484 UNIQUE (Naam)
);

CREATE TABLE Kern.Land (
   ID                            Integer                       NOT NULL    /* LandID */,
   Naam                          Varchar(40)                   NOT NULL    /* NaamEnumeratiewaarde */,
   ISO31661Alpha2                Varchar(2)                                /* ISO31661Alpha2 */,
   DatAanvGel                    Numeric(8,0)                              /* Dat */,
   DatEindeGel                   Numeric(8,0)                              /* Dat */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4399 PRIMARY KEY (ID),
   CONSTRAINT BR4400 UNIQUE (Naam)
);

CREATE SEQUENCE Kern.seq_MultiRealiteitRegel;
CREATE TABLE Kern.MultiRealiteitRegel (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_MultiRealiteitRegel')  /* MultiRealiteitregelID */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   GeldigVoor                    Bigint                                    /* PersID */,
   Srt                           Smallint                                  /* SrtMultiRealiteitRegelID */,
   Pers                          Bigint                                    /* PersID */,
   MultiRealiteitPers            Bigint                                    /* PersID */,
   Relatie                       Bigint                                    /* RelatieID */,
   Betr                          Bigint                                    /* BetrID */,
   IndMultiRealiteitRegelMater   boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4488 PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_MultiRealiteitRegel OWNED BY Kern.MultiRealiteitRegel.ID;

CREATE TABLE Kern.Nation (
   ID                            Integer                       NOT NULL    /* NationID */,
   Naam                          Varchar(40)                   NOT NULL    /* NaamEnumeratiewaarde */,
   DatAanvGel                    Numeric(8,0)                              /* Dat */,
   DatEindeGel                   Numeric(8,0)                              /* Dat */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4404 PRIMARY KEY (ID),
   CONSTRAINT BR4405 UNIQUE (Naam)
);

CREATE SEQUENCE Kern.seq_Onderzoek;
CREATE TABLE Kern.Onderzoek (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_Onderzoek')  /* OnderzoekID */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   DatBegin                      Numeric(8,0)                              /* Dat */,
   DatEinde                      Numeric(8,0)                              /* Dat */,
   Oms                           Text                                      /* OnderzoekOms */,
   IndOnderzoekMaterieel         boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4421 PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_Onderzoek OWNED BY Kern.Onderzoek.ID;

CREATE SEQUENCE Kern.seq_Partij;
CREATE TABLE Kern.Partij (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_Partij')  /* PartijID */,
   Naam                          Varchar(40)                   NOT NULL    /* NaamEnumeratiewaarde */,
   Srt                           Integer                                   /* SrtPartijID */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   DatAanv                       Numeric(8,0)                              /* Dat */,
   DatEinde                      Numeric(8,0)                              /* Dat */,
   Sector                        Integer                                   /* SectorID */,
   IndPartijMaterieel            boolean                       NOT NULL    /* JaNee */,
   VoortzettendeGem              Integer                                   /* PartijID */,
   Gemcode                       Numeric(4,0)                              /* Gemcode */,
   OnderdeelVan                  Integer                                   /* PartijID */,
   IndGemMaterieel               boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4417 PRIMARY KEY (ID),
   CONSTRAINT BR4418 UNIQUE (Naam)
);
ALTER SEQUENCE Kern.seq_Partij OWNED BY Kern.Partij.ID;

CREATE SEQUENCE Kern.seq_PartijRol;
CREATE TABLE Kern.PartijRol (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_PartijRol')  /* PartijRolID */,
   Partij                        Integer                       NOT NULL    /* PartijID */,
   Rol                           Integer                       NOT NULL    /* RolID */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4495 PRIMARY KEY (ID),
   CONSTRAINT BR4688 UNIQUE (Partij, Rol)
);
ALTER SEQUENCE Kern.seq_PartijRol OWNED BY Kern.PartijRol.ID;

CREATE SEQUENCE Kern.seq_Pers;
CREATE TABLE Kern.Pers (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_Pers')  /* PersID */,
   Srt                           Smallint                      NOT NULL    /* SrtPersID */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   BSN                           Numeric(9,0)                              /* BSN */,
   ANr                           Numeric(10,0)                             /* ANr */,
   IndIDsMaterieel               boolean                       NOT NULL    /* JaNee */,
   Geslachtsaand                 Smallint                                  /* GeslachtsaandID */,
   IndGeslachtsaandMaterieel     boolean                       NOT NULL    /* JaNee */,
   Predikaat                     Smallint                                  /* PredikaatID */,
   Voornamen                     Varchar(200)                              /* Voornamen */,
   Voorvoegsel                   Varchar(10)                               /* Voorvoegsel */,
   Scheidingsteken               Varchar(1)                                /* Scheidingsteken */,
   AdellijkeTitel                Smallint                                  /* AdellijkeTitelID */,
   Geslnaam                      Varchar(200)                              /* Geslnaam */,
   IndNreeksAlsGeslnaam          boolean                                   /* JaNee */,
   IndAlgoritmischAfgeleid       boolean                                   /* JaNee */,
   IndSamengesteldeNaamMateriee  boolean                       NOT NULL    /* JaNee */,
   GebrGeslnaamEGP               Smallint                                  /* WijzeGebruikGeslnaamID */,
   IndAanschrMetAdellijkeTitel   boolean                                   /* JaNee */,
   IndAanschrAlgoritmischAfgele  boolean                                   /* JaNee */,
   PredikaatAanschr              Smallint                                  /* PredikaatID */,
   VoornamenAanschr              Varchar(200)                              /* Voornamen */,
   VoorvoegselAanschr            Varchar(10)                               /* Voorvoegsel */,
   ScheidingstekenAanschr        Varchar(1)                                /* Scheidingsteken */,
   GeslnaamAanschr               Varchar(200)                              /* Geslnaam */,
   IndAanschrMaterieel           boolean                       NOT NULL    /* JaNee */,
   DatGeboorte                   Numeric(8,0)                              /* Dat */,
   GemGeboorte                   Integer                                   /* PartijID */,
   WplGeboorte                   Integer                                   /* PlaatsID */,
   BLGeboorteplaats              Varchar(40)                               /* BLPlaats */,
   BLRegioGeboorte               Varchar(35)                               /* BLRegio */,
   LandGeboorte                  Integer                                   /* LandID */,
   OmsGeboorteloc                Varchar(40)                               /* LocOms */,
   IndGeboorteMaterieel          boolean                       NOT NULL    /* JaNee */,
   DatOverlijden                 Numeric(8,0)                              /* Dat */,
   GemOverlijden                 Integer                                   /* PartijID */,
   WplOverlijden                 Integer                                   /* PlaatsID */,
   BLPlaatsOverlijden            Varchar(40)                               /* BLPlaats */,
   BLRegioOverlijden             Varchar(35)                               /* BLRegio */,
   LandOverlijden                Integer                                   /* LandID */,
   OmsLocOverlijden              Varchar(40)                               /* LocOms */,
   IndOverlijdenMaterieel        boolean                       NOT NULL    /* JaNee */,
   Verblijfsr                    Integer                                   /* VerblijfsrID */,
   DatAanvVerblijfsr             Numeric(8,0)                              /* Dat */,
   DatVoorzEindeVerblijfsr       Numeric(8,0)                              /* Dat */,
   DatAanvAaneenslVerblijfsr     Numeric(8,0)                              /* Dat */,
   IndVerblijfsrMaterieel        boolean                       NOT NULL    /* JaNee */,
   IndUitslNLKiesr               boolean                                   /* JaNee */,
   DatEindeUitslNLKiesr          Numeric(8,0)                              /* Dat */,
   IndUitslNLKiesrMaterieel      boolean                       NOT NULL    /* JaNee */,
   IndDeelnEUVerkiezingen        boolean                                   /* JaNee */,
   DatAanlAanpDeelnEUVerkiezi    Numeric(8,0)                              /* Dat */,
   DatEindeUitslEUKiesr          Numeric(8,0)                              /* Dat */,
   IndEUVerkiezingenMaterieel    boolean                       NOT NULL    /* JaNee */,
   Verantwoordelijke             Smallint                                  /* VerantwoordelijkeID */,
   IndBijhverantwoordelijkheidM  boolean                       NOT NULL    /* JaNee */,
   RdnOpschortingBijhouding      Smallint                                  /* RdnOpschortingID */,
   IndOpschortingMaterieel       boolean                       NOT NULL    /* JaNee */,
   Bijhgem                       Integer                                   /* PartijID */,
   DatInschrInGem                Numeric(8,0)                              /* Dat */,
   IndOnverwDocAanw              boolean                                   /* JaNee */,
   IndBijhgemMaterieel           boolean                       NOT NULL    /* JaNee */,
   GemPK                         Integer                                   /* PartijID */,
   IndPKVolledigGeconv           boolean                                   /* JaNee */,
   IndPKMaterieel                boolean                       NOT NULL    /* JaNee */,
   LandVanwaarGevestigd          Integer                                   /* LandID */,
   DatVestigingInNederland       Numeric(8,0)                              /* Dat */,
   IndImmigratieMaterieel        boolean                       NOT NULL    /* JaNee */,
   DatInschr                     Numeric(8,0)                              /* Dat */,
   Versienr                      Bigint                                    /* Versienr */,
   VorigePers                    Bigint                                    /* PersID */,
   VolgendePers                  Bigint                                    /* PersID */,
   IndInschrMaterieel            boolean                       NOT NULL    /* JaNee */,
   TijdstipLaatsteWijz           Timestamp                                 /* Ts */,
   IndGegevensInOnderzoek        boolean                                   /* JaNee */,
   IndAfgeleidAdministratiefMat  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4390 PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_Pers OWNED BY Kern.Pers.ID;

CREATE SEQUENCE Kern.seq_PersAdres;
CREATE TABLE Kern.PersAdres (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_PersAdres')  /* PersAdresID */,
   Pers                          Bigint                        NOT NULL    /* PersID */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   Srt                           Smallint                                  /* SrtAdresID */,
   RdnWijz                       Smallint                                  /* RdnWijzAdresID */,
   AangAdresh                    Smallint                                  /* AangAdreshID */,
   DatAanvAdresh                 Numeric(8,0)                              /* Dat */,
   IndAdreshMaterieel            boolean                       NOT NULL    /* JaNee */,
   AdresseerbaarObject           Varchar(16)                               /* AandAdresseerbaarObject */,
   IdentcodeNraand               Varchar(16)                               /* IdentcodeNraand */,
   Gem                           Integer                                   /* PartijID */,
   NOR                           Varchar(80)                               /* NOR */,
   AfgekorteNOR                  Varchar(24)                               /* AfgekorteNOR */,
   Gemdeel                       Varchar(24)                               /* Gemdeel */,
   Huisnr                        Numeric(5,0)                              /* Huisnr */,
   Huisletter                    Varchar(1)                                /* Huisletter */,
   Huisnrtoevoeging              Varchar(4)                                /* Huisnrtoevoeging */,
   Postcode                      Varchar(6)                                /* Postcode */,
   Wpl                           Integer                                   /* PlaatsID */,
   LoctovAdres                   Varchar(2)                                /* AandBijHuisnr */,
   LocOms                        Varchar(40)                               /* LocOms */,
   IndNLAdresMaterieel           boolean                       NOT NULL    /* JaNee */,
   BLAdresRegel1                 Varchar(40)                               /* Adresregel */,
   BLAdresRegel2                 Varchar(40)                               /* Adresregel */,
   BLAdresRegel3                 Varchar(40)                               /* Adresregel */,
   BLAdresRegel4                 Varchar(40)                               /* Adresregel */,
   BLAdresRegel5                 Varchar(40)                               /* Adresregel */,
   BLAdresRegel6                 Varchar(40)                               /* Adresregel */,
   Land                          Integer                                   /* LandID */,
   DatVertrekUitNederland        Numeric(8,0)                              /* Dat */,
   IndBLAdresMaterieel           boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4434 PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_PersAdres OWNED BY Kern.PersAdres.ID;

CREATE SEQUENCE Kern.seq_PersGeslnaamcomp;
CREATE TABLE Kern.PersGeslnaamcomp (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_PersGeslnaamcomp')  /* PersGeslnaamID */,
   Pers                          Bigint                        NOT NULL    /* PersID */,
   Volgnr                        Integer                       NOT NULL    /* Volgnr */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   Voorvoegsel                   Varchar(10)                               /* Voorvoegsel */,
   Scheidingsteken               Varchar(1)                                /* Scheidingsteken */,
   Naam                          Varchar(200)                              /* Geslnaamcomp */,
   Predikaat                     Smallint                                  /* PredikaatID */,
   AdellijkeTitel                Smallint                                  /* AdellijkeTitelID */,
   IndPersGeslnaamcompMaterieel  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4391 PRIMARY KEY (ID),
   CONSTRAINT BR4392 UNIQUE (Pers, Volgnr)
);
ALTER SEQUENCE Kern.seq_PersGeslnaamcomp OWNED BY Kern.PersGeslnaamcomp.ID;

CREATE SEQUENCE Kern.seq_PersIndicatie;
CREATE TABLE Kern.PersIndicatie (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_PersIndicatie')  /* PersIndicatieID */,
   Pers                          Bigint                        NOT NULL    /* PersID */,
   Srt                           Smallint                      NOT NULL    /* SrtIndicatieID */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   Waarde                        boolean                                   /* JaNee */,
   IndPersIndicatieMaterieel     boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4455 PRIMARY KEY (ID),
   CONSTRAINT BR4456 UNIQUE (Pers, Srt)
);
ALTER SEQUENCE Kern.seq_PersIndicatie OWNED BY Kern.PersIndicatie.ID;

CREATE SEQUENCE Kern.seq_PersNation;
CREATE TABLE Kern.PersNation (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_PersNation')  /* PersNationID */,
   Pers                          Bigint                        NOT NULL    /* PersID */,
   Nation                        Integer                       NOT NULL    /* NationID */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   RdnVerlies                    Integer                                   /* RdnVerliesNLNationID */,
   RdnVerk                       Integer                                   /* RdnVerkNLNationID */,
   IndPersNationMaterieel        boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4414 PRIMARY KEY (ID),
   CONSTRAINT BR4415 UNIQUE (Pers, Nation)
);
ALTER SEQUENCE Kern.seq_PersNation OWNED BY Kern.PersNation.ID;

CREATE SEQUENCE Kern.seq_PersReisdoc;
CREATE TABLE Kern.PersReisdoc (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_PersReisdoc')  /* ReisdocID */,
   Pers                          Bigint                        NOT NULL    /* PersID */,
   Srt                           Integer                       NOT NULL    /* SrtNLReisdocID */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   Nr                            Varchar(9)                                /* ReisdocNr */,
   DatUitgifte                   Numeric(8,0)                              /* Dat */,
   AutVanAfgifte                 Integer                                   /* AutVanAfgifteReisdocID */,
   DatVoorzeEindeGel             Numeric(8,0)                              /* Dat */,
   DatInhingVermissing           Numeric(8,0)                              /* Dat */,
   RdnVervallen                  Smallint                                  /* RdnOntbrReisdocID */,
   LengteHouder                  Numeric(3,0)                              /* LengteInCm */,
   IndPersReisdocMaterieel       boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4446 PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_PersReisdoc OWNED BY Kern.PersReisdoc.ID;

CREATE SEQUENCE Kern.seq_PersVerificatie;
CREATE TABLE Kern.PersVerificatie (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_PersVerificatie')  /* VerificatieID */,
   Geverifieerde                 Bigint                        NOT NULL    /* PersID */,
   Srt                           Integer                                   /* SrtVerificatieID */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   Dat                           Numeric(8,0)                              /* Dat */,
   IndPersVerificatieMaterieel   boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4459 PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_PersVerificatie OWNED BY Kern.PersVerificatie.ID;

CREATE SEQUENCE Kern.seq_PersVoornaam;
CREATE TABLE Kern.PersVoornaam (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_PersVoornaam')  /* PersVoornaamID */,
   Pers                          Bigint                        NOT NULL    /* PersID */,
   Volgnr                        Integer                       NOT NULL    /* Volgnr */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   Naam                          Varchar(40)                               /* Voornaam */,
   IndPersVoornaamMaterieel      boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4393 PRIMARY KEY (ID),
   CONSTRAINT BR4394 UNIQUE (Pers, Volgnr)
);
ALTER SEQUENCE Kern.seq_PersVoornaam OWNED BY Kern.PersVoornaam.ID;

CREATE TABLE Kern.Plaats (
   ID                            Integer                       NOT NULL    /* PlaatsID */,
   Naam                          Varchar(40)                   NOT NULL    /* NaamEnumeratiewaarde */,
   DatAanvGel                    Numeric(8,0)                              /* Dat */,
   DatEindeGel                   Numeric(8,0)                              /* Dat */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4397 PRIMARY KEY (ID),
   CONSTRAINT BR4398 UNIQUE (Naam)
);

CREATE TABLE Kern.Predikaat (
   ID                            Smallint                      NOT NULL    /* PredikaatID */,
   Code                          Varchar(1)                    NOT NULL    /* PredikaatCode */,
   NaamMannelijk                 Varchar(40)                   NOT NULL    /* NaamEnumeratiewaarde */,
   NaamVrouwelijk                Varchar(40)                   NOT NULL    /* NaamEnumeratiewaarde */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4406 PRIMARY KEY (ID),
   CONSTRAINT BR4407 UNIQUE (Code),
   CONSTRAINT BR4408 UNIQUE (NaamMannelijk),
   CONSTRAINT BR4409 UNIQUE (NaamVrouwelijk)
);

CREATE TABLE Kern.RdnBeeindRelatie (
   ID                            Smallint                      NOT NULL    /* RdnBeeindRelatieID */,
   Naam                          Varchar(40)                   NOT NULL    /* NaamEnumeratiewaarde */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4428 PRIMARY KEY (ID),
   CONSTRAINT BR4429 UNIQUE (Naam)
);

CREATE TABLE Kern.RdnOntbrReisdoc (
   ID                            Smallint                      NOT NULL    /* RdnOntbrReisdocID */,
   Code                          Varchar(1)                    NOT NULL    /* RdnOntbrReisdocCode */,
   Naam                          Varchar(40)                   NOT NULL    /* NaamEnumeratiewaarde */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4469 PRIMARY KEY (ID),
   CONSTRAINT BR4470 UNIQUE (Code),
   CONSTRAINT BR4471 UNIQUE (Naam)
);

CREATE TABLE Kern.RdnOpschorting (
   ID                            Smallint                      NOT NULL    /* RdnOpschortingID */,
   Code                          Varchar(1)                    NOT NULL    /* RdnOpschortingCode */,
   Naam                          Varchar(40)                   NOT NULL    /* NaamEnumeratiewaarde */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4449 PRIMARY KEY (ID),
   CONSTRAINT BR4450 UNIQUE (Code),
   CONSTRAINT BR4451 UNIQUE (Naam)
);

CREATE TABLE Kern.RdnVerkNLNation (
   ID                            Integer                       NOT NULL    /* RdnVerkNLNationID */,
   Naam                          Varchar(40)                   NOT NULL    /* NaamEnumeratiewaarde */,
   DatAanvGel                    Numeric(8,0)                              /* Dat */,
   DatEindeGel                   Numeric(8,0)                              /* Dat */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4430 PRIMARY KEY (ID),
   CONSTRAINT BR4431 UNIQUE (Naam)
);

CREATE TABLE Kern.RdnVerliesNLNation (
   ID                            Integer                       NOT NULL    /* RdnVerliesNLNationID */,
   Naam                          Varchar(40)                   NOT NULL    /* NaamEnumeratiewaarde */,
   DatAanvGel                    Numeric(8,0)                              /* Dat */,
   DatEindeGel                   Numeric(8,0)                              /* Dat */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4432 PRIMARY KEY (ID),
   CONSTRAINT BR4433 UNIQUE (Naam)
);

CREATE TABLE Kern.RdnWijzAdres (
   ID                            Smallint                      NOT NULL    /* RdnWijzAdresID */,
   Code                          Varchar(1)                    NOT NULL    /* RdnWijzAdresCode */,
   Naam                          Varchar(40)                   NOT NULL    /* NaamEnumeratiewaarde */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4460 PRIMARY KEY (ID),
   CONSTRAINT BR4461 UNIQUE (Code),
   CONSTRAINT BR4462 UNIQUE (Naam)
);

CREATE SEQUENCE Kern.seq_Relatie;
CREATE TABLE Kern.Relatie (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_Relatie')  /* RelatieID */,
   Srt                           Integer                       NOT NULL    /* SrtRelatieID */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   DatAanv                       Numeric(8,0)                              /* Dat */,
   GemAanv                       Integer                                   /* PartijID */,
   WplAanv                       Integer                                   /* PlaatsID */,
   BLPlaatsAanv                  Varchar(40)                               /* BLPlaats */,
   BLRegioAanv                   Varchar(35)                               /* BLRegio */,
   LandAanv                      Integer                                   /* LandID */,
   OmsLocAanv                    Varchar(40)                               /* LocOms */,
   RdnEinde                      Smallint                                  /* RdnBeeindRelatieID */,
   DatEinde                      Numeric(8,0)                              /* Dat */,
   GemEinde                      Integer                                   /* PartijID */,
   WplEinde                      Integer                                   /* PlaatsID */,
   BLPlaatsEinde                 Varchar(40)                               /* BLPlaats */,
   BLRegioEinde                  Varchar(35)                               /* BLRegio */,
   LandEinde                     Integer                                   /* LandID */,
   OmsLocEinde                   Varchar(40)                               /* LocOms */,
   IndRelatieMaterieel           boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4424 PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_Relatie OWNED BY Kern.Relatie.ID;

CREATE TABLE Kern.Rol (
   ID                            Integer                       NOT NULL    /* RolID */,
   Naam                          Varchar(40)                   NOT NULL    /* NaamEnumeratiewaarde */,
   DatAanvGel                    Numeric(8,0)                              /* Dat */,
   DatEindeGel                   Numeric(8,0)                              /* Dat */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4491 PRIMARY KEY (ID),
   CONSTRAINT BR4492 UNIQUE (Naam)
);

CREATE SEQUENCE Kern.seq_Sector;
CREATE TABLE Kern.Sector (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_Sector')  /* SectorID */,
   Naam                          Varchar(40)                   NOT NULL    /* NaamEnumeratiewaarde */,
   DatAanvGel                    Numeric(8,0)                              /* Dat */,
   DatEindeGel                   Numeric(8,0)                              /* Dat */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4494 PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_Sector OWNED BY Kern.Sector.ID;

CREATE TABLE Kern.SrtNLReisdoc (
   ID                            Integer                       NOT NULL    /* SrtNLReisdocID */,
   Code                          Varchar(2)                    NOT NULL    /* SrtNLReisdocCode */,
   Naam                          Varchar(40)                   NOT NULL    /* NaamEnumeratiewaarde */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4463 PRIMARY KEY (ID),
   CONSTRAINT BR4464 UNIQUE (Code),
   CONSTRAINT BR4465 UNIQUE (Naam)
);

CREATE TABLE Kern.SrtActie (
   ID                            Integer                       NOT NULL    /* SrtActieID */,
   Naam                          Varchar(40)                   NOT NULL    /* NaamEnumeratiewaarde */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4401 PRIMARY KEY (ID),
   CONSTRAINT BR4402 UNIQUE (Naam)
);

CREATE TABLE Kern.SrtBetr (
   ID                            Smallint                      NOT NULL    /* SrtBetrID */,
   Code                          Varchar(1)                    NOT NULL    /* SrtBetrCode */,
   Naam                          Varchar(40)                   NOT NULL    /* NaamEnumeratiewaarde */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4476 PRIMARY KEY (ID),
   CONSTRAINT BR4477 UNIQUE (Code),
   CONSTRAINT BR4478 UNIQUE (Naam)
);

CREATE TABLE Kern.SrtDoc (
   ID                            Integer                       NOT NULL    /* SrtDocID */,
   Oms                           Varchar(200)                  NOT NULL    /* OmsEnumeratiewaarde */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4419 PRIMARY KEY (ID),
   CONSTRAINT BR4420 UNIQUE (Oms)
);

CREATE TABLE Kern.SrtElement (
   ID                            Smallint                      NOT NULL    /* SrtElementID */,
   Naam                          Varchar(40)                   NOT NULL    /* NaamEnumeratiewaarde */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4457 PRIMARY KEY (ID),
   CONSTRAINT BR4458 UNIQUE (Naam)
);

CREATE TABLE Kern.SrtIndicatie (
   ID                            Smallint                      NOT NULL    /* SrtIndicatieID */,
   Naam                          Varchar(40)                   NOT NULL    /* NaamEnumeratiewaarde */,
   IndMaterieleHistorieVanToep   boolean                       NOT NULL    /* JaNee */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4447 PRIMARY KEY (ID),
   CONSTRAINT BR4448 UNIQUE (Naam)
);

CREATE TABLE Kern.SrtMultiRealiteitRegel (
   ID                            Smallint                      NOT NULL    /* SrtMultiRealiteitRegelID */,
   Naam                          Varchar(40)                   NOT NULL    /* NaamEnumeratiewaarde */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4489 PRIMARY KEY (ID),
   CONSTRAINT BR4490 UNIQUE (Naam)
);

CREATE TABLE Kern.SrtPartij (
   ID                            Integer                       NOT NULL    /* SrtPartijID */,
   Naam                          Varchar(40)                   NOT NULL    /* NaamEnumeratiewaarde */,
   DatAanvGel                    Numeric(8,0)                              /* Dat */,
   DatEindeGel                   Numeric(8,0)                              /* Dat */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4493 PRIMARY KEY (ID)
);

CREATE TABLE Kern.SrtPers (
   ID                            Smallint                      NOT NULL    /* SrtPersID */,
   Code                          Varchar(1)                    NOT NULL    /* SrtPersCode */,
   Naam                          Varchar(40)                   NOT NULL    /* NaamEnumeratiewaarde */,
   Oms                           Varchar(200)                              /* OmsEnumeratiewaarde */,
   DatAanvGel                    Numeric(8,0)                              /* Dat */,
   DatEindeGel                   Numeric(8,0)                              /* Dat */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4485 PRIMARY KEY (ID),
   CONSTRAINT BR4486 UNIQUE (Code),
   CONSTRAINT BR4487 UNIQUE (Naam)
);

CREATE TABLE Kern.SrtRelatie (
   ID                            Integer                       NOT NULL    /* SrtRelatieID */,
   Code                          Varchar(1)                    NOT NULL    /* SrtRelatieCode */,
   Naam                          Varchar(40)                   NOT NULL    /* NaamEnumeratiewaarde */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4425 PRIMARY KEY (ID),
   CONSTRAINT BR4426 UNIQUE (Naam),
   CONSTRAINT BR4427 UNIQUE (Code)
);

CREATE TABLE Kern.SrtVerificatie (
   ID                            Integer                       NOT NULL    /* SrtVerificatieID */,
   Naam                          Varchar(40)                   NOT NULL    /* NaamEnumeratiewaarde */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4472 PRIMARY KEY (ID),
   CONSTRAINT BR4473 UNIQUE (Naam)
);

CREATE TABLE Kern.Verantwoordelijke (
   ID                            Smallint                      NOT NULL    /* VerantwoordelijkeID */,
   Code                          Varchar(1)                    NOT NULL    /* VerantwoordelijkeCode */,
   Naam                          Varchar(40)                   NOT NULL    /* NaamEnumeratiewaarde */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4443 PRIMARY KEY (ID),
   CONSTRAINT BR4444 UNIQUE (Naam),
   CONSTRAINT BR4445 UNIQUE (Code)
);

CREATE TABLE Kern.Verblijfsr (
   ID                            Integer                       NOT NULL    /* VerblijfsrID */,
   Oms                           Varchar(200)                  NOT NULL    /* OmsEnumeratiewaarde */,
   DatAanvGel                    Numeric(8,0)                              /* Dat */,
   DatEindeGel                   Numeric(8,0)                              /* Dat */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4441 PRIMARY KEY (ID),
   CONSTRAINT BR4442 UNIQUE (Oms)
);

CREATE TABLE Kern.Verdrag (
   ID                            Integer                       NOT NULL    /* VerdragID */,
   Oms                           Varchar(200)                  NOT NULL    /* OmsEnumeratiewaarde */,
   DatAanvGel                    Numeric(8,0)                              /* Dat */,
   DatEindeGel                   Numeric(8,0)                              /* Dat */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4474 PRIMARY KEY (ID),
   CONSTRAINT BR4475 UNIQUE (Oms)
);

CREATE TABLE Kern.WijzeGebruikGeslnaam (
   ID                            Smallint                      NOT NULL    /* WijzeGebruikGeslnaamID */,
   Code                          Varchar(1)                    NOT NULL    /* WijzeGebruikGeslnaamCode */,
   Naam                          Varchar(40)                   NOT NULL    /* NaamEnumeratiewaarde */,
   Oms                           Varchar(200)                              /* OmsEnumeratiewaarde */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4452 PRIMARY KEY (ID),
   CONSTRAINT BR4453 UNIQUE (Code),
   CONSTRAINT BR4454 UNIQUE (Naam)
);

CREATE SEQUENCE Lev.seq_Lev;
CREATE TABLE Lev.Lev (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Lev.seq_Lev')  /* LevID */,
   Srt                           Bigint                        NOT NULL    /* SrtLevID */,
   Partij                        Integer                       NOT NULL    /* PartijID */,
   TsBesch                       Timestamp                                 /* Ts */,
   TsLev                         Timestamp                                 /* Ts */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4828 PRIMARY KEY (ID)
);
ALTER SEQUENCE Lev.seq_Lev OWNED BY Lev.Lev.ID;

CREATE SEQUENCE Lev.seq_LevBer;
CREATE TABLE Lev.LevBer (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Lev.seq_LevBer')  /* LevBerID */,
   Lev                           Bigint                        NOT NULL    /* LevID */,
   Ber                           Bigint                        NOT NULL    /* BerID */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4848 PRIMARY KEY (ID),
   CONSTRAINT BR4849 UNIQUE (Lev, Ber)
);
ALTER SEQUENCE Lev.seq_LevBer OWNED BY Lev.LevBer.ID;

CREATE SEQUENCE Lev.seq_LevPers;
CREATE TABLE Lev.LevPers (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Lev.seq_LevPers')  /* LevPersID */,
   Lev                           Bigint                        NOT NULL    /* LevID */,
   Pers                          Bigint                        NOT NULL    /* PersID */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4850 PRIMARY KEY (ID),
   CONSTRAINT BR4851 UNIQUE (Lev, Pers)
);
ALTER SEQUENCE Lev.seq_LevPers OWNED BY Lev.LevPers.ID;

CREATE TABLE Lev.SrtLev (
   ID                            Bigint                        NOT NULL    /* SrtLevID */,
   Naam                          Varchar(40)                   NOT NULL    /* NaamEnumeratiewaarde */,
   IndMaterieel                  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4829 PRIMARY KEY (ID),
   CONSTRAINT BR4830 UNIQUE (Naam)
);

-- Materiele historie tabellen -------------------------------------------------
CREATE SEQUENCE AutAut.seq_His_Authenticatiemiddel;
CREATE TABLE AutAut.His_Authenticatiemiddel (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('AutAut.seq_His_Authenticatiemiddel')  /* His_AuthenticatiemiddelID */,
   Authenticatiemiddel           Integer                                   /* AuthenticatiemiddelID */,
   TsReg                         Timestamp                                 /* Ts */,
   TsVerval                      Timestamp                                 /* Ts */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   Functie                       Smallint                                  /* FunctieID */,
   CertificaatTbvSSL             Integer                       NOT NULL    /* CertificaatID */,
   CertificaatTbvOndertekening   Integer                       NOT NULL    /* CertificaatID */,
   IPAdres                       inet                                      /* IPAdres */,
   CONSTRAINT BR4691 PRIMARY KEY (ID),
   CONSTRAINT BR4687 UNIQUE (Authenticatiemiddel, TsReg)
);
ALTER SEQUENCE AutAut.seq_His_Authenticatiemiddel OWNED BY AutAut.His_Authenticatiemiddel.ID;

CREATE SEQUENCE Kern.seq_His_Betr;
CREATE TABLE Kern.His_Betr (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_Betr')  /* His_BetrID */,
   Betr                          Bigint                                    /* BetrID */,
   TsReg                         Timestamp                                 /* Ts */,
   TsVerval                      Timestamp                                 /* Ts */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   DatAanv                       Numeric(8,0)                              /* Dat */,
   DatEinde                      Numeric(8,0)                              /* Dat */,
   CONSTRAINT BR4510 PRIMARY KEY (ID),
   CONSTRAINT BR4032 UNIQUE (Betr, TsReg)
);
ALTER SEQUENCE Kern.seq_His_Betr OWNED BY Kern.His_Betr.ID;

CREATE SEQUENCE Kern.seq_His_BetrOuderlijkGezag;
CREATE TABLE Kern.His_BetrOuderlijkGezag (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_BetrOuderlijkGezag')  /* His_BetrOuderlijkGezagID */,
   Betr                          Bigint                                    /* BetrID */,
   DatAanvGel                    Numeric(8,0)                              /* Dat */,
   DatEindeGel                   Numeric(8,0)                              /* Dat */,
   TsReg                         Timestamp                                 /* Ts */,
   TsVerval                      Timestamp                                 /* Ts */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   ActieAanpGel                  Bigint                                    /* ActieID */,
   IndOuderHeeftGezag            boolean                                   /* JaNee */,
   CONSTRAINT BR4513 PRIMARY KEY (ID),
   CONSTRAINT BR4042 UNIQUE (Betr, TsReg)
);
ALTER SEQUENCE Kern.seq_His_BetrOuderlijkGezag OWNED BY Kern.His_BetrOuderlijkGezag.ID;

CREATE SEQUENCE Kern.seq_His_Doc;
CREATE TABLE Kern.His_Doc (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_Doc')  /* His_DocID */,
   Doc                           Bigint                                    /* DocID */,
   TsReg                         Timestamp                                 /* Ts */,
   TsVerval                      Timestamp                                 /* Ts */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   Ident                         Varchar(20)                               /* DocIdent */,
   Aktenr                        Varchar(7)                                /* Aktenr */,
   Oms                           Varchar(80)                               /* DocOms */,
   Partij                        Integer                       NOT NULL    /* PartijID */,
   CONSTRAINT BR4516 PRIMARY KEY (ID),
   CONSTRAINT BR4052 UNIQUE (Doc, TsReg)
);
ALTER SEQUENCE Kern.seq_His_Doc OWNED BY Kern.His_Doc.ID;

CREATE SEQUENCE Kern.seq_His_Gem;
CREATE TABLE Kern.His_Gem (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_Gem')  /* His_GemID */,
   Partij                        Integer                                   /* PartijID */,
   TsReg                         Timestamp                                 /* Ts */,
   TsVerval                      Timestamp                                 /* Ts */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   VoortzettendeGem              Integer                                   /* PartijID */,
   Gemcode                       Numeric(4,0)                  NOT NULL    /* Gemcode */,
   OnderdeelVan                  Integer                                   /* PartijID */,
   CONSTRAINT BR4617 PRIMARY KEY (ID),
   CONSTRAINT BR4614 UNIQUE (Partij, TsReg)
);
ALTER SEQUENCE Kern.seq_His_Gem OWNED BY Kern.His_Gem.ID;

CREATE SEQUENCE Kern.seq_His_MultiRealiteitRegel;
CREATE TABLE Kern.His_MultiRealiteitRegel (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_MultiRealiteitRegel')  /* His_MultiRealiteitRegelID */,
   MultiRealiteitRegel           Bigint                                    /* MultiRealiteitregelID */,
   DatAanvGel                    Numeric(8,0)                              /* Dat */,
   DatEindeGel                   Numeric(8,0)                              /* Dat */,
   TsReg                         Timestamp                                 /* Ts */,
   TsVerval                      Timestamp                                 /* Ts */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   ActieAanpGel                  Bigint                                    /* ActieID */,
   GeldigVoor                    Bigint                        NOT NULL    /* PersID */,
   Srt                           Smallint                      NOT NULL    /* SrtMultiRealiteitRegelID */,
   Pers                          Bigint                                    /* PersID */,
   MultiRealiteitPers            Bigint                                    /* PersID */,
   Relatie                       Bigint                                    /* RelatieID */,
   Betr                          Bigint                                    /* BetrID */,
   CONSTRAINT BR4519 PRIMARY KEY (ID),
   CONSTRAINT BR4067 UNIQUE (MultiRealiteitRegel, TsReg)
);
ALTER SEQUENCE Kern.seq_His_MultiRealiteitRegel OWNED BY Kern.His_MultiRealiteitRegel.ID;

CREATE SEQUENCE Kern.seq_His_Onderzoek;
CREATE TABLE Kern.His_Onderzoek (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_Onderzoek')  /* His_OnderzoekID */,
   Onderzoek                     Bigint                                    /* OnderzoekID */,
   TsReg                         Timestamp                                 /* Ts */,
   TsVerval                      Timestamp                                 /* Ts */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   DatBegin                      Numeric(8,0)                  NOT NULL    /* Dat */,
   DatEinde                      Numeric(8,0)                              /* Dat */,
   Oms                           Text                                      /* OnderzoekOms */,
   CONSTRAINT BR4522 PRIMARY KEY (ID),
   CONSTRAINT BR4076 UNIQUE (Onderzoek, TsReg)
);
ALTER SEQUENCE Kern.seq_His_Onderzoek OWNED BY Kern.His_Onderzoek.ID;

CREATE SEQUENCE Kern.seq_His_Partij;
CREATE TABLE Kern.His_Partij (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_Partij')  /* His_PartijID */,
   Partij                        Integer                                   /* PartijID */,
   TsReg                         Timestamp                                 /* Ts */,
   TsVerval                      Timestamp                                 /* Ts */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   DatAanv                       Numeric(8,0)                  NOT NULL    /* Dat */,
   DatEinde                      Numeric(8,0)                              /* Dat */,
   Sector                        Integer                                   /* SectorID */,
   CONSTRAINT BR4631 PRIMARY KEY (ID),
   CONSTRAINT BR4628 UNIQUE (Partij, TsReg)
);
ALTER SEQUENCE Kern.seq_His_Partij OWNED BY Kern.His_Partij.ID;

CREATE SEQUENCE Kern.seq_His_PartijRol;
CREATE TABLE Kern.His_PartijRol (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PartijRol')  /* His_PartijRolID */,
   PartijRol                     Integer                                   /* PartijRolID */,
   DatAanvGel                    Numeric(8,0)                              /* Dat */,
   DatEindeGel                   Numeric(8,0)                              /* Dat */,
   TsReg                         Timestamp                                 /* Ts */,
   TsVerval                      Timestamp                                 /* Ts */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   CONSTRAINT BR4793 PRIMARY KEY (ID),
   CONSTRAINT BR4790 UNIQUE (PartijRol, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PartijRol OWNED BY Kern.His_PartijRol.ID;

CREATE SEQUENCE Kern.seq_His_PersAanschr;
CREATE TABLE Kern.His_PersAanschr (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersAanschr')  /* His_PersAanschrID */,
   Pers                          Bigint                                    /* PersID */,
   DatAanvGel                    Numeric(8,0)                              /* Dat */,
   DatEindeGel                   Numeric(8,0)                              /* Dat */,
   TsReg                         Timestamp                                 /* Ts */,
   TsVerval                      Timestamp                                 /* Ts */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   ActieAanpGel                  Bigint                                    /* ActieID */,
   GebrGeslnaamEGP               Smallint                                  /* WijzeGebruikGeslnaamID */,
   IndAanschrMetAdellijkeTitel   boolean                                   /* JaNee */,
   IndAanschrAlgoritmischAfgele  boolean                       NOT NULL    /* JaNee */,
   PredikaatAanschr              Smallint                                  /* PredikaatID */,
   VoornamenAanschr              Varchar(200)                              /* Voornamen */,
   VoorvoegselAanschr            Varchar(10)                               /* Voorvoegsel */,
   ScheidingstekenAanschr        Varchar(1)                                /* Scheidingsteken */,
   GeslnaamAanschr               Varchar(200)                  NOT NULL    /* Geslnaam */,
   CONSTRAINT BR4534 PRIMARY KEY (ID),
   CONSTRAINT BR4131 UNIQUE (Pers, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PersAanschr OWNED BY Kern.His_PersAanschr.ID;

CREATE SEQUENCE Kern.seq_His_PersBijhgem;
CREATE TABLE Kern.His_PersBijhgem (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersBijhgem')  /* His_PersBijhgemID */,
   Pers                          Bigint                                    /* PersID */,
   DatAanvGel                    Numeric(8,0)                              /* Dat */,
   DatEindeGel                   Numeric(8,0)                              /* Dat */,
   TsReg                         Timestamp                                 /* Ts */,
   TsVerval                      Timestamp                                 /* Ts */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   ActieAanpGel                  Bigint                                    /* ActieID */,
   Bijhgem                       Integer                       NOT NULL    /* PartijID */,
   DatInschrInGem                Numeric(8,0)                  NOT NULL    /* Dat */,
   IndOnverwDocAanw              boolean                                   /* JaNee */,
   CONSTRAINT BR4558 PRIMARY KEY (ID),
   CONSTRAINT BR4216 UNIQUE (Pers, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PersBijhgem OWNED BY Kern.His_PersBijhgem.ID;

CREATE SEQUENCE Kern.seq_His_PersBijhverantwoordelijk;
CREATE TABLE Kern.His_PersBijhverantwoordelijk (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersBijhverantwoordelijk')  /* His_PersBijhverantwoordelijk */,
   Pers                          Bigint                                    /* PersID */,
   DatAanvGel                    Numeric(8,0)                              /* Dat */,
   DatEindeGel                   Numeric(8,0)                              /* Dat */,
   TsReg                         Timestamp                                 /* Ts */,
   TsVerval                      Timestamp                                 /* Ts */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   ActieAanpGel                  Bigint                                    /* ActieID */,
   Verantwoordelijke             Smallint                      NOT NULL    /* VerantwoordelijkeID */,
   CONSTRAINT BR4552 PRIMARY KEY (ID),
   CONSTRAINT BR4197 UNIQUE (Pers, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PersBijhverantwoordelijk OWNED BY Kern.His_PersBijhverantwoordelijk.ID;

CREATE SEQUENCE Kern.seq_His_PersEUVerkiezingen;
CREATE TABLE Kern.His_PersEUVerkiezingen (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersEUVerkiezingen')  /* His_PersEUVerkiezingenID */,
   Pers                          Bigint                                    /* PersID */,
   TsReg                         Timestamp                                 /* Ts */,
   TsVerval                      Timestamp                                 /* Ts */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   IndDeelnEUVerkiezingen        boolean                                   /* JaNee */,
   DatAanlAanpDeelnEUVerkiezi    Numeric(8,0)                              /* Dat */,
   DatEindeUitslEUKiesr          Numeric(8,0)                              /* Dat */,
   CONSTRAINT BR4549 PRIMARY KEY (ID),
   CONSTRAINT BR4187 UNIQUE (Pers, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PersEUVerkiezingen OWNED BY Kern.His_PersEUVerkiezingen.ID;

CREATE SEQUENCE Kern.seq_His_PersGeboorte;
CREATE TABLE Kern.His_PersGeboorte (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersGeboorte')  /* His_PersGeboorteID */,
   Pers                          Bigint                                    /* PersID */,
   TsReg                         Timestamp                                 /* Ts */,
   TsVerval                      Timestamp                                 /* Ts */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   DatGeboorte                   Numeric(8,0)                  NOT NULL    /* Dat */,
   GemGeboorte                   Integer                                   /* PartijID */,
   WplGeboorte                   Integer                                   /* PlaatsID */,
   BLGeboorteplaats              Varchar(40)                               /* BLPlaats */,
   BLRegioGeboorte               Varchar(35)                               /* BLRegio */,
   LandGeboorte                  Integer                       NOT NULL    /* LandID */,
   OmsGeboorteloc                Varchar(40)                               /* LocOms */,
   CONSTRAINT BR4537 PRIMARY KEY (ID),
   CONSTRAINT BR4144 UNIQUE (Pers, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PersGeboorte OWNED BY Kern.His_PersGeboorte.ID;

CREATE SEQUENCE Kern.seq_His_PersGeslachtsaand;
CREATE TABLE Kern.His_PersGeslachtsaand (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersGeslachtsaand')  /* His_PersGeslachtsaandID */,
   Pers                          Bigint                                    /* PersID */,
   DatAanvGel                    Numeric(8,0)                              /* Dat */,
   DatEindeGel                   Numeric(8,0)                              /* Dat */,
   TsReg                         Timestamp                                 /* Ts */,
   TsVerval                      Timestamp                                 /* Ts */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   ActieAanpGel                  Bigint                                    /* ActieID */,
   Geslachtsaand                 Smallint                      NOT NULL    /* GeslachtsaandID */,
   CONSTRAINT BR4528 PRIMARY KEY (ID),
   CONSTRAINT BR4097 UNIQUE (Pers, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PersGeslachtsaand OWNED BY Kern.His_PersGeslachtsaand.ID;

CREATE SEQUENCE Kern.seq_His_PersIDs;
CREATE TABLE Kern.His_PersIDs (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersIDs')  /* His_PersIDsID */,
   Pers                          Bigint                                    /* PersID */,
   DatAanvGel                    Numeric(8,0)                              /* Dat */,
   DatEindeGel                   Numeric(8,0)                              /* Dat */,
   TsReg                         Timestamp                                 /* Ts */,
   TsVerval                      Timestamp                                 /* Ts */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   ActieAanpGel                  Bigint                                    /* ActieID */,
   BSN                           Numeric(9,0)                              /* BSN */,
   ANr                           Numeric(10,0)                             /* ANr */,
   CONSTRAINT BR4525 PRIMARY KEY (ID),
   CONSTRAINT BR4087 UNIQUE (Pers, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PersIDs OWNED BY Kern.His_PersIDs.ID;

CREATE SEQUENCE Kern.seq_His_PersImmigratie;
CREATE TABLE Kern.His_PersImmigratie (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersImmigratie')  /* His_PersImmigratieID */,
   Pers                          Bigint                                    /* PersID */,
   DatAanvGel                    Numeric(8,0)                              /* Dat */,
   DatEindeGel                   Numeric(8,0)                              /* Dat */,
   TsReg                         Timestamp                                 /* Ts */,
   TsVerval                      Timestamp                                 /* Ts */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   ActieAanpGel                  Bigint                                    /* ActieID */,
   LandVanwaarGevestigd          Integer                                   /* LandID */,
   DatVestigingInNederland       Numeric(8,0)                  NOT NULL    /* Dat */,
   CONSTRAINT BR4564 PRIMARY KEY (ID),
   CONSTRAINT BR4235 UNIQUE (Pers, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PersImmigratie OWNED BY Kern.His_PersImmigratie.ID;

CREATE SEQUENCE Kern.seq_His_PersInschr;
CREATE TABLE Kern.His_PersInschr (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersInschr')  /* His_PersInschrID */,
   Pers                          Bigint                                    /* PersID */,
   DatAanvGel                    Numeric(8,0)                              /* Dat */,
   DatEindeGel                   Numeric(8,0)                              /* Dat */,
   TsReg                         Timestamp                                 /* Ts */,
   TsVerval                      Timestamp                                 /* Ts */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   ActieAanpGel                  Bigint                                    /* ActieID */,
   DatInschr                     Numeric(8,0)                  NOT NULL    /* Dat */,
   Versienr                      Bigint                        NOT NULL    /* Versienr */,
   VorigePers                    Bigint                                    /* PersID */,
   VolgendePers                  Bigint                                    /* PersID */,
   CONSTRAINT BR4567 PRIMARY KEY (ID),
   CONSTRAINT BR4248 UNIQUE (Pers, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PersInschr OWNED BY Kern.His_PersInschr.ID;

CREATE SEQUENCE Kern.seq_His_PersOpschorting;
CREATE TABLE Kern.His_PersOpschorting (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersOpschorting')  /* His_PersOpschortingID */,
   Pers                          Bigint                                    /* PersID */,
   TsReg                         Timestamp                                 /* Ts */,
   TsVerval                      Timestamp                                 /* Ts */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   RdnOpschortingBijhouding      Smallint                      NOT NULL    /* RdnOpschortingID */,
   CONSTRAINT BR4555 PRIMARY KEY (ID),
   CONSTRAINT BR4204 UNIQUE (Pers, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PersOpschorting OWNED BY Kern.His_PersOpschorting.ID;

CREATE SEQUENCE Kern.seq_His_PersOverlijden;
CREATE TABLE Kern.His_PersOverlijden (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersOverlijden')  /* His_PersOverlijdenID */,
   Pers                          Bigint                                    /* PersID */,
   TsReg                         Timestamp                                 /* Ts */,
   TsVerval                      Timestamp                                 /* Ts */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   DatOverlijden                 Numeric(8,0)                  NOT NULL    /* Dat */,
   GemOverlijden                 Integer                                   /* PartijID */,
   WplOverlijden                 Integer                                   /* PlaatsID */,
   BLPlaatsOverlijden            Varchar(40)                               /* BLPlaats */,
   BLRegioOverlijden             Varchar(35)                               /* BLRegio */,
   LandOverlijden                Integer                       NOT NULL    /* LandID */,
   OmsLocOverlijden              Varchar(40)                               /* LocOms */,
   CONSTRAINT BR4540 PRIMARY KEY (ID),
   CONSTRAINT BR4157 UNIQUE (Pers, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PersOverlijden OWNED BY Kern.His_PersOverlijden.ID;

CREATE SEQUENCE Kern.seq_His_PersPK;
CREATE TABLE Kern.His_PersPK (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersPK')  /* His_PersPKID */,
   Pers                          Bigint                                    /* PersID */,
   TsReg                         Timestamp                                 /* Ts */,
   TsVerval                      Timestamp                                 /* Ts */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   GemPK                         Integer                                   /* PartijID */,
   IndPKVolledigGeconv           boolean                                   /* JaNee */,
   CONSTRAINT BR4561 PRIMARY KEY (ID),
   CONSTRAINT BR4224 UNIQUE (Pers, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PersPK OWNED BY Kern.His_PersPK.ID;

CREATE SEQUENCE Kern.seq_His_PersSamengesteldeNaam;
CREATE TABLE Kern.His_PersSamengesteldeNaam (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersSamengesteldeNaam')  /* His_PersSamengesteldeNaamID */,
   Pers                          Bigint                                    /* PersID */,
   DatAanvGel                    Numeric(8,0)                              /* Dat */,
   DatEindeGel                   Numeric(8,0)                              /* Dat */,
   TsReg                         Timestamp                                 /* Ts */,
   TsVerval                      Timestamp                                 /* Ts */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   ActieAanpGel                  Bigint                                    /* ActieID */,
   Predikaat                     Smallint                                  /* PredikaatID */,
   Voornamen                     Varchar(200)                              /* Voornamen */,
   Voorvoegsel                   Varchar(10)                               /* Voorvoegsel */,
   Scheidingsteken               Varchar(1)                                /* Scheidingsteken */,
   AdellijkeTitel                Smallint                                  /* AdellijkeTitelID */,
   Geslnaam                      Varchar(200)                  NOT NULL    /* Geslnaam */,
   IndNreeksAlsGeslnaam          boolean                                   /* JaNee */,
   IndAlgoritmischAfgeleid       boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4531 PRIMARY KEY (ID),
   CONSTRAINT BR4114 UNIQUE (Pers, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PersSamengesteldeNaam OWNED BY Kern.His_PersSamengesteldeNaam.ID;

CREATE SEQUENCE Kern.seq_His_PersUitslNLKiesr;
CREATE TABLE Kern.His_PersUitslNLKiesr (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersUitslNLKiesr')  /* His_PersUitslNLKiesrID */,
   Pers                          Bigint                                    /* PersID */,
   TsReg                         Timestamp                                 /* Ts */,
   TsVerval                      Timestamp                                 /* Ts */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   IndUitslNLKiesr               boolean                                   /* JaNee */,
   DatEindeUitslNLKiesr          Numeric(8,0)                              /* Dat */,
   CONSTRAINT BR4546 PRIMARY KEY (ID),
   CONSTRAINT BR4178 UNIQUE (Pers, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PersUitslNLKiesr OWNED BY Kern.His_PersUitslNLKiesr.ID;

CREATE SEQUENCE Kern.seq_His_PersVerblijfsr;
CREATE TABLE Kern.His_PersVerblijfsr (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersVerblijfsr')  /* His_PersVerblijfsrID */,
   Pers                          Bigint                                    /* PersID */,
   DatAanvGel                    Numeric(8,0)                              /* Dat */,
   DatEindeGel                   Numeric(8,0)                              /* Dat */,
   TsReg                         Timestamp                                 /* Ts */,
   TsVerval                      Timestamp                                 /* Ts */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   ActieAanpGel                  Bigint                                    /* ActieID */,
   Verblijfsr                    Integer                       NOT NULL    /* VerblijfsrID */,
   DatAanvVerblijfsr             Numeric(8,0)                  NOT NULL    /* Dat */,
   DatVoorzEindeVerblijfsr       Numeric(8,0)                              /* Dat */,
   DatAanvAaneenslVerblijfsr     Numeric(8,0)                              /* Dat */,
   CONSTRAINT BR4543 PRIMARY KEY (ID),
   CONSTRAINT BR4170 UNIQUE (Pers, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PersVerblijfsr OWNED BY Kern.His_PersVerblijfsr.ID;

CREATE SEQUENCE Kern.seq_His_PersAdresAdresh;
CREATE TABLE Kern.His_PersAdresAdresh (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersAdresAdresh')  /* His_PersAdresAdreshID */,
   PersAdres                     Bigint                                    /* PersAdresID */,
   DatAanvGel                    Numeric(8,0)                              /* Dat */,
   DatEindeGel                   Numeric(8,0)                              /* Dat */,
   TsReg                         Timestamp                                 /* Ts */,
   TsVerval                      Timestamp                                 /* Ts */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   ActieAanpGel                  Bigint                                    /* ActieID */,
   Srt                           Smallint                      NOT NULL    /* SrtAdresID */,
   RdnWijz                       Smallint                      NOT NULL    /* RdnWijzAdresID */,
   AangAdresh                    Smallint                                  /* AangAdreshID */,
   DatAanvAdresh                 Numeric(8,0)                  NOT NULL    /* Dat */,
   CONSTRAINT BR4570 PRIMARY KEY (ID),
   CONSTRAINT BR4261 UNIQUE (PersAdres, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PersAdresAdresh OWNED BY Kern.His_PersAdresAdresh.ID;

CREATE SEQUENCE Kern.seq_His_PersAdresBLAdres;
CREATE TABLE Kern.His_PersAdresBLAdres (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersAdresBLAdres')  /* His_PersAdresBLAdresID */,
   PersAdres                     Bigint                                    /* PersAdresID */,
   DatAanvGel                    Numeric(8,0)                              /* Dat */,
   DatEindeGel                   Numeric(8,0)                              /* Dat */,
   TsReg                         Timestamp                                 /* Ts */,
   TsVerval                      Timestamp                                 /* Ts */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   ActieAanpGel                  Bigint                                    /* ActieID */,
   BLAdresRegel1                 Varchar(40)                               /* Adresregel */,
   BLAdresRegel2                 Varchar(40)                               /* Adresregel */,
   BLAdresRegel3                 Varchar(40)                               /* Adresregel */,
   BLAdresRegel4                 Varchar(40)                               /* Adresregel */,
   BLAdresRegel5                 Varchar(40)                               /* Adresregel */,
   BLAdresRegel6                 Varchar(40)                               /* Adresregel */,
   Land                          Integer                       NOT NULL    /* LandID */,
   DatVertrekUitNederland        Numeric(8,0)                  NOT NULL    /* Dat */,
   CONSTRAINT BR4576 PRIMARY KEY (ID),
   CONSTRAINT BR4300 UNIQUE (PersAdres, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PersAdresBLAdres OWNED BY Kern.His_PersAdresBLAdres.ID;

CREATE SEQUENCE Kern.seq_His_PersAdresNLAdres;
CREATE TABLE Kern.His_PersAdresNLAdres (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersAdresNLAdres')  /* His_PersAdresNLAdresID */,
   PersAdres                     Bigint                                    /* PersAdresID */,
   DatAanvGel                    Numeric(8,0)                              /* Dat */,
   DatEindeGel                   Numeric(8,0)                              /* Dat */,
   TsReg                         Timestamp                                 /* Ts */,
   TsVerval                      Timestamp                                 /* Ts */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   ActieAanpGel                  Bigint                                    /* ActieID */,
   AdresseerbaarObject           Varchar(16)                               /* AandAdresseerbaarObject */,
   IdentcodeNraand               Varchar(16)                               /* IdentcodeNraand */,
   Gem                           Integer                       NOT NULL    /* PartijID */,
   NOR                           Varchar(80)                               /* NOR */,
   AfgekorteNOR                  Varchar(24)                               /* AfgekorteNOR */,
   Gemdeel                       Varchar(24)                               /* Gemdeel */,
   Huisnr                        Numeric(5,0)                              /* Huisnr */,
   Huisletter                    Varchar(1)                                /* Huisletter */,
   Huisnrtoevoeging              Varchar(4)                                /* Huisnrtoevoeging */,
   Postcode                      Varchar(6)                                /* Postcode */,
   Wpl                           Integer                                   /* PlaatsID */,
   LoctovAdres                   Varchar(2)                                /* AandBijHuisnr */,
   LocOms                        Varchar(40)                               /* LocOms */,
   CONSTRAINT BR4573 PRIMARY KEY (ID),
   CONSTRAINT BR4283 UNIQUE (PersAdres, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PersAdresNLAdres OWNED BY Kern.His_PersAdresNLAdres.ID;

CREATE SEQUENCE Kern.seq_His_PersGeslnaamcomp;
CREATE TABLE Kern.His_PersGeslnaamcomp (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersGeslnaamcomp')  /* His_PersGeslnaamcompID */,
   PersGeslnaamcomp              Bigint                                    /* PersGeslnaamID */,
   DatAanvGel                    Numeric(8,0)                              /* Dat */,
   DatEindeGel                   Numeric(8,0)                              /* Dat */,
   TsReg                         Timestamp                                 /* Ts */,
   TsVerval                      Timestamp                                 /* Ts */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   ActieAanpGel                  Bigint                                    /* ActieID */,
   Voorvoegsel                   Varchar(10)                               /* Voorvoegsel */,
   Scheidingsteken               Varchar(1)                                /* Scheidingsteken */,
   Naam                          Varchar(200)                  NOT NULL    /* Geslnaamcomp */,
   Predikaat                     Smallint                                  /* PredikaatID */,
   AdellijkeTitel                Smallint                                  /* AdellijkeTitelID */,
   CONSTRAINT BR4579 PRIMARY KEY (ID),
   CONSTRAINT BR4314 UNIQUE (PersGeslnaamcomp, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PersGeslnaamcomp OWNED BY Kern.His_PersGeslnaamcomp.ID;

CREATE SEQUENCE Kern.seq_His_PersIndicatie;
CREATE TABLE Kern.His_PersIndicatie (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersIndicatie')  /* His_PersIndicatieID */,
   PersIndicatie                 Bigint                                    /* PersIndicatieID */,
   DatAanvGel                    Numeric(8,0)                              /* Dat */,
   DatEindeGel                   Numeric(8,0)                              /* Dat */,
   TsReg                         Timestamp                                 /* Ts */,
   TsVerval                      Timestamp                                 /* Ts */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   ActieAanpGel                  Bigint                                    /* ActieID */,
   Waarde                        boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4582 PRIMARY KEY (ID),
   CONSTRAINT BR4324 UNIQUE (PersIndicatie, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PersIndicatie OWNED BY Kern.His_PersIndicatie.ID;

CREATE SEQUENCE Kern.seq_His_PersNation;
CREATE TABLE Kern.His_PersNation (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersNation')  /* His_PersNationID */,
   PersNation                    Bigint                                    /* PersNationID */,
   DatAanvGel                    Numeric(8,0)                              /* Dat */,
   DatEindeGel                   Numeric(8,0)                              /* Dat */,
   TsReg                         Timestamp                                 /* Ts */,
   TsVerval                      Timestamp                                 /* Ts */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   ActieAanpGel                  Bigint                                    /* ActieID */,
   RdnVerlies                    Integer                                   /* RdnVerliesNLNationID */,
   RdnVerk                       Integer                                   /* RdnVerkNLNationID */,
   CONSTRAINT BR4585 PRIMARY KEY (ID),
   CONSTRAINT BR4335 UNIQUE (PersNation, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PersNation OWNED BY Kern.His_PersNation.ID;

CREATE SEQUENCE Kern.seq_His_PersReisdoc;
CREATE TABLE Kern.His_PersReisdoc (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersReisdoc')  /* His_PersReisdocID */,
   PersReisdoc                   Bigint                                    /* ReisdocID */,
   DatAanvGel                    Numeric(8,0)                              /* Dat */,
   DatEindeGel                   Numeric(8,0)                              /* Dat */,
   TsReg                         Timestamp                                 /* Ts */,
   TsVerval                      Timestamp                                 /* Ts */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   ActieAanpGel                  Bigint                                    /* ActieID */,
   Nr                            Varchar(9)                    NOT NULL    /* ReisdocNr */,
   DatUitgifte                   Numeric(8,0)                  NOT NULL    /* Dat */,
   AutVanAfgifte                 Integer                       NOT NULL    /* AutVanAfgifteReisdocID */,
   DatVoorzeEindeGel             Numeric(8,0)                  NOT NULL    /* Dat */,
   DatInhingVermissing           Numeric(8,0)                              /* Dat */,
   RdnVervallen                  Smallint                                  /* RdnOntbrReisdocID */,
   LengteHouder                  Numeric(3,0)                              /* LengteInCm */,
   CONSTRAINT BR4588 PRIMARY KEY (ID),
   CONSTRAINT BR4351 UNIQUE (PersReisdoc, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PersReisdoc OWNED BY Kern.His_PersReisdoc.ID;

CREATE SEQUENCE Kern.seq_His_PersVerificatie;
CREATE TABLE Kern.His_PersVerificatie (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersVerificatie')  /* His_PersVerificatieID */,
   PersVerificatie               Bigint                                    /* VerificatieID */,
   TsReg                         Timestamp                                 /* Ts */,
   TsVerval                      Timestamp                                 /* Ts */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   Dat                           Numeric(8,0)                  NOT NULL    /* Dat */,
   CONSTRAINT BR4591 PRIMARY KEY (ID),
   CONSTRAINT BR4358 UNIQUE (PersVerificatie, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PersVerificatie OWNED BY Kern.His_PersVerificatie.ID;

CREATE SEQUENCE Kern.seq_His_PersVoornaam;
CREATE TABLE Kern.His_PersVoornaam (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersVoornaam')  /* His_PersVoornaamID */,
   PersVoornaam                  Bigint                                    /* PersVoornaamID */,
   DatAanvGel                    Numeric(8,0)                              /* Dat */,
   DatEindeGel                   Numeric(8,0)                              /* Dat */,
   TsReg                         Timestamp                                 /* Ts */,
   TsVerval                      Timestamp                                 /* Ts */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   ActieAanpGel                  Bigint                                    /* ActieID */,
   Naam                          Varchar(40)                   NOT NULL    /* Voornaam */,
   CONSTRAINT BR4594 PRIMARY KEY (ID),
   CONSTRAINT BR4368 UNIQUE (PersVoornaam, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PersVoornaam OWNED BY Kern.His_PersVoornaam.ID;

CREATE SEQUENCE Kern.seq_His_Relatie;
CREATE TABLE Kern.His_Relatie (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_Relatie')  /* His_RelatieID */,
   Relatie                       Bigint                                    /* RelatieID */,
   TsReg                         Timestamp                                 /* Ts */,
   TsVerval                      Timestamp                                 /* Ts */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   DatAanv                       Numeric(8,0)                              /* Dat */,
   GemAanv                       Integer                                   /* PartijID */,
   WplAanv                       Integer                                   /* PlaatsID */,
   BLPlaatsAanv                  Varchar(40)                               /* BLPlaats */,
   BLRegioAanv                   Varchar(35)                               /* BLRegio */,
   LandAanv                      Integer                                   /* LandID */,
   OmsLocAanv                    Varchar(40)                               /* LocOms */,
   RdnEinde                      Smallint                                  /* RdnBeeindRelatieID */,
   DatEinde                      Numeric(8,0)                              /* Dat */,
   GemEinde                      Integer                                   /* PartijID */,
   WplEinde                      Integer                                   /* PlaatsID */,
   BLPlaatsEinde                 Varchar(40)                               /* BLPlaats */,
   BLRegioEinde                  Varchar(35)                               /* BLRegio */,
   LandEinde                     Integer                                   /* LandID */,
   OmsLocEinde                   Varchar(40)                               /* LocOms */,
   CONSTRAINT BR4597 PRIMARY KEY (ID),
   CONSTRAINT BR4389 UNIQUE (Relatie, TsReg)
);
ALTER SEQUENCE Kern.seq_His_Relatie OWNED BY Kern.His_Relatie.ID;


-- Foreign keys ----------------------------------------------------------------
ALTER TABLE AutAut.Authenticatiemiddel ADD CONSTRAINT FK2232 FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE AutAut.Authenticatiemiddel ADD CONSTRAINT FK2233 FOREIGN KEY (Rol) REFERENCES Kern.Rol (ID);
ALTER TABLE AutAut.Authenticatiemiddel ADD CONSTRAINT FK4665 FOREIGN KEY (Functie) REFERENCES AutAut.Functie (ID);
ALTER TABLE AutAut.Authenticatiemiddel ADD CONSTRAINT FK4667 FOREIGN KEY (CertificaatTbvSSL) REFERENCES AutAut.Certificaat (ID);
ALTER TABLE AutAut.Authenticatiemiddel ADD CONSTRAINT FK4668 FOREIGN KEY (CertificaatTbvOndertekening) REFERENCES AutAut.Certificaat (ID);
ALTER TABLE Kern.Actie ADD CONSTRAINT FK3055 FOREIGN KEY (Srt) REFERENCES Kern.SrtActie (ID);
ALTER TABLE Kern.Actie ADD CONSTRAINT FK3209 FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.Actie ADD CONSTRAINT FK3212 FOREIGN KEY (Verdrag) REFERENCES Kern.Verdrag (ID);
ALTER TABLE Kern.Betr ADD CONSTRAINT FK3860 FOREIGN KEY (Relatie) REFERENCES Kern.Relatie (ID);
ALTER TABLE Kern.Betr ADD CONSTRAINT FK3861 FOREIGN KEY (Rol) REFERENCES Kern.SrtBetr (ID);
ALTER TABLE Kern.Betr ADD CONSTRAINT FK3859 FOREIGN KEY (Betrokkene) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.Bron ADD CONSTRAINT FK3875 FOREIGN KEY (Actie) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.Bron ADD CONSTRAINT FK3876 FOREIGN KEY (Doc) REFERENCES Kern.Doc (ID);
ALTER TABLE Kern.Doc ADD CONSTRAINT FK3157 FOREIGN KEY (Srt) REFERENCES Kern.SrtDoc (ID);
ALTER TABLE Kern.Doc ADD CONSTRAINT FK3139 FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.Element ADD CONSTRAINT FK3721 FOREIGN KEY (Srt) REFERENCES Kern.SrtElement (ID);
ALTER TABLE Kern.GegevenInOnderzoek ADD CONSTRAINT FK3865 FOREIGN KEY (Onderzoek) REFERENCES Kern.Onderzoek (ID);
ALTER TABLE Kern.GegevenInOnderzoek ADD CONSTRAINT FK3866 FOREIGN KEY (SrtGegeven) REFERENCES Kern.Element (ID);
ALTER TABLE Kern.MultiRealiteitRegel ADD CONSTRAINT FK2050 FOREIGN KEY (GeldigVoor) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.MultiRealiteitRegel ADD CONSTRAINT FK2051 FOREIGN KEY (Srt) REFERENCES Kern.SrtMultiRealiteitRegel (ID);
ALTER TABLE Kern.MultiRealiteitRegel ADD CONSTRAINT FK2053 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.MultiRealiteitRegel ADD CONSTRAINT FK2054 FOREIGN KEY (MultiRealiteitPers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.MultiRealiteitRegel ADD CONSTRAINT FK2055 FOREIGN KEY (Relatie) REFERENCES Kern.Relatie (ID);
ALTER TABLE Kern.MultiRealiteitRegel ADD CONSTRAINT FK2056 FOREIGN KEY (Betr) REFERENCES Kern.Betr (ID);
ALTER TABLE Kern.Partij ADD CONSTRAINT FK2195 FOREIGN KEY (Srt) REFERENCES Kern.SrtPartij (ID);
ALTER TABLE Kern.Partij ADD CONSTRAINT FK2196 FOREIGN KEY (Sector) REFERENCES Kern.Sector (ID);
ALTER TABLE Kern.Partij ADD CONSTRAINT FK2005 FOREIGN KEY (VoortzettendeGem) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.Partij ADD CONSTRAINT FK4602 FOREIGN KEY (OnderdeelVan) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.PartijRol ADD CONSTRAINT FK2185 FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.PartijRol ADD CONSTRAINT FK2186 FOREIGN KEY (Rol) REFERENCES Kern.Rol (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK1997 FOREIGN KEY (Srt) REFERENCES Kern.SrtPers (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK3031 FOREIGN KEY (Geslachtsaand) REFERENCES Kern.Geslachtsaand (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK1969 FOREIGN KEY (Predikaat) REFERENCES Kern.Predikaat (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK1968 FOREIGN KEY (AdellijkeTitel) REFERENCES Kern.AdellijkeTitel (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK3593 FOREIGN KEY (GebrGeslnaamEGP) REFERENCES Kern.WijzeGebruikGeslnaam (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK3703 FOREIGN KEY (PredikaatAanschr) REFERENCES Kern.Predikaat (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK3675 FOREIGN KEY (GemGeboorte) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK3676 FOREIGN KEY (WplGeboorte) REFERENCES Kern.Plaats (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK3543 FOREIGN KEY (LandGeboorte) REFERENCES Kern.Land (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK3551 FOREIGN KEY (GemOverlijden) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK3544 FOREIGN KEY (WplOverlijden) REFERENCES Kern.Plaats (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK3558 FOREIGN KEY (LandOverlijden) REFERENCES Kern.Land (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK3310 FOREIGN KEY (Verblijfsr) REFERENCES Kern.Verblijfsr (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK3568 FOREIGN KEY (Verantwoordelijke) REFERENCES Kern.Verantwoordelijke (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK3663 FOREIGN KEY (RdnOpschortingBijhouding) REFERENCES Kern.RdnOpschorting (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK3573 FOREIGN KEY (Bijhgem) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK3233 FOREIGN KEY (GemPK) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK3579 FOREIGN KEY (LandVanwaarGevestigd) REFERENCES Kern.Land (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK3666 FOREIGN KEY (VorigePers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK3667 FOREIGN KEY (VolgendePers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.PersAdres ADD CONSTRAINT FK3241 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.PersAdres ADD CONSTRAINT FK3263 FOREIGN KEY (Srt) REFERENCES Kern.FunctieAdres (ID);
ALTER TABLE Kern.PersAdres ADD CONSTRAINT FK3715 FOREIGN KEY (RdnWijz) REFERENCES Kern.RdnWijzAdres (ID);
ALTER TABLE Kern.PersAdres ADD CONSTRAINT FK3301 FOREIGN KEY (AangAdresh) REFERENCES Kern.AangAdresh (ID);
ALTER TABLE Kern.PersAdres ADD CONSTRAINT FK3788 FOREIGN KEY (Gem) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.PersAdres ADD CONSTRAINT FK3282 FOREIGN KEY (Wpl) REFERENCES Kern.Plaats (ID);
ALTER TABLE Kern.PersAdres ADD CONSTRAINT FK3289 FOREIGN KEY (Land) REFERENCES Kern.Land (ID);
ALTER TABLE Kern.PersGeslnaamcomp ADD CONSTRAINT FK3024 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.PersGeslnaamcomp ADD CONSTRAINT FK3117 FOREIGN KEY (Predikaat) REFERENCES Kern.Predikaat (ID);
ALTER TABLE Kern.PersGeslnaamcomp ADD CONSTRAINT FK3118 FOREIGN KEY (AdellijkeTitel) REFERENCES Kern.AdellijkeTitel (ID);
ALTER TABLE Kern.PersIndicatie ADD CONSTRAINT FK3657 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.PersIndicatie ADD CONSTRAINT FK3658 FOREIGN KEY (Srt) REFERENCES Kern.SrtIndicatie (ID);
ALTER TABLE Kern.PersNation ADD CONSTRAINT FK3130 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.PersNation ADD CONSTRAINT FK3131 FOREIGN KEY (Nation) REFERENCES Kern.Nation (ID);
ALTER TABLE Kern.PersNation ADD CONSTRAINT FK3230 FOREIGN KEY (RdnVerlies) REFERENCES Kern.RdnVerliesNLNation (ID);
ALTER TABLE Kern.PersNation ADD CONSTRAINT FK3229 FOREIGN KEY (RdnVerk) REFERENCES Kern.RdnVerkNLNation (ID);
ALTER TABLE Kern.PersReisdoc ADD CONSTRAINT FK3752 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.PersReisdoc ADD CONSTRAINT FK3739 FOREIGN KEY (Srt) REFERENCES Kern.SrtNLReisdoc (ID);
ALTER TABLE Kern.PersReisdoc ADD CONSTRAINT FK3744 FOREIGN KEY (AutVanAfgifte) REFERENCES Kern.AutVanAfgifteReisdoc (ID);
ALTER TABLE Kern.PersReisdoc ADD CONSTRAINT FK3747 FOREIGN KEY (RdnVervallen) REFERENCES Kern.RdnOntbrReisdoc (ID);
ALTER TABLE Kern.PersVerificatie ADD CONSTRAINT FK2142 FOREIGN KEY (Geverifieerde) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.PersVerificatie ADD CONSTRAINT FK3779 FOREIGN KEY (Srt) REFERENCES Kern.SrtVerificatie (ID);
ALTER TABLE Kern.PersVoornaam ADD CONSTRAINT FK3023 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.Relatie ADD CONSTRAINT FK3198 FOREIGN KEY (Srt) REFERENCES Kern.SrtRelatie (ID);
ALTER TABLE Kern.Relatie ADD CONSTRAINT FK3755 FOREIGN KEY (GemAanv) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.Relatie ADD CONSTRAINT FK3756 FOREIGN KEY (WplAanv) REFERENCES Kern.Plaats (ID);
ALTER TABLE Kern.Relatie ADD CONSTRAINT FK3760 FOREIGN KEY (LandAanv) REFERENCES Kern.Land (ID);
ALTER TABLE Kern.Relatie ADD CONSTRAINT FK3207 FOREIGN KEY (RdnEinde) REFERENCES Kern.RdnBeeindRelatie (ID);
ALTER TABLE Kern.Relatie ADD CONSTRAINT FK3763 FOREIGN KEY (GemEinde) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.Relatie ADD CONSTRAINT FK3764 FOREIGN KEY (WplEinde) REFERENCES Kern.Plaats (ID);
ALTER TABLE Kern.Relatie ADD CONSTRAINT FK3768 FOREIGN KEY (LandEinde) REFERENCES Kern.Land (ID);
ALTER TABLE Lev.Lev ADD CONSTRAINT FK4820 FOREIGN KEY (Srt) REFERENCES Lev.SrtLev (ID);
ALTER TABLE Lev.Lev ADD CONSTRAINT FK4821 FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE Lev.LevBer ADD CONSTRAINT FK4835 FOREIGN KEY (Lev) REFERENCES Lev.Lev (ID);
ALTER TABLE Lev.LevBer ADD CONSTRAINT FK4836 FOREIGN KEY (Ber) REFERENCES Ber.Ber (ID);
ALTER TABLE Lev.LevPers ADD CONSTRAINT FK4842 FOREIGN KEY (Lev) REFERENCES Lev.Lev (ID);
ALTER TABLE Lev.LevPers ADD CONSTRAINT FK4843 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE AutAut.His_Authenticatiemiddel ADD CONSTRAINT FK4678 FOREIGN KEY (Authenticatiemiddel) REFERENCES AutAut.Authenticatiemiddel (ID);
ALTER TABLE AutAut.His_Authenticatiemiddel ADD CONSTRAINT FK4681 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE AutAut.His_Authenticatiemiddel ADD CONSTRAINT FK4682 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE AutAut.His_Authenticatiemiddel ADD CONSTRAINT FK4683 FOREIGN KEY (Functie) REFERENCES AutAut.Functie (ID);
ALTER TABLE AutAut.His_Authenticatiemiddel ADD CONSTRAINT FK4684 FOREIGN KEY (CertificaatTbvSSL) REFERENCES AutAut.Certificaat (ID);
ALTER TABLE AutAut.His_Authenticatiemiddel ADD CONSTRAINT FK4685 FOREIGN KEY (CertificaatTbvOndertekening) REFERENCES AutAut.Certificaat (ID);
ALTER TABLE Kern.His_Betr ADD CONSTRAINT FK4025 FOREIGN KEY (Betr) REFERENCES Kern.Betr (ID);
ALTER TABLE Kern.His_Betr ADD CONSTRAINT FK4028 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Betr ADD CONSTRAINT FK4029 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_BetrOuderlijkGezag ADD CONSTRAINT FK4033 FOREIGN KEY (Betr) REFERENCES Kern.Betr (ID);
ALTER TABLE Kern.His_BetrOuderlijkGezag ADD CONSTRAINT FK4038 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_BetrOuderlijkGezag ADD CONSTRAINT FK4039 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_BetrOuderlijkGezag ADD CONSTRAINT FK4040 FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Doc ADD CONSTRAINT FK4043 FOREIGN KEY (Doc) REFERENCES Kern.Doc (ID);
ALTER TABLE Kern.His_Doc ADD CONSTRAINT FK4046 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Doc ADD CONSTRAINT FK4047 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Doc ADD CONSTRAINT FK4051 FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.His_Gem ADD CONSTRAINT FK4603 FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.His_Gem ADD CONSTRAINT FK4608 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Gem ADD CONSTRAINT FK4609 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Gem ADD CONSTRAINT FK4611 FOREIGN KEY (VoortzettendeGem) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.His_Gem ADD CONSTRAINT FK4613 FOREIGN KEY (OnderdeelVan) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.His_MultiRealiteitRegel ADD CONSTRAINT FK4053 FOREIGN KEY (MultiRealiteitRegel) REFERENCES Kern.MultiRealiteitRegel (ID);
ALTER TABLE Kern.His_MultiRealiteitRegel ADD CONSTRAINT FK4058 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_MultiRealiteitRegel ADD CONSTRAINT FK4059 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_MultiRealiteitRegel ADD CONSTRAINT FK4060 FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_MultiRealiteitRegel ADD CONSTRAINT FK4061 FOREIGN KEY (GeldigVoor) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_MultiRealiteitRegel ADD CONSTRAINT FK4062 FOREIGN KEY (Srt) REFERENCES Kern.SrtMultiRealiteitRegel (ID);
ALTER TABLE Kern.His_MultiRealiteitRegel ADD CONSTRAINT FK4063 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_MultiRealiteitRegel ADD CONSTRAINT FK4064 FOREIGN KEY (MultiRealiteitPers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_MultiRealiteitRegel ADD CONSTRAINT FK4065 FOREIGN KEY (Relatie) REFERENCES Kern.Relatie (ID);
ALTER TABLE Kern.His_MultiRealiteitRegel ADD CONSTRAINT FK4066 FOREIGN KEY (Betr) REFERENCES Kern.Betr (ID);
ALTER TABLE Kern.His_Onderzoek ADD CONSTRAINT FK4068 FOREIGN KEY (Onderzoek) REFERENCES Kern.Onderzoek (ID);
ALTER TABLE Kern.His_Onderzoek ADD CONSTRAINT FK4071 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Onderzoek ADD CONSTRAINT FK4072 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Partij ADD CONSTRAINT FK4620 FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.His_Partij ADD CONSTRAINT FK4623 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Partij ADD CONSTRAINT FK4624 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Partij ADD CONSTRAINT FK4627 FOREIGN KEY (Sector) REFERENCES Kern.Sector (ID);
ALTER TABLE Kern.His_PartijRol ADD CONSTRAINT FK4783 FOREIGN KEY (PartijRol) REFERENCES Kern.PartijRol (ID);
ALTER TABLE Kern.His_PartijRol ADD CONSTRAINT FK4788 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PartijRol ADD CONSTRAINT FK4789 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersAanschr ADD CONSTRAINT FK4115 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_PersAanschr ADD CONSTRAINT FK4120 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersAanschr ADD CONSTRAINT FK4121 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersAanschr ADD CONSTRAINT FK4122 FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersAanschr ADD CONSTRAINT FK4123 FOREIGN KEY (GebrGeslnaamEGP) REFERENCES Kern.WijzeGebruikGeslnaam (ID);
ALTER TABLE Kern.His_PersAanschr ADD CONSTRAINT FK4126 FOREIGN KEY (PredikaatAanschr) REFERENCES Kern.Predikaat (ID);
ALTER TABLE Kern.His_PersBijhgem ADD CONSTRAINT FK4205 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_PersBijhgem ADD CONSTRAINT FK4210 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersBijhgem ADD CONSTRAINT FK4211 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersBijhgem ADD CONSTRAINT FK4212 FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersBijhgem ADD CONSTRAINT FK4213 FOREIGN KEY (Bijhgem) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.His_PersBijhverantwoordelijk ADD CONSTRAINT FK4188 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_PersBijhverantwoordelijk ADD CONSTRAINT FK4193 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersBijhverantwoordelijk ADD CONSTRAINT FK4194 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersBijhverantwoordelijk ADD CONSTRAINT FK4195 FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersBijhverantwoordelijk ADD CONSTRAINT FK4196 FOREIGN KEY (Verantwoordelijke) REFERENCES Kern.Verantwoordelijke (ID);
ALTER TABLE Kern.His_PersEUVerkiezingen ADD CONSTRAINT FK4179 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_PersEUVerkiezingen ADD CONSTRAINT FK4182 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersEUVerkiezingen ADD CONSTRAINT FK4183 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersGeboorte ADD CONSTRAINT FK4132 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_PersGeboorte ADD CONSTRAINT FK4135 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersGeboorte ADD CONSTRAINT FK4136 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersGeboorte ADD CONSTRAINT FK4138 FOREIGN KEY (GemGeboorte) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.His_PersGeboorte ADD CONSTRAINT FK4139 FOREIGN KEY (WplGeboorte) REFERENCES Kern.Plaats (ID);
ALTER TABLE Kern.His_PersGeboorte ADD CONSTRAINT FK4142 FOREIGN KEY (LandGeboorte) REFERENCES Kern.Land (ID);
ALTER TABLE Kern.His_PersGeslachtsaand ADD CONSTRAINT FK4088 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_PersGeslachtsaand ADD CONSTRAINT FK4093 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersGeslachtsaand ADD CONSTRAINT FK4094 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersGeslachtsaand ADD CONSTRAINT FK4095 FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersGeslachtsaand ADD CONSTRAINT FK4096 FOREIGN KEY (Geslachtsaand) REFERENCES Kern.Geslachtsaand (ID);
ALTER TABLE Kern.His_PersIDs ADD CONSTRAINT FK4077 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_PersIDs ADD CONSTRAINT FK4082 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersIDs ADD CONSTRAINT FK4083 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersIDs ADD CONSTRAINT FK4084 FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersImmigratie ADD CONSTRAINT FK4225 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_PersImmigratie ADD CONSTRAINT FK4230 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersImmigratie ADD CONSTRAINT FK4231 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersImmigratie ADD CONSTRAINT FK4232 FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersImmigratie ADD CONSTRAINT FK4233 FOREIGN KEY (LandVanwaarGevestigd) REFERENCES Kern.Land (ID);
ALTER TABLE Kern.His_PersInschr ADD CONSTRAINT FK4236 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_PersInschr ADD CONSTRAINT FK4241 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersInschr ADD CONSTRAINT FK4242 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersInschr ADD CONSTRAINT FK4243 FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersInschr ADD CONSTRAINT FK4246 FOREIGN KEY (VorigePers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_PersInschr ADD CONSTRAINT FK4247 FOREIGN KEY (VolgendePers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_PersOpschorting ADD CONSTRAINT FK4198 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_PersOpschorting ADD CONSTRAINT FK4201 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersOpschorting ADD CONSTRAINT FK4202 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersOpschorting ADD CONSTRAINT FK4203 FOREIGN KEY (RdnOpschortingBijhouding) REFERENCES Kern.RdnOpschorting (ID);
ALTER TABLE Kern.His_PersOverlijden ADD CONSTRAINT FK4145 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_PersOverlijden ADD CONSTRAINT FK4148 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersOverlijden ADD CONSTRAINT FK4149 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersOverlijden ADD CONSTRAINT FK4151 FOREIGN KEY (GemOverlijden) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.His_PersOverlijden ADD CONSTRAINT FK4152 FOREIGN KEY (WplOverlijden) REFERENCES Kern.Plaats (ID);
ALTER TABLE Kern.His_PersOverlijden ADD CONSTRAINT FK4155 FOREIGN KEY (LandOverlijden) REFERENCES Kern.Land (ID);
ALTER TABLE Kern.His_PersPK ADD CONSTRAINT FK4217 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_PersPK ADD CONSTRAINT FK4220 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersPK ADD CONSTRAINT FK4221 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersPK ADD CONSTRAINT FK4222 FOREIGN KEY (GemPK) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.His_PersSamengesteldeNaam ADD CONSTRAINT FK4098 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_PersSamengesteldeNaam ADD CONSTRAINT FK4103 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersSamengesteldeNaam ADD CONSTRAINT FK4104 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersSamengesteldeNaam ADD CONSTRAINT FK4105 FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersSamengesteldeNaam ADD CONSTRAINT FK4106 FOREIGN KEY (Predikaat) REFERENCES Kern.Predikaat (ID);
ALTER TABLE Kern.His_PersSamengesteldeNaam ADD CONSTRAINT FK4110 FOREIGN KEY (AdellijkeTitel) REFERENCES Kern.AdellijkeTitel (ID);
ALTER TABLE Kern.His_PersUitslNLKiesr ADD CONSTRAINT FK4171 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_PersUitslNLKiesr ADD CONSTRAINT FK4174 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersUitslNLKiesr ADD CONSTRAINT FK4175 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersVerblijfsr ADD CONSTRAINT FK4158 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_PersVerblijfsr ADD CONSTRAINT FK4163 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersVerblijfsr ADD CONSTRAINT FK4164 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersVerblijfsr ADD CONSTRAINT FK4165 FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersVerblijfsr ADD CONSTRAINT FK4166 FOREIGN KEY (Verblijfsr) REFERENCES Kern.Verblijfsr (ID);
ALTER TABLE Kern.His_PersAdresAdresh ADD CONSTRAINT FK4249 FOREIGN KEY (PersAdres) REFERENCES Kern.PersAdres (ID);
ALTER TABLE Kern.His_PersAdresAdresh ADD CONSTRAINT FK4254 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersAdresAdresh ADD CONSTRAINT FK4255 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersAdresAdresh ADD CONSTRAINT FK4256 FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersAdresAdresh ADD CONSTRAINT FK4257 FOREIGN KEY (Srt) REFERENCES Kern.FunctieAdres (ID);
ALTER TABLE Kern.His_PersAdresAdresh ADD CONSTRAINT FK4258 FOREIGN KEY (RdnWijz) REFERENCES Kern.RdnWijzAdres (ID);
ALTER TABLE Kern.His_PersAdresAdresh ADD CONSTRAINT FK4259 FOREIGN KEY (AangAdresh) REFERENCES Kern.AangAdresh (ID);
ALTER TABLE Kern.His_PersAdresBLAdres ADD CONSTRAINT FK4284 FOREIGN KEY (PersAdres) REFERENCES Kern.PersAdres (ID);
ALTER TABLE Kern.His_PersAdresBLAdres ADD CONSTRAINT FK4289 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersAdresBLAdres ADD CONSTRAINT FK4290 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersAdresBLAdres ADD CONSTRAINT FK4291 FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersAdresBLAdres ADD CONSTRAINT FK4298 FOREIGN KEY (Land) REFERENCES Kern.Land (ID);
ALTER TABLE Kern.His_PersAdresNLAdres ADD CONSTRAINT FK4262 FOREIGN KEY (PersAdres) REFERENCES Kern.PersAdres (ID);
ALTER TABLE Kern.His_PersAdresNLAdres ADD CONSTRAINT FK4267 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersAdresNLAdres ADD CONSTRAINT FK4268 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersAdresNLAdres ADD CONSTRAINT FK4269 FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersAdresNLAdres ADD CONSTRAINT FK4272 FOREIGN KEY (Gem) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.His_PersAdresNLAdres ADD CONSTRAINT FK4280 FOREIGN KEY (Wpl) REFERENCES Kern.Plaats (ID);
ALTER TABLE Kern.His_PersGeslnaamcomp ADD CONSTRAINT FK4301 FOREIGN KEY (PersGeslnaamcomp) REFERENCES Kern.PersGeslnaamcomp (ID);
ALTER TABLE Kern.His_PersGeslnaamcomp ADD CONSTRAINT FK4306 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersGeslnaamcomp ADD CONSTRAINT FK4307 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersGeslnaamcomp ADD CONSTRAINT FK4308 FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersGeslnaamcomp ADD CONSTRAINT FK4312 FOREIGN KEY (Predikaat) REFERENCES Kern.Predikaat (ID);
ALTER TABLE Kern.His_PersGeslnaamcomp ADD CONSTRAINT FK4313 FOREIGN KEY (AdellijkeTitel) REFERENCES Kern.AdellijkeTitel (ID);
ALTER TABLE Kern.His_PersIndicatie ADD CONSTRAINT FK4315 FOREIGN KEY (PersIndicatie) REFERENCES Kern.PersIndicatie (ID);
ALTER TABLE Kern.His_PersIndicatie ADD CONSTRAINT FK4320 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersIndicatie ADD CONSTRAINT FK4321 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersIndicatie ADD CONSTRAINT FK4322 FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersNation ADD CONSTRAINT FK4325 FOREIGN KEY (PersNation) REFERENCES Kern.PersNation (ID);
ALTER TABLE Kern.His_PersNation ADD CONSTRAINT FK4330 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersNation ADD CONSTRAINT FK4331 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersNation ADD CONSTRAINT FK4332 FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersNation ADD CONSTRAINT FK4333 FOREIGN KEY (RdnVerlies) REFERENCES Kern.RdnVerliesNLNation (ID);
ALTER TABLE Kern.His_PersNation ADD CONSTRAINT FK4334 FOREIGN KEY (RdnVerk) REFERENCES Kern.RdnVerkNLNation (ID);
ALTER TABLE Kern.His_PersReisdoc ADD CONSTRAINT FK4336 FOREIGN KEY (PersReisdoc) REFERENCES Kern.PersReisdoc (ID);
ALTER TABLE Kern.His_PersReisdoc ADD CONSTRAINT FK4341 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersReisdoc ADD CONSTRAINT FK4342 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersReisdoc ADD CONSTRAINT FK4343 FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersReisdoc ADD CONSTRAINT FK4346 FOREIGN KEY (AutVanAfgifte) REFERENCES Kern.AutVanAfgifteReisdoc (ID);
ALTER TABLE Kern.His_PersReisdoc ADD CONSTRAINT FK4349 FOREIGN KEY (RdnVervallen) REFERENCES Kern.RdnOntbrReisdoc (ID);
ALTER TABLE Kern.His_PersVerificatie ADD CONSTRAINT FK4352 FOREIGN KEY (PersVerificatie) REFERENCES Kern.PersVerificatie (ID);
ALTER TABLE Kern.His_PersVerificatie ADD CONSTRAINT FK4355 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersVerificatie ADD CONSTRAINT FK4356 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersVoornaam ADD CONSTRAINT FK4359 FOREIGN KEY (PersVoornaam) REFERENCES Kern.PersVoornaam (ID);
ALTER TABLE Kern.His_PersVoornaam ADD CONSTRAINT FK4364 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersVoornaam ADD CONSTRAINT FK4365 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersVoornaam ADD CONSTRAINT FK4366 FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Relatie ADD CONSTRAINT FK4369 FOREIGN KEY (Relatie) REFERENCES Kern.Relatie (ID);
ALTER TABLE Kern.His_Relatie ADD CONSTRAINT FK4372 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Relatie ADD CONSTRAINT FK4373 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Relatie ADD CONSTRAINT FK4375 FOREIGN KEY (GemAanv) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.His_Relatie ADD CONSTRAINT FK4376 FOREIGN KEY (WplAanv) REFERENCES Kern.Plaats (ID);
ALTER TABLE Kern.His_Relatie ADD CONSTRAINT FK4379 FOREIGN KEY (LandAanv) REFERENCES Kern.Land (ID);
ALTER TABLE Kern.His_Relatie ADD CONSTRAINT FK4381 FOREIGN KEY (RdnEinde) REFERENCES Kern.RdnBeeindRelatie (ID);
ALTER TABLE Kern.His_Relatie ADD CONSTRAINT FK4383 FOREIGN KEY (GemEinde) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.His_Relatie ADD CONSTRAINT FK4384 FOREIGN KEY (WplEinde) REFERENCES Kern.Plaats (ID);
ALTER TABLE Kern.His_Relatie ADD CONSTRAINT FK4387 FOREIGN KEY (LandEinde) REFERENCES Kern.Land (ID);


-- ------------------------------------------------------------------------
-- Begin van triggercode---------------------------------------------------
-- ------------------------------------------------------------------------
-- Gegenereerd door BRP Meta Register.
-- Gegenereerd op: Wednesday 12 Oct 2011 11:19:29
-- ------------------------------------------------------------------------
-- ------------------------------------------------------------------------
-- ------------------------------------------------------------------------
-- ------------------------------------------------------------------------
