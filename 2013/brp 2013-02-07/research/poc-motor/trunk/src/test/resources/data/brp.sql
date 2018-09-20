--------------------------------------------------------------------------------
-- PostgreSQL SQL-DDL bestand voor Basisregistratie Personen (BRP)
--------------------------------------------------------------------------------
--
-- Gegenereerd door BRP Meta Register.
-- Gegenereerd op: Thu Feb 23 13:31:33 CET 2012
--
--------------------------------------------------------------------------------


-- Schemas ---------------------------------------------------------------------
drop schema IF EXISTS AutAut CASCADE;
CREATE SCHEMA AutAut;
drop schema IF EXISTS BRM CASCADE;
CREATE SCHEMA BRM;
drop schema IF EXISTS Ber CASCADE;
CREATE SCHEMA Ber;
drop schema IF EXISTS Kern CASCADE;
CREATE SCHEMA Kern;
drop schema IF EXISTS Lev CASCADE;
CREATE SCHEMA Lev;


-- Actual tabellen--------------------------------------------------------------

CREATE SEQUENCE AutAut.seq_Authenticatiemiddel;
CREATE TABLE AutAut.Authenticatiemiddel (
    ID Integer NOT NULL DEFAULT nextval('AutAut.seq_Authenticatiemiddel') /* AuthenticatiemiddelID */,
    Partij Integer NOT NULL /* PartijID */,
    Rol Integer/* RolID */,
    Functie Smallint/* FunctieID */,
    CertificaatTbvSSL Integer/* CertificaatID */,
    CertificaatTbvOndertekening Integer/* CertificaatID */,
    IPAdres inet/* IPAdres */,
    AuthenticatiemiddelStatusHis Varchar(1) NOT NULL /* StatusHistorie */,
    CONSTRAINT BR4497 PRIMARY KEY (ID)
);
ALTER SEQUENCE AutAut.seq_Authenticatiemiddel OWNED BY AutAut.Authenticatiemiddel.ID;

CREATE SEQUENCE AutAut.seq_Autorisatiebesluit;
CREATE TABLE AutAut.Autorisatiebesluit (
    ID Integer NOT NULL DEFAULT nextval('AutAut.seq_Autorisatiebesluit') /* AutorisatiebesluitID */,
    Srt Smallint NOT NULL /* SrtAutorisatiebesluitID */,
    Besluittekst Text NOT NULL /* TekstUitBesluit */,
    Autoriseerder Integer NOT NULL /* PartijID */,
    IndModelBesluit boolean NOT NULL /* JaNee */,
    GebaseerdOp Integer/* AutorisatiebesluitID */,
    IndIngetrokken boolean/* JaNee */,
    DatBesluit Numeric(8)/* Dat */,
    DatIngang Numeric(8)/* Dat */,
    DatEinde Numeric(8)/* Dat */,
    AutorisatiebesluitStatusHis Varchar(1) NOT NULL /* StatusHistorie */,
    Toestand Smallint/* ToestandsID */,
    BijhautorisatiebesluitStatus Varchar(1) NOT NULL /* StatusHistorie */,
    CONSTRAINT BR4978 PRIMARY KEY (ID)
);
ALTER SEQUENCE AutAut.seq_Autorisatiebesluit OWNED BY AutAut.Autorisatiebesluit.ID;

CREATE TABLE AutAut.BeperkingPopulatie (
    ID Smallint NOT NULL /* BijhpopulatieID */,
    Naam Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    Oms Varchar(250) NOT NULL /* OmsEnumeratiewaarde */,
    DatAanvGel Numeric(8)/* Dat */,
    DatEindeGel Numeric(8)/* Dat */,
    CONSTRAINT BR5773 PRIMARY KEY (ID),
    CONSTRAINT BR5774 UNIQUE (Naam)
);

CREATE SEQUENCE AutAut.seq_Bijhautorisatie;
CREATE TABLE AutAut.Bijhautorisatie (
    ID Smallint NOT NULL DEFAULT nextval('AutAut.seq_Bijhautorisatie') /* BijhautorisatieID */,
    Bijhautorisatiebesluit Integer NOT NULL /* AutorisatiebesluitID */,
    Verantwoordelijke Smallint/* VerantwoordelijkeID */,
    SrtBijhouding Smallint/* SrtBijhoudingID */,
    GeautoriseerdeSrtPartij Integer/* SrtPartijID */,
    GeautoriseerdePartij Integer/* PartijID */,
    Toestand Smallint/* ToestandsID */,
    Oms Varchar(250)/* OmsEnumeratiewaarde */,
    BeperkingPopulatie Smallint/* BijhpopulatieID */,
    DatIngang Numeric(8)/* Dat */,
    DatEinde Numeric(8)/* Dat */,
    BijhautorisatieStatusHis Varchar(1) NOT NULL /* StatusHistorie */,
    CONSTRAINT BR5747 PRIMARY KEY (ID)
);
ALTER SEQUENCE AutAut.seq_Bijhautorisatie OWNED BY AutAut.Bijhautorisatie.ID;

CREATE SEQUENCE AutAut.seq_Bijhsituatie;
CREATE TABLE AutAut.Bijhsituatie (
    ID Integer NOT NULL DEFAULT nextval('AutAut.seq_Bijhsituatie') /* BijhsituatieID */,
    Bijhautorisatie Smallint NOT NULL /* BijhautorisatieID */,
    CategorieSrtActie Smallint/* CategorieSrtActieID */,
    SrtActie Integer/* SrtActieID */,
    CategorieSrtDoc Smallint/* CategorieSrtDocID */,
    SrtDoc Integer/* SrtDocID */,
    BijhsituatieStatusHis Varchar(1) NOT NULL /* StatusHistorie */,
    CONSTRAINT BR5748 PRIMARY KEY (ID),
    CONSTRAINT BR5749 UNIQUE (Bijhautorisatie, CategorieSrtActie, SrtActie, CategorieSrtDoc, SrtDoc)
);
ALTER SEQUENCE AutAut.seq_Bijhsituatie OWNED BY AutAut.Bijhsituatie.ID;

CREATE SEQUENCE AutAut.seq_Certificaat;
CREATE TABLE AutAut.Certificaat (
    ID Integer NOT NULL DEFAULT nextval('AutAut.seq_Certificaat') /* CertificaatID */,
    Subject Varchar(1024) NOT NULL /* Certificaatsubject */,
    Serial Bigint NOT NULL /* Certificaatserial */,
    Signature Varchar(1024) NOT NULL /* PubliekeSleutel */,
    CONSTRAINT BR4727 PRIMARY KEY (ID),
    CONSTRAINT BR4728 UNIQUE (Subject, Serial),
    CONSTRAINT BR5037 UNIQUE (Signature)
);
ALTER SEQUENCE AutAut.seq_Certificaat OWNED BY AutAut.Certificaat.ID;

CREATE SEQUENCE AutAut.seq_Doelbinding;
CREATE TABLE AutAut.Doelbinding (
    ID Integer NOT NULL DEFAULT nextval('AutAut.seq_Doelbinding') /* DoelbindingID */,
    Levsautorisatiebesluit Integer NOT NULL /* AutorisatiebesluitID */,
    Geautoriseerde Integer NOT NULL /* PartijID */,
    Protocolleringsniveau Smallint/* ProtocolleringsniveauID */,
    TekstDoelbinding Text/* TekstDoelbinding */,
    Populatiecriterium Text/* Populatiecriterium */,
    IndVerstrbeperkingHonoreren boolean/* JaNee */,
    DoelbindingStatusHis Varchar(1) NOT NULL /* StatusHistorie */,
    CONSTRAINT BR4979 PRIMARY KEY (ID),
    CONSTRAINT BR4980 UNIQUE (Geautoriseerde, Levsautorisatiebesluit)
);
ALTER SEQUENCE AutAut.seq_Doelbinding OWNED BY AutAut.Doelbinding.ID;

CREATE SEQUENCE AutAut.seq_DoelbindingGegevenselement;
CREATE TABLE AutAut.DoelbindingGegevenselement (
    ID Integer NOT NULL DEFAULT nextval('AutAut.seq_DoelbindingGegevenselement') /* GegevenselementDoelbindingID */,
    Doelbinding Integer NOT NULL /* DoelbindingID */,
    Gegevenselement Integer NOT NULL /* DbObjectID */,
    CONSTRAINT BR4981 PRIMARY KEY (ID),
    CONSTRAINT BR4982 UNIQUE (Doelbinding, Gegevenselement)
);
ALTER SEQUENCE AutAut.seq_DoelbindingGegevenselement OWNED BY AutAut.DoelbindingGegevenselement.ID;

CREATE TABLE AutAut.Functie (
    ID Smallint NOT NULL /* FunctieID */,
    Naam Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    CONSTRAINT BR4725 PRIMARY KEY (ID),
    CONSTRAINT BR4726 UNIQUE (Naam)
);

CREATE TABLE AutAut.Protocolleringsniveau (
    ID Smallint NOT NULL /* ProtocolleringsniveauID */,
    Code Smallint NOT NULL /* ProtocolleringsniveauCode */,
    Naam Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    Oms Varchar(250) NOT NULL /* OmsEnumeratiewaarde */,
    DatAanvGel Numeric(8)/* Dat */,
    DatEindeGel Numeric(8)/* Dat */,
    CONSTRAINT BR4990 PRIMARY KEY (ID),
    CONSTRAINT BR4991 UNIQUE (Naam),
    CONSTRAINT BR4992 UNIQUE (Oms),
    CONSTRAINT BR5035 UNIQUE (Code)
);

CREATE TABLE AutAut.SrtAutorisatiebesluit (
    ID Smallint NOT NULL /* SrtAutorisatiebesluitID */,
    Naam Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    Oms Varchar(250) NOT NULL /* OmsEnumeratiewaarde */,
    DatAanvGel Numeric(8)/* Dat */,
    DatEindeGel Numeric(8)/* Dat */,
    CONSTRAINT BR4987 PRIMARY KEY (ID),
    CONSTRAINT BR4988 UNIQUE (Oms),
    CONSTRAINT BR4989 UNIQUE (Naam)
);

CREATE TABLE AutAut.SrtBijhouding (
    ID Smallint NOT NULL /* SrtBijhoudingID */,
    Naam Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    Oms Varchar(250) NOT NULL /* OmsEnumeratiewaarde */,
    DatAanvGel Numeric(8)/* Dat */,
    DatEindeGel Numeric(8)/* Dat */,
    CONSTRAINT BR5750 PRIMARY KEY (ID),
    CONSTRAINT BR5751 UNIQUE (Naam)
);

CREATE TABLE AutAut.Toestand (
    ID Smallint NOT NULL /* ToestandsID */,
    Naam Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    Oms Varchar(250) NOT NULL /* OmsEnumeratiewaarde */,
    DatAanvGel Numeric(8)/* Dat */,
    DatEindeGel Numeric(8)/* Dat */,
    CONSTRAINT BR5746 PRIMARY KEY (ID)
);

CREATE SEQUENCE AutAut.seq_Uitgeslotene;
CREATE TABLE AutAut.Uitgeslotene (
    ID Integer NOT NULL DEFAULT nextval('AutAut.seq_Uitgeslotene') /* AuthorisatiebesluitGegevense */,
    Bijhautorisatie Smallint NOT NULL /* BijhautorisatieID */,
    UitgeslotenPartij Integer NOT NULL /* PartijID */,
    UitgesloteneStatusHis Varchar(1) NOT NULL /* StatusHistorie */,
    CONSTRAINT BR5791 PRIMARY KEY (ID),
    CONSTRAINT BR5792 UNIQUE (Bijhautorisatie, UitgeslotenPartij)
);
ALTER SEQUENCE AutAut.seq_Uitgeslotene OWNED BY AutAut.Uitgeslotene.ID;

CREATE SEQUENCE AutAut.seq_His_Authenticatiemiddel;
CREATE TABLE AutAut.His_Authenticatiemiddel (
    ID Bigint NOT NULL DEFAULT nextval('AutAut.seq_His_Authenticatiemiddel') /* His_AuthenticatiemiddelID */,
    Authenticatiemiddel Integer/* AuthenticatiemiddelID */,
    TsReg Timestamp/* Ts */,
    TsVerval Timestamp/* Ts */,
    ActieInh Bigint/* ActieID */,
    ActieVerval Bigint/* ActieID */,
    Functie Smallint/* FunctieID */,
    CertificaatTbvSSL Integer NOT NULL /* CertificaatID */,
    CertificaatTbvOndertekening Integer NOT NULL /* CertificaatID */,
    IPAdres inet/* IPAdres */,
    CONSTRAINT BR4691 PRIMARY KEY (ID),
    CONSTRAINT BR4687 UNIQUE (Authenticatiemiddel, TsReg)
);
ALTER SEQUENCE AutAut.seq_His_Authenticatiemiddel OWNED BY AutAut.His_Authenticatiemiddel.ID;

CREATE SEQUENCE AutAut.seq_His_Autorisatiebesluit;
CREATE TABLE AutAut.His_Autorisatiebesluit (
    ID Bigint NOT NULL DEFAULT nextval('AutAut.seq_His_Autorisatiebesluit') /* His_AutorisatiebesluitID */,
    Autorisatiebesluit Integer/* AutorisatiebesluitID */,
    TsReg Timestamp/* Ts */,
    TsVerval Timestamp/* Ts */,
    ActieInh Bigint/* ActieID */,
    ActieVerval Bigint/* ActieID */,
    IndIngetrokken boolean NOT NULL /* JaNee */,
    DatBesluit Numeric(8) NOT NULL /* Dat */,
    DatIngang Numeric(8) NOT NULL /* Dat */,
    DatEinde Numeric(8)/* Dat */,
    CONSTRAINT BR5000 PRIMARY KEY (ID),
    CONSTRAINT BR4965 UNIQUE (Autorisatiebesluit, TsReg)
);
ALTER SEQUENCE AutAut.seq_His_Autorisatiebesluit OWNED BY AutAut.His_Autorisatiebesluit.ID;

CREATE SEQUENCE AutAut.seq_His_AutorisatiebesluitBijhau;
CREATE TABLE AutAut.His_AutorisatiebesluitBijhau (
    ID Bigint NOT NULL DEFAULT nextval('AutAut.seq_His_AutorisatiebesluitBijhau') /* His_AutorisatiebesluitBijhau */,
    Autorisatiebesluit Integer/* AutorisatiebesluitID */,
    TsReg Timestamp/* Ts */,
    TsVerval Timestamp/* Ts */,
    ActieInh Bigint/* ActieID */,
    ActieVerval Bigint/* ActieID */,
    Toestand Smallint NOT NULL /* ToestandsID */,
    CONSTRAINT BR5754 PRIMARY KEY (ID),
    CONSTRAINT BR5729 UNIQUE (Autorisatiebesluit, TsReg)
);
ALTER SEQUENCE AutAut.seq_His_AutorisatiebesluitBijhau OWNED BY AutAut.His_AutorisatiebesluitBijhau.ID;

CREATE SEQUENCE AutAut.seq_His_Bijhautorisatie;
CREATE TABLE AutAut.His_Bijhautorisatie (
    ID Bigint NOT NULL DEFAULT nextval('AutAut.seq_His_Bijhautorisatie') /* His_BijhautorisatieID */,
    Bijhautorisatie Smallint/* BijhautorisatieID */,
    TsReg Timestamp/* Ts */,
    TsVerval Timestamp/* Ts */,
    ActieInh Bigint/* ActieID */,
    ActieVerval Bigint/* ActieID */,
    Verantwoordelijke Smallint NOT NULL /* VerantwoordelijkeID */,
    SrtBijhouding Smallint NOT NULL /* SrtBijhoudingID */,
    GeautoriseerdeSrtPartij Integer/* SrtPartijID */,
    GeautoriseerdePartij Integer/* PartijID */,
    Toestand Smallint NOT NULL /* ToestandsID */,
    Oms Varchar(250) NOT NULL /* OmsEnumeratiewaarde */,
    BeperkingPopulatie Smallint/* BijhpopulatieID */,
    DatIngang Numeric(8) NOT NULL /* Dat */,
    DatEinde Numeric(8) NOT NULL /* Dat */,
    CONSTRAINT BR5757 PRIMARY KEY (ID),
    CONSTRAINT BR5739 UNIQUE (Bijhautorisatie, TsReg)
);
ALTER SEQUENCE AutAut.seq_His_Bijhautorisatie OWNED BY AutAut.His_Bijhautorisatie.ID;

CREATE SEQUENCE AutAut.seq_His_Bijhsituatie;
CREATE TABLE AutAut.His_Bijhsituatie (
    ID Bigint NOT NULL DEFAULT nextval('AutAut.seq_His_Bijhsituatie') /* His_BijhsituatieID */,
    Bijhsituatie Integer/* BijhsituatieID */,
    TsReg Timestamp/* Ts */,
    TsVerval Timestamp/* Ts */,
    ActieInh Bigint/* ActieID */,
    ActieVerval Bigint/* ActieID */,
    CONSTRAINT BR5760 PRIMARY KEY (ID),
    CONSTRAINT BR5745 UNIQUE (Bijhsituatie, TsReg)
);
ALTER SEQUENCE AutAut.seq_His_Bijhsituatie OWNED BY AutAut.His_Bijhsituatie.ID;

