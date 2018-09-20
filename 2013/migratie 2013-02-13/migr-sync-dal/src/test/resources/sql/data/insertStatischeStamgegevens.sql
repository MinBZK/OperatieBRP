-- RM: COMPLEET
INSERT INTO Kern.AdellijkeTitel (ID, Code, NaamMannelijk, NaamVrouwelijk) VALUES (1, 'B', 'baron', 'barones');
INSERT INTO Kern.AdellijkeTitel (ID, Code, NaamMannelijk, NaamVrouwelijk) VALUES (2, 'G', 'graaf', 'gravin');
INSERT INTO Kern.AdellijkeTitel (ID, Code, NaamMannelijk, NaamVrouwelijk) VALUES (3, 'H', 'hertog', 'hertogin');
INSERT INTO Kern.AdellijkeTitel (ID, Code, NaamMannelijk, NaamVrouwelijk) VALUES (4, 'M', 'markies', 'markiezin');
INSERT INTO Kern.AdellijkeTitel (ID, Code, NaamMannelijk, NaamVrouwelijk) VALUES (5, 'P', 'prins', 'prinses');
INSERT INTO Kern.AdellijkeTitel (ID, Code, NaamMannelijk, NaamVrouwelijk) VALUES (6, 'R', 'ridder', 'ridder');

INSERT INTO Kern.CategorieSrtActie (ID, Naam, Oms) VALUES (1, 'Conversie', 'Alle soorten acties voortvloeiend uit conversie');
INSERT INTO Kern.CategorieSrtActie (ID, Naam, Oms) VALUES (2, 'Familierechtelijke betrekking', 'Alle soorten acties met betrekking tot leggen van familierechtelijke betrekking tussen ouder(s) en kind');
INSERT INTO Kern.CategorieSrtActie (ID, Naam, Oms) VALUES (3, 'Verhuizing', 'Alle soorten acties met betrekking tot verhuizingen');
INSERT INTO Kern.CategorieSrtActie (ID, Naam, Oms) VALUES (4, 'Huwelijk / Geregistreerd Partnerschap', 'Alle soorten acties met betrekking tot huwelijk/geregistreerd partnerschap');

INSERT INTO Kern.CategorieSrtDoc (ID, Naam, Oms, DatAanvGel, DatEindeGel) VALUES (1, 'Nederlandse Akte', 'Nederlandse Akten van de burgerlijke stand.', null, null);

-- RM: COMPLEET
INSERT INTO Kern.FunctieAdres (ID, Code, Naam) VALUES (1, 'W', 'Woonadres');
INSERT INTO Kern.FunctieAdres (ID, Code, Naam) VALUES (2, 'B', 'Briefadres');

-- RM: COMPLEET
INSERT INTO Kern.Geslachtsaand (ID, Code, Naam) VALUES (1, 'M', 'Man');
INSERT INTO Kern.Geslachtsaand (ID, Code, Naam) VALUES (2, 'V', 'Vrouw');
INSERT INTO Kern.Geslachtsaand (ID, Code, Naam) VALUES (3, 'O', 'Onbekend');

-- RM: COMPLEET
INSERT INTO Kern.Predikaat (ID, Code, NaamMannelijk, NaamVrouwelijk) VALUES (1, 'K', 'Zijne Koninklijke Hoogheid', 'Hare Koninklijke Hoogheid');
INSERT INTO Kern.Predikaat (ID, Code, NaamMannelijk, NaamVrouwelijk) VALUES (2, 'H', 'Zijne Hoogheid', 'Hare Hoogheid');
INSERT INTO Kern.Predikaat (ID, Code, NaamMannelijk, NaamVrouwelijk) VALUES (3, 'J', 'jonkheer', 'jonkvrouw');

-- RM: COMPLEET
INSERT INTO Kern.RdnOpschorting (ID, Code, Naam) VALUES (1, 'O', 'Overlijden');
INSERT INTO Kern.RdnOpschorting (ID, Code, Naam) VALUES (2, 'M', 'Ministerieel besluit');
INSERT INTO Kern.RdnOpschorting (ID, Code, Naam) VALUES (3, 'F', 'Fout');
INSERT INTO Kern.RdnOpschorting (ID, Code, Naam) VALUES (4, '?', 'Onbekend');

