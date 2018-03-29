delete from kern.his_partijrol where partijrol in (select id from kern.partijrol where partij = (select id from kern.partij where code = '999952'));
delete from kern.partijrol where partij = (select id from kern.partij where code = '999952');
delete from kern.his_partij where partij = (select id from kern.partij where code = '999952');
delete from kern.partij where code = '999952';

insert into kern.partij (naam, code, datingang, dateinde, indverstrbeperkingmogelijk, datovergangnaarbrp, indag) values ('Partijregister - uc202-07C10T030','999952', '20100101', cast(to_char(current_date + interval '1' day, 'yyyymmdd') as int), false, null, true);
insert into kern.his_partij (partij, tsreg, naam, datingang, dateinde, indverstrbeperkingmogelijk, datovergangnaarbrp)
	select id, now(), naam, datingang, dateinde, indverstrbeperkingmogelijk, datovergangnaarbrp from kern.partij where code = '999952';
insert into kern.partijrol(partij, rol, datingang, dateinde, indag) select id, 2, datingang, null, true from kern.partij where code = '999952';
insert into kern.partijrol(partij, rol, datingang, dateinde, indag) select id, 3, datingang, null, true from kern.partij where code = '999952';
insert into kern.his_partijrol(partijrol, tsreg, datingang, dateinde) select id, now(), datingang, dateinde from kern.partijrol where partij = (select id from kern.partij where code = '999952') and rol = 2;
insert into kern.his_partijrol(partijrol, tsreg, datingang, dateinde) select id, now(), datingang, dateinde from kern.partijrol where partij = (select id from kern.partij where code = '999952') and rol = 3;