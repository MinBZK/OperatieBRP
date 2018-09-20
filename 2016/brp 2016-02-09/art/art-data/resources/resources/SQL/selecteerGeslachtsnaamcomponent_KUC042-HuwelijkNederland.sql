SELECT
  geslachtsnaam.id,
  geslachtsnaam.persgeslnaamcomp,
  geslachtsnaam.dataanvgel,
  geslachtsnaam.dateindegel,
  geslachtsnaam.tsreg,
  geslachtsnaam.tsverval,
  geslachtsnaam.actieinh,
  geslachtsnaam.actieverval,
  geslachtsnaam.actieaanpgel,
  geslachtsnaam.predicaat,
  geslachtsnaam.adellijketitel,
  geslachtsnaam.voorvoegsel,
  geslachtsnaam.scheidingsteken,
  geslachtsnaam.stam,
  pers.id
FROM
  kern.his_persgeslnaamcomp geslachtsnaam,
  kern.pers
WHERE
 (pers.bsn = ${DataSource Values#burgerservicenummer_ipp0}) AND
  geslachtsnaam.persgeslnaamcomp = pers.id
  order by geslachtsnaam.dataanvgel asc, geslachtsnaam.dateindegel asc;
