select persafnemerindicatie.id 
from   autaut.persafnemerindicatie 
join   autaut.his_persafnemerindicatie 
on     his_persafnemerindicatie.persafnemerindicatie = persafnemerindicatie.id
where  pers = $$persoonid$$ 
and    afnemer = $$partijid$$ 
and    levsautorisatie = $$autorisatieid$$
and    his_persafnemerindicatie.dienstverval is null
