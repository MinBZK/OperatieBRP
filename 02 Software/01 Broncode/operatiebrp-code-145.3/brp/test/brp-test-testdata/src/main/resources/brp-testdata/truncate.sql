BEGIN;

TRUNCATE Kern.his_persnaamgebruik CASCADE;
TRUNCATE Kern.his_persbijhouding CASCADE;
TRUNCATE Kern.his_persdeelneuverkiezingen CASCADE;
TRUNCATE Kern.His_PersGeboorte CASCADE;
TRUNCATE Kern.His_PersGeslachtsaand CASCADE;
TRUNCATE Kern.His_PersIds CASCADE;
TRUNCATE Kern.his_persmigratie CASCADE;
TRUNCATE Kern.his_persinschr CASCADE;
TRUNCATE Kern.His_PersOverlijden CASCADE;
TRUNCATE Kern.his_perspk CASCADE;
TRUNCATE Kern.His_PersSamengesteldeNaam CASCADE;
TRUNCATE Kern.his_persuitslkiesr CASCADE;
TRUNCATE Kern.his_persverblijfsr CASCADE;
TRUNCATE Kern.his_persverstrbeperking CASCADE;

COMMIT;

BEGIN;

TRUNCATE Kern.his_ouderouderlijkgezag  CASCADE;
TRUNCATE Kern.his_ouderouderschap  CASCADE;
TRUNCATE Kern.His_Betr CASCADE;
TRUNCATE Kern.Betr CASCADE;
TRUNCATE Kern.his_relatie CASCADE;
TRUNCATE Kern.Relatie CASCADE;

TRUNCATE Kern.his_onderzoek CASCADE;
TRUNCATE Kern.onderzoek CASCADE;

TRUNCATE Kern.his_persreisdoc CASCADE;
TRUNCATE Kern.persreisdoc CASCADE;

TRUNCATE Kern.his_persverificatie CASCADE;
TRUNCATE Kern.persverificatie CASCADE;

TRUNCATE Kern.PersIndicatie CASCADE;
TRUNCATE Kern.His_PersIndicatie CASCADE;

TRUNCATE Kern.His_PersGeslnaamComp CASCADE;
TRUNCATE Kern.PersGeslnaamComp CASCADE;
TRUNCATE Kern.His_PersVoornaam CASCADE;
TRUNCATE Kern.PersVoornaam CASCADE;

TRUNCATE Kern.His_PersNation CASCADE;
TRUNCATE Kern.PersNation CASCADE;

TRUNCATE Kern.His_PersAdres CASCADE;
TRUNCATE Kern.PersAdres CASCADE;

TRUNCATE Kern.Pers CASCADE;

TRUNCATE Kern.ActieBron CASCADE;
TRUNCATE Kern.Doc CASCADE;
TRUNCATE Kern.Actie CASCADE;
TRUNCATE Kern.AdmHnd CASCADE;

TRUNCATE Kern.Rechtsgrond CASCADE;

COMMIT;

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
