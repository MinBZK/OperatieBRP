SELECT 'ALTER TABLE "'||nspname||'"."'||relname||'" ADD CONSTRAINT "'||conname||'" '|| pg_get_constraintdef(pg_constraint.oid)||';' as tempColumn 
FROM pg_constraint
INNER JOIN pg_class ON conrelid=pg_class.oid
INNER JOIN pg_namespace ON pg_namespace.oid=pg_class.relnamespace
--WHERE contype not in ('p','u')
ORDER BY  CASE WHEN contype='p' THEN 0 WHEN contype='f' THEN 1 WHEN contype='u' THEN 2 ELSE 3 END, nspname,relname;
