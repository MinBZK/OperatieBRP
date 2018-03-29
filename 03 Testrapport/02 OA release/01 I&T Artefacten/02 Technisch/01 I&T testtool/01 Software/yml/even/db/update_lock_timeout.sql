
-- verander default lock.timeout
UPDATE mig_configuratie
SET waarde='10 minutes'
WHERE configuratie='lock.timeout';
