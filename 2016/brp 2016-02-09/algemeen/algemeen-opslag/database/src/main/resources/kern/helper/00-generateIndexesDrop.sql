-- Selecteer alle indexen die NIET onder constraints vallen; deze zijn extra's die we ook moeten droppen en opnieuw aanmaken.
--
--
SELECT 'DROP INDEX IF EXISTS "' || n.nspname || '"."' || i2.relname || '" ;        -- ' ||  pg_get_indexdef(pgi.indexrelid) || ';' as tempColumn
FROM pg_index pgi
INNER JOIN pg_class t ON pgi.indrelid=t.oid
INNER JOIN pg_class i2 ON pgi.indexrelid=i2.oid
INNER JOIN pg_namespace n ON n.oid=t.relnamespace
WHERE n.nspname not in ('pg_catalog', 'pg_toast')
AND i2.relname not in (SELECT conname FROM pg_constraint)
ORDER BY n.nspname, t.relname, i2.relname
;
