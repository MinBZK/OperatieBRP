insert into autaut.persafnemerindicatie (pers, partij, levsautorisatie, dataanvmaterieleperiode, dateindevolgen, indag) values ($$persoonid$$, $$partijid$$, $$autorisatieid$$, '20000101', '20160101', true);
insert into autaut.his_persafnemerindicatie (persafnemerindicatie, tsreg, dienstinh, dataanvmaterieleperiode, dateindevolgen)
	select persafnemerindicatie.id, now(), $$dienstid$$, persafnemerindicatie.dataanvmaterieleperiode, persafnemerindicatie.dateindevolgen from autaut.persafnemerindicatie, autaut.dienst where
	persafnemerindicatie.partij = '6594' and dienst.srt = '3';