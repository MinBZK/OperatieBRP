delete from kern.his_partijrol where partijrol = (select id from kern.partijrol where partij = (select id from kern.partij where code = '999971'));
delete from kern.partijrol where partij = (select id from kern.partij where code = '999971');
delete from kern.his_partij where partij = (select id from kern.partij where code = '999971');
delete from kern.partij where code = '999971';

insert into kern.partij (naam, code, datingang, indverstrbeperkingmogelijk, indag, oin, datovergangnaarbrp) values ('uc1003-BRP-Route-PLTS','999971','19700101', false, true, 'a1b2c3d4e5', '20160101');
insert into kern.his_partij (partij, tsreg, naam, datingang, indverstrbeperkingmogelijk, datovergangnaarbrp)
	select id, now(), naam, datingang, indverstrbeperkingmogelijk, datovergangnaarbrp from kern.partij where code = '999971';