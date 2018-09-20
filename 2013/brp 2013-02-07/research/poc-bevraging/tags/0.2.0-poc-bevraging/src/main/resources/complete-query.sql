#-- PersoonModel
SELECT
  pred.id AS predikaat_persoon_id, pred.code AS predikaat_persoon_code, pred.naammannelijk AS predikaat_persoon_naammannelijk, pred.naamvrouwelijk AS predikaat_persoon_naamvrouwelijk,
  adel.id AS titel_persoon_id, adel.code AS titel_persoon_code, adel.naammannelijk AS titel_persoon_naammannelijk, adel.naamvrouwelijk AS titel_persoon_naamvrouwelijk,

  gbl.id AS land_geboorte_id, gbl.code AS land_geboorte_code, gbl.naam AS land_geboorte_naam, gbl.iso31661alpha2 AS land_geboorte_iso31661alpha2, gbl.dataanvgel AS land_geboorte_dataanvgel, gbl.dateindegel AS land_geboorte_dateindegel,
  gbp.id AS plaats_geboorte_id, gbp.code AS plaats_geboorte_code, gbp.naam AS plaats_geboorte_naam, gbp.dataanvgel AS plaats_geboorte_dataanvgel, gbp.dateindegel AS plaats_geboorte_dateindegel,
  gbpa.id AS partij_geboorte_id, gbpa.naam AS partij_geboorte_naam, gbpa.srt AS partij_geboorte_srt, gbpa.code AS partij_geboorte_code, gbpa.voortzettendegem AS partij_geboorte_voortzettendegem, gbpa.onderdeelvan AS partij_geboorte_onderdeelvan, gbpa.gemstatushis AS partij_geboorte_gemstatushis, gbpa.dateinde AS partij_geboorte_dateinde, gbpa.dataanv AS partij_geboorte_dataanv, gbpa.sector AS partij_geboorte_sector, gbpa.partijstatushis AS partij_geboorte_partijstatushis,

  aapr.id AS predikaat_aanschrijving_id, aapr.code AS predikaat_aanschrijving_code, aapr.naammannelijk AS predikaat_aanschrijving_naammannelijk, aapr.naamvrouwelijk AS predikaat_aanschrijving_naamvrouwelijk,
  aaat.id AS titel_aanschrijving_id, aaat.code AS titel_aanschrijving_code, aaat.naammannelijk AS titel_aanschrijving_naammannelijk, aaat.naamvrouwelijk AS titel_aanschrijving_naamvrouwelijk,

  bhpa.id AS partij_bijhouding_id, bhpa.naam AS partij_bijhouding_naam, bhpa.srt AS partij_bijhouding_srt, bhpa.code AS partij_bijhouding_code, bhpa.voortzettendegem AS partij_bijhouding_voortzettendegem, bhpa.onderdeelvan AS partij_bijhouding_onderdeelvan, bhpa.gemstatushis AS partij_bijhouding_gemstatushis, bhpa.dateinde AS partij_bijhouding_dateinde, bhpa.dataanv AS partij_bijhouding_dataanv, bhpa.sector AS partij_bijhouding_sector, bhpa.partijstatushis AS partij_bijhouding_partijstatushis,

  ovl.id AS land_overlijden_id, ovl.code AS land_overlijden_code, ovl.naam AS land_overlijden_naam, ovl.iso31661alpha2 AS land_overlijden_iso31661alpha2, ovl.dataanvgel AS land_overlijden_dataanvgel, ovl.dateindegel AS land_overlijden_dateindegel,
  ovp.id AS plaats_overlijden_id, ovp.code AS plaats_overlijden_code, ovp.naam AS plaats_overlijden_naam, ovp.dataanvgel AS plaats_overlijden_dataanvgel, ovp.dateindegel AS plaats_overlijden_dateindegel,
  ovpa.id AS partij_overlijden_id, ovpa.naam AS partij_overlijden_naam, ovpa.srt AS partij_overlijden_srt, ovpa.code AS partij_overlijden_code, ovpa.voortzettendegem AS partij_overlijden_voortzettendegem, ovpa.onderdeelvan AS partij_overlijden_onderdeelvan, ovpa.gemstatushis AS partij_overlijden_gemstatushis, ovpa.dateinde AS partij_overlijden_dateinde, ovpa.dataanv AS partij_overlijden_dataanv, ovpa.sector AS partij_overlijden_sector, ovpa.partijstatushis AS partij_overlijden_partijstatushis,

  vblr.id AS verblijfsrecht_id, vblr.oms AS verblijfsrecht_oms, vblr.dataanvgel AS verblijfsrecht_dataanvgel, vblr.dateindegel AS verblijfsrecht_dateindegel,

  iml.id AS land_immigratie_id, iml.code AS land_immigratie_code, iml.naam AS land_immigratie_naam, iml.iso31661alpha2 AS land_immigratie_iso31661alpha2, iml.dataanvgel AS land_immigratie_dataanvgel, iml.dateindegel AS land_immigratie_dateindegel,
  pkp.id AS partij_persoonskaart_id, pkp.naam AS partij_persoonskaart_naam, pkp.srt AS partij_persoonskaart_srt, pkp.code AS partij_persoonskaart_code, pkp.voortzettendegem AS partij_persoonskaart_voortzettendegem, pkp.onderdeelvan AS partij_persoonskaart_onderdeelvan, pkp.gemstatushis AS partij_persoonskaart_gemstatushis, pkp.dateinde AS partij_persoonskaart_dateinde, pkp.dataanv AS partij_persoonskaart_dataanv, pkp.sector AS partij_persoonskaart_sector, pkp.partijstatushis AS partij_persoonskaart_partijstatushis,

  kp.*
