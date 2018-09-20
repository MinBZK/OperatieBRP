-- Dit is de testdata die nodig is voor de POC met migratie

-- Abonnement voor de diensten doelbinding en synchroniseer persoon toevoegen
INSERT INTO autaut.abonnement (naam,populatiebeperking,protocolleringsniveau,datingang,dateinde,toestand) VALUES('Automatisch synchroniseren populatie 051801','WAAR','1','20130101',null,'4');
INSERT INTO autaut.his_abonnement
(abonnement,tsreg,tsverval,actieinh,actieverval,nadereaandverval,populatiebeperking,protocolleringsniveau,datingang,dateinde,toestand,indaliassrtadmhndleveren) VALUES((select currval('autaut.seq_abonnement')),'20130101',null,null,null,null,'WAAR','1','20130101',null,'4',false);

-- ToegangAbonnement voor de diensten doelbinding en synchroniseer persoon toevoegen
INSERT INTO autaut.toegangabonnement (partij,authenticatiemiddel,intermediair,abonnement,datingang,dateinde) VALUES(521,null,null,(select currval('autaut.seq_abonnement')),'20130101',null);
INSERT INTO autaut.his_toegangabonnement (toegangabonnement,tsreg,tsverval,actieinh,actieverval,nadereaandverval,datingang,dateinde) VALUES((select currval('autaut.seq_toegangabonnement')),'20130101',null,null,null,null,'20130101',null);

-- Diensten doelbinding en synchroniseer persoon voor het eerste abonnement
-- Mutatielevering op basis van doelbinding
INSERT INTO autaut.dienst (catalogusoptie,abonnement,naderepopulatiebeperking,attenderingscriterium,datingang,dateinde,toestand) VALUES('1',(select currval('autaut.seq_abonnement')),null,null,'20130101',null,'4');
INSERT INTO autaut.his_dienst (dienst,tsreg,tsverval,actieinh,actieverval,nadereaandverval,naderepopulatiebeperking,datingang,dateinde,toestand,eersteselectiedat,selectieperiodeinmaanden) VALUES((select currval('autaut.seq_dienst')),'20130101',null,null,null,null,null,'20130101',null,'4',null,null);

--Geef details persoon
INSERT INTO autaut.dienst (catalogusoptie,abonnement,naderepopulatiebeperking,attenderingscriterium,datingang,dateinde,toestand) VALUES('9',(select currval('autaut.seq_abonnement')),null,null,'20130101',null,'4');
INSERT INTO autaut.his_dienst (dienst,tsreg,tsverval,actieinh,actieverval,nadereaandverval,naderepopulatiebeperking,datingang,dateinde,toestand,eersteselectiedat,selectieperiodeinmaanden) VALUES((select currval('autaut.seq_dienst')),'20130101',null,null,null,null,null,'20130101',null,'4',null,null);

--Synchroniseer persoon
INSERT INTO autaut.dienst (catalogusoptie,abonnement,naderepopulatiebeperking,attenderingscriterium,datingang,dateinde,toestand) VALUES('10',(select currval('autaut.seq_abonnement')),null,null,'20130101',null,'4');
INSERT INTO autaut.his_dienst (dienst,tsreg,tsverval,actieinh,actieverval,nadereaandverval,naderepopulatiebeperking,datingang,dateinde,toestand,eersteselectiedat,selectieperiodeinmaanden) VALUES((select currval('autaut.seq_dienst')),'20130101',null,null,null,null,null,'20130101',null,'4',null,null);

--Afleverwijze voor het eerste abonnement
INSERT INTO autaut.afleverwijze (toegangabonnement,kanaal,uri,datingang,dateinde) VALUES((select currval('autaut.seq_toegangabonnement')),'2','http://localhost:8280/afnemer-voorbeeld/BrpBerichtVerwerkingService','20130101',null);
INSERT INTO autaut.his_afleverwijze (afleverwijze,tsreg,tsverval,actieinh,actieverval,nadereaandverval,datingang,dateinde) VALUES((select currval('autaut.seq_afleverwijze')),'20130101',null,null,null,null,'20130101',null);

--Gegevensfilter open zetten
INSERT INTO autaut.abonnementexpressie (abonnement, expressie, rol, actief) SELECT  a.id, e.id, 1, TRUE from kern.expressie e, autaut.abonnement a where a.id = (select currval('autaut.seq_abonnement')) and (e.versieexpressietaal != 'LO3' or e.versieexpressietaal is null);

--Groepenfilter open zetten
INSERT INTO autaut.abonnementgroep (abonnement, groep, indverantwoordingsinfo, indkopiebrondoc, indmaterielehistorie, indformelehistorie) SELECT  a.id,
e.id, TRUE, TRUE, TRUE, TRUE from kern.element e, autaut.abonnement a where a.id = (select currval('autaut.seq_abonnement')) and e.srt = 2;