INSERT INTO Kern.Rol (ID, Naam, DatAanvGel, DatEindeGel) VALUES (1, 'Afnemer', null, null);
INSERT INTO Kern.Rol (ID, Naam, DatAanvGel, DatEindeGel) VALUES (2, 'Bevoegdheidstoedeler', null, null);
INSERT INTO Kern.Rol (ID, Naam, DatAanvGel, DatEindeGel) VALUES (3, 'Bijhoudingsorgaan College', null, null);
INSERT INTO Kern.Rol (ID, Naam, DatAanvGel, DatEindeGel) VALUES (4, 'Bijhoudingsorgaan Minister', null, null);
INSERT INTO Kern.Rol (ID, Naam, DatAanvGel, DatEindeGel) VALUES (5, 'Stelselbeheerder', null, null);
INSERT INTO Kern.Rol (ID, Naam, DatAanvGel, DatEindeGel) VALUES (6, 'Toezichthouder', null, null);

INSERT INTO Kern.SrtDbObject (ID, Naam, DatAanvGel, DatEindeGel) VALUES (1, 'Tabel', 20120101, null);
INSERT INTO Kern.SrtDbObject (ID, Naam, DatAanvGel, DatEindeGel) VALUES (2, 'Kolom', 20120101, null);

INSERT INTO Kern.SrtBetr (ID, Code, Naam) VALUES (1, 'P', 'Partner');
INSERT INTO Kern.SrtBetr (ID, Code, Naam) VALUES (2, 'O', 'Ouder');
INSERT INTO Kern.SrtBetr (ID, Code, Naam) VALUES (3, 'K', 'Kind');

INSERT INTO Kern.SrtElement (ID, Naam) VALUES (1, 'Objecttype');
INSERT INTO Kern.SrtElement (ID, Naam) VALUES (2, 'Attribuut');

INSERT INTO Kern.SrtIndicatie (ID, Naam, IndMaterieleHistorieVanToepa) VALUES (1, 'Derde heeft gezag?', true);
INSERT INTO Kern.SrtIndicatie (ID, Naam, IndMaterieleHistorieVanToepa) VALUES (2, 'Onder curatele?', true);
INSERT INTO Kern.SrtIndicatie (ID, Naam, IndMaterieleHistorieVanToepa) VALUES (3, 'Verstrekkingsbeperking?', false);
INSERT INTO Kern.SrtIndicatie (ID, Naam, IndMaterieleHistorieVanToepa) VALUES (4, 'Geprivilegieerde?', false);
INSERT INTO Kern.SrtIndicatie (ID, Naam, IndMaterieleHistorieVanToepa) VALUES (5, 'Vastgesteld niet Nederlander?', true);
INSERT INTO Kern.SrtIndicatie (ID, Naam, IndMaterieleHistorieVanToepa) VALUES (6, 'Behandeld als Nederlander?', true);
INSERT INTO Kern.SrtIndicatie (ID, Naam, IndMaterieleHistorieVanToepa) VALUES (7, 'Belemmering verstrekking reisdocument?', false);
INSERT INTO Kern.SrtIndicatie (ID, Naam, IndMaterieleHistorieVanToepa) VALUES (8, 'Bezit buitenlands reisdocument?', true);
INSERT INTO Kern.SrtIndicatie (ID, Naam, IndMaterieleHistorieVanToepa) VALUES (9, 'Statenloos?', true);

INSERT INTO Kern.SrtMultiRealiteitRegel (ID, Naam, Oms) VALUES (1, 'Persoon', 'Multirealiteit op persoonsgegevens, zoals naam en geboortegegevens.');
INSERT INTO Kern.SrtMultiRealiteitRegel (ID, Naam, Oms) VALUES (2, 'Relatie', 'Multirealiteit op Relatie.');
INSERT INTO Kern.SrtMultiRealiteitRegel (ID, Naam, Oms) VALUES (3, 'Betrokkenheid', 'Multirealiteit op Betrokkenheid.');

