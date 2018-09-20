DROP SEQUENCE IF EXISTS SEQ_FINGERPRINT;
DROP TABLE IF EXISTS fingerprint;
DROP TABLE IF EXISTS initvullingresult;

CREATE TABLE tmp_init_vulling_pl_act AS
  SELECT
    DISTINCT ON (pl.pl_id)
    pl.pl_id,
    pl.bijhouding_opschort_datum,
    pl.bijhouding_opschort_reden,
    pl.mutatie_dt,
    vbp.inschrijving_gemeente_code,
    act.activiteit_subtype,
    act.activiteit_id
  FROM lo3_pl pl
    JOIN activiteit act
      ON pl.pl_id = act.pl_id
    JOIN lo3_pl_verblijfplaats vbp
      ON pl.pl_id = vbp.pl_id AND vbp.volg_nr = 0
  WHERE (pl.bijhouding_opschort_reden IS NULL OR pl.bijhouding_opschort_reden != 'W')
        AND
        act.activiteit_type = 100 --inkomend bericht
        AND
        (act.activiteit_subtype = 1111 OR act.activiteit_subtype = 1112) --Lg01 of La01
        AND
        act.toestand = 8000
  ORDER BY pl.pl_id, act.laatste_actie_dt DESC
;

CREATE TABLE initvullingresult AS
  SELECT
    plact.pl_id                      AS "gbav_pl_id",
    plact.bijhouding_opschort_datum  AS "datum_opschorting",
    plact.bijhouding_opschort_reden  AS "reden_opschorting",
    persoon.a_nr                     AS "anummer",
    plact.mutatie_dt                 AS "datumtijd_opname_in_gbav",
    ber.lo3_bericht_id               AS "berichtidentificatie",
    plact.activiteit_subtype         AS "berichttype",
    plact.inschrijving_gemeente_code AS "gemeente_van_inschrijving",
    CASE
    WHEN plact.activiteit_subtype = '1111' -- Lg01 kop is 8+4+17+10+10=49
    THEN substring(ber.bericht_data FROM 50)
    WHEN plact.activiteit_subtype = '1112' -- La01 kop is 8+4=12
    THEN substring(ber.bericht_data FROM 13)
    END                              AS "bericht_inhoud",
    null :: TEXT                     AS "inhoud_na_terugconversie",
    null :: TEXT                     AS "bericht_diff",
    'TE_VERZENDEN' :: VARCHAR(200)   AS "conversie_resultaat",
    null :: TEXT                     AS "foutmelding",
    null :: INTEGER                  AS "foutcategorie",
    null :: VARCHAR(6)               AS "preconditie"

  FROM tmp_init_vulling_pl_act plact
    JOIN lo3_bericht ber
      ON plact.activiteit_id = ber.bericht_activiteit_id
    JOIN lo3_pl_persoon persoon
      ON plact.pl_id = persoon.pl_id
         AND
         persoon.persoon_type = 'P'
         AND
         persoon.volg_nr = 0
         AND
         persoon.stapel_nr = 0
;

ALTER TABLE initvullingresult ADD PRIMARY KEY (gbav_pl_id);
CREATE INDEX initvullingresult_gem_inschrijving ON initvullingresult (gemeente_van_inschrijving);
CREATE UNIQUE INDEX initvullingresult_anummer ON initvullingresult (anummer);
CREATE UNIQUE INDEX initvullingresult_resultaat_plid on initvullingresult (conversie_resultaat, gbav_pl_id);

CREATE TABLE fingerprint (
  id                 BIGINT NOT NULL,
  voorkomen_verschil VARCHAR(1000),
  gbav_pl_id         BIGINT,
  PRIMARY KEY (id)
);
ALTER TABLE fingerprint
ADD CONSTRAINT FKADFCE8E43D3973B0
FOREIGN KEY (gbav_pl_id)
REFERENCES initvullingresult (gbav_pl_id);

CREATE SEQUENCE SEQ_FINGERPRINT;

DROP TABLE tmp_init_vulling_pl_act;
