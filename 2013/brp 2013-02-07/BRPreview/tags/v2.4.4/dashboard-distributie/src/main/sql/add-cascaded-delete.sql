-- update script om van release 2.2.5 naar release 2.2.6 te gaan
ALTER TABLE dashboard.bericht_bsn DROP CONSTRAINT bericht_bsn_bericht_fkey;
ALTER TABLE dashboard.bericht_bsn ADD FOREIGN KEY (bericht) REFERENCES dashboard.berichten ON DELETE CASCADE;
