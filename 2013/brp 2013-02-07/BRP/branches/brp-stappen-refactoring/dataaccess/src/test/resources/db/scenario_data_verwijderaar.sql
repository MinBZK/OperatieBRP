BEGIN;

TRUNCATE Kern.His_PersNation CASCADE;
TRUNCATE Kern.PersNation CASCADE;
TRUNCATE Kern.His_PersIndicatie CASCADE;
TRUNCATE Kern.PersIndicatie CASCADE;
TRUNCATE Kern.His_PersOpschorting CASCADE;
TRUNCATE Kern.His_PersAanschr CASCADE;
TRUNCATE Kern.His_PersGeslnaamComp CASCADE;
TRUNCATE Kern.PersGeslnaamComp CASCADE;
TRUNCATE Kern.His_PersVoornaam CASCADE;
TRUNCATE Kern.PersVoornaam CASCADE;
TRUNCATE Kern.His_PersBijhaard CASCADE;


COMMIT;

BEGIN;

TRUNCATE Kern.His_OuderOuderschap CASCADE;
TRUNCATE Kern.Betr CASCADE;
TRUNCATE Kern.Relatie CASCADE;
TRUNCATE Kern.His_PersBijhGem CASCADE;
TRUNCATE Kern.His_PersAdres CASCADE;
TRUNCATE Kern.PersAdres CASCADE;
TRUNCATE Kern.His_PersOverlijden CASCADE;
TRUNCATE Kern.His_PersGeboorte CASCADE;
TRUNCATE Kern.His_PersSamengesteldeNaam CASCADE;
TRUNCATE Kern.His_PersGeslachtsaand CASCADE;
TRUNCATE Kern.His_PersIds CASCADE;
TRUNCATE Kern.Actie CASCADE;
TRUNCATE Kern.Pers CASCADE;

COMMIT;
