select
  hpa.id,
  hpa.actieinh,
  hpa.actieverval,
  hpa.actieaanpgel,
  actie.id actie_id,
  actie.srt actie_srt,
  partij.code actie_partij,
  actie.admhnd actie_verdrag,
  actie.tsreg actie_tsreg ,
  p.voornamen,
  p.geslnaamstam,
  trim(to_char(hpa.dataanvgel,'0000-00-00'))dataanvgel,
  trim(to_char(hpa.dateindegel, '0000-00-00'))dateindegel,
  hpa.tsreg,
  trim(to_char(hpa.dataanvadresh,'0000-00-00'))dataanvadresh,
  gem.naam gemeente,
  pl.naam plaats,
  hpa.postcode,
  hpa.nor,
  hpa.huisnr,
  hpa.huisletter,
  hpa.huisnrtoevoeging,
  lnd.naam
from
  kern.actie actie,
  kern.his_persadres hpa,
  kern.persadres pa,
  kern.pers p,
  kern.partij partij,
  kern.gem gem,
  kern.plaats pl,
  kern.landgebied lnd
where
  lnd.id =pa.landgebied and
  hpa.tsverval is null and
  hpa.persadres = pa.id and
  p.bsn = ${DataSource Values#|objectid.burgerservicenummer_ipv1|} and
  hpa.landgebied = lnd.id and
  hpa.wplnaam = pl.naam and
  pa.pers = p.id  and
  hpa.gem = gem.id  and
  hpa.actieinh = actie.id and
  partij.id = actie.partij
order by actie.id desc, hpa.id desc




