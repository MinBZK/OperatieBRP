SELECT
  substring(sr.naam,0,9) relatie,
  hhp.dataanv,
  hhp.gemaanv,
  hhp.wplnaamaanv,
  hhp.blplaatsaanv,
  hhp.blregioaanv,
  hhp.landgebiedaanv,
  hhp.omslocaanv,
  hhp.rdneinde,
  to_char(hhp.tsverval, 'YYYYMMDD') tsverval,
  hhp.actieverval,
  hhp.dateinde,
  hhp.gemeinde,
  hhp.wplnaameinde,
  hhp.blplaatseinde,
  hhp.blregioeinde,
  hhp.landgebiedeinde,
  hhp.omsloceinde,
  hhp.id,
  betr.indouder,
  betr.id,
  betr.relatie,
  betr.rol,
  betr.pers,
  betr.indouderheeftgezag,
  pers.id
FROM
  kern.his_relatie hhp,
  kern.srtrelatie sr,
  kern.relatie rel,
  kern.betr,
  kern.pers
WHERE
  betr.relatie = rel.id AND
  sr.id = rel.srt AND
  hhp.relatie = rel.id AND
  betr.pers = pers.id AND betr.rol = 3 AND
  (pers.bsn = ${DataSource Values#|objectid.persoon7|} or pers.bsn = ${DataSource Values#|objectid.persoon8|})
  order by sr.naam, betr.pers, hhp.id ASC;
