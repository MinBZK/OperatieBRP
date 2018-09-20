
INSERT INTO autaut.abonnement (id,naam,populatiebeperking,protocolleringsniveau,datingang,dateinde,toestand) VALUES('0','testabo0', 'WAAR','1','20130101',null,'4');

INSERT INTO autaut.certificaat (id,subject,serial,signature,partij) VALUES('0','CN=BRPTest, L=Den Haag, ST=Zuid-Holland, C=NL,
EMAILADDRESS=support@modernodam.nl, OU=Modernodam, O=Programma mGBA','0',
'signature0','1');

INSERT INTO autaut.authenticatiemiddel (id,partij,rol,certificaat) VALUES('0','1','1','0');

INSERT INTO autaut.toegangabonnement (id,partij,authenticatiemiddel,intermediair,abonnement,datingang,dateinde) VALUES('0', '1', '0',null,'0','20130101',null);

INSERT INTO autaut.afleverwijze (id,toegangabonnement,kanaal,uri,datingang,dateinde) VALUES('0','0','2','http://localhost:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService','20130101',null);

INSERT INTO autaut.dienst (id,catalogusoptie,abonnement,naderepopulatiebeperking,attenderingscriterium,datingang,dateinde,toestand) VALUES('0','1','0',null,null,'20130101',null,'4');

INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = 0 and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum aanvang geldigheid', 'Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.autorisatie NOT IN (2,7);

INSERT INTO autaut.abonnementgroep (abonnement, groep, indnadereverantwoording, inddoc, indmaterielehistorie, indformelehistorie) SELECT a.id, e.id, TRUE, TRUE, TRUE, TRUE from  autaut.abonnement a, kern.element e where a.id = 0 and e.srt = 2;

