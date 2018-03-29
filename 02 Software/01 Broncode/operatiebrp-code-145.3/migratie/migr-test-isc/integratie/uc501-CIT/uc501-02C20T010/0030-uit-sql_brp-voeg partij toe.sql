delete from kern.his_partijrol where partijrol = (select id from kern.partijrol where partij = (select id from kern.partij where code = '999901'));
delete from kern.partijrol where partij = (select id from kern.partij where code = '999901');
delete from kern.his_partij where partij = (select id from kern.partij where code = '999901');
delete from kern.his_partijvrijber WHERE partij = (select id from kern.partij where code = '999901');
delete from kern.partij where code = '999901';

insert into kern.partij (naam, code, afleverpuntvrijber, datingang, indverstrbeperkingmogelijk, indag, oin) values ('integratie-uc501-02C20T010', '999901', 'http://tools-afnemervoorbeeld:8080/afnemervoorbeeld/BrpBerichtVerwerkingService/VrijBericht', '20000101', false, true, 'a1b2c3d4e5');
insert into kern.his_partij (partij, tsreg, naam, datingang, indverstrbeperkingmogelijk) select id, now(), naam, datingang, indverstrbeperkingmogelijk from kern.partij where code = '999901';
insert into kern.his_partijvrijber (partij, tsreg, datingangvrijber) select id, now(), datingang from kern.partij where code = '999901';
