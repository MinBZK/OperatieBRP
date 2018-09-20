select
  hpa.id,
  p.voornamen,
  p.geslnaamstam,
  trim(to_char(hpa.dataanvgel,'0000-00-00'))dataanvgel,
  trim(to_char(hpa.dateindegel,'0000-00-00'))dateindegel,
  hpa.tsreg,
  hpa.tsverval,
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
  kern.his_persadres hpa,
  kern.persadres pa,
  kern.pers p,
  kern.gem gem,
  kern.plaats pl,
  kern.landgebied lnd
where
  hpa.tsverval is not null and
  hpa.persadres = pa.id and
  pa.pers = p.id and
  hpa.gem = gem.id  and
  hpa.wplnaam = pl.naam and
  lnd.id = hpa.landgebied and
  p.bsn = ${DataSource Values#|objectid.burgerservicenummer_ipv1|} and
  hpa.dateindegel is null
order by hpa.tsverval asc, hpa.dataanvgel desc
