delete from kern.his_partijrol where partijrol in (select id from kern.partijrol where partij in (select id from kern.partij where code = '999971'));
delete from kern.partijrol where partij = (select id from kern.partij where code = '999971');
delete from kern.his_partij where partij = (select id from kern.partij where code = '999971');
delete from kern.partij where code = '999971';

insert into kern.partij (naam, code, datingang, indverstrbeperkingmogelijk, indag) values ('ATR interne service BRP','999971','19700101', false, true);
insert into kern.his_partij (partij, tsreg, naam, datingang, indverstrbeperkingmogelijk)
	select id, now(), naam, datingang, indverstrbeperkingmogelijk from kern.partij where code = '999971';
insert into kern.partijrol(partij, rol, datingang, indag) select id, 1, datingang, true from kern.partij where code = '999971';
insert into kern.partijrol(partij, rol, datingang, indag) select id, 3, datingang, true from kern.partij where code = '999971';
insert into kern.his_partijrol(partijrol, tsreg, datingang) select id, now(), datingang from kern.partijrol where partij = (select id from kern.partij where code = '999971') and rol = 1;
insert into kern.his_partijrol(partijrol, tsreg, datingang) select id, now(), datingang from kern.partijrol where partij = (select id from kern.partij where code = '999971') and rol = 3;
