-- Deze file bevat 'handmatige' aanpassingen aan de brp.sql.
-- Dit zijn aanpassingen die bijvoorbeeld tijdelijk nodig zijn om het geheel werkend te krijgen.
-- Uiteindelijk is de doelstelling dat deze file niet meer nodig hoeft te zijn.

-- Zet (tijdelijk) de verplichting van administratieve handeling uit op een actie.
ALTER TABLE Kern.Actie ALTER COLUMN AdmHnd DROP NOT NULL;

-- SoortBericht is verplicht in het data model maar niet in te vullen door archivering omdat archivering voor het parsen
-- van de xml plaatsvindt. Potentieel moet archivering anders worden opgezet.
-- In de oude brp.sql was dit ook al uitgecommentarieerd.
ALTER TABLE Ber.Ber ALTER COLUMN srt DROP NOT NULL;
