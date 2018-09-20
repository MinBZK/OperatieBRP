update kern.pers
set    geslachtsaand = 1
where  id = $$persoonid$$
;

update kern.his_persgeslachtsaand
set    geslachtsaand = 1
where  pers = $$persoonid$$
;