CREATE SEQUENCE AutAut.seq_His_Doelbinding;
CREATE TABLE AutAut.His_Doelbinding (
    ID Bigint NOT NULL DEFAULT nextval('AutAut.seq_His_Doelbinding') /* His_DoelbindingID */,
    Doelbinding Integer/* DoelbindingID */,
    DatAanvGel Numeric(8)/* Dat */,
    DatEindeGel Numeric(8)/* Dat */,
    TsReg Timestamp/* Ts */,
    TsVerval Timestamp/* Ts */,
    ActieInh Bigint/* ActieID */,
    ActieVerval Bigint/* ActieID */,
    ActieAanpGel Bigint/* ActieID */,
    Protocolleringsniveau Smallint NOT NULL /* ProtocolleringsniveauID */,
    TekstDoelbinding Text NOT NULL /* TekstDoelbinding */,
    Populatiecriterium Text NOT NULL /* Populatiecriterium */,
    IndVerstrbeperkingHonoreren boolean NOT NULL /* JaNee */,
    CONSTRAINT BR5003 PRIMARY KEY (ID),
    CONSTRAINT BR4977 UNIQUE (Doelbinding, TsReg, DatAanvGel)
);
ALTER SEQUENCE AutAut.seq_His_Doelbinding OWNED BY AutAut.His_Doelbinding.ID;

CREATE SEQUENCE AutAut.seq_His_Uitgeslotene;
CREATE TABLE AutAut.His_Uitgeslotene (
    ID Bigint NOT NULL DEFAULT nextval('AutAut.seq_His_Uitgeslotene') /* His_UitgesloteneID */,
    Uitgeslotene Integer/* AuthorisatiebesluitGegevense */,
    TsReg Timestamp/* Ts */,
    TsVerval Timestamp/* Ts */,
    ActieInh Bigint/* ActieID */,
    ActieVerval Bigint/* ActieID */,
    CONSTRAINT BR5795 PRIMARY KEY (ID),
    CONSTRAINT BR5789 UNIQUE (Uitgeslotene, TsReg)
);
ALTER SEQUENCE AutAut.seq_His_Uitgeslotene OWNED BY AutAut.His_Uitgeslotene.ID;

CREATE TABLE BRM.Regel (
    ID Integer NOT NULL /* RegelID */,
    Srt Smallint NOT NULL /* SrtRegelID */,
    Code Varchar(40) NOT NULL /* RegelCode */,
    Oms Varchar(250) NOT NULL /* OmsEnumeratiewaarde */,
    Specificatie Text NOT NULL /* Regelspecificatie */,
    CONSTRAINT BR5811 PRIMARY KEY (ID),
    CONSTRAINT BR6050 UNIQUE (Code),
    CONSTRAINT BR6051 UNIQUE (Specificatie),
    CONSTRAINT BR6052 UNIQUE (Oms)
);

CREATE TABLE BRM.Regeleffect (
    ID Smallint NOT NULL /* SrtRegelgedragID */,
    Naam Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    Oms Varchar(250) NOT NULL /* OmsEnumeratiewaarde */,
    DatAanvGel Numeric(8)/* Dat */,
    DatEindeGel Numeric(8)/* Dat */,
    CONSTRAINT BR5978 PRIMARY KEY (ID),
    CONSTRAINT BR5979 UNIQUE (Naam),
    CONSTRAINT BR5980 UNIQUE (Oms)
);

CREATE TABLE BRM.Regelimplementatie (
    ID Integer NOT NULL /* RegelimplementatieID */,
    Regel Integer NOT NULL /* RegelID */,
    SrtBer Smallint NOT NULL /* SrtBerID */,
    CONSTRAINT BR6060 PRIMARY KEY (ID),
    CONSTRAINT BR6062 UNIQUE (Regel, SrtBer)
);

CREATE SEQUENCE BRM.seq_Regelimplementatiesituatie;
CREATE TABLE BRM.Regelimplementatiesituatie (
    ID Integer NOT NULL DEFAULT nextval('BRM.seq_Regelimplementatiesituatie') /* RegeleffectID */,
    Regelimplementatie Integer NOT NULL /* RegelimplementatieID */,
    Bijhverantwoordelijkheid Smallint/* VerantwoordelijkeID */,
    IndOpgeschort boolean/* JaNee */,
    RdnOpschorting Smallint/* RdnOpschortingID */,
    Effect Smallint/* SrtRegelgedragID */,
    IndActief boolean/* JaNee */,
    RegelimplementatiesituatieSt Varchar(1) NOT NULL /* StatusHistorie */,
    CONSTRAINT BR5976 PRIMARY KEY (ID)
);
ALTER SEQUENCE BRM.seq_Regelimplementatiesituatie OWNED BY BRM.Regelimplementatiesituatie.ID;

CREATE TABLE BRM.SrtRegel (
    ID Smallint NOT NULL /* SrtRegelID */,
    Naam Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    Oms Varchar(250) NOT NULL /* OmsEnumeratiewaarde */,
    DatAanvGel Numeric(8)/* Dat */,
    DatEindeGel Numeric(8)/* Dat */,
    CONSTRAINT BR5973 PRIMARY KEY (ID),
    CONSTRAINT BR5974 UNIQUE (Naam),
    CONSTRAINT BR5975 UNIQUE (Oms)
);

CREATE SEQUENCE BRM.seq_His_Regelimplementatiesituat;
CREATE TABLE BRM.His_Regelimplementatiesituat (
    ID Bigint NOT NULL DEFAULT nextval('BRM.seq_His_Regelimplementatiesituat') /* His_Regelimplementatiesituat */,
    Regelimplementatiesituatie Integer/* RegeleffectID */,
    TsReg Timestamp/* Ts */,
    TsVerval Timestamp/* Ts */,
    ActieInh Bigint/* ActieID */,
    ActieVerval Bigint/* ActieID */,
    Bijhverantwoordelijkheid Smallint/* VerantwoordelijkeID */,
    IndOpgeschort boolean/* JaNee */,
    RdnOpschorting Smallint/* RdnOpschortingID */,
    Effect Smallint NOT NULL /* SrtRegelgedragID */,
    IndActief boolean NOT NULL /* JaNee */,
    CONSTRAINT BR5989 PRIMARY KEY (ID),
    CONSTRAINT BR5972 UNIQUE (Regelimplementatiesituatie, TsReg)
);
ALTER SEQUENCE BRM.seq_His_Regelimplementatiesituat OWNED BY BRM.His_Regelimplementatiesituat.ID;

CREATE SEQUENCE Ber.seq_Ber;
CREATE TABLE Ber.Ber (
    ID Bigint NOT NULL DEFAULT nextval('Ber.seq_Ber') /* BerID */,
    Data Text NOT NULL /* Berdata */,
    TsOntv Timestamp NOT NULL /* Ts */,
    AntwoordOp Bigint/* BerID */,
    Richting Smallint NOT NULL /* RichtingID */,
    CONSTRAINT BR4827 PRIMARY KEY (ID)
);
ALTER SEQUENCE Ber.seq_Ber OWNED BY Ber.Ber.ID;

CREATE TABLE Ber.Richting (
    ID Smallint NOT NULL /* RichtingID */,
    Naam Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    Oms Varchar(250) NOT NULL /* OmsEnumeratiewaarde */,
    CONSTRAINT BR5635 PRIMARY KEY (ID)
);

CREATE TABLE Ber.SrtBer (
    ID Smallint NOT NULL /* SrtBerID */,
    Naam Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    DatAanvGel Numeric(8)/* Dat */,
    DatEindeGel Numeric(8)/* Dat */,
    CONSTRAINT BR5027 PRIMARY KEY (ID),
    CONSTRAINT BR5028 UNIQUE (Naam)
);

CREATE TABLE Kern.AangAdresh (
    ID Smallint NOT NULL /* AangAdreshID */,
    Code Varchar(1) NOT NULL /* AangAdreshCode */,
    Naam Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    Oms Varchar(250) NOT NULL /* OmsEnumeratiewaarde */,
    CONSTRAINT BR4438 PRIMARY KEY (ID),
    CONSTRAINT BR4439 UNIQUE (Naam),
    CONSTRAINT BR4440 UNIQUE (Code)
);

CREATE TABLE Kern.AdellijkeTitel (
    ID Smallint NOT NULL /* AdellijkeTitelID */,
    Code Varchar(1) NOT NULL /* AdellijkeTitelCode */,
    NaamMannelijk Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    NaamVrouwelijk Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    CONSTRAINT BR4410 PRIMARY KEY (ID),
    CONSTRAINT BR4411 UNIQUE (NaamMannelijk),
    CONSTRAINT BR4412 UNIQUE (Code),
    CONSTRAINT BR4413 UNIQUE (NaamVrouwelijk)
);

CREATE TABLE Kern.AutVanAfgifteReisdoc (
    ID Integer NOT NULL /* AutVanAfgifteReisdocID */,
    Code Varchar(6) NOT NULL /* AutVanAfgifteReisdocCode */,
    Oms Varchar(250) NOT NULL /* OmsEnumeratiewaarde */,
    DatAanvGel Numeric(8)/* Dat */,
    DatEindeGel Numeric(8)/* Dat */,
    CONSTRAINT BR4466 PRIMARY KEY (ID),
    CONSTRAINT BR4467 UNIQUE (Code),
    CONSTRAINT BR4468 UNIQUE (Oms)
);