FROM kern.pers kp
LEFT JOIN kern.predikaat pred ON pred.id = kp.predikaat
LEFT JOIN kern.adellijketitel adel ON adel.id = kp.adellijketitel
LEFT JOIN kern.partij gbpa ON gbpa.id = kp.gemgeboorte
LEFT JOIN kern.plaats gbp ON gbp.id = kp.wplGeboorte
LEFT JOIN kern.land gbl ON gbl.id = kp.landgeboorte
LEFT JOIN kern.predikaat aapr ON aapr.id = kp.predikaataanschr
LEFT JOIN kern.adellijketitel aaat ON aaat.id = kp.adellijketitelaanschr
LEFT JOIN kern.partij bhpa ON bhpa.id = kp.bijhgem
LEFT JOIN kern.partij ovpa ON ovpa.id = kp.gemoverlijden
LEFT JOIN kern.plaats ovp ON ovp.id = kp.wploverlijden
LEFT JOIN kern.land ovl ON ovl.id = kp.landoverlijden
LEFT JOIN kern.verblijfsr vblr ON vblr.id = kp.verblijfsr
LEFT JOIN kern.land iml ON iml.id = kp.landvanwaargevestigd
LEFT JOIN kern.partij pkp ON pkp.id = kp.gempk
WHERE kp.bsn = 123456789;

#-- PersoonVoornaamModel
SELECT
  *
FROM kern.persvoornaam kpv
WHERE kpv.pers = 1;

#-- PersoonAdresModel
SELECT
  l.id AS land_id, l.code AS land_code, l.naam AS land_naam, l.iso31661alpha2 AS land_iso31661alpha2, l.dataanvgel AS land_dataanvgel, l.dateindegel AS land_dateindegel,
  p.id AS plaats_id, p.code AS plaats_code, p.naam AS plaats_naam, p.dataanvgel AS plaats_dataanvgel, p.dateindegel AS plaats_dateindegel,
  pa.id AS partij_id, pa.naam AS partij_naam, pa.srt AS partij_srt, pa.code AS partij_code, pa.voortzettendegem AS partij_voortzettendegem, pa.onderdeelvan AS partij_onderdeelvan, pa.gemstatushis AS partij_gemstatushis, pa.dateinde AS partij_dateinde, pa.dataanv AS partij_dataanv, pa.sector AS partij_sector, pa.partijstatushis AS partij_partijstatushis,
  rdn.id AS reden_id, rdn.code AS reden_code, rdn.naam AS reden_naam,
  kpa.*
