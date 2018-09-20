
-- ------------------------------------------------------------------------
-- Begin van insertcode---------------------------------------------------
-- ------------------------------------------------------------------------
-- Gegenereerd door BRP Meta Register.
-- Gegenereerd op: Thursday 10 Nov 2011 14:45:19
-- ------------------------------------------------------------------------
-- ------------------------------------------------------------------------
-- ------------------------------------------------------------------------
-- ------------------------------------------------------------------------
INSERT INTO AutAut.Protocolleringsniveau (ID, Code, Naam, Oms) VALUES (1, '0', 'Geen beperkingen', 'Aan een burger desgevraagd inzage geven in de bij zijn persoonsgegevens gelegde afnemersindicaties.');
INSERT INTO AutAut.Protocolleringsniveau (ID, Code, Naam, Oms) VALUES (2, '1', 'Gevoelig', 'Aan een burger desgevraagd inzage geven in de bij zijn persoonsgegevens geregistreerde afnemersindicaties. Deze informatie is beperkt tot daartoe expliciet geautoriseerde gemeenteambtenaren.');
INSERT INTO AutAut.Protocolleringsniveau (ID, Code, Naam, Oms) VALUES (3, '2', 'Geheim', 'Aan een burger wordt GEEN inzage verstrekt over de in kader van deze Autorisatie geleverde gegevens. Toegang tot deze gegevens is beperkt tot gemeenteambtenaren die daartoe expliciet zijn gautoriseerd.');
 
INSERT INTO AutAut.SrtAutorisatiebesluit (ID, Naam, Oms, DatAanvGel, DatEindeGel) VALUES (1, 'Leveringsautorisatie', 'Een autorisatie ten behoeve van het leveren van gegevens aan Partijen in de rol van Afnemer.', 0, 99991231);
 
INSERT INTO Ber.SrtBer (ID, Naam) VALUES (1, 'OpvragenPersoonVraag');
INSERT INTO Ber.SrtBer (ID, Naam) VALUES (2, 'OpvragenPersoonAntwoord');
 
INSERT INTO Kern.AangAdresh (ID, Code, Naam, Oms) VALUES (1, 'G', 'Gezaghouder', 'De gezaghouder is de ouder, de voogd of de curator van de ingeschrevene.');
INSERT INTO Kern.AangAdresh (ID, Code, Naam, Oms) VALUES (2, 'H', 'Hoofd instelling', 'De hoofd van de instelling waar de persoon verblijft.');
INSERT INTO Kern.AangAdresh (ID, Code, Naam, Oms) VALUES (3, 'I', 'Ingeschrevene', 'De ingeschrevene zelf');
INSERT INTO Kern.AangAdresh (ID, Code, Naam, Oms) VALUES (4, 'K', 'Meerderjarig inwonend kind voor ouder', 'Een kind dat op hetzelfde adres woont als de ouder doet de adresaangifte.');
INSERT INTO Kern.AangAdresh (ID, Code, Naam, Oms) VALUES (5, 'M', 'Meerderjarig gemachtigde', 'Een door de ingeschrevenes gemachtigde.');
INSERT INTO Kern.AangAdresh (ID, Code, Naam, Oms) VALUES (6, 'O', 'Inwonend ouder voor meerderjarig kind', 'Een ouder die op hetzelfde adres woont doet aangifte van het adres van het kind.');
INSERT INTO Kern.AangAdresh (ID, Code, Naam, Oms) VALUES (7, 'P', 'Echtgenoot/geregistreerd partner', 'De inwonende echtgenoot/geregistreerd partner doet aangifte van het adres.');
 
INSERT INTO Kern.AdellijkeTitel (ID, Code, NaamMannelijk, NaamVrouwelijk) VALUES (1, 'B', 'baron', 'barones');
INSERT INTO Kern.AdellijkeTitel (ID, Code, NaamMannelijk, NaamVrouwelijk) VALUES (2, 'G', 'graaf', 'gravin');
INSERT INTO Kern.AdellijkeTitel (ID, Code, NaamMannelijk, NaamVrouwelijk) VALUES (3, 'H', 'hertog', 'hertogin');
INSERT INTO Kern.AdellijkeTitel (ID, Code, NaamMannelijk, NaamVrouwelijk) VALUES (4, 'M', 'markies', 'markiezin');
INSERT INTO Kern.AdellijkeTitel (ID, Code, NaamMannelijk, NaamVrouwelijk) VALUES (5, 'P', 'prins', 'prinses');
INSERT INTO Kern.AdellijkeTitel (ID, Code, NaamMannelijk, NaamVrouwelijk) VALUES (6, 'R', 'ridder', 'ridder');
 
INSERT INTO Kern.FunctieAdres (ID, Code, Naam) VALUES (1, 'W', 'Woonadres');
INSERT INTO Kern.FunctieAdres (ID, Code, Naam) VALUES (2, 'B', 'Briefadres');
 
INSERT INTO Kern.Geslachtsaand (ID, Code, Naam) VALUES (1, 'M', 'Man');
INSERT INTO Kern.Geslachtsaand (ID, Code, Naam) VALUES (2, 'V', 'Vrouw');
INSERT INTO Kern.Geslachtsaand (ID, Code, Naam) VALUES (3, 'O', 'Onbekend');
 
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
 
