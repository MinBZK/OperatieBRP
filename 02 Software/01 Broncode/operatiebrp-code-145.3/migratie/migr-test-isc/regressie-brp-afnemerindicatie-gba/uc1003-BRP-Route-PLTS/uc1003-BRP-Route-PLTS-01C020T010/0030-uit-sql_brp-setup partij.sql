delete from kern.his_partijrol where partijrol = (select id from kern.partijrol where partij = (select id from kern.partij where code = '999971'));
delete from kern.partijrol where partij = (select id from kern.partij where code = '999971');
delete from kern.his_partij where partij = (select id from kern.partij where code = '999971');
delete from kern.partij where code = '999971';

insert into kern.partij (naam, code, datingang, indverstrbeperkingmogelijk, indag, oin) values ('uc1003-BRP-Route-PLTS','999971','20300101', false, true, 'a1b2c3d4e5');
insert into kern.his_partij (partij, tsreg, naam, datingang, indverstrbeperkingmogelijk)
	select id, now(), naam, datingang, indverstrbeperkingmogelijk from kern.partij where code = '999971';