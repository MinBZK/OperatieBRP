BEGIN;

TRUNCATE autaut.his_persafnemerindicatie CASCADE;
TRUNCATE autaut.persafnemerindicatie CASCADE;
TRUNCATE autaut.his_toeganglevsautorisatie CASCADE;
TRUNCATE autaut.toeganglevsautorisatie CASCADE;
TRUNCATE autaut.his_dienst CASCADE;
TRUNCATE autaut.dienst CASCADE;
TRUNCATE autaut.his_levsautorisatie CASCADE;
TRUNCATE autaut.levsautorisatie CASCADE;
TRUNCATE autaut.dienstbundelgroep CASCADE;
TRUNCATE autaut.dienstbundelgroepattr CASCADE;

TRUNCATE kern.partijrol CASCADE;
COMMIT;
