-- Toevoegen van de kolom die gebruikt wordt om de tijdstempel tbv verwerking mutatie te plaatsen.

ALTER TABLE kern.admhnd ADD COLUMN tsverwerkingmutatie timestamp without time zone;
