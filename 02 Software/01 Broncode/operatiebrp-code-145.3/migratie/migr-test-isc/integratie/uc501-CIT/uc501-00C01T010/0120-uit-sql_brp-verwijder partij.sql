delete from kern.his_partijrol where partijrol in (select id from kern.partijrol where partij in (select id from kern.partij where code = '999971'));
delete from kern.partijrol where partij = (select id from kern.partij where code = '999971');
delete from kern.his_partij where partij = (select id from kern.partij where code = '999971');
delete from kern.his_partijvrijber where partij = (select id from kern.partij where code = '999971');
delete from kern.partij where code = '999971';
