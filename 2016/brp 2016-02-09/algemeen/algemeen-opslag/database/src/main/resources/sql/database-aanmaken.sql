-- Maak de gebruikers aan
CREATE USER brp WITH PASSWORD 'brp';
CREATE USER brplezen WITH PASSWORD 'brp';
CREATE USER brpprotocol WITH PASSWORD 'brp';
CREATE USER brparchiveer WITH PASSWORD 'brp';

-- Maak de database aan
CREATE DATABASE brp OWNER brp WITH ENCODING 'UTF8'
ALTER DATABASE brp set timezone to 'UTC';
GRANT ALL PRIVILEGES ON DATABASE brp to brp;

CREATE DATABASE "brp-lev" WITH OWNER brpprotocol ENCODING 'UTF8';
ALTER DATABASE "brp-lev" set timezone to 'UTC';

CREATE DATABASE "brp-ber" WITH OWNER brparchiveer ENCODING 'UTF8';
ALTER DATABASE "brp-ber" set timezone to 'UTC';

-- Zorg dat de alleen lezen gebruiker toegang heeft tot alle tabellen in alle schema's

ALTER DEFAULT PRIVILEGES IN SCHEMA kern
   GRANT SELECT ON TABLES TO brplezen;

ALTER DEFAULT PRIVILEGES IN SCHEMA autaut
   GRANT SELECT ON TABLES TO brplezen;

-- Bericht archivering schema
ALTER DEFAULT PRIVILEGES IN SCHEMA ber
   GRANT SELECT ON TABLES TO brplezen;
ALTER DEFAULT PRIVILEGES IN SCHEMA ber
   GRANT SELECT,INSERT ON TABLES TO brparchiveer;

-- Bedrijfregel manager schema
ALTER DEFAULT PRIVILEGES IN SCHEMA brm
   GRANT SELECT ON TABLES TO brplezen;

-- Protocollering van leveringen schema
ALTER DEFAULT PRIVILEGES IN SCHEMA lev
   GRANT SELECT ON TABLES TO brplezen;
ALTER DEFAULT PRIVILEGES IN SCHEMA lev
   GRANT SELECT,INSERT ON TABLES TO brpprotocol;


# Aanmaken databases voor test machines
CREATE DATABASE "brp-lev-oap10" WITH OWNER brpprotocol ENCODING 'UTF8';
ALTER DATABASE "brp-lev-oap10" set timezone to 'UTC';

CREATE DATABASE "brp-ber-oap10" WITH OWNER brparchiveer ENCODING 'UTF8';
ALTER DATABASE "brp-ber-oap10" set timezone to 'UTC';

CREATE DATABASE "brp-lev-oap11" WITH OWNER brpprotocol ENCODING 'UTF8';
ALTER DATABASE "brp-lev-oap11" set timezone to 'UTC';

CREATE DATABASE "brp-ber-oap12" WITH OWNER brparchiveer ENCODING 'UTF8';
ALTER DATABASE "brp-ber-oap12" set timezone to 'UTC';

CREATE DATABASE "brp-lev-oap13" WITH OWNER brpprotocol ENCODING 'UTF8';
ALTER DATABASE "brp-lev-oap13" set timezone to 'UTC';

CREATE DATABASE "brp-ber-oap13" WITH OWNER brparchiveer ENCODING 'UTF8';
ALTER DATABASE "brp-ber-oap13" set timezone to 'UTC';

CREATE DATABASE "brp-lev-oap14" WITH OWNER brpprotocol ENCODING 'UTF8';
ALTER DATABASE "brp-lev-oap14" set timezone to 'UTC';

CREATE DATABASE "brp-ber-oap15" WITH OWNER brparchiveer ENCODING 'UTF8';
ALTER DATABASE "brp-ber-oap15" set timezone to 'UTC';


-- activeer unaccent extensie text search dictionary that removes accents (diacritic signs) from lexemes
create extension IF NOT EXISTS unaccent;
