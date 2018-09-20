-- Maakt een info tabel die opgeleverd kan worden aan leveranciers

DROP TABLE info;

CREATE TABLE info
(
  pers integer,
  p_nummer_personage integer,
  bsn integer,
  anr bigint,
  functionelecode_gem smallint,
  wplnaam character varying(80),
  postcode character varying(6),
  nor character varying(80),
  huisnummer integer,
  huisnrtoevoeging character(3)
);

INSERT INTO info (pers, p_nummer_personage, bsn, anr, functionelecode_gem, wplnaam, postcode, nor, huisnummer, huisnrtoevoeging)
SELECT p.id, (p.id % 10000000) % 360, p.bsn, p.anr, pa.gem, pa.wplnaam, pa.postcode, pa.nor, pa.huisnr, pa.huisnrtoevoeging
FROM kern.pers p
JOIN kern.persadres pa ON (pa.pers = p.id)
ORDER BY p.id ASC;
