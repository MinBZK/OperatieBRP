-- Selecteer alle indexen die NIET onder constraints vallen; deze zijn extra's die we ook moeten droppen en opnieuw aanmaken.
--
--
SELECT pg_get_indexdef(pgi.indexrelid) || ';              -- ' || '"' || n.nspname || '"."' || t.relname || '"."' || i2.relname || '";' AS tempColumn
FROM pg_index pgi
INNER JOIN pg_class t ON pgi.indrelid=t.oid
INNER JOIN pg_class i2 ON pgi.indexrelid=i2.oid
INNER JOIN pg_namespace n ON n.oid=t.relnamespace
WHERE n.nspname NOT IN ('pg_catalog', 'pg_toast')
AND (pgi.indisprimary=false AND pgi.indisunique=false)
AND i2.relname not in (SELECT conname FROM pg_constraint)
ORDER BY n.nspname, t.relname, i2.relname
