-- Bij het gebruik van de in-memory DB wordt het schema gegenereerd vanuit de JPA-metadata.
-- Hierbij is het helaas (nog) niet mogelijk ook automatisch het schema te laten aanmaken, vandaar dat we dat via dit script regelen.
CREATE SCHEMA KERN;
-- Om de compatibiliteit met PostgreSQL te vergroten zetten we de volgende HSQLDB-configuratie-opties aan:  
-- (zie http://hsqldb.org/doc/2.0/guide/guide.pdf)
SET DATABASE TRANSACTION CONTROL MVCC;
SET DATABASE SQL SYNTAX PGS TRUE;
SET DATABASE REFERENTIAL INTEGRITY TRUE;

-- Hieronder worden alle tabellen aangemaakt:


CREATE TABLE Kern.AangAdresh (
   ID                            Smallint                      NOT NULL    /* AangAdreshID */,
   Code                          Varchar(1)                    NOT NULL    /* AangAdreshCode */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL    /* OmsEnumeratiewaarde */,
   CONSTRAINT BR4438 PRIMARY KEY (ID),
   CONSTRAINT BR4439 UNIQUE (Naam),
   CONSTRAINT BR4440 UNIQUE (Code)
);
CREATE TABLE Kern.AdellijkeTitel (
   ID                            Smallint                      NOT NULL    /* AdellijkeTitelID */,
   Code                          Varchar(1)                    NOT NULL    /* AdellijkeTitelCode */,
   NaamMannelijk                 Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   NaamVrouwelijk                Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   CONSTRAINT BR4410 PRIMARY KEY (ID),
   CONSTRAINT BR4413 UNIQUE (NaamVrouwelijk),
   CONSTRAINT BR4411 UNIQUE (NaamMannelijk),
   CONSTRAINT BR4412 UNIQUE (Code)
);
CREATE TABLE Kern.AutVanAfgifteReisdoc (
   ID                            Integer                       NOT NULL    /* AutVanAfgifteReisdocID */,
   Code                          Varchar(6)                    NOT NULL    /* AutVanAfgifteReisdocCode */,
   Oms                           Varchar(250)                  NOT NULL    /* OmsEnumeratiewaarde */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   CONSTRAINT BR4466 PRIMARY KEY (ID),
   CONSTRAINT BR4467 UNIQUE (Code),
   CONSTRAINT BR4468 UNIQUE (Oms)
);
CREATE TABLE Kern.CategoriePersonen (
   ID                            Smallint                      NOT NULL    /* CategoriePersonenID */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL    /* OmsEnumeratiewaarde */,
   CONSTRAINT BR6169 PRIMARY KEY (ID),
   CONSTRAINT BR6170 UNIQUE (Naam)
);
CREATE TABLE Kern.CategorieSrtActie (
   ID                            Smallint                      NOT NULL    /* CategorieSrtActieID */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL    /* OmsEnumeratiewaarde */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   CONSTRAINT BR5981 PRIMARY KEY (ID),
   CONSTRAINT BR5982 UNIQUE (Naam),
   CONSTRAINT BR5983 UNIQUE (Oms)
);
CREATE TABLE Kern.CategorieSrtDoc (
   ID                            Smallint                      NOT NULL    /* CategorieSrtDocID */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL    /* OmsEnumeratiewaarde */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   CONSTRAINT BR5984 PRIMARY KEY (ID),
   CONSTRAINT BR5985 UNIQUE (Naam),
   CONSTRAINT BR5986 UNIQUE (Oms)
);
CREATE TABLE Kern.DbObject (
   ID                            Integer                       NOT NULL    /* DbObjectID */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   Srt                           Smallint                      NOT NULL    /* SrtDbObjectID */,
   Ouder                         Integer                                   /* DbObjectID */,
   JavaIdentifier                Varchar(80)                   NOT NULL    /* IdentifierLang */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   CONSTRAINT BR5659 PRIMARY KEY (ID)
);
CREATE TABLE Kern.Element (
   ID                            Integer                       NOT NULL    /* ElementID */,
   Naam                          Varchar(80)                   NOT NULL    /* LangeNaamEnumeratiewaarde */,
   Srt                           Smallint                      NOT NULL    /* SrtElementID */,
   Ouder                         Integer                                   /* ElementID */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   CONSTRAINT BR4422 PRIMARY KEY (ID),
   CONSTRAINT BR4423 UNIQUE (Srt, Naam, Ouder)
);
CREATE TABLE Kern.FunctieAdres (
   ID                            Smallint                      NOT NULL    /* SrtAdresID */,
   Code                          Varchar(1)                    NOT NULL    /* SrtAdresCode */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   CONSTRAINT BR4435 PRIMARY KEY (ID),
   CONSTRAINT BR4436 UNIQUE (Naam),
   CONSTRAINT BR4437 UNIQUE (Code)
);
CREATE TABLE Kern.Geslachtsaand (
   ID                            Smallint                      NOT NULL    /* GeslachtsaandID */,
   Code                          Varchar(1)                    NOT NULL    /* GeslachtsaandCode */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                              /* OmsEnumeratiewaarde */,
   CONSTRAINT BR4482 PRIMARY KEY (ID),
   CONSTRAINT BR4483 UNIQUE (Code),
   CONSTRAINT BR4484 UNIQUE (Naam)
);
CREATE SEQUENCE Kern.seq_His_Partij;
CREATE TABLE Kern.His_Partij (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_Partij')  /* His_PartijID */,
   Partij                        Smallint                                  /* PartijID */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   DatEinde                      Integer                                   /* Dat */,
   DatAanv                       Integer                       NOT NULL    /* Dat */,
   Sector                        Smallint                                  /* SectorID */,
   CONSTRAINT BR4631 PRIMARY KEY (ID),
   CONSTRAINT BR4628 UNIQUE (Partij, TsReg)
);
CREATE SEQUENCE Kern.seq_His_PartijGem;
CREATE TABLE Kern.His_PartijGem (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PartijGem')  /* His_PartijGemID */,
   Partij                        Smallint                                  /* PartijID */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   VoortzettendeGem              Smallint                                  /* PartijID */,
   OnderdeelVan                  Smallint                                  /* PartijID */,
   CONSTRAINT BR6125 PRIMARY KEY (ID),
   CONSTRAINT BR6122 UNIQUE (Partij, TsReg)
);
CREATE TABLE Kern.Land (
   ID                            Integer                       NOT NULL    /* LandID */,
   Code                          Smallint                      NOT NULL    /* Landcode */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   ISO31661Alpha2                Varchar(2)                                /* ISO31661Alpha2 */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   CONSTRAINT BR4399 PRIMARY KEY (ID),
   CONSTRAINT BR4400 UNIQUE (Naam)
);
CREATE TABLE Kern.Nation (
   ID                            Integer                       NOT NULL    /* NationID */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   Nationcode                    Smallint                      NOT NULL    /* Nationcode */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   CONSTRAINT BR4404 PRIMARY KEY (ID),
   CONSTRAINT BR4405 UNIQUE (Naam)
);
CREATE SEQUENCE Kern.seq_Partij;
CREATE TABLE Kern.Partij (
   ID                            Smallint                      NOT NULL  DEFAULT nextval('Kern.seq_Partij')  /* PartijID */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   Srt                           Smallint                                  /* SrtPartijID */,
   Code                          Smallint                      NOT NULL    /* Gemcode */,
   DatEinde                      Integer                                   /* Dat */,
   DatAanv                       Integer                                   /* Dat */,
   Sector                        Smallint                                  /* SectorID */,
   PartijStatusHis               Varchar(1)                    NOT NULL    /* StatusHistorie */,
   VoortzettendeGem              Smallint                                  /* PartijID */,
   OnderdeelVan                  Smallint                                  /* PartijID */,
   GemStatusHis                  Varchar(1)                    NOT NULL    /* StatusHistorie */,
   CONSTRAINT BR4417 PRIMARY KEY (ID),
   CONSTRAINT BR4418 UNIQUE (Naam)
);
CREATE SEQUENCE Kern.seq_PartijRol;
CREATE TABLE Kern.PartijRol (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_PartijRol')  /* PartijRolID */,
   Partij                        Smallint                      NOT NULL    /* PartijID */,
   Rol                           Smallint                      NOT NULL    /* RolID */,
   PartijRolStatusHis            Varchar(1)                    NOT NULL    /* StatusHistorie */,
   CONSTRAINT BR4495 PRIMARY KEY (ID),
   CONSTRAINT BR4688 UNIQUE (Partij, Rol)
);
CREATE TABLE Kern.Plaats (
   ID                            Integer                       NOT NULL    /* PlaatsID */,
   Code                          Smallint                      NOT NULL    /* Wplcode */,
   Naam                          Varchar(80)                   NOT NULL    /* LangeNaamEnumeratiewaarde */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   CONSTRAINT BR4397 PRIMARY KEY (ID),
   CONSTRAINT BR4398 UNIQUE (Code)
);
CREATE TABLE Kern.Predikaat (
   ID                            Smallint                      NOT NULL    /* PredikaatID */,
   Code                          Varchar(1)                    NOT NULL    /* PredikaatCode */,
   NaamMannelijk                 Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   NaamVrouwelijk                Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   CONSTRAINT BR4406 PRIMARY KEY (ID),
   CONSTRAINT BR4407 UNIQUE (Code),
   CONSTRAINT BR4408 UNIQUE (NaamMannelijk),
   CONSTRAINT BR4409 UNIQUE (NaamVrouwelijk)
);
CREATE TABLE Kern.RdnBeeindRelatie (
   ID                            Smallint                      NOT NULL    /* RdnBeeindRelatieID */,
   Code                          Varchar(1)                    NOT NULL    /* RdnBeeindRelatieCode */,
   Oms                           Varchar(250)                  NOT NULL    /* OmsEnumeratiewaarde */,
   CONSTRAINT BR4428 PRIMARY KEY (ID),
   CONSTRAINT BR4429 UNIQUE (Oms)
);
CREATE TABLE Kern.RdnOpschorting (
   ID                            Smallint                      NOT NULL    /* RdnOpschortingID */,
   Code                          Varchar(1)                    NOT NULL    /* RdnOpschortingCode */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   CONSTRAINT BR4449 PRIMARY KEY (ID),
   CONSTRAINT BR4450 UNIQUE (Code),
   CONSTRAINT BR4451 UNIQUE (Naam)
);
CREATE TABLE Kern.RdnVerkNLNation (
   ID                            Smallint                      NOT NULL    /* RdnVerkNLNationID */,
   Code                          Smallint                      NOT NULL    /* RdnVerkCode */,
   Oms                           Varchar(250)                  NOT NULL    /* OmsEnumeratiewaarde */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   CONSTRAINT BR4430 PRIMARY KEY (ID),
   CONSTRAINT BR4431 UNIQUE (Oms),
   CONSTRAINT BR6264 UNIQUE (Code)
);
CREATE TABLE Kern.RdnVerliesNLNation (
   ID                            Smallint                      NOT NULL    /* RdnVerliesNLNationID */,
   Code                          Smallint                      NOT NULL    /* RdnVerliesCode */,
   Oms                           Varchar(250)                  NOT NULL    /* OmsEnumeratiewaarde */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   CONSTRAINT BR4432 PRIMARY KEY (ID),
   CONSTRAINT BR4433 UNIQUE (Code)
);
CREATE TABLE Kern.RdnVervallenReisdoc (
   ID                            Smallint                      NOT NULL    /* RdnOntbrReisdocID */,
   Code                          Varchar(1)                    NOT NULL    /* RdnOntbrReisdocCode */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   CONSTRAINT BR4469 PRIMARY KEY (ID),
   CONSTRAINT BR4470 UNIQUE (Code),
   CONSTRAINT BR4471 UNIQUE (Naam)
);
CREATE TABLE Kern.RdnWijzAdres (
   ID                            Smallint                      NOT NULL    /* RdnWijzAdresID */,
   Code                          Varchar(1)                    NOT NULL    /* RdnWijzAdresCode */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   CONSTRAINT BR4460 PRIMARY KEY (ID),
   CONSTRAINT BR4461 UNIQUE (Code),
   CONSTRAINT BR4462 UNIQUE (Naam)
);
CREATE TABLE Kern.Regel (
   ID                            Integer                       NOT NULL    /* RegelID */,
   Srt                           Smallint                      NOT NULL    /* SrtRegelID */,
   Code                          Varchar(40)                   NOT NULL    /* RegelCode */,
   Oms                           Varchar(250)                  NOT NULL    /* OmsEnumeratiewaarde */,
   Specificatie                  Text                          NOT NULL    /* Regelspecificatie */,
   CONSTRAINT BR5811 PRIMARY KEY (ID),
   CONSTRAINT BR6050 UNIQUE (Code),
   CONSTRAINT BR6051 UNIQUE (Specificatie),
   CONSTRAINT BR6052 UNIQUE (Oms)
);
CREATE TABLE Kern.Rol (
   ID                            Smallint                      NOT NULL    /* RolID */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   CONSTRAINT BR4491 PRIMARY KEY (ID),
   CONSTRAINT BR4492 UNIQUE (Naam)
);
CREATE SEQUENCE Kern.seq_Sector;
CREATE TABLE Kern.Sector (
   ID                            Smallint                      NOT NULL  DEFAULT nextval('Kern.seq_Sector')  /* SectorID */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   CONSTRAINT BR4494 PRIMARY KEY (ID)
);
CREATE TABLE Kern.SrtDbObject (
   ID                            Smallint                      NOT NULL    /* SrtDbObjectID */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   CONSTRAINT BR5660 PRIMARY KEY (ID)
);
CREATE TABLE Kern.SrtNLReisdoc (
   ID                            Smallint                      NOT NULL    /* SrtNLReisdocID */,
   Code                          Varchar(2)                    NOT NULL    /* SrtNLReisdocCode */,
   Oms                           Varchar(250)                  NOT NULL    /* OmsEnumeratiewaarde */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   CONSTRAINT BR4463 PRIMARY KEY (ID),
   CONSTRAINT BR4464 UNIQUE (Code),
   CONSTRAINT BR4465 UNIQUE (Oms)
);
CREATE TABLE Kern.SrtActie (
   ID                            Smallint                      NOT NULL    /* SrtActieID */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   CategorieSrtActie             Smallint                      NOT NULL    /* CategorieSrtActieID */,
   CONSTRAINT BR4401 PRIMARY KEY (ID),
   CONSTRAINT BR4402 UNIQUE (Naam)
);
CREATE TABLE Kern.SrtBetr (
   ID                            Smallint                      NOT NULL    /* SrtBetrID */,
   Code                          Varchar(1)                    NOT NULL    /* SrtBetrCode */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   CONSTRAINT BR4476 PRIMARY KEY (ID),
   CONSTRAINT BR4477 UNIQUE (Code),
   CONSTRAINT BR4478 UNIQUE (Naam)
);
CREATE TABLE Kern.SrtDoc (
   ID                            Smallint                      NOT NULL    /* SrtDocID */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   CategorieSrtDoc               Smallint                      NOT NULL    /* CategorieSrtDocID */,
   CONSTRAINT BR4419 PRIMARY KEY (ID),
   CONSTRAINT BR4420 UNIQUE (Naam)
);
CREATE TABLE Kern.SrtElement (
   ID                            Smallint                      NOT NULL    /* SrtElementID */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   CONSTRAINT BR4457 PRIMARY KEY (ID),
   CONSTRAINT BR4458 UNIQUE (Naam)
);
CREATE TABLE Kern.SrtIndicatie (
   ID                            Smallint                      NOT NULL    /* SrtIndicatieID */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   IndMaterieleHistorieVanToepa  boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4447 PRIMARY KEY (ID),
   CONSTRAINT BR4448 UNIQUE (Naam)
);
CREATE TABLE Kern.SrtMultiRealiteitRegel (
   ID                            Smallint                      NOT NULL    /* SrtMultiRealiteitRegelID */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                  NOT NULL    /* OmsEnumeratiewaarde */,
   CONSTRAINT BR4489 PRIMARY KEY (ID),
   CONSTRAINT BR4490 UNIQUE (Naam)
);
CREATE TABLE Kern.SrtPartij (
   ID                            Smallint                      NOT NULL    /* SrtPartijID */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   CONSTRAINT BR4493 PRIMARY KEY (ID)
);
CREATE TABLE Kern.SrtPers (
   ID                            Smallint                      NOT NULL    /* SrtPersID */,
   Code                          Varchar(1)                    NOT NULL    /* SrtPersCode */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                              /* OmsEnumeratiewaarde */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   CONSTRAINT BR4485 PRIMARY KEY (ID),
   CONSTRAINT BR4486 UNIQUE (Code),
   CONSTRAINT BR4487 UNIQUE (Naam)
);
CREATE TABLE Kern.SrtRelatie (
   ID                            Smallint                      NOT NULL    /* SrtRelatieID */,
   Code                          Varchar(1)                    NOT NULL    /* SrtRelatieCode */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                              /* OmsEnumeratiewaarde */,
   CONSTRAINT BR4425 PRIMARY KEY (ID),
   CONSTRAINT BR4426 UNIQUE (Naam),
   CONSTRAINT BR4427 UNIQUE (Code)
);
CREATE TABLE Kern.SrtVerificatie (
   ID                            Smallint                      NOT NULL    /* SrtVerificatieID */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   CONSTRAINT BR4472 PRIMARY KEY (ID),
   CONSTRAINT BR4473 UNIQUE (Naam)
);
CREATE TABLE Kern.Verantwoordelijke (
   ID                            Smallint                      NOT NULL    /* VerantwoordelijkeID */,
   Code                          Varchar(1)                    NOT NULL    /* VerantwoordelijkeCode */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   CONSTRAINT BR4443 PRIMARY KEY (ID),
   CONSTRAINT BR4444 UNIQUE (Naam),
   CONSTRAINT BR4445 UNIQUE (Code)
);
CREATE TABLE Kern.Verblijfsr (
   ID                            Smallint                      NOT NULL    /* VerblijfsrID */,
   Oms                           Varchar(250)                  NOT NULL    /* OmsEnumeratiewaarde */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   CONSTRAINT BR4441 PRIMARY KEY (ID),
   CONSTRAINT BR4442 UNIQUE (Oms)
);
CREATE TABLE Kern.Verdrag (
   ID                            Integer                       NOT NULL    /* VerdragID */,
   Oms                           Varchar(250)                  NOT NULL    /* OmsEnumeratiewaarde */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   CONSTRAINT BR4474 PRIMARY KEY (ID),
   CONSTRAINT BR4475 UNIQUE (Oms)
);
CREATE TABLE Kern.WijzeGebruikGeslnaam (
   ID                            Smallint                      NOT NULL    /* WijzeGebruikGeslnaamID */,
   Code                          Varchar(1)                    NOT NULL    /* WijzeGebruikGeslnaamCode */,
   Naam                          Varchar(80)                   NOT NULL    /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)                              /* OmsEnumeratiewaarde */,
   CONSTRAINT BR4452 PRIMARY KEY (ID),
   CONSTRAINT BR4453 UNIQUE (Code),
   CONSTRAINT BR4454 UNIQUE (Naam)
);

