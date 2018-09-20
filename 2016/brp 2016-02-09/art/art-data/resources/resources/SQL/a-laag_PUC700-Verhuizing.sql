select
  p.bijhpartij,
  p.id,
  p.voornamen,
  p.geslnaamstam,
  pa.srt,
  pa.rdnwijz,
  pa.aangadresh,
  pa.dataanvadresh,
  pa.identcodeadresseerbaarobject,
  pa.identcodenraand,
  gem.naam gemeente,
  pl.naam woonplaats,
  pa.gemdeel,
  pa.postcode,
  pa.nor,
  pa.afgekortenor,
  pa.huisnr,
  pa.huisletter,
  pa.huisnrtoevoeging,
  lnd.naam
from
  kern.persadres pa,
  kern.pers p,
  kern.gem gem,
  kern.plaats pl,
  kern.landgebied lnd
where
  lnd.id =pa.landgebied and
  pa.pers = p.id and
  pa.gem = gem.id and
  pa.wplnaam = pl.naam and
  p.bsn = ${DataSource Values#|objectid.burgerservicenummer_ipv1|}
