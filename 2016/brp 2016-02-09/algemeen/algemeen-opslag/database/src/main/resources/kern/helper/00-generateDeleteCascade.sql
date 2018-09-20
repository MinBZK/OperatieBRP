SELECT 'ALTER TABLE "'||nspname||'"."'||relname||'" DROP CONSTRAINT IF EXISTS "'||conname||'" ;   ALTER TABLE "'||nspname||'"."'||relname||'" ADD CONSTRAINT "'||conname||'" '|| pg_get_constraintdef(pg_constraint.oid) || ' ON DELETE CASCADE;'as tempColumn
FROM pg_constraint
INNER JOIN pg_class ON conrelid=pg_class.oid
INNER JOIN pg_namespace ON pg_namespace.oid=pg_class.relnamespace
WHERE contype='f'
-- We kunnen kiezen voor ALLE foreign keys een CASCADING DELETE (dus ook bv. srtPersoon, functie, rdnVerlies, land, gem etc)
-- AND ( pg_get_constraintdef(pg_constraint.oid) like '%(id)%')
-- OF per stuk aan/uitzetten zoals hieronder
AND ( pg_get_constraintdef(pg_constraint.oid) like '%.pers(id)%'
   OR pg_get_constraintdef(pg_constraint.oid) like '%.betr(id)%'
   OR pg_get_constraintdef(pg_constraint.oid) like '%.relatie(id)%'
   OR pg_get_constraintdef(pg_constraint.oid) like '%.doc(id)%'
   OR pg_get_constraintdef(pg_constraint.oid) like '%.admhnd(id)%'
   OR pg_get_constraintdef(pg_constraint.oid) like '%.actie(id)%'
   OR pg_get_constraintdef(pg_constraint.oid) like '%.persadres(id)%'
   OR pg_get_constraintdef(pg_constraint.oid) like '%.persgeslnaamcomp(id)%'
   OR pg_get_constraintdef(pg_constraint.oid) like '%.persindicatie(id)%'
   OR pg_get_constraintdef(pg_constraint.oid) like '%.persnation(id)%'
   OR pg_get_constraintdef(pg_constraint.oid) like '%.persreisdoc(id)%'
   OR pg_get_constraintdef(pg_constraint.oid) like '%.persverificatie(id)%'
   OR pg_get_constraintdef(pg_constraint.oid) like '%.persvoornaam(id)%'
   OR pg_get_constraintdef(pg_constraint.oid) like '%.persverstrbeperking(id)%'
   OR pg_get_constraintdef(pg_constraint.oid) like '%.persafnemerindicatie(id)%'
   OR pg_get_constraintdef(pg_constraint.oid) like '%.personderzoek(id)%'
   OR pg_get_constraintdef(pg_constraint.oid) like '%.onderzoek(id)%'
   OR pg_get_constraintdef(pg_constraint.oid) like '%.ber(id)%'
   OR pg_get_constraintdef(pg_constraint.oid) like '%.gedeblokkeerdemelding(id)%'
   OR pg_get_constraintdef(pg_constraint.oid) like '%.melding(id)%'
   OR pg_get_constraintdef(pg_constraint.oid) like '%.multirealiteitregel(id)%'
   OR pg_get_constraintdef(pg_constraint.oid) like '%.abonnement(id)%'
   OR pg_get_constraintdef(pg_constraint.oid) like '%.lev(id)%'
   )
AND (relname NOT in ('pers'))
AND (NOT pg_get_constraintdef(pg_constraint.oid) like '%ON DELETE CASCADE%')
ORDER BY nspname,relname, CASE WHEN contype='p' THEN 0 WHEN contype='f' THEN 1 WHEN contype='u' THEN 2 ELSE 3 END;
