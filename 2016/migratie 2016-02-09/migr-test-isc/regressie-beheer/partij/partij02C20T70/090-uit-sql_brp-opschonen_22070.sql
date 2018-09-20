delete from kern.his_partij where partij in (select id from kern.partij where code = '22070');
delete from kern.partij where code = '22070'
