update kern.partij set datovergangnaarbrp = '20000101' where code = '199902';
delete from kern.his_partijrol where partijrol in (select id from kern.partijrol where partij in (select id from kern.partij where code = '999971'));
delete from kern.partijrol where partij = (select id from kern.partij where code = '999971');
delete from kern.his_partij where partij = (select id from kern.partij where code = '999971');
delete from kern.his_partijvrijber where partij = (select id from kern.partij where code = '999971');
delete from kern.partij where code = '999971';

insert into kern.partij (naam, code, datingang, datovergangnaarbrp, datingangvrijber, indverstrbeperkingmogelijk, indag, indagvrijber,afleverpuntvrijber)
    values ('ATR interne service BRP','999971','19700101', '20000101', '20000101', false, true, true,'http://tools-afnemervoorbeeld:8080/afnemervoorbeeld/BrpBerichtVerwerkingService/VrijBericht');
insert into kern.his_partij (partij, tsreg, naam, datingang, datovergangnaarbrp, indverstrbeperkingmogelijk)
    select id, now(), naam, datingang, datovergangnaarbrp, indverstrbeperkingmogelijk from kern.partij where code = '999971';
insert into kern.partijrol(partij, rol, datingang, indag) select id, 1, datingang, true from kern.partij where code = '999971';
insert into kern.partijrol(partij, rol, datingang, indag) select id, 3, datingang, true from kern.partij where code = '999971';
insert into kern.his_partijrol(partijrol, tsreg, datingang) select id, now(), datingang from kern.partijrol where partij = (select id from kern.partij where code = '999971') and rol = 1;
insert into kern.his_partijrol(partijrol, tsreg, datingang) select id, now(), datingang from kern.partijrol where partij = (select id from kern.partij where code = '999971') and rol = 3;
insert into kern.his_partijvrijber (partij, tsreg, datingangvrijber, afleverpuntvrijber)
    select id, now(), datingangvrijber, afleverpuntvrijber from kern.partij where code = '999971';

-- Voor uc501-01C20T020a, een verzender die nog niet geldig is --
update kern.partij set datingang = '20300101' where code = '062701';
update kern.partij set datovergangnaarbrp = '20000101' where code = '062701';

-- Voor uc501-01C20T020b, een verzender met partijrol die nog niet geldig is --
update kern.partijrol set dateinde = '20500101' where partij = '670';
update kern.partijrol set datingang = '20300101' where partij = '670';
update kern.partij set datovergangnaarbrp = '20000101' where code = '066601';

-- Voor uc501-01C20T030a, een partij verzender die verlopen is --
update kern.partij set datingang = '20000101' where code = '076501';
update kern.partij set dateinde = '20020101' where code = '076501';
update kern.partij set datovergangnaarbrp = '20000101' where code = '076501';
update kern.partijrol set dateinde = '20500101' where partij = '771';

-- Voor uc501-01C20T030b, een partijrol verzender die verlopen is --
update kern.partij set datovergangnaarbrp = '20500101' where code = '076601';

-- Voor uc501-01C20T040, verzender is geen BRP gemeente  --
update kern.partij set datovergangnaarbrp = '20000101' where code = '077001';