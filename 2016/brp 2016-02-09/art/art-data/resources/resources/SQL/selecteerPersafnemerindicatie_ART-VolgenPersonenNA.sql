select 	
	afnemer,
	abonnement,
	dataanvmaterieleperiode,
	dateindevolgen
from autaut.persafnemerindicatie 
where pers in (select id 
				from kern.pers 
				where bsn = ${DataSource Values#burgerservicenummer_ipr1});