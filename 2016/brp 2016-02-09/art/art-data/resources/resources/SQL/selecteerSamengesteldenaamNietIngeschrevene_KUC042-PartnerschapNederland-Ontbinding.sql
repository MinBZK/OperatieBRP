SELECT
  hsn.id hsn_id,
  hsn.pers hsn_pers,
  hsn.dataanvgel,
  hsn.dateindegel,
  hsn.tsreg,
  hsn.tsverval,
  hsn.actieinh,
  hsn.actieverval,
  hsn.actieaanpgel,
  hsn.indafgeleid,
  hsn.indnreeks,
  hsn.predicaat,
  hsn.voornamen,
  hsn.adellijketitel,
  hsn.voorvoegsel,
  hsn.scheidingsteken,
  hsn.geslnaamstam
FROM
  kern.his_perssamengesteldenaam hsn,
  kern.pers,
  kern.betr,
  kern.relatie rel

WHERE
  hsn.pers = pers.id AND
  pers.id = betr.pers AND
  betr.relatie = rel.id AND
  rel.id in (select relatie from kern.betr b where b.rol = 3 AND b.pers in(select id from kern.pers where bsn = ${DataSource Values#|objectid.persoon8|}))
  order by betr.pers, hsn_pers, hsn.geslnaamstam;
