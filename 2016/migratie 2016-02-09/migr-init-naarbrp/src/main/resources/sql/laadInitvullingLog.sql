INSERT INTO initvul.initvullingresult (
  gbav_pl_id,
  datum_opschorting,
  reden_opschorting,
  anummer,
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
    persoon.a_nr,
    pl.mutatie_dt,
    bericht.lo3_bericht_id,
    CASE
        WHEN
            bericht_data like '00000000Lg01%'
        THEN
            1111
        WHEN
            bericht_data like '00000000La01%'
        THEN
            1112
    END,
    substring(bericht.originator_or_recipient, 1, 4),
    CASE
        WHEN
            bericht_data like '00000000Lg01%'
        THEN  -- Lg01 kop is 8+4+17+10+10=49
            substring(bericht_data FROM 50)
        WHEN
            bericht_data like '00000000La01%'
        THEN  -- La01 kop is 8+4=12
            substring(bericht_data FROM 13)
    END,
    CASE
        WHEN
            bericht_data is null
        THEN
            'GEEN_BRON_BERICHT'
        ELSE
            'TE_VERZENDEN'
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
