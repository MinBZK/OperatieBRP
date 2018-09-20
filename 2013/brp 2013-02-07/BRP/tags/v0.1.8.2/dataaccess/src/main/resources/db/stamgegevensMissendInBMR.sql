
-- ------------------------------------------------------------------------
-- De in dit bestand opgenomen SQL statements zijn nodig voor het correct
-- vullen met stamgegevens na een volledige cleanup van de BRP database.
-- In principe zouden alle stamgegevens moeten worden toegevoegd vanuit
-- het gegenereerde stamgegevensStatisch.sql script, maar hierin ontbreken
-- nog enkele statements en deze zijn hieronder opgenomen.
--
-- Uiteraard dienen de hieronder opgenomen waarden nog aan het BMR
-- toegevoegd te worden zodat ze bij een volgende generatie slag wel in
-- het stamgegevensStatisch.sql script worden gegenereerd.
-- ------------------------------------------------------------------------
-- ------------------------------------------------------------------------
-- ------------------------------------------------------------------------
-- ------------------------------------------------------------------------

INSERT INTO kern.rdnverknlnation (ID, code, oms) VALUES (1, 1, 'Bij geboorte');
INSERT INTO kern.rdnverknlnation (ID, code, oms) VALUES (2, 2, 'Bij gratie');
