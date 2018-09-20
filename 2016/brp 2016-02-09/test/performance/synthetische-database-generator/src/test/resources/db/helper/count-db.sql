-- Deze query laat zien hoeveel rijen er per tabel in het schema kern bestaan.

SELECT
  pgClass.relname AS tableName,
  pgClass.reltuples AS rowCount
FROM
  pg_class pgClass
LEFT JOIN
  pg_namespace pgNamespace ON (pgNamespace.oid = pgClass.relnamespace)
WHERE
  pgNamespace.nspname NOT IN ('pg_catalog', 'information_schema')
  AND pgClass.relkind='r'
  AND nspname = 'kern'
ORDER BY tableName ASC
