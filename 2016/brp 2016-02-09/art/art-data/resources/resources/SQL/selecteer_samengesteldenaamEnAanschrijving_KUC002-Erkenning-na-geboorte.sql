select
indafgeleid,indnreeks,predicaat,voornamen,adellijketitel,voorvoegsel,scheidingsteken,geslnaamstam,indnaamgebruikafgeleid,predicaatnaamgebruik,voornamennaamgebruik,adellijketitelnaamgebruik,voorvoegselnaamgebruik,scheidingstekennaamgebruik,geslnaamstamnaamgebruik
from
kern.pers
where
bsn = ${DataSource Values#|objectid.burgerservicenummer_B07|}
