select
   p.id,
   p.bsn,
   p.datgeboorte,
   p.voornamen,
   concat(p.voorvoegsel,
   p.scheidingsteken,
   p.geslnaamstam) voorvoegsel_scheidingsteken_geslnaamstam,
   prd.srt,
   srd.oms,
   hprd.id,
   hprd.persreisdoc,
   hprd.tsreg,
   hprd.tsverval,
   hprd.actieinh,
   hprd.actieverval,
   hprd.nr,
   hprd.autvanafgifte,
   hprd.datingangdoc,
   hprd.datuitgifte,
   hprd.dateindedoc,
   hprd.datinhingvermissing,
   hprd.aandinhingvermissing

from
   kern.pers p,
   kern.persreisdoc prd,
   kern.his_persreisdoc hprd,
   kern.srtnlreisdoc srd
where
   hprd.persreisdoc = prd.id and
   prd.pers = p.id and
   prd.srt= srd.id and
   p.id in (select id from kern.pers where bsn =  ${DataSource Values#|objectid.persoon2|})
order by
    p.bsn, hprd.TSVERVAL asc;