CREATE SEQUENCE Kern.seq_Actie;
CREATE TABLE Kern.Actie (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_Actie')  /* ActieID */,
   Srt                           Smallint                      NOT NULL    /* SrtActieID */,
   Partij                        Smallint                      NOT NULL    /* PartijID */,
   Verdrag                       Integer                                   /* VerdragID */,
/*   DatAanvGel                    Integer                       NOT NULL */   /* Dat */ /*, */
/*   DatEindeGel                   Integer                                */   /* Dat */ /*, */
   TijdstipOntlening             Timestamp                                 /* DatTijd */,
   TijdstipReg                   Timestamp                     NOT NULL    /* DatTijd */,
   ToelichtingOntlening          Text                                      /* Ontleningstoelichting */,
   CONSTRAINT BR4403 PRIMARY KEY (ID)
);
CREATE SEQUENCE Kern.seq_Betr;
CREATE TABLE Kern.Betr (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_Betr')  /* BetrID */,
   Relatie                       Integer                       NOT NULL    /* RelatieID */,
   Rol                           Smallint                      NOT NULL    /* SrtBetrID */,
   Pers                          Integer                                   /* PersID */,
   DatAanv                       Integer                                   /* Dat */,
   IndOuder                      boolean                                   /* Ja */,
   IndAdresgevend                boolean                                   /* Ja */,
   OuderschapStatusHis           Varchar(1)                    NOT NULL    /* StatusHistorie */,
   IndOuderHeeftGezag            boolean                                   /* JaNee */,
   OuderlijkGezagStatusHis       Varchar(1)                    NOT NULL    /* StatusHistorie */,
   CONSTRAINT BR6104 PRIMARY KEY (ID),
   CONSTRAINT BR6105 UNIQUE (Relatie, Pers)
);
CREATE SEQUENCE Kern.seq_Bron;
CREATE TABLE Kern.Bron (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_Bron')  /* BronID */,
   Actie                         Bigint                        NOT NULL    /* ActieID */,
   Doc                           Bigint                        NOT NULL    /* DocID */,
   CONSTRAINT BR4504 PRIMARY KEY (ID),
   CONSTRAINT BR4481 UNIQUE (Actie, Doc)
);
CREATE SEQUENCE Kern.seq_Doc;
CREATE TABLE Kern.Doc (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_Doc')  /* DocID */,
   Srt                           Smallint                      NOT NULL    /* SrtDocID */,
   Ident                         Varchar(20)                               /* DocIdent */,
   Aktenr                        Varchar(7)                                /* Aktenr */,
   Oms                           Varchar(80)                               /* DocOms */,
   Partij                        Smallint                                  /* PartijID */,
   DocStatusHis                  Varchar(1)                    NOT NULL    /* StatusHistorie */,
   CONSTRAINT BR4416 PRIMARY KEY (ID)
);
CREATE SEQUENCE Kern.seq_GegevenInOnderzoek;
CREATE TABLE Kern.GegevenInOnderzoek (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_GegevenInOnderzoek')  /* GegevenInOnderzoekID */,
   Onderzoek                     Integer                       NOT NULL    /* OnderzoekID */,
   SrtGegeven                    Integer                       NOT NULL    /* DbObjectID */,
   Ident                         Bigint                        NOT NULL    /* Sleutelwaarde */,
   CONSTRAINT BR4507 PRIMARY KEY (ID),
   CONSTRAINT BR4480 UNIQUE (Onderzoek, SrtGegeven)
);
CREATE SEQUENCE Kern.seq_MultiRealiteitRegel;
CREATE TABLE Kern.MultiRealiteitRegel (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_MultiRealiteitRegel')  /* MultiRealiteitregelID */,
   GeldigVoor                    Integer                       NOT NULL    /* PersID */,
   Srt                           Smallint                      NOT NULL    /* SrtMultiRealiteitRegelID */,
   Pers                          Integer                                   /* PersID */,
   MultiRealiteitPers            Integer                                   /* PersID */,
   Relatie                       Integer                                   /* RelatieID */,
   Betr                          Integer                                   /* BetrID */,
   MultiRealiteitRegelStatusHis  Varchar(1)                    NOT NULL    /* StatusHistorie */,
   CONSTRAINT BR4488 PRIMARY KEY (ID)
);
CREATE SEQUENCE Kern.seq_Onderzoek;
CREATE TABLE Kern.Onderzoek (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_Onderzoek')  /* OnderzoekID */,
   DatBegin                      Integer                                   /* Dat */,
   DatEinde                      Integer                                   /* Dat */,
   Oms                           Text                                      /* OnderzoekOms */,
   OnderzoekStatusHis            Varchar(1)                    NOT NULL    /* StatusHistorie */,
   CONSTRAINT BR4421 PRIMARY KEY (ID)
);
CREATE SEQUENCE Kern.seq_Pers;
CREATE TABLE Kern.Pers (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_Pers')  /* PersID */,
   Srt                           Smallint                      NOT NULL    /* SrtPersID */,
   BSN                           Integer                                   /* BSN */,
   ANr                           Bigint                                    /* ANr */,
   IDsStatusHis                  Varchar(1)                    NOT NULL    /* StatusHistorie */,
   Geslachtsaand                 Smallint                                  /* GeslachtsaandID */,
   GeslachtsaandStatusHis        Varchar(1)                    NOT NULL    /* StatusHistorie */,
   Predikaat                     Smallint                                  /* PredikaatID */,
   Voornamen                     Varchar(200)                              /* Voornamen */,
   Voorvoegsel                   Varchar(10)                               /* Voorvoegsel */,
   Scheidingsteken               Varchar(1)                                /* Scheidingsteken */,
   AdellijkeTitel                Smallint                                  /* AdellijkeTitelID */,
   Geslnaam                      Varchar(200)                              /* Geslnaam */,
   IndNreeksAlsGeslnaam          boolean                                   /* JaNee */,
   IndAlgoritmischAfgeleid       boolean                                   /* JaNee */,
   SamengesteldeNaamStatusHis    Varchar(1)                    NOT NULL    /* StatusHistorie */,
   GebrGeslnaamEGP               Smallint                                  /* WijzeGebruikGeslnaamID */,
   IndAanschrMetAdellijkeTitels  boolean                                   /* JaNee */,
   IndAanschrAlgoritmischAfgele  boolean                                   /* JaNee */,
   PredikaatAanschr              Smallint                                  /* PredikaatID */,
   VoornamenAanschr              Varchar(200)                              /* Voornamen */,
   VoorvoegselAanschr            Varchar(10)                               /* Voorvoegsel */,
   ScheidingstekenAanschr        Varchar(1)                                /* Scheidingsteken */,
   AdellijkeTitelAanschr         Smallint                                  /* AdellijkeTitelID */,
   GeslnaamAanschr               Varchar(200)                              /* Geslnaam */,
   AanschrStatusHis              Varchar(1)                    NOT NULL    /* StatusHistorie */,
   DatGeboorte                   Integer                                   /* Dat */,
   GemGeboorte                   Smallint                                  /* PartijID */,
   WplGeboorte                   Integer                                   /* PlaatsID */,
   BLGeboorteplaats              Varchar(40)                               /* BLPlaats */,
   BLRegioGeboorte               Varchar(35)                               /* BLRegio */,
   LandGeboorte                  Integer                                   /* LandID */,
   OmsGeboorteloc                Varchar(40)                               /* LocOms */,
   GeboorteStatusHis             Varchar(1)                    NOT NULL    /* StatusHistorie */,
   DatOverlijden                 Integer                                   /* Dat */,
   GemOverlijden                 Smallint                                  /* PartijID */,
   WplOverlijden                 Integer                                   /* PlaatsID */,
   BLPlaatsOverlijden            Varchar(40)                               /* BLPlaats */,
   BLRegioOverlijden             Varchar(35)                               /* BLRegio */,
   LandOverlijden                Integer                                   /* LandID */,
   OmsLocOverlijden              Varchar(40)                               /* LocOms */,
   OverlijdenStatusHis           Varchar(1)                    NOT NULL    /* StatusHistorie */,
   Verblijfsr                    Smallint                                  /* VerblijfsrID */,
   DatAanvVerblijfsr             Integer                                   /* Dat */,
   DatVoorzEindeVerblijfsr       Integer                                   /* Dat */,
   DatAanvAaneenslVerblijfsr     Integer                                   /* Dat */,
   VerblijfsrStatusHis           Varchar(1)                    NOT NULL    /* StatusHistorie */,
   IndUitslNLKiesr               boolean                                   /* Ja */,
   DatEindeUitslNLKiesr          Integer                                   /* Dat */,
   UitslNLKiesrStatusHis         Varchar(1)                    NOT NULL    /* StatusHistorie */,
   IndDeelnEUVerkiezingen        boolean                                   /* JaNee */,
   DatAanlAanpDeelnEUVerkiezing  Integer                                   /* Dat */,
   DatEindeUitslEUKiesr          Integer                                   /* Dat */,
   EUVerkiezingenStatusHis       Varchar(1)                    NOT NULL    /* StatusHistorie */,
   Verantwoordelijke             Smallint                                  /* VerantwoordelijkeID */,
   BijhverantwoordelijkheidStat  Varchar(1)                    NOT NULL    /* StatusHistorie */,
   RdnOpschortingBijhouding      Smallint                                  /* RdnOpschortingID */,
   OpschortingStatusHis          Varchar(1)                    NOT NULL    /* StatusHistorie */,
   Bijhgem                       Smallint                                  /* PartijID */,
   DatInschrInGem                Integer                                   /* Dat */,
   IndOnverwDocAanw              boolean                                   /* JaNee */,
   BijhgemStatusHis              Varchar(1)                    NOT NULL    /* StatusHistorie */,
   GemPK                         Smallint                                  /* PartijID */,
   IndPKVolledigGeconv           boolean                                   /* JaNee */,
   PKStatusHis                   Varchar(1)                    NOT NULL    /* StatusHistorie */,
   LandVanwaarGevestigd          Integer                                   /* LandID */,
   DatVestigingInNederland       Integer                                   /* Dat */,
   ImmigratieStatusHis           Varchar(1)                    NOT NULL    /* StatusHistorie */,
   DatInschr                     Integer                                   /* Dat */,
   Versienr                      Bigint                                    /* Versienr */,
   VorigePers                    Integer                                   /* PersID */,
   VolgendePers                  Integer                                   /* PersID */,
   InschrStatusHis               Varchar(1)                    NOT NULL    /* StatusHistorie */,
   TijdstipLaatsteWijz           Timestamp                                 /* DatTijd */,
   IndGegevensInOnderzoek        boolean                                   /* JaNee */,
   CONSTRAINT BR4390 PRIMARY KEY (ID)
);
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
   LoctovAdres                   Varchar(2)                                /* AandBijHuisnr */,
   LocOms                        Varchar(40)                               /* LocOms */,
   BLAdresRegel1                 Varchar(40)                               /* Adresregel */,
   BLAdresRegel2                 Varchar(40)                               /* Adresregel */,
   BLAdresRegel3                 Varchar(40)                               /* Adresregel */,
   BLAdresRegel4                 Varchar(40)                               /* Adresregel */,
   BLAdresRegel5                 Varchar(40)                               /* Adresregel */,
   BLAdresRegel6                 Varchar(40)                               /* Adresregel */,
   Land                          Integer                                   /* LandID */,
   DatVertrekUitNederland        Integer                                   /* Dat */,
   PersAdresStatusHis            Varchar(1)                    NOT NULL    /* StatusHistorie */,
   CONSTRAINT BR4434 PRIMARY KEY (ID)
);
CREATE SEQUENCE Kern.seq_PersGeslnaamcomp;
CREATE TABLE Kern.PersGeslnaamcomp (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_PersGeslnaamcomp')  /* PersGeslnaamID */,
   Pers                          Integer                       NOT NULL    /* PersID */,
   Volgnr                        Integer                       NOT NULL    /* Volgnr */,
   Voorvoegsel                   Varchar(10)                               /* Voorvoegsel */,
   Scheidingsteken               Varchar(1)                                /* Scheidingsteken */,
   Naam                          Varchar(200)                              /* Geslnaamcomp */,
   Predikaat                     Smallint                                  /* PredikaatID */,
   AdellijkeTitel                Smallint                                  /* AdellijkeTitelID */,
   PersGeslnaamcompStatusHis     Varchar(1)                    NOT NULL    /* StatusHistorie */,
   CONSTRAINT BR4391 PRIMARY KEY (ID),
   CONSTRAINT BR4392 UNIQUE (Pers, Volgnr)
);
CREATE SEQUENCE Kern.seq_PersIndicatie;
CREATE TABLE Kern.PersIndicatie (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_PersIndicatie')  /* PersIndicatieID */,
   Pers                          Integer                       NOT NULL    /* PersID */,
   Srt                           Smallint                      NOT NULL    /* SrtIndicatieID */,
   Waarde                        boolean                                   /* Ja */,
   PersIndicatieStatusHis        Varchar(1)                    NOT NULL    /* StatusHistorie */,
   CONSTRAINT BR4455 PRIMARY KEY (ID),
   CONSTRAINT BR4456 UNIQUE (Pers, Srt)
);
CREATE SEQUENCE Kern.seq_PersNation;
CREATE TABLE Kern.PersNation (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_PersNation')  /* PersNationID */,
   Pers                          Integer                       NOT NULL    /* PersID */,
   Nation                        Integer                       NOT NULL    /* NationID */,
   RdnVerlies                    Smallint                                  /* RdnVerliesNLNationID */,
   RdnVerk                       Smallint                                  /* RdnVerkNLNationID */,
   PersNationStatusHis           Varchar(1)                    NOT NULL    /* StatusHistorie */,
   CONSTRAINT BR4414 PRIMARY KEY (ID),
   CONSTRAINT BR4415 UNIQUE (Pers, Nation)
);
CREATE SEQUENCE Kern.seq_PersOnderzoek;
CREATE TABLE Kern.PersOnderzoek (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_PersOnderzoek')  /* PersOnderzoekID */,
   Pers                          Integer                       NOT NULL    /* PersID */,
   Onderzoek                     Integer                       NOT NULL    /* OnderzoekID */,
   CONSTRAINT BR6137 PRIMARY KEY (ID),
   CONSTRAINT BR6138 UNIQUE (Onderzoek, Pers)
);
CREATE SEQUENCE Kern.seq_PersReisdoc;
CREATE TABLE Kern.PersReisdoc (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_PersReisdoc')  /* ReisdocID */,
   Pers                          Integer                       NOT NULL    /* PersID */,
   Srt                           Smallint                      NOT NULL    /* SrtNLReisdocID */,
   Nr                            Varchar(9)                                /* ReisdocNr */,
   AutVanAfgifte                 Integer                                   /* AutVanAfgifteReisdocID */,
   DatIngangDoc                  Integer                                   /* Dat */,
   DatUitgifte                   Integer                                   /* Dat */,
   DatVoorzeEindeGel             Integer                                   /* Dat */,
   DatInhingVermissing           Integer                                   /* Dat */,
   RdnVervallen                  Smallint                                  /* RdnOntbrReisdocID */,
   LengteHouder                  Smallint                                  /* LengteInCm */,
   PersReisdocStatusHis          Varchar(1)                    NOT NULL    /* StatusHistorie */,
   CONSTRAINT BR4446 PRIMARY KEY (ID)
);
CREATE SEQUENCE Kern.seq_PersVerificatie;
CREATE TABLE Kern.PersVerificatie (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_PersVerificatie')  /* VerificatieID */,
   Geverifieerde                 Integer                       NOT NULL    /* PersID */,
   Srt                           Smallint                                  /* SrtVerificatieID */,
   Dat                           Integer                                   /* Dat */,
   PersVerificatieStatusHis      Varchar(1)                    NOT NULL    /* StatusHistorie */,
   CONSTRAINT BR4459 PRIMARY KEY (ID)
);
CREATE SEQUENCE Kern.seq_PersVoornaam;
CREATE TABLE Kern.PersVoornaam (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_PersVoornaam')  /* PersVoornaamID */,
   Pers                          Integer                       NOT NULL    /* PersID */,
   Volgnr                        Integer                       NOT NULL    /* Volgnr */,
   Naam                          Varchar(40)                               /* Voornaam */,
   PersVoornaamStatusHis         Varchar(1)                    NOT NULL    /* StatusHistorie */,
   CONSTRAINT BR4393 PRIMARY KEY (ID),
   CONSTRAINT BR4394 UNIQUE (Pers, Volgnr)
);
CREATE SEQUENCE Kern.seq_Regelverantwoording;
CREATE TABLE Kern.Regelverantwoording (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_Regelverantwoording')  /* ActieRegelimplementatieID */,
   Actie                         Bigint                        NOT NULL    /* ActieID */,
   Regel                         Integer                       NOT NULL    /* RegelID */,
   CONSTRAINT BR6158 PRIMARY KEY (ID)
);
CREATE SEQUENCE Kern.seq_Relatie;
CREATE TABLE Kern.Relatie (
   ID                            Integer                       NOT NULL  DEFAULT nextval('Kern.seq_Relatie')  /* RelatieID */,
   Srt                           Smallint                      NOT NULL    /* SrtRelatieID */,
   DatAanv                       Integer                                   /* Dat */,
   GemAanv                       Smallint                                  /* PartijID */,
   WplAanv                       Integer                                   /* PlaatsID */,
   BLPlaatsAanv                  Varchar(40)                               /* BLPlaats */,
   BLRegioAanv                   Varchar(35)                               /* BLRegio */,
   LandAanv                      Integer                                   /* LandID */,
   OmsLocAanv                    Varchar(40)                               /* LocOms */,
   RdnEinde                      Smallint                                  /* RdnBeeindRelatieID */,
   DatEinde                      Integer                                   /* Dat */,
   GemEinde                      Smallint                                  /* PartijID */,
   WplEinde                      Integer                                   /* PlaatsID */,
   BLPlaatsEinde                 Varchar(40)                               /* BLPlaats */,
   BLRegioEinde                  Varchar(35)                               /* BLRegio */,
   LandEinde                     Integer                                   /* LandID */,
   OmsLocEinde                   Varchar(40)                               /* LocOms */,
   RelatieStatusHis              Varchar(1)                    NOT NULL    /* StatusHistorie */,
   CONSTRAINT BR4424 PRIMARY KEY (ID)
);