FROM kern.persadres kpa
LEFT JOIN kern.partij pa ON pa.id = kpa.gem
LEFT JOIN kern.plaats p ON p.id = kpa.wpl
LEFT JOIN kern.land l ON l.id = kpa.land
LEFT JOIN kern.rdnwijzadres rdn ON rdn.id = kpa.rdnwijz
WHERE kpa.pers = 1;

#-- PersoonGeslachtsnaamcomponentGroep
SELECT
  pred.id AS predikaat_geslnaamcomp_id, pred.code AS predikaat_geslnaamcomp_code, pred.naammannelijk AS predikaat_geslnaamcomp_naammannelijk, pred.naamvrouwelijk AS predikaat_geslnaamcomp_naamvrouwelijk,
  adel.id AS titel_geslnaamcomp_id, adel.code AS titel_geslnaamcomp_code, adel.naammannelijk AS titel_geslnaamcomp_naammannelijk, adel.naamvrouwelijk AS titel_geslnaamcomp_naamvrouwelijk,
  kpg.*
FROM kern.persgeslnaamcomp kpg
LEFT JOIN kern.predikaat pred ON pred.id = kpg.predikaat
LEFT JOIN kern.adellijketitel adel ON adel.id = kpg.adellijketitel
WHERE kpg.pers = 1;

#-- PersoonNationalitiet
SELECT
  nat.id AS nation_id, nat.naam AS nation_naam, nat.nationcode AS nation_nationcode,
  verk.id AS verkrijgen_id, verk.code AS verkrijgen_code, verk.oms AS verkrijgen_oms,
  verl.id AS verlies_id, verl.oms AS verlies_oms,
  kpn.*
FROM kern.persnation kpn
LEFT JOIN kern.nation nat ON nat.id = kpn.nation
LEFT JOIN kern.rdnverknlnation verk ON verk.id = kpn.rdnverk
LEFT JOIN kern.rdnverliesnlnation verl ON verl.id = kpn.rdnverlies
WHERE kpn.pers = 1;

#-- PersoonIndicatie
SELECT
  *
FROM kern.persindicatie kpi
WHERE kpi.pers = 1;

