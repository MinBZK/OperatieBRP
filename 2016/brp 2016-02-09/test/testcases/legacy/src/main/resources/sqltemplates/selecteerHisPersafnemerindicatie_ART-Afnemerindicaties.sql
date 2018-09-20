select 
	tsreg,
	tsverval,
	dienstinh,
	dienstverval,
	dataanvmaterieleperiode,
	dateindevolgen
from 
	autaut.his_persafnemerindicatie 
where 
	persafnemerindicatie in 
	(select id from autaut.persafnemerindicatie where pers in 
		(select id from kern.pers where bsn = ${burgerservicenummer_ipr1}))
order by dienstinh
limit 1;