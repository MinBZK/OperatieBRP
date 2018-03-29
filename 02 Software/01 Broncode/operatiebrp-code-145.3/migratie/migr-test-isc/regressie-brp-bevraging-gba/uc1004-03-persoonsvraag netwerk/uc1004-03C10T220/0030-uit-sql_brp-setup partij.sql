delete from kern.his_partijrol where partijrol = (select id from kern.partijrol where partij = (select id from kern.partij where code = '999901'));
delete from kern.partijrol where partij = (select id from kern.partij where code = '999901');
delete from kern.his_partij where partij = (select id from kern.partij where code = '999901');
delete from kern.partij where code = '999901';

insert into kern.partij (naam, code, datingang, indverstrbeperkingmogelijk, indag, oin) values ('Gemeente Rotterdam Adhoc','999901','20000101', false, true, 'a1b2c3d4e5');
insert into kern.his_partij (partij, tsreg, naam, datingang, indverstrbeperkingmogelijk)
	select id, now(), naam, datingang, indverstrbeperkingmogelijk from kern.partij where code = '999901';
