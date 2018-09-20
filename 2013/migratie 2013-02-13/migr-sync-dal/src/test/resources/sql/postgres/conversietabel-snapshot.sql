
-- Schemas ---------------------------------------------------------------------
drop schema IF EXISTS Conversietabel CASCADE;
CREATE SCHEMA Conversietabel;


-- Hieronder worden alle tabellen aangemaakt:

CREATE TABLE conversietabel.voorvoegsel
(
  lo3voorvoegsel character varying(10) NOT NULL,
  brpvoorvoegsel character varying(10) NOT NULL,
  brpscheidingsteken character(1) NOT NULL,
  CONSTRAINT voorvoegsel_pkey PRIMARY KEY (lo3voorvoegsel ),
  CONSTRAINT voorvoegsel_brpvoorvoegsel_brpscheidingsteken_key UNIQUE (brpvoorvoegsel , brpscheidingsteken )
);

CREATE TABLE conversietabel.adellijketitelpredikaat
(
  lo3adellijketitelpredikaat character varying(2) NOT NULL,
  brpgeslachtsaanduiding smallint NOT NULL,
  brpadellijketitel character(1),
  brppredikaat character(1),
  CONSTRAINT adellijketitelpredikaat_pkey PRIMARY KEY (lo3adellijketitelpredikaat),
  CONSTRAINT adellijketitelpredikaat_brpadellijke_key UNIQUE (brpadellijketitel, brppredikaat, brpgeslachtsaanduiding)
);


CREATE TABLE conversietabel.redenverkrijgingverliesnlschap
(
  lo3code character(3) NOT NULL,
  brpredenverkrijgingnaam character varying(40),
  brpredenverliesnaam character varying(40),
  CONSTRAINT redenverkrijgingverliesnlschap_lo3code_pkey PRIMARY KEY (lo3code),
  CONSTRAINT redenverkrijgingverliesnlschap_brpnaam_key UNIQUE (brpredenverkrijgingnaam , brpredenverliesnaam)
);

CREATE TABLE conversietabel.aangifteadreshouding
(
  lo3code character(1) NOT NULL,
  brpaangeveradreshoudingcode character(1),
  brpredenwijzigingadrescode character(1),
  CONSTRAINT aangifteadreshouding_lo3code_pkey PRIMARY KEY (lo3code),
  CONSTRAINT aangifteadreshouding_brpcode_key UNIQUE (brpaangeveradreshoudingcode , brpredenwijzigingadrescode)
);

CREATE TABLE conversietabel.verblijfstitel
(
  lo3code character(2) NOT NULL,
  brpomschrijving character varying(250) NOT NULL,
  CONSTRAINT verblijfstitel_lo3code_pkey PRIMARY KEY (lo3code),
  CONSTRAINT verblijfstitel_brpomschrijving_key UNIQUE (brpomschrijving)
);

CREATE TABLE conversietabel.soortnlreisdocument
(
  lo3code character(2) NOT NULL,
  brpcode character varying(2) NOT NULL,
  CONSTRAINT soortnlreisdocument_lo3code_pkey PRIMARY KEY (lo3code),
  CONSTRAINT soortnlreisdocument_brpcode_key UNIQUE (brpcode)
);

CREATE TABLE conversietabel.redeninhoudingvermissingreisdocument
(
  lo3code character(1) NOT NULL,
  brpcode character(1) NOT NULL,
  CONSTRAINT redenvervallenreisdocument_lo3code_pkey PRIMARY KEY (lo3code),
  CONSTRAINT redenvervallenreisdocument_brpcode_key UNIQUE (brpcode)
);

CREATE TABLE conversietabel.autoriteitvanafgifte
(
  lo3code character varying(6) NOT NULL,
  brpcode character varying(6) NOT NULL,
  CONSTRAINT autoriteitvanafgifte_lo3code_pkey PRIMARY KEY (lo3code),
  CONSTRAINT autoriteitvanafgifte_brpcode_key UNIQUE (brpcode)
);

CREATE TABLE conversietabel.redenopschorting
(
  lo3code character(1) NOT NULL,
  brpcode character(1),
  CONSTRAINT redenopschorting_lo3code_pkey PRIMARY KEY (lo3code),
  CONSTRAINT redenopschorting_brpcode_key UNIQUE (brpcode)
);

CREATE TABLE conversietabel.redenontbindinghuwelijkpartnerschap
(
  lo3code character(1) NOT NULL,
  brpcode character(1) NOT NULL,
  CONSTRAINT redenontbindinghuwelijkpartnerschap_lo3code_pkey PRIMARY KEY (lo3code),
  CONSTRAINT redenontbindinghuwelijkpartnerschap_brpcode_key UNIQUE (brpcode)
);

