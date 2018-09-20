update kern.pers
set    naderebijhaard = 7
where  id = $$persoonid$$
;

update kern.his_persbijhouding
set    naderebijhaard = 7
where  pers = $$persoonid$$
;