-- Materiele historie tabellen -------------------------------------------------
CREATE SEQUENCE Kern.seq_His_BetrOuderlijkGezag;
CREATE TABLE Kern.His_BetrOuderlijkGezag (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_BetrOuderlijkGezag')  /* His_BetrOuderlijkGezagID */,
   Betr                          Integer                                   /* BetrID */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   ActieAanpGel                  Bigint                                    /* ActieID */,
   IndOuderHeeftGezag            boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4513 PRIMARY KEY (ID),
   CONSTRAINT BR4042 UNIQUE (Betr, TsReg, DatAanvGel)
);
CREATE SEQUENCE Kern.seq_His_BetrOuderschap;
CREATE TABLE Kern.His_BetrOuderschap (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_BetrOuderschap')  /* His_BetrOuderschapID */,
   Betr                          Integer                                   /* BetrID */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   ActieAanpGel                  Bigint                                    /* ActieID */,
   DatAanv                       Integer                                   /* Dat */,
   IndOuder                      boolean                       NOT NULL    /* Ja */,
   IndAdresgevend                boolean                                   /* Ja */,
   CONSTRAINT BR4510 PRIMARY KEY (ID),
   CONSTRAINT BR4032 UNIQUE (Betr, TsReg, DatAanvGel)
);
CREATE SEQUENCE Kern.seq_His_Doc;
CREATE TABLE Kern.His_Doc (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_Doc')  /* His_DocID */,
   Doc                           Bigint                                    /* DocID */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   Ident                         Varchar(20)                               /* DocIdent */,
   Aktenr                        Varchar(7)                                /* Aktenr */,
   Oms                           Varchar(80)                               /* DocOms */,
   Partij                        Smallint                      NOT NULL    /* PartijID */,
   CONSTRAINT BR4516 PRIMARY KEY (ID),
   CONSTRAINT BR4052 UNIQUE (Doc, TsReg)
);
CREATE SEQUENCE Kern.seq_His_MultiRealiteitRegel;
CREATE TABLE Kern.His_MultiRealiteitRegel (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_MultiRealiteitRegel')  /* His_MultiRealiteitRegelID */,
   MultiRealiteitRegel           Integer                                   /* MultiRealiteitregelID */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   CONSTRAINT BR5480 PRIMARY KEY (ID),
   CONSTRAINT BR5477 UNIQUE (MultiRealiteitRegel, TsReg)
);
CREATE SEQUENCE Kern.seq_His_Onderzoek;
CREATE TABLE Kern.His_Onderzoek (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_Onderzoek')  /* His_OnderzoekID */,
   Onderzoek                     Integer                                   /* OnderzoekID */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   DatBegin                      Integer                       NOT NULL    /* Dat */,
   DatEinde                      Integer                                   /* Dat */,
   Oms                           Text                                      /* OnderzoekOms */,
   CONSTRAINT BR4522 PRIMARY KEY (ID),
   CONSTRAINT BR4076 UNIQUE (Onderzoek, TsReg)
);
CREATE SEQUENCE Kern.seq_His_PersAanschr;
CREATE TABLE Kern.His_PersAanschr (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersAanschr')  /* His_PersAanschrID */,
   Pers                          Integer                                   /* PersID */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   ActieAanpGel                  Bigint                                    /* ActieID */,
   GebrGeslnaamEGP               Smallint                                  /* WijzeGebruikGeslnaamID */,
   IndAanschrMetAdellijkeTitels  boolean                                   /* JaNee */,
   IndAanschrAlgoritmischAfgele  boolean                       NOT NULL    /* JaNee */,
   PredikaatAanschr              Smallint                                  /* PredikaatID */,
   VoornamenAanschr              Varchar(200)                              /* Voornamen */,
   VoorvoegselAanschr            Varchar(10)                               /* Voorvoegsel */,
   ScheidingstekenAanschr        Varchar(1)                                /* Scheidingsteken */,
   AdellijkeTitelAanschr         Smallint                                  /* AdellijkeTitelID */,
   GeslnaamAanschr               Varchar(200)                  NOT NULL    /* Geslnaam */,
   CONSTRAINT BR4534 PRIMARY KEY (ID),
   CONSTRAINT BR4131 UNIQUE (Pers, TsReg, DatAanvGel)
);
CREATE SEQUENCE Kern.seq_His_PersBijhgem;
CREATE TABLE Kern.His_PersBijhgem (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersBijhgem')  /* His_PersBijhgemID */,
   Pers                          Integer                                   /* PersID */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   ActieAanpGel                  Bigint                                    /* ActieID */,
   Bijhgem                       Smallint                      NOT NULL    /* PartijID */,
   DatInschrInGem                Integer                       NOT NULL    /* Dat */,
   IndOnverwDocAanw              boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4558 PRIMARY KEY (ID),
   CONSTRAINT BR4216 UNIQUE (Pers, TsReg, DatAanvGel)
);
CREATE SEQUENCE Kern.seq_His_PersBijhverantwoordelijk;
CREATE TABLE Kern.His_PersBijhverantwoordelijk (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersBijhverantwoordelijk')  /* His_PersBijhverantwoordelijk */,
   Pers                          Integer                                   /* PersID */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   ActieAanpGel                  Bigint                                    /* ActieID */,
   Verantwoordelijke             Smallint                      NOT NULL    /* VerantwoordelijkeID */,
   CONSTRAINT BR4552 PRIMARY KEY (ID),
   CONSTRAINT BR4197 UNIQUE (Pers, TsReg, DatAanvGel)
);
CREATE SEQUENCE Kern.seq_His_PersEUVerkiezingen;
CREATE TABLE Kern.His_PersEUVerkiezingen (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersEUVerkiezingen')  /* His_PersEUVerkiezingenID */,
   Pers                          Integer                                   /* PersID */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   IndDeelnEUVerkiezingen        boolean                       NOT NULL    /* JaNee */,
   DatAanlAanpDeelnEUVerkiezing  Integer                                   /* Dat */,
   DatEindeUitslEUKiesr          Integer                                   /* Dat */,
   CONSTRAINT BR4549 PRIMARY KEY (ID),
   CONSTRAINT BR4187 UNIQUE (Pers, TsReg)
);
CREATE SEQUENCE Kern.seq_His_PersGeboorte;
CREATE TABLE Kern.His_PersGeboorte (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersGeboorte')  /* His_PersGeboorteID */,
   Pers                          Integer                                   /* PersID */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   DatGeboorte                   Integer                       NOT NULL    /* Dat */,
   GemGeboorte                   Smallint                                  /* PartijID */,
   WplGeboorte                   Integer                                   /* PlaatsID */,
   BLGeboorteplaats              Varchar(40)                               /* BLPlaats */,
   BLRegioGeboorte               Varchar(35)                               /* BLRegio */,
   LandGeboorte                  Integer                       NOT NULL    /* LandID */,
   OmsGeboorteloc                Varchar(40)                               /* LocOms */,
   CONSTRAINT BR4537 PRIMARY KEY (ID),
   CONSTRAINT BR4144 UNIQUE (Pers, TsReg)
);
CREATE SEQUENCE Kern.seq_His_PersGeslachtsaand;
CREATE TABLE Kern.His_PersGeslachtsaand (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersGeslachtsaand')  /* His_PersGeslachtsaandID */,
   Pers                          Integer                                   /* PersID */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   ActieAanpGel                  Bigint                                    /* ActieID */,
   Geslachtsaand                 Smallint                      NOT NULL    /* GeslachtsaandID */,
   CONSTRAINT BR4528 PRIMARY KEY (ID),
   CONSTRAINT BR4097 UNIQUE (Pers, TsReg, DatAanvGel)
);
CREATE SEQUENCE Kern.seq_His_PersIDs;
CREATE TABLE Kern.His_PersIDs (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersIDs')  /* His_PersIDsID */,
   Pers                          Integer                                   /* PersID */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   ActieAanpGel                  Bigint                                    /* ActieID */,
   BSN                           Integer                                   /* BSN */,
   ANr                           Bigint                                    /* ANr */,
   CONSTRAINT BR4525 PRIMARY KEY (ID),
   CONSTRAINT BR4087 UNIQUE (Pers, TsReg, DatAanvGel)
);
CREATE SEQUENCE Kern.seq_His_PersImmigratie;
CREATE TABLE Kern.His_PersImmigratie (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersImmigratie')  /* His_PersImmigratieID */,
   Pers                          Integer                                   /* PersID */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   ActieAanpGel                  Bigint                                    /* ActieID */,
   LandVanwaarGevestigd          Integer                                   /* LandID */,
   DatVestigingInNederland       Integer                       NOT NULL    /* Dat */,
   CONSTRAINT BR4564 PRIMARY KEY (ID),
   CONSTRAINT BR4235 UNIQUE (Pers, TsReg, DatAanvGel)
);
CREATE SEQUENCE Kern.seq_His_PersInschr;
CREATE TABLE Kern.His_PersInschr (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersInschr')  /* His_PersInschrID */,
   Pers                          Integer                                   /* PersID */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   DatInschr                     Integer                       NOT NULL    /* Dat */,
   Versienr                      Bigint                        NOT NULL    /* Versienr */,
   VorigePers                    Integer                                   /* PersID */,
   VolgendePers                  Integer                                   /* PersID */,
   CONSTRAINT BR4567 PRIMARY KEY (ID),
   CONSTRAINT BR4248 UNIQUE (Pers, TsReg)
);
CREATE SEQUENCE Kern.seq_His_PersOpschorting;
CREATE TABLE Kern.His_PersOpschorting (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersOpschorting')  /* His_PersOpschortingID */,
   Pers                          Integer                                   /* PersID */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   ActieAanpGel                  Bigint                                    /* ActieID */,
   RdnOpschortingBijhouding      Smallint                      NOT NULL    /* RdnOpschortingID */,
   CONSTRAINT BR4555 PRIMARY KEY (ID),
   CONSTRAINT BR4204 UNIQUE (Pers, TsReg, DatAanvGel)
);
CREATE SEQUENCE Kern.seq_His_PersOverlijden;
CREATE TABLE Kern.His_PersOverlijden (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersOverlijden')  /* His_PersOverlijdenID */,
   Pers                          Integer                                   /* PersID */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   DatOverlijden                 Integer                       NOT NULL    /* Dat */,
   GemOverlijden                 Smallint                                  /* PartijID */,
   WplOverlijden                 Integer                                   /* PlaatsID */,
   BLPlaatsOverlijden            Varchar(40)                               /* BLPlaats */,
   BLRegioOverlijden             Varchar(35)                               /* BLRegio */,
   LandOverlijden                Integer                       NOT NULL    /* LandID */,
   OmsLocOverlijden              Varchar(40)                               /* LocOms */,
   CONSTRAINT BR4540 PRIMARY KEY (ID),
   CONSTRAINT BR4157 UNIQUE (Pers, TsReg)
);
CREATE SEQUENCE Kern.seq_His_PersPK;
CREATE TABLE Kern.His_PersPK (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersPK')  /* His_PersPKID */,
   Pers                          Integer                                   /* PersID */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   GemPK                         Smallint                                  /* PartijID */,
   IndPKVolledigGeconv           boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4561 PRIMARY KEY (ID),
   CONSTRAINT BR4224 UNIQUE (Pers, TsReg)
);
CREATE SEQUENCE Kern.seq_His_PersSamengesteldeNaam;
CREATE TABLE Kern.His_PersSamengesteldeNaam (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersSamengesteldeNaam')  /* His_PersSamengesteldeNaamID */,
   Pers                          Integer                                   /* PersID */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   ActieAanpGel                  Bigint                                    /* ActieID */,
   Predikaat                     Smallint                                  /* PredikaatID */,
   Voornamen                     Varchar(200)                              /* Voornamen */,
   Voorvoegsel                   Varchar(10)                               /* Voorvoegsel */,
   Scheidingsteken               Varchar(1)                                /* Scheidingsteken */,
   AdellijkeTitel                Smallint                                  /* AdellijkeTitelID */,
   Geslnaam                      Varchar(200)                  NOT NULL    /* Geslnaam */,
   IndNreeksAlsGeslnaam          boolean                       NOT NULL    /* JaNee */,
   IndAlgoritmischAfgeleid       boolean                       NOT NULL    /* JaNee */,
   CONSTRAINT BR4531 PRIMARY KEY (ID),
   CONSTRAINT BR4114 UNIQUE (Pers, TsReg, DatAanvGel)
);
CREATE SEQUENCE Kern.seq_His_PersUitslNLKiesr;
CREATE TABLE Kern.His_PersUitslNLKiesr (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersUitslNLKiesr')  /* His_PersUitslNLKiesrID */,
   Pers                          Integer                                   /* PersID */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   IndUitslNLKiesr               boolean                       NOT NULL    /* Ja */,
   DatEindeUitslNLKiesr          Integer                                   /* Dat */,
   CONSTRAINT BR4546 PRIMARY KEY (ID),
   CONSTRAINT BR4178 UNIQUE (Pers, TsReg)
);
CREATE SEQUENCE Kern.seq_His_PersVerblijfsr;
CREATE TABLE Kern.His_PersVerblijfsr (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersVerblijfsr')  /* His_PersVerblijfsrID */,
   Pers                          Integer                                   /* PersID */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   ActieAanpGel                  Bigint                                    /* ActieID */,
   Verblijfsr                    Smallint                      NOT NULL    /* VerblijfsrID */,
   DatAanvVerblijfsr             Integer                       NOT NULL    /* Dat */,
   DatVoorzEindeVerblijfsr       Integer                                   /* Dat */,
   DatAanvAaneenslVerblijfsr     Integer                                   /* Dat */,
   CONSTRAINT BR4543 PRIMARY KEY (ID),
   CONSTRAINT BR4170 UNIQUE (Pers, TsReg, DatAanvGel)
);
CREATE SEQUENCE Kern.seq_His_PersAdres;
CREATE TABLE Kern.His_PersAdres (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersAdres')  /* His_PersAdresID */,
   PersAdres                     Integer                                   /* PersAdresID */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   ActieAanpGel                  Bigint                                    /* ActieID */,
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
   LoctovAdres                   Varchar(2)                                /* AandBijHuisnr */,
   LocOms                        Varchar(40)                               /* LocOms */,
   BLAdresRegel1                 Varchar(40)                               /* Adresregel */,
   BLAdresRegel2                 Varchar(40)                               /* Adresregel */,
   BLAdresRegel3                 Varchar(40)                               /* Adresregel */,
   BLAdresRegel4                 Varchar(40)                               /* Adresregel */,
   BLAdresRegel5                 Varchar(40)                               /* Adresregel */,
   BLAdresRegel6                 Varchar(40)                               /* Adresregel */,
   Land                          Integer                       NOT NULL    /* LandID */,
   DatVertrekUitNederland        Integer                                   /* Dat */,
   CONSTRAINT BR6076 PRIMARY KEY (ID),
   CONSTRAINT BR6073 UNIQUE (PersAdres, TsReg, DatAanvGel)
);
CREATE SEQUENCE Kern.seq_His_PersGeslnaamcomp;
CREATE TABLE Kern.His_PersGeslnaamcomp (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersGeslnaamcomp')  /* His_PersGeslnaamcompID */,
   PersGeslnaamcomp              Integer                                   /* PersGeslnaamID */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   ActieAanpGel                  Bigint                                    /* ActieID */,
   Voorvoegsel                   Varchar(10)                               /* Voorvoegsel */,
   Scheidingsteken               Varchar(1)                                /* Scheidingsteken */,
   Naam                          Varchar(200)                  NOT NULL    /* Geslnaamcomp */,
   Predikaat                     Smallint                                  /* PredikaatID */,
   AdellijkeTitel                Smallint                                  /* AdellijkeTitelID */,
   CONSTRAINT BR4579 PRIMARY KEY (ID),
   CONSTRAINT BR4314 UNIQUE (PersGeslnaamcomp, TsReg, DatAanvGel)
);
CREATE SEQUENCE Kern.seq_His_PersIndicatie;
CREATE TABLE Kern.His_PersIndicatie (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersIndicatie')  /* His_PersIndicatieID */,
   PersIndicatie                 Integer                                   /* PersIndicatieID */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   ActieAanpGel                  Bigint                                    /* ActieID */,
   Waarde                        boolean                       NOT NULL    /* Ja */,
   CONSTRAINT BR4582 PRIMARY KEY (ID),
   CONSTRAINT BR4324 UNIQUE (PersIndicatie, TsReg, DatAanvGel)
);
CREATE SEQUENCE Kern.seq_His_PersNation;
CREATE TABLE Kern.His_PersNation (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersNation')  /* His_PersNationID */,
   PersNation                    Integer                                   /* PersNationID */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   ActieAanpGel                  Bigint                                    /* ActieID */,
   RdnVerlies                    Smallint                                  /* RdnVerliesNLNationID */,
   RdnVerk                       Smallint                                  /* RdnVerkNLNationID */,
   CONSTRAINT BR4585 PRIMARY KEY (ID),
   CONSTRAINT BR4335 UNIQUE (PersNation, TsReg, DatAanvGel)
);
CREATE SEQUENCE Kern.seq_His_PersReisdoc;
CREATE TABLE Kern.His_PersReisdoc (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersReisdoc')  /* His_PersReisdocID */,
   PersReisdoc                   Integer                                   /* ReisdocID */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   Nr                            Varchar(9)                    NOT NULL    /* ReisdocNr */,
   AutVanAfgifte                 Integer                       NOT NULL    /* AutVanAfgifteReisdocID */,
   DatIngangDoc                  Integer                       NOT NULL    /* Dat */,
   DatUitgifte                   Integer                       NOT NULL    /* Dat */,
   DatVoorzeEindeGel             Integer                       NOT NULL    /* Dat */,
   DatInhingVermissing           Integer                                   /* Dat */,
   RdnVervallen                  Smallint                                  /* RdnOntbrReisdocID */,
   LengteHouder                  Smallint                                  /* LengteInCm */,
   CONSTRAINT BR4588 PRIMARY KEY (ID),
   CONSTRAINT BR4351 UNIQUE (PersReisdoc, TsReg)
);
CREATE SEQUENCE Kern.seq_His_PersVerificatie;
CREATE TABLE Kern.His_PersVerificatie (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersVerificatie')  /* His_PersVerificatieID */,
   PersVerificatie               Bigint                                    /* VerificatieID */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   Dat                           Integer                       NOT NULL    /* Dat */,
   CONSTRAINT BR4591 PRIMARY KEY (ID),
   CONSTRAINT BR4358 UNIQUE (PersVerificatie, TsReg)
);
CREATE SEQUENCE Kern.seq_His_PersVoornaam;
CREATE TABLE Kern.His_PersVoornaam (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_PersVoornaam')  /* His_PersVoornaamID */,
   PersVoornaam                  Integer                                   /* PersVoornaamID */,
   DatAanvGel                    Integer                                   /* Dat */,
   DatEindeGel                   Integer                                   /* Dat */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   ActieAanpGel                  Bigint                                    /* ActieID */,
   Naam                          Varchar(40)                   NOT NULL    /* Voornaam */,
   CONSTRAINT BR4594 PRIMARY KEY (ID),
   CONSTRAINT BR4368 UNIQUE (PersVoornaam, TsReg, DatAanvGel)
);
CREATE SEQUENCE Kern.seq_His_Relatie;
CREATE TABLE Kern.His_Relatie (
   ID                            Bigint                        NOT NULL  DEFAULT nextval('Kern.seq_His_Relatie')  /* His_RelatieID */,
   Relatie                       Integer                                   /* RelatieID */,
   TsReg                         Timestamp                                 /* DatTijd */,
   TsVerval                      Timestamp                                 /* DatTijd */,
   ActieInh                      Bigint                                    /* ActieID */,
   ActieVerval                   Bigint                                    /* ActieID */,
   DatAanv                       Integer                       NOT NULL    /* Dat */,
   GemAanv                       Smallint                                  /* PartijID */,
   WplAanv                       Integer                                   /* PlaatsID */,
   BLPlaatsAanv                  Varchar(40)                               /* BLPlaats */,
   BLRegioAanv                   Varchar(35)                               /* BLRegio */,
   LandAanv                      Integer                       NOT NULL    /* LandID */,
   OmsLocAanv                    Varchar(40)                               /* LocOms */,
   RdnEinde                      Smallint                                  /* RdnBeeindRelatieID */,
   DatEinde                      Integer                                   /* Dat */,
   GemEinde                      Smallint                                  /* PartijID */,
   WplEinde                      Integer                                   /* PlaatsID */,
   BLPlaatsEinde                 Varchar(40)                               /* BLPlaats */,
   BLRegioEinde                  Varchar(35)                               /* BLRegio */,
   LandEinde                     Integer                                   /* LandID */,
   OmsLocEinde                   Varchar(40)                               /* LocOms */,
   CONSTRAINT BR4597 PRIMARY KEY (ID),
   CONSTRAINT BR4389 UNIQUE (Relatie, TsReg)
);

