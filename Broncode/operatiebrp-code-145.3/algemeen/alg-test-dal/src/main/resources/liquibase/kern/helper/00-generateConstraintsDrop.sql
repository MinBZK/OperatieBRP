SELECT 'ALTER TABLE '||nspname||'.'||relname||' DROP CONSTRAINT IF EXISTS '||conname|| ';             -- ' || pg_get_constraintdef(pg_constraint.oid) as tempColumn
FROM pg_constraint
INNER JOIN pg_class ON conrelid=pg_class.oid
INNER JOIN pg_namespace ON pg_namespace.oid=pg_class.relnamespace
ORDER BY CASE WHEN contype='f' THEN 0 WHEN contype='p' THEN 1 WHEN contype='u' THEN 2 ELSE 3 END, nspname,relname;
