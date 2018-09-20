-- Serial van een certificaat kan groter zijn dan Postgresql's bigint, vandaar numeric
ALTER TABLE autaut.certificaat ALTER COLUMN serial TYPE numeric;

-- Signature van een certificaat kan 2048 bytes bevatten, vandaag aanpassing naar grootte van 2048
ALTER TABLE autaut.certificaat ALTER COLUMN signature TYPE character varying(2048);
