
INSERT INTO autaut.abonnement (id,naam,populatiebeperking,protocolleringsniveau,datingang,dateinde,toestand) VALUES('0','testabo0', 'WAAR','1','20130101',null,'4');



--3788 gemeente code
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) values (0, 3788, 'TRUE');
--"Persoon.Geboorte.GemeenteCode"
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) values (0, 3675, 'TRUE');
--"Persoon.Identificatienummers.Burgerservicenummer"
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) values (0, 3018, 'TRUE');
--"Persoon.Identificatienummers.Administratienummer"
INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) values (0, 3013, 'TRUE');


INSERT INTO autaut.dienst (id,catalogusoptie,abonnement,naderepopulatiebeperking,attenderingscriterium,datingang,dateinde,toestand) VALUES('0','2','0',null,null,'20130101',null,'4');



--INSERT INTO autaut.certificaat (id,subject,serial,signature,partij) VALUES('0','CN=BRPTest, L=Den Haag, ST=Zuid-Holland, C=NL, EMAILADDRESS=support@modernodam.nl, OU=Modernodam, O=Programma mGBA','0', 'signature0','1');

--INSERT INTO autaut.authenticatiemiddel (id,partij,rol,certificaat) VALUES('0','1','1','0');

INSERT INTO autaut.toegangabonnement (id,partij,authenticatiemiddel,intermediair,abonnement,datingang,dateinde) VALUES('0', '1', '0',null,'0','20130101',null);
INSERT INTO autaut.toegangabonnement (id,partij,authenticatiemiddel,intermediair,abonnement,datingang,dateinde) VALUES('1', '2', '0',null,'0','20130101',null);
INSERT INTO autaut.toegangabonnement (id,partij,authenticatiemiddel,intermediair,abonnement,datingang,dateinde) VALUES('2', '3', '0',null,'0','20130101',null);
INSERT INTO autaut.toegangabonnement (id,partij,authenticatiemiddel,intermediair,abonnement,datingang,dateinde) VALUES('3', '4', '0',null,'0','20130101',null);
INSERT INTO autaut.toegangabonnement (id,partij,authenticatiemiddel,intermediair,abonnement,datingang,dateinde) VALUES('4', '5', '0',null,'0','20130101',null);
INSERT INTO autaut.toegangabonnement (id,partij,authenticatiemiddel,intermediair,abonnement,datingang,dateinde) VALUES('5', '6', '0',null,'0','20130101',null);
INSERT INTO autaut.toegangabonnement (id,partij,authenticatiemiddel,intermediair,abonnement,datingang,dateinde) VALUES('6', '7', '0',null,'0','20130101',null);
INSERT INTO autaut.toegangabonnement (id,partij,authenticatiemiddel,intermediair,abonnement,datingang,dateinde) VALUES('7', '8', '0',null,'0','20130101',null);
INSERT INTO autaut.toegangabonnement (id,partij,authenticatiemiddel,intermediair,abonnement,datingang,dateinde) VALUES('8', '9', '0',null,'0','20130101',null);
INSERT INTO autaut.toegangabonnement (id,partij,authenticatiemiddel,intermediair,abonnement,datingang,dateinde) VALUES('9', '10', '0',null,'0','20130101',null);

INSERT INTO autaut.afleverwijze (id,toegangabonnement,kanaal,uri,datingang,dateinde) VALUES('0','0','2','http://localhost:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService','20130101',null);
INSERT INTO autaut.afleverwijze (id,toegangabonnement,kanaal,uri,datingang,dateinde) VALUES('1','1','2','http://localhost:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService','20130101',null);
INSERT INTO autaut.afleverwijze (id,toegangabonnement,kanaal,uri,datingang,dateinde) VALUES('2','2','2','http://localhost:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService','20130101',null);
INSERT INTO autaut.afleverwijze (id,toegangabonnement,kanaal,uri,datingang,dateinde) VALUES('3','3','2','http://localhost:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService','20130101',null);
INSERT INTO autaut.afleverwijze (id,toegangabonnement,kanaal,uri,datingang,dateinde) VALUES('4','4','2','http://localhost:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService','20130101',null);
INSERT INTO autaut.afleverwijze (id,toegangabonnement,kanaal,uri,datingang,dateinde) VALUES('5','5','2','http://localhost:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService','20130101',null);
INSERT INTO autaut.afleverwijze (id,toegangabonnement,kanaal,uri,datingang,dateinde) VALUES('6','6','2','http://localhost:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService','20130101',null);
INSERT INTO autaut.afleverwijze (id,toegangabonnement,kanaal,uri,datingang,dateinde) VALUES('7','7','2','http://localhost:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService','20130101',null);
INSERT INTO autaut.afleverwijze (id,toegangabonnement,kanaal,uri,datingang,dateinde) VALUES('8','8','2','http://localhost:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService','20130101',null);
INSERT INTO autaut.afleverwijze (id,toegangabonnement,kanaal,uri,datingang,dateinde) VALUES('9','9','2','http://localhost:8080/brp-kennisgeving-ontvanger/BrpBerichtVerwerkingService','20130101',null);



