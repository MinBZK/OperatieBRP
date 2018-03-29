INSERT INTO viewer.protocollering (id, gebruikersnaam, datumtijd, a_nummer, geautoriseerd) VALUES (1, 'test', '2013-05-07 13:05:31.41', 1111111111, true);
INSERT INTO viewer.protocollering (id, gebruikersnaam, datumtijd, a_nummer, geautoriseerd) VALUES (2, 'admin', '2013-05-07 14:03:03.151', '8750000001', true);
INSERT INTO viewer.protocollering (id, gebruikersnaam, datumtijd, a_nummer, geautoriseerd) VALUES (3, 'admin', '2013-05-07 14:17:07.356', '8750000002', true);

ALTER SEQUENCE viewer.seq_protocollering RESTART WITH 4;
