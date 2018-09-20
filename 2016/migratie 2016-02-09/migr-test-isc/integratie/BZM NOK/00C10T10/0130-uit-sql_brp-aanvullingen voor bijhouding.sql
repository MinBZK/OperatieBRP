--UPDATE autaut.levsautorisatie set populatiebeperking = 'WAAR';
UPDATE kern.partij set oin = code;
DELETE FROM autaut.toegangbijhautorisatie;
INSERT INTO autaut.toegangbijhautorisatie (geautoriseerde, ondertekenaar, transporteur, datingang, dateinde, indblok)
SELECT geautoriseerde, ondertekenaar, transporteur, datingang, dateinde, null FROM autaut.toeganglevsautorisatie;