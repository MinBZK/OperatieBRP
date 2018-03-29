-- Verwijder evt vervuilde testdata en insert testdata voor sortering en ongeldige soorten.
DELETE FROM kern.srtvrijber WHERE naam = 'Zoort' OR naam = 'Soort vrijber morgen geldig' OR naam =  'Soort vrijber niet meer geldig' or naam = 'Nieuw soort vrij bericht toegevoegd via de beheer applicatie';

INSERT INTO kern.srtvrijber (naam, dataanvgel, dateindegel) VALUES
('Zoort', 20160101, null),
('Soort vrijber morgen geldig', (SELECT to_number(to_char(current_date + interval '1 days', 'YYYYMMDD'), '99999999')), null),
('Soort vrijber niet meer geldig', 20160101, 20160102);

