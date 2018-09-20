-- BOLIE: met de hand toegevoegd, wordt met de nieuwe generator overschreven.

update Ber.SrtBer set naam = 'AFS_RegistreerErkenning_B-obsolete' WHERE ID = 39;
update Ber.SrtBer set naam = 'AFS_RegistreerErkenning_BR-obsolete' WHERE ID = 40;

INSERT INTO Ber.SrtBer (ID, Naam, Module) VALUES (24, 'AFS_RegistreerErkenning_B', 1);
INSERT INTO Ber.SrtBer (ID, Naam, Module) VALUES (25, 'AFS_RegistreerErkenning_BR', 1);
INSERT INTO Ber.SrtBer (ID, Naam, Module) VALUES (26, 'AFS_CorrigeerAfstamming_B', 1);
INSERT INTO Ber.SrtBer (ID, Naam, Module) VALUES (27, 'AFS_CorrigeerAfstamming_BR', 1);
INSERT INTO Ber.SrtBer (ID, Naam, Module) VALUES (28, 'MIG_RegistreerInschrijvingDoorImmigratie_B', 3);
INSERT INTO Ber.SrtBer (ID, Naam, Module) VALUES (29, 'MIG_RegistreerInschrijvingDoorImmigratie_BR', 3);
INSERT INTO Ber.SrtBer (ID, Naam, Module) VALUES (30, 'MIG_RegistreerOpschorting_B', 3);
INSERT INTO Ber.SrtBer (ID, Naam, Module) VALUES (31, 'MIG_RegistreerOpschorting_BR', 3);
INSERT INTO Ber.SrtBer (ID, Naam, Module) VALUES (32, 'MIG_CorrigeerOpschorting_B', 3);
INSERT INTO Ber.SrtBer (ID, Naam, Module) VALUES (33, 'MIG_CorrigeerOpschorting_BR', 3);
INSERT INTO Ber.SrtBer (ID, Naam, Module) VALUES (34, 'NAT_RegistreerNationaliteit_B', 1);
INSERT INTO Ber.SrtBer (ID, Naam, Module) VALUES (35, 'NAT_RegistreerNationaliteit_BR', 1);
INSERT INTO Ber.SrtBer (ID, Naam, Module) VALUES (36, 'NAT_CorrigeerNationaliteit_B', 1);
INSERT INTO Ber.SrtBer (ID, Naam, Module) VALUES (37, 'NAT_CorrigeerNationaliteit_BR', 1);
INSERT INTO Ber.SrtBer (ID, Naam, Module) VALUES (38, 'HGP_CorrigeerHuwelijkPartnerschap_B', 2);
UPDATE Ber.SrtBer SET Naam = 'HGP_CorrigeerHuwelijkPartnerschap_BR', Module=2 WHERE ID = 39;


update Kern.SrtActie set naam = 'Registratie vaderschap-obsolete' WHERE ID = 41;

INSERT INTO Kern.SrtActie (ID, Naam) VALUES (25, 'Registratie vaderschap');
INSERT INTO Kern.SrtActie (ID, Naam) VALUES (26, 'Beeindiging nationaliteit');
INSERT INTO Kern.SrtActie (ID, Naam) VALUES (27, 'Beeindiging ouderschap');
INSERT INTO Kern.SrtActie (ID, Naam) VALUES (28, 'Beeindiging behandeld als nederlander');
INSERT INTO Kern.SrtActie (ID, Naam) VALUES (29, 'Beeindiging verstrekkingsbeperking');

INSERT INTO Kern.SrtActie (ID, Naam) VALUES (52, 'Registratie voornaam');
INSERT INTO Kern.SrtActie (ID, Naam) VALUES (53, 'Beeindiging voornaam');
INSERT INTO Kern.SrtActie (ID, Naam) VALUES (54, 'Registratie geslachtsnaam');
INSERT INTO Kern.SrtActie (ID, Naam) VALUES (55, 'Registratie opschorting');

INSERT INTO Kern.SrtAdmHnd (ID, Code, Naam, CategorieAdmHnd, Module) VALUES (39, '05008', 'Inschrijving door immigratie', 1, 1);
INSERT INTO Kern.SrtAdmHnd (ID, Code, Naam, CategorieAdmHnd, Module) VALUES (40, '05009', 'Opschorting ingezetene', 1, 1);
INSERT INTO Kern.SrtAdmHnd (ID, Code, Naam, CategorieAdmHnd, Module) VALUES (41, '05010', 'Beeindiging opschorting', 1, 1);
INSERT INTO Kern.SrtAdmHnd (ID, Code, Naam, CategorieAdmHnd, Module) VALUES (42, '05011', 'Correctie opschorting', 1, 1);
INSERT INTO Kern.SrtAdmHnd (ID, Code, Naam, CategorieAdmHnd, Module) VALUES (43, '06001', 'Registratie nationaliteit', 1, 1);

ALTER TABLE kern.admhnd RENAME COLUMN tsleveringverwerkt TO tslev;
