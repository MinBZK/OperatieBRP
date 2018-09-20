INSERT INTO autaut.his_abonnement
(id, abonnement, tsreg, tsverval, actieinh, actieverval, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand)
SELECT id, id, '2013-01-01 00:00:00.000+02', null, (SELECT MIN(id) FROM kern.actie), null, populatiebeperking, protocolleringsniveau, datingang, dateinde, toestand
FROM autaut.abonnement;

INSERT INTO autaut.his_toegangabonnement
(id, toegangabonnement, tsreg, tsverval, actieinh, actieverval, datingang, dateinde)
SELECT id, id, '2013-01-01 00:00:00.000+02', null, (SELECT MIN(id) FROM kern.actie), null, datingang, dateinde
FROM autaut.toegangabonnement;

INSERT INTO autaut.his_dienst
(id, dienst, tsreg, tsverval, actieinh, actieverval, naderepopulatiebeperking, datingang, dateinde, toestand, eersteselectiedat, selectieperiodeinmaanden)
SELECT id, id, '2013-01-01 00:00:00.000+02', null, (SELECT MIN(id) FROM kern.actie), null, naderepopulatiebeperking, datingang, dateinde, toestand, eersteselectiedat, selectieperiodeinmaanden
FROM autaut.dienst;

INSERT INTO autaut.his_afleverwijze
(id, afleverwijze, tsreg, tsverval, actieinh, actieverval, datingang, dateinde)
SELECT id, id, '2013-01-01 00:00:00.000+02', null, (SELECT MIN(id) FROM kern.actie), null, datingang, dateinde
FROM autaut.afleverwijze;

INSERT INTO autaut.his_persafnemerindicatie (persafnemerindicatie, dataanvmaterieleperiode, tsreg, actieinh)
SELECT id, '20130101', '2013-01-01 00:00:00.000+02', (SELECT MIN(id) FROM kern.actie)
FROM autaut.persafnemerindicatie;
