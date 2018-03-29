-- Voeg nieuwe testpartij toe met rol BIJHOUDINGSORGAAN_COLLEGE en code < 200000.
delete from kern.his_partijrol where partijrol in (select id from kern.partijrol where partij = (select id from kern.partij where code = '099971'));
delete from kern.partijrol where partij = (select id from kern.partij where code = '099971');
delete from kern.his_partij where partij = (select id from kern.partij where code = '099971');
delete from kern.partij where code = '099971';