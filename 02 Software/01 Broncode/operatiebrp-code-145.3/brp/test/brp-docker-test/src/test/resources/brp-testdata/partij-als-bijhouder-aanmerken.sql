-- Vullen van kern.partijrol met bijhouder rollen
BEGIN;
INSERT INTO kern.partijrol (partij, datingang, rol) VALUES ((SELECT id FROM kern.partij WHERE naam='Migratievoorziening'), '19940101', '2');
INSERT INTO kern.partijrol (partij, datingang, rol) VALUES ((SELECT id FROM kern.partij WHERE naam='Gemeente Tiel'), '19940101', '2');
INSERT INTO kern.partijrol (partij, datingang, rol) VALUES ((SELECT id FROM kern.partij WHERE naam='Gemeente Olst'), '19940101', '2');
INSERT INTO kern.partijrol (partij, datingang, rol) VALUES ((SELECT id FROM kern.partij WHERE naam='Gemeente Alkmaar'), '19940101', '2');
INSERT INTO kern.partijrol (partij, datingang, rol) VALUES ((SELECT id FROM kern.partij WHERE naam='Gemeente Amsterdam'), '19940101', '2');
COMMIT;
