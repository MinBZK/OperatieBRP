--------------------------------------------------------------------------------
-- HSQLDB SQL-DDL bestand voor Basisregistratie Personen (BRP)
--------------------------------------------------------------------------------


-- Stamtabellen ----------------------------------------------------------------
INSERT INTO Kern.AangAdresh (ID, Code, Naam) VALUES (1, 'G', 'Gezaghouder');
INSERT INTO Kern.AangAdresh (ID, Code, Naam) VALUES (2, 'H', 'Hoofd instelling');
INSERT INTO Kern.AangAdresh (ID, Code, Naam) VALUES (3, 'I', 'Ingeschrevene');
INSERT INTO Kern.AangAdresh (ID, Code, Naam) VALUES (4, 'K', 'Meerderjarig inwonend kind voor ouder');
INSERT INTO Kern.AangAdresh (ID, Code, Naam) VALUES (5, 'M', 'Meerderjarig gemachtigde');
INSERT INTO Kern.AangAdresh (ID, Code, Naam) VALUES (6, 'O', 'Inwonend ouder voor meerderjarig kind');
INSERT INTO Kern.AangAdresh (ID, Code, Naam) VALUES (7, 'P', 'Echtgenoot/geregistreerd partner');

INSERT INTO Kern.AdellijkeTitel (ID, Code, NaamMannelijk, NaamVrouwelijk) VALUES (1, 'B', 'baron', 'barones');
INSERT INTO Kern.AdellijkeTitel (ID, Code, NaamMannelijk, NaamVrouwelijk) VALUES (2, 'G', 'graaf', 'gravin');
INSERT INTO Kern.AdellijkeTitel (ID, Code, NaamMannelijk, NaamVrouwelijk) VALUES (3, 'H', 'hertog', 'hertogin');
INSERT INTO Kern.AdellijkeTitel (ID, Code, NaamMannelijk, NaamVrouwelijk) VALUES (4, 'M', 'markies', 'markiezin');
INSERT INTO Kern.AdellijkeTitel (ID, Code, NaamMannelijk, NaamVrouwelijk) VALUES (5, 'P', 'prins', 'prinses');
INSERT INTO Kern.AdellijkeTitel (ID, Code, NaamMannelijk, NaamVrouwelijk) VALUES (6, 'R', 'ridder', 'ridder');

INSERT INTO Kern.FunctieAdres (ID, Code, Naam) VALUES (1, 'V', 'Verblijfsadres');
INSERT INTO Kern.FunctieAdres (ID, Code, Naam) VALUES (2, 'B', 'Briefadres');

INSERT INTO Kern.Geslachtsaand (ID, Code, Naam, Oms) VALUES (1, 'M', 'Man', '');
INSERT INTO Kern.Geslachtsaand (ID, Code, Naam, Oms) VALUES (2, 'V', 'Vrouw', '');
INSERT INTO Kern.Geslachtsaand (ID, Code, Naam, Oms) VALUES (3, 'O', 'Onbekend', '');

INSERT INTO Kern.Predikaat (ID, Code, NaamMannelijk, NaamVrouwelijk) VALUES (1, 'K', 'Zijne Koninklijke Hoogheid', 'Hare Koninklijke Hoogheid');
INSERT INTO Kern.Predikaat (ID, Code, NaamMannelijk, NaamVrouwelijk) VALUES (2, 'H', 'Zijne Hoogheid', 'Hare Hoogheid');
INSERT INTO Kern.Predikaat (ID, Code, NaamMannelijk, NaamVrouwelijk) VALUES (3, 'J', 'jonkheer', 'jonkvrouw');

INSERT INTO Kern.RdnOpschorting (ID, Code, Naam) VALUES (1, 'O', 'Overlijden');
INSERT INTO Kern.RdnOpschorting (ID, Code, Naam) VALUES (2, 'M', 'Ministerieel besluit');
INSERT INTO Kern.RdnOpschorting (ID, Code, Naam) VALUES (3, 'F', 'Fout');
INSERT INTO Kern.RdnOpschorting (ID, Code, Naam) VALUES (4, '?', 'Onbekend');

INSERT INTO Kern.RdnWijzAdres (ID, Code, Naam) VALUES (1, 'P', 'Aangifte door persoon');
INSERT INTO Kern.RdnWijzAdres (ID, Code, Naam) VALUES (2, 'A', 'Ambtshalve');
INSERT INTO Kern.RdnWijzAdres (ID, Code, Naam) VALUES (3, 'M', 'Ministerieel besluit');
INSERT INTO Kern.RdnWijzAdres (ID, Code, Naam) VALUES (4, 'B', 'Technische wijzigingen i.v.m. BAG');
INSERT INTO Kern.RdnWijzAdres (ID, Code, Naam) VALUES (5, 'I', 'Infrastructurele wijziging');

INSERT INTO Kern.SrtBetr (ID, Code, Naam) VALUES (1, 'P', 'Partner');
INSERT INTO Kern.SrtBetr (ID, Code, Naam) VALUES (2, 'O', 'Ouder');
INSERT INTO Kern.SrtBetr (ID, Code, Naam) VALUES (3, 'K', 'Kind');
INSERT INTO Kern.SrtBetr (ID, Code, Naam) VALUES (4, 'E', 'Erkenner');
INSERT INTO Kern.SrtBetr (ID, Code, Naam) VALUES (5, 'I', 'Instemmer');
INSERT INTO Kern.SrtBetr (ID, Code, Naam) VALUES (6, 'S', 'Ouder-in-spe');
INSERT INTO Kern.SrtBetr (ID, Code, Naam) VALUES (7, 'X', 'Ontkende');
INSERT INTO Kern.SrtBetr (ID, Code, Naam) VALUES (8, 'N', 'Naamgever');

INSERT INTO Kern.SrtIndicatie (ID, Naam, IndMaterieleHistorieVanToep) VALUES (1, 'Derde heeft gezag?', 'J');
INSERT INTO Kern.SrtIndicatie (ID, Naam, IndMaterieleHistorieVanToep) VALUES (2, 'Onder curatele?', 'J');
INSERT INTO Kern.SrtIndicatie (ID, Naam, IndMaterieleHistorieVanToep) VALUES (3, 'Verstrekkingsbeperking?', 'N');
INSERT INTO Kern.SrtIndicatie (ID, Naam, IndMaterieleHistorieVanToep) VALUES (4, 'Geprivilegieerde?', 'N');
INSERT INTO Kern.SrtIndicatie (ID, Naam, IndMaterieleHistorieVanToep) VALUES (5, 'Vastgesteld niet Nederlander?', 'J');
INSERT INTO Kern.SrtIndicatie (ID, Naam, IndMaterieleHistorieVanToep) VALUES (6, 'Behandeld als Nederlander?', 'J');
INSERT INTO Kern.SrtIndicatie (ID, Naam, IndMaterieleHistorieVanToep) VALUES (7, 'Belemmering verstrekking reisdocument?', 'N');
INSERT INTO Kern.SrtIndicatie (ID, Naam, IndMaterieleHistorieVanToep) VALUES (8, 'Bezit buitenlands reisdocument?', 'N');

INSERT INTO Kern.SrtPers (ID, Code, Naam, Oms) VALUES (1, 'I', 'Ingeschrevene', '');
INSERT INTO Kern.SrtPers (ID, Code, Naam, Oms) VALUES (2, 'N', 'Niet ingeschrevene', '');
INSERT INTO Kern.SrtPers (ID, Code, Naam, Oms) VALUES (3, 'A', 'Alternatieve realiteit', '');

INSERT INTO Kern.SrtRelatie (ID, Code, Naam) VALUES (1, 'H', 'Huwelijk');
INSERT INTO Kern.SrtRelatie (ID, Code, Naam) VALUES (2, 'G', 'Geregistreerd partnerschap');
INSERT INTO Kern.SrtRelatie (ID, Code, Naam) VALUES (3, 'F', 'Familie rechtelijke betrekking');
INSERT INTO Kern.SrtRelatie (ID, Code, Naam) VALUES (4, 'E', 'Erkenning ongeboren vrucht');
INSERT INTO Kern.SrtRelatie (ID, Code, Naam) VALUES (5, 'O', 'Ontkenning ouderschap ongeboren vrucht');
INSERT INTO Kern.SrtRelatie (ID, Code, Naam) VALUES (6, 'N', 'Naamskeuze ongeboren vrucht');

INSERT INTO Kern.Verantwoordelijke (ID, Code, Naam) VALUES (1, 'C', 'College van burgemeester en wethouders');
INSERT INTO Kern.Verantwoordelijke (ID, Code, Naam) VALUES (2, 'M', 'Minister');

INSERT INTO Kern.WijzeGebruikGeslnaam (ID, Code, Naam, Oms) VALUES (1, 'E', 'Eigen', 'Eigen geslachtsnaam');
INSERT INTO Kern.WijzeGebruikGeslnaam (ID, Code, Naam, Oms) VALUES (2, 'P', 'Partner', 'Geslachtsnaam echtgenoot/geregistreerd partner');
INSERT INTO Kern.WijzeGebruikGeslnaam (ID, Code, Naam, Oms) VALUES (3, 'V', 'Partner, eigen', 'Geslachtsnaam echtgenoot/geregistreerd partner voor eigen geslachtsnaam');
INSERT INTO Kern.WijzeGebruikGeslnaam (ID, Code, Naam, Oms) VALUES (4, 'N', 'Eigen, partner', 'Geslachtsnaam echtgenoot/geregistreerd partner na eigen geslachtsnaam');

