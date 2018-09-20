-- Stukje code om te regelen dat de database in UTC is:
CREATE OR REPLACE FUNCTION set_currentdatabase_to_utc() returns integer
AS
$body$
declare
result integer;
begin
execute 'ALTER DATABASE "' || current_database() || '"' || ' set timezone to ''UTC''';
RETURN 1;
end
$body$
LANGUAGE plpgsql;
SELECT set_currentdatabase_to_utc();
