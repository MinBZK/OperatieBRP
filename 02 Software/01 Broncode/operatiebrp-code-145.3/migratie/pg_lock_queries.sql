select pid
,      datname
,      usename
,      waiting
,      state
,      query 
,      (select count(*) from pg_locks where pg_locks.pid = pg_stat_activity.pid) locks
from   pg_stat_activity
;

select pg_database.datname
,      pg_class.relname
,      pg_locks.*
from   pg_locks
join   pg_database on pg_locks.database = pg_database.oid
join   pg_class on pg_locks.relation = pg_class.oid
;

SELECT blockeda.pid AS blocked_pid, blockeda.query as blocked_query,
  blockinga.pid AS blocking_pid, blockinga.query as blocking_query
FROM pg_catalog.pg_locks blockedl
JOIN pg_stat_activity blockeda ON blockedl.pid = blockeda.pid
JOIN pg_catalog.pg_locks blockingl ON(blockingl.transactionid=blockedl.transactionid
  AND blockedl.pid != blockingl.pid)
JOIN pg_stat_activity blockinga ON blockingl.pid = blockinga.pid
WHERE NOT blockedl.granted ;

