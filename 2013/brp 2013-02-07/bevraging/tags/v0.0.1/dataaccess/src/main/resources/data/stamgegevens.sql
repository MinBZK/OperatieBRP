--
-- File: stamgegevens.sql
-- Doel: Vulling van de statische stamtabellen.
--

-- Soort persoon
DELETE FROM kern.srtpers;
INSERT INTO kern.srtpers (id, code, naam, dataanvgel, indmaterieel) VALUES (0, 'I', 'Ingeschrevene', 20112011, true);
INSERT INTO kern.srtpers (id, code, naam, dataanvgel, indmaterieel) VALUES (1, 'N', 'Niet ingeschrevene', 20112011, true);
INSERT INTO kern.srtpers (id, code, naam, dataanvgel, indmaterieel) VALUES (2, 'A', 'Alternatieve realiteit', 20112011, true);

-- Geslachtsaanduiding
DELETE FROM kern.geslachtsaand;
INSERT INTO kern.geslachtsaand (id, code, naam, indmaterieel) VALUES (0, 'M', 'Man', true);
INSERT INTO kern.geslachtsaand (id, code, naam, indmaterieel) VALUES (1, 'V', 'Vrouw', true);
INSERT INTO kern.geslachtsaand (id, code, naam, indmaterieel) VALUES (2, 'O', 'Onbekend', true);


