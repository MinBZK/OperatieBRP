
INSERT INTO kern.partij (id,srt,code,naam,dataanv,dateinde,indverstrbeperkingmogelijk) VALUES('{partijId}','3','{abonnementId}', 'TEST PARTIJ {leveringsautorisatieId}', '19540101', null,'false');

INSERT INTO autaut.levsautorisatie (id,naam,populatiebeperking,protocolleringsniveau,datingang,dateinde,toestand) VALUES('{leveringsautorisatieId}',
'{leveringsautorisatieNaam}', '{populatieBeperking}','1','20130101',null,'4');

INSERT INTO autaut.certificaat (id,subject,serial,signature,partij) VALUES('{certificaatId}','CN=BRPTest, L=Den Haag, ST=Zuid-Holland, C=NL,
EMAILADDRESS=support@modernodam.nl, OU=Modernodam, O=Programma mGBA','{certificaatSignature}',
'signature{partijId}','{partijId}');

INSERT INTO autaut.authenticatiemiddel (id,partij,rol,certificaat) VALUES('{authenticatiemiddelId}','{partijId}','1','{certificaatId}');

INSERT INTO autaut.toeganglevsautorisatie (id,partij,authenticatiemiddel,intermediair,abonnement,datingang,dateinde) VALUES('{toegangLeveringsautorisatieId}',
'{partijId}', '{authenticatiemiddelId}',null,'{leveringsautorisatieId}','20130101',null);

INSERT INTO autaut.dienst (id,catalogusoptie,abonnement,naderepopulatiebeperking,attenderingscriterium,datingang,dateinde,toestand) VALUES('{dienstId}','1','{leveringsautorisatieId}',null,null,'20130101',null,'4');

INSERT INTO autaut.abonnementattribuut (abonnement, attribuut, indactief) SELECT  a.id, e.id, TRUE from autaut.abonnement a, kern.element e where a.id = {abonnementId} and e.expressie is not null and e.srt = 3 and e.elementnaam NOT IN ('Datum aanvang geldigheid', 'Datum einde geldigheid', 'Datum/tijd registratie', 'Datum/tijd verval', 'BRP Actie inhoud', 'BRP Actie verval', 'BRP Actie Aanpassing Geldigheid') and e.autorisatie NOT IN (2,7);

INSERT INTO autaut.abonnementgroep (abonnement, groep, indnadereverantwoording, inddoc, indmaterielehistorie, indformelehistorie) SELECT a.id, e.id, TRUE, TRUE, TRUE, TRUE from  autaut.abonnement a, kern.element e where a.id = {abonnementId} and e.srt = 2;

INSERT INTO kern.gem (id,code,naam,partij,voortzettendegem,dataanvgel,dateindegel) VALUES('{partijId}','{partijId}','Gemeente{partijId}','{partijId}',null,null,null);