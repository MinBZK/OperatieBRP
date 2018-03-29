-- Vullen van kern.partijrol met bijhouder rollen
BEGIN;
INSERT INTO kern.partijrol (partij, datingang, rol) VALUES ((SELECT id FROM kern.partij WHERE naam='Migratievoorziening'), 20000101, 2);
INSERT INTO kern.partijrol (partij, datingang, rol) VALUES ((SELECT id FROM kern.partij WHERE naam='Gemeente Tiel'), 20000101, 2);
INSERT INTO kern.partijrol (partij, datingang, rol) VALUES ((SELECT id FROM kern.partij WHERE naam='Gemeente Olst'), 20000101, 2);
INSERT INTO kern.partijrol (partij, datingang, rol) VALUES ((SELECT id FROM kern.partij WHERE naam='Gemeente Alkmaar'), 20000101, 2);
INSERT INTO kern.partijrol (partij, datingang, rol) VALUES ((SELECT id FROM kern.partij WHERE naam='Gemeente Amsterdam'), 20000101, 2);
COMMIT;
