-- Genereer SQL statements om de sequences te updaten naar de hoogst beschikbare waarde in de database.
-- Aanname: sequences hebben altijd deze naam :
---                       seq_<tablenaam>
--          elk tabel met een sequence heef id als primary key.
--
--
SELECT 'SELECT SETVAL(' || '''' || n.nspname || '.' ||s.relname || '''' || ', (select 1 + coalesce(max(id),0) FROM ' || n.nspname || '.' || substring(s.relname from 5) || '), false);'  as tempColumn
FROM pg_class s 
INNER JOIN pg_namespace n ON n.oid=s.relnamespace
WHERE s.relkind = 'S'
order by n.nspname, s.relname
;