#-- Betrokkenheden
SELECT betr.*,
    rel.id AS relatie_id,
    rel.dataanv AS relatie_dataanv,
    rel.gemaanv AS relatie_gemaanv,
    rel.wplaanv AS relatie_wplaanv,
    rel.blplaatsaanv AS relatie_blplaatsaanv,
    rel.blregioaanv AS relatie_blregioaanv,
    rel.landaanv AS relatie_landaanv,
    rel.omslocaanv AS relatie_omslocaanv,
    rel.rdneinde AS relatie_rdneinde,
    rel.dateinde AS relatie_dateinde,
    rel.gemeinde AS relatie_gemeinde,
    rel.wpleinde AS relatie_wpleinde,
    rel.blplaatseinde AS relatie_blplaatseinde,
    rel.blregioeinde AS relatie_blregioeinde,
    rel.landeinde AS relatie_landeinde,
    rel.omsloceinde AS relatie_omsloceinde,
    rel.relatiestatushis AS relatie_relatiestatushis,
    rel.srt AS relatie_srt,
    landAanv.id AS land_relatie_aanvang_id,
    landAanv.code AS land_relatie_aanvang_code,
    landAanv.naam AS land_relatie_aanvang_naam,
    landAanv.iso31661alpha2 AS land_relatie_aanvang_iso31661alpha2,
    landAanv.dataanvgel AS land_relatie_aanvang_dataanvgel,
    landAanv.dateindegel AS land_relatie_aanvang_dateindegel,
    landEind.id AS land_relatie_einde_id,
    landEind.code AS land_relatie_einde_code,
    landEind.naam AS land_relatie_einde_naam,
    landEind.iso31661alpha2 AS land_relatie_einde_iso31661alpha2,
    landEind.dataanvgel AS land_relatie_einde_dataanvgel,
    landEind.dateindegel AS land_relatie_einde_dateindegel,
    partijAanv.id AS partij_relatie_aanvang_id,
    partijAanv.code AS partij_relatie_aanvang_code,
    partijAanv.naam AS partij_relatie_aanvang_naam,
    partijAanv.dataanv AS partij_relatie_aanvang_dataanv,
    partijAanv.dateinde AS partij_relatie_aanvang_dateinde,
    partijAanv.voortzettendegem AS partij_relatie_aanvang_voortzettendegem,
    partijAanv.onderdeelvan AS partij_relatie_aanvang_onderdeelvan,
    partijAanv.gemstatushis AS partij_relatie_aanvang_gemstatushis,
    partijAanv.sector AS partij_relatie_aanvang_sector,
    partijAanv.partijstatushis AS partij_relatie_aanvang_partijstatushis,
    partijAanv.srt AS partij_relatie_aanvang_srt,
    partijEind.id AS partij_relatie_einde_id,
    partijEind.code AS partij_relatie_einde_code,
    partijEind.naam AS partij_relatie_einde_naam,
    partijEind.dataanv AS partij_relatie_einde_dataanv,
    partijEind.dateinde AS partij_relatie_einde_dateinde,
    partijEind.voortzettendegem AS partij_relatie_einde_voortzettendegem,
    partijEind.onderdeelvan AS partij_relatie_einde_onderdeelvan,
    partijEind.gemstatushis AS partij_relatie_einde_gemstatushis,
    partijEind.sector AS partij_relatie_einde_sector,
    partijEind.partijstatushis AS partij_relatie_einde_partijstatushis,
    partijEind.srt AS partij_relatie_einde_srt,
    plaatsAanv.id AS plaats_relatie_aanvang_id,
    plaatsAanv.code AS plaats_relatie_aanvang_code,
    plaatsAanv.naam AS plaats_relatie_aanvang_naam,
    plaatsAanv.dataanvgel AS plaats_relatie_aanvang_dataanvgel,
    plaatsAanv.dateindegel AS plaats_relatie_aanvang_dateindegel,
    plaatsEind.id AS plaats_relatie_einde_id,
    plaatsEind.code AS plaats_relatie_einde_code,
    plaatsEind.naam AS plaats_relatie_einde_naam,
    plaatsEind.dataanvgel AS plaats_relatie_einde_dataanvgel,
    plaatsEind.dateindegel AS plaats_relatie_einde_dateindegel,
    redenEind.id AS rdneinde_relatie_id,
    redenEind.code AS rdneinde_relatie_code,
    redenEind.oms AS rdneinde_relatie_oms
FROM kern.betr betr
LEFT JOIN kern.relatie rel ON rel.id = betr.relatie
LEFT JOIN kern.land landAanv ON rel.landaanv = landAanv.id
LEFT JOIN kern.land landEind ON rel.landeinde = landEind.id
LEFT JOIN kern.partij partijAanv ON rel.gemaanv = partijAanv.id
LEFT JOIN kern.partij partijEind ON rel.gemeinde = partijEind.id
LEFT JOIN kern.plaats plaatsAanv ON rel.wplaanv = plaatsAanv.id
LEFT JOIN kern.plaats plaatsEind ON rel.wpleinde = plaatsEind.id
LEFT JOIN kern.rdnbeeindrelatie redenEind ON rel.rdneinde = redenEind.id
WHERE betr.pers = ?;

#-- Relatie betrokkenheden
SELECT betr.*,
FROM kern.betr betr
LEFT JOIN kern.relatie rel ON rel.id = betr.relatie
WHERE betr.relatie = 1
