insert into autaut.persafnemerindicatie (pers, partij, levsautorisatie, dataanvmaterieleperiode, indag) values ($$persoonid$$, $$partijid$$, $$autorisatieid$$, '20300101', true);
insert into autaut.his_persafnemerindicatie (persafnemerindicatie, tsreg, dienstinh, dataanvmaterieleperiode)
	select persafnemerindicatie.id, now(), $$dienstid$$, persafnemerindicatie.dataanvmaterieleperiode from autaut.persafnemerindicatie, autaut.dienst where
	persafnemerindicatie.partij = '6594' and dienst.srt = '3';