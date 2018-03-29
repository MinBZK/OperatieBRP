delete from kern.his_partijrol where partijrol in ((select id from kern.partijrol where partij in (select id from kern.partij where code = '990082')));
delete from kern.partijrol where partij = (select id from kern.partij where code = '990082');
delete from kern.his_partij where partij = (select id from kern.partij where code = '990082');
delete from kern.partij where code = '990082';

insert into kern.partij (naam, code, datingang, indverstrbeperkingmogelijk, indag) values ('ATR 990082','990082','19700101', false, true);
insert into kern.partijrol (partij, rol, datingang, indag) values ((select id from kern.partij where code = '990082'), 2, 20000101, true);
insert into kern.his_partij (partij, tsreg, naam, datingang, indverstrbeperkingmogelijk)
	select id, now(), naam, datingang, indverstrbeperkingmogelijk from kern.partij where code = '990082';
