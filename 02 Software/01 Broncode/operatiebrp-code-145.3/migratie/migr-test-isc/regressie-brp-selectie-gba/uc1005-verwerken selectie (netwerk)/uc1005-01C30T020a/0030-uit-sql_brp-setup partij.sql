delete from kern.his_partijrol where partijrol in (select id from kern.partijrol where partij in (select id from kern.partij where code = '999901'));
delete from kern.partijrol where partij in (select id from kern.partij where code = '999901');
delete from kern.his_partij where partij in (select id from kern.partij where code = '999901');
delete from kern.partij where code = '999901';

insert into kern.partij (naam, code, datingang, indverstrbeperkingmogelijk, indag) values ('Selectie partij','999901','20000101', false, true);
insert into kern.his_partij (partij, tsreg, naam, datingang, indverstrbeperkingmogelijk)
	select id, now(), naam, datingang, indverstrbeperkingmogelijk from kern.partij where code = '999901';
