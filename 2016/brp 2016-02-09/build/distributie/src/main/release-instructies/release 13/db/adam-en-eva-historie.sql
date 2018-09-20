--Vul de historie voor adam en eva. De where clauses garanderen dat we alleen de ADAM en EVA's raken:
BEGIN;

insert into kern.his_perssamengesteldenaam (id, pers, dataanvgel, tsreg, indalgoritmischafgeleid, indnreeks, voornamen, voorvoegsel, scheidingsteken, geslnaam) select id, id, 19511111, now(), true, false, voornamen, voorvoegsel, scheidingsteken, geslnaam from kern.pers where srt=2 and geslnaam = 'Modernodam' and voorvoegsel = 'van' and bsn is null;
insert into kern.his_persgeboorte (id, pers, tsreg, datgeboorte, landgeboorte) select id, id, now(), 19511111, 229 from kern.pers where srt=2 and geslnaam = 'Modernodam' and voorvoegsel = 'van' and bsn is null;
insert into kern.his_persgeslachtsaand (id, pers, dataanvgel, tsreg, geslachtsaand) select id, id, 19511111 ,now(), geslachtsaand from kern.pers where srt=2 and geslnaam = 'Modernodam' and voorvoegsel = 'van' and bsn is null;
insert into kern.his_persafgeleidadministrati (id, pers, tsreg, admhnd, tslaatstewijz, sorteervolgorde) select id, id, now(), 1, tslaatstewijz, 1 from kern.pers where srt=2 and geslnaam = 'Modernodam' and voorvoegsel = 'van' and bsn is null;;

COMMIT;
