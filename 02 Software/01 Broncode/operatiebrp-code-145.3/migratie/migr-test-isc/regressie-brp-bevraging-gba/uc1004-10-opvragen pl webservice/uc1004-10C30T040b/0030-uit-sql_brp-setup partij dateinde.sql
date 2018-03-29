-- Voeg nieuwe testpartij toe met rol BIJHOUDINGSORGAAN_COLLEGE en code < 200000.
delete from kern.his_partijrol where partijrol in (select id from kern.partijrol where partij = (select id from kern.partij where code = '099971'));
delete from kern.partijrol where partij = (select id from kern.partij where code = '099971');
delete from kern.his_partij where partij = (select id from kern.partij where code = '099971');
delete from kern.partij where code = '099971';

insert into kern.partij (naam, code, datingang, dateinde, indverstrbeperkingmogelijk, indag, oin) values ('UC1004-Opslaan PL','099971','19700101','20160101', false, true, 'a1b2c3d4e5');
insert into kern.his_partij (partij, tsreg, naam, datingang, dateinde, indverstrbeperkingmogelijk)
	select id, now(), naam, datingang, dateinde, indverstrbeperkingmogelijk from kern.partij where code = '099971';
insert into kern.partijrol(partij, rol, datingang, dateinde, indag) select id, 1, datingang, dateinde, true from kern.partij where code = '099971';
insert into kern.partijrol(partij, rol, datingang, dateinde, indag) select id, 3, datingang, dateinde, true from kern.partij where code = '099971';
insert into kern.his_partijrol(partijrol, tsreg, datingang, dateinde) select id, now(), datingang, dateinde from kern.partijrol where partij = (select id from kern.partij where code = '099971') and rol = 1;
insert into kern.his_partijrol(partijrol, tsreg, datingang, dateinde) select id, now(), datingang, dateinde from kern.partijrol where partij = (select id from kern.partij where code = '099971') and rol = 3;
