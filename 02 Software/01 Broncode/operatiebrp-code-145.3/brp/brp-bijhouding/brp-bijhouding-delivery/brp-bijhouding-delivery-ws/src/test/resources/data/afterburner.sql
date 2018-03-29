-- Dit script mag alleen voor unittest gebruikt worden, het is een fix om te voorkomen dat de testdata.xml id in botsing komt.

BEGIN;

SELECT SETVAL('kern.seq_actie', (select 90000+ coalesce(max(id),0)  FROM "kern"."actie"), true);
SELECT SETVAL('kern.seq_actiebron', (select 90000+ coalesce(max(id),0)  FROM "kern"."actiebron"), true);
SELECT SETVAL('kern.seq_admhnd', (select 90000+ coalesce(max(id),0)  FROM "kern"."admhnd"), true);
SELECT SETVAL('kern.seq_betr', (select 90000+ coalesce(max(id),0)  FROM "kern"."betr"), true);
SELECT SETVAL('kern.seq_his_betr', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_betr"), true);
SELECT SETVAL('kern.seq_doc', (select 90000+ coalesce(max(id),0)  FROM "kern"."doc"), true);
SELECT SETVAL('kern.seq_gegeveninonderzoek', (select 90000+ coalesce(max(id),0)  FROM "kern"."gegeveninonderzoek"), true);
SELECT SETVAL('kern.seq_his_onderzoek', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_onderzoek"), true);
SELECT SETVAL('kern.seq_his_ouderouderlijkgezag', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_ouderouderlijkgezag"), true);
SELECT SETVAL('kern.seq_his_ouderouderschap', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_ouderouderschap"), true);
SELECT SETVAL('kern.seq_his_partij', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_partij"), true);
SELECT SETVAL('kern.seq_his_persnaamgebruik', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_persnaamgebruik"), true);
SELECT SETVAL('kern.seq_his_persadres', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_persadres"), true);
SELECT SETVAL('kern.seq_his_persdeelneuverkiezingen', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_persdeelneuverkiezingen"), true);
SELECT SETVAL('kern.seq_his_persgeboorte', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_persgeboorte"), true);
SELECT SETVAL('kern.seq_his_persgeslachtsaand', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_persgeslachtsaand"), true);
SELECT SETVAL('kern.seq_his_persgeslnaamcomp', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_persgeslnaamcomp"), true);
SELECT SETVAL('kern.seq_his_persids', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_persids"), true);
SELECT SETVAL('kern.seq_his_persmigratie', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_persmigratie"), true);
SELECT SETVAL('kern.seq_his_persindicatie', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_persindicatie"), true);
SELECT SETVAL('kern.seq_his_persinschr', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_persinschr"), true);
SELECT SETVAL('kern.seq_his_persnation', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_persnation"), true);
SELECT SETVAL('kern.seq_his_persoverlijden', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_persoverlijden"), true);
SELECT SETVAL('kern.seq_his_perspk', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_perspk"), true);
SELECT SETVAL('kern.seq_his_persreisdoc', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_persreisdoc"), true);
SELECT SETVAL('kern.seq_his_perssamengesteldenaam', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_perssamengesteldenaam"), true);
SELECT SETVAL('kern.seq_his_persuitslkiesr', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_persuitslkiesr"), true);
SELECT SETVAL('kern.seq_his_persverblijfsr', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_persverblijfsr"), true);
SELECT SETVAL('kern.seq_his_persverificatie', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_persverificatie"), true);
SELECT SETVAL('kern.seq_his_persvoornaam', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_persvoornaam"), true);
SELECT SETVAL('kern.seq_onderzoek', (select 90000+ coalesce(max(id),0)  FROM "kern"."onderzoek"), true);
SELECT SETVAL('kern.seq_pers', (select 90000+ coalesce(max(id),0)  FROM "kern"."pers"), true);
SELECT SETVAL('kern.seq_persadres', (select 90000+ coalesce(max(id),0)  FROM "kern"."persadres"), true);
SELECT SETVAL('kern.seq_persgeslnaamcomp', (select 90000+ coalesce(max(id),0)  FROM "kern"."persgeslnaamcomp"), true);
SELECT SETVAL('kern.seq_persindicatie', (select 90000+ coalesce(max(id),0)  FROM "kern"."persindicatie"), true);
SELECT SETVAL('kern.seq_persnation', (select 90000+ coalesce(max(id),0)  FROM "kern"."persnation"), true);
SELECT SETVAL('kern.seq_persreisdoc', (select 90000+ coalesce(max(id),0)  FROM "kern"."persreisdoc"), true);
SELECT SETVAL('kern.seq_persverificatie', (select 90000+ coalesce(max(id),0)  FROM "kern"."persverificatie"), true);
SELECT SETVAL('kern.seq_persvoornaam', (select 90000+ coalesce(max(id),0)  FROM "kern"."persvoornaam"), true);
SELECT SETVAL('kern.seq_regelverantwoording', (select 90000+ coalesce(max(id),0)  FROM "kern"."regelverantwoording"), true);
SELECT SETVAL('kern.seq_relatie', (select 90000+ coalesce(max(id),0)  FROM "kern"."relatie"), true);
SELECT SETVAL('kern.seq_his_relatie', (select 90000+ coalesce(max(id),0)  FROM "kern"."his_relatie"), true);

SELECT SETVAL('autaut.seq_his_authenticatiemiddel', (select 90000+ coalesce(max(id),0)  FROM "autaut"."his_authenticatiemiddel"), true);
SELECT SETVAL('autaut.seq_his_autorisatiebesluit', (select 90000+ coalesce(max(id),0)  FROM "autaut"."his_autorisatiebesluit"), true);
-- SELECT SETVAL('autaut.seq_his_autorisatiebesluitbijhau', (select 90000+ coalesce(max(id),0)  FROM "autaut"."his_autorisatiebesluitbijhau"), true);
SELECT SETVAL('autaut.seq_his_bijhautorisatie', (select 90000+ coalesce(max(id),0)  FROM "autaut"."his_bijhautorisatie"), true);
--SELECT SETVAL('autaut.seq_his_bijhsituatie', (select 90000+ coalesce(max(id),0)  FROM "autaut"."his_bijhsituatie"), true);
SELECT SETVAL('autaut.seq_his_doelbinding', (select 90000+ coalesce(max(id),0)  FROM "autaut"."his_doelbinding"), true);
SELECT SETVAL('autaut.seq_his_uitgeslotene', (select 90000+ coalesce(max(id),0)  FROM "autaut"."his_uitgeslotene"), true);

-- SELECT SETVAL('ber.seq_admhndbijgehoudenpers', (select 90000+ coalesce(max(id),0)  FROM "ber"."admhndbijgehoudenpers"), true);
SELECT SETVAL('kern.seq_admhndgedeblokkeerderegel', (select 90000+ coalesce(max(id),0)  FROM "kern"."admhndgedeblokkeerderegel"), true);
SELECT SETVAL('ber.seq_ber', (select 90000+ coalesce(max(id),0)  FROM "ber"."ber"), true);

SELECT SETVAL('autaut.seq_his_abonnement', (select 90000+ coalesce(max(id),0)  FROM "autaut"."his_abonnement"), true);
-- SELECT SETVAL('lev.seq_his_abonnementsrtber', (select 90000+ coalesce(max(id),0)  FROM "lev"."his_abonnementsrtber"), true);
SELECT SETVAL('lev.seq_lev', (select 90000+ coalesce(max(id),0)  FROM "lev"."lev"), true);
SELECT SETVAL('lev.seq_levpers', (select 90000+ coalesce(max(id),0)  FROM "lev"."levpers"), true);


COMMIT;
