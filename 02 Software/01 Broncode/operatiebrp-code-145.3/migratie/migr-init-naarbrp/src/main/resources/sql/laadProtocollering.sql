-- Stap 1: zoekt alle activiteiten die aan de voorwaarden voldoen om als protocollering overgezet te worden naar de BRP
INSERT INTO initvul.initvullingresult_protocollering_activiteit(
  activiteit_id,
  communicatie_partner,
  laatste_actie_dt,
  start_dt,
  laatste_actie_tijdstip,
  dienst_selectie,
  dienst_details_persoon,
  dienst_mutatielevering,
  dienst_attendering,
  dienst_plaatsen_afnind,
  a_nr,
  bijhouding_opschort_reden)
SELECT
  activiteit.activiteit_id AS activiteit_id,
  lpad(cast(cast(activiteit.communicatie_partner AS INTEGER) as VARCHAR(6)), 6, '0') AS communicatie_partner, -- koppeling met toegangleveringsautorisatie
  cast(to_char(activiteit.laatste_actie_dt, 'YYYYMMDD') AS INTEGER) AS laatste_actie_dt, -- koppeling met toegangleveringsautorisatie
  activiteit.start_dt,
  activiteit.laatste_actie_dt AS laatste_actie_tijdstip,
  CASE WHEN ( ( moeder_activiteit.activiteit_type = 106 ) OR activiteit.activiteit_subtype IN (1207) ) THEN true ELSE false END AS dienst_selectie,
  CASE WHEN ( ( moeder_activiteit.activiteit_type <> 106 ) OR ( moeder_activiteit.activiteit_type IS NULL ) ) and activiteit.activiteit_subtype IN (1216,1214,1750,1751,1321,1307,1226) THEN true ELSE false END AS dienst_details_persoon,
  CASE WHEN ( ( moeder_activiteit.activiteit_type <> 106 ) OR ( moeder_activiteit.activiteit_type IS NULL ) ) and activiteit.activiteit_subtype IN (1221,1222,1223,1213,1224,1321,1307,1226) THEN true ELSE false END AS dienst_mutatielevering,
  CASE WHEN ( ( moeder_activiteit.activiteit_type <> 106 ) OR ( moeder_activiteit.activiteit_type IS NULL ) ) and activiteit.activiteit_subtype IN (1211,1212,1213,1321,1307,1226) THEN true ELSE false END AS dienst_attendering,
  CASE WHEN ( ( moeder_activiteit.activiteit_type <> 106 ) OR ( moeder_activiteit.activiteit_type IS NULL ) ) and activiteit.activiteit_subtype IN (1210,1213,1321,1307,1226) THEN true ELSE false END AS dienst_plaatsen_afnind,
  lpad(cast(lo3_pl_persoon.a_nr as VARCHAR(10)), 10, '0'), -- koppeling met persoon
  lo3_pl.bijhouding_opschort_reden -- controle persoon is opgeschort met reden 'F'
FROM
  activiteit
LEFT JOIN
  activiteit moeder_activiteit ON activiteit.moeder_id = moeder_activiteit.activiteit_id
LEFT JOIN
  lo3_pl_persoon ON activiteit.pl_id = lo3_pl_persoon.pl_id
                AND lo3_pl_persoon.persoon_type = 'P'
                AND lo3_pl_persoon.stapel_nr = 0
                AND lo3_pl_persoon.volg_nr = 0
LEFT JOIN
  lo3_pl ON activiteit.pl_id = lo3_pl.pl_id
WHERE
  activiteit.toestand BETWEEN 8000 AND 8999
  AND activiteit.pl_id IS NOT NULL
  AND activiteit.communicatie_partner IS NOT NULL
  AND (  -- Selecties
         (   ( moeder_activiteit.activiteit_type = 106 )
         AND ( activiteit.activiteit_type = 101 AND activiteit.activiteit_subtype IN ( 1226, 1207, 1210, 1211, 1212, 1213, 1221, 1222, 1223, 1224 ) )
         )
      OR -- Overig
         (   (   ( moeder_activiteit.activiteit_type <> 106 ) OR  ( moeder_activiteit.activiteit_type IS NULL ) )
         AND (  ( activiteit.activiteit_type = 101 AND activiteit.activiteit_subtype IN ( 1226, 1207, 1210, 1211, 1212, 1213, 1221, 1222, 1223, 1224 ) )
             OR ( activiteit.activiteit_type = 102 AND activiteit.activiteit_subtype IN ( 1321, 1307 ) )
             OR ( activiteit.activiteit_type = 202 AND activiteit.activiteit_subtype IN ( 1214, 1216, 1750, 1751 ) )
             )
         )
      )