-- Foreign keys ----------------------------------------------------------------
ALTER TABLE Kern.DbObject ADD CONSTRAINT FK5644 FOREIGN KEY (Srt) REFERENCES Kern.SrtDbObject (ID);
ALTER TABLE Kern.DbObject ADD CONSTRAINT FK5661 FOREIGN KEY (Ouder) REFERENCES Kern.DbObject (ID);
ALTER TABLE Kern.Element ADD CONSTRAINT FK3721 FOREIGN KEY (Srt) REFERENCES Kern.SrtElement (ID);
ALTER TABLE Kern.Element ADD CONSTRAINT FK5663 FOREIGN KEY (Ouder) REFERENCES Kern.Element (ID);
ALTER TABLE Kern.His_Partij ADD CONSTRAINT FK4620 FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.His_Partij ADD CONSTRAINT FK4623 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Partij ADD CONSTRAINT FK4624 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Partij ADD CONSTRAINT FK4627 FOREIGN KEY (Sector) REFERENCES Kern.Sector (ID);
ALTER TABLE Kern.His_PartijGem ADD CONSTRAINT FK6117 FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.His_PartijGem ADD CONSTRAINT FK6120 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PartijGem ADD CONSTRAINT FK6121 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PartijGem ADD CONSTRAINT FK4611 FOREIGN KEY (VoortzettendeGem) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.His_PartijGem ADD CONSTRAINT FK4613 FOREIGN KEY (OnderdeelVan) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.Partij ADD CONSTRAINT FK2195 FOREIGN KEY (Srt) REFERENCES Kern.SrtPartij (ID);
ALTER TABLE Kern.Partij ADD CONSTRAINT FK2196 FOREIGN KEY (Sector) REFERENCES Kern.Sector (ID);
ALTER TABLE Kern.Partij ADD CONSTRAINT FK2005 FOREIGN KEY (VoortzettendeGem) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.Partij ADD CONSTRAINT FK4602 FOREIGN KEY (OnderdeelVan) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.PartijRol ADD CONSTRAINT FK2185 FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.PartijRol ADD CONSTRAINT FK2186 FOREIGN KEY (Rol) REFERENCES Kern.Rol (ID);
ALTER TABLE Kern.SrtActie ADD CONSTRAINT FK5845 FOREIGN KEY (CategorieSrtActie) REFERENCES Kern.CategorieSrtActie (ID);
ALTER TABLE Kern.SrtDoc ADD CONSTRAINT FK5846 FOREIGN KEY (CategorieSrtDoc) REFERENCES Kern.CategorieSrtDoc (ID);

