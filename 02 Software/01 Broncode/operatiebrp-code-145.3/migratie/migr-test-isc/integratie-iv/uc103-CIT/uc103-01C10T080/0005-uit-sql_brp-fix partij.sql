delete from kern.his_partijrol where partijrol in (select id from kern.partijrol where partij in (select id from kern.partij where code in ('999971','250602')));
delete from kern.partijrol where partij in (select id from kern.partij where code in ('999971','250602'));
delete from kern.his_partij where partij in (select id from kern.partij where code in ('999971','250602'));
delete from kern.his_partijvrijber where partij in (select id from kern.partij where code in ('999971','250602'));
delete from kern.partij where code in ('999971','250602');
insert into kern.partij (naam, code, datingang) values ('ATR indicatie3','999971','19700101');
insert into kern.his_partij (partij, tsreg, naam, datingang, indverstrbeperkingmogelijk) select id, now(), 'ATR indicatie3','19700101', false from kern.partij where code = '999971';
insert into kern.partij (naam, code, datingang) values ('HHS Schieland en de Krimpenerwaard (2)','250602','19700101');
insert into kern.his_partij (partij, tsreg, naam, datingang, indverstrbeperkingmogelijk) select id, now(), 'HHS Schieland en de Krimpenerwaard (2)','19700101', false from kern.partij where code = '250602';
