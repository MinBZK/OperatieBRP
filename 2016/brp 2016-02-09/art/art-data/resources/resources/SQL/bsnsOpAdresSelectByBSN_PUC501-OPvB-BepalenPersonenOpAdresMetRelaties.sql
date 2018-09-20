select p.bsn, p.voornamen, sb.naam rol, pa.*
from kern.persadres pa, kern.pers p, kern.betr b, kern.relatie rel, kern.srtBetr sb
where pa.pers = p.id AND b.relatie = rel.id AND pa.pers = b.pers AND sb.id = b.rol AND pa.pers in
(select pers from kern.betr where relatie in(select relatie from kern.betr where pers in(select id from kern.pers where bsn =${DataSource Values#burgerservicenummer_zvA0}))) order by b.id

