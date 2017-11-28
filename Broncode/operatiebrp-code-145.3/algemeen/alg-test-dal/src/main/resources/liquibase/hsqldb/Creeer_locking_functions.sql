DROP FUNCTION IF EXISTS pg_advisory_xact_lock_shared;
CREATE FUNCTION pg_advisory_xact_lock_shared (t INT, w INT)
  RETURNS INT
  RETURN NULL;

DROP FUNCTION IF EXISTS pg_advisory_xact_lock;
CREATE FUNCTION pg_advisory_xact_lock (t INT, w INT)
  RETURNS INT
  RETURN NULL;
