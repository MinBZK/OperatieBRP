select distinct
   p.id persid,
   p.bijhpartij,
   ah.srt ,
   ah.partij,
   ah.toelichtingontlening,
   ah.tsreg,
   partij.code
from
	kern.pers p,
	kern.his_persafgeleidadministrati,
	kern.admhnd ah,
	kern.actie,
	kern.srtadmhnd sah,
	kern.partij,
	kern.persadres--,
where
	ah.srt = sah.id
	and p.id = persadres.pers
	and actie.admhnd = ah.id
	and actie.partij = partij.id
	and his_persafgeleidadministrati.admhnd = ah.id
	and his_persafgeleidadministrati.pers = p.id
	and (p.bsn = ${burgerservicenummer_ipk0})
order by persid desc
