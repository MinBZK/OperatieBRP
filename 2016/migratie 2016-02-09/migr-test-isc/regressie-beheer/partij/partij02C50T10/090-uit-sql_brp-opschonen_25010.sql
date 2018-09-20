delete from kern.his_partij where partij in (select id from kern.partij where code = '25010');
delete from kern.partij where code = '25010'
