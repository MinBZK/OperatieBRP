CREATE TABLE adres_huisletter
(
  waarde Varchar(1) NOT NULL,
  aantal integer NOT NULL,
  van bigint,
  tot_en_met bigint,
  CONSTRAINT "PK_adres_huisletter" PRIMARY KEY (waarde )
);

CREATE TABLE adres_huisnummer
(
  waarde Integer NOT NULL,
  aantal integer NOT NULL,
  van bigint,
  tot_en_met bigint,
  CONSTRAINT "PK_adres_huisnummer" PRIMARY KEY (waarde )
);

CREATE TABLE adres_huisnummertoevoeging
(
  waarde Varchar(4) NOT NULL,
  aantal integer NOT NULL,
  van bigint,
  tot_en_met bigint,
  CONSTRAINT "PK_adres_huisnummertoevoeging" PRIMARY KEY (waarde )
);

CREATE TABLE adres_nor
(
  waarde Varchar(80) NOT NULL,
  aantal integer NOT NULL,
  van bigint,
  tot_en_met bigint,
  CONSTRAINT "PK_adres_nor" PRIMARY KEY (waarde )
);

CREATE TABLE adres_postcode
(
  waarde Varchar(6) NOT NULL,
  aantal integer NOT NULL,
  van bigint,
  tot_en_met bigint,
  CONSTRAINT "PK_adres_postcode" PRIMARY KEY (waarde )
);

