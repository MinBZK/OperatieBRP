CREATE EXTENSION IF NOT EXISTS postgres_fdw;

GRANT ALL ON FOREIGN DATA WRAPPER postgres_fdw TO migratie;

DROP SCHEMA IF EXISTS AutAut CASCADE;
CREATE SCHEMA AutAut;
DROP SCHEMA IF EXISTS Kern CASCADE;
CREATE SCHEMA Kern;

--CREATE SERVER brp_server FOREIGN DATA WRAPPER postgres_fdw OPTIONS (host 'brp-database', port '5432', dbname 'brp');
--CREATE USER MAPPING FOR migratie SERVER brp_server OPTIONS (user 'brp', password 'brp');
--IMPORT FOREIGN SCHEMA autaut LIMIT TO (dienst,dienstbundel,srtdienst,levsautorisatie,toeganglevsautorisatie) FROM SERVER brp_server INTO autaut;
--IMPORT FOREIGN SCHEMA kern LIMIT TO (naderebijhaard,srtpers,pers,partij,partijrol) FROM SERVER brp_server INTO kern;
