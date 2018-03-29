delete from kern.his_partijrol where partijrol in (select id from kern.partijrol where partij = (select id from kern.partij where code = '999972'));
delete from kern.partijrol where partij = (select id from kern.partij where code = '999972');
delete from kern.his_partij where partij = (select id from kern.partij where code = '999972');
delete from kern.partij where code = '999972';

insert into kern.partij (naam, code, datingang, dateinde, indverstrbeperkingmogelijk, datovergangnaarbrp, indag) values ('Partijregister - uc202-07C30T030','999972', cast(to_char(current_date, 'yyyymmdd') as int), null, false, null, true);
insert into kern.his_partij (partij, tsreg, naam, datingang, dateinde, indverstrbeperkingmogelijk, datovergangnaarbrp)
	select id, now(), naam, datingang, dateinde, indverstrbeperkingmogelijk, datovergangnaarbrp from kern.partij where code = '999972';
insert into kern.partijrol(partij, rol, datingang, dateinde, indag) select id, 2, '20100101', cast(to_char(current_date + interval '1' day, 'yyyymmdd') as int), true from kern.partij where code = '999972';
insert into kern.partijrol(partij, rol, datingang, dateinde, indag) select id, 3, '20100101', cast(to_char(current_date + interval '1' day, 'yyyymmdd') as int), true from kern.partij where code = '999972';
insert into kern.his_partijrol(partijrol, tsreg, datingang, dateinde) select id, now(), datingang, dateinde from kern.partijrol where partij = (select id from kern.partij where code = '999972') and rol = 2;
insert into kern.his_partijrol(partijrol, tsreg, datingang, dateinde) select id, now(), datingang, dateinde from kern.partijrol where partij = (select id from kern.partij where code = '999972') and rol = 3;