/* NAVULLING-BEPERKING-PLACEHOLDER */
;

-- Stap 2a (prepare): overhalen toegang leveringsautorisatie
INSERT INTO initvul.initvullingresult_protocollering_brp_toeglevaut(
  id,
  partij_code,
  datingang,
  dateinde)
SELECT
  toeganglevsautorisatie.id AS id,
  partij.code AS partij_code,
  toeganglevsautorisatie.datingang,
  toeganglevsautorisatie.dateinde
FROM
  autaut.toeganglevsautorisatie
JOIN
  kern.partijrol ON partijrol.id = toeganglevsautorisatie.geautoriseerde
JOIN
  kern.partij ON partij.id = partijrol.partij
;

-- Stap 2b (prepare): overhalen diensten
INSERT INTO initvul.initvullingresult_protocollering_brp_dienst(
  dienst_id,
  toeganglevsautorisatie_id,
  dienst_selectie,
  dienst_details_persoon,
  dienst_mutatielevering,
  dienst_attendering,
  dienst_plaatsen_afnind)
SELECT
  dienst.id AS dienst_id,
  toeganglevsautorisatie.id AS toeganglevsautorisatie_id,
  CASE WHEN dienstbundel.naam = 'Selectie' AND dienst.srt = 12 THEN true ELSE false END AS dienst_selectie,
  CASE WHEN dienstbundel.naam = 'Ad hoc' AND dienst.srt = 8 THEN true ELSE false END AS dienst_details_persoon,
  CASE WHEN dienstbundel.naam = 'Spontaan' AND dienst.srt = 2 THEN true ELSE false END AS dienst_mutatielevering,
  CASE WHEN dienstbundel.naam = 'Spontaan' AND dienst.srt = 4 THEN true ELSE false END AS dienst_attendering,
  CASE WHEN dienstbundel.naam = 'Ad hoc' AND dienst.srt = 3 THEN true ELSE false END AS dienst_plaatsen_afnind
FROM
  autaut.dienst
JOIN
  autaut.dienstbundel ON dienstbundel.id = dienst.dienstbundel
JOIN
  autaut.toeganglevsautorisatie ON toeganglevsautorisatie.levsautorisatie = dienstbundel.levsautorisatie
;

-- Stap 2c (prepare): overhalen pers
INSERT INTO initvul.initvullingresult_protocollering_brp_pers(
  id,
  anr)
SELECT
  pers.id,
  pers.anr
FROM
  kern.pers
WHERE
  pers.srt = 1
;

-- Stap 2a: zoekt Toegang leveringsautorisatie waarvan de geldigheid overeenkomt met die van de laatste actie datum van de activiteit
INSERT INTO initvul.initvullingresult_protocollering_toeglevaut(
  activiteit_id,
  dienst_selectie,
  dienst_details_persoon,
  dienst_mutatielevering,
  dienst_attendering,
  dienst_plaatsen_afnind,
  toeganglevsautorisatie_id,
  toeganglevsautorisatie_count)
SELECT
  activiteit.activiteit_id,
  activiteit.dienst_selectie,
  activiteit.dienst_details_persoon,
  activiteit.dienst_mutatielevering,
  activiteit.dienst_attendering,
  activiteit.dienst_plaatsen_afnind,
  min(toeglevaut.id) AS toeganglevsautorisatie_id,
  count(toeglevaut.id) AS toeganglevsautorisatie_count
FROM
  initvul.initvullingresult_protocollering_activiteit activiteit
JOIN
  initvul.initvullingresult_protocollering_brp_toeglevaut toeglevaut ON activiteit.communicatie_partner = toeglevaut.partij_code
                                                                    AND toeglevaut.datingang <= activiteit.laatste_actie_dt
                                                                    AND (  ( toeglevaut.dateinde IS NULL )
                                                                        OR ( toeglevaut.dateinde > activiteit.laatste_actie_dt)
                                                                        )
GROUP BY
  activiteit.activiteit_id,
  activiteit.dienst_selectie,
  activiteit.dienst_details_persoon,
  activiteit.dienst_mutatielevering,
  activiteit.dienst_attendering,
  activiteit.dienst_plaatsen_afnind
;

-- Stap 2b: bepaalt bij welke dienst de GBA-V-activiteit hoort
INSERT INTO initvul.initvullingresult_protocollering_dienst(
  activiteit_id,
  toeganglevsautorisatie_id,
  toeganglevsautorisatie_count,
  dienst_selectie_id,
  dienst_details_persoon_id,
  dienst_mutatielevering_id,
  dienst_attendering_id,
  dienst_plaatsen_afnind_id)
SELECT activiteit.activiteit_id,
  activiteit.toeganglevsautorisatie_id,
  activiteit.toeganglevsautorisatie_count,
  dienst_selectie.dienst_id dienst_selectie_id,
  dienst_details_persoon.dienst_id dienst_details_persoon_id,
  dienst_mutatielevering.dienst_id dienst_mutatielevering_id,
  dienst_attendering.dienst_id dienst_attendering_id,
  dienst_plaatsen_afnind.dienst_id dienst_plaatsen_afnind_id
FROM
  initvul.initvullingresult_protocollering_toeglevaut activiteit
LEFT JOIN
  initvul.initvullingresult_protocollering_brp_dienst dienst_selectie ON dienst_selectie.toeganglevsautorisatie_id = activiteit.toeganglevsautorisatie_id
                                                                     AND dienst_selectie.dienst_selectie = TRUE
                                                                     AND activiteit.dienst_selectie = TRUE
LEFT JOIN
  initvul.initvullingresult_protocollering_brp_dienst dienst_details_persoon ON dienst_details_persoon.toeganglevsautorisatie_id = activiteit.toeganglevsautorisatie_id
                                                                            AND dienst_details_persoon.dienst_details_persoon = TRUE
                                                                            AND activiteit.dienst_details_persoon = TRUE
LEFT JOIN
  initvul.initvullingresult_protocollering_brp_dienst dienst_mutatielevering ON dienst_mutatielevering.toeganglevsautorisatie_id = activiteit.toeganglevsautorisatie_id
                                                                            AND dienst_mutatielevering.dienst_mutatielevering = TRUE
                                                                            AND activiteit.dienst_mutatielevering = TRUE
LEFT JOIN
  initvul.initvullingresult_protocollering_brp_dienst dienst_attendering ON dienst_attendering.toeganglevsautorisatie_id = activiteit.toeganglevsautorisatie_id
                                                                        AND dienst_attendering.dienst_attendering = TRUE
                                                                        AND activiteit.dienst_attendering = TRUE
LEFT JOIN
  initvul.initvullingresult_protocollering_brp_dienst dienst_plaatsen_afnind ON dienst_plaatsen_afnind.toeganglevsautorisatie_id = activiteit.toeganglevsautorisatie_id
                                                                            AND dienst_plaatsen_afnind.dienst_plaatsen_afnind = TRUE
                                                                            AND activiteit.dienst_plaatsen_afnind = TRUE
;

-- Stap 2c: bepaalt welke persoon in BRP bij de activiteit hoort
INSERT INTO initvul.initvullingresult_protocollering(
  activiteit_id,
  pers_id,
  bijhouding_opschort_reden,
  toeganglevsautorisatie_id,
  toeganglevsautorisatie_count,
  dienst_id,
  start_dt,
  laatste_actie_dt,
  conversie_resultaat)
SELECT
  activiteit.activiteit_id,
  pers.id AS pers_id,
  activiteit.bijhouding_opschort_reden,
  CASE
    WHEN toeganglevsautorisatie_count = 1
    THEN toeganglevsautorisatie_id
    ELSE null
  END as toeganglevsautorisatie_id,
  coalesce(toeganglevsautorisatie_count, 0),
  CASE
    WHEN toeganglevsautorisatie_count = 1
    THEN coalesce(dienst_selectie_id, dienst_details_persoon_id, dienst_mutatielevering_id, dienst_attendering_id, dienst_plaatsen_afnind_id)
    ELSE null
  END AS dienst_id,
  start_dt,
  laatste_actie_tijdstip,
  'TE_VERZENDEN'
FROM
  initvul.initvullingresult_protocollering_activiteit activiteit
LEFT JOIN
  initvul.initvullingresult_protocollering_brp_pers pers ON activiteit.a_nr = pers.anr
LEFT JOIN
  initvul.initvullingresult_protocollering_dienst dienst ON activiteit.activiteit_id = dienst.activiteit_id
;