CREATE SEQUENCE Kern.seq_Actie;
CREATE TABLE Kern.Actie (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_Actie') /* ActieID */,
    Srt Integer NOT NULL /* SrtActieID */,
    Partij Integer NOT NULL /* PartijID */,
    Verdrag Integer/* VerdragID */,
    TsOntlening Timestamp/* Ts */,
    TsReg Timestamp NOT NULL /* Ts */,
    CONSTRAINT BR4403 PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_Actie OWNED BY Kern.Actie.ID;

CREATE SEQUENCE Kern.seq_Betr;
CREATE TABLE Kern.Betr (
    ID Integer NOT NULL DEFAULT nextval('Kern.seq_Betr') /* BetrID */,
    Relatie Bigint NOT NULL /* RelatieID */,
    Rol Smallint NOT NULL /* SrtBetrID */,
    Betrokkene Bigint NOT NULL /* PersID */,
    IndOuder boolean/* Ja */,
    OuderStatusHis Varchar(1) NOT NULL /* StatusHistorie */,
    IndOuderHeeftGezag boolean/* JaNee */,
    OuderlijkGezagStatusHis Varchar(1) NOT NULL /* StatusHistorie */,
    CONSTRAINT BR6104 PRIMARY KEY (ID),
    CONSTRAINT BR6105 UNIQUE (Relatie, Betrokkene)
);
ALTER SEQUENCE Kern.seq_Betr OWNED BY Kern.Betr.ID;

CREATE SEQUENCE Kern.seq_Bron;
CREATE TABLE Kern.Bron (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_Bron') /* BronID */,
    Actie Bigint NOT NULL /* ActieID */,
    Doc Bigint NOT NULL /* DocID */,
    CONSTRAINT BR4504 PRIMARY KEY (ID),
    CONSTRAINT BR4481 UNIQUE (Actie, Doc)
);
ALTER SEQUENCE Kern.seq_Bron OWNED BY Kern.Bron.ID;

CREATE TABLE Kern.CategorieSrtActie (
    ID Smallint NOT NULL /* CategorieSrtActieID */,
    Naam Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    Oms Varchar(250) NOT NULL /* OmsEnumeratiewaarde */,
    DatAanvGel Numeric(8)/* Dat */,
    DatEindeGel Numeric(8)/* Dat */,
    CONSTRAINT BR5981 PRIMARY KEY (ID),
    CONSTRAINT BR5982 UNIQUE (Naam),
    CONSTRAINT BR5983 UNIQUE (Oms)
);

CREATE TABLE Kern.CategorieSrtDoc (
    ID Smallint NOT NULL /* CategorieSrtDocID */,
    Naam Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    Oms Varchar(250) NOT NULL /* OmsEnumeratiewaarde */,
    DatAanvGel Numeric(8)/* Dat */,
    DatEindeGel Numeric(8)/* Dat */,
    CONSTRAINT BR5984 PRIMARY KEY (ID),
    CONSTRAINT BR5985 UNIQUE (Naam),
    CONSTRAINT BR5986 UNIQUE (Oms)
);

CREATE TABLE Kern.DbObject (
    ID Integer NOT NULL /* DbObjectID */,
    Naam Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    Srt Smallint NOT NULL /* SrtDbObjectID */,
    Ouder Integer/* DbObjectID */,
    JavaIdentifier Varchar(80) NOT NULL /* IdentifierLang */,
    DatAanvGel Numeric(8)/* Dat */,
    DatEindeGel Numeric(8)/* Dat */,
    CONSTRAINT BR5659 PRIMARY KEY (ID)
);

CREATE SEQUENCE Kern.seq_Doc;
CREATE TABLE Kern.Doc (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_Doc') /* DocID */,
    Srt Integer NOT NULL /* SrtDocID */,
    Ident Varchar(20)/* DocIdent */,
    Aktenr Varchar(7)/* Aktenr */,
    Oms Varchar(80)/* DocOms */,
    Partij Integer/* PartijID */,
    DocStatusHis Varchar(1) NOT NULL /* StatusHistorie */,
    CONSTRAINT BR4416 PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_Doc OWNED BY Kern.Doc.ID;

CREATE TABLE Kern.Element (
    ID Integer NOT NULL /* ElementID */,
    Naam Varchar(80) NOT NULL /* LangeNaamEnumeratiewaarde */,
    Srt Smallint NOT NULL /* SrtElementID */,
    Ouder Integer/* ElementID */,
    DatAanvGel Numeric(8)/* Dat */,
    DatEindeGel Numeric(8)/* Dat */,
    CONSTRAINT BR4422 PRIMARY KEY (ID),
    CONSTRAINT BR4423 UNIQUE (Srt, Naam, Ouder)
);

CREATE TABLE Kern.FunctieAdres (
    ID Smallint NOT NULL /* SrtAdresID */,
    Code Varchar(1) NOT NULL /* SrtAdresCode */,
    Naam Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    CONSTRAINT BR4435 PRIMARY KEY (ID),
    CONSTRAINT BR4436 UNIQUE (Naam),
    CONSTRAINT BR4437 UNIQUE (Code)
);

CREATE SEQUENCE Kern.seq_GegevenInOnderzoek;
CREATE TABLE Kern.GegevenInOnderzoek (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_GegevenInOnderzoek') /* GegevenInOnderzoekID */,
    Onderzoek Bigint NOT NULL /* OnderzoekID */,
    SrtGegeven Integer NOT NULL /* DbObjectID */,
    Ident Bigint NOT NULL /* Sleutelwaarde */,
    CONSTRAINT BR4507 PRIMARY KEY (ID),
    CONSTRAINT BR4480 UNIQUE (Onderzoek, SrtGegeven)
);
ALTER SEQUENCE Kern.seq_GegevenInOnderzoek OWNED BY Kern.GegevenInOnderzoek.ID;

CREATE TABLE Kern.Geslachtsaand (
    ID Smallint NOT NULL /* GeslachtsaandID */,
    Code Varchar(1) NOT NULL /* GeslachtsaandCode */,
    Naam Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    Oms Varchar(250)/* OmsEnumeratiewaarde */,
    CONSTRAINT BR4482 PRIMARY KEY (ID),
    CONSTRAINT BR4483 UNIQUE (Code),
    CONSTRAINT BR4484 UNIQUE (Naam)
);

CREATE TABLE Kern.Land (
    ID Integer NOT NULL /* LandID */,
    Naam Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    ISO31661Alpha2 Varchar(2)/* ISO31661Alpha2 */,
    Landcode Varchar(4) NOT NULL /* Landcode */,
    DatAanvGel Numeric(8)/* Dat */,
    DatEindeGel Numeric(8)/* Dat */,
    CONSTRAINT BR4399 PRIMARY KEY (ID),
    CONSTRAINT BR4400 UNIQUE (Naam)
);

CREATE SEQUENCE Kern.seq_MultiRealiteitRegel;
CREATE TABLE Kern.MultiRealiteitRegel (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_MultiRealiteitRegel') /* MultiRealiteitregelID */,
    GeldigVoor Bigint NOT NULL /* PersID */,
    Srt Smallint NOT NULL /* SrtMultiRealiteitRegelID */,
    Pers Bigint/* PersID */,
    MultiRealiteitPers Bigint/* PersID */,
    Relatie Bigint/* RelatieID */,
    Betr Integer/* BetrID */,
    MultiRealiteitRegelStatusHis Varchar(1) NOT NULL /* StatusHistorie */,
    CONSTRAINT BR4488 PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_MultiRealiteitRegel OWNED BY Kern.MultiRealiteitRegel.ID;

CREATE TABLE Kern.Nation (
    ID Integer NOT NULL /* NationID */,
    Naam Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    Nationcode Varchar(4) NOT NULL /* Nationcode */,
    DatAanvGel Numeric(8)/* Dat */,
    DatEindeGel Numeric(8)/* Dat */,
    CONSTRAINT BR4404 PRIMARY KEY (ID),
    CONSTRAINT BR4405 UNIQUE (Naam)
);

CREATE SEQUENCE Kern.seq_Onderzoek;
CREATE TABLE Kern.Onderzoek (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_Onderzoek') /* OnderzoekID */,
    DatBegin Numeric(8)/* Dat */,
    DatEinde Numeric(8)/* Dat */,
    Oms Text/* OnderzoekOms */,
    OnderzoekStatusHis Varchar(1) NOT NULL /* StatusHistorie */,
    CONSTRAINT BR4421 PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_Onderzoek OWNED BY Kern.Onderzoek.ID;

CREATE SEQUENCE Kern.seq_Partij;
CREATE TABLE Kern.Partij (
    ID Integer NOT NULL DEFAULT nextval('Kern.seq_Partij') /* PartijID */,
    Naam Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    Srt Integer/* SrtPartijID */,
    DatAanv Numeric(8)/* Dat */,
    DatEinde Numeric(8)/* Dat */,
    Sector Integer/* SectorID */,
    PartijStatusHis Varchar(1) NOT NULL /* StatusHistorie */,
    VoortzettendeGem Integer/* PartijID */,
    Gemcode Varchar(4)/* Gemcode */,
    OnderdeelVan Integer/* PartijID */,
    GemStatusHis Varchar(1) NOT NULL /* StatusHistorie */,
    CONSTRAINT BR4417 PRIMARY KEY (ID),
    CONSTRAINT BR4418 UNIQUE (Naam)
);
ALTER SEQUENCE Kern.seq_Partij OWNED BY Kern.Partij.ID;

CREATE SEQUENCE Kern.seq_PartijRol;
CREATE TABLE Kern.PartijRol (
    ID Integer NOT NULL DEFAULT nextval('Kern.seq_PartijRol') /* PartijRolID */,
    Partij Integer NOT NULL /* PartijID */,
    Rol Integer NOT NULL /* RolID */,
    PartijRolStatusHis Varchar(1) NOT NULL /* StatusHistorie */,
    CONSTRAINT BR4495 PRIMARY KEY (ID),
    CONSTRAINT BR4688 UNIQUE (Partij, Rol)
);
ALTER SEQUENCE Kern.seq_PartijRol OWNED BY Kern.PartijRol.ID;

CREATE SEQUENCE Kern.seq_Pers;
CREATE TABLE Kern.Pers (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_Pers') /* PersID */,
    Srt Smallint NOT NULL /* SrtPersID */,
    BSN Varchar(9)/* BSN */,
    ANr Varchar(10)/* ANr */,
    IDsStatusHis Varchar(1) NOT NULL /* StatusHistorie */,
    Geslachtsaand Smallint/* GeslachtsaandID */,
    GeslachtsaandStatusHis Varchar(1) NOT NULL /* StatusHistorie */,
    Predikaat Smallint/* PredikaatID */,
    Voornamen Varchar(200)/* Voornamen */,
    Voorvoegsel Varchar(10)/* Voorvoegsel */,
    Scheidingsteken Varchar(1)/* Scheidingsteken */,
    AdellijkeTitel Smallint/* AdellijkeTitelID */,
    Geslnaam Varchar(200)/* Geslnaam */,
    IndNreeksAlsGeslnaam boolean/* JaNee */,
    IndAlgoritmischAfgeleid boolean/* JaNee */,
    SamengesteldeNaamStatusHis Varchar(1) NOT NULL /* StatusHistorie */,
    GebrGeslnaamEGP Smallint/* WijzeGebruikGeslnaamID */,
    IndAanschrMetAdellijkeTitels boolean/* JaNee */,
    IndAanschrAlgoritmischAfgele boolean/* JaNee */,
    PredikaatAanschr Smallint/* PredikaatID */,
    VoornamenAanschr Varchar(200)/* Voornamen */,
    VoorvoegselAanschr Varchar(10)/* Voorvoegsel */,
    ScheidingstekenAanschr Varchar(1)/* Scheidingsteken */,
    GeslnaamAanschr Varchar(200)/* Geslnaam */,
    AanschrStatusHis Varchar(1) NOT NULL /* StatusHistorie */,
    DatGeboorte Numeric(8)/* Dat */,
    GemGeboorte Integer/* PartijID */,
    WplGeboorte Integer/* PlaatsID */,
    BLGeboorteplaats Varchar(40)/* BLPlaats */,
    BLRegioGeboorte Varchar(35)/* BLRegio */,
    LandGeboorte Integer/* LandID */,
    OmsGeboorteloc Varchar(40)/* LocOms */,
    GeboorteStatusHis Varchar(1) NOT NULL /* StatusHistorie */,
    DatOverlijden Numeric(8)/* Dat */,
    GemOverlijden Integer/* PartijID */,
    WplOverlijden Integer/* PlaatsID */,
    BLPlaatsOverlijden Varchar(40)/* BLPlaats */,
    BLRegioOverlijden Varchar(35)/* BLRegio */,
    LandOverlijden Integer/* LandID */,
    OmsLocOverlijden Varchar(40)/* LocOms */,
    OverlijdenStatusHis Varchar(1) NOT NULL /* StatusHistorie */,
    Verblijfsr Integer/* VerblijfsrID */,
    DatAanvVerblijfsr Numeric(8)/* Dat */,
    DatVoorzEindeVerblijfsr Numeric(8)/* Dat */,
    DatAanvAaneenslVerblijfsr Numeric(8)/* Dat */,
    VerblijfsrStatusHis Varchar(1) NOT NULL /* StatusHistorie */,
    IndUitslNLKiesr boolean/* JaNee */,
    DatEindeUitslNLKiesr Numeric(8)/* Dat */,
    UitslNLKiesrStatusHis Varchar(1) NOT NULL /* StatusHistorie */,
    IndDeelnEUVerkiezingen boolean/* JaNee */,
    DatAanlAanpDeelnEUVerkiezing Numeric(8)/* Dat */,
    DatEindeUitslEUKiesr Numeric(8)/* Dat */,
    EUVerkiezingenStatusHis Varchar(1) NOT NULL /* StatusHistorie */,
    Verantwoordelijke Smallint/* VerantwoordelijkeID */,
    BijhverantwoordelijkheidStat Varchar(1) NOT NULL /* StatusHistorie */,
    RdnOpschortingBijhouding Smallint/* RdnOpschortingID */,
    OpschortingStatusHis Varchar(1) NOT NULL /* StatusHistorie */,
    Bijhgem Integer/* PartijID */,
    DatInschrInGem Numeric(8)/* Dat */,
    IndOnverwDocAanw boolean/* JaNee */,
    BijhgemStatusHis Varchar(1) NOT NULL /* StatusHistorie */,
    GemPK Integer/* PartijID */,
    IndPKVolledigGeconv boolean/* JaNee */,
    PKStatusHis Varchar(1) NOT NULL /* StatusHistorie */,
    LandVanwaarGevestigd Integer/* LandID */,
    DatVestigingInNederland Numeric(8)/* Dat */,
    ImmigratieStatusHis Varchar(1) NOT NULL /* StatusHistorie */,
    DatInschr Numeric(8)/* Dat */,
    Versienr Bigint/* Versienr */,
    VorigePers Bigint/* PersID */,
    VolgendePers Bigint/* PersID */,
    InschrStatusHis Varchar(1) NOT NULL /* StatusHistorie */,
    TijdstipLaatsteWijz Timestamp/* Ts */,
    IndGegevensInOnderzoek boolean/* JaNee */,
    CONSTRAINT BR4390 PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_Pers OWNED BY Kern.Pers.ID;

CREATE SEQUENCE Kern.seq_PersAdres;
CREATE TABLE Kern.PersAdres (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_PersAdres') /* PersAdresID */,
    Pers Bigint NOT NULL /* PersID */,
    Srt Smallint/* SrtAdresID */,
    RdnWijz Smallint/* RdnWijzAdresID */,
    AangAdresh Smallint/* AangAdreshID */,
    DatAanvAdresh Numeric(8)/* Dat */,
    AdresseerbaarObject Varchar(16)/* AandAdresseerbaarObject */,
    IdentcodeNraand Varchar(16)/* IdentcodeNraand */,
    Gem Integer/* PartijID */,
    NOR Varchar(80)/* NOR */,
    AfgekorteNOR Varchar(24)/* AfgekorteNOR */,
    Gemdeel Varchar(24)/* Gemdeel */,
    Huisnr Varchar(5)/* Huisnr */,
    Huisletter Varchar(1)/* Huisletter */,
    Huisnrtoevoeging Varchar(4)/* Huisnrtoevoeging */,
    Postcode Varchar(6)/* Postcode */,
    Wpl Integer/* PlaatsID */,
    LoctovAdres Varchar(2)/* AandBijHuisnr */,
    LocOms Varchar(40)/* LocOms */,
    BLAdresRegel1 Varchar(40)/* Adresregel */,
    BLAdresRegel2 Varchar(40)/* Adresregel */,
    BLAdresRegel3 Varchar(40)/* Adresregel */,
    BLAdresRegel4 Varchar(40)/* Adresregel */,
    BLAdresRegel5 Varchar(40)/* Adresregel */,
    BLAdresRegel6 Varchar(40)/* Adresregel */,
    Land Integer/* LandID */,
    DatVertrekUitNederland Numeric(8)/* Dat */,
    PersAdresStatusHis Varchar(1) NOT NULL /* StatusHistorie */,
    CONSTRAINT BR4434 PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_PersAdres OWNED BY Kern.PersAdres.ID;

CREATE SEQUENCE Kern.seq_PersGeslnaamcomp;
CREATE TABLE Kern.PersGeslnaamcomp (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_PersGeslnaamcomp') /* PersGeslnaamID */,
    Pers Bigint NOT NULL /* PersID */,
    Volgnr Integer NOT NULL /* Volgnr */,
    Voorvoegsel Varchar(10)/* Voorvoegsel */,
    Scheidingsteken Varchar(1)/* Scheidingsteken */,
    Naam Varchar(200)/* Geslnaamcomp */,
    Predikaat Smallint/* PredikaatID */,
    AdellijkeTitel Smallint/* AdellijkeTitelID */,
    PersGeslnaamcompStatusHis Varchar(1) NOT NULL /* StatusHistorie */,
    CONSTRAINT BR4391 PRIMARY KEY (ID),
    CONSTRAINT BR4392 UNIQUE (Pers, Volgnr)
);
ALTER SEQUENCE Kern.seq_PersGeslnaamcomp OWNED BY Kern.PersGeslnaamcomp.ID;

CREATE SEQUENCE Kern.seq_PersIndicatie;
CREATE TABLE Kern.PersIndicatie (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_PersIndicatie') /* PersIndicatieID */,
    Pers Bigint NOT NULL /* PersID */,
    Srt Smallint NOT NULL /* SrtIndicatieID */,
    Waarde boolean/* Ja */,
    PersIndicatieStatusHis Varchar(1) NOT NULL /* StatusHistorie */,
    CONSTRAINT BR4455 PRIMARY KEY (ID),
    CONSTRAINT BR4456 UNIQUE (Pers, Srt)
);
ALTER SEQUENCE Kern.seq_PersIndicatie OWNED BY Kern.PersIndicatie.ID;

CREATE SEQUENCE Kern.seq_PersNation;
CREATE TABLE Kern.PersNation (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_PersNation') /* PersNationID */,
    Pers Bigint NOT NULL /* PersID */,
    Nation Integer NOT NULL /* NationID */,
    RdnVerlies Integer/* RdnVerliesNLNationID */,
    RdnVerk Integer/* RdnVerkNLNationID */,
    PersNationStatusHis Varchar(1) NOT NULL /* StatusHistorie */,
    CONSTRAINT BR4414 PRIMARY KEY (ID),
    CONSTRAINT BR4415 UNIQUE (Pers, Nation)
);
ALTER SEQUENCE Kern.seq_PersNation OWNED BY Kern.PersNation.ID;

CREATE SEQUENCE Kern.seq_PersReisdoc;
CREATE TABLE Kern.PersReisdoc (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_PersReisdoc') /* ReisdocID */,
    Pers Bigint NOT NULL /* PersID */,
    Srt Integer NOT NULL /* SrtNLReisdocID */,
    Nr Varchar(9)/* ReisdocNr */,
    DatUitgifte Numeric(8)/* Dat */,
    AutVanAfgifte Integer/* AutVanAfgifteReisdocID */,
    DatVoorzeEindeGel Numeric(8)/* Dat */,
    DatInhingVermissing Numeric(8)/* Dat */,
    RdnVervallen Smallint/* RdnOntbrReisdocID */,
    LengteHouder Numeric(3)/* LengteInCm */,
    PersReisdocStatusHis Varchar(1) NOT NULL /* StatusHistorie */,
    CONSTRAINT BR4446 PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_PersReisdoc OWNED BY Kern.PersReisdoc.ID;

CREATE SEQUENCE Kern.seq_PersVerificatie;
CREATE TABLE Kern.PersVerificatie (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_PersVerificatie') /* VerificatieID */,
    Geverifieerde Bigint NOT NULL /* PersID */,
    Srt Integer/* SrtVerificatieID */,
    Dat Numeric(8)/* Dat */,
    PersVerificatieStatusHis Varchar(1) NOT NULL /* StatusHistorie */,
    CONSTRAINT BR4459 PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_PersVerificatie OWNED BY Kern.PersVerificatie.ID;

CREATE SEQUENCE Kern.seq_PersVoornaam;
CREATE TABLE Kern.PersVoornaam (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_PersVoornaam') /* PersVoornaamID */,
    Pers Bigint NOT NULL /* PersID */,
    Volgnr Integer NOT NULL /* Volgnr */,
    Naam Varchar(40)/* Voornaam */,
    PersVoornaamStatusHis Varchar(1) NOT NULL /* StatusHistorie */,
    CONSTRAINT BR4393 PRIMARY KEY (ID),
    CONSTRAINT BR4394 UNIQUE (Pers, Volgnr)
);
ALTER SEQUENCE Kern.seq_PersVoornaam OWNED BY Kern.PersVoornaam.ID;

CREATE TABLE Kern.Plaats (
    ID Integer NOT NULL /* PlaatsID */,
    Naam Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    Wplcode Varchar(4) NOT NULL /* Wplcode */,
    DatAanvGel Numeric(8)/* Dat */,
    DatEindeGel Numeric(8)/* Dat */,
    CONSTRAINT BR4397 PRIMARY KEY (ID),
    CONSTRAINT BR4398 UNIQUE (Wplcode)
);

CREATE TABLE Kern.Predikaat (
    ID Smallint NOT NULL /* PredikaatID */,
    Code Varchar(1) NOT NULL /* PredikaatCode */,
    NaamMannelijk Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    NaamVrouwelijk Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    CONSTRAINT BR4406 PRIMARY KEY (ID),
    CONSTRAINT BR4407 UNIQUE (Code),
    CONSTRAINT BR4408 UNIQUE (NaamMannelijk),
    CONSTRAINT BR4409 UNIQUE (NaamVrouwelijk)
);

CREATE TABLE Kern.RdnBeeindRelatie (
    ID Smallint NOT NULL /* RdnBeeindRelatieID */,
    Code Varchar(1) NOT NULL /* RdnBeeindRelatieCode */,
    Oms Varchar(250) NOT NULL /* OmsEnumeratiewaarde */,
    CONSTRAINT BR4428 PRIMARY KEY (ID),
    CONSTRAINT BR4429 UNIQUE (Oms)
);

CREATE TABLE Kern.RdnOpschorting (
    ID Smallint NOT NULL /* RdnOpschortingID */,
    Code Varchar(1) NOT NULL /* RdnOpschortingCode */,
    Naam Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    CONSTRAINT BR4449 PRIMARY KEY (ID),
    CONSTRAINT BR4450 UNIQUE (Code),
    CONSTRAINT BR4451 UNIQUE (Naam)
);

CREATE TABLE Kern.RdnVerkNLNation (
    ID Integer NOT NULL /* RdnVerkNLNationID */,
    Naam Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    DatAanvGel Numeric(8)/* Dat */,
    DatEindeGel Numeric(8)/* Dat */,
    CONSTRAINT BR4430 PRIMARY KEY (ID),
    CONSTRAINT BR4431 UNIQUE (Naam)
);

CREATE TABLE Kern.RdnVerliesNLNation (
    ID Integer NOT NULL /* RdnVerliesNLNationID */,
    Naam Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    DatAanvGel Numeric(8)/* Dat */,
    DatEindeGel Numeric(8)/* Dat */,
    CONSTRAINT BR4432 PRIMARY KEY (ID),
    CONSTRAINT BR4433 UNIQUE (Naam)
);

CREATE TABLE Kern.RdnVervallenReisdoc (
    ID Smallint NOT NULL /* RdnOntbrReisdocID */,
    Code Varchar(1) NOT NULL /* RdnOntbrReisdocCode */,
    Naam Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    CONSTRAINT BR4469 PRIMARY KEY (ID),
    CONSTRAINT BR4470 UNIQUE (Code),
    CONSTRAINT BR4471 UNIQUE (Naam)
);

CREATE TABLE Kern.RdnWijzAdres (
    ID Smallint NOT NULL /* RdnWijzAdresID */,
    Code Varchar(1) NOT NULL /* RdnWijzAdresCode */,
    Naam Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    CONSTRAINT BR4460 PRIMARY KEY (ID),
    CONSTRAINT BR4461 UNIQUE (Code),
    CONSTRAINT BR4462 UNIQUE (Naam)
);

CREATE SEQUENCE Kern.seq_Relatie;
CREATE TABLE Kern.Relatie (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_Relatie') /* RelatieID */,
    Srt Integer NOT NULL /* SrtRelatieID */,
    DatAanv Numeric(8)/* Dat */,
    GemAanv Integer/* PartijID */,
    WplAanv Integer/* PlaatsID */,
    BLPlaatsAanv Varchar(40)/* BLPlaats */,
    BLRegioAanv Varchar(35)/* BLRegio */,
    LandAanv Integer/* LandID */,
    OmsLocAanv Varchar(40)/* LocOms */,
    RdnEinde Smallint/* RdnBeeindRelatieID */,
    DatEinde Numeric(8)/* Dat */,
    GemEinde Integer/* PartijID */,
    WplEinde Integer/* PlaatsID */,
    BLPlaatsEinde Varchar(40)/* BLPlaats */,
    BLRegioEinde Varchar(35)/* BLRegio */,
    LandEinde Integer/* LandID */,
    OmsLocEinde Varchar(40)/* LocOms */,
    RelatieStatusHis Varchar(1) NOT NULL /* StatusHistorie */,
    CONSTRAINT BR4424 PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_Relatie OWNED BY Kern.Relatie.ID;

CREATE TABLE Kern.Rol (
    ID Integer NOT NULL /* RolID */,
    Naam Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    DatAanvGel Numeric(8)/* Dat */,
    DatEindeGel Numeric(8)/* Dat */,
    CONSTRAINT BR4491 PRIMARY KEY (ID),
    CONSTRAINT BR4492 UNIQUE (Naam)
);

CREATE SEQUENCE Kern.seq_Sector;
CREATE TABLE Kern.Sector (
    ID Integer NOT NULL DEFAULT nextval('Kern.seq_Sector') /* SectorID */,
    Naam Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    DatAanvGel Numeric(8)/* Dat */,
    DatEindeGel Numeric(8)/* Dat */,
    CONSTRAINT BR4494 PRIMARY KEY (ID)
);
ALTER SEQUENCE Kern.seq_Sector OWNED BY Kern.Sector.ID;

CREATE TABLE Kern.SrtDbObject (
    ID Smallint NOT NULL /* SrtDbObjectID */,
    Naam Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    DatAanvGel Numeric(8)/* Dat */,
    DatEindeGel Numeric(8)/* Dat */,
    CONSTRAINT BR5660 PRIMARY KEY (ID)
);

CREATE TABLE Kern.SrtNLReisdoc (
    ID Integer NOT NULL /* SrtNLReisdocID */,
    Code Varchar(2) NOT NULL /* SrtNLReisdocCode */,
    Oms Varchar(250) NOT NULL /* OmsEnumeratiewaarde */,
    DatAanvGel Numeric(8)/* Dat */,
    DatEindeGel Numeric(8)/* Dat */,
    CONSTRAINT BR4463 PRIMARY KEY (ID),
    CONSTRAINT BR4464 UNIQUE (Code),
    CONSTRAINT BR4465 UNIQUE (Oms)
);

CREATE TABLE Kern.SrtActie (
    ID Integer NOT NULL /* SrtActieID */,
    Naam Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    CategorieSrtActie Smallint NOT NULL /* CategorieSrtActieID */,
    CONSTRAINT BR4401 PRIMARY KEY (ID),
    CONSTRAINT BR4402 UNIQUE (Naam)
);

CREATE TABLE Kern.SrtBetr (
    ID Smallint NOT NULL /* SrtBetrID */,
    Code Varchar(1) NOT NULL /* SrtBetrCode */,
    Naam Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    CONSTRAINT BR4476 PRIMARY KEY (ID),
    CONSTRAINT BR4477 UNIQUE (Code),
    CONSTRAINT BR4478 UNIQUE (Naam)
);

CREATE TABLE Kern.SrtDoc (
    ID Integer NOT NULL /* SrtDocID */,
    Oms Varchar(250) NOT NULL /* OmsEnumeratiewaarde */,
    CategorieSrtDoc Smallint NOT NULL /* CategorieSrtDocID */,
    CONSTRAINT BR4419 PRIMARY KEY (ID),
    CONSTRAINT BR4420 UNIQUE (Oms)
);

CREATE TABLE Kern.SrtElement (
    ID Smallint NOT NULL /* SrtElementID */,
    Naam Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    CONSTRAINT BR4457 PRIMARY KEY (ID),
    CONSTRAINT BR4458 UNIQUE (Naam)
);

CREATE TABLE Kern.SrtIndicatie (
    ID Smallint NOT NULL /* SrtIndicatieID */,
    Naam Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    IndMaterieleHistorieVanToepa boolean NOT NULL /* JaNee */,
    CONSTRAINT BR4447 PRIMARY KEY (ID),
    CONSTRAINT BR4448 UNIQUE (Naam)
);

CREATE TABLE Kern.SrtMultiRealiteitRegel (
    ID Smallint NOT NULL /* SrtMultiRealiteitRegelID */,
    Naam Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    Oms Varchar(250) NOT NULL /* OmsEnumeratiewaarde */,
    CONSTRAINT BR4489 PRIMARY KEY (ID),
    CONSTRAINT BR4490 UNIQUE (Naam)
);

CREATE TABLE Kern.SrtPartij (
    ID Integer NOT NULL /* SrtPartijID */,
    Naam Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    DatAanvGel Numeric(8)/* Dat */,
    DatEindeGel Numeric(8)/* Dat */,
    CONSTRAINT BR4493 PRIMARY KEY (ID)
);

CREATE TABLE Kern.SrtPers (
    ID Smallint NOT NULL /* SrtPersID */,
    Code Varchar(1) NOT NULL /* SrtPersCode */,
    Naam Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    Oms Varchar(250)/* OmsEnumeratiewaarde */,
    DatAanvGel Numeric(8)/* Dat */,
    DatEindeGel Numeric(8)/* Dat */,
    CONSTRAINT BR4485 PRIMARY KEY (ID),
    CONSTRAINT BR4486 UNIQUE (Code),
    CONSTRAINT BR4487 UNIQUE (Naam)
);

CREATE TABLE Kern.SrtRelatie (
    ID Integer NOT NULL /* SrtRelatieID */,
    Code Varchar(1) NOT NULL /* SrtRelatieCode */,
    Naam Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    Oms Varchar(250)/* OmsEnumeratiewaarde */,
    CONSTRAINT BR4425 PRIMARY KEY (ID),
    CONSTRAINT BR4426 UNIQUE (Naam),
    CONSTRAINT BR4427 UNIQUE (Code)
);

CREATE TABLE Kern.SrtVerificatie (
    ID Integer NOT NULL /* SrtVerificatieID */,
    Naam Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    CONSTRAINT BR4472 PRIMARY KEY (ID),
    CONSTRAINT BR4473 UNIQUE (Naam)
);

CREATE TABLE Kern.Verantwoordelijke (
    ID Smallint NOT NULL /* VerantwoordelijkeID */,
    Code Varchar(1) NOT NULL /* VerantwoordelijkeCode */,
    Naam Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    CONSTRAINT BR4443 PRIMARY KEY (ID),
    CONSTRAINT BR4444 UNIQUE (Naam),
    CONSTRAINT BR4445 UNIQUE (Code)
);

CREATE TABLE Kern.Verblijfsr (
    ID Integer NOT NULL /* VerblijfsrID */,
    Oms Varchar(250) NOT NULL /* OmsEnumeratiewaarde */,
    DatAanvGel Numeric(8)/* Dat */,
    DatEindeGel Numeric(8)/* Dat */,
    CONSTRAINT BR4441 PRIMARY KEY (ID),
    CONSTRAINT BR4442 UNIQUE (Oms)
);

CREATE TABLE Kern.Verdrag (
    ID Integer NOT NULL /* VerdragID */,
    Oms Varchar(250) NOT NULL /* OmsEnumeratiewaarde */,
    DatAanvGel Numeric(8)/* Dat */,
    DatEindeGel Numeric(8)/* Dat */,
    CONSTRAINT BR4474 PRIMARY KEY (ID),
    CONSTRAINT BR4475 UNIQUE (Oms)
);

CREATE TABLE Kern.WijzeGebruikGeslnaam (
    ID Smallint NOT NULL /* WijzeGebruikGeslnaamID */,
    Code Varchar(1) NOT NULL /* WijzeGebruikGeslnaamCode */,
    Naam Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    Oms Varchar(250)/* OmsEnumeratiewaarde */,
    CONSTRAINT BR4452 PRIMARY KEY (ID),
    CONSTRAINT BR4453 UNIQUE (Code),
    CONSTRAINT BR4454 UNIQUE (Naam)
);

CREATE SEQUENCE Kern.seq_His_BetrOuder;
CREATE TABLE Kern.His_BetrOuder (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_His_BetrOuder') /* His_BetrOuderID */,
    Betr Integer/* BetrID */,
    DatAanvGel Numeric(8)/* Dat */,
    DatEindeGel Numeric(8)/* Dat */,
    TsReg Timestamp/* Ts */,
    TsVerval Timestamp/* Ts */,
    ActieInh Bigint/* ActieID */,
    ActieVerval Bigint/* ActieID */,
    ActieAanpGel Bigint/* ActieID */,
    IndOuder boolean NOT NULL /* Ja */,
    CONSTRAINT BR4510 PRIMARY KEY (ID),
    CONSTRAINT BR4032 UNIQUE (Betr, TsReg, DatAanvGel)
);
ALTER SEQUENCE Kern.seq_His_BetrOuder OWNED BY Kern.His_BetrOuder.ID;

CREATE SEQUENCE Kern.seq_His_BetrOuderlijkGezag;
CREATE TABLE Kern.His_BetrOuderlijkGezag (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_His_BetrOuderlijkGezag') /* His_BetrOuderlijkGezagID */,
    Betr Integer/* BetrID */,
    DatAanvGel Numeric(8)/* Dat */,
    DatEindeGel Numeric(8)/* Dat */,
    TsReg Timestamp/* Ts */,
    TsVerval Timestamp/* Ts */,
    ActieInh Bigint/* ActieID */,
    ActieVerval Bigint/* ActieID */,
    ActieAanpGel Bigint/* ActieID */,
    IndOuderHeeftGezag boolean NOT NULL /* JaNee */,
    CONSTRAINT BR4513 PRIMARY KEY (ID),
    CONSTRAINT BR4042 UNIQUE (Betr, TsReg, DatAanvGel)
);
ALTER SEQUENCE Kern.seq_His_BetrOuderlijkGezag OWNED BY Kern.His_BetrOuderlijkGezag.ID;

CREATE SEQUENCE Kern.seq_His_Doc;
CREATE TABLE Kern.His_Doc (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_His_Doc') /* His_DocID */,
    Doc Bigint/* DocID */,
    TsReg Timestamp/* Ts */,
    TsVerval Timestamp/* Ts */,
    ActieInh Bigint/* ActieID */,
    ActieVerval Bigint/* ActieID */,
    Ident Varchar(20)/* DocIdent */,
    Aktenr Varchar(7)/* Aktenr */,
    Oms Varchar(80)/* DocOms */,
    Partij Integer NOT NULL /* PartijID */,
    CONSTRAINT BR4516 PRIMARY KEY (ID),
    CONSTRAINT BR4052 UNIQUE (Doc, TsReg)
);
ALTER SEQUENCE Kern.seq_His_Doc OWNED BY Kern.His_Doc.ID;

CREATE SEQUENCE Kern.seq_His_MultiRealiteitRegel;
CREATE TABLE Kern.His_MultiRealiteitRegel (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_His_MultiRealiteitRegel') /* His_MultiRealiteitRegelID */,
    MultiRealiteitRegel Bigint/* MultiRealiteitregelID */,
    TsReg Timestamp/* Ts */,
    TsVerval Timestamp/* Ts */,
    ActieInh Bigint/* ActieID */,
    ActieVerval Bigint/* ActieID */,
    CONSTRAINT BR5480 PRIMARY KEY (ID),
    CONSTRAINT BR5477 UNIQUE (MultiRealiteitRegel, TsReg)
);
ALTER SEQUENCE Kern.seq_His_MultiRealiteitRegel OWNED BY Kern.His_MultiRealiteitRegel.ID;

CREATE SEQUENCE Kern.seq_His_Onderzoek;
CREATE TABLE Kern.His_Onderzoek (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_His_Onderzoek') /* His_OnderzoekID */,
    Onderzoek Bigint/* OnderzoekID */,
    TsReg Timestamp/* Ts */,
    TsVerval Timestamp/* Ts */,
    ActieInh Bigint/* ActieID */,
    ActieVerval Bigint/* ActieID */,
    DatBegin Numeric(8) NOT NULL /* Dat */,
    DatEinde Numeric(8)/* Dat */,
    Oms Text/* OnderzoekOms */,
    CONSTRAINT BR4522 PRIMARY KEY (ID),
    CONSTRAINT BR4076 UNIQUE (Onderzoek, TsReg)
);
ALTER SEQUENCE Kern.seq_His_Onderzoek OWNED BY Kern.His_Onderzoek.ID;

CREATE SEQUENCE Kern.seq_His_Partij;
CREATE TABLE Kern.His_Partij (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_His_Partij') /* His_PartijID */,
    Partij Integer/* PartijID */,
    TsReg Timestamp/* Ts */,
    TsVerval Timestamp/* Ts */,
    ActieInh Bigint/* ActieID */,
    ActieVerval Bigint/* ActieID */,
    DatAanv Numeric(8) NOT NULL /* Dat */,
    DatEinde Numeric(8)/* Dat */,
    Sector Integer/* SectorID */,
    CONSTRAINT BR4631 PRIMARY KEY (ID),
    CONSTRAINT BR4628 UNIQUE (Partij, TsReg)
);
ALTER SEQUENCE Kern.seq_His_Partij OWNED BY Kern.His_Partij.ID;

CREATE SEQUENCE Kern.seq_His_Gem;
CREATE TABLE Kern.His_Gem (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_His_Gem') /* His_GemID */,
    Partij Integer/* PartijID */,
    TsReg Timestamp/* Ts */,
    TsVerval Timestamp/* Ts */,
    ActieInh Bigint/* ActieID */,
    ActieVerval Bigint/* ActieID */,
    VoortzettendeGem Integer/* PartijID */,
    Gemcode Varchar(4) NOT NULL /* Gemcode */,
    OnderdeelVan Integer/* PartijID */,
    CONSTRAINT BR4617 PRIMARY KEY (ID),
    CONSTRAINT BR4614 UNIQUE (Partij, TsReg)
);
ALTER SEQUENCE Kern.seq_His_Gem OWNED BY Kern.His_Gem.ID;

CREATE SEQUENCE Kern.seq_His_PersIDs;
CREATE TABLE Kern.His_PersIDs (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_His_PersIDs') /* His_PersIDsID */,
    Pers Bigint/* PersID */,
    DatAanvGel Numeric(8)/* Dat */,
    DatEindeGel Numeric(8)/* Dat */,
    TsReg Timestamp/* Ts */,
    TsVerval Timestamp/* Ts */,
    ActieInh Bigint/* ActieID */,
    ActieVerval Bigint/* ActieID */,
    ActieAanpGel Bigint/* ActieID */,
    BSN Varchar(9)/* BSN */,
    ANr Varchar(10)/* ANr */,
    CONSTRAINT BR4525 PRIMARY KEY (ID),
    CONSTRAINT BR4087 UNIQUE (Pers, TsReg, DatAanvGel)
);
ALTER SEQUENCE Kern.seq_His_PersIDs OWNED BY Kern.His_PersIDs.ID;

CREATE SEQUENCE Kern.seq_His_PersGeslachtsaand;
CREATE TABLE Kern.His_PersGeslachtsaand (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_His_PersGeslachtsaand') /* His_PersGeslachtsaandID */,
    Pers Bigint/* PersID */,
    DatAanvGel Numeric(8)/* Dat */,
    DatEindeGel Numeric(8)/* Dat */,
    TsReg Timestamp/* Ts */,
    TsVerval Timestamp/* Ts */,
    ActieInh Bigint/* ActieID */,
    ActieVerval Bigint/* ActieID */,
    ActieAanpGel Bigint/* ActieID */,
    Geslachtsaand Smallint NOT NULL /* GeslachtsaandID */,
    CONSTRAINT BR4528 PRIMARY KEY (ID),
    CONSTRAINT BR4097 UNIQUE (Pers, TsReg, DatAanvGel)
);
ALTER SEQUENCE Kern.seq_His_PersGeslachtsaand OWNED BY Kern.His_PersGeslachtsaand.ID;

CREATE SEQUENCE Kern.seq_His_PersSamengesteldeNaam;
CREATE TABLE Kern.His_PersSamengesteldeNaam (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_His_PersSamengesteldeNaam') /* His_PersSamengesteldeNaamID */,
    Pers Bigint/* PersID */,
    DatAanvGel Numeric(8)/* Dat */,
    DatEindeGel Numeric(8)/* Dat */,
    TsReg Timestamp/* Ts */,
    TsVerval Timestamp/* Ts */,
    ActieInh Bigint/* ActieID */,
    ActieVerval Bigint/* ActieID */,
    ActieAanpGel Bigint/* ActieID */,
    Predikaat Smallint/* PredikaatID */,
    Voornamen Varchar(200)/* Voornamen */,
    Voorvoegsel Varchar(10)/* Voorvoegsel */,
    Scheidingsteken Varchar(1)/* Scheidingsteken */,
    AdellijkeTitel Smallint/* AdellijkeTitelID */,
    Geslnaam Varchar(200) NOT NULL /* Geslnaam */,
    IndNreeksAlsGeslnaam boolean NOT NULL /* JaNee */,
    IndAlgoritmischAfgeleid boolean NOT NULL /* JaNee */,
    CONSTRAINT BR4531 PRIMARY KEY (ID),
    CONSTRAINT BR4114 UNIQUE (Pers, TsReg, DatAanvGel)
);
ALTER SEQUENCE Kern.seq_His_PersSamengesteldeNaam OWNED BY Kern.His_PersSamengesteldeNaam.ID;

CREATE SEQUENCE Kern.seq_His_PersAanschr;
CREATE TABLE Kern.His_PersAanschr (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_His_PersAanschr') /* His_PersAanschrID */,
    Pers Bigint/* PersID */,
    DatAanvGel Numeric(8)/* Dat */,
    DatEindeGel Numeric(8)/* Dat */,
    TsReg Timestamp/* Ts */,
    TsVerval Timestamp/* Ts */,
    ActieInh Bigint/* ActieID */,
    ActieVerval Bigint/* ActieID */,
    ActieAanpGel Bigint/* ActieID */,
    GebrGeslnaamEGP Smallint/* WijzeGebruikGeslnaamID */,
    IndAanschrMetAdellijkeTitels boolean/* JaNee */,
    IndAanschrAlgoritmischAfgele boolean NOT NULL /* JaNee */,
    PredikaatAanschr Smallint/* PredikaatID */,
    VoornamenAanschr Varchar(200)/* Voornamen */,
    VoorvoegselAanschr Varchar(10)/* Voorvoegsel */,
    ScheidingstekenAanschr Varchar(1)/* Scheidingsteken */,
    GeslnaamAanschr Varchar(200) NOT NULL /* Geslnaam */,
    CONSTRAINT BR4534 PRIMARY KEY (ID),
    CONSTRAINT BR4131 UNIQUE (Pers, TsReg, DatAanvGel)
);
ALTER SEQUENCE Kern.seq_His_PersAanschr OWNED BY Kern.His_PersAanschr.ID;

CREATE SEQUENCE Kern.seq_His_PersGeboorte;
CREATE TABLE Kern.His_PersGeboorte (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_His_PersGeboorte') /* His_PersGeboorteID */,
    Pers Bigint/* PersID */,
    TsReg Timestamp/* Ts */,
    TsVerval Timestamp/* Ts */,
    ActieInh Bigint/* ActieID */,
    ActieVerval Bigint/* ActieID */,
    DatGeboorte Numeric(8) NOT NULL /* Dat */,
    GemGeboorte Integer/* PartijID */,
    WplGeboorte Integer/* PlaatsID */,
    BLGeboorteplaats Varchar(40)/* BLPlaats */,
    BLRegioGeboorte Varchar(35)/* BLRegio */,
    LandGeboorte Integer NOT NULL /* LandID */,
    OmsGeboorteloc Varchar(40)/* LocOms */,
    CONSTRAINT BR4537 PRIMARY KEY (ID),
    CONSTRAINT BR4144 UNIQUE (Pers, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PersGeboorte OWNED BY Kern.His_PersGeboorte.ID;

CREATE SEQUENCE Kern.seq_His_PersOverlijden;
CREATE TABLE Kern.His_PersOverlijden (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_His_PersOverlijden') /* His_PersOverlijdenID */,
    Pers Bigint/* PersID */,
    TsReg Timestamp/* Ts */,
    TsVerval Timestamp/* Ts */,
    ActieInh Bigint/* ActieID */,
    ActieVerval Bigint/* ActieID */,
    DatOverlijden Numeric(8) NOT NULL /* Dat */,
    GemOverlijden Integer/* PartijID */,
    WplOverlijden Integer/* PlaatsID */,
    BLPlaatsOverlijden Varchar(40)/* BLPlaats */,
    BLRegioOverlijden Varchar(35)/* BLRegio */,
    LandOverlijden Integer NOT NULL /* LandID */,
    OmsLocOverlijden Varchar(40)/* LocOms */,
    CONSTRAINT BR4540 PRIMARY KEY (ID),
    CONSTRAINT BR4157 UNIQUE (Pers, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PersOverlijden OWNED BY Kern.His_PersOverlijden.ID;

CREATE SEQUENCE Kern.seq_His_PersVerblijfsr;
CREATE TABLE Kern.His_PersVerblijfsr (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_His_PersVerblijfsr') /* His_PersVerblijfsrID */,
    Pers Bigint/* PersID */,
    DatAanvGel Numeric(8)/* Dat */,
    DatEindeGel Numeric(8)/* Dat */,
    TsReg Timestamp/* Ts */,
    TsVerval Timestamp/* Ts */,
    ActieInh Bigint/* ActieID */,
    ActieVerval Bigint/* ActieID */,
    ActieAanpGel Bigint/* ActieID */,
    Verblijfsr Integer NOT NULL /* VerblijfsrID */,
    DatAanvVerblijfsr Numeric(8) NOT NULL /* Dat */,
    DatVoorzEindeVerblijfsr Numeric(8)/* Dat */,
    DatAanvAaneenslVerblijfsr Numeric(8)/* Dat */,
    CONSTRAINT BR4543 PRIMARY KEY (ID),
    CONSTRAINT BR4170 UNIQUE (Pers, TsReg, DatAanvGel)
);
ALTER SEQUENCE Kern.seq_His_PersVerblijfsr OWNED BY Kern.His_PersVerblijfsr.ID;

CREATE SEQUENCE Kern.seq_His_PersUitslNLKiesr;
CREATE TABLE Kern.His_PersUitslNLKiesr (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_His_PersUitslNLKiesr') /* His_PersUitslNLKiesrID */,
    Pers Bigint/* PersID */,
    TsReg Timestamp/* Ts */,
    TsVerval Timestamp/* Ts */,
    ActieInh Bigint/* ActieID */,
    ActieVerval Bigint/* ActieID */,
    IndUitslNLKiesr boolean NOT NULL /* JaNee */,
    DatEindeUitslNLKiesr Numeric(8)/* Dat */,
    CONSTRAINT BR4546 PRIMARY KEY (ID),
    CONSTRAINT BR4178 UNIQUE (Pers, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PersUitslNLKiesr OWNED BY Kern.His_PersUitslNLKiesr.ID;

CREATE SEQUENCE Kern.seq_His_PersEUVerkiezingen;
CREATE TABLE Kern.His_PersEUVerkiezingen (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_His_PersEUVerkiezingen') /* His_PersEUVerkiezingenID */,
    Pers Bigint/* PersID */,
    TsReg Timestamp/* Ts */,
    TsVerval Timestamp/* Ts */,
    ActieInh Bigint/* ActieID */,
    ActieVerval Bigint/* ActieID */,
    IndDeelnEUVerkiezingen boolean NOT NULL /* JaNee */,
    DatAanlAanpDeelnEUVerkiezing Numeric(8)/* Dat */,
    DatEindeUitslEUKiesr Numeric(8)/* Dat */,
    CONSTRAINT BR4549 PRIMARY KEY (ID),
    CONSTRAINT BR4187 UNIQUE (Pers, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PersEUVerkiezingen OWNED BY Kern.His_PersEUVerkiezingen.ID;

CREATE SEQUENCE Kern.seq_His_PersBijhverantwoordelijk;
CREATE TABLE Kern.His_PersBijhverantwoordelijk (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_His_PersBijhverantwoordelijk') /* His_PersBijhverantwoordelijk */,
    Pers Bigint/* PersID */,
    DatAanvGel Numeric(8)/* Dat */,
    DatEindeGel Numeric(8)/* Dat */,
    TsReg Timestamp/* Ts */,
    TsVerval Timestamp/* Ts */,
    ActieInh Bigint/* ActieID */,
    ActieVerval Bigint/* ActieID */,
    ActieAanpGel Bigint/* ActieID */,
    Verantwoordelijke Smallint NOT NULL /* VerantwoordelijkeID */,
    CONSTRAINT BR4552 PRIMARY KEY (ID),
    CONSTRAINT BR4197 UNIQUE (Pers, TsReg, DatAanvGel)
);
ALTER SEQUENCE Kern.seq_His_PersBijhverantwoordelijk OWNED BY Kern.His_PersBijhverantwoordelijk.ID;

CREATE SEQUENCE Kern.seq_His_PersOpschorting;
CREATE TABLE Kern.His_PersOpschorting (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_His_PersOpschorting') /* His_PersOpschortingID */,
    Pers Bigint/* PersID */,
    DatAanvGel Numeric(8)/* Dat */,
    DatEindeGel Numeric(8)/* Dat */,
    TsReg Timestamp/* Ts */,
    TsVerval Timestamp/* Ts */,
    ActieInh Bigint/* ActieID */,
    ActieVerval Bigint/* ActieID */,
    ActieAanpGel Bigint/* ActieID */,
    RdnOpschortingBijhouding Smallint NOT NULL /* RdnOpschortingID */,
    CONSTRAINT BR4555 PRIMARY KEY (ID),
    CONSTRAINT BR4204 UNIQUE (Pers, TsReg, DatAanvGel)
);
ALTER SEQUENCE Kern.seq_His_PersOpschorting OWNED BY Kern.His_PersOpschorting.ID;

CREATE SEQUENCE Kern.seq_His_PersBijhgem;
CREATE TABLE Kern.His_PersBijhgem (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_His_PersBijhgem') /* His_PersBijhgemID */,
    Pers Bigint/* PersID */,
    DatAanvGel Numeric(8)/* Dat */,
    DatEindeGel Numeric(8)/* Dat */,
    TsReg Timestamp/* Ts */,
    TsVerval Timestamp/* Ts */,
    ActieInh Bigint/* ActieID */,
    ActieVerval Bigint/* ActieID */,
    ActieAanpGel Bigint/* ActieID */,
    Bijhgem Integer NOT NULL /* PartijID */,
    DatInschrInGem Numeric(8) NOT NULL /* Dat */,
    IndOnverwDocAanw boolean NOT NULL /* JaNee */,
    CONSTRAINT BR4558 PRIMARY KEY (ID),
    CONSTRAINT BR4216 UNIQUE (Pers, TsReg, DatAanvGel)
);
ALTER SEQUENCE Kern.seq_His_PersBijhgem OWNED BY Kern.His_PersBijhgem.ID;

CREATE SEQUENCE Kern.seq_His_PersPK;
CREATE TABLE Kern.His_PersPK (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_His_PersPK') /* His_PersPKID */,
    Pers Bigint/* PersID */,
    TsReg Timestamp/* Ts */,
    TsVerval Timestamp/* Ts */,
    ActieInh Bigint/* ActieID */,
    ActieVerval Bigint/* ActieID */,
    GemPK Integer/* PartijID */,
    IndPKVolledigGeconv boolean NOT NULL /* JaNee */,
    CONSTRAINT BR4561 PRIMARY KEY (ID),
    CONSTRAINT BR4224 UNIQUE (Pers, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PersPK OWNED BY Kern.His_PersPK.ID;

CREATE SEQUENCE Kern.seq_His_PersImmigratie;
CREATE TABLE Kern.His_PersImmigratie (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_His_PersImmigratie') /* His_PersImmigratieID */,
    Pers Bigint/* PersID */,
    DatAanvGel Numeric(8)/* Dat */,
    DatEindeGel Numeric(8)/* Dat */,
    TsReg Timestamp/* Ts */,
    TsVerval Timestamp/* Ts */,
    ActieInh Bigint/* ActieID */,
    ActieVerval Bigint/* ActieID */,
    ActieAanpGel Bigint/* ActieID */,
    LandVanwaarGevestigd Integer/* LandID */,
    DatVestigingInNederland Numeric(8) NOT NULL /* Dat */,
    CONSTRAINT BR4564 PRIMARY KEY (ID),
    CONSTRAINT BR4235 UNIQUE (Pers, TsReg, DatAanvGel)
);
ALTER SEQUENCE Kern.seq_His_PersImmigratie OWNED BY Kern.His_PersImmigratie.ID;

CREATE SEQUENCE Kern.seq_His_PersInschr;
CREATE TABLE Kern.His_PersInschr (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_His_PersInschr') /* His_PersInschrID */,
    Pers Bigint/* PersID */,
    TsReg Timestamp/* Ts */,
    TsVerval Timestamp/* Ts */,
    ActieInh Bigint/* ActieID */,
    ActieVerval Bigint/* ActieID */,
    DatInschr Numeric(8) NOT NULL /* Dat */,
    Versienr Bigint NOT NULL /* Versienr */,
    VorigePers Bigint/* PersID */,
    VolgendePers Bigint/* PersID */,
    CONSTRAINT BR4567 PRIMARY KEY (ID),
    CONSTRAINT BR4248 UNIQUE (Pers, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PersInschr OWNED BY Kern.His_PersInschr.ID;

CREATE SEQUENCE Kern.seq_His_PersAdres;
CREATE TABLE Kern.His_PersAdres (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_His_PersAdres') /* His_PersAdresID */,
    PersAdres Bigint/* PersAdresID */,
    DatAanvGel Numeric(8)/* Dat */,
    DatEindeGel Numeric(8)/* Dat */,
    TsReg Timestamp/* Ts */,
    TsVerval Timestamp/* Ts */,
    ActieInh Bigint/* ActieID */,
    ActieVerval Bigint/* ActieID */,
    ActieAanpGel Bigint/* ActieID */,
    Srt Smallint/* SrtAdresID */,
    RdnWijz Smallint/* RdnWijzAdresID */,
    AangAdresh Smallint/* AangAdreshID */,
    DatAanvAdresh Numeric(8)/* Dat */,
    AdresseerbaarObject Varchar(16)/* AandAdresseerbaarObject */,
    IdentcodeNraand Varchar(16)/* IdentcodeNraand */,
    Gem Integer/* PartijID */,
    NOR Varchar(80)/* NOR */,
    AfgekorteNOR Varchar(24)/* AfgekorteNOR */,
    Gemdeel Varchar(24)/* Gemdeel */,
    Huisnr Varchar(5)/* Huisnr */,
    Huisletter Varchar(1)/* Huisletter */,
    Huisnrtoevoeging Varchar(4)/* Huisnrtoevoeging */,
    Postcode Varchar(6)/* Postcode */,
    Wpl Integer/* PlaatsID */,
    LoctovAdres Varchar(2)/* AandBijHuisnr */,
    LocOms Varchar(40)/* LocOms */,
    BLAdresRegel1 Varchar(40)/* Adresregel */,
    BLAdresRegel2 Varchar(40)/* Adresregel */,
    BLAdresRegel3 Varchar(40)/* Adresregel */,
    BLAdresRegel4 Varchar(40)/* Adresregel */,
    BLAdresRegel5 Varchar(40)/* Adresregel */,
    BLAdresRegel6 Varchar(40)/* Adresregel */,
    Land Integer NOT NULL /* LandID */,
    DatVertrekUitNederland Numeric(8)/* Dat */,
    CONSTRAINT BR6076 PRIMARY KEY (ID),
    CONSTRAINT BR6073 UNIQUE (PersAdres, TsReg, DatAanvGel)
);
ALTER SEQUENCE Kern.seq_His_PersAdres OWNED BY Kern.His_PersAdres.ID;

CREATE SEQUENCE Kern.seq_His_PersGeslnaamcomp;
CREATE TABLE Kern.His_PersGeslnaamcomp (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_His_PersGeslnaamcomp') /* His_PersGeslnaamcompID */,
    PersGeslnaamcomp Bigint/* PersGeslnaamID */,
    DatAanvGel Numeric(8)/* Dat */,
    DatEindeGel Numeric(8)/* Dat */,
    TsReg Timestamp/* Ts */,
    TsVerval Timestamp/* Ts */,
    ActieInh Bigint/* ActieID */,
    ActieVerval Bigint/* ActieID */,
    ActieAanpGel Bigint/* ActieID */,
    Voorvoegsel Varchar(10)/* Voorvoegsel */,
    Scheidingsteken Varchar(1)/* Scheidingsteken */,
    Naam Varchar(200) NOT NULL /* Geslnaamcomp */,
    Predikaat Smallint/* PredikaatID */,
    AdellijkeTitel Smallint/* AdellijkeTitelID */,
    CONSTRAINT BR4579 PRIMARY KEY (ID),
    CONSTRAINT BR4314 UNIQUE (PersGeslnaamcomp, TsReg, DatAanvGel)
);
ALTER SEQUENCE Kern.seq_His_PersGeslnaamcomp OWNED BY Kern.His_PersGeslnaamcomp.ID;

CREATE SEQUENCE Kern.seq_His_PersIndicatie;
CREATE TABLE Kern.His_PersIndicatie (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_His_PersIndicatie') /* His_PersIndicatieID */,
    PersIndicatie Bigint/* PersIndicatieID */,
    DatAanvGel Numeric(8)/* Dat */,
    DatEindeGel Numeric(8)/* Dat */,
    TsReg Timestamp/* Ts */,
    TsVerval Timestamp/* Ts */,
    ActieInh Bigint/* ActieID */,
    ActieVerval Bigint/* ActieID */,
    ActieAanpGel Bigint/* ActieID */,
    Waarde boolean NOT NULL /* Ja */,
    CONSTRAINT BR4582 PRIMARY KEY (ID),
    CONSTRAINT BR4324 UNIQUE (PersIndicatie, TsReg, DatAanvGel)
);
ALTER SEQUENCE Kern.seq_His_PersIndicatie OWNED BY Kern.His_PersIndicatie.ID;

CREATE SEQUENCE Kern.seq_His_PersNation;
CREATE TABLE Kern.His_PersNation (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_His_PersNation') /* His_PersNationID */,
    PersNation Bigint/* PersNationID */,
    DatAanvGel Numeric(8)/* Dat */,
    DatEindeGel Numeric(8)/* Dat */,
    TsReg Timestamp/* Ts */,
    TsVerval Timestamp/* Ts */,
    ActieInh Bigint/* ActieID */,
    ActieVerval Bigint/* ActieID */,
    ActieAanpGel Bigint/* ActieID */,
    RdnVerlies Integer/* RdnVerliesNLNationID */,
    RdnVerk Integer/* RdnVerkNLNationID */,
    CONSTRAINT BR4585 PRIMARY KEY (ID),
    CONSTRAINT BR4335 UNIQUE (PersNation, TsReg, DatAanvGel)
);
ALTER SEQUENCE Kern.seq_His_PersNation OWNED BY Kern.His_PersNation.ID;

CREATE SEQUENCE Kern.seq_His_PersReisdoc;
CREATE TABLE Kern.His_PersReisdoc (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_His_PersReisdoc') /* His_PersReisdocID */,
    PersReisdoc Bigint/* ReisdocID */,
    TsReg Timestamp/* Ts */,
    TsVerval Timestamp/* Ts */,
    ActieInh Bigint/* ActieID */,
    ActieVerval Bigint/* ActieID */,
    Nr Varchar(9) NOT NULL /* ReisdocNr */,
    DatUitgifte Numeric(8) NOT NULL /* Dat */,
    AutVanAfgifte Integer NOT NULL /* AutVanAfgifteReisdocID */,
    DatVoorzeEindeGel Numeric(8) NOT NULL /* Dat */,
    DatInhingVermissing Numeric(8)/* Dat */,
    RdnVervallen Smallint/* RdnOntbrReisdocID */,
    LengteHouder Numeric(3)/* LengteInCm */,
    CONSTRAINT BR4588 PRIMARY KEY (ID),
    CONSTRAINT BR4351 UNIQUE (PersReisdoc, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PersReisdoc OWNED BY Kern.His_PersReisdoc.ID;

CREATE SEQUENCE Kern.seq_His_PersVerificatie;
CREATE TABLE Kern.His_PersVerificatie (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_His_PersVerificatie') /* His_PersVerificatieID */,
    PersVerificatie Bigint/* VerificatieID */,
    TsReg Timestamp/* Ts */,
    TsVerval Timestamp/* Ts */,
    ActieInh Bigint/* ActieID */,
    ActieVerval Bigint/* ActieID */,
    Dat Numeric(8) NOT NULL /* Dat */,
    CONSTRAINT BR4591 PRIMARY KEY (ID),
    CONSTRAINT BR4358 UNIQUE (PersVerificatie, TsReg)
);
ALTER SEQUENCE Kern.seq_His_PersVerificatie OWNED BY Kern.His_PersVerificatie.ID;

CREATE SEQUENCE Kern.seq_His_PersVoornaam;
CREATE TABLE Kern.His_PersVoornaam (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_His_PersVoornaam') /* His_PersVoornaamID */,
    PersVoornaam Bigint/* PersVoornaamID */,
    DatAanvGel Numeric(8)/* Dat */,
    DatEindeGel Numeric(8)/* Dat */,
    TsReg Timestamp/* Ts */,
    TsVerval Timestamp/* Ts */,
    ActieInh Bigint/* ActieID */,
    ActieVerval Bigint/* ActieID */,
    ActieAanpGel Bigint/* ActieID */,
    Naam Varchar(40) NOT NULL /* Voornaam */,
    CONSTRAINT BR4594 PRIMARY KEY (ID),
    CONSTRAINT BR4368 UNIQUE (PersVoornaam, TsReg, DatAanvGel)
);
ALTER SEQUENCE Kern.seq_His_PersVoornaam OWNED BY Kern.His_PersVoornaam.ID;

CREATE SEQUENCE Kern.seq_His_Relatie;
CREATE TABLE Kern.His_Relatie (
    ID Bigint NOT NULL DEFAULT nextval('Kern.seq_His_Relatie') /* His_RelatieID */,
    Relatie Bigint/* RelatieID */,
    TsReg Timestamp/* Ts */,
    TsVerval Timestamp/* Ts */,
    ActieInh Bigint/* ActieID */,
    ActieVerval Bigint/* ActieID */,
    DatAanv Numeric(8)/* Dat */,
    GemAanv Integer/* PartijID */,
    WplAanv Integer/* PlaatsID */,
    BLPlaatsAanv Varchar(40)/* BLPlaats */,
    BLRegioAanv Varchar(35)/* BLRegio */,
    LandAanv Integer/* LandID */,
    OmsLocAanv Varchar(40)/* LocOms */,
    RdnEinde Smallint/* RdnBeeindRelatieID */,
    DatEinde Numeric(8)/* Dat */,
    GemEinde Integer/* PartijID */,
    WplEinde Integer/* PlaatsID */,
    BLPlaatsEinde Varchar(40)/* BLPlaats */,
    BLRegioEinde Varchar(35)/* BLRegio */,
    LandEinde Integer/* LandID */,
    OmsLocEinde Varchar(40)/* LocOms */,
    CONSTRAINT BR4597 PRIMARY KEY (ID),
    CONSTRAINT BR4389 UNIQUE (Relatie, TsReg)
);
ALTER SEQUENCE Kern.seq_His_Relatie OWNED BY Kern.His_Relatie.ID;

CREATE SEQUENCE Lev.seq_Abonnement;
CREATE TABLE Lev.Abonnement (
    ID Integer NOT NULL DEFAULT nextval('Lev.seq_Abonnement') /* AbonnementID */,
    Doelbinding Integer NOT NULL /* DoelbindingID */,
    SrtAbonnement Smallint NOT NULL /* SrtAbonnementID */,
    Populatiecriterium Text/* Populatiecriterium */,
    AbonnementStatusHis Varchar(1) NOT NULL /* StatusHistorie */,
    CONSTRAINT BR4983 PRIMARY KEY (ID),
    CONSTRAINT BR4984 UNIQUE (Doelbinding, SrtAbonnement)
);
ALTER SEQUENCE Lev.seq_Abonnement OWNED BY Lev.Abonnement.ID;

CREATE SEQUENCE Lev.seq_AbonnementGegevenselement;
CREATE TABLE Lev.AbonnementGegevenselement (
    ID Integer NOT NULL DEFAULT nextval('Lev.seq_AbonnementGegevenselement') /* GegevenselementAbonnementID */,
    Abonnement Integer NOT NULL /* AbonnementID */,
    Gegevenselement Integer NOT NULL /* DbObjectID */,
    CONSTRAINT BR4985 PRIMARY KEY (ID),
    CONSTRAINT BR4986 UNIQUE (Abonnement, Gegevenselement)
);
ALTER SEQUENCE Lev.seq_AbonnementGegevenselement OWNED BY Lev.AbonnementGegevenselement.ID;

CREATE SEQUENCE Lev.seq_AbonnementSrtBer;
CREATE TABLE Lev.AbonnementSrtBer (
    ID Integer NOT NULL DEFAULT nextval('Lev.seq_AbonnementSrtBer') /* AbonnementSrtBerID */,
    Abonnement Integer NOT NULL /* AbonnementID */,
    SrtBer Smallint NOT NULL /* SrtBerID */,
    AbonnementSrtBerStatusHis Varchar(1) NOT NULL /* StatusHistorie */,
    CONSTRAINT BR5607 PRIMARY KEY (ID),
    CONSTRAINT BR5608 UNIQUE (Abonnement, SrtBer)
);
ALTER SEQUENCE Lev.seq_AbonnementSrtBer OWNED BY Lev.AbonnementSrtBer.ID;

CREATE SEQUENCE Lev.seq_Lev;
CREATE TABLE Lev.Lev (
    ID Bigint NOT NULL DEFAULT nextval('Lev.seq_Lev') /* LevID */,
    Srt Bigint NOT NULL /* SrtLevID */,
    Authenticatiemiddel Integer NOT NULL /* AuthenticatiemiddelID */,
    Abonnement Integer NOT NULL /* AbonnementID */,
    TsBesch Timestamp/* Ts */,
    TsKlaarzettenLev Timestamp/* Ts */,
    GebaseerdOp Bigint/* BerID */,
    CONSTRAINT BR4828 PRIMARY KEY (ID)
);
ALTER SEQUENCE Lev.seq_Lev OWNED BY Lev.Lev.ID;

CREATE SEQUENCE Lev.seq_LevCommunicatie;
CREATE TABLE Lev.LevCommunicatie (
    ID Bigint NOT NULL DEFAULT nextval('Lev.seq_LevCommunicatie') /* LevBerID */,
    Lev Bigint NOT NULL /* LevID */,
    UitgaandBer Bigint NOT NULL /* BerID */,
    CONSTRAINT BR4848 PRIMARY KEY (ID),
    CONSTRAINT BR4849 UNIQUE (Lev, UitgaandBer)
);
ALTER SEQUENCE Lev.seq_LevCommunicatie OWNED BY Lev.LevCommunicatie.ID;

CREATE SEQUENCE Lev.seq_LevPers;
CREATE TABLE Lev.LevPers (
    ID Bigint NOT NULL DEFAULT nextval('Lev.seq_LevPers') /* LevPersID */,
    Lev Bigint NOT NULL /* LevID */,
    Pers Bigint NOT NULL /* PersID */,
    CONSTRAINT BR4850 PRIMARY KEY (ID),
    CONSTRAINT BR4851 UNIQUE (Lev, Pers)
);
ALTER SEQUENCE Lev.seq_LevPers OWNED BY Lev.LevPers.ID;

CREATE TABLE Lev.SrtAbonnement (
    ID Smallint NOT NULL /* SrtAbonnementID */,
    Naam Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    CONSTRAINT BR4993 PRIMARY KEY (ID),
    CONSTRAINT BR4994 UNIQUE (Naam)
);

CREATE TABLE Lev.SrtLev (
    ID Bigint NOT NULL /* SrtLevID */,
    Naam Varchar(40) NOT NULL /* NaamEnumeratiewaarde */,
    CONSTRAINT BR4829 PRIMARY KEY (ID),
    CONSTRAINT BR4830 UNIQUE (Naam)
);

CREATE SEQUENCE Lev.seq_His_Abonnement;
CREATE TABLE Lev.His_Abonnement (
    ID Bigint NOT NULL DEFAULT nextval('Lev.seq_His_Abonnement') /* His_AbonnementID */,
    Abonnement Integer/* AbonnementID */,
    TsReg Timestamp/* Ts */,
    TsVerval Timestamp/* Ts */,
    ActieInh Bigint/* ActieID */,
    ActieVerval Bigint/* ActieID */,
    Populatiecriterium Text NOT NULL /* Populatiecriterium */,
    CONSTRAINT BR4997 PRIMARY KEY (ID),
    CONSTRAINT BR4958 UNIQUE (Abonnement, TsReg)
);
ALTER SEQUENCE Lev.seq_His_Abonnement OWNED BY Lev.His_Abonnement.ID;

CREATE SEQUENCE Lev.seq_His_AbonnementSrtBer;
CREATE TABLE Lev.His_AbonnementSrtBer (
    ID Bigint NOT NULL DEFAULT nextval('Lev.seq_His_AbonnementSrtBer') /* His_AbonnementSrtBerID */,
    AbonnementSrtBer Integer/* AbonnementSrtBerID */,
    TsReg Timestamp/* Ts */,
    TsVerval Timestamp/* Ts */,
    ActieInh Bigint/* ActieID */,
    ActieVerval Bigint/* ActieID */,
    CONSTRAINT BR5611 PRIMARY KEY (ID),
    CONSTRAINT BR5606 UNIQUE (AbonnementSrtBer, TsReg)
);
ALTER SEQUENCE Lev.seq_His_AbonnementSrtBer OWNED BY Lev.His_AbonnementSrtBer.ID;

-- Deze tabel komt NIET uit het BRP metaregister.
CREATE TABLE Kern.PersoonsLock (BSN Varchar(9) PRIMARY KEY);

-- Foreign keys ----------------------------------------------------------------
ALTER TABLE AutAut.Authenticatiemiddel ADD FOREIGN KEY (Partij) REFERENCES Kern.Partij;
ALTER TABLE AutAut.Authenticatiemiddel ADD FOREIGN KEY (Rol) REFERENCES Kern.Rol;
ALTER TABLE AutAut.Authenticatiemiddel ADD FOREIGN KEY (Functie) REFERENCES AutAut.Functie;
ALTER TABLE AutAut.Authenticatiemiddel ADD FOREIGN KEY (CertificaatTbvSSL) REFERENCES AutAut.Certificaat;
ALTER TABLE AutAut.Authenticatiemiddel ADD FOREIGN KEY (CertificaatTbvOndertekening) REFERENCES AutAut.Certificaat;
ALTER TABLE AutAut.Autorisatiebesluit ADD FOREIGN KEY (Srt) REFERENCES AutAut.SrtAutorisatiebesluit;
ALTER TABLE AutAut.Autorisatiebesluit ADD FOREIGN KEY (Autoriseerder) REFERENCES Kern.Partij;
ALTER TABLE AutAut.Autorisatiebesluit ADD FOREIGN KEY (GebaseerdOp) REFERENCES AutAut.Autorisatiebesluit;
ALTER TABLE AutAut.Autorisatiebesluit ADD FOREIGN KEY (Toestand) REFERENCES AutAut.Toestand;
ALTER TABLE AutAut.Bijhautorisatie ADD FOREIGN KEY (Bijhautorisatiebesluit) REFERENCES AutAut.Autorisatiebesluit;
ALTER TABLE AutAut.Bijhautorisatie ADD FOREIGN KEY (Verantwoordelijke) REFERENCES Kern.Verantwoordelijke;
ALTER TABLE AutAut.Bijhautorisatie ADD FOREIGN KEY (SrtBijhouding) REFERENCES AutAut.SrtBijhouding;
ALTER TABLE AutAut.Bijhautorisatie ADD FOREIGN KEY (GeautoriseerdeSrtPartij) REFERENCES Kern.SrtPartij;
ALTER TABLE AutAut.Bijhautorisatie ADD FOREIGN KEY (GeautoriseerdePartij) REFERENCES Kern.Partij;
ALTER TABLE AutAut.Bijhautorisatie ADD FOREIGN KEY (Toestand) REFERENCES AutAut.Toestand;
ALTER TABLE AutAut.Bijhautorisatie ADD FOREIGN KEY (BeperkingPopulatie) REFERENCES AutAut.BeperkingPopulatie;
ALTER TABLE AutAut.Bijhsituatie ADD FOREIGN KEY (Bijhautorisatie) REFERENCES AutAut.Bijhautorisatie;
ALTER TABLE AutAut.Bijhsituatie ADD FOREIGN KEY (CategorieSrtActie) REFERENCES Kern.CategorieSrtActie;
ALTER TABLE AutAut.Bijhsituatie ADD FOREIGN KEY (SrtActie) REFERENCES Kern.SrtActie;
ALTER TABLE AutAut.Bijhsituatie ADD FOREIGN KEY (CategorieSrtDoc) REFERENCES Kern.CategorieSrtDoc;
ALTER TABLE AutAut.Bijhsituatie ADD FOREIGN KEY (SrtDoc) REFERENCES Kern.SrtDoc;
ALTER TABLE AutAut.Doelbinding ADD FOREIGN KEY (Levsautorisatiebesluit) REFERENCES AutAut.Autorisatiebesluit;
ALTER TABLE AutAut.Doelbinding ADD FOREIGN KEY (Geautoriseerde) REFERENCES Kern.Partij;
ALTER TABLE AutAut.Doelbinding ADD FOREIGN KEY (Protocolleringsniveau) REFERENCES AutAut.Protocolleringsniveau;
ALTER TABLE AutAut.DoelbindingGegevenselement ADD FOREIGN KEY (Doelbinding) REFERENCES AutAut.Doelbinding;
ALTER TABLE AutAut.DoelbindingGegevenselement ADD FOREIGN KEY (Gegevenselement) REFERENCES Kern.DbObject;
ALTER TABLE AutAut.Uitgeslotene ADD FOREIGN KEY (Bijhautorisatie) REFERENCES AutAut.Bijhautorisatie;
ALTER TABLE AutAut.Uitgeslotene ADD FOREIGN KEY (UitgeslotenPartij) REFERENCES Kern.Partij;
ALTER TABLE AutAut.His_Authenticatiemiddel ADD FOREIGN KEY (Authenticatiemiddel) REFERENCES AutAut.Authenticatiemiddel;
ALTER TABLE AutAut.His_Authenticatiemiddel ADD FOREIGN KEY (ActieInh) REFERENCES Kern.Actie;
ALTER TABLE AutAut.His_Authenticatiemiddel ADD FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie;
ALTER TABLE AutAut.His_Authenticatiemiddel ADD FOREIGN KEY (Functie) REFERENCES AutAut.Functie;
ALTER TABLE AutAut.His_Authenticatiemiddel ADD FOREIGN KEY (CertificaatTbvSSL) REFERENCES AutAut.Certificaat;
ALTER TABLE AutAut.His_Authenticatiemiddel ADD FOREIGN KEY (CertificaatTbvOndertekening) REFERENCES AutAut.Certificaat;
ALTER TABLE AutAut.His_Autorisatiebesluit ADD FOREIGN KEY (Autorisatiebesluit) REFERENCES AutAut.Autorisatiebesluit;
ALTER TABLE AutAut.His_Autorisatiebesluit ADD FOREIGN KEY (ActieInh) REFERENCES Kern.Actie;
ALTER TABLE AutAut.His_Autorisatiebesluit ADD FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie;
ALTER TABLE AutAut.His_AutorisatiebesluitBijhau ADD FOREIGN KEY (Autorisatiebesluit) REFERENCES AutAut.Autorisatiebesluit;
ALTER TABLE AutAut.His_AutorisatiebesluitBijhau ADD FOREIGN KEY (ActieInh) REFERENCES Kern.Actie;
ALTER TABLE AutAut.His_AutorisatiebesluitBijhau ADD FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie;
ALTER TABLE AutAut.His_AutorisatiebesluitBijhau ADD FOREIGN KEY (Toestand) REFERENCES AutAut.Toestand;
ALTER TABLE AutAut.His_Bijhautorisatie ADD FOREIGN KEY (Bijhautorisatie) REFERENCES AutAut.Bijhautorisatie;
ALTER TABLE AutAut.His_Bijhautorisatie ADD FOREIGN KEY (ActieInh) REFERENCES Kern.Actie;
ALTER TABLE AutAut.His_Bijhautorisatie ADD FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie;
ALTER TABLE AutAut.His_Bijhautorisatie ADD FOREIGN KEY (Verantwoordelijke) REFERENCES Kern.Verantwoordelijke;
ALTER TABLE AutAut.His_Bijhautorisatie ADD FOREIGN KEY (SrtBijhouding) REFERENCES AutAut.SrtBijhouding;
ALTER TABLE AutAut.His_Bijhautorisatie ADD FOREIGN KEY (GeautoriseerdeSrtPartij) REFERENCES Kern.SrtPartij;
ALTER TABLE AutAut.His_Bijhautorisatie ADD FOREIGN KEY (GeautoriseerdePartij) REFERENCES Kern.Partij;
ALTER TABLE AutAut.His_Bijhautorisatie ADD FOREIGN KEY (Toestand) REFERENCES AutAut.Toestand;
ALTER TABLE AutAut.His_Bijhautorisatie ADD FOREIGN KEY (BeperkingPopulatie) REFERENCES AutAut.BeperkingPopulatie;
ALTER TABLE AutAut.His_Bijhsituatie ADD FOREIGN KEY (Bijhsituatie) REFERENCES AutAut.Bijhsituatie;
ALTER TABLE AutAut.His_Bijhsituatie ADD FOREIGN KEY (ActieInh) REFERENCES Kern.Actie;
ALTER TABLE AutAut.His_Bijhsituatie ADD FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie;
ALTER TABLE AutAut.His_Doelbinding ADD FOREIGN KEY (Doelbinding) REFERENCES AutAut.Doelbinding;
ALTER TABLE AutAut.His_Doelbinding ADD FOREIGN KEY (ActieInh) REFERENCES Kern.Actie;
ALTER TABLE AutAut.His_Doelbinding ADD FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie;
ALTER TABLE AutAut.His_Doelbinding ADD FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie;
ALTER TABLE AutAut.His_Doelbinding ADD FOREIGN KEY (Protocolleringsniveau) REFERENCES AutAut.Protocolleringsniveau;
ALTER TABLE AutAut.His_Uitgeslotene ADD FOREIGN KEY (Uitgeslotene) REFERENCES AutAut.Uitgeslotene;
ALTER TABLE AutAut.His_Uitgeslotene ADD FOREIGN KEY (ActieInh) REFERENCES Kern.Actie;
ALTER TABLE AutAut.His_Uitgeslotene ADD FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie;
ALTER TABLE BRM.Regel ADD FOREIGN KEY (Srt) REFERENCES BRM.SrtRegel;
ALTER TABLE BRM.Regelimplementatie ADD FOREIGN KEY (Regel) REFERENCES BRM.Regel;
ALTER TABLE BRM.Regelimplementatie ADD FOREIGN KEY (SrtBer) REFERENCES Ber.SrtBer;
ALTER TABLE BRM.Regelimplementatiesituatie ADD FOREIGN KEY (Regelimplementatie) REFERENCES BRM.Regelimplementatie;
ALTER TABLE BRM.Regelimplementatiesituatie ADD FOREIGN KEY (Bijhverantwoordelijkheid) REFERENCES Kern.Verantwoordelijke;
ALTER TABLE BRM.Regelimplementatiesituatie ADD FOREIGN KEY (RdnOpschorting) REFERENCES Kern.RdnOpschorting;
ALTER TABLE BRM.Regelimplementatiesituatie ADD FOREIGN KEY (Effect) REFERENCES BRM.Regeleffect;
ALTER TABLE BRM.His_Regelimplementatiesituat ADD FOREIGN KEY (Regelimplementatiesituatie) REFERENCES BRM.Regelimplementatiesituatie;
ALTER TABLE BRM.His_Regelimplementatiesituat ADD FOREIGN KEY (ActieInh) REFERENCES Kern.Actie;
ALTER TABLE BRM.His_Regelimplementatiesituat ADD FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie;
ALTER TABLE BRM.His_Regelimplementatiesituat ADD FOREIGN KEY (Bijhverantwoordelijkheid) REFERENCES Kern.Verantwoordelijke;
ALTER TABLE BRM.His_Regelimplementatiesituat ADD FOREIGN KEY (RdnOpschorting) REFERENCES Kern.RdnOpschorting;
ALTER TABLE BRM.His_Regelimplementatiesituat ADD FOREIGN KEY (Effect) REFERENCES BRM.Regeleffect;
ALTER TABLE Ber.Ber ADD FOREIGN KEY (AntwoordOp) REFERENCES Ber.Ber;
ALTER TABLE Ber.Ber ADD FOREIGN KEY (Richting) REFERENCES Ber.Richting;
ALTER TABLE Kern.Actie ADD FOREIGN KEY (Srt) REFERENCES Kern.SrtActie;
ALTER TABLE Kern.Actie ADD FOREIGN KEY (Partij) REFERENCES Kern.Partij;
ALTER TABLE Kern.Actie ADD FOREIGN KEY (Verdrag) REFERENCES Kern.Verdrag;
ALTER TABLE Kern.Betr ADD FOREIGN KEY (Relatie) REFERENCES Kern.Relatie;
ALTER TABLE Kern.Betr ADD FOREIGN KEY (Rol) REFERENCES Kern.SrtBetr;
ALTER TABLE Kern.Betr ADD FOREIGN KEY (Betrokkene) REFERENCES Kern.Pers;
ALTER TABLE Kern.Bron ADD FOREIGN KEY (Actie) REFERENCES Kern.Actie;
ALTER TABLE Kern.Bron ADD FOREIGN KEY (Doc) REFERENCES Kern.Doc;
ALTER TABLE Kern.DbObject ADD FOREIGN KEY (Srt) REFERENCES Kern.SrtDbObject;
ALTER TABLE Kern.DbObject ADD FOREIGN KEY (Ouder) REFERENCES Kern.DbObject;
ALTER TABLE Kern.Doc ADD FOREIGN KEY (Srt) REFERENCES Kern.SrtDoc;
ALTER TABLE Kern.Doc ADD FOREIGN KEY (Partij) REFERENCES Kern.Partij;
ALTER TABLE Kern.Element ADD FOREIGN KEY (Srt) REFERENCES Kern.SrtElement;
ALTER TABLE Kern.Element ADD FOREIGN KEY (Ouder) REFERENCES Kern.Element;
ALTER TABLE Kern.GegevenInOnderzoek ADD FOREIGN KEY (Onderzoek) REFERENCES Kern.Onderzoek;
ALTER TABLE Kern.GegevenInOnderzoek ADD FOREIGN KEY (SrtGegeven) REFERENCES Kern.DbObject;
ALTER TABLE Kern.MultiRealiteitRegel ADD FOREIGN KEY (GeldigVoor) REFERENCES Kern.Pers;
ALTER TABLE Kern.MultiRealiteitRegel ADD FOREIGN KEY (Srt) REFERENCES Kern.SrtMultiRealiteitRegel;
ALTER TABLE Kern.MultiRealiteitRegel ADD FOREIGN KEY (Pers) REFERENCES Kern.Pers;
ALTER TABLE Kern.MultiRealiteitRegel ADD FOREIGN KEY (MultiRealiteitPers) REFERENCES Kern.Pers;
ALTER TABLE Kern.MultiRealiteitRegel ADD FOREIGN KEY (Relatie) REFERENCES Kern.Relatie;
ALTER TABLE Kern.MultiRealiteitRegel ADD FOREIGN KEY (Betr) REFERENCES Kern.Betr;
ALTER TABLE Kern.Partij ADD FOREIGN KEY (Srt) REFERENCES Kern.SrtPartij;
ALTER TABLE Kern.Partij ADD FOREIGN KEY (Sector) REFERENCES Kern.Sector;
ALTER TABLE Kern.Partij ADD FOREIGN KEY (VoortzettendeGem) REFERENCES Kern.Partij;
ALTER TABLE Kern.Partij ADD FOREIGN KEY (OnderdeelVan) REFERENCES Kern.Partij;
ALTER TABLE Kern.PartijRol ADD FOREIGN KEY (Partij) REFERENCES Kern.Partij;
ALTER TABLE Kern.PartijRol ADD FOREIGN KEY (Rol) REFERENCES Kern.Rol;
ALTER TABLE Kern.Pers ADD FOREIGN KEY (Srt) REFERENCES Kern.SrtPers;
ALTER TABLE Kern.Pers ADD FOREIGN KEY (Geslachtsaand) REFERENCES Kern.Geslachtsaand;
ALTER TABLE Kern.Pers ADD FOREIGN KEY (Predikaat) REFERENCES Kern.Predikaat;
ALTER TABLE Kern.Pers ADD FOREIGN KEY (AdellijkeTitel) REFERENCES Kern.AdellijkeTitel;
ALTER TABLE Kern.Pers ADD FOREIGN KEY (GebrGeslnaamEGP) REFERENCES Kern.WijzeGebruikGeslnaam;
ALTER TABLE Kern.Pers ADD FOREIGN KEY (PredikaatAanschr) REFERENCES Kern.Predikaat;
ALTER TABLE Kern.Pers ADD FOREIGN KEY (GemGeboorte) REFERENCES Kern.Partij;
ALTER TABLE Kern.Pers ADD FOREIGN KEY (WplGeboorte) REFERENCES Kern.Plaats;
ALTER TABLE Kern.Pers ADD FOREIGN KEY (LandGeboorte) REFERENCES Kern.Land;
ALTER TABLE Kern.Pers ADD FOREIGN KEY (GemOverlijden) REFERENCES Kern.Partij;
ALTER TABLE Kern.Pers ADD FOREIGN KEY (WplOverlijden) REFERENCES Kern.Plaats;
ALTER TABLE Kern.Pers ADD FOREIGN KEY (LandOverlijden) REFERENCES Kern.Land;
ALTER TABLE Kern.Pers ADD FOREIGN KEY (Verblijfsr) REFERENCES Kern.Verblijfsr;
ALTER TABLE Kern.Pers ADD FOREIGN KEY (Verantwoordelijke) REFERENCES Kern.Verantwoordelijke;
ALTER TABLE Kern.Pers ADD FOREIGN KEY (RdnOpschortingBijhouding) REFERENCES Kern.RdnOpschorting;
ALTER TABLE Kern.Pers ADD FOREIGN KEY (Bijhgem) REFERENCES Kern.Partij;
ALTER TABLE Kern.Pers ADD FOREIGN KEY (GemPK) REFERENCES Kern.Partij;
ALTER TABLE Kern.Pers ADD FOREIGN KEY (LandVanwaarGevestigd) REFERENCES Kern.Land;
ALTER TABLE Kern.Pers ADD FOREIGN KEY (VorigePers) REFERENCES Kern.Pers;
ALTER TABLE Kern.Pers ADD FOREIGN KEY (VolgendePers) REFERENCES Kern.Pers;
ALTER TABLE Kern.PersAdres ADD FOREIGN KEY (Pers) REFERENCES Kern.Pers;
ALTER TABLE Kern.PersAdres ADD FOREIGN KEY (Srt) REFERENCES Kern.FunctieAdres;
ALTER TABLE Kern.PersAdres ADD FOREIGN KEY (RdnWijz) REFERENCES Kern.RdnWijzAdres;
ALTER TABLE Kern.PersAdres ADD FOREIGN KEY (AangAdresh) REFERENCES Kern.AangAdresh;
ALTER TABLE Kern.PersAdres ADD FOREIGN KEY (Gem) REFERENCES Kern.Partij;
ALTER TABLE Kern.PersAdres ADD FOREIGN KEY (Wpl) REFERENCES Kern.Plaats;
ALTER TABLE Kern.PersAdres ADD FOREIGN KEY (Land) REFERENCES Kern.Land;
ALTER TABLE Kern.PersGeslnaamcomp ADD FOREIGN KEY (Pers) REFERENCES Kern.Pers;
ALTER TABLE Kern.PersGeslnaamcomp ADD FOREIGN KEY (Predikaat) REFERENCES Kern.Predikaat;
ALTER TABLE Kern.PersGeslnaamcomp ADD FOREIGN KEY (AdellijkeTitel) REFERENCES Kern.AdellijkeTitel;
ALTER TABLE Kern.PersIndicatie ADD FOREIGN KEY (Pers) REFERENCES Kern.Pers;
ALTER TABLE Kern.PersIndicatie ADD FOREIGN KEY (Srt) REFERENCES Kern.SrtIndicatie;
ALTER TABLE Kern.PersNation ADD FOREIGN KEY (Pers) REFERENCES Kern.Pers;
ALTER TABLE Kern.PersNation ADD FOREIGN KEY (Nation) REFERENCES Kern.Nation;
ALTER TABLE Kern.PersNation ADD FOREIGN KEY (RdnVerlies) REFERENCES Kern.RdnVerliesNLNation;
ALTER TABLE Kern.PersNation ADD FOREIGN KEY (RdnVerk) REFERENCES Kern.RdnVerkNLNation;
ALTER TABLE Kern.PersReisdoc ADD FOREIGN KEY (Pers) REFERENCES Kern.Pers;
ALTER TABLE Kern.PersReisdoc ADD FOREIGN KEY (Srt) REFERENCES Kern.SrtNLReisdoc;
ALTER TABLE Kern.PersReisdoc ADD FOREIGN KEY (AutVanAfgifte) REFERENCES Kern.AutVanAfgifteReisdoc;
ALTER TABLE Kern.PersReisdoc ADD FOREIGN KEY (RdnVervallen) REFERENCES Kern.RdnVervallenReisdoc;
ALTER TABLE Kern.PersVerificatie ADD FOREIGN KEY (Geverifieerde) REFERENCES Kern.Pers;
ALTER TABLE Kern.PersVerificatie ADD FOREIGN KEY (Srt) REFERENCES Kern.SrtVerificatie;
ALTER TABLE Kern.PersVoornaam ADD FOREIGN KEY (Pers) REFERENCES Kern.Pers;
ALTER TABLE Kern.Relatie ADD FOREIGN KEY (Srt) REFERENCES Kern.SrtRelatie;
ALTER TABLE Kern.Relatie ADD FOREIGN KEY (GemAanv) REFERENCES Kern.Partij;
ALTER TABLE Kern.Relatie ADD FOREIGN KEY (WplAanv) REFERENCES Kern.Plaats;
ALTER TABLE Kern.Relatie ADD FOREIGN KEY (LandAanv) REFERENCES Kern.Land;
ALTER TABLE Kern.Relatie ADD FOREIGN KEY (RdnEinde) REFERENCES Kern.RdnBeeindRelatie;
ALTER TABLE Kern.Relatie ADD FOREIGN KEY (GemEinde) REFERENCES Kern.Partij;
ALTER TABLE Kern.Relatie ADD FOREIGN KEY (WplEinde) REFERENCES Kern.Plaats;
ALTER TABLE Kern.Relatie ADD FOREIGN KEY (LandEinde) REFERENCES Kern.Land;
ALTER TABLE Kern.SrtActie ADD FOREIGN KEY (CategorieSrtActie) REFERENCES Kern.CategorieSrtActie;
ALTER TABLE Kern.SrtDoc ADD FOREIGN KEY (CategorieSrtDoc) REFERENCES Kern.CategorieSrtDoc;
ALTER TABLE Kern.His_BetrOuder ADD FOREIGN KEY (Betr) REFERENCES Kern.Betr;
ALTER TABLE Kern.His_BetrOuder ADD FOREIGN KEY (ActieInh) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_BetrOuder ADD FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_BetrOuder ADD FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_BetrOuderlijkGezag ADD FOREIGN KEY (Betr) REFERENCES Kern.Betr;
ALTER TABLE Kern.His_BetrOuderlijkGezag ADD FOREIGN KEY (ActieInh) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_BetrOuderlijkGezag ADD FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_BetrOuderlijkGezag ADD FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_Doc ADD FOREIGN KEY (Doc) REFERENCES Kern.Doc;
ALTER TABLE Kern.His_Doc ADD FOREIGN KEY (ActieInh) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_Doc ADD FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_Doc ADD FOREIGN KEY (Partij) REFERENCES Kern.Partij;
ALTER TABLE Kern.His_MultiRealiteitRegel ADD FOREIGN KEY (MultiRealiteitRegel) REFERENCES Kern.MultiRealiteitRegel;
ALTER TABLE Kern.His_MultiRealiteitRegel ADD FOREIGN KEY (ActieInh) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_MultiRealiteitRegel ADD FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_Onderzoek ADD FOREIGN KEY (Onderzoek) REFERENCES Kern.Onderzoek;
ALTER TABLE Kern.His_Onderzoek ADD FOREIGN KEY (ActieInh) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_Onderzoek ADD FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_Partij ADD FOREIGN KEY (Partij) REFERENCES Kern.Partij;
ALTER TABLE Kern.His_Partij ADD FOREIGN KEY (ActieInh) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_Partij ADD FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_Partij ADD FOREIGN KEY (Sector) REFERENCES Kern.Sector;
ALTER TABLE Kern.His_Gem ADD FOREIGN KEY (Partij) REFERENCES Kern.Partij;
ALTER TABLE Kern.His_Gem ADD FOREIGN KEY (ActieInh) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_Gem ADD FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_Gem ADD FOREIGN KEY (VoortzettendeGem) REFERENCES Kern.Partij;
ALTER TABLE Kern.His_Gem ADD FOREIGN KEY (OnderdeelVan) REFERENCES Kern.Partij;
ALTER TABLE Kern.His_PersIDs ADD FOREIGN KEY (Pers) REFERENCES Kern.Pers;
ALTER TABLE Kern.His_PersIDs ADD FOREIGN KEY (ActieInh) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersIDs ADD FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersIDs ADD FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersGeslachtsaand ADD FOREIGN KEY (Pers) REFERENCES Kern.Pers;
ALTER TABLE Kern.His_PersGeslachtsaand ADD FOREIGN KEY (ActieInh) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersGeslachtsaand ADD FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersGeslachtsaand ADD FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersGeslachtsaand ADD FOREIGN KEY (Geslachtsaand) REFERENCES Kern.Geslachtsaand;
ALTER TABLE Kern.His_PersSamengesteldeNaam ADD FOREIGN KEY (Pers) REFERENCES Kern.Pers;
ALTER TABLE Kern.His_PersSamengesteldeNaam ADD FOREIGN KEY (ActieInh) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersSamengesteldeNaam ADD FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersSamengesteldeNaam ADD FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersSamengesteldeNaam ADD FOREIGN KEY (Predikaat) REFERENCES Kern.Predikaat;
ALTER TABLE Kern.His_PersSamengesteldeNaam ADD FOREIGN KEY (AdellijkeTitel) REFERENCES Kern.AdellijkeTitel;
ALTER TABLE Kern.His_PersAanschr ADD FOREIGN KEY (Pers) REFERENCES Kern.Pers;
ALTER TABLE Kern.His_PersAanschr ADD FOREIGN KEY (ActieInh) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersAanschr ADD FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersAanschr ADD FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersAanschr ADD FOREIGN KEY (GebrGeslnaamEGP) REFERENCES Kern.WijzeGebruikGeslnaam;
ALTER TABLE Kern.His_PersAanschr ADD FOREIGN KEY (PredikaatAanschr) REFERENCES Kern.Predikaat;
ALTER TABLE Kern.His_PersGeboorte ADD FOREIGN KEY (Pers) REFERENCES Kern.Pers;
ALTER TABLE Kern.His_PersGeboorte ADD FOREIGN KEY (ActieInh) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersGeboorte ADD FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersGeboorte ADD FOREIGN KEY (GemGeboorte) REFERENCES Kern.Partij;
ALTER TABLE Kern.His_PersGeboorte ADD FOREIGN KEY (WplGeboorte) REFERENCES Kern.Plaats;
ALTER TABLE Kern.His_PersGeboorte ADD FOREIGN KEY (LandGeboorte) REFERENCES Kern.Land;
ALTER TABLE Kern.His_PersOverlijden ADD FOREIGN KEY (Pers) REFERENCES Kern.Pers;
ALTER TABLE Kern.His_PersOverlijden ADD FOREIGN KEY (ActieInh) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersOverlijden ADD FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersOverlijden ADD FOREIGN KEY (GemOverlijden) REFERENCES Kern.Partij;
ALTER TABLE Kern.His_PersOverlijden ADD FOREIGN KEY (WplOverlijden) REFERENCES Kern.Plaats;
ALTER TABLE Kern.His_PersOverlijden ADD FOREIGN KEY (LandOverlijden) REFERENCES Kern.Land;
ALTER TABLE Kern.His_PersVerblijfsr ADD FOREIGN KEY (Pers) REFERENCES Kern.Pers;
ALTER TABLE Kern.His_PersVerblijfsr ADD FOREIGN KEY (ActieInh) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersVerblijfsr ADD FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersVerblijfsr ADD FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersVerblijfsr ADD FOREIGN KEY (Verblijfsr) REFERENCES Kern.Verblijfsr;
ALTER TABLE Kern.His_PersUitslNLKiesr ADD FOREIGN KEY (Pers) REFERENCES Kern.Pers;
ALTER TABLE Kern.His_PersUitslNLKiesr ADD FOREIGN KEY (ActieInh) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersUitslNLKiesr ADD FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersEUVerkiezingen ADD FOREIGN KEY (Pers) REFERENCES Kern.Pers;
ALTER TABLE Kern.His_PersEUVerkiezingen ADD FOREIGN KEY (ActieInh) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersEUVerkiezingen ADD FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersBijhverantwoordelijk ADD FOREIGN KEY (Pers) REFERENCES Kern.Pers;
ALTER TABLE Kern.His_PersBijhverantwoordelijk ADD FOREIGN KEY (ActieInh) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersBijhverantwoordelijk ADD FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersBijhverantwoordelijk ADD FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersBijhverantwoordelijk ADD FOREIGN KEY (Verantwoordelijke) REFERENCES Kern.Verantwoordelijke;
ALTER TABLE Kern.His_PersOpschorting ADD FOREIGN KEY (Pers) REFERENCES Kern.Pers;
ALTER TABLE Kern.His_PersOpschorting ADD FOREIGN KEY (ActieInh) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersOpschorting ADD FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersOpschorting ADD FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersOpschorting ADD FOREIGN KEY (RdnOpschortingBijhouding) REFERENCES Kern.RdnOpschorting;
ALTER TABLE Kern.His_PersBijhgem ADD FOREIGN KEY (Pers) REFERENCES Kern.Pers;
ALTER TABLE Kern.His_PersBijhgem ADD FOREIGN KEY (ActieInh) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersBijhgem ADD FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersBijhgem ADD FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersBijhgem ADD FOREIGN KEY (Bijhgem) REFERENCES Kern.Partij;
ALTER TABLE Kern.His_PersPK ADD FOREIGN KEY (Pers) REFERENCES Kern.Pers;
ALTER TABLE Kern.His_PersPK ADD FOREIGN KEY (ActieInh) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersPK ADD FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersPK ADD FOREIGN KEY (GemPK) REFERENCES Kern.Partij;
ALTER TABLE Kern.His_PersImmigratie ADD FOREIGN KEY (Pers) REFERENCES Kern.Pers;
ALTER TABLE Kern.His_PersImmigratie ADD FOREIGN KEY (ActieInh) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersImmigratie ADD FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersImmigratie ADD FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersImmigratie ADD FOREIGN KEY (LandVanwaarGevestigd) REFERENCES Kern.Land;
ALTER TABLE Kern.His_PersInschr ADD FOREIGN KEY (Pers) REFERENCES Kern.Pers;
ALTER TABLE Kern.His_PersInschr ADD FOREIGN KEY (ActieInh) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersInschr ADD FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersInschr ADD FOREIGN KEY (VorigePers) REFERENCES Kern.Pers;
ALTER TABLE Kern.His_PersInschr ADD FOREIGN KEY (VolgendePers) REFERENCES Kern.Pers;
ALTER TABLE Kern.His_PersAdres ADD FOREIGN KEY (PersAdres) REFERENCES Kern.PersAdres;
ALTER TABLE Kern.His_PersAdres ADD FOREIGN KEY (ActieInh) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersAdres ADD FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersAdres ADD FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersAdres ADD FOREIGN KEY (Srt) REFERENCES Kern.FunctieAdres;
ALTER TABLE Kern.His_PersAdres ADD FOREIGN KEY (RdnWijz) REFERENCES Kern.RdnWijzAdres;
ALTER TABLE Kern.His_PersAdres ADD FOREIGN KEY (AangAdresh) REFERENCES Kern.AangAdresh;
ALTER TABLE Kern.His_PersAdres ADD FOREIGN KEY (Gem) REFERENCES Kern.Partij;
ALTER TABLE Kern.His_PersAdres ADD FOREIGN KEY (Wpl) REFERENCES Kern.Plaats;
ALTER TABLE Kern.His_PersAdres ADD FOREIGN KEY (Land) REFERENCES Kern.Land;
ALTER TABLE Kern.His_PersGeslnaamcomp ADD FOREIGN KEY (PersGeslnaamcomp) REFERENCES Kern.PersGeslnaamcomp;
ALTER TABLE Kern.His_PersGeslnaamcomp ADD FOREIGN KEY (ActieInh) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersGeslnaamcomp ADD FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersGeslnaamcomp ADD FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersGeslnaamcomp ADD FOREIGN KEY (Predikaat) REFERENCES Kern.Predikaat;
ALTER TABLE Kern.His_PersGeslnaamcomp ADD FOREIGN KEY (AdellijkeTitel) REFERENCES Kern.AdellijkeTitel;
ALTER TABLE Kern.His_PersIndicatie ADD FOREIGN KEY (PersIndicatie) REFERENCES Kern.PersIndicatie;
ALTER TABLE Kern.His_PersIndicatie ADD FOREIGN KEY (ActieInh) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersIndicatie ADD FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersIndicatie ADD FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersNation ADD FOREIGN KEY (PersNation) REFERENCES Kern.PersNation;
ALTER TABLE Kern.His_PersNation ADD FOREIGN KEY (ActieInh) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersNation ADD FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersNation ADD FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersNation ADD FOREIGN KEY (RdnVerlies) REFERENCES Kern.RdnVerliesNLNation;
ALTER TABLE Kern.His_PersNation ADD FOREIGN KEY (RdnVerk) REFERENCES Kern.RdnVerkNLNation;
ALTER TABLE Kern.His_PersReisdoc ADD FOREIGN KEY (PersReisdoc) REFERENCES Kern.PersReisdoc;
ALTER TABLE Kern.His_PersReisdoc ADD FOREIGN KEY (ActieInh) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersReisdoc ADD FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersReisdoc ADD FOREIGN KEY (AutVanAfgifte) REFERENCES Kern.AutVanAfgifteReisdoc;
ALTER TABLE Kern.His_PersReisdoc ADD FOREIGN KEY (RdnVervallen) REFERENCES Kern.RdnVervallenReisdoc;
ALTER TABLE Kern.His_PersVerificatie ADD FOREIGN KEY (PersVerificatie) REFERENCES Kern.PersVerificatie;
ALTER TABLE Kern.His_PersVerificatie ADD FOREIGN KEY (ActieInh) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersVerificatie ADD FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersVoornaam ADD FOREIGN KEY (PersVoornaam) REFERENCES Kern.PersVoornaam;
ALTER TABLE Kern.His_PersVoornaam ADD FOREIGN KEY (ActieInh) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersVoornaam ADD FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_PersVoornaam ADD FOREIGN KEY (ActieAanpGel) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_Relatie ADD FOREIGN KEY (Relatie) REFERENCES Kern.Relatie;
ALTER TABLE Kern.His_Relatie ADD FOREIGN KEY (ActieInh) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_Relatie ADD FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie;
ALTER TABLE Kern.His_Relatie ADD FOREIGN KEY (GemAanv) REFERENCES Kern.Partij;
ALTER TABLE Kern.His_Relatie ADD FOREIGN KEY (WplAanv) REFERENCES Kern.Plaats;
ALTER TABLE Kern.His_Relatie ADD FOREIGN KEY (LandAanv) REFERENCES Kern.Land;
ALTER TABLE Kern.His_Relatie ADD FOREIGN KEY (RdnEinde) REFERENCES Kern.RdnBeeindRelatie;
ALTER TABLE Kern.His_Relatie ADD FOREIGN KEY (GemEinde) REFERENCES Kern.Partij;
ALTER TABLE Kern.His_Relatie ADD FOREIGN KEY (WplEinde) REFERENCES Kern.Plaats;
ALTER TABLE Kern.His_Relatie ADD FOREIGN KEY (LandEinde) REFERENCES Kern.Land;
ALTER TABLE Lev.Abonnement ADD FOREIGN KEY (Doelbinding) REFERENCES AutAut.Doelbinding;
ALTER TABLE Lev.Abonnement ADD FOREIGN KEY (SrtAbonnement) REFERENCES Lev.SrtAbonnement;
ALTER TABLE Lev.AbonnementGegevenselement ADD FOREIGN KEY (Abonnement) REFERENCES Lev.Abonnement;
ALTER TABLE Lev.AbonnementGegevenselement ADD FOREIGN KEY (Gegevenselement) REFERENCES Kern.DbObject;
ALTER TABLE Lev.AbonnementSrtBer ADD FOREIGN KEY (Abonnement) REFERENCES Lev.Abonnement;
ALTER TABLE Lev.AbonnementSrtBer ADD FOREIGN KEY (SrtBer) REFERENCES Ber.SrtBer;
ALTER TABLE Lev.Lev ADD FOREIGN KEY (Srt) REFERENCES Lev.SrtLev;
ALTER TABLE Lev.Lev ADD FOREIGN KEY (Authenticatiemiddel) REFERENCES AutAut.Authenticatiemiddel;
ALTER TABLE Lev.Lev ADD FOREIGN KEY (Abonnement) REFERENCES Lev.Abonnement;
ALTER TABLE Lev.Lev ADD FOREIGN KEY (GebaseerdOp) REFERENCES Ber.Ber;
ALTER TABLE Lev.LevCommunicatie ADD FOREIGN KEY (Lev) REFERENCES Lev.Lev;
ALTER TABLE Lev.LevCommunicatie ADD FOREIGN KEY (UitgaandBer) REFERENCES Ber.Ber;
ALTER TABLE Lev.LevPers ADD FOREIGN KEY (Lev) REFERENCES Lev.Lev;
ALTER TABLE Lev.LevPers ADD FOREIGN KEY (Pers) REFERENCES Kern.Pers;
ALTER TABLE Lev.His_Abonnement ADD FOREIGN KEY (Abonnement) REFERENCES Lev.Abonnement;
ALTER TABLE Lev.His_Abonnement ADD FOREIGN KEY (ActieInh) REFERENCES Kern.Actie;
ALTER TABLE Lev.His_Abonnement ADD FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie;
ALTER TABLE Lev.His_AbonnementSrtBer ADD FOREIGN KEY (AbonnementSrtBer) REFERENCES Lev.AbonnementSrtBer;
ALTER TABLE Lev.His_AbonnementSrtBer ADD FOREIGN KEY (ActieInh) REFERENCES Kern.Actie;
ALTER TABLE Lev.His_AbonnementSrtBer ADD FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie;
