update kern.partij set datovergangnaarbrp = '20000101' where code = '199902';
update kern.partij set dateindevrijber = NULL where code = '059901';
update kern.his_partijvrijber set dateindevrijber = NULL where partij in (select id from kern.partij where code = '059901');
delete from kern.his_partijrol where partijrol in (select id from kern.partijrol where partij in (select id from kern.partij where code = '999971'));
delete from kern.partijrol where partij = (select id from kern.partij where code = '999971');
delete from kern.his_partij where partij = (select id from kern.partij where code = '999971');
delete from kern.his_partijvrijber where partij = (select id from kern.partij where code = '999971');
delete from kern.partij where code = '999971';
delete from kern.his_partijrol where partijrol in (select id from kern.partijrol where partij in (select id from kern.partij where code = '999901'));
delete from kern.partijrol where partij = (select id from kern.partij where code = '999901');
delete from kern.his_partij where partij = (select id from kern.partij where code = '999901');
delete from kern.his_partijvrijber where partij = (select id from kern.partij where code = '999901');
delete from kern.partij where code = '999901';

insert into kern.partij (naam, code, oin, datingang, datovergangnaarbrp, datingangvrijber, indverstrbeperkingmogelijk, indag, indagvrijber,afleverpuntvrijber)
    values ('ATR interne service BRP','999971', 'abcdefgh123', '19700101', '20000101', '20000101', false, true, true,'http://tools-afnemervoorbeeld:8080/afnemervoorbeeld/BrpBerichtVerwerkingService/VrijBericht');
insert into kern.his_partij (partij, tsreg, naam, datingang, datovergangnaarbrp, indverstrbeperkingmogelijk)
    select id, now(), naam, datingang, datovergangnaarbrp, indverstrbeperkingmogelijk from kern.partij where code = '999971';
insert into kern.partijrol(partij, rol, datingang, indag) select id, 1, datingang, true from kern.partij where code = '999971';
insert into kern.partijrol(partij, rol, datingang, indag) select id, 3, datingang, true from kern.partij where code = '999971';
insert into kern.his_partijrol(partijrol, tsreg, datingang) select id, now(), datingang from kern.partijrol where partij = (select id from kern.partij where code = '999971') and rol = 1;
insert into kern.his_partijrol(partijrol, tsreg, datingang) select id, now(), datingang from kern.partijrol where partij = (select id from kern.partij where code = '999971') and rol = 3;
insert into kern.his_partijvrijber (partij, tsreg, datingangvrijber, afleverpuntvrijber)
    select id, now(), datingangvrijber, afleverpuntvrijber from kern.partij where code = '999971';

