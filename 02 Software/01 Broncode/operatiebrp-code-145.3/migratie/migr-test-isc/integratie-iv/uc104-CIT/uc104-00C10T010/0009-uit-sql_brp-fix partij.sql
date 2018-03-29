delete from kern.his_partijrol where partijrol = (select id from kern.partijrol where partij = (select id from kern.partij where code = '900050'));
delete from kern.partijrol where partij = (select id from kern.partij where code = '900050');
delete from kern.his_partij where partij = (select id from kern.partij where code = '900050');
delete from kern.his_partijvrijber where partij = (select id from kern.partij where code = '900050');
delete from kern.partij where code = '900050';
insert into kern.partij (naam, code, datingang) values ('Autorisatietabelregel met spontaan, selectie, adhoc en adresgeörienteerd','900050','19700101');
insert into kern.his_partij (partij, tsreg, naam, datingang, indverstrbeperkingmogelijk) select id, now(), 'Autorisatietabelregel met spontaan, selectie, adhoc en adresgeörienteerd','19700101', false from kern.partij where code = '900050';
