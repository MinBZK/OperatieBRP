SELECT
  rel.id rel_id,
  pers.id pers_id,
  hpgc.id hpgc_id,
  hpgc.persgeslnaamcomp,
  hpgc.dataanvgel,
  hpgc.dateindegel,
  hpgc.tsreg,
  hpgc.tsverval,
  hpgc.actieinh,
  hpgc.actieverval,
  hpgc.actieaanpgel,
  hpgc.predicaat,
  hpgc.adellijketitel,
  hpgc.voorvoegsel,
  hpgc.scheidingsteken,
  hpgc.stam
FROM
  kern.his_persgeslnaamcomp hpgc,
  kern.persgeslnaamcomp pgc,
  kern.pers,
  kern.betr,
  kern.relatie rel

WHERE
  hpgc.persgeslnaamcomp = pgc.id AND
  pers.id = pgc.pers AND
  pers.id = betr.pers AND
  betr.relatie = rel.id AND
  rel.id in (select relatie from kern.betr b where b.rol = 3 AND b.pers in(select id from kern.pers where bsn = ${DataSource Values#|objectid.persoon8|}))
  order by betr.pers;
