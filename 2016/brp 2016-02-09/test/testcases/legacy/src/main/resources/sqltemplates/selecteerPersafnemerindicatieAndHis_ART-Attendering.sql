select 
	ai.abonnement ai_abonnement,
	ai.afnemer ai_afnemer,
	ai.pers ai_pers,
	ai.dataanvmaterieleperiode ai_dataanvmaterieleperiode,
	ai.dateindevolgen ai_dateindevolgen,
	hai.tsreg hai_tsreg,
	hai.tsverval hai_tsverval,
	--hai.actieinh hai_actieinh, --kolommen bestaan niet meer in de tabel
	--hai.actieverval hai_actieverval,
	--hai.nadereaandverval hai_nadereaandverval,
	hai.dataanvmaterieleperiode hai_dataanvmaterieleperiode,
	hai.dateindevolgen hai_dateindevolgen
from 
	autaut.persafnemerindicatie ai,
	autaut.his_persafnemerindicatie hai 
where 
	hai.persafnemerindicatie = ai.id
	and ai.pers in (select id from kern.pers where bsn = ${_objectid$persoon1_}) and ai.abonnement=5670032
	ORDER BY ai_abonnement ASC;