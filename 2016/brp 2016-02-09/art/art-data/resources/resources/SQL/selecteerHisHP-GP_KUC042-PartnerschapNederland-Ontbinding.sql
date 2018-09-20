SELECT
  rel.id rel_id,
  substring(sr.naam,0,9) relatie,
  hhp.dataanv hhp_dataaanv,
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
  betr.pers betr_pers,
  betr.indouder,
  betr.id,
  betr.relatie,
  betr.rol,
  betr.indouderheeftgezag
FROM
  kern.his_relatie hhp,
  kern.srtrelatie sr,
  kern.relatie rel,
  kern.betr
WHERE
  sr.id = rel.srt AND
  hhp.relatie = rel.id AND betr.relatie = rel.id AND
  rel.id in (select relatie from kern.betr b where b.rol = 3 AND b.pers in(select id from kern.pers where bsn = ${DataSource Values#|objectid.persoon2|}))
  order by sr.naam, betr.pers, hhp.id ASC;