INSERT INTO Kern.Rol (ID, Naam) VALUES (4, 'Bijhoudingsorgaan Minister');
INSERT INTO Kern.Rol (ID, Naam) VALUES (1, 'Afnemer');
INSERT INTO Kern.Rol (ID, Naam) VALUES (2, 'Bevoegdheidstoedeler');
INSERT INTO Kern.Rol (ID, Naam) VALUES (3, 'Bijhoudingsorgaan College');
INSERT INTO Kern.Rol (ID, Naam) VALUES (5, 'Stelselbeheerder');
INSERT INTO Kern.Rol (ID, Naam) VALUES (6, 'Toezichthouder');
 
INSERT INTO Kern.SrtBetr (ID, Code, Naam) VALUES (1, 'P', 'Partner');
INSERT INTO Kern.SrtBetr (ID, Code, Naam) VALUES (2, 'O', 'Ouder');
INSERT INTO Kern.SrtBetr (ID, Code, Naam) VALUES (3, 'K', 'Kind');
 
INSERT INTO Kern.SrtIndicatie (ID, Naam, IndMaterieleHistorieVanToep) VALUES (1, 'Derde heeft gezag?', true);
INSERT INTO Kern.SrtIndicatie (ID, Naam, IndMaterieleHistorieVanToep) VALUES (2, 'Onder curatele?', true);
INSERT INTO Kern.SrtIndicatie (ID, Naam, IndMaterieleHistorieVanToep) VALUES (3, 'Verstrekkingsbeperking?', false);
INSERT INTO Kern.SrtIndicatie (ID, Naam, IndMaterieleHistorieVanToep) VALUES (4, 'Geprivilegieerde?', false);
INSERT INTO Kern.SrtIndicatie (ID, Naam, IndMaterieleHistorieVanToep) VALUES (5, 'Vastgesteld niet Nederlander?', true);
INSERT INTO Kern.SrtIndicatie (ID, Naam, IndMaterieleHistorieVanToep) VALUES (6, 'Behandeld als Nederlander?', true);
INSERT INTO Kern.SrtIndicatie (ID, Naam, IndMaterieleHistorieVanToep) VALUES (7, 'Belemmering verstrekking reisdocument?', false);
INSERT INTO Kern.SrtIndicatie (ID, Naam, IndMaterieleHistorieVanToep) VALUES (8, 'Bezit buitenlands reisdocument?', false);
INSERT INTO Kern.SrtIndicatie (ID, Naam, IndMaterieleHistorieVanToep) VALUES (9, 'Statenloos?', true);
 
INSERT INTO Kern.SrtPartij (ID, Naam) VALUES (4, 'Derde');
INSERT INTO Kern.SrtPartij (ID, Naam) VALUES (1, 'Gemeente');
INSERT INTO Kern.SrtPartij (ID, Naam) VALUES (2, 'Minister');
INSERT INTO Kern.SrtPartij (ID, Naam) VALUES (3, 'Overheidsorgaan');
INSERT INTO Kern.SrtPartij (ID, Naam) VALUES (5, 'Samenwerkingsverband');
 
INSERT INTO Kern.SrtPers (ID, Code, Naam) VALUES (1, 'I', 'Ingeschrevene');
INSERT INTO Kern.SrtPers (ID, Code, Naam) VALUES (2, 'N', 'Niet ingeschrevene');
INSERT INTO Kern.SrtPers (ID, Code, Naam) VALUES (3, 'A', 'Alternatieve realiteit');
 
INSERT INTO Kern.SrtRelatie (ID, Code, Naam) VALUES (1, 'H', 'Huwelijk');
INSERT INTO Kern.SrtRelatie (ID, Code, Naam) VALUES (2, 'G', 'Geregistreerd partnerschap');
INSERT INTO Kern.SrtRelatie (ID, Code, Naam, Oms) VALUES (3, 'F', 'Familierechtelijke betrekking', 'Het betreft hier de familierechtelijke betrekking tussen Ouder(s) en Kind.');
 
INSERT INTO Kern.Verantwoordelijke (ID, Code, Naam) VALUES (1, 'C', 'College van burgemeester en wethouders');
INSERT INTO Kern.Verantwoordelijke (ID, Code, Naam) VALUES (2, 'M', 'Minister');
 
INSERT INTO Kern.WijzeGebruikGeslnaam (ID, Code, Naam, Oms) VALUES (1, 'E', 'Eigen', 'Eigen geslachtsnaam');
INSERT INTO Kern.WijzeGebruikGeslnaam (ID, Code, Naam, Oms) VALUES (2, 'P', 'Partner', 'Geslachtsnaam echtgenoot/geregistreerd partner');
INSERT INTO Kern.WijzeGebruikGeslnaam (ID, Code, Naam, Oms) VALUES (3, 'V', 'Partner, eigen', 'Geslachtsnaam echtgenoot/geregistreerd partner voor eigen geslachtsnaam');
INSERT INTO Kern.WijzeGebruikGeslnaam (ID, Code, Naam, Oms) VALUES (4, 'N', 'Eigen, partner', 'Geslachtsnaam echtgenoot/geregistreerd partner na eigen geslachtsnaam');
 
INSERT INTO Lev.SrtAbonnement (ID, Naam) VALUES (1, 'Levering');
 
INSERT INTO Lev.SrtLev (ID, Naam) VALUES (1, 'Bevraging');
INSERT INTO Lev.SrtLev (ID, Naam) VALUES (2, 'Mutatie');
INSERT INTO Lev.SrtLev (ID, Naam) VALUES (3, 'Selectie');
 
