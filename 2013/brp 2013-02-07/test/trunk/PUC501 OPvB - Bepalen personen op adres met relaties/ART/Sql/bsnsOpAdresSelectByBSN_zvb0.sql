select 
  p1.bsn bsn, 
  pa1.srt srt,
  pa1.rdnwijz rdnwijz,
  pa1.aangadresh aangadresh,
  pa1.dataanvadresh dataanvadresh,
  pa1.adresseerbaarobject adresseerbaarobject,
  pa1.identcodenraand identcodenraand,
  pa1.gem gem,
  pa1.nor nor,
  pa1.afgekortenor afgekortenor,
  pa1.gemdeel gemdeel,
  pa1.huisnr huisnr,
  pa1.huisletter huisletter,
  pa1.huisnrtoevoeging huisnrtoevoeging,
  pa1.postcode postcode,
  pa1.wpl wpl,
  pa1.loctovadres loctovadres,
  pa1.locoms locoms,
  pa1.bladresregel1 bladresregel1,
  pa1.bladresregel2 bladresregel2,
  pa1.bladresregel3 bladresregel3,
  pa1.bladresregel4 bladresregel4,
  pa1.bladresregel5 bladresregel5,
  pa1.bladresregel6 bladresregel6,
  pa1.land land,
  pa1.datvertrekuitnederland datvertrekuitnederland,
  pa1.persadresstatushis persadresstatushis,
  pa1.id id,
  pa1.pers pers  
from 
  kern.persadres pa1, 
  kern.pers p1, 
  kern.persadres pa2, 
  kern.pers p2 
where 
  pa2.pers = p2.id and 
  pa1.pers = p1.id and 
  p2.bsn in (${datasource values#burgerservicenummer_zvb0}) and 
  pa1.adresseerbaarobject = pa2.adresseerbaarobject and 
  pa1.identcodenraand = pa2.identcodenraand and 
  pa1.land = pa2.land 
order by p1.id