INSERT INTO Kern.SrtPartij (ID, Naam, DatAanvGel, DatEindeGel) VALUES (1, 'Wetgever', null, null);
INSERT INTO Kern.SrtPartij (ID, Naam, DatAanvGel, DatEindeGel) VALUES (2, 'Vertegenwoordiger Regering', null, null);
INSERT INTO Kern.SrtPartij (ID, Naam, DatAanvGel, DatEindeGel) VALUES (3, 'Gemeente', null, null);
INSERT INTO Kern.SrtPartij (ID, Naam, DatAanvGel, DatEindeGel) VALUES (4, 'Overheidsorgaan', null, null);
INSERT INTO Kern.SrtPartij (ID, Naam, DatAanvGel, DatEindeGel) VALUES (5, 'Derde', null, null);
INSERT INTO Kern.SrtPartij (ID, Naam, DatAanvGel, DatEindeGel) VALUES (6, 'Samenwerkingsverband', null, null);

INSERT INTO Kern.SrtPers (ID, Code, Naam, DatAanvGel, DatEindeGel) VALUES (1, 'I', 'Ingeschrevene', null, null);
INSERT INTO Kern.SrtPers (ID, Code, Naam, DatAanvGel, DatEindeGel) VALUES (2, 'N', 'Niet ingeschrevene', null, null);
INSERT INTO Kern.SrtPers (ID, Code, Naam, DatAanvGel, DatEindeGel) VALUES (3, 'A', 'Alternatieve realiteit', null, null);

INSERT INTO Kern.SrtRelatie (ID, Code, Naam) VALUES (1, 'H', 'Huwelijk');
INSERT INTO Kern.SrtRelatie (ID, Code, Naam) VALUES (2, 'G', 'Geregistreerd partnerschap');
INSERT INTO Kern.SrtRelatie (ID, Code, Naam, Oms) VALUES (3, 'F', 'Familierechtelijke betrekking', 'Het betreft hier de familierechtelijke betrekking tussen Ouder(s) en Kind.');

INSERT INTO Kern.Verantwoordelijke (ID, Code, Naam) VALUES (1, 'C', 'College van burgemeester en wethouders');
INSERT INTO Kern.Verantwoordelijke (ID, Code, Naam) VALUES (2, 'M', 'Minister');

INSERT INTO Kern.WijzeGebruikGeslnaam (ID, Code, Naam, Oms) VALUES (1, 'E', 'Eigen', 'Eigen geslachtsnaam');
INSERT INTO Kern.WijzeGebruikGeslnaam (ID, Code, Naam, Oms) VALUES (2, 'P', 'Partner', 'Geslachtsnaam echtgenoot/geregistreerd partner');
INSERT INTO Kern.WijzeGebruikGeslnaam (ID, Code, Naam, Oms) VALUES (3, 'V', 'Partner, eigen', 'Geslachtsnaam echtgenoot/geregistreerd partner voor eigen geslachtsnaam');
INSERT INTO Kern.WijzeGebruikGeslnaam (ID, Code, Naam, Oms) VALUES (4, 'N', 'Eigen, partner', 'Geslachtsnaam echtgenoot/geregistreerd partner na eigen geslachtsnaam');

INSERT INTO Kern.SrtActie (ID, Naam, CategorieSrtActie) values (1, 'Conversie GBA', 1);
INSERT INTO Kern.SrtActie (ID, Naam, CategorieSrtActie) VALUES (2, 'Inschrijving Geboorte', 1);
INSERT INTO Kern.SrtActie (ID, Naam, CategorieSrtActie) VALUES (3, 'Verhuizing', 3);
INSERT INTO Kern.SrtActie (ID, Naam, CategorieSrtActie) VALUES (4, 'Registratie Erkenning', 2);
INSERT INTO Kern.SrtActie (ID, Naam, CategorieSrtActie) VALUES (5, 'Registratie Huwelijk', 4);
INSERT INTO Kern.SrtActie (ID, Naam, CategorieSrtActie) VALUES (6, 'Wijziging Geslachtsnaamcomponent', 2);
INSERT INTO Kern.SrtActie (ID, Naam, CategorieSrtActie) VALUES (7, 'Wijziging Naamgebruik', 4);
INSERT INTO Kern.SrtActie (ID, Naam, CategorieSrtActie) VALUES (8, 'Correctie Adres Binnen NL', 3);

