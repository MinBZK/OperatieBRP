SELECT
  substring(sr.naam,0,9) relatie,
  relatie.dataanv rel_dataanv,
  relatie.gemaanv rel_gemaanv,
  hp.gemaanv hp_gemaanv,
  hp.dateinde hp_dateinde,
  hp.gemeinde hp_gemeinde,
  relatie.wplnaamaanv rel_wplnaamaanv,
  relatie.blplaatsaanv,
  relatie.blregioaanv,
  relatie.landgebiedaanv,
  relatie.omslocaanv,
  relatie.rdneinde,
  relatie.dateinde,
  relatie.gemeinde,
  relatie.wplnaameinde,
  relatie.blplaatseinde,
  relatie.blregioeinde,
  relatie.landgebiedeinde,
  relatie.omsloceinde,
  relatie.id,
  relatie.srt rel_srt,
  betr.indouder betr_indouder,
  betr.id,
  betr.relatie,
  betr.rol,
  betr.pers,
  betr.indouderheeftgezag,
  pers.id pers_id
FROM
  kern.relatie relatie,
  kern.srtrelatie sr,
  kern.betr,
  kern.pers,
  kern.his_relatie hp
WHERE
  betr.relatie = relatie.id AND
  betr.relatie = hp.relatie AND
  sr.id = relatie.srt AND
  betr.pers = pers.id AND
  (relatie.srt = 1 OR relatie.srt = 2) AND
  (pers.bsn = ${DataSource Values#|objectid.persoon7|} or pers.bsn = ${DataSource Values#|objectid.persoon8|})
  order by betr.pers ASC, rel_srt DESC, hp_dateinde desc;
