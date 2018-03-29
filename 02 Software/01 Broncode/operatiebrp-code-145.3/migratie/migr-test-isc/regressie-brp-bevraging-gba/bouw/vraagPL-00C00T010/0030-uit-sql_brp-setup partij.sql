-- Voeg nieuwe testpartij toe met rol BIJHOUDINGSORGAAN_COLLEGE en code < 200000.
delete from kern.his_partijrol where partijrol in (select id from kern.partijrol where partij = (select id from kern.partij where code = '099971'));
delete from kern.partijrol where partij = (select id from kern.partij where code = '099971');
delete from kern.his_partij where partij = (select id from kern.partij where code = '099971');
delete from kern.partij where code = '099971';

insert into kern.partij (naam, code, datingang, indverstrbeperkingmogelijk, indag, oin) values ('uc1003-BRP-Route-PLTS-01c10t010','099971','19700101', false, true, 'a1b2c3d4e5');
insert into kern.his_partij (partij, tsreg, naam, datingang, indverstrbeperkingmogelijk)
	select id, now(), naam, datingang, indverstrbeperkingmogelijk from kern.partij where code = '099971';
insert into kern.partijrol(partij, rol, datingang, indag) select id, 1, datingang, true from kern.partij where code = '099971';
insert into kern.partijrol(partij, rol, datingang, indag) select id, 2, datingang, true from kern.partij where code = '099971';
insert into kern.his_partijrol(partijrol, tsreg, datingang) select id, now(), datingang from kern.partijrol where partij = (select id from kern.partij where code = '099971') and rol = 1;
insert into kern.his_partijrol(partijrol, tsreg, datingang) select id, now(), datingang from kern.partijrol where partij = (select id from kern.partij where code = '099971') and rol = 2;
