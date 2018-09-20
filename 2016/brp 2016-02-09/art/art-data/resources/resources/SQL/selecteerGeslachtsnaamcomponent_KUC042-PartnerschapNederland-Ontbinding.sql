SELECT
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
  hpgc.stam,
  pers.id pers_id
FROM
  kern.his_persgeslnaamcomp hpgc,
  kern.persgeslnaamcomp pgc,
  kern.pers
WHERE
 (pers.bsn = ${DataSource Values#|objectid.persoon7|}) AND
  hpgc.persgeslnaamcomp = pgc.id AND
  pers.id = pgc.pers
  order by hpgc.dataanvgel asc, hpgc.tsverval asc, hpgc.dateindegel asc;
