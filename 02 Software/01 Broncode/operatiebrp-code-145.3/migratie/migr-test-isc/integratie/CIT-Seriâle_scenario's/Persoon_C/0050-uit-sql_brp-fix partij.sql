delete from kern.his_partijrol where partijrol = (select id from kern.partijrol where partij in (select id from kern.partij where code = '990001'));
delete from kern.partijrol where partij = (select id from kern.partij where code = '990001');
delete from kern.his_partij where partij = (select id from kern.partij where code = '990001');
delete from kern.partij where code = '990001';

insert into kern.partij (naam, code, datingang, indverstrbeperkingmogelijk, indag) values ('ATR 990001','990001','19700101', false, true);
insert into kern.his_partij (partij, tsreg, naam, datingang, indverstrbeperkingmogelijk)
	select id, now(), naam, datingang, indverstrbeperkingmogelijk from kern.partij where code = '990001';
