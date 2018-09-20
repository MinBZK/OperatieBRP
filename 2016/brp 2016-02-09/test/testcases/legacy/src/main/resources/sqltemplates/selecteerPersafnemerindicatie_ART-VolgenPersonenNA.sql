select 	
	afnemer,
	abonnement,
	dataanvmaterieleperiode,
	dateindevolgen
from autaut.persafnemerindicatie 
where pers in (select id 
				from kern.pers 
				where bsn = ${burgerservicenummer_ipr1}) and abonnement<5671000
order by afnemer;