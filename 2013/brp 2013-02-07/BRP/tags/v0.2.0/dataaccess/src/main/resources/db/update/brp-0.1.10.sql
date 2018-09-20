-- Code op kern.Partij is niet langer verplicht.
ALTER TABLE kern.partij ALTER COLUMN code DROP NOT NULL;

-- Verwijder de code van die partijen die geen code zouden moeten hebben.
UPDATE kern.partij SET code = NULL WHERE id = 1;
UPDATE kern.partij SET code = NULL WHERE id = 2;
UPDATE kern.partij SET code = NULL WHERE id = 2000;
