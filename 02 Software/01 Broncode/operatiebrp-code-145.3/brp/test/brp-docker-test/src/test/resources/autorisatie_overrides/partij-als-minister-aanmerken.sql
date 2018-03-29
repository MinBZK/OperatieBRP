-- Vullen van kern.partijrol met Bijhoudingsorgaan Minister

BEGIN;
INSERT INTO kern.partijrol (partij, rol, datingang) VALUES ((SELECT id FROM kern.partij WHERE naam='Minister'), '3', '20130101');
INSERT INTO kern.partijrol (partij, rol, datingang) VALUES ((SELECT id FROM kern.partij WHERE naam='Gemeente Oosterhout'),'3','20130101');
INSERT INTO kern.partijrol (partij, rol, datingang) VALUES ((SELECT id FROM kern.partij WHERE naam='BRP Minister Test'),'3','20130101');
COMMIT;
