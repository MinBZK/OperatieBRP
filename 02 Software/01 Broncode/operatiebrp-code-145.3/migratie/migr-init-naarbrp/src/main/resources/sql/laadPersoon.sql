INSERT INTO initvul.initvullingresult (
  gbav_pl_id,
  datum_opschorting,
  reden_opschorting,
  anummer,
  bsn,
  datumtijd_opname_in_gbav,
  berichtidentificatie,
  berichttype,
  gemeente_van_inschrijving,
  bericht_inhoud,
  conversie_resultaat
)
SELECT
    pl.pl_id,
    pl.bijhouding_opschort_datum,
    pl.bijhouding_opschort_reden,
    lpad(cast(persoon.a_nr as VARCHAR(10)), 10, '0'),
    lpad(cast(persoon.burger_service_nr as VARCHAR(9)), 9, '0'),
    pl.mutatie_dt,
    bericht.lo3_bericht_id,
    CASE
        WHEN bericht_data like '00000000Lg01%'
            THEN 'Lg01'
        WHEN bericht_data like '00000000La01%'
            THEN 'La01'
    END,
    substring(bericht.originator_or_recipient, 1, 4),
    CASE
        WHEN bericht_data like '00000000Lg01%'
            THEN substring(bericht_data FROM 50)  -- Lg01 kop is 8+4+17+10+10=49
        WHEN bericht_data like '00000000La01%'
            THEN substring(bericht_data FROM 13) -- La01 kop is 8+4=12
    END,
    CASE
        WHEN bericht_data is null
            THEN 'GEEN_BRON_BERICHT'
        WHEN bericht_data like '00000000Lg01%'
            THEN 'TE_VERZENDEN'
        WHEN bericht_data like '00000000La01%'
            THEN 'TE_VERZENDEN'
        ELSE 'ONGELDIG_BRON_BERICHT'
    END
FROM
        lo3_pl pl
LEFT JOIN
        lo3_pl_persoon persoon
ON
        pl.pl_id = persoon.pl_id
AND
        persoon.persoon_type = 'P'
AND
        persoon.volg_nr = 0
AND
        persoon.stapel_nr = 0
LEFT JOIN
        lo3_bericht bericht
ON
        bericht.bericht_activiteit_id = pl.mutatie_activiteit_id
WHERE (pl.bijhouding_opschort_reden IS NULL OR pl.bijhouding_opschort_reden != 'W')
ORDER BY pl.pl_id ASC;

-- PL-en waarbij het BSN overeenkomt en de reden opschorting niet F is, melden als DUBBEL_BSN
UPDATE
  initvul.initvullingresult
SET
  conversie_resultaat = 'DUBBEL_BSN'
WHERE
  (reden_opschorting IS NULL OR reden_opschorting <> 'F')
AND
  gbav_pl_id IN (
    SELECT
      gbav_pl_id
    FROM
      initvul.initvullingresult i
    WHERE EXISTS
      (
        SELECT
          1
        FROM
          initvul.initvullingresult j
        WHERE
          i.bsn = j.bsn
        AND
          i.gbav_pl_id != j.gbav_pl_id
        AND (j.reden_opschorting IS NULL OR j.reden_opschorting <> 'F')
      )
);