ALTER TABLE Kern.Actie ADD CONSTRAINT FK3055 FOREIGN KEY (Srt) REFERENCES Kern.SrtActie (ID);
ALTER TABLE Kern.Actie ADD CONSTRAINT FK3209 FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.Actie ADD CONSTRAINT FK3212 FOREIGN KEY (Verdrag) REFERENCES Kern.Verdrag (ID);
ALTER TABLE Kern.Betr ADD CONSTRAINT FK3860 FOREIGN KEY (Relatie) REFERENCES Kern.Relatie (ID);
ALTER TABLE Kern.Betr ADD CONSTRAINT FK3861 FOREIGN KEY (Rol) REFERENCES Kern.SrtBetr (ID);
ALTER TABLE Kern.Betr ADD CONSTRAINT FK3859 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.Bron ADD CONSTRAINT FK3875 FOREIGN KEY (Actie) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.Bron ADD CONSTRAINT FK3876 FOREIGN KEY (Doc) REFERENCES Kern.Doc (ID);
ALTER TABLE Kern.Doc ADD CONSTRAINT FK3157 FOREIGN KEY (Srt) REFERENCES Kern.SrtDoc (ID);
ALTER TABLE Kern.Doc ADD CONSTRAINT FK3139 FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.GegevenInOnderzoek ADD CONSTRAINT FK3865 FOREIGN KEY (Onderzoek) REFERENCES Kern.Onderzoek (ID);
ALTER TABLE Kern.GegevenInOnderzoek ADD CONSTRAINT FK3866 FOREIGN KEY (SrtGegeven) REFERENCES Kern.DbObject (ID);
ALTER TABLE Kern.MultiRealiteitRegel ADD CONSTRAINT FK2050 FOREIGN KEY (GeldigVoor) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.MultiRealiteitRegel ADD CONSTRAINT FK2051 FOREIGN KEY (Srt) REFERENCES Kern.SrtMultiRealiteitRegel (ID);
ALTER TABLE Kern.MultiRealiteitRegel ADD CONSTRAINT FK2053 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.MultiRealiteitRegel ADD CONSTRAINT FK2054 FOREIGN KEY (MultiRealiteitPers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.MultiRealiteitRegel ADD CONSTRAINT FK2055 FOREIGN KEY (Relatie) REFERENCES Kern.Relatie (ID);
ALTER TABLE Kern.MultiRealiteitRegel ADD CONSTRAINT FK2056 FOREIGN KEY (Betr) REFERENCES Kern.Betr (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK1997 FOREIGN KEY (Srt) REFERENCES Kern.SrtPers (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK3031 FOREIGN KEY (Geslachtsaand) REFERENCES Kern.Geslachtsaand (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK1969 FOREIGN KEY (Predikaat) REFERENCES Kern.Predikaat (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK1968 FOREIGN KEY (AdellijkeTitel) REFERENCES Kern.AdellijkeTitel (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK3593 FOREIGN KEY (GebrGeslnaamEGP) REFERENCES Kern.WijzeGebruikGeslnaam (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK3703 FOREIGN KEY (PredikaatAanschr) REFERENCES Kern.Predikaat (ID);
ALTER TABLE Kern.Pers ADD CONSTRAINT FK6113 FOREIGN KEY (AdellijkeTitelAanschr) REFERENCES Kern.AdellijkeTitel (ID);
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
ALTER TABLE Kern.PersOnderzoek ADD CONSTRAINT FK6131 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.PersOnderzoek ADD CONSTRAINT FK6132 FOREIGN KEY (Onderzoek) REFERENCES Kern.Onderzoek (ID);
ALTER TABLE Kern.PersReisdoc ADD CONSTRAINT FK3752 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.PersReisdoc ADD CONSTRAINT FK3739 FOREIGN KEY (Srt) REFERENCES Kern.SrtNLReisdoc (ID);
ALTER TABLE Kern.PersReisdoc ADD CONSTRAINT FK3744 FOREIGN KEY (AutVanAfgifte) REFERENCES Kern.AutVanAfgifteReisdoc (ID);
ALTER TABLE Kern.PersReisdoc ADD CONSTRAINT FK3747 FOREIGN KEY (RdnVervallen) REFERENCES Kern.RdnVervallenReisdoc (ID);
ALTER TABLE Kern.PersVerificatie ADD CONSTRAINT FK2142 FOREIGN KEY (Geverifieerde) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.PersVerificatie ADD CONSTRAINT FK3779 FOREIGN KEY (Srt) REFERENCES Kern.SrtVerificatie (ID);
ALTER TABLE Kern.PersVoornaam ADD CONSTRAINT FK3023 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.Regelverantwoording ADD CONSTRAINT FK6149 FOREIGN KEY (Actie) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.Regelverantwoording ADD CONSTRAINT FK6152 FOREIGN KEY (Regel) REFERENCES Kern.Regel (ID);
ALTER TABLE Kern.Relatie ADD CONSTRAINT FK3198 FOREIGN KEY (Srt) REFERENCES Kern.SrtRelatie (ID);
ALTER TABLE Kern.Relatie ADD CONSTRAINT FK3755 FOREIGN KEY (GemAanv) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.Relatie ADD CONSTRAINT FK3756 FOREIGN KEY (WplAanv) REFERENCES Kern.Plaats (ID);
ALTER TABLE Kern.Relatie ADD CONSTRAINT FK3760 FOREIGN KEY (LandAanv) REFERENCES Kern.Land (ID);
ALTER TABLE Kern.Relatie ADD CONSTRAINT FK3207 FOREIGN KEY (RdnEinde) REFERENCES Kern.RdnBeeindRelatie (ID);
ALTER TABLE Kern.Relatie ADD CONSTRAINT FK3763 FOREIGN KEY (GemEinde) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.Relatie ADD CONSTRAINT FK3764 FOREIGN KEY (WplEinde) REFERENCES Kern.Plaats (ID);
ALTER TABLE Kern.Relatie ADD CONSTRAINT FK3768 FOREIGN KEY (LandEinde) REFERENCES Kern.Land (ID);
ALTER TABLE Kern.His_BetrOuderlijkGezag ADD CONSTRAINT FK4033 FOREIGN KEY (Betr) REFERENCES Kern.Betr (ID);
ALTER TABLE Kern.His_BetrOuderlijkGezag ADD CONSTRAINT FK4038 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_BetrOuderlijkGezag ADD CONSTRAINT FK4039 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_BetrOuderlijkGezag ADD CONSTRAINT FK4040 FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_BetrOuderschap ADD CONSTRAINT FK4025 FOREIGN KEY (Betr) REFERENCES Kern.Betr (ID);
ALTER TABLE Kern.His_BetrOuderschap ADD CONSTRAINT FK4028 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_BetrOuderschap ADD CONSTRAINT FK4029 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_BetrOuderschap ADD CONSTRAINT FK6091 FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Doc ADD CONSTRAINT FK4043 FOREIGN KEY (Doc) REFERENCES Kern.Doc (ID);
ALTER TABLE Kern.His_Doc ADD CONSTRAINT FK4046 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Doc ADD CONSTRAINT FK4047 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Doc ADD CONSTRAINT FK4051 FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.His_MultiRealiteitRegel ADD CONSTRAINT FK5472 FOREIGN KEY (MultiRealiteitRegel) REFERENCES Kern.MultiRealiteitRegel (ID);
ALTER TABLE Kern.His_MultiRealiteitRegel ADD CONSTRAINT FK5475 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_MultiRealiteitRegel ADD CONSTRAINT FK5476 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Onderzoek ADD CONSTRAINT FK4068 FOREIGN KEY (Onderzoek) REFERENCES Kern.Onderzoek (ID);
ALTER TABLE Kern.His_Onderzoek ADD CONSTRAINT FK4071 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_Onderzoek ADD CONSTRAINT FK4072 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersAanschr ADD CONSTRAINT FK4115 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_PersAanschr ADD CONSTRAINT FK4120 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersAanschr ADD CONSTRAINT FK4121 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersAanschr ADD CONSTRAINT FK4122 FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersAanschr ADD CONSTRAINT FK4123 FOREIGN KEY (GebrGeslnaamEGP) REFERENCES Kern.WijzeGebruikGeslnaam (ID);
ALTER TABLE Kern.His_PersAanschr ADD CONSTRAINT FK4126 FOREIGN KEY (PredikaatAanschr) REFERENCES Kern.Predikaat (ID);
ALTER TABLE Kern.His_PersAanschr ADD CONSTRAINT FK6114 FOREIGN KEY (AdellijkeTitelAanschr) REFERENCES Kern.AdellijkeTitel (ID);
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
ALTER TABLE Kern.His_PersInschr ADD CONSTRAINT FK4246 FOREIGN KEY (VorigePers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_PersInschr ADD CONSTRAINT FK4247 FOREIGN KEY (VolgendePers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_PersOpschorting ADD CONSTRAINT FK4198 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE Kern.His_PersOpschorting ADD CONSTRAINT FK4201 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersOpschorting ADD CONSTRAINT FK4202 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersOpschorting ADD CONSTRAINT FK5006 FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
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
ALTER TABLE Kern.His_PersAdres ADD CONSTRAINT FK6065 FOREIGN KEY (PersAdres) REFERENCES Kern.PersAdres (ID);
ALTER TABLE Kern.His_PersAdres ADD CONSTRAINT FK6070 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersAdres ADD CONSTRAINT FK6071 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersAdres ADD CONSTRAINT FK6072 FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie (ID);
ALTER TABLE Kern.His_PersAdres ADD CONSTRAINT FK4257 FOREIGN KEY (Srt) REFERENCES Kern.FunctieAdres (ID);
ALTER TABLE Kern.His_PersAdres ADD CONSTRAINT FK4258 FOREIGN KEY (RdnWijz) REFERENCES Kern.RdnWijzAdres (ID);
ALTER TABLE Kern.His_PersAdres ADD CONSTRAINT FK4259 FOREIGN KEY (AangAdresh) REFERENCES Kern.AangAdresh (ID);
ALTER TABLE Kern.His_PersAdres ADD CONSTRAINT FK4272 FOREIGN KEY (Gem) REFERENCES Kern.Partij (ID);
ALTER TABLE Kern.His_PersAdres ADD CONSTRAINT FK4280 FOREIGN KEY (Wpl) REFERENCES Kern.Plaats (ID);
ALTER TABLE Kern.His_PersAdres ADD CONSTRAINT FK4298 FOREIGN KEY (Land) REFERENCES Kern.Land (ID);
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
ALTER TABLE Kern.His_PersReisdoc ADD CONSTRAINT FK4346 FOREIGN KEY (AutVanAfgifte) REFERENCES Kern.AutVanAfgifteReisdoc (ID);
ALTER TABLE Kern.His_PersReisdoc ADD CONSTRAINT FK4349 FOREIGN KEY (RdnVervallen) REFERENCES Kern.RdnVervallenReisdoc (ID);
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


