--------------------------------------------------------------------------------
-- PostgreSQL SQL-DDL bestand voor Basisregistratie Personen (BRP)            --
-- PostgreSQL - Kern Dynamische Stamgegevens                                  --
--------------------------------------------------------------------------------
-- 
-- Versie: Release 59
-- Laatste wijziging: donderdag 10 aug 2017 09:46:45
-- 
--------------------------------------------------------------------------------

--------------------------------------------------------------------------------
-- Stamgegeven: Aanduiding inhouding/vermissing reisdocument
--------------------------------------------------------------------------------
INSERT INTO Kern.AandInhingVermissingReisdoc (ID, Code, Naam) VALUES
   (1, 'I', 'Ingehouden, ingeleverd'),
   (2, 'V', 'Vermist'),
   (3, '?', 'Onbekend'),
   (4, 'R', 'Van rechtswege vervallen');

--------------------------------------------------------------------------------
-- Stamgegeven: Aangever
--------------------------------------------------------------------------------
INSERT INTO Kern.Aang (ID, Code, Naam, Oms) VALUES
   (1, 'G', 'Gezaghouder', 'De gezaghouder is de ouder, de voogd of de curator van de ingeschrevene.'),
   (2, 'H', 'Hoofd instelling', 'De hoofd van de instelling waar de persoon verblijft.'),
   (3, 'I', 'Ingeschrevene', 'De ingeschrevene zelf'),
   (4, 'K', 'Meerderjarig inwonend kind voor ouder', 'Een kind dat op hetzelfde adres woont als de ouder doet de adresaangifte.'),
   (5, 'M', 'Meerderjarig gemachtigde', 'Een door de ingeschrevene gemachtigde.'),
   (6, 'O', 'Inwonend ouder voor meerderjarig kind', 'Een ouder die op hetzelfde adres woont doet aangifte van het adres van het kind.'),
   (7, 'P', 'Echtgenoot/geregistreerd partner', 'De inwonende echtgenoot/geregistreerd partner doet aangifte van het adres.'),
   (8, 'V', 'Verzorger', 'Aangifte door verzorger voor minderjarig kind');

--------------------------------------------------------------------------------
-- Stamgegeven: Autoriteittype van afgifte reisdocument
--------------------------------------------------------------------------------
INSERT INTO Kern.AuttypeVanAfgifteReisdoc (ID, Code, Naam, DatAanvGel, DatEindeGel) VALUES
   (1, 'B', 'Burgemeester', NULL, NULL),
   (2, 'BI', 'Minister van Binnenlandse zaken', 19920401, NULL),
   (3, 'BZ', 'Minister van Buitenlandse zaken', NULL, 19920401),
   (4, 'BU', 'Minister van Buitenlandse zaken Den Haag', 19920401, NULL),
   (5, 'BW', 'College van Burgemeester en wethouders', 19930101, 19950101),
   (6, 'C', 'Consulaire post', NULL, 20140309),
   (7, 'G', 'Gouverneur', NULL, 20101010),
   (8, 'GH', 'Gezaghebber', NULL, NULL),
   (9, 'GN', 'Gouverneur Nederlands gebied', 20101010, NULL),
   (10, 'KM', 'Commandant Koninklijke Marechaussee', NULL, NULL),
   (11, 'P', 'Provincie', NULL, 19940101),
   (12, 'PK', 'Persoonskaart', NULL, NULL);

--------------------------------------------------------------------------------
-- Stamgegeven: Reden einde relatie
--------------------------------------------------------------------------------
INSERT INTO Kern.RdnEindeRelatie (ID, Code, Oms) VALUES
   (1, '?', 'Onbekend'),
   (2, 'A', 'Vermissing van een persoon gevolgd door andere verbintenis'),
   (3, 'N', 'Nietigverklaring'),
   (4, 'O', 'Overlijden partner'),
   (5, 'R', 'Rechtsvermoeden van overlijden partner'),
   (6, 'S', 'Echtscheiding, ontbinding of eindigen conform Nederlands recht'),
   (7, 'V', 'Naar vreemd recht anders beëindigd'),
   (8, 'Z', 'Omzetting');

--------------------------------------------------------------------------------
-- Stamgegeven: Reden wijziging verblijf
--------------------------------------------------------------------------------
INSERT INTO Kern.RdnWijzVerblijf (ID, Code, Naam) VALUES
   (1, 'P', 'Aangifte door persoon'),
   (2, 'A', 'Ambtshalve'),
   (3, 'M', 'Ministerieel besluit'),
   (4, 'B', 'Technische wijzigingen i.v.m. BAG'),
   (5, 'I', 'Infrastructurele wijziging'),
   (6, '?', 'Onbekend');

--------------------------------------------------------------------------------
-- Stamgegeven: Soort document
--------------------------------------------------------------------------------
INSERT INTO Kern.SrtDoc (ID, Naam, Oms, Registersrt) VALUES
   (1, 'Geboorteakte', 'Geboorteakte, opgenomen in de registers van de burgerlijke stand in Nederland, zoals bedoeld in artikel 2.8 lid 1 sub a, dan wel artikel 2.8 lid 2 sub a', 1),
   (2, 'Overlijdensakte', 'Akte van overlijden, opgenomen in de registers van de burgerlijke stand in Nederland, zoals bedoeld in artikel 2.8 lid 1 sub a, dan wel artikel 2.8 lid 2 sub a', 2),
   (3, 'Huwelijksakte', 'Huwelijksakte, opgenomen in de registers van de burgerlijke stand in Nederland, zoals bedoeld in artikel 2.8 lid 1 sub a, dan wel artikel 2.8 lid 2 sub a', 3),
   (4, 'Akte van echtscheiding', 'Akte van echtscheiding van in buitenland voltrokken huwelijk, opgenomen in de registers van de burgerlijke stand in Nederland, zoals bedoeld in artikel 2.8 lid 1 sub a', 3),
   (5, 'Akte van partnerschapsregistratie', 'Akte van registratie van een partnerschap, opgenomen in de registers van de burgerlijke stand in Nederland, zoals bedoeld in artikel 2.8 lid 1 sub a, dan wel artikel 2.8 lid 2 sub a', 5),
   (6, 'Akte van beëindiging partnerschapsregistratie', 'Akte van beëindiging van het in het buitenland aangegaan geregistreerd partnerschap, opgenomen in de registers van de burgerlijke stand in Nederland, zoals bedoeld in artikel 2.8 lid 1 sub a', 5),
   (7, 'Persoonslijst gerelateerde', 'Het geheel van gegevens over een persoon in de basisregistratie die aan de ingeschrevene gerelateerd is, zoals bedoeld in artikel 1.1 sub c', NULL),
   (8, 'Akte van ontkenning ouderschap', 'Akte van ontkenning van het ouderschap opgemaakt door de ambtenaar van de burgerlijke stand, zoals bedoeld in artikel 2.8 lid 1 sub b', 1),
   (9, 'Akte van erkenning', 'Akte van erkenning opgemaakt door de ambtenaar van de burgerlijke stand, zoals bedoeld in artikel 2.8 lid 1 sub b', 1),
   (10, 'Akte van naamskeuze', 'Akte van naamskeuze opgemaakt door de ambtenaar van de burgerlijke stand, zoals bedoeld in artikel 2.8 lid 1 sub b', NULL),
   (11, 'Koninklijk besluit', 'Koninklijk besluit over in Nederland voorgedane feiten, zoals bedoeld in artikel 2.8 lid 1 sub b', NULL),
   (12, 'Besluit overig', 'Besluit over in Nederland voorgedane feiten, zoals bedoeld in artikel 2.8 lid 1 sub b, niet zijnde een koninklijk besluit.', NULL),
   (13, 'Nederlandse rechterlijke uitspraak', 'Een in kracht van gewijsde gegane rechterlijke uitspraak over feiten die zich hebben voorgedaan in Nederland en/of waarvan de rechterlijke uitspraak gedaan is in Nederland, zoals bedoeld in artikel 2.8 lid 1 sub b en artikel 2.8 lid 2 sub b', NULL),
   (14, 'Notariële akte', 'Notariële akte zoals bedoeld in artikel 2.8 lid 1 sub b', NULL),
   (15, 'Consulaire akte van een rechtsfeit in Nederland', 'Een door een in Nederland geaccrediteerde consulaire ambtenaar van een ander land bevoegd opgemaakte akte die ten doel heeft een in Nederland voorgedaan feit te bewijzen, zoals bedoeld in artikel 2.8 lid 3', NULL),
   (16, 'Buitenlandse akte', 'Een buiten Nederland overeenkomstig de plaatselijke voorschriften door een bevoegde instantie opgemaakte akte die ten doel heeft tot bewijs te dienen van het desbetreffende feit, zoals bedoeld in artikel 2.8 lid 2 sub c', NULL),
   (17, 'Buitenlandse rechterlijke uitspraak', 'Een uitspraak door een niet-Nederlandse rechter over buiten Nederland voorgedane feiten, zoals bedoeld in artikel 2.8 lid 2 sub c', NULL),
   (18, 'Beëdigde verklaring', 'Een beëdigde verklaring volgens artikel 1:45 Burgerlijk wetboek, zoals bedoeld in artikel 2.8 lid 2 sub c', NULL),
   (19, 'Buitenlands geschrift', 'Een geschrift - inclusief PIVA PL - dat in het buitenland overeenkomstig de plaatselijke voorschriften is opgemaakt door een bevoegde instantie, waarin het zich desbetreffende feit is vermeld, zoals bedoeld in artikel 2.8 lid 2 sub d', NULL),
   (20, 'Verklaring onder eed of belofte', 'Een verklaring onder eed of belofte over in een ander land voorgedane feiten gedaan ten overstaan van een door College B&W aangestelde ambtenaar, zoals bedoeld in artikel 2.8 lid 2 sub e', NULL),
   (21, 'Mededeling Minister inzake verblijfsrecht', 'Een mededeling zoals bedoeld in artikel 2.16', NULL),
   (22, 'Mededeling Minister inzake nationaliteit en geboorte', 'Een mededeling zoals bedoeld in artikel 2.17', NULL),
   (23, 'Mededeling Curateleregister', 'Een mededeling uit het curateleregister, zoals bedoeld in artikel 2.13 lid 1', NULL),
   (24, 'Mededeling Gezagsregister', 'Een mededeling uit het gezagsregister, zoals bedoeld in artikel 2.13 lid 2', NULL),
   (25, 'Verklaring of kennisgeving naamgebruik', 'Een verklaring of kennisgeving rechterlijke uitspraak inzake naamgebruik, zoals bedoeld in artikel 2.25', NULL),
   (26, 'Akte Nederlandse nationaliteit', 'Akte volgens artikel 22 lid 1 onder b, c en d of lid 2 RWN, zoals bedoeld in artikel 2.14 lid 2', NULL),
   (27, 'Rechterlijke uitspraak Nederlandse nationaliteit', 'Uitspraak rechter zoals bedoeld in artikel 2.14, lid 4 en 5', NULL),
   (28, 'Beschikking/uitspraak vreemde nationaliteit', 'Beschikking of uitspraak zoals bedoeld in artikel 2.15, lid 1', NULL),
   (29, 'Geschrift vreemde nationaliteit', 'Geschrift zoals bedoeld in artikel 2.15, lid 2', NULL),
   (30, 'Verzoek verstrekkingsbeperking', 'Een schriftelijk verzoek inzake verstrekkingsbeperking, zoals bedoeld in artikel 2.59', NULL),
   (33, 'Optieverklaring verkrijging Nederlanderschap', 'Een bevestiging van de verklaring tot verkrijging van het Nederlanderschap, zoals bedoeld in artikel 2.14 lid 3', NULL),
   (34, 'Koninklijk besluit naturalisatie', 'Het uittreksel van het besluit tot verlening van het Nederlanderschap, zoals bedoeld in artikel 2.14 lid 3', NULL),
   (35, 'Verklaring van afstand Nederlanderschap', 'De door de betrokkene afgelegde verklaring van afstand van de Nederlandse nationaliteit, zoals bedoeld in artikel 2.14 lid 3', NULL),
   (36, 'Verklaring van verbondenheid', 'Het bewijs van het hebben afgelegd van een verklaring van verbondenheid op een naturalisatieceremonie', NULL),
   (37, 'Buitenlands reisdocument of identiteitsbewijs', 'Document dat de houder toestemming verleent tot het betreden van en reizen door een bepaald land of gebied, zoals bedoeld in artikel 2.8 lid 2 sub d en artikel 2.15 lid 2', NULL),
   (38, 'Nederlands reisdocument', 'Een document zoals bedoeld in artikel 2 Paspoortwet of artikel 2.8 lid 2 sub d Wet BRP', NULL),
   (39, 'Overig', 'Een document, niet behorende tot één van de expliciet benoemde documentsoorten', NULL),
   (40, 'Historie conversie', 'Akten en documenten geconverteerd vanuit de GBA', NULL),
   (41, 'Verzoek deelname / einde deelname Europese Verkiezingen', 'Het verzoek om de kiesgerechtigheid te registreren of beëindigen, zoals bedoeld in de Kierswet, afdeling V', NULL),
   (42, 'Mededeling uitsluiting kiesrecht', 'De mededeling over de uitsluiting van het kiesrecht, zoals bedoeld in artikel B5 lid 2 Kieswet', NULL),
   (43, 'Mededeling uitsluiting Europees kiesrecht', 'De mededeling over de uitsluiting van het Europees kiesrecht, zoals bedoeld in de Kieswet, afdeling V', NULL),
   (44, 'Mededeling Register paspoortsignalering', 'Een mededeling zoals bedoeld in artikel 25 lid 4 Paspoortwet', NULL),
   (45, 'Kennisgeving Nederlands reisdocument', 'Een kennisgeving als bedoeld in artikel 75 lid 1 Paspoortuitvoeringsregeling Nederland 2001', NULL),
   (46, 'Verklaring vermissing reisdocument', 'De verklaring omtrent de vermissing van een reisdocument, zoals bedoeld in artikel 31 lid 1 van de Paspoortwet', NULL),
   (47, 'Besluit wijziging BSN', 'Toekenning of wijziging van een burgerservicenummer, zoals bedoeld in artikel 2.24 lid 1', NULL),
   (48, 'Besluit wijziging A-nummer', 'Toekenning of wijziging van een administratienummer, zoals bedoeld in artikel 4.9 Wet BRP', NULL),
   (49, 'Mededeling bijzondere verblijfsrechtelijke positie', 'Een mededeling zoals bedoeld in artikel 25, lid 1 Besluit Brp', NULL);

--------------------------------------------------------------------------------
-- Stamgegeven: Soort vrij bericht
--------------------------------------------------------------------------------
INSERT INTO Kern.SrtVrijBer (ID, Naam, DatAanvGel) VALUES
   (1, 'Beheermelding', NULL),
   (2, 'Beheerverzoek', NULL),
   (3, 'Correctie', NULL),
   (4, 'Protocollering', NULL),
   (5, 'Onderzoekdossier', NULL),
   (6, 'Brondocument', NULL),
   (7, 'RPS', NULL),
   (8, 'RNI', NULL),
   (9, 'PIVA', NULL),
   (10, 'GBA partij', NULL),
   (11, 'Overig', NULL);

--------------------------------------------------------------------------------
-- Stamgegeven: Soort bericht \ Element
--------------------------------------------------------------------------------
INSERT INTO Kern.SrtBerElement (ID, SrtBer, Element) VALUES
  (1, 74, 2064),
  (2, 74, 3909),
  (3, 74, 3344),
  (4, 74, 3557),
  (5, 74, 3514),
  (6, 74, 3554),
  (7, 74, 3664),
  (8, 74, 3515),
  (9, 74, 3487),
  (10, 74, 2068),
  (11, 74, 6063),
  (12, 74, 26045),
  (13, 74, 26049),
  (14, 74, 2072),
  (15, 74, 3774),
  (16, 74, 2075),
  (17, 74, 39400),
  (18, 74, 39411),
  (19, 74, 22211),
  (20, 74, 39423),
  (21, 74, 12692),
  (22, 74, 13848),
  (23, 74, 12774),
  (24, 74, 14324),
  (25, 74, 12739),
  (26, 74, 12751),
  (27, 74, 12921),
  (28, 74, 12923),
  (29, 74, 12925),
  (30, 74, 12826),
  (31, 74, 12828),
  (32, 74, 12827),
  (33, 74, 12841),
  (34, 74, 12843),
  (35, 74, 13074),
  (36, 74, 13076),
  (37, 74, 13078),
  (38, 74, 12992),
  (39, 74, 12994),
  (40, 74, 12996),
  (41, 76, 2064),
  (42, 76, 3909),
  (43, 76, 3344),
  (44, 76, 3557),
  (45, 76, 3514),
  (46, 76, 3554),
  (47, 76, 3664),
  (48, 76, 3515),
  (49, 76, 3487),
  (50, 76, 2068),
  (51, 76, 6063),
  (52, 76, 3790),
  (53, 76, 2072),
  (54, 76, 3774),
  (55, 76, 2075),
  (56, 152, 2064),
  (57, 152, 3909),
  (58, 152, 3344),
  (59, 152, 3557),
  (60, 152, 3514),
  (61, 152, 3554),
  (62, 152, 3664),
  (63, 152, 3515),
  (64, 152, 3487),
  (65, 152, 3790),
  (66, 152, 2068),
  (67, 152, 6063),
  (68, 152, 2072),
  (69, 152, 3774),
  (70, 152, 2075);

--------------------------------------------------------------------------------
-- Stamgegeven: Soort actie \ Brongebruikt
--------------------------------------------------------------------------------
INSERT INTO Kern.SrtActieBrongebruik (SrtActie, SrtAdmHnd, SrtDoc) VALUES
  (32, 18, 3),
  (32, 18, 15),
  (32, 22, 3),
  (32, 22, 13),
  (32, 22, 16),
  (32, 22, 17),
  (32, 22, 19),
  (32, 22, 20),
  (32, 22, 39),
  (61, 20, 5),
  (61, 46, 5),
  (61, 46, 13),
  (61, 46, 16),
  (61, 46, 17),
  (61, 46, 19),
  (61, 46, 20),
  (33, 19, 2),
  (33, 19, 3),
  (33, 19, 4),
  (33, 23, 3),
  (33, 23, 13),
  (33, 23, 16),
  (33, 23, 17),
  (33, 23, 19),
  (33, 23, 20),
  (33, 23, 39),
  (33, 24, 3),
  (33, 24, 13),
  (62, 21, 2),
  (62, 21, 5),
  (62, 21, 6),
  (62, 47, 5),
  (62, 47, 6),
  (62, 47, 13),
  (62, 47, 16),
  (62, 47, 17),
  (62, 47, 19),
  (62, 47, 20),
  (62, 47, 39),
  (62, 26, 3),
  (62, 25, 5),
  (62, 25, 6),
  (62, 25, 13),
  (4, 18, 25),
  (4, 20, 25),
  (4, 22, 25),
  (4, 22, 39),
  (4, 46, 25),
  (4, 46, 39),
  (4, 19, 13),
  (4, 19, 25),
  (4, 21, 13),
  (4, 21, 25),
  (4, 23, 13),
  (4, 23, 25),
  (4, 23, 39),
  (4, 47, 13),
  (4, 47, 25),
  (4, 47, 39),
  (4, 26, 25),
  (4, 24, 13),
  (4, 24, 25),
  (4, 25, 13),
  (4, 25, 25),
  (30, 18, 3),
  (30, 18, 15),
  (30, 20, 5),
  (30, 22, 3),
  (30, 22, 16),
  (30, 22, 17),
  (30, 22, 19),
  (30, 22, 39),
  (30, 46, 5),
  (30, 46, 16),
  (30, 46, 17),
  (30, 46, 19),
  (30, 46, 39),
  (30, 19, 3),
  (30, 19, 4),
  (30, 21, 5),
  (30, 21, 6),
  (30, 23, 3),
  (30, 23, 13),
  (30, 23, 16),
  (30, 23, 17),
  (30, 23, 19),
  (30, 23, 39),
  (30, 47, 5),
  (30, 47, 6),
  (30, 47, 16),
  (30, 47, 17),
  (30, 47, 19),
  (30, 47, 39),
  (30, 26, 3),
  (30, 24, 3),
  (30, 24, 13),
  (30, 25, 5),
  (30, 25, 13),
  (13, 29, 30),
  (13, 30, 30),
  (13, 32, 30),
  (55, 40, 21),
  (55, 40, 39),
  (43, 63, 39),
  (43, 63, 49),
  (3, 63, 39),
  (3, 63, 49),
  (1, 1, 1),
  (1, 3, 1),
  (1, 162, 1),
  (67, 4, 9),
  (67, 4, 14),
  (67, 4, 16),
  (67, 4, 19),
  (67, 3, 1),
  (5, 4, 16),
  (5, 4, 19),
  (5, 4, 28),
  (5, 4, 29),
  (23, 4, 9),
  (23, 4, 14),
  (23, 4, 16),
  (23, 4, 19),
  (13, 1, 30),
  (13, 3, 30),
  (13, 162, 30),
  (149, 105, 39),
  (150, 104, 39),
  (172, 142, 39),
  (173, 102, 39),
  (173, 103, 39),
  (173, 179, 39),
  (115, 176, 39),
  (115, 176, 7),
  (115, 176, 47),
  (115, 176, 48),
  (115, 177, 39),
  (115, 177, 7),
  (115, 177, 47),
  (115, 177, 48),
  (117, 176, 1),
  (117, 176, 3),
  (117, 176, 4),
  (117, 176, 11),
  (117, 176, 12),
  (117, 176, 13),
  (117, 176, 14),
  (117, 176, 15),
  (117, 176, 16),
  (117, 176, 17),
  (117, 176, 19),
  (117, 176, 20),
  (117, 176, 33),
  (117, 176, 34),
  (117, 176, 37),
  (117, 176, 38),
  (117, 176, 39),
  (117, 177, 1),
  (117, 177, 5),
  (117, 177, 6),
  (117, 177, 11),
  (117, 177, 12),
  (117, 177, 13),
  (117, 177, 14),
  (117, 177, 16),
  (117, 177, 17),
  (117, 177, 19),
  (117, 177, 20),
  (117, 177, 33),
  (117, 177, 34),
  (117, 177, 37),
  (117, 177, 38),
  (117, 177, 39),
  (116, 176, 1),
  (116, 176, 3),
  (116, 176, 4),
  (116, 176, 11),
  (116, 176, 12),
  (116, 176, 13),
  (116, 176, 14),
  (116, 176, 15),
  (116, 176, 16),
  (116, 176, 17),
  (116, 176, 19),
  (116, 176, 20),
  (116, 176, 33),
  (116, 176, 34),
  (116, 176, 37),
  (116, 176, 39),
  (116, 177, 1),
  (116, 177, 6),
  (116, 177, 5),
  (116, 177, 11),
  (116, 177, 12),
  (116, 177, 13),
  (116, 177, 14),
  (116, 177, 16),
  (116, 177, 17),
  (116, 177, 19),
  (116, 177, 20),
  (116, 177, 33),
  (116, 177, 34),
  (116, 177, 37),
  (116, 177, 38),
  (116, 177, 39),
  (118, 176, 1),
  (118, 176, 3),
  (118, 176, 4),
  (118, 176, 12),
  (118, 176, 14),
  (118, 176, 16),
  (118, 176, 17),
  (118, 176, 19),
  (118, 176, 33),
  (118, 176, 34),
  (118, 176, 37),
  (118, 176, 38),
  (118, 176, 39),
  (118, 177, 1),
  (118, 177, 5),
  (118, 177, 6),
  (118, 177, 12),
  (118, 177, 14),
  (118, 177, 16),
  (118, 177, 17),
  (118, 177, 19),
  (118, 177, 33),
  (118, 177, 37),
  (118, 177, 34),
  (118, 177, 38),
  (118, 177, 39),
  (10, 66, 39),
  (10, 66, 23),
  (99, 66, 39),
  (99, 66, 23),
  (23, 4, 17),
  (67, 4, 17),
  (33, 19, 15),
  (62, 47, 2),
  (116, 176, 38),
  (33, 23, 2),
  (67, 5, 1),
  (67, 5, 13),
  (67, 5, 16),
  (67, 5, 17),
  (67, 5, 19),
  (84, 5, 1),
  (84, 5, 13),
  (84, 5, 16),
  (84, 5, 17),
  (84, 5, 19),
  (67, 13, 1),
  (67, 13, 8),
  (67, 13, 13),
  (67, 13, 16),
  (67, 13, 17),
  (67, 13, 19),
  (84, 13, 1),
  (84, 13, 8),
  (84, 13, 13),
  (84, 13, 16),
  (84, 13, 17),
  (84, 13, 19),
  (67, 9, 1),
  (67, 9, 13),
  (67, 9, 16),
  (67, 9, 17),
  (67, 9, 19),
  (23, 9, 1),
  (23, 9, 13),
  (23, 9, 16),
  (23, 9, 17),
  (23, 9, 19),
  (67, 6, 1),
  (67, 6, 16),
  (67, 6, 17),
  (67, 6, 19),
  (5, 6, 19),
  (5, 6, 37),
  (5, 6, 39),
  (25, 6, 1),
  (25, 6, 16),
  (25, 6, 17),
  (25, 6, 19),
  (23, 6, 1),
  (23, 6, 16),
  (23, 6, 17),
  (23, 6, 19),
  (67, 7, 1),
  (25, 7, 1),
  (50, 12, 1),
  (13, 39, 30),
  (50, 12, 9),
  (50, 12, 12),
  (50, 12, 14),
  (50, 12, 16),
  (50, 12, 17),
  (50, 12, 19),
  (43, 39, 39),
  (50, 12, 20),
  (50, 12, 26),
  (50, 12, 27),
  (50, 12, 28),
  (50, 12, 39),
  (43, 39, 49),
  (114, 12, 1),
  (114, 12, 8),
  (114, 12, 12),
  (5, 39, 1),
  (114, 12, 16),
  (114, 12, 17),
  (114, 12, 19),
  (114, 12, 26),
  (5, 39, 2),
  (114, 12, 27),
  (114, 12, 28),
  (5, 39, 11),
  (114, 12, 39),
  (5, 39, 13),
  (5, 39, 17),
  (115, 12, 7),
  (5, 39, 18),
  (5, 39, 19),
  (115, 12, 47),
  (5, 39, 20),
  (115, 12, 48),
  (5, 39, 24),
  (5, 39, 25),
  (5, 39, 26),
  (5, 39, 27),
  (5, 39, 28),
  (5, 39, 29),
  (5, 39, 30),
  (11, 39, 13),
  (11, 39, 28),
  (11, 39, 29),
  (11, 39, 30),
  (117, 12, 1),
  (117, 12, 8),
  (117, 12, 9),
  (117, 12, 12),
  (117, 12, 14),
  (117, 12, 16),
  (117, 12, 17),
  (117, 12, 19),
  (117, 12, 26),
  (117, 12, 27),
  (117, 12, 28),
  (117, 12, 39),
  (116, 12, 1),
  (116, 12, 9),
  (116, 12, 12),
  (116, 12, 14),
  (116, 12, 16),
  (116, 12, 17),
  (116, 12, 19),
  (116, 12, 26),
  (116, 12, 27),
  (116, 12, 28),
  (116, 12, 39),
  (23, 174, 1),
  (23, 174, 9),
  (23, 174, 12),
  (23, 174, 14),
  (23, 174, 16),
  (23, 174, 17),
  (23, 174, 19),
  (23, 174, 20),
  (23, 174, 26),
  (23, 174, 27),
  (23, 174, 28),
  (23, 174, 39),
  (23, 39, 1),
  (25, 174, 1),
  (23, 39, 8),
  (25, 174, 8),
  (23, 39, 9),
  (25, 174, 12),
  (25, 174, 16),
  (25, 174, 17),
  (23, 39, 13),
  (25, 174, 19),
  (25, 174, 20),
  (23, 39, 14),
  (25, 174, 26),
  (25, 174, 27),
  (23, 39, 15),
  (25, 174, 28),
  (25, 174, 39),
  (23, 39, 16),
  (23, 39, 17),
  (23, 39, 18),
  (23, 39, 19),
  (115, 174, 39),
  (23, 39, 20),
  (23, 39, 39),
  (115, 174, 7),
  (115, 174, 47),
  (115, 174, 48),
  (117, 174, 1),
  (88, 39, 1),
  (88, 39, 3),
  (88, 39, 4),
  (88, 39, 5),
  (117, 174, 9),
  (117, 174, 12),
  (88, 39, 6),
  (117, 174, 14),
  (88, 39, 8),
  (88, 39, 9),
  (117, 174, 16),
  (117, 174, 17),
  (88, 39, 11),
  (117, 174, 19),
  (117, 174, 26),
  (117, 174, 27),
  (117, 174, 28),
  (88, 39, 12),
  (117, 174, 39),
  (116, 174, 1),
  (88, 39, 13),
  (88, 39, 14),
  (88, 39, 15),
  (88, 39, 16),
  (88, 39, 17),
  (88, 39, 18),
  (88, 39, 19),
  (116, 174, 9),
  (88, 39, 20),
  (116, 174, 12),
  (116, 174, 14),
  (88, 39, 37),
  (116, 174, 16),
  (88, 39, 38),
  (116, 174, 17),
  (88, 39, 39),
  (116, 174, 19),
  (116, 174, 26),
  (116, 174, 27),
  (116, 174, 28),
  (116, 174, 39),
  (88, 39, 49),
  (118, 174, 1),
  (40, 39, 1),
  (118, 174, 9),
  (118, 174, 12),
  (118, 174, 14),
  (118, 174, 16),
  (118, 174, 17),
  (118, 174, 19),
  (118, 174, 26),
  (118, 174, 27),
  (118, 174, 28),
  (118, 174, 39),
  (40, 39, 3),
  (40, 39, 4),
  (40, 39, 5),
  (40, 39, 6),
  (40, 39, 8),
  (40, 39, 9),
  (40, 39, 11),
  (40, 39, 12),
  (40, 39, 13),
  (40, 39, 14),
  (40, 39, 15),
  (40, 39, 16),
  (40, 39, 17),
  (40, 39, 18),
  (40, 39, 19),
  (40, 39, 20),
  (40, 39, 37),
  (40, 39, 38),
  (40, 39, 39),
  (40, 39, 49),
  (30, 39, 1),
  (30, 17, 1),
  (30, 39, 3),
  (30, 39, 4),
  (30, 39, 5),
  (30, 39, 6),
  (30, 39, 8),
  (30, 39, 9),
  (30, 39, 11),
  (30, 17, 3),
  (30, 17, 4),
  (30, 17, 5),
  (30, 39, 12),
  (30, 39, 13),
  (30, 39, 14),
  (30, 39, 15),
  (30, 39, 16),
  (30, 39, 17),
  (30, 39, 18),
  (30, 39, 19),
  (30, 39, 20),
  (30, 39, 37),
  (30, 39, 38),
  (30, 39, 39),
  (30, 39, 49),
  (28, 39, 1),
  (28, 39, 3),
  (28, 39, 4),
  (28, 39, 5),
  (28, 39, 6),
  (28, 39, 8),
  (28, 39, 9),
  (30, 17, 6),
  (28, 39, 11),
  (30, 17, 8),
  (30, 17, 9),
  (28, 39, 12),
  (30, 17, 10),
  (28, 39, 13),
  (30, 17, 11),
  (28, 39, 14),
  (30, 17, 12),
  (28, 39, 15),
  (30, 17, 13),
  (30, 17, 14),
  (30, 17, 15),
  (28, 39, 16),
  (28, 39, 17),
  (28, 39, 18),
  (28, 39, 19),
  (28, 39, 20),
  (28, 39, 37),
  (30, 17, 16),
  (28, 39, 38),
  (30, 17, 17),
  (28, 39, 39),
  (30, 17, 18),
  (28, 39, 49),
  (30, 17, 19),
  (30, 17, 20),
  (30, 17, 22),
  (30, 17, 26),
  (30, 17, 27),
  (30, 17, 28),
  (30, 17, 29),
  (30, 17, 33),
  (30, 17, 34),
  (30, 17, 37),
  (30, 17, 38),
  (30, 17, 39),
  (13, 41, 30),
  (82, 41, 1),
  (5, 41, 39),
  (40, 58, 1),
  (40, 58, 16),
  (40, 58, 19),
  (40, 58, 39),
  (28, 58, 1),
  (28, 58, 16),
  (28, 58, 19),
  (28, 58, 39),
  (4, 59, 25),
  (4, 59, 39),
  (13, 42, 30),
  (43, 42, 39),
  (43, 42, 49),
  (23, 42, 1),
  (9, 65, 24),
  (98, 59, 24),
  (13, 64, 30),
  (23, 42, 8),
  (23, 42, 9),
  (11, 56, 22),
  (23, 42, 13),
  (23, 42, 14),
  (23, 42, 15),
  (23, 42, 16),
  (23, 42, 17),
  (23, 42, 18),
  (11, 56, 26),
  (23, 42, 19),
  (11, 56, 27),
  (11, 56, 28),
  (23, 42, 20),
  (11, 56, 29),
  (11, 56, 33),
  (11, 56, 34),
  (23, 42, 39),
  (11, 56, 39),
  (41, 56, 22),
  (41, 56, 26),
  (41, 56, 27),
  (41, 56, 28),
  (41, 56, 29),
  (41, 56, 33),
  (41, 56, 34),
  (44, 107, 39),
  (44, 107, 48),
  (44, 107, 47),
  (184, 187, 39),
  (184, 187, 48),
  (184, 187, 47),
  (185, 187, 39),
  (185, 187, 47),
  (185, 187, 48),
  (6, 56, 22),
  (6, 56, 26),
  (6, 56, 27),
  (6, 56, 28),
  (6, 56, 29),
  (6, 56, 33),
  (6, 56, 34),
  (6, 56, 39),
  (26, 56, 22),
  (26, 56, 26),
  (26, 56, 27),
  (26, 56, 28),
  (26, 56, 29),
  (26, 56, 33),
  (26, 56, 34),
  (26, 56, 39),
  (12, 56, 22),
  (12, 56, 26),
  (12, 56, 27),
  (12, 56, 28),
  (12, 56, 29),
  (12, 56, 33),
  (12, 56, 34),
  (12, 56, 39),
  (2, 56, 22),
  (2, 56, 26),
  (2, 56, 27),
  (2, 56, 28),
  (2, 56, 29),
  (2, 56, 33),
  (2, 56, 34),
  (2, 56, 39),
  (5, 43, 22),
  (5, 43, 26),
  (5, 43, 27),
  (5, 43, 33),
  (5, 43, 34),
  (5, 43, 36),
  (5, 43, 39),
  (67, 43, 49),
  (67, 43, 26),
  (67, 43, 28),
  (67, 43, 33),
  (67, 43, 34),
  (67, 43, 36),
  (67, 43, 39),
  (24, 53, 22),
  (24, 53, 28),
  (24, 53, 27),
  (24, 53, 35),
  (24, 53, 39),
  (5, 54, 28),
  (5, 54, 29),
  (5, 54, 37),
  (5, 54, 39),
  (16, 35, 2),
  (16, 35, 39),
  (16, 36, 2),
  (67, 54, 17),
  (16, 36, 16),
  (16, 36, 39),
  (67, 54, 19),
  (67, 54, 28),
  (67, 54, 29),
  (67, 54, 37),
  (67, 54, 39),
  (24, 55, 28),
  (24, 55, 29),
  (24, 55, 39),
  (186, 188, 39);

--------------------------------------------------------------------------------
-- Stamgegeven: Aanduiding verblijfsrecht
--------------------------------------------------------------------------------
INSERT INTO Kern.AandVerblijfsr (ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES
   (1, '00', 'Onbekend', NULL, NULL),
   (2, '09', 'Art. 9 van de Vreemdelingenwet', NULL, 19941001),
   (3, '10', 'Art. 10 van de Vreemdelingenwet', NULL, 19941001),
   (4, '11', 'Vergunning art. 9 Vw, zonder beperking, dan wel gerechtigd krachtens art. 10 Vw', 19670101, 20010401),
   (5, '12', 'Vergunning art. 9 Vw, met beperking, die elke vorm arbeid in loondienst toestaat', 19670101, 20010401),
   (6, '13', 'Vergunning art. 9 Vw, met beperking, die arbeid in loondienst niet toestaat', 19670101, 20010401),
   (7, '14', 'Voorwaardelijke vergunning art. 9a Vw', 19940101, 20010401),
   (8, '15', 'Benelux of EG richtl nrs 68/360/EEG, 73/148/EEG 75/34/EEG, verord nr 1251/70/EEG', 19520723, 20010401),
   (9, '16', 'EG-richtlijnen nrs. 90/364/EEG, 90/365/EEG en 90/366/EEG', 19520723, 20010401),
   (10, '17', 'Verzoek tot toelating als vluchteling, uitzetting blijft achterwege', 19540421, 20010401),
   (11, '18', 'Gemeld bij VD, geen verzoek toelating als vluchteling, geen uitzetting', 19460101, 20010401),
   (12, '19', 'Vergunning art. 9 Vw, met andere beperking dan hierboven', 19670101, 20010401),
   (13, '20', 'Ander verdrag dan hierboven', 19460101, 20010401),
   (14, '21', 'Vw 2000 art. 8, onder a, vergunning regulier bepaalde tijd, arbeid vrij', NULL, NULL),
   (15, '22', 'Vw2000 art. 8, sub a, VVR bep tijd arbeid mits TWV, VVR bep tijd verblijf&arbeid', NULL, NULL),
   (16, '23', 'Vw 2000 art. 8, onder a, vergunning regulier bepaalde tijd, arbeid specifiek', NULL, NULL),
   (17, '24', 'Vw 2000 art. 8, onder a, vergunning regulier bepaalde tijd, geen arbeid', NULL, NULL),
   (18, '25', 'Vw 2000 art. 8, sub b, verg. reg. onbep. tijd of langdurig ingez., arbeid vrij', NULL, NULL),
   (19, '26', 'Vw 2000 art. 8, onder c, vergunning asiel bepaalde tijd, arbeid vrij', NULL, NULL),
   (20, '27', 'Vw 2000 art. 8, sub d, verg. asiel onbep. tijd of langdurig ingez., arbeid vrij', NULL, NULL),
   (21, '28', 'Vw 2000 art. 8, onder e, gemeenschapsonderdaan econ. actief, arbeid vrij', NULL, NULL),
   (22, '29', 'Vw 2000 art. 8, onder e, gemeenschapsonderdaan econ. niet-actief, arbeid vrij', NULL, NULL),
   (23, '30', 'Vw 2000 art. 8, onder e, toetsing aan het gemeenschapsrecht, arbeid vrij', NULL, NULL),
   (24, '31', 'Vw 2000 art. 8, onder f en h, in procedure voor vergunning art. 14 Vw 2000', NULL, NULL),
   (25, '32', 'Vw 2000 art. 8, onder f en h, in procedure voor vergunning art. 28 Vw 2000', NULL, NULL),
   (26, '33', 'Vw 2000 art. 8, onder g en h, in procedure voortgezet verblijf, tijdige aanvr.', NULL, NULL),
   (27, '34', 'Vw 2000 art. 8, onder g en h, in procedure voortgezet verblijf, ontijdige aanvr.', NULL, NULL),
   (28, '35', 'Vw 2000 art. 8, onder l, Associatiebesluit 1/80 EEG/Turkije, arbeid specifiek', NULL, NULL),
   (29, '36', 'Vw 2000 art. 8, onder e, gemeenschapsonderdaan econ. actief, arbeid specifiek', 20040501, NULL),
   (30, '37', 'Vw 2000 art. 8, onder e, gemeenschapsonderdaan econ. niet-actief, arbeid spec.', 20040501, NULL),
   (31, '38', 'Vw 2000 art. 8, onder e, toetsing aan het gemeenschapsrecht, arbeid specifiek', 20040501, NULL),
   (32, '39', 'Vw 2000 art. 8, onder m, in afwachting indiening asielaanvraag, geen arbeid', 20000101, NULL),
   (33, '40', 'Vw 2000 art. 8, onder e, gemeenschapsonderdaan duurzaam verblijf, arbeid vrij', NULL, NULL),
   (34, '41', 'Rechtmatig verblijf Vw 2000 art. 8, onder e, is beëindigd', 20000101, NULL),
   (35, '42', 'Rechtmatig verblijf op grond van voorlopige maatregel EHRM, geen arbeid', 20000101, NULL),
   (36, '43', 'Rechtmatig verblijf op aanwijzing Minister van Justitie, geen arbeid', 20000101, NULL),
   (37, '91', 'Vw 2000 art. 115, lid 4, vergunning onbepaalde tijd, arbeid vrij', NULL, 20140106),
   (38, '92', 'Vw 2000 art. 115, lid 6, vergunning asiel bepaalde tijd, arbeid vrij', NULL, 20140106),
   (39, '93', 'Vw 2000 art. 115, lid 2, verg. regulier bepaalde tijd, arbeid nader te bepalen', NULL, 20140106),
   (40, '98', 'geen verblijfstitel (meer)', NULL, NULL),
   (41, '44', 'Vw 2000 art. 8, onder m, Dublinclaimant, wacht op overdracht, rva, geen arbeid', 20140101, NULL);


--------------------------------------------------------------------------------
-- Stamgegeven: Conversie aangifte adreshouding
--------------------------------------------------------------------------------
INSERT INTO Conv.ConvAangifteAdresh (ID, Rubr7210OmsVanDeAangifteAdre, Aang, RdnWijzVerblijf) VALUES
   (1, 'A', NULL, 2),
   (2, 'B', NULL, 3),
   (3, 'G', 1, 1),
   (4, 'H', 2, 1),
   (5, 'I', 3, 1),
   (6, 'K', 4, 1),
   (7, 'M', 5, 1),
   (8, 'O', 6, 1),
   (9, 'P', 7, 1),
   (10, 'T', NULL, 4),
   (11, 'W', NULL, 5),
   (12, '.', NULL, 6);


--------------------------------------------------------------------------------
-- Stamgegeven: Conversie adellijke titel predikaat
--------------------------------------------------------------------------------
INSERT INTO Conv.ConvAdellijkeTitelPredikaat (ID, Rubr0221AdellijkeTitelPredik, Geslachtsaand, AdellijkeTitel, Predicaat) VALUES
   (1, 'B', 1, 1, NULL),
   (2, 'BS', 2, 1, NULL),
   (3, 'G', 1, 2, NULL),
   (4, 'GI', 2, 2, NULL),
   (5, 'H', 1, 3, NULL),
   (6, 'HI', 2, 3, NULL),
   (7, 'M', 1, 4, NULL),
   (8, 'MI', 2, 4, NULL),
   (9, 'P', 1, 5, NULL),
   (10, 'PS', 2, 5, NULL),
   (11, 'R', 1, 6, NULL),
   (12, 'R', 2, 6, NULL),
   (13, 'JH', 1, NULL, 3),
   (14, 'JV', 2, NULL, 3);


--------------------------------------------------------------------------------
-- Stamgegeven: Conversie LO3 Rubriek
--------------------------------------------------------------------------------
INSERT INTO Conv.ConvLO3Rubriek (Naam) VALUES
   ('01.01.10'),
   ('01.01.20'),
   ('01.02.10'),
   ('01.02.20'),
   ('01.02.30'),
   ('01.02.40'),
   ('01.03.10'),
   ('01.03.20'),
   ('01.03.30'),
   ('01.04.10'),
   ('01.20.10'),
   ('01.20.20'),
   ('01.61.10'),
   ('01.81.10'),
   ('01.81.20'),
   ('01.82.10'),
   ('01.82.20'),
   ('01.82.30'),
   ('01.83.10'),
   ('01.83.20'),
   ('01.83.30'),
   ('01.85.10'),
   ('01.86.10'),
   ('01.88.10'),
   ('01.88.20'),
   ('02.01.10'),
   ('02.01.20'),
   ('02.02.10'),
   ('02.02.20'),
   ('02.02.30'),
   ('02.02.40'),
   ('02.03.10'),
   ('02.03.20'),
   ('02.03.30'),
   ('02.04.10'),
   ('02.62.10'),
   ('02.81.10'),
   ('02.81.20'),
   ('02.82.10'),
   ('02.82.20'),
   ('02.82.30'),
   ('02.83.10'),
   ('02.83.20'),
   ('02.83.30'),
   ('02.85.10'),
   ('02.86.10'),
   ('03.01.10'),
   ('03.01.20'),
   ('03.02.10'),
   ('03.02.20'),
   ('03.02.30'),
   ('03.02.40'),
   ('03.03.10'),
   ('03.03.20'),
   ('03.03.30'),
   ('03.04.10'),
   ('03.62.10'),
   ('03.81.10'),
   ('03.81.20'),
   ('03.82.10'),
   ('03.82.20'),
   ('03.82.30'),
   ('03.83.10'),
   ('03.83.20'),
   ('03.83.30'),
   ('03.85.10'),
   ('03.86.10'),
   ('04.05.10'),
   ('04.63.10'),
   ('04.64.10'),
   ('04.65.10'),
   ('04.82.10'),
   ('04.82.20'),
   ('04.82.30'),
   ('04.83.10'),
   ('04.83.20'),
   ('04.83.30'),
   ('04.85.10'),
   ('04.86.10'),
   ('04.88.10'),
   ('04.88.20'),
   ('05.01.10'),
   ('05.01.20'),
   ('05.02.10'),
   ('05.02.20'),
   ('05.02.30'),
   ('05.02.40'),
   ('05.03.10'),
   ('05.03.20'),
   ('05.03.30'),
   ('05.04.10'),
   ('05.06.10'),
   ('05.06.20'),
   ('05.06.30'),
   ('05.07.10'),
   ('05.07.20'),
   ('05.07.30'),
   ('05.07.40'),
   ('05.15.10'),
   ('05.81.10'),
   ('05.81.20'),
   ('05.82.10'),
   ('05.82.20'),
   ('05.82.30'),
   ('05.83.10'),
   ('05.83.20'),
   ('05.83.30'),
   ('05.85.10'),
   ('05.86.10'),
   ('06.08.10'),
   ('06.08.20'),
   ('06.08.30'),
   ('06.81.10'),
   ('06.81.20'),
   ('06.82.10'),
   ('06.82.20'),
   ('06.82.30'),
   ('06.83.10'),
   ('06.83.20'),
   ('06.83.30'),
   ('06.85.10'),
   ('06.86.10'),
   ('06.88.10'),
   ('06.88.20'),
   ('07.66.20'),
   ('07.67.10'),
   ('07.67.20'),
   ('07.68.10'),
   ('07.69.10'),
   ('07.70.10'),
   ('07.71.10'),
   ('07.71.20'),
   ('07.80.10'),
   ('07.80.20'),
   ('07.87.10'),
   ('07.88.10'),
   ('07.88.20'),
   ('08.09.10'),
   ('08.09.20'),
   ('08.10.10'),
   ('08.10.20'),
   ('08.10.30'),
   ('08.11.10'),
   ('08.11.15'),
   ('08.11.20'),
   ('08.11.30'),
   ('08.11.40'),
   ('08.11.50'),
   ('08.11.60'),
   ('08.11.70'),
   ('08.11.80'),
   ('08.11.90'),
   ('08.12.10'),
   ('08.13.10'),
   ('08.13.20'),
   ('08.13.30'),
   ('08.13.40'),
   ('08.13.50'),
   ('08.14.10'),
   ('08.14.20'),
   ('08.72.10'),
   ('08.75.10'),
   ('08.83.10'),
   ('08.83.20'),
   ('08.83.30'),
   ('08.85.10'),
   ('08.86.10'),
   ('08.88.10'),
   ('08.88.20'),
   ('09.01.10'),
   ('09.01.20'),
   ('09.02.10'),
   ('09.02.20'),
   ('09.02.30'),
   ('09.02.40'),
   ('09.03.10'),
   ('09.03.20'),
   ('09.03.30'),
   ('09.81.10'),
   ('09.81.20'),
   ('09.82.10'),
   ('09.82.20'),
   ('09.82.30'),
   ('09.83.10'),
   ('09.83.20'),
   ('09.83.30'),
   ('09.85.10'),
   ('09.86.10'),
   ('10.39.10'),
   ('10.39.20'),
   ('10.39.30'),
   ('10.83.10'),
   ('10.83.20'),
   ('10.83.30'),
   ('10.85.10'),
   ('10.86.10'),
   ('11.32.10'),
   ('11.33.10'),
   ('11.82.10'),
   ('11.82.19'),
   ('11.82.20'),
   ('11.82.30'),
   ('11.83.10'),
   ('11.83.20'),
   ('11.83.30'),
   ('11.85.10'),
   ('11.86.10'),
   ('12.35.10'),
   ('12.35.20'),
   ('12.35.30'),
   ('12.35.40'),
   ('12.35.50'),
   ('12.35.60'),
   ('12.35.70'),
   ('12.35.80'),
   ('12.36.10'),
   ('12.37.10'),
   ('12.82.10'),
   ('12.82.20'),
   ('12.82.30'),
   ('12.83.10'),
   ('12.83.20'),
   ('12.83.30'),
   ('12.85.10'),
   ('12.86.10'),
   ('12.86.19'),
   ('13.31.10'),
   ('13.31.20'),
   ('13.31.30'),
   ('13.38.10'),
   ('13.38.20'),
   ('13.82.10'),
   ('13.82.20'),
   ('13.82.30'),
   ('14.40.10'),
   ('14.85.10'),
   ('51.01.10'),
   ('51.01.20'),
   ('51.02.10'),
   ('51.02.20'),
   ('51.02.30'),
   ('51.02.40'),
   ('51.03.10'),
   ('51.03.20'),
   ('51.03.30'),
   ('51.04.10'),
   ('51.20.10'),
   ('51.20.20'),
   ('51.61.10'),
   ('51.81.10'),
   ('51.81.20'),
   ('51.82.10'),
   ('51.82.20'),
   ('51.82.30'),
   ('51.83.10'),
   ('51.83.20'),
   ('51.83.30'),
   ('51.84.10'),
   ('51.85.10'),
   ('51.86.10'),
   ('51.88.10'),
   ('51.88.20'),
   ('52.01.10'),
   ('52.01.20'),
   ('52.02.10'),
   ('52.02.20'),
   ('52.02.30'),
   ('52.02.40'),
   ('52.03.10'),
   ('52.03.20'),
   ('52.03.30'),
   ('52.04.10'),
   ('52.62.10'),
   ('52.81.10'),
   ('52.81.20'),
   ('52.82.10'),
   ('52.82.20'),
   ('52.82.30'),
   ('52.83.10'),
   ('52.83.20'),
   ('52.83.30'),
   ('52.84.10'),
   ('52.85.10'),
   ('52.86.10'),
   ('53.01.10'),
   ('53.01.20'),
   ('53.02.10'),
   ('53.02.20'),
   ('53.02.30'),
   ('53.02.40'),
   ('53.03.10'),
   ('53.03.20'),
   ('53.03.30'),
   ('53.04.10'),
   ('53.62.10'),
   ('53.81.10'),
   ('53.81.20'),
   ('53.82.10'),
   ('53.82.20'),
   ('53.82.30'),
   ('53.83.10'),
   ('53.83.20'),
   ('53.83.30'),
   ('53.84.10'),
   ('53.85.10'),
   ('53.86.10'),
   ('54.05.10'),
   ('54.63.10'),
   ('54.64.10'),
   ('54.65.10'),
   ('54.82.10'),
   ('54.82.20'),
   ('54.82.30'),
   ('54.83.10'),
   ('54.83.20'),
   ('54.83.30'),
   ('54.84.10'),
   ('54.85.10'),
   ('54.86.10'),
   ('54.88.10'),
   ('54.88.20'),
   ('55.01.10'),
   ('55.01.20'),
   ('55.02.10'),
   ('55.02.20'),
   ('55.02.30'),
   ('55.02.40'),
   ('55.03.10'),
   ('55.03.20'),
   ('55.03.30'),
   ('55.04.10'),
   ('55.06.10'),
   ('55.06.20'),
   ('55.06.30'),
   ('55.07.10'),
   ('55.07.20'),
   ('55.07.30'),
   ('55.07.40'),
   ('55.15.10'),
   ('55.81.10'),
   ('55.81.20'),
   ('55.82.10'),
   ('55.82.20'),
   ('55.82.30'),
   ('55.83.10'),
   ('55.83.20'),
   ('55.83.30'),
   ('55.84.10'),
   ('55.85.10'),
   ('55.86.10'),
   ('56.08.10'),
   ('56.08.20'),
   ('56.08.30'),
   ('56.81.10'),
   ('56.81.20'),
   ('56.82.10'),
   ('56.82.20'),
   ('56.82.30'),
   ('56.83.10'),
   ('56.83.20'),
   ('56.83.30'),
   ('56.84.10'),
   ('56.85.10'),
   ('56.86.10'),
   ('56.88.10'),
   ('56.88.20'),
   ('58.09.10'),
   ('58.09.20'),
   ('58.10.10'),
   ('58.10.20'),
   ('58.10.30'),
   ('58.11.10'),
   ('58.11.15'),
   ('58.11.20'),
   ('58.11.30'),
   ('58.11.40'),
   ('58.11.50'),
   ('58.11.60'),
   ('58.11.70'),
   ('58.11.80'),
   ('58.11.90'),
   ('58.12.10'),
   ('58.13.10'),
   ('58.13.20'),
   ('58.13.30'),
   ('58.13.40'),
   ('58.13.50'),
   ('58.14.10'),
   ('58.14.20'),
   ('58.72.10'),
   ('58.75.10'),
   ('58.83.10'),
   ('58.83.20'),
   ('58.83.30'),
   ('58.84.10'),
   ('58.85.10'),
   ('58.86.10'),
   ('58.88.10'),
   ('58.88.20'),
   ('59.01.10'),
   ('59.01.20'),
   ('59.02.10'),
   ('59.02.20'),
   ('59.02.30'),
   ('59.02.40'),
   ('59.03.10'),
   ('59.03.20'),
   ('59.03.30'),
   ('59.81.10'),
   ('59.81.20'),
   ('59.82.10'),
   ('59.82.20'),
   ('59.82.30'),
   ('59.83.10'),
   ('59.83.20'),
   ('59.83.30'),
   ('59.84.10'),
   ('59.85.10'),
   ('59.86.10'),
   ('60.39.10'),
   ('60.39.20'),
   ('60.39.30'),
   ('60.83.10'),
   ('60.83.20'),
   ('60.83.30'),
   ('60.84.10'),
   ('60.85.10'),
   ('60.86.10'),
   ('61.32.10'),
   ('61.33.10'),
   ('61.82.10'),
   ('61.82.20'),
   ('61.82.30'),
   ('61.83.10'),
   ('61.83.20'),
   ('61.83.30'),
   ('61.84.10'),
   ('61.85.10'),
   ('61.86.10'),
   ('64.40.10'),
   ('64.85.10'),
   ('04.73.10'),
   ('54.73.10');


--------------------------------------------------------------------------------
-- Stamgegeven: LandGebied
--------------------------------------------------------------------------------
INSERT INTO Kern.LandGebied (ID, Code, DatAanvGel, DatEindeGel, ISO31661Alpha2, Naam) VALUES
   (1, '9076', NULL, 19910601, NULL, 'Abessinië'),
   (2, '9061', NULL, 19910601, NULL, 'Abu Dhabi'),
   (3, '9041', NULL, 19910601, NULL, 'Aden'),
   (4, '9062', NULL, 19910601, NULL, 'Ajman'),
   (5, '6023', NULL, NULL, 'AF', 'Afghanistan'),
   (6, '5034', NULL, NULL, 'AL', 'Albanië'),
   (7, '6047', NULL, NULL, 'DZ', 'Algerije'),
   (8, '8002', NULL, NULL, 'AS', 'Amerikaans-Samoa'),
   (9, '7088', NULL, NULL, 'VI', 'Amerikaanse Maagdeneilanden'),
   (10, '7005', NULL, NULL, 'AD', 'Andorra'),
   (11, '5026', NULL, NULL, 'AO', 'Angola'),
   (12, '8036', NULL, NULL, 'AI', 'Anguilla'),
   (13, '8038', NULL, 19811101, NULL, 'Antigua'),
   (14, '8045', 19811101, NULL, 'AG', 'Antigua en Barbuda'),
   (15, '7015', NULL, NULL, 'AR', 'Argentinië'),
   (16, '5054', 19911231, NULL, 'AM', 'Armenië'),
   (17, '5095', 19860101, NULL, 'AW', 'Aruba'),
   (18, '5055', NULL, NULL, 'AC', 'Ascension'),
   (19, '6016', NULL, NULL, 'AU', 'Australië'),
   (20, '5041', NULL, 19750916, NULL, 'Australisch Nieuw-Guinea'),
   (21, '7089', NULL, 19750916, NULL, 'Australische Salomonseilanden'),
   (22, '5097', 19911231, NULL, 'AZ', 'Azerbeidzjan'),
   (23, '5056', NULL, NULL, NULL, 'Azoren'),
   (24, '6033', NULL, NULL, 'BS', 'Bahama''s'),
   (25, '5057', NULL, NULL, 'BH', 'Bahrein'),
   (26, '7084', 19711216, NULL, 'BD', 'Bangladesh'),
   (27, '7004', NULL, NULL, 'BB', 'Barbados'),
   (28, '9063', NULL, 19661004, NULL, 'Basutoland'),
   (29, '9064', NULL, 19660930, NULL, 'Bechuanaland'),
   (30, '5098', 19911231, NULL, 'BY', 'Belarus'),
   (31, '8043', NULL, 19941001, NULL, 'Belau'),
   (32, '5010', NULL, NULL, 'BE', 'België'),
   (33, '7068', NULL, 19600630, NULL, 'Belgisch-Congo'),
   (34, '8017', NULL, NULL, 'BZ', 'Belize'),
   (35, '8023', 19751130, NULL, 'BJ', 'Benin'),
   (36, '9048', NULL, NULL, 'BM', 'Bermuda'),
   (37, '5058', NULL, NULL, 'BT', 'Bhutan'),
   (38, '6015', NULL, NULL, 'BO', 'Bolivia'),
   (39, '5106', 20101010, NULL, 'BQ', 'Bonaire'),
   (40, '9089', 19490801, NULL, 'DE', 'Bondsrepubliek Duitsland'),
   (41, '6065', 19920406, NULL, 'BA', 'Bosnië-Herzegovina'),
   (42, '5011', 19660930, NULL, 'BW', 'Botswana'),
   (43, '6046', NULL, 19840804, NULL, 'Opper-Volta'),
   (44, '6008', NULL, NULL, 'BR', 'Brazilië'),
   (45, '7067', NULL, 19910601, NULL, 'Brits-Afrika'),
   (46, '9058', NULL, 19630916, NULL, 'Brits-Borneo'),
   (47, '9031', NULL, 19660526, NULL, 'Brits-Guyana'),
   (48, '6052', NULL, 19730601, NULL, 'Brits-Honduras'),
   (49, '7070', NULL, 19470815, NULL, 'Brits-Indië'),
   (50, '9006', NULL, 19611001, NULL, 'Brits-Kameroen'),
   (51, '7083', NULL, 19630916, NULL, 'Brits Noord-Borneo'),
   (52, '7082', NULL, 19631212, NULL, 'Brits Oost-Afrika'),
   (53, '9027', NULL, 19600701, NULL, 'Brits-Somaliland'),
   (54, '7095', NULL, NULL, 'BQ', 'Brits Antarctisch Territorium'),
   (55, '7096', NULL, NULL, 'IO', 'Brits Indische Oceaanterritorium'),
   (56, '7075', NULL, 19630916, NULL, 'Brits West-Borneo'),
   (57, '7062', NULL, 19620201, NULL, 'Brits West-Indië'),
   (58, '5059', 19620201, 19811101, NULL, 'Britse Antillen'),
   (59, '7030', NULL, NULL, 'VG', 'Britse Maagdeneilanden'),
   (60, '6058', NULL, 19780707, NULL, 'Britse Salomonseilanden'),
   (61, '5042', NULL, NULL, 'BN', 'Brunei'),
   (62, '7024', NULL, NULL, 'BG', 'Bulgarije'),
   (63, '5096', 19840804, NULL, 'BF', 'Burkina Faso'),
   (64, '5031', NULL, 19890618, 'BU', 'Burma'),
   (65, '6001', 19620701, NULL, 'BI', 'Burundi'),
   (66, '8010', NULL, 19910601, NULL, 'Cabinda'),
   (67, '7093', NULL, 19910601, NULL, 'Caicoseilanden'),
   (68, '5001', 18670701, NULL, 'CA', 'Canada'),
   (69, '6053', NULL, NULL, 'IC', 'Canarische Eilanden'),
   (70, '8011', NULL, 19910601, NULL, 'Canton en Enderbury'),
   (71, '7092', NULL, NULL, 'KY', 'Caymaneilanden'),
   (72, '9086', NULL, NULL, 'CF', 'Centraal-Afrikaanse Republiek'),
   (73, '9052', NULL, 19720522, NULL, 'Ceylon'),
   (74, '5021', NULL, NULL, 'CL', 'Chili'),
   (75, '6022', NULL, NULL, 'CN', 'China'),
   (76, '8012', NULL, NULL, 'CX', 'Christmaseiland'),
   (77, '8013', NULL, NULL, 'CC', 'Cocoseilanden'),
   (78, '5033', NULL, NULL, 'CO', 'Colombia'),
   (79, '5060', NULL, NULL, 'KM', 'Comoren'),
   (80, '7097', NULL, NULL, 'CK', 'Cookeilanden'),
   (81, '7007', NULL, NULL, 'CR', 'Costa Rica'),
   (82, '5006', NULL, NULL, 'CU', 'Cuba'),
   (83, '5107', 20101010, NULL, 'CW', 'Curaçao'),
   (84, '5040', NULL, NULL, 'CY', 'Cyprus'),
   (85, '7023', NULL, 19751130, NULL, 'Dahomey'),
   (86, '9085', NULL, 19390901, NULL, 'Dantzig'),
   (87, '6069', 19970517, NULL, 'CD', 'Democratische Republiek Congo'),
   (88, '5015', NULL, NULL, 'DK', 'Denemarken'),
   (89, '9087', 19770627, NULL, 'DJ', 'Djibouti'),
   (90, '8032', NULL, 19910601, NULL, 'Dubai'),
   (91, '8030', NULL, NULL, 'DM', 'Dominica'),
   (92, '7027', NULL, NULL, 'DO', 'Dominicaanse Republiek'),
   (93, '9050', NULL, 19910601, NULL, 'Duits Oost-Afrika'),
   (94, '9022', NULL, 19910601, NULL, 'Duits Zuidwest-Afrika'),
   (95, '7085', 19491007, 19901003, NULL, 'Duitse Democratische Republiek'),
   (96, '6029', NULL, 19910601, NULL, 'Duitsland'),
   (97, '7039', NULL, NULL, 'EC', 'Ecuador'),
   (98, '7014', NULL, NULL, 'EG', 'Egypte'),
   (99, '7032', NULL, NULL, 'SV', 'El Salvador'),
   (100, '9043', NULL, NULL, 'GQ', 'Equatoriaal-Guinea'),
   (101, '9003', NULL, NULL, 'ER', 'Eritrea'),
   (102, '7065', NULL, NULL, 'EE', 'Estland'),
   (103, '5020', NULL, NULL, 'ET', 'Ethiopië'),
   (104, '8014', NULL, NULL, 'FO', 'Faeröer'),
   (105, '5061', NULL, NULL, 'FK', 'Falklandeilanden'),
   (106, '6068', 19920427, 20040201, NULL, 'Federale Republiek Joegoslavië'),
   (107, '6032', NULL, NULL, 'FJ', 'Fiji'),
   (108, '5027', NULL, NULL, 'PH', 'Filipijnen'),
   (109, '6002', NULL, NULL, 'FI', 'Finland'),
   (110, '9065', NULL, 19910601, NULL, 'Fujairah'),
   (111, '5002', NULL, NULL, 'FR', 'Frankrijk'),
   (112, '7079', NULL, 19600815, NULL, 'Frans-Congo'),
   (113, '9078', NULL, 19910601, NULL, 'Frans Equatoriaal-Afrika'),
   (114, '9016', NULL, 19770627, NULL, 'Frans Territorium voor Afars en Issa''s'),
   (115, '5062', NULL, NULL, 'GF', 'Frans-Guyana'),
   (116, '5079', NULL, 19910601, NULL, 'Frans-Indië'),
   (117, '7074', NULL, 19910601, NULL, 'Frans Indochina'),
   (118, '9066', NULL, 19600101, NULL, 'Frans-Kameroen'),
   (119, '6054', NULL, NULL, 'PF', 'Frans-Polynesië'),
   (120, '5063', NULL, 19770627, NULL, 'Frans-Somaliland'),
   (121, '9077', NULL, 19910601, NULL, 'Frans West-Afrika'),
   (122, '6048', NULL, NULL, 'GA', 'Gabon'),
   (123, '7008', NULL, NULL, 'GM', 'Gambia'),
   (124, '6064', 19911231, NULL, 'GE', 'Georgië'),
   (125, '5024', 19570306, NULL, 'GH', 'Ghana'),
   (126, '6055', NULL, NULL, 'GI', 'Gibraltar'),
   (127, '5064', NULL, 19751001, NULL, 'Gilbert- en Ellice-eilanden'),
   (128, '8040', NULL, 19790712, NULL, 'Gilberteilanden'),
   (129, '9084', NULL, 19611218, NULL, 'Goa'),
   (130, '7076', NULL, 19570306, NULL, 'Goudkust'),
   (131, '8008', NULL, NULL, 'GD', 'Grenada'),
   (132, '6003', NULL, NULL, 'GR', 'Griekenland'),
   (133, '5065', NULL, NULL, 'GL', 'Groenland'),
   (134, '6039', NULL, NULL, 'GB', 'Groot-Brittannië'),
   (135, '5066', NULL, NULL, 'GP', 'Guadeloupe'),
   (136, '8001', NULL, NULL, 'GU', 'Guam'),
   (137, '6004', NULL, NULL, 'GT', 'Guatemala'),
   (138, '7040', NULL, NULL, 'GN', 'Guinee'),
   (139, '5072', NULL, NULL, 'GW', 'Guinee-Bissau'),
   (140, '6025', 19660526, NULL, 'GY', 'Guyana'),
   (141, '6041', NULL, NULL, 'HT', 'Haïti'),
   (142, '8000', NULL, 19910601, NULL, 'Hawaii-eilanden'),
   (143, '7017', NULL, NULL, 'HN', 'Honduras'),
   (144, '5017', NULL, NULL, 'HU', 'Hongarije'),
   (145, '7036', NULL, 19970701, 'HK', 'Hongkong'),
   (146, '6007', NULL, NULL, 'IE', 'Ierland'),
   (147, '9005', NULL, 19690106, NULL, 'Ifni'),
   (148, '6011', NULL, NULL, 'IS', 'IJsland'),
   (149, '7046', NULL, NULL, 'IN', 'India'),
   (150, '9055', NULL, 19910601, NULL, 'Indochina'),
   (151, '6024', 19491227, NULL, 'ID', 'Indonesië'),
   (152, '9999', NULL, NULL, NULL, 'Internationaal gebied'),
   (153, '5043', NULL, NULL, 'IQ', 'Irak'),
   (154, '5012', NULL, NULL, 'IR', 'Iran'),
   (155, '6034', 19480514, NULL, 'IL', 'Israël'),
   (156, '9028', NULL, 19600701, NULL, 'Italiaans-Somaliland'),
   (157, '7044', NULL, NULL, 'IT', 'Italië'),
   (158, '5030', NULL, NULL, 'CI', 'Ivoorkust'),
   (159, '6017', NULL, NULL, 'JM', 'Jamaica'),
   (160, '7035', NULL, NULL, 'JP', 'Japan'),
   (161, '5048', 19900522, NULL, 'YE', 'Jemen'),
   (162, '6045', NULL, 19960601, 'YU', 'Joegoslavië'),
   (163, '5080', NULL, NULL, NULL, 'Johnston'),
   (164, '9067', NULL, 19460401, NULL, 'Johore'),
   (165, '6042', NULL, NULL, 'JO', 'Jordanië'),
   (166, '8025', NULL, NULL, 'CV', 'Kaapverdië'),
   (167, '5067', NULL, 19750705, NULL, 'Kaapverdische Eilanden'),
   (168, '9007', NULL, 19910601, NULL, 'Keizer Wilhelmsland'),
   (169, '6031', NULL, NULL, 'KH', 'Cambodja'),
   (170, '5035', 19600101, NULL, 'CM', 'Kameroen'),
   (171, '8034', NULL, NULL, NULL, 'Kanaaleilanden'),
   (172, '5046', NULL, 19910601, NULL, 'Kashmir'),
   (173, '9037', NULL, NULL, 'QA', 'Qatar'),
   (174, '5099', 19911231, NULL, 'KZ', 'Kazachstan'),
   (175, '5081', NULL, 19460401, NULL, 'Kedah'),
   (176, '5082', NULL, 19460401, NULL, 'Kelantan'),
   (177, '7002', 19631212, NULL, 'KE', 'Kenya'),
   (178, '8027', 19790712, NULL, 'KI', 'Kiribati'),
   (179, '7045', NULL, NULL, 'KW', 'Koeweit'),
   (180, '9008', 19600815, NULL, 'CG', 'Congo'),
   (181, '9013', NULL, 19600815, NULL, 'Congo-Brazzaville'),
   (182, '9009', NULL, 19711027, NULL, 'Congo-Kinshasa'),
   (183, '9068', NULL, 19910601, NULL, 'Korea'),
   (184, '5105', 20080217, NULL, NULL, 'Kosovo'),
   (185, '5051', 19920115, NULL, 'HR', 'Kroatië'),
   (186, '6021', 19911231, NULL, 'KG', 'Kirgizië'),
   (187, '9069', NULL, 19910601, NULL, 'Labuan'),
   (188, '5025', NULL, NULL, 'LA', 'Laos'),
   (189, '9014', NULL, 19910601, NULL, 'Leewardeilanden'),
   (190, '7016', 19661004, NULL, 'LS', 'Lesotho'),
   (191, '7064', 19181118, NULL, 'LV', 'Letland'),
   (192, '7043', NULL, NULL, 'LB', 'Libanon'),
   (193, '5019', 18470726, NULL, 'LR', 'Liberia'),
   (194, '6006', NULL, NULL, 'LY', 'Libië'),
   (195, '6012', NULL, NULL, 'LI', 'Liechtenstein'),
   (196, '7066', 19180216, NULL, 'LT', 'Litouwen'),
   (197, '6018', NULL, NULL, 'LU', 'Luxemburg'),
   (198, '5068', NULL, 19991220, 'MO', 'Macau'),
   (199, '5100', 19930419, NULL, 'MK', 'Macedonië'),
   (200, '9010', NULL, NULL, 'MG', 'Madagaskar'),
   (201, '7087', NULL, NULL, NULL, 'Madeira-eilanden'),
   (202, '5083', NULL, 19630916, NULL, 'Malakka'),
   (203, '5005', 19640706, NULL, 'MW', 'Malawi'),
   (204, '7041', NULL, NULL, 'MV', 'Maldiven'),
   (205, '7026', 19630916, NULL, 'MY', 'Maleisië'),
   (206, '5029', 19600620, NULL, 'ML', 'Mali'),
   (207, '7003', NULL, NULL, 'MT', 'Malta'),
   (208, '8035', NULL, NULL, 'IM', 'Man'),
   (209, '8009', NULL, NULL, 'MP', 'Marianen'),
   (210, '5022', NULL, NULL, 'MA', 'Marokko'),
   (211, '9056', NULL, NULL, 'MH', 'Marshalleilanden'),
   (212, '5069', NULL, NULL, 'MQ', 'Martinique'),
   (213, '6020', NULL, NULL, 'MR', 'Mauritanië'),
   (214, '5044', NULL, NULL, 'MU', 'Mauritius'),
   (215, '5084', NULL, NULL, 'YT', 'Mayotte'),
   (216, '7006', NULL, NULL, 'MX', 'Mexico'),
   (217, '9094', NULL, NULL, 'FM', 'Micronesia'),
   (218, '8003', NULL, NULL, 'MI', 'Midway'),
   (219, '6000', 19911231, NULL, 'MD', 'Moldavië'),
   (220, '5032', NULL, NULL, 'MC', 'Monaco'),
   (221, '7052', NULL, NULL, 'MN', 'Mongolië'),
   (222, '5104', 20060603, NULL, 'ME', 'Montenegro'),
   (223, '8015', NULL, NULL, 'MS', 'Montserrat'),
   (224, '5070', 19750625, NULL, 'MZ', 'Mozambique'),
   (225, '9053', NULL, 19700809, NULL, 'Muscat en Oman'),
   (226, '5047', 19890618, NULL, 'MM', 'Myanmar'),
   (227, '9023', 19680131, NULL, 'NA', 'Namibië'),
   (228, '7057', NULL, NULL, 'NR', 'Nauru'),
   (229, '6030', NULL, NULL, 'NL', 'Nederland'),
   (230, '9030', NULL, 19491227, NULL, 'Nederlands-Indië'),
   (231, '7058', NULL, 19621001, NULL, 'Nederlands Nieuw-Guinea'),
   (232, '7011', NULL, 20101010, 'AN', 'Nederlandse Antillen'),
   (233, '5085', NULL, 19460401, NULL, 'Negri Sembilan'),
   (234, '6035', NULL, NULL, 'NP', 'Nepal'),
   (235, '9000', NULL, 19910601, NULL, 'Newfoundland'),
   (236, '7018', NULL, NULL, 'NI', 'Nicaragua'),
   (237, '7099', NULL, NULL, 'NC', 'Nieuw-Caledonië'),
   (238, '8033', NULL, 19800730, NULL, 'Nieuwe Hebriden'),
   (239, '5013', NULL, NULL, 'NZ', 'Nieuw-Zeeland'),
   (240, '6040', NULL, NULL, 'NE', 'Niger'),
   (241, '6005', NULL, NULL, 'NG', 'Nigeria'),
   (242, '9091', NULL, NULL, 'NU', 'Niue'),
   (243, '5016', NULL, 19900522, NULL, 'Noord-Jemen'),
   (244, '6049', 19450906, NULL, 'KP', 'Noord-Korea'),
   (245, '7071', NULL, 19641024, NULL, 'Noord-Rhodesië'),
   (246, '6026', 19450902, 19760702, NULL, 'Noord-Vietnam'),
   (247, '6027', NULL, NULL, 'NO', 'Noorwegen'),
   (248, '8016', NULL, NULL, 'NF', 'Norfolk'),
   (249, '9001', NULL, 19640706, NULL, 'Nyasaland'),
   (250, '6038', 19911231, NULL, 'UA', 'Oekraïne'),
   (251, '9070', NULL, 19910601, NULL, 'Umm Al-Qaiwain'),
   (252, '9081', NULL, 19910601, NULL, 'Urundi'),
   (253, '6050', 19911231, NULL, 'UZ', 'Oezbekistan'),
   (254, '7051', 19700809, NULL, 'OM', 'Oman'),
   (255, '0000', NULL, NULL, NULL, 'Onbekend'),
   (256, '5009', NULL, NULL, 'AT', 'Oostenrijk'),
   (257, '9071', NULL, 19910601, NULL, 'Oostenrijk-Hongarije'),
   (258, '8006', NULL, 19910601, NULL, 'Pacific-eilanden'),
   (259, '5086', NULL, 19460401, NULL, 'Pahang'),
   (260, '7020', NULL, NULL, 'PK', 'Pakistan'),
   (261, '7060', NULL, 19480514, 'PS', 'Palestina'),
   (262, '7037', NULL, NULL, 'PA', 'Panama'),
   (263, '8041', NULL, NULL, 'PZ', 'Panamakanaalzone'),
   (264, '8021', 19750916, NULL, 'PG', 'Papoea-Nieuw-Guinea'),
   (265, '5038', NULL, NULL, 'PY', 'Paraguay'),
   (266, '5087', NULL, 19460401, NULL, 'Perak'),
   (267, '5088', NULL, 19460401, NULL, 'Perlis'),
   (268, '7049', NULL, NULL, 'PE', 'Peru'),
   (269, '9017', NULL, 19910601, NULL, 'Phoenixeilanden'),
   (270, '5071', NULL, NULL, 'PN', 'Pitcairneilanden'),
   (271, '7028', NULL, NULL, 'PL', 'Polen'),
   (272, '7050', NULL, NULL, 'PT', 'Portugal'),
   (273, '7063', NULL, 19910601, NULL, 'Portugees-Afrika'),
   (274, '9020', NULL, 19740910, NULL, 'Portugees-Guinee'),
   (275, '5089', NULL, 19611218, NULL, 'Portugees-Indië'),
   (276, '9072', NULL, 19750625, NULL, 'Portugees Oost-Afrika'),
   (277, '6056', NULL, 19760717, NULL, 'Portugees-Timor'),
   (278, '9073', NULL, 19751111, NULL, 'Portugees West-Afrika'),
   (279, '8020', NULL, NULL, 'PR', 'Puerto Rico'),
   (280, '7077', NULL, 19910601, NULL, 'Ras al-Khaimah'),
   (281, '8044', 19941001, NULL, 'PW', 'Palau'),
   (282, '5073', NULL, NULL, 'RE', 'Réunion'),
   (283, '5004', 19651111, 19790601, NULL, 'Rhodesië'),
   (284, '9082', NULL, 19620701, NULL, 'Ruanda-Urundi'),
   (285, '7047', NULL, NULL, 'RO', 'Roemenië'),
   (286, '5053', 19911231, NULL, 'RU', 'Rusland'),
   (287, '7029', NULL, 19171107, NULL, 'Rusland (oud)'),
   (288, '6009', 19620701, NULL, 'RW', 'Rwanda'),
   (289, '8004', NULL, 19910601, NULL, 'Riukiu-eilanden'),
   (290, '7073', NULL, 19910601, NULL, 'Saarland'),
   (291, '5108', 20101010, NULL, 'BQ', 'Saba'),
   (292, '9060', NULL, 19910601, NULL, 'Sabah'),
   (293, '8037', NULL, NULL, 'KN', 'Saint Kitts en Nevis'),
   (294, '8042', 19670227, 19801219, NULL, 'Saint Kitts, Nevis en Anguilla'),
   (295, '5074', NULL, NULL, 'PM', 'Saint Pierre en Miquelon'),
   (296, '6028', NULL, NULL, 'SM', 'San Marino'),
   (297, '6059', NULL, NULL, 'ST', 'São Tomé en Principe'),
   (298, '5018', NULL, NULL, 'SA', 'Saoedi-Arabië'),
   (299, '9057', NULL, 19910601, NULL, 'Sarawak'),
   (300, '5090', NULL, 19460401, NULL, 'Selangor'),
   (301, '7021', NULL, NULL, 'SN', 'Senegal'),
   (302, '5103', 20060603, NULL, 'RS', 'Servië'),
   (303, '5102', 20030204, 20060603, 'CS', 'Servië en Montenegro'),
   (304, '8026', NULL, NULL, 'SC', 'Seychellen'),
   (305, '5075', NULL, 19910601, NULL, 'Seychellen en Amiranten'),
   (306, '7080', NULL, 19910601, NULL, 'Siam'),
   (307, '6051', NULL, NULL, 'SL', 'Sierra Leone'),
   (308, '5091', NULL, 19910601, NULL, 'Sikkim'),
   (309, '5037', NULL, NULL, 'SG', 'Singapore'),
   (310, '5109', 20101010, NULL, 'BQ', 'Sint Eustatius'),
   (311, '6060', NULL, NULL, 'SH', 'Sint-Helena'),
   (312, '8029', NULL, NULL, 'LC', 'Saint Lucia'),
   (313, '5110', 20101010, NULL, 'SX', 'Sint Maarten'),
   (314, '8039', NULL, 19791027, 'WV', 'Saint Vincent'),
   (315, '5092', 19791027, NULL, 'VC', 'Saint Vincent en de Grenadines'),
   (316, '9074', NULL, 19910601, NULL, 'Sharjah'),
   (317, '5049', 19920115, NULL, 'SI', 'Slovenië'),
   (318, '6067', 19930101, NULL, 'SK', 'Slowakije'),
   (319, '7034', NULL, NULL, 'SD', 'Soedan'),
   (320, '8022', 19780707, NULL, 'SB', 'Salomonseilanden'),
   (321, '6013', 19600701, NULL, 'SO', 'Somalië'),
   (322, '9049', 19171107, 19911231, NULL, 'Sovjet-Unie'),
   (323, '9044', NULL, 19910601, NULL, 'Spaans-Guinee'),
   (324, '9092', NULL, 19910601, NULL, 'Spaans Noord-Afrika'),
   (325, '7091', NULL, 19760226, NULL, 'Spaanse Sahara'),
   (326, '6037', NULL, NULL, 'ES', 'Spanje'),
   (327, '5093', NULL, 19910601, NULL, 'Spitsbergen'),
   (328, '7033', 19720522, NULL, 'LK', 'Sri Lanka'),
   (329, '9075', NULL, 19460401, NULL, 'Straits Settlements'),
   (330, '5007', NULL, NULL, 'SR', 'Suriname'),
   (331, '9095', NULL, NULL, 'SJ', 'Svalbardeilanden'),
   (332, '9036', NULL, NULL, 'SZ', 'Swaziland'),
   (333, '7009', NULL, NULL, 'SY', 'Syrië'),
   (334, '6057', 19911231, NULL, 'TJ', 'Tadzjikistan'),
   (335, '5052', NULL, NULL, 'TW', 'Taiwan'),
   (336, '7059', NULL, 19640427, NULL, 'Tanganyika'),
   (337, '7031', 19640427, NULL, 'TZ', 'Tanzania'),
   (338, '8018', NULL, 19910601, NULL, 'Tasmanië'),
   (339, '7042', NULL, NULL, 'TH', 'Thailand'),
   (340, '7055', NULL, NULL, NULL, 'Tibet'),
   (341, '5101', 20020520, NULL, 'TL', 'Timor Leste'),
   (342, '6063', 19911231, NULL, 'TM', 'Turkmenistan'),
   (343, '5023', NULL, NULL, 'TG', 'Togo'),
   (344, '7098', NULL, NULL, 'TK', 'Tokelau'),
   (345, '5076', NULL, NULL, 'TO', 'Tonga'),
   (346, '9088', 19230515, 19910601, NULL, 'Transjordanië'),
   (347, '5094', NULL, 19460401, NULL, 'Trengganu'),
   (348, '6044', NULL, NULL, 'TT', 'Trinidad en Tobago'),
   (349, '6061', NULL, NULL, 'TA', 'Tristan Da Cunha'),
   (350, '9054', NULL, 19711202, NULL, 'Trucial Oman'),
   (351, '6019', NULL, NULL, 'TD', 'Tsjaad'),
   (352, '6066', 19930101, NULL, 'CZ', 'Tsjechië'),
   (353, '7048', NULL, 19930101, NULL, 'Tsjecho-Slowakije'),
   (354, '5008', NULL, NULL, 'TN', 'Tunesië'),
   (355, '6043', NULL, NULL, 'TR', 'Turkije'),
   (356, '8019', NULL, NULL, 'TC', 'Turks- en Caicoseilanden'),
   (357, '7094', NULL, 19910601, NULL, 'Turkseilanden'),
   (358, '8028', 19751001, NULL, 'TV', 'Tuvalu'),
   (359, '7001', NULL, NULL, 'UG', 'Uganda'),
   (360, '7038', NULL, NULL, 'UY', 'Uruguay'),
   (361, '9090', 19800730, NULL, 'VU', 'Vanuatu'),
   (362, '5045', 19290211, NULL, 'VA', 'Vaticaanstad'),
   (363, '6010', NULL, NULL, 'VE', 'Venezuela'),
   (364, '7054', 19711202, NULL, 'AE', 'Verenigde Arabische Emiraten'),
   (365, '9047', 19580222, 19610928, NULL, 'Verenigde Arabische Republiek'),
   (366, '6014', NULL, NULL, 'US', 'Verenigde Staten van Amerika'),
   (367, '8024', NULL, NULL, 'VN', 'Vietnam'),
   (368, '8005', NULL, NULL, 'WK', 'Wake'),
   (369, '5077', NULL, NULL, 'WF', 'Wallis en Futuna'),
   (370, '9093', 19760226, NULL, 'EH', 'Westelijke Sahara'),
   (371, '6062', NULL, 19970704, 'WS', 'West-Samoa'),
   (372, '9015', NULL, 19910601, NULL, 'Windwardeilanden'),
   (373, '5050', 19711027, 19970715, 'ZR', 'Zaïre'),
   (374, '5028', 19641024, NULL, 'ZM', 'Zambia'),
   (375, '9051', NULL, 19640427, NULL, 'Zanzibar'),
   (376, '8031', 19790601, NULL, 'ZW', 'Zimbabwe'),
   (377, '5014', NULL, NULL, 'ZA', 'Zuid-Afrika'),
   (378, '9042', 19590211, 19671127, NULL, 'Zuid-Arabische Federatie'),
   (379, '7012', 19671127, 19900522, NULL, 'Zuid-Jemen'),
   (380, '6036', NULL, NULL, 'KR', 'Zuid-Korea'),
   (381, '7072', NULL, 19651111, NULL, 'Zuid-Rhodesië'),
   (382, '5036', NULL, 19760702, NULL, 'Zuid-Vietnam'),
   (383, '5078', NULL, 19900321, NULL, 'Zuidwest-Afrika'),
   (384, '5039', NULL, NULL, 'SE', 'Zweden'),
   (385, '5003', NULL, NULL, 'CH', 'Zwitserland'),
   (386, '5111', 20110709, NULL, 'SS', 'Zuid-Soedan'),
   (387, '7053', 19970704, NULL, NULL, 'Samoa');


--------------------------------------------------------------------------------
-- Stamgegeven: Nationaliteit
--------------------------------------------------------------------------------
INSERT INTO Kern.Nation (ID, Code, Naam, DatAanvGel, DatEindeGel) VALUES
   (1, '0000', 'Onbekend', NULL, NULL),
   (2, '0001', 'Nederlandse', NULL, NULL),
   (4, '0027', 'Slowaakse', 19930101, NULL),
   (5, '0028', 'Tsjechische', 19930101, NULL),
   (6, '0029', 'Burger van Bosnië-Herzegovina', 19920406, NULL),
   (7, '0030', 'Georgische', 19911231, NULL),
   (8, '0031', 'Turkmeense', 19911231, NULL),
   (9, '0032', 'Tadzjiekse', 19911231, NULL),
   (10, '0033', 'Oezbeekse', 19911231, NULL),
   (11, '0034', 'Oekraïense', 19911231, NULL),
   (12, '0035', 'Kirgizische', 19911231, NULL),
   (13, '0036', 'Moldavische', 19911231, NULL),
   (14, '0037', 'Kazachse', 19911231, NULL),
   (15, '0038', 'Belarussische', 19911231, NULL),
   (16, '0039', 'Azerbeidzjaanse', 19911231, NULL),
   (17, '0040', 'Armeense', 19911231, NULL),
   (18, '0041', 'Russische', 19911231, NULL),
   (19, '0042', 'Sloveense', 19920115, NULL),
   (20, '0043', 'Kroatische', 19920115, NULL),
   (21, '0044', 'Letse', 19910828, NULL),
   (22, '0045', 'Estische', 19910828, NULL),
   (23, '0046', 'Litouwse', 19910828, NULL),
   (24, '0047', 'Marshalleilandse', NULL, NULL),
   (25, '0048', 'Myanmarese', NULL, NULL),
   (26, '0049', 'Namibische', NULL, NULL),
   (27, '0050', 'Albanese', NULL, NULL),
   (28, '0051', 'Andorrese', NULL, NULL),
   (29, '0052', 'Belgische', NULL, NULL),
   (30, '0053', 'Bulgaarse', NULL, NULL),
   (31, '0054', 'Deense', NULL, NULL),
   (32, '0055', 'Burger van de Bondsrepubliek Duitsland', NULL, NULL),
   (33, '0056', 'Finse', NULL, NULL),
   (34, '0057', 'Franse', NULL, NULL),
   (35, '0058', 'Jemenitische', 19900522, NULL),
   (36, '0059', 'Griekse', NULL, NULL),
   (37, '0060', 'Brits burger', NULL, NULL),
   (38, '0061', 'Hongaarse', NULL, NULL),
   (39, '0062', 'Ierse', NULL, NULL),
   (40, '0063', 'IJslandse', NULL, NULL),
   (41, '0064', 'Italiaanse', NULL, NULL),
   (42, '0065', 'Joegoslavische', NULL, 20040201),
   (43, '0066', 'Liechtensteinse', NULL, NULL),
   (44, '0067', 'Luxemburgse', NULL, NULL),
   (45, '0068', 'Maltese', NULL, NULL),
   (46, '0069', 'Monegaskische', NULL, NULL),
   (47, '0070', 'Noorse', NULL, NULL),
   (48, '0071', 'Oostenrijkse', NULL, NULL),
   (49, '0072', 'Poolse', NULL, NULL),
   (50, '0073', 'Portugese', NULL, NULL),
   (51, '0074', 'Roemeense', NULL, NULL),
   (52, '0075', 'Burger van de Sovjet-Unie', NULL, 19911231),
   (53, '0076', 'San Marinese', NULL, NULL),
   (54, '0077', 'Spaanse', NULL, NULL),
   (55, '0078', 'Tsjecho-Slowaakse', NULL, 19930101),
   (56, '0079', 'Vaticaanse', NULL, NULL),
   (57, '0080', 'Zweedse', NULL, NULL),
   (58, '0081', 'Zwitserse', NULL, NULL),
   (59, '0082', 'Oost-Duitse', NULL, 19901003),
   (60, '0083', 'Brits onderdaan', NULL, NULL),
   (61, '0084', 'Eritrese', 19930528, NULL),
   (62, '0085', 'Brits overzees burger', NULL, NULL),
   (63, '0086', 'Macedonische', 19930419, NULL),
   (64, '0087', 'Kosovaarse', 20080615, NULL),
   (65, '0100', 'Algerijnse', NULL, NULL),
   (66, '0101', 'Angolese', NULL, NULL),
   (67, '0104', 'Burundese', NULL, NULL),
   (68, '0105', 'Botswaanse', NULL, NULL),
   (69, '0106', 'Burkinese', NULL, NULL),
   (70, '0108', 'Centraal-Afrikaanse', NULL, NULL),
   (71, '0109', 'Comorese', NULL, NULL),
   (72, '0110', 'Burger van Congo', NULL, NULL),
   (73, '0111', 'Beninse', NULL, NULL),
   (74, '0112', 'Egyptische', NULL, NULL),
   (75, '0113', 'Equatoriaal-Guinese', NULL, NULL),
   (76, '0114', 'Ethiopische', NULL, NULL),
   (77, '0115', 'Djiboutiaanse', NULL, NULL),
   (78, '0116', 'Gabonese', NULL, NULL),
   (79, '0117', 'Gambiaanse', NULL, NULL),
   (80, '0118', 'Ghanese', NULL, NULL),
   (81, '0119', 'Guinese', NULL, NULL),
   (82, '0120', 'Ivoriaanse', NULL, NULL),
   (83, '0121', 'Kaapverdische', NULL, NULL),
   (84, '0122', 'Kameroense', NULL, NULL),
   (85, '0123', 'Kenyaanse', NULL, NULL),
   (86, '0124', 'Zaïrese', NULL, 19970715),
   (87, '0125', 'Lesothaanse', NULL, NULL),
   (88, '0126', 'Liberiaanse', NULL, NULL),
   (89, '0127', 'Libische', NULL, NULL),
   (90, '0128', 'Malagassische', NULL, NULL),
   (91, '0129', 'Malawische', NULL, NULL),
   (92, '0130', 'Malinese', NULL, NULL),
   (93, '0131', 'Marokkaanse', NULL, NULL),
   (94, '0132', 'Mauritaanse', NULL, NULL),
   (95, '0133', 'Mauritiaanse', NULL, NULL),
   (96, '0134', 'Mozambikaanse', NULL, NULL),
   (97, '0135', 'Swazische', NULL, NULL),
   (98, '0136', 'Nigerese', NULL, NULL),
   (99, '0137', 'Nigeriaanse', NULL, NULL),
   (100, '0138', 'Ugandese', NULL, NULL),
   (101, '0139', 'Guinee-Bissause', NULL, NULL),
   (102, '0140', 'Zuid-Afrikaanse', NULL, NULL),
   (103, '0142', 'Zimbabwaanse', NULL, NULL),
   (104, '0143', 'Rwandese', NULL, NULL),
   (105, '0144', 'Burger van São Tomé en Principe', NULL, NULL),
   (106, '0145', 'Senegalese', NULL, NULL),
   (107, '0147', 'Sierra Leoonse', NULL, NULL),
   (108, '0148', 'Soedanese', NULL, NULL),
   (109, '0149', 'Somalische', NULL, NULL),
   (110, '0151', 'Tanzaniaanse', NULL, NULL),
   (111, '0152', 'Togolese', NULL, NULL),
   (112, '0154', 'Tsjadische', NULL, NULL),
   (113, '0155', 'Tunesische', NULL, NULL),
   (114, '0156', 'Zambiaanse', NULL, NULL),
   (115, '0200', 'Bahamaanse', NULL, NULL),
   (116, '0202', 'Belizaanse', NULL, NULL),
   (117, '0204', 'Canadese', NULL, NULL),
   (118, '0205', 'Costa Ricaanse', NULL, NULL),
   (119, '0206', 'Cubaanse', NULL, NULL),
   (120, '0207', 'Dominicaanse', NULL, NULL),
   (121, '0208', 'Salvadoraanse', NULL, NULL),
   (122, '0211', 'Guatemalaanse', NULL, NULL),
   (123, '0212', 'Haïtiaanse', NULL, NULL),
   (124, '0213', 'Hondurese', NULL, NULL),
   (125, '0214', 'Jamaicaanse', NULL, NULL),
   (126, '0216', 'Mexicaanse', NULL, NULL),
   (127, '0218', 'Nicaraguaanse', NULL, NULL),
   (128, '0219', 'Panamese', NULL, NULL),
   (129, '0222', 'Burger van Trinidad en Tobago', NULL, NULL),
   (130, '0223', 'Amerikaans burger', NULL, NULL),
   (131, '0250', 'Argentijnse', NULL, NULL),
   (132, '0251', 'Barbadaanse', NULL, NULL),
   (133, '0252', 'Boliviaanse', NULL, NULL),
   (134, '0253', 'Braziliaanse', NULL, NULL),
   (135, '0254', 'Chileense', NULL, NULL),
   (136, '0255', 'Colombiaanse', NULL, NULL),
   (137, '0256', 'Ecuadoraanse', NULL, NULL),
   (138, '0259', 'Guyaanse', NULL, NULL),
   (139, '0261', 'Paraguayaanse', NULL, NULL),
   (140, '0262', 'Peruaanse', NULL, NULL),
   (141, '0263', 'Surinaamse', NULL, NULL),
   (142, '0264', 'Uruguayaanse', NULL, NULL),
   (143, '0265', 'Venezolaanse', NULL, NULL),
   (144, '0267', 'Grenadaanse', NULL, NULL),
   (145, '0268', 'Burger van Saint Kitts en Nevis', NULL, NULL),
   (146, '0300', 'Afghaanse', NULL, NULL),
   (147, '0301', 'Bahreinse', NULL, NULL),
   (148, '0302', 'Bhutaanse', NULL, NULL),
   (149, '0303', 'Burmaanse', NULL, 19890618),
   (150, '0304', 'Bruneise', NULL, NULL),
   (151, '0305', 'Cambodjaanse', NULL, NULL),
   (152, '0306', 'Sri Lankaanse', NULL, NULL),
   (153, '0307', 'Chinese', NULL, NULL),
   (154, '0308', 'Cyprische', NULL, NULL),
   (155, '0309', 'Filipijnse', NULL, NULL),
   (156, '0310', 'Taiwanese', NULL, NULL),
   (157, '0312', 'Indiase', NULL, NULL),
   (158, '0313', 'Indonesische', NULL, NULL),
   (159, '0314', 'Iraakse', NULL, NULL),
   (160, '0315', 'Iraanse', NULL, NULL),
   (161, '0316', 'Israëlische', NULL, NULL),
   (162, '0317', 'Japanse', NULL, NULL),
   (163, '0318', 'Noord-Jemenitische', NULL, 19900522),
   (164, '0319', 'Jordaanse', NULL, NULL),
   (165, '0320', 'Koeweitse', NULL, NULL),
   (166, '0321', 'Laotiaanse', NULL, NULL),
   (167, '0322', 'Libanese', NULL, NULL),
   (168, '0324', 'Maldivische', NULL, NULL),
   (169, '0325', 'Maleisische', NULL, NULL),
   (170, '0326', 'Mongolische', NULL, NULL),
   (171, '0327', 'Omaanse', NULL, NULL),
   (172, '0328', 'Nepalese', NULL, NULL),
   (173, '0329', 'Noord-Koreaanse', NULL, NULL),
   (174, '0331', 'Pakistaanse', NULL, NULL),
   (175, '0333', 'Qatarese', NULL, NULL),
   (176, '0334', 'Saoedi-Arabische', NULL, NULL),
   (177, '0335', 'Singaporese', NULL, NULL),
   (178, '0336', 'Syrische', NULL, NULL),
   (179, '0337', 'Thaise', NULL, NULL),
   (180, '0338', 'Burger van de Verenigde Arabische Emiraten', NULL, NULL),
   (181, '0339', 'Turkse', NULL, NULL),
   (182, '0340', 'Zuid-Jemenitische', NULL, 19900522),
   (183, '0341', 'Zuid-Koreaanse', NULL, NULL),
   (184, '0342', 'Vietnamese', NULL, NULL),
   (185, '0345', 'Bengalese', NULL, NULL),
   (186, '0400', 'Australische', NULL, NULL),
   (187, '0401', 'Papoea-Nieuw-Guinese', NULL, NULL),
   (188, '0402', 'Nieuw-Zeelandse', NULL, NULL),
   (189, '0404', 'West-Samoaanse', NULL, 19970704),
   (190, '0421', 'Burger van Antigua en Barbuda', NULL, NULL),
   (191, '0424', 'Vanuatuaanse', NULL, NULL),
   (192, '0425', 'Fijische', NULL, NULL),
   (193, '0429', 'Burger van Britse afhankelijke gebieden', NULL, NULL),
   (194, '0430', 'Tongaanse', NULL, NULL),
   (195, '0431', 'Nauruaanse', NULL, NULL),
   (196, '0437', 'Amerikaans onderdaan', NULL, NULL),
   (197, '0442', 'Salomonseilandse', NULL, NULL),
   (198, '0444', 'Seychelse', NULL, NULL),
   (199, '0445', 'Kiribatische', NULL, NULL),
   (200, '0446', 'Tuvaluaanse', NULL, NULL),
   (201, '0447', 'Saint Luciaanse', NULL, NULL),
   (202, '0448', 'Burger van Dominica', NULL, NULL),
   (203, '0449', 'Burger van Saint Vincent en de Grenadines', NULL, NULL),
   (204, '0450', 'British National (overseas)', 19870701, NULL),
   (205, '0451', 'Burger van Democratische Republiek Congo', 19970517, NULL),
   (206, '0452', 'Burger van Timor Leste', 20020520, NULL),
   (207, '0453', 'Burger van Servië en Montenegro', 20030204, 20060603),
   (208, '0454', 'Servische', 20060603, NULL),
   (209, '0455', 'Montenegrijnse', 20060603, NULL),
   (212, '0157', 'Zuid-Soedanese', 20110709, NULL),
   (213, '0405', 'Samoaanse', 19970704, NULL),
   (214, '0432', 'Palause', 19941001, NULL),
   (215, '0443', 'Micronesische', NULL, NULL);


--------------------------------------------------------------------------------
-- Stamgegeven: Soort Nederlands reisdocument
--------------------------------------------------------------------------------
INSERT INTO Kern.SrtNLReisdoc (ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES
   (1, '?', 'Onbekend', NULL, NULL),
   (2, 'BJ', 'Identiteitskaart (toeristenkaart) BJ', 19920401, 19950101),
   (3, 'EK', 'Europese identiteitskaart', 19950101, 20011001),
   (4, 'IA', 'Identiteitskaart (toeristenkaart) A', NULL, 19920101),
   (5, 'IB', 'Identiteitskaart (toeristenkaart) B', NULL, 19950101),
   (6, 'IC', 'Identiteitskaart (toeristenkaart) C', NULL, 19920101),
   (7, 'ID', 'Gemeentelijke Identiteitskaart', 19930101, 19950101),
   (8, 'KE', 'Kaart met paspoortboekje, 64 pag.', 19920101, 19920101),
   (9, 'KN', 'Kaart met paspoortboekje, 32 pag.', 19920101, 19920101),
   (10, 'KZ', 'Kaart zonder paspoortboekje', 19920101, 19920101),
   (11, 'LP', 'Laissez-passer', 19950101, NULL),
   (12, 'NB', 'Nooddocument (model reisdocument vreemdelingen)', 19950101, 20010401),
   (13, 'NI', 'Nederlandse identiteitskaart', 20011001, NULL),
   (14, 'NN', 'Noodpaspoort (model nationaal paspoort)', 19950101, NULL),
   (15, 'NP', 'Noodpaspoort', NULL, 19950101),
   (16, 'NV', 'Nooddocument (model reisdocument vluchtelingen)', 19950101, 20010401),
   (17, 'PB', 'Reisdocument voor vreemdelingen', NULL, NULL),
   (18, 'PD', 'Diplomatiek paspoort', NULL, NULL),
   (19, 'PF', 'Faciliteitenpaspoort', NULL, NULL),
   (20, 'PN', 'Nationaal paspoort', NULL, NULL),
   (21, 'PV', 'Reisdocument voor vluchtelingen', NULL, NULL),
   (22, 'PZ', 'Dienstpaspoort', NULL, NULL),
   (23, 'R1', 'Reisdocument ouder1', 20010201, NULL),
   (24, 'R2', 'Reisdocument ouder2', 20010201, NULL),
   (25, 'RD', 'Reisdocument voogd', NULL, NULL),
   (26, 'RM', 'Reisdocument moeder', NULL, 20010201),
   (27, 'RV', 'Reisdocument vader', NULL, 20010201),
   (28, 'TE', 'Tweede paspoort (zakenpaspoort)', 19920401, NULL),
   (29, 'TN', 'Tweede paspoort', 19920401, NULL),
   (30, 'ZN', 'Nationaal paspoort (zakenpaspoort)', 19920401, NULL);


--------------------------------------------------------------------------------
-- Stamgegeven: Partij Bijhouders
--------------------------------------------------------------------------------
INSERT INTO Kern.Partij (ID, Naam, Srt, DatIngang, DatEinde, Code, Indverstrbeperkingmogelijk, IndAG, DatIngangVrijBer, IndAGVrijBer) VALUES
   (1, 'Onbekend', NULL, 19000101, NULL, '000000', false, true, 19000101, true),
   (2, 'Regering en Staten-Generaal', NULL, 18140101, NULL, '199900', false, true, 18140101, true),
   (3, 'Minister', NULL, 18140101, NULL, '199901', false, true, 18140101, true),
   (2000, 'Migratievoorziening', NULL, 20120101, NULL, '199902', false, true, 20120101, true),
   (2001, 'BRP Voorziening', NULL, 20120101, NULL, '199903', false, true, 20120101, true),
   (2002, 'Noodvoorziening loket-inschrijving', NULL, 20160106, NULL, '999999', false, true, 20160106, true),
   (4, 'Gemeente Onbekend', NULL, 19000101, NULL, '000001', false, true, 19000101, true),
   (5, 'Gemeente Adorp', NULL, 19000101, 19900101, '000101', false, true, null, false),
   (6, 'Gemeente Aduard', NULL, 19000101, 19900101, '000201', false, true, null, false),
   (7, 'Gemeente Appingedam', NULL, 19000101, NULL, '000301', false, true, 19000101, true),
   (8, 'Gemeente Baflo', NULL, 19000101, 19900101, '000401', false, true, null, false),
   (9, 'Gemeente Bedum', NULL, 19000101, NULL, '000501', false, true, 19000101, true),
   (10, 'Gemeente Beerta', NULL, 19000101, 19910701, '000601', false, true, null, false),
   (11, 'Gemeente Bellingwedde', NULL, 19680101, NULL, '000701', false, true, 19680101, true),
   (12, 'Gemeente Bierum', NULL, 19000101, 19900101, '000801', false, true, null, false),
   (13, 'Gemeente Ten Boer', NULL, 19000101, NULL, '000901', false, true, 19000101, true),
   (14, 'Gemeente Delfzijl', NULL, 19000101, NULL, '001001', false, true, 19000101, true),
   (15, 'Gemeente Eenrum', NULL, 19000101, 19900101, '001101', false, true, null, false),
   (16, 'Gemeente Ezinge', NULL, 19000101, 19900101, '001201', false, true, null, false),
   (17, 'Gemeente Finsterwolde', NULL, 19000101, 19900101, '001301', false, true, null, false),
   (18, 'Gemeente Groningen', NULL, 19000101, NULL, '001401', false, true, 19000101, true),
   (19, 'Gemeente Grootegast', NULL, 19000101, NULL, '001501', false, true, 19000101, true),
   (20, 'Gemeente Grijpskerk', NULL, 19000101, 19900101, '001601', false, true, null, false),
   (21, 'Gemeente Haren', NULL, 19000101, NULL, '001701', false, true, 19000101, true),
   (22, 'Gemeente Hoogezand-Sappemeer', NULL, 19490401, NULL, '001801', false, true, 19490401, true),
   (23, 'Gemeente Hefshuizen', NULL, 19790101, 19920101, '001901', false, true, null, false),
   (24, 'Gemeente Kantens', NULL, 19000101, 19900101, '002001', false, true, null, false),
   (25, 'Gemeente Kloosterburen', NULL, 19000101, 19900101, '002101', false, true, null, false),
   (26, 'Gemeente Leek', NULL, 19000101, NULL, '002201', false, true, 19000101, true),
   (27, 'Gemeente Leens', NULL, 19000101, 19900101, '002301', false, true, null, false),
   (28, 'Gemeente Loppersum', NULL, 19000101, NULL, '002401', false, true, 19000101, true),
   (29, 'Gemeente Marum', NULL, 19000101, NULL, '002501', false, true, 19000101, true),
   (30, 'Gemeente Meeden', NULL, 19000101, 19900101, '002601', false, true, null, false),
   (31, 'Gemeente Middelstum', NULL, 19000101, 19900101, '002701', false, true, null, false),
   (32, 'Gemeente Midwolda', NULL, 19000101, 19900101, '002801', false, true, null, false),
   (33, 'Gemeente Muntendam', NULL, 19000101, 19900101, '002901', false, true, null, false),
   (34, 'Gemeente Nieuwe Pekela', NULL, 19000101, 19900101, '003001', false, true, null, false),
   (35, 'Gemeente Nieuweschans', NULL, 19000101, 19900101, '003101', false, true, null, false),
   (36, 'Gemeente Nieuwolda', NULL, 19000101, 19900101, '003201', false, true, null, false),
   (37, 'Gemeente Oosterbroek', NULL, 19650701, 19910201, '003301', false, true, null, false),
   (38, 'Gemeente Almere', NULL, 19840101, NULL, '003401', false, true, 19840101, true),
   (39, 'Gemeente Oldehove', NULL, 19000101, 19900101, '003501', false, true, null, false),
   (40, 'Gemeente Oldekerk', NULL, 19000101, 19900101, '003601', false, true, null, false),
   (41, 'Gemeente Stadskanaal', NULL, 19690101, NULL, '003701', false, true, 19690101, true),
   (42, 'Gemeente Oude Pekela', NULL, 19000101, 19900101, '003801', false, true, null, false),
   (43, 'Gemeente Scheemda', NULL, 19000101, 20100101, '003901', false, true, null, false),
   (44, 'Gemeente Slochteren', NULL, 19000101, NULL, '004001', false, true, 19000101, true),
   (45, 'Gemeente Stedum', NULL, 19000101, 19900101, '004101', false, true, null, false),
   (46, 'Gemeente Termunten', NULL, 19000101, 19900101, '004201', false, true, null, false),
   (47, 'Gemeente Uithuizen', NULL, 19000101, 19790101, '004301', false, true, null, false),
   (48, 'Gemeente Uithuizermeeden', NULL, 19000101, 19790101, '004401', false, true, null, false),
   (49, 'Gemeente Ulrum', NULL, 19000101, 19920101, '004501', false, true, null, false),
   (50, 'Gemeente Usquert', NULL, 19000101, 19900101, '004601', false, true, null, false),
   (51, 'Gemeente Veendam', NULL, 19000101, NULL, '004701', false, true, 19000101, true),
   (52, 'Gemeente Vlagtwedde', NULL, 19000101, NULL, '004801', false, true, 19000101, true),
   (53, 'Gemeente Warffum', NULL, 19000101, 19900101, '004901', false, true, null, false),
   (54, 'Gemeente Zeewolde', NULL, 19840101, NULL, '005001', false, true, 19840101, true),
   (55, 'Gemeente Skarsterlân', NULL, 19850301, 20140101, '005101', false, true, null, false),
   (56, 'Gemeente Winschoten', NULL, 19000101, 20100101, '005201', false, true, null, false),
   (57, 'Gemeente Winsum', NULL, 19000101, NULL, '005301', false, true, 19000101, true),
   (58, 'Gemeente ''t Zandt', NULL, 19000101, 19900101, '005401', false, true, null, false),
   (59, 'Gemeente Boarnsterhim', NULL, 19850103, 20140101, '005501', false, true, null, false),
   (60, 'Gemeente Zuidhorn', NULL, 19000101, NULL, '005601', false, true, 19000101, true),
   (61, 'Gemeente Boornsterhem', NULL, 19840101, 19850103, '005701', false, true, null, false),
   (62, 'Gemeente Dongeradeel', NULL, 19840101, NULL, '005801', false, true, 19840101, true),
   (63, 'Gemeente Achtkarspelen', NULL, 19000101, NULL, '005901', false, true, 19000101, true),
   (64, 'Gemeente Ameland', NULL, 19000101, NULL, '006001', false, true, 19000101, true),
   (65, 'Gemeente Baarderadeel', NULL, 19000101, 19840101, '006101', false, true, null, false),
   (66, 'Gemeente Barradeel', NULL, 19000101, 19840101, '006201', false, true, null, false),
   (67, 'Gemeente het Bildt', NULL, 19000101, NULL, '006301', false, true, 19000101, true),
   (68, 'Gemeente Bolsward', NULL, 19000101, 20110101, '006401', false, true, null, false),
   (69, 'Gemeente Dantumadeel', NULL, 19000101, 20090101, '006501', false, true, null, false),
   (70, 'Gemeente Dokkum', NULL, 19000101, 19840101, '006601', false, true, null, false),
   (71, 'Gemeente Doniawerstal', NULL, 19000101, 19840101, '006701', false, true, null, false),
   (72, 'Gemeente Ferwerderadeel', NULL, 19000101, 19990101, '006801', false, true, null, false),
   (73, 'Gemeente Franeker', NULL, 19000101, 19840101, '006901', false, true, null, false),
   (74, 'Gemeente Franekeradeel', NULL, 19000101, NULL, '007001', false, true, 19000101, true),
   (75, 'Gemeente Gaasterland', NULL, 19000101, 19850605, '007101', false, true, null, false),
   (76, 'Gemeente Harlingen', NULL, 19000101, NULL, '007201', false, true, 19000101, true),
   (77, 'Gemeente Haskerland', NULL, 19000101, 19840101, '007301', false, true, null, false),
   (78, 'Gemeente Heerenveen', NULL, 19340701, NULL, '007401', false, true, 19340701, true),
   (79, 'Gemeente Hemelumer Oldeferd', NULL, 19560101, 19840101, '007501', false, true, null, false),
   (80, 'Gemeente Hennaarderadeel', NULL, 19000101, 19840101, '007601', false, true, null, false),
   (81, 'Gemeente Hindeloopen', NULL, 19000101, 19840101, '007701', false, true, null, false),
   (82, 'Gemeente Idaarderadeel', NULL, 19000101, 19840101, '007801', false, true, null, false),
   (83, 'Gemeente Kollumerland en Nieuwkruisland', NULL, 19000101, NULL, '007901', false, true, 19000101, true),
   (84, 'Gemeente Leeuwarden', NULL, 19000101, NULL, '008001', false, true, 19000101, true),
   (85, 'Gemeente Leeuwarderadeel', NULL, 19000101, NULL, '008101', false, true, 19000101, true),
   (86, 'Gemeente Lemsterland', NULL, 19000101, 20140101, '008201', false, true, null, false),
   (87, 'Gemeente Menaldumadeel', NULL, 19000101, 20110101, '008301', false, true, null, false),
   (88, 'Gemeente Oostdongeradeel', NULL, 19000101, 19840101, '008401', false, true, null, false),
   (89, 'Gemeente Ooststellingwerf', NULL, 19000101, NULL, '008501', false, true, 19000101, true),
   (90, 'Gemeente Opsterland', NULL, 19000101, NULL, '008601', false, true, 19000101, true),
   (91, 'Gemeente Rauwerderhem', NULL, 19000101, 19840101, '008701', false, true, null, false),
   (92, 'Gemeente Schiermonnikoog', NULL, 19000101, NULL, '008801', false, true, 19000101, true),
   (93, 'Gemeente Sloten (F)', NULL, 19000101, 19840101, '008901', false, true, null, false),
   (94, 'Gemeente Smallingerland', NULL, 19000101, NULL, '009001', false, true, 19000101, true),
   (95, 'Gemeente Sneek', NULL, 19000101, 20110101, '009101', false, true, null, false),
   (96, 'Gemeente Stavoren', NULL, 19000101, 19840101, '009201', false, true, null, false),
   (97, 'Gemeente Terschelling', NULL, 19000101, NULL, '009301', false, true, 19000101, true),
   (98, 'Gemeente Tietjerksteradeel', NULL, 19000101, 19890101, '009401', false, true, null, false),
   (99, 'Gemeente Utingeradeel', NULL, 19000101, 19840101, '009501', false, true, null, false),
   (100, 'Gemeente Vlieland', NULL, 19000101, NULL, '009601', false, true, 19000101, true),
   (101, 'Gemeente Westdongeradeel', NULL, 19000101, 19840101, '009701', false, true, null, false),
   (102, 'Gemeente Weststellingwerf', NULL, 19000101, NULL, '009801', false, true, 19000101, true),
   (103, 'Gemeente Wonseradeel', NULL, 19000101, 19870101, '009901', false, true, null, false),
   (104, 'Gemeente Workum', NULL, 19000101, 19840101, '010001', false, true, null, false),
   (105, 'Gemeente Wymbritseradeel', NULL, 19000101, 19860101, '010101', false, true, null, false),
   (106, 'Gemeente IJlst', NULL, 19000101, 19840101, '010201', false, true, null, false),
   (107, 'Gemeente Littenseradeel', NULL, 19840101, 19850126, '010301', false, true, null, false),
   (108, 'Gemeente Nijefurd', NULL, 19840101, 20110101, '010401', false, true, null, false),
   (109, 'Gemeente Anloo', NULL, 19000101, 19980101, '010501', false, true, null, false),
   (110, 'Gemeente Assen', NULL, 19000101, NULL, '010601', false, true, 19000101, true),
   (111, 'Gemeente Beilen', NULL, 19000101, 19980101, '010701', false, true, null, false),
   (112, 'Gemeente Borger', NULL, 19000101, 19980101, '010801', false, true, null, false),
   (113, 'Gemeente Coevorden', NULL, 19000101, NULL, '010901', false, true, 19000101, true),
   (114, 'Gemeente Dalen', NULL, 19000101, 19980101, '011001', false, true, null, false),
   (115, 'Gemeente Diever', NULL, 19000101, 19980101, '011101', false, true, null, false),
   (116, 'Gemeente Dwingeloo', NULL, 19000101, 19980101, '011201', false, true, null, false),
   (117, 'Gemeente Eelde', NULL, 19000101, 19980101, '011301', false, true, null, false),
   (118, 'Gemeente Emmen', NULL, 19000101, NULL, '011401', false, true, 19000101, true),
   (119, 'Gemeente Gasselte', NULL, 19000101, 19980101, '011501', false, true, null, false),
   (120, 'Gemeente Gieten', NULL, 19000101, 19980101, '011601', false, true, null, false),
   (121, 'Gemeente Havelte', NULL, 19000101, 19980101, '011701', false, true, null, false),
   (122, 'Gemeente Hoogeveen', NULL, 19000101, NULL, '011801', false, true, 19000101, true),
   (123, 'Gemeente Meppel', NULL, 19000101, NULL, '011901', false, true, 19000101, true),
   (124, 'Gemeente Norg', NULL, 19000101, 19980101, '012001', false, true, null, false),
   (125, 'Gemeente Nijeveen', NULL, 19000101, 19980101, '012101', false, true, null, false),
   (126, 'Gemeente Odoorn', NULL, 19000101, 19980101, '012201', false, true, null, false),
   (127, 'Gemeente Oosterhesselen', NULL, 19000101, 19980101, '012301', false, true, null, false),
   (128, 'Gemeente Peize', NULL, 19000101, 19980101, '012401', false, true, null, false),
   (129, 'Gemeente Roden', NULL, 19000101, 19980101, '012501', false, true, null, false),
   (130, 'Gemeente Rolde', NULL, 19000101, 19980101, '012601', false, true, null, false),
   (131, 'Gemeente Ruinen', NULL, 19000101, 19980101, '012701', false, true, null, false),
   (132, 'Gemeente Ruinerwold', NULL, 19000101, 19980101, '012801', false, true, null, false),
   (133, 'Gemeente Schoonebeek', NULL, 18840501, 19980101, '012901', false, true, null, false),
   (134, 'Gemeente Sleen', NULL, 19000101, 19980101, '013001', false, true, null, false),
   (135, 'Gemeente Smilde', NULL, 19000101, 19980101, '013101', false, true, null, false),
   (136, 'Gemeente Vledder', NULL, 19000101, 19980101, '013201', false, true, null, false),
   (137, 'Gemeente Vries', NULL, 19000101, 19980101, '013301', false, true, null, false),
   (138, 'Gemeente Westerbork', NULL, 19000101, 19980101, '013401', false, true, null, false),
   (139, 'Gemeente de Wijk', NULL, 19000101, 19980101, '013501', false, true, null, false),
   (140, 'Gemeente Zuidlaren', NULL, 19000101, 19991201, '013601', false, true, null, false),
   (141, 'Gemeente Zuidwolde', NULL, 19000101, 19980101, '013701', false, true, null, false),
   (142, 'Gemeente Zweeloo', NULL, 19000101, 19980101, '013801', false, true, null, false),
   (143, 'Gemeente Scharsterland', NULL, 19840101, 19850301, '013901', false, true, null, false),
   (144, 'Gemeente Littenseradiel', NULL, 19850126, NULL, '014001', false, true, 19850126, true),
   (145, 'Gemeente Almelo', NULL, 19130101, NULL, '014101', false, true, 19130101, true),
   (146, 'Gemeente Ambt Delden', NULL, 19000101, 20010101, '014201', false, true, null, false),
   (147, 'Gemeente Avereest', NULL, 19000101, 20010101, '014301', false, true, null, false),
   (148, 'Gemeente Bathmen', NULL, 19000101, 20050101, '014401', false, true, null, false),
   (149, 'Gemeente Blankenham', NULL, 19000101, 19730101, '014501', false, true, null, false),
   (150, 'Gemeente Blokzijl', NULL, 19000101, 19730101, '014601', false, true, null, false),
   (151, 'Gemeente Borne', NULL, 19000101, NULL, '014701', false, true, 19000101, true),
   (152, 'Gemeente Dalfsen', NULL, 19000101, NULL, '014801', false, true, 19000101, true),
   (153, 'Gemeente Denekamp', NULL, 19000101, 20020601, '014901', false, true, null, false),
   (154, 'Gemeente Deventer', NULL, 19000101, NULL, '015001', false, true, 19000101, true),
   (155, 'Gemeente Diepenheim', NULL, 19000101, 20010101, '015101', false, true, null, false),
   (156, 'Gemeente Diepenveen', NULL, 19000101, 19990101, '015201', false, true, null, false),
   (157, 'Gemeente Enschede', NULL, 19000101, NULL, '015301', false, true, 19000101, true),
   (158, 'Gemeente Genemuiden', NULL, 19000101, 20010101, '015401', false, true, null, false),
   (159, 'Gemeente Giethoorn', NULL, 19000101, 19730101, '015501', false, true, null, false),
   (160, 'Gemeente Goor', NULL, 19000101, 20010101, '015601', false, true, null, false),
   (161, 'Gemeente Gramsbergen', NULL, 19000101, 20010101, '015701', false, true, null, false),
   (162, 'Gemeente Haaksbergen', NULL, 19000101, NULL, '015801', false, true, 19000101, true),
   (163, 'Gemeente Den Ham', NULL, 19000101, 20010101, '015901', false, true, null, false),
   (164, 'Gemeente Hardenberg', NULL, 19410501, NULL, '016001', false, true, 19410501, true),
   (165, 'Gemeente Hasselt', NULL, 19000101, 20010101, '016101', false, true, null, false),
   (166, 'Gemeente Heino', NULL, 19000101, 20010101, '016201', false, true, null, false),
   (167, 'Gemeente Hellendoorn', NULL, 19000101, NULL, '016301', false, true, 19000101, true),
   (168, 'Gemeente Hengelo (O)', NULL, 19000101, NULL, '016401', false, true, 19000101, true),
   (169, 'Gemeente Holten', NULL, 19000101, 20010101, '016501', false, true, null, false),
   (170, 'Gemeente Kampen', NULL, 19000101, NULL, '016601', false, true, 19000101, true),
   (171, 'Gemeente Kuinre', NULL, 19000101, 19730101, '016701', false, true, null, false),
   (172, 'Gemeente Losser', NULL, 19000101, NULL, '016801', false, true, 19000101, true),
   (173, 'Gemeente Markelo', NULL, 19000101, 20010101, '016901', false, true, null, false),
   (174, 'Gemeente Nieuwleusen', NULL, 19000101, 20010101, '017001', false, true, null, false),
   (175, 'Gemeente Noordoostpolder', NULL, 19620701, NULL, '017101', false, true, 19620701, true),
   (176, 'Gemeente Oldemarkt', NULL, 19000101, 19730101, '017201', false, true, null, false),
   (177, 'Gemeente Oldenzaal', NULL, 19000101, NULL, '017301', false, true, 19000101, true),
   (178, 'Gemeente Olst', NULL, 19000101, 20020326, '017401', false, true, null, false),
   (179, 'Gemeente Ommen', NULL, 19230501, NULL, '017501', false, true, 19230501, true),
   (180, 'Gemeente Ootmarsum', NULL, 19000101, 20010101, '017601', false, true, null, false),
   (181, 'Gemeente Raalte', NULL, 19000101, NULL, '017701', false, true, 19000101, true),
   (182, 'Gemeente Rijssen', NULL, 19000101, 20030315, '017801', false, true, null, false),
   (183, 'Gemeente Stad Delden', NULL, 19000101, 20010101, '017901', false, true, null, false),
   (184, 'Gemeente Staphorst', NULL, 19000101, NULL, '018001', false, true, 19000101, true),
   (185, 'Gemeente Steenwijk', NULL, 19000101, 20030101, '018101', false, true, null, false),
   (186, 'Gemeente Steenwijkerwold', NULL, 19000101, 19730101, '018201', false, true, null, false),
   (187, 'Gemeente Tubbergen', NULL, 19000101, NULL, '018301', false, true, 19000101, true),
   (188, 'Gemeente Urk', NULL, 19000101, NULL, '018401', false, true, 19000101, true),
   (189, 'Gemeente Vollenhove', NULL, 19420201, 19730101, '018501', false, true, null, false),
   (190, 'Gemeente Vriezenveen', NULL, 19000101, 20020601, '018601', false, true, null, false),
   (191, 'Gemeente Wanneperveen', NULL, 19000101, 19730101, '018701', false, true, null, false),
   (192, 'Gemeente Weerselo', NULL, 19000101, 20010101, '018801', false, true, null, false),
   (193, 'Gemeente Wierden', NULL, 19000101, NULL, '018901', false, true, 19000101, true),
   (194, 'Gemeente Wijhe', NULL, 19000101, 20010101, '019001', false, true, null, false),
   (195, 'Gemeente IJsselmuiden', NULL, 19000101, 20010101, '019101', false, true, null, false),
   (196, 'Gemeente Zwartsluis', NULL, 19000101, 20010101, '019201', false, true, null, false),
   (197, 'Gemeente Zwolle', NULL, 19000101, NULL, '019301', false, true, 19000101, true),
   (198, 'Gemeente Brederwiede', NULL, 19730101, 20010101, '019401', false, true, null, false),
   (199, 'Gemeente IJsselham', NULL, 19730101, 20010101, '019501', false, true, null, false),
   (200, 'Gemeente Rijnwaarden', NULL, 19850101, NULL, '019601', false, true, 19850101, true),
   (201, 'Gemeente Aalten', NULL, 19000101, NULL, '019701', false, true, 19000101, true),
   (202, 'Gemeente Ammerzoden', NULL, 19000101, 19990101, '019801', false, true, null, false),
   (203, 'Gemeente Angerlo', NULL, 19000101, 20050101, '019901', false, true, null, false),
   (204, 'Gemeente Apeldoorn', NULL, 19000101, NULL, '020001', false, true, 19000101, true),
   (205, 'Gemeente Appeltern', NULL, 19000101, 19840101, '020101', false, true, null, false),
   (206, 'Gemeente Arnhem', NULL, 19000101, NULL, '020201', false, true, 19000101, true),
   (207, 'Gemeente Barneveld', NULL, 19000101, NULL, '020301', false, true, 19000101, true),
   (208, 'Gemeente Batenburg', NULL, 19000101, 19840101, '020401', false, true, null, false),
   (209, 'Gemeente Beesd', NULL, 19000101, 19780101, '020501', false, true, null, false),
   (210, 'Gemeente Bemmel', NULL, 19000101, 20030101, '020601', false, true, null, false),
   (211, 'Gemeente Bergh', NULL, 19000101, 20050101, '020701', false, true, null, false),
   (212, 'Gemeente Bergharen', NULL, 19000101, 19840101, '020801', false, true, null, false),
   (213, 'Gemeente Beuningen', NULL, 19000101, NULL, '020901', false, true, 19000101, true),
   (214, 'Gemeente Beusichem', NULL, 19000101, 19780101, '021001', false, true, null, false),
   (215, 'Gemeente Borculo', NULL, 19000101, 20050101, '021101', false, true, null, false),
   (216, 'Gemeente Brakel', NULL, 19000101, 19990101, '021201', false, true, null, false),
   (217, 'Gemeente Brummen', NULL, 19000101, NULL, '021301', false, true, 19000101, true),
   (218, 'Gemeente Buren', NULL, 19000101, NULL, '021401', false, true, 19000101, true),
   (219, 'Gemeente Buurmalsen', NULL, 19000101, 19780101, '021501', false, true, null, false),
   (220, 'Gemeente Culemborg', NULL, 19000101, NULL, '021601', false, true, 19000101, true),
   (221, 'Gemeente Deil', NULL, 19000101, 19780101, '021701', false, true, null, false),
   (222, 'Gemeente Didam', NULL, 19000101, 20050101, '021801', false, true, null, false),
   (223, 'Gemeente Dinxperlo', NULL, 19000101, 20050101, '021901', false, true, null, false),
   (224, 'Gemeente Dodewaard', NULL, 19000101, 20020101, '022001', false, true, null, false),
   (225, 'Gemeente Doesburg', NULL, 19000101, NULL, '022101', false, true, 19000101, true),
   (226, 'Gemeente Doetinchem', NULL, 19200101, NULL, '022201', false, true, 19200101, true),
   (227, 'Gemeente Doornspijk', NULL, 19000101, 19740801, '022301', false, true, null, false),
   (228, 'Gemeente Dreumel', NULL, 19000101, 19840101, '022401', false, true, null, false),
   (229, 'Gemeente Druten', NULL, 19000101, NULL, '022501', false, true, 19000101, true),
   (230, 'Gemeente Duiven', NULL, 19000101, NULL, '022601', false, true, 19000101, true),
   (231, 'Gemeente Echteld', NULL, 19000101, 20020101, '022701', false, true, null, false),
   (232, 'Gemeente Ede', NULL, 19000101, NULL, '022801', false, true, 19000101, true),
   (233, 'Gemeente Eibergen', NULL, 19000101, 20050101, '022901', false, true, null, false),
   (234, 'Gemeente Elburg', NULL, 19000101, NULL, '023001', false, true, 19000101, true),
   (235, 'Gemeente Elst', NULL, 19000101, 20010101, '023101', false, true, null, false),
   (236, 'Gemeente Epe', NULL, 19000101, NULL, '023201', false, true, 19000101, true),
   (237, 'Gemeente Ermelo', NULL, 19000101, NULL, '023301', false, true, 19000101, true),
   (238, 'Gemeente Est en Opijnen', NULL, 19000101, 19780101, '023401', false, true, null, false),
   (239, 'Gemeente Ewijk', NULL, 19000101, 19800701, '023501', false, true, null, false),
   (240, 'Gemeente Geldermalsen', NULL, 19000101, NULL, '023601', false, true, 19000101, true),
   (241, 'Gemeente Gendringen', NULL, 19000101, 20050101, '023701', false, true, null, false),
   (242, 'Gemeente Gendt', NULL, 19000101, 20010101, '023801', false, true, null, false),
   (243, 'Gemeente Gorssel', NULL, 19000101, 20050101, '023901', false, true, null, false),
   (244, 'Gemeente Groenlo', NULL, 19000101, 20060519, '024001', false, true, null, false),
   (245, 'Gemeente Groesbeek', NULL, 19000101, 20160101, '024101', false, true, null, false),
   (246, 'Gemeente Haaften', NULL, 19000101, 19780101, '024201', false, true, null, false),
   (247, 'Gemeente Harderwijk', NULL, 19000101, NULL, '024301', false, true, 19000101, true),
   (248, 'Gemeente Hattem', NULL, 19000101, NULL, '024401', false, true, 19000101, true),
   (249, 'Gemeente Hedel', NULL, 19000101, 19990101, '024501', false, true, null, false),
   (250, 'Gemeente Heerde', NULL, 19000101, NULL, '024601', false, true, 19000101, true),
   (251, 'Gemeente Heerewaarden', NULL, 19000101, 19990101, '024701', false, true, null, false),
   (252, 'Gemeente Hengelo (Gld)', NULL, 19000101, 20050101, '024801', false, true, null, false),
   (253, 'Gemeente Herwen en Aerdt', NULL, 19000101, 19850101, '024901', false, true, null, false),
   (254, 'Gemeente Herwijnen', NULL, 19000101, 19860101, '025001', false, true, null, false),
   (255, 'Gemeente Heteren', NULL, 19000101, 20010101, '025101', false, true, null, false),
   (256, 'Gemeente Heumen', NULL, 19000101, NULL, '025201', false, true, 19000101, true),
   (257, 'Gemeente Hoevelaken', NULL, 19000101, 20000101, '025301', false, true, null, false),
   (258, 'Gemeente Horssen', NULL, 19000101, 19840101, '025401', false, true, null, false),
   (259, 'Gemeente Huissen', NULL, 19000101, 20010101, '025501', false, true, null, false),
   (260, 'Gemeente Hummelo en Keppel', NULL, 19000101, 20050101, '025601', false, true, null, false),
   (261, 'Gemeente Kerkwijk', NULL, 19000101, 19990101, '025701', false, true, null, false),
   (262, 'Gemeente Kesteren', NULL, 19000101, 20030401, '025801', false, true, null, false),
   (263, 'Gemeente Laren (Gld)', NULL, 19000101, 19710801, '025901', false, true, null, false),
   (264, 'Gemeente Lichtenvoorde', NULL, 19000101, 20050101, '026001', false, true, null, false),
   (265, 'Gemeente Lienden', NULL, 19000101, 19990101, '026101', false, true, null, false),
   (266, 'Gemeente Lochem', NULL, 19000101, NULL, '026201', false, true, 19000101, true),
   (267, 'Gemeente Maasdriel', NULL, 19440801, NULL, '026301', false, true, 19440801, true),
   (268, 'Gemeente Maurik', NULL, 19000101, 19990101, '026401', false, true, null, false),
   (269, 'Gemeente Millingen aan de Rijn', NULL, 19550101, 20150101, '026501', false, true, null, false),
   (270, 'Gemeente Neede', NULL, 19000101, 20050101, '026601', false, true, null, false),
   (271, 'Gemeente Nijkerk', NULL, 19000101, NULL, '026701', false, true, 19000101, true),
   (272, 'Gemeente Nijmegen', NULL, 19000101, NULL, '026801', false, true, 19000101, true),
   (273, 'Gemeente Oldebroek', NULL, 19000101, NULL, '026901', false, true, 19000101, true),
   (274, 'Gemeente Ophemert', NULL, 19000101, 19780101, '027001', false, true, null, false),
   (275, 'Gemeente Overasselt', NULL, 19000101, 19800701, '027101', false, true, null, false),
   (276, 'Gemeente Pannerden', NULL, 19000101, 19850101, '027201', false, true, null, false),
   (277, 'Gemeente Putten', NULL, 19000101, NULL, '027301', false, true, 19000101, true),
   (278, 'Gemeente Renkum', NULL, 19000101, NULL, '027401', false, true, 19000101, true),
   (279, 'Gemeente Rheden', NULL, 19000101, NULL, '027501', false, true, 19000101, true),
   (280, 'Gemeente Rossum', NULL, 19000101, 19990101, '027601', false, true, null, false),
   (281, 'Gemeente Rozendaal', NULL, 19000101, NULL, '027701', false, true, 19000101, true),
   (282, 'Gemeente Ruurlo', NULL, 19000101, 20050101, '027801', false, true, null, false),
   (283, 'Gemeente Scherpenzeel', NULL, 19000101, NULL, '027901', false, true, 19000101, true),
   (284, 'Gemeente Steenderen', NULL, 19000101, 20050101, '028001', false, true, null, false),
   (285, 'Gemeente Tiel', NULL, 19000101, NULL, '028101', false, true, 19000101, true),
   (286, 'Gemeente Ubbergen', NULL, 19000101, 20150101, '028201', false, true, null, false),
   (287, 'Gemeente Valburg', NULL, 19000101, 20010101, '028301', false, true, null, false),
   (288, 'Gemeente Varik', NULL, 19000101, 19780101, '028401', false, true, null, false),
   (289, 'Gemeente Voorst', NULL, 19000101, NULL, '028501', false, true, 19000101, true),
   (290, 'Gemeente Vorden', NULL, 19000101, 20050101, '028601', false, true, null, false),
   (291, 'Gemeente Vuren', NULL, 19000101, 19870103, '028701', false, true, null, false),
   (292, 'Gemeente Waardenburg', NULL, 19000101, 19780101, '028801', false, true, null, false),
   (293, 'Gemeente Wageningen', NULL, 19000101, NULL, '028901', false, true, 19000101, true),
   (294, 'Gemeente Wamel', NULL, 19000101, 19850701, '029001', false, true, null, false),
   (295, 'Gemeente Warnsveld', NULL, 19000101, 20050101, '029101', false, true, null, false),
   (296, 'Gemeente Wehl', NULL, 19000101, 20050101, '029201', false, true, null, false),
   (297, 'Gemeente Westervoort', NULL, 19000101, NULL, '029301', false, true, 19000101, true),
   (298, 'Gemeente Winterswijk', NULL, 19000101, NULL, '029401', false, true, 19000101, true),
   (299, 'Gemeente Wisch', NULL, 19000101, 20050101, '029501', false, true, null, false),
   (300, 'Gemeente Wijchen', NULL, 19000101, NULL, '029601', false, true, 19000101, true),
   (301, 'Gemeente Zaltbommel', NULL, 19000101, NULL, '029701', false, true, 19000101, true),
   (302, 'Gemeente Zelhem', NULL, 19000101, 20050101, '029801', false, true, null, false),
   (303, 'Gemeente Zevenaar', NULL, 19000101, NULL, '029901', false, true, 19000101, true),
   (304, 'Gemeente Zoelen', NULL, 19000101, 19780101, '030001', false, true, null, false),
   (305, 'Gemeente Zutphen', NULL, 19000101, NULL, '030101', false, true, 19000101, true),
   (306, 'Gemeente Nunspeet', NULL, 19720101, NULL, '030201', false, true, 19720101, true),
   (307, 'Gemeente Dronten', NULL, 19720101, NULL, '030301', false, true, 19720101, true),
   (308, 'Gemeente Neerijnen', NULL, 19780101, NULL, '030401', false, true, 19780101, true),
   (309, 'Gemeente Abcoude', NULL, 19410501, 20110101, '030501', false, true, null, false),
   (310, 'Gemeente Amerongen', NULL, 19000101, 20060101, '030601', false, true, null, false),
   (311, 'Gemeente Amersfoort', NULL, 19000101, NULL, '030701', false, true, 19000101, true),
   (312, 'Gemeente Baarn', NULL, 19000101, NULL, '030801', false, true, 19000101, true),
   (313, 'Gemeente Benschop', NULL, 19000101, 19890101, '030901', false, true, null, false),
   (314, 'Gemeente De Bilt', NULL, 19000101, NULL, '031001', false, true, 19000101, true),
   (315, 'Gemeente Breukelen', NULL, 19490101, 20110101, '031101', false, true, null, false),
   (316, 'Gemeente Bunnik', NULL, 19000101, NULL, '031201', false, true, 19000101, true),
   (317, 'Gemeente Bunschoten', NULL, 19000101, NULL, '031301', false, true, 19000101, true),
   (318, 'Gemeente Cothen', NULL, 19000101, 19960101, '031401', false, true, null, false),
   (319, 'Gemeente Doorn', NULL, 19000101, 20060101, '031501', false, true, null, false),
   (320, 'Gemeente Driebergen-Rijsenburg', NULL, 19310501, 20060101, '031601', false, true, null, false),
   (321, 'Gemeente Eemnes', NULL, 19000101, NULL, '031701', false, true, 19000101, true),
   (322, 'Gemeente Harmelen', NULL, 19000101, 20010101, '031801', false, true, null, false),
   (323, 'Gemeente Hoenkoop', NULL, 19000101, 19700901, '031901', false, true, null, false),
   (324, 'Gemeente Hoogland', NULL, 19000101, 19740101, '032001', false, true, null, false),
   (325, 'Gemeente Houten', NULL, 19000101, NULL, '032101', false, true, 19000101, true),
   (326, 'Gemeente Jutphaas', NULL, 19000101, 19710701, '032201', false, true, null, false),
   (327, 'Gemeente Kamerik', NULL, 19000101, 19890101, '032301', false, true, null, false),
   (328, 'Gemeente Kockengen', NULL, 19000101, 19890101, '032401', false, true, null, false),
   (329, 'Gemeente Langbroek', NULL, 19000101, 19960101, '032501', false, true, null, false),
   (330, 'Gemeente Leersum', NULL, 19000101, 20060101, '032601', false, true, null, false),
   (331, 'Gemeente Leusden', NULL, 19000101, NULL, '032701', false, true, 19000101, true),
   (332, 'Gemeente Linschoten', NULL, 19000101, 19890101, '032801', false, true, null, false),
   (333, 'Gemeente Loenen', NULL, 19000101, 20110101, '032901', false, true, null, false),
   (334, 'Gemeente Loosdrecht', NULL, 19000101, 20020101, '033001', false, true, null, false),
   (335, 'Gemeente Lopik', NULL, 19000101, NULL, '033101', false, true, 19000101, true),
   (336, 'Gemeente Maarn', NULL, 19000101, 20060101, '033201', false, true, null, false),
   (337, 'Gemeente Maarssen', NULL, 19000101, 20110101, '033301', false, true, null, false),
   (338, 'Gemeente Maartensdijk', NULL, 19000101, 20010101, '033401', false, true, null, false),
   (339, 'Gemeente Montfoort', NULL, 19000101, NULL, '033501', false, true, 19000101, true),
   (340, 'Gemeente Mijdrecht', NULL, 19000101, 19890101, '033601', false, true, null, false),
   (341, 'Gemeente Nigtevecht', NULL, 19000101, 19890101, '033701', false, true, null, false),
   (342, 'Gemeente Polsbroek', NULL, 18570613, 19890101, '033801', false, true, null, false),
   (343, 'Gemeente Renswoude', NULL, 19000101, NULL, '033901', false, true, 19000101, true),
   (344, 'Gemeente Rhenen', NULL, 19000101, NULL, '034001', false, true, 19000101, true),
   (345, 'Gemeente Snelrewaard', NULL, 19000101, 19890101, '034101', false, true, null, false),
   (346, 'Gemeente Soest', NULL, 19000101, NULL, '034201', false, true, 19000101, true),
   (347, 'Gemeente Stoutenburg', NULL, 19000101, 19690601, '034301', false, true, null, false),
   (348, 'Gemeente Utrecht', NULL, 19000101, NULL, '034401', false, true, 19000101, true),
   (349, 'Gemeente Veenendaal', NULL, 19000101, NULL, '034501', false, true, 19000101, true),
   (350, 'Gemeente Vinkeveen en Waverveen', NULL, 18410101, 19890101, '034601', false, true, null, false),
   (351, 'Gemeente Vleuten-De Meern', NULL, 19540101, 20010101, '034701', false, true, null, false),
   (352, 'Gemeente Vreeswijk', NULL, 19000101, 19710701, '034801', false, true, null, false),
   (353, 'Gemeente Willeskop', NULL, 19000101, 19890101, '034901', false, true, null, false),
   (354, 'Gemeente Wilnis', NULL, 19000101, 19890101, '035001', false, true, null, false),
   (355, 'Gemeente Woudenberg', NULL, 19000101, NULL, '035101', false, true, 19000101, true),
   (356, 'Gemeente Wijk bij Duurstede', NULL, 19000101, NULL, '035201', false, true, 19000101, true),
   (357, 'Gemeente IJsselstein', NULL, 19000101, NULL, '035301', false, true, 19000101, true),
   (358, 'Gemeente Zegveld', NULL, 19000101, 19890101, '035401', false, true, null, false),
   (359, 'Gemeente Zeist', NULL, 19000101, NULL, '035501', false, true, 19000101, true),
   (360, 'Gemeente Nieuwegein', NULL, 19710701, NULL, '035601', false, true, 19710701, true),
   (361, 'Gemeente Egmond', NULL, 19780701, 20010101, '035701', false, true, null, false),
   (362, 'Gemeente Aalsmeer', NULL, 19000101, NULL, '035801', false, true, 19000101, true),
   (363, 'Gemeente Abbekerk', NULL, 19000101, 19790101, '035901', false, true, null, false),
   (364, 'Gemeente Akersloot', NULL, 19000101, 20020101, '036001', false, true, null, false),
   (365, 'Gemeente Alkmaar', NULL, 19000101, NULL, '036101', false, true, 19000101, true),
   (366, 'Gemeente Amstelveen', NULL, 19640101, NULL, '036201', false, true, 19640101, true),
   (367, 'Gemeente Amsterdam', NULL, 19000101, NULL, '036301', false, true, 19000101, true),
   (368, 'Gemeente Andijk', NULL, 19000101, 20110101, '036401', false, true, null, false),
   (369, 'Gemeente Graft-De Rijp', NULL, 19700801, 20150101, '036501', false, true, null, false),
   (370, 'Gemeente Anna Paulowna', NULL, 18700718, 20120101, '036601', false, true, null, false),
   (371, 'Gemeente Assendelft', NULL, 19000101, 19740101, '036701', false, true, null, false),
   (372, 'Gemeente Avenhorn', NULL, 19000101, 19790101, '036801', false, true, null, false),
   (373, 'Gemeente Barsingerhorn', NULL, 19000101, 19900101, '036901', false, true, null, false),
   (374, 'Gemeente Beemster', NULL, 19000101, NULL, '037001', false, true, 19000101, true),
   (375, 'Gemeente Beets', NULL, 19000101, 19700801, '037101', false, true, null, false),
   (376, 'Gemeente Bennebroek', NULL, 19000101, 20090101, '037201', false, true, null, false),
   (377, 'Gemeente Bergen (NH)', NULL, 19000101, NULL, '037301', false, true, 19000101, true),
   (378, 'Gemeente Berkhout', NULL, 19000101, 19790101, '037401', false, true, null, false),
   (379, 'Gemeente Beverwijk', NULL, 19000101, NULL, '037501', false, true, 19000101, true),
   (380, 'Gemeente Blaricum', NULL, 19000101, NULL, '037601', false, true, 19000101, true),
   (381, 'Gemeente Bloemendaal', NULL, 19000101, NULL, '037701', false, true, 19000101, true),
   (382, 'Gemeente Blokker', NULL, 19000101, 19790101, '037801', false, true, null, false),
   (383, 'Gemeente Bovenkarspel', NULL, 19000101, 19790101, '037901', false, true, null, false),
   (384, 'Gemeente Broek in Waterland', NULL, 19000101, 19910101, '038001', false, true, null, false),
   (385, 'Gemeente Bussum', NULL, 19000101, 20160101, '038101', false, true, null, false),
   (386, 'Gemeente Callantsoog', NULL, 19000101, 19900101, '038201', false, true, null, false),
   (387, 'Gemeente Castricum', NULL, 19000101, NULL, '038301', false, true, 19000101, true),
   (388, 'Gemeente Diemen', NULL, 19000101, NULL, '038401', false, true, 19000101, true),
   (389, 'Gemeente Edam-Volendam', NULL, 19750101, NULL, '038501', false, true, 19750101, true),
   (390, 'Gemeente Egmond aan Zee', NULL, 19000101, 19780701, '038601', false, true, null, false),
   (391, 'Gemeente Egmond-Binnen', NULL, 19000101, 19780701, '038701', false, true, null, false),
   (392, 'Gemeente Enkhuizen', NULL, 19000101, NULL, '038801', false, true, 19000101, true),
   (393, 'Gemeente Graft', NULL, 19000101, 19700801, '038901', false, true, null, false),
   (394, 'Gemeente ''s-Graveland', NULL, 19000101, 20020101, '039001', false, true, null, false),
   (395, 'Gemeente Grootebroek', NULL, 19000101, 19790101, '039101', false, true, null, false),
   (396, 'Gemeente Haarlem', NULL, 19000101, NULL, '039201', false, true, 19000101, true),
   (397, 'Gemeente Haarlemmerliede en Spaarnwoude', NULL, 18570101, NULL, '039301', false, true, 18570101, true),
   (398, 'Gemeente Haarlemmermeer', NULL, 18550101, NULL, '039401', false, true, 18550101, true),
   (399, 'Gemeente Harenkarspel', NULL, 19000101, 20130101, '039501', false, true, null, false),
   (400, 'Gemeente Heemskerk', NULL, 19000101, NULL, '039601', false, true, 19000101, true),
   (401, 'Gemeente Heemstede', NULL, 19000101, NULL, '039701', false, true, 19000101, true),
   (402, 'Gemeente Heerhugowaard', NULL, 19000101, NULL, '039801', false, true, 19000101, true),
   (403, 'Gemeente Heiloo', NULL, 19000101, NULL, '039901', false, true, 19000101, true),
   (404, 'Gemeente Den Helder', NULL, 19000101, NULL, '040001', false, true, 19000101, true),
   (405, 'Gemeente Hensbroek', NULL, 19000101, 19790101, '040101', false, true, null, false),
   (406, 'Gemeente Hilversum', NULL, 19000101, NULL, '040201', false, true, 19000101, true),
   (407, 'Gemeente Hoogkarspel', NULL, 19000101, 19790101, '040301', false, true, null, false),
   (408, 'Gemeente Hoogwoud', NULL, 19000101, 19790101, '040401', false, true, null, false),
   (409, 'Gemeente Hoorn', NULL, 19000101, NULL, '040501', false, true, 19000101, true),
   (410, 'Gemeente Huizen', NULL, 19000101, NULL, '040601', false, true, 19000101, true),
   (411, 'Gemeente Ilpendam', NULL, 19000101, 19910101, '040701', false, true, null, false),
   (412, 'Gemeente Jisp', NULL, 19000101, 19910101, '040801', false, true, null, false),
   (413, 'Gemeente Katwoude', NULL, 19000101, 19910101, '040901', false, true, null, false),
   (414, 'Gemeente Koedijk', NULL, 19000101, 19721001, '041001', false, true, null, false),
   (415, 'Gemeente Koog aan de Zaan', NULL, 19000101, 19740101, '041101', false, true, null, false),
   (416, 'Gemeente Niedorp', NULL, 19700801, 20120101, '041201', false, true, null, false),
   (417, 'Gemeente Krommenie', NULL, 19000101, 19740101, '041301', false, true, null, false),
   (418, 'Gemeente Kwadijk', NULL, 19000101, 19700801, '041401', false, true, null, false),
   (419, 'Gemeente Landsmeer', NULL, 19000101, NULL, '041501', false, true, 19000101, true),
   (420, 'Gemeente Langedijk', NULL, 19410801, NULL, '041601', false, true, 19410801, true),
   (421, 'Gemeente Laren', NULL, 19000101, NULL, '041701', false, true, 19000101, true),
   (422, 'Gemeente Limmen', NULL, 19000101, 20020101, '041801', false, true, null, false),
   (423, 'Gemeente Marken', NULL, 19000101, 19910101, '041901', false, true, null, false),
   (424, 'Gemeente Medemblik', NULL, 19000101, NULL, '042001', false, true, 19000101, true),
   (425, 'Gemeente Middelie', NULL, 19000101, 19700801, '042101', false, true, null, false),
   (426, 'Gemeente Midwoud', NULL, 19000101, 19790101, '042201', false, true, null, false),
   (427, 'Gemeente Monnickendam', NULL, 19000101, 19910101, '042301', false, true, null, false),
   (428, 'Gemeente Muiden', NULL, 19000101, 20160101, '042401', false, true, null, false),
   (429, 'Gemeente Naarden', NULL, 19000101, 20160101, '042501', false, true, null, false),
   (430, 'Gemeente Nederhorst den Berg', NULL, 19000101, 20020101, '042601', false, true, null, false),
   (431, 'Gemeente Nibbixwoud', NULL, 19000101, 19790101, '042701', false, true, null, false),
   (432, 'Gemeente Nieuwe Niedorp', NULL, 19000101, 19700801, '042801', false, true, null, false),
   (433, 'Gemeente Obdam', NULL, 19000101, 20070101, '042901', false, true, null, false),
   (434, 'Gemeente Oosthuizen', NULL, 19000101, 19700801, '043001', false, true, null, false),
   (435, 'Gemeente Oostzaan', NULL, 19000101, NULL, '043101', false, true, 19000101, true),
   (436, 'Gemeente Opmeer', NULL, 19000101, NULL, '043201', false, true, 19000101, true),
   (437, 'Gemeente Opperdoes', NULL, 19000101, 19790101, '043301', false, true, null, false),
   (438, 'Gemeente Oterleek', NULL, 19000101, 19700801, '043401', false, true, null, false),
   (439, 'Gemeente Oudendijk', NULL, 19000101, 19790101, '043501', false, true, null, false),
   (440, 'Gemeente Oude Niedorp', NULL, 19000101, 19700801, '043601', false, true, null, false),
   (441, 'Gemeente Ouder-Amstel', NULL, 19000101, NULL, '043701', false, true, 19000101, true),
   (442, 'Gemeente Oudorp', NULL, 19000101, 19721001, '043801', false, true, null, false),
   (443, 'Gemeente Purmerend', NULL, 19000101, NULL, '043901', false, true, 19000101, true),
   (444, 'Gemeente De Rijp', NULL, 19000101, 19700801, '044001', false, true, null, false),
   (445, 'Gemeente Schagen', NULL, 19000101, NULL, '044101', false, true, 19000101, true),
   (446, 'Gemeente Schellinkhout', NULL, 19000101, 19700801, '044201', false, true, null, false),
   (447, 'Gemeente Schermerhorn', NULL, 19000101, 19700801, '044301', false, true, null, false),
   (448, 'Gemeente Schoorl', NULL, 19000101, 20010101, '044401', false, true, null, false),
   (449, 'Gemeente Sint Maarten', NULL, 19000101, 19900101, '044501', false, true, null, false),
   (450, 'Gemeente Sint Pancras', NULL, 19000101, 19900101, '044601', false, true, null, false),
   (451, 'Gemeente Sijbekarspel', NULL, 19000101, 19790101, '044701', false, true, null, false),
   (452, 'Gemeente Texel', NULL, 19000101, NULL, '044801', false, true, 19000101, true),
   (453, 'Gemeente Twisk', NULL, 19000101, 19790101, '044901', false, true, null, false),
   (454, 'Gemeente Uitgeest', NULL, 19000101, NULL, '045001', false, true, 19000101, true),
   (455, 'Gemeente Uithoorn', NULL, 19000101, NULL, '045101', false, true, 19000101, true),
   (456, 'Gemeente Ursem', NULL, 19000101, 19790101, '045201', false, true, null, false),
   (457, 'Gemeente Velsen', NULL, 19000101, NULL, '045301', false, true, 19000101, true),
   (458, 'Gemeente Venhuizen', NULL, 19000101, 20060101, '045401', false, true, null, false),
   (459, 'Gemeente Warder', NULL, 19000101, 19700801, '045501', false, true, null, false),
   (460, 'Gemeente Warmenhuizen', NULL, 19000101, 19900101, '045601', false, true, null, false),
   (461, 'Gemeente Weesp', NULL, 19000101, NULL, '045701', false, true, 19000101, true),
   (462, 'Gemeente Schermer', NULL, 19700801, 20150101, '045801', false, true, null, false),
   (463, 'Gemeente Wervershoof', NULL, 19000101, 20110101, '045901', false, true, null, false),
   (464, 'Gemeente Westwoud', NULL, 19000101, 19790101, '046001', false, true, null, false),
   (465, 'Gemeente Westzaan', NULL, 19000101, 19740101, '046101', false, true, null, false),
   (466, 'Gemeente Wieringen', NULL, 19000101, 20120101, '046201', false, true, null, false),
   (467, 'Gemeente Wieringermeer', NULL, 19380101, 20120101, '046301', false, true, null, false),
   (468, 'Gemeente Wieringerwaard', NULL, 19000101, 19700801, '046401', false, true, null, false),
   (469, 'Gemeente Winkel', NULL, 19000101, 19700801, '046501', false, true, null, false),
   (470, 'Gemeente Wognum', NULL, 19000101, 20070101, '046601', false, true, null, false),
   (471, 'Gemeente Wormer', NULL, 19000101, 19910101, '046701', false, true, null, false),
   (472, 'Gemeente Wormerveer', NULL, 19000101, 19740101, '046801', false, true, null, false),
   (473, 'Gemeente Wijdenes', NULL, 19000101, 19700801, '046901', false, true, null, false),
   (474, 'Gemeente Wijdewormer', NULL, 19000101, 19910101, '047001', false, true, null, false),
   (475, 'Gemeente Zaandam', NULL, 19000101, 19740101, '047101', false, true, null, false),
   (476, 'Gemeente Zaandijk', NULL, 19000101, 19740101, '047201', false, true, null, false),
   (477, 'Gemeente Zandvoort', NULL, 19000101, NULL, '047301', false, true, 19000101, true),
   (478, 'Gemeente Zuid- en Noord-Schermer', NULL, 19000101, 19700801, '047401', false, true, null, false),
   (479, 'Gemeente Zwaag', NULL, 19000101, 19790101, '047501', false, true, null, false),
   (480, 'Gemeente Zijpe', NULL, 19000101, 20130101, '047601', false, true, null, false),
   (481, 'Gemeente Albrandswaard (oud)', NULL, 19000101, 18410901, '047701', false, true, null, false),
   (482, 'Gemeente Zeevang', NULL, 19700801, 20160101, '047801', false, true, null, false),
   (483, 'Gemeente Zaanstad', NULL, 19740101, NULL, '047901', false, true, 19740101, true),
   (484, 'Gemeente Ter Aar', NULL, 19000101, 20070101, '048001', false, true, null, false),
   (485, 'Gemeente Abbenbroek', NULL, 19000101, 19800101, '048101', false, true, null, false),
   (486, 'Gemeente Alblasserdam', NULL, 19000101, NULL, '048201', false, true, 19000101, true),
   (487, 'Gemeente Alkemade', NULL, 19000101, 20090101, '048301', false, true, null, false),
   (488, 'Gemeente Alphen aan den Rijn', NULL, 19180101, NULL, '048401', false, true, 19180101, true),
   (489, 'Gemeente Ameide', NULL, 19000101, 19860101, '048501', false, true, null, false),
   (490, 'Gemeente Ammerstol', NULL, 19000101, 19850101, '048601', false, true, null, false),
   (491, 'Gemeente Arkel', NULL, 19000101, 19860101, '048701', false, true, null, false),
   (492, 'Gemeente Asperen', NULL, 19000101, 19860101, '048801', false, true, null, false),
   (493, 'Gemeente Barendrecht', NULL, 18860101, NULL, '048901', false, true, 18860101, true),
   (494, 'Gemeente Benthuizen', NULL, 19000101, 19910101, '049001', false, true, null, false),
   (495, 'Gemeente Bergambacht', NULL, 19000101, 20150101, '049101', false, true, null, false),
   (496, 'Gemeente Bergschenhoek', NULL, 19000101, 20070101, '049201', false, true, null, false),
   (497, 'Gemeente Berkel en Rodenrijs', NULL, 19000101, 20070101, '049301', false, true, null, false),
   (498, 'Gemeente Berkenwoude', NULL, 19000101, 19850101, '049401', false, true, null, false),
   (499, 'Gemeente Bleiswijk', NULL, 19000101, 20070101, '049501', false, true, null, false),
   (500, 'Gemeente Bleskensgraaf en Hofwegen', NULL, 19000101, 19860101, '049601', false, true, null, false),
   (501, 'Gemeente Bodegraven', NULL, 19000101, 20110101, '049701', false, true, null, false),
   (502, 'Gemeente Drechterland', NULL, 19800101, NULL, '049801', false, true, 19800101, true),
   (503, 'Gemeente Boskoop', NULL, 19000101, 20140101, '049901', false, true, null, false),
   (504, 'Gemeente Brandwijk', NULL, 19000101, 19860101, '050001', false, true, null, false),
   (505, 'Gemeente Brielle', NULL, 19000101, NULL, '050101', false, true, 19000101, true),
   (506, 'Gemeente Capelle aan den IJssel', NULL, 19000101, NULL, '050201', false, true, 19000101, true),
   (507, 'Gemeente Delft', NULL, 19000101, NULL, '050301', false, true, 19000101, true),
   (508, 'Gemeente Dirksland', NULL, 19000101, 20130101, '050401', false, true, null, false),
   (509, 'Gemeente Dordrecht', NULL, 19000101, NULL, '050501', false, true, 19000101, true),
   (510, 'Gemeente Driebruggen', NULL, 19640201, 19890101, '050601', false, true, null, false),
   (511, 'Gemeente Dubbeldam', NULL, 19000101, 19700701, '050701', false, true, null, false),
   (512, 'Gemeente Everdingen', NULL, 19000101, 19860101, '050801', false, true, null, false),
   (513, 'Gemeente Geervliet', NULL, 19000101, 19800101, '050901', false, true, null, false),
   (514, 'Gemeente Giessenburg', NULL, 19570101, 19860101, '051001', false, true, null, false),
   (515, 'Gemeente Goedereede', NULL, 19000101, 20130101, '051101', false, true, null, false),
   (516, 'Gemeente Gorinchem', NULL, 19000101, NULL, '051201', false, true, 19000101, true),
   (517, 'Gemeente Gouda', NULL, 19000101, NULL, '051301', false, true, 19000101, true),
   (518, 'Gemeente Gouderak', NULL, 19000101, 19850101, '051401', false, true, null, false),
   (519, 'Gemeente Goudriaan', NULL, 19000101, 19860101, '051501', false, true, null, false),
   (520, 'Gemeente Goudswaard', NULL, 19000101, 19840101, '051601', false, true, null, false),
   (521, 'Gemeente ''s-Gravendeel', NULL, 19000101, 20070101, '051701', false, true, null, false),
   (522, 'Gemeente ''s-Gravenhage', NULL, 19000101, NULL, '051801', false, true, 19000101, true),
   (523, 'Gemeente ''s-Gravenzande', NULL, 19000101, 20040101, '051901', false, true, null, false),
   (524, 'Gemeente Groot-Ammers', NULL, 19000101, 19860101, '052001', false, true, null, false),
   (525, 'Gemeente Haastrecht', NULL, 19000101, 19850101, '052101', false, true, null, false),
   (526, 'Gemeente Hagestein', NULL, 19000101, 19860101, '052201', false, true, null, false),
   (527, 'Gemeente Hardinxveld-Giessendam', NULL, 19570101, NULL, '052301', false, true, 19570101, true),
   (528, 'Gemeente Hazerswoude', NULL, 19000101, 19910101, '052401', false, true, null, false),
   (529, 'Gemeente Heenvliet', NULL, 19000101, 19800101, '052501', false, true, null, false),
   (530, 'Gemeente Heerjansdam', NULL, 19000101, 20030101, '052601', false, true, null, false),
   (531, 'Gemeente Hei- en Boeicop', NULL, 19000101, 19860101, '052701', false, true, null, false),
   (532, 'Gemeente Heinenoord', NULL, 19000101, 19840101, '052801', false, true, null, false),
   (533, 'Gemeente Noorder-Koggenland', NULL, 19790101, 20070101, '052901', false, true, null, false),
   (534, 'Gemeente Hellevoetsluis', NULL, 19000101, NULL, '053001', false, true, 19000101, true),
   (535, 'Gemeente Hendrik-Ido-Ambacht', NULL, 19000101, NULL, '053101', false, true, 19000101, true),
   (536, 'Gemeente Stede Broec', NULL, 19790101, NULL, '053201', false, true, 19790101, true),
   (537, 'Gemeente Heukelum', NULL, 19000101, 19860101, '053301', false, true, null, false),
   (538, 'Gemeente Hillegom', NULL, 19000101, NULL, '053401', false, true, 19000101, true),
   (539, 'Gemeente Hoogblokland', NULL, 19000101, 19860101, '053501', false, true, null, false),
   (540, 'Gemeente Hoornaar', NULL, 19000101, 19860101, '053601', false, true, null, false),
   (541, 'Gemeente Katwijk', NULL, 19000101, NULL, '053701', false, true, 19000101, true),
   (542, 'Gemeente Kedichem', NULL, 19000101, 19860101, '053801', false, true, null, false),
   (543, 'Gemeente Klaaswaal', NULL, 19000101, 19840101, '053901', false, true, null, false),
   (544, 'Gemeente Koudekerk aan den Rijn', NULL, 19000101, 19910101, '054001', false, true, null, false),
   (545, 'Gemeente Krimpen aan de Lek', NULL, 19000101, 19850101, '054101', false, true, null, false),
   (546, 'Gemeente Krimpen aan den IJssel', NULL, 19000101, NULL, '054201', false, true, 19000101, true),
   (547, 'Gemeente Langerak', NULL, 19000101, 19860101, '054301', false, true, null, false),
   (548, 'Gemeente Leerbroek', NULL, 19000101, 19860101, '054401', false, true, null, false),
   (549, 'Gemeente Leerdam', NULL, 19000101, NULL, '054501', false, true, 19000101, true),
   (550, 'Gemeente Leiden', NULL, 19000101, NULL, '054601', false, true, 19000101, true),
   (551, 'Gemeente Leiderdorp', NULL, 19000101, NULL, '054701', false, true, 19000101, true),
   (552, 'Gemeente Leidschendam', NULL, 19380101, 20020101, '054801', false, true, null, false),
   (553, 'Gemeente Leimuiden', NULL, 19000101, 19910101, '054901', false, true, null, false),
   (554, 'Gemeente Lekkerkerk', NULL, 19000101, 19850101, '055001', false, true, null, false),
   (555, 'Gemeente Lexmond', NULL, 19000101, 19860101, '055101', false, true, null, false),
   (556, 'Gemeente De Lier', NULL, 19000101, 20040101, '055201', false, true, null, false),
   (557, 'Gemeente Lisse', NULL, 19000101, NULL, '055301', false, true, 19000101, true),
   (558, 'Gemeente Maasdam', NULL, 19000101, 19840101, '055401', false, true, null, false),
   (559, 'Gemeente Maasland', NULL, 19000101, 20040101, '055501', false, true, null, false),
   (560, 'Gemeente Maassluis', NULL, 19000101, NULL, '055601', false, true, 19000101, true),
   (561, 'Gemeente Meerkerk', NULL, 19000101, 19860101, '055701', false, true, null, false),
   (562, 'Gemeente Wester-Koggenland', NULL, 19790101, 20070101, '055801', false, true, null, false),
   (563, 'Gemeente Middelharnis', NULL, 19000101, 20130101, '055901', false, true, null, false),
   (564, 'Gemeente Moerkapelle', NULL, 19000101, 19910101, '056001', false, true, null, false),
   (565, 'Gemeente Molenaarsgraaf', NULL, 19000101, 19860101, '056101', false, true, null, false),
   (566, 'Gemeente Monster', NULL, 19000101, 20040101, '056201', false, true, null, false),
   (567, 'Gemeente Moordrecht', NULL, 19000101, 20100101, '056301', false, true, null, false),
   (568, 'Gemeente Mijnsheerenland', NULL, 19000101, 19840101, '056401', false, true, null, false),
   (569, 'Gemeente Naaldwijk', NULL, 19000101, 20040101, '056501', false, true, null, false),
   (570, 'Gemeente Nieuw-Beijerland', NULL, 19000101, 19840101, '056601', false, true, null, false),
   (571, 'Gemeente Nieuwerkerk aan den IJssel', NULL, 19000101, 20100101, '056701', false, true, null, false),
   (572, 'Gemeente Bernisse', NULL, 19800101, 20150101, '056801', false, true, null, false),
   (573, 'Gemeente Nieuwkoop', NULL, 19000101, NULL, '056901', false, true, 19000101, true),
   (574, 'Gemeente Nieuwland', NULL, 19000101, 19860101, '057001', false, true, null, false),
   (575, 'Gemeente Nieuw-Lekkerland', NULL, 19000101, 20130101, '057101', false, true, null, false),
   (576, 'Gemeente Nieuwpoort', NULL, 19000101, 19860101, '057201', false, true, null, false),
   (577, 'Gemeente Nieuwveen', NULL, 19000101, 19940101, '057301', false, true, null, false),
   (578, 'Gemeente Noordeloos', NULL, 19000101, 19860101, '057401', false, true, null, false),
   (579, 'Gemeente Noordwijk', NULL, 19000101, NULL, '057501', false, true, 19000101, true),
   (580, 'Gemeente Noordwijkerhout', NULL, 19000101, NULL, '057601', false, true, 19000101, true),
   (581, 'Gemeente Nootdorp', NULL, 19000101, 20020101, '057701', false, true, null, false),
   (582, 'Gemeente Numansdorp', NULL, 19000101, 19840101, '057801', false, true, null, false),
   (583, 'Gemeente Oegstgeest', NULL, 19000101, NULL, '057901', false, true, 19000101, true),
   (584, 'Gemeente Oostflakkee', NULL, 19660101, 20130101, '058001', false, true, null, false),
   (585, 'Gemeente Oostvoorne', NULL, 19000101, 19800101, '058101', false, true, null, false),
   (586, 'Gemeente Ottoland', NULL, 19000101, 19860101, '058201', false, true, null, false),
   (587, 'Gemeente Oud-Alblas', NULL, 19000101, 19860101, '058301', false, true, null, false),
   (588, 'Gemeente Oud-Beijerland', NULL, 19000101, NULL, '058401', false, true, 19000101, true),
   (589, 'Gemeente Binnenmaas', NULL, 19840101, NULL, '058501', false, true, 19840101, true),
   (590, 'Gemeente Oudenhoorn', NULL, 19000101, 19800101, '058601', false, true, null, false),
   (591, 'Gemeente Ouderkerk aan den IJssel', NULL, 19000101, 19850101, '058701', false, true, null, false),
   (592, 'Gemeente Korendijk', NULL, 19840101, NULL, '058801', false, true, 19840101, true),
   (593, 'Gemeente Oudewater', NULL, 19000101, NULL, '058901', false, true, 19000101, true),
   (594, 'Gemeente Papendrecht', NULL, 19000101, NULL, '059001', false, true, 19000101, true),
   (595, 'Gemeente Piershil', NULL, 19000101, 19840101, '059101', false, true, null, false),
   (596, 'Gemeente Poortugaal', NULL, 19000101, 19850101, '059201', false, true, null, false),
   (597, 'Gemeente Puttershoek', NULL, 19000101, 19840101, '059301', false, true, null, false),
   (598, 'Gemeente Pijnacker', NULL, 19000101, 20020101, '059401', false, true, null, false),
   (599, 'Gemeente Reeuwijk', NULL, 19000101, 20110101, '059501', false, true, null, false),
   (600, 'Gemeente Rhoon', NULL, 19000101, 19850101, '059601', false, true, null, false),
   (601, 'Gemeente Ridderkerk', NULL, 19000101, NULL, '059701', false, true, 19000101, true),
   (602, 'Gemeente Rockanje', NULL, 19000101, 19800101, '059801', false, true, null, false),
   (603, 'Gemeente Rotterdam', NULL, 19000101, NULL, '059901', false, true, 19000101, true),
   (604, 'Gemeente Rozenburg', NULL, 19000101, 20100318, '060001', false, true, null, false),
   (605, 'Gemeente Rijnsaterwoude', NULL, 19000101, 19910101, '060101', false, true, null, false),
   (606, 'Gemeente Rijnsburg', NULL, 19000101, 20060101, '060201', false, true, null, false),
   (607, 'Gemeente Rijswijk', NULL, 19000101, NULL, '060301', false, true, 19000101, true),
   (608, 'Gemeente Sassenheim', NULL, 19000101, 20060101, '060401', false, true, null, false),
   (609, 'Gemeente Schelluinen', NULL, 19000101, 19860101, '060501', false, true, null, false),
   (610, 'Gemeente Schiedam', NULL, 19000101, NULL, '060601', false, true, 19000101, true),
   (611, 'Gemeente Schipluiden', NULL, 19000101, 20040101, '060701', false, true, null, false),
   (612, 'Gemeente Schoonhoven', NULL, 19000101, 20150101, '060801', false, true, null, false),
   (613, 'Gemeente Schoonrewoerd', NULL, 19000101, 19860101, '060901', false, true, null, false),
   (614, 'Gemeente Sliedrecht', NULL, 19000101, NULL, '061001', false, true, 19000101, true),
   (615, 'Gemeente Cromstrijen', NULL, 19840101, NULL, '061101', false, true, 19840101, true),
   (616, 'Gemeente Spijkenisse', NULL, 19000101, 20150101, '061201', false, true, null, false),
   (617, 'Gemeente Albrandswaard', NULL, 19850101, NULL, '061301', false, true, 19850101, true),
   (618, 'Gemeente Westvoorne', NULL, 19800101, NULL, '061401', false, true, 19800101, true),
   (619, 'Gemeente Stolwijk', NULL, 19000101, 19850101, '061501', false, true, null, false),
   (620, 'Gemeente Streefkerk', NULL, 19000101, 19860101, '061601', false, true, null, false),
   (621, 'Gemeente Strijen', NULL, 19000101, NULL, '061701', false, true, 19000101, true),
   (622, 'Gemeente Tienhoven (ZH)', NULL, 19000101, 19860101, '061801', false, true, null, false),
   (623, 'Gemeente Valkenburg (ZH)', NULL, 19000101, 20060101, '061901', false, true, null, false),
   (624, 'Gemeente Vianen', NULL, 19000101, NULL, '062001', false, true, 19000101, true),
   (625, 'Gemeente Vierpolders', NULL, 19000101, 19800101, '062101', false, true, null, false),
   (626, 'Gemeente Vlaardingen', NULL, 19000101, NULL, '062201', false, true, 19000101, true),
   (627, 'Gemeente Vlist', NULL, 19000101, 20150101, '062301', false, true, null, false),
   (628, 'Gemeente Voorburg', NULL, 19000101, 20020101, '062401', false, true, null, false),
   (629, 'Gemeente Voorhout', NULL, 19000101, 20060101, '062501', false, true, null, false),
   (630, 'Gemeente Voorschoten', NULL, 19000101, NULL, '062601', false, true, 19000101, true),
   (631, 'Gemeente Waddinxveen', NULL, 18700701, NULL, '062701', false, true, 18700701, true),
   (632, 'Gemeente Warmond', NULL, 19000101, 20060101, '062801', false, true, null, false),
   (633, 'Gemeente Wassenaar', NULL, 19000101, NULL, '062901', false, true, 19000101, true),
   (634, 'Gemeente Wateringen', NULL, 19000101, 20040101, '063001', false, true, null, false),
   (635, 'Gemeente Westmaas', NULL, 19000101, 19840101, '063101', false, true, null, false),
   (636, 'Gemeente Woerden', NULL, 19000101, NULL, '063201', false, true, 19000101, true),
   (637, 'Gemeente Woubrugge', NULL, 19000101, 19910101, '063301', false, true, null, false),
   (638, 'Gemeente Wijngaarden', NULL, 19000101, 19860101, '063401', false, true, null, false),
   (639, 'Gemeente Zevenhoven', NULL, 19000101, 19910101, '063501', false, true, null, false),
   (640, 'Gemeente Zevenhuizen', NULL, 19000101, 19910101, '063601', false, true, null, false),
   (641, 'Gemeente Zoetermeer', NULL, 19000101, NULL, '063701', false, true, 19000101, true),
   (642, 'Gemeente Zoeterwoude', NULL, 19000101, NULL, '063801', false, true, 19000101, true),
   (643, 'Gemeente Zuid-Beijerland', NULL, 19000101, 19840101, '063901', false, true, null, false),
   (644, 'Gemeente Zuidland', NULL, 19000101, 19800101, '064001', false, true, null, false),
   (645, 'Gemeente Zwartewaal', NULL, 19000101, 19800101, '064101', false, true, null, false),
   (646, 'Gemeente Zwijndrecht', NULL, 19000101, NULL, '064201', false, true, 19000101, true),
   (647, 'Gemeente Nederlek', NULL, 19850101, 20150101, '064301', false, true, null, false),
   (648, 'Gemeente Ouderkerk', NULL, 19850101, 20150101, '064401', false, true, null, false),
   (649, 'Gemeente Jacobswoude', NULL, 19910101, 20090101, '064501', false, true, null, false),
   (650, 'Gemeente Rijneveld', NULL, 19910101, 19930101, '064601', false, true, null, false),
   (651, 'Gemeente Moerhuizen', NULL, 19910101, 19920201, '064701', false, true, null, false),
   (652, 'Gemeente Aardenburg', NULL, 19000101, 19950101, '064801', false, true, null, false),
   (653, 'Gemeente Arnemuiden', NULL, 19000101, 19970101, '064901', false, true, null, false),
   (654, 'Gemeente Axel', NULL, 19000101, 20030101, '065001', false, true, null, false),
   (655, 'Gemeente Baarland', NULL, 19000101, 19700101, '065101', false, true, null, false),
   (656, 'Gemeente Biervliet', NULL, 19000101, 19700401, '065201', false, true, null, false),
   (657, 'Gemeente Gaasterlân-Sleat', NULL, 19850605, 20140101, '065301', false, true, null, false),
   (658, 'Gemeente Borsele', NULL, 19700101, NULL, '065401', false, true, 19700101, true),
   (659, 'Gemeente Breskens', NULL, 19000101, 19700401, '065501', false, true, null, false),
   (660, 'Gemeente Brouwershaven', NULL, 19000101, 19970101, '065601', false, true, null, false),
   (661, 'Gemeente Bruinisse', NULL, 19000101, 19970101, '065701', false, true, null, false),
   (662, 'Gemeente Cadzand', NULL, 19000101, 19700401, '065801', false, true, null, false),
   (663, 'Gemeente Clinge', NULL, 19000101, 19700401, '065901', false, true, null, false),
   (664, 'Gemeente Domburg', NULL, 19000101, 19970101, '066001', false, true, null, false),
   (665, 'Gemeente Driewegen', NULL, 19000101, 19700101, '066101', false, true, null, false),
   (666, 'Gemeente Duiveland', NULL, 19610101, 19970101, '066201', false, true, null, false),
   (667, 'Gemeente Ellewoutsdijk', NULL, 19000101, 19700101, '066301', false, true, null, false),
   (668, 'Gemeente Goes', NULL, 19000101, NULL, '066401', false, true, 19000101, true),
   (669, 'Gemeente Graauw en Langendam', NULL, 19000101, 19700401, '066501', false, true, null, false),
   (670, 'Gemeente ''s-Gravenpolder', NULL, 19000101, 19700101, '066601', false, true, null, false),
   (671, 'Gemeente Groede', NULL, 19000101, 19700401, '066701', false, true, null, false),
   (672, 'Gemeente West Maas en Waal', NULL, 19850701, NULL, '066801', false, true, 19850701, true),
   (673, 'Gemeente ''s Heer Abtskerke', NULL, 19000101, 19700101, '066901', false, true, null, false),
   (674, 'Gemeente ''s-Heer Arendskerke', NULL, 19000101, 19700101, '067001', false, true, null, false),
   (675, 'Gemeente ''s-Heerenhoek', NULL, 19000101, 19700101, '067101', false, true, null, false),
   (676, 'Gemeente Heinkenszand', NULL, 19000101, 19700101, '067201', false, true, null, false),
   (677, 'Gemeente Hoedekenskerke', NULL, 19000101, 19700101, '067301', false, true, null, false),
   (678, 'Gemeente Hoek', NULL, 19000101, 19700401, '067401', false, true, null, false),
   (679, 'Gemeente Hontenisse', NULL, 19000101, 20030101, '067501', false, true, null, false),
   (680, 'Gemeente Hoofdplaat', NULL, 19000101, 19700401, '067601', false, true, null, false),
   (681, 'Gemeente Hulst', NULL, 19000101, NULL, '067701', false, true, 19000101, true),
   (682, 'Gemeente Kapelle', NULL, 19000101, NULL, '067801', false, true, 19000101, true),
   (683, 'Gemeente Kattendijke', NULL, 19000101, 19700101, '067901', false, true, null, false),
   (684, 'Gemeente Kloetinge', NULL, 19000101, 19700401, '068001', false, true, null, false),
   (685, 'Gemeente Koewacht', NULL, 19000101, 19700401, '068101', false, true, null, false),
   (686, 'Gemeente Kortgene', NULL, 19000101, 19950101, '068201', false, true, null, false),
   (687, 'Gemeente Wymbritseradiel', NULL, 19860101, 20110101, '068301', false, true, null, false),
   (688, 'Gemeente Krabbendijke', NULL, 19000101, 19700101, '068401', false, true, null, false),
   (689, 'Gemeente Kruiningen', NULL, 19000101, 19700101, '068501', false, true, null, false),
   (690, 'Gemeente Mariekerke', NULL, 19660701, 19970101, '068601', false, true, null, false),
   (691, 'Gemeente Middelburg', NULL, 19000101, NULL, '068701', false, true, 19000101, true),
   (692, 'Gemeente Middenschouwen', NULL, 19610101, 19970101, '068801', false, true, null, false),
   (693, 'Gemeente Giessenlanden', NULL, 19860101, NULL, '068901', false, true, 19860101, true),
   (694, 'Gemeente Nieuwvliet', NULL, 19000101, 19700401, '069001', false, true, null, false),
   (695, 'Gemeente Nisse', NULL, 19000101, 19700101, '069101', false, true, null, false),
   (696, 'Gemeente Oostburg', NULL, 19000101, 20030101, '069201', false, true, null, false),
   (697, 'Gemeente Graafstroom', NULL, 19860101, 20130101, '069301', false, true, null, false),
   (698, 'Gemeente Liesveld', NULL, 19860101, 20130101, '069401', false, true, null, false),
   (699, 'Gemeente Oudelande', NULL, 19000101, 19700101, '069501', false, true, null, false),
   (700, 'Gemeente Oud-Vossemeer', NULL, 19000101, 19710701, '069601', false, true, null, false),
   (701, 'Gemeente Overslag', NULL, 19000101, 19700401, '069701', false, true, null, false),
   (702, 'Gemeente Ovezande', NULL, 19000101, 19700101, '069801', false, true, null, false),
   (703, 'Gemeente Philippine', NULL, 19000101, 19700401, '069901', false, true, null, false),
   (704, 'Gemeente Poortvliet', NULL, 19000101, 19710701, '070001', false, true, null, false),
   (705, 'Gemeente Retranchement', NULL, 19000101, 19700401, '070101', false, true, null, false),
   (706, 'Gemeente Rilland-Bath', NULL, 18771210, 19700101, '070201', false, true, null, false),
   (707, 'Gemeente Reimerswaal', NULL, 19700101, NULL, '070301', false, true, 19700101, true),
   (708, 'Gemeente Sas van Gent', NULL, 19000101, 20030101, '070401', false, true, null, false),
   (709, 'Gemeente Scherpenisse', NULL, 19000101, 19710701, '070501', false, true, null, false),
   (710, 'Gemeente Schoondijke', NULL, 19000101, 19700401, '070601', false, true, null, false),
   (711, 'Gemeente Zederik', NULL, 19860101, NULL, '070701', false, true, 19860101, true),
   (712, 'Gemeente Sint-Annaland', NULL, 19000101, 19710701, '070801', false, true, null, false),
   (713, 'Gemeente St. Jansteen', NULL, 19000101, 19700401, '070901', false, true, null, false),
   (714, 'Gemeente Wûnseradiel', NULL, 19870101, 20110101, '071001', false, true, null, false),
   (715, 'Gemeente Sint-Maartensdijk', NULL, 19000101, 19710701, '071101', false, true, null, false),
   (716, 'Gemeente Sint Philipsland', NULL, 19000101, 19950101, '071201', false, true, null, false),
   (717, 'Gemeente Sluis (oud)', NULL, 19000101, 19950101, '071301', false, true, null, false),
   (718, 'Gemeente Stavenisse', NULL, 19000101, 19710701, '071401', false, true, null, false),
   (719, 'Gemeente Terneuzen', NULL, 19000101, NULL, '071501', false, true, 19000101, true),
   (720, 'Gemeente Tholen', NULL, 19000101, NULL, '071601', false, true, 19000101, true),
   (721, 'Gemeente Veere', NULL, 19000101, NULL, '071701', false, true, 19000101, true),
   (722, 'Gemeente Vlissingen', NULL, 19000101, NULL, '071801', false, true, 19000101, true),
   (723, 'Gemeente Vogelwaarde', NULL, 19360701, 19700401, '071901', false, true, null, false),
   (724, 'Gemeente Valkenisse', NULL, 19660701, 19970101, '072001', false, true, null, false),
   (725, 'Gemeente Waarde', NULL, 19000101, 19700101, '072101', false, true, null, false),
   (726, 'Gemeente Waterlandkerkje', NULL, 19000101, 19700401, '072201', false, true, null, false),
   (727, 'Gemeente Wemeldinge', NULL, 19000101, 19700101, '072301', false, true, null, false),
   (728, 'Gemeente Westdorpe', NULL, 19000101, 19700401, '072401', false, true, null, false),
   (729, 'Gemeente Westerschouwen', NULL, 19610101, 19970101, '072501', false, true, null, false),
   (730, 'Gemeente Westkapelle', NULL, 19000101, 19970101, '072601', false, true, null, false),
   (731, 'Gemeente Wissenkerke', NULL, 19000101, 19950101, '072701', false, true, null, false),
   (732, 'Gemeente Wolphaartsdijk', NULL, 19000101, 19700101, '072801', false, true, null, false),
   (733, 'Gemeente Yerseke', NULL, 19000101, 19700101, '072901', false, true, null, false),
   (734, 'Gemeente IJzendijke', NULL, 19000101, 19700401, '073001', false, true, null, false),
   (735, 'Gemeente Zaamslag', NULL, 19000101, 19700401, '073101', false, true, null, false),
   (736, 'Gemeente Zierikzee', NULL, 19000101, 19970101, '073201', false, true, null, false),
   (737, 'Gemeente Lingewaal', NULL, 19870103, NULL, '073301', false, true, 19870103, true),
   (738, 'Gemeente Zuiddorpe', NULL, 19000101, 19700401, '073401', false, true, null, false),
   (739, 'Gemeente Zuidzande', NULL, 19000101, 19700401, '073501', false, true, null, false),
   (740, 'Gemeente De Ronde Venen', NULL, 19890101, NULL, '073601', false, true, 19890101, true),
   (741, 'Gemeente Tytsjerksteradiel', NULL, 19890101, NULL, '073701', false, true, 19890101, true),
   (742, 'Gemeente Aalburg', NULL, 19000101, NULL, '073801', false, true, 19000101, true),
   (743, 'Gemeente Aarle-Rixtel', NULL, 19000101, 19970101, '073901', false, true, null, false),
   (744, 'Gemeente Almkerk', NULL, 19000101, 19730101, '074001', false, true, null, false),
   (745, 'Gemeente Alphen en Riel', NULL, 19000101, 19970101, '074101', false, true, null, false),
   (746, 'Gemeente Andel', NULL, 19000101, 19730101, '074201', false, true, null, false),
   (747, 'Gemeente Asten', NULL, 19000101, NULL, '074301', false, true, 19000101, true),
   (748, 'Gemeente Baarle-Nassau', NULL, 19000101, NULL, '074401', false, true, 19000101, true),
   (749, 'Gemeente Bakel en Milheeze', NULL, 19000101, 19970101, '074501', false, true, null, false),
   (750, 'Gemeente Beek en Donk', NULL, 19000101, 19970101, '074601', false, true, null, false),
   (751, 'Gemeente Beers', NULL, 19000101, 19940101, '074701', false, true, null, false),
   (752, 'Gemeente Bergen op Zoom', NULL, 19000101, NULL, '074801', false, true, 19000101, true),
   (753, 'Gemeente Bergeyk', NULL, 19000101, 19990101, '074901', false, true, null, false),
   (754, 'Gemeente Berghem', NULL, 19000101, 19940101, '075001', false, true, null, false),
   (755, 'Gemeente Berkel-Enschot', NULL, 19000101, 19970101, '075101', false, true, null, false),
   (756, 'Gemeente Berlicum', NULL, 19000101, 19960101, '075201', false, true, null, false),
   (757, 'Gemeente Best', NULL, 19000101, NULL, '075301', false, true, 19000101, true),
   (758, 'Gemeente Bladel en Netersel', NULL, 19000101, 19970101, '075401', false, true, null, false),
   (759, 'Gemeente Boekel', NULL, 19000101, NULL, '075501', false, true, 19000101, true),
   (760, 'Gemeente Boxmeer', NULL, 19000101, NULL, '075601', false, true, 19000101, true),
   (761, 'Gemeente Boxtel', NULL, 19000101, NULL, '075701', false, true, 19000101, true),
   (762, 'Gemeente Breda', NULL, 19000101, NULL, '075801', false, true, 19000101, true),
   (763, 'Gemeente Budel', NULL, 19000101, 19980128, '075901', false, true, null, false),
   (764, 'Gemeente Chaam', NULL, 19000101, 19970101, '076001', false, true, null, false),
   (765, 'Gemeente Cuijk en Sint Agatha', NULL, 19000101, 19940101, '076101', false, true, null, false),
   (766, 'Gemeente Deurne', NULL, 19260101, NULL, '076201', false, true, 19260101, true),
   (767, 'Gemeente Diessen', NULL, 19000101, 19970101, '076301', false, true, null, false),
   (768, 'Gemeente Dinteloord en Prinsenland', NULL, 19000101, 19970101, '076401', false, true, null, false),
   (769, 'Gemeente Pekela', NULL, 19900101, NULL, '076501', false, true, 19900101, true),
   (770, 'Gemeente Dongen', NULL, 19000101, NULL, '076601', false, true, 19000101, true),
   (771, 'Gemeente Drunen', NULL, 19000101, 19970101, '076701', false, true, null, false),
   (772, 'Gemeente Den Dungen', NULL, 19000101, 19960101, '076801', false, true, null, false),
   (773, 'Gemeente Dussen', NULL, 19000101, 19970101, '076901', false, true, null, false),
   (774, 'Gemeente Eersel', NULL, 19000101, NULL, '077001', false, true, 19000101, true),
   (775, 'Gemeente Eethen', NULL, 19230501, 19730101, '077101', false, true, null, false),
   (776, 'Gemeente Eindhoven', NULL, 19000101, NULL, '077201', false, true, 19000101, true),
   (777, 'Gemeente Empel en Meerwijk', NULL, 19000101, 19710401, '077301', false, true, null, false),
   (778, 'Gemeente Engelen', NULL, 19000101, 19710401, '077401', false, true, null, false),
   (779, 'Gemeente Erp', NULL, 19000101, 19940101, '077501', false, true, null, false),
   (780, 'Gemeente Esch', NULL, 19000101, 19960101, '077601', false, true, null, false),
   (781, 'Gemeente Etten-Leur', NULL, 19680101, NULL, '077701', false, true, 19680101, true),
   (782, 'Gemeente Fijnaart en Heijningen', NULL, 19000101, 19970101, '077801', false, true, null, false),
   (783, 'Gemeente Geertruidenberg', NULL, 19000101, NULL, '077901', false, true, 19000101, true),
   (784, 'Gemeente Geffen', NULL, 19000101, 19930101, '078001', false, true, null, false),
   (785, 'Gemeente Geldrop', NULL, 19000101, 20040101, '078101', false, true, null, false),
   (786, 'Gemeente Gemert', NULL, 19000101, 19970101, '078201', false, true, null, false),
   (787, 'Gemeente Giessen', NULL, 19000101, 19730101, '078301', false, true, null, false),
   (788, 'Gemeente Gilze en Rijen', NULL, 19000101, NULL, '078401', false, true, 19000101, true),
   (789, 'Gemeente Goirle', NULL, 19000101, NULL, '078501', false, true, 19000101, true),
   (790, 'Gemeente Grave', NULL, 19000101, NULL, '078601', false, true, 19000101, true),
   (791, 'Gemeente ''s Gravenmoer', NULL, 19000101, 19970101, '078701', false, true, null, false),
   (792, 'Gemeente Haaren', NULL, 19000101, NULL, '078801', false, true, 19000101, true),
   (793, 'Gemeente Halsteren', NULL, 19000101, 19970101, '078901', false, true, null, false),
   (794, 'Gemeente Haps', NULL, 19000101, 19940101, '079001', false, true, null, false),
   (795, 'Gemeente Heesch', NULL, 19000101, 19950128, '079101', false, true, null, false),
   (796, 'Gemeente Heeswijk-Dinther', NULL, 19690101, 19940101, '079201', false, true, null, false),
   (797, 'Gemeente Heeze', NULL, 19000101, 19970101, '079301', false, true, null, false),
   (798, 'Gemeente Helmond', NULL, 19000101, NULL, '079401', false, true, 19000101, true),
   (799, 'Gemeente Helvoirt', NULL, 19000101, 19960101, '079501', false, true, null, false),
   (800, 'Gemeente ''s-Hertogenbosch', NULL, 19000101, NULL, '079601', false, true, 19000101, true),
   (801, 'Gemeente Heusden', NULL, 19000101, NULL, '079701', false, true, 19000101, true),
   (802, 'Gemeente Hilvarenbeek', NULL, 19000101, NULL, '079801', false, true, 19000101, true),
   (803, 'Gemeente Hoeven', NULL, 19000101, 19970101, '079901', false, true, null, false),
   (804, 'Gemeente Hoogeloon, Hapert en Casteren', NULL, 19000101, 19970101, '080001', false, true, null, false),
   (805, 'Gemeente Hooge en Lage Mierde', NULL, 19000101, 19970101, '080101', false, true, null, false),
   (806, 'Gemeente Hooge en Lage Zwaluwe', NULL, 19000101, 19970101, '080201', false, true, null, false),
   (807, 'Gemeente Huijbergen', NULL, 19000101, 19970101, '080301', false, true, null, false),
   (808, 'Gemeente Klundert', NULL, 19000101, 19970101, '080401', false, true, null, false),
   (809, 'Gemeente Leende', NULL, 19000101, 19970101, '080501', false, true, null, false),
   (810, 'Gemeente Liempde', NULL, 19000101, 19960101, '080601', false, true, null, false),
   (811, 'Gemeente Lieshout', NULL, 19000101, 19970101, '080701', false, true, null, false),
   (812, 'Gemeente Lith', NULL, 19000101, 20110101, '080801', false, true, null, false),
   (813, 'Gemeente Loon op Zand', NULL, 19000101, NULL, '080901', false, true, 19000101, true),
   (814, 'Gemeente Luyksgestel', NULL, 19000101, 19970101, '081001', false, true, null, false),
   (815, 'Gemeente Maarheeze', NULL, 19000101, 19970101, '081101', false, true, null, false),
   (816, 'Gemeente Made en Drimmelen', NULL, 19000101, 19970101, '081201', false, true, null, false),
   (817, 'Gemeente Megen, Haren en Macharen', NULL, 19000101, 19940101, '081301', false, true, null, false),
   (818, 'Gemeente Mierlo', NULL, 19000101, 20040101, '081401', false, true, null, false),
   (819, 'Gemeente Mill en Sint Hubert', NULL, 19000101, NULL, '081501', false, true, 19000101, true),
   (820, 'Gemeente Moergestel', NULL, 19000101, 19970101, '081601', false, true, null, false),
   (821, 'Gemeente Nieuw-Ginneken', NULL, 19420101, 19970101, '081701', false, true, null, false),
   (822, 'Gemeente Nieuw-Vossemeer', NULL, 19000101, 19970101, '081801', false, true, null, false),
   (823, 'Gemeente Nistelrode', NULL, 19000101, 19940101, '081901', false, true, null, false),
   (824, 'Gemeente Nuenen, Gerwen en Nederwetten', NULL, 19000101, NULL, '082001', false, true, 19000101, true),
   (825, 'Gemeente Nuland', NULL, 19000101, 19930101, '082101', false, true, null, false),
   (826, 'Gemeente Oeffelt', NULL, 19000101, 19940101, '082201', false, true, null, false),
   (827, 'Gemeente Oirschot', NULL, 19000101, NULL, '082301', false, true, 19000101, true),
   (828, 'Gemeente Oisterwijk', NULL, 19000101, NULL, '082401', false, true, 19000101, true),
   (829, 'Gemeente Oost-, West- en Middelbeers', NULL, 19000101, 19970101, '082501', false, true, null, false),
   (830, 'Gemeente Oosterhout', NULL, 19000101, NULL, '082601', false, true, 19000101, true),
   (831, 'Gemeente Oploo, St. Anthonis en Ledeacker', NULL, 19000101, 19940101, '082701', false, true, null, false),
   (832, 'Gemeente Oss', NULL, 19000101, NULL, '082801', false, true, 19000101, true),
   (833, 'Gemeente Ossendrecht', NULL, 19000101, 19970101, '082901', false, true, null, false),
   (834, 'Gemeente Oudenbosch', NULL, 19000101, 19970101, '083001', false, true, null, false),
   (835, 'Gemeente Oud en Nieuw Gastel', NULL, 19000101, 19970101, '083101', false, true, null, false),
   (836, 'Gemeente Prinsenbeek', NULL, 19510101, 19970101, '083201', false, true, null, false),
   (837, 'Gemeente Putte', NULL, 19000101, 19970101, '083301', false, true, null, false),
   (838, 'Gemeente Raamsdonk', NULL, 19000101, 19970101, '083401', false, true, null, false),
   (839, 'Gemeente Ravenstein', NULL, 19000101, 20030101, '083501', false, true, null, false),
   (840, 'Gemeente Reusel', NULL, 19000101, 19970101, '083601', false, true, null, false),
   (841, 'Gemeente Riethoven', NULL, 19000101, 19970101, '083701', false, true, null, false),
   (842, 'Gemeente Roosendaal en Nispen', NULL, 19000101, 19970101, '083801', false, true, null, false),
   (843, 'Gemeente Rosmalen', NULL, 19000101, 19960101, '083901', false, true, null, false),
   (844, 'Gemeente Rucphen', NULL, 19000101, NULL, '084001', false, true, 19000101, true),
   (845, 'Gemeente Rijsbergen', NULL, 19000101, 19970101, '084101', false, true, null, false),
   (846, 'Gemeente Rijswijk (NB)', NULL, 19000101, 19730101, '084201', false, true, null, false),
   (847, 'Gemeente Schaijk', NULL, 19000101, 19940101, '084301', false, true, null, false),
   (848, 'Gemeente Schijndel', NULL, 19000101, 20170101, '084401', false, true, null, false),
   (849, 'Gemeente Sint-Michielsgestel', NULL, 19000101, NULL, '084501', false, true, 19000101, true),
   (850, 'Gemeente Sint-Oedenrode', NULL, 19000101, 20170101, '084601', false, true, null, false),
   (851, 'Gemeente Someren', NULL, 19000101, NULL, '084701', false, true, 19000101, true),
   (852, 'Gemeente Son en Breugel', NULL, 19000101, NULL, '084801', false, true, 19000101, true),
   (853, 'Gemeente Sprang-Capelle', NULL, 19230101, 19970101, '084901', false, true, null, false),
   (854, 'Gemeente Standdaarbuiten', NULL, 19000101, 19970101, '085001', false, true, null, false),
   (855, 'Gemeente Steenbergen', NULL, 19000101, NULL, '085101', false, true, 19000101, true),
   (856, 'Gemeente Waterland', NULL, 19910101, NULL, '085201', false, true, 19910101, true),
   (857, 'Gemeente Terheijden', NULL, 19000101, 19970101, '085301', false, true, null, false),
   (858, 'Gemeente Teteringen', NULL, 19000101, 19970101, '085401', false, true, null, false),
   (859, 'Gemeente Tilburg', NULL, 19000101, NULL, '085501', false, true, 19000101, true),
   (860, 'Gemeente Uden', NULL, 19000101, NULL, '085601', false, true, 19000101, true),
   (861, 'Gemeente Udenhout', NULL, 19000101, 19970101, '085701', false, true, null, false),
   (862, 'Gemeente Valkenswaard', NULL, 19000101, NULL, '085801', false, true, 19000101, true),
   (863, 'Gemeente Veen', NULL, 19000101, 19730101, '085901', false, true, null, false),
   (864, 'Gemeente Veghel', NULL, 19000101, 20170101, '086001', false, true, null, false),
   (865, 'Gemeente Veldhoven', NULL, 19210101, NULL, '086101', false, true, 19210101, true),
   (866, 'Gemeente Vessem, Wintelre en Knegsel', NULL, 19000101, 19970101, '086201', false, true, null, false),
   (867, 'Gemeente Vierlingsbeek', NULL, 19000101, 19980101, '086301', false, true, null, false),
   (868, 'Gemeente Vlijmen', NULL, 19000101, 19970101, '086401', false, true, null, false),
   (869, 'Gemeente Vught', NULL, 19000101, NULL, '086501', false, true, 19000101, true),
   (870, 'Gemeente Waalre', NULL, 19000101, NULL, '086601', false, true, 19000101, true),
   (871, 'Gemeente Waalwijk', NULL, 19000101, NULL, '086701', false, true, 19000101, true),
   (872, 'Gemeente Wanroij', NULL, 19000101, 19940101, '086801', false, true, null, false),
   (873, 'Gemeente Waspik', NULL, 19000101, 19970101, '086901', false, true, null, false),
   (874, 'Gemeente Werkendam', NULL, 19000101, NULL, '087001', false, true, 19000101, true),
   (875, 'Gemeente Westerhoven', NULL, 19000101, 19970101, '087101', false, true, null, false),
   (876, 'Gemeente Willemstad', NULL, 19000101, 19970101, '087201', false, true, null, false),
   (877, 'Gemeente Woensdrecht', NULL, 19000101, NULL, '087301', false, true, 19000101, true),
   (878, 'Gemeente Woudrichem', NULL, 19000101, NULL, '087401', false, true, 19000101, true),
   (879, 'Gemeente Wouw', NULL, 19000101, 19970101, '087501', false, true, null, false),
   (880, 'Gemeente Wijk en Aalburg', NULL, 19000101, 19730101, '087601', false, true, null, false),
   (881, 'Gemeente Zeeland', NULL, 19000101, 19940101, '087701', false, true, null, false),
   (882, 'Gemeente Zevenbergen', NULL, 19000101, 19980401, '087801', false, true, null, false),
   (883, 'Gemeente Zundert', NULL, 19000101, NULL, '087901', false, true, 19000101, true),
   (884, 'Gemeente Wormerland', NULL, 19910101, NULL, '088001', false, true, 19910101, true),
   (885, 'Gemeente Onderbanken', NULL, 19820101, NULL, '088101', false, true, 19820101, true),
   (886, 'Gemeente Landgraaf', NULL, 19820101, NULL, '088201', false, true, 19820101, true),
   (887, 'Gemeente Amby', NULL, 19000101, 19700701, '088301', false, true, null, false),
   (888, 'Gemeente Amstenrade', NULL, 19000101, 19820101, '088401', false, true, null, false),
   (889, 'Gemeente Arcen en Velden', NULL, 19000101, 20100101, '088501', false, true, null, false),
   (890, 'Gemeente Baexem', NULL, 19000101, 19910101, '088601', false, true, null, false),
   (891, 'Gemeente Beegden', NULL, 19000101, 19910101, '088701', false, true, null, false),
   (892, 'Gemeente Beek', NULL, 19000101, NULL, '088801', false, true, 19000101, true),
   (893, 'Gemeente Beesel', NULL, 19000101, NULL, '088901', false, true, 19000101, true),
   (894, 'Gemeente Belfeld', NULL, 19000101, 20010101, '089001', false, true, null, false),
   (895, 'Gemeente Bemelen', NULL, 19000101, 19820101, '089101', false, true, null, false),
   (896, 'Gemeente Berg en Terblijt', NULL, 19000101, 19820101, '089201', false, true, null, false),
   (897, 'Gemeente Bergen (L)', NULL, 19000101, NULL, '089301', false, true, 19000101, true),
   (898, 'Gemeente Bingelrade', NULL, 19000101, 19820101, '089401', false, true, null, false),
   (899, 'Gemeente Bocholtz', NULL, 19000101, 19820101, '089501', false, true, null, false),
   (900, 'Gemeente Borgharen', NULL, 19000101, 19700701, '089601', false, true, null, false),
   (901, 'Gemeente Born', NULL, 19000101, 20010101, '089701', false, true, null, false),
   (902, 'Gemeente Broekhuizen', NULL, 19000101, 20010101, '089801', false, true, null, false),
   (903, 'Gemeente Brunssum', NULL, 19000101, NULL, '089901', false, true, 19000101, true),
   (904, 'Gemeente Bunde', NULL, 19000101, 19820101, '090001', false, true, null, false),
   (905, 'Gemeente Cadier en Keer', NULL, 19000101, 19820101, '090101', false, true, null, false),
   (906, 'Gemeente Echt', NULL, 19000101, 20030101, '090201', false, true, null, false),
   (907, 'Gemeente Elsloo', NULL, 19000101, 19820101, '090301', false, true, null, false),
   (908, 'Gemeente Eygelshoven', NULL, 19000101, 19820101, '090401', false, true, null, false),
   (909, 'Gemeente Eijsden', NULL, 19000101, 20110101, '090501', false, true, null, false),
   (910, 'Gemeente Geleen', NULL, 19000101, 20010101, '090601', false, true, null, false),
   (911, 'Gemeente Gennep', NULL, 19000101, NULL, '090701', false, true, 19000101, true),
   (912, 'Gemeente Geulle', NULL, 19000101, 19820101, '090801', false, true, null, false),
   (913, 'Gemeente Grathem', NULL, 19000101, 19910101, '090901', false, true, null, false),
   (914, 'Gemeente Grevenbicht', NULL, 19000101, 19820101, '091001', false, true, null, false),
   (915, 'Gemeente Gronsveld', NULL, 19000101, 19820101, '091101', false, true, null, false),
   (916, 'Gemeente Grubbenvorst', NULL, 19000101, 20010101, '091201', false, true, null, false),
   (917, 'Gemeente Gulpen', NULL, 19000101, 19990101, '091301', false, true, null, false),
   (918, 'Gemeente Haelen', NULL, 19000101, 20070101, '091401', false, true, null, false),
   (919, 'Gemeente Heel en Panheel', NULL, 19000101, 19910101, '091501', false, true, null, false),
   (920, 'Gemeente Heer', NULL, 19000101, 19700701, '091601', false, true, null, false),
   (921, 'Gemeente Heerlen', NULL, 19000101, NULL, '091701', false, true, 19000101, true),
   (922, 'Gemeente Helden', NULL, 19000101, 20100101, '091801', false, true, null, false),
   (923, 'Gemeente Herten', NULL, 19000101, 19910101, '091901', false, true, null, false),
   (924, 'Gemeente Heythuysen', NULL, 19000101, 20070101, '092001', false, true, null, false),
   (925, 'Gemeente Hoensbroek', NULL, 19000101, 19820101, '092101', false, true, null, false),
   (926, 'Gemeente Horn', NULL, 19000101, 19910101, '092201', false, true, null, false),
   (927, 'Gemeente Horst', NULL, 19000101, 20010101, '092301', false, true, null, false),
   (928, 'Gemeente Hulsberg', NULL, 19000101, 19820101, '092401', false, true, null, false),
   (929, 'Gemeente Hunsel', NULL, 19000101, 20070101, '092501', false, true, null, false),
   (930, 'Gemeente Itteren', NULL, 19000101, 19700701, '092601', false, true, null, false),
   (931, 'Gemeente Jabeek', NULL, 19000101, 19820101, '092701', false, true, null, false),
   (932, 'Gemeente Kerkrade', NULL, 19000101, NULL, '092801', false, true, 19000101, true),
   (933, 'Gemeente Kessel', NULL, 19000101, 20100101, '092901', false, true, null, false),
   (934, 'Gemeente Klimmen', NULL, 19000101, 19820101, '093001', false, true, null, false),
   (935, 'Gemeente Limbricht', NULL, 19000101, 19820101, '093101', false, true, null, false),
   (936, 'Gemeente Linne', NULL, 19000101, 19910101, '093201', false, true, null, false),
   (937, 'Gemeente Maasbracht', NULL, 19000101, 20070101, '093301', false, true, null, false),
   (938, 'Gemeente Maasbree', NULL, 19000101, 20100101, '093401', false, true, null, false),
   (939, 'Gemeente Maastricht', NULL, 19000101, NULL, '093501', false, true, 19000101, true),
   (940, 'Gemeente Margraten', NULL, 19000101, 20110101, '093601', false, true, null, false),
   (941, 'Gemeente Meerlo', NULL, 19000101, 19690701, '093701', false, true, null, false),
   (942, 'Gemeente Meerssen', NULL, 19000101, NULL, '093801', false, true, 19000101, true),
   (943, 'Gemeente Melick en Herkenbosch', NULL, 19000101, 19930101, '093901', false, true, null, false),
   (944, 'Gemeente Merkelbeek', NULL, 19000101, 19820101, '094001', false, true, null, false),
   (945, 'Gemeente Meijel', NULL, 19000101, 20100101, '094101', false, true, null, false),
   (946, 'Gemeente Mheer', NULL, 19000101, 19820101, '094201', false, true, null, false),
   (947, 'Gemeente Montfort', NULL, 19000101, 19910101, '094301', false, true, null, false),
   (948, 'Gemeente Mook en Middelaar', NULL, 19000101, NULL, '094401', false, true, 19000101, true),
   (949, 'Gemeente Munstergeleen', NULL, 19000101, 19820101, '094501', false, true, null, false),
   (950, 'Gemeente Nederweert', NULL, 19000101, NULL, '094601', false, true, 19000101, true),
   (951, 'Gemeente Neer', NULL, 19000101, 19910101, '094701', false, true, null, false),
   (952, 'Gemeente Nieuwenhagen', NULL, 19000101, 19820101, '094801', false, true, null, false),
   (953, 'Gemeente Nieuwstadt', NULL, 19000101, 19820101, '094901', false, true, null, false),
   (954, 'Gemeente Noorbeek', NULL, 19000101, 19820101, '095001', false, true, null, false),
   (955, 'Gemeente Nuth', NULL, 19000101, NULL, '095101', false, true, 19000101, true),
   (956, 'Gemeente Obbicht en Papenhoven', NULL, 19000101, 19820101, '095201', false, true, null, false),
   (957, 'Gemeente Ohé en Laak', NULL, 19000101, 19910101, '095301', false, true, null, false),
   (958, 'Gemeente Oirsbeek', NULL, 19000101, 19820101, '095401', false, true, null, false),
   (959, 'Gemeente Ottersum', NULL, 19000101, 19730101, '095501', false, true, null, false),
   (960, 'Gemeente Posterholt', NULL, 19000101, 19940201, '095601', false, true, null, false),
   (961, 'Gemeente Roermond', NULL, 19000101, NULL, '095701', false, true, 19000101, true),
   (962, 'Gemeente Roggel', NULL, 19000101, 19930101, '095801', false, true, null, false),
   (963, 'Gemeente Roosteren', NULL, 19000101, 19820101, '095901', false, true, null, false),
   (964, 'Gemeente Schaesberg', NULL, 19000101, 19820101, '096001', false, true, null, false),
   (965, 'Gemeente Schimmert', NULL, 19000101, 19820101, '096101', false, true, null, false),
   (966, 'Gemeente Schinnen', NULL, 19000101, NULL, '096201', false, true, 19000101, true),
   (967, 'Gemeente Schinveld', NULL, 19000101, 19820101, '096301', false, true, null, false),
   (968, 'Gemeente Sevenum', NULL, 19000101, 20100101, '096401', false, true, null, false),
   (969, 'Gemeente Simpelveld', NULL, 19000101, NULL, '096501', false, true, 19000101, true),
   (970, 'Gemeente Sint Geertruid', NULL, 19000101, 19820101, '096601', false, true, null, false),
   (971, 'Gemeente Sint Odiliënberg', NULL, 19000101, 19910101, '096701', false, true, null, false),
   (972, 'Gemeente Sittard', NULL, 19000101, 20010101, '096801', false, true, null, false),
   (973, 'Gemeente Slenaken', NULL, 19000101, 19820101, '096901', false, true, null, false),
   (974, 'Gemeente Spaubeek', NULL, 19000101, 19820101, '097001', false, true, null, false),
   (975, 'Gemeente Stein', NULL, 19000101, NULL, '097101', false, true, 19000101, true),
   (976, 'Gemeente Stevensweert', NULL, 19000101, 19910101, '097201', false, true, null, false),
   (977, 'Gemeente Stramproy', NULL, 19000101, 19980101, '097301', false, true, null, false),
   (978, 'Gemeente Susteren', NULL, 19000101, 20030101, '097401', false, true, null, false),
   (979, 'Gemeente Swalmen', NULL, 19000101, 20070101, '097501', false, true, null, false),
   (980, 'Gemeente Tegelen', NULL, 19000101, 20010101, '097601', false, true, null, false),
   (981, 'Gemeente Thorn', NULL, 19000101, 20070101, '097701', false, true, null, false),
   (982, 'Gemeente Ubach over Worms', NULL, 19000101, 19820101, '097801', false, true, null, false),
   (983, 'Gemeente Ulestraten', NULL, 19000101, 19820101, '097901', false, true, null, false),
   (984, 'Gemeente Urmond', NULL, 19000101, 19820101, '098001', false, true, null, false),
   (985, 'Gemeente Vaals', NULL, 19000101, NULL, '098101', false, true, 19000101, true),
   (986, 'Gemeente Valkenburg-Houthem', NULL, 19401001, 19820101, '098201', false, true, null, false),
   (987, 'Gemeente Venlo', NULL, 19000101, NULL, '098301', false, true, 19000101, true),
   (988, 'Gemeente Venray', NULL, 19000101, NULL, '098401', false, true, 19000101, true),
   (989, 'Gemeente Vlodrop', NULL, 19000101, 19910101, '098501', false, true, null, false),
   (990, 'Gemeente Voerendaal', NULL, 19000101, NULL, '098601', false, true, 19000101, true),
   (991, 'Gemeente Wanssum', NULL, 19000101, 19690701, '098701', false, true, null, false),
   (992, 'Gemeente Weert', NULL, 19000101, NULL, '098801', false, true, 19000101, true),
   (993, 'Gemeente Wessem', NULL, 19000101, 19910101, '098901', false, true, null, false),
   (994, 'Gemeente Wittem', NULL, 19000101, 19990101, '099001', false, true, null, false),
   (995, 'Gemeente Wijlre', NULL, 19000101, 19820101, '099101', false, true, null, false),
   (996, 'Gemeente Wijnandsrade', NULL, 19000101, 19820101, '099201', false, true, null, false),
   (997, 'Gemeente Meerlo-Wanssum', NULL, 19690701, 20100101, '099301', false, true, null, false),
   (998, 'Gemeente Valkenburg aan de Geul', NULL, 19820101, NULL, '099401', false, true, 19820101, true),
   (999, 'Gemeente Lelystad', NULL, 19800101, NULL, '099501', false, true, 19800101, true),
   (1000, 'Gemeente Zuidelijke IJsselmeerpolders', NULL, 19551130, 19960101, '099601', false, true, null, false),
   (1001, 'Gemeente Centraal Persoonsregister (Niet GBA)', NULL, 19000101, 19901001, '099701', false, true, null, false),
   (1002, 'Gemeente Buitenland (Niet GBA)', NULL, 19000101, 19901001, '099801', false, true, null, false),
   (1003, 'Gemeente Onbekend (Niet GBA)', NULL, 19000101, 19901001, '099901', false, true, null, false),
   (1004, 'Gemeente Aagtekerke', NULL, 19000101, 19660701, '100001', false, true, null, false),
   (1005, 'Gemeente Aalst', NULL, 19000101, 19230101, '100101', false, true, null, false),
   (1006, 'Gemeente Aarlanderveen', NULL, 19000101, 19180101, '100201', false, true, null, false),
   (1007, 'Gemeente Abcoude-Baambrugge', NULL, 19000101, 19410501, '100301', false, true, null, false),
   (1008, 'Gemeente Abcoude-Proostdij', NULL, 19000101, 19410501, '100401', false, true, null, false),
   (1009, 'Gemeente Achttienhoven', NULL, 19000101, 19540101, '100501', false, true, null, false),
   (1010, 'Gemeente Aengwirden', NULL, 19000101, 19340701, '100601', false, true, null, false),
   (1011, 'Gemeente Alem, Maren en Kessel', NULL, 19000101, 19580101, '100701', false, true, null, false),
   (1012, 'Gemeente Alphen', NULL, 19000101, 19180101, '100801', false, true, null, false),
   (1013, 'Gemeente Ambt-Almelo', NULL, 19000101, 19140101, '100901', false, true, null, false),
   (1014, 'Gemeente Ambt-Doetinchem', NULL, 19000101, 19200101, '101001', false, true, null, false),
   (1015, 'Gemeente Ambt-Hardenberg', NULL, 19000101, 19410501, '101101', false, true, null, false),
   (1016, 'Gemeente Ambt-Ommen', NULL, 19000101, 19230501, '101201', false, true, null, false),
   (1017, 'Gemeente Ambt-Vollenhove', NULL, 19000101, 19420201, '101301', false, true, null, false),
   (1018, 'Gemeente Ankeveen', NULL, 19000101, 19660801, '101401', false, true, null, false),
   (1019, 'Gemeente Baardwijk', NULL, 19000101, 19220101, '101501', false, true, null, false),
   (1020, 'Gemeente Balgoij', NULL, 19000101, 19230501, '101601', false, true, null, false),
   (1021, 'Gemeente Barwoutswaarder', NULL, 19000101, 19640201, '101701', false, true, null, false),
   (1022, 'Gemeente Bath', NULL, 19000101, 18780101, '101801', false, true, null, false),
   (1023, 'Gemeente Bellingwolde', NULL, 19000101, 19680901, '101901', false, true, null, false),
   (1024, 'Gemeente Besoijen', NULL, 19000101, 19290101, '102001', false, true, null, false),
   (1025, 'Gemeente Beugen en Rijkevoort', NULL, 19000101, 19420501, '102101', false, true, null, false),
   (1026, 'Gemeente Biggekerke', NULL, 19000101, 19660701, '102201', false, true, null, false),
   (1027, 'Gemeente Bokhoven', NULL, 19000101, 19220101, '102301', false, true, null, false),
   (1028, 'Gemeente Den Bommel', NULL, 19000101, 19660101, '102401', false, true, null, false),
   (1029, 'Gemeente Bommenede', NULL, 19000101, 18650101, '102501', false, true, null, false),
   (1030, 'Gemeente Borkel en Schaft', NULL, 19000101, 19340501, '102601', false, true, null, false),
   (1031, 'Gemeente Boschkapelle', NULL, 19000101, 19360701, '102701', false, true, null, false),
   (1032, 'Gemeente Breukelen-Nijenrode', NULL, 19000101, 19490101, '102801', false, true, null, false),
   (1033, 'Gemeente Breukelen-Sint Pieters', NULL, 19000101, 19490101, '102901', false, true, null, false),
   (1034, 'Gemeente Broek', NULL, 19000101, 18700701, '103001', false, true, null, false),
   (1035, 'Gemeente Broek op Langendijk', NULL, 19000101, 19410801, '103101', false, true, null, false),
   (1036, 'Gemeente Broeksittard', NULL, 19000101, 19421001, '103201', false, true, null, false),
   (1037, 'Gemeente Buggenum', NULL, 19000101, 19421001, '103301', false, true, null, false),
   (1038, 'Gemeente Buiksloot', NULL, 19000101, 19210101, '103401', false, true, null, false),
   (1039, 'Gemeente Burgh', NULL, 19000101, 19610101, '103501', false, true, null, false),
   (1040, 'Gemeente Capelle', NULL, 19000101, 19230101, '103601', false, true, null, false),
   (1041, 'Gemeente Charlois', NULL, 19000101, 18950228, '103701', false, true, null, false),
   (1042, 'Gemeente Colijnsplaat', NULL, 19000101, 19410401, '103801', false, true, null, false),
   (1043, 'Gemeente Cromvoirt', NULL, 19000101, 19330101, '103901', false, true, null, false),
   (1044, 'Gemeente Delfshaven', NULL, 19000101, 18860114, '104001', false, true, null, false),
   (1045, 'Gemeente Deurne en Liessel', NULL, 19000101, 19260101, '104101', false, true, null, false),
   (1046, 'Gemeente Deursen en Dennenburg', NULL, 19000101, 19230501, '104201', false, true, null, false),
   (1047, 'Gemeente Dieden, Demen en Langel', NULL, 19000101, 19230501, '104301', false, true, null, false),
   (1048, 'Gemeente Dinther', NULL, 19000101, 19690101, '104401', false, true, null, false),
   (1049, 'Gemeente Dommelen', NULL, 19000101, 19340501, '104501', false, true, null, false),
   (1050, 'Gemeente Doorwerth', NULL, 19000101, 19230501, '104601', false, true, null, false),
   (1051, 'Gemeente Dreischor', NULL, 19000101, 19610101, '104701', false, true, null, false),
   (1052, 'Gemeente Driebergen', NULL, 19000101, 19310501, '104801', false, true, null, false),
   (1053, 'Gemeente Drongelen', NULL, 19000101, 19230501, '104901', false, true, null, false),
   (1054, 'Gemeente Duivendijke', NULL, 19000101, 19610101, '105001', false, true, null, false),
   (1055, 'Gemeente Duizel en Steensel', NULL, 19000101, 19230101, '105101', false, true, null, false),
   (1056, 'Gemeente Eede', NULL, 19000101, 19410401, '105201', false, true, null, false),
   (1057, 'Gemeente Elkerzee', NULL, 19000101, 19610101, '105301', false, true, null, false),
   (1058, 'Gemeente Ellemeet', NULL, 19000101, 19610101, '105401', false, true, null, false),
   (1059, 'Gemeente Elten', NULL, 19490423, 19630801, '105501', false, true, null, false),
   (1060, 'Gemeente Emmikhoven en Waardhuizen', NULL, 19000101, 18790715, '105601', false, true, null, false),
   (1061, 'Gemeente Escharen', NULL, 19000101, 19420701, '105701', false, true, null, false),
   (1062, 'Gemeente Gameren', NULL, 19000101, 19550701, '105801', false, true, null, false),
   (1063, 'Gemeente Gassel', NULL, 19000101, 19420801, '105901', false, true, null, false),
   (1064, 'Gemeente Genderen', NULL, 19080801, 19230501, '106001', false, true, null, false),
   (1065, 'Gemeente Gestel en Blaarthem', NULL, 19000101, 19200101, '106101', false, true, null, false),
   (1066, 'Gemeente Giessen-Nieuwkerk', NULL, 19000101, 19570101, '106201', false, true, null, false),
   (1067, 'Gemeente Giessendam', NULL, 19000101, 19570101, '106301', false, true, null, false),
   (1068, 'Gemeente Ginneken en Bavel', NULL, 19000101, 19420101, '106401', false, true, null, false),
   (1069, 'Gemeente Grafhorst', NULL, 19000101, 19370101, '106501', false, true, null, false),
   (1070, 'Gemeente Groote Lindt', NULL, 19000101, 18810906, '106601', false, true, null, false),
   (1071, 'Gemeente Grijpskerke', NULL, 19000101, 19660701, '106701', false, true, null, false),
   (1072, 'Gemeente Haamstede', NULL, 19000101, 19610101, '106801', false, true, null, false),
   (1073, 'Gemeente Haarzuilens', NULL, 19000101, 19540101, '106901', false, true, null, false),
   (1074, 'Gemeente Hardinxveld', NULL, 19000101, 19570101, '107001', false, true, null, false),
   (1075, 'Gemeente Hedikhuizen', NULL, 19000101, 19350501, '107101', false, true, null, false),
   (1076, 'Gemeente Heeswijk', NULL, 19000101, 19690101, '107201', false, true, null, false),
   (1077, 'Gemeente Heille', NULL, 19000101, 18800423, '107301', false, true, null, false),
   (1078, 'Gemeente Hekelingen', NULL, 19000101, 19660501, '107401', false, true, null, false),
   (1079, 'Gemeente Hekendorp', NULL, 19000101, 19640201, '107501', false, true, null, false),
   (1080, 'Gemeente Hemmen', NULL, 19000101, 19550701, '107601', false, true, null, false),
   (1081, 'Gemeente Hengstdijk', NULL, 19000101, 19360701, '107701', false, true, null, false),
   (1082, 'Gemeente Herkingen', NULL, 19000101, 19660101, '107801', false, true, null, false),
   (1083, 'Gemeente Herpen', NULL, 19000101, 19410401, '107901', false, true, null, false),
   (1084, 'Gemeente Herpt', NULL, 19000101, 19350501, '108001', false, true, null, false),
   (1085, 'Gemeente Hillegersberg', NULL, 19000101, 19410801, '108101', false, true, null, false),
   (1086, 'Gemeente Hof van Delft', NULL, 19000101, 19210101, '108201', false, true, null, false),
   (1087, 'Gemeente Hoogezand', NULL, 19000101, 19490401, '108301', false, true, null, false),
   (1088, 'Gemeente Hoogkerk', NULL, 19000101, 19690101, '108401', false, true, null, false),
   (1089, 'Gemeente Hoogvliet', NULL, 19000101, 19340501, '108501', false, true, null, false),
   (1090, 'Gemeente Houthem', NULL, 19000101, 19401001, '108601', false, true, null, false),
   (1091, 'Gemeente Houtrijk en Polanen', NULL, 19000101, 18630922, '108701', false, true, null, false),
   (1092, 'Gemeente Huisseling en Neerloon', NULL, 19000101, 19230501, '108801', false, true, null, false),
   (1093, 'Gemeente Hurwenen', NULL, 19000101, 19550701, '108901', false, true, null, false),
   (1094, 'Gemeente Ittervoort', NULL, 19000101, 19420701, '109001', false, true, null, false),
   (1095, 'Gemeente Jaarsveld', NULL, 19000101, 19430101, '109101', false, true, null, false),
   (1096, 'Gemeente Kamperveen', NULL, 19000101, 19370101, '109201', false, true, null, false),
   (1097, 'Gemeente Katendrecht', NULL, 19000101, 18740101, '109301', false, true, null, false),
   (1098, 'Gemeente Kats', NULL, 19000101, 19410401, '109401', false, true, null, false),
   (1099, 'Gemeente Kerkwerve', NULL, 19000101, 19610101, '109501', false, true, null, false),
   (1100, 'Gemeente Kethel en Spaland', NULL, 19000101, 19410801, '109601', false, true, null, false),
   (1101, 'Gemeente Kortenhoef', NULL, 19000101, 19660801, '109701', false, true, null, false),
   (1102, 'Gemeente Koudekerke', NULL, 19000101, 19660701, '109801', false, true, null, false),
   (1103, 'Gemeente Kralingen', NULL, 19000101, 18950228, '109901', false, true, null, false),
   (1104, 'Gemeente Laag-Nieuwkoop', NULL, 19000101, 19420501, '110001', false, true, null, false),
   (1105, 'Gemeente Lange Ruige Weide', NULL, 19000101, 19640201, '110101', false, true, null, false),
   (1106, 'Gemeente Lierop', NULL, 19000101, 19350501, '110201', false, true, null, false),
   (1107, 'Gemeente Linden', NULL, 19000101, 19420801, '110301', false, true, null, false),
   (1108, 'Gemeente Lithoijen', NULL, 19000101, 19390101, '110401', false, true, null, false),
   (1109, 'Gemeente Loenersloot', NULL, 19000101, 19640401, '110501', false, true, null, false),
   (1110, 'Gemeente Lonneker', NULL, 19000101, 19340501, '110601', false, true, null, false),
   (1111, 'Gemeente Loosduinen', NULL, 19000101, 19230701, '110701', false, true, null, false),
   (1112, 'Gemeente Maarsseveen', NULL, 19000101, 19490701, '110801', false, true, null, false),
   (1113, 'Gemeente Maashees en Overloon', NULL, 19000101, 19420501, '110901', false, true, null, false),
   (1114, 'Gemeente Maasniel', NULL, 19000101, 19590801, '111001', false, true, null, false),
   (1115, 'Gemeente Meeuwen', NULL, 19000101, 19230501, '111101', false, true, null, false),
   (1116, 'Gemeente Meliskerke', NULL, 19000101, 19660701, '111201', false, true, null, false),
   (1117, 'Gemeente Melissant', NULL, 19000101, 19660101, '111301', false, true, null, false),
   (1118, 'Gemeente Mesch', NULL, 19000101, 19430101, '111401', false, true, null, false),
   (1119, 'Gemeente Nederhemert', NULL, 19000101, 19550701, '111501', false, true, null, false),
   (1120, 'Gemeente Neeritter', NULL, 19000101, 19420701, '111601', false, true, null, false),
   (1121, 'Gemeente Nieuw- en Sint Joosland', NULL, 19000101, 19660701, '111701', false, true, null, false),
   (1122, 'Gemeente Nieuw-Helvoet', NULL, 19000101, 19600101, '111801', false, true, null, false),
   (1123, 'Gemeente Nieuwe Tonge', NULL, 19000101, 19660101, '111901', false, true, null, false),
   (1124, 'Gemeente Nieuwendam', NULL, 19000101, 19210101, '112001', false, true, null, false),
   (1125, 'Gemeente Nieuwenhoorn', NULL, 19000101, 19600101, '112101', false, true, null, false),
   (1126, 'Gemeente Nieuwerkerk', NULL, 19000101, 19610101, '112201', false, true, null, false),
   (1127, 'Gemeente Nieuwkuijk', NULL, 19000101, 19350501, '112301', false, true, null, false),
   (1128, 'Gemeente Noord-Scharwoude', NULL, 19000101, 19410801, '112401', false, true, null, false),
   (1129, 'Gemeente Noord-Waddinxveen', NULL, 19000101, 18700701, '112501', false, true, null, false),
   (1130, 'Gemeente Noordbroek', NULL, 19000101, 19650701, '112601', false, true, null, false),
   (1131, 'Gemeente Noorddijk', NULL, 19000101, 19690101, '112701', false, true, null, false),
   (1132, 'Gemeente Noordgouwe', NULL, 19000101, 19610101, '112801', false, true, null, false),
   (1133, 'Gemeente Noordwelle', NULL, 19000101, 19610101, '112901', false, true, null, false),
   (1134, 'Gemeente Nunhem', NULL, 19000101, 19421001, '113001', false, true, null, false),
   (1135, 'Gemeente Odijk', NULL, 19000101, 19640901, '113101', false, true, null, false),
   (1136, 'Gemeente Oerle', NULL, 19000101, 19210501, '113201', false, true, null, false),
   (1137, 'Gemeente Ooltgensplaat', NULL, 19000101, 19660101, '113301', false, true, null, false),
   (1138, 'Gemeente Oost- en West-Barendrecht', NULL, 18360101, 18860101, '113401', false, true, null, false),
   (1139, 'Gemeente Oost- en West-Souburg', NULL, 19000101, 19660701, '113501', false, true, null, false),
   (1140, 'Gemeente Oosterland', NULL, 19000101, 19610101, '113601', false, true, null, false),
   (1141, 'Gemeente Oostkapelle', NULL, 19000101, 19660701, '113701', false, true, null, false),
   (1142, 'Gemeente Ossenisse', NULL, 19000101, 19360701, '113801', false, true, null, false),
   (1143, 'Gemeente Oud- en Nieuw-Mathenesse', NULL, 19000101, 18680101, '113901', false, true, null, false),
   (1144, 'Gemeente Oud-Valkenburg', NULL, 19000101, 19401001, '114001', false, true, null, false),
   (1145, 'Gemeente Oud-Vroenhoven', NULL, 19000101, 19200101, '114101', false, true, null, false),
   (1146, 'Gemeente Ouddorp', NULL, 19000101, 19660101, '114201', false, true, null, false),
   (1147, 'Gemeente Oude-Tonge', NULL, 19000101, 19660101, '114301', false, true, null, false),
   (1148, 'Gemeente Oudenrijn', NULL, 19000101, 19540101, '114401', false, true, null, false),
   (1149, 'Gemeente Oudheusden', NULL, 19000101, 19350501, '114501', false, true, null, false),
   (1150, 'Gemeente Oudkarspel', NULL, 19000101, 19410801, '114601', false, true, null, false),
   (1151, 'Gemeente Oudshoorn', NULL, 19000101, 19180101, '114701', false, true, null, false),
   (1152, 'Gemeente Ouwerkerk', NULL, 19000101, 19610101, '114801', false, true, null, false),
   (1153, 'Gemeente Overschie', NULL, 19000101, 19410801, '114901', false, true, null, false),
   (1154, 'Gemeente Oijen en Teeffelen', NULL, 19000101, 19390101, '115001', false, true, null, false),
   (1155, 'Gemeente Papekop', NULL, 19000101, 19640201, '115101', false, true, null, false),
   (1156, 'Gemeente Pernis', NULL, 19000101, 19340501, '115201', false, true, null, false),
   (1157, 'Gemeente Petten', NULL, 19000101, 19290501, '115301', false, true, null, false),
   (1158, 'Gemeente Peursum', NULL, 19000101, 19570101, '115401', false, true, null, false),
   (1159, 'Gemeente Poederoijen', NULL, 19000101, 19550701, '115501', false, true, null, false),
   (1160, 'Gemeente Princenhage', NULL, 19000101, 19420101, '115601', false, true, null, false),
   (1161, 'Gemeente Ransdorp', NULL, 19000101, 19210101, '115701', false, true, null, false),
   (1162, 'Gemeente Reek', NULL, 19000101, 19420701, '115801', false, true, null, false),
   (1163, 'Gemeente Renesse', NULL, 19000101, 19610101, '115901', false, true, null, false),
   (1164, 'Gemeente Rietveld', NULL, 19000101, 19640201, '116001', false, true, null, false),
   (1165, 'Gemeente Rilland', NULL, 19000101, 18780101, '116101', false, true, null, false),
   (1166, 'Gemeente Rimburg', NULL, 19000101, 18870315, '116201', false, true, null, false),
   (1167, 'Gemeente Ritthem', NULL, 19000101, 19660701, '116301', false, true, null, false),
   (1168, 'Gemeente Ruwiel', NULL, 19000101, 19640401, '116401', false, true, null, false),
   (1169, 'Gemeente Rijckholt', NULL, 19000101, 19430101, '116501', false, true, null, false),
   (1170, 'Gemeente Rijsenburg', NULL, 19000101, 19310501, '116601', false, true, null, false),
   (1171, 'Gemeente Sambeek', NULL, 19000101, 19420501, '116701', false, true, null, false),
   (1172, 'Gemeente Sappemeer', NULL, 19000101, 19490401, '116801', false, true, null, false),
   (1173, 'Gemeente Schalkwijk', NULL, 19000101, 19620101, '116901', false, true, null, false),
   (1174, 'Gemeente Schiebroek', NULL, 19000101, 19410801, '117001', false, true, null, false),
   (1175, 'Gemeente Schin op Geul', NULL, 19000101, 19401001, '117101', false, true, null, false),
   (1176, 'Gemeente Schore', NULL, 19000101, 19410101, '117201', false, true, null, false),
   (1177, 'Gemeente Schoten', NULL, 19000101, 19270501, '117301', false, true, null, false),
   (1178, 'Gemeente Schoterland', NULL, 19000101, 19340701, '117401', false, true, null, false),
   (1179, 'Gemeente Serooskerke (Schouwen)', NULL, 19000101, 19610101, '117501', false, true, null, false),
   (1180, 'Gemeente Serooskerke (Walcheren)', NULL, 19000101, 19660701, '117601', false, true, null, false),
   (1181, 'Gemeente St. Anna ter Muiden', NULL, 19000101, 18800423, '117701', false, true, null, false),
   (1182, 'Gemeente Sint Kruis', NULL, 19000101, 19410401, '117801', false, true, null, false),
   (1183, 'Gemeente Sint Laurens', NULL, 19000101, 19660701, '117901', false, true, null, false),
   (1184, 'Gemeente Sint Pieter', NULL, 19000101, 19200101, '118001', false, true, null, false),
   (1185, 'Gemeente Sloten (NH)', NULL, 19000101, 19210101, '118101', false, true, null, false),
   (1186, 'Gemeente Sluipwijk', NULL, 19000101, 18700701, '118201', false, true, null, false),
   (1187, 'Gemeente Soerendonk, Sterksel en Gastel', NULL, 19000101, 19250101, '118301', false, true, null, false),
   (1188, 'Gemeente Sommelsdijk', NULL, 19000101, 19660101, '118401', false, true, null, false),
   (1189, 'Gemeente Spaarndam', NULL, 19000101, 19270501, '118501', false, true, null, false),
   (1190, 'Gemeente Spanbroek', NULL, 19000101, 19590701, '118601', false, true, null, false),
   (1191, 'Gemeente Sprang', NULL, 19000101, 19230101, '118701', false, true, null, false),
   (1192, 'Gemeente Stad aan ''t Haringvliet', NULL, 19000101, 19660101, '118801', false, true, null, false),
   (1193, 'Gemeente Stad-Almelo', NULL, 19000101, 19140101, '118901', false, true, null, false),
   (1194, 'Gemeente Stad-Doetinchem', NULL, 19000101, 19200101, '119001', false, true, null, false),
   (1195, 'Gemeente Stad-Hardenberg', NULL, 19000101, 19410501, '119101', false, true, null, false),
   (1196, 'Gemeente Stad-Ommen', NULL, 19000101, 19230501, '119201', false, true, null, false),
   (1197, 'Gemeente Stad-Vollenhove', NULL, 19000101, 19420201, '119301', false, true, null, false),
   (1198, 'Gemeente Stein (ZH)', NULL, 19000101, 18700701, '119401', false, true, null, false),
   (1199, 'Gemeente Stellendam', NULL, 19000101, 19660101, '119501', false, true, null, false),
   (1200, 'Gemeente Stiphout', NULL, 19000101, 19680101, '119601', false, true, null, false),
   (1201, 'Gemeente Stompwijk', NULL, 19000101, 19380101, '119701', false, true, null, false),
   (1202, 'Gemeente Stoppeldijk', NULL, 19000101, 19360701, '119801', false, true, null, false),
   (1203, 'Gemeente Stratum', NULL, 19000101, 19200101, '119901', false, true, null, false),
   (1204, 'Gemeente Strucht', NULL, 19000101, 18790102, '120001', false, true, null, false),
   (1205, 'Gemeente Strijp', NULL, 19000101, 19200101, '120101', false, true, null, false),
   (1206, 'Gemeente Tienhoven (U)', NULL, 19000101, 19570701, '120201', false, true, null, false),
   (1207, 'Gemeente Tongelre', NULL, 19000101, 19200101, '120301', false, true, null, false),
   (1208, 'Gemeente Tudderen', NULL, 19490422, 19630801, '120401', false, true, null, false),
   (1209, 'Gemeente Tull en ''t Waal', NULL, 19000101, 19620101, '120501', false, true, null, false),
   (1210, 'Gemeente Veldhoven en Meerveldhoven', NULL, 19000101, 19210501, '120601', false, true, null, false),
   (1211, 'Gemeente Veldhuizen', NULL, 19000101, 19540101, '120701', false, true, null, false),
   (1212, 'Gemeente Velp', NULL, 19000101, 19420701, '120801', false, true, null, false),
   (1213, 'Gemeente Veur', NULL, 19000101, 19380101, '120901', false, true, null, false),
   (1214, 'Gemeente Vlaardinger-Ambacht', NULL, 19000101, 19410801, '121001', false, true, null, false),
   (1215, 'Gemeente Vleuten', NULL, 19000101, 19540101, '121101', false, true, null, false),
   (1216, 'Gemeente Vlierden', NULL, 19000101, 19260101, '121201', false, true, null, false),
   (1217, 'Gemeente Vreeland', NULL, 19000101, 19640401, '121301', false, true, null, false),
   (1218, 'Gemeente Vrouwenpolder', NULL, 19000101, 19660701, '121401', false, true, null, false),
   (1219, 'Gemeente Vrijenban', NULL, 19000101, 19210101, '121501', false, true, null, false),
   (1220, 'Gemeente Vrijhoeve-Capelle', NULL, 19000101, 19230101, '121601', false, true, null, false),
   (1221, 'Gemeente Waarder', NULL, 19000101, 19640201, '121701', false, true, null, false),
   (1222, 'Gemeente Wadenoijen', NULL, 19000101, 19560701, '121801', false, true, null, false),
   (1223, 'Gemeente Watergraafsmeer', NULL, 19000101, 19210101, '121901', false, true, null, false),
   (1224, 'Gemeente Wedde', NULL, 19000101, 19680901, '122001', false, true, null, false),
   (1225, 'Gemeente Weesperkarspel', NULL, 19000101, 19660801, '122101', false, true, null, false),
   (1226, 'Gemeente de Werken en Sleeuwijk', NULL, 19000101, 19501001, '122201', false, true, null, false),
   (1227, 'Gemeente Werkhoven', NULL, 19000101, 19640901, '122301', false, true, null, false),
   (1228, 'Gemeente Westbroek', NULL, 19000101, 19570701, '122401', false, true, null, false),
   (1229, 'Gemeente Wildervank', NULL, 19000101, 19690101, '122501', false, true, null, false),
   (1230, 'Gemeente Willige-Langerak', NULL, 19000101, 19430101, '122601', false, true, null, false),
   (1231, 'Gemeente Wilsum', NULL, 19000101, 19370101, '122701', false, true, null, false),
   (1232, 'Gemeente Woensel', NULL, 19000101, 19200101, '122801', false, true, null, false),
   (1233, 'Gemeente Wijk aan Zee en Duin', NULL, 19000101, 19360501, '122901', false, true, null, false),
   (1234, 'Gemeente IJsselmonde', NULL, 19000101, 19410801, '123001', false, true, null, false),
   (1235, 'Gemeente IJzendoorn', NULL, 19000101, 19230501, '123101', false, true, null, false),
   (1236, 'Gemeente Zalk en Veecaten', NULL, 19000101, 19370101, '123201', false, true, null, false),
   (1237, 'Gemeente Zeelst', NULL, 19000101, 19210501, '123301', false, true, null, false),
   (1238, 'Gemeente Zegwaart', NULL, 19000101, 19350501, '123401', false, true, null, false),
   (1239, 'Gemeente Zesgehuchten', NULL, 19000101, 19210501, '123501', false, true, null, false),
   (1240, 'Gemeente Zonnemaire', NULL, 19000101, 19610101, '123601', false, true, null, false),
   (1241, 'Gemeente Zoutelande', NULL, 19000101, 19660701, '123701', false, true, null, false),
   (1242, 'Gemeente Zuid-Scharwoude', NULL, 19000101, 19410801, '123801', false, true, null, false),
   (1243, 'Gemeente Zuid-Waddinxveen', NULL, 19000101, 18700701, '123901', false, true, null, false),
   (1244, 'Gemeente Zuidbroek', NULL, 19000101, 19650701, '124001', false, true, null, false),
   (1245, 'Gemeente Zuidschalkwijk', NULL, 19000101, 18630922, '124101', false, true, null, false),
   (1246, 'Gemeente Zuilen', NULL, 19000101, 19540101, '124201', false, true, null, false),
   (1247, 'Gemeente Zuilichem', NULL, 19000101, 19550701, '124301', false, true, null, false),
   (1248, 'Gemeente Zwammerdam', NULL, 19000101, 19640201, '124401', false, true, null, false),
   (1249, 'Gemeente Zwollerkerspel', NULL, 19000101, 19670801, '124501', false, true, null, false),
   (1250, 'Gemeente Bangert', NULL, 19790101, 19800101, '124601', false, true, null, false),
   (1251, 'Gemeente Beek (NB)', NULL, 19420101, 19510101, '124701', false, true, null, false),
   (1252, 'Gemeente Driel', NULL, 19000101, 19440801, '124801', false, true, null, false),
   (1253, 'Gemeente Nieuwer-Amstel', NULL, 19000101, 19640101, '124901', false, true, null, false),
   (1254, 'Gemeente Onstwedde', NULL, 19000101, 19690101, '125001', false, true, null, false),
   (1255, 'Gemeente Etten en Leur', NULL, 19000101, 19680101, '125101', false, true, null, false),
   (1256, 'Gemeente Valkenburg (L)', NULL, 19000101, 19410715, '125201', false, true, null, false),
   (1257, 'Gemeente Wissekerke', NULL, 19000101, 19580801, '125301', false, true, null, false),
   (1258, 'Gemeente Borssele', NULL, 19000101, 19700101, '125401', false, true, null, false),
   (1259, 'Gemeente Eethen, Genderen en Heesbeen', NULL, 19000101, 19080801, '125501', false, true, null, false),
   (1260, 'Gemeente Koudekerk', NULL, 19000101, 19380101, '125601', false, true, null, false),
   (1261, 'Gemeente Staveren', NULL, 19390101, 19790101, '125701', false, true, null, false),
   (1262, 'Gemeente Rijsoort en Strevelshoek', NULL, 18460101, 18550901, '125801', false, true, null, false),
   (1263, 'Gemeente Millingen', NULL, 19000101, 19550101, '125901', false, true, null, false),
   (1264, 'Gemeente Hemelumer Oldephaert en Noordwolde', NULL, 19000101, 19560101, '126001', false, true, null, false),
   (1265, 'Gemeente Simonshaven', NULL, 19000101, 18550901, '126101', false, true, null, false),
   (1266, 'Gemeente Bleskensgraaf', NULL, 19000101, 18550901, '126201', false, true, null, false),
   (1267, 'Gemeente Drongelen, Hangoort,Gansoyen en Doeveren', NULL, 19000101, 19080801, '126301', false, true, null, false),
   (1268, 'Gemeente Dussen, Munster en Muilkerk', NULL, 19000101, 19080801, '126401', false, true, null, false),
   (1269, 'Gemeente Meeuwen, Hill en Babyloniënbroek', NULL, 19000101, 19080801, '126501', false, true, null, false),
   (1270, 'Gemeente Abtsregt', NULL, 19000101, 18550901, '130101', false, true, null, false),
   (1271, 'Gemeente Achthoven', NULL, 19000101, 18570908, '130201', false, true, null, false),
   (1272, 'Gemeente Achttienhoven (bij Nieuwkoop)', NULL, 19000101, 18550901, '130301', false, true, null, false),
   (1273, 'Gemeente Ackersdijk en Vrouwenregt', NULL, 19000101, 18550901, '130401', false, true, null, false),
   (1274, 'Gemeente Benthorn', NULL, 19000101, 18460101, '130501', false, true, null, false),
   (1275, 'Gemeente Berkenrode', NULL, 19000101, 18580101, '130601', false, true, null, false),
   (1276, 'Gemeente Biert', NULL, 19000101, 18550901, '130701', false, true, null, false),
   (1277, 'Gemeente Biesland', NULL, 19000101, 18330101, '130801', false, true, null, false),
   (1278, 'Gemeente Bijlmermeer', NULL, 19000101, 18460101, '130901', false, true, null, false),
   (1279, 'Gemeente Cabauw', NULL, 19000101, 18570908, '131001', false, true, null, false),
   (1280, 'Gemeente Cillaarshoek', NULL, 19000101, 18320101, '131101', false, true, null, false),
   (1281, 'Gemeente Darthuizen', NULL, 19000101, 18570908, '131201', false, true, null, false),
   (1282, 'Gemeente Dorth', NULL, 19000101, 18310701, '131301', false, true, null, false),
   (1283, 'Gemeente Duist', NULL, 19000101, 18570908, '131401', false, true, null, false),
   (1284, 'Gemeente Edam', NULL, 19000101, 19750101, '131501', false, true, null, false),
   (1285, 'Gemeente Etersheim', NULL, 19000101, 18480101, '131601', false, true, null, false),
   (1286, 'Gemeente Gapinge', NULL, 19000101, 18571002, '131701', false, true, null, false),
   (1287, 'Gemeente Gerverscop', NULL, 19000101, 18570908, '131801', false, true, null, false),
   (1288, 'Gemeente Goidschalxoord', NULL, 19000101, 18550901, '131901', false, true, null, false),
   (1289, 'Gemeente ''s-Gravenambacht', NULL, 19000101, 18340101, '132001', false, true, null, false),
   (1290, 'Gemeente ''s-Gravesloot', NULL, 19000101, 18570908, '132101', false, true, null, false),
   (1291, 'Gemeente Groeneveld', NULL, 19000101, 18550901, '132201', false, true, null, false),
   (1292, 'Gemeente Groet', NULL, 19000101, 18340101, '132301', false, true, null, false),
   (1293, 'Gemeente Grosthuizen', NULL, 19000101, 18540531, '132401', false, true, null, false),
   (1294, 'Gemeente Haarlemmerliede', NULL, 19000101, 18570908, '132501', false, true, null, false),
   (1295, 'Gemeente ''s Heer Hendriks Kinderen', NULL, 19000101, 18570801, '132601', false, true, null, false),
   (1296, 'Gemeente Heer Oudelands Ambacht', NULL, 19000101, 18570819, '132701', false, true, null, false),
   (1297, 'Gemeente Hodenpijl', NULL, 19000101, 18550901, '132801', false, true, null, false),
   (1298, 'Gemeente Hofwegen', NULL, 19000101, 18550901, '132901', false, true, null, false),
   (1299, 'Gemeente Hoog en Woud Harnasch', NULL, 19000101, 18330101, '133001', false, true, null, false),
   (1300, 'Gemeente Hoogeveen in Delfland', NULL, 19000101, 18320101, '133101', false, true, null, false),
   (1301, 'Gemeente Hoogeveen in Rijnland', NULL, 19000101, 18550901, '133201', false, true, null, false),
   (1302, 'Gemeente Hoogmade', NULL, 19000101, 18550917, '133301', false, true, null, false),
   (1303, 'Gemeente Kalslagen', NULL, 19000101, 18540531, '133401', false, true, null, false),
   (1304, 'Gemeente Kamerik Houtdijken', NULL, 19000101, 18570908, '133501', false, true, null, false),
   (1305, 'Gemeente Kamerik Mijzijde', NULL, 19000101, 18570908, '133601', false, true, null, false),
   (1306, 'Gemeente Kijfhoek', NULL, 19000101, 18570819, '133701', false, true, null, false),
   (1307, 'Gemeente Kleine Lindt', NULL, 19000101, 18570613, '133801', false, true, null, false),
   (1308, 'Gemeente Kleverskerke', NULL, 19000101, 18571002, '133901', false, true, null, false),
   (1309, 'Gemeente Laagblokland', NULL, 19000101, 18580101, '134001', false, true, null, false),
   (1310, 'Gemeente Loenen en Wolveren', NULL, 19000101, 18540420, '134101', false, true, null, false),
   (1311, 'Gemeente Maarssenbroek', NULL, 19000101, 18570908, '134201', false, true, null, false),
   (1312, 'Gemeente Meerdervoort', NULL, 19000101, 18550901, '134301', false, true, null, false),
   (1313, 'Gemeente Middelburg (ZH)', NULL, 19000101, 18550901, '134401', false, true, null, false),
   (1314, 'Gemeente De Mijl', NULL, 19000101, 18570131, '134501', false, true, null, false),
   (1315, 'Gemeente Naters', NULL, 19000101, 18560101, '134601', false, true, null, false),
   (1316, 'Gemeente Nederslingelandt', NULL, 19000101, 18570819, '134701', false, true, null, false),
   (1317, 'Gemeente Nieuwland, Kortland en ''s-Graveland', NULL, 19000101, 18560101, '134801', false, true, null, false),
   (1318, 'Gemeente Nieuwveen in Delfland', NULL, 19000101, 18320101, '134901', false, true, null, false),
   (1319, 'Gemeente Noord-Polsbroek', NULL, 19000101, 18570908, '135001', false, true, null, false),
   (1320, 'Gemeente Onwaard', NULL, 19000101, 18580101, '135101', false, true, null, false),
   (1321, 'Gemeente Oost-Barendrecht', NULL, 19000101, 18360101, '135201', false, true, null, false),
   (1322, 'Gemeente Oost-Souburg', NULL, 19000101, 18340101, '135301', false, true, null, false),
   (1323, 'Gemeente Oud-Wulven', NULL, 19000101, 18580101, '135501', false, true, null, false),
   (1324, 'Gemeente Oude en Nieuwe Struiten', NULL, 19000101, 18550901, '135601', false, true, null, false),
   (1325, 'Gemeente Oudhuizen', NULL, 19000101, 18580101, '135701', false, true, null, false),
   (1326, 'Gemeente Oukoop', NULL, 19000101, 18570819, '135801', false, true, null, false),
   (1327, 'Gemeente Portengen', NULL, 19000101, 18590101, '135901', false, true, null, false),
   (1328, 'Gemeente Rhijnauwen', NULL, 19000101, 18570908, '136001', false, true, null, false),
   (1329, 'Gemeente Rietwijkeroord', NULL, 19000101, 18540623, '136101', false, true, null, false),
   (1330, 'Gemeente Rijsoort', NULL, 19000101, 18550101, '136201', false, true, null, false),
   (1331, 'Gemeente Roxenisse', NULL, 19000101, 18580101, '136301', false, true, null, false),
   (1332, 'Gemeente Ruijven', NULL, 19000101, 18460101, '136401', false, true, null, false),
   (1333, 'Gemeente Sandelingen-Ambacht', NULL, 19000101, 18550901, '136501', false, true, null, false),
   (1334, 'Gemeente Schardam', NULL, 19000101, 18540531, '136601', false, true, null, false),
   (1335, 'Gemeente Scharwoude', NULL, 19000101, 18540531, '136701', false, true, null, false),
   (1336, 'Gemeente Schellingwoude', NULL, 19000101, 18570919, '136801', false, true, null, false),
   (1337, 'Gemeente Schokland', NULL, 19000101, 18580901, '136901', false, true, null, false),
   (1338, 'Gemeente Schonauwen', NULL, 19000101, 18580101, '137001', false, true, null, false),
   (1339, 'Gemeente Schuddebeurs en Simonshaven', NULL, 19000101, 18550711, '137101', false, true, null, false),
   (1340, 'Gemeente Sint Anthonypolder', NULL, 19000101, 18320101, '137201', false, true, null, false),
   (1341, 'Gemeente St. Maartensregt', NULL, 19000101, 18550901, '137301', false, true, null, false),
   (1342, 'Gemeente Spaarnwoude', NULL, 19000101, 18570908, '137401', false, true, null, false),
   (1343, 'Gemeente Spijk', NULL, 19000101, 18550901, '137501', false, true, null, false),
   (1344, 'Gemeente Steenbergen en Kruisland', NULL, 19000101, 19620625, '137601', false, true, null, false),
   (1345, 'Gemeente Sterkenburg', NULL, 19000101, 18570908, '137701', false, true, null, false),
   (1346, 'Gemeente Stormpolder', NULL, 19000101, 18550901, '137801', false, true, null, false),
   (1347, 'Gemeente Strevelshoek', NULL, 19000101, 18460101, '138001', false, true, null, false),
   (1348, 'Gemeente Strijensas', NULL, 19000101, 18550901, '138101', false, true, null, false),
   (1349, 'Gemeente Teckop', NULL, 19000101, 18570908, '138201', false, true, null, false),
   (1350, 'Gemeente Tempel', NULL, 19000101, 18580711, '138301', false, true, null, false),
   (1351, 'Gemeente Veenhuizen', NULL, 19000101, 18540413, '138401', false, true, null, false),
   (1352, 'Gemeente De Vennip', NULL, 19000101, 18550815, '138501', false, true, null, false),
   (1353, 'Gemeente Verwolde', NULL, 19000101, 18540522, '138601', false, true, null, false),
   (1354, 'Gemeente Vinkeveen', NULL, 19000101, 18410101, '138701', false, true, null, false),
   (1355, 'Gemeente Vliet', NULL, 19000101, 18460101, '138801', false, true, null, false),
   (1356, 'Gemeente Vrije en Lage Boekhorst', NULL, 19000101, 18550815, '139001', false, true, null, false),
   (1357, 'Gemeente Vrijhoeven', NULL, 19000101, 18400710, '139101', false, true, null, false),
   (1358, 'Gemeente de Vuursche', NULL, 19000101, 18571201, '139201', false, true, null, false),
   (1359, 'Gemeente Waverveen', NULL, 19000101, 18410101, '139301', false, true, null, false),
   (1360, 'Gemeente West-Barendrecht', NULL, 19000101, 18360101, '139401', false, true, null, false),
   (1361, 'Gemeente West-Souburg', NULL, 19000101, 18340101, '139501', false, true, null, false),
   (1362, 'Gemeente Wieldrecht', NULL, 19000101, 18570131, '139601', false, true, null, false),
   (1363, 'Gemeente Wimmenum', NULL, 19000101, 18570713, '139701', false, true, null, false),
   (1364, 'Gemeente Wulverhorst', NULL, 19000101, 18570908, '139801', false, true, null, false),
   (1365, 'Gemeente Zevender', NULL, 19000101, 18570908, '139901', false, true, null, false),
   (1366, 'Gemeente Zouteveen', NULL, 19000101, 18550901, '140001', false, true, null, false),
   (1367, 'Gemeente Zuid-Polsbroek', NULL, 19000101, 18570908, '140101', false, true, null, false),
   (1368, 'Gemeente Zuidbroek (ZH)', NULL, 19000101, 18570613, '140201', false, true, null, false),
   (1369, 'Gemeente Zuidwijk', NULL, 19000101, 18460101, '140301', false, true, null, false),
   (1370, 'Gemeente de Noordoostelijke Polder', NULL, 19000101, 19620701, '140401', false, true, null, false),
   (1371, 'Gemeente Horst aan de Maas', NULL, 20010101, NULL, '150701', false, true, 20010101, true),
   (1372, 'Gemeente Oude IJsselstreek', NULL, 20050101, NULL, '150901', false, true, 20050101, true),
   (1373, 'Gemeente Teylingen', NULL, 20060101, NULL, '152501', false, true, 20060101, true),
   (1374, 'Gemeente Utrechtse Heuvelrug', NULL, 20060101, NULL, '158101', false, true, 20060101, true),
   (1375, 'Gemeente Oost Gelre', NULL, 20060519, NULL, '158601', false, true, 20060519, true),
   (1376, 'Gemeente Koggenland', NULL, 20070101, NULL, '159801', false, true, 20070101, true),
   (1377, 'Gemeente Lansingerland', NULL, 20070101, NULL, '162101', false, true, 20070101, true),
   (1378, 'Gemeente Leudal', NULL, 20070101, NULL, '164001', false, true, 20070101, true),
   (1379, 'Gemeente Maasgouw', NULL, 20070101, NULL, '164101', false, true, 20070101, true),
   (1380, 'Gemeente Eemsmond', NULL, 19920101, NULL, '165101', false, true, 19920101, true),
   (1381, 'Gemeente Gemert-Bakel', NULL, 19970101, NULL, '165201', false, true, 19970101, true),
   (1382, 'Gemeente Halderberge', NULL, 19970101, NULL, '165501', false, true, 19970101, true),
   (1383, 'Gemeente Heeze-Leende', NULL, 19970101, NULL, '165801', false, true, 19970101, true),
   (1384, 'Gemeente Laarbeek', NULL, 19970101, NULL, '165901', false, true, 19970101, true),
   (1385, 'Gemeente Reiderland', NULL, 19910701, 20100101, '166101', false, true, null, false),
   (1386, 'Gemeente De Marne', NULL, 19920101, NULL, '166301', false, true, 19920101, true),
   (1387, 'Gemeente Made', NULL, 19970101, 19980425, '166501', false, true, null, false),
   (1388, 'Gemeente Zevenhuizen-Moerkapelle', NULL, 19920201, 20100101, '166601', false, true, null, false),
   (1389, 'Gemeente Reusel-De Mierden', NULL, 19970101, NULL, '166701', false, true, 19970101, true),
   (1390, 'Gemeente Roerdalen', NULL, 19930101, NULL, '166901', false, true, 19930101, true),
   (1391, 'Gemeente Roggel en Neer', NULL, 19930101, 20070101, '167001', false, true, null, false),
   (1392, 'Gemeente Maasdonk', NULL, 19930101, 20150101, '167101', false, true, null, false),
   (1393, 'Gemeente Rijnwoude', NULL, 19930101, 20140101, '167201', false, true, null, false),
   (1394, 'Gemeente Liemeer', NULL, 19940101, 20070101, '167301', false, true, null, false),
   (1395, 'Gemeente Roosendaal', NULL, 19970101, NULL, '167401', false, true, 19970101, true),
   (1396, 'Gemeente Schouwen-Duiveland', NULL, 19970101, NULL, '167601', false, true, 19970101, true),
   (1397, 'Gemeente Ambt Montfort', NULL, 19940201, 20070101, '167901', false, true, null, false),
   (1398, 'Gemeente Aa en Hunze', NULL, 19980101, NULL, '168001', false, true, 19980101, true),
   (1399, 'Gemeente Borger-Odoorn', NULL, 19980101, NULL, '168101', false, true, 19980101, true),
   (1400, 'Gemeente Cuijk', NULL, 19940101, NULL, '168401', false, true, 19940101, true),
   (1401, 'Gemeente Landerd', NULL, 19940101, NULL, '168501', false, true, 19940101, true),
   (1402, 'Gemeente De Wolden', NULL, 19980101, NULL, '169001', false, true, 19980101, true),
   (1403, 'Gemeente St. Anthonis', NULL, 19940101, 19950704, '169101', false, true, null, false),
   (1404, 'Gemeente Noord-Beveland', NULL, 19950101, NULL, '169501', false, true, 19950101, true),
   (1405, 'Gemeente Wijdemeren', NULL, 20020101, NULL, '169601', false, true, 20020101, true),
   (1406, 'Gemeente Middenveld', NULL, 19980101, 20000101, '169701', false, true, null, false),
   (1407, 'Gemeente Sluis-Aardenburg', NULL, 19950101, 20030101, '169801', false, true, null, false),
   (1408, 'Gemeente Noordenveld', NULL, 19980101, NULL, '169901', false, true, 19980101, true),
   (1409, 'Gemeente Twenterand', NULL, 20020601, NULL, '170001', false, true, 20020601, true),
   (1410, 'Gemeente Westerveld', NULL, 19980101, NULL, '170101', false, true, 19980101, true),
   (1411, 'Gemeente Sint Anthonis', NULL, 19950704, NULL, '170201', false, true, 19950704, true),
   (1412, 'Gemeente Lingewaard', NULL, 20030101, NULL, '170501', false, true, 20030101, true),
   (1413, 'Gemeente Cranendonck', NULL, 19980128, NULL, '170601', false, true, 19980128, true),
   (1414, 'Gemeente Steenwijkerland', NULL, 20030101, NULL, '170801', false, true, 20030101, true),
   (1415, 'Gemeente Moerdijk', NULL, 19980401, NULL, '170901', false, true, 19980401, true),
   (1416, 'Gemeente Echt-Susteren', NULL, 20030101, NULL, '171101', false, true, 20030101, true),
   (1417, 'Gemeente Sluis', NULL, 20030101, NULL, '171401', false, true, 20030101, true),
   (1418, 'Gemeente Drimmelen', NULL, 19980425, NULL, '171901', false, true, 19980425, true),
   (1419, 'Gemeente Bernheze', NULL, 19950128, NULL, '172101', false, true, 19950128, true),
   (1420, 'Gemeente Ferwerderadiel', NULL, 19990101, NULL, '172201', false, true, 19990101, true),
   (1421, 'Gemeente Alphen-Chaam', NULL, 19970101, NULL, '172301', false, true, 19970101, true),
   (1422, 'Gemeente Bergeijk', NULL, 19990101, NULL, '172401', false, true, 19990101, true),
   (1423, 'Gemeente Bladel', NULL, 19970101, NULL, '172801', false, true, 19970101, true),
   (1424, 'Gemeente Gulpen-Wittem', NULL, 19990101, NULL, '172901', false, true, 19990101, true),
   (1425, 'Gemeente Tynaarlo', NULL, 19991201, NULL, '173001', false, true, 19991201, true),
   (1426, 'Gemeente Midden-Drenthe', NULL, 20000101, NULL, '173101', false, true, 20000101, true),
   (1427, 'Gemeente Overbetuwe', NULL, 20010101, NULL, '173401', false, true, 20010101, true),
   (1428, 'Gemeente Hof van Twente', NULL, 20010101, NULL, '173501', false, true, 20010101, true),
   (1429, 'Gemeente Neder-Betuwe', NULL, 20030401, NULL, '174001', false, true, 20030401, true),
   (1430, 'Gemeente Rijssen-Holten', NULL, 20030315, NULL, '174201', false, true, 20030315, true),
   (1431, 'Gemeente Geldrop-Mierlo', NULL, 20040101, NULL, '177101', false, true, 20040101, true),
   (1432, 'Gemeente Olst-Wijhe', NULL, 20020326, NULL, '177301', false, true, 20020326, true),
   (1433, 'Gemeente Dinkelland', NULL, 20020601, NULL, '177401', false, true, 20020601, true),
   (1434, 'Gemeente Westland', NULL, 20040101, NULL, '178301', false, true, 20040101, true),
   (1435, 'Gemeente Midden-Delfland', NULL, 20040101, NULL, '184201', false, true, 20040101, true),
   (1436, 'Gemeente Berkelland', NULL, 20050101, NULL, '185901', false, true, 20050101, true),
   (1437, 'Gemeente Bronckhorst', NULL, 20050101, NULL, '187601', false, true, 20050101, true),
   (1438, 'Gemeente Sittard-Geleen', NULL, 20010101, NULL, '188301', false, true, 20010101, true),
   (1439, 'Gemeente Kaag en Braassem', NULL, 20090101, NULL, '188401', false, true, 20090101, true),
   (1440, 'Gemeente Dantumadiel', NULL, 20090101, NULL, '189101', false, true, 20090101, true),
   (1441, 'Gemeente Zuidplas', NULL, 20100101, NULL, '189201', false, true, 20100101, true),
   (1442, 'Gemeente Peel en Maas', NULL, 20100101, NULL, '189401', false, true, 20100101, true),
   (1443, 'Gemeente Oldambt', NULL, 20100101, NULL, '189501', false, true, 20100101, true),
   (1444, 'Gemeente Zwartewaterland', NULL, 20010101, NULL, '189601', false, true, 20010101, true),
   (1445, 'Gemeente Súdwest-Fryslân', NULL, 20110101, NULL, '190001', false, true, 20110101, true),
   (1446, 'Gemeente Bodegraven-Reeuwijk', NULL, 20110101, NULL, '190101', false, true, 20110101, true),
   (1447, 'Gemeente Eijsden-Margraten', NULL, 20110101, NULL, '190301', false, true, 20110101, true),
   (1448, 'Gemeente Stichtse Vecht', NULL, 20110101, NULL, '190401', false, true, 20110101, true),
   (1449, 'Gemeente Menameradiel', NULL, 20110101, NULL, '190801', false, true, 20110101, true),
   (1450, 'Gemeente Hollands Kroon', NULL, 20120101, NULL, '191101', false, true, 20120101, true),
   (1451, 'Gemeente Leidschendam-Voorburg', NULL, 20020101, NULL, '191601', false, true, 20020101, true),
   (1452, 'Gemeente Pijnacker-Nootdorp', NULL, 20020101, NULL, '192601', false, true, 20020101, true),
   (1453, 'Gemeente Heel', NULL, 19910101, 20070101, '193701', false, true, null, false),
   (1454, 'Gemeente Montferland', NULL, 20050101, NULL, '195501', false, true, 20050101, true),
   (1455, 'Gemeente Menterwolde', NULL, 19910201, NULL, '198701', false, true, 19910201, true),
   (1456, 'Gemeente Goeree-Overflakkee', NULL, 20130101, NULL, '192401', false, true, 20130101, true),
   (1457, 'Gemeente Molenwaard', NULL, 20130101, NULL, '192701', false, true, 20130101, true),
   (1458, 'Gemeente De Friese Meren', NULL, 20140101, 20150701, '192101', false, true, null, false),
   (1460, 'Gemeente Krimpenerwaard', NULL, 20150101, NULL, '193101', false, true, 20150101, true),
   (1461, 'Gemeente Nissewaard', NULL, 20150101, NULL, '193001', false, true, 20150101, true),
   (1462, 'Gemeente De Fryske Marren', NULL, 20150701, NULL, '194001', false, true, 20150701, true),
   (1463, 'Gemeente Gooise Meren', NULL, 20160101, NULL, '194201', false, true, 20160101, true),
   (1464, 'Gemeente Berg en Dal', NULL, 20160101, NULL, '194501', false, true, 20160101, true),
   (1465, 'Gemeente Meierijstad', NULL, 20170101, NULL, '194801', false, true, 20170101, true);
ALTER SEQUENCE Kern.seq_Partij RESTART WITH 5000;
INSERT INTO Kern.PartijRol (Partij, Rol, DatIngang, DatEinde, IndAG) VALUES
 (3, 3, 18140101, NULL, true),
 (2002, 3, 20160106, NULL, true),
 (5, 2, 19000101, 19900101, true),
 (6, 2, 19000101, 19900101, true),
 (7, 2, 19000101, NULL, true),
 (8, 2, 19000101, 19900101, true),
 (9, 2, 19000101, NULL, true),
 (10, 2, 19000101, 19910701, true),
 (11, 2, 19680101, NULL, true),
 (12, 2, 19000101, 19900101, true),
 (13, 2, 19000101, NULL, true),
 (14, 2, 19000101, NULL, true),
 (15, 2, 19000101, 19900101, true),
 (16, 2, 19000101, 19900101, true),
 (17, 2, 19000101, 19900101, true),
 (18, 2, 19000101, NULL, true),
 (19, 2, 19000101, NULL, true),
 (20, 2, 19000101, 19900101, true),
 (21, 2, 19000101, NULL, true),
 (22, 2, 19490401, NULL, true),
 (23, 2, 19790101, 19920101, true),
 (24, 2, 19000101, 19900101, true),
 (25, 2, 19000101, 19900101, true),
 (26, 2, 19000101, NULL, true),
 (27, 2, 19000101, 19900101, true),
 (28, 2, 19000101, NULL, true),
 (29, 2, 19000101, NULL, true),
 (30, 2, 19000101, 19900101, true),
 (31, 2, 19000101, 19900101, true),
 (32, 2, 19000101, 19900101, true),
 (33, 2, 19000101, 19900101, true),
 (34, 2, 19000101, 19900101, true),
 (35, 2, 19000101, 19900101, true),
 (36, 2, 19000101, 19900101, true),
 (37, 2, 19650701, 19910201, true),
 (38, 2, 19840101, NULL, true),
 (39, 2, 19000101, 19900101, true),
 (40, 2, 19000101, 19900101, true),
 (41, 2, 19690101, NULL, true),
 (42, 2, 19000101, 19900101, true),
 (43, 2, 19000101, 20100101, true),
 (44, 2, 19000101, NULL, true),
 (45, 2, 19000101, 19900101, true),
 (46, 2, 19000101, 19900101, true),
 (47, 2, 19000101, 19790101, true),
 (48, 2, 19000101, 19790101, true),
 (49, 2, 19000101, 19920101, true),
 (50, 2, 19000101, 19900101, true),
 (51, 2, 19000101, NULL, true),
 (52, 2, 19000101, NULL, true),
 (53, 2, 19000101, 19900101, true),
 (54, 2, 19840101, NULL, true),
 (55, 2, 19850301, 20140101, true),
 (56, 2, 19000101, 20100101, true),
 (57, 2, 19000101, NULL, true),
 (58, 2, 19000101, 19900101, true),
 (59, 2, 19850103, 20140101, true),
 (60, 2, 19000101, NULL, true),
 (61, 2, 19840101, 19850103, true),
 (62, 2, 19840101, NULL, true),
 (63, 2, 19000101, NULL, true),
 (64, 2, 19000101, NULL, true),
 (65, 2, 19000101, 19840101, true),
 (66, 2, 19000101, 19840101, true),
 (67, 2, 19000101, NULL, true),
 (68, 2, 19000101, 20110101, true),
 (69, 2, 19000101, 20090101, true),
 (70, 2, 19000101, 19840101, true),
 (71, 2, 19000101, 19840101, true),
 (72, 2, 19000101, 19990101, true),
 (73, 2, 19000101, 19840101, true),
 (74, 2, 19000101, NULL, true),
 (75, 2, 19000101, 19850605, true),
 (76, 2, 19000101, NULL, true),
 (77, 2, 19000101, 19840101, true),
 (78, 2, 19340701, NULL, true),
 (79, 2, 19560101, 19840101, true),
 (80, 2, 19000101, 19840101, true),
 (81, 2, 19000101, 19840101, true),
 (82, 2, 19000101, 19840101, true),
 (83, 2, 19000101, NULL, true),
 (84, 2, 19000101, NULL, true),
 (85, 2, 19000101, NULL, true),
 (86, 2, 19000101, 20140101, true),
 (87, 2, 19000101, 20110101, true),
 (88, 2, 19000101, 19840101, true),
 (89, 2, 19000101, NULL, true),
 (90, 2, 19000101, NULL, true),
 (91, 2, 19000101, 19840101, true),
 (92, 2, 19000101, NULL, true),
 (93, 2, 19000101, 19840101, true),
 (94, 2, 19000101, NULL, true),
 (95, 2, 19000101, 20110101, true),
 (96, 2, 19000101, 19840101, true),
 (97, 2, 19000101, NULL, true),
 (98, 2, 19000101, 19890101, true),
 (99, 2, 19000101, 19840101, true),
 (100, 2, 19000101, NULL, true),
 (101, 2, 19000101, 19840101, true),
 (102, 2, 19000101, NULL, true),
 (103, 2, 19000101, 19870101, true),
 (104, 2, 19000101, 19840101, true),
 (105, 2, 19000101, 19860101, true),
 (106, 2, 19000101, 19840101, true),
 (107, 2, 19840101, 19850126, true),
 (108, 2, 19840101, 20110101, true),
 (109, 2, 19000101, 19980101, true),
 (110, 2, 19000101, NULL, true),
 (111, 2, 19000101, 19980101, true),
 (112, 2, 19000101, 19980101, true),
 (113, 2, 19000101, NULL, true),
 (114, 2, 19000101, 19980101, true),
 (115, 2, 19000101, 19980101, true),
 (116, 2, 19000101, 19980101, true),
 (117, 2, 19000101, 19980101, true),
 (118, 2, 19000101, NULL, true),
 (119, 2, 19000101, 19980101, true),
 (120, 2, 19000101, 19980101, true),
 (121, 2, 19000101, 19980101, true),
 (122, 2, 19000101, NULL, true),
 (123, 2, 19000101, NULL, true),
 (124, 2, 19000101, 19980101, true),
 (125, 2, 19000101, 19980101, true),
 (126, 2, 19000101, 19980101, true),
 (127, 2, 19000101, 19980101, true),
 (128, 2, 19000101, 19980101, true),
 (129, 2, 19000101, 19980101, true),
 (130, 2, 19000101, 19980101, true),
 (131, 2, 19000101, 19980101, true),
 (132, 2, 19000101, 19980101, true),
 (133, 2, 18840501, 19980101, true),
 (134, 2, 19000101, 19980101, true),
 (135, 2, 19000101, 19980101, true),
 (136, 2, 19000101, 19980101, true),
 (137, 2, 19000101, 19980101, true),
 (138, 2, 19000101, 19980101, true),
 (139, 2, 19000101, 19980101, true),
 (140, 2, 19000101, 19991201, true),
 (141, 2, 19000101, 19980101, true),
 (142, 2, 19000101, 19980101, true),
 (143, 2, 19840101, 19850301, true),
 (144, 2, 19850126, NULL, true),
 (145, 2, 19130101, NULL, true),
 (146, 2, 19000101, 20010101, true),
 (147, 2, 19000101, 20010101, true),
 (148, 2, 19000101, 20050101, true),
 (149, 2, 19000101, 19730101, true),
 (150, 2, 19000101, 19730101, true),
 (151, 2, 19000101, NULL, true),
 (152, 2, 19000101, NULL, true),
 (153, 2, 19000101, 20020601, true),
 (154, 2, 19000101, NULL, true),
 (155, 2, 19000101, 20010101, true),
 (156, 2, 19000101, 19990101, true),
 (157, 2, 19000101, NULL, true),
 (158, 2, 19000101, 20010101, true),
 (159, 2, 19000101, 19730101, true),
 (160, 2, 19000101, 20010101, true),
 (161, 2, 19000101, 20010101, true),
 (162, 2, 19000101, NULL, true),
 (163, 2, 19000101, 20010101, true),
 (164, 2, 19410501, NULL, true),
 (165, 2, 19000101, 20010101, true),
 (166, 2, 19000101, 20010101, true),
 (167, 2, 19000101, NULL, true),
 (168, 2, 19000101, NULL, true),
 (169, 2, 19000101, 20010101, true),
 (170, 2, 19000101, NULL, true),
 (171, 2, 19000101, 19730101, true),
 (172, 2, 19000101, NULL, true),
 (173, 2, 19000101, 20010101, true),
 (174, 2, 19000101, 20010101, true),
 (175, 2, 19620701, NULL, true),
 (176, 2, 19000101, 19730101, true),
 (177, 2, 19000101, NULL, true),
 (178, 2, 19000101, 20020326, true),
 (179, 2, 19230501, NULL, true),
 (180, 2, 19000101, 20010101, true),
 (181, 2, 19000101, NULL, true),
 (182, 2, 19000101, 20030315, true),
 (183, 2, 19000101, 20010101, true),
 (184, 2, 19000101, NULL, true),
 (185, 2, 19000101, 20030101, true),
 (186, 2, 19000101, 19730101, true),
 (187, 2, 19000101, NULL, true),
 (188, 2, 19000101, NULL, true),
 (189, 2, 19420201, 19730101, true),
 (190, 2, 19000101, 20020601, true),
 (191, 2, 19000101, 19730101, true),
 (192, 2, 19000101, 20010101, true),
 (193, 2, 19000101, NULL, true),
 (194, 2, 19000101, 20010101, true),
 (195, 2, 19000101, 20010101, true),
 (196, 2, 19000101, 20010101, true),
 (197, 2, 19000101, NULL, true),
 (198, 2, 19730101, 20010101, true),
 (199, 2, 19730101, 20010101, true),
 (200, 2, 19850101, NULL, true),
 (201, 2, 19000101, NULL, true),
 (202, 2, 19000101, 19990101, true),
 (203, 2, 19000101, 20050101, true),
 (204, 2, 19000101, NULL, true),
 (205, 2, 19000101, 19840101, true),
 (206, 2, 19000101, NULL, true),
 (207, 2, 19000101, NULL, true),
 (208, 2, 19000101, 19840101, true),
 (209, 2, 19000101, 19780101, true),
 (210, 2, 19000101, 20030101, true),
 (211, 2, 19000101, 20050101, true),
 (212, 2, 19000101, 19840101, true),
 (213, 2, 19000101, NULL, true),
 (214, 2, 19000101, 19780101, true),
 (215, 2, 19000101, 20050101, true),
 (216, 2, 19000101, 19990101, true),
 (217, 2, 19000101, NULL, true),
 (218, 2, 19000101, NULL, true),
 (219, 2, 19000101, 19780101, true),
 (220, 2, 19000101, NULL, true),
 (221, 2, 19000101, 19780101, true),
 (222, 2, 19000101, 20050101, true),
 (223, 2, 19000101, 20050101, true),
 (224, 2, 19000101, 20020101, true),
 (225, 2, 19000101, NULL, true),
 (226, 2, 19200101, NULL, true),
 (227, 2, 19000101, 19740801, true),
 (228, 2, 19000101, 19840101, true),
 (229, 2, 19000101, NULL, true),
 (230, 2, 19000101, NULL, true),
 (231, 2, 19000101, 20020101, true),
 (232, 2, 19000101, NULL, true),
 (233, 2, 19000101, 20050101, true),
 (234, 2, 19000101, NULL, true),
 (235, 2, 19000101, 20010101, true),
 (236, 2, 19000101, NULL, true),
 (237, 2, 19000101, NULL, true),
 (238, 2, 19000101, 19780101, true),
 (239, 2, 19000101, 19800701, true),
 (240, 2, 19000101, NULL, true),
 (241, 2, 19000101, 20050101, true),
 (242, 2, 19000101, 20010101, true),
 (243, 2, 19000101, 20050101, true),
 (244, 2, 19000101, 20060519, true),
 (245, 2, 19000101, 20160101, true),
 (246, 2, 19000101, 19780101, true),
 (247, 2, 19000101, NULL, true),
 (248, 2, 19000101, NULL, true),
 (249, 2, 19000101, 19990101, true),
 (250, 2, 19000101, NULL, true),
 (251, 2, 19000101, 19990101, true),
 (252, 2, 19000101, 20050101, true),
 (253, 2, 19000101, 19850101, true),
 (254, 2, 19000101, 19860101, true),
 (255, 2, 19000101, 20010101, true),
 (256, 2, 19000101, NULL, true),
 (257, 2, 19000101, 20000101, true),
 (258, 2, 19000101, 19840101, true),
 (259, 2, 19000101, 20010101, true),
 (260, 2, 19000101, 20050101, true),
 (261, 2, 19000101, 19990101, true),
 (262, 2, 19000101, 20030401, true),
 (263, 2, 19000101, 19710801, true),
 (264, 2, 19000101, 20050101, true),
 (265, 2, 19000101, 19990101, true),
 (266, 2, 19000101, NULL, true),
 (267, 2, 19440801, NULL, true),
 (268, 2, 19000101, 19990101, true),
 (269, 2, 19550101, 20150101, true),
 (270, 2, 19000101, 20050101, true),
 (271, 2, 19000101, NULL, true),
 (272, 2, 19000101, NULL, true),
 (273, 2, 19000101, NULL, true),
 (274, 2, 19000101, 19780101, true),
 (275, 2, 19000101, 19800701, true),
 (276, 2, 19000101, 19850101, true),
 (277, 2, 19000101, NULL, true),
 (278, 2, 19000101, NULL, true),
 (279, 2, 19000101, NULL, true),
 (280, 2, 19000101, 19990101, true),
 (281, 2, 19000101, NULL, true),
 (282, 2, 19000101, 20050101, true),
 (283, 2, 19000101, NULL, true),
 (284, 2, 19000101, 20050101, true),
 (285, 2, 19000101, NULL, true),
 (286, 2, 19000101, 20150101, true),
 (287, 2, 19000101, 20010101, true),
 (288, 2, 19000101, 19780101, true),
 (289, 2, 19000101, NULL, true),
 (290, 2, 19000101, 20050101, true),
 (291, 2, 19000101, 19870103, true),
 (292, 2, 19000101, 19780101, true),
 (293, 2, 19000101, NULL, true),
 (294, 2, 19000101, 19850701, true),
 (295, 2, 19000101, 20050101, true),
 (296, 2, 19000101, 20050101, true),
 (297, 2, 19000101, NULL, true),
 (298, 2, 19000101, NULL, true),
 (299, 2, 19000101, 20050101, true),
 (300, 2, 19000101, NULL, true),
 (301, 2, 19000101, NULL, true),
 (302, 2, 19000101, 20050101, true),
 (303, 2, 19000101, NULL, true),
 (304, 2, 19000101, 19780101, true),
 (305, 2, 19000101, NULL, true),
 (306, 2, 19720101, NULL, true),
 (307, 2, 19720101, NULL, true),
 (308, 2, 19780101, NULL, true),
 (309, 2, 19410501, 20110101, true),
 (310, 2, 19000101, 20060101, true),
 (311, 2, 19000101, NULL, true),
 (312, 2, 19000101, NULL, true),
 (313, 2, 19000101, 19890101, true),
 (314, 2, 19000101, NULL, true),
 (315, 2, 19490101, 20110101, true),
 (316, 2, 19000101, NULL, true),
 (317, 2, 19000101, NULL, true),
 (318, 2, 19000101, 19960101, true),
 (319, 2, 19000101, 20060101, true),
 (320, 2, 19310501, 20060101, true),
 (321, 2, 19000101, NULL, true),
 (322, 2, 19000101, 20010101, true),
 (323, 2, 19000101, 19700901, true),
 (324, 2, 19000101, 19740101, true),
 (325, 2, 19000101, NULL, true),
 (326, 2, 19000101, 19710701, true),
 (327, 2, 19000101, 19890101, true),
 (328, 2, 19000101, 19890101, true),
 (329, 2, 19000101, 19960101, true),
 (330, 2, 19000101, 20060101, true),
 (331, 2, 19000101, NULL, true),
 (332, 2, 19000101, 19890101, true),
 (333, 2, 19000101, 20110101, true),
 (334, 2, 19000101, 20020101, true),
 (335, 2, 19000101, NULL, true),
 (336, 2, 19000101, 20060101, true),
 (337, 2, 19000101, 20110101, true),
 (338, 2, 19000101, 20010101, true),
 (339, 2, 19000101, NULL, true),
 (340, 2, 19000101, 19890101, true),
 (341, 2, 19000101, 19890101, true),
 (342, 2, 18570613, 19890101, true),
 (343, 2, 19000101, NULL, true),
 (344, 2, 19000101, NULL, true),
 (345, 2, 19000101, 19890101, true),
 (346, 2, 19000101, NULL, true),
 (347, 2, 19000101, 19690601, true),
 (348, 2, 19000101, NULL, true),
 (349, 2, 19000101, NULL, true),
 (350, 2, 18410101, 19890101, true),
 (351, 2, 19540101, 20010101, true),
 (352, 2, 19000101, 19710701, true),
 (353, 2, 19000101, 19890101, true),
 (354, 2, 19000101, 19890101, true),
 (355, 2, 19000101, NULL, true),
 (356, 2, 19000101, NULL, true),
 (357, 2, 19000101, NULL, true),
 (358, 2, 19000101, 19890101, true),
 (359, 2, 19000101, NULL, true),
 (360, 2, 19710701, NULL, true),
 (361, 2, 19780701, 20010101, true),
 (362, 2, 19000101, NULL, true),
 (363, 2, 19000101, 19790101, true),
 (364, 2, 19000101, 20020101, true),
 (365, 2, 19000101, NULL, true),
 (366, 2, 19640101, NULL, true),
 (367, 2, 19000101, NULL, true),
 (368, 2, 19000101, 20110101, true),
 (369, 2, 19700801, 20150101, true),
 (370, 2, 18700718, 20120101, true),
 (371, 2, 19000101, 19740101, true),
 (372, 2, 19000101, 19790101, true),
 (373, 2, 19000101, 19900101, true),
 (374, 2, 19000101, NULL, true),
 (375, 2, 19000101, 19700801, true),
 (376, 2, 19000101, 20090101, true),
 (377, 2, 19000101, NULL, true),
 (378, 2, 19000101, 19790101, true),
 (379, 2, 19000101, NULL, true),
 (380, 2, 19000101, NULL, true),
 (381, 2, 19000101, NULL, true),
 (382, 2, 19000101, 19790101, true),
 (383, 2, 19000101, 19790101, true),
 (384, 2, 19000101, 19910101, true),
 (385, 2, 19000101, 20160101, true),
 (386, 2, 19000101, 19900101, true),
 (387, 2, 19000101, NULL, true),
 (388, 2, 19000101, NULL, true),
 (389, 2, 19750101, NULL, true),
 (390, 2, 19000101, 19780701, true),
 (391, 2, 19000101, 19780701, true),
 (392, 2, 19000101, NULL, true),
 (393, 2, 19000101, 19700801, true),
 (394, 2, 19000101, 20020101, true),
 (395, 2, 19000101, 19790101, true),
 (396, 2, 19000101, NULL, true),
 (397, 2, 18570101, NULL, true),
 (398, 2, 18550101, NULL, true),
 (399, 2, 19000101, 20130101, true),
 (400, 2, 19000101, NULL, true),
 (401, 2, 19000101, NULL, true),
 (402, 2, 19000101, NULL, true),
 (403, 2, 19000101, NULL, true),
 (404, 2, 19000101, NULL, true),
 (405, 2, 19000101, 19790101, true),
 (406, 2, 19000101, NULL, true),
 (407, 2, 19000101, 19790101, true),
 (408, 2, 19000101, 19790101, true),
 (409, 2, 19000101, NULL, true),
 (410, 2, 19000101, NULL, true),
 (411, 2, 19000101, 19910101, true),
 (412, 2, 19000101, 19910101, true),
 (413, 2, 19000101, 19910101, true),
 (414, 2, 19000101, 19721001, true),
 (415, 2, 19000101, 19740101, true),
 (416, 2, 19700801, 20120101, true),
 (417, 2, 19000101, 19740101, true),
 (418, 2, 19000101, 19700801, true),
 (419, 2, 19000101, NULL, true),
 (420, 2, 19410801, NULL, true),
 (421, 2, 19000101, NULL, true),
 (422, 2, 19000101, 20020101, true),
 (423, 2, 19000101, 19910101, true),
 (424, 2, 19000101, NULL, true),
 (425, 2, 19000101, 19700801, true),
 (426, 2, 19000101, 19790101, true),
 (427, 2, 19000101, 19910101, true),
 (428, 2, 19000101, 20160101, true),
 (429, 2, 19000101, 20160101, true),
 (430, 2, 19000101, 20020101, true),
 (431, 2, 19000101, 19790101, true),
 (432, 2, 19000101, 19700801, true),
 (433, 2, 19000101, 20070101, true),
 (434, 2, 19000101, 19700801, true),
 (435, 2, 19000101, NULL, true),
 (436, 2, 19000101, NULL, true),
 (437, 2, 19000101, 19790101, true),
 (438, 2, 19000101, 19700801, true),
 (439, 2, 19000101, 19790101, true),
 (440, 2, 19000101, 19700801, true),
 (441, 2, 19000101, NULL, true),
 (442, 2, 19000101, 19721001, true),
 (443, 2, 19000101, NULL, true),
 (444, 2, 19000101, 19700801, true),
 (445, 2, 19000101, NULL, true),
 (446, 2, 19000101, 19700801, true),
 (447, 2, 19000101, 19700801, true),
 (448, 2, 19000101, 20010101, true),
 (449, 2, 19000101, 19900101, true),
 (450, 2, 19000101, 19900101, true),
 (451, 2, 19000101, 19790101, true),
 (452, 2, 19000101, NULL, true),
 (453, 2, 19000101, 19790101, true),
 (454, 2, 19000101, NULL, true),
 (455, 2, 19000101, NULL, true),
 (456, 2, 19000101, 19790101, true),
 (457, 2, 19000101, NULL, true),
 (458, 2, 19000101, 20060101, true),
 (459, 2, 19000101, 19700801, true),
 (460, 2, 19000101, 19900101, true),
 (461, 2, 19000101, NULL, true),
 (462, 2, 19700801, 20150101, true),
 (463, 2, 19000101, 20110101, true),
 (464, 2, 19000101, 19790101, true),
 (465, 2, 19000101, 19740101, true),
 (466, 2, 19000101, 20120101, true),
 (467, 2, 19380101, 20120101, true),
 (468, 2, 19000101, 19700801, true),
 (469, 2, 19000101, 19700801, true),
 (470, 2, 19000101, 20070101, true),
 (471, 2, 19000101, 19910101, true),
 (472, 2, 19000101, 19740101, true),
 (473, 2, 19000101, 19700801, true),
 (474, 2, 19000101, 19910101, true),
 (475, 2, 19000101, 19740101, true),
 (476, 2, 19000101, 19740101, true),
 (477, 2, 19000101, NULL, true),
 (478, 2, 19000101, 19700801, true),
 (479, 2, 19000101, 19790101, true),
 (480, 2, 19000101, 20130101, true),
 (481, 2, 19000101, 18410901, true),
 (482, 2, 19700801, 20160101, true),
 (483, 2, 19740101, NULL, true),
 (484, 2, 19000101, 20070101, true),
 (485, 2, 19000101, 19800101, true),
 (486, 2, 19000101, NULL, true),
 (487, 2, 19000101, 20090101, true),
 (488, 2, 19180101, NULL, true),
 (489, 2, 19000101, 19860101, true),
 (490, 2, 19000101, 19850101, true),
 (491, 2, 19000101, 19860101, true),
 (492, 2, 19000101, 19860101, true),
 (493, 2, 18860101, NULL, true),
 (494, 2, 19000101, 19910101, true),
 (495, 2, 19000101, 20150101, true),
 (496, 2, 19000101, 20070101, true),
 (497, 2, 19000101, 20070101, true),
 (498, 2, 19000101, 19850101, true),
 (499, 2, 19000101, 20070101, true),
 (500, 2, 19000101, 19860101, true),
 (501, 2, 19000101, 20110101, true),
 (502, 2, 19800101, NULL, true),
 (503, 2, 19000101, 20140101, true),
 (504, 2, 19000101, 19860101, true),
 (505, 2, 19000101, NULL, true),
 (506, 2, 19000101, NULL, true),
 (507, 2, 19000101, NULL, true),
 (508, 2, 19000101, 20130101, true),
 (509, 2, 19000101, NULL, true),
 (510, 2, 19640201, 19890101, true),
 (511, 2, 19000101, 19700701, true),
 (512, 2, 19000101, 19860101, true),
 (513, 2, 19000101, 19800101, true),
 (514, 2, 19570101, 19860101, true),
 (515, 2, 19000101, 20130101, true),
 (516, 2, 19000101, NULL, true),
 (517, 2, 19000101, NULL, true),
 (518, 2, 19000101, 19850101, true),
 (519, 2, 19000101, 19860101, true),
 (520, 2, 19000101, 19840101, true),
 (521, 2, 19000101, 20070101, true),
 (522, 2, 19000101, NULL, true),
 (523, 2, 19000101, 20040101, true),
 (524, 2, 19000101, 19860101, true),
 (525, 2, 19000101, 19850101, true),
 (526, 2, 19000101, 19860101, true),
 (527, 2, 19570101, NULL, true),
 (528, 2, 19000101, 19910101, true),
 (529, 2, 19000101, 19800101, true),
 (530, 2, 19000101, 20030101, true),
 (531, 2, 19000101, 19860101, true),
 (532, 2, 19000101, 19840101, true),
 (533, 2, 19790101, 20070101, true),
 (534, 2, 19000101, NULL, true),
 (535, 2, 19000101, NULL, true),
 (536, 2, 19790101, NULL, true),
 (537, 2, 19000101, 19860101, true),
 (538, 2, 19000101, NULL, true),
 (539, 2, 19000101, 19860101, true),
 (540, 2, 19000101, 19860101, true),
 (541, 2, 19000101, NULL, true),
 (542, 2, 19000101, 19860101, true),
 (543, 2, 19000101, 19840101, true),
 (544, 2, 19000101, 19910101, true),
 (545, 2, 19000101, 19850101, true),
 (546, 2, 19000101, NULL, true),
 (547, 2, 19000101, 19860101, true),
 (548, 2, 19000101, 19860101, true),
 (549, 2, 19000101, NULL, true),
 (550, 2, 19000101, NULL, true),
 (551, 2, 19000101, NULL, true),
 (552, 2, 19380101, 20020101, true),
 (553, 2, 19000101, 19910101, true),
 (554, 2, 19000101, 19850101, true),
 (555, 2, 19000101, 19860101, true),
 (556, 2, 19000101, 20040101, true),
 (557, 2, 19000101, NULL, true),
 (558, 2, 19000101, 19840101, true),
 (559, 2, 19000101, 20040101, true),
 (560, 2, 19000101, NULL, true),
 (561, 2, 19000101, 19860101, true),
 (562, 2, 19790101, 20070101, true),
 (563, 2, 19000101, 20130101, true),
 (564, 2, 19000101, 19910101, true),
 (565, 2, 19000101, 19860101, true),
 (566, 2, 19000101, 20040101, true),
 (567, 2, 19000101, 20100101, true),
 (568, 2, 19000101, 19840101, true),
 (569, 2, 19000101, 20040101, true),
 (570, 2, 19000101, 19840101, true),
 (571, 2, 19000101, 20100101, true),
 (572, 2, 19800101, 20150101, true),
 (573, 2, 19000101, NULL, true),
 (574, 2, 19000101, 19860101, true),
 (575, 2, 19000101, 20130101, true),
 (576, 2, 19000101, 19860101, true),
 (577, 2, 19000101, 19940101, true),
 (578, 2, 19000101, 19860101, true),
 (579, 2, 19000101, NULL, true),
 (580, 2, 19000101, NULL, true),
 (581, 2, 19000101, 20020101, true),
 (582, 2, 19000101, 19840101, true),
 (583, 2, 19000101, NULL, true),
 (584, 2, 19660101, 20130101, true),
 (585, 2, 19000101, 19800101, true),
 (586, 2, 19000101, 19860101, true),
 (587, 2, 19000101, 19860101, true),
 (588, 2, 19000101, NULL, true),
 (589, 2, 19840101, NULL, true),
 (590, 2, 19000101, 19800101, true),
 (591, 2, 19000101, 19850101, true),
 (592, 2, 19840101, NULL, true),
 (593, 2, 19000101, NULL, true),
 (594, 2, 19000101, NULL, true),
 (595, 2, 19000101, 19840101, true),
 (596, 2, 19000101, 19850101, true),
 (597, 2, 19000101, 19840101, true),
 (598, 2, 19000101, 20020101, true),
 (599, 2, 19000101, 20110101, true),
 (600, 2, 19000101, 19850101, true),
 (601, 2, 19000101, NULL, true),
 (602, 2, 19000101, 19800101, true),
 (603, 2, 19000101, NULL, true),
 (604, 2, 19000101, 20100318, true),
 (605, 2, 19000101, 19910101, true),
 (606, 2, 19000101, 20060101, true),
 (607, 2, 19000101, NULL, true),
 (608, 2, 19000101, 20060101, true),
 (609, 2, 19000101, 19860101, true),
 (610, 2, 19000101, NULL, true),
 (611, 2, 19000101, 20040101, true),
 (612, 2, 19000101, 20150101, true),
 (613, 2, 19000101, 19860101, true),
 (614, 2, 19000101, NULL, true),
 (615, 2, 19840101, NULL, true),
 (616, 2, 19000101, 20150101, true),
 (617, 2, 19850101, NULL, true),
 (618, 2, 19800101, NULL, true),
 (619, 2, 19000101, 19850101, true),
 (620, 2, 19000101, 19860101, true),
 (621, 2, 19000101, NULL, true),
 (622, 2, 19000101, 19860101, true),
 (623, 2, 19000101, 20060101, true),
 (624, 2, 19000101, NULL, true),
 (625, 2, 19000101, 19800101, true),
 (626, 2, 19000101, NULL, true),
 (627, 2, 19000101, 20150101, true),
 (628, 2, 19000101, 20020101, true),
 (629, 2, 19000101, 20060101, true),
 (630, 2, 19000101, NULL, true),
 (631, 2, 18700701, NULL, true),
 (632, 2, 19000101, 20060101, true),
 (633, 2, 19000101, NULL, true),
 (634, 2, 19000101, 20040101, true),
 (635, 2, 19000101, 19840101, true),
 (636, 2, 19000101, NULL, true),
 (637, 2, 19000101, 19910101, true),
 (638, 2, 19000101, 19860101, true),
 (639, 2, 19000101, 19910101, true),
 (640, 2, 19000101, 19910101, true),
 (641, 2, 19000101, NULL, true),
 (642, 2, 19000101, NULL, true),
 (643, 2, 19000101, 19840101, true),
 (644, 2, 19000101, 19800101, true),
 (645, 2, 19000101, 19800101, true),
 (646, 2, 19000101, NULL, true),
 (647, 2, 19850101, 20150101, true),
 (648, 2, 19850101, 20150101, true),
 (649, 2, 19910101, 20090101, true),
 (650, 2, 19910101, 19930101, true),
 (651, 2, 19910101, 19920201, true),
 (652, 2, 19000101, 19950101, true),
 (653, 2, 19000101, 19970101, true),
 (654, 2, 19000101, 20030101, true),
 (655, 2, 19000101, 19700101, true),
 (656, 2, 19000101, 19700401, true),
 (657, 2, 19850605, 20140101, true),
 (658, 2, 19700101, NULL, true),
 (659, 2, 19000101, 19700401, true),
 (660, 2, 19000101, 19970101, true),
 (661, 2, 19000101, 19970101, true),
 (662, 2, 19000101, 19700401, true),
 (663, 2, 19000101, 19700401, true),
 (664, 2, 19000101, 19970101, true),
 (665, 2, 19000101, 19700101, true),
 (666, 2, 19610101, 19970101, true),
 (667, 2, 19000101, 19700101, true),
 (668, 2, 19000101, NULL, true),
 (669, 2, 19000101, 19700401, true),
 (670, 2, 19000101, 19700101, true),
 (671, 2, 19000101, 19700401, true),
 (672, 2, 19850701, NULL, true),
 (673, 2, 19000101, 19700101, true),
 (674, 2, 19000101, 19700101, true),
 (675, 2, 19000101, 19700101, true),
 (676, 2, 19000101, 19700101, true),
 (677, 2, 19000101, 19700101, true),
 (678, 2, 19000101, 19700401, true),
 (679, 2, 19000101, 20030101, true),
 (680, 2, 19000101, 19700401, true),
 (681, 2, 19000101, NULL, true),
 (682, 2, 19000101, NULL, true),
 (683, 2, 19000101, 19700101, true),
 (684, 2, 19000101, 19700401, true),
 (685, 2, 19000101, 19700401, true),
 (686, 2, 19000101, 19950101, true),
 (687, 2, 19860101, 20110101, true),
 (688, 2, 19000101, 19700101, true),
 (689, 2, 19000101, 19700101, true),
 (690, 2, 19660701, 19970101, true),
 (691, 2, 19000101, NULL, true),
 (692, 2, 19610101, 19970101, true),
 (693, 2, 19860101, NULL, true),
 (694, 2, 19000101, 19700401, true),
 (695, 2, 19000101, 19700101, true),
 (696, 2, 19000101, 20030101, true),
 (697, 2, 19860101, 20130101, true),
 (698, 2, 19860101, 20130101, true),
 (699, 2, 19000101, 19700101, true),
 (700, 2, 19000101, 19710701, true),
 (701, 2, 19000101, 19700401, true),
 (702, 2, 19000101, 19700101, true),
 (703, 2, 19000101, 19700401, true),
 (704, 2, 19000101, 19710701, true),
 (705, 2, 19000101, 19700401, true),
 (706, 2, 18771210, 19700101, true),
 (707, 2, 19700101, NULL, true),
 (708, 2, 19000101, 20030101, true),
 (709, 2, 19000101, 19710701, true),
 (710, 2, 19000101, 19700401, true),
 (711, 2, 19860101, NULL, true),
 (712, 2, 19000101, 19710701, true),
 (713, 2, 19000101, 19700401, true),
 (714, 2, 19870101, 20110101, true),
 (715, 2, 19000101, 19710701, true),
 (716, 2, 19000101, 19950101, true),
 (717, 2, 19000101, 19950101, true),
 (718, 2, 19000101, 19710701, true),
 (719, 2, 19000101, NULL, true),
 (720, 2, 19000101, NULL, true),
 (721, 2, 19000101, NULL, true),
 (722, 2, 19000101, NULL, true),
 (723, 2, 19360701, 19700401, true),
 (724, 2, 19660701, 19970101, true),
 (725, 2, 19000101, 19700101, true),
 (726, 2, 19000101, 19700401, true),
 (727, 2, 19000101, 19700101, true),
 (728, 2, 19000101, 19700401, true),
 (729, 2, 19610101, 19970101, true),
 (730, 2, 19000101, 19970101, true),
 (731, 2, 19000101, 19950101, true),
 (732, 2, 19000101, 19700101, true),
 (733, 2, 19000101, 19700101, true),
 (734, 2, 19000101, 19700401, true),
 (735, 2, 19000101, 19700401, true),
 (736, 2, 19000101, 19970101, true),
 (737, 2, 19870103, NULL, true),
 (738, 2, 19000101, 19700401, true),
 (739, 2, 19000101, 19700401, true),
 (740, 2, 19890101, NULL, true),
 (741, 2, 19890101, NULL, true),
 (742, 2, 19000101, NULL, true),
 (743, 2, 19000101, 19970101, true),
 (744, 2, 19000101, 19730101, true),
 (745, 2, 19000101, 19970101, true),
 (746, 2, 19000101, 19730101, true),
 (747, 2, 19000101, NULL, true),
 (748, 2, 19000101, NULL, true),
 (749, 2, 19000101, 19970101, true),
 (750, 2, 19000101, 19970101, true),
 (751, 2, 19000101, 19940101, true),
 (752, 2, 19000101, NULL, true),
 (753, 2, 19000101, 19990101, true),
 (754, 2, 19000101, 19940101, true),
 (755, 2, 19000101, 19970101, true),
 (756, 2, 19000101, 19960101, true),
 (757, 2, 19000101, NULL, true),
 (758, 2, 19000101, 19970101, true),
 (759, 2, 19000101, NULL, true),
 (760, 2, 19000101, NULL, true),
 (761, 2, 19000101, NULL, true),
 (762, 2, 19000101, NULL, true),
 (763, 2, 19000101, 19980128, true),
 (764, 2, 19000101, 19970101, true),
 (765, 2, 19000101, 19940101, true),
 (766, 2, 19260101, NULL, true),
 (767, 2, 19000101, 19970101, true),
 (768, 2, 19000101, 19970101, true),
 (769, 2, 19900101, NULL, true),
 (770, 2, 19000101, NULL, true),
 (771, 2, 19000101, 19970101, true),
 (772, 2, 19000101, 19960101, true),
 (773, 2, 19000101, 19970101, true),
 (774, 2, 19000101, NULL, true),
 (775, 2, 19230501, 19730101, true),
 (776, 2, 19000101, NULL, true),
 (777, 2, 19000101, 19710401, true),
 (778, 2, 19000101, 19710401, true),
 (779, 2, 19000101, 19940101, true),
 (780, 2, 19000101, 19960101, true),
 (781, 2, 19680101, NULL, true),
 (782, 2, 19000101, 19970101, true),
 (783, 2, 19000101, NULL, true),
 (784, 2, 19000101, 19930101, true),
 (785, 2, 19000101, 20040101, true),
 (786, 2, 19000101, 19970101, true),
 (787, 2, 19000101, 19730101, true),
 (788, 2, 19000101, NULL, true),
 (789, 2, 19000101, NULL, true),
 (790, 2, 19000101, NULL, true),
 (791, 2, 19000101, 19970101, true),
 (792, 2, 19000101, NULL, true),
 (793, 2, 19000101, 19970101, true),
 (794, 2, 19000101, 19940101, true),
 (795, 2, 19000101, 19950128, true),
 (796, 2, 19690101, 19940101, true),
 (797, 2, 19000101, 19970101, true),
 (798, 2, 19000101, NULL, true),
 (799, 2, 19000101, 19960101, true),
 (800, 2, 19000101, NULL, true),
 (801, 2, 19000101, NULL, true),
 (802, 2, 19000101, NULL, true),
 (803, 2, 19000101, 19970101, true),
 (804, 2, 19000101, 19970101, true),
 (805, 2, 19000101, 19970101, true),
 (806, 2, 19000101, 19970101, true),
 (807, 2, 19000101, 19970101, true),
 (808, 2, 19000101, 19970101, true),
 (809, 2, 19000101, 19970101, true),
 (810, 2, 19000101, 19960101, true),
 (811, 2, 19000101, 19970101, true),
 (812, 2, 19000101, 20110101, true),
 (813, 2, 19000101, NULL, true),
 (814, 2, 19000101, 19970101, true),
 (815, 2, 19000101, 19970101, true),
 (816, 2, 19000101, 19970101, true),
 (817, 2, 19000101, 19940101, true),
 (818, 2, 19000101, 20040101, true),
 (819, 2, 19000101, NULL, true),
 (820, 2, 19000101, 19970101, true),
 (821, 2, 19420101, 19970101, true),
 (822, 2, 19000101, 19970101, true),
 (823, 2, 19000101, 19940101, true),
 (824, 2, 19000101, NULL, true),
 (825, 2, 19000101, 19930101, true),
 (826, 2, 19000101, 19940101, true),
 (827, 2, 19000101, NULL, true),
 (828, 2, 19000101, NULL, true),
 (829, 2, 19000101, 19970101, true),
 (830, 2, 19000101, NULL, true),
 (831, 2, 19000101, 19940101, true),
 (832, 2, 19000101, NULL, true),
 (833, 2, 19000101, 19970101, true),
 (834, 2, 19000101, 19970101, true),
 (835, 2, 19000101, 19970101, true),
 (836, 2, 19510101, 19970101, true),
 (837, 2, 19000101, 19970101, true),
 (838, 2, 19000101, 19970101, true),
 (839, 2, 19000101, 20030101, true),
 (840, 2, 19000101, 19970101, true),
 (841, 2, 19000101, 19970101, true),
 (842, 2, 19000101, 19970101, true),
 (843, 2, 19000101, 19960101, true),
 (844, 2, 19000101, NULL, true),
 (845, 2, 19000101, 19970101, true),
 (846, 2, 19000101, 19730101, true),
 (847, 2, 19000101, 19940101, true),
 (848, 2, 19000101, 20170101, true),
 (849, 2, 19000101, NULL, true),
 (850, 2, 19000101, 20170101, true),
 (851, 2, 19000101, NULL, true),
 (852, 2, 19000101, NULL, true),
 (853, 2, 19230101, 19970101, true),
 (854, 2, 19000101, 19970101, true),
 (855, 2, 19000101, NULL, true),
 (856, 2, 19910101, NULL, true),
 (857, 2, 19000101, 19970101, true),
 (858, 2, 19000101, 19970101, true),
 (859, 2, 19000101, NULL, true),
 (860, 2, 19000101, NULL, true),
 (861, 2, 19000101, 19970101, true),
 (862, 2, 19000101, NULL, true),
 (863, 2, 19000101, 19730101, true),
 (864, 2, 19000101, 20170101, true),
 (865, 2, 19210101, NULL, true),
 (866, 2, 19000101, 19970101, true),
 (867, 2, 19000101, 19980101, true),
 (868, 2, 19000101, 19970101, true),
 (869, 2, 19000101, NULL, true),
 (870, 2, 19000101, NULL, true),
 (871, 2, 19000101, NULL, true),
 (872, 2, 19000101, 19940101, true),
 (873, 2, 19000101, 19970101, true),
 (874, 2, 19000101, NULL, true),
 (875, 2, 19000101, 19970101, true),
 (876, 2, 19000101, 19970101, true),
 (877, 2, 19000101, NULL, true),
 (878, 2, 19000101, NULL, true),
 (879, 2, 19000101, 19970101, true),
 (880, 2, 19000101, 19730101, true),
 (881, 2, 19000101, 19940101, true),
 (882, 2, 19000101, 19980401, true),
 (883, 2, 19000101, NULL, true),
 (884, 2, 19910101, NULL, true),
 (885, 2, 19820101, NULL, true),
 (886, 2, 19820101, NULL, true),
 (887, 2, 19000101, 19700701, true),
 (888, 2, 19000101, 19820101, true),
 (889, 2, 19000101, 20100101, true),
 (890, 2, 19000101, 19910101, true),
 (891, 2, 19000101, 19910101, true),
 (892, 2, 19000101, NULL, true),
 (893, 2, 19000101, NULL, true),
 (894, 2, 19000101, 20010101, true),
 (895, 2, 19000101, 19820101, true),
 (896, 2, 19000101, 19820101, true),
 (897, 2, 19000101, NULL, true),
 (898, 2, 19000101, 19820101, true),
 (899, 2, 19000101, 19820101, true),
 (900, 2, 19000101, 19700701, true),
 (901, 2, 19000101, 20010101, true),
 (902, 2, 19000101, 20010101, true),
 (903, 2, 19000101, NULL, true),
 (904, 2, 19000101, 19820101, true),
 (905, 2, 19000101, 19820101, true),
 (906, 2, 19000101, 20030101, true),
 (907, 2, 19000101, 19820101, true),
 (908, 2, 19000101, 19820101, true),
 (909, 2, 19000101, 20110101, true),
 (910, 2, 19000101, 20010101, true),
 (911, 2, 19000101, NULL, true),
 (912, 2, 19000101, 19820101, true),
 (913, 2, 19000101, 19910101, true),
 (914, 2, 19000101, 19820101, true),
 (915, 2, 19000101, 19820101, true),
 (916, 2, 19000101, 20010101, true),
 (917, 2, 19000101, 19990101, true),
 (918, 2, 19000101, 20070101, true),
 (919, 2, 19000101, 19910101, true),
 (920, 2, 19000101, 19700701, true),
 (921, 2, 19000101, NULL, true),
 (922, 2, 19000101, 20100101, true),
 (923, 2, 19000101, 19910101, true),
 (924, 2, 19000101, 20070101, true),
 (925, 2, 19000101, 19820101, true),
 (926, 2, 19000101, 19910101, true),
 (927, 2, 19000101, 20010101, true),
 (928, 2, 19000101, 19820101, true),
 (929, 2, 19000101, 20070101, true),
 (930, 2, 19000101, 19700701, true),
 (931, 2, 19000101, 19820101, true),
 (932, 2, 19000101, NULL, true),
 (933, 2, 19000101, 20100101, true),
 (934, 2, 19000101, 19820101, true),
 (935, 2, 19000101, 19820101, true),
 (936, 2, 19000101, 19910101, true),
 (937, 2, 19000101, 20070101, true),
 (938, 2, 19000101, 20100101, true),
 (939, 2, 19000101, NULL, true),
 (940, 2, 19000101, 20110101, true),
 (941, 2, 19000101, 19690701, true),
 (942, 2, 19000101, NULL, true),
 (943, 2, 19000101, 19930101, true),
 (944, 2, 19000101, 19820101, true),
 (945, 2, 19000101, 20100101, true),
 (946, 2, 19000101, 19820101, true),
 (947, 2, 19000101, 19910101, true),
 (948, 2, 19000101, NULL, true),
 (949, 2, 19000101, 19820101, true),
 (950, 2, 19000101, NULL, true),
 (951, 2, 19000101, 19910101, true),
 (952, 2, 19000101, 19820101, true),
 (953, 2, 19000101, 19820101, true),
 (954, 2, 19000101, 19820101, true),
 (955, 2, 19000101, NULL, true),
 (956, 2, 19000101, 19820101, true),
 (957, 2, 19000101, 19910101, true),
 (958, 2, 19000101, 19820101, true),
 (959, 2, 19000101, 19730101, true),
 (960, 2, 19000101, 19940201, true),
 (961, 2, 19000101, NULL, true),
 (962, 2, 19000101, 19930101, true),
 (963, 2, 19000101, 19820101, true),
 (964, 2, 19000101, 19820101, true),
 (965, 2, 19000101, 19820101, true),
 (966, 2, 19000101, NULL, true),
 (967, 2, 19000101, 19820101, true),
 (968, 2, 19000101, 20100101, true),
 (969, 2, 19000101, NULL, true),
 (970, 2, 19000101, 19820101, true),
 (971, 2, 19000101, 19910101, true),
 (972, 2, 19000101, 20010101, true),
 (973, 2, 19000101, 19820101, true),
 (974, 2, 19000101, 19820101, true),
 (975, 2, 19000101, NULL, true),
 (976, 2, 19000101, 19910101, true),
 (977, 2, 19000101, 19980101, true),
 (978, 2, 19000101, 20030101, true),
 (979, 2, 19000101, 20070101, true),
 (980, 2, 19000101, 20010101, true),
 (981, 2, 19000101, 20070101, true),
 (982, 2, 19000101, 19820101, true),
 (983, 2, 19000101, 19820101, true),
 (984, 2, 19000101, 19820101, true),
 (985, 2, 19000101, NULL, true),
 (986, 2, 19401001, 19820101, true),
 (987, 2, 19000101, NULL, true),
 (988, 2, 19000101, NULL, true),
 (989, 2, 19000101, 19910101, true),
 (990, 2, 19000101, NULL, true),
 (991, 2, 19000101, 19690701, true),
 (992, 2, 19000101, NULL, true),
 (993, 2, 19000101, 19910101, true),
 (994, 2, 19000101, 19990101, true),
 (995, 2, 19000101, 19820101, true),
 (996, 2, 19000101, 19820101, true),
 (997, 2, 19690701, 20100101, true),
 (998, 2, 19820101, NULL, true),
 (999, 2, 19800101, NULL, true),
 (1000, 2, 19551130, 19960101, true),
 (1001, 2, 19000101, 19901001, true),
 (1002, 2, 19000101, 19901001, true),
 (1003, 2, 19000101, 19901001, true),
 (1004, 2, 19000101, 19660701, true),
 (1005, 2, 19000101, 19230101, true),
 (1006, 2, 19000101, 19180101, true),
 (1007, 2, 19000101, 19410501, true),
 (1008, 2, 19000101, 19410501, true),
 (1009, 2, 19000101, 19540101, true),
 (1010, 2, 19000101, 19340701, true),
 (1011, 2, 19000101, 19580101, true),
 (1012, 2, 19000101, 19180101, true),
 (1013, 2, 19000101, 19140101, true),
 (1014, 2, 19000101, 19200101, true),
 (1015, 2, 19000101, 19410501, true),
 (1016, 2, 19000101, 19230501, true),
 (1017, 2, 19000101, 19420201, true),
 (1018, 2, 19000101, 19660801, true),
 (1019, 2, 19000101, 19220101, true),
 (1020, 2, 19000101, 19230501, true),
 (1021, 2, 19000101, 19640201, true),
 (1022, 2, 19000101, 18780101, true),
 (1023, 2, 19000101, 19680901, true),
 (1024, 2, 19000101, 19290101, true),
 (1025, 2, 19000101, 19420501, true),
 (1026, 2, 19000101, 19660701, true),
 (1027, 2, 19000101, 19220101, true),
 (1028, 2, 19000101, 19660101, true),
 (1029, 2, 19000101, 18650101, true),
 (1030, 2, 19000101, 19340501, true),
 (1031, 2, 19000101, 19360701, true),
 (1032, 2, 19000101, 19490101, true),
 (1033, 2, 19000101, 19490101, true),
 (1034, 2, 19000101, 18700701, true),
 (1035, 2, 19000101, 19410801, true),
 (1036, 2, 19000101, 19421001, true),
 (1037, 2, 19000101, 19421001, true),
 (1038, 2, 19000101, 19210101, true),
 (1039, 2, 19000101, 19610101, true),
 (1040, 2, 19000101, 19230101, true),
 (1041, 2, 19000101, 18950228, true),
 (1042, 2, 19000101, 19410401, true),
 (1043, 2, 19000101, 19330101, true),
 (1044, 2, 19000101, 18860114, true),
 (1045, 2, 19000101, 19260101, true),
 (1046, 2, 19000101, 19230501, true),
 (1047, 2, 19000101, 19230501, true),
 (1048, 2, 19000101, 19690101, true),
 (1049, 2, 19000101, 19340501, true),
 (1050, 2, 19000101, 19230501, true),
 (1051, 2, 19000101, 19610101, true),
 (1052, 2, 19000101, 19310501, true),
 (1053, 2, 19000101, 19230501, true),
 (1054, 2, 19000101, 19610101, true),
 (1055, 2, 19000101, 19230101, true),
 (1056, 2, 19000101, 19410401, true),
 (1057, 2, 19000101, 19610101, true),
 (1058, 2, 19000101, 19610101, true),
 (1059, 2, 19490423, 19630801, true),
 (1060, 2, 19000101, 18790715, true),
 (1061, 2, 19000101, 19420701, true),
 (1062, 2, 19000101, 19550701, true),
 (1063, 2, 19000101, 19420801, true),
 (1064, 2, 19080801, 19230501, true),
 (1065, 2, 19000101, 19200101, true),
 (1066, 2, 19000101, 19570101, true),
 (1067, 2, 19000101, 19570101, true),
 (1068, 2, 19000101, 19420101, true),
 (1069, 2, 19000101, 19370101, true),
 (1070, 2, 19000101, 18810906, true),
 (1071, 2, 19000101, 19660701, true),
 (1072, 2, 19000101, 19610101, true),
 (1073, 2, 19000101, 19540101, true),
 (1074, 2, 19000101, 19570101, true),
 (1075, 2, 19000101, 19350501, true),
 (1076, 2, 19000101, 19690101, true),
 (1077, 2, 19000101, 18800423, true),
 (1078, 2, 19000101, 19660501, true),
 (1079, 2, 19000101, 19640201, true),
 (1080, 2, 19000101, 19550701, true),
 (1081, 2, 19000101, 19360701, true),
 (1082, 2, 19000101, 19660101, true),
 (1083, 2, 19000101, 19410401, true),
 (1084, 2, 19000101, 19350501, true),
 (1085, 2, 19000101, 19410801, true),
 (1086, 2, 19000101, 19210101, true),
 (1087, 2, 19000101, 19490401, true),
 (1088, 2, 19000101, 19690101, true),
 (1089, 2, 19000101, 19340501, true),
 (1090, 2, 19000101, 19401001, true),
 (1091, 2, 19000101, 18630922, true),
 (1092, 2, 19000101, 19230501, true),
 (1093, 2, 19000101, 19550701, true),
 (1094, 2, 19000101, 19420701, true),
 (1095, 2, 19000101, 19430101, true),
 (1096, 2, 19000101, 19370101, true),
 (1097, 2, 19000101, 18740101, true),
 (1098, 2, 19000101, 19410401, true),
 (1099, 2, 19000101, 19610101, true),
 (1100, 2, 19000101, 19410801, true),
 (1101, 2, 19000101, 19660801, true),
 (1102, 2, 19000101, 19660701, true),
 (1103, 2, 19000101, 18950228, true),
 (1104, 2, 19000101, 19420501, true),
 (1105, 2, 19000101, 19640201, true),
 (1106, 2, 19000101, 19350501, true),
 (1107, 2, 19000101, 19420801, true),
 (1108, 2, 19000101, 19390101, true),
 (1109, 2, 19000101, 19640401, true),
 (1110, 2, 19000101, 19340501, true),
 (1111, 2, 19000101, 19230701, true),
 (1112, 2, 19000101, 19490701, true),
 (1113, 2, 19000101, 19420501, true),
 (1114, 2, 19000101, 19590801, true),
 (1115, 2, 19000101, 19230501, true),
 (1116, 2, 19000101, 19660701, true),
 (1117, 2, 19000101, 19660101, true),
 (1118, 2, 19000101, 19430101, true),
 (1119, 2, 19000101, 19550701, true),
 (1120, 2, 19000101, 19420701, true),
 (1121, 2, 19000101, 19660701, true),
 (1122, 2, 19000101, 19600101, true),
 (1123, 2, 19000101, 19660101, true),
 (1124, 2, 19000101, 19210101, true),
 (1125, 2, 19000101, 19600101, true),
 (1126, 2, 19000101, 19610101, true),
 (1127, 2, 19000101, 19350501, true),
 (1128, 2, 19000101, 19410801, true),
 (1129, 2, 19000101, 18700701, true),
 (1130, 2, 19000101, 19650701, true),
 (1131, 2, 19000101, 19690101, true),
 (1132, 2, 19000101, 19610101, true),
 (1133, 2, 19000101, 19610101, true),
 (1134, 2, 19000101, 19421001, true),
 (1135, 2, 19000101, 19640901, true),
 (1136, 2, 19000101, 19210501, true),
 (1137, 2, 19000101, 19660101, true),
 (1138, 2, 18360101, 18860101, true),
 (1139, 2, 19000101, 19660701, true),
 (1140, 2, 19000101, 19610101, true),
 (1141, 2, 19000101, 19660701, true),
 (1142, 2, 19000101, 19360701, true),
 (1143, 2, 19000101, 18680101, true),
 (1144, 2, 19000101, 19401001, true),
 (1145, 2, 19000101, 19200101, true),
 (1146, 2, 19000101, 19660101, true),
 (1147, 2, 19000101, 19660101, true),
 (1148, 2, 19000101, 19540101, true),
 (1149, 2, 19000101, 19350501, true),
 (1150, 2, 19000101, 19410801, true),
 (1151, 2, 19000101, 19180101, true),
 (1152, 2, 19000101, 19610101, true),
 (1153, 2, 19000101, 19410801, true),
 (1154, 2, 19000101, 19390101, true),
 (1155, 2, 19000101, 19640201, true),
 (1156, 2, 19000101, 19340501, true),
 (1157, 2, 19000101, 19290501, true),
 (1158, 2, 19000101, 19570101, true),
 (1159, 2, 19000101, 19550701, true),
 (1160, 2, 19000101, 19420101, true),
 (1161, 2, 19000101, 19210101, true),
 (1162, 2, 19000101, 19420701, true),
 (1163, 2, 19000101, 19610101, true),
 (1164, 2, 19000101, 19640201, true),
 (1165, 2, 19000101, 18780101, true),
 (1166, 2, 19000101, 18870315, true),
 (1167, 2, 19000101, 19660701, true),
 (1168, 2, 19000101, 19640401, true),
 (1169, 2, 19000101, 19430101, true),
 (1170, 2, 19000101, 19310501, true),
 (1171, 2, 19000101, 19420501, true),
 (1172, 2, 19000101, 19490401, true),
 (1173, 2, 19000101, 19620101, true),
 (1174, 2, 19000101, 19410801, true),
 (1175, 2, 19000101, 19401001, true),
 (1176, 2, 19000101, 19410101, true),
 (1177, 2, 19000101, 19270501, true),
 (1178, 2, 19000101, 19340701, true),
 (1179, 2, 19000101, 19610101, true),
 (1180, 2, 19000101, 19660701, true),
 (1181, 2, 19000101, 18800423, true),
 (1182, 2, 19000101, 19410401, true),
 (1183, 2, 19000101, 19660701, true),
 (1184, 2, 19000101, 19200101, true),
 (1185, 2, 19000101, 19210101, true),
 (1186, 2, 19000101, 18700701, true),
 (1187, 2, 19000101, 19250101, true),
 (1188, 2, 19000101, 19660101, true),
 (1189, 2, 19000101, 19270501, true),
 (1190, 2, 19000101, 19590701, true),
 (1191, 2, 19000101, 19230101, true),
 (1192, 2, 19000101, 19660101, true),
 (1193, 2, 19000101, 19140101, true),
 (1194, 2, 19000101, 19200101, true),
 (1195, 2, 19000101, 19410501, true),
 (1196, 2, 19000101, 19230501, true),
 (1197, 2, 19000101, 19420201, true),
 (1198, 2, 19000101, 18700701, true),
 (1199, 2, 19000101, 19660101, true),
 (1200, 2, 19000101, 19680101, true),
 (1201, 2, 19000101, 19380101, true),
 (1202, 2, 19000101, 19360701, true),
 (1203, 2, 19000101, 19200101, true),
 (1204, 2, 19000101, 18790102, true),
 (1205, 2, 19000101, 19200101, true),
 (1206, 2, 19000101, 19570701, true),
 (1207, 2, 19000101, 19200101, true),
 (1208, 2, 19490422, 19630801, true),
 (1209, 2, 19000101, 19620101, true),
 (1210, 2, 19000101, 19210501, true),
 (1211, 2, 19000101, 19540101, true),
 (1212, 2, 19000101, 19420701, true),
 (1213, 2, 19000101, 19380101, true),
 (1214, 2, 19000101, 19410801, true),
 (1215, 2, 19000101, 19540101, true),
 (1216, 2, 19000101, 19260101, true),
 (1217, 2, 19000101, 19640401, true),
 (1218, 2, 19000101, 19660701, true),
 (1219, 2, 19000101, 19210101, true),
 (1220, 2, 19000101, 19230101, true),
 (1221, 2, 19000101, 19640201, true),
 (1222, 2, 19000101, 19560701, true),
 (1223, 2, 19000101, 19210101, true),
 (1224, 2, 19000101, 19680901, true),
 (1225, 2, 19000101, 19660801, true),
 (1226, 2, 19000101, 19501001, true),
 (1227, 2, 19000101, 19640901, true),
 (1228, 2, 19000101, 19570701, true),
 (1229, 2, 19000101, 19690101, true),
 (1230, 2, 19000101, 19430101, true),
 (1231, 2, 19000101, 19370101, true),
 (1232, 2, 19000101, 19200101, true),
 (1233, 2, 19000101, 19360501, true),
 (1234, 2, 19000101, 19410801, true),
 (1235, 2, 19000101, 19230501, true),
 (1236, 2, 19000101, 19370101, true),
 (1237, 2, 19000101, 19210501, true),
 (1238, 2, 19000101, 19350501, true),
 (1239, 2, 19000101, 19210501, true),
 (1240, 2, 19000101, 19610101, true),
 (1241, 2, 19000101, 19660701, true),
 (1242, 2, 19000101, 19410801, true),
 (1243, 2, 19000101, 18700701, true),
 (1244, 2, 19000101, 19650701, true),
 (1245, 2, 19000101, 18630922, true),
 (1246, 2, 19000101, 19540101, true),
 (1247, 2, 19000101, 19550701, true),
 (1248, 2, 19000101, 19640201, true),
 (1249, 2, 19000101, 19670801, true),
 (1250, 2, 19790101, 19800101, true),
 (1251, 2, 19420101, 19510101, true),
 (1252, 2, 19000101, 19440801, true),
 (1253, 2, 19000101, 19640101, true),
 (1254, 2, 19000101, 19690101, true),
 (1255, 2, 19000101, 19680101, true),
 (1256, 2, 19000101, 19410715, true),
 (1257, 2, 19000101, 19580801, true),
 (1258, 2, 19000101, 19700101, true),
 (1259, 2, 19000101, 19080801, true),
 (1260, 2, 19000101, 19380101, true),
 (1261, 2, 19390101, 19790101, true),
 (1262, 2, 18460101, 18550901, true),
 (1263, 2, 19000101, 19550101, true),
 (1264, 2, 19000101, 19560101, true),
 (1265, 2, 19000101, 18550901, true),
 (1266, 2, 19000101, 18550901, true),
 (1267, 2, 19000101, 19080801, true),
 (1268, 2, 19000101, 19080801, true),
 (1269, 2, 19000101, 19080801, true),
 (1270, 2, 19000101, 18550901, true),
 (1271, 2, 19000101, 18570908, true),
 (1272, 2, 19000101, 18550901, true),
 (1273, 2, 19000101, 18550901, true),
 (1274, 2, 19000101, 18460101, true),
 (1275, 2, 19000101, 18580101, true),
 (1276, 2, 19000101, 18550901, true),
 (1277, 2, 19000101, 18330101, true),
 (1278, 2, 19000101, 18460101, true),
 (1279, 2, 19000101, 18570908, true),
 (1280, 2, 19000101, 18320101, true),
 (1281, 2, 19000101, 18570908, true),
 (1282, 2, 19000101, 18310701, true),
 (1283, 2, 19000101, 18570908, true),
 (1284, 2, 19000101, 19750101, true),
 (1285, 2, 19000101, 18480101, true),
 (1286, 2, 19000101, 18571002, true),
 (1287, 2, 19000101, 18570908, true),
 (1288, 2, 19000101, 18550901, true),
 (1289, 2, 19000101, 18340101, true),
 (1290, 2, 19000101, 18570908, true),
 (1291, 2, 19000101, 18550901, true),
 (1292, 2, 19000101, 18340101, true),
 (1293, 2, 19000101, 18540531, true),
 (1294, 2, 19000101, 18570908, true),
 (1295, 2, 19000101, 18570801, true),
 (1296, 2, 19000101, 18570819, true),
 (1297, 2, 19000101, 18550901, true),
 (1298, 2, 19000101, 18550901, true),
 (1299, 2, 19000101, 18330101, true),
 (1300, 2, 19000101, 18320101, true),
 (1301, 2, 19000101, 18550901, true),
 (1302, 2, 19000101, 18550917, true),
 (1303, 2, 19000101, 18540531, true),
 (1304, 2, 19000101, 18570908, true),
 (1305, 2, 19000101, 18570908, true),
 (1306, 2, 19000101, 18570819, true),
 (1307, 2, 19000101, 18570613, true),
 (1308, 2, 19000101, 18571002, true),
 (1309, 2, 19000101, 18580101, true),
 (1310, 2, 19000101, 18540420, true),
 (1311, 2, 19000101, 18570908, true),
 (1312, 2, 19000101, 18550901, true),
 (1313, 2, 19000101, 18550901, true),
 (1314, 2, 19000101, 18570131, true),
 (1315, 2, 19000101, 18560101, true),
 (1316, 2, 19000101, 18570819, true),
 (1317, 2, 19000101, 18560101, true),
 (1318, 2, 19000101, 18320101, true),
 (1319, 2, 19000101, 18570908, true),
 (1320, 2, 19000101, 18580101, true),
 (1321, 2, 19000101, 18360101, true),
 (1322, 2, 19000101, 18340101, true),
 (1323, 2, 19000101, 18580101, true),
 (1324, 2, 19000101, 18550901, true),
 (1325, 2, 19000101, 18580101, true),
 (1326, 2, 19000101, 18570819, true),
 (1327, 2, 19000101, 18590101, true),
 (1328, 2, 19000101, 18570908, true),
 (1329, 2, 19000101, 18540623, true),
 (1330, 2, 19000101, 18550101, true),
 (1331, 2, 19000101, 18580101, true),
 (1332, 2, 19000101, 18460101, true),
 (1333, 2, 19000101, 18550901, true),
 (1334, 2, 19000101, 18540531, true),
 (1335, 2, 19000101, 18540531, true),
 (1336, 2, 19000101, 18570919, true),
 (1337, 2, 19000101, 18580901, true),
 (1338, 2, 19000101, 18580101, true),
 (1339, 2, 19000101, 18550711, true),
 (1340, 2, 19000101, 18320101, true),
 (1341, 2, 19000101, 18550901, true),
 (1342, 2, 19000101, 18570908, true),
 (1343, 2, 19000101, 18550901, true),
 (1344, 2, 19000101, 19620625, true),
 (1345, 2, 19000101, 18570908, true),
 (1346, 2, 19000101, 18550901, true),
 (1347, 2, 19000101, 18460101, true),
 (1348, 2, 19000101, 18550901, true),
 (1349, 2, 19000101, 18570908, true),
 (1350, 2, 19000101, 18580711, true),
 (1351, 2, 19000101, 18540413, true),
 (1352, 2, 19000101, 18550815, true),
 (1353, 2, 19000101, 18540522, true),
 (1354, 2, 19000101, 18410101, true),
 (1355, 2, 19000101, 18460101, true),
 (1356, 2, 19000101, 18550815, true),
 (1357, 2, 19000101, 18400710, true),
 (1358, 2, 19000101, 18571201, true),
 (1359, 2, 19000101, 18410101, true),
 (1360, 2, 19000101, 18360101, true),
 (1361, 2, 19000101, 18340101, true),
 (1362, 2, 19000101, 18570131, true),
 (1363, 2, 19000101, 18570713, true),
 (1364, 2, 19000101, 18570908, true),
 (1365, 2, 19000101, 18570908, true),
 (1366, 2, 19000101, 18550901, true),
 (1367, 2, 19000101, 18570908, true),
 (1368, 2, 19000101, 18570613, true),
 (1369, 2, 19000101, 18460101, true),
 (1370, 2, 19000101, 19620701, true),
 (1371, 2, 20010101, NULL, true),
 (1372, 2, 20050101, NULL, true),
 (1373, 2, 20060101, NULL, true),
 (1374, 2, 20060101, NULL, true),
 (1375, 2, 20060519, NULL, true),
 (1376, 2, 20070101, NULL, true),
 (1377, 2, 20070101, NULL, true),
 (1378, 2, 20070101, NULL, true),
 (1379, 2, 20070101, NULL, true),
 (1380, 2, 19920101, NULL, true),
 (1381, 2, 19970101, NULL, true),
 (1382, 2, 19970101, NULL, true),
 (1383, 2, 19970101, NULL, true),
 (1384, 2, 19970101, NULL, true),
 (1385, 2, 19910701, 20100101, true),
 (1386, 2, 19920101, NULL, true),
 (1387, 2, 19970101, 19980425, true),
 (1388, 2, 19920201, 20100101, true),
 (1389, 2, 19970101, NULL, true),
 (1390, 2, 19930101, NULL, true),
 (1391, 2, 19930101, 20070101, true),
 (1392, 2, 19930101, 20150101, true),
 (1393, 2, 19930101, 20140101, true),
 (1394, 2, 19940101, 20070101, true),
 (1395, 2, 19970101, NULL, true),
 (1396, 2, 19970101, NULL, true),
 (1397, 2, 19940201, 20070101, true),
 (1398, 2, 19980101, NULL, true),
 (1399, 2, 19980101, NULL, true),
 (1400, 2, 19940101, NULL, true),
 (1401, 2, 19940101, NULL, true),
 (1402, 2, 19980101, NULL, true),
 (1403, 2, 19940101, 19950704, true),
 (1404, 2, 19950101, NULL, true),
 (1405, 2, 20020101, NULL, true),
 (1406, 2, 19980101, 20000101, true),
 (1407, 2, 19950101, 20030101, true),
 (1408, 2, 19980101, NULL, true),
 (1409, 2, 20020601, NULL, true),
 (1410, 2, 19980101, NULL, true),
 (1411, 2, 19950704, NULL, true),
 (1412, 2, 20030101, NULL, true),
 (1413, 2, 19980128, NULL, true),
 (1414, 2, 20030101, NULL, true),
 (1415, 2, 19980401, NULL, true),
 (1416, 2, 20030101, NULL, true),
 (1417, 2, 20030101, NULL, true),
 (1418, 2, 19980425, NULL, true),
 (1419, 2, 19950128, NULL, true),
 (1420, 2, 19990101, NULL, true),
 (1421, 2, 19970101, NULL, true),
 (1422, 2, 19990101, NULL, true),
 (1423, 2, 19970101, NULL, true),
 (1424, 2, 19990101, NULL, true),
 (1425, 2, 19991201, NULL, true),
 (1426, 2, 20000101, NULL, true),
 (1427, 2, 20010101, NULL, true),
 (1428, 2, 20010101, NULL, true),
 (1429, 2, 20030401, NULL, true),
 (1430, 2, 20030315, NULL, true),
 (1431, 2, 20040101, NULL, true),
 (1432, 2, 20020326, NULL, true),
 (1433, 2, 20020601, NULL, true),
 (1434, 2, 20040101, NULL, true),
 (1435, 2, 20040101, NULL, true),
 (1436, 2, 20050101, NULL, true),
 (1437, 2, 20050101, NULL, true),
 (1438, 2, 20010101, NULL, true),
 (1439, 2, 20090101, NULL, true),
 (1440, 2, 20090101, NULL, true),
 (1441, 2, 20100101, NULL, true),
 (1442, 2, 20100101, NULL, true),
 (1443, 2, 20100101, NULL, true),
 (1444, 2, 20010101, NULL, true),
 (1445, 2, 20110101, NULL, true),
 (1446, 2, 20110101, NULL, true),
 (1447, 2, 20110101, NULL, true),
 (1448, 2, 20110101, NULL, true),
 (1449, 2, 20110101, NULL, true),
 (1450, 2, 20120101, NULL, true),
 (1451, 2, 20020101, NULL, true),
 (1452, 2, 20020101, NULL, true),
 (1453, 2, 19910101, 20070101, true),
 (1454, 2, 20050101, NULL, true),
 (1455, 2, 19910201, NULL, true),
 (1456, 2, 20130101, NULL, true),
 (1457, 2, 20130101, NULL, true),
 (1458, 2, 20140101, 20150701, true),
 (1460, 2, 20150101, NULL, true),
 (1461, 2, 20150101, NULL, true),
 (1462, 2, 20150701, NULL, true),
 (1463, 2, 20160101, NULL, true),
 (1464, 2, 20160101, NULL, true),
 (1465, 2, 20170101, NULL, true);
INSERT INTO Kern.Gem (ID, Partij, Naam, Code, DatAanvGel, DatEindeGel) VALUES
   (3, 4, 'Onbekend', '0000', 19000101, NULL),
   (4, 5, 'Adorp', '0001', 19000101, 19900101),
   (5, 6, 'Aduard', '0002', 19000101, 19900101),
   (6, 7, 'Appingedam', '0003', 19000101, NULL),
   (7, 8, 'Baflo', '0004', 19000101, 19900101),
   (8, 9, 'Bedum', '0005', 19000101, NULL),
   (9, 10, 'Beerta', '0006', 19000101, 19910701),
   (10, 11, 'Bellingwedde', '0007', 19680101, NULL),
   (11, 12, 'Bierum', '0008', 19000101, 19900101),
   (12, 13, 'Ten Boer', '0009', 19000101, NULL),
   (13, 14, 'Delfzijl', '0010', 19000101, NULL),
   (14, 15, 'Eenrum', '0011', 19000101, 19900101),
   (15, 16, 'Ezinge', '0012', 19000101, 19900101),
   (16, 17, 'Finsterwolde', '0013', 19000101, 19900101),
   (17, 18, 'Groningen', '0014', 19000101, NULL),
   (18, 19, 'Grootegast', '0015', 19000101, NULL),
   (19, 20, 'Grijpskerk', '0016', 19000101, 19900101),
   (20, 21, 'Haren', '0017', 19000101, NULL),
   (21, 22, 'Hoogezand-Sappemeer', '0018', 19490401, NULL),
   (22, 23, 'Hefshuizen', '0019', 19790101, 19920101),
   (23, 24, 'Kantens', '0020', 19000101, 19900101),
   (24, 25, 'Kloosterburen', '0021', 19000101, 19900101),
   (25, 26, 'Leek', '0022', 19000101, NULL),
   (26, 27, 'Leens', '0023', 19000101, 19900101),
   (27, 28, 'Loppersum', '0024', 19000101, NULL),
   (28, 29, 'Marum', '0025', 19000101, NULL),
   (29, 30, 'Meeden', '0026', 19000101, 19900101),
   (30, 31, 'Middelstum', '0027', 19000101, 19900101),
   (31, 32, 'Midwolda', '0028', 19000101, 19900101),
   (32, 33, 'Muntendam', '0029', 19000101, 19900101),
   (33, 34, 'Nieuwe Pekela', '0030', 19000101, 19900101),
   (34, 35, 'Nieuweschans', '0031', 19000101, 19900101),
   (35, 36, 'Nieuwolda', '0032', 19000101, 19900101),
   (36, 37, 'Oosterbroek', '0033', 19650701, 19910201),
   (37, 38, 'Almere', '0034', 19840101, NULL),
   (38, 39, 'Oldehove', '0035', 19000101, 19900101),
   (39, 40, 'Oldekerk', '0036', 19000101, 19900101),
   (40, 41, 'Stadskanaal', '0037', 19690101, NULL),
   (41, 42, 'Oude Pekela', '0038', 19000101, 19900101),
   (42, 43, 'Scheemda', '0039', 19000101, 20100101),
   (43, 44, 'Slochteren', '0040', 19000101, NULL),
   (44, 45, 'Stedum', '0041', 19000101, 19900101),
   (45, 46, 'Termunten', '0042', 19000101, 19900101),
   (46, 47, 'Uithuizen', '0043', 19000101, 19790101),
   (47, 48, 'Uithuizermeeden', '0044', 19000101, 19790101),
   (48, 49, 'Ulrum', '0045', 19000101, 19920101),
   (49, 50, 'Usquert', '0046', 19000101, 19900101),
   (50, 51, 'Veendam', '0047', 19000101, NULL),
   (51, 52, 'Vlagtwedde', '0048', 19000101, NULL),
   (52, 53, 'Warffum', '0049', 19000101, 19900101),
   (53, 54, 'Zeewolde', '0050', 19840101, NULL),
   (54, 55, 'Skarsterlân', '0051', 19850301, 20140101),
   (55, 56, 'Winschoten', '0052', 19000101, 20100101),
   (56, 57, 'Winsum', '0053', 19000101, NULL),
   (57, 58, '''t Zandt', '0054', 19000101, 19900101),
   (58, 59, 'Boarnsterhim', '0055', 19850103, 20140101),
   (59, 60, 'Zuidhorn', '0056', 19000101, NULL),
   (60, 61, 'Boornsterhem', '0057', 19840101, 19850103),
   (61, 62, 'Dongeradeel', '0058', 19840101, NULL),
   (62, 63, 'Achtkarspelen', '0059', 19000101, NULL),
   (63, 64, 'Ameland', '0060', 19000101, NULL),
   (64, 65, 'Baarderadeel', '0061', 19000101, 19840101),
   (65, 66, 'Barradeel', '0062', 19000101, 19840101),
   (66, 67, 'het Bildt', '0063', 19000101, NULL),
   (67, 68, 'Bolsward', '0064', 19000101, 20110101),
   (68, 69, 'Dantumadeel', '0065', 19000101, 20090101),
   (69, 70, 'Dokkum', '0066', 19000101, 19840101),
   (70, 71, 'Doniawerstal', '0067', 19000101, 19840101),
   (71, 72, 'Ferwerderadeel', '0068', 19000101, 19990101),
   (72, 73, 'Franeker', '0069', 19000101, 19840101),
   (73, 74, 'Franekeradeel', '0070', 19000101, NULL),
   (74, 75, 'Gaasterland', '0071', 19000101, 19850605),
   (75, 76, 'Harlingen', '0072', 19000101, NULL),
   (76, 77, 'Haskerland', '0073', 19000101, 19840101),
   (77, 78, 'Heerenveen', '0074', 19340701, NULL),
   (78, 79, 'Hemelumer Oldeferd', '0075', 19560101, 19840101),
   (79, 80, 'Hennaarderadeel', '0076', 19000101, 19840101),
   (80, 81, 'Hindeloopen', '0077', 19000101, 19840101),
   (81, 82, 'Idaarderadeel', '0078', 19000101, 19840101),
   (82, 83, 'Kollumerland en Nieuwkruisland', '0079', 19000101, NULL),
   (83, 84, 'Leeuwarden', '0080', 19000101, NULL),
   (84, 85, 'Leeuwarderadeel', '0081', 19000101, NULL),
   (85, 86, 'Lemsterland', '0082', 19000101, 20140101),
   (86, 87, 'Menaldumadeel', '0083', 19000101, 20110101),
   (87, 88, 'Oostdongeradeel', '0084', 19000101, 19840101),
   (88, 89, 'Ooststellingwerf', '0085', 19000101, NULL),
   (89, 90, 'Opsterland', '0086', 19000101, NULL),
   (90, 91, 'Rauwerderhem', '0087', 19000101, 19840101),
   (91, 92, 'Schiermonnikoog', '0088', 19000101, NULL),
   (92, 93, 'Sloten (F)', '0089', 19000101, 19840101),
   (93, 94, 'Smallingerland', '0090', 19000101, NULL),
   (94, 95, 'Sneek', '0091', 19000101, 20110101),
   (95, 96, 'Stavoren', '0092', 19000101, 19840101),
   (96, 97, 'Terschelling', '0093', 19000101, NULL),
   (97, 98, 'Tietjerksteradeel', '0094', 19000101, 19890101),
   (98, 99, 'Utingeradeel', '0095', 19000101, 19840101),
   (99, 100, 'Vlieland', '0096', 19000101, NULL),
   (100, 101, 'Westdongeradeel', '0097', 19000101, 19840101),
   (101, 102, 'Weststellingwerf', '0098', 19000101, NULL),
   (102, 103, 'Wonseradeel', '0099', 19000101, 19870101),
   (103, 104, 'Workum', '0100', 19000101, 19840101),
   (104, 105, 'Wymbritseradeel', '0101', 19000101, 19860101),
   (105, 106, 'IJlst', '0102', 19000101, 19840101),
   (106, 107, 'Littenseradeel', '0103', 19840101, 19850126),
   (107, 108, 'Nijefurd', '0104', 19840101, 20110101),
   (108, 109, 'Anloo', '0105', 19000101, 19980101),
   (109, 110, 'Assen', '0106', 19000101, NULL),
   (110, 111, 'Beilen', '0107', 19000101, 19980101),
   (111, 112, 'Borger', '0108', 19000101, 19980101),
   (112, 113, 'Coevorden', '0109', 19000101, NULL),
   (113, 114, 'Dalen', '0110', 19000101, 19980101),
   (114, 115, 'Diever', '0111', 19000101, 19980101),
   (115, 116, 'Dwingeloo', '0112', 19000101, 19980101),
   (116, 117, 'Eelde', '0113', 19000101, 19980101),
   (117, 118, 'Emmen', '0114', 19000101, NULL),
   (118, 119, 'Gasselte', '0115', 19000101, 19980101),
   (119, 120, 'Gieten', '0116', 19000101, 19980101),
   (120, 121, 'Havelte', '0117', 19000101, 19980101),
   (121, 122, 'Hoogeveen', '0118', 19000101, NULL),
   (122, 123, 'Meppel', '0119', 19000101, NULL),
   (123, 124, 'Norg', '0120', 19000101, 19980101),
   (124, 125, 'Nijeveen', '0121', 19000101, 19980101),
   (125, 126, 'Odoorn', '0122', 19000101, 19980101),
   (126, 127, 'Oosterhesselen', '0123', 19000101, 19980101),
   (127, 128, 'Peize', '0124', 19000101, 19980101),
   (128, 129, 'Roden', '0125', 19000101, 19980101),
   (129, 130, 'Rolde', '0126', 19000101, 19980101),
   (130, 131, 'Ruinen', '0127', 19000101, 19980101),
   (131, 132, 'Ruinerwold', '0128', 19000101, 19980101),
   (132, 133, 'Schoonebeek', '0129', 18840501, 19980101),
   (133, 134, 'Sleen', '0130', 19000101, 19980101),
   (134, 135, 'Smilde', '0131', 19000101, 19980101),
   (135, 136, 'Vledder', '0132', 19000101, 19980101),
   (136, 137, 'Vries', '0133', 19000101, 19980101),
   (137, 138, 'Westerbork', '0134', 19000101, 19980101),
   (138, 139, 'de Wijk', '0135', 19000101, 19980101),
   (139, 140, 'Zuidlaren', '0136', 19000101, 19991201),
   (140, 141, 'Zuidwolde', '0137', 19000101, 19980101),
   (141, 142, 'Zweeloo', '0138', 19000101, 19980101),
   (142, 143, 'Scharsterland', '0139', 19840101, 19850301),
   (143, 144, 'Littenseradiel', '0140', 19850126, NULL),
   (144, 145, 'Almelo', '0141', 19130101, NULL),
   (145, 146, 'Ambt Delden', '0142', 19000101, 20010101),
   (146, 147, 'Avereest', '0143', 19000101, 20010101),
   (147, 148, 'Bathmen', '0144', 19000101, 20050101),
   (148, 149, 'Blankenham', '0145', 19000101, 19730101),
   (149, 150, 'Blokzijl', '0146', 19000101, 19730101),
   (150, 151, 'Borne', '0147', 19000101, NULL),
   (151, 152, 'Dalfsen', '0148', 19000101, NULL),
   (152, 153, 'Denekamp', '0149', 19000101, 20020601),
   (153, 154, 'Deventer', '0150', 19000101, NULL),
   (154, 155, 'Diepenheim', '0151', 19000101, 20010101),
   (155, 156, 'Diepenveen', '0152', 19000101, 19990101),
   (156, 157, 'Enschede', '0153', 19000101, NULL),
   (157, 158, 'Genemuiden', '0154', 19000101, 20010101),
   (158, 159, 'Giethoorn', '0155', 19000101, 19730101),
   (159, 160, 'Goor', '0156', 19000101, 20010101),
   (160, 161, 'Gramsbergen', '0157', 19000101, 20010101),
   (161, 162, 'Haaksbergen', '0158', 19000101, NULL),
   (162, 163, 'Den Ham', '0159', 19000101, 20010101),
   (163, 164, 'Hardenberg', '0160', 19410501, NULL),
   (164, 165, 'Hasselt', '0161', 19000101, 20010101),
   (165, 166, 'Heino', '0162', 19000101, 20010101),
   (166, 167, 'Hellendoorn', '0163', 19000101, NULL),
   (167, 168, 'Hengelo (O)', '0164', 19000101, NULL),
   (168, 169, 'Holten', '0165', 19000101, 20010101),
   (169, 170, 'Kampen', '0166', 19000101, NULL),
   (170, 171, 'Kuinre', '0167', 19000101, 19730101),
   (171, 172, 'Losser', '0168', 19000101, NULL),
   (172, 173, 'Markelo', '0169', 19000101, 20010101),
   (173, 174, 'Nieuwleusen', '0170', 19000101, 20010101),
   (174, 175, 'Noordoostpolder', '0171', 19620701, NULL),
   (175, 176, 'Oldemarkt', '0172', 19000101, 19730101),
   (176, 177, 'Oldenzaal', '0173', 19000101, NULL),
   (177, 178, 'Olst', '0174', 19000101, 20020326),
   (178, 179, 'Ommen', '0175', 19230501, NULL),
   (179, 180, 'Ootmarsum', '0176', 19000101, 20010101),
   (180, 181, 'Raalte', '0177', 19000101, NULL),
   (181, 182, 'Rijssen', '0178', 19000101, 20030315),
   (182, 183, 'Stad Delden', '0179', 19000101, 20010101),
   (183, 184, 'Staphorst', '0180', 19000101, NULL),
   (184, 185, 'Steenwijk', '0181', 19000101, 20030101),
   (185, 186, 'Steenwijkerwold', '0182', 19000101, 19730101),
   (186, 187, 'Tubbergen', '0183', 19000101, NULL),
   (187, 188, 'Urk', '0184', 19000101, NULL),
   (188, 189, 'Vollenhove', '0185', 19420201, 19730101),
   (189, 190, 'Vriezenveen', '0186', 19000101, 20020601),
   (190, 191, 'Wanneperveen', '0187', 19000101, 19730101),
   (191, 192, 'Weerselo', '0188', 19000101, 20010101),
   (192, 193, 'Wierden', '0189', 19000101, NULL),
   (193, 194, 'Wijhe', '0190', 19000101, 20010101),
   (194, 195, 'IJsselmuiden', '0191', 19000101, 20010101),
   (195, 196, 'Zwartsluis', '0192', 19000101, 20010101),
   (196, 197, 'Zwolle', '0193', 19000101, NULL),
   (197, 198, 'Brederwiede', '0194', 19730101, 20010101),
   (198, 199, 'IJsselham', '0195', 19730101, 20010101),
   (199, 200, 'Rijnwaarden', '0196', 19850101, NULL),
   (200, 201, 'Aalten', '0197', 19000101, NULL),
   (201, 202, 'Ammerzoden', '0198', 19000101, 19990101),
   (202, 203, 'Angerlo', '0199', 19000101, 20050101),
   (203, 204, 'Apeldoorn', '0200', 19000101, NULL),
   (204, 205, 'Appeltern', '0201', 19000101, 19840101),
   (205, 206, 'Arnhem', '0202', 19000101, NULL),
   (206, 207, 'Barneveld', '0203', 19000101, NULL),
   (207, 208, 'Batenburg', '0204', 19000101, 19840101),
   (208, 209, 'Beesd', '0205', 19000101, 19780101),
   (209, 210, 'Bemmel', '0206', 19000101, 20030101),
   (210, 211, 'Bergh', '0207', 19000101, 20050101),
   (211, 212, 'Bergharen', '0208', 19000101, 19840101),
   (212, 213, 'Beuningen', '0209', 19000101, NULL),
   (213, 214, 'Beusichem', '0210', 19000101, 19780101),
   (214, 215, 'Borculo', '0211', 19000101, 20050101),
   (215, 216, 'Brakel', '0212', 19000101, 19990101),
   (216, 217, 'Brummen', '0213', 19000101, NULL),
   (217, 218, 'Buren', '0214', 19000101, NULL),
   (218, 219, 'Buurmalsen', '0215', 19000101, 19780101),
   (219, 220, 'Culemborg', '0216', 19000101, NULL),
   (220, 221, 'Deil', '0217', 19000101, 19780101),
   (221, 222, 'Didam', '0218', 19000101, 20050101),
   (222, 223, 'Dinxperlo', '0219', 19000101, 20050101),
   (223, 224, 'Dodewaard', '0220', 19000101, 20020101),
   (224, 225, 'Doesburg', '0221', 19000101, NULL),
   (225, 226, 'Doetinchem', '0222', 19200101, NULL),
   (226, 227, 'Doornspijk', '0223', 19000101, 19740801),
   (227, 228, 'Dreumel', '0224', 19000101, 19840101),
   (228, 229, 'Druten', '0225', 19000101, NULL),
   (229, 230, 'Duiven', '0226', 19000101, NULL),
   (230, 231, 'Echteld', '0227', 19000101, 20020101),
   (231, 232, 'Ede', '0228', 19000101, NULL),
   (232, 233, 'Eibergen', '0229', 19000101, 20050101),
   (233, 234, 'Elburg', '0230', 19000101, NULL),
   (234, 235, 'Elst', '0231', 19000101, 20010101),
   (235, 236, 'Epe', '0232', 19000101, NULL),
   (236, 237, 'Ermelo', '0233', 19000101, NULL),
   (237, 238, 'Est en Opijnen', '0234', 19000101, 19780101),
   (238, 239, 'Ewijk', '0235', 19000101, 19800701),
   (239, 240, 'Geldermalsen', '0236', 19000101, NULL),
   (240, 241, 'Gendringen', '0237', 19000101, 20050101),
   (241, 242, 'Gendt', '0238', 19000101, 20010101),
   (242, 243, 'Gorssel', '0239', 19000101, 20050101),
   (243, 244, 'Groenlo', '0240', 19000101, 20060519),
   (244, 245, 'Groesbeek', '0241', 19000101, 20160101),
   (245, 246, 'Haaften', '0242', 19000101, 19780101),
   (246, 247, 'Harderwijk', '0243', 19000101, NULL),
   (247, 248, 'Hattem', '0244', 19000101, NULL),
   (248, 249, 'Hedel', '0245', 19000101, 19990101),
   (249, 250, 'Heerde', '0246', 19000101, NULL),
   (250, 251, 'Heerewaarden', '0247', 19000101, 19990101),
   (251, 252, 'Hengelo (Gld)', '0248', 19000101, 20050101),
   (252, 253, 'Herwen en Aerdt', '0249', 19000101, 19850101),
   (253, 254, 'Herwijnen', '0250', 19000101, 19860101),
   (254, 255, 'Heteren', '0251', 19000101, 20010101),
   (255, 256, 'Heumen', '0252', 19000101, NULL),
   (256, 257, 'Hoevelaken', '0253', 19000101, 20000101),
   (257, 258, 'Horssen', '0254', 19000101, 19840101),
   (258, 259, 'Huissen', '0255', 19000101, 20010101),
   (259, 260, 'Hummelo en Keppel', '0256', 19000101, 20050101),
   (260, 261, 'Kerkwijk', '0257', 19000101, 19990101),
   (261, 262, 'Kesteren', '0258', 19000101, 20030401),
   (262, 263, 'Laren (Gld)', '0259', 19000101, 19710801),
   (263, 264, 'Lichtenvoorde', '0260', 19000101, 20050101),
   (264, 265, 'Lienden', '0261', 19000101, 19990101),
   (265, 266, 'Lochem', '0262', 19000101, NULL),
   (266, 267, 'Maasdriel', '0263', 19440801, NULL),
   (267, 268, 'Maurik', '0264', 19000101, 19990101),
   (268, 269, 'Millingen aan de Rijn', '0265', 19550101, 20150101),
   (269, 270, 'Neede', '0266', 19000101, 20050101),
   (270, 271, 'Nijkerk', '0267', 19000101, NULL),
   (271, 272, 'Nijmegen', '0268', 19000101, NULL),
   (272, 273, 'Oldebroek', '0269', 19000101, NULL),
   (273, 274, 'Ophemert', '0270', 19000101, 19780101),
   (274, 275, 'Overasselt', '0271', 19000101, 19800701),
   (275, 276, 'Pannerden', '0272', 19000101, 19850101),
   (276, 277, 'Putten', '0273', 19000101, NULL),
   (277, 278, 'Renkum', '0274', 19000101, NULL),
   (278, 279, 'Rheden', '0275', 19000101, NULL),
   (279, 280, 'Rossum', '0276', 19000101, 19990101),
   (280, 281, 'Rozendaal', '0277', 19000101, NULL),
   (281, 282, 'Ruurlo', '0278', 19000101, 20050101),
   (282, 283, 'Scherpenzeel', '0279', 19000101, NULL),
   (283, 284, 'Steenderen', '0280', 19000101, 20050101),
   (284, 285, 'Tiel', '0281', 19000101, NULL),
   (285, 286, 'Ubbergen', '0282', 19000101, 20150101),
   (286, 287, 'Valburg', '0283', 19000101, 20010101),
   (287, 288, 'Varik', '0284', 19000101, 19780101),
   (288, 289, 'Voorst', '0285', 19000101, NULL),
   (289, 290, 'Vorden', '0286', 19000101, 20050101),
   (290, 291, 'Vuren', '0287', 19000101, 19870103),
   (291, 292, 'Waardenburg', '0288', 19000101, 19780101),
   (292, 293, 'Wageningen', '0289', 19000101, NULL),
   (293, 294, 'Wamel', '0290', 19000101, 19850701),
   (294, 295, 'Warnsveld', '0291', 19000101, 20050101),
   (295, 296, 'Wehl', '0292', 19000101, 20050101),
   (296, 297, 'Westervoort', '0293', 19000101, NULL),
   (297, 298, 'Winterswijk', '0294', 19000101, NULL),
   (298, 299, 'Wisch', '0295', 19000101, 20050101),
   (299, 300, 'Wijchen', '0296', 19000101, NULL),
   (300, 301, 'Zaltbommel', '0297', 19000101, NULL),
   (301, 302, 'Zelhem', '0298', 19000101, 20050101),
   (302, 303, 'Zevenaar', '0299', 19000101, NULL),
   (303, 304, 'Zoelen', '0300', 19000101, 19780101),
   (304, 305, 'Zutphen', '0301', 19000101, NULL),
   (305, 306, 'Nunspeet', '0302', 19720101, NULL),
   (306, 307, 'Dronten', '0303', 19720101, NULL),
   (307, 308, 'Neerijnen', '0304', 19780101, NULL),
   (308, 309, 'Abcoude', '0305', 19410501, 20110101),
   (309, 310, 'Amerongen', '0306', 19000101, 20060101),
   (310, 311, 'Amersfoort', '0307', 19000101, NULL),
   (311, 312, 'Baarn', '0308', 19000101, NULL),
   (312, 313, 'Benschop', '0309', 19000101, 19890101),
   (313, 314, 'De Bilt', '0310', 19000101, NULL),
   (314, 315, 'Breukelen', '0311', 19490101, 20110101),
   (315, 316, 'Bunnik', '0312', 19000101, NULL),
   (316, 317, 'Bunschoten', '0313', 19000101, NULL),
   (317, 318, 'Cothen', '0314', 19000101, 19960101),
   (318, 319, 'Doorn', '0315', 19000101, 20060101),
   (319, 320, 'Driebergen-Rijsenburg', '0316', 19310501, 20060101),
   (320, 321, 'Eemnes', '0317', 19000101, NULL),
   (321, 322, 'Harmelen', '0318', 19000101, 20010101),
   (322, 323, 'Hoenkoop', '0319', 19000101, 19700901),
   (323, 324, 'Hoogland', '0320', 19000101, 19740101),
   (324, 325, 'Houten', '0321', 19000101, NULL),
   (325, 326, 'Jutphaas', '0322', 19000101, 19710701),
   (326, 327, 'Kamerik', '0323', 19000101, 19890101),
   (327, 328, 'Kockengen', '0324', 19000101, 19890101),
   (328, 329, 'Langbroek', '0325', 19000101, 19960101),
   (329, 330, 'Leersum', '0326', 19000101, 20060101),
   (330, 331, 'Leusden', '0327', 19000101, NULL),
   (331, 332, 'Linschoten', '0328', 19000101, 19890101),
   (332, 333, 'Loenen', '0329', 19000101, 20110101),
   (333, 334, 'Loosdrecht', '0330', 19000101, 20020101),
   (334, 335, 'Lopik', '0331', 19000101, NULL),
   (335, 336, 'Maarn', '0332', 19000101, 20060101),
   (336, 337, 'Maarssen', '0333', 19000101, 20110101),
   (337, 338, 'Maartensdijk', '0334', 19000101, 20010101),
   (338, 339, 'Montfoort', '0335', 19000101, NULL),
   (339, 340, 'Mijdrecht', '0336', 19000101, 19890101),
   (340, 341, 'Nigtevecht', '0337', 19000101, 19890101),
   (341, 342, 'Polsbroek', '0338', 18570613, 19890101),
   (342, 343, 'Renswoude', '0339', 19000101, NULL),
   (343, 344, 'Rhenen', '0340', 19000101, NULL),
   (344, 345, 'Snelrewaard', '0341', 19000101, 19890101),
   (345, 346, 'Soest', '0342', 19000101, NULL),
   (346, 347, 'Stoutenburg', '0343', 19000101, 19690601),
   (347, 348, 'Utrecht', '0344', 19000101, NULL),
   (348, 349, 'Veenendaal', '0345', 19000101, NULL),
   (349, 350, 'Vinkeveen en Waverveen', '0346', 18410101, 19890101),
   (350, 351, 'Vleuten-De Meern', '0347', 19540101, 20010101),
   (351, 352, 'Vreeswijk', '0348', 19000101, 19710701),
   (352, 353, 'Willeskop', '0349', 19000101, 19890101),
   (353, 354, 'Wilnis', '0350', 19000101, 19890101),
   (354, 355, 'Woudenberg', '0351', 19000101, NULL),
   (355, 356, 'Wijk bij Duurstede', '0352', 19000101, NULL),
   (356, 357, 'IJsselstein', '0353', 19000101, NULL),
   (357, 358, 'Zegveld', '0354', 19000101, 19890101),
   (358, 359, 'Zeist', '0355', 19000101, NULL),
   (359, 360, 'Nieuwegein', '0356', 19710701, NULL),
   (360, 361, 'Egmond', '0357', 19780701, 20010101),
   (361, 362, 'Aalsmeer', '0358', 19000101, NULL),
   (362, 363, 'Abbekerk', '0359', 19000101, 19790101),
   (363, 364, 'Akersloot', '0360', 19000101, 20020101),
   (364, 365, 'Alkmaar', '0361', 19000101, NULL),
   (365, 366, 'Amstelveen', '0362', 19640101, NULL),
   (366, 367, 'Amsterdam', '0363', 19000101, NULL),
   (367, 368, 'Andijk', '0364', 19000101, 20110101),
   (368, 369, 'Graft-De Rijp', '0365', 19700801, 20150101),
   (369, 370, 'Anna Paulowna', '0366', 18700718, 20120101),
   (370, 371, 'Assendelft', '0367', 19000101, 19740101),
   (371, 372, 'Avenhorn', '0368', 19000101, 19790101),
   (372, 373, 'Barsingerhorn', '0369', 19000101, 19900101),
   (373, 374, 'Beemster', '0370', 19000101, NULL),
   (374, 375, 'Beets', '0371', 19000101, 19700801),
   (375, 376, 'Bennebroek', '0372', 19000101, 20090101),
   (376, 377, 'Bergen (NH)', '0373', 19000101, NULL),
   (377, 378, 'Berkhout', '0374', 19000101, 19790101),
   (378, 379, 'Beverwijk', '0375', 19000101, NULL),
   (379, 380, 'Blaricum', '0376', 19000101, NULL),
   (380, 381, 'Bloemendaal', '0377', 19000101, NULL),
   (381, 382, 'Blokker', '0378', 19000101, 19790101),
   (382, 383, 'Bovenkarspel', '0379', 19000101, 19790101),
   (383, 384, 'Broek in Waterland', '0380', 19000101, 19910101),
   (384, 385, 'Bussum', '0381', 19000101, 20160101),
   (385, 386, 'Callantsoog', '0382', 19000101, 19900101),
   (386, 387, 'Castricum', '0383', 19000101, NULL),
   (387, 388, 'Diemen', '0384', 19000101, NULL),
   (388, 389, 'Edam-Volendam', '0385', 19750101, NULL),
   (389, 390, 'Egmond aan Zee', '0386', 19000101, 19780701),
   (390, 391, 'Egmond-Binnen', '0387', 19000101, 19780701),
   (391, 392, 'Enkhuizen', '0388', 19000101, NULL),
   (392, 393, 'Graft', '0389', 19000101, 19700801),
   (393, 394, '''s-Graveland', '0390', 19000101, 20020101),
   (394, 395, 'Grootebroek', '0391', 19000101, 19790101),
   (395, 396, 'Haarlem', '0392', 19000101, NULL),
   (396, 397, 'Haarlemmerliede en Spaarnwoude', '0393', 18570101, NULL),
   (397, 398, 'Haarlemmermeer', '0394', 18550101, NULL),
   (398, 399, 'Harenkarspel', '0395', 19000101, 20130101),
   (399, 400, 'Heemskerk', '0396', 19000101, NULL),
   (400, 401, 'Heemstede', '0397', 19000101, NULL),
   (401, 402, 'Heerhugowaard', '0398', 19000101, NULL),
   (402, 403, 'Heiloo', '0399', 19000101, NULL),
   (403, 404, 'Den Helder', '0400', 19000101, NULL),
   (404, 405, 'Hensbroek', '0401', 19000101, 19790101),
   (405, 406, 'Hilversum', '0402', 19000101, NULL),
   (406, 407, 'Hoogkarspel', '0403', 19000101, 19790101),
   (407, 408, 'Hoogwoud', '0404', 19000101, 19790101),
   (408, 409, 'Hoorn', '0405', 19000101, NULL),
   (409, 410, 'Huizen', '0406', 19000101, NULL),
   (410, 411, 'Ilpendam', '0407', 19000101, 19910101),
   (411, 412, 'Jisp', '0408', 19000101, 19910101),
   (412, 413, 'Katwoude', '0409', 19000101, 19910101),
   (413, 414, 'Koedijk', '0410', 19000101, 19721001),
   (414, 415, 'Koog aan de Zaan', '0411', 19000101, 19740101),
   (415, 416, 'Niedorp', '0412', 19700801, 20120101),
   (416, 417, 'Krommenie', '0413', 19000101, 19740101),
   (417, 418, 'Kwadijk', '0414', 19000101, 19700801),
   (418, 419, 'Landsmeer', '0415', 19000101, NULL),
   (419, 420, 'Langedijk', '0416', 19410801, NULL),
   (420, 421, 'Laren', '0417', 19000101, NULL),
   (421, 422, 'Limmen', '0418', 19000101, 20020101),
   (422, 423, 'Marken', '0419', 19000101, 19910101),
   (423, 424, 'Medemblik', '0420', 19000101, NULL),
   (424, 425, 'Middelie', '0421', 19000101, 19700801),
   (425, 426, 'Midwoud', '0422', 19000101, 19790101),
   (426, 427, 'Monnickendam', '0423', 19000101, 19910101),
   (427, 428, 'Muiden', '0424', 19000101, 20160101),
   (428, 429, 'Naarden', '0425', 19000101, 20160101),
   (429, 430, 'Nederhorst den Berg', '0426', 19000101, 20020101),
   (430, 431, 'Nibbixwoud', '0427', 19000101, 19790101),
   (431, 432, 'Nieuwe Niedorp', '0428', 19000101, 19700801),
   (432, 433, 'Obdam', '0429', 19000101, 20070101),
   (433, 434, 'Oosthuizen', '0430', 19000101, 19700801),
   (434, 435, 'Oostzaan', '0431', 19000101, NULL),
   (435, 436, 'Opmeer', '0432', 19000101, NULL),
   (436, 437, 'Opperdoes', '0433', 19000101, 19790101),
   (437, 438, 'Oterleek', '0434', 19000101, 19700801),
   (438, 439, 'Oudendijk', '0435', 19000101, 19790101),
   (439, 440, 'Oude Niedorp', '0436', 19000101, 19700801),
   (440, 441, 'Ouder-Amstel', '0437', 19000101, NULL),
   (441, 442, 'Oudorp', '0438', 19000101, 19721001),
   (442, 443, 'Purmerend', '0439', 19000101, NULL),
   (443, 444, 'De Rijp', '0440', 19000101, 19700801),
   (444, 445, 'Schagen', '0441', 19000101, NULL),
   (445, 446, 'Schellinkhout', '0442', 19000101, 19700801),
   (446, 447, 'Schermerhorn', '0443', 19000101, 19700801),
   (447, 448, 'Schoorl', '0444', 19000101, 20010101),
   (448, 449, 'Sint Maarten', '0445', 19000101, 19900101),
   (449, 450, 'Sint Pancras', '0446', 19000101, 19900101),
   (450, 451, 'Sijbekarspel', '0447', 19000101, 19790101),
   (451, 452, 'Texel', '0448', 19000101, NULL),
   (452, 453, 'Twisk', '0449', 19000101, 19790101),
   (453, 454, 'Uitgeest', '0450', 19000101, NULL),
   (454, 455, 'Uithoorn', '0451', 19000101, NULL),
   (455, 456, 'Ursem', '0452', 19000101, 19790101),
   (456, 457, 'Velsen', '0453', 19000101, NULL),
   (457, 458, 'Venhuizen', '0454', 19000101, 20060101),
   (458, 459, 'Warder', '0455', 19000101, 19700801),
   (459, 460, 'Warmenhuizen', '0456', 19000101, 19900101),
   (460, 461, 'Weesp', '0457', 19000101, NULL),
   (461, 462, 'Schermer', '0458', 19700801, 20150101),
   (462, 463, 'Wervershoof', '0459', 19000101, 20110101),
   (463, 464, 'Westwoud', '0460', 19000101, 19790101),
   (464, 465, 'Westzaan', '0461', 19000101, 19740101),
   (465, 466, 'Wieringen', '0462', 19000101, 20120101),
   (466, 467, 'Wieringermeer', '0463', 19380101, 20120101),
   (467, 468, 'Wieringerwaard', '0464', 19000101, 19700801),
   (468, 469, 'Winkel', '0465', 19000101, 19700801),
   (469, 470, 'Wognum', '0466', 19000101, 20070101),
   (470, 471, 'Wormer', '0467', 19000101, 19910101),
   (471, 472, 'Wormerveer', '0468', 19000101, 19740101),
   (472, 473, 'Wijdenes', '0469', 19000101, 19700801),
   (473, 474, 'Wijdewormer', '0470', 19000101, 19910101),
   (474, 475, 'Zaandam', '0471', 19000101, 19740101),
   (475, 476, 'Zaandijk', '0472', 19000101, 19740101),
   (476, 477, 'Zandvoort', '0473', 19000101, NULL),
   (477, 478, 'Zuid- en Noord-Schermer', '0474', 19000101, 19700801),
   (478, 479, 'Zwaag', '0475', 19000101, 19790101),
   (479, 480, 'Zijpe', '0476', 19000101, 20130101),
   (480, 481, 'Albrandswaard (oud)', '0477', 19000101, 18410901),
   (481, 482, 'Zeevang', '0478', 19700801, 20160101),
   (482, 483, 'Zaanstad', '0479', 19740101, NULL),
   (483, 484, 'Ter Aar', '0480', 19000101, 20070101),
   (484, 485, 'Abbenbroek', '0481', 19000101, 19800101),
   (485, 486, 'Alblasserdam', '0482', 19000101, NULL),
   (486, 487, 'Alkemade', '0483', 19000101, 20090101),
   (487, 488, 'Alphen aan den Rijn', '0484', 19180101, NULL),
   (488, 489, 'Ameide', '0485', 19000101, 19860101),
   (489, 490, 'Ammerstol', '0486', 19000101, 19850101),
   (490, 491, 'Arkel', '0487', 19000101, 19860101),
   (491, 492, 'Asperen', '0488', 19000101, 19860101),
   (492, 493, 'Barendrecht', '0489', 18860101, NULL),
   (493, 494, 'Benthuizen', '0490', 19000101, 19910101),
   (494, 495, 'Bergambacht', '0491', 19000101, 20150101),
   (495, 496, 'Bergschenhoek', '0492', 19000101, 20070101),
   (496, 497, 'Berkel en Rodenrijs', '0493', 19000101, 20070101),
   (497, 498, 'Berkenwoude', '0494', 19000101, 19850101),
   (498, 499, 'Bleiswijk', '0495', 19000101, 20070101),
   (499, 500, 'Bleskensgraaf en Hofwegen', '0496', 19000101, 19860101),
   (500, 501, 'Bodegraven', '0497', 19000101, 20110101),
   (501, 502, 'Drechterland', '0498', 19800101, NULL),
   (502, 503, 'Boskoop', '0499', 19000101, 20140101),
   (503, 504, 'Brandwijk', '0500', 19000101, 19860101),
   (504, 505, 'Brielle', '0501', 19000101, NULL),
   (505, 506, 'Capelle aan den IJssel', '0502', 19000101, NULL),
   (506, 507, 'Delft', '0503', 19000101, NULL),
   (507, 508, 'Dirksland', '0504', 19000101, 20130101),
   (508, 509, 'Dordrecht', '0505', 19000101, NULL),
   (509, 510, 'Driebruggen', '0506', 19640201, 19890101),
   (510, 511, 'Dubbeldam', '0507', 19000101, 19700701),
   (511, 512, 'Everdingen', '0508', 19000101, 19860101),
   (512, 513, 'Geervliet', '0509', 19000101, 19800101),
   (513, 514, 'Giessenburg', '0510', 19570101, 19860101),
   (514, 515, 'Goedereede', '0511', 19000101, 20130101),
   (515, 516, 'Gorinchem', '0512', 19000101, NULL),
   (516, 517, 'Gouda', '0513', 19000101, NULL),
   (517, 518, 'Gouderak', '0514', 19000101, 19850101),
   (518, 519, 'Goudriaan', '0515', 19000101, 19860101),
   (519, 520, 'Goudswaard', '0516', 19000101, 19840101),
   (520, 521, '''s-Gravendeel', '0517', 19000101, 20070101),
   (521, 522, '''s-Gravenhage', '0518', 19000101, NULL),
   (522, 523, '''s-Gravenzande', '0519', 19000101, 20040101),
   (523, 524, 'Groot-Ammers', '0520', 19000101, 19860101),
   (524, 525, 'Haastrecht', '0521', 19000101, 19850101),
   (525, 526, 'Hagestein', '0522', 19000101, 19860101),
   (526, 527, 'Hardinxveld-Giessendam', '0523', 19570101, NULL),
   (527, 528, 'Hazerswoude', '0524', 19000101, 19910101),
   (528, 529, 'Heenvliet', '0525', 19000101, 19800101),
   (529, 530, 'Heerjansdam', '0526', 19000101, 20030101),
   (530, 531, 'Hei- en Boeicop', '0527', 19000101, 19860101),
   (531, 532, 'Heinenoord', '0528', 19000101, 19840101),
   (532, 533, 'Noorder-Koggenland', '0529', 19790101, 20070101),
   (533, 534, 'Hellevoetsluis', '0530', 19000101, NULL),
   (534, 535, 'Hendrik-Ido-Ambacht', '0531', 19000101, NULL),
   (535, 536, 'Stede Broec', '0532', 19790101, NULL),
   (536, 537, 'Heukelum', '0533', 19000101, 19860101),
   (537, 538, 'Hillegom', '0534', 19000101, NULL),
   (538, 539, 'Hoogblokland', '0535', 19000101, 19860101),
   (539, 540, 'Hoornaar', '0536', 19000101, 19860101),
   (540, 541, 'Katwijk', '0537', 19000101, NULL),
   (541, 542, 'Kedichem', '0538', 19000101, 19860101),
   (542, 543, 'Klaaswaal', '0539', 19000101, 19840101),
   (543, 544, 'Koudekerk aan den Rijn', '0540', 19000101, 19910101),
   (544, 545, 'Krimpen aan de Lek', '0541', 19000101, 19850101),
   (545, 546, 'Krimpen aan den IJssel', '0542', 19000101, NULL),
   (546, 547, 'Langerak', '0543', 19000101, 19860101),
   (547, 548, 'Leerbroek', '0544', 19000101, 19860101),
   (548, 549, 'Leerdam', '0545', 19000101, NULL),
   (549, 550, 'Leiden', '0546', 19000101, NULL),
   (550, 551, 'Leiderdorp', '0547', 19000101, NULL),
   (551, 552, 'Leidschendam', '0548', 19380101, 20020101),
   (552, 553, 'Leimuiden', '0549', 19000101, 19910101),
   (553, 554, 'Lekkerkerk', '0550', 19000101, 19850101),
   (554, 555, 'Lexmond', '0551', 19000101, 19860101),
   (555, 556, 'De Lier', '0552', 19000101, 20040101),
   (556, 557, 'Lisse', '0553', 19000101, NULL),
   (557, 558, 'Maasdam', '0554', 19000101, 19840101),
   (558, 559, 'Maasland', '0555', 19000101, 20040101),
   (559, 560, 'Maassluis', '0556', 19000101, NULL),
   (560, 561, 'Meerkerk', '0557', 19000101, 19860101),
   (561, 562, 'Wester-Koggenland', '0558', 19790101, 20070101),
   (562, 563, 'Middelharnis', '0559', 19000101, 20130101),
   (563, 564, 'Moerkapelle', '0560', 19000101, 19910101),
   (564, 565, 'Molenaarsgraaf', '0561', 19000101, 19860101),
   (565, 566, 'Monster', '0562', 19000101, 20040101),
   (566, 567, 'Moordrecht', '0563', 19000101, 20100101),
   (567, 568, 'Mijnsheerenland', '0564', 19000101, 19840101),
   (568, 569, 'Naaldwijk', '0565', 19000101, 20040101),
   (569, 570, 'Nieuw-Beijerland', '0566', 19000101, 19840101),
   (570, 571, 'Nieuwerkerk aan den IJssel', '0567', 19000101, 20100101),
   (571, 572, 'Bernisse', '0568', 19800101, 20150101),
   (572, 573, 'Nieuwkoop', '0569', 19000101, NULL),
   (573, 574, 'Nieuwland', '0570', 19000101, 19860101),
   (574, 575, 'Nieuw-Lekkerland', '0571', 19000101, 20130101),
   (575, 576, 'Nieuwpoort', '0572', 19000101, 19860101),
   (576, 577, 'Nieuwveen', '0573', 19000101, 19940101),
   (577, 578, 'Noordeloos', '0574', 19000101, 19860101),
   (578, 579, 'Noordwijk', '0575', 19000101, NULL),
   (579, 580, 'Noordwijkerhout', '0576', 19000101, NULL),
   (580, 581, 'Nootdorp', '0577', 19000101, 20020101),
   (581, 582, 'Numansdorp', '0578', 19000101, 19840101),
   (582, 583, 'Oegstgeest', '0579', 19000101, NULL),
   (583, 584, 'Oostflakkee', '0580', 19660101, 20130101),
   (584, 585, 'Oostvoorne', '0581', 19000101, 19800101),
   (585, 586, 'Ottoland', '0582', 19000101, 19860101),
   (586, 587, 'Oud-Alblas', '0583', 19000101, 19860101),
   (587, 588, 'Oud-Beijerland', '0584', 19000101, NULL),
   (588, 589, 'Binnenmaas', '0585', 19840101, NULL),
   (589, 590, 'Oudenhoorn', '0586', 19000101, 19800101),
   (590, 591, 'Ouderkerk aan den IJssel', '0587', 19000101, 19850101),
   (591, 592, 'Korendijk', '0588', 19840101, NULL),
   (592, 593, 'Oudewater', '0589', 19000101, NULL),
   (593, 594, 'Papendrecht', '0590', 19000101, NULL),
   (594, 595, 'Piershil', '0591', 19000101, 19840101),
   (595, 596, 'Poortugaal', '0592', 19000101, 19850101),
   (596, 597, 'Puttershoek', '0593', 19000101, 19840101),
   (597, 598, 'Pijnacker', '0594', 19000101, 20020101),
   (598, 599, 'Reeuwijk', '0595', 19000101, 20110101),
   (599, 600, 'Rhoon', '0596', 19000101, 19850101),
   (600, 601, 'Ridderkerk', '0597', 19000101, NULL),
   (601, 602, 'Rockanje', '0598', 19000101, 19800101),
   (602, 603, 'Rotterdam', '0599', 19000101, NULL),
   (603, 604, 'Rozenburg', '0600', 19000101, 20100318),
   (604, 605, 'Rijnsaterwoude', '0601', 19000101, 19910101),
   (605, 606, 'Rijnsburg', '0602', 19000101, 20060101),
   (606, 607, 'Rijswijk', '0603', 19000101, NULL),
   (607, 608, 'Sassenheim', '0604', 19000101, 20060101),
   (608, 609, 'Schelluinen', '0605', 19000101, 19860101),
   (609, 610, 'Schiedam', '0606', 19000101, NULL),
   (610, 611, 'Schipluiden', '0607', 19000101, 20040101),
   (611, 612, 'Schoonhoven', '0608', 19000101, 20150101),
   (612, 613, 'Schoonrewoerd', '0609', 19000101, 19860101),
   (613, 614, 'Sliedrecht', '0610', 19000101, NULL),
   (614, 615, 'Cromstrijen', '0611', 19840101, NULL),
   (615, 616, 'Spijkenisse', '0612', 19000101, 20150101),
   (616, 617, 'Albrandswaard', '0613', 19850101, NULL),
   (617, 618, 'Westvoorne', '0614', 19800101, NULL),
   (618, 619, 'Stolwijk', '0615', 19000101, 19850101),
   (619, 620, 'Streefkerk', '0616', 19000101, 19860101),
   (620, 621, 'Strijen', '0617', 19000101, NULL),
   (621, 622, 'Tienhoven (ZH)', '0618', 19000101, 19860101),
   (622, 623, 'Valkenburg (ZH)', '0619', 19000101, 20060101),
   (623, 624, 'Vianen', '0620', 19000101, NULL),
   (624, 625, 'Vierpolders', '0621', 19000101, 19800101),
   (625, 626, 'Vlaardingen', '0622', 19000101, NULL),
   (626, 627, 'Vlist', '0623', 19000101, 20150101),
   (627, 628, 'Voorburg', '0624', 19000101, 20020101),
   (628, 629, 'Voorhout', '0625', 19000101, 20060101),
   (629, 630, 'Voorschoten', '0626', 19000101, NULL),
   (630, 631, 'Waddinxveen', '0627', 18700701, NULL),
   (631, 632, 'Warmond', '0628', 19000101, 20060101),
   (632, 633, 'Wassenaar', '0629', 19000101, NULL),
   (633, 634, 'Wateringen', '0630', 19000101, 20040101),
   (634, 635, 'Westmaas', '0631', 19000101, 19840101),
   (635, 636, 'Woerden', '0632', 19000101, NULL),
   (636, 637, 'Woubrugge', '0633', 19000101, 19910101),
   (637, 638, 'Wijngaarden', '0634', 19000101, 19860101),
   (638, 639, 'Zevenhoven', '0635', 19000101, 19910101),
   (639, 640, 'Zevenhuizen', '0636', 19000101, 19910101),
   (640, 641, 'Zoetermeer', '0637', 19000101, NULL),
   (641, 642, 'Zoeterwoude', '0638', 19000101, NULL),
   (642, 643, 'Zuid-Beijerland', '0639', 19000101, 19840101),
   (643, 644, 'Zuidland', '0640', 19000101, 19800101),
   (644, 645, 'Zwartewaal', '0641', 19000101, 19800101),
   (645, 646, 'Zwijndrecht', '0642', 19000101, NULL),
   (646, 647, 'Nederlek', '0643', 19850101, 20150101),
   (647, 648, 'Ouderkerk', '0644', 19850101, 20150101),
   (648, 649, 'Jacobswoude', '0645', 19910101, 20090101),
   (649, 650, 'Rijneveld', '0646', 19910101, 19930101),
   (650, 651, 'Moerhuizen', '0647', 19910101, 19920201),
   (651, 652, 'Aardenburg', '0648', 19000101, 19950101),
   (652, 653, 'Arnemuiden', '0649', 19000101, 19970101),
   (653, 654, 'Axel', '0650', 19000101, 20030101),
   (654, 655, 'Baarland', '0651', 19000101, 19700101),
   (655, 656, 'Biervliet', '0652', 19000101, 19700401),
   (656, 657, 'Gaasterlân-Sleat', '0653', 19850605, 20140101),
   (657, 658, 'Borsele', '0654', 19700101, NULL),
   (658, 659, 'Breskens', '0655', 19000101, 19700401),
   (659, 660, 'Brouwershaven', '0656', 19000101, 19970101),
   (660, 661, 'Bruinisse', '0657', 19000101, 19970101),
   (661, 662, 'Cadzand', '0658', 19000101, 19700401),
   (662, 663, 'Clinge', '0659', 19000101, 19700401),
   (663, 664, 'Domburg', '0660', 19000101, 19970101),
   (664, 665, 'Driewegen', '0661', 19000101, 19700101),
   (665, 666, 'Duiveland', '0662', 19610101, 19970101),
   (666, 667, 'Ellewoutsdijk', '0663', 19000101, 19700101),
   (667, 668, 'Goes', '0664', 19000101, NULL),
   (668, 669, 'Graauw en Langendam', '0665', 19000101, 19700401),
   (669, 670, '''s-Gravenpolder', '0666', 19000101, 19700101),
   (670, 671, 'Groede', '0667', 19000101, 19700401),
   (671, 672, 'West Maas en Waal', '0668', 19850701, NULL),
   (672, 673, '''s Heer Abtskerke', '0669', 19000101, 19700101),
   (673, 674, '''s-Heer Arendskerke', '0670', 19000101, 19700101),
   (674, 675, '''s-Heerenhoek', '0671', 19000101, 19700101),
   (675, 676, 'Heinkenszand', '0672', 19000101, 19700101),
   (676, 677, 'Hoedekenskerke', '0673', 19000101, 19700101),
   (677, 678, 'Hoek', '0674', 19000101, 19700401),
   (678, 679, 'Hontenisse', '0675', 19000101, 20030101),
   (679, 680, 'Hoofdplaat', '0676', 19000101, 19700401),
   (680, 681, 'Hulst', '0677', 19000101, NULL),
   (681, 682, 'Kapelle', '0678', 19000101, NULL),
   (682, 683, 'Kattendijke', '0679', 19000101, 19700101),
   (683, 684, 'Kloetinge', '0680', 19000101, 19700401),
   (684, 685, 'Koewacht', '0681', 19000101, 19700401),
   (685, 686, 'Kortgene', '0682', 19000101, 19950101),
   (686, 687, 'Wymbritseradiel', '0683', 19860101, 20110101),
   (687, 688, 'Krabbendijke', '0684', 19000101, 19700101),
   (688, 689, 'Kruiningen', '0685', 19000101, 19700101),
   (689, 690, 'Mariekerke', '0686', 19660701, 19970101),
   (690, 691, 'Middelburg', '0687', 19000101, NULL),
   (691, 692, 'Middenschouwen', '0688', 19610101, 19970101),
   (692, 693, 'Giessenlanden', '0689', 19860101, NULL),
   (693, 694, 'Nieuwvliet', '0690', 19000101, 19700401),
   (694, 695, 'Nisse', '0691', 19000101, 19700101),
   (695, 696, 'Oostburg', '0692', 19000101, 20030101),
   (696, 697, 'Graafstroom', '0693', 19860101, 20130101),
   (697, 698, 'Liesveld', '0694', 19860101, 20130101),
   (698, 699, 'Oudelande', '0695', 19000101, 19700101),
   (699, 700, 'Oud-Vossemeer', '0696', 19000101, 19710701),
   (700, 701, 'Overslag', '0697', 19000101, 19700401),
   (701, 702, 'Ovezande', '0698', 19000101, 19700101),
   (702, 703, 'Philippine', '0699', 19000101, 19700401),
   (703, 704, 'Poortvliet', '0700', 19000101, 19710701),
   (704, 705, 'Retranchement', '0701', 19000101, 19700401),
   (705, 706, 'Rilland-Bath', '0702', 18771210, 19700101),
   (706, 707, 'Reimerswaal', '0703', 19700101, NULL),
   (707, 708, 'Sas van Gent', '0704', 19000101, 20030101),
   (708, 709, 'Scherpenisse', '0705', 19000101, 19710701),
   (709, 710, 'Schoondijke', '0706', 19000101, 19700401),
   (710, 711, 'Zederik', '0707', 19860101, NULL),
   (711, 712, 'Sint-Annaland', '0708', 19000101, 19710701),
   (712, 713, 'St. Jansteen', '0709', 19000101, 19700401),
   (713, 714, 'Wûnseradiel', '0710', 19870101, 20110101),
   (714, 715, 'Sint-Maartensdijk', '0711', 19000101, 19710701),
   (715, 716, 'Sint Philipsland', '0712', 19000101, 19950101),
   (716, 717, 'Sluis (oud)', '0713', 19000101, 19950101),
   (717, 718, 'Stavenisse', '0714', 19000101, 19710701),
   (718, 719, 'Terneuzen', '0715', 19000101, NULL),
   (719, 720, 'Tholen', '0716', 19000101, NULL),
   (720, 721, 'Veere', '0717', 19000101, NULL),
   (721, 722, 'Vlissingen', '0718', 19000101, NULL),
   (722, 723, 'Vogelwaarde', '0719', 19360701, 19700401),
   (723, 724, 'Valkenisse', '0720', 19660701, 19970101),
   (724, 725, 'Waarde', '0721', 19000101, 19700101),
   (725, 726, 'Waterlandkerkje', '0722', 19000101, 19700401),
   (726, 727, 'Wemeldinge', '0723', 19000101, 19700101),
   (727, 728, 'Westdorpe', '0724', 19000101, 19700401),
   (728, 729, 'Westerschouwen', '0725', 19610101, 19970101),
   (729, 730, 'Westkapelle', '0726', 19000101, 19970101),
   (730, 731, 'Wissenkerke', '0727', 19000101, 19950101),
   (731, 732, 'Wolphaartsdijk', '0728', 19000101, 19700101),
   (732, 733, 'Yerseke', '0729', 19000101, 19700101),
   (733, 734, 'IJzendijke', '0730', 19000101, 19700401),
   (734, 735, 'Zaamslag', '0731', 19000101, 19700401),
   (735, 736, 'Zierikzee', '0732', 19000101, 19970101),
   (736, 737, 'Lingewaal', '0733', 19870103, NULL),
   (737, 738, 'Zuiddorpe', '0734', 19000101, 19700401),
   (738, 739, 'Zuidzande', '0735', 19000101, 19700401),
   (739, 740, 'De Ronde Venen', '0736', 19890101, NULL),
   (740, 741, 'Tytsjerksteradiel', '0737', 19890101, NULL),
   (741, 742, 'Aalburg', '0738', 19000101, NULL),
   (742, 743, 'Aarle-Rixtel', '0739', 19000101, 19970101),
   (743, 744, 'Almkerk', '0740', 19000101, 19730101),
   (744, 745, 'Alphen en Riel', '0741', 19000101, 19970101),
   (745, 746, 'Andel', '0742', 19000101, 19730101),
   (746, 747, 'Asten', '0743', 19000101, NULL),
   (747, 748, 'Baarle-Nassau', '0744', 19000101, NULL),
   (748, 749, 'Bakel en Milheeze', '0745', 19000101, 19970101),
   (749, 750, 'Beek en Donk', '0746', 19000101, 19970101),
   (750, 751, 'Beers', '0747', 19000101, 19940101),
   (751, 752, 'Bergen op Zoom', '0748', 19000101, NULL),
   (752, 753, 'Bergeyk', '0749', 19000101, 19990101),
   (753, 754, 'Berghem', '0750', 19000101, 19940101),
   (754, 755, 'Berkel-Enschot', '0751', 19000101, 19970101),
   (755, 756, 'Berlicum', '0752', 19000101, 19960101),
   (756, 757, 'Best', '0753', 19000101, NULL),
   (757, 758, 'Bladel en Netersel', '0754', 19000101, 19970101),
   (758, 759, 'Boekel', '0755', 19000101, NULL),
   (759, 760, 'Boxmeer', '0756', 19000101, NULL),
   (760, 761, 'Boxtel', '0757', 19000101, NULL),
   (761, 762, 'Breda', '0758', 19000101, NULL),
   (762, 763, 'Budel', '0759', 19000101, 19980128),
   (763, 764, 'Chaam', '0760', 19000101, 19970101),
   (764, 765, 'Cuijk en Sint Agatha', '0761', 19000101, 19940101),
   (765, 766, 'Deurne', '0762', 19260101, NULL),
   (766, 767, 'Diessen', '0763', 19000101, 19970101),
   (767, 768, 'Dinteloord en Prinsenland', '0764', 19000101, 19970101),
   (768, 769, 'Pekela', '0765', 19900101, NULL),
   (769, 770, 'Dongen', '0766', 19000101, NULL),
   (770, 771, 'Drunen', '0767', 19000101, 19970101),
   (771, 772, 'Den Dungen', '0768', 19000101, 19960101),
   (772, 773, 'Dussen', '0769', 19000101, 19970101),
   (773, 774, 'Eersel', '0770', 19000101, NULL),
   (774, 775, 'Eethen', '0771', 19230501, 19730101),
   (775, 776, 'Eindhoven', '0772', 19000101, NULL),
   (776, 777, 'Empel en Meerwijk', '0773', 19000101, 19710401),
   (777, 778, 'Engelen', '0774', 19000101, 19710401),
   (778, 779, 'Erp', '0775', 19000101, 19940101),
   (779, 780, 'Esch', '0776', 19000101, 19960101),
   (780, 781, 'Etten-Leur', '0777', 19680101, NULL),
   (781, 782, 'Fijnaart en Heijningen', '0778', 19000101, 19970101),
   (782, 783, 'Geertruidenberg', '0779', 19000101, NULL),
   (783, 784, 'Geffen', '0780', 19000101, 19930101),
   (784, 785, 'Geldrop', '0781', 19000101, 20040101),
   (785, 786, 'Gemert', '0782', 19000101, 19970101),
   (786, 787, 'Giessen', '0783', 19000101, 19730101),
   (787, 788, 'Gilze en Rijen', '0784', 19000101, NULL),
   (788, 789, 'Goirle', '0785', 19000101, NULL),
   (789, 790, 'Grave', '0786', 19000101, NULL),
   (790, 791, '''s Gravenmoer', '0787', 19000101, 19970101),
   (791, 792, 'Haaren', '0788', 19000101, NULL),
   (792, 793, 'Halsteren', '0789', 19000101, 19970101),
   (793, 794, 'Haps', '0790', 19000101, 19940101),
   (794, 795, 'Heesch', '0791', 19000101, 19950128),
   (795, 796, 'Heeswijk-Dinther', '0792', 19690101, 19940101),
   (796, 797, 'Heeze', '0793', 19000101, 19970101),
   (797, 798, 'Helmond', '0794', 19000101, NULL),
   (798, 799, 'Helvoirt', '0795', 19000101, 19960101),
   (799, 800, '''s-Hertogenbosch', '0796', 19000101, NULL),
   (800, 801, 'Heusden', '0797', 19000101, NULL),
   (801, 802, 'Hilvarenbeek', '0798', 19000101, NULL),
   (802, 803, 'Hoeven', '0799', 19000101, 19970101),
   (803, 804, 'Hoogeloon, Hapert en Casteren', '0800', 19000101, 19970101),
   (804, 805, 'Hooge en Lage Mierde', '0801', 19000101, 19970101),
   (805, 806, 'Hooge en Lage Zwaluwe', '0802', 19000101, 19970101),
   (806, 807, 'Huijbergen', '0803', 19000101, 19970101),
   (807, 808, 'Klundert', '0804', 19000101, 19970101),
   (808, 809, 'Leende', '0805', 19000101, 19970101),
   (809, 810, 'Liempde', '0806', 19000101, 19960101),
   (810, 811, 'Lieshout', '0807', 19000101, 19970101),
   (811, 812, 'Lith', '0808', 19000101, 20110101),
   (812, 813, 'Loon op Zand', '0809', 19000101, NULL),
   (813, 814, 'Luyksgestel', '0810', 19000101, 19970101),
   (814, 815, 'Maarheeze', '0811', 19000101, 19970101),
   (815, 816, 'Made en Drimmelen', '0812', 19000101, 19970101),
   (816, 817, 'Megen, Haren en Macharen', '0813', 19000101, 19940101),
   (817, 818, 'Mierlo', '0814', 19000101, 20040101),
   (818, 819, 'Mill en Sint Hubert', '0815', 19000101, NULL),
   (819, 820, 'Moergestel', '0816', 19000101, 19970101),
   (820, 821, 'Nieuw-Ginneken', '0817', 19420101, 19970101),
   (821, 822, 'Nieuw-Vossemeer', '0818', 19000101, 19970101),
   (822, 823, 'Nistelrode', '0819', 19000101, 19940101),
   (823, 824, 'Nuenen, Gerwen en Nederwetten', '0820', 19000101, NULL),
   (824, 825, 'Nuland', '0821', 19000101, 19930101),
   (825, 826, 'Oeffelt', '0822', 19000101, 19940101),
   (826, 827, 'Oirschot', '0823', 19000101, NULL),
   (827, 828, 'Oisterwijk', '0824', 19000101, NULL),
   (828, 829, 'Oost-, West- en Middelbeers', '0825', 19000101, 19970101),
   (829, 830, 'Oosterhout', '0826', 19000101, NULL),
   (830, 831, 'Oploo, St. Anthonis en Ledeacker', '0827', 19000101, 19940101),
   (831, 832, 'Oss', '0828', 19000101, NULL),
   (832, 833, 'Ossendrecht', '0829', 19000101, 19970101),
   (833, 834, 'Oudenbosch', '0830', 19000101, 19970101),
   (834, 835, 'Oud en Nieuw Gastel', '0831', 19000101, 19970101),
   (835, 836, 'Prinsenbeek', '0832', 19510101, 19970101),
   (836, 837, 'Putte', '0833', 19000101, 19970101),
   (837, 838, 'Raamsdonk', '0834', 19000101, 19970101),
   (838, 839, 'Ravenstein', '0835', 19000101, 20030101),
   (839, 840, 'Reusel', '0836', 19000101, 19970101),
   (840, 841, 'Riethoven', '0837', 19000101, 19970101),
   (841, 842, 'Roosendaal en Nispen', '0838', 19000101, 19970101),
   (842, 843, 'Rosmalen', '0839', 19000101, 19960101),
   (843, 844, 'Rucphen', '0840', 19000101, NULL),
   (844, 845, 'Rijsbergen', '0841', 19000101, 19970101),
   (845, 846, 'Rijswijk (NB)', '0842', 19000101, 19730101),
   (846, 847, 'Schaijk', '0843', 19000101, 19940101),
   (847, 848, 'Schijndel', '0844', 19000101, 20170101),
   (848, 849, 'Sint-Michielsgestel', '0845', 19000101, NULL),
   (849, 850, 'Sint-Oedenrode', '0846', 19000101, 20170101),
   (850, 851, 'Someren', '0847', 19000101, NULL),
   (851, 852, 'Son en Breugel', '0848', 19000101, NULL),
   (852, 853, 'Sprang-Capelle', '0849', 19230101, 19970101),
   (853, 854, 'Standdaarbuiten', '0850', 19000101, 19970101),
   (854, 855, 'Steenbergen', '0851', 19000101, NULL),
   (855, 856, 'Waterland', '0852', 19910101, NULL),
   (856, 857, 'Terheijden', '0853', 19000101, 19970101),
   (857, 858, 'Teteringen', '0854', 19000101, 19970101),
   (858, 859, 'Tilburg', '0855', 19000101, NULL),
   (859, 860, 'Uden', '0856', 19000101, NULL),
   (860, 861, 'Udenhout', '0857', 19000101, 19970101),
   (861, 862, 'Valkenswaard', '0858', 19000101, NULL),
   (862, 863, 'Veen', '0859', 19000101, 19730101),
   (863, 864, 'Veghel', '0860', 19000101, 20170101),
   (864, 865, 'Veldhoven', '0861', 19210101, NULL),
   (865, 866, 'Vessem, Wintelre en Knegsel', '0862', 19000101, 19970101),
   (866, 867, 'Vierlingsbeek', '0863', 19000101, 19980101),
   (867, 868, 'Vlijmen', '0864', 19000101, 19970101),
   (868, 869, 'Vught', '0865', 19000101, NULL),
   (869, 870, 'Waalre', '0866', 19000101, NULL),
   (870, 871, 'Waalwijk', '0867', 19000101, NULL),
   (871, 872, 'Wanroij', '0868', 19000101, 19940101),
   (872, 873, 'Waspik', '0869', 19000101, 19970101),
   (873, 874, 'Werkendam', '0870', 19000101, NULL),
   (874, 875, 'Westerhoven', '0871', 19000101, 19970101),
   (875, 876, 'Willemstad', '0872', 19000101, 19970101),
   (876, 877, 'Woensdrecht', '0873', 19000101, NULL),
   (877, 878, 'Woudrichem', '0874', 19000101, NULL),
   (878, 879, 'Wouw', '0875', 19000101, 19970101),
   (879, 880, 'Wijk en Aalburg', '0876', 19000101, 19730101),
   (880, 881, 'Zeeland', '0877', 19000101, 19940101),
   (881, 882, 'Zevenbergen', '0878', 19000101, 19980401),
   (882, 883, 'Zundert', '0879', 19000101, NULL),
   (883, 884, 'Wormerland', '0880', 19910101, NULL),
   (884, 885, 'Onderbanken', '0881', 19820101, NULL),
   (885, 886, 'Landgraaf', '0882', 19820101, NULL),
   (886, 887, 'Amby', '0883', 19000101, 19700701),
   (887, 888, 'Amstenrade', '0884', 19000101, 19820101),
   (888, 889, 'Arcen en Velden', '0885', 19000101, 20100101),
   (889, 890, 'Baexem', '0886', 19000101, 19910101),
   (890, 891, 'Beegden', '0887', 19000101, 19910101),
   (891, 892, 'Beek', '0888', 19000101, NULL),
   (892, 893, 'Beesel', '0889', 19000101, NULL),
   (893, 894, 'Belfeld', '0890', 19000101, 20010101),
   (894, 895, 'Bemelen', '0891', 19000101, 19820101),
   (895, 896, 'Berg en Terblijt', '0892', 19000101, 19820101),
   (896, 897, 'Bergen (L)', '0893', 19000101, NULL),
   (897, 898, 'Bingelrade', '0894', 19000101, 19820101),
   (898, 899, 'Bocholtz', '0895', 19000101, 19820101),
   (899, 900, 'Borgharen', '0896', 19000101, 19700701),
   (900, 901, 'Born', '0897', 19000101, 20010101),
   (901, 902, 'Broekhuizen', '0898', 19000101, 20010101),
   (902, 903, 'Brunssum', '0899', 19000101, NULL),
   (903, 904, 'Bunde', '0900', 19000101, 19820101),
   (904, 905, 'Cadier en Keer', '0901', 19000101, 19820101),
   (905, 906, 'Echt', '0902', 19000101, 20030101),
   (906, 907, 'Elsloo', '0903', 19000101, 19820101),
   (907, 908, 'Eygelshoven', '0904', 19000101, 19820101),
   (908, 909, 'Eijsden', '0905', 19000101, 20110101),
   (909, 910, 'Geleen', '0906', 19000101, 20010101),
   (910, 911, 'Gennep', '0907', 19000101, NULL),
   (911, 912, 'Geulle', '0908', 19000101, 19820101),
   (912, 913, 'Grathem', '0909', 19000101, 19910101),
   (913, 914, 'Grevenbicht', '0910', 19000101, 19820101),
   (914, 915, 'Gronsveld', '0911', 19000101, 19820101),
   (915, 916, 'Grubbenvorst', '0912', 19000101, 20010101),
   (916, 917, 'Gulpen', '0913', 19000101, 19990101),
   (917, 918, 'Haelen', '0914', 19000101, 20070101),
   (918, 919, 'Heel en Panheel', '0915', 19000101, 19910101),
   (919, 920, 'Heer', '0916', 19000101, 19700701),
   (920, 921, 'Heerlen', '0917', 19000101, NULL),
   (921, 922, 'Helden', '0918', 19000101, 20100101),
   (922, 923, 'Herten', '0919', 19000101, 19910101),
   (923, 924, 'Heythuysen', '0920', 19000101, 20070101),
   (924, 925, 'Hoensbroek', '0921', 19000101, 19820101),
   (925, 926, 'Horn', '0922', 19000101, 19910101),
   (926, 927, 'Horst', '0923', 19000101, 20010101),
   (927, 928, 'Hulsberg', '0924', 19000101, 19820101),
   (928, 929, 'Hunsel', '0925', 19000101, 20070101),
   (929, 930, 'Itteren', '0926', 19000101, 19700701),
   (930, 931, 'Jabeek', '0927', 19000101, 19820101),
   (931, 932, 'Kerkrade', '0928', 19000101, NULL),
   (932, 933, 'Kessel', '0929', 19000101, 20100101),
   (933, 934, 'Klimmen', '0930', 19000101, 19820101),
   (934, 935, 'Limbricht', '0931', 19000101, 19820101),
   (935, 936, 'Linne', '0932', 19000101, 19910101),
   (936, 937, 'Maasbracht', '0933', 19000101, 20070101),
   (937, 938, 'Maasbree', '0934', 19000101, 20100101),
   (938, 939, 'Maastricht', '0935', 19000101, NULL),
   (939, 940, 'Margraten', '0936', 19000101, 20110101),
   (940, 941, 'Meerlo', '0937', 19000101, 19690701),
   (941, 942, 'Meerssen', '0938', 19000101, NULL),
   (942, 943, 'Melick en Herkenbosch', '0939', 19000101, 19930101),
   (943, 944, 'Merkelbeek', '0940', 19000101, 19820101),
   (944, 945, 'Meijel', '0941', 19000101, 20100101),
   (945, 946, 'Mheer', '0942', 19000101, 19820101),
   (946, 947, 'Montfort', '0943', 19000101, 19910101),
   (947, 948, 'Mook en Middelaar', '0944', 19000101, NULL),
   (948, 949, 'Munstergeleen', '0945', 19000101, 19820101),
   (949, 950, 'Nederweert', '0946', 19000101, NULL),
   (950, 951, 'Neer', '0947', 19000101, 19910101),
   (951, 952, 'Nieuwenhagen', '0948', 19000101, 19820101),
   (952, 953, 'Nieuwstadt', '0949', 19000101, 19820101),
   (953, 954, 'Noorbeek', '0950', 19000101, 19820101),
   (954, 955, 'Nuth', '0951', 19000101, NULL),
   (955, 956, 'Obbicht en Papenhoven', '0952', 19000101, 19820101),
   (956, 957, 'Ohé en Laak', '0953', 19000101, 19910101),
   (957, 958, 'Oirsbeek', '0954', 19000101, 19820101),
   (958, 959, 'Ottersum', '0955', 19000101, 19730101),
   (959, 960, 'Posterholt', '0956', 19000101, 19940201),
   (960, 961, 'Roermond', '0957', 19000101, NULL),
   (961, 962, 'Roggel', '0958', 19000101, 19930101),
   (962, 963, 'Roosteren', '0959', 19000101, 19820101),
   (963, 964, 'Schaesberg', '0960', 19000101, 19820101),
   (964, 965, 'Schimmert', '0961', 19000101, 19820101),
   (965, 966, 'Schinnen', '0962', 19000101, NULL),
   (966, 967, 'Schinveld', '0963', 19000101, 19820101),
   (967, 968, 'Sevenum', '0964', 19000101, 20100101),
   (968, 969, 'Simpelveld', '0965', 19000101, NULL),
   (969, 970, 'Sint Geertruid', '0966', 19000101, 19820101),
   (970, 971, 'Sint Odiliënberg', '0967', 19000101, 19910101),
   (971, 972, 'Sittard', '0968', 19000101, 20010101),
   (972, 973, 'Slenaken', '0969', 19000101, 19820101),
   (973, 974, 'Spaubeek', '0970', 19000101, 19820101),
   (974, 975, 'Stein', '0971', 19000101, NULL),
   (975, 976, 'Stevensweert', '0972', 19000101, 19910101),
   (976, 977, 'Stramproy', '0973', 19000101, 19980101),
   (977, 978, 'Susteren', '0974', 19000101, 20030101),
   (978, 979, 'Swalmen', '0975', 19000101, 20070101),
   (979, 980, 'Tegelen', '0976', 19000101, 20010101),
   (980, 981, 'Thorn', '0977', 19000101, 20070101),
   (981, 982, 'Ubach over Worms', '0978', 19000101, 19820101),
   (982, 983, 'Ulestraten', '0979', 19000101, 19820101),
   (983, 984, 'Urmond', '0980', 19000101, 19820101),
   (984, 985, 'Vaals', '0981', 19000101, NULL),
   (985, 986, 'Valkenburg-Houthem', '0982', 19401001, 19820101),
   (986, 987, 'Venlo', '0983', 19000101, NULL),
   (987, 988, 'Venray', '0984', 19000101, NULL),
   (988, 989, 'Vlodrop', '0985', 19000101, 19910101),
   (989, 990, 'Voerendaal', '0986', 19000101, NULL),
   (990, 991, 'Wanssum', '0987', 19000101, 19690701),
   (991, 992, 'Weert', '0988', 19000101, NULL),
   (992, 993, 'Wessem', '0989', 19000101, 19910101),
   (993, 994, 'Wittem', '0990', 19000101, 19990101),
   (994, 995, 'Wijlre', '0991', 19000101, 19820101),
   (995, 996, 'Wijnandsrade', '0992', 19000101, 19820101),
   (996, 997, 'Meerlo-Wanssum', '0993', 19690701, 20100101),
   (997, 998, 'Valkenburg aan de Geul', '0994', 19820101, NULL),
   (998, 999, 'Lelystad', '0995', 19800101, NULL),
   (999, 1000, 'Zuidelijke IJsselmeerpolders', '0996', 19551130, 19960101),
   (1000, 1001, 'Centraal Persoonsregister (Niet GBA)', '0997', 19000101, 19901001),
   (1001, 1002, 'Buitenland (Niet GBA)', '0998', 19000101, 19901001),
   (1002, 1003, 'Onbekend (Niet GBA)', '0999', 19000101, 19901001),
   (1003, 1004, 'Aagtekerke', '1000', 19000101, 19660701),
   (1004, 1005, 'Aalst', '1001', 19000101, 19230101),
   (1005, 1006, 'Aarlanderveen', '1002', 19000101, 19180101),
   (1006, 1007, 'Abcoude-Baambrugge', '1003', 19000101, 19410501),
   (1007, 1008, 'Abcoude-Proostdij', '1004', 19000101, 19410501),
   (1008, 1009, 'Achttienhoven', '1005', 19000101, 19540101),
   (1009, 1010, 'Aengwirden', '1006', 19000101, 19340701),
   (1010, 1011, 'Alem, Maren en Kessel', '1007', 19000101, 19580101),
   (1011, 1012, 'Alphen', '1008', 19000101, 19180101),
   (1012, 1013, 'Ambt-Almelo', '1009', 19000101, 19140101),
   (1013, 1014, 'Ambt-Doetinchem', '1010', 19000101, 19200101),
   (1014, 1015, 'Ambt-Hardenberg', '1011', 19000101, 19410501),
   (1015, 1016, 'Ambt-Ommen', '1012', 19000101, 19230501),
   (1016, 1017, 'Ambt-Vollenhove', '1013', 19000101, 19420201),
   (1017, 1018, 'Ankeveen', '1014', 19000101, 19660801),
   (1018, 1019, 'Baardwijk', '1015', 19000101, 19220101),
   (1019, 1020, 'Balgoij', '1016', 19000101, 19230501),
   (1020, 1021, 'Barwoutswaarder', '1017', 19000101, 19640201),
   (1021, 1022, 'Bath', '1018', 19000101, 18780101),
   (1022, 1023, 'Bellingwolde', '1019', 19000101, 19680901),
   (1023, 1024, 'Besoijen', '1020', 19000101, 19290101),
   (1024, 1025, 'Beugen en Rijkevoort', '1021', 19000101, 19420501),
   (1025, 1026, 'Biggekerke', '1022', 19000101, 19660701),
   (1026, 1027, 'Bokhoven', '1023', 19000101, 19220101),
   (1027, 1028, 'Den Bommel', '1024', 19000101, 19660101),
   (1028, 1029, 'Bommenede', '1025', 19000101, 18650101),
   (1029, 1030, 'Borkel en Schaft', '1026', 19000101, 19340501),
   (1030, 1031, 'Boschkapelle', '1027', 19000101, 19360701),
   (1031, 1032, 'Breukelen-Nijenrode', '1028', 19000101, 19490101),
   (1032, 1033, 'Breukelen-Sint Pieters', '1029', 19000101, 19490101),
   (1033, 1034, 'Broek', '1030', 19000101, 18700701),
   (1034, 1035, 'Broek op Langendijk', '1031', 19000101, 19410801),
   (1035, 1036, 'Broeksittard', '1032', 19000101, 19421001),
   (1036, 1037, 'Buggenum', '1033', 19000101, 19421001),
   (1037, 1038, 'Buiksloot', '1034', 19000101, 19210101),
   (1038, 1039, 'Burgh', '1035', 19000101, 19610101),
   (1039, 1040, 'Capelle', '1036', 19000101, 19230101),
   (1040, 1041, 'Charlois', '1037', 19000101, 18950228),
   (1041, 1042, 'Colijnsplaat', '1038', 19000101, 19410401),
   (1042, 1043, 'Cromvoirt', '1039', 19000101, 19330101),
   (1043, 1044, 'Delfshaven', '1040', 19000101, 18860114),
   (1044, 1045, 'Deurne en Liessel', '1041', 19000101, 19260101),
   (1045, 1046, 'Deursen en Dennenburg', '1042', 19000101, 19230501),
   (1046, 1047, 'Dieden, Demen en Langel', '1043', 19000101, 19230501),
   (1047, 1048, 'Dinther', '1044', 19000101, 19690101),
   (1048, 1049, 'Dommelen', '1045', 19000101, 19340501),
   (1049, 1050, 'Doorwerth', '1046', 19000101, 19230501),
   (1050, 1051, 'Dreischor', '1047', 19000101, 19610101),
   (1051, 1052, 'Driebergen', '1048', 19000101, 19310501),
   (1052, 1053, 'Drongelen', '1049', 19000101, 19230501),
   (1053, 1054, 'Duivendijke', '1050', 19000101, 19610101),
   (1054, 1055, 'Duizel en Steensel', '1051', 19000101, 19230101),
   (1055, 1056, 'Eede', '1052', 19000101, 19410401),
   (1056, 1057, 'Elkerzee', '1053', 19000101, 19610101),
   (1057, 1058, 'Ellemeet', '1054', 19000101, 19610101),
   (1058, 1059, 'Elten', '1055', 19490423, 19630801),
   (1059, 1060, 'Emmikhoven en Waardhuizen', '1056', 19000101, 18790715),
   (1060, 1061, 'Escharen', '1057', 19000101, 19420701),
   (1061, 1062, 'Gameren', '1058', 19000101, 19550701),
   (1062, 1063, 'Gassel', '1059', 19000101, 19420801),
   (1063, 1064, 'Genderen', '1060', 19080801, 19230501),
   (1064, 1065, 'Gestel en Blaarthem', '1061', 19000101, 19200101),
   (1065, 1066, 'Giessen-Nieuwkerk', '1062', 19000101, 19570101),
   (1066, 1067, 'Giessendam', '1063', 19000101, 19570101),
   (1067, 1068, 'Ginneken en Bavel', '1064', 19000101, 19420101),
   (1068, 1069, 'Grafhorst', '1065', 19000101, 19370101),
   (1069, 1070, 'Groote Lindt', '1066', 19000101, 18810906),
   (1070, 1071, 'Grijpskerke', '1067', 19000101, 19660701),
   (1071, 1072, 'Haamstede', '1068', 19000101, 19610101),
   (1072, 1073, 'Haarzuilens', '1069', 19000101, 19540101),
   (1073, 1074, 'Hardinxveld', '1070', 19000101, 19570101),
   (1074, 1075, 'Hedikhuizen', '1071', 19000101, 19350501),
   (1075, 1076, 'Heeswijk', '1072', 19000101, 19690101),
   (1076, 1077, 'Heille', '1073', 19000101, 18800423),
   (1077, 1078, 'Hekelingen', '1074', 19000101, 19660501),
   (1078, 1079, 'Hekendorp', '1075', 19000101, 19640201),
   (1079, 1080, 'Hemmen', '1076', 19000101, 19550701),
   (1080, 1081, 'Hengstdijk', '1077', 19000101, 19360701),
   (1081, 1082, 'Herkingen', '1078', 19000101, 19660101),
   (1082, 1083, 'Herpen', '1079', 19000101, 19410401),
   (1083, 1084, 'Herpt', '1080', 19000101, 19350501),
   (1084, 1085, 'Hillegersberg', '1081', 19000101, 19410801),
   (1085, 1086, 'Hof van Delft', '1082', 19000101, 19210101),
   (1086, 1087, 'Hoogezand', '1083', 19000101, 19490401),
   (1087, 1088, 'Hoogkerk', '1084', 19000101, 19690101),
   (1088, 1089, 'Hoogvliet', '1085', 19000101, 19340501),
   (1089, 1090, 'Houthem', '1086', 19000101, 19401001),
   (1090, 1091, 'Houtrijk en Polanen', '1087', 19000101, 18630922),
   (1091, 1092, 'Huisseling en Neerloon', '1088', 19000101, 19230501),
   (1092, 1093, 'Hurwenen', '1089', 19000101, 19550701),
   (1093, 1094, 'Ittervoort', '1090', 19000101, 19420701),
   (1094, 1095, 'Jaarsveld', '1091', 19000101, 19430101),
   (1095, 1096, 'Kamperveen', '1092', 19000101, 19370101),
   (1096, 1097, 'Katendrecht', '1093', 19000101, 18740101),
   (1097, 1098, 'Kats', '1094', 19000101, 19410401),
   (1098, 1099, 'Kerkwerve', '1095', 19000101, 19610101),
   (1099, 1100, 'Kethel en Spaland', '1096', 19000101, 19410801),
   (1100, 1101, 'Kortenhoef', '1097', 19000101, 19660801),
   (1101, 1102, 'Koudekerke', '1098', 19000101, 19660701),
   (1102, 1103, 'Kralingen', '1099', 19000101, 18950228),
   (1103, 1104, 'Laag-Nieuwkoop', '1100', 19000101, 19420501),
   (1104, 1105, 'Lange Ruige Weide', '1101', 19000101, 19640201),
   (1105, 1106, 'Lierop', '1102', 19000101, 19350501),
   (1106, 1107, 'Linden', '1103', 19000101, 19420801),
   (1107, 1108, 'Lithoijen', '1104', 19000101, 19390101),
   (1108, 1109, 'Loenersloot', '1105', 19000101, 19640401),
   (1109, 1110, 'Lonneker', '1106', 19000101, 19340501),
   (1110, 1111, 'Loosduinen', '1107', 19000101, 19230701),
   (1111, 1112, 'Maarsseveen', '1108', 19000101, 19490701),
   (1112, 1113, 'Maashees en Overloon', '1109', 19000101, 19420501),
   (1113, 1114, 'Maasniel', '1110', 19000101, 19590801),
   (1114, 1115, 'Meeuwen', '1111', 19000101, 19230501),
   (1115, 1116, 'Meliskerke', '1112', 19000101, 19660701),
   (1116, 1117, 'Melissant', '1113', 19000101, 19660101),
   (1117, 1118, 'Mesch', '1114', 19000101, 19430101),
   (1118, 1119, 'Nederhemert', '1115', 19000101, 19550701),
   (1119, 1120, 'Neeritter', '1116', 19000101, 19420701),
   (1120, 1121, 'Nieuw- en Sint Joosland', '1117', 19000101, 19660701),
   (1121, 1122, 'Nieuw-Helvoet', '1118', 19000101, 19600101),
   (1122, 1123, 'Nieuwe Tonge', '1119', 19000101, 19660101),
   (1123, 1124, 'Nieuwendam', '1120', 19000101, 19210101),
   (1124, 1125, 'Nieuwenhoorn', '1121', 19000101, 19600101),
   (1125, 1126, 'Nieuwerkerk', '1122', 19000101, 19610101),
   (1126, 1127, 'Nieuwkuijk', '1123', 19000101, 19350501),
   (1127, 1128, 'Noord-Scharwoude', '1124', 19000101, 19410801),
   (1128, 1129, 'Noord-Waddinxveen', '1125', 19000101, 18700701),
   (1129, 1130, 'Noordbroek', '1126', 19000101, 19650701),
   (1130, 1131, 'Noorddijk', '1127', 19000101, 19690101),
   (1131, 1132, 'Noordgouwe', '1128', 19000101, 19610101),
   (1132, 1133, 'Noordwelle', '1129', 19000101, 19610101),
   (1133, 1134, 'Nunhem', '1130', 19000101, 19421001),
   (1134, 1135, 'Odijk', '1131', 19000101, 19640901),
   (1135, 1136, 'Oerle', '1132', 19000101, 19210501),
   (1136, 1137, 'Ooltgensplaat', '1133', 19000101, 19660101),
   (1137, 1138, 'Oost- en West-Barendrecht', '1134', 18360101, 18860101),
   (1138, 1139, 'Oost- en West-Souburg', '1135', 19000101, 19660701),
   (1139, 1140, 'Oosterland', '1136', 19000101, 19610101),
   (1140, 1141, 'Oostkapelle', '1137', 19000101, 19660701),
   (1141, 1142, 'Ossenisse', '1138', 19000101, 19360701),
   (1142, 1143, 'Oud- en Nieuw-Mathenesse', '1139', 19000101, 18680101),
   (1143, 1144, 'Oud-Valkenburg', '1140', 19000101, 19401001),
   (1144, 1145, 'Oud-Vroenhoven', '1141', 19000101, 19200101),
   (1145, 1146, 'Ouddorp', '1142', 19000101, 19660101),
   (1146, 1147, 'Oude-Tonge', '1143', 19000101, 19660101),
   (1147, 1148, 'Oudenrijn', '1144', 19000101, 19540101),
   (1148, 1149, 'Oudheusden', '1145', 19000101, 19350501),
   (1149, 1150, 'Oudkarspel', '1146', 19000101, 19410801),
   (1150, 1151, 'Oudshoorn', '1147', 19000101, 19180101),
   (1151, 1152, 'Ouwerkerk', '1148', 19000101, 19610101),
   (1152, 1153, 'Overschie', '1149', 19000101, 19410801),
   (1153, 1154, 'Oijen en Teeffelen', '1150', 19000101, 19390101),
   (1154, 1155, 'Papekop', '1151', 19000101, 19640201),
   (1155, 1156, 'Pernis', '1152', 19000101, 19340501),
   (1156, 1157, 'Petten', '1153', 19000101, 19290501),
   (1157, 1158, 'Peursum', '1154', 19000101, 19570101),
   (1158, 1159, 'Poederoijen', '1155', 19000101, 19550701),
   (1159, 1160, 'Princenhage', '1156', 19000101, 19420101),
   (1160, 1161, 'Ransdorp', '1157', 19000101, 19210101),
   (1161, 1162, 'Reek', '1158', 19000101, 19420701),
   (1162, 1163, 'Renesse', '1159', 19000101, 19610101),
   (1163, 1164, 'Rietveld', '1160', 19000101, 19640201),
   (1164, 1165, 'Rilland', '1161', 19000101, 18780101),
   (1165, 1166, 'Rimburg', '1162', 19000101, 18870315),
   (1166, 1167, 'Ritthem', '1163', 19000101, 19660701),
   (1167, 1168, 'Ruwiel', '1164', 19000101, 19640401),
   (1168, 1169, 'Rijckholt', '1165', 19000101, 19430101),
   (1169, 1170, 'Rijsenburg', '1166', 19000101, 19310501),
   (1170, 1171, 'Sambeek', '1167', 19000101, 19420501),
   (1171, 1172, 'Sappemeer', '1168', 19000101, 19490401),
   (1172, 1173, 'Schalkwijk', '1169', 19000101, 19620101),
   (1173, 1174, 'Schiebroek', '1170', 19000101, 19410801),
   (1174, 1175, 'Schin op Geul', '1171', 19000101, 19401001),
   (1175, 1176, 'Schore', '1172', 19000101, 19410101),
   (1176, 1177, 'Schoten', '1173', 19000101, 19270501),
   (1177, 1178, 'Schoterland', '1174', 19000101, 19340701),
   (1178, 1179, 'Serooskerke (Schouwen)', '1175', 19000101, 19610101),
   (1179, 1180, 'Serooskerke (Walcheren)', '1176', 19000101, 19660701),
   (1180, 1181, 'St. Anna ter Muiden', '1177', 19000101, 18800423),
   (1181, 1182, 'Sint Kruis', '1178', 19000101, 19410401),
   (1182, 1183, 'Sint Laurens', '1179', 19000101, 19660701),
   (1183, 1184, 'Sint Pieter', '1180', 19000101, 19200101),
   (1184, 1185, 'Sloten (NH)', '1181', 19000101, 19210101),
   (1185, 1186, 'Sluipwijk', '1182', 19000101, 18700701),
   (1186, 1187, 'Soerendonk, Sterksel en Gastel', '1183', 19000101, 19250101),
   (1187, 1188, 'Sommelsdijk', '1184', 19000101, 19660101),
   (1188, 1189, 'Spaarndam', '1185', 19000101, 19270501),
   (1189, 1190, 'Spanbroek', '1186', 19000101, 19590701),
   (1190, 1191, 'Sprang', '1187', 19000101, 19230101),
   (1191, 1192, 'Stad aan ''t Haringvliet', '1188', 19000101, 19660101),
   (1192, 1193, 'Stad-Almelo', '1189', 19000101, 19140101),
   (1193, 1194, 'Stad-Doetinchem', '1190', 19000101, 19200101),
   (1194, 1195, 'Stad-Hardenberg', '1191', 19000101, 19410501),
   (1195, 1196, 'Stad-Ommen', '1192', 19000101, 19230501),
   (1196, 1197, 'Stad-Vollenhove', '1193', 19000101, 19420201),
   (1197, 1198, 'Stein (ZH)', '1194', 19000101, 18700701),
   (1198, 1199, 'Stellendam', '1195', 19000101, 19660101),
   (1199, 1200, 'Stiphout', '1196', 19000101, 19680101),
   (1200, 1201, 'Stompwijk', '1197', 19000101, 19380101),
   (1201, 1202, 'Stoppeldijk', '1198', 19000101, 19360701),
   (1202, 1203, 'Stratum', '1199', 19000101, 19200101),
   (1203, 1204, 'Strucht', '1200', 19000101, 18790102),
   (1204, 1205, 'Strijp', '1201', 19000101, 19200101),
   (1205, 1206, 'Tienhoven (U)', '1202', 19000101, 19570701),
   (1206, 1207, 'Tongelre', '1203', 19000101, 19200101),
   (1207, 1208, 'Tudderen', '1204', 19490422, 19630801),
   (1208, 1209, 'Tull en ''t Waal', '1205', 19000101, 19620101),
   (1209, 1210, 'Veldhoven en Meerveldhoven', '1206', 19000101, 19210501),
   (1210, 1211, 'Veldhuizen', '1207', 19000101, 19540101),
   (1211, 1212, 'Velp', '1208', 19000101, 19420701),
   (1212, 1213, 'Veur', '1209', 19000101, 19380101),
   (1213, 1214, 'Vlaardinger-Ambacht', '1210', 19000101, 19410801),
   (1214, 1215, 'Vleuten', '1211', 19000101, 19540101),
   (1215, 1216, 'Vlierden', '1212', 19000101, 19260101),
   (1216, 1217, 'Vreeland', '1213', 19000101, 19640401),
   (1217, 1218, 'Vrouwenpolder', '1214', 19000101, 19660701),
   (1218, 1219, 'Vrijenban', '1215', 19000101, 19210101),
   (1219, 1220, 'Vrijhoeve-Capelle', '1216', 19000101, 19230101),
   (1220, 1221, 'Waarder', '1217', 19000101, 19640201),
   (1221, 1222, 'Wadenoijen', '1218', 19000101, 19560701),
   (1222, 1223, 'Watergraafsmeer', '1219', 19000101, 19210101),
   (1223, 1224, 'Wedde', '1220', 19000101, 19680901),
   (1224, 1225, 'Weesperkarspel', '1221', 19000101, 19660801),
   (1225, 1226, 'de Werken en Sleeuwijk', '1222', 19000101, 19501001),
   (1226, 1227, 'Werkhoven', '1223', 19000101, 19640901),
   (1227, 1228, 'Westbroek', '1224', 19000101, 19570701),
   (1228, 1229, 'Wildervank', '1225', 19000101, 19690101),
   (1229, 1230, 'Willige-Langerak', '1226', 19000101, 19430101),
   (1230, 1231, 'Wilsum', '1227', 19000101, 19370101),
   (1231, 1232, 'Woensel', '1228', 19000101, 19200101),
   (1232, 1233, 'Wijk aan Zee en Duin', '1229', 19000101, 19360501),
   (1233, 1234, 'IJsselmonde', '1230', 19000101, 19410801),
   (1234, 1235, 'IJzendoorn', '1231', 19000101, 19230501),
   (1235, 1236, 'Zalk en Veecaten', '1232', 19000101, 19370101),
   (1236, 1237, 'Zeelst', '1233', 19000101, 19210501),
   (1237, 1238, 'Zegwaart', '1234', 19000101, 19350501),
   (1238, 1239, 'Zesgehuchten', '1235', 19000101, 19210501),
   (1239, 1240, 'Zonnemaire', '1236', 19000101, 19610101),
   (1240, 1241, 'Zoutelande', '1237', 19000101, 19660701),
   (1241, 1242, 'Zuid-Scharwoude', '1238', 19000101, 19410801),
   (1242, 1243, 'Zuid-Waddinxveen', '1239', 19000101, 18700701),
   (1243, 1244, 'Zuidbroek', '1240', 19000101, 19650701),
   (1244, 1245, 'Zuidschalkwijk', '1241', 19000101, 18630922),
   (1245, 1246, 'Zuilen', '1242', 19000101, 19540101),
   (1246, 1247, 'Zuilichem', '1243', 19000101, 19550701),
   (1247, 1248, 'Zwammerdam', '1244', 19000101, 19640201),
   (1248, 1249, 'Zwollerkerspel', '1245', 19000101, 19670801),
   (1249, 1250, 'Bangert', '1246', 19790101, 19800101),
   (1250, 1251, 'Beek (NB)', '1247', 19420101, 19510101),
   (1251, 1252, 'Driel', '1248', 19000101, 19440801),
   (1252, 1253, 'Nieuwer-Amstel', '1249', 19000101, 19640101),
   (1253, 1254, 'Onstwedde', '1250', 19000101, 19690101),
   (1254, 1255, 'Etten en Leur', '1251', 19000101, 19680101),
   (1255, 1256, 'Valkenburg (L)', '1252', 19000101, 19410715),
   (1256, 1257, 'Wissekerke', '1253', 19000101, 19580801),
   (1257, 1258, 'Borssele', '1254', 19000101, 19700101),
   (1258, 1259, 'Eethen, Genderen en Heesbeen', '1255', 19000101, 19080801),
   (1259, 1260, 'Koudekerk', '1256', 19000101, 19380101),
   (1260, 1261, 'Staveren', '1257', 19390101, 19790101),
   (1261, 1262, 'Rijsoort en Strevelshoek', '1258', 18460101, 18550901),
   (1262, 1263, 'Millingen', '1259', 19000101, 19550101),
   (1263, 1264, 'Hemelumer Oldephaert en Noordwolde', '1260', 19000101, 19560101),
   (1264, 1265, 'Simonshaven', '1261', 19000101, 18550901),
   (1265, 1266, 'Bleskensgraaf', '1262', 19000101, 18550901),
   (1266, 1267, 'Drongelen, Hangoort,Gansoyen en Doeveren', '1263', 19000101, 19080801),
   (1267, 1268, 'Dussen, Munster en Muilkerk', '1264', 19000101, 19080801),
   (1268, 1269, 'Meeuwen, Hill en Babyloniënbroek', '1265', 19000101, 19080801),
   (1269, 1270, 'Abtsregt', '1301', 19000101, 18550901),
   (1270, 1271, 'Achthoven', '1302', 19000101, 18570908),
   (1271, 1272, 'Achttienhoven (bij Nieuwkoop)', '1303', 19000101, 18550901),
   (1272, 1273, 'Ackersdijk en Vrouwenregt', '1304', 19000101, 18550901),
   (1273, 1274, 'Benthorn', '1305', 19000101, 18460101),
   (1274, 1275, 'Berkenrode', '1306', 19000101, 18580101),
   (1275, 1276, 'Biert', '1307', 19000101, 18550901),
   (1276, 1277, 'Biesland', '1308', 19000101, 18330101),
   (1277, 1278, 'Bijlmermeer', '1309', 19000101, 18460101),
   (1278, 1279, 'Cabauw', '1310', 19000101, 18570908),
   (1279, 1280, 'Cillaarshoek', '1311', 19000101, 18320101),
   (1280, 1281, 'Darthuizen', '1312', 19000101, 18570908),
   (1281, 1282, 'Dorth', '1313', 19000101, 18310701),
   (1282, 1283, 'Duist', '1314', 19000101, 18570908),
   (1283, 1284, 'Edam', '1315', 19000101, 19750101),
   (1284, 1285, 'Etersheim', '1316', 19000101, 18480101),
   (1285, 1286, 'Gapinge', '1317', 19000101, 18571002),
   (1286, 1287, 'Gerverscop', '1318', 19000101, 18570908),
   (1287, 1288, 'Goidschalxoord', '1319', 19000101, 18550901),
   (1288, 1289, '''s-Gravenambacht', '1320', 19000101, 18340101),
   (1289, 1290, '''s-Gravesloot', '1321', 19000101, 18570908),
   (1290, 1291, 'Groeneveld', '1322', 19000101, 18550901),
   (1291, 1292, 'Groet', '1323', 19000101, 18340101),
   (1292, 1293, 'Grosthuizen', '1324', 19000101, 18540531),
   (1293, 1294, 'Haarlemmerliede', '1325', 19000101, 18570908),
   (1294, 1295, '''s Heer Hendriks Kinderen', '1326', 19000101, 18570801),
   (1295, 1296, 'Heer Oudelands Ambacht', '1327', 19000101, 18570819),
   (1296, 1297, 'Hodenpijl', '1328', 19000101, 18550901),
   (1297, 1298, 'Hofwegen', '1329', 19000101, 18550901),
   (1298, 1299, 'Hoog en Woud Harnasch', '1330', 19000101, 18330101),
   (1299, 1300, 'Hoogeveen in Delfland', '1331', 19000101, 18320101),
   (1300, 1301, 'Hoogeveen in Rijnland', '1332', 19000101, 18550901),
   (1301, 1302, 'Hoogmade', '1333', 19000101, 18550917),
   (1302, 1303, 'Kalslagen', '1334', 19000101, 18540531),
   (1303, 1304, 'Kamerik Houtdijken', '1335', 19000101, 18570908),
   (1304, 1305, 'Kamerik Mijzijde', '1336', 19000101, 18570908),
   (1305, 1306, 'Kijfhoek', '1337', 19000101, 18570819),
   (1306, 1307, 'Kleine Lindt', '1338', 19000101, 18570613),
   (1307, 1308, 'Kleverskerke', '1339', 19000101, 18571002),
   (1308, 1309, 'Laagblokland', '1340', 19000101, 18580101),
   (1309, 1310, 'Loenen en Wolveren', '1341', 19000101, 18540420),
   (1310, 1311, 'Maarssenbroek', '1342', 19000101, 18570908),
   (1311, 1312, 'Meerdervoort', '1343', 19000101, 18550901),
   (1312, 1313, 'Middelburg (ZH)', '1344', 19000101, 18550901),
   (1313, 1314, 'De Mijl', '1345', 19000101, 18570131),
   (1314, 1315, 'Naters', '1346', 19000101, 18560101),
   (1315, 1316, 'Nederslingelandt', '1347', 19000101, 18570819),
   (1316, 1317, 'Nieuwland, Kortland en ''s-Graveland', '1348', 19000101, 18560101),
   (1317, 1318, 'Nieuwveen in Delfland', '1349', 19000101, 18320101),
   (1318, 1319, 'Noord-Polsbroek', '1350', 19000101, 18570908),
   (1319, 1320, 'Onwaard', '1351', 19000101, 18580101),
   (1320, 1321, 'Oost-Barendrecht', '1352', 19000101, 18360101),
   (1321, 1322, 'Oost-Souburg', '1353', 19000101, 18340101),
   (1322, 1323, 'Oud-Wulven', '1355', 19000101, 18580101),
   (1323, 1324, 'Oude en Nieuwe Struiten', '1356', 19000101, 18550901),
   (1324, 1325, 'Oudhuizen', '1357', 19000101, 18580101),
   (1325, 1326, 'Oukoop', '1358', 19000101, 18570819),
   (1326, 1327, 'Portengen', '1359', 19000101, 18590101),
   (1327, 1328, 'Rhijnauwen', '1360', 19000101, 18570908),
   (1328, 1329, 'Rietwijkeroord', '1361', 19000101, 18540623),
   (1329, 1330, 'Rijsoort', '1362', 19000101, 18550101),
   (1330, 1331, 'Roxenisse', '1363', 19000101, 18580101),
   (1331, 1332, 'Ruijven', '1364', 19000101, 18460101),
   (1332, 1333, 'Sandelingen-Ambacht', '1365', 19000101, 18550901),
   (1333, 1334, 'Schardam', '1366', 19000101, 18540531),
   (1334, 1335, 'Scharwoude', '1367', 19000101, 18540531),
   (1335, 1336, 'Schellingwoude', '1368', 19000101, 18570919),
   (1336, 1337, 'Schokland', '1369', 19000101, 18580901),
   (1337, 1338, 'Schonauwen', '1370', 19000101, 18580101),
   (1338, 1339, 'Schuddebeurs en Simonshaven', '1371', 19000101, 18550711),
   (1339, 1340, 'Sint Anthonypolder', '1372', 19000101, 18320101),
   (1340, 1341, 'St. Maartensregt', '1373', 19000101, 18550901),
   (1341, 1342, 'Spaarnwoude', '1374', 19000101, 18570908),
   (1342, 1343, 'Spijk', '1375', 19000101, 18550901),
   (1343, 1344, 'Steenbergen en Kruisland', '1376', 19000101, 19620625),
   (1344, 1345, 'Sterkenburg', '1377', 19000101, 18570908),
   (1345, 1346, 'Stormpolder', '1378', 19000101, 18550901),
   (1346, 1347, 'Strevelshoek', '1380', 19000101, 18460101),
   (1347, 1348, 'Strijensas', '1381', 19000101, 18550901),
   (1348, 1349, 'Teckop', '1382', 19000101, 18570908),
   (1349, 1350, 'Tempel', '1383', 19000101, 18580711),
   (1350, 1351, 'Veenhuizen', '1384', 19000101, 18540413),
   (1351, 1352, 'De Vennip', '1385', 19000101, 18550815),
   (1352, 1353, 'Verwolde', '1386', 19000101, 18540522),
   (1353, 1354, 'Vinkeveen', '1387', 19000101, 18410101),
   (1354, 1355, 'Vliet', '1388', 19000101, 18460101),
   (1355, 1356, 'Vrije en Lage Boekhorst', '1390', 19000101, 18550815),
   (1356, 1357, 'Vrijhoeven', '1391', 19000101, 18400710),
   (1357, 1358, 'de Vuursche', '1392', 19000101, 18571201),
   (1358, 1359, 'Waverveen', '1393', 19000101, 18410101),
   (1359, 1360, 'West-Barendrecht', '1394', 19000101, 18360101),
   (1360, 1361, 'West-Souburg', '1395', 19000101, 18340101),
   (1361, 1362, 'Wieldrecht', '1396', 19000101, 18570131),
   (1362, 1363, 'Wimmenum', '1397', 19000101, 18570713),
   (1363, 1364, 'Wulverhorst', '1398', 19000101, 18570908),
   (1364, 1365, 'Zevender', '1399', 19000101, 18570908),
   (1365, 1366, 'Zouteveen', '1400', 19000101, 18550901),
   (1366, 1367, 'Zuid-Polsbroek', '1401', 19000101, 18570908),
   (1367, 1368, 'Zuidbroek (ZH)', '1402', 19000101, 18570613),
   (1368, 1369, 'Zuidwijk', '1403', 19000101, 18460101),
   (1369, 1370, 'de Noordoostelijke Polder', '1404', 19000101, 19620701),
   (1370, 1371, 'Horst aan de Maas', '1507', 20010101, NULL),
   (1371, 1372, 'Oude IJsselstreek', '1509', 20050101, NULL),
   (1372, 1373, 'Teylingen', '1525', 20060101, NULL),
   (1373, 1374, 'Utrechtse Heuvelrug', '1581', 20060101, NULL),
   (1374, 1375, 'Oost Gelre', '1586', 20060519, NULL),
   (1375, 1376, 'Koggenland', '1598', 20070101, NULL),
   (1376, 1377, 'Lansingerland', '1621', 20070101, NULL),
   (1377, 1378, 'Leudal', '1640', 20070101, NULL),
   (1378, 1379, 'Maasgouw', '1641', 20070101, NULL),
   (1379, 1380, 'Eemsmond', '1651', 19920101, NULL),
   (1380, 1381, 'Gemert-Bakel', '1652', 19970101, NULL),
   (1381, 1382, 'Halderberge', '1655', 19970101, NULL),
   (1382, 1383, 'Heeze-Leende', '1658', 19970101, NULL),
   (1383, 1384, 'Laarbeek', '1659', 19970101, NULL),
   (1384, 1385, 'Reiderland', '1661', 19910701, 20100101),
   (1385, 1386, 'De Marne', '1663', 19920101, NULL),
   (1386, 1387, 'Made', '1665', 19970101, 19980425),
   (1387, 1388, 'Zevenhuizen-Moerkapelle', '1666', 19920201, 20100101),
   (1388, 1389, 'Reusel-De Mierden', '1667', 19970101, NULL),
   (1389, 1390, 'Roerdalen', '1669', 19930101, NULL),
   (1390, 1391, 'Roggel en Neer', '1670', 19930101, 20070101),
   (1391, 1392, 'Maasdonk', '1671', 19930101, 20150101),
   (1392, 1393, 'Rijnwoude', '1672', 19930101, 20140101),
   (1393, 1394, 'Liemeer', '1673', 19940101, 20070101),
   (1394, 1395, 'Roosendaal', '1674', 19970101, NULL),
   (1395, 1396, 'Schouwen-Duiveland', '1676', 19970101, NULL),
   (1396, 1397, 'Ambt Montfort', '1679', 19940201, 20070101),
   (1397, 1398, 'Aa en Hunze', '1680', 19980101, NULL),
   (1398, 1399, 'Borger-Odoorn', '1681', 19980101, NULL),
   (1399, 1400, 'Cuijk', '1684', 19940101, NULL),
   (1400, 1401, 'Landerd', '1685', 19940101, NULL),
   (1401, 1402, 'De Wolden', '1690', 19980101, NULL),
   (1402, 1403, 'St. Anthonis', '1691', 19940101, 19950704),
   (1403, 1404, 'Noord-Beveland', '1695', 19950101, NULL),
   (1404, 1405, 'Wijdemeren', '1696', 20020101, NULL),
   (1405, 1406, 'Middenveld', '1697', 19980101, 20000101),
   (1406, 1407, 'Sluis-Aardenburg', '1698', 19950101, 20030101),
   (1407, 1408, 'Noordenveld', '1699', 19980101, NULL),
   (1408, 1409, 'Twenterand', '1700', 20020601, NULL),
   (1409, 1410, 'Westerveld', '1701', 19980101, NULL),
   (1410, 1411, 'Sint Anthonis', '1702', 19950704, NULL),
   (1411, 1412, 'Lingewaard', '1705', 20030101, NULL),
   (1412, 1413, 'Cranendonck', '1706', 19980128, NULL),
   (1413, 1414, 'Steenwijkerland', '1708', 20030101, NULL),
   (1414, 1415, 'Moerdijk', '1709', 19980401, NULL),
   (1415, 1416, 'Echt-Susteren', '1711', 20030101, NULL),
   (1416, 1417, 'Sluis', '1714', 20030101, NULL),
   (1417, 1418, 'Drimmelen', '1719', 19980425, NULL),
   (1418, 1419, 'Bernheze', '1721', 19950128, NULL),
   (1419, 1420, 'Ferwerderadiel', '1722', 19990101, NULL),
   (1420, 1421, 'Alphen-Chaam', '1723', 19970101, NULL),
   (1421, 1422, 'Bergeijk', '1724', 19990101, NULL),
   (1422, 1423, 'Bladel', '1728', 19970101, NULL),
   (1423, 1424, 'Gulpen-Wittem', '1729', 19990101, NULL),
   (1424, 1425, 'Tynaarlo', '1730', 19991201, NULL),
   (1425, 1426, 'Midden-Drenthe', '1731', 20000101, NULL),
   (1426, 1427, 'Overbetuwe', '1734', 20010101, NULL),
   (1427, 1428, 'Hof van Twente', '1735', 20010101, NULL),
   (1428, 1429, 'Neder-Betuwe', '1740', 20030401, NULL),
   (1429, 1430, 'Rijssen-Holten', '1742', 20030315, NULL),
   (1430, 1431, 'Geldrop-Mierlo', '1771', 20040101, NULL),
   (1431, 1432, 'Olst-Wijhe', '1773', 20020326, NULL),
   (1432, 1433, 'Dinkelland', '1774', 20020601, NULL),
   (1433, 1434, 'Westland', '1783', 20040101, NULL),
   (1434, 1435, 'Midden-Delfland', '1842', 20040101, NULL),
   (1435, 1436, 'Berkelland', '1859', 20050101, NULL),
   (1436, 1437, 'Bronckhorst', '1876', 20050101, NULL),
   (1437, 1438, 'Sittard-Geleen', '1883', 20010101, NULL),
   (1438, 1439, 'Kaag en Braassem', '1884', 20090101, NULL),
   (1439, 1440, 'Dantumadiel', '1891', 20090101, NULL),
   (1440, 1441, 'Zuidplas', '1892', 20100101, NULL),
   (1441, 1442, 'Peel en Maas', '1894', 20100101, NULL),
   (1442, 1443, 'Oldambt', '1895', 20100101, NULL),
   (1443, 1444, 'Zwartewaterland', '1896', 20010101, NULL),
   (1444, 1445, 'Súdwest-Fryslân', '1900', 20110101, NULL),
   (1445, 1446, 'Bodegraven-Reeuwijk', '1901', 20110101, NULL),
   (1446, 1447, 'Eijsden-Margraten', '1903', 20110101, NULL),
   (1447, 1448, 'Stichtse Vecht', '1904', 20110101, NULL),
   (1448, 1449, 'Menameradiel', '1908', 20110101, NULL),
   (1449, 1450, 'Hollands Kroon', '1911', 20120101, NULL),
   (1450, 1451, 'Leidschendam-Voorburg', '1916', 20020101, NULL),
   (1451, 1452, 'Pijnacker-Nootdorp', '1926', 20020101, NULL),
   (1452, 1453, 'Heel', '1937', 19910101, 20070101),
   (1453, 1454, 'Montferland', '1955', 20050101, NULL),
   (1454, 1455, 'Menterwolde', '1987', 19910201, NULL),
   (1455, 1456, 'Goeree-Overflakkee', '1924', 20130101, NULL),
   (1456, 1457, 'Molenwaard', '1927', 20130101, NULL),
   (1457, 1458, 'De Friese Meren', '1921', 20140101, 20150701),
   (1459, 1460, 'Krimpenerwaard', '1931', 20150101, NULL),
   (1460, 1461, 'Nissewaard', '1930', 20150101, NULL),
   (1461, 1462, 'De Fryske Marren', '1940', 20150701, NULL),
   (1462, 1463, 'Gooise Meren', '1942', 20160101, NULL),
   (1463, 1464, 'Berg en Dal', '1945', 20160101, NULL),
   (1464, 1465, 'Meierijstad', '1948', 20170101, NULL);
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0053') WHERE ID = 4;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0056') WHERE ID = 5;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0053') WHERE ID = 7;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1661') WHERE ID = 9;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0010') WHERE ID = 11;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0045') WHERE ID = 14;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0053') WHERE ID = 15;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0006') WHERE ID = 16;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0056') WHERE ID = 19;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1651') WHERE ID = 22;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0019') WHERE ID = 23;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0045') WHERE ID = 24;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0045') WHERE ID = 26;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0033') WHERE ID = 29;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0024') WHERE ID = 30;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0039') WHERE ID = 31;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0033') WHERE ID = 32;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0765') WHERE ID = 33;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0006') WHERE ID = 34;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0039') WHERE ID = 35;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1987') WHERE ID = 36;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0056') WHERE ID = 38;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0015') WHERE ID = 39;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0765') WHERE ID = 41;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1895') WHERE ID = 42;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0024') WHERE ID = 44;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0010') WHERE ID = 45;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0019') WHERE ID = 46;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0019') WHERE ID = 47;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1663') WHERE ID = 48;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0019') WHERE ID = 49;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0019') WHERE ID = 52;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1921') WHERE ID = 54;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1895') WHERE ID = 55;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0024') WHERE ID = 57;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0080') WHERE ID = 58;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0055') WHERE ID = 60;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0103') WHERE ID = 64;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0070') WHERE ID = 65;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1900') WHERE ID = 67;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1891') WHERE ID = 68;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0058') WHERE ID = 69;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0139') WHERE ID = 70;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1722') WHERE ID = 71;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0070') WHERE ID = 72;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0653') WHERE ID = 74;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0139') WHERE ID = 76;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0104') WHERE ID = 78;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0103') WHERE ID = 79;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0104') WHERE ID = 80;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0057') WHERE ID = 81;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1921') WHERE ID = 85;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1908') WHERE ID = 86;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0058') WHERE ID = 87;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0057') WHERE ID = 90;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0071') WHERE ID = 92;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1900') WHERE ID = 94;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0104') WHERE ID = 95;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0737') WHERE ID = 97;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0057') WHERE ID = 98;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0058') WHERE ID = 100;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0710') WHERE ID = 102;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0104') WHERE ID = 103;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0683') WHERE ID = 104;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0101') WHERE ID = 105;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0140') WHERE ID = 106;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1900') WHERE ID = 107;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1680') WHERE ID = 108;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1697') WHERE ID = 110;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1681') WHERE ID = 111;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0109') WHERE ID = 113;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1701') WHERE ID = 114;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1701') WHERE ID = 115;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0136') WHERE ID = 116;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1680') WHERE ID = 118;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1680') WHERE ID = 119;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1701') WHERE ID = 120;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1699') WHERE ID = 123;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0119') WHERE ID = 124;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1681') WHERE ID = 125;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0109') WHERE ID = 126;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1699') WHERE ID = 127;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1699') WHERE ID = 128;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1680') WHERE ID = 129;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1690') WHERE ID = 130;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1690') WHERE ID = 131;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0114') WHERE ID = 132;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0109') WHERE ID = 133;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1697') WHERE ID = 134;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1701') WHERE ID = 135;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0136') WHERE ID = 136;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1697') WHERE ID = 137;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1690') WHERE ID = 138;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1730') WHERE ID = 139;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1690') WHERE ID = 140;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0109') WHERE ID = 141;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0051') WHERE ID = 142;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1735') WHERE ID = 145;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0160') WHERE ID = 146;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0150') WHERE ID = 147;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0195') WHERE ID = 148;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0194') WHERE ID = 149;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1774') WHERE ID = 152;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1735') WHERE ID = 154;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0150') WHERE ID = 155;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1896') WHERE ID = 157;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0194') WHERE ID = 158;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1735') WHERE ID = 159;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0160') WHERE ID = 160;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0186') WHERE ID = 162;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1896') WHERE ID = 164;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0177') WHERE ID = 165;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0178') WHERE ID = 168;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0195') WHERE ID = 170;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1735') WHERE ID = 172;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0148') WHERE ID = 173;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0195') WHERE ID = 175;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1773') WHERE ID = 177;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0149') WHERE ID = 179;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1742') WHERE ID = 181;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1735') WHERE ID = 182;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1708') WHERE ID = 184;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0181') WHERE ID = 185;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0194') WHERE ID = 188;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1700') WHERE ID = 189;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0194') WHERE ID = 190;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0149') WHERE ID = 191;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0174') WHERE ID = 193;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0166') WHERE ID = 194;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1896') WHERE ID = 195;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0181') WHERE ID = 197;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0181') WHERE ID = 198;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0263') WHERE ID = 201;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0299') WHERE ID = 202;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0290') WHERE ID = 204;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0296') WHERE ID = 207;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0236') WHERE ID = 208;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1705') WHERE ID = 209;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1955') WHERE ID = 210;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0296') WHERE ID = 211;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0214') WHERE ID = 213;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1859') WHERE ID = 214;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0297') WHERE ID = 215;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0236') WHERE ID = 218;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0236') WHERE ID = 220;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1955') WHERE ID = 221;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0197') WHERE ID = 222;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0258') WHERE ID = 223;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0230') WHERE ID = 226;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0290') WHERE ID = 227;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0258') WHERE ID = 230;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1859') WHERE ID = 232;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1734') WHERE ID = 234;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0304') WHERE ID = 237;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0209') WHERE ID = 238;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1509') WHERE ID = 240;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0206') WHERE ID = 241;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0262') WHERE ID = 242;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1586') WHERE ID = 243;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1945') WHERE ID = 244;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0304') WHERE ID = 245;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0263') WHERE ID = 248;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0263') WHERE ID = 250;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1876') WHERE ID = 251;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0196') WHERE ID = 252;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0287') WHERE ID = 253;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1734') WHERE ID = 254;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0267') WHERE ID = 256;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0225') WHERE ID = 257;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0206') WHERE ID = 258;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1876') WHERE ID = 259;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0297') WHERE ID = 260;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1740') WHERE ID = 261;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0262') WHERE ID = 262;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0240') WHERE ID = 263;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0214') WHERE ID = 264;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0214') WHERE ID = 267;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0241') WHERE ID = 268;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1859') WHERE ID = 269;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0304') WHERE ID = 273;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0252') WHERE ID = 274;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0196') WHERE ID = 275;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0263') WHERE ID = 279;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1859') WHERE ID = 281;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1876') WHERE ID = 283;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0241') WHERE ID = 285;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1734') WHERE ID = 286;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0304') WHERE ID = 287;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1876') WHERE ID = 289;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0733') WHERE ID = 290;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0304') WHERE ID = 291;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0668') WHERE ID = 293;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0301') WHERE ID = 294;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0222') WHERE ID = 295;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1509') WHERE ID = 298;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1876') WHERE ID = 301;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0214') WHERE ID = 303;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0736') WHERE ID = 308;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1581') WHERE ID = 309;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0331') WHERE ID = 312;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1904') WHERE ID = 314;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0352') WHERE ID = 317;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1581') WHERE ID = 318;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1581') WHERE ID = 319;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0632') WHERE ID = 321;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0589') WHERE ID = 322;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0307') WHERE ID = 323;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0356') WHERE ID = 325;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0632') WHERE ID = 326;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0311') WHERE ID = 327;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0352') WHERE ID = 328;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1581') WHERE ID = 329;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0335') WHERE ID = 331;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1904') WHERE ID = 332;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1696') WHERE ID = 333;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1581') WHERE ID = 335;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1904') WHERE ID = 336;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0310') WHERE ID = 337;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0736') WHERE ID = 339;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0329') WHERE ID = 340;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0331') WHERE ID = 341;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0589') WHERE ID = 344;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0327') WHERE ID = 346;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0736') WHERE ID = 349;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0344') WHERE ID = 350;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0356') WHERE ID = 351;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0335') WHERE ID = 352;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0736') WHERE ID = 353;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0632') WHERE ID = 357;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0373') WHERE ID = 360;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0529') WHERE ID = 362;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0383') WHERE ID = 363;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0420') WHERE ID = 367;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0361') WHERE ID = 368;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1911') WHERE ID = 369;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0479') WHERE ID = 370;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0558') WHERE ID = 371;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0412') WHERE ID = 372;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0478') WHERE ID = 374;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0377') WHERE ID = 375;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0558') WHERE ID = 377;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0405') WHERE ID = 381;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0532') WHERE ID = 382;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0852') WHERE ID = 383;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1942') WHERE ID = 384;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0476') WHERE ID = 385;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0357') WHERE ID = 389;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0357') WHERE ID = 390;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0365') WHERE ID = 392;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1696') WHERE ID = 393;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0532') WHERE ID = 394;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0441') WHERE ID = 398;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0429') WHERE ID = 404;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1246') WHERE ID = 406;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0432') WHERE ID = 407;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0415') WHERE ID = 410;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0880') WHERE ID = 411;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0852') WHERE ID = 412;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0446') WHERE ID = 413;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0479') WHERE ID = 414;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1911') WHERE ID = 415;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0479') WHERE ID = 416;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0478') WHERE ID = 417;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0383') WHERE ID = 421;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0852') WHERE ID = 422;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0478') WHERE ID = 424;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0529') WHERE ID = 425;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0852') WHERE ID = 426;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1942') WHERE ID = 427;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1942') WHERE ID = 428;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1696') WHERE ID = 429;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0466') WHERE ID = 430;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0412') WHERE ID = 431;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1598') WHERE ID = 432;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0478') WHERE ID = 433;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0529') WHERE ID = 436;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0458') WHERE ID = 437;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0558') WHERE ID = 438;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0412') WHERE ID = 439;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0361') WHERE ID = 441;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0365') WHERE ID = 443;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0454') WHERE ID = 445;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0458') WHERE ID = 446;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0373') WHERE ID = 447;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0395') WHERE ID = 448;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0416') WHERE ID = 449;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0529') WHERE ID = 450;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0529') WHERE ID = 452;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0558') WHERE ID = 455;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0498') WHERE ID = 457;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0478') WHERE ID = 458;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0395') WHERE ID = 459;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0361') WHERE ID = 461;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0420') WHERE ID = 462;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1246') WHERE ID = 463;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0479') WHERE ID = 464;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1911') WHERE ID = 465;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1911') WHERE ID = 466;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0369') WHERE ID = 467;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0412') WHERE ID = 468;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0420') WHERE ID = 469;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0880') WHERE ID = 470;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0479') WHERE ID = 471;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0454') WHERE ID = 472;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0880') WHERE ID = 473;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0479') WHERE ID = 474;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0479') WHERE ID = 475;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0458') WHERE ID = 477;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0405') WHERE ID = 478;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0441') WHERE ID = 479;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0592') WHERE ID = 480;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0385') WHERE ID = 481;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0569') WHERE ID = 483;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0568') WHERE ID = 484;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1884') WHERE ID = 486;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0707') WHERE ID = 488;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0491') WHERE ID = 489;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0689') WHERE ID = 490;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0287') WHERE ID = 491;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0646') WHERE ID = 493;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1931') WHERE ID = 494;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1621') WHERE ID = 495;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1621') WHERE ID = 496;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0491') WHERE ID = 497;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1621') WHERE ID = 498;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0693') WHERE ID = 499;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1901') WHERE ID = 500;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0484') WHERE ID = 502;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0693') WHERE ID = 503;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1924') WHERE ID = 507;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0595') WHERE ID = 509;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0505') WHERE ID = 510;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0620') WHERE ID = 511;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0568') WHERE ID = 512;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0689') WHERE ID = 513;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1924') WHERE ID = 514;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0644') WHERE ID = 517;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0693') WHERE ID = 518;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0588') WHERE ID = 519;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0585') WHERE ID = 520;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1783') WHERE ID = 522;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0694') WHERE ID = 523;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0623') WHERE ID = 524;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0620') WHERE ID = 525;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0646') WHERE ID = 527;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0568') WHERE ID = 528;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0642') WHERE ID = 529;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0707') WHERE ID = 530;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0585') WHERE ID = 531;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0420') WHERE ID = 532;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0287') WHERE ID = 536;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0689') WHERE ID = 538;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0689') WHERE ID = 539;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0545') WHERE ID = 541;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0611') WHERE ID = 542;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0646') WHERE ID = 543;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0643') WHERE ID = 544;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0694') WHERE ID = 546;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0707') WHERE ID = 547;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1916') WHERE ID = 551;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0645') WHERE ID = 552;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0643') WHERE ID = 553;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0707') WHERE ID = 554;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1783') WHERE ID = 555;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0585') WHERE ID = 557;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1842') WHERE ID = 558;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0707') WHERE ID = 560;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1598') WHERE ID = 561;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1924') WHERE ID = 562;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0647') WHERE ID = 563;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0693') WHERE ID = 564;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1783') WHERE ID = 565;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1892') WHERE ID = 566;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0585') WHERE ID = 567;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1783') WHERE ID = 568;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0588') WHERE ID = 569;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1892') WHERE ID = 570;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1930') WHERE ID = 571;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0707') WHERE ID = 573;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1927') WHERE ID = 574;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0694') WHERE ID = 575;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1673') WHERE ID = 576;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0689') WHERE ID = 577;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1926') WHERE ID = 580;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0611') WHERE ID = 581;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1924') WHERE ID = 583;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0614') WHERE ID = 584;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0693') WHERE ID = 585;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0693') WHERE ID = 586;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0568') WHERE ID = 589;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0644') WHERE ID = 590;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0588') WHERE ID = 594;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0613') WHERE ID = 595;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0585') WHERE ID = 596;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1926') WHERE ID = 597;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1901') WHERE ID = 598;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0613') WHERE ID = 599;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0614') WHERE ID = 601;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0599') WHERE ID = 603;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0645') WHERE ID = 604;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0537') WHERE ID = 605;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1525') WHERE ID = 607;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0689') WHERE ID = 608;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1842') WHERE ID = 610;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1931') WHERE ID = 611;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0545') WHERE ID = 612;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1930') WHERE ID = 615;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0623') WHERE ID = 618;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0694') WHERE ID = 619;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0707') WHERE ID = 621;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0537') WHERE ID = 622;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0501') WHERE ID = 624;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1931') WHERE ID = 626;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1916') WHERE ID = 627;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1525') WHERE ID = 628;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1525') WHERE ID = 631;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1783') WHERE ID = 633;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0585') WHERE ID = 634;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0645') WHERE ID = 636;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0693') WHERE ID = 637;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0573') WHERE ID = 638;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0647') WHERE ID = 639;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0588') WHERE ID = 642;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0568') WHERE ID = 643;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0501') WHERE ID = 644;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1931') WHERE ID = 646;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1931') WHERE ID = 647;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1884') WHERE ID = 648;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1672') WHERE ID = 649;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1666') WHERE ID = 650;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1698') WHERE ID = 651;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0687') WHERE ID = 652;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0715') WHERE ID = 653;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0654') WHERE ID = 654;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0715') WHERE ID = 655;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1921') WHERE ID = 656;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0692') WHERE ID = 658;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1676') WHERE ID = 659;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1676') WHERE ID = 660;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0692') WHERE ID = 661;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0677') WHERE ID = 662;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0717') WHERE ID = 663;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0654') WHERE ID = 664;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1676') WHERE ID = 665;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0654') WHERE ID = 666;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0677') WHERE ID = 668;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0654') WHERE ID = 669;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0692') WHERE ID = 670;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0654') WHERE ID = 672;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0664') WHERE ID = 673;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0654') WHERE ID = 674;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0654') WHERE ID = 675;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0654') WHERE ID = 676;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0715') WHERE ID = 677;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0677') WHERE ID = 678;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0692') WHERE ID = 679;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0664') WHERE ID = 682;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0664') WHERE ID = 683;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0650') WHERE ID = 684;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1695') WHERE ID = 685;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1900') WHERE ID = 686;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0703') WHERE ID = 687;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0703') WHERE ID = 688;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0717') WHERE ID = 689;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1676') WHERE ID = 691;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0692') WHERE ID = 693;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0654') WHERE ID = 694;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1714') WHERE ID = 695;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1927') WHERE ID = 696;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1927') WHERE ID = 697;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0654') WHERE ID = 698;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0716') WHERE ID = 699;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0650') WHERE ID = 700;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0654') WHERE ID = 701;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0704') WHERE ID = 702;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0716') WHERE ID = 703;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0713') WHERE ID = 704;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0703') WHERE ID = 705;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0715') WHERE ID = 707;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0716') WHERE ID = 708;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0692') WHERE ID = 709;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0716') WHERE ID = 711;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0677') WHERE ID = 712;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1900') WHERE ID = 713;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0716') WHERE ID = 714;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0716') WHERE ID = 715;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1698') WHERE ID = 716;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0716') WHERE ID = 717;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0675') WHERE ID = 722;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0717') WHERE ID = 723;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0703') WHERE ID = 724;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0692') WHERE ID = 725;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0678') WHERE ID = 726;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0704') WHERE ID = 727;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1676') WHERE ID = 728;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0717') WHERE ID = 729;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1695') WHERE ID = 730;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0664') WHERE ID = 731;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0703') WHERE ID = 732;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0692') WHERE ID = 733;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0715') WHERE ID = 734;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1676') WHERE ID = 735;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0650') WHERE ID = 737;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0692') WHERE ID = 738;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1659') WHERE ID = 742;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0874') WHERE ID = 743;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1723') WHERE ID = 744;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0874') WHERE ID = 745;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1652') WHERE ID = 748;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1659') WHERE ID = 749;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1684') WHERE ID = 750;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1724') WHERE ID = 752;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0828') WHERE ID = 753;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0855') WHERE ID = 754;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0845') WHERE ID = 755;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1728') WHERE ID = 757;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1706') WHERE ID = 762;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1723') WHERE ID = 763;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1684') WHERE ID = 764;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0798') WHERE ID = 766;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0851') WHERE ID = 767;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0797') WHERE ID = 770;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0845') WHERE ID = 771;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0870') WHERE ID = 772;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0738') WHERE ID = 774;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0796') WHERE ID = 776;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0796') WHERE ID = 777;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0860') WHERE ID = 778;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0788') WHERE ID = 779;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0878') WHERE ID = 781;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1671') WHERE ID = 783;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1771') WHERE ID = 784;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1652') WHERE ID = 785;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0874') WHERE ID = 786;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0766') WHERE ID = 790;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0748') WHERE ID = 792;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1684') WHERE ID = 793;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1721') WHERE ID = 794;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0791') WHERE ID = 795;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1658') WHERE ID = 796;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0788') WHERE ID = 798;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1655') WHERE ID = 802;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1728') WHERE ID = 803;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1667') WHERE ID = 804;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1665') WHERE ID = 805;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0873') WHERE ID = 806;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0878') WHERE ID = 807;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1658') WHERE ID = 808;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0757') WHERE ID = 809;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1659') WHERE ID = 810;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0828') WHERE ID = 811;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0749') WHERE ID = 813;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0759') WHERE ID = 814;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1665') WHERE ID = 815;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0828') WHERE ID = 816;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1771') WHERE ID = 817;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0824') WHERE ID = 819;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0758') WHERE ID = 820;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0851') WHERE ID = 821;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0791') WHERE ID = 822;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1671') WHERE ID = 824;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0756') WHERE ID = 825;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0823') WHERE ID = 828;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1691') WHERE ID = 830;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0873') WHERE ID = 832;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1655') WHERE ID = 833;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1655') WHERE ID = 834;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0758') WHERE ID = 835;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0873') WHERE ID = 836;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0779') WHERE ID = 837;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0828') WHERE ID = 838;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1667') WHERE ID = 839;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0749') WHERE ID = 840;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1674') WHERE ID = 841;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0796') WHERE ID = 842;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0879') WHERE ID = 844;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0874') WHERE ID = 845;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1685') WHERE ID = 846;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1948') WHERE ID = 847;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1948') WHERE ID = 849;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0867') WHERE ID = 852;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0878') WHERE ID = 853;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1665') WHERE ID = 856;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0758') WHERE ID = 857;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0855') WHERE ID = 860;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0738') WHERE ID = 862;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1948') WHERE ID = 863;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0770') WHERE ID = 865;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0756') WHERE ID = 866;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0797') WHERE ID = 867;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1691') WHERE ID = 871;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0867') WHERE ID = 872;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0749') WHERE ID = 874;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0878') WHERE ID = 875;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1674') WHERE ID = 878;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0738') WHERE ID = 879;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1685') WHERE ID = 880;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1709') WHERE ID = 881;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0935') WHERE ID = 886;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0962') WHERE ID = 887;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0983') WHERE ID = 888;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0920') WHERE ID = 889;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1937') WHERE ID = 890;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0983') WHERE ID = 893;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0936') WHERE ID = 894;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0994') WHERE ID = 895;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0963') WHERE ID = 897;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0965') WHERE ID = 898;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0935') WHERE ID = 899;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1883') WHERE ID = 900;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1507') WHERE ID = 901;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0938') WHERE ID = 903;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0936') WHERE ID = 904;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1711') WHERE ID = 905;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0971') WHERE ID = 906;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0928') WHERE ID = 907;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1903') WHERE ID = 908;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1883') WHERE ID = 909;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0938') WHERE ID = 911;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0920') WHERE ID = 912;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0897') WHERE ID = 913;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0905') WHERE ID = 914;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1507') WHERE ID = 915;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1729') WHERE ID = 916;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1640') WHERE ID = 917;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1937') WHERE ID = 918;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0935') WHERE ID = 919;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1894') WHERE ID = 921;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0957') WHERE ID = 922;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1640') WHERE ID = 923;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0917') WHERE ID = 924;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0914') WHERE ID = 925;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1507') WHERE ID = 926;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0951') WHERE ID = 927;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1640') WHERE ID = 928;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0935') WHERE ID = 929;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0963') WHERE ID = 930;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1894') WHERE ID = 932;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0986') WHERE ID = 933;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0968') WHERE ID = 934;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0933') WHERE ID = 935;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1641') WHERE ID = 936;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1894') WHERE ID = 937;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1903') WHERE ID = 939;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0993') WHERE ID = 940;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1669') WHERE ID = 942;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0963') WHERE ID = 943;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1894') WHERE ID = 944;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0936') WHERE ID = 945;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0956') WHERE ID = 946;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0968') WHERE ID = 948;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0958') WHERE ID = 950;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0882') WHERE ID = 951;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0974') WHERE ID = 952;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0936') WHERE ID = 953;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0897') WHERE ID = 955;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0933') WHERE ID = 956;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0962') WHERE ID = 957;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0907') WHERE ID = 958;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1679') WHERE ID = 959;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1670') WHERE ID = 961;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0974') WHERE ID = 962;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0882') WHERE ID = 963;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0951') WHERE ID = 964;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0881') WHERE ID = 966;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1507') WHERE ID = 967;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0936') WHERE ID = 969;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0956') WHERE ID = 970;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1883') WHERE ID = 971;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0990') WHERE ID = 972;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0888') WHERE ID = 973;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0933') WHERE ID = 975;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0988') WHERE ID = 976;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1711') WHERE ID = 977;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0957') WHERE ID = 978;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0983') WHERE ID = 979;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1641') WHERE ID = 980;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0882') WHERE ID = 981;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0938') WHERE ID = 982;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0971') WHERE ID = 983;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0994') WHERE ID = 985;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0939') WHERE ID = 988;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0993') WHERE ID = 990;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1937') WHERE ID = 992;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1729') WHERE ID = 993;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0913') WHERE ID = 994;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0951') WHERE ID = 995;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0984') WHERE ID = 996;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0034') WHERE ID = 999;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0686') WHERE ID = 1003;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0866') WHERE ID = 1004;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0484') WHERE ID = 1005;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0305') WHERE ID = 1006;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0305') WHERE ID = 1007;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1224') WHERE ID = 1008;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0074') WHERE ID = 1009;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0808') WHERE ID = 1010;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0484') WHERE ID = 1011;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0141') WHERE ID = 1012;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0222') WHERE ID = 1013;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0160') WHERE ID = 1014;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0175') WHERE ID = 1015;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0185') WHERE ID = 1016;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0390') WHERE ID = 1017;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0867') WHERE ID = 1018;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0271') WHERE ID = 1019;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0632') WHERE ID = 1020;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0702') WHERE ID = 1021;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0007') WHERE ID = 1022;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0867') WHERE ID = 1023;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0756') WHERE ID = 1024;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0720') WHERE ID = 1025;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0774') WHERE ID = 1026;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0580') WHERE ID = 1027;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1236') WHERE ID = 1028;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0858') WHERE ID = 1029;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0719') WHERE ID = 1030;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0311') WHERE ID = 1031;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0311') WHERE ID = 1032;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0627') WHERE ID = 1033;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0416') WHERE ID = 1034;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0968') WHERE ID = 1035;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0914') WHERE ID = 1036;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0363') WHERE ID = 1037;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0725') WHERE ID = 1038;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0849') WHERE ID = 1039;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0599') WHERE ID = 1040;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0682') WHERE ID = 1041;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0865') WHERE ID = 1042;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0599') WHERE ID = 1043;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0762') WHERE ID = 1044;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0835') WHERE ID = 1045;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0835') WHERE ID = 1046;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0792') WHERE ID = 1047;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0858') WHERE ID = 1048;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0274') WHERE ID = 1049;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0656') WHERE ID = 1050;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0316') WHERE ID = 1051;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0771') WHERE ID = 1052;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0688') WHERE ID = 1053;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0770') WHERE ID = 1054;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0648') WHERE ID = 1055;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0688') WHERE ID = 1056;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0688') WHERE ID = 1057;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0740') WHERE ID = 1059;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0786') WHERE ID = 1060;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0257') WHERE ID = 1061;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0747') WHERE ID = 1062;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0771') WHERE ID = 1063;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0772') WHERE ID = 1064;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0510') WHERE ID = 1065;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0523') WHERE ID = 1066;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0817') WHERE ID = 1067;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0191') WHERE ID = 1068;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0642') WHERE ID = 1069;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0686') WHERE ID = 1070;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0725') WHERE ID = 1071;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0347') WHERE ID = 1072;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0523') WHERE ID = 1073;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0797') WHERE ID = 1074;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0792') WHERE ID = 1075;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0713') WHERE ID = 1076;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0612') WHERE ID = 1077;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0506') WHERE ID = 1078;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0283') WHERE ID = 1079;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0719') WHERE ID = 1080;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0504') WHERE ID = 1081;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0835') WHERE ID = 1082;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0797') WHERE ID = 1083;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0599') WHERE ID = 1084;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0503') WHERE ID = 1085;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0018') WHERE ID = 1086;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0014') WHERE ID = 1087;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0599') WHERE ID = 1088;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0982') WHERE ID = 1089;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0393') WHERE ID = 1090;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0835') WHERE ID = 1091;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0276') WHERE ID = 1092;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0925') WHERE ID = 1093;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0331') WHERE ID = 1094;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0191') WHERE ID = 1095;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1037') WHERE ID = 1096;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0682') WHERE ID = 1097;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0688') WHERE ID = 1098;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0606') WHERE ID = 1099;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0390') WHERE ID = 1100;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0720') WHERE ID = 1101;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0599') WHERE ID = 1102;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0324') WHERE ID = 1103;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0506') WHERE ID = 1104;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0847') WHERE ID = 1105;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0747') WHERE ID = 1106;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0808') WHERE ID = 1107;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0311') WHERE ID = 1108;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0153') WHERE ID = 1109;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0518') WHERE ID = 1110;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0333') WHERE ID = 1111;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0863') WHERE ID = 1112;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0957') WHERE ID = 1113;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0771') WHERE ID = 1114;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0686') WHERE ID = 1115;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0504') WHERE ID = 1116;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0905') WHERE ID = 1117;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0257') WHERE ID = 1118;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0925') WHERE ID = 1119;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0687') WHERE ID = 1120;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0530') WHERE ID = 1121;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0559') WHERE ID = 1122;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0363') WHERE ID = 1123;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0530') WHERE ID = 1124;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0662') WHERE ID = 1125;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0864') WHERE ID = 1126;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0416') WHERE ID = 1127;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0627') WHERE ID = 1128;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0033') WHERE ID = 1129;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0014') WHERE ID = 1130;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0656') WHERE ID = 1131;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0725') WHERE ID = 1132;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0914') WHERE ID = 1133;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0312') WHERE ID = 1134;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0861') WHERE ID = 1135;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0580') WHERE ID = 1136;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0489') WHERE ID = 1137;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0718') WHERE ID = 1138;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0662') WHERE ID = 1139;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0660') WHERE ID = 1140;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0719') WHERE ID = 1141;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0606') WHERE ID = 1142;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0982') WHERE ID = 1143;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0935') WHERE ID = 1144;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0511') WHERE ID = 1145;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0580') WHERE ID = 1146;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0347') WHERE ID = 1147;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0797') WHERE ID = 1148;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0416') WHERE ID = 1149;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0484') WHERE ID = 1150;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0662') WHERE ID = 1151;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0599') WHERE ID = 1152;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0808') WHERE ID = 1153;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0506') WHERE ID = 1154;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0599') WHERE ID = 1155;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0476') WHERE ID = 1156;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0510') WHERE ID = 1157;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0212') WHERE ID = 1158;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1247') WHERE ID = 1159;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0363') WHERE ID = 1160;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0843') WHERE ID = 1161;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0725') WHERE ID = 1162;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0632') WHERE ID = 1163;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0702') WHERE ID = 1164;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0978') WHERE ID = 1165;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0718') WHERE ID = 1166;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0311') WHERE ID = 1167;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0911') WHERE ID = 1168;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0316') WHERE ID = 1169;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0756') WHERE ID = 1170;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0018') WHERE ID = 1171;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0321') WHERE ID = 1172;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0599') WHERE ID = 1173;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0982') WHERE ID = 1174;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0678') WHERE ID = 1175;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0392') WHERE ID = 1176;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0074') WHERE ID = 1177;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0725') WHERE ID = 1178;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0717') WHERE ID = 1179;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0713') WHERE ID = 1180;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0648') WHERE ID = 1181;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0687') WHERE ID = 1182;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0935') WHERE ID = 1183;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0363') WHERE ID = 1184;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0595') WHERE ID = 1185;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0811') WHERE ID = 1186;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0559') WHERE ID = 1187;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0392') WHERE ID = 1188;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0432') WHERE ID = 1189;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0849') WHERE ID = 1190;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0559') WHERE ID = 1191;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0141') WHERE ID = 1192;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0222') WHERE ID = 1193;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0160') WHERE ID = 1194;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0175') WHERE ID = 1195;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0185') WHERE ID = 1196;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0595') WHERE ID = 1197;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0511') WHERE ID = 1198;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0794') WHERE ID = 1199;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0548') WHERE ID = 1200;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0719') WHERE ID = 1201;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0772') WHERE ID = 1202;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1171') WHERE ID = 1203;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0772') WHERE ID = 1204;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0333') WHERE ID = 1205;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0772') WHERE ID = 1206;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0321') WHERE ID = 1208;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0861') WHERE ID = 1209;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0347') WHERE ID = 1210;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0786') WHERE ID = 1211;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0548') WHERE ID = 1212;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0622') WHERE ID = 1213;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0347') WHERE ID = 1214;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0762') WHERE ID = 1215;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0329') WHERE ID = 1216;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0717') WHERE ID = 1217;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0503') WHERE ID = 1218;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0849') WHERE ID = 1219;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0506') WHERE ID = 1220;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0281') WHERE ID = 1221;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0363') WHERE ID = 1222;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0007') WHERE ID = 1223;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0363') WHERE ID = 1224;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0870') WHERE ID = 1225;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0312') WHERE ID = 1226;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0334') WHERE ID = 1227;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0047') WHERE ID = 1228;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0331') WHERE ID = 1229;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0191') WHERE ID = 1230;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0772') WHERE ID = 1231;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0375') WHERE ID = 1232;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0599') WHERE ID = 1233;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0227') WHERE ID = 1234;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0191') WHERE ID = 1235;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0861') WHERE ID = 1236;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0637') WHERE ID = 1237;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0781') WHERE ID = 1238;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0656') WHERE ID = 1239;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0720') WHERE ID = 1240;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0416') WHERE ID = 1241;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0627') WHERE ID = 1242;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0033') WHERE ID = 1243;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0393') WHERE ID = 1244;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0344') WHERE ID = 1245;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0212') WHERE ID = 1246;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0484') WHERE ID = 1247;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0193') WHERE ID = 1248;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0498') WHERE ID = 1249;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0832') WHERE ID = 1250;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0263') WHERE ID = 1251;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0362') WHERE ID = 1252;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0037') WHERE ID = 1253;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0777') WHERE ID = 1254;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0982') WHERE ID = 1255;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0670') WHERE ID = 1256;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0654') WHERE ID = 1257;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1060') WHERE ID = 1258;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0540') WHERE ID = 1259;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0092') WHERE ID = 1260;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0597') WHERE ID = 1261;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0265') WHERE ID = 1262;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0075') WHERE ID = 1263;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0509') WHERE ID = 1264;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0496') WHERE ID = 1265;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1049') WHERE ID = 1266;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0769') WHERE ID = 1267;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1111') WHERE ID = 1268;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1215') WHERE ID = 1269;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0328') WHERE ID = 1270;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0569') WHERE ID = 1271;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1215') WHERE ID = 1272;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0490') WHERE ID = 1273;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0397') WHERE ID = 1274;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0509') WHERE ID = 1275;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1215') WHERE ID = 1276;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1221') WHERE ID = 1277;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1226') WHERE ID = 1278;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0554') WHERE ID = 1279;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0326') WHERE ID = 1280;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0239') WHERE ID = 1281;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0320') WHERE ID = 1282;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0385') WHERE ID = 1283;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0430') WHERE ID = 1284;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1214') WHERE ID = 1285;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0318') WHERE ID = 1286;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0528') WHERE ID = 1287;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1152') WHERE ID = 1288;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0323') WHERE ID = 1289;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1082') WHERE ID = 1290;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0444') WHERE ID = 1291;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0368') WHERE ID = 1292;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0393') WHERE ID = 1293;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0670') WHERE ID = 1294;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1066') WHERE ID = 1295;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0607') WHERE ID = 1296;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0496') WHERE ID = 1297;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1082') WHERE ID = 1298;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0577') WHERE ID = 1299;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0490') WHERE ID = 1300;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0633') WHERE ID = 1301;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0549') WHERE ID = 1302;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0323') WHERE ID = 1303;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0323') WHERE ID = 1304;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1066') WHERE ID = 1305;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0526') WHERE ID = 1306;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0649') WHERE ID = 1307;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0582') WHERE ID = 1308;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0283') WHERE ID = 1309;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0333') WHERE ID = 1310;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0642') WHERE ID = 1311;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0595') WHERE ID = 1312;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0507') WHERE ID = 1313;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0598') WHERE ID = 1314;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1154') WHERE ID = 1315;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1096') WHERE ID = 1316;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0577') WHERE ID = 1317;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0338') WHERE ID = 1318;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1113') WHERE ID = 1319;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1134') WHERE ID = 1320;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1135') WHERE ID = 1321;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0321') WHERE ID = 1322;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1118') WHERE ID = 1323;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0350') WHERE ID = 1324;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1075') WHERE ID = 1325;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1028') WHERE ID = 1326;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0312') WHERE ID = 1327;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1249') WHERE ID = 1328;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1258') WHERE ID = 1329;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1113') WHERE ID = 1330;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0594') WHERE ID = 1331;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0531') WHERE ID = 1332;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0371') WHERE ID = 1333;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0368') WHERE ID = 1334;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1157') WHERE ID = 1335;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0166') WHERE ID = 1336;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0321') WHERE ID = 1337;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0509') WHERE ID = 1338;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0554') WHERE ID = 1339;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0607') WHERE ID = 1340;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0393') WHERE ID = 1341;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0533') WHERE ID = 1342;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0851') WHERE ID = 1343;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1048') WHERE ID = 1344;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0542') WHERE ID = 1345;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1258') WHERE ID = 1346;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0617') WHERE ID = 1347;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0323') WHERE ID = 1348;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0493') WHERE ID = 1349;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0398') WHERE ID = 1350;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0534') WHERE ID = 1351;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0259') WHERE ID = 1352;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0346') WHERE ID = 1353;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0521') WHERE ID = 1354;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0483') WHERE ID = 1355;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0480') WHERE ID = 1356;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0308') WHERE ID = 1357;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0346') WHERE ID = 1358;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1134') WHERE ID = 1359;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1135') WHERE ID = 1360;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0507') WHERE ID = 1361;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0387') WHERE ID = 1362;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0328') WHERE ID = 1363;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1226') WHERE ID = 1364;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1210') WHERE ID = 1365;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0338') WHERE ID = 1366;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0491') WHERE ID = 1367;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0499') WHERE ID = 1368;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0171') WHERE ID = 1369;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1895') WHERE ID = 1384;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1719') WHERE ID = 1386;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1892') WHERE ID = 1387;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1640') WHERE ID = 1390;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0796') WHERE ID = 1391;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0484') WHERE ID = 1392;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '0569') WHERE ID = 1393;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1669') WHERE ID = 1396;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1702') WHERE ID = 1402;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1731') WHERE ID = 1405;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1714') WHERE ID = 1406;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1641') WHERE ID = 1452;
UPDATE Kern.Gem SET VoortzettendeGem = (SELECT B.ID FROM Kern.Gem B WHERE B.Code = '1940') WHERE ID = 1457;


--------------------------------------------------------------------------------
-- Stamgegeven: Reden verkrijging/verlies nationaliteit
--------------------------------------------------------------------------------
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (1, '000', 'Onbekend', NULL, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (2, '001', 'Wet op het Nederlanderschap 1892, art.1, lid 1a', 18930701, 19850101);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (3, '002', 'Wet op het Nederlanderschap 1892, art. 1, lid 1b', 18940701, 19850101);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (4, '003', 'Wet op het Nederlanderschap 1892, art. 1, lid 1c', 18930701, 19850101);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (5, '004', 'Wet op het Nederlanderschap 1892, art. 1, lid 1d', 18930701, 19850101);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (6, '005', 'Wet op het Nederlanderschap 1892, art. 1 bis', 19561101, 19850101);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (7, '006', 'Wet op het Nederlanderschap 1892, art. 2, lid 1a', 18930701, 19850101);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (8, '007', 'Wet op het Nederlanderschap 1892, art. 2, lid 1b', 18930701, 19850101);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (9, '008', 'Wet op het Nederlanderschap 1892, art. 2, lid 1c', 18930701, 19850101);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (10, '009', 'Wet op het Nederlanderschap 1892, art . 3', 18930701, 19850404);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (11, '010', 'Wet op het Nederlanderschap 1892,  art. 4', 18930701, 19850101);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (12, '011', 'Wet op het Nederlanderschap 1892,  art. 5 (oud)', 18930701, 19640301);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (13, '012', 'Wet op het Nederlanderschap 1892, art. 6', 18930701, 19850404);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (14, '013', 'Wet op het Nederlanderschap 1892, art. 8', 19640301, 19850101);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (15, '014', 'Wet op het Nederlanderschap 1892, art. 8b', 19640301, 19850101);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (16, '015', 'Wet op het Nederlanderschap 1892, art. 9, lid 1', 19640301, 19770315);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (17, '016', 'Wet op het Nederlanderschap 1892, art. 10', 18930701, 19850101);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (18, '017', 'Rijkswet op het Nederlanderschap 1984, art. 3, lid 1', 19850101, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (19, '018', 'Rijkswet op het Nederlanderschap 1984, art. 3, lid 2', 19850101, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (20, '019', 'Rijkswet op het Nederlanderschap 1984, art. 3, lid 3', 19850101, 20030401);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (21, '020', 'Rijkswet op het Nederlanderschap 1984, art. 4, lid 1', 19850101, 20030401);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (22, '021', 'Rijkswet op het Nederlanderschap 1984, art. 4, lid 2', 19850101, 20030401);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (23, '022', 'Rijkswet op het Nederlanderschap 1984, art. 4, lid 3', 19850101, 20030401);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (24, '023', 'Rijkswet op het Nederlanderschap 1984, art. 5, lid 1', 19850101, 20040101);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (25, '024', 'Rijkswet op het Nederlanderschap 1984, art. 5, lid 2', 19850101, 19981001);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (26, '025', 'Rijkswet op het Nederlanderschap 1984, art. 6, lid 1a', 19850101, 20030401);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (27, '026', 'Rijkswet op het Nederlanderschap 1984, art. 6, lid 1b', 19850101, 20030401);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (28, '027', 'Rijkswet op het Nederlanderschap 1984, art. 7', 19850101, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (29, '028', 'Rijkswet op het Nederlanderschap 1984, art. 10', 19850101, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (30, '029', 'Rijkswet op het Nederlanderschap 1984, art. 27, lid 2', 19850101, 19880101);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (31, '030', 'Rijkswet op het Nederlanderschap 1984, art. 11', 19850101, 20030401);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (32, '031', 'Toescheidingsovereenkomst Nederland - Suriname, art. 6, lid 1', 19751125, 19931125);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (33, '032', 'Toescheidingsovereenkomst Nederland - Suriname, art. 6, lid 2', 19751125, 19931125);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (34, '033', 'Toescheidingsovereenkomst Nederland - Suriname, art. 6, lid 4', 19751125, 19981125);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (35, '034', 'Toescheidingsovereenkomst Nederland-Suriname, art. 2, lid 1, jo. art. 5, lid 2', 19751125, NULL);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (36, '035', 'Toescheidingsovereenkomst Nederland-Suriname, art. 2, lid 1, jo. art. 6, lid 1', 19751125, 19931125);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (37, '036', 'Toescheidingsovereenkomst Nederland-Suriname, art. 2, lid 1, jo. art. 6, lid 2', 19751125, 19931125);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (38, '037', 'Toescheidingsovereenkomst Nederland-Suriname, art. 2, lid 1, jo art. 6, lid 4', 19751125, 19981125);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (39, '038', 'Toescheidingsovereenkomst Nederland-Suriname, art. 2, lid 1, jo. art. 7, lid 1', 19751125, NULL);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (40, '039', 'Toescheidingsovereenkomst Nederland-Suriname, art. 2, lid 1, jo. art. 7, lid 2', 19751125, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (41, '040', 'Overgangsbep. bij Wet op het Nederlanderschap 1892. Bep.: Wet 15 juli 1910', 19100816, 19100817);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (42, '041', 'Overgangsbep. bij Wet op het Nederl. 1892. Bep: Rijkswet 14-11-63, art. 3, sub I', 19640301, 20030401);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (43, '042', 'Overg.bep. bij Wet op het Nederl. 1892. Bep: Rijkswet 14-11-63, art. 3, sub III', 19640301, 20030401);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (44, '043', 'Overgangsbep. bij Wet op het Nederl. 1892. Bep: Rijkswet 14-11-63, art. 3, sub V', 19640301, 20030401);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (45, '044', 'Toescheidingsovereenkomst Nederland-Suriname, art. 6, lid 5', 19751125, 19981125);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (46, '045', 'Toescheidingsovereenkomst Nederland-Suriname, art. 7, lid 1', 19751125, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (47, '046', 'Toescheidingsovereenkomst Nederland-Suriname, art. 7, lid 2', 19751125, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (48, '047', 'Toescheidingsovereenkomst Nederland-Suriname, art. 8, lid 1', 19751125, 19761125);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (49, '048', 'Toescheidingsovereenkomst Nederland-Suriname, art. 10', 19751125, 19761125);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (50, '049', 'Rijkswet op het Nederlanderschap 1984, art. 28', 19850101, 20030401);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (51, '050', 'Toescheidingsovereenkomst Nederland-Suriname, art. 2, lid 1, jo. art. 8, lid 2', 19751125, 19761125);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (52, '051', 'Toescheidingsovereenkomst Nederland-Suriname, art. 2, lid 1, jo. art. 9', 19751125, 19781125);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (53, '052', 'Wet op het Nederlanderschap 1892, art. 2 bis', 19521226, 19751125);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (54, '053', 'Wet 30 juli 1953, art. 1, lid 1', 19530805, 19770315);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (55, '054', 'Wet 30 juli 1953, art. 1, lid 2', 19530805, 19770315);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (56, '055', 'Wet 3 november 1954, art. 2', 19541108, 19751125);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (57, '056', 'Wet 3 november 1954, art. 3', 19541108, 19751125);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (58, '057', 'Wet op het Nederlanderschap 1892, art. 5', 19770315, 19850101);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (59, '058', 'Wet op het Nederlanderschap 1892, art. 5 (oud)', 18930701, 19640301);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (60, '059', 'Wet op het Nederlanderschap 1892, art. 5', 19770315, 19850101);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (61, '060', 'Wet op het Nederlanderschap 1892, art. 7, lid 1, ond. 1', 18930701, 19850101);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (62, '061', 'Wet op het Nederlanderschap 1892, art. 7, lid 1, ond. 2', 19370701, 19850101);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (63, '062', 'Wet op het Nederlanderschap 1892, art. 7, lid 1, ond. 3', 18930701, 19850101);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (64, '063', 'Wet op het Nederlanderschap 1892, art. 7, lid 1, ond. 4', 18930701, 19850101);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (65, '064', 'Wet op het Nederlanderschap 1892, art. 7, lid 1, ond. 5', 18930701, 19850101);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (66, '065', 'Wet op het Nederlanderschap 1892, art. 8 a', 19640301, 19850101);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (67, '066', 'Wet op het Nederlanderschap 1892, art. 8 c', 19640301, 19850101);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (68, '067', 'Wet op het Nederlanderschap 1892, art. 9, lid 3', 19640301, 19770315);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (69, '068', 'Overgangsbep. bij Wet op het Nederl. 1892. Bep.: Wet 21 dec. 1936', 19370701, 19850101);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (70, '069', 'Overg.bep. bij de Wet op het Nederl. 1892. Bep: Wet 12-7-62. VERVALLEN!', 19621001, 19911101);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (71, '070', 'Overg.bep. bij Wet op het Nederl. 1892.  Bep: Rijkswet 14-11-63, art. 3, sub II', 19640301, 19850101);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (72, '071', 'Overg.bep. bij Wet op het Nederl. 1892. Bep: Rijkswet 14-11-1963, art. 3, sub IV', 19640301, 19850101);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (73, '072', 'Overg.bep. bij Wet op het Nederl. 1892. Bep: Rijkswet  14-11-1963, art. 3, sub V', 19640301, 19850101);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (74, '073', 'Toescheidingsovereenkomst Nederland - Suriname, art. 2, lid 1, jo. art. 3', 19751125, 19751126);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (75, '074', 'Toescheidingsovereenkomst Nederland - Suriname, art. 2, lid 1, jo. art. 4, sub a', 19751125, 19751126);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (76, '075', 'Toescheidingsovereenkomst Nederland - Suriname, art. 2, lid 1, jo. art. 4, sub b', 19751125, 19751126);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (77, '076', 'Toescheidingsovereenkomst Nederland - Suriname, art. 2, lid 1, jo. art. 5, lid 1', 19751125, 19860101);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (78, '077', 'Rijkswet op het Nederlanderschap 1984, art. 15, sub a', 19850101, 20030401);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (79, '078', 'Rijkswet op het Nederlanderschap 1984, art. 15, sub b', 19850101, 20030401);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (80, '079', 'Rijkswet op het Nederlanderschap 1984, art. 15, sub c', 19850101, 20030401);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (81, '080', 'Rijkswet op het Nederlanderschap 1984, art. 15, sub d', 19850101, 20030401);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (82, '081', 'Rijkswet op het Nederlanderschap 1984, art. 16, lid 1a', 19850101, 20030401);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (83, '082', 'Rijkswet op het Nederlanderschap 1984, art. 16, lid 1b', 19850101, 20030401);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (84, '083', 'Rijkswet op het Nederlanderschap 1984, art. 16, lid 1c', 19850101, 20030401);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (85, '084', 'Rijkswet op het Nederlanderschap 1984, art. 16, lid 1d', 19850101, 20030401);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (86, '085', 'Rijkswet 14 september 1962, art. 3, lid 1', 19620921, NULL);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (87, '086', 'Rijkswet 14 september 1962, art. 3, lid 2', 19620921, 19850101);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (88, '087', 'Rijkswet 8 september 1976, art. II', 19770315, 19850101);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (89, '088', 'Wet op het Nederlanderschap 1892, art. 6', 18930701, 19850101);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (90, '089', 'Wet op het Nederlanderschap 1892, art. 8 (oud)', 18930701, 19640301);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (91, '090', 'Wet op het Nederlanderschap 1892, art. 8a (oud), lid 1', 19580901, 19640301);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (92, '091', 'Wet op het Nederlanderschap 1892, art. 8a (oud), lid 2', 19580901, 19850101);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (93, '092', 'Wet op het Nederlanderschap 1892, art. 9 (oud)', 18930701, 19640301);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (94, '093', 'Koninklijk Besluit 17 november 1945, art. 2, lid 1', 19400511, 19510113);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (95, '094', 'Wet 29 december 1950, art. 2', 19400511, 19510113);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (96, '095', 'Wet 29 december 1950, art. 3, lid 2', 19400511, NULL);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (97, '096', 'Wet 23 december 1953, art. I', 19540301, 19540302);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (98, '097', 'Wet 23 december 1953, art. II', 19540301, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (99, '098', 'Wet 23 december 1953, art. III', 19540301, 19760301);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (100, '099', 'Overgangsbep. van de Wet op het Nederlanderschap 1892', 18930701, 18930702);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (101, '100', 'Toescheidingsovereenkomst Nederland-Indonesië, art. 3', 19491227, 19511228);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (102, '101', 'Toescheidingsovereenkomst Nederland-Indonesië, art. 4, lid 1', 19491227, 19491228);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (103, '102', 'Toescheidingsovereenkomst Nederland-Indonesië, art. 4, lid 2, sub a', 19491227, 19511228);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (104, '103', 'Toescheidingsovereenkomst Nederland-Indonesië, art. 4, lid 2, sub b', 19491227, 19491228);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (105, '104', 'Toescheidingsovereenkomst Nederland-Indonesië, art. 4, lid 2, sub b', 19491227, 19511228);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (106, '105', 'Toescheidingsovereenkomst Nederland-Indonesië, art. 5, 2e volzin', 19491227, 19511228);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (107, '106', 'Toescheidingsovereenkomst Nederland-Indonesië, art. 5, 3e volzin', 19491227, 19511228);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (108, '107', 'Toescheidingsovereenkomst Nederland-Indonesië, art. 6', 19491227, 19491228);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (109, '108', 'Toescheidingsovereenkomst Nederland-Indonesië, art. 6', 19491227, 19511228);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (110, '109', 'Toescheidingsovereenkomst Nederland-Indonesië, art. 7', 19491227, 19491228);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (111, '110', 'Toescheidingsovereenkomst Nederland-Indonesië, art. 7', 19491227, 19511228);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (112, '111', 'Toescheidingsovereenkomst Nederland-Indonesië, art. 8', 19491227, 19671226);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (113, '112', 'Toescheidingsovereenkomst Nederland-Indonesië, art. 8', 19491227, 19671226);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (114, '113', 'Toescheidingsovereenkomst Nederland-Indonesië, art. 10', 19491227, NULL);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (115, '114', 'Toescheidingsovereenkomst Nederland-Indonesië, art. 10', 19491227, NULL);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (116, '115', 'Rijkswet op het Nederlanderschap 1984, art. 14, lid 1', 19850101, 20030401);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (117, '116', 'Toescheidingsovereenkomst Nederland-Suriname, art. 3 VERVALLEN!', 19751125, 19911101);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (118, '117', 'Overeenkomst van Bern van 13 september 1973, art. 3, jo. art. 1, lid 1', 19850519, 19850520);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (119, '118', 'Rijkswet op het Nederlanderschap 1984 i.v.m. wijziging 1998, art. 5, lid 2', 19981001, 20040101);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (120, '119', 'Rijkswet op het Nederlanderschap 1984 i.v.m. wijziging 1998, art. 5, lid 3', 19981001, 20040101);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (121, '120', 'Rijkswet op het Nederlanderschap 1984 i.v.m. wijziging 1998, art. 5, lid 4', 19981001, 20040101);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (122, '121', 'Overg.bep. bij wijz. Rijkswet Nederl.  Bep: Rijkswet 21-12-2000, art. V, lid 2', 20010201, 20030401);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (123, '122', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2000, art. 3, lid 3', 20030401, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (124, '123', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2000, art. 4, lid 1', 20030401, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (125, '124', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2000, art. 4, lid 2', 20030401, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (126, '125', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2000, art. 6, lid 1, sub a', 20030401, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (127, '126', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2000, art. 6, lid 1, sub b', 20030401, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (128, '127', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2002, art. 6, lid 1, sub c', 20030401, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (129, '128', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2002, art. 6, lid 1, sub d', 20030401, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (130, '129', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2000, art. 6, lid 1, sub e', 20030401, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (131, '130', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2000, art. 6, lid 1, sub f', 20030401, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (132, '131', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2000, art. 6, lid 1, sub g', 20030401, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (133, '132', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2000, art. 6, lid 1, sub h', 20030401, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (134, '133', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2002, art. 6, lid 7', 20030401, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (135, '134', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2000, art. 11, lid 1', 20030401, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (136, '135', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2002, art. 11, lid 4', 20030401, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (137, '136', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2002, art. 11, lid 5', 20030401, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (138, '137', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2000, art. 11, lid 7', 20030401, NULL);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (139, '138', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2000, art. 14, lid 1', 20030401, NULL);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (140, '139', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2000, art. 14, lid 2', 20030401, 20101001);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (141, '140', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2000, art. 15, lid 1, sub c', 20030401, NULL);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (142, '141', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2000, art. 15, lid 1, sub e', 20030401, 20170301);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (143, '142', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2000, art. 15A, lid 1', 20030401, 20040101);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (144, '143', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2000, art. 15A, lid 2', 20030401, 20040101);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (145, '144', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2002, art. 16, lid 1, sub a', 20030401, NULL);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (146, '145', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2002, art. 16, lid 1, sub b', 20030401, NULL);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (147, '146', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2002, art. 16, lid 1, sub c', 20030401, NULL);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (148, '147', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2002, art. 16, lid 1, sub d', 20030401, NULL);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (149, '148', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2002, art. 16, lid 1, sub e', 20030401, NULL);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (150, '149', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2000, art. 16A', 20030401, 20040101);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (151, '150', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2002, art. 26, lid 3', 20030401, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (152, '151', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2000, art. 28, lid 1', 20030401, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (153, '152', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2002, art. 28, lid 3', 20030401, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (154, '153', 'Overgangsbep. bij wijz. Rijkswet Nederl. Bep: Rijkswet 21-12-2000, art. V, lid 1', 20030401, 20050401);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (155, '154', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2000, art. 15, lid 1, sub a', 20030401, NULL);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (156, '155', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2000, art. 15, lid 1, sub b', 20030401, NULL);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (157, '156', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2000, art. 15, lid 1, sub d', 20030401, NULL);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (158, '157', 'Verdrag van Straatsburg van 6 mei 1963, art. 1, lid 1', 19850610, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (159, '158', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2003, art. 5', 20040101, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (160, '159', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2003, art. 5a, lid 1', 20040101, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (161, '160', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2003, art. 5a, lid 2', 20040101, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (162, '161', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2003, art. 5b, lid 1', 20040101, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (163, '162', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2003, art. 5b, lid 2', 20040101, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (164, '163', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2003, art. 5c', 20040101, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (165, '164', 'Rijkswet Nederlanderschap 1984, ivm wijziging 2008, art. 4, lid 2', 20090301, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (166, '165', 'Rijkswet Nederlanderschap 1984, ivm wijziging 2008, art. 4, lid 3', 20090301, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (167, '166', 'Rijkswet Nederlanderschap 1984, ivm wijziging 2008, art. 4, lid 4', 20090301, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (168, '167', 'Rijkswet Nederlanderschap 1984, ivm wijziging 2008, art. 4, lid 5', 20090301, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (169, '168', 'Rijkswet Nederlanderschap 1984, ivm wijziging 2008, art. 6, lid 1, sub c', 20090301, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (170, '169', 'Rijkswet Nederlanderschap 1984, ivm wijziging 2008, art. 6, lid 8', 20090301, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (171, '170', 'Rijkswet Nederlanderschap 1984, ivm wijziging 2008, art. 11, lid 4', 20090301, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (172, '171', 'Rijkswet Nederlanderschap 1984, ivm wijziging 2008, art. 11, lid 5', 20090301, NULL);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (173, '172', 'Rijkswet Nederlanderschap 1984, ivm wijziging 2008, art. 16, lid 1, sub b', 20090301, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (174, '173', 'Rijkswet Nederlanderschap 1984, ivm wijziging 2008, art. 26, lid 3', 20090301, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (175, '174', 'Rijkswet Nederlanderschap 1984, ivm wijziging 2008, art. 28, lid 3', 20090301, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (176, '175', 'Wijziging Rw. Nederl. Bep: Rw. 27-06-2008 art. II, lid 1, sub a (Stb. 2008, 270)', 20090301, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (177, '176', 'Wijziging Rw. Nederl. Bep: Rw. 27-06-2008 art. II, lid 1, sub b (Stb. 2008, 270)', 20090301, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (178, '177', 'Wijziging Rw. Nederl. Bep: Rw. 27-06-2008 art. II, lid 1, sub c (Stb. 2008, 270)', 20090301, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (179, '178', 'Wijziging Rw. Nederl. Bep: Rw. 27-06-2008 art. II, lid 2 (Stb. 2008, 270)', 20090301, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (180, '179', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2010, art. 6, lid 1, sub i', 20101001, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (181, '180', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2010, art. 6, lid 1, sub j', 20101001, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (182, '181', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2010, art. 6, lid 1, sub k', 20101001, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (183, '182', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2010, art. 6, lid 1, sub l', 20101001, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (184, '183', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2010, art. 6, lid 1, sub m', 20101001, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (185, '184', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2010, art. 6, lid 1, sub n', 20101001, NULL);
INSERT INTO Kern.RdnVerkNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (186, '185', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2010, art. 6, lid 1, sub o', 20101001, NULL);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (187, '186', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2010, art. 14, lid 2', 20101001, NULL);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (188, '187', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2010, art. 14, lid 4', 20101001, 20170301);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (189, '188', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2010, art. 15, lid 1, sub f', 20101001, 20170301);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (190, '189', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2017, art. 14, lid 3', 20170301, NULL);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (191, '190', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2017, art. 14, lid 4', 20170301, NULL);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (192, '191', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2017, art. 14, lid 6', 20170301, NULL);
INSERT INTO Kern.RdnVerliesNLNation(ID, Code, Oms, DatAanvGel, DatEindeGel) VALUES (193, '192', 'Rijkswet Nederlanderschap 1984 ivm wijziging 2017, art. 15, lid 1, sub e', 20170301, NULL);


--------------------------------------------------------------------------------
-- Stamgegeven: Conversie reden opname-beeindiging nationaliteit
--------------------------------------------------------------------------------
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (1, '000', 1);

INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (2, '001', 2);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (3, '002', 3);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (4, '003', 4);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (5, '004', 5);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (6, '005', 6);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (7, '006', 7);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (8, '007', 8);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (9, '008', 9);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (10, '009', 10);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (11, '010', 11);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (12, '011', 12);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (13, '012', 13);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (14, '013', 14);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (15, '014', 15);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (16, '015', 16);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (17, '016', 17);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (18, '017', 18);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (19, '018', 19);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (20, '019', 20);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (21, '020', 21);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (22, '021', 22);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (23, '022', 23);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (24, '023', 24);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (25, '024', 25);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (26, '025', 26);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (27, '026', 27);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (28, '027', 28);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (29, '028', 29);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (30, '029', 30);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (31, '030', 31);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (32, '031', 32);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (33, '032', 33);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (34, '033', 34);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (35, '034', 35);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (36, '035', 36);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (37, '036', 37);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (38, '037', 38);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (39, '038', 39);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (40, '039', 40);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (41, '040', 41);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (42, '041', 42);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (43, '042', 43);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (44, '043', 44);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (45, '044', 45);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (46, '045', 46);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (47, '046', 47);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (48, '047', 48);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (49, '048', 49);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (50, '049', 50);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (51, '050', 51);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (52, '051', 52);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (53, '052', 53);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (54, '053', 54);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (55, '054', 55);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (56, '055', 56);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (57, '056', 57);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (58, '057', 58);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (59, '058', 59);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (60, '059', 60);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (61, '060', 61);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (62, '061', 62);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (63, '062', 63);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (64, '063', 64);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (65, '064', 65);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (66, '065', 66);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (67, '066', 67);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (68, '067', 68);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (69, '068', 69);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (70, '069', 70);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (71, '070', 71);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (72, '071', 72);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (73, '072', 73);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (74, '073', 74);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (75, '074', 75);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (76, '075', 76);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (77, '076', 77);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (78, '077', 78);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (79, '078', 79);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (80, '079', 80);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (81, '080', 81);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (82, '081', 82);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (83, '082', 83);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (84, '083', 84);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (85, '084', 85);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (86, '085', 86);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (87, '086', 87);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (88, '087', 88);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (89, '088', 89);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (90, '089', 90);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (91, '090', 91);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (92, '091', 92);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (93, '092', 93);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (94, '093', 94);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (95, '094', 95);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (96, '095', 96);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (97, '096', 97);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (98, '097', 98);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (99, '098', 99);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (100, '099', 100);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (101, '100', 101);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (102, '101', 102);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (103, '102', 103);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (104, '103', 104);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (105, '104', 105);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (106, '105', 106);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (107, '106', 107);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (108, '107', 108);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (109, '108', 109);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (110, '109', 110);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (111, '110', 111);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (112, '111', 112);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (113, '112', 113);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (114, '113', 114);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (115, '114', 115);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (116, '115', 116);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (117, '116', 117);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (118, '117', 118);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (119, '118', 119);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (120, '119', 120);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (121, '120', 121);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (122, '121', 122);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (123, '122', 123);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (124, '123', 124);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (125, '124', 125);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (126, '125', 126);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (127, '126', 127);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (128, '127', 128);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (129, '128', 129);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (130, '129', 130);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (131, '130', 131);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (132, '131', 132);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (133, '132', 133);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (134, '133', 134);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (135, '134', 135);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (136, '135', 136);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (137, '136', 137);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (138, '137', 138);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (139, '138', 139);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (140, '139', 140);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (141, '140', 141);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (142, '141', 142);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (143, '142', 143);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (144, '143', 144);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (145, '144', 145);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (146, '145', 146);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (147, '146', 147);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (148, '147', 148);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (149, '148', 149);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (150, '149', 150);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (151, '150', 151);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (152, '151', 152);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (153, '152', 153);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (154, '153', 154);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (155, '154', 155);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (156, '155', 156);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (157, '156', 157);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (158, '157', 158);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (159, '158', 159);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (160, '159', 160);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (161, '160', 161);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (162, '161', 162);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (163, '162', 163);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (164, '163', 164);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (165, '164', 165);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (166, '165', 166);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (167, '166', 167);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (168, '167', 168);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (169, '168', 169);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (170, '169', 170);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (171, '170', 171);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (172, '171', 172);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (173, '172', 173);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (174, '173', 174);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (175, '174', 175);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (176, '175', 176);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (177, '176', 177);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (178, '177', 178);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (179, '178', 179);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (180, '179', 180);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (181, '180', 181);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (182, '181', 182);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (183, '182', 183);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (184, '183', 184);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (185, '184', 185);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (186, '185', 186);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (187, '186', 187);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (188, '187', 188);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (189, '188', 189);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (203, '189', 190);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (204, '190', 191);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (205, '191', 192);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (206, '192', 193);

INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (190, '000', NULL);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (191, '301', NULL);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (192, '310', NULL);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (193, '311', NULL);
INSERT INTO Conv.ConvRdnOpnameNation (ID, Rubr6310RdnOpnameNation, RdnVerk) VALUES (194, '312', NULL);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (195, '401', NULL);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (196, '402', NULL);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (197, '403', NULL);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (198, '404', NULL);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (199, '405', NULL);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (200, '410', NULL);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (201, '411', NULL);
INSERT INTO Conv.ConvRdnBeeindigenNation(ID, Rubr6410RdnBeeindigenNation, RdnVerlies) VALUES (202, '412', NULL);


--------------------------------------------------------------------------------
-- Stamgegeven: Voorvoegsel
--------------------------------------------------------------------------------
INSERT INTO Conv.ConvVoorvoegsel(ID, Rubr0231Voorvoegsel, Voorvoegsel, Scheidingsteken) VALUES
   (1, 'A', 'A', ' '),
   (2, 'a', 'a', ' '),
   (3, 'Aan', 'Aan', ' '),
   (4, 'aan', 'aan', ' '),
   (5, 'Aan de', 'Aan de', ' '),
   (6, 'aan de', 'aan de', ' '),
   (7, 'Aan den', 'Aan den', ' '),
   (8, 'aan den', 'aan den', ' '),
   (9, 'Aan der', 'Aan der', ' '),
   (10, 'aan der', 'aan der', ' '),
   (11, 'Aan het', 'Aan het', ' '),
   (12, 'aan het', 'aan het', ' '),
   (13, 'Aan t', 'Aan t', ' '),
   (14, 'aan t', 'aan t', ' '),
   (15, 'Aan ''t', 'Aan ''t', ' '),
   (16, 'aan ''t', 'aan ''t', ' '),
   (17, 'Af', 'Af', ' '),
   (18, 'af', 'af', ' '),
   (19, 'Al', 'Al', ' '),
   (20, 'al', 'al', ' '),
   (21, 'Am', 'Am', ' '),
   (22, 'am', 'am', ' '),
   (23, 'Am de', 'Am de', ' '),
   (24, 'am de', 'am de', ' '),
   (25, 'Auf', 'Auf', ' '),
   (26, 'auf', 'auf', ' '),
   (27, 'Auf dem', 'Auf dem', ' '),
   (28, 'auf dem', 'auf dem', ' '),
   (29, 'Auf den', 'Auf den', ' '),
   (30, 'auf den', 'auf den', ' '),
   (31, 'Auf der', 'Auf der', ' '),
   (32, 'auf der', 'auf der', ' '),
   (33, 'Auf ter', 'Auf ter', ' '),
   (34, 'auf ter', 'auf ter', ' '),
   (35, 'Aus', 'Aus', ' '),
   (36, 'aus', 'aus', ' '),
   (37, 'Aus dem', 'Aus dem', ' '),
   (38, 'aus dem', 'aus dem', ' '),
   (39, 'Aus den', 'Aus den', ' '),
   (40, 'aus den', 'aus den', ' '),
   (41, 'Aus der', 'Aus der', ' '),
   (42, 'aus der', 'aus der', ' '),
   (43, 'Aus m', 'Aus m', ' '),
   (44, 'aus m', 'aus m', ' '),
   (45, 'Aus ''m', 'Aus ''m', ' '),
   (46, 'aus ''m', 'aus ''m', ' '),
   (47, 'Ben', 'Ben', ' '),
   (48, 'ben', 'ben', ' '),
   (49, 'Bij', 'Bij', ' '),
   (50, 'bij', 'bij', ' '),
   (51, 'Bij de', 'Bij de', ' '),
   (52, 'bij de', 'bij de', ' '),
   (53, 'Bij den', 'Bij den', ' '),
   (54, 'bij den', 'bij den', ' '),
   (55, 'Bij het', 'Bij het', ' '),
   (56, 'bij het', 'bij het', ' '),
   (57, 'Bij t', 'Bij t', ' '),
   (58, 'bij t', 'bij t', ' '),
   (59, 'Bij ''t', 'Bij ''t', ' '),
   (60, 'bij ''t', 'bij ''t', ' '),
   (61, 'Bin', 'Bin', ' '),
   (62, 'bin', 'bin', ' '),
   (63, 'Boven d', 'Boven d', ' '),
   (64, 'boven d', 'boven d', ' '),
   (65, 'Boven d''', 'Boven d', ''''),
   (66, 'boven d''', 'boven d', ''''),
   (67, 'D', 'D', ' '),
   (68, 'd', 'd', ' '),
   (69, 'D''', 'D', ''''),
   (70, 'd''', 'd', ''''),
   (71, 'Da', 'Da', ' '),
   (72, 'da', 'da', ' '),
   (73, 'Dal', 'Dal', ' '),
   (74, 'dal', 'dal', ' '),
   (75, 'Dal''', 'Dal', ''''),
   (76, 'dal''', 'dal', ''''),
   (77, 'Dalla', 'Dalla', ' '),
   (78, 'dalla', 'dalla', ' '),
   (79, 'Das', 'Das', ' '),
   (80, 'das', 'das', ' '),
   (81, 'De', 'De', ' '),
   (82, 'de', 'de', ' '),
   (83, 'De die', 'De die', ' '),
   (84, 'de die', 'de die', ' '),
   (85, 'De die le', 'De die le', ' '),
   (86, 'de die le', 'de die le', ' '),
   (87, 'De l', 'De l', ' '),
   (88, 'de l', 'de l', ' '),
   (89, 'De l''', 'De l', ''''),
   (90, 'de l''', 'de l', ''''),
   (91, 'De la', 'De la', ' '),
   (92, 'de la', 'de la', ' '),
   (93, 'De las', 'De las', ' '),
   (94, 'de las', 'de las', ' '),
   (95, 'De le', 'De le', ' '),
   (96, 'de le', 'de le', ' '),
   (97, 'De van der', 'De van der', ' '),
   (98, 'de van der', 'de van der', ' '),
   (99, 'Deca', 'Deca', ' '),
   (100, 'deca', 'deca', ' '),
   (101, 'Degli', 'Degli', ' '),
   (102, 'degli', 'degli', ' '),
   (103, 'Dei', 'Dei', ' '),
   (104, 'dei', 'dei', ' '),
   (105, 'Del', 'Del', ' '),
   (106, 'del', 'del', ' '),
   (107, 'Della', 'Della', ' '),
   (108, 'della', 'della', ' '),
   (109, 'Den', 'Den', ' '),
   (110, 'den', 'den', ' '),
   (111, 'Der', 'Der', ' '),
   (112, 'der', 'der', ' '),
   (113, 'Des', 'Des', ' '),
   (114, 'des', 'des', ' '),
   (115, 'Di', 'Di', ' '),
   (116, 'di', 'di', ' '),
   (117, 'Die le', 'Die le', ' '),
   (118, 'die le', 'die le', ' '),
   (119, 'Do', 'Do', ' '),
   (120, 'do', 'do', ' '),
   (121, 'Don', 'Don', ' '),
   (122, 'don', 'don', ' '),
   (123, 'Dos', 'Dos', ' '),
   (124, 'dos', 'dos', ' '),
   (125, 'Du', 'Du', ' '),
   (126, 'du', 'du', ' '),
   (127, 'El', 'El', ' '),
   (128, 'el', 'el', ' '),
   (129, 'Het', 'Het', ' '),
   (130, 'het', 'het', ' '),
   (131, 'I', 'I', ' '),
   (132, 'i', 'i', ' '),
   (133, 'Im', 'Im', ' '),
   (134, 'im', 'im', ' '),
   (135, 'In', 'In', ' '),
   (136, 'in', 'in', ' '),
   (137, 'In de', 'In de', ' '),
   (138, 'in de', 'in de', ' '),
   (139, 'In den', 'In den', ' '),
   (140, 'in den', 'in den', ' '),
   (141, 'In der', 'In der', ' '),
   (142, 'in der', 'in der', ' '),
   (143, 'In het', 'In het', ' '),
   (144, 'in het', 'in het', ' '),
   (145, 'In t', 'In t', ' '),
   (146, 'in t', 'in t', ' '),
   (147, 'In ''t', 'In ''t', ' '),
   (148, 'in ''t', 'in ''t', ' '),
   (149, 'L', 'L', ' '),
   (150, 'l', 'l', ' '),
   (151, 'L''', 'L', ''''),
   (152, 'l''', 'l', ''''),
   (153, 'La', 'La', ' '),
   (154, 'la', 'la', ' '),
   (155, 'Las', 'Las', ' '),
   (156, 'las', 'las', ' '),
   (157, 'Le', 'Le', ' '),
   (158, 'le', 'le', ' '),
   (159, 'Les', 'Les', ' '),
   (160, 'les', 'les', ' '),
   (161, 'Lo', 'Lo', ' '),
   (162, 'lo', 'lo', ' '),
   (163, 'Los', 'Los', ' '),
   (164, 'los', 'los', ' '),
   (165, 'Of', 'Of', ' '),
   (166, 'of', 'of', ' '),
   (167, 'Onder', 'Onder', ' '),
   (168, 'onder', 'onder', ' '),
   (169, 'Onder de', 'Onder de', ' '),
   (170, 'onder de', 'onder de', ' '),
   (171, 'Onder den', 'Onder den', ' '),
   (172, 'onder den', 'onder den', ' '),
   (173, 'Onder het', 'Onder het', ' '),
   (174, 'onder het', 'onder het', ' '),
   (175, 'Onder t', 'Onder t', ' '),
   (176, 'onder t', 'onder t', ' '),
   (177, 'Onder ''t', 'Onder ''t', ' '),
   (178, 'onder ''t', 'onder ''t', ' '),
   (179, 'Op', 'Op', ' '),
   (180, 'op', 'op', ' '),
   (181, 'Op de', 'Op de', ' '),
   (182, 'op de', 'op de', ' '),
   (183, 'Op den', 'Op den', ' '),
   (184, 'op den', 'op den', ' '),
   (185, 'Op der', 'Op der', ' '),
   (186, 'op der', 'op der', ' '),
   (187, 'Op gen', 'Op gen', ' '),
   (188, 'op gen', 'op gen', ' '),
   (189, 'Op het', 'Op het', ' '),
   (190, 'op het', 'op het', ' '),
   (191, 'Op t', 'Op t', ' '),
   (192, 'op t', 'op t', ' '),
   (193, 'Op ''t', 'Op ''t', ' '),
   (194, 'op ''t', 'op ''t', ' '),
   (195, 'Op ten', 'Op ten', ' '),
   (196, 'op ten', 'op ten', ' '),
   (197, 'Over', 'Over', ' '),
   (198, 'over', 'over', ' '),
   (199, 'Over de', 'Over de', ' '),
   (200, 'over de', 'over de', ' '),
   (201, 'Over den', 'Over den', ' '),
   (202, 'over den', 'over den', ' '),
   (203, 'Over het', 'Over het', ' '),
   (204, 'over het', 'over het', ' '),
   (205, 'Over t', 'Over t', ' '),
   (206, 'over t', 'over t', ' '),
   (207, 'Over ''t', 'Over ''t', ' '),
   (208, 'over ''t', 'over ''t', ' '),
   (209, 'S', 'S', ' '),
   (210, 's', 's', ' '),
   (211, '''S', '''S', ' '),
   (212, '''s', '''s', ' '),
   (213, 'S''', 'S', ''''),
   (214, 's''', 's', ''''),
   (215, 'T', 'T', ' '),
   (216, 't', 't', ' '),
   (217, '''T', '''T', ' '),
   (218, '''t', '''t', ' '),
   (219, 'Te', 'Te', ' '),
   (220, 'te', 'te', ' '),
   (221, 'Ten', 'Ten', ' '),
   (222, 'ten', 'ten', ' '),
   (223, 'Ter', 'Ter', ' '),
   (224, 'ter', 'ter', ' '),
   (225, 'Tho', 'Tho', ' '),
   (226, 'tho', 'tho', ' '),
   (227, 'Thoe', 'Thoe', ' '),
   (228, 'thoe', 'thoe', ' '),
   (229, 'Thor', 'Thor', ' '),
   (230, 'thor', 'thor', ' '),
   (231, 'To', 'To', ' '),
   (232, 'to', 'to', ' '),
   (233, 'Toe', 'Toe', ' '),
   (234, 'toe', 'toe', ' '),
   (235, 'Tot', 'Tot', ' '),
   (236, 'tot', 'tot', ' '),
   (237, 'Uijt', 'Uijt', ' '),
   (238, 'uijt', 'uijt', ' '),
   (239, 'Uijt de', 'Uijt de', ' '),
   (240, 'uijt de', 'uijt de', ' '),
   (241, 'Uijt den', 'Uijt den', ' '),
   (242, 'uijt den', 'uijt den', ' '),
   (243, 'Uijt ''t', 'Uijt ''t', ' '),
   (244, 'uijt ''t', 'uijt ''t', ' '),
   (245, 'Uijt te de', 'Uijt te de', ' '),
   (246, 'uijt te de', 'uijt te de', ' '),
   (247, 'Uijt ten', 'Uijt ten', ' '),
   (248, 'uijt ten', 'uijt ten', ' '),
   (249, 'Uit', 'Uit', ' '),
   (250, 'uit', 'uit', ' '),
   (251, 'Uit de', 'Uit de', ' '),
   (252, 'uit de', 'uit de', ' '),
   (253, 'Uit den', 'Uit den', ' '),
   (254, 'uit den', 'uit den', ' '),
   (255, 'Uit het', 'Uit het', ' '),
   (256, 'uit het', 'uit het', ' '),
   (257, 'Uit t', 'Uit t', ' '),
   (258, 'uit t', 'uit t', ' '),
   (259, 'Uit ''t', 'Uit ''t', ' '),
   (260, 'uit ''t', 'uit ''t', ' '),
   (261, 'Uit te de', 'Uit te de', ' '),
   (262, 'uit te de', 'uit te de', ' '),
   (263, 'Uit ten', 'Uit ten', ' '),
   (264, 'uit ten', 'uit ten', ' '),
   (265, 'Unter', 'Unter', ' '),
   (266, 'unter', 'unter', ' '),
   (267, 'Van', 'Van', ' '),
   (268, 'van', 'van', ' '),
   (269, 'Van de', 'Van de', ' '),
   (270, 'van De', 'van De', ' '),
   (271, 'van de', 'van de', ' '),
   (272, 'Van de l', 'Van de l', ' '),
   (273, 'van de l', 'van de l', ' '),
   (274, 'Van de l''', 'Van de l', ''''),
   (275, 'van de l''', 'van de l', ''''),
   (276, 'Van Den', 'Van Den', ' '),
   (277, 'Van den', 'Van den', ' '),
   (278, 'van den', 'van den', ' '),
   (279, 'Van Der', 'Van Der', ' '),
   (280, 'Van der', 'Van der', ' '),
   (281, 'van der', 'van der', ' '),
   (282, 'Van gen', 'Van gen', ' '),
   (283, 'van gen', 'van gen', ' '),
   (284, 'Van het', 'Van het', ' '),
   (285, 'van het', 'van het', ' '),
   (286, 'Van la', 'Van la', ' '),
   (287, 'van la', 'van la', ' '),
   (288, 'Van t', 'Van t', ' '),
   (289, 'van t', 'van t', ' '),
   (290, 'Van ''t', 'Van ''t', ' '),
   (291, 'van ''t', 'van ''t', ' '),
   (292, 'Van ter', 'Van ter', ' '),
   (293, 'van ter', 'van ter', ' '),
   (294, 'Van van de', 'Van van de', ' '),
   (295, 'van van de', 'van van de', ' '),
   (296, 'Ver', 'Ver', ' '),
   (297, 'ver', 'ver', ' '),
   (298, 'Vom', 'Vom', ' '),
   (299, 'vom', 'vom', ' '),
   (300, 'Von', 'Von', ' '),
   (301, 'von', 'von', ' '),
   (302, 'Von dem', 'Von dem', ' '),
   (303, 'von dem', 'von dem', ' '),
   (304, 'Von den', 'Von den', ' '),
   (305, 'von den', 'von den', ' '),
   (306, 'Von der', 'Von der', ' '),
   (307, 'von der', 'von der', ' '),
   (308, 'Von t', 'Von t', ' '),
   (309, 'von t', 'von t', ' '),
   (310, 'Von ''t', 'Von ''t', ' '),
   (311, 'von ''t', 'von ''t', ' '),
   (312, 'Voor', 'Voor', ' '),
   (313, 'voor', 'voor', ' '),
   (314, 'Voor de', 'Voor de', ' '),
   (315, 'voor de', 'voor de', ' '),
   (316, 'Voor den', 'Voor den', ' '),
   (317, 'voor den', 'voor den', ' '),
   (318, 'Voor in t', 'Voor in t', ' '),
   (319, 'voor in t', 'voor in t', ' '),
   (320, 'Voor in ''t', 'Voor in ''t', ' '),
   (321, 'voor in ''t', 'voor in ''t', ' '),
   (322, 'Voor ''t', 'Voor ''t', ' '),
   (323, 'voor ''t', 'voor ''t', ' '),
   (324, 'Vor', 'Vor', ' '),
   (325, 'vor', 'vor', ' '),
   (326, 'Vor der', 'Vor der', ' '),
   (327, 'vor der', 'vor der', ' '),
   (328, 'Zu', 'Zu', ' '),
   (329, 'zu', 'zu', ' '),
   (330, 'Zum', 'Zum', ' '),
   (331, 'zum', 'zum', ' '),
   (332, 'Zur', 'Zur', ' '),
   (333, 'zur', 'zur', ' ');

INSERT INTO Kern.Voorvoegsel (ID, Voorvoegsel, Scheidingsteken) SELECT ID, Voorvoegsel, Scheidingsteken FROM Conv.ConvVoorvoegsel;


--------------------------------------------------------------------------------
-- Stamgegeven: Plaats
--------------------------------------------------------------------------------
-- Bron voor plaatsen: 9999WPL08072017-000001.xml
-- Datum/tijd genereren vulling: Tue Jul 11 09:25:30 CEST 2017
INSERT INTO Kern.Plaats (Naam) VALUES
   ('Hoogerheide')
  ,('Huijbergen')
  ,('Ossendrecht')
  ,('Putte')
  ,('Woensdrecht')
  ,('Gouda')
  ,('Waalre')
  ,('Middelburg')
  ,('Arnemuiden')
  ,('Nieuw- en Sint Joosland')
  ,('Etten-Leur')
  ,('Huizen')
  ,('Weesp')
  ,('Soest')
  ,('Soesterberg')
  ,('Vlaardingen')
  ,('Nieuwerkerk aan den IJssel')
  ,('Foxhol')
  ,('Hoogezand')
  ,('Kiel-Windeweer')
  ,('Kropswolde')
  ,('Sappemeer')
  ,('Waterhuizen')
  ,('Westerbroek')
  ,('Amsterdam')
  ,('Amsterdam Zuidoost')
  ,('America')
  ,('Broekhuizen')
  ,('Broekhuizenvorst')
  ,('Griendtsveen')
  ,('Grubbenvorst')
  ,('Hegelsom')
  ,('Horst')
  ,('Lottum')
  ,('Melderslo')
  ,('Meterik')
  ,('Hilversum')
  ,('Eemnes')
  ,('Daarle')
  ,('Daarlerveen')
  ,('Haarle')
  ,('Hellendoorn')
  ,('Nijverdal')
  ,('Tilburg')
  ,('Berkel-Enschot')
  ,('Udenhout')
  ,('Staphorst')
  ,('Rouveen')
  ,('IJhorst')
  ,('Punthorst')
  ,('Amstelveen')
  ,('Lelystad')
  ,('Naarden')
  ,('Andijk')
  ,('Barneveld')
  ,('Voorthuizen')
  ,('Kootwijkerbroek')
  ,('Garderen')
  ,('Terschuur')
  ,('Stroe')
  ,('Zwartebroek')
  ,('De Glind')
  ,('Kootwijk')
  ,('Achterveld')
  ,('Bladel')
  ,('Hapert')
  ,('Hoogeloon')
  ,('Casteren')
  ,('Netersel')
  ,('Maassluis')
  ,('Groningen')
  ,('Woerden')
  ,('Harmelen')
  ,('Kamerik')
  ,('Zegveld')
  ,('Zeewolde')
  ,('Oost-Souburg')
  ,('Ritthem')
  ,('Vlissingen')
  ,('Aarlanderveen')
  ,('Alphen aan den Rijn')
  ,('Zwammerdam')
  ,('Meppel')
  ,('Nijeveen')
  ,('Rogat')
  ,('De Schiphorst')
  ,('Wierden')
  ,('Enter')
  ,('Hoge Hexel')
  ,('Notter')
  ,('Zuna')
  ,('Baarn')
  ,('Lage Vuursche')
  ,('Dodewaard')
  ,('Echteld')
  ,('Kesteren')
  ,('Ochten')
  ,('Opheusden')
  ,('IJzendoorn')
  ,('Valkenswaard')
  ,('Eindhoven')
  ,('Bovenkarspel')
  ,('Grootebroek')
  ,('Lutjebroek')
  ,('Capelle aan den IJssel')
  ,('Wervershoof')
  ,('Zwaagdijk')
  ,('Zwaagdijk-Oost')
  ,('Nieuwegein')
  ,('Empe')
  ,('Tonden')
  ,('Brummen')
  ,('Leuvenheim')
  ,('Hall')
  ,('Eerbeek')
  ,('Sint Pancras')
  ,('Broek op Langedijk')
  ,('Zuid-Scharwoude')
  ,('Noord-Scharwoude')
  ,('Oudkarspel')
  ,('Koedijk')
  ,('Bergen L')
  ,('Afferden L')
  ,('Siebengewald')
  ,('Well L')
  ,('Wellerlooi')
  ,('Geldrop')
  ,('Mierlo')
  ,('Axel')
  ,('Biervliet')
  ,('Hoek')
  ,('Koewacht')
  ,('Overslag')
  ,('Philippine')
  ,('Sas van Gent')
  ,('Sluiskil')
  ,('Spui')
  ,('Terneuzen')
  ,('Westdorpe')
  ,('Zaamslag')
  ,('Zuiddorpe')
  ,('Pijnacker')
  ,('Nootdorp')
  ,('Delfgauw')
  ,('Krimpen aan den IJssel')
  ,('Enschede')
  ,('Helmond')
  ,('Amen')
  ,('Anderen')
  ,('Anloo')
  ,('Annen')
  ,('Annerveenschekanaal')
  ,('Balloo')
  ,('Balloërveld')
  ,('Deurze')
  ,('Eext')
  ,('Eexterveen')
  ,('Eexterveenschekanaal')
  ,('Eexterzandvoort')
  ,('Ekehaar')
  ,('Eldersloo')
  ,('Eleveld')
  ,('Gasselte')
  ,('Gasselternijveen')
  ,('Gasselternijveenschemond')
  ,('Gasteren')
  ,('Geelbroek')
  ,('Gieten')
  ,('Gieterveen')
  ,('Grolloo')
  ,('Marwijksoord')
  ,('Nieuw Annerveen')
  ,('Nieuwediep')
  ,('Nijlande')
  ,('Nooitgedacht')
  ,('Oud Annerveen')
  ,('Papenvoort')
  ,('Rolde')
  ,('Schipborg')
  ,('Schoonloo')
  ,('Spijkerboor')
  ,('Vredenheim')
  ,('Zwolle')
  ,('Avenhorn')
  ,('Berkhout')
  ,('De Goorn')
  ,('Hensbroek')
  ,('Obdam')
  ,('Oudendijk')
  ,('Scharwoude')
  ,('Spierdijk')
  ,('Ursem')
  ,('Zuidermeer')
  ,('Aalten')
  ,('Bredevoort')
  ,('De Heurne')
  ,('Dinxperlo')
  ,('Leeuwarden')
  ,('Lekkum')
  ,('Miedum')
  ,('Snakkerburen')
  ,('Goutum')
  ,('Hempens')
  ,('Teerns')
  ,('Swichum')
  ,('Wirdum')
  ,('Wytgaard')
  ,('Oirschot')
  ,('Oost West en Middelbeers')
  ,('Oostvoorne')
  ,('Rockanje')
  ,('Tinte')
  ,('Moerkapelle')
  ,('Zevenhuizen')
  ,('Beugen')
  ,('Boxmeer')
  ,('Groeningen')
  ,('Holthees')
  ,('Maashees')
  ,('Oeffelt')
  ,('Overloon')
  ,('Rijkevoort')
  ,('Sambeek')
  ,('Vierlingsbeek')
  ,('Vortum-Mullem')
  ,('Gulpen')
  ,('Ingber')
  ,('Reijmerstok')
  ,('Heijenrath')
  ,('Slenaken')
  ,('Beutenaken')
  ,('Mechelen')
  ,('Epen')
  ,('Wittem')
  ,('Eys')
  ,('Elkenrade')
  ,('Wijlre')
  ,('Dalfsen')
  ,('Lemelerveld')
  ,('Nieuwleusen')
  ,('Veendam')
  ,('Wildervank')
  ,('Borgercompagnie')
  ,('Heiloo')
  ,('Moordrecht')
  ,('''s-Gravenhage')
  ,('Oudenbosch')
  ,('Stampersgat')
  ,('Oud Gastel')
  ,('Bosschenhoofd')
  ,('Hoeven')
  ,('Baarland')
  ,('Borssele')
  ,('Driewegen')
  ,('Ellewoutsdijk')
  ,('Heinkenszand')
  ,('Hoedekenskerke')
  ,('Kwadendamme')
  ,('Lewedorp')
  ,('Nieuwdorp')
  ,('Nisse')
  ,('Oudelande')
  ,('Ovezande')
  ,('''s-Gravenpolder')
  ,('''s-Heer Abtskerke')
  ,('''s-Heerenhoek')
  ,('Haren Gn')
  ,('Glimmen')
  ,('Noordlaren')
  ,('Onnen')
  ,('Almere')
  ,('Emmeloord')
  ,('Bant')
  ,('Luttelgeest')
  ,('Marknesse')
  ,('Kraggenburg')
  ,('Ens')
  ,('Schokland')
  ,('Nagele')
  ,('Tollebeek')
  ,('Espel')
  ,('Creil')
  ,('Rutten')
  ,('Albergen')
  ,('Fleringen')
  ,('Geesteren')
  ,('Harbrinkhoek')
  ,('Hezingen')
  ,('Langeveen')
  ,('Mander')
  ,('Manderveen')
  ,('Mariaparochie')
  ,('Reutum')
  ,('Tubbergen')
  ,('Vasse')
  ,('Arnhem')
  ,('Zevenaar')
  ,('Babberich')
  ,('Angerlo')
  ,('Giesbeek')
  ,('Lathum')
  ,('Wijk bij Duurstede')
  ,('Langbroek')
  ,('Cothen')
  ,('Zandvoort')
  ,('Bentveld')
  ,('Hilvarenbeek')
  ,('Diessen')
  ,('Biest-Houtakker')
  ,('Haghorst')
  ,('Esbeek')
  ,('Heerlen')
  ,('Hoensbroek')
  ,('Heeze')
  ,('Leende')
  ,('Sterksel')
  ,('Willemstad')
  ,('Heijningen')
  ,('Klundert')
  ,('Oudemolen')
  ,('Fijnaart')
  ,('Moerdijk')
  ,('Zevenbergen')
  ,('Standdaarbuiten')
  ,('Noordhoek')
  ,('Langeweg')
  ,('Zevenbergschen Hoek')
  ,('Landsmeer')
  ,('Den Ilp')
  ,('Purmerland')
  ,('Bussum')
  ,('Abcoude')
  ,('Baambrugge')
  ,('Almen')
  ,('Barchem')
  ,('Eefde')
  ,('Epse')
  ,('Gorssel')
  ,('Harfsen')
  ,('Joppe')
  ,('Kring van Dorth')
  ,('Laren')
  ,('Lochem')
  ,('Epe')
  ,('Vaassen')
  ,('Emst')
  ,('Oene')
  ,('Froombosch')
  ,('Harkstede')
  ,('Hellum')
  ,('Kolham')
  ,('Lageland')
  ,('Luddeweer')
  ,('Overschild')
  ,('Scharmer')
  ,('Schildwolde')
  ,('Siddeburen')
  ,('Slochteren')
  ,('Steendam')
  ,('Tjuchem')
  ,('Woudbloem')
  ,('Tzum')
  ,('Hitzum')
  ,('Achlum')
  ,('Herbaijum')
  ,('Franeker')
  ,('Zweins')
  ,('Peins')
  ,('Schalsum')
  ,('Ried')
  ,('Boer')
  ,('Dongjum')
  ,('Sexbierum')
  ,('Pietersbierum')
  ,('Oosterbierum')
  ,('Klooster Lidlum')
  ,('Tzummarum')
  ,('Firdgum')
  ,('Olst')
  ,('Wijhe')
  ,('Wesepe')
  ,('Welsum')
  ,('Marle')
  ,('Veldhoven')
  ,('Abbenbroek')
  ,('Geervliet')
  ,('Heenvliet')
  ,('Oudenhoorn')
  ,('Simonshaven')
  ,('Zuidland')
  ,('Bennebroek')
  ,('Breukelen')
  ,('Kockengen')
  ,('Nieuwer Ter Aa')
  ,('Zutphen')
  ,('Warnsveld')
  ,('Oude Pekela')
  ,('Nieuwe Pekela')
  ,('Rhenen')
  ,('Elst Ut')
  ,('De Heen')
  ,('Dinteloord')
  ,('Kruisland')
  ,('Nieuw-Vossemeer')
  ,('Steenbergen')
  ,('Maastricht')
  ,('Agelo')
  ,('Denekamp')
  ,('Deurningen')
  ,('Lattrop-Breklenkamp')
  ,('Nutter')
  ,('Ootmarsum')
  ,('Oud Ootmarsum')
  ,('Rossum')
  ,('Saasveld')
  ,('Tilligte')
  ,('Weerselo')
  ,('Beerta')
  ,('Finsterwolde')
  ,('Bad Nieuweschans')
  ,('Nieuw Beerta')
  ,('Drieborg')
  ,('Oudezijl')
  ,('Biezenmortel')
  ,('Esch')
  ,('Haaren')
  ,('Helvoirt')
  ,('Vaals')
  ,('Lemiers')
  ,('Vijlen')
  ,('Arkel')
  ,('Giessenburg')
  ,('Hoogblokland')
  ,('Hoornaar')
  ,('Noordeloos')
  ,('Schelluinen')
  ,('Ambt Delden')
  ,('Bentelo')
  ,('Delden')
  ,('Diepenheim')
  ,('Goor')
  ,('Hengevelde')
  ,('Markelo')
  ,('Aarle-Rixtel')
  ,('Beek en Donk')
  ,('Lieshout')
  ,('Mariahout')
  ,('Beers NB')
  ,('Cuijk')
  ,('Haps')
  ,('Katwijk NB')
  ,('Linden')
  ,('Sint Agatha')
  ,('Vianen NB')
  ,('Best')
  ,('Bergeijk')
  ,('Luyksgestel')
  ,('Riethoven')
  ,('Westerhoven')
  ,('Bunde')
  ,('Geulle')
  ,('Meerssen')
  ,('Moorveld')
  ,('Ulestraten')
  ,('Lisse')
  ,('Kapelle')
  ,('Kloetinge')
  ,('Schore')
  ,('Wemeldinge')
  ,('Winschoten')
  ,('Venlo')
  ,('Tegelen')
  ,('Steyl')
  ,('Belfeld')
  ,('Papendrecht')
  ,('Winterswijk')
  ,('Winterswijk Meddo')
  ,('Winterswijk Huppel')
  ,('Winterswijk Ratum')
  ,('Winterswijk Kotten')
  ,('Winterswijk Woold')
  ,('Winterswijk Miste')
  ,('Winterswijk Henxel')
  ,('Winterswijk Brinkheurne')
  ,('Winterswijk Corle')
  ,('Hippolytushoef')
  ,('Den Oever')
  ,('Westerland')
  ,('Raalte')
  ,('Heino')
  ,('Mariënheem')
  ,('Luttenberg')
  ,('Laag Zuthem')
  ,('Lierderholthuis')
  ,('Broekland')
  ,('Heeten')
  ,('Nieuw Heeten')
  ,('Velp')
  ,('Rheden')
  ,('De Steeg')
  ,('Ellecom')
  ,('Dieren')
  ,('Spankeren')
  ,('Laag-Soeren')
  ,('Hem')
  ,('Hoogkarspel')
  ,('Oosterblokker')
  ,('Oosterleek')
  ,('Schellinkhout')
  ,('Venhuizen')
  ,('Westwoud')
  ,('Wijdenes')
  ,('Sevenum')
  ,('Kronenberg')
  ,('Evertsoord')
  ,('Muiden')
  ,('Muiderberg')
  ,('Drempt')
  ,('Hoog-Keppel')
  ,('Laag-Keppel')
  ,('Hummelo')
  ,('Zelhem')
  ,('Halle')
  ,('Steenderen')
  ,('Baak')
  ,('Rha')
  ,('Olburgen')
  ,('Bronkhorst')
  ,('Toldijk')
  ,('Vierakker')
  ,('Wichmond')
  ,('Vorden')
  ,('Hengelo (Gld)')
  ,('Keijenborg')
  ,('Ane')
  ,('Anerveen')
  ,('Anevelde')
  ,('Balkbrug')
  ,('Bergentheim')
  ,('Brucht')
  ,('Bruchterveld')
  ,('Collendoorn')
  ,('De Krim')
  ,('Dedemsvaart')
  ,('Den Velde')
  ,('Diffelen')
  ,('Gramsbergen')
  ,('Hardenberg')
  ,('Heemserveen')
  ,('Holtheme')
  ,('Holthone')
  ,('Hoogenweg')
  ,('Kloosterhaar')
  ,('Loozen')
  ,('Lutten')
  ,('Mariënberg')
  ,('Radewijk')
  ,('Rheeze')
  ,('Rheezerveen')
  ,('Schuinesloot')
  ,('Sibculo')
  ,('Slagharen')
  ,('Venebrugge')
  ,('Rijssen')
  ,('Holten')
  ,('Maarheeze')
  ,('Soerendonk')
  ,('Gastel')
  ,('Budel')
  ,('Budel-Schoot')
  ,('Budel-Dorplein')
  ,('Varsseveld')
  ,('Westendorp')
  ,('Heelweg')
  ,('Terborg')
  ,('Silvolde')
  ,('Sinderen')
  ,('Ulft')
  ,('Etten')
  ,('Varsselder')
  ,('Netterden')
  ,('Megchelen')
  ,('Gendringen')
  ,('Voorst')
  ,('Breedenbroek')
  ,('Roosendaal')
  ,('Wouw')
  ,('Heerle')
  ,('Nispen')
  ,('Wouwse Plantage')
  ,('Moerstraten')
  ,('Barendrecht')
  ,('''s-Hertogenbosch')
  ,('Rosmalen')
  ,('Hoofddorp')
  ,('Rozenburg')
  ,('Oude Meer')
  ,('Aalsmeerderbrug')
  ,('Rijsenhout')
  ,('Nieuw-Vennep')
  ,('Burgerveen')
  ,('Schiphol-Rijk')
  ,('Leimuiderbrug')
  ,('Weteringbrug')
  ,('Buitenkaag')
  ,('Abbenes')
  ,('Lisserbroek')
  ,('Beinsdorp')
  ,('Zwaanshoek')
  ,('Cruquius')
  ,('Vijfhuizen')
  ,('Zwanenburg')
  ,('Boesingheliede')
  ,('Lijnden')
  ,('Badhoevedorp')
  ,('Schiphol')
  ,('Vught')
  ,('Cromvoirt')
  ,('Leidschendam')
  ,('Voorburg')
  ,('Alem')
  ,('Ammerzoden')
  ,('Hedel')
  ,('Heerewaarden')
  ,('Hoenzadriel')
  ,('Hurwenen')
  ,('Kerkdriel')
  ,('Velddriel')
  ,('Well')
  ,('Alteveer')
  ,('Een')
  ,('Een-West')
  ,('Foxwolde')
  ,('Huis ter Heide')
  ,('Langelo')
  ,('Leutingewolde')
  ,('Lieveren')
  ,('Matsloot')
  ,('Nietap')
  ,('Nieuw-Roden')
  ,('Norg')
  ,('Peest')
  ,('Peize')
  ,('Roden')
  ,('Roderesch')
  ,('Roderwolde')
  ,('Veenhuizen')
  ,('Westervelde')
  ,('Zuidvelde')
  ,('Gelselaar')
  ,('Neede')
  ,('Rietmolen')
  ,('Ruurlo')
  ,('Borculo')
  ,('Haarlo')
  ,('Beltrum')
  ,('Eibergen')
  ,('Rekken')
  ,('Amersfoort')
  ,('Hoogland')
  ,('Hooglanderveen')
  ,('Stoutenburg Noord')
  ,('Oldenzaal')
  ,('Beek')
  ,('Spaubeek')
  ,('Maastricht-Airport')
  ,('Bakhuizen')
  ,('Balk')
  ,('Elahuizen')
  ,('Harich')
  ,('Kolderwolde')
  ,('Mirns')
  ,('Nijemirdum')
  ,('Oudega')
  ,('Oudemirdum')
  ,('Rijs')
  ,('Ruigahuizen')
  ,('Sloten')
  ,('Sondel')
  ,('Wijckel')
  ,('Berkel en Rodenrijs')
  ,('Bergschenhoek')
  ,('Bleiswijk')
  ,('Banholt')
  ,('Bemelen')
  ,('Cadier en Keer')
  ,('Eckelrade')
  ,('Margraten')
  ,('Mheer')
  ,('Noorbeek')
  ,('Scheulder')
  ,('Sint Geertruid')
  ,('Kessel')
  ,('Eijsden')
  ,('Gronsveld')
  ,('Breda')
  ,('Bavel')
  ,('Ulvenhout')
  ,('Prinsenbeek')
  ,('Teteringen')
  ,('Katwijk')
  ,('Rijnsburg')
  ,('Valkenburg')
  ,('Hillegom')
  ,('Beuningen')
  ,('Glane')
  ,('Losser')
  ,('de Lutte')
  ,('Overdinkel')
  ,('Berg en Terblijt')
  ,('Schin op Geul')
  ,('Walem')
  ,('Blesdijke')
  ,('Boijl')
  ,('De Blesse')
  ,('De Hoeve')
  ,('Langelille')
  ,('Munnekeburen')
  ,('Nijeholtpade')
  ,('Nijeholtwolde')
  ,('Nijelamer')
  ,('Nijetrijne')
  ,('Noordwolde')
  ,('Oldeholtpade')
  ,('Oldeholtwolde')
  ,('Oldelamer')
  ,('Oldetrijne')
  ,('Oosterstreek')
  ,('Peperga')
  ,('Scherpenzeel')
  ,('Slijkenburg')
  ,('Sonnega')
  ,('Spanga')
  ,('Steggerda')
  ,('Ter Idzard')
  ,('Vinkega')
  ,('Wolvega')
  ,('Zandhuizen')
  ,('Heerhugowaard')
  ,('Edam')
  ,('Volendam')
  ,('Purmer')
  ,('Raamsdonksveer')
  ,('Geertruidenberg')
  ,('Raamsdonk')
  ,('Appelscha')
  ,('Donkerbroek')
  ,('Elsloo')
  ,('Fochteloo')
  ,('Haule')
  ,('Haulerwijk')
  ,('Langedijke')
  ,('Makkinga')
  ,('Nijeberkoop')
  ,('Oldeberkoop')
  ,('Oosterwolde')
  ,('Ravenswoud')
  ,('Waskemeer')
  ,('Alphen')
  ,('Altforst')
  ,('Appeltern')
  ,('Beneden-Leeuwen')
  ,('Boven-Leeuwen')
  ,('Dreumel')
  ,('Maasbommel')
  ,('Wamel')
  ,('Deurne')
  ,('Vlierden')
  ,('Liessel')
  ,('Neerkant')
  ,('Helenaveen')
  ,('1e Exloërmond')
  ,('2e Exloërmond')
  ,('2e Valthermond')
  ,('Borger')
  ,('Bronneger')
  ,('Bronnegerveen')
  ,('Buinen')
  ,('Buinerveen')
  ,('Drouwen')
  ,('Drouwenermond')
  ,('Drouwenerveen')
  ,('Ees')
  ,('Eesergroen')
  ,('Eeserveen')
  ,('Ellertshaar')
  ,('Exloërveen')
  ,('Exloo')
  ,('Klijndijk')
  ,('Nieuw-Buinen')
  ,('Odoorn')
  ,('Odoornerveen')
  ,('Valthe')
  ,('Valthermond')
  ,('Westdorp')
  ,('Zandberg')
  ,('Hengelo')
  ,('Streefkerk')
  ,('Groot-Ammers')
  ,('Nieuwpoort')
  ,('Langerak')
  ,('Waal')
  ,('Alteveer gem Hoogeveen')
  ,('Elim')
  ,('Fluitenberg')
  ,('Hollandscheveld')
  ,('Hoogeveen')
  ,('Nieuweroord')
  ,('Nieuwlande')
  ,('Noordscheschut')
  ,('Pesse')
  ,('Stuifzand')
  ,('Tiendeveen')
  ,('Wassenaar')
  ,('''s-Heer Arendskerke')
  ,('''s-Heer Hendrikskinderen')
  ,('Goes')
  ,('Kattendijke')
  ,('Wilhelminadorp')
  ,('Wolphaartsdijk')
  ,('Ten Boer')
  ,('Ten Post')
  ,('Garmerwolde')
  ,('Thesinge')
  ,('Woltersum')
  ,('Sint Annen')
  ,('Lellens')
  ,('Winneweer')
  ,('Beets')
  ,('Hobrede')
  ,('Kwadijk')
  ,('Middelie')
  ,('Oosthuizen')
  ,('Schardam')
  ,('Warder')
  ,('Elburg')
  ,('''t Harde')
  ,('Doornspijk')
  ,('Uden')
  ,('Volkel')
  ,('Odiliapeel')
  ,('Zeeland')
  ,('Schaijk')
  ,('Reek')
  ,('Klimmen')
  ,('Ransdaal')
  ,('Voerendaal')
  ,('Blitterswijck')
  ,('Geijsteren')
  ,('Meerlo')
  ,('Swolgen')
  ,('Tienray')
  ,('Wanssum')
  ,('Andelst')
  ,('Driel')
  ,('Elst')
  ,('Hemmen')
  ,('Herveld')
  ,('Heteren')
  ,('Homoet')
  ,('Oosterhout')
  ,('Randwijk')
  ,('Slijk-Ewijk')
  ,('Valburg')
  ,('Zetten')
  ,('Hooge Mierde')
  ,('Hulsel')
  ,('Lage Mierde')
  ,('Reusel')
  ,('Assendelft')
  ,('Koog aan de Zaan')
  ,('Krommenie')
  ,('Westknollendam')
  ,('Westzaan')
  ,('Wormerveer')
  ,('Zaandam')
  ,('Zaandijk')
  ,('Driehuizen')
  ,('Grootschermer')
  ,('Oterleek')
  ,('Schermerhorn')
  ,('Stompetoren')
  ,('Ursem gem. S')
  ,('Zuidschermer')
  ,('Scheemda')
  ,('Nieuw Scheemda')
  ,('''t Waar')
  ,('Nieuwolda')
  ,('Midwolda')
  ,('Oostwold')
  ,('Heiligerlee')
  ,('Westerlee')
  ,('De Rijp')
  ,('Graft')
  ,('Markenbinnen')
  ,('Noordeinde')
  ,('Oost-Graftdijk')
  ,('Starnmeer')
  ,('West-Graftdijk')
  ,('Hekendorp')
  ,('Oudewater')
  ,('Papekop')
  ,('Snelrewaard')
  ,('Stein')
  ,('Urmond')
  ,('Thorn')
  ,('Heel')
  ,('Beegden')
  ,('Wessem')
  ,('Maasbracht')
  ,('Linne')
  ,('Stevensweert')
  ,('Ohé en Laak')
  ,('Almelo')
  ,('Aadorp')
  ,('Bornerbroek')
  ,('Noordwijkerhout')
  ,('De Zilk')
  ,('Mijdrecht')
  ,('Vinkeveen')
  ,('Wilnis')
  ,('Amstelhoek')
  ,('Waverveen')
  ,('De Hoef')
  ,('de Hoef')
  ,('Schiedam')
  ,('Westervoort')
  ,('Bergen op Zoom')
  ,('Halsteren')
  ,('Lepelstraat')
  ,('Broek in Waterland')
  ,('Ilpendam')
  ,('Katwoude')
  ,('Marken')
  ,('Monnickendam')
  ,('Watergang')
  ,('Uitdam')
  ,('Zuiderwoude')
  ,('Reuver')
  ,('Beesel')
  ,('Babyloniënbroek')
  ,('Drongelen')
  ,('Eethen')
  ,('Genderen')
  ,('Meeuwen')
  ,('Veen')
  ,('Wijk en Aalburg')
  ,('Benschop')
  ,('Jaarsveld')
  ,('Lopik')
  ,('Lopikerkapel')
  ,('Polsbroek')
  ,('Linschoten')
  ,('Montfoort')
  ,('Workum')
  ,('Koudum')
  ,('Molkwerum')
  ,('Hindeloopen')
  ,('Stavoren')
  ,('Hemelum')
  ,('Warns')
  ,('Nijhuizum')
  ,('It Heidenskip')
  ,('Weert')
  ,('Stramproy')
  ,('Mill')
  ,('Sint Hubert')
  ,('Langenboom')
  ,('Wilbertoord')
  ,('Bleskensgraaf ca')
  ,('Brandwijk')
  ,('Goudriaan')
  ,('Molenaarsgraaf')
  ,('Ottoland')
  ,('Oud-Alblas')
  ,('Wijngaarden')
  ,('Barger-Compascuum')
  ,('Emmen')
  ,('Emmer-Compascuum')
  ,('Erica')
  ,('Klazienaveen')
  ,('Klazienaveen-Noord')
  ,('Nieuw-Amsterdam')
  ,('Nieuw-Dordrecht')
  ,('Nieuw-Schoonebeek')
  ,('Nieuw-Weerdinge')
  ,('Roswinkel')
  ,('Schoonebeek')
  ,('Veenoord')
  ,('Weiteveen')
  ,('Zandpol')
  ,('Zwartemeer')
  ,('Sassenheim')
  ,('Voorhout')
  ,('Warmond')
  ,('Wageningen')
  ,('Putten')
  ,('Baaium')
  ,('Baard')
  ,('Bears')
  ,('Boazum')
  ,('Britswert')
  ,('Easterein')
  ,('Easterlittens')
  ,('Easterwierrum')
  ,('Hidaard')
  ,('Hilaard')
  ,('Hinnaard')
  ,('Húns')
  ,('Iens')
  ,('Itens')
  ,('Jellum')
  ,('Jorwert')
  ,('Kûbaard')
  ,('Leons')
  ,('Lytsewierrum')
  ,('Mantgum')
  ,('Reahûs')
  ,('Rien')
  ,('Spannum')
  ,('Waaksens')
  ,('Weidum')
  ,('Winsum')
  ,('Wiuwert')
  ,('Wjelsryp')
  ,('Wommels')
  ,('Nederweert')
  ,('Ospel')
  ,('Nederweert-Eind')
  ,('Leveroy')
  ,('Echt')
  ,('Koningsbosch')
  ,('Maria Hoop')
  ,('Sint Joost')
  ,('Susteren')
  ,('Roosteren')
  ,('Nieuwstadt')
  ,('Veenendaal')
  ,('Borne')
  ,('Hertme')
  ,('Zenderen')
  ,('Culemborg')
  ,('Houten')
  ,('''t Goy')
  ,('Tull en ''t Waal')
  ,('Schalkwijk')
  ,('Berlicum')
  ,('Den Dungen')
  ,('Gemonde')
  ,('Sint-Michielsgestel')
  ,('Noordwijk')
  ,('Uithoorn')
  ,('De Kwakel')
  ,('Maarssen')
  ,('Tienhoven')
  ,('Oud Zuilen')
  ,('Gilze')
  ,('Hulten')
  ,('Molenschot')
  ,('Rijen')
  ,('Tholen')
  ,('Poortvliet')
  ,('Scherpenisse')
  ,('Sint-Maartensdijk')
  ,('Stavenisse')
  ,('Sint-Annaland')
  ,('Oud-Vossemeer')
  ,('Sint Philipsland')
  ,('Bolsward')
  ,('Landhorst')
  ,('Ledeacker')
  ,('Oploo')
  ,('Sint Anthonis')
  ,('Stevensbeek')
  ,('Wanroij')
  ,('Westerbeek')
  ,('Rijkevoort-De Walsert')
  ,('Leiden')
  ,('Gemert')
  ,('Bakel')
  ,('Milheeze')
  ,('Handel')
  ,('De Mortel')
  ,('De Rips')
  ,('Elsendorp')
  ,('Duiven')
  ,('Groessen')
  ,('Loo Gld')
  ,('Amstenrade')
  ,('Doenrade')
  ,('Oirsbeek')
  ,('Puth')
  ,('Schinnen')
  ,('Sweikhuizen')
  ,('Lexmond')
  ,('Ameide')
  ,('Meerkerk')
  ,('Hei- en Boeicop')
  ,('Leerbroek')
  ,('Nieuwland')
  ,('Leiderdorp')
  ,('Oostzaan')
  ,('Aduard')
  ,('Briltil')
  ,('Den Ham')
  ,('Den Horn')
  ,('Grijpskerk')
  ,('Kommerzijl')
  ,('Lauwerzijl')
  ,('Niehove')
  ,('Niezijl')
  ,('Noordhorn')
  ,('Oldehove')
  ,('Pieterzijl')
  ,('Saaksum')
  ,('Visvliet')
  ,('Zuidhorn')
  ,('Ouderkerk aan de Amstel')
  ,('Duivendrecht')
  ,('Achthuizen')
  ,('Den Bommel')
  ,('Ooltgensplaat')
  ,('Oude-Tonge')
  ,('Nuenen')
  ,('Hellevoetsluis')
  ,('Delft')
  ,('Schijndel')
  ,('Meijel')
  ,('Renswoude')
  ,('Doesburg')
  ,('Urk')
  ,('Diemen')
  ,('Aalden')
  ,('Benneveld')
  ,('Coevorden')
  ,('Dalen')
  ,('Dalerpeel')
  ,('Dalerveen')
  ,('De Kiel')
  ,('Diphoorn')
  ,('Erm')
  ,('Gees')
  ,('Geesbrug')
  ,('Holsloot')
  ,('Meppen')
  ,('Nieuwlande Coevorden')
  ,('Noord-Sleen')
  ,('Oosterhesselen')
  ,('Schoonoord')
  ,('Sleen')
  ,('Stieltjeskanaal')
  ,('''t Haantje')
  ,('Wachtum')
  ,('Wezup')
  ,('Wezuperbrug')
  ,('Zweeloo')
  ,('Zwinderen')
  ,('Middenbeemster')
  ,('Noordbeemster')
  ,('Westbeemster')
  ,('Zuidoostbeemster')
  ,('Doezum')
  ,('Grootegast')
  ,('Kornhorn')
  ,('Lutjegast')
  ,('Niekerk')
  ,('Oldekerk')
  ,('Opende')
  ,('Sebaldeburen')
  ,('Terband')
  ,('Luinjeberd')
  ,('Tjalleberd')
  ,('Gersloot')
  ,('Heerenveen')
  ,('Nieuweschoot')
  ,('Oudeschoot')
  ,('Oranjewoud')
  ,('De Knipe')
  ,('Mildam')
  ,('Katlijk')
  ,('Bontebok')
  ,('Nieuwehorne')
  ,('Oudehorne')
  ,('Jubbega')
  ,('Hoornsterzwaag')
  ,('Genemuiden')
  ,('Hasselt')
  ,('Mastenbroek')
  ,('Zwartsluis')
  ,('Azewijn')
  ,('Braamt')
  ,('Didam')
  ,('''s-Heerenberg')
  ,('Kilder')
  ,('Lengel')
  ,('Loerbeek')
  ,('Stokkum')
  ,('Vethuizen')
  ,('Wijnbergen')
  ,('Zeddam')
  ,('Dussen')
  ,('Hank')
  ,('Nieuwendijk')
  ,('Sleeuwijk')
  ,('Werkendam')
  ,('Loenen aan de Vecht')
  ,('Loenersloot')
  ,('Nieuwersluis')
  ,('Nigtevecht')
  ,('Vreeland')
  ,('Groenlo')
  ,('Lievelde')
  ,('Vragender')
  ,('Lichtenvoorde')
  ,('Harreveld')
  ,('Zieuwent')
  ,('Mariënvelde')
  ,('Bellingwolde')
  ,('Blijham')
  ,('Oudeschans')
  ,('Veelerveen')
  ,('Vriescheloo')
  ,('Wedde')
  ,('Bunschoten-Spakenburg')
  ,('Eemdijk')
  ,('Bantega')
  ,('Delfstrahuizen')
  ,('Echten')
  ,('Echtenerbrug')
  ,('Eesterga')
  ,('Follega')
  ,('Lemmer')
  ,('Oosterzee')
  ,('Apeldoorn')
  ,('Klarenbeek')
  ,('Hoog Soeren')
  ,('Lieren')
  ,('Loenen')
  ,('Radio Kootwijk')
  ,('Wenum Wiesel')
  ,('Beemte Broekland')
  ,('Uddel')
  ,('Ugchelen')
  ,('Hoenderloo')
  ,('Beekbergen')
  ,('Boerakker')
  ,('De Wilp')
  ,('Jonkersvaart')
  ,('Lucaswolde')
  ,('Marum')
  ,('Niebert')
  ,('Nuis')
  ,('Akersloot')
  ,('Castricum')
  ,('de Woude')
  ,('Limmen')
  ,('Leerdam')
  ,('Schoonrewoerd')
  ,('Kedichem')
  ,('Oosterwijk')
  ,('Tripscompagnie')
  ,('Meeden')
  ,('Muntendam')
  ,('Noordbroek')
  ,('Zuidbroek')
  ,('Roermond')
  ,('Herten')
  ,('Swalmen')
  ,('Jisp')
  ,('Oostknollendam')
  ,('Wormer')
  ,('Wijdewormer')
  ,('Millingen aan de Rijn')
  ,('Berg en Dal')
  ,('Groesbeek')
  ,('Heilig Landstichting')
  ,('Zoetermeer')
  ,('Bourtange')
  ,('Sellingen')
  ,('Ter Apel')
  ,('Ter Apelkanaal')
  ,('Vlagtwedde')
  ,('Gorinchem')
  ,('Dalem')
  ,('Allingawier')
  ,('Arum')
  ,('Breezanddijk')
  ,('Burgwerd')
  ,('Cornwerd')
  ,('Dedgum')
  ,('Exmorra')
  ,('Ferwoude')
  ,('Gaast')
  ,('Hartwerd')
  ,('Hichtum')
  ,('Hieslum')
  ,('Idsegahuizum')
  ,('Kimswerd')
  ,('Kornwerderzand')
  ,('Lollum')
  ,('Longerhouw')
  ,('Makkum')
  ,('Parrega')
  ,('Piaam')
  ,('Pingjum')
  ,('Schettens')
  ,('Schraard')
  ,('Tjerkwerd')
  ,('Witmarsum')
  ,('Wons')
  ,('Zurich')
  ,('Castenray')
  ,('Heide')
  ,('Leunen')
  ,('Merselo')
  ,('Oirlo')
  ,('Oostrum')
  ,('Smakt')
  ,('Venray')
  ,('Veulen')
  ,('Vredepeel')
  ,('Ysselsteyn')
  ,('Enumatil')
  ,('Leek')
  ,('Lettelbert')
  ,('Midwolde')
  ,('Tolbert')
  ,('Haaksbergen')
  ,('Alkmaar')
  ,('Oudorp')
  ,('Dordrecht')
  ,('Hendrik-Ido-Ambacht')
  ,('Asperen')
  ,('Herwijnen')
  ,('Heukelum')
  ,('Spijk')
  ,('Vuren')
  ,('Nuth')
  ,('Hulsberg')
  ,('Schimmert')
  ,('Wijnandsrade')
  ,('Rhoon')
  ,('Poortugaal')
  ,('Rotterdam-Albrandswaard')
  ,('Bergambacht')
  ,('Berkenwoude')
  ,('Ammerstol')
  ,('Brielle')
  ,('Vierpolders')
  ,('Zwartewaal')
  ,('Klaaswaal')
  ,('Numansdorp')
  ,('Ouderkerk aan den IJssel')
  ,('Gouderak')
  ,('Krimpen aan de Lek')
  ,('Lekkerkerk')
  ,('Dirksland')
  ,('Herkingen')
  ,('Melissant')
  ,('''s-Gravendeel')
  ,('Heinenoord')
  ,('Maasdam')
  ,('Mijnsheerenland')
  ,('Puttershoek')
  ,('Westmaas')
  ,('Alblasserdam')
  ,('Haastrecht')
  ,('Stolwijk')
  ,('Vlist')
  ,('Schoonhoven')
  ,('Assen')
  ,('Loon')
  ,('Rhee')
  ,('Ter Aard')
  ,('Ubbena')
  ,('Zeijerveen')
  ,('Zeijerveld')
  ,('Heerjansdam')
  ,('Zwijndrecht')
  ,('Abbega')
  ,('Blauwhuis')
  ,('Folsgare')
  ,('Gaastmeer')
  ,('Gauw')
  ,('Goënga')
  ,('Greonterp')
  ,('Heeg')
  ,('Hommerts')
  ,('Idzega')
  ,('IJlst')
  ,('Indijk')
  ,('Jutrijp')
  ,('Koufurderrige')
  ,('Nijland')
  ,('Oosthem')
  ,('Oppenhuizen')
  ,('Sandfirden')
  ,('Scharnegoutum')
  ,('Smallebrugge')
  ,('Tirns')
  ,('Tjalhuizum')
  ,('Uitwellingerga')
  ,('Westhem')
  ,('Wolsum')
  ,('Woudsend')
  ,('Ypecolsga')
  ,('Schiermonnikoog')
  ,('Biddinghuizen')
  ,('Dronten')
  ,('Swifterbant')
  ,('IJsselstein')
  ,('Kreileroord')
  ,('Middenmeer')
  ,('Slootdorp')
  ,('Wieringerwerf')
  ,('Sliedrecht')
  ,('Boxtel')
  ,('Liempde')
  ,('Aldtsjerk')
  ,('Wyns')
  ,('Oentsjerk')
  ,('Gytsjerk')
  ,('Ryptsjerk')
  ,('Mûnein')
  ,('Tytsjerk')
  ,('Suwâld')
  ,('Hurdegaryp')
  ,('Noardburgum')
  ,('Burgum')
  ,('Jistrum')
  ,('Sumar')
  ,('Eastermar')
  ,('Garyp')
  ,('Earnewâld')
  ,('Smilde')
  ,('Heemskerk')
  ,('Rijswijk')
  ,('Schagen')
  ,('Hoorn')
  ,('Blokker')
  ,('Zwaag')
  ,('Adorp')
  ,('Baflo')
  ,('Den Andel')
  ,('Ezinge')
  ,('Feerwerd')
  ,('Garnwerd')
  ,('Rasquert')
  ,('Saaxumhuizen')
  ,('Sauwerd')
  ,('Tinallinge')
  ,('Wetsinge')
  ,('Reeuwijk')
  ,('Driebruggen')
  ,('Waarder')
  ,('Milsbeek')
  ,('Ottersum')
  ,('Ven-Zelderheide')
  ,('Gennep')
  ,('Heijen')
  ,('Akmarijp')
  ,('Boornzwaag')
  ,('Broek')
  ,('Dijken')
  ,('Doniaga')
  ,('Goingarijp')
  ,('Haskerdijken')
  ,('Haskerhorne')
  ,('Idskenhuizen')
  ,('Joure')
  ,('Langweer')
  ,('Legemeer')
  ,('Nieuwebrug')
  ,('Nijehaske')
  ,('Oldeouwer')
  ,('Oudehaske')
  ,('Ouwsterhaule')
  ,('Ouwster-Nijega')
  ,('Rohel')
  ,('Rotstergaast')
  ,('Rotsterhaule')
  ,('Rottum')
  ,('Scharsterbrug')
  ,('Sint Nicolaasga')
  ,('Sintjohannesga')
  ,('Snikzwaag')
  ,('Terkaple')
  ,('Teroele')
  ,('Tjerkgaast')
  ,('Vegelinsoord')
  ,('Barsingerhorn')
  ,('Haringhuizen')
  ,('Kolhorn')
  ,('Lutjewinkel')
  ,('Nieuwe Niedorp')
  ,('Oude Niedorp')
  ,('''t Veld')
  ,('Winkel')
  ,('Zijdewind')
  ,('Woudenberg')
  ,('Eagum')
  ,('Akkrum')
  ,('Dearsum')
  ,('Friens')
  ,('Grou')
  ,('Idaerd')
  ,('Jirnsum')
  ,('Nes')
  ,('Aldeboarn')
  ,('Poppenwier')
  ,('Raerd')
  ,('Reduzum')
  ,('Sibrandabuorren')
  ,('Terherne')
  ,('Tersoal')
  ,('Wergea')
  ,('Warstiens')
  ,('Warten')
  ,('Lent')
  ,('Ommen')
  ,('Arriën')
  ,('Beerze')
  ,('Beerzerveld')
  ,('Dalmsholte')
  ,('Giethmen')
  ,('Lemele')
  ,('Stegeren')
  ,('Vilsteren')
  ,('Vinkenbuurt')
  ,('Witharen')
  ,('Tiel')
  ,('Zennewijnen')
  ,('Wadenoijen')
  ,('Kapel Avezaath')
  ,('Kerk Avezaath')
  ,('Ridderkerk')
  ,('Heemstede')
  ,('Wijchen')
  ,('Bergharen')
  ,('Hernen')
  ,('Leur')
  ,('Batenburg')
  ,('Niftrik')
  ,('Balgoij')
  ,('Aalsmeer')
  ,('Kudelstaart')
  ,('Ermelo')
  ,('Drimmelen')
  ,('Hooge Zwaluwe')
  ,('Lage Zwaluwe')
  ,('Made')
  ,('Terheijden')
  ,('Wagenberg')
  ,('Rucphen')
  ,('Schijf')
  ,('Sprundel')
  ,('St. Willebrord')
  ,('Zegge')
  ,('Hollum')
  ,('Ballum')
  ,('Buren')
  ,('Herkenbosch')
  ,('Melick')
  ,('Montfort')
  ,('Posterholt')
  ,('Sint Odiliënberg')
  ,('Vlodrop')
  ,('Sprang-Capelle')
  ,('Waalwijk')
  ,('Waspik')
  ,('Son')
  ,('Breugel')
  ,('Maasbree')
  ,('Baarlo')
  ,('Bunnik')
  ,('Odijk')
  ,('Werkhoven')
  ,('Nijbroek')
  ,('Steenenkamer')
  ,('Terwolde')
  ,('Teuge')
  ,('Twello')
  ,('Wilp')
  ,('Oosterend')
  ,('Lies')
  ,('Formerum')
  ,('Landerum')
  ,('Midsland')
  ,('Striep')
  ,('Baaiduinen')
  ,('Kaard')
  ,('Hee')
  ,('Kinnum')
  ,('West-Terschelling')
  ,('Aalst')
  ,('Bern')
  ,('Brakel')
  ,('Bruchem')
  ,('Delwijnen')
  ,('Gameren')
  ,('Kerkwijk')
  ,('Nederhemert')
  ,('Nieuwaal')
  ,('Poederoijen')
  ,('Zaltbommel')
  ,('Zuilichem')
  ,('Leusden')
  ,('Stoutenburg')
  ,('Haarlemmerliede')
  ,('Spaarndam')
  ,('Halfweg')
  ,('Zoeterwoude')
  ,('Gelderswoude')
  ,('Oisterwijk')
  ,('Moergestel')
  ,('Heukelom')
  ,('Kerkrade')
  ,('Eygelshoven')
  ,('Vogelenzang')
  ,('Aerdenhout')
  ,('Overveen')
  ,('Bloemendaal')
  ,('De Lier')
  ,('Honselersdijk')
  ,('Kwintsheul')
  ,('Maasdijk')
  ,('Monster')
  ,('Naaldwijk')
  ,('Poeldijk')
  ,('''s-Gravenzande')
  ,('Ter Heijde')
  ,('Wateringen')
  ,('Bodegraven')
  ,('Nieuwerbrug aan den Rijn')
  ,('Nieuwkoop')
  ,('Noorden')
  ,('Woerdense Verlaat')
  ,('Nieuwveen')
  ,('Zevenhoven')
  ,('Vrouwenakker')
  ,('Ter Aar')
  ,('Brouwershaven')
  ,('Bruinisse')
  ,('Burgh-Haamstede')
  ,('Dreischor')
  ,('Ellemeet')
  ,('Kerkwerve')
  ,('Nieuwerkerk')
  ,('Noordgouwe')
  ,('Noordwelle')
  ,('Oosterland')
  ,('Ouwerkerk')
  ,('Renesse')
  ,('Scharendijke')
  ,('Serooskerke')
  ,('Sirjansland')
  ,('Zierikzee')
  ,('Zonnemaire')
  ,('Baars')
  ,('Basse')
  ,('Belt-Schutsloot')
  ,('Blankenham')
  ,('Blokzijl')
  ,('De Bult')
  ,('De Pol')
  ,('Eesveen')
  ,('Giethoorn')
  ,('IJsselham')
  ,('Kalenberg')
  ,('Kallenkote')
  ,('Kuinre')
  ,('Marijenkampen')
  ,('Nederland')
  ,('Oldemarkt')
  ,('Onna')
  ,('Ossenzijl')
  ,('Paasloo')
  ,('Scheerwolde')
  ,('Sint Jansklooster')
  ,('Steenwijk')
  ,('Steenwijkerwold')
  ,('Tuk')
  ,('Vollenhove')
  ,('Wanneperveen')
  ,('Wetering')
  ,('Willemsoord')
  ,('Witte Paarden')
  ,('Zuidveen')
  ,('Bitgum')
  ,('Bitgummole')
  ,('Berltsum')
  ,('Blessum')
  ,('Boksum')
  ,('Deinum')
  ,('Ingelum')
  ,('Dronryp')
  ,('Marsum')
  ,('Menaam')
  ,('Skingen')
  ,('Slappeterp')
  ,('Wier')
  ,('Beverwijk')
  ,('Wijk aan Zee')
  ,('De Cocksdorp')
  ,('De Koog')
  ,('De Waal')
  ,('Den Burg')
  ,('Den Hoorn Texel')
  ,('Den Hoorn')
  ,('Oosterend Nh')
  ,('Oudeschild')
  ,('Oegstgeest')
  ,('Someren')
  ,('Lierop')
  ,('Geffen')
  ,('Nuland')
  ,('Vinkel')
  ,('Weurt')
  ,('Beuningen Gld')
  ,('Ewijk')
  ,('Winssen')
  ,('Enkhuizen')
  ,('Uitgeest')
  ,('Schagerbrug')
  ,('Callantsoog')
  ,('''t Zand')
  ,('Sint Maartensbrug')
  ,('Sint Maartensvlotbrug')
  ,('Petten')
  ,('Burgerbrug')
  ,('Oudesluis')
  ,('Ede')
  ,('Lunteren')
  ,('Bennekom')
  ,('Otterlo')
  ,('Harskamp')
  ,('Ederveen')
  ,('De Klomp')
  ,('Wekerom')
  ,('Deelen')
  ,('Aagtekerke')
  ,('Biggekerke')
  ,('Domburg')
  ,('Gapinge')
  ,('Grijpskerke')
  ,('Koudekerke')
  ,('Meliskerke')
  ,('Oostkapelle')
  ,('Veere')
  ,('Vrouwenpolder')
  ,('Westkapelle')
  ,('Zoutelande')
  ,('Sint-Oedenrode')
  ,('Aartswoud')
  ,('De Weere')
  ,('Hoogwoud')
  ,('Opmeer')
  ,('Spanbroek')
  ,('Oud-Beijerland')
  ,('Kinderdijk')
  ,('Nieuw-Lekkerland')
  ,('Mookhoek')
  ,('Strijen')
  ,('Strijensas')
  ,('Dongen')
  ,('''s Gravenmoer')
  ,('Middelaar')
  ,('Molenhoek')
  ,('Mook')
  ,('Plasmolen')
  ,('Schipluiden')
  ,('Maasland')
  ,('Born')
  ,('Buchten')
  ,('Einighausen')
  ,('Geleen')
  ,('Grevenbicht')
  ,('Guttecoven')
  ,('Holtum')
  ,('Limbricht')
  ,('Munstergeleen')
  ,('Obbicht')
  ,('Papenhoven')
  ,('Sittard')
  ,('Windraak')
  ,('Zeist')
  ,('Den Dolder')
  ,('Bosch en Duin')
  ,('Austerlitz')
  ,('Erp')
  ,('Veghel')
  ,('Blaricum')
  ,('Bedum')
  ,('Onderdendam')
  ,('Zuidwolde')
  ,('Velsen-Noord')
  ,('Velsen-Zuid')
  ,('IJmuiden')
  ,('Driehuis NH')
  ,('Santpoort-Noord')
  ,('Santpoort-Zuid')
  ,('Velserbroek')
  ,('Everdingen')
  ,('Hagestein')
  ,('Ossenwaard')
  ,('Vianen')
  ,('Zijderveld')
  ,('Bingelrade')
  ,('Jabeek')
  ,('Merkelbeek')
  ,('Schinveld')
  ,('Brunssum')
  ,('Koudekerk aan den Rijn')
  ,('Hazerswoude-Rijndijk')
  ,('Hazerswoude-Dorp')
  ,('Benthuizen')
  ,('Bathmen')
  ,('Colmschate')
  ,('Deventer')
  ,('Diepenveen')
  ,('Lettele')
  ,('Okkenbroek')
  ,('Schalkhaar')
  ,('Sneek')
  ,('Loënga')
  ,('Offingawier')
  ,('Ysbrechtum')
  ,('Heerde')
  ,('Veessen')
  ,('Vorchten')
  ,('Wapenveld')
  ,('Nijkerk')
  ,('Nijkerkerveen')
  ,('Hoevelaken')
  ,('Erlecom')
  ,('Kekerdom')
  ,('Leuth')
  ,('Ooij')
  ,('Persingen')
  ,('Ubbergen')
  ,('Goirle')
  ,('Riel')
  ,('Hattem')
  ,('Baarle-Nassau')
  ,('Ulicoten')
  ,('Castelre')
  ,('Goudswaard')
  ,('Nieuw-Beijerland')
  ,('Piershil')
  ,('Zuid-Beijerland')
  ,('Harlingen')
  ,('Midlum')
  ,('Wijnaldum')
  ,('Kampen')
  ,('IJsselmuiden')
  ,('Grafhorst')
  ,('Zalk')
  ,('Wilsum')
  ,('''s-Heerenbroek')
  ,('Kamperveen')
  ,('Afferden')
  ,('Deest')
  ,('Druten')
  ,('Horssen')
  ,('Puiflijk')
  ,('Haarlem')
  ,('Spaarndam gem. Haarlem')
  ,('Eelde')
  ,('Paterswolde')
  ,('Eelderwolde')
  ,('Vries')
  ,('Zuidlaren')
  ,('Bunne')
  ,('De Punt')
  ,('Donderen')
  ,('Taarlo')
  ,('Tynaarlo')
  ,('Winde')
  ,('Yde')
  ,('Zeegse')
  ,('Zeijen')
  ,('De Groeve')
  ,('Zuidlaarderveen')
  ,('Midlaren')
  ,('Rozendaal')
  ,('Asten')
  ,('Heusden')
  ,('Ommel')
  ,('Goedereede')
  ,('Ouddorp')
  ,('Stellendam')
  ,('De Bilt')
  ,('Bilthoven')
  ,('Maartensdijk')
  ,('Groenekan')
  ,('Hollandsche Rading')
  ,('Westbroek')
  ,('Baexem')
  ,('Buggenum')
  ,('Ell')
  ,('Grathem')
  ,('Haelen')
  ,('Haler')
  ,('Heibloem')
  ,('Heythuysen')
  ,('Horn')
  ,('Hunsel')
  ,('Ittervoort')
  ,('Kelpen-Oler')
  ,('Neer')
  ,('Neeritter')
  ,('Nunhem')
  ,('Roggel')
  ,('Oosteind')
  ,('Den Hout')
  ,('Dorst')
  ,('Oostburg')
  ,('Aardenburg')
  ,('Sluis')
  ,('Waterlandkerkje')
  ,('Cadzand')
  ,('Breskens')
  ,('Eede')
  ,('Groede')
  ,('Hoofdplaat')
  ,('Nieuwvliet')
  ,('Retranchement')
  ,('Schoondijke')
  ,('Zuidzande')
  ,('Sint Kruis')
  ,('IJzendijke')
  ,('Aerdt')
  ,('Herwen')
  ,('Lobith')
  ,('Pannerden')
  ,('Tolkamer')
  ,('Nunspeet')
  ,('Elspeet')
  ,('Hulshorst')
  ,('Vierhouten')
  ,('Doorwerth')
  ,('Heelsum')
  ,('Heveadorp')
  ,('Wolfheze')
  ,('Oosterbeek')
  ,('Renkum')
  ,('Duizel')
  ,('Eersel')
  ,('Knegsel')
  ,('Steensel')
  ,('Vessem')
  ,('Wintelre')
  ,('Boskoop')
  ,('Ankeveen')
  ,('''s-Graveland')
  ,('Kortenhoef')
  ,('Loosdrecht')
  ,('Nederhorst den Berg')
  ,('Breukeleveen')
  ,('Balinge')
  ,('Beilen')
  ,('Bovensmilde')
  ,('Bruntinge')
  ,('Drijber')
  ,('Elp')
  ,('Eursinge')
  ,('Garminge')
  ,('Hijken')
  ,('Hoogersmilde')
  ,('Hooghalen')
  ,('Mantinge')
  ,('Nieuw-Balinge')
  ,('Oranje')
  ,('Orvelte')
  ,('Spier')
  ,('Westerbork')
  ,('Wijster')
  ,('Witteveen')
  ,('Zuidveld')
  ,('Zwiggelte')
  ,('Nijmegen')
  ,('Hardinxveld-Giessendam')
  ,('Spijkenisse')
  ,('Hekelingen')
  ,('Hoogmade')
  ,('Kaag')
  ,('Leimuiden')
  ,('Nieuwe Wetering')
  ,('Oud Ade')
  ,('Oude Wetering')
  ,('Rijnsaterwoude')
  ,('Rijpwetering')
  ,('Roelofarendsveen')
  ,('Woubrugge')
  ,('Groet')
  ,('Schoorl')
  ,('Bergen aan Zee')
  ,('Bergen (NH)')
  ,('Egmond aan den Hoef')
  ,('Egmond aan Zee')
  ,('Egmond-Binnen')
  ,('Waddinxveen')
  ,('Kaatsheuvel')
  ,('Loon op Zand')
  ,('De Moer')
  ,('Landgraaf')
  ,('Den Helder')
  ,('Huisduinen')
  ,('Julianadorp')
  ,('Heesch')
  ,('Heeswijk-Dinther')
  ,('Loosbroek')
  ,('Nistelrode')
  ,('Vorstenbosch')
  ,('Simpelveld')
  ,('Bocholtz')
  ,('Baneheide')
  ,('''t Loo Oldebroek')
  ,('Hattemerbroek')
  ,('Noordeinde Gld')
  ,('Oldebroek')
  ,('Oosterwolde Gld')
  ,('Wezep')
  ,('Stadskanaal')
  ,('Musselkanaal')
  ,('Onstwedde')
  ,('Mussel')
  ,('Vledderveen')
  ,('Botlek Rotterdam')
  ,('Europoort Rotterdam')
  ,('Hoek van Holland')
  ,('Hoogvliet Rotterdam')
  ,('Maasvlakte Rotterdam')
  ,('Pernis Rotterdam')
  ,('Rotterdam')
  ,('Vondelingenplaat Rotterdam')
  ,('Hulst')
  ,('Sint Jansteen')
  ,('Kapellebrug')
  ,('Heikant')
  ,('Clinge')
  ,('Nieuw Namen')
  ,('Graauw')
  ,('Vogelwaarde')
  ,('Terhole')
  ,('Kuitaart')
  ,('Hengstdijk')
  ,('Lamswaarde')
  ,('Kloosterzande')
  ,('Walsoorden')
  ,('Ossenisse')
  ,('Purmerend')
  ,('Ansen')
  ,('Boschoord')
  ,('Darp')
  ,('Diever')
  ,('Dieverbrug')
  ,('Doldersum')
  ,('Dwingeloo')
  ,('Frederiksoord')
  ,('Geeuwenbrug')
  ,('Havelte')
  ,('Havelterberg')
  ,('Nijensleek')
  ,('Oude Willem')
  ,('Ruinen')
  ,('Uffelte')
  ,('Vledder')
  ,('Wapse')
  ,('Wapserveen')
  ,('Wateren')
  ,('Wilhelminaoord')
  ,('Wittelte')
  ,('Zorgvlied')
  ,('Huissen')
  ,('Bemmel')
  ,('Gendt')
  ,('Angeren')
  ,('Doornenburg')
  ,('Haalderen')
  ,('Ressen')
  ,('Boekel')
  ,('Venhorst')
  ,('Arcen')
  ,('Lomm')
  ,('Velden')
  ,('Middelharnis')
  ,('Nieuwe-Tonge')
  ,('Sommelsdijk')
  ,('Stad aan ''t Haringvliet')
  ,('Abbekerk')
  ,('Benningbroek')
  ,('Hauwert')
  ,('Lambertschaag')
  ,('Medemblik')
  ,('Midwoud')
  ,('Nibbixwoud')
  ,('Oostwoud')
  ,('Opperdoes')
  ,('Sijbekarspel')
  ,('Twisk')
  ,('Wognum')
  ,('Zwaagdijk-West')
  ,('de Wijk')
  ,('Drogteropslagen')
  ,('Kerkenveld')
  ,('Koekange')
  ,('Linde')
  ,('Ruinerwold')
  ,('Veeningen')
  ,('Augustinusga')
  ,('Boelenslaan')
  ,('Buitenpost')
  ,('Drogeham')
  ,('Gerkesklooster')
  ,('Harkema')
  ,('Kootstertille')
  ,('Stroobos')
  ,('Surhuisterveen')
  ,('Surhuizum')
  ,('Twijzel')
  ,('Twijzelerheide')
  ,('Anna Paulowna')
  ,('Breezand')
  ,('Wieringerwaard')
  ,('Kamperland')
  ,('Kortgene')
  ,('Colijnsplaat')
  ,('Wissenkerke')
  ,('Kats')
  ,('Geersdijk')
  ,('Escharen')
  ,('Gassel')
  ,('Grave')
  ,('Blije')
  ,('Burdaard')
  ,('Ferwert')
  ,('Ginnum')
  ,('Hallum')
  ,('Hegebeintum')
  ,('Jannum')
  ,('Jislum')
  ,('Lichtaard')
  ,('Marrum')
  ,('Reitsum')
  ,('Wânswert')
  ,('Acquoy')
  ,('Rhenoy')
  ,('Beesd')
  ,('Gellicum')
  ,('Rumpt')
  ,('Enspijk')
  ,('Deil')
  ,('Geldermalsen')
  ,('Meteren')
  ,('Tricht')
  ,('Buurmalsen')
  ,('Harderwijk')
  ,('Hierden')
  ,('Alde Leie')
  ,('Britsum')
  ,('Feinsum')
  ,('Hijum')
  ,('Jelsum')
  ,('Koarnjum')
  ,('Stiens')
  ,('Voorschoten')
  ,('Bierum')
  ,('Borgsweer')
  ,('Delfzijl')
  ,('Farmsum')
  ,('Godlinze')
  ,('Holwierde')
  ,('Krewerd')
  ,('Losdorp')
  ,('Meedhuizen')
  ,('Spijk Gn')
  ,('Termunten')
  ,('Termunterzijl')
  ,('Wagenborgen')
  ,('Woldendorp')
  ,('Doetinchem')
  ,('Gaanderen')
  ,('Wehl')
  ,('Oss')
  ,('Berghem')
  ,('Megen')
  ,('Macharen')
  ,('Haren')
  ,('Ravenstein')
  ,('Herpen')
  ,('Deursen-Dennenburg')
  ,('Huisseling')
  ,('Koolwijk')
  ,('Dieden')
  ,('Demen')
  ,('Neerlangel')
  ,('Neerloon')
  ,('Overlangel')
  ,('Keent')
  ,('Broeksterwâld')
  ,('Damwâld')
  ,('De Falom')
  ,('Driezum')
  ,('Wâlterswâld')
  ,('Rinsumageast')
  ,('Feanwâlden')
  ,('Readtsjerk')
  ,('De Westereen')
  ,('Sibrandahûs')
  ,('Boornbergum')
  ,('De Tike')
  ,('De Veenhoop')
  ,('De Wilgen')
  ,('Drachten')
  ,('Drachtstercompagnie')
  ,('Goëngahuizen')
  ,('Houtigehage')
  ,('Kortehemmen')
  ,('Nijega')
  ,('Opeinde')
  ,('Rottevalle')
  ,('Smalle Ee')
  ,('Dirkshorn')
  ,('Sint Maarten')
  ,('Tuitjenhorn')
  ,('Waarland')
  ,('Warmenhuizen')
  ,('Utrecht')
  ,('Vleuten')
  ,('De Meern')
  ,('Haarzuilens')
  ,('Vlieland')
  ,('Almkerk')
  ,('Andel')
  ,('Giessen')
  ,('Rijswijk (NB)')
  ,('Uitwijk')
  ,('Waardhuizen')
  ,('Woudrichem')
  ,('Augsbuurt')
  ,('Burum')
  ,('Kollum')
  ,('Kollumerpomp')
  ,('Kollumerzwaag')
  ,('Munnekezijl')
  ,('Oudwoude')
  ,('Triemen')
  ,('Veenklooster')
  ,('Warfstermolen')
  ,('Westergeest')
  ,('Zwagerbosch')
  ,('Blauwestad')
  ,('Bruinehaar')
  ,('Geerdijk')
  ,('Vriezenveen')
  ,('Vroomshoop')
  ,('Westerhaar-Vriezenveensewijk')
  ,('Oudebildtzijl')
  ,('Westhoek')
  ,('Nij Altoenae')
  ,('St.-Annaparochie')
  ,('St.-Jacobiparochie')
  ,('Minnertsga')
  ,('Vrouwenparochie')
  ,('Driebergen-Rijsenburg')
  ,('Leersum')
  ,('Amerongen')
  ,('Overberg')
  ,('Doorn')
  ,('Maarn')
  ,('Maarsbergen')
  ,('Bakkeveen')
  ,('Beetsterzwaag')
  ,('Drachten-Azeven')
  ,('Frieschepalen')
  ,('Gorredijk')
  ,('Hemrik')
  ,('Jonkerslân')
  ,('Langezwaag')
  ,('Lippenhuizen')
  ,('Luxwoude')
  ,('Nij Beets')
  ,('Olterterp')
  ,('Siegerswoude')
  ,('Terwispel')
  ,('Tijnje')
  ,('Ureterp')
  ,('Wijnjewoude')
  ,('Est')
  ,('Haaften')
  ,('Heesselt')
  ,('Hellouw')
  ,('Neerijnen')
  ,('Ophemert')
  ,('Opijnen')
  ,('Tuil')
  ,('Varik')
  ,('Waardenburg')
  ,('Asch')
  ,('Beusichem')
  ,('Eck en Wiel')
  ,('Erichem')
  ,('Ingen')
  ,('Kapel-Avezaath')
  ,('Kerk-Avezaath')
  ,('Lienden')
  ,('Maurik')
  ,('Ommeren')
  ,('Ravenswaaij')
  ,('Rijswijk (GLD)')
  ,('Zoelen')
  ,('Zoelmond')
  ,('Appingedam')
  ,('Aalsum')
  ,('Anjum')
  ,('Bornwird')
  ,('Brantgum')
  ,('Dokkum')
  ,('Ee')
  ,('Engwierum')
  ,('Foudgum')
  ,('Hantum')
  ,('Hantumeruitburen')
  ,('Hantumhuizen')
  ,('Hiaure')
  ,('Holwerd')
  ,('Jouswier')
  ,('Lioessens')
  ,('Metslawier')
  ,('Moddergat')
  ,('Morra')
  ,('Niawier')
  ,('Oosternijkerk')
  ,('Paesens')
  ,('Raard')
  ,('Ternaard')
  ,('Waaxens')
  ,('Wetsens')
  ,('Wierum')
  ,('Eenrum')
  ,('Hornhuizen')
  ,('Houwerzijl')
  ,('Kloosterburen')
  ,('Lauwersoog')
  ,('Leens')
  ,('Mensingeweer')
  ,('Pieterburen')
  ,('Schouwerzijl')
  ,('Ulrum')
  ,('Vierhuizen')
  ,('Warfhuizen')
  ,('Wehe-den Hoorn')
  ,('Westernieland')
  ,('Zoutkamp')
  ,('Zuurdijk')
  ,('Chaam')
  ,('Galder')
  ,('Strijbeek')
  ,('Ulvenhout AC')
  ,('Bavel AC')
  ,('Beringe')
  ,('Egchel')
  ,('Grashoek')
  ,('Helden')
  ,('Koningslust')
  ,('Panningen')
  ,('Eenum')
  ,('Garrelsweer')
  ,('Garsthuizen')
  ,('Huizinge')
  ,('Leermens')
  ,('Loppersum')
  ,('Middelstum')
  ,('Oosterwijtwerd')
  ,('Startenhuizen')
  ,('Stedum')
  ,('Toornwerd')
  ,('Westeremden')
  ,('Westerwijtwerd')
  ,('Wirdum Gn')
  ,('''t Zandt')
  ,('Zeerijp')
  ,('Zijldijk')
  ,('Hansweert')
  ,('Krabbendijke')
  ,('Kruiningen')
  ,('Oostdijk')
  ,('Rilland')
  ,('Waarde')
  ,('Yerseke')
  ,('Lith')
  ,('Lithoijen')
  ,('Oijen')
  ,('Maren-Kessel')
  ,('Teeffelen')
  ,('Eemshaven')
  ,('Eppenhuizen')
  ,('Kantens')
  ,('Oldenzijl')
  ,('Oosternieland')
  ,('Oudeschip')
  ,('Roodeschool')
  ,('Stitswerd')
  ,('Uithuizen')
  ,('Uithuizermeeden')
  ,('Usquert')
  ,('Warffum')
  ,('Zandeweer')
  ,('Meerstad')
  ,('Zundert')
  ,('Rijsbergen')
  ,('Wernhout')
  ,('Klein Zundert')
  ,('Achtmaal')
  ,('Malden')
  ,('Heumen')
  ,('Overasselt')
  ,('Nederasselt')
  ,('DRUNEN')
  ,('Drunen')
  ,('ELSHOUT')
  ,('Elshout')
  ,('HAARSTEEG')
  ,('Haarsteeg')
  ,('HEUSDEN GEM HEUSDEN')
  ,('NIEUWKUIJK')
  ,('Nieuwkuijk')
  ,('VLIJMEN')
  ,('Vlijmen')
  ,('DOEVEREN')
  ,('Doeveren')
  ,('HEDIKHUIZEN')
  ,('Hedikhuizen')
  ,('HEESBEEN')
  ,('Heesbeen')
  ,('HERPT')
  ,('Herpt')
  ,('HEUSDEN')
  ,('OUDHEUSDEN')
  ,('Oudheusden')
  ,('Son en Breugel')
  ,('Amsterdam-Duivendrecht')
  ,('Hoef en Haag')
  ,('Harkstede GN')
  ,('Lageland GN')
;


--------------------------------------------------------------------------------
-- Stamgegeven: Autoriteit van afgifte buitenlands persoonsnummer
--------------------------------------------------------------------------------
INSERT INTO Kern.AutVanAfgifteBLPersnr (Code, Nation, LandGebied, DatAanvGel) VALUES
   ('0027', (SELECT ID FROM Kern.Nation WHERE Code = '0027'), (SELECT ID FROM Kern.LandGebied WHERE Code = '6067'), NULL),
   ('0028', (SELECT ID FROM Kern.Nation WHERE Code = '0028'), (SELECT ID FROM Kern.LandGebied WHERE Code = '6066'), NULL),
   ('0042', (SELECT ID FROM Kern.Nation WHERE Code = '0042'), (SELECT ID FROM Kern.LandGebied WHERE Code = '5049'), NULL),
   ('0043', (SELECT ID FROM Kern.Nation WHERE Code = '0043'), (SELECT ID FROM Kern.LandGebied WHERE Code = '5051'), NULL),
   ('0044', (SELECT ID FROM Kern.Nation WHERE Code = '0044'), (SELECT ID FROM Kern.LandGebied WHERE Code = '7064'), NULL),
   ('0045', (SELECT ID FROM Kern.Nation WHERE Code = '0045'), (SELECT ID FROM Kern.LandGebied WHERE Code = '7065'), NULL),
   ('0046', (SELECT ID FROM Kern.Nation WHERE Code = '0046'), (SELECT ID FROM Kern.LandGebied WHERE Code = '7066'), NULL),
   ('0052', (SELECT ID FROM Kern.Nation WHERE Code = '0052'), (SELECT ID FROM Kern.LandGebied WHERE Code = '5010'), NULL),
   ('0053', (SELECT ID FROM Kern.Nation WHERE Code = '0053'), (SELECT ID FROM Kern.LandGebied WHERE Code = '7024'), NULL),
   ('0054', (SELECT ID FROM Kern.Nation WHERE Code = '0054'), (SELECT ID FROM Kern.LandGebied WHERE Code = '5015'), NULL),
   ('0055', (SELECT ID FROM Kern.Nation WHERE Code = '0055'), (SELECT ID FROM Kern.LandGebied WHERE Code = '9089'), NULL),
   ('0056', (SELECT ID FROM Kern.Nation WHERE Code = '0056'), (SELECT ID FROM Kern.LandGebied WHERE Code = '6002'), NULL),
   ('0057', (SELECT ID FROM Kern.Nation WHERE Code = '0057'), (SELECT ID FROM Kern.LandGebied WHERE Code = '5002'), NULL),
   ('0059', (SELECT ID FROM Kern.Nation WHERE Code = '0059'), (SELECT ID FROM Kern.LandGebied WHERE Code = '6003'), NULL),
   ('0060', (SELECT ID FROM Kern.Nation WHERE Code = '0060'), (SELECT ID FROM Kern.LandGebied WHERE Code = '6039'), NULL),
   ('0061', (SELECT ID FROM Kern.Nation WHERE Code = '0061'), (SELECT ID FROM Kern.LandGebied WHERE Code = '5017'), NULL),
   ('0062', (SELECT ID FROM Kern.Nation WHERE Code = '0062'), (SELECT ID FROM Kern.LandGebied WHERE Code = '6007'), NULL),
   ('0064', (SELECT ID FROM Kern.Nation WHERE Code = '0064'), (SELECT ID FROM Kern.LandGebied WHERE Code = '7044'), NULL),
   ('0067', (SELECT ID FROM Kern.Nation WHERE Code = '0067'), (SELECT ID FROM Kern.LandGebied WHERE Code = '6018'), NULL),
   ('0068', (SELECT ID FROM Kern.Nation WHERE Code = '0068'), (SELECT ID FROM Kern.LandGebied WHERE Code = '7003'), NULL),
   ('0071', (SELECT ID FROM Kern.Nation WHERE Code = '0071'), (SELECT ID FROM Kern.LandGebied WHERE Code = '5009'), NULL),
   ('0072', (SELECT ID FROM Kern.Nation WHERE Code = '0072'), (SELECT ID FROM Kern.LandGebied WHERE Code = '7028'), NULL),
   ('0073', (SELECT ID FROM Kern.Nation WHERE Code = '0073'), (SELECT ID FROM Kern.LandGebied WHERE Code = '7050'), NULL),
   ('0074', (SELECT ID FROM Kern.Nation WHERE Code = '0074'), (SELECT ID FROM Kern.LandGebied WHERE Code = '7047'), NULL),
   ('0077', (SELECT ID FROM Kern.Nation WHERE Code = '0077'), (SELECT ID FROM Kern.LandGebied WHERE Code = '6037'), NULL),
   ('0080', (SELECT ID FROM Kern.Nation WHERE Code = '0080'), (SELECT ID FROM Kern.LandGebied WHERE Code = '5039'), NULL),
   ('0308', (SELECT ID FROM Kern.Nation WHERE Code = '0308'), (SELECT ID FROM Kern.LandGebied WHERE Code = '5040'), NULL);


--------------------------------------------------------------------------------
-- Stamgegeven: Versie Stuf BG
--------------------------------------------------------------------------------
INSERT INTO Kern.VersieStufBG (ID, Nr, Oms, DatAanvGel, DatEindeGel) VALUES
   (1, '0310', 'Vertaling van BRP-bericht naar StUF BG versie 0310.', NULL, NULL);


--------------------------------------------------------------------------------
-- Stamgegeven: Vertaling berichtsoort BRP
--------------------------------------------------------------------------------
INSERT INTO Kern.VertalingBersrtBRP (ID, Naam, Oms, DatAanvGel, DatEindeGel) VALUES
   (1, 'Mutatiebericht', 'Het te vertalen BRP-bericht betreft een mutatiebericht.', NULL, NULL),
   (2, 'Volledigbericht', 'Het te vertalen BRP-bericht betreft een volledigbericht.', NULL, NULL);


--------------------------------------------------------------------------------
-- Stamgegeven: Conversie overig
--------------------------------------------------------------------------------
-- Handmatige SQL code zodat vulling conversietabellen consistent is met vulling stamtabellen
INSERT INTO Conv.ConvAandInhingVermissingReis(ID, Rubr3570AandInhingDanWelVerm, AandInhingVermissingReisdoc)
  SELECT ID, Code, ID FROM Kern.AandInhingVermissingReisdoc;
UPDATE Conv.ConvAandInhingVermissingReis
  SET Rubr3570AandInhingDanWelVerm = '.' WHERE Rubr3570AandInhingDanWelVerm = '?';
  
INSERT INTO Conv.ConvSrtNLReisdoc(ID, Rubr3511NLReisdoc, srtnlreisdoc)
  SELECT ID, Code, ID FROM kern.SrtNLReisdoc;
UPDATE Conv.ConvSrtNLReisdoc SET Rubr3511NLReisdoc = '..' WHERE Rubr3511NLReisdoc = '?';

INSERT INTO Conv.ConvRdnOpschorting (ID, Rubr6720OmsRdnOpschortingBij, Naderebijhaard)
  SELECT ID, Code, ID FROM kern.Naderebijhaard;
UPDATE Conv.ConvRdnOpschorting SET Rubr6720OmsRdnOpschortingBij = '.' WHERE Rubr6720OmsRdnOpschortingBij = '?';

INSERT INTO Conv.ConvRdnOntbindingHuwelijkGer(ID, rubr0741rdnontbindinghuwelij, RdnEindeRelatie)
  SELECT ID, Code, ID FROM kern.RdnEindeRelatie;
UPDATE Conv.ConvRdnOntbindingHuwelijkGer SET rubr0741rdnontbindinghuwelij = '.' WHERE rubr0741rdnontbindinghuwelij = '?';


--------------------------------------------------------------------------------
-- Stamgegeven: Partij Afnemers
--------------------------------------------------------------------------------
INSERT INTO Kern.Partij(Code, Naam, DatIngang, DatEinde, IndVerstrBeperkingMogelijk, IndAG, DatIngangVrijBer, IndAGVrijBer) VALUES
   ('200001', 'Inspectie SZW/Handhaving', 20120301, NULL, false, true, null, false),
   ('200101', 'Centrum voor Werk en Inkomen (CWI)', 20020101, 20100501, false, true, null, false),
   ('200801', 'Samenwerkingsverband Vastgoedinformatie Heffing en Waardebepaling (SVHW) (2)', 20050301, 20080901, false, true, 20050301, true),
   ('201101', 'Regionaal Bureau Leerlingzaken Kop van Noord-Holland', 20030601, 20151001, false, true, 20030601, true),
   ('201301', 'Regionaal Bureau Leerlingzaken Holland Rijnland', 20081201, NULL, false, true, 20081201, true),
   ('201401', 'Regionaal Bureau Leerlingzaken Noord-Veluwe', 20080901, NULL, false, true, 20080901, true),
   ('201601', 'Samenwerkingsverband Regio Eindhoven', 20110901, NULL, false, true, 20110901, true),
   ('250011', 'Belastingdienst - CBI (selectie)', 20090201, 20120901, false, true, null, false),
   ('250101', 'Belastingsamenwerking voor Gemeenten en Waterschappen', 19950201, NULL, false, true, 19950201, true),
   ('250201', 'Samenwerkingsverband Vastgoedinformatie Heffing en Waardebepaling (SVHW)', 19950201, NULL, false, true, 19950201, true),
   ('250301', 'Hoogheemraadschap Amstel, Gooi en Vecht', 19950201, NULL, false, true, 19950201, true),
   ('250302', 'Hoogheemraadschap Amstel, Gooi en Vecht (selectie)', 20120101, 20120901, false, true, null, false),
   ('250401', 'Belastingsamenwerking West-Brabant', 20121001, NULL, false, true, 20121001, true),
   ('250501', 'Waterschap Roer en Overmaas', 19950801, 20040901, false, true, 19950801, true),
   ('250601', 'Regionale Belasting Groep (1)', 20140601, 20160701, false, true, 20140601, true),
   ('250602', 'Regionale Belasting Groep (2)', 20140601, 20160601, false, true, 20140601, true),
   ('250801', 'Samenwerkingsverband Vierstromengebied', 19981101, 20030301, false, true, 19981101, true),
   ('250802', 'Samenwerkingsverband Vierstromengebied (2)', 19990901, 20030301, false, true, 19990901, true),
   ('250902', 'Belastingsamenwerking Gouwe-Rijnland (2)', 20140801, 20151201, false, true, 20140801, true),
   ('250903', 'Belastingkantoor Gouwe-Rijnland (selectie)', 20101101, 20120901, false, true, null, false),
   ('250912', 'Belastingsamenwerking Gouwe-Rijnland (2) (pilot)', 20120401, 20121001, false, true, null, false),
   ('251001', 'Waterschap Scheldestromen', 19960501, NULL, false, true, 19960501, true),
   ('251101', 'Hoogheemraadschap Hollands Noorderkwartier', 19960801, NULL, false, true, 19960801, true),
   ('251201', 'Hoogheemraadschap De Stichtse Rijnlanden', 20131201, NULL, false, true, null, false),
   ('251301', 'Het Waterschapshuis', 20080901, 20081001, false, true, null, false),
   ('251401', 'Waterschap Vallei en Veluwe', 20130401, NULL, false, true, null, false),
   ('251402', 'Waterschap Vallei en Veluwe (SaWa)', 20150101, NULL, false, true, null, false),
   ('251501', 'Gemeentelijk Belastingkantoor Twente', 20090601, NULL, false, true, 20090601, true),
   ('251601', 'Waterschap Regge en Dinkel', 19970201, 20070301, false, true, 19970201, true),
   ('251701', 'Gemeentelijk Belastingkantoor Aalsmeer-Uithoorn', 20100901, 20110301, false, true, 20100901, true),
   ('251801', 'Waterschap Groot Salland (1)', 19970201, 19990601, false, true, 19970201, true),
   ('251802', 'Waterschap Groot Salland', 19970201, 20070301, false, true, 19970201, true),
   ('251901', 'Zuiveringsbeheer Provincie Groningen', 19961101, 20000301, false, true, 19961101, true),
   ('252001', 'Belastingsamenwerking Oost-Brabant', 19970501, NULL, false, true, 19970501, true),
   ('252002', 'Waterschap De Dommel (selectie)', 20111001, 20120901, false, true, null, false),
   ('252011', 'Belastingsamenwerking Oost-Brabant (pilot)', 20120501, 20120717, false, true, 20120501, true),
   ('252101', 'Waterschap Veluwe', 19970101, 20130401, false, true, null, false),
   ('252102', 'Waterschap Veluwe (2)', 19970101, 19991201, false, true, 19970101, true),
   ('252201', 'Zuiveringsschap Drenthe', 19970801, 20000301, false, true, 19970801, true),
   ('252301', 'Waterschap Zeeuws-Vlaanderen', 19970501, 20110501, false, true, 19970501, true),
   ('252401', 'Waterschap Hulster Ambacht', 19970501, 19990301, false, true, 19970501, true),
   ('252501', 'Waterschap Het Vrije van Sluis', 19970501, 19990301, false, true, 19970501, true),
   ('252601', 'Belastingsamenwerking gemeenten en hoogheemraadschap Utrecht', 20131001, NULL, false, true, 20131001, true),
   ('252701', 'Waterschap Peel en Maasvallei', 19970501, 20040901, false, true, 19970501, true),
   ('252702', 'Waterschap Peel en Maasvallei (SaWa)', 20150101, 20170301, false, true, null, false),
   ('252801', 'Waterschap Rijn en IJssel', 19970501, 20070301, false, true, 19970501, true),
   ('252901', 'Belastingsamenwerking Rivierenland', 19980501, NULL, false, true, 19980501, true),
   ('253102', 'Wetterskip Fryslân (2)', 20000601, 20081201, false, true, 20000601, true),
   ('253201', 'Waterschap Zuiderzeeland', 19981101, NULL, false, true, null, false),
   ('253301', 'Hoogheemraadschap Rijnland', 20140601, NULL, false, true, null, false),
   ('253401', 'Hoogheemraadschap Schieland en de Krimpenerwaard', 20140601, NULL, false, true, null, false),
   ('253501', 'Hoogheemraadschap Delfland', 20140601, NULL, false, true, null, false),
   ('253601', 'Waterschap Hunze en Aa''s', 20140701, NULL, false, true, null, false),
   ('253701', 'Waterschap Noorderzijlvest', 20000301, 20081201, false, true, 20000301, true),
   ('253801', 'Waterschap Reest en Wieden', 20000301, 20070301, false, true, 20000301, true),
   ('253901', 'Waterschap Velt en Vecht', 20000301, 20070301, false, true, 20000301, true),
   ('254001', 'Gemeentelijk Belastingkantoor Lococensus-Tricijn', 20130401, NULL, false, true, 20130401, true),
   ('254002', 'Lococensus (selectie)', 20110901, 20120901, false, true, null, false),
   ('254101', 'Gemeentelijk Belastingkantoor Rijn-Midden', 20071201, 20130601, false, true, 20071201, true),
   ('254102', 'Gemeentelijk Belastingkantoor Rijn-Midden (selectie)', 20100601, 20120901, false, true, null, false),
   ('254201', 'Gemeenschappelijke regeling Cocensus', 20080901, NULL, false, true, 20080901, true),
   ('254301', 'Gemeenschappelijke regeling Drechtsteden', 20080901, NULL, false, true, 20080901, true),
   ('254501', 'Gemeenschappelijke regeling Parkstad Limburg', 20100301, 20150201, false, true, 20100301, true),
   ('254601', 'MvSZW/Landelijk Register Kinderopvang', 20100901, NULL, false, true, null, false),
   ('254701', 'Ministerie van Financiën/Rijksvastgoed en Ontwikkelingsbedrijf (RVOB)', 20100601, NULL, false, true, null, false),
   ('254801', 'Waterschap Brabantse Delta', 20140701, NULL, false, true, null, false),
   ('254902', 'Waterschap Hollandse Delta (SaWa)', 20150101, NULL, false, true, null, false),
   ('255001', 'MvI&M/Rijkswaterstaat/verontreiningsheffing rijkswateren', 20061201, NULL, false, true, 20061201, true),
   ('300001', 'Bureau Vestigingsregister', 19941001, 20071201, false, true, 19941001, true),
   ('300002', 'PIVA-GBA Koppeling (PGK)', 20150101, NULL, false, true, 20150101, true),
   ('300003', 'Afnemer Registratie Niet-Ingezetenen (RNI)', 20140106, NULL, false, true, null, false),
   ('300010', 'Beheervoorziening BSN (nummerregister)', 20030601, NULL, false, true, 20030601, true),
   ('300012', 'Bureau Vestigingsregister/PIVA-GBA (pilot)', 20120401, 20121001, false, true, null, false),
   ('300024', 'Stichting Vaarbewijs- en Marifoonexamens (VAMEX)', 20091101, NULL, false, true, null, false),
   ('300030', 'Beheervoorziening BSN (presentievraag)', 20071126, 20130101, false, true, 20071126, true),
   ('300031', 'Veolia Transport Limburg', 20091201, 20170101, false, true, null, false),
   ('300101', 'Kanselarij der Nederlandse Orden', 19940901, NULL, false, true, 19940901, true),
   ('300102', 'Kanselarij der Nederlandse Orden (selectie)', 20120501, 20120901, false, true, null, false),
   ('300201', 'MvVWS/BIG (Beroepen in de Individuele Gezondheidszorg)-register', 20150101, NULL, false, true, 20150101, true),
   ('300202', 'MvVWS/BIG-register (selectie)', 20120601, 20120901, false, true, null, false),
   ('300301', 'Voedsel en Waren Autoriteit', 20020601, NULL, false, true, null, false),
   ('300401', 'Stichting Ambulante FIOM', 20020301, NULL, false, true, null, false),
   ('300501', 'Hoge Raad van Adel', 20040901, NULL, false, true, 20040901, true),
   ('300601', 'MvBZK/Analyse, Arbeidsmarkt en Economie', 20090301, NULL, false, true, null, false),
   ('300701', 'MvVWS/Klantenloket', 20090101, 20130101, false, true, null, false),
   ('300801', 'MvVWS/IGZ Opsporing', 20090701, NULL, false, true, null, false),
   ('300802', 'MvVWS/IGZ Toezicht', 20090701, NULL, false, true, null, false),
   ('300901', 'MvBZK/Registratie Niet-Ingezetenen (RNI) (selectie)', 20090901, 20120901, false, true, null, false),
   ('301101', 'Het Nederlandse Rode Kruis/Opsporing', 20131201, NULL, false, true, null, false),
   ('301201', 'MvVWS/UZI (Unieke Zorgverlener Identificatie)-register', 20150101, NULL, false, true, null, false),
   ('390001', 'Agentschap BPR/Register Paspoortsignaleringen (RPS) (selectie)', 20120801, 20150101, false, true, null, false),
   ('400101', 'CBS1: Structuurtelling (selectie)', 19941001, NULL, false, true, null, false),
   ('400102', 'CBS2: Verhuizing', 19941001, 20120716, false, true, 19941001, true),
   ('400103', 'CBS3: Geboorte & Afstamming (kind)', 19941001, 20120716, false, true, 19941001, true),
   ('400104', 'CBS4: Geboorte & Afstamming (ouder)', 19941001, 20120716, false, true, 19941001, true),
   ('400105', 'CBS5: Overlijden', 19941001, 20120716, false, true, 19941001, true),
   ('400106', 'CBS6: Huwelijk/geregistreerd partnerschap', 19941001, 20120716, false, true, 19941001, true),
   ('400107', 'CBS7: Nationaliteit', 19941001, 20120716, false, true, 19941001, true),
   ('400108', 'CBS8: Ad hoc', 19941001, 20120601, false, true, 19941001, true),
   ('400109', 'CBS9: Sofinummer', 20050601, 20120716, false, true, 20050601, true),
   ('400110', 'CBS10: PROBAS', 20100601, 20130601, false, true, 20100601, true),
   ('400113', 'CBS3: Geboorte & Afstamming (kind)', 20120401, NULL, false, true, 20120401, true),
   ('400114', 'CBS4: Geboorte & Afstamming (ouder)', 20120401, NULL, false, true, 20120401, true),
   ('400115', 'CBS5: Overlijden', 20120401, NULL, false, true, 20120401, true),
   ('400116', 'CBS6: Huwelijk/geregistreerd partnerschap', 20120401, NULL, false, true, 20120401, true),
   ('400117', 'CBS7: Nationaliteit', 20120401, NULL, false, true, 20120401, true),
   ('400118', 'CBS8: Ad hoc', 20120101, NULL, false, true, 20120101, true),
   ('400119', 'CBS9: A-nummer/Burgerservicenummer', 20130301, NULL, false, true, 20130301, true),
   ('400201', 'Centraal Bureau voor Genealogie', 19941001, NULL, false, true, 19941001, true),
   ('400301', 'Universiteit Utrecht/Voornamen onderzoek', 20150101, NULL, false, true, null, false),
   ('400401', 'Provincie Utrecht', 20091201, NULL, false, true, null, false),
   ('400501', 'Provincie Noord-Holland', 20100101, NULL, false, true, null, false),
   ('400601', 'Provincie Noord-Brabant', 20100101, NULL, false, true, null, false),
   ('400701', 'Provincie Gelderland', 20100201, NULL, false, true, null, false),
   ('400801', 'Provincie Flevoland', 20100201, NULL, false, true, null, false),
   ('400901', 'Provincie Zeeland', 20100301, NULL, false, true, null, false),
   ('401001', 'Provincie Zuid-Holland', 20100301, NULL, false, true, null, false),
   ('401101', 'Provincie Overijssel', 20100401, NULL, false, true, null, false),
   ('401201', 'Provincie Limburg', 20110101, NULL, false, true, null, false),
   ('401301', 'Provincie Drenthe', 20110701, NULL, false, true, null, false),
   ('401401', 'Provincie Groningen', 20110701, NULL, false, true, null, false),
   ('401501', 'Provincie Fryslân', 20141201, NULL, false, true, null, false),
   ('410001', 'Regionale Sociale Dienst Pentasz Mergelland', 20040601, 20161001, false, true, 20040601, true),
   ('410101', 'Dienst Sociale Zaken en Werkgelegenheid Noardwest-Fryslân', 20040601, NULL, false, true, 20040601, true),
   ('410301', 'Intergemeentelijke Sociale Dienst Voorne-Putten/Rozenburg', 20041201, 20120301, false, true, 20041201, true),
   ('410401', 'Uitvoeringsorganisatie Baanbrekers', 20130901, NULL, false, true, 20130901, true),
   ('410501', 'Intergemeentelijke Sociale Dienst Zuidwest-Fryslân', 20050901, 20110301, false, true, 20050901, true),
   ('410601', 'Intergemeentelijke Sociale Dienst OptimISD', 20050901, 20170201, false, true, 20050901, true),
   ('410801', 'Intergemeentelijke Sociale Dienst Oldambt', 20050901, 20100601, false, true, 20050901, true),
   ('410901', 'Intergemeentelijke Sociale Dienst Noordenkwartier', 20050901, NULL, false, true, 20050901, true),
   ('411101', 'Intergemeentelijke Sociale Dienst Kompas', 20061201, NULL, false, true, 20061201, true),
   ('411201', 'Gemeenschappelijke regeling Orionis Walcheren', 20130601, NULL, false, true, 20130601, true),
   ('411301', 'Intergemeentelijke Sociale Dienst Noordoost', 20060901, NULL, false, true, 20060901, true),
   ('411401', 'Intergemeentelijke Sociale Dienst De Rijnstreek', 20061201, 20120601, false, true, 20061201, true),
   ('411501', 'Regionale Sociale Dienst Bommelerwaard', 20070601, 20160301, false, true, 20070601, true),
   ('411601', 'Intergemeentelijke Sociale Dienst Drechtsteden', 20070301, NULL, false, true, 20070301, true),
   ('411701', 'Intergemeentelijke Sociale Dienst Kop van Noord-Holland', 20070901, 20151001, false, true, 20070901, true),
   ('411801', 'Intergemeentelijke Sociale Dienst Brunssum, Onderbanken en Landgraaf (BOL)', 20071201, NULL, false, true, 20071201, true),
   ('411901', 'Intergemeentelijke Sociale Dienst Steenwijkerland en Westerveld', 20081201, NULL, false, true, 20081201, true),
   ('412001', 'Regionale Sociale Dienst Kromme Rijn Heuvelrug', 20090901, NULL, false, true, 20090901, true),
   ('412101', 'Intergemeentelijke Sociale Dienst Bollenstreek', 20090901, NULL, false, true, 20090901, true),
   ('412201', 'Regionale Sociale Dienst Aalsmeer-Uithoorn', 20100901, 20110301, false, true, 20100901, true),
   ('412501', 'Gemeenschappelijke regeling K5-gemeenten', 20100901, 20150601, false, true, 20100901, true),
   ('412701', 'Sociale Dienst Veluwerand', 20101201, NULL, false, true, 20101201, true),
   ('412801', 'Atlant Groep', 20110301, NULL, false, true, 20110301, true),
   ('412901', 'Werkvoorzieningsschap West Noord-Brabant (WVS-Groep)', 20110301, NULL, false, true, 20110301, true),
   ('413401', 'Diamant-groep', 20110901, NULL, false, true, 20110901, true),
   ('413501', 'Werkvoorzieningsschap Noord-Kennemerland (WNK Bedrijven)', 20110901, NULL, false, true, 20110901, true),
   ('413601', 'Sociaal Werkvoorzieningsbedrijf IJmond Werkt', 20141001, NULL, false, true, 20141001, true),
   ('413701', 'Gemeenschappelijke regeling Werkvoorzieningschap Fryslân-West', 20111201, NULL, false, true, 20111201, true),
   ('413801', 'Werkvoorzieningschap De Dommel (WSD)', 20111201, NULL, false, true, 20111201, true),
   ('413901', 'Werkvoorzieningschap Breed', 20120716, 20161201, false, true, 20120716, true),
   ('414001', 'Gemeenschappelijke regeling Sociaal Werkvoorzieningsbedrijf Midden-Twente', 20120716, NULL, false, true, 20120716, true),
   ('414201', 'Gemeenschappelijke regeling Sociale Werkvoorziening Fryslân', 20120901, NULL, false, true, 20120901, true),
   ('414301', 'Regionale Sociale Dienst Werk & Inkomen Lekstroom', 20130601, NULL, false, true, 20130601, true),
   ('414401', 'Werkvoorzieningschap Tomingroep', 20140101, NULL, false, true, 20140101, true),
   ('414501', 'Werkvoorzieningschap Noordoost-Brabant', 20140101, NULL, false, true, 20140101, true),
   ('414601', 'Gemeenschappelijke regeling Alescon', 20140201, NULL, false, true, 20140201, true),
   ('414701', 'Regionale Sociale Dienst WerkSaam Westfriesland', 20150101, NULL, false, true, 20150101, true),
   ('450001', 'Dienst Omroepbijdragen', 19950501, 20000601, false, true, 19950501, true),
   ('450002', 'Dienst Omroepbijdragen (2)', 19991201, 20000601, false, true, 19991201, true),
   ('450101', 'Kamers van Koophandel en Fabrieken', 19960201, NULL, false, true, 19960201, true),
   ('450102', 'Kamers van Koophandel en Fabrieken (selectie)', 20120601, 20120901, false, true, null, false),
   ('450201', 'MvBZK/e-Overheid voor Burgers', 20071201, NULL, false, true, null, false),
   ('450301', 'eFormulieren/ICTU', 20080901, 20100201, false, true, null, false),
   ('450401', 'DigiD Machtigen', 20090301, NULL, false, true, null, false),
   ('450501', 'eFormulieren typo-3', 20100101, NULL, false, true, null, false),
   ('450601', 'eFormulieren - Gemeente Hoorn', 20100801, NULL, false, true, null, false),
   ('450801', 'eFormulieren - SIMgroep', 20120401, NULL, false, true, null, false),
   ('450902', 'MvBZK/Bestuur, Democratie en Financiën/EP-verkiezingen', 20140401, NULL, false, true, null, false),
   ('451001', 'Kiesraad', 20140301, NULL, false, true, null, false),
   ('451101', 'MvBZK/MijnOverheid/machtigen', 20140901, NULL, false, true, null, false),
   ('510001', 'Afnemer Rotterdam', 19970801, NULL, false, true, 19970801, true),
   ('510002', 'Afnemer Oostzaan', 19971101, NULL, false, true, 19971101, true),
   ('510003', 'Afnemer Rozenburg', 19971101, 20100318, false, true, 19971101, true),
   ('510004', 'Afnemer Tilburg', 20030901, NULL, false, true, 20030901, true),
   ('510005', 'Afnemer Almelo', 19971101, NULL, false, true, 19971101, true),
   ('510006', 'Afnemer Bergh', 19971101, 20050301, false, true, 19971101, true),
   ('510007', 'Afnemer Beuningen', 19971101, NULL, false, true, 19971101, true),
   ('510008', 'Afnemer Diemen', 19971101, NULL, false, true, 19971101, true),
   ('510009', 'Afnemer Dirksland', 19971101, 20130201, false, true, 19971101, true),
   ('510010', 'Afnemer Enkhuizen', 19971101, NULL, false, true, 19971101, true),
   ('510011', 'Afnemer Goedereede', 19971101, 20130201, false, true, 19971101, true),
   ('510012', 'Afnemer Gorssel', 19971101, 20050101, false, true, 19971101, true),
   ('510014', 'Afnemer Groningen', 19971101, NULL, false, true, 19971101, true),
   ('510015', 'Afnemer Hattem', 19971101, NULL, false, true, 19971101, true),
   ('510016', 'Afnemer Leerdam', 19980501, NULL, false, true, 19980501, true),
   ('510017', 'Afnemer Leeuwarden', 20050901, NULL, false, true, 20050901, true),
   ('510018', 'Afnemer Best', 19971101, NULL, false, true, 19971101, true),
   ('510019', 'Afnemer Delft', 19980201, NULL, false, true, 19980201, true),
   ('510020', 'Afnemer Utrechtse Heuvelrug', 19971101, NULL, false, true, 19971101, true),
   ('510021', 'Afnemer Druten', 19971101, NULL, false, true, 19971101, true),
   ('510022', 'Afnemer Elburg', 19971101, NULL, false, true, 19971101, true),
   ('510023', 'Afnemer Rijswijk', 20080601, NULL, false, true, 20080601, true),
   ('510024', 'Afnemer Grave', 19971101, NULL, false, true, 19971101, true),
   ('510025', 'Afnemer Harenkarspel', 19971101, 20130201, false, true, 19971101, true),
   ('510026', 'Afnemer Huizen', 19971101, NULL, false, true, 19971101, true),
   ('510027', 'Afnemer Dantumadiel', 20031201, NULL, false, true, 20031201, true),
   ('510028', 'Afnemer IJsselmuiden', 19971101, 20010101, false, true, 19971101, true),
   ('510029', 'Afnemer Losser', 20060601, NULL, false, true, 20060601, true),
   ('510030', 'Afnemer Langedijk', 19971101, NULL, false, true, 19971101, true),
   ('510031', 'Afnemer Woensdrecht', 20081201, NULL, false, true, 20081201, true),
   ('510032', 'Afnemer Niedorp', 20040601, 20120101, false, true, 20040601, true),
   ('510033', 'Afnemer Woerden', 20040301, NULL, false, true, 20040301, true),
   ('510034', 'Afnemer Voerendaal', 20050601, NULL, false, true, 20050601, true),
   ('510035', 'Afnemer Sliedrecht', 19971101, NULL, false, true, 19971101, true),
   ('510036', 'Afnemer Vlagtwedde', 19971101, NULL, false, true, 19971101, true),
   ('510037', 'Afnemer Vlissingen', 19980201, NULL, false, true, 19980201, true),
   ('510038', 'Afnemer Wierden', 19971101, NULL, false, true, 19971101, true),
   ('510039', 'Afnemer Zandvoort', 19971101, NULL, false, true, 19971101, true),
   ('510040', 'Afnemer Zijpe', 19971101, 20130201, false, true, 19971101, true),
   ('510041', 'Afnemer Ameland', 19980201, NULL, false, true, 19980201, true),
   ('510042', 'Afnemer Apeldoorn', 20011201, NULL, false, true, 20011201, true),
   ('510043', 'Afnemer Terneuzen', 19980201, NULL, false, true, 19980201, true),
   ('510044', 'Afnemer Bergen op Zoom', 20020901, NULL, false, true, 20020901, true),
   ('510045', 'Afnemer Bernisse', 19980201, 20150201, false, true, 19980201, true),
   ('510046', 'Afnemer Bloemendaal', 19980201, NULL, false, true, 19980201, true),
   ('510047', 'Afnemer Borsele', 19980201, NULL, false, true, 19980201, true),
   ('510048', 'Afnemer ''s-Gravenhage', 20141201, NULL, false, true, 20141201, true),
   ('510049', 'Afnemer Montferland', 19980201, NULL, false, true, 19980201, true),
   ('510050', 'Afnemer Epe', 19980201, NULL, false, true, 19980201, true),
   ('510051', 'Afnemer Gennep', 19980201, NULL, false, true, 19980201, true),
   ('510052', 'Afnemer Haarlem', 19980201, NULL, false, true, 19980201, true),
   ('510053', 'Afnemer Haarlemmermeer', 19990601, NULL, false, true, 19990601, true),
   ('510054', 'Afnemer Alkmaar', 20060301, NULL, false, true, 20060301, true),
   ('510055', 'Afnemer Vught', 20060601, NULL, false, true, 20060601, true),
   ('510056', 'Afnemer Leiderdorp', 19980201, NULL, false, true, 19980201, true),
   ('510057', 'Afnemer Buren', 19980201, NULL, false, true, 19980201, true),
   ('510058', 'Afnemer Noordwijk', 19980201, NULL, false, true, 19980201, true),
   ('510059', 'Afnemer Nunspeet', 19980201, NULL, false, true, 19980201, true),
   ('510060', 'Afnemer Oegstgeest', 19980201, NULL, false, true, 19980201, true),
   ('510061', 'Afnemer Hilvarenbeek', 20060601, NULL, false, true, 20060601, true),
   ('510062', 'Afnemer Reeuwijk', 19980201, 20110101, false, true, 19980201, true),
   ('510063', 'Afnemer Reusel-De Mierden', 19980201, NULL, false, true, 19980201, true),
   ('510064', 'Afnemer Sevenum', 19980201, 20100101, false, true, 19980201, true),
   ('510065', 'Afnemer Slochteren', 19980201, NULL, false, true, 19980201, true),
   ('510066', 'Afnemer Smallingerland', 19980201, NULL, false, true, 19980201, true),
   ('510067', 'Afnemer Staphorst', 19980201, NULL, false, true, 19980201, true),
   ('510068', 'Afnemer Oisterwijk', 20050901, NULL, false, true, 20050901, true),
   ('510069', 'Afnemer Ubbergen', 19980201, 20150201, false, true, 19980201, true),
   ('510070', 'Afnemer Vaals', 19980201, NULL, false, true, 19980201, true),
   ('510071', 'Afnemer Vianen', 19980201, NULL, false, true, 19980201, true),
   ('510072', 'Afnemer Weert', 20011201, NULL, false, true, 20011201, true),
   ('510073', 'Afnemer Valkenswaard', 20060601, NULL, false, true, 20060601, true),
   ('510074', 'Afnemer Meppel', 20050901, NULL, false, true, 20050901, true),
   ('510075', 'Afnemer Zwolle', 19980201, NULL, false, true, 19980201, true),
   ('510076', 'Afnemer Alkmaar', 19980501, 20020301, false, true, 19980501, true),
   ('510077', 'Afnemer Geldrop-Mierlo', 20060901, NULL, false, true, 20060901, true),
   ('510078', 'Afnemer Beverwijk', 19980501, NULL, false, true, 19980501, true),
   ('510079', 'Afnemer Bladel', 19980501, NULL, false, true, 19980501, true),
   ('510080', 'Afnemer Veldhoven', 20060601, NULL, false, true, 20060601, true),
   ('510081', 'Afnemer Twenterand', 19980501, NULL, false, true, 19980501, true),
   ('510082', 'Afnemer Deventer', 19980501, NULL, false, true, 19980501, true),
   ('510083', 'Afnemer Graafstroom', 19980501, 20130201, false, true, 19980501, true),
   ('510084', 'Afnemer Wijdemeren', 19980501, NULL, false, true, 19980501, true),
   ('510085', 'Afnemer Grubbenvorst', 19980501, 20010101, false, true, 19980501, true),
   ('510086', 'Afnemer Oude IJsselstreek', 20050901, NULL, false, true, 20050901, true),
   ('510087', 'Afnemer Heeze-Leende', 19980501, NULL, false, true, 19980501, true),
   ('510088', 'Afnemer Hummelo en Keppel', 19980501, 20050101, false, true, 19980501, true),
   ('510089', 'Afnemer Laren', 19981101, NULL, false, true, 19981101, true),
   ('510090', 'Afnemer Lisse', 19980501, NULL, false, true, 19980501, true),
   ('510091', 'Afnemer Loenen', 19980501, 20110101, false, true, 19980501, true),
   ('510092', 'Afnemer Noordenveld', 19981101, NULL, false, true, 19981101, true),
   ('510093', 'Afnemer Noordwijkerhout', 19980501, NULL, false, true, 19980501, true),
   ('510094', 'Afnemer Pekela', 19980501, NULL, false, true, 19980501, true),
   ('510095', 'Afnemer Rheden', 19980501, NULL, false, true, 19980501, true),
   ('510096', 'Afnemer Schiermonnikoog', 19991201, 20071201, false, true, 19991201, true),
   ('510097', 'Afnemer Drimmelen', 20050901, NULL, false, true, 20050901, true),
   ('510098', 'Afnemer Krimpenerwaard', 20150101, NULL, false, true, 20150101, true),
   ('510099', 'Afnemer Venray', 19981101, NULL, false, true, 19981101, true),
   ('510100', 'Afnemer Abcoude', 19980501, 19980502, false, true, 19980501, true),
   ('510101', 'Afnemer Ambt Delden', 19980501, 20010101, false, true, 19980501, true),
   ('510102', 'Afnemer Amerongen', 19981101, 20060101, false, true, 19981101, true),
   ('510103', 'Afnemer Lansingerland', 19980501, NULL, false, true, 19980501, true),
   ('510104', 'Afnemer Binnenmaas', 19980501, NULL, false, true, 19980501, true),
   ('510105', 'Afnemer Dalfsen', 19980501, NULL, false, true, 19980501, true),
   ('510106', 'Afnemer Gramsbergen', 19980501, 20010101, false, true, 19980501, true),
   ('510107', 'Afnemer Heteren', 19980501, 20010101, false, true, 19980501, true),
   ('510108', 'Afnemer Baarn', 20050901, NULL, false, true, 20050901, true),
   ('510109', 'Afnemer Lochem', 19990301, NULL, false, true, 19990301, true),
   ('510110', 'Afnemer Dongen', 20060601, NULL, false, true, 20060601, true),
   ('510111', 'Afnemer Nieuw-Lekkerland', 19980501, 20060901, false, true, 19980501, true),
   ('510112', 'Afnemer Berkelland', 19980501, NULL, false, true, 19980501, true),
   ('510113', 'Afnemer Someren', 19990301, NULL, false, true, 19990301, true),
   ('510114', 'Afnemer Weerselo', 19980501, 20010101, false, true, 19980501, true),
   ('510115', 'Afnemer Blaricum', 19980501, NULL, false, true, 19980501, true),
   ('510116', 'Afnemer Borger-Odoorn', 20031201, NULL, false, true, 20031201, true),
   ('510117', 'Afnemer Eemnes', 19980501, NULL, false, true, 19980501, true),
   ('510118', 'Afnemer Hoevelaken', 19980801, 20000101, false, true, 19980801, true),
   ('510119', 'Afnemer Leersum', 19980501, 20060101, false, true, 19980501, true),
   ('510120', 'Afnemer Oost Gelre', 19980501, NULL, false, true, 19980501, true),
   ('510121', 'Afnemer Nederlek', 19980501, 20150201, false, true, 19980501, true),
   ('510122', 'Afnemer Tubbergen', 19980501, NULL, false, true, 19980501, true),
   ('510123', 'Afnemer Zaanstad', 19980501, NULL, false, true, 19980501, true),
   ('510124', 'Afnemer Kaag en Braassem', 20020901, NULL, false, true, 20020901, true),
   ('510125', 'Afnemer Angerlo', 19980801, 20050301, false, true, 19980801, true),
   ('510126', 'Afnemer Laarbeek', 20060601, NULL, false, true, 20060601, true),
   ('510127', 'Afnemer Brederwiede', 19980801, 20010101, false, true, 19980801, true),
   ('510128', 'Afnemer Bussum', 20020301, 20160201, false, true, 20020301, true),
   ('510129', 'Afnemer Dodewaard', 19980801, 20020101, false, true, 19980801, true),
   ('510130', 'Afnemer Giessenlanden', 19980801, NULL, false, true, 19980801, true),
   ('510131', 'Afnemer Goes', 19980801, NULL, false, true, 19980801, true),
   ('510132', 'Afnemer Grootegast', 19980801, NULL, false, true, 19980801, true),
   ('510133', 'Afnemer Hengelo (O)', 19980801, NULL, false, true, 19980801, true),
   ('510134', 'Afnemer Neder-Betuwe', 19980801, NULL, false, true, 19980801, true),
   ('510135', 'Afnemer Krimpen aan den IJssel', 20050901, NULL, false, true, 20050901, true),
   ('510136', 'Afnemer Lingewaal', 19980801, NULL, false, true, 19980801, true),
   ('510137', 'Afnemer Meerssen', 19980801, NULL, false, true, 19980801, true),
   ('510138', 'Afnemer Nuenen, Gerwen en Nederwetten', 19980801, NULL, false, true, 19980801, true),
   ('510139', 'Afnemer Pijnacker-Nootdorp', 19990901, NULL, false, true, 19990901, true),
   ('510140', 'Afnemer Putten', 19980801, NULL, false, true, 19980801, true),
   ('510141', 'Afnemer Rijssen-Holten', 19980801, NULL, false, true, 19980801, true),
   ('510142', 'Afnemer Soest', 19980801, NULL, false, true, 19980801, true),
   ('510143', 'Afnemer Urk', 19980801, NULL, false, true, 19980801, true),
   ('510144', 'Afnemer Wassenaar', 19980801, NULL, false, true, 19980801, true),
   ('510145', 'Afnemer Wognum', 19981101, 20070101, false, true, 19981101, true),
   ('510146', 'Afnemer Zuidhorn', 19980801, NULL, false, true, 19980801, true),
   ('510147', 'Afnemer Ommen', 19990301, NULL, false, true, 19990301, true),
   ('510148', 'Afnemer Boxmeer', 20000601, NULL, false, true, 20000601, true),
   ('510149', 'Afnemer Breda', 19981101, NULL, false, true, 19981101, true),
   ('510150', 'Afnemer Brielle', 20010901, NULL, false, true, 20010901, true),
   ('510151', 'Afnemer De Wolden', 19990301, NULL, false, true, 19990301, true),
   ('510152', 'Afnemer Eijsden-Margraten', 19990301, NULL, false, true, 19990301, true),
   ('510153', 'Afnemer Heemskerk', 20031201, NULL, false, true, 20031201, true),
   ('510154', 'Afnemer Heemstede', 19990301, NULL, false, true, 19990301, true),
   ('510155', 'Afnemer Hoorn', 19990301, NULL, false, true, 19990301, true),
   ('510156', 'Afnemer Kerkrade', 19981101, NULL, false, true, 19981101, true),
   ('510157', 'Afnemer Liemeer', 19981101, 20070101, false, true, 19981101, true),
   ('510158', 'Afnemer Liesveld', 19990301, 20130201, false, true, 19990301, true),
   ('510159', 'Afnemer Maasdonk', 19981101, 20150201, false, true, 19981101, true),
   ('510160', 'Afnemer Maasdriel', 19981101, NULL, false, true, 19981101, true),
   ('510161', 'Afnemer Margraten', 19981101, 20110101, false, true, 19981101, true),
   ('510162', 'Afnemer Markelo', 19981101, 20010101, false, true, 19981101, true),
   ('510163', 'Afnemer Medemblik', 19990601, NULL, false, true, 19990601, true),
   ('510164', 'Afnemer Moerdijk', 19981101, NULL, false, true, 19981101, true),
   ('510165', 'Afnemer Stichtse Vecht', 20060601, NULL, false, true, 20060601, true),
   ('510166', 'Afnemer Nieuwkoop', 19981101, NULL, false, true, 19981101, true),
   ('510167', 'Afnemer Noorder-Koggenland', 19981101, 20070101, false, true, 19981101, true),
   ('510168', 'Afnemer Cranendonck', 20060301, NULL, false, true, 20060301, true),
   ('510169', 'Afnemer Noordoostpolder', 20051201, NULL, false, true, 20051201, true),
   ('510170', 'Afnemer Bunschoten', 20060301, NULL, false, true, 20060301, true),
   ('510171', 'Afnemer Midden-Drenthe', 20040601, NULL, false, true, 20040601, true),
   ('510172', 'Afnemer Vlaardingen', 19990301, NULL, false, true, 19990301, true),
   ('510173', 'Afnemer Koggenland', 19981101, NULL, false, true, 19981101, true),
   ('510174', 'Afnemer Abcoude', 19980501, 20110101, false, true, 19980501, true),
   ('510175', 'Afnemer Albrandswaard', 20040901, NULL, false, true, 20040901, true),
   ('510176', 'Afnemer Amersfoort', 20001201, NULL, false, true, 20001201, true),
   ('510177', 'Afnemer Breukelen', 19990301, 20110101, false, true, 19990301, true),
   ('510178', 'Afnemer Castricum', 20030901, NULL, false, true, 20030901, true),
   ('510179', 'Afnemer Eemsmond', 19990301, NULL, false, true, 19990301, true),
   ('510180', 'Afnemer Emmen', 19990301, NULL, false, true, 19990301, true),
   ('510181', 'Afnemer Hendrik-Ido-Ambacht', 19990901, NULL, false, true, 19990901, true),
   ('510182', 'Afnemer Leudal', 19990301, NULL, false, true, 19990301, true),
   ('510183', 'Afnemer Gulpen-Wittem', 20040601, NULL, false, true, 20040601, true),
   ('510184', 'Afnemer Schinnen', 20051201, NULL, false, true, 20051201, true),
   ('510185', 'Afnemer Cromstrijen', 20080601, NULL, false, true, 20080601, true),
   ('510186', 'Afnemer Purmerend', 19990301, NULL, false, true, 19990301, true),
   ('510187', 'Afnemer Reimerswaal', 19990301, NULL, false, true, 19990301, true),
   ('510189', 'Afnemer Schagen', 20030601, NULL, false, true, 20030601, true),
   ('510190', 'Afnemer Westerveld', 20051201, NULL, false, true, 20051201, true),
   ('510191', 'Afnemer Bronckhorst', 19990301, NULL, false, true, 19990301, true),
   ('510192', 'Afnemer Doesburg', 19990601, NULL, false, true, 19990601, true),
   ('510193', 'Afnemer Amsterdam', 19990601, NULL, false, true, 19990601, true),
   ('510194', 'Afnemer De Bilt', 19990601, NULL, false, true, 19990601, true),
   ('510195', 'Afnemer Hellevoetsluis', 19990601, NULL, false, true, 19990601, true),
   ('510196', 'Afnemer Hellendoorn', 19990601, NULL, false, true, 19990601, true),
   ('510197', 'Afnemer Hoogezand-Sappemeer', 19990601, NULL, false, true, 19990601, true),
   ('510198', 'Afnemer Meijel', 20040901, 20100101, false, true, 20040901, true),
   ('510199', 'Afnemer Maastricht', 20051201, NULL, false, true, 20051201, true),
   ('510201', 'Afnemer Cuijk', 20031201, NULL, false, true, 20031201, true),
   ('510202', 'Afnemer Ermelo', 19991201, NULL, false, true, 19991201, true),
   ('510203', 'Afnemer Hardinxveld-Giessendam', 19990901, NULL, false, true, 19990901, true),
   ('510204', 'Afnemer Heerlen', 20020601, NULL, false, true, 20020601, true),
   ('510205', 'Afnemer Huissen', 19990901, 20010101, false, true, 19990901, true),
   ('510206', 'Afnemer Nederweert', 19990901, NULL, false, true, 19990901, true),
   ('510207', 'Afnemer Weesp', 19990901, NULL, false, true, 19990901, true),
   ('510208', 'Afnemer Loosdrecht', 19991201, 20020101, false, true, 19991201, true),
   ('510209', 'Afnemer Zaltbommel', 19991201, NULL, false, true, 19991201, true),
   ('510210', 'Afnemer Midden-Delfland', 19990901, NULL, false, true, 19990901, true),
   ('510211', 'Afnemer Avereest', 20000301, 20010101, false, true, 20000301, true),
   ('510212', 'Afnemer Heusden', 20011201, NULL, false, true, 20011201, true),
   ('510213', 'Afnemer Deurne', 20001201, NULL, false, true, 20001201, true),
   ('510214', 'Afnemer Goirle', 20001201, NULL, false, true, 20001201, true),
   ('510215', 'Afnemer Aalten', 20001201, NULL, false, true, 20001201, true),
   ('510216', 'Afnemer Boarnsterhim', 20010901, 20140201, false, true, 20010901, true),
   ('510217', 'Afnemer Beek', 20010901, NULL, false, true, 20010901, true),
   ('510218', 'Afnemer Velsen', 20021201, NULL, false, true, 20021201, true),
   ('510219', 'Afnemer Eindhoven', 20001201, NULL, false, true, 20001201, true),
   ('510221', 'Afnemer Voorschoten', 20010901, NULL, false, true, 20010901, true),
   ('510222', 'Afnemer Westvoorne', 20010901, NULL, false, true, 20010901, true),
   ('510223', 'Afnemer Oldebroek', 20011201, NULL, false, true, 20011201, true),
   ('510224', 'Afnemer Alphen-Chaam', 20010901, NULL, false, true, 20010901, true),
   ('510225', 'Afnemer Oldambt', 20030601, NULL, false, true, 20030601, true),
   ('510226', 'Afnemer Hilversum', 20011201, NULL, false, true, 20011201, true),
   ('510227', 'Afnemer Zevenaar', 20040601, NULL, false, true, 20040601, true),
   ('510228', 'Afnemer Venlo', 20010901, NULL, false, true, 20010901, true),
   ('510229', 'Afnemer Hardenberg', 20011201, NULL, false, true, 20011201, true),
   ('510230', 'Afnemer Lingewaard', 20010901, NULL, false, true, 20010901, true),
   ('510231', 'Afnemer Dordrecht', 20091201, NULL, false, true, 20091201, true),
   ('510232', 'Afnemer Tholen', 20020601, NULL, false, true, 20020601, true),
   ('510233', 'Afnemer De Ronde Venen', 20020301, NULL, false, true, 20020301, true),
   ('510234', 'Afnemer Muiden', 20020601, 20160201, false, true, 20020601, true),
   ('510235', 'Afnemer Hoogeveen', 20020601, NULL, false, true, 20020601, true),
   ('510236', 'Afnemer Dinkelland', 20020601, NULL, false, true, 20020601, true),
   ('510237', 'Afnemer Voorst', 20040301, NULL, false, true, 20040301, true),
   ('510238', 'Afnemer Hillegom', 20031201, NULL, false, true, 20031201, true),
   ('510239', 'Afnemer Hof van Twente', 20040301, NULL, false, true, 20040301, true),
   ('510240', 'Afnemer Rijnwoude', 20020301, 20140201, false, true, 20020301, true),
   ('510241', 'Afnemer Harderwijk', 20040601, NULL, false, true, 20040601, true),
   ('510242', 'Afnemer Barneveld', 20021201, NULL, false, true, 20021201, true),
   ('510243', 'Afnemer Sluis', 20030901, NULL, false, true, 20030901, true),
   ('510244', 'Afnemer Boxtel', 20021201, NULL, false, true, 20021201, true),
   ('510245', 'Afnemer Helmond', 20021201, NULL, false, true, 20021201, true),
   ('510246', 'Afnemer Wymbritseradiel', 20021201, 20110101, false, true, 20021201, true),
   ('510247', 'Afnemer Zwartewaterland', 20030601, NULL, false, true, 20030601, true),
   ('510248', 'Afnemer Bodegraven-Reeuwijk', 20030601, NULL, false, true, 20030601, true),
   ('510249', 'Afnemer Nissewaard', 20150101, NULL, false, true, 20150101, true),
   ('510250', 'Afnemer Borne', 20011201, NULL, false, true, 20011201, true),
   ('510251', 'Afnemer Echt-Susteren', 20030601, NULL, false, true, 20030601, true),
   ('510252', 'Afnemer Duiven', 20030601, NULL, false, true, 20030601, true),
   ('510253', 'Afnemer Achtkarspelen', 20031201, NULL, false, true, 20031201, true),
   ('510254', 'Afnemer Tynaarlo', 20040601, NULL, false, true, 20040601, true),
   ('510255', 'Afnemer Leiden', 20040601, NULL, false, true, 20040601, true),
   ('510256', 'Afnemer Amstelveen', 20040301, NULL, false, true, 20040301, true),
   ('510257', 'Afnemer Barendrecht', 20040301, NULL, false, true, 20040301, true),
   ('510258', 'Afnemer Veenendaal', 20041201, NULL, false, true, 20041201, true),
   ('510259', 'Afnemer Zederik', 20040901, NULL, false, true, 20040901, true),
   ('510260', 'Afnemer Horst aan de Maas', 20020601, NULL, false, true, 20020601, true),
   ('510261', 'Afnemer Ooststellingwerf', 20040901, NULL, false, true, 20040901, true),
   ('510262', 'Afnemer Heiloo', 20050301, NULL, false, true, 20050301, true),
   ('510263', 'Afnemer Súdwest-Fryslân', 20040901, NULL, false, true, 20040901, true),
   ('510264', 'Afnemer Geertruidenberg', 20040601, NULL, false, true, 20040601, true),
   ('510265', 'Afnemer Zeewolde', 20040901, NULL, false, true, 20040901, true),
   ('510266', 'Afnemer Peel en Maas', 20040901, NULL, false, true, 20040901, true),
   ('510267', 'Afnemer Edam-Volendam', 20040901, NULL, false, true, 20040901, true),
   ('510268', 'Afnemer Coevorden', 20040901, NULL, false, true, 20040901, true),
   ('510269', 'Afnemer Winterswijk', 20040901, NULL, false, true, 20040901, true),
   ('510270', 'Afnemer Katwijk', 20020601, NULL, false, true, 20020601, true),
   ('510271', 'Afnemer Steenwijkerland', 20040901, NULL, false, true, 20040901, true),
   ('510272', 'Afnemer Landgraaf', 20040901, NULL, false, true, 20040901, true),
   ('510274', 'Afnemer Opmeer', 20041201, NULL, false, true, 20041201, true),
   ('510275', 'Afnemer Utrecht', 20041201, NULL, false, true, 20041201, true),
   ('510276', 'Afnemer Bergen (L)', 20040901, NULL, false, true, 20040901, true),
   ('510277', 'Afnemer Scheemda', 20051201, 20100101, false, true, 20051201, true),
   ('510278', 'Afnemer Westland', 20050301, NULL, false, true, 20050301, true),
   ('510279', 'Afnemer Moordrecht', 20050301, 20100101, false, true, 20050301, true),
   ('510280', 'Afnemer Ede', 20020601, NULL, false, true, 20020601, true),
   ('510281', 'Afnemer Oostflakkee', 20041201, 20130201, false, true, 20041201, true),
   ('510282', 'Afnemer Nuth', 20050901, NULL, false, true, 20050901, true),
   ('510283', 'Afnemer Goeree-Overflakkee', 20130101, NULL, false, true, 20130101, true),
   ('510284', 'Afnemer Overbetuwe', 20050601, NULL, false, true, 20050601, true),
   ('510285', 'Afnemer Zutphen', 20081201, NULL, false, true, 20081201, true),
   ('510286', 'Afnemer Zuidplas', 20050601, NULL, false, true, 20050601, true),
   ('510287', 'Afnemer Sint-Michielsgestel', 20050601, NULL, false, true, 20050601, true),
   ('510288', 'Afnemer Brunssum', 20061201, NULL, false, true, 20061201, true),
   ('510289', 'Afnemer Weststellingwerf', 20070601, NULL, false, true, 20070601, true),
   ('510290', 'Afnemer Waalwijk', 20020901, NULL, false, true, 20020901, true),
   ('510291', 'Afnemer Uden', 20070901, NULL, false, true, 20070901, true),
   ('510292', 'Afnemer Franekeradeel', 20081201, NULL, false, true, 20081201, true),
   ('510293', 'Afnemer Geldermalsen', 20070901, NULL, false, true, 20070901, true),
   ('510294', 'Afnemer Veere', 20060901, NULL, false, true, 20060901, true),
   ('510295', 'Afnemer Oosterhout', 20060901, NULL, false, true, 20060901, true),
   ('510296', 'Afnemer Uithoorn', 20060901, NULL, false, true, 20060901, true),
   ('510297', 'Afnemer Aalburg', 20070301, NULL, false, true, 20070301, true),
   ('510298', 'Afnemer Oldenzaal', 20070601, NULL, false, true, 20070601, true),
   ('510299', 'Afnemer Roermond', 20080901, NULL, false, true, 20080901, true),
   ('510301', 'Afnemer Heumen', 20070301, NULL, false, true, 20070301, true),
   ('510302', 'Afnemer Leidschendam-Voorburg', 20061201, NULL, false, true, 20061201, true),
   ('510303', 'Afnemer Doetinchem', 20061201, NULL, false, true, 20061201, true),
   ('510304', 'Afnemer Dronten', 20070601, NULL, false, true, 20070601, true),
   ('510305', 'Afnemer Beemster', 20071201, NULL, false, true, 20071201, true),
   ('510306', 'Afnemer Oud-Beijerland', 20070301, NULL, false, true, 20070301, true),
   ('510307', 'Afnemer Gemert-Bakel', 20090901, NULL, false, true, 20090901, true),
   ('510308', 'Afnemer Bergen (NH)', 20061201, NULL, false, true, 20061201, true),
   ('510309', 'Afnemer Zoeterwoude', 20090601, NULL, false, true, 20090601, true),
   ('510310', 'Afnemer Maasgouw', 20080901, NULL, false, true, 20080901, true),
   ('510311', 'Afnemer Sint Anthonis', 20081201, NULL, false, true, 20081201, true),
   ('510312', 'Afnemer ''s-Hertogenbosch', 20071201, NULL, false, true, 20071201, true),
   ('510313', 'Afnemer Almere', 20070601, NULL, false, true, 20070601, true),
   ('510314', 'Afnemer Aalsmeer', 20090601, NULL, false, true, 20090601, true),
   ('510315', 'Afnemer Kampen', 20071201, NULL, false, true, 20071201, true),
   ('510316', 'Afnemer Menterwolde', 20070601, NULL, false, true, 20070601, true),
   ('510317', 'Afnemer Delfzijl', 20061201, NULL, false, true, 20061201, true),
   ('510318', 'Afnemer Waalre', 20061201, NULL, false, true, 20061201, true),
   ('510319', 'Afnemer Nieuwegein', 20080301, NULL, false, true, 20080301, true),
   ('510321', 'Afnemer Rucphen', 20070601, NULL, false, true, 20070601, true),
   ('510322', 'Afnemer Rhenen', 20070901, NULL, false, true, 20070901, true),
   ('510324', 'Afnemer Appingedam', 20080301, NULL, false, true, 20080301, true),
   ('510325', 'Afnemer Bellingwedde', 20090501, NULL, false, true, 20090501, true),
   ('510326', 'Afnemer Meerlo-Wanssum', 20071201, 20100101, false, true, 20071201, true),
   ('510327', 'Afnemer Etten-Leur', 20071201, NULL, false, true, 20071201, true),
   ('510328', 'Afnemer Arcen en Velden', 20071201, 20100101, false, true, 20071201, true),
   ('510329', 'Afnemer Rijnwaarden', 20080301, NULL, false, true, 20080301, true),
   ('510330', 'Afnemer Lelystad', 20071201, NULL, false, true, 20071201, true),
   ('510331', 'Afnemer Gouda', 20081201, NULL, false, true, 20081201, true),
   ('510332', 'Afnemer Simpelveld', 20090901, NULL, false, true, 20090901, true),
   ('510333', 'Afnemer Enschede', 20080601, NULL, false, true, 20080601, true),
   ('510334', 'Afnemer Gorinchem', 20091201, NULL, false, true, 20091201, true),
   ('510335', 'Afnemer Gilze en Rijen', 20071201, NULL, false, true, 20071201, true),
   ('510336', 'Afnemer Schijndel', 20071201, 20170201, false, true, 20071201, true),
   ('510337', 'Afnemer Sint-Oedenrode', 20080301, 20170201, false, true, 20080301, true),
   ('510338', 'Afnemer Marum', 20080301, NULL, false, true, 20080301, true),
   ('510339', 'Afnemer Nijkerk', 20071201, NULL, false, true, 20071201, true),
   ('510341', 'Afnemer Drechterland', 20080301, NULL, false, true, 20080301, true),
   ('510342', 'Afnemer Waterland', 20080301, NULL, false, true, 20080301, true),
   ('510343', 'Afnemer Schouwen-Duiveland', 20080301, NULL, false, true, 20080301, true),
   ('510344', 'Afnemer Sittard-Geleen', 20080301, NULL, false, true, null, false),
   ('510345', 'Afnemer Teylingen', 20080301, NULL, false, true, 20080301, true),
   ('510346', 'Afnemer Roerdalen', 20080301, NULL, false, true, 20080301, true),
   ('510347', 'Afnemer Alphen aan den Rijn', 20080601, NULL, false, true, 20080601, true),
   ('510348', 'Afnemer Arnhem', 20080901, NULL, false, true, 20080901, true),
   ('510349', 'Afnemer Capelle aan den IJssel', 20090601, NULL, false, true, 20090601, true),
   ('510350', 'Afnemer Hulst', 20080901, NULL, false, true, 20080901, true),
   ('510351', 'Afnemer Opsterland', 20080601, NULL, false, true, 20080601, true),
   ('510352', 'Afnemer Zoetermeer', 20081201, NULL, false, true, 20081201, true),
   ('510353', 'Afnemer Oss', 20080301, NULL, false, true, 20080301, true),
   ('510354', 'Afnemer Assen', 20080601, NULL, false, true, 20080601, true),
   ('510356', 'Afnemer Haren', 20080901, NULL, false, true, 20080901, true),
   ('510357', 'Afnemer Neerijnen', 20080601, NULL, false, true, 20080601, true),
   ('510358', 'Afnemer Schiedam', 20080601, NULL, false, true, 20080601, true),
   ('510359', 'Afnemer Veendam', 20080601, NULL, false, true, 20080601, true),
   ('510360', 'Afnemer Woudrichem', 20080601, NULL, false, true, 20080601, true),
   ('510361', 'Afnemer Wormerland', 20080901, NULL, false, true, 20080901, true),
   ('510362', 'Afnemer Wijchen', 20080601, NULL, false, true, 20080601, true),
   ('510363', 'Afnemer Kapelle', 20080601, NULL, false, true, 20080601, true),
   ('510364', 'Afnemer Loon op Zand', 20090301, NULL, false, true, 20090301, true),
   ('510365', 'Afnemer Son en Breugel', 20080601, NULL, false, true, 20080601, true),
   ('510366', 'Afnemer Heerhugowaard', 20080601, NULL, false, true, 20080601, true),
   ('510367', 'Afnemer Oirschot', 20080601, NULL, false, true, 20080601, true),
   ('510368', 'Afnemer Uitgeest', 20080601, NULL, false, true, 20080601, true),
   ('510369', 'Afnemer Middelburg', 20081201, NULL, false, true, 20081201, true),
   ('510370', 'Afnemer Aa en Hunze', 20080901, NULL, false, true, 20080901, true),
   ('510371', 'Afnemer Beesel', 20090601, NULL, false, true, 20090601, true),
   ('510372', 'Afnemer Lopik', 20081201, NULL, false, true, 20081201, true),
   ('510373', 'Afnemer Westervoort', 20080901, NULL, false, true, 20080901, true),
   ('510374', 'Afnemer Haaksbergen', 20090601, NULL, false, true, 20090601, true),
   ('510375', 'Afnemer Haaren', 20081201, NULL, false, true, 20081201, true),
   ('510376', 'Afnemer Zeevang', 20080901, 20160201, false, true, 20080901, true),
   ('510377', 'Afnemer Valkenburg aan de Geul', 20081201, NULL, false, true, 20081201, true),
   ('510378', 'Afnemer Korendijk', 20080901, NULL, false, true, 20080901, true),
   ('510379', 'Afnemer Littenseradiel', 20081201, NULL, false, true, 20081201, true),
   ('510380', 'Afnemer Wûnseradiel', 20081201, 20110101, false, true, 20081201, true),
   ('510381', 'Afnemer Gaasterlân-Sleat', 20081201, 20140201, false, true, 20081201, true),
   ('510382', 'Afnemer Nijefurd', 20081201, 20110101, false, true, 20081201, true),
   ('510383', 'Afnemer Eersel', 20080901, NULL, false, true, 20080901, true),
   ('510384', 'Afnemer Bolsward', 20081201, 20110101, false, true, 20081201, true),
   ('510385', 'Afnemer Nijmegen', 20081201, NULL, false, true, 20081201, true),
   ('510386', 'Afnemer Lemsterland', 20081201, 20140201, false, true, 20081201, true),
   ('510387', 'Afnemer Zeist', 20100301, NULL, false, true, 20100301, true),
   ('510388', 'Afnemer Asten', 20081201, NULL, false, true, 20081201, true),
   ('510389', 'Afnemer Vlist', 20090601, 20150201, false, true, 20090601, true),
   ('510390', 'Afnemer Heerde', 20090901, NULL, false, true, 20090901, true),
   ('510391', 'Afnemer Raalte', 20090901, NULL, false, true, 20090901, true),
   ('510392', 'Afnemer Zwijndrecht', 20090301, NULL, false, true, 20090301, true),
   ('510393', 'Afnemer Wervershoof', 20081201, 20110101, false, true, null, false),
   ('510394', 'Afnemer Ouderkerk', 20090601, 20150201, false, true, 20090601, true),
   ('510395', 'Afnemer Halderberge', 20090601, NULL, false, true, 20090601, true),
   ('510396', 'Afnemer Mook en Middelaar', 20090901, NULL, false, true, 20090901, true),
   ('510397', 'Afnemer Tiel', 20090601, NULL, false, true, 20090601, true),
   ('510398', 'Afnemer Bunnik', 20090601, NULL, false, true, 20090601, true),
   ('510399', 'Afnemer Kollumerland en Nieuwkruisland', 20090301, NULL, false, true, 20090301, true),
   ('510401', 'Afnemer Dongeradeel', 20090301, NULL, false, true, 20090301, true),
   ('510402', 'Afnemer Renkum', 20090901, NULL, false, true, 20090901, true),
   ('510403', 'Afnemer het Bildt', 20091201, NULL, false, true, 20091201, true),
   ('510404', 'Afnemer Graft-De Rijp', 20081201, 20150101, false, true, null, false),
   ('510405', 'Afnemer Leek', 20081101, NULL, false, true, 20081101, true),
   ('510406', 'Afnemer Landerd', 20090601, NULL, false, true, 20090601, true),
   ('510407', 'Afnemer Millingen aan de Rijn', 20090301, 20150201, false, true, 20090301, true),
   ('510408', 'Afnemer Ridderkerk', 20090601, NULL, false, true, 20090601, true),
   ('510409', 'Afnemer Bedum', 20090901, NULL, false, true, null, false),
   ('510410', 'Afnemer Montfoort', 20090601, NULL, false, true, 20090601, true),
   ('510411', 'Afnemer Waddinxveen', 20090301, NULL, false, true, 20090301, true),
   ('510412', 'Afnemer Strijen', 20090601, NULL, false, true, 20090601, true),
   ('510413', 'Afnemer Hollands Kroon', 20090901, NULL, false, true, 20090901, true),
   ('510414', 'Afnemer Wijk bij Duurstede', 20090601, NULL, false, true, 20090601, true),
   ('510415', 'Afnemer Steenbergen', 20090301, NULL, false, true, 20090301, true),
   ('510416', 'Afnemer Terschelling', 20090301, NULL, false, true, 20090301, true),
   ('510417', 'Afnemer Wieringen', 20090601, 20120101, false, true, 20090601, true),
   ('510418', 'Afnemer Stadskanaal', 20090601, NULL, false, true, 20090601, true),
   ('510419', 'Afnemer Maassluis', 20090601, NULL, false, true, 20090601, true),
   ('510420', 'Afnemer Olst-Wijhe', 20090601, NULL, false, true, 20090601, true),
   ('510421', 'Afnemer Den Helder', 20090901, NULL, false, true, 20090901, true),
   ('510422', 'Afnemer Ten Boer', 20090601, NULL, false, true, 20090601, true),
   ('510423', 'Afnemer Texel', 20090601, NULL, false, true, 20090601, true),
   ('510424', 'Afnemer Winsum', 20090901, NULL, false, true, 20090901, true),
   ('510425', 'Afnemer Zundert', 20090601, NULL, false, true, 20090601, true),
   ('510426', 'Afnemer Brummen', 20090901, NULL, false, true, 20090901, true),
   ('510427', 'Afnemer De Marne', 20090901, NULL, false, true, null, false),
   ('510428', 'Afnemer Loppersum', 20090601, NULL, false, true, 20090601, true),
   ('510429', 'Afnemer Roosendaal', 20090601, NULL, false, true, 20090601, true),
   ('510430', 'Afnemer Leusden', 20090601, NULL, false, true, 20090601, true),
   ('510431', 'Afnemer Onderbanken', 20090901, NULL, false, true, 20090901, true),
   ('510432', 'Afnemer Ouder-Amstel', 20090301, NULL, false, true, 20090301, true),
   ('510433', 'Afnemer Oudewater', 20090601, NULL, false, true, 20090601, true),
   ('510434', 'Afnemer Bergeijk', 20090401, NULL, false, true, 20090401, true),
   ('510435', 'Afnemer Stein', 20090901, NULL, false, true, 20090901, true),
   ('510436', 'Afnemer Renswoude', 20090901, NULL, false, true, 20090901, true),
   ('510437', 'Afnemer Bernheze', 20090901, NULL, false, true, 20090901, true),
   ('510438', 'Afnemer West Maas en Waal', 20090901, NULL, false, true, 20090901, true),
   ('510439', 'Afnemer Vlieland', 20090901, NULL, false, true, 20090901, true),
   ('510440', 'Afnemer Boekel', 20090401, NULL, false, true, 20090401, true),
   ('510441', 'Afnemer Heerenveen', 20090501, NULL, false, true, 20090501, true),
   ('510442', 'Afnemer Reiderland', 20090401, 20100101, false, true, null, false),
   ('510443', 'Afnemer Alblasserdam', 20091201, NULL, false, true, 20091201, true),
   ('510444', 'Afnemer Wieringermeer', 20090901, 20120101, false, true, 20090901, true),
   ('510445', 'Afnemer Scherpenzeel', 20150101, NULL, false, true, 20150101, true),
   ('510446', 'Afnemer Rozendaal', 20090601, NULL, false, true, 20090601, true),
   ('510447', 'Afnemer Noord-Beveland', 20091201, NULL, false, true, 20091201, true),
   ('510448', 'Afnemer Houten', 20091201, NULL, false, true, 20091201, true),
   ('510449', 'Afnemer Menameradiel', 20091201, NULL, false, true, 20091201, true),
   ('510450', 'Afnemer Woudenberg', 20150101, NULL, false, true, 20150101, true),
   ('510451', 'Afnemer Ferwerderadiel', 20091201, NULL, false, true, 20091201, true),
   ('510452', 'Afnemer Molenwaard', 20130101, NULL, false, true, 20130101, true),
   ('510453', 'Afnemer Werkendam', 20091201, NULL, false, true, 20091201, true),
   ('510454', 'Afnemer Harlingen', 20091201, NULL, false, true, 20091201, true),
   ('510455', 'Afnemer IJsselstein', 20091201, NULL, false, true, 20091201, true),
   ('510456', 'Afnemer Stede Broec', 20100301, NULL, false, true, 20100301, true),
   ('510457', 'Afnemer Landsmeer', 20091001, NULL, false, true, 20091001, true),
   ('510458', 'Afnemer Schiermonnikoog', 20091001, NULL, false, true, 20091001, true),
   ('510460', 'Afnemer Leeuwarderadeel', 20091201, NULL, false, true, 20091201, true),
   ('510461', 'Afnemer Wageningen', 20091201, NULL, false, true, 20091201, true),
   ('510462', 'Afnemer Culemborg', 20100601, NULL, false, true, 20100601, true),
   ('510463', 'Afnemer Baarle-Nassau', 20100301, NULL, false, true, 20100301, true),
   ('510464', 'Afnemer Andijk', 20100101, 20110101, false, true, null, false),
   ('510465', 'Afnemer Boskoop', 20100601, 20140201, false, true, 20100601, true),
   ('510500', 'Afnemer Venlo (selectie)', 20110201, 20120901, false, true, null, false),
   ('510501', 'Afnemer Nieuwkoop (selectie)', 20120301, 20120901, false, true, null, false),
   ('510550', 'Afnemer Bergambacht', 20091201, 20150201, false, true, 20091201, true),
   ('510551', 'Afnemer Tytsjerksteradiel', 20091201, NULL, false, true, 20091201, true),
   ('510552', 'Afnemer Mill en Sint Hubert', 20140501, NULL, false, true, 20140501, true),
   ('510553', 'Afnemer Schermer', 20090801, 20150101, false, true, null, false),
   ('510554', 'Afnemer Haarlemmerliede en Spaarnwoude', 20090801, NULL, false, true, null, false),
   ('520014', 'Afnemer Groningen (pilot)', 20120501, 20120717, false, true, 20120501, true),
   ('520092', 'Afnemer Noordenveld (pilot)', 20120601, 20120717, false, true, 20120601, true),
   ('600001', 'Provinciale Entadministratie Limburg', 19921101, 20100601, false, true, 19921101, true),
   ('600101', 'Provinciale Entadministratie Noord-Brabant', 19921101, 20100601, false, true, 19921101, true),
   ('600201', 'Provinciale Entadministratie Overijssel/Flevoland', 19921101, 20100601, false, true, 19921101, true),
   ('600301', 'Provinciale Entadministratie Drenthe', 19930315, 20030601, false, true, 19930315, true),
   ('600401', 'Provinciale Entadministratie Noord-Nederland', 19930701, 20100601, false, true, 19930701, true),
   ('600501', 'Stichting Prepas', 19930701, 20100601, false, true, 19930701, true),
   ('600601', 'Provinciale Entadministratie Friesland-Groningen (2)', 19930701, 20030601, false, true, 19930701, true),
   ('600701', 'Provinciale Entadministratie Utrecht & Noord-Holland', 19930701, 20100601, false, true, 19930701, true),
   ('600801', 'Provinciale Entadministratie Utrecht & Noord-Holland (2)', 19930701, 20040301, false, true, 19930701, true),
   ('600901', 'Provinciale Entadministratie Zeeland', 19940701, 20100601, false, true, 19940701, true),
   ('601001', 'Provinciale Entadministratie Zuid-Holland', 19940901, 20100601, false, true, 19940901, true),
   ('601101', 'Zorgverzekeraar DSW', 19950801, NULL, false, true, 19950801, true),
   ('601102', 'Zorgkantoor DSW', 20140801, NULL, false, true, 20140801, true),
   ('601203', 'RIVM/Vaccinatie influenza A', 20091109, 20100301, false, true, null, false),
   ('601204', 'RIVM/Entadministraties (selectie)', 20100101, 20120901, false, true, null, false),
   ('601301', 'Bevolkingsonderzoek Midden-West (BOB 2)', 19960201, 20130701, false, true, 19960201, true),
   ('601401', 'Bevolkingsonderzoek Oost (BOB 2)', 19960201, 20130701, false, true, 19960201, true),
   ('601402', 'Bevolkingsonderzoek Oost (BOB 2) (selectie)', 20120401, 20120901, false, true, null, false),
   ('601501', 'Bevolkingsonderzoek Zuid-West (BOB 1)', 19960201, 20130701, false, true, 19960201, true),
   ('601502', 'Bevolkingsonderzoek Zuid-West (BOB 1) (selectie)', 20120401, 20120901, false, true, null, false),
   ('601601', 'Bevolkingsonderzoek Zuid (BOB 1)', 19960201, 20130701, false, true, 19960201, true),
   ('601602', 'Bevolkingsonderzoek Zuid (BMHK 1)', 19960201, 20130701, false, true, 19960201, true),
   ('601701', 'Bevolkingsonderzoek Oost (BOB 1)', 19960201, 20130701, false, true, 19960201, true),
   ('601702', 'Bevolkingsonderzoek Oost (BOB 1) (selectie)', 20120401, 20120901, false, true, null, false),
   ('601801', 'Bevolkingsonderzoek Noord (BOB)', 19960501, 20130701, false, true, 19960501, true),
   ('601802', 'Bevolkingsonderzoek Borstkanker Noord-Nederland (2)', 19990901, 19991201, false, true, 19990901, true),
   ('601803', 'Bevolkingsonderzoek Borstkanker Noord-Nederland (3)', 19990901, 19991201, false, true, 19990901, true),
   ('601901', 'Bevolkingsonderzoek Zuid (BOB 2)', 19960501, 20130701, false, true, 19960501, true),
   ('602001', 'Bevolkingsonderzoek Zuid-West (BOB 2)', 19960501, 20130701, false, true, 19960501, true),
   ('602002', 'Bevolkingsonderzoek Zuid-West (BOB 2) (selectie)', 20120401, 20120901, false, true, null, false),
   ('602101', 'Bevolkingsonderzoek Midden-West (BOB 1)', 19960501, 20130701, false, true, 19960501, true),
   ('602102', 'Bevolkingsonderzoek Midden-West (BMHK 1)', 20031201, 20130701, false, true, 20031201, true),
   ('602202', 'MvVWS/Dienst Donorregister (2)', 19980201, 19981101, false, true, 19980201, true),
   ('602203', 'MvVWS/Donorregister (nieuw ingezetenen)', 20081201, NULL, false, true, null, false),
   ('602204', 'MvVWS/Donorregister (selectie)', 20081201, NULL, false, true, null, false),
   ('602301', 'Univé Zorg', 19980201, 20081201, false, true, 19980201, true),
   ('602401', 'PWZ Dienstverlening N.V.', 19980501, 20031201, false, true, 19980501, true),
   ('602501', 'Eno Zorgverzekeraar N.V.', 19980501, NULL, false, true, 19980501, true),
   ('602502', 'Salland Zorgkantoor', 20140801, NULL, false, true, 20140801, true),
   ('602601', '''t Lange Land Ziekenhuis', 20040601, NULL, false, true, null, false),
   ('602701', 'Trias Zorgverzekeraar', 19990301, 20100301, false, true, 19990301, true),
   ('602801', 'ANOZ Zorgverzekeringen UA', 19981101, 20040301, false, true, 19981101, true),
   ('602901', 'FBTO Zorgverzekeraar', 20140106, NULL, false, true, 20140106, true),
   ('603001', 'De Friesland Zorgverzekeraar', 19990901, NULL, false, true, 19990901, true),
   ('603002', 'Zorgkantoor Friesland', 20140201, NULL, false, true, 20140201, true),
   ('603101', 'CZ Groep Zorgverzekeringen', 19990901, NULL, false, true, 19990901, true),
   ('603102', 'CZ Zorgkantoor', 20140501, NULL, false, true, 20140501, true),
   ('603201', 'Zorgverzekeraar VGZ', 19990901, NULL, false, true, 19990901, true),
   ('603301', 'ZAO Zorgverzekeringen', 19991201, 20040301, false, true, 19991201, true),
   ('603401', 'OZ Zorgverzekeringen', 19991201, 20071201, false, true, 19991201, true),
   ('603501', 'Geové Zorgverzekeraar', 19991201, 20060301, false, true, 19991201, true),
   ('603601', 'Agis Zorgverzekeringen', 20000301, 20160101, false, true, 20000301, true),
   ('603602', 'Agis Zorgkantoor', 20131201, 20140101, false, true, 20131201, true),
   ('603702', 'CAK Bijzondere Zorgkosten (selectie)', 20120701, 20120901, false, true, null, false),
   ('603703', 'CAK/Ouderbijdrage Jeugdwet', 20150101, NULL, false, true, 20150101, true),
   ('603901', 'Zorgverzekeraar Zorg en Zekerheid', 20140901, NULL, false, true, 20140901, true),
   ('603902', 'Zorg en Zekerheid particulier', 20001201, 20060601, false, true, 20001201, true),
   ('603903', 'Zorgkantoor Zorg en Zekerheid', 20140801, NULL, false, true, 20140801, true),
   ('604001', 'Tergooiziekenhuizen', 20030601, NULL, false, true, null, false),
   ('604101', 'Menzis Zorgverzekeraar', 20010901, NULL, false, true, 20010901, true),
   ('604201', 'Azivo Ziekenfonds', 20020601, 20120301, false, true, 20020601, true),
   ('604301', 'Delta Lloyd en OHRA', 20020901, 20100901, false, true, 20020901, true),
   ('604401', 'ONVZ Zorgverzekeraar', 20020901, NULL, false, true, 20020901, true),
   ('604601', 'Bevolkingsonderzoek Zuid-West (BMHK 1)', 20030901, 20130701, false, true, 20030901, true),
   ('604801', 'Maasstad ziekenhuis', 20030901, NULL, false, true, null, false),
   ('604901', 'Stichting Arkin', 20040901, NULL, false, true, null, false),
   ('605101', 'Vektis CV', 20051201, NULL, false, true, 20051201, true),
   ('605201', 'Bevolkingsonderzoek Oost (BMHK 2)', 20070301, 20130701, false, true, 20070301, true),
   ('605202', 'Bevolkingsonderzoek Oost (BMHK 2) (selectie)', 20120401, 20120901, false, true, null, false),
   ('605301', 'Integraal Kankercentrum Nederland (IKNL)', 20140301, NULL, false, true, null, false),
   ('605401', 'Rivierduinen Servicebedrijf', 20061115, NULL, false, true, null, false),
   ('605501', 'Vlietland Ziekenhuis', 20061115, NULL, false, true, null, false),
   ('605601', 'GGZ Buitenamstel', 20060601, 20100901, false, true, 20060601, true),
   ('605701', 'Ziekenhuis Gelderse Vallei', 20120901, NULL, false, true, null, false),
   ('605801', 'Adhesie GGZ Midden-Overijssel', 20061201, 20091201, false, true, null, false),
   ('605901', 'Bevolkingsonderzoek Midden-West (BMHK 2)', 20071201, 20130701, false, true, 20071201, true),
   ('606001', 'Laurentius Ziekenhuis', 20030601, NULL, false, true, null, false),
   ('606201', 'Amphia Ziekenhuis', 20050901, NULL, false, true, 20050901, true),
   ('606401', 'Meander Medisch Centrum', 20030601, NULL, false, true, 20030601, true),
   ('606501', 'Universitair Medisch Centrum St. Radboud', 20030901, NULL, false, true, null, false),
   ('606502', 'Universitair Medisch Centrum St. Radboud (2)', 20050301, 20090601, false, true, 20050301, true),
   ('606601', 'Groene Hart Ziekenhuis', 20040301, NULL, false, true, 20040301, true),
   ('606701', 'Stichting BovenIJ Ziekenhuis', 20030901, NULL, false, true, null, false),
   ('606901', 'Deventer Ziekenhuis', 20040601, NULL, false, true, null, false),
   ('607001', 'Academisch Medisch Centrum', 20030901, NULL, false, true, 20030901, true),
   ('607101', 'Universitair Medisch Centrum Utrecht', 20030901, NULL, false, true, null, false),
   ('607102', 'UMC Utrecht/ALS onderzoek', 20130201, NULL, false, true, null, false),
   ('607103', 'Universiteit Utrecht/Arbeid, Milieu en Gezondheid Onderzoek (AMIGO)', 20130701, NULL, false, true, null, false),
   ('607201', 'Universitair Medisch Centrum Groningen', 20030901, NULL, false, true, 20030901, true),
   ('607202', 'Universitair Medisch Centrum Groningen/LifeLines', 20130701, NULL, false, true, 20130701, true),
   ('607401', 'Academisch Ziekenhuis Maastricht', 20030901, NULL, false, true, null, false),
   ('607402', 'Universiteit Maastricht/NLCS', 20050301, NULL, false, true, null, false),
   ('607403', 'Universiteit Maastricht/studie Arbeidsdeelname en gezondheid door de jaren heen', 20121201, NULL, false, true, null, false),
   ('607404', 'Universiteit Maastricht/KOALA Birth Cohort Study', 20121201, NULL, false, true, null, false),
   ('607405', 'Universiteit Maastricht/studie Influenza vaccination', 20121201, 20160101, false, true, null, false),
   ('607406', 'Universiteit Maastricht/IBD-ZL (Inflammatory Bowel Disease Zuid-Limburg) project', 20131201, NULL, false, true, null, false),
   ('607407', 'Universiteit Maastricht/CAPHRI (Public Health and Primary Care)-onderzoek', 20150101, NULL, false, true, null, false),
   ('607601', 'Erasmus MC', 20040301, NULL, false, true, 20040301, true),
   ('607701', 'Diakonessenhuis Utrecht', 20040601, NULL, false, true, null, false),
   ('607801', 'VU medisch centrum', 20030901, NULL, false, true, 20030901, true),
   ('607802', 'VU medisch centrum - LASA', 20110801, NULL, false, true, null, false),
   ('607803', 'VU medisch centrum - Nederlands Tweelingenregister', 20130901, NULL, false, true, null, false),
   ('608001', 'Slotervaartziekenhuis', 20050601, NULL, false, true, null, false),
   ('608401', 'Maastro clinic', 20070301, NULL, false, true, null, false),
   ('608501', 'Bevolkingsonderzoek Zuid-West (BMHK 2)', 20071201, 20130701, false, true, 20071201, true),
   ('608601', 'Ikazia Ziekenhuis', 20080601, NULL, false, true, null, false),
   ('608701', 'Proefbevolkingsonderzoek Darmkanker Erasmus MC/AMC Amsterdam', 20140401, 20170301, false, true, 20140401, true),
   ('608801', 'Bevolkingsonderzoek Zuid (BMHK 2)', 20080901, 20130701, false, true, 20080901, true),
   ('608901', 'Bureau Jeugdzorg', 20090401, 20150801, false, true, 20090401, true),
   ('608902', 'Bureau Jeugdzorg/AMK', 20090401, 20150501, false, true, null, false),
   ('609001', 'Antoni van Leeuwenhoek Ziekenhuis', 20091201, NULL, false, true, null, false),
   ('609101', 'Flevoziekenhuis', 20100401, NULL, false, true, null, false),
   ('609301', 'Isala klinieken', 20110601, NULL, false, true, null, false),
   ('609401', 'Spaarne Ziekenhuis', 20120601, NULL, false, true, null, false),
   ('609501', 'Catharina Ziekenhuis', 20120701, NULL, false, true, null, false),
   ('609601', 'Martini Ziekenhuis', 20131101, NULL, false, true, null, false),
   ('609701', 'Canisius-Wilhelmina Ziekenhuis', 20140201, NULL, false, true, 20140201, true),
   ('609901', 'MvVWS/Verwijsindex Risicojongeren', 20080301, NULL, false, true, 20080301, true),
   ('610101', 'GGD Nieuwe Waterweg Noord', 19961101, 20080301, false, true, 19961101, true),
   ('610102', 'GGD Nieuwe Waterweg Noord (EPI)', 20000601, 20090601, false, true, 20000601, true),
   ('610103', 'GGD Nieuwe Waterweg Noord (JGZ/overig)', 20071201, 20090601, false, true, 20071201, true),
   ('610201', 'Bevolkingsonderzoek Noord (BMHK 2)', 19961101, 20130701, false, true, 19961101, true),
   ('610202', 'GGD Fryslân (JGZ/overig)', 20071201, NULL, false, true, 20071201, true),
   ('610301', 'GGD Regio Achterhoek', 19970501, 20050601, false, true, 19970501, true),
   ('610302', 'GGD Noord- en Oost-Gelderland (EPI)', 20130101, NULL, false, true, null, false),
   ('610401', 'RDOG GGD Hollands Midden (JGZ/overig)', 20141201, NULL, false, true, 20141201, true),
   ('610402', 'RDOG GGD Hollands Midden (OGGZ)', 20141201, NULL, false, true, null, false),
   ('610501', 'GGD Regio Nijmegen', 19970801, NULL, false, true, 19970801, true),
   ('610502', 'GGD Regio Nijmegen (EPI)', 20051201, NULL, false, true, null, false),
   ('610601', 'GGD Zuid-Limburg (JGZ/overig)', 19970501, NULL, false, true, 19970501, true),
   ('610602', 'GGD Zuid-Limburg (OGGZ/Kinderopvang)', 20110601, NULL, false, true, null, false),
   ('610701', 'GGD Zeeland (BMHK)', 19970501, 20090301, false, true, 19970501, true),
   ('610702', 'GGD Zeeland (JGZ/overig)', 20071201, NULL, false, true, 20071201, true),
   ('610703', 'GGD Zeeland (EPI)', 20090601, NULL, false, true, null, false),
   ('610704', 'GGD Zeeland (OGGZ/Kinderopvang)', 20111201, 20170201, false, true, null, false),
   ('610801', 'GGD Zuidhollandse Eilanden (BMHK)', 19970501, 20090301, false, true, 19970501, true),
   ('610802', 'GGD Zuidhollandse Eilanden (EPI)', 20001201, 20100301, false, true, 20001201, true),
   ('610803', 'GGD Zuidhollandse Eilanden (JGZ/overig)', 20071201, 20100601, false, true, 20071201, true),
   ('610901', 'GGD Zuid-Holland Zuid (BMHK)', 19970501, 20090301, false, true, 19970501, true),
   ('611001', 'GGD Midden-Nederland (3)', 19970501, 20020601, false, true, 19970501, true),
   ('611002', 'GGD Midden-Nederland (EPI)', 20001201, NULL, false, true, null, false),
   ('611101', 'GGD IJsselland (JGZ/overig)', 19970501, NULL, false, true, 19970501, true),
   ('611102', 'GGD IJsselland (EPI)', 20020301, NULL, false, true, null, false),
   ('611201', 'GGD Kop van Noord-Holland', 19970201, 20080901, false, true, 19970201, true),
   ('611202', 'GGD Kop van Noord-Holland (EPI)', 20030601, 20080601, false, true, 20030601, true),
   ('611301', 'GGD Zuid-Holland West', 19970801, NULL, false, true, 19970801, true),
   ('611302', 'GGD West-Holland (2)', 19970801, 20040301, false, true, 19970801, true),
   ('611303', 'GGD Zuid-Holland West (EPI)', 20000601, NULL, false, true, null, false),
   ('611401', 'GGD West-Brabant (BMHK) (1)', 19970801, 20081201, false, true, 19970801, true),
   ('611402', 'GGD West-Brabant (EPI)', 20020301, NULL, false, true, null, false),
   ('611403', 'GGD West-Brabant (JGZ/overig)', 20071201, NULL, false, true, 20071201, true),
   ('611501', 'GGD Midden-Nederland (JGZ/overig)', 19970801, NULL, false, true, 19970801, true),
   ('611502', 'GGD Midden-Nederland (2)', 19970801, 20040601, false, true, 19970801, true),
   ('611504', 'GGD Regio Utrecht (OGGZ/Kinderopvang)', 20140601, NULL, false, true, null, false),
   ('611601', 'Hulpverleningsdienst Gelderland-Midden', 19970801, NULL, false, true, 19970801, true),
   ('611602', 'Hulpverleningsdienst Gelderland-Midden (EPI)', 20060601, NULL, false, true, null, false),
   ('611701', 'GGD Delfland (1)', 19970801, 20060601, false, true, 19970801, true),
   ('611702', 'GGD Delfland (2)', 19970801, 20040301, false, true, 19970801, true),
   ('611703', 'GGD Delfland (EPI)', 20000601, 20051201, false, true, 20000601, true),
   ('611801', 'GGD Gewest Eemland', 19970801, 20091201, false, true, 19970801, true),
   ('611802', 'GGD Gewest Eemland (EPI)', 20001201, 20090901, false, true, 20001201, true),
   ('611901', 'GGD Flevoland', 19970801, 20080301, false, true, 19970801, true),
   ('611902', 'GGD Flevoland (JGZ/overig)', 20071201, NULL, false, true, 20071201, true),
   ('611903', 'GGD Flevoland (OGGZ/Kinderopvang)', 20130501, NULL, false, true, null, false),
   ('612001', 'Bevolkingsonderzoek Noord (BMHK 3)', 19980201, 20130701, false, true, 19980201, true),
   ('612002', 'GGD Groningen (JGZ/overig)', 20071201, NULL, false, true, 20071201, true),
   ('612101', 'GGD Midden-Limburg', 19970801, 20030301, false, true, 19970801, true),
   ('612201', 'GGD Noord- en Oost-Gelderland (JGZ/overig)', 20130101, NULL, false, true, 20130101, true),
   ('612301', 'GGD Hollands Noorden (JGZ/overig)', 20080601, NULL, false, true, 20080601, true),
   ('612302', 'GGD Hollands Noorden (OGGZ/Kinderopvang)', 20111101, NULL, false, true, null, false),
   ('612501', 'GGD Noord- en Midden-Limburg (BMHK)', 19971101, 20081201, false, true, 19971101, true),
   ('612502', 'GGD Limburg-Noord (JGZ/overig)', 20071201, NULL, false, true, 20071201, true),
   ('612601', 'GGD Zuid-Limburg (2)', 19971101, 20100601, false, true, 19971101, true),
   ('612701', 'GGD Rotterdam en omstreken', 19971101, 20080301, false, true, 19971101, true),
   ('612702', 'GGD Rotterdam-Rijnmond (EPI)', 20000601, NULL, false, true, null, false),
   ('612703', 'GGD Rotterdam-Rijnmond (JGZ/overig)', 20130101, NULL, false, true, 20130101, true),
   ('612704', 'GGD Rotterdam-Rijnmond (OGGZ/Kinderopvang)', 20120901, NULL, false, true, null, false),
   ('612801', 'GGD Zuid-Limburg (3)', 19971101, 20100601, false, true, 19971101, true),
   ('612901', 'RDOG GGD Hollands Midden (JGZ/overig)', 19971101, 20150101, false, true, 19971101, true),
   ('612902', 'RDOG GGD Hollands Midden (OGGZ)', 20120101, 20141201, false, true, null, false),
   ('613001', 'GGD Amsterdam/Amstelland (JGZ/overig)', 20070901, NULL, false, true, 20070901, true),
   ('613002', 'GGD Amsterdam/Amstelland (EPI)', 20110101, NULL, false, true, null, false),
   ('613003', 'GGD Amsterdam/Amstelland (OGGZ/Kinderopvang)', 20110601, NULL, false, true, null, false),
   ('613101', 'Bevolkingsonderzoek Oost (BMHK 3)', 19971101, 20130701, false, true, 19971101, true),
   ('613102', 'Bevolkingsonderzoek Oost (BMHK 3) (selectie)', 20120401, 20120901, false, true, null, false),
   ('613201', 'GGD Amstelland (JGZ/overig)', 19971101, 20120301, false, true, 19971101, true),
   ('613202', 'GGD Amstelland (EPI)', 20020301, 20120101, false, true, null, false),
   ('613203', 'GGD Amstelland (OGGZ/Kinderopvang)', 20110601, 20110901, false, true, null, false),
   ('613301', 'GGD Gooi en Vechtstreek', 19971101, 20080301, false, true, 19971101, true),
   ('613302', 'GGD Gooi en Vechtstreek (EPI)', 20001201, 20150601, false, true, null, false),
   ('613303', 'GGD Gooi en Vechtstreek (JGZ/overig)', 20071201, NULL, false, true, 20071201, true),
   ('613304', 'GGD Gooi en Vechtstreek (OGGZ/Kinderopvang)', 20110601, NULL, false, true, null, false),
   ('613401', 'GGD West-Veluwe/Vallei', 19980201, 20030601, false, true, 19980201, true),
   ('613501', 'Bevolkingsonderzoek Oost (BMHK 1)', 19971101, 20130701, false, true, 19971101, true),
   ('613502', 'GGD Regio Twente (EPI)', 20000601, NULL, false, true, null, false),
   ('613503', 'GGD Regio Twente (JGZ/overig)', 20071201, NULL, false, true, 20071201, true),
   ('613504', 'GGD Twente (OGGZ/Kinderopvang)', 20120501, NULL, false, true, null, false),
   ('613505', 'Bevolkingsonderzoek Oost (BMHK 1) (selectie)', 20120401, 20120901, false, true, null, false),
   ('613601', 'GGD Hart voor Brabant (BMHK) (1)', 19980101, 20081201, false, true, 19980101, true),
   ('613602', 'GGD Hart voor Brabant (EPI)', 20010901, NULL, false, true, null, false),
   ('613603', 'GGD Hart voor Brabant (JGZ/overig)', 20071201, NULL, false, true, 20071201, true),
   ('613701', 'GGD Hart voor Brabant (BMHK) (2)', 19971101, 20081201, false, true, 19971101, true),
   ('613801', 'GGD West-Brabant (BMHK) (2)', 19971101, 20081201, false, true, 19971101, true),
   ('613901', 'GGD Hart voor Brabant (BMHK) (3)', 19971101, 20081201, false, true, 19971101, true),
   ('614001', 'GGD Zuid-Holland Noord (2)', 19980201, 20021201, false, true, 19980201, true),
   ('614101', 'GGD Kennemerland', 19980201, 20080301, false, true, 19980201, true),
   ('614102', 'GGD Kennemerland (JGZ/overig)', 20071201, NULL, false, true, 20071201, true),
   ('614103', 'GGD Kennemerland (OGGZ/Kinderopvang)', 20110801, NULL, false, true, null, false),
   ('614201', 'GGD Zuid-Kennemerland', 19980201, 20020901, false, true, 19980201, true),
   ('614202', 'GGD Kennemerland (EPI)', 20001201, NULL, false, true, null, false),
   ('614301', 'GGD Zaanstreek-Waterland', 19980201, NULL, false, true, 19980201, true),
   ('614302', 'GGD Zaanstreek-Waterland (EPI)', 20001201, NULL, false, true, null, false),
   ('614401', 'GGD Westfriesland (1)', 19980201, 20080901, false, true, 19980201, true),
   ('614402', 'GGD Westfriesland (2)', 20010901, 20080301, false, true, 20010901, true),
   ('614403', 'GGD Hollands Noorden (EPI)', 20010901, NULL, false, true, null, false),
   ('614501', 'GGD Noord-Kennemerland', 19980201, 20080901, false, true, 19980201, true),
   ('614601', 'GGD Utrecht (JGZ/overig)', 20091201, NULL, false, true, 20091201, true),
   ('614602', 'Gemeente Utrecht Volksgezondheid (OGGZ/Kinderopvang)', 20141101, NULL, false, true, null, false),
   ('614701', 'GGD Den Haag (JGZ/overig)', 20110901, NULL, false, true, 20110901, true),
   ('614702', 'GGD Den Haag (OGGZ)', 20110901, NULL, false, true, null, false),
   ('614801', 'GGD Brabant-Zuidoost (BMHK)', 19980201, 20081201, false, true, 19980201, true),
   ('614802', 'GGD Brabant-Zuidoost (JGZ/overig)', 20071201, NULL, false, true, 20071201, true),
   ('614803', 'GGD Brabant-Zuidoost (EPI)', 20110401, NULL, false, true, null, false),
   ('615001', 'GGD Drenthe (3)', 19980201, 20040601, false, true, 19980201, true),
   ('615101', 'GGD Drenthe (1)', 19980201, 20040901, false, true, 19980201, true),
   ('615102', 'GGD Drenthe', 20060601, NULL, false, true, 20060601, true),
   ('615201', 'Bevolkingsonderzoek Noord (BMHK 1)', 19980501, 20130701, false, true, 19980501, true),
   ('615202', 'GGD Noord- en Midden-Drenthe (EPI)', 20001201, 20011201, false, true, 20001201, true),
   ('615301', 'GGD Rivierenland (1)', 19980801, NULL, false, true, 19980801, true),
   ('615302', 'GGD Rivierenland (2)', 19980801, 20070601, false, true, 19980801, true),
   ('615303', 'GGD Rivierenland (3)', 19980801, 20090601, false, true, 19980801, true),
   ('615304', 'GGD Rivierenland (EPI)', 20090301, NULL, false, true, null, false),
   ('615501', 'Instituut Verbeeten', 20100901, NULL, false, true, 20100901, true),
   ('615601', 'OMEGA-onderzoek', 20111101, NULL, false, true, null, false),
   ('615801', 'Wageningen Universiteit/COLON-studie', 20120101, NULL, false, true, null, false),
   ('615901', 'Stichting GGZ inGeest', 20111201, NULL, false, true, null, false),
   ('616001', 'Bevolkingsonderzoek Midden-West', 20130301, NULL, false, true, 20130301, true),
   ('616101', 'Bevolkingsonderzoek Noord', 20130301, NULL, false, true, 20130301, true),
   ('616201', 'Bevolkingsonderzoek Oost', 20130301, NULL, false, true, 20130301, true),
   ('616301', 'Bevolkingsonderzoek Zuid', 20130301, NULL, false, true, 20130301, true),
   ('616401', 'Bevolkingsonderzoek Zuid-West', 20130301, NULL, false, true, 20130301, true),
   ('616501', 'ASR Ziektekosten N.V.', 20141101, NULL, false, true, 20141101, true),
   ('616701', 'Medisch Centrum Leeuwarden/Hartcentrum Friesland', 20140901, NULL, false, true, null, false),
   ('616801', 'Stichting Medisch Spectrum Twente', 20141101, NULL, false, true, 20141101, true),
   ('625201', 'Regionaal Indicatie Orgaan Enschede e.o.', 20031201, 20060601, false, true, 20031201, true),
   ('625301', 'Regionaal Indicatie Orgaan Noord West-Twente', 20031201, 20060601, false, true, 20031201, true),
   ('625401', 'Regionaal Indicatie Orgaan WZV-regio Zwolle', 20030601, 20051201, false, true, 20030601, true),
   ('625501', 'Regionaal Indicatie Orgaan Centraal Twente', 20031201, 20060601, false, true, 20031201, true),
   ('625801', 'Regionaal Indicatie Orgaan Zaanstreek', 20040301, 20061201, false, true, 20040301, true),
   ('626201', 'Regionaal Indicatie Orgaan Midden-Holland', 20030601, 20070601, false, true, 20030601, true),
   ('626301', 'Regionaal Indicatie Orgaan Noord-Limburg', 20030601, 20070601, false, true, 20030601, true),
   ('626401', 'Regionaal Indicatie Orgaan Midden-Limburg', 20040601, 20070601, false, true, 20040601, true),
   ('627601', 'Regionaal Indicatie Orgaan Gooi en Vechtstreek', 20030601, 20061201, false, true, 20030601, true),
   ('627701', 'Stichting Ketenzorg', 20030901, 20050301, false, true, 20030901, true),
   ('628201', 'Regionaal Indicatie Orgaan Zuid Oost-Groningen', 20031201, 20060601, false, true, 20031201, true),
   ('628401', 'Regionaal Indicatie Orgaan Noord-Groningen', 20030901, 20051201, false, true, 20030901, true),
   ('628501', 'Regionaal Indicatie Orgaan Midden-Brabant', 20040301, 20060901, false, true, 20040301, true),
   ('628601', 'Regionaal Indicatie Orgaan Brabant-Noordoost', 20040301, 20051201, false, true, 20040301, true),
   ('629801', 'Stichting Rotterdams Indicatieorgaan', 20040601, 20051201, false, true, 20040601, true),
   ('640001', 'Koninklijke Nederlandse Maatschappij tot bevordering der Geneeskunst (KNMG)', 20040901, NULL, false, true, 20040901, true),
   ('640002', 'Nederlandse Zorgautoriteit (NZa)', 20101001, NULL, false, true, null, false),
   ('640101', 'Stichting Centrum Indicatiestelling Zorg (CIZ)', 20140401, NULL, false, true, 20140401, true),
   ('640201', 'MvEL&I - Diergeneeskunderegister', 20120301, NULL, false, true, 20120301, true),
   ('641101', 'MvVWS/Regelhulp', 20111001, 20170201, false, true, null, false),
   ('650001', 'MvVROM/Informatie, Beheer en Subsidieregelingen (IBS)', 19931001, 20070901, false, true, 19931001, true),
   ('650002', 'MvI&M/Eigenwoningregeling', 19960801, NULL, false, true, 19960801, true),
   ('650003', 'MvI&M Inlichtingen/Opsporingsdienst (1)', 19971101, NULL, false, true, null, false),
   ('650004', 'MvI&M Inlichtingen/Opsporingsdienst (2)', 19971101, NULL, false, true, null, false),
   ('650005', 'MvVROM/DG Wonen/Woononderzoek Nederland', 20020301, 20100301, false, true, 20020301, true),
   ('650101', 'Kadaster en Openbare Registers', 19951101, NULL, false, true, 19951101, true),
   ('650102', 'Kadaster en Openbare Registers (selectie)', 20120701, 20120901, false, true, null, false),
   ('650301', 'Stichting LID Dierenbescherming', 20080901, NULL, false, true, null, false),
   ('650401', 'Stichting Bureau Krediet Registratie', 20111101, NULL, false, true, null, false),
   ('650501', 'Nederlandse emissieautoriteit', 20110301, NULL, false, true, null, false),
   ('650601', 'Bureau Erkenningen van de AOC Raad', 20110901, NULL, false, true, 20110901, true),
   ('650701', 'Notaris Appel', 20110901, 20160701, false, true, null, false),
   ('650801', 'Huurcommissie', 20120401, NULL, false, true, null, false),
   ('650901', 'Ministerie van Infrastructuur en Milieu - Omgevingsloket Online', 20121001, NULL, false, true, null, false),
   ('651001', 'Het Inlichtingenbureau', 20130201, NULL, false, true, null, false),
   ('700001', 'Landelijk Bureau Inning Onderhoudsbijdragen', 19940901, NULL, false, true, 19940901, true),
   ('700002', 'MvVenJ/Raad voor de Kinderbescherming', 20140101, NULL, false, true, null, false),
   ('700003', 'MvVenJ/Generiek Casusoverleg Ondersteunend Systeem (GCOS)', 20101001, NULL, false, true, null, false),
   ('700101', 'Raad voor de Rechtspraak - Strafrecht (2)', 19950201, NULL, false, true, null, false),
   ('700102', 'Raad voor de Rechtspraak - Strafrecht (1)', 19950201, NULL, false, true, 19950201, true),
   ('700103', 'Raad voor de Rechtspraak - Admin.rechtelijke handhaving verkeersvoorschriften', 19950201, NULL, false, true, null, false),
   ('700105', 'Raad voor de Rechtspraak - Bestuursrecht', 19950201, NULL, false, true, null, false),
   ('700106', 'Raad voor de Rechtspraak - Civiele zaken', 19950201, NULL, false, true, null, false),
   ('700107', 'MvVW/Adv.dienst Verkeer en Vervoer', 20040301, 20100301, false, true, 20040301, true),
   ('700108', 'MvVenJ/Functioneel Parket', 20140601, NULL, false, true, null, false),
   ('700109', 'MvVenJ/Schadefonds Geweldsmisdrijven', 20100201, NULL, false, true, null, false),
   ('700110', 'MvVenJ/Centraal Justitieel Incassobureau (2)', 20141001, 20170701, false, true, null, false),
   ('700201', 'MvJ/KLPD/CRI mbt ongeldig reisdocument', 19950201, 20030901, false, true, 19950201, true),
   ('700202', 'MvJ/KLPD/Divisie Kon. en Dipl. Beveiliging', 19950501, 20010301, false, true, 19950501, true),
   ('700203', 'MvJ/KLPD/Divisie Kon. en Dipl. Beveiliging (Geheim)', 19950501, 20010301, false, true, 19950501, true),
   ('700204', 'MvJ/KLPD/Divisie CRI', 19950501, 19961101, false, true, 19950501, true),
   ('700205', 'MvJ/KLPD/Divisie CRI (Geheim)', 19950501, 19961101, false, true, 19950501, true),
   ('700206', 'MvJ/KLPD/Divisie Mobiliteit', 19950501, 19961101, false, true, 19950501, true),
   ('700207', 'MvJ/KLPD/Divisie Mobiliteit (Geheim)', 19950501, 19961101, false, true, 19950501, true),
   ('700208', 'MvJus/KLPD', 19950501, 20010301, false, true, 19950501, true),
   ('700209', 'MvJus/KLPD (Geheim)', 19950501, 20010301, false, true, 19950501, true),
   ('700210', 'MvBZK/KLPD/NRI mbt NSIS', 19961101, 20130601, false, true, 19961101, true),
   ('700301', 'MvVenJ/IND (Nederlanders)', 20131101, NULL, false, true, 20131101, true),
   ('700302', 'MvVenJ/IND (Vreemdelingen)', 20130901, NULL, false, true, 20130901, true),
   ('700303', 'MvBZK/IND (Nederlanders) (selectie)', 20110701, 20120901, false, true, null, false),
   ('700401', 'Algemene Inlichtingen- en Veiligheidsdienst (AIVD)', 20150101, NULL, false, true, null, false),
   ('700501', 'NS Beveiliging Services', 19960501, 20001201, false, true, 19960501, true),
   ('700601', 'Financial Intelligence Unit - Nederland', 19980501, 20120601, false, true, 19980501, true),
   ('700701', 'MvJus/NIFP Locatie Pieter Baan Centrum', 20120201, NULL, false, true, null, false),
   ('700901', 'MvVenJ/Rijksrecherche', 19981101, 20130401, false, true, 19981101, true),
   ('701001', 'MvJus/Cliënt-Volgsysteem JC (1)', 19980801, 20021201, false, true, 19980801, true),
   ('701002', 'MvJus/Cliënt-Volgsysteem JC (2)', 20010401, 20110601, false, true, 20010401, true),
   ('701102', 'SAB-St. Afvalstoffen&vaardoc binnenvaart', 20100101, NULL, false, true, null, false),
   ('701201', 'MvJus/Verwijs Index Personen (VIP) (1)', 19990301, 20021201, false, true, 19990301, true),
   ('701202', 'MvVenJ/Justitiële Informatiedienst/Strafrechtketen Databank (SKDB)', 20140201, NULL, false, true, 20140201, true),
   ('701212', 'MvVenJ/Strafrechtketen Databank (SKDB)/Verwijs Index Personen (VIP) (pilot)', 20120501, 20130701, false, true, null, false),
   ('701302', 'MvVenJ/Dienst Justis/Covog', 20081001, NULL, false, true, null, false),
   ('701303', 'MvVenJ/Dienst Justis/HTR', 20100601, NULL, false, true, null, false),
   ('701304', 'MvVenJ/Dienst Justis/WPBR (Wet particuliere beveiligingsorg en recherchebureaus)', 20140501, NULL, false, true, null, false),
   ('701305', 'MvVenJ/Dienst Justis/Naamswijziging', 20140501, NULL, false, true, null, false),
   ('701401', 'Raad voor de Rechtsbijstand', 20041201, NULL, false, true, 20041201, true),
   ('701501', 'Stichting VAM (IBKI)', 20090901, NULL, false, true, null, false),
   ('701601', 'Autoriteit Financiële Markten', 20091201, NULL, false, true, null, false),
   ('701701', 'Bureau Financieel Toezicht', 20100601, NULL, false, true, null, false),
   ('701901', 'MvEZ/Basisbedrijvenregister', 20031201, 20101201, false, true, 20031201, true),
   ('702001', 'Nederlands Studiecentrum Criminaliteit en Rechtshandhandhaving (NSCR)', 20140901, NULL, false, true, null, false),
   ('702101', 'Nederlands bureau brandweerexamens (Nbbe)', 20111001, 20150701, false, true, null, false),
   ('702201', 'Hoge Raad der Nederlanden', 20110701, NULL, false, true, null, false),
   ('702301', 'MvVenJ/Agentschap SZW', 20120101, 20150701, false, true, null, false),
   ('702501', 'MvVenJ/Dienst Justitiële Inrichtingen/WETS en WOTS', 20150101, NULL, false, true, null, false),
   ('702601', 'Centraal Bureau Rijvaardigheidsbewijzen (CBR)', 20130101, NULL, false, true, null, false),
   ('702701', 'MvVenJ/Openbaar Ministerie', 20140301, NULL, false, true, null, false),
   ('702801', 'MvI&M/Rijkswaterstaat/BOA-taak', 20141101, NULL, false, true, null, false),
   ('703301', 'Raad van State/Bestuursrechtspraak', 20150101, NULL, false, true, null, false),
   ('710101', 'Regiopolitie Brabant-Noord', 19951101, 20010301, false, true, 19951101, true),
   ('710102', 'Regiopolitie Brabant-Noord (Geheim)', 19951101, 20010301, false, true, 19951101, true),
   ('710103', 'Regiopolitie Hollands Midden', 19951101, 20010301, false, true, 19951101, true),
   ('710104', 'Regiopolitie Hollands Midden (Geheim)', 19951101, 20010301, false, true, 19951101, true),
   ('710105', 'Regiopolitie Kennemerland', 19951101, 20010301, false, true, 19951101, true),
   ('710106', 'Regiopolitie Kennemerland (Geheim)', 19951101, 20010301, false, true, 19951101, true),
   ('710107', 'Regiopolitie Midden- en West-Brabant', 19951101, 20010301, false, true, 19951101, true),
   ('710108', 'Regiopolitie Midden- en West-Brabant (Geheim)', 19951101, 20010301, false, true, 19951101, true),
   ('710109', 'Regiopolitie Zeeland', 19951101, 20010301, false, true, 19951101, true),
   ('710110', 'Regiopolitie Zeeland (Geheim)', 19951101, 20010301, false, true, 19951101, true),
   ('710111', 'Regiopolitie Brabant Zuid-Oost', 19960201, 20010301, false, true, 19960201, true),
   ('710112', 'Regiopolitie Brabant Zuid-Oost (Geheim)', 19960201, 20010301, false, true, 19960201, true),
   ('710113', 'Regiopolitie Gelderland-Midden', 19960201, 20010301, false, true, 19960201, true),
   ('710114', 'Regiopolitie Gelderland-Midden (Geheim)', 19960201, 20010301, false, true, 19960201, true),
   ('710115', 'Regiopolitie Gelderland-Zuid', 19960201, 20010301, false, true, 19960201, true),
   ('710116', 'Regiopolitie Gelderland-Zuid (Geheim)', 19960201, 20010301, false, true, 19960201, true),
   ('710117', 'Regiopolitie Limburg-Noord', 19960201, 20010301, false, true, 19960201, true),
   ('710118', 'Regiopolitie Limburg-Noord (Geheim)', 19960201, 20010301, false, true, 19960201, true),
   ('710119', 'Regiopolitie Limburg-Zuid', 19960201, 20010301, false, true, 19960201, true),
   ('710120', 'Regiopolitie Limburg-Zuid (Geheim)', 19960201, 20010301, false, true, 19960201, true),
   ('710121', 'Regiopolitie Utrecht', 19960201, 20010301, false, true, 19960201, true),
   ('710122', 'Regiopolitie Utrecht (Geheim)', 19960201, 20010301, false, true, 19960201, true),
   ('710123', 'Regiopolitie Flevoland', 19960501, 20010301, false, true, 19960501, true),
   ('710124', 'Regiopolitie Flevoland (Geheim)', 19960501, 20010301, false, true, 19960501, true),
   ('710125', 'Regiopolitie Gooi- en Vechtstreek', 19960501, 20010301, false, true, 19960501, true),
   ('710126', 'Regiopolitie Gooi- en Vechtstreek (Geheim)', 19960501, 20010301, false, true, 19960501, true),
   ('710127', 'Regiopolitie IJsselland', 19960801, 20010301, false, true, 19960801, true),
   ('710128', 'Regiopolitie IJsselland (Geheim)', 19960801, 20010301, false, true, 19960801, true),
   ('710129', 'Regiopolitie Noord- en Oost-Gelderland', 19960501, 20010301, false, true, 19960501, true),
   ('710130', 'Regiopolitie Noord- en Oost-Gelderland (Geheim)', 19960501, 20010301, false, true, 19960501, true),
   ('710131', 'Regiopolitie Noord-Holland Noord', 19960501, 20010301, false, true, 19960501, true),
   ('710132', 'Regiopolitie Noord-Holland Noord (Geheim)', 19960501, 20010301, false, true, 19960501, true),
   ('710133', 'Regiopolitie Rotterdam-Rijnmond', 19960501, 20010301, false, true, 19960501, true),
   ('710134', 'Regiopolitie Rotterdam-Rijnmond (Geheim)', 19960501, 20010301, false, true, 19960501, true),
   ('710135', 'Regiopolitie Twente', 19960501, 20010301, false, true, 19960501, true),
   ('710136', 'Regiopolitie Twente (Geheim)', 19960501, 20010301, false, true, 19960501, true),
   ('710137', 'Regiopolitie Zaanstreek-Waterland', 19960501, 20010301, false, true, 19960501, true),
   ('710138', 'Regiopolitie Zaanstreek-Waterland (Geheim)', 19960501, 20010301, false, true, 19960501, true),
   ('710139', 'Regiopolitie Zuid-Holland Zuid', 19960501, 20010301, false, true, 19960501, true),
   ('710140', 'Regiopolitie Zuid-Holland Zuid (Geheim)', 19960501, 20010301, false, true, 19960501, true),
   ('710141', 'Regiopolitie Amsterdam-Amstelland', 19960801, 20010301, false, true, 19960801, true),
   ('710142', 'Regiopolitie Amsterdam-Amstelland (Geheim)', 19960801, 20010301, false, true, 19960801, true),
   ('710143', 'Regiopolitie Drenthe', 19961101, 20010301, false, true, 19961101, true),
   ('710144', 'Regiopolitie Drenthe (Geheim)', 19961101, 20010301, false, true, 19961101, true),
   ('710145', 'Regiopolitie Friesland', 19961101, 20010301, false, true, 19961101, true),
   ('710146', 'Regiopolitie Friesland (Geheim)', 19961101, 20010301, false, true, 19961101, true),
   ('710147', 'Regiopolitie Groningen', 19961101, 20010301, false, true, 19961101, true),
   ('710148', 'Regiopolitie Groningen (Geheim)', 19961101, 20010301, false, true, 19961101, true),
   ('710149', 'Regiopolitie Haaglanden', 19960801, 20010301, false, true, 19960801, true),
   ('710150', 'Regiopolitie Haaglanden (Geheim)', 19960801, 20010301, false, true, 19960801, true),
   ('710151', 'Politie (1)', 20010301, NULL, false, true, 20010301, true),
   ('710152', 'Politie (2)', 20010301, NULL, false, true, 20010301, true),
   ('710153', 'Politie (3)', 20010301, NULL, false, true, 20010301, true),
   ('750001', 'Militaire Inlichtingen- en Veiligheidsdienst (MIVD)', 20150101, NULL, false, true, 20150101, true),
   ('750002', 'Militaire Inlichtingen en Veiligheidsdienst (pilot)', 20120101, 20121001, false, true, null, false),
   ('800001', 'USZO/ABP/FB/IT/BO/BIV', 19921101, 20090901, false, true, 19921101, true),
   ('800101', 'Pensioen- en Uitkeringsraad', 19931001, NULL, false, true, 19931001, true),
   ('800201', 'DUO, Staf en Informatievoorziening', 19940901, 19960101, false, true, 19940901, true),
   ('800301', 'Doctors Pension Funds Services B.V.', 19950501, 20150801, false, true, 19950501, true),
   ('800302', 'Doctors Pension Funds Services B.V. (selectie)', 20120401, 20120901, false, true, null, false),
   ('800401', 'MN Services', 19950501, NULL, false, true, 19950501, true),
   ('800402', 'MN Services schade', 20110901, NULL, false, true, 20110901, true),
   ('800403', 'MN Services (selectie)', 20120401, 20120901, false, true, null, false),
   ('800501', 'Delta Lloyd Levensverzekering N.V.', 19950501, NULL, false, true, 19950501, true),
   ('800502', 'Delta Lloyd Levensverzekering N.V. (selectie)', 20120401, 20120901, false, true, null, false),
   ('800601', 'Achmea Pensioen- en Levensverzekeringen', 19950501, NULL, false, true, 19950501, true),
   ('800602', 'Achmea Pensioen- en Levensverzekeringen (selectie)', 20120401, NULL, false, true, null, false),
   ('800701', 'Achmea Pensioen- en Levensverzekeringen (1)', 19950501, 20140601, false, true, 19950501, true),
   ('800702', 'Achmea Pensioen- en Levensverzekeringen (1) (selectie)', 20120401, 20120901, false, true, null, false),
   ('800801', 'Hewitt Associates Outsourcing B.V.', 19950801, 20170401, false, true, 19950801, true),
   ('800802', 'Hewitt Associates Outsourcing B.V. (selectie)', 20120401, 20120901, false, true, null, false),
   ('800901', 'DSM Pension Services B.V.', 19950801, NULL, false, true, 19950801, true),
   ('800902', 'DSM Pension Services B.V. (selectie)', 20120401, 20120901, false, true, null, false),
   ('801001', 'Verzekeringsgroep ASR Nederland', 19950801, NULL, false, true, 19950801, true),
   ('801002', 'Verzekeringsgroep ASR Nederland (selectie)', 20120401, 20120901, false, true, null, false),
   ('801101', 'AEGON Levensverzekering N.V. (selectie)', 19950801, NULL, false, true, 19950801, true),
   ('801102', 'AEGON Levensverzekering N.V. (selectie)', 20120401, 20120901, false, true, null, false),
   ('801201', 'Pensioenfonds Ahold', 19951101, NULL, false, true, 19951101, true),
   ('801202', 'Pensioenfonds Ahold (selectie)', 20120401, 20120901, false, true, null, false),
   ('801301', 'Interpolis Pensioenbeheer (2)', 19951101, 20100601, false, true, 19951101, true),
   ('801401', 'ING Pension Services', 19951101, 20080901, false, true, 19951101, true),
   ('801501', 'PVF Nederland N.V. Pensioenfonds', 19951101, 20100601, false, true, 19951101, true),
   ('801601', 'Pensioenfonds S.M.I.', 19951101, 20131101, false, true, 19951101, true),
   ('801602', 'Pensioenfonds S.M.I. (selectie)', 20120401, 20120901, false, true, null, false),
   ('801701', 'OPTAS', 19951101, 20091201, false, true, 19951101, true),
   ('801801', 'Pensioenfonds Nationale-Nederlanden', 19951101, NULL, false, true, 19951101, true),
   ('801802', 'Pensioenfonds Nationale-Nederlanden (selectie)', 20120401, 20120901, false, true, null, false),
   ('801901', 'PBO-Dienstverlening', 19951101, 20160201, false, true, 19951101, true),
   ('801902', 'PBO-Dienstverlening (selectie)', 20120401, 20120901, false, true, null, false),
   ('802001', 'AZL N.V.', 19920501, NULL, false, true, 19920501, true),
   ('802101', 'Stichting Pensioenfonds Grontmij', 19951101, NULL, false, true, 19951101, true),
   ('802102', 'Stichting Pensioenfonds Grontmij (selectie)', 20120401, 20120901, false, true, null, false),
   ('802201', 'Stichting Pensioenfonds Sphinx Maastricht', 19951101, 20130101, false, true, 19951101, true),
   ('802202', 'Pensioenfonds Koninklijke Sphinx (selectie)', 20120401, 20120901, false, true, null, false),
   ('802301', 'Pensioenfonds Wolters Kluwer Nederland', 19951101, NULL, false, true, 19951101, true),
   ('802401', 'Pensioenfonds Vereenigde Glasfabrieken', 19951101, 20040601, false, true, 19951101, true),
   ('802501', 'Achmea Pensioen- en Levensverzekering (2)', 19951101, 20160201, false, true, 19951101, true),
   ('802602', 'Generali verzekeringsgroep (selectie)', 20120401, 20120901, false, true, null, false),
   ('802701', 'Stichting Pensioenfonds Confidentia', 19960201, 20030601, false, true, 19960201, true),
   ('802801', 'Bedrijfspensioenfonds Zorgverzekeraars', 19960201, 20131201, false, true, 19960201, true),
   ('802901', 'Stichting Pensioenfonds Imtech', 19960201, 20130801, false, true, 19960201, true),
   ('802902', 'Stichting Pensioenfonds Imtech (selectie)', 20120401, 20120901, false, true, null, false),
   ('803001', 'Stichting Pensioenfonds Erven Lucas Bols', 19960201, 20010401, false, true, 19960201, true),
   ('803201', 'Stad Rotterdam Verzekeringen', 19960201, 20060901, false, true, 19960201, true),
   ('803301', 'Stichting Pensioenfonds ABN AMRO bank N.V.', 19960501, NULL, false, true, 19960501, true),
   ('803302', 'Stichting Pensioenfonds ABN AMRO bank N.V. (selectie)', 20120401, 20120901, false, true, null, false),
   ('803401', 'Stichting Pensioenfonds Akzo Nobel', 19960501, 20110301, false, true, 19960501, true),
   ('803501', 'Stichting Pensioenfonds Holland Casino', 19960501, NULL, false, true, 19960501, true),
   ('803502', 'Stichting Pensioenfonds Holland Casino (selectie)', 20120401, 20120901, false, true, null, false),
   ('803601', 'De Eendragt Pensioen N.V.', 19960501, NULL, false, true, 19960501, true),
   ('803602', 'De Eendragt Pensioen N.V. (selectie)', 20120401, 20120901, false, true, null, false),
   ('803701', 'IFF Pensioenfonds', 19960501, 20150201, false, true, 19960501, true),
   ('803702', 'IFF Pensioenfonds (selectie)', 20120401, 20120901, false, true, null, false),
   ('803801', 'Stichting Pensioenfonds IHC Holland', 19960501, 20050301, false, true, 19960501, true),
   ('803901', 'TKP Pensioen B.V.', 19960501, NULL, false, true, 19960501, true),
   ('803902', 'TKP Pensioen B.V. (selectie)', 20120401, 20120901, false, true, null, false),
   ('804001', 'Kuwait Petroleum Pensioenfonds', 19960501, 20070601, false, true, 19960501, true),
   ('804101', 'Stichting Nedlloyd Pensioenfonds', 19960501, 20150501, false, true, 19960501, true),
   ('804201', 'Pensioenfonds vd Gezondheid, Geestelijke en Maatschappelijke belangen (PGGM)', 19960501, NULL, false, true, 19960501, true),
   ('804202', 'Pensioenfonds vd Gezondheid, Geestelijke en Maatschappelijke belangen (selectie)', 20120401, 20120901, false, true, null, false),
   ('804301', 'Reaal Verzekeringen', 19960501, NULL, false, true, 19960501, true),
   ('804302', 'Reaal Verzekeringen (selectie)', 20120401, 20120901, false, true, null, false),
   ('804401', 'TNO Pensioenfonds', 19960501, 20130201, false, true, 19960501, true),
   ('804402', 'TNO Pensioenfonds (selectie)', 20120401, 20120901, false, true, null, false),
   ('804501', 'Co-op Pensioenfonds', 19960501, 20150301, false, true, 19960501, true),
   ('804502', 'Co-op Pensioenfonds (selectie)', 20120401, 20120901, false, true, null, false),
   ('804601', 'Hoogovens Pensioenfonds', 19960801, NULL, false, true, 19960801, true),
   ('804602', 'Hoogovens Pensioenfonds (selectie)', 20120401, 20120901, false, true, null, false),
   ('804701', 'Media Pensioen Diensten', 19960801, NULL, false, true, 19960801, true),
   ('804702', 'PNO Media (selectie)', 20120401, 20120901, false, true, null, false),
   ('804801', 'Cordares Pensioendiensten B.V.', 19960801, NULL, false, true, 19960801, true),
   ('804802', 'Cordares Pensioendiensten B.V. (selectie)', 20120401, 20120901, false, true, null, false),
   ('804901', 'Stichting Pensioenfonds Gist-Brocades', 19960801, 20060601, false, true, 19960801, true),
   ('805001', 'Syntrus Achmea Pensioenbeheer', 19960801, NULL, false, true, 19960801, true),
   ('805002', 'Syntrus Achmea Pensioenbeheer (selectie)', 20120401, 20120901, false, true, null, false),
   ('805102', 'Stichting Grafische Bedrijfsfondsen (selectie)', 20120401, 20120901, false, true, null, false),
   ('805201', 'Stichting Pensioenfonds HBG', 19961101, 20090301, false, true, 19961101, true),
   ('805301', 'OHRA Levensverzekeringen N.V.', 19961101, NULL, false, true, 19961101, true),
   ('805302', 'OHRA Levensverzekeringen N.V. (selectie)', 20120401, 20120901, false, true, null, false),
   ('805401', 'Pensioenfonds Koninklijke Volker Wessels Stevin', 19961101, 20120301, false, true, 19961101, true),
   ('805501', 'Stichting Pensioenfonds Campina', 19961101, NULL, false, true, 19961101, true),
   ('805502', 'Stichting Pensioenfonds Campina (selectie)', 20120401, 20120901, false, true, null, false),
   ('805601', 'Stichting Heineken Pensioenfonds', 19961101, NULL, false, true, 19961101, true),
   ('805602', 'Stichting Heineken Pensioenfonds (selectie)', 20120401, 20120901, false, true, null, false),
   ('805701', 'Goudse Levensverzekering N.V.', 19961101, 20060601, false, true, 19961101, true),
   ('805801', 'William M. Mercer', 19970201, 20040601, false, true, 19970201, true),
   ('805901', 'Stichting Pensioenfonds Bayer Cropscience', 19970201, 20051201, false, true, 19970201, true),
   ('806001', 'Stichting Pensioenfonds GRES', 19970201, 20010401, false, true, 19970201, true),
   ('806101', 'Stichting Pensioenfonds Cosun', 19970201, NULL, false, true, 19970201, true),
   ('806201', 'Stichting Pensioenfonds Sagittarius', 19970201, NULL, false, true, 19970201, true),
   ('806301', 'Stichting Pensioenfonds Hillen & Roosen', 20030901, 20050901, false, true, 20030901, true),
   ('806401', 'Stichting Pensioenfonds Personeel Gesubsidieerde Instellingen (PGI)', 19970201, 20060601, false, true, 19970201, true),
   ('806501', 'Stichting Pensioenfonds van Radio-Holland', 19970201, 20091201, false, true, 19970201, true),
   ('806601', 'Stichting Pensioenfonds Buma/Stemra', 19970201, 20030901, false, true, 19970201, true),
   ('806701', 'Stichting Pensioenfonds Gazelle', 19970201, 20140401, false, true, 19970201, true),
   ('806702', 'Stichting Pensioenfonds Gazelle (selectie)', 20120401, 20120901, false, true, null, false),
   ('806801', 'Stichting Pensioenfonds TDV', 19970201, NULL, false, true, 19970201, true),
   ('806802', 'Stichting Pensioenfonds TDV (selectie)', 20120401, 20120901, false, true, null, false),
   ('806901', 'Stichting Pensioenfonds Alcatel Nederland', 19970201, 20111201, false, true, 19970201, true),
   ('807001', 'Stichting Servicekantoor AGH', 19970501, NULL, false, true, 19970501, true),
   ('807002', 'Stichting Servicekantoor AGH (selectie)', 20120401, 20120901, false, true, null, false),
   ('807201', 'Stichting Pensioenfonds HAL', 19970501, NULL, false, true, 19970501, true),
   ('807202', 'Stichting Pensioenfonds HAL (selectie)', 20120401, 20120901, false, true, null, false),
   ('807301', 'Stichting Bedrijfspensioenfonds vd Detailhandel', 19970501, 20080301, false, true, 19970501, true),
   ('807401', 'Stichting Bedrijfspensioenfonds Optiekbedrijven', 19970501, 20071201, false, true, 19970501, true),
   ('807501', 'Stichting Pensioenfonds Ballast Nedam', 19970801, NULL, false, true, 19970801, true),
   ('807502', 'Stichting Pensioenfonds Ballast Nedam (selectie)', 20120401, 20120901, false, true, null, false),
   ('807601', 'Stichting Pensioenfonds Robecam', 19970501, 20040601, false, true, 19970501, true),
   ('807701', 'Stichting Pensioenfonds Stork', 19970501, 20130101, false, true, 19970501, true),
   ('807801', 'Crystal Marbles', 19970801, 20150301, false, true, 19970801, true),
   ('807802', 'Crystal Marbles (selectie)', 20120401, 20120901, false, true, null, false),
   ('807901', 'Pensioenbureau BAM NBM', 19970501, 20050301, false, true, 19970501, true),
   ('808001', 'Pensioenfonds Océ', 19970801, 20150101, false, true, 19970801, true),
   ('808002', 'Pensioenfonds Océ (selectie)', 20120401, 20120901, false, true, null, false),
   ('808101', 'Stichting Pensioenfonds Schuitema (2)', 19970501, 20050901, false, true, 19970501, true),
   ('808201', 'Stichting CRH Pensioenfonds', 19970501, NULL, false, true, 19970501, true),
   ('808202', 'Stichting CRH Pensioenfonds (selectie)', 20120401, 20120901, false, true, null, false),
   ('808301', 'Stichting Pensioenfonds Het Witte Hart', 19970501, 20080301, false, true, 19970501, true),
   ('808401', 'FairGo Leven', 19970801, 20030301, false, true, 19970801, true),
   ('808501', 'Stichting Pensioenfonds VCBV', 19970801, 20070901, false, true, 19970801, true),
   ('808601', 'Zwitserleven', 19971101, NULL, false, true, 19971101, true),
   ('808602', 'Zwitserleven PPI N.V.', 20140401, NULL, false, true, 20140401, true),
   ('808701', 'Stichting Shell Pensioenfonds', 19971101, NULL, false, true, 19971101, true),
   ('808801', 'Stichting Pensioenfonds ICI Holland', 19971101, 20000601, false, true, 19971101, true),
   ('808901', 'Pensioenfonds Arcadis Nederland', 19971101, NULL, false, true, 19971101, true),
   ('808902', 'Pensioenfonds Arcadis Nederland (selectie)', 20120401, 20120901, false, true, null, false),
   ('809001', 'ACS HR Solutions', 19980201, NULL, false, true, 19980201, true),
   ('809002', 'ACS HR Solutions (selectie)', 20120401, 20120901, false, true, null, false),
   ('809101', 'Stichting Pensioenfonds De Boer', 19980201, 20030901, false, true, 19980201, true),
   ('809201', 'A&O Pensioen Services', 19980501, 20151001, false, true, 19980501, true),
   ('809301', 'Hewitt Associates B.V.', 19980501, NULL, false, true, 19980501, true),
   ('809302', 'Hewitt Associates B.V. (selectie)', 20120401, 20120901, false, true, null, false),
   ('809401', 'AXA Leven N.V.', 19980501, 20101201, false, true, 19980501, true),
   ('809501', 'Stichting Pensioenfonds C1000', 19980501, 20151001, false, true, 19980501, true),
   ('809601', 'Stichting Pensioenfonds Gamma Holding Nederland', 19981101, 20070901, false, true, 19981101, true),
   ('809701', 'Stichting Pensioenfonds Cementbouw', 19980801, 20030901, false, true, 19980801, true),
   ('809801', 'Stichting Pensioenfonds Randstad', 19980801, NULL, false, true, 19980801, true),
   ('809901', 'Stichting Pensioenfonds Elsevier-Ondernemingen', 19980801, 20160701, false, true, 19980801, true),
   ('810001', 'Xerox Pensioenfondsen', 20060901, NULL, false, true, 20060901, true),
   ('810002', 'Xerox Pensioenfondsen (selectie)', 20120401, 20120901, false, true, null, false),
   ('810101', 'Stichting Pensioenfonds Asea Brown Boveri', 19980801, 20101201, false, true, 19980801, true),
   ('810201', 'Appolaris Pensioenbeheer', 19980801, NULL, false, true, 19980801, true),
   ('810202', 'Appolaris Pensioenbeheer (selectie)', 20120401, 20120901, false, true, null, false),
   ('810301', 'Stichting TOTAL Pensioenfonds Nederland', 20031201, 20151101, false, true, 20031201, true),
   ('810302', 'Stichting TOTAL Pensioenfonds Nederland (selectie)', 20120401, 20120901, false, true, null, false),
   ('810402', 'KPMG Management Services B.V. (selectie)', 20120401, 20120901, false, true, null, false),
   ('810501', 'Stichting Pensioenfonds De Hoop', 19980801, 20101201, false, true, 19980801, true),
   ('810601', 'Interpolis', 19981101, 20040901, false, true, 19981101, true),
   ('810701', 'Blue Sky Group', 19981101, NULL, false, true, 19981101, true),
   ('810702', 'Blue Sky Group (selectie)', 20120401, 20120901, false, true, null, false),
   ('810801', 'Poseidon', 19981101, 20140301, false, true, 19981101, true),
   ('810901', 'Pensioenfonds Witteveen+Bos', 19981101, 20080301, false, true, 19981101, true),
   ('811001', 'Ernst & Young Actuarissen B.V.', 19990301, 20070101, false, true, 19990301, true),
   ('811101', 'Pensioenfonds Alliance', 19990301, 20030901, false, true, 19990301, true),
   ('811201', 'Hagee-Stichting Pensioenfonds', 19990601, NULL, false, true, 19990601, true),
   ('811202', 'Hagee-Stichting Pensioenfonds (selectie)', 20120401, 20120901, false, true, null, false),
   ('811301', 'Stichting Masterfoods Pensioenfonds', 19990601, 20080901, false, true, 19990601, true),
   ('811302', 'Stichting Mars Pensioenfonds', 20100601, 20140401, false, true, 20100601, true),
   ('811303', 'Stichting Mars Pensioenfonds (selectie)', 20120401, 20120901, false, true, null, false),
   ('811401', 'Stichting Pensioenfonds Meneba', 19990601, 20150201, false, true, 19990601, true),
   ('811402', 'Stichting Pensioenfonds Meneba (selectie)', 20120401, 20120901, false, true, null, false),
   ('811501', 'Allianz Nederland', 19991201, NULL, false, true, 19991201, true),
   ('811601', 'Stichting Notarieel Pensioenfonds', 19991201, 20150101, false, true, 19991201, true),
   ('811701', 'Tandartsen en tandartsspecialisten', 19991201, 20060301, false, true, 19991201, true),
   ('811801', 'Stichting Pensioenfonds Siemens', 19991201, 20120301, false, true, 19991201, true),
   ('811901', 'Winterthur', 20000601, 20101201, false, true, 20000601, true),
   ('812001', 'Stichting Pensioenfonds Horeca & Catering', 20050601, NULL, false, true, 20050601, true),
   ('812002', 'Stichting Pensioenfonds Horeca & Catering (selectie)', 20120401, 20120901, false, true, null, false),
   ('812101', 'PCM Uitgevers', 20000601, 20060601, false, true, 20000601, true),
   ('812201', 'Stichting Pensioenfonds Deutsche Bank Nederland', 20000601, 20110901, false, true, 20000601, true),
   ('812301', 'Stichting Pensioenfonds OPG', 20000601, 20080301, false, true, 20000601, true),
   ('812401', 'Stichting Pensioenfonds Fresenius Nederland', 20001201, NULL, false, true, 20001201, true),
   ('812501', 'Draka Holding', 20001201, 20150501, false, true, 20001201, true),
   ('812502', 'Draka Holding (selectie)', 20120401, 20120901, false, true, null, false),
   ('812701', 'Rockwool', 20001201, 20101201, false, true, 20001201, true),
   ('812801', 'De Nederlandsche Bank', 20001201, 20080301, false, true, 20001201, true),
   ('812901', 'Grolsche Bierbrouwerij', 20001201, 20070601, false, true, 20001201, true),
   ('813001', 'Lxy Groep B.V.', 20030901, NULL, false, true, 20030901, true),
   ('813002', 'Lxy Groep B.V. (selectie)', 20120401, 20120901, false, true, null, false),
   ('813101', 'Stichting Pensioenfonds Deloitte', 20001201, 20161201, false, true, 20001201, true),
   ('813201', 'C.Misset', 20001201, 20040901, false, true, 20001201, true),
   ('813301', 'Kunst en Cultuur', 20001201, 20120901, false, true, 20001201, true),
   ('813401', 'Stichting Pensioenfonds ACF/Brocacef', 20041201, NULL, false, true, 20041201, true),
   ('813402', 'Stichting Pensioenfonds ACF/Brocacef (selectie)', 20120401, 20120901, false, true, null, false),
   ('813501', 'Stichting Pensioenfonds Cindu International', 20061201, NULL, false, true, 20061201, true),
   ('813502', 'Stichting Pensioenfonds Cindu International (selectie)', 20120401, 20120901, false, true, null, false),
   ('813601', 'Interbrew', 20001201, 20081201, false, true, 20001201, true),
   ('813701', 'Allied Domecq', 20001201, 20030901, false, true, 20001201, true),
   ('813801', 'Provisum', 20001201, NULL, false, true, 20001201, true),
   ('813802', 'Provisum (selectie)', 20120401, 20120901, false, true, null, false),
   ('813901', 'Stichting Pensioenfonds Peek & Cloppenburg', 20010901, 20151201, false, true, 20010901, true),
   ('813902', 'Stichting Pensioenfonds Peek & Cloppenburg (selectie)', 20120401, 20120901, false, true, null, false),
   ('814001', 'Douwe Egberts', 20010901, 20070601, false, true, 20010901, true),
   ('814101', 'Woningraad Groep', 20010901, 20060901, false, true, 20010901, true),
   ('814201', 'Stichting-Telegraafpensioenfonds 1959', 20010901, NULL, false, true, 20010901, true),
   ('814301', 'CSM Nederland', 20011201, 20140201, false, true, 20011201, true),
   ('814401', 'Holec', 20010901, 20050301, false, true, 20010901, true),
   ('814501', 'Stichting Pensioenfonds KPMG', 20010901, 20100301, false, true, 20010901, true),
   ('814601', 'Panteia B.V.', 20011201, 20140201, false, true, 20011201, true),
   ('814701', 'NKF Kabel', 20011201, 20080901, false, true, 20011201, true),
   ('814801', 'Nedalco', 20011201, 20130101, false, true, 20011201, true),
   ('814802', 'Nedalco (selectie)', 20120401, 20120901, false, true, null, false),
   ('814901', 'Stichting Pensioenfonds Sandvik in Nederland', 20060901, 20100901, false, true, 20060901, true),
   ('815001', 'Stichting Pensioenfonds Unisys Nederland', 20071201, 20160101, false, true, 20071201, true),
   ('815002', 'Stichitng Pensioenfonds Unisys Nederland (selectie)', 20120401, 20120901, false, true, null, false),
   ('815101', 'Towers Watson', 20020901, NULL, false, true, 20020901, true),
   ('815301', 'Stichting Lips Pensioenfonds', 20020901, 20120301, false, true, 20020901, true),
   ('815401', 'Stichting Pensioenfonds Predikanten in de Protestantse Kerk', 20071201, 20130301, false, true, 20071201, true),
   ('815402', 'Stichting Pensioenfonds Predikanten in de Protestantse Kerk (selectie)', 20120401, 20120901, false, true, null, false),
   ('815501', 'Stichting Pensioenfonds H. Desseaux N.V.', 20021201, 20070301, false, true, 20021201, true),
   ('815601', 'Stichting Pensioenfonds Verkade', 20030601, 20080301, false, true, 20030601, true),
   ('815701', 'Stichting Pensioenfonds British American Tobacco', 20030601, 20160601, false, true, 20030601, true),
   ('815702', 'Stichting Pensioenfonds British American Tobacco (selectie)', 20120401, 20120901, false, true, null, false),
   ('815801', 'Stichting Ondernemingspensioenfonds MN Services', 20071201, NULL, false, true, 20071201, true),
   ('815901', 'Akkermans en Partners Facilitair', 20080301, 20100901, false, true, 20080301, true),
   ('816001', 'ABN-AMRO Levensverzekering N.V.', 20080901, 20170601, false, true, 20080901, true),
   ('816002', 'ABN-AMRO Levensverzekering N.V. (selectie)', 20120401, 20120901, false, true, null, false),
   ('816101', 'Stichting Pensioenfonds PON Holdings', 20080901, NULL, false, true, 20080901, true),
   ('816201', 'Goudse Levensverzekeringen N.V.', 20140401, NULL, false, true, 20140401, true),
   ('816301', 'Delta Lloyd schadeverzekering N.V.', 20081101, NULL, false, true, null, false),
   ('816401', 'OHRA Schadeverzekeringen N.V.', 20081201, 20140301, false, true, null, false),
   ('816501', 'Nationale Nederlanden Schadeverz. Mij.', 20081201, NULL, false, true, null, false),
   ('816602', 'First Pensions Administration Partners (selectie)', 20120401, 20120901, false, true, null, false),
   ('816701', 'Achmea schadeverzekeringen N.V.', 20090401, NULL, false, true, null, false),
   ('816801', 'N.V. Interpolis Schade', 20090401, 20110601, false, true, null, false),
   ('816901', 'Goudse Schadeverzekeringen N.V.', 20140401, NULL, false, true, 20140401, true),
   ('817001', 'Aegon Schadeverzekering N.V.', 20090501, NULL, false, true, null, false),
   ('817101', 'Stichting Norit Pensioenfonds', 20100601, NULL, false, true, 20100601, true),
   ('817102', 'Stichting Norit Pensioenfonds (selectie)', 20120401, 20120901, false, true, null, false),
   ('817201', 'Stichting Pensioenfonds Reclame en Marketing', 20100901, 20120101, false, true, null, false),
   ('817301', 'Swiss RE International SE', 20100901, NULL, false, true, 20100901, true),
   ('817302', 'Swiss RE International SE (selectie)', 20120401, 20120901, false, true, null, false),
   ('817401', 'Onderlinge ''s-Gravenhage Uitkeringen SUI', 20100901, NULL, false, true, 20100901, true),
   ('817501', 'Algemene Levensherverzekering Maatschappij', 20100901, 20170401, false, true, 20100901, true),
   ('817502', 'Algemene Levensherverzekering Maatschappij (selectie)', 20120401, 20120901, false, true, null, false),
   ('817701', 'TVM volmachten', 20111001, 20160201, false, true, null, false),
   ('817801', 'Kettlitz Wulfse Volmachten B.V. namens AEGON Schadeverzekeringen N.V.', 20121101, NULL, false, true, null, false),
   ('817901', 'Conservatrix Levensverzekeringen N.V.', 20121101, NULL, false, true, 20121101, true),
   ('818001', 'Elips Life AG', 20130101, NULL, false, true, 20130101, true),
   ('818101', 'Brand New Day Premiepensioeninstelling NV', 20130201, NULL, false, true, 20130201, true),
   ('818201', 'Stichting Rabo PGGM Premiepensioeninstelling', 20130201, NULL, false, true, 20130201, true),
   ('818301', 'Nationale-Nederlanden Premium Pension Institution B.V.', 20130201, NULL, false, true, 20130201, true),
   ('818401', 'BeFrank Premiepensioeninstelling N.V.', 20130301, NULL, false, true, 20130301, true),
   ('818501', 'Generali schadeverzekering maatschappij', 20130301, NULL, false, true, null, false),
   ('818601', 'Stichting Premiepensioeninstelling Robeco', 20130501, 20170501, false, true, 20130501, true),
   ('818701', 'Stichting Pensioenfonds voor Roeiers in het Rotterdamse Havengebied', 20130501, NULL, false, true, 20130501, true),
   ('818801', 'Stichting Pensioenfonds Boskalis', 20130601, 20150101, false, true, 20130601, true),
   ('818901', 'AEGON Premiepensioeninstelling B.V.', 20130801, NULL, false, true, 20130801, true),
   ('819001', 'Stichting Pensioenfonds Flexsecurity', 20131101, NULL, false, true, 20131101, true),
   ('819101', 'InAdmin N.V. Premiepensioeninstelling', 20150101, NULL, false, true, 20150101, true),
   ('850001', 'Stichting Interkerkelijke Ledenadministratie', 19941001, NULL, false, true, 19941001, true),
   ('850002', 'Stichting Interkerkelijke Ledenadministratie (2)', 20010401, 20020301, false, true, 20010401, true),
   ('850003', 'Stichting Interkerkelijke Ledenadministratie (selectie)', 20121101, NULL, false, true, null, false),
   ('852018', 'Uitvoeringsinstituut Werknemersverzekeringen (UWV)', 19920501, NULL, false, true, 19920501, true),
   ('852019', 'Gemeenschappelijk Administratiekantoor (GAK) Nederland (2)', 19980701, 19990301, false, true, 19980701, true),
   ('852028', 'Uitvoeringsinstituut Werknemersverzekeringen (UWV) (selectie)', 20091101, 20120901, false, true, null, false),
   ('852103', 'SVB/AKW (verzekerden)', 19950401, NULL, false, true, 19950401, true),
   ('852104', 'SVB/AKW (kind)', 19950401, NULL, false, true, 19950401, true),
   ('852105', 'SVB/Remigratieregeling', 19950201, 20001201, false, true, 19950201, true),
   ('852106', 'SVB/FVP', 19950801, 20141001, false, true, 19950801, true),
   ('852107', 'SVB/ANW', 20031201, 20100901, false, true, 20031201, true),
   ('852108', 'SVB/VVA/AOW-ANW (selectie)', 20090501, 20120901, false, true, null, false),
   ('852201', 'UWV-GUO-Uitvoeringsinstelling', 19920501, 20060601, false, true, 19920501, true),
   ('852301', 'SVB/Bureau voor Duitse Zaken', 19921101, 20110301, false, true, 19921101, true),
   ('852401', 'Cadans (BVG)', 19930315, 20060601, false, true, 19930315, true),
   ('852501', 'Cordares/Pensioenen', 19931201, 20150501, false, true, 19931201, true),
   ('852502', 'Cordares/Nabestaanden', 19960101, 20150501, false, true, 19960101, true),
   ('852503', 'UWV Bouwnijverheid', 20021201, 20060601, false, true, 20021201, true),
   ('852504', 'Cordares/Pensioenen (selectie)', 20101001, 20120901, false, true, null, false),
   ('852505', 'Cordares/Nabestaanden (selectie)', 20101001, 20120901, false, true, null, false),
   ('852602', 'Stichting Fondsenbeheer Waterbouw (selectie)', 20120101, 20120901, false, true, null, false),
   ('852701', 'A&O services', 19940701, 20150901, false, true, 19940701, true),
   ('852702', 'A&O services (selectie)', 20120401, 20120901, false, true, null, false),
   ('852801', 'Cadans (DETAM)', 19940501, 20060601, false, true, 19940501, true),
   ('852901', 'Bedrijfsvereniging van het Bakkersbedrijf', 19940701, 19941101, false, true, 19940701, true),
   ('853001', 'Intergemeentelijk Samenwerkingsverband Werk en Inkomen (ISWI) Ulft', 20140901, 20160501, false, true, 20140901, true),
   ('853201', 'KPMG - Wachtgeldregeling', 20090301, NULL, false, true, 20090301, true),
   ('870101', 'SPF Beheer', 19930315, NULL, false, true, 19930315, true),
   ('870201', 'Rijksdienst voor het Wegverkeer', 19951101, NULL, false, true, 19951101, true),
   ('870301', 'Stichting Waarborgfonds Motorverkeer', 19960501, 20170201, false, true, 19960501, true),
   ('870401', 'MvI&M/Inspectie Leefomgeving en Transport', 20140601, NULL, false, true, 20140601, true),
   ('870501', 'NS Reizigers', 20010401, NULL, false, true, null, false),
   ('870601', 'MvVW/Inspectie Verkeer en Waterstaat/Divers vervoer', 20020901, 20060601, false, true, 20020901, true),
   ('870701', 'HTM', 20020601, NULL, false, true, 20020601, true),
   ('870801', 'Connexxion Openbaar Vervoer NV', 20130201, NULL, false, true, null, false),
   ('870901', 'MvV&W/Centraal Bureau Rijvaardigheidsbewijzen (CBR)', 20030901, 20060601, false, true, 20030901, true),
   ('871001', 'RET', 20080601, 20160601, false, true, 20080601, true),
   ('871101', 'GVB', 20080301, NULL, false, true, null, false),
   ('871201', 'OV-bureau Groningen Drenthe', 20110701, NULL, false, true, null, false),
   ('871401', 'Qbuzz Openbaar Vervoer', 20140601, NULL, false, true, null, false),
   ('880101', 'APG Algemene Pensioen Groep N.V.', 20050901, NULL, false, true, 20050901, true),
   ('880102', 'APG Algemene Pensioen Groep N.V. (selectie)', 20120401, 20120901, false, true, null, false),
   ('880201', 'Loyalis verzekeringen', 20090601, NULL, false, true, 20090601, true),
   ('880202', 'Loyalis verzekeringen (selectie)', 20120401, 20120901, false, true, null, false),
   ('890001', 'MvDef/Dienstplichtzaken en Veteranen Registratie Systeem (VRS)', 20140101, NULL, false, true, 20140101, true),
   ('890101', 'USZO Defensie', 19931201, 20061201, false, true, 19931201, true),
   ('890102', 'MvDef/Dienst Militaire Pensioenen', 19950201, 20020301, false, true, 19950201, true),
   ('890202', 'Dienst Uitvoering Onderwijs - DUO (2)', 20060301, NULL, false, true, 20060301, true),
   ('890203', 'Dienst Uitvoering Onderwijs (DUO)/Inning en Incasso', 20140301, NULL, false, true, null, false),
   ('890301', 'Koninklijke Notariële Beroepsorganisatie', 19951101, NULL, false, true, null, false),
   ('890302', 'Koninklijke Notariële Beroepsorganisatie/Centraal Testamenten Register', 20061201, NULL, false, true, null, false),
   ('890303', 'Notaris A.J. van der Bijl', 20100301, 20101201, false, true, null, false),
   ('890401', 'Gerechtsdeurwaarderskantoor Van der Hoeden/Mulder', 19960201, 20081201, false, true, 19960201, true),
   ('890501', 'Stichting Netwerk Gerechtsdeurwaarders', 19951101, NULL, false, true, null, false),
   ('890601', 'MvEL&I / Algemene Inspectiedienst (AID) (1)', 19960201, NULL, false, true, null, false),
   ('890602', 'MvEL&I / Algemene Inspectiedienst (AID) (2)', 19960201, NULL, false, true, null, false),
   ('890701', 'Oordijk & Partners B.V.', 19970801, 20080901, false, true, 19970801, true),
   ('890801', 'Gerechtsdeurwaarderskantoor De Klerk & Vis B.V.', 19970801, 20070601, false, true, 19970801, true),
   ('890901', 'Samenwerkingsorgaan Westfriesland/CAW', 19971101, 20040901, false, true, 19971101, true),
   ('891001', 'Janssen & Janssen CS Gerechtsdeurwaarders', 20050901, 20080601, false, true, 20050901, true),
   ('891101', 'Deurwaarders Software Services (DWSS)', 19991201, 20070901, false, true, 19991201, true),
   ('891201', 'MvEL&I/REBUS', 19980801, NULL, false, true, 19980801, true),
   ('891202', 'MvEL&I/REBUS (selectie)', 20120601, 20120901, false, true, null, false),
   ('891301', 'Intergemeentelijk Orgaan Rivierenland/AVRI', 19981101, 20070901, false, true, 19981101, true),
   ('891501', 'Gerechtsdeurwaarder Touber, van der Velden & Kassies', 20030901, 20060601, false, true, 20030901, true),
   ('891601', 'Basisregister (1)', 20011201, 20161008, false, true, 20011201, true),
   ('891602', 'Basisregister (2)', 20011201, 20161008, false, true, 20011201, true),
   ('254802', 'Waterschap Brabantse Delta (Sawa)', 20150201, NULL, false, true, null, false),
   ('255002', 'MvI&M/Rijkswaterstaat/Vergunning en Handhaving (SaWa)', 20150201, NULL, false, true, null, false),
   ('400120', 'CBS11: Toerismestatistieken Caribisch Nederland (BES)', 20150201, NULL, false, true, null, false),
   ('510220', 'Afnemer Papendrecht', 20150201, NULL, false, true, null, false),
   ('611103', 'GGD IJsselland (OGGZ)', 20150201, NULL, false, true, null, false),
   ('651002', 'Het Inlichtingenbureau/Woonplaatsbeginsel Jeugdwet', 20150201, NULL, false, true, null, false),
   ('607408', 'Universiteit Maastricht/MEFAB (Maastricht Essential Fatty Acid Birth)-cohort', 20150301, NULL, false, true, null, false),
   ('616901', 'Veilig Thuis Rotterdam-Rijnmond', 20150301, NULL, false, true, null, false),
   ('617001', 'Regiecentrum Bescherming en Veiligheid-Veilig Thuis Friesland', 20150301, NULL, false, true, null, false),
   ('617201', 'Veilig Thuis Amsterdam-Amstelland', 20150301, NULL, false, true, null, false),
   ('617301', 'Veilig Thuis Brabant-Noordoost', 20150301, NULL, false, true, null, false),
   ('617401', 'Veilig Thuis Drenthe', 20150301, NULL, false, true, null, false),
   ('617501', 'Veilig Thuis Gelderland-Midden', 20150301, NULL, false, true, null, false),
   ('617601', 'Veilig Thuis Gelderland-Zuid', 20150301, NULL, false, true, null, false),
   ('617701', 'Veilig Thuis Gooi en Vechtstreek', 20150301, NULL, false, true, null, false),
   ('617801', 'Veilig Thuis Groningen', 20150301, NULL, false, true, null, false),
   ('617901', 'Veilig Thuis Haaglanden', 20150301, NULL, false, true, null, false),
   ('618001', 'Veilig Thuis IJsselland', 20150301, NULL, false, true, null, false),
   ('618101', 'Veilig Thuis Midden-Brabant', 20150301, NULL, false, true, null, false),
   ('618201', 'Veilig Thuis Noord- en Midden-Limburg', 20150301, NULL, false, true, null, false),
   ('618301', 'Veilig Thuis Noord- en Oost-Gelderland', 20150301, NULL, false, true, null, false),
   ('618401', 'Veilig Thuis GGD Hollands Noorden', 20150301, NULL, false, true, null, false),
   ('618501', 'Veilig Thuis Utrecht', 20150301, NULL, false, true, null, false),
   ('618601', 'Veilig Thuis GGD Zaanstreek-Waterland', 20150301, NULL, false, true, null, false),
   ('618701', 'Veilig Thuis Zeeland', 20150301, NULL, false, true, null, false),
   ('618801', 'Veilig Thuis Zuid-Holland Zuid', 20150301, NULL, false, true, null, false),
   ('618901', 'Veilig Thuis Brabant-Zuidoost', 20150301, NULL, false, true, null, false),
   ('891701', 'Rijksdienst voor Identiteitsgegevens/Register Paspoortsignaleringen (RPS)', 20150301, NULL, false, true, null, false),
   ('999903', 'Rijksdienst voor Identiteitsgegevens/GBA-Verstrekkingsvoorziening (test)', 20150301, NULL, false, true, 20150301, true),
   ('999904', 'Rijksdienst voor Identiteitsgegevens', 20150301, NULL, false, true, 20150301, true),
   ('251102', 'Hoogheemraadschap Hollands Noorderkwartier (SaWa)', 20150401, NULL, false, true, null, false),
   ('413301', 'Sociale Werkvoorziening WerkSaam Westfriesland', 20150401, NULL, false, true, 20150401, true),
   ('607203', 'UMC Groningen/TRAILS (Tracking Adolescents'' Individual Lives Survey)-onderzoek', 20150401, NULL, false, true, null, false),
   ('617101', 'Veilig Thuis West-Brabant', 20150401, NULL, false, true, null, false),
   ('619001', 'Veilig Thuis Twente', 20150401, NULL, false, true, null, false),
   ('619101', 'Veilig Thuis Zuid-Limburg', 20150401, NULL, false, true, null, false),
   ('619201', 'Maatschappelijke Zorg en Veilig Thuis Hollands Midden', 20150401, NULL, false, true, null, false),
   ('619301', 'Veilig Thuis Flevoland', 20150401, NULL, false, true, null, false),
   ('619401', 'Veilig Thuis Kennemerland', 20150401, NULL, false, true, null, false),
   ('650201', 'Waarborgfonds Eigen Woningen/Nationale Hypotheek Garantie', 20150401, NULL, false, true, 20150401, true),
   ('652801', 'Omgevingsdienst Midden-Holland (BSBm: Bestuurlijke Strafbeschikking milieu)', 20150401, NULL, false, true, null, false),
   ('803101', 'Pensioenfonds Unilever', 20150401, NULL, false, true, 20150401, true),
   ('607409', 'Universiteit Maastricht/MC (Microscopische colitis)-studie', 20150601, NULL, false, true, null, false),
   ('451002', 'Kiesraad (raadgevend referendum)', 20150701, NULL, false, true, null, false),
   ('510273', 'Afnemer De Fryske Marren', 20150701, NULL, false, true, 20150701, true),
   ('616902', 'Stichting Jeugdbescherming Rotterdam Rijnmond', 20150701, NULL, false, true, 20150701, true),
   ('617002', 'Stichting Regiecentrum Bescherming en Veiligheid', 20150701, NULL, false, true, 20150701, true),
   ('617202', 'Stichting Jeugdbescherming Regio Amsterdam', 20150701, NULL, false, true, 20150701, true),
   ('617302', 'Stichting Bureau Jeugdzorg Noord-Brabant', 20150701, NULL, false, true, 20150701, true),
   ('617402', 'Stichting Jeugdbescherming Noord/Drenthe', 20150701, NULL, false, true, 20150701, true),
   ('617502', 'Stichting Jeugdbescherming Gelderland', 20150701, NULL, false, true, 20150701, true),
   ('617802', 'Stichting Jeugdbescherming Noord/Groningen', 20150701, NULL, false, true, 20150701, true),
   ('617902', 'Stichting Jeugdbescherming west', 20150701, NULL, false, true, 20150701, true),
   ('618002', 'Stichting Jeugdbescherming Overijssel', 20150701, NULL, false, true, 20150701, true),
   ('618202', 'Stichting Bureau Jeugdzorg Limburg', 20150701, NULL, false, true, 20150701, true),
   ('618502', 'Stichting Samen Veilig Midden-Nederland', 20150701, NULL, false, true, 20150701, true),
   ('618702', 'Stichting Intervence', 20150701, NULL, false, true, 20150701, true),
   ('619402', 'Stichting De Jeugd- & Gezinsbeschermers', 20150701, NULL, false, true, 20150701, true),
   ('619502', 'Stichting William Schrikker Groep', 20150701, NULL, false, true, 20150701, true),
   ('619602', 'Stichting Gereformeerde Jeugdbescherming', 20150701, NULL, false, true, 20150701, true),
   ('653401', 'Omgevingsdienst Brabant-Noord (BSBm: Bestuurlijke Strafbeschikking milieu)', 20150701, NULL, false, true, null, false),
   ('852601', 'Fondsenbeheer Waterbouw B.V.', 20150701, NULL, false, true, 20150701, true),
   ('890205', 'Ministerie van Financien/DUO/register beroepskwalificaties WFT', 20150701, NULL, false, true, null, false),
   ('082802', 'Gemeente Oss (2)', 20150724, NULL, false, true, null, false),
   ('085102', 'Gemeente Steenbergen (2)', 20150801, NULL, false, true, null, false),
   ('414801', 'Intergemeentelijke Sociale Dienst Werkplein Hart van West-Brabant', 20150801, NULL, false, true, 20150801, true),
   ('074802', 'Gemeente Bergen op Zoom (2)', 20150901, NULL, false, true, null, false),
   ('253702', 'Waterschap Noorderzijlvest (SaWa)', 20150901, NULL, false, true, null, false),
   ('451201', 'MvBZK/Logius/Pilot voor Remote Document Authentication (RDA)', 20150901, NULL, false, true, null, false),
   ('807101', 'Stichting Pensioenfonds HaskoningDHV', 20150901, NULL, false, true, 20150901, true),
   ('815201', 'Dion Pensioen Services B.V.', 20150901, NULL, false, true, 20150901, true),
   ('819102', 'InAdmin N.V.', 20150901, NULL, false, true, 20150901, true),
   ('413201', 'Werkvoorzieningsschap Zaanstreek-Waterland (Baanstede)', 20151001, NULL, false, true, 20151001, true),
   ('603801', 'Zilveren Kruis Zorgverzekeringen', 20151001, NULL, false, true, 20151001, true),
   ('701801', 'Autoriteit Consument en Markt', 20151001, NULL, false, true, null, false),
   ('451401', 'LAA Raadpleging voor gemeenten', 20151012, 20151014, false, true, null, false),
   ('099502', 'Gemeente Lelystad (2)', 20151101, NULL, false, true, null, false),
   ('255201', 'Gemeenschappelijke regeling Tribuut belastingsamenwerking', 20151101, NULL, false, true, 20151101, true),
   ('414901', 'Gemeenschappelijke regeling Werkzaak Rivierenland', 20151101, NULL, false, true, 20151101, true),
   ('414902', 'Gemeenschappelijke regeling Sociale Werkvoorziening Werkzaak Rivierenland', 20151101, NULL, false, true, 20151101, true),
   ('451501', 'MvBZK/MijnOverheid/Berichtenbox', 20151101, NULL, false, true, null, false),
   ('610001', 'Nederlands Kanker Instituut/Hebon-studie', 20151101, NULL, false, true, null, false),
   ('057602', 'Gemeente Noordwijkerhout (2)', 20151201, NULL, false, true, null, false),
   ('201201', 'Dienst Gezondheid & Jeugd Zuid-Holland Zuid (Leerplicht)', 20151201, NULL, false, true, 20151201, true),
   ('250901', 'Belastingsamenwerking Gouwe-Rijnland', 20151201, NULL, false, true, 20151201, true),
   ('451301', 'MvBZK/BSN-Koppelregister', 20151201, NULL, false, true, null, false),
   ('610902', 'Dienst Gezondheid & Jeugd Zuid-Holland Zuid (JGZ/overig)', 20151201, NULL, false, true, 20151201, true),
   ('610904', 'Dienst Gezondheid & Jeugd Zuid-Holland Zuid (OGGZ/Kinderopvang)', 20151201, NULL, false, true, null, false),
   ('630301', 'Rijksinstituut voor Volksgezondheid en Milieu (RIVM)/Prenatale screening', 20151201, NULL, false, true, 20151201, true),
   ('700402', 'Algemene Inlichtingen- en Veiligheidsdienst (AIVD)/e-Formulieren', 20151201, NULL, false, true, null, false),
   ('805003', 'Syntrus Achmea Pensioenbeheer N.V. PPI', 20151201, 20170201, false, true, 20151201, true),
   ('400112', 'CBS2: Vestiging en Verhuizing', 20160101, NULL, false, true, 20160101, true),
   ('510013', 'Afnemer Berg en Dal', 20160101, NULL, false, true, 20160101, true),
   ('510320', 'Afnemer Gooise Meren', 20160101, NULL, false, true, 20160101, true),
   ('610002', 'Nederlands Kanker Instituut /Nightingale-studie', 20160101, NULL, false, true, null, false),
   ('615701', 'Zilveren Kruis Zorgkantoor', 20160101, NULL, false, true, 20160101, true),
   ('619702', 'Stichting Leger des Heils Jeugdbescherming en Reclassering', 20160101, NULL, false, true, 20160101, true),
   ('805101', 'Timeos B.V.', 20160101, NULL, false, true, 20160101, true),
   ('819201', 'Klaverblad Levensverzekering N.V.', 20160101, NULL, false, true, 20160101, true),
   ('253202', 'Waterschap Zuiderzeeland (SaWa)', 20160201, NULL, false, true, null, false),
   ('415001', 'Gemeenschappelijke regeling Uitvoeringsorganisatie Laborijn', 20160201, NULL, false, true, 20160201, true),
   ('415002', 'Sociale Werkvoorziening Uitvoeringsorganisatie Laborijn', 20160201, NULL, false, true, 20160201, true),
   ('450901', 'MvBZK/BDF/Verkiezingen en referenda', 20160201, NULL, false, true, null, false),
   ('451003', 'MvBZK/RvIG/Uitsluitingen kiesrecht', 20160201, 20160501, false, true, null, false),
   ('603701', 'CAK/Bijzondere Zorgkosten', 20160201, NULL, false, true, 20160201, true),
   ('703501', 'MvVenJ/Nationaal Coördinator Terrorismebestrijding en Veiligheid (NCTV)', 20160201, NULL, false, true, null, false),
   ('760001', 'Arriva Personenvervoer Nederland', 20160201, NULL, false, true, null, false),
   ('810401', '216 Accountants B.V.', 20160201, NULL, false, true, 20160201, true),
   ('703401', 'Min. voor Wonen en Rijksdienst/Rijksdienst voor Ondernemend Nederland (RVO)', 20160301, NULL, false, true, null, false),
   ('254901', 'Waterschap Hollandse Delta', 20160401, NULL, false, true, null, false),
   ('410701', 'Gemeenschappelijke regeling Werk en Inkomen Hoeksche Waard', 20160401, NULL, false, true, 20160401, true),
   ('410702', 'Sociale Werkvoorziening Werk en Inkomen Hoeksche Waard', 20160401, NULL, false, true, 20160401, true),
   ('415101', 'Gemeenschappelijke regeling De Bevelanden', 20160401, NULL, false, true, 20160401, true),
   ('802601', 'Generali Nederland', 20160401, NULL, false, true, 20160401, true),
   ('871301', 'Min. van EZ/Agentschap Telecom/BOA-taak', 20160401, NULL, false, true, null, false),
   ('410002', 'Regionale Sociale Dienst Pentasz Mergelland (Suwinet)', 20160501, 20160901, false, true, null, false),
   ('410602', 'Intergemeentelijke Sociale Dienst OptimISD (Suwinet)', 20160501, 20170101, false, true, null, false),
   ('415201', 'Gemeenschappelijke regeling Ferm Werk', 20160501, NULL, false, true, 20160501, true),
   ('415202', 'Sociale Werkvoorziening Ferm Werk', 20160501, NULL, false, true, 20160501, true),
   ('607302', 'Leids Universitair Medisch Centrum/Lang Leven-studie', 20160501, NULL, false, true, null, false),
   ('616601', 'Erasmus MC/Afdeling Maatschappelijke Gezondheidszorg/ROBINSCA-studie', 20160501, NULL, false, true, 20160501, true),
   ('700801', 'Min. van EZ/Agentschap Telecom/Vergunning en Handhaving', 20160501, NULL, false, true, 20160501, true),
   ('816601', 'Appel Pensioenuitvoering', 20160501, NULL, false, true, 20160501, true),
   ('819301', 'Pensioenuitvoerder Ecolab', 20160501, NULL, false, true, 20160501, true),
   ('871501', 'Syntus Openbaar Vervoer', 20160501, NULL, false, true, null, false),
   ('604701', 'Medisch Centrum Haaglanden en Bronovo-Nebo', 20160601, NULL, false, true, null, false),
   ('999905', 'Bestandsvergelijking PIVA - GBA-V', 20160601, NULL, false, true, 20160601, true),
   ('250701', 'Regionale Belasting Groep (1)', 20160701, NULL, false, true, 20160701, true),
   ('250702', 'Regionale Belasting Groep (2)', 20160701, NULL, false, true, 20160701, true),
   ('871002', 'Rotterdamse Elektrische Tram (RET)', 20160701, NULL, false, true, null, false),
   ('252902', 'Waterschap Rivierenland (SaWa)', 20160801, NULL, false, true, null, false),
   ('651301', 'Regionale Uitvoeringsdienst Drenthe (BSBm: Bestuurlijke Strafbeschikking milieu)', 20160801, NULL, false, true, null, false),
   ('451402', 'Min. van BZK/RvIG/Aanpak adreskwaliteit/Raadpleging voor gemeenten (op BSN)', 20160815, NULL, false, true, null, false),
   ('451403', 'Min. van BZK/RvIG/Aanpak adreskwaliteit/Raadpleging voor gemeenten (op A-nummer)', 20160815, NULL, false, true, null, false),
   ('607303', 'Leids Universitair Medisch Centrum (LUMC)/Genes, Germs and Resources-onderzoek', 20160901, NULL, false, true, null, false),
   ('171902', 'Gemeente Drimmelen (2)', 20161001, NULL, false, true, null, false),
   ('653402', 'Uitvoerders omgevingsrecht via Omgevingsdienst Brabant-Noord', 20161001, NULL, false, true, 20161001, true),
   ('891401', 'Min. van BZ/Directie Consulaire Zaken en Protocol', 20161001, NULL, false, true, 20161001, true),
   ('300028', 'Verstrekkingsvoorziening - Daft (Data Analyzing Fingerprint Tool)', 20161008, NULL, false, true, 20161008, true),
   ('300032', 'Terugmeldvoorziening', 20161008, NULL, false, true, 20161008, true),
   ('891801', 'Min. van BZK/RvIG/Basisregister reisdocumenten/Van rechtswege vervallen-Vermist', 20161008, NULL, false, true, 20161008, true),
   ('891802', 'Min. van BZK/RvIG/Basisregister reisdocumenten/Houder overleden', 20161008, NULL, false, true, 20161008, true),
   ('891803', 'Min. van BZK/RvIG/Basisregister reisdocumenten/A-nummerwijziging', 20161008, NULL, false, true, 20161008, true),
   ('255301', 'Waterschap Fryslân/Beheertaken, handhaving en opsporing', 20161101, NULL, false, true, null, false),
   ('819401', 'Pensioenuitvoerder voor de Handel in Bouwmaterialen (HiBiN)', 20161101, NULL, false, true, 20161101, true),
   ('055602', 'Gemeente Maassluis (2)', 20161201, NULL, false, true, null, false),
   ('173102', 'Gemeente Midden-Drenthe (2)', 20161201, NULL, false, true, null, false),
   ('255401', 'Gemeenschappelijke regeling Meerinzicht belastingsamenwerking', 20161201, NULL, false, true, 20161201, true),
   ('410201', 'Samenwerkingsverband Avres/Sociale voorzieningen', 20161201, NULL, false, true, 20161201, true),
   ('410202', 'Samenwerkingsverband Avres via Suwinet/Werk en inkomen', 20161201, NULL, false, true, null, false),
   ('702401', 'Kansspelautoriteit', 20161201, NULL, false, true, null, false),
   ('451701', 'Gemeente ''s-Gravenhage/Landelijke Taken/Kiezers buiten Nederland (1)', 20170101, NULL, false, true, 20170101, true),
   ('451702', 'Gemeente ''s-Gravenhage/Landelijke Taken/Kiezers buiten Nederland (2)', 20170101, NULL, false, true, 20170101, true),
   ('510323', 'Afnemer Meierijstad', 20170101, NULL, false, true, 20170101, true),
   ('609801', 'CAK/Burgerregelingen', 20170101, NULL, false, true, 20170101, true),
   ('609802', 'CAK via Centraal Justitieel Incassobureau (CJIB)/Inning en incasso', 20170101, NULL, false, true, null, false),
   ('200901', 'Samenwerkingsverband Regio Gooi en Vechtstreek/Leerlingzaken', 20170201, NULL, false, true, 20170201, true),
   ('250001', 'Min. van Financiën/Belastingdienst', 20170201, NULL, false, true, 20170201, true),
   ('400121', 'Centraal Bureau voor de Statistiek (CBS)/Pilot voor selectie-invoerbestand', 20170201, NULL, false, true, null, false),
   ('619801', 'Instelling voor geestelijke gezondheidszorg Pro Persona', 20170201, NULL, false, true, 20170201, true),
   ('653901', 'Omgevingsdienst Groningen/Bestuurlijke strafbeschikking milieu', 20170201, NULL, false, true, null, false),
   ('255501', 'Waterschap Limburg', 20170301, NULL, false, true, null, false),
   ('255502', 'Waterschap Limburg via Centraal aansluitpunt Min. van IenM', 20170301, NULL, false, true, null, false),
   ('606801', 'Ziekenhuis Zuyderland Medisch Centrum', 20170301, NULL, false, true, null, false),
   ('255601', 'Waterschap Drents Overijsselse Delta/Beheertaken, handhaving en opsporing', 20170401, NULL, false, true, null, false),
   ('411001', 'Samenwerkingsverband Werkplein Drentsche Aa/Sociale voorzieningen', 20170401, NULL, false, true, 20170401, true),
   ('411002', 'Samenwerkingsverband Werkplein Drentsche Aa via Suwinet/Werk en inkomen', 20170401, NULL, false, true, null, false),
   ('703502', 'Min. van VenJ/Nationaal Coördinator Terrorismebestrijding en Veiligheid (NCTV)', 20170401, NULL, false, true, null, false),
   ('890201', 'Min. van OCW/Dienst Uitvoering Onderwijs (DUO)', 20170401, NULL, false, true, 20170401, true),
   ('410102', 'Samenwerkingsverband Noardwest-Fryslân via Suwinet/Werk en inkomen', 20170501, NULL, false, true, null, false),
   ('410402', 'Samenwerkingsverband Baanbrekers via Suwinet/Werk en inkomen', 20170501, NULL, false, true, null, false),
   ('410703', 'Samenwerkingsverband Hoeksche Waard via Suwinet/Werk en inkomen', 20170501, NULL, false, true, null, false),
   ('410902', 'Samenwerkingsverband Noordenkwartier via Suwinet/Werk en inkomen', 20170501, NULL, false, true, null, false),
   ('411102', 'Samenwerkingsverband Kompas via Suwinet/Werk en inkomen', 20170501, NULL, false, true, null, false),
   ('411202', 'Samenwerkingsverband Orionis Walcheren via Suwinet/Werk en inkomen', 20170501, NULL, false, true, null, false),
   ('411302', 'Samenwerkingsverband Noordoost via Suwinet/Werk en inkomen', 20170501, NULL, false, true, null, false),
   ('411602', 'Samenwerkingsverband Drechtsteden via Suwinet/Werk en inkomen', 20170501, NULL, false, true, null, false),
   ('411802', 'Samenwerkingsverband Brunssum Onderbanken Landgraaf via Suwinet/Werk en inkomen', 20170501, NULL, false, true, null, false),
   ('411902', 'Samenwerkingsverband Steenwijkerland en Westerveld via Suwinet/Werk en inkomen', 20170501, NULL, false, true, null, false),
   ('412002', 'Samenwerkingsverband Kromme Rijn Heuvelrug via Suwinet/Werk en inkomen', 20170501, NULL, false, true, null, false),
   ('412102', 'Samenwerkingsverband Bollenstreek via Suwinet/Werk en inkomen', 20170501, NULL, false, true, null, false),
   ('412601', 'Gemeentelijke sociale diensten via Suwinet/Werk en inkomen', 20170501, NULL, false, true, null, false),
   ('412702', 'Samenwerkingsverband Veluwerand via Suwinet/Werk en inkomen', 20170501, NULL, false, true, null, false),
   ('414302', 'Samenwerkingsverband Lekstroom via Suwinet/Werk en inkomen', 20170501, NULL, false, true, null, false),
   ('414702', 'Samenwerkingsverband West-Friesland via Suwinet/Werk en inkomen', 20170501, NULL, false, true, null, false),
   ('414802', 'Samenwerkingsverband Hart van West-Brabant via Suwinet/Werk en inkomen', 20170501, NULL, false, true, null, false),
   ('414903', 'Samenwerkingsverband Rivierenland via Suwinet/Werk en inkomen', 20170501, NULL, false, true, null, false),
   ('415003', 'Samenwerkingsverband Laborijn via Suwinet/Werk en inkomen', 20170501, NULL, false, true, null, false),
   ('415102', 'Samenwerkingsverband De Bevelanden via Suwinet/Werk en inkomen', 20170501, NULL, false, true, null, false),
   ('415203', 'Samenwerkingsverband Ferm Werk via Suwinet/Werk en inkomen', 20170501, NULL, false, true, null, false),
   ('601202', 'Min. van VWS/RIVM/Entadministraties', 20170501, NULL, false, true, 20170501, true),
   ('607301', 'Academisch ziekenhuis Leids Universitair Medisch Centrum (LUMC)', 20170501, NULL, false, true, 20170501, true),
   ('619902', 'Jeugd Veilig Verder voor jeugdbescherming', 20170501, NULL, false, true, 20170501, true),
   ('652001', 'Omgevingsdienst Achterhoek/Bestuurlijke strafbeschikking milieu', 20170501, NULL, false, true, null, false),
   ('047903', 'Gemeente Zaanstad via MijnOverheid/Verhuisvoorziening', 20170601, NULL, false, true, null, false),
   ('079603', 'Gemeente ''s-Hertogenbosch via MijnOverheid/Verhuisvoorziening', 20170601, NULL, false, true, null, false),
   ('192702', 'Gemeente Molenwaard (2)', 20170601, NULL, false, true, null, false),
   ('250002', 'Min. van BZK/Logius/Overheidstoegangsvoorziening DigiD', 20170601, NULL, false, true, null, false),
   ('852102', 'Sociale Verzekeringsbank (SVB)/Basisadministratie Volksverzekeringen', 20170601, NULL, false, true, 20170601, true),
   ('853101', 'Min. van SZW/Inspectie SZW/Strafrechtelijke handhaving en opsporingstaken', 20170601, NULL, false, true, null, false),
   ('093503', 'Gemeente Maastricht via MijnOverheid/Verhuisvoorziening', 20170701, NULL, false, true, null, false),
   ('253101', 'Belastingkantoor Hefpunt/Waterschapbelastingen', 20170701, NULL, false, true, 20170701, true),
   ('254401', 'Belastingkantoor SaBeWa Zeeland/Gemeentelijke en waterschapsbelastingen', 20170701, NULL, false, true, 20170701, true),
   ('300020', 'Verstrekkingsvoorziening gemeenten (1)', 20170701, NULL, false, true, 20170701, true),
   ('300021', 'Verstrekkingsvoorziening gemeenten (2)', 20170701, NULL, false, true, 20170701, true),
   ('300022', 'Verstrekkingsvoorziening gemeenten (3)', 20170701, NULL, false, true, 20170701, true),
   ('300023', 'Verstrekkingsvoorziening gemeenten (4)', 20170701, NULL, false, true, 20170701, true),
   ('300025', 'Verstrekkingsvoorziening afnemers (1)', 20170701, NULL, false, true, 20170701, true),
   ('300026', 'Verstrekkingsvoorziening afnemers (2)', 20170701, NULL, false, true, 20170701, true),
   ('300027', 'Verstrekkingsvoorziening RNI', 20170701, NULL, false, true, 20170701, true),
   ('601201', 'Min. van VWS/RIVM/Onderzoek en crisissituaties', 20170701, NULL, false, true, null, false),
   ('602201', 'Min. van VWS/CIBG/Donorregister', 20170701, NULL, false, true, 20170701, true),
   ('651501', 'Omgevingsdienst IJsselland/Bestuurlijke strafbeschikking milieu', 20170701, NULL, false, true, null, false),
   ('700104', 'Min. van VenJ/Centraal Justitieel Incassobureau (CJIB) (1)', 20170701, NULL, false, true, 20170701, true),
   ('700111', 'Min. van VenJ/Centraal Justitieel Incassobureau (CJIB) (2)', 20170701, NULL, false, true, null, false),
   ('701301', 'Min. van VenJ/Justis/Landelijk Bureau Bibob', 20170701, NULL, false, true, null, false),
   ('817601', 'Pensioenuitvoerder Scildon', 20170701, NULL, false, true, 20170701, true),
   ('819601', 'Pensioenuitvoerder Delta Lloyd', 20170701, NULL, false, true, 20170701, true),
   ('819701', 'Pensioenuitvoerder Delta Lloyd Algemeen Pensioenfonds', 20170701, NULL, false, true, 20170701, true);
INSERT INTO Kern.PartijRol (Partij, Rol, DatIngang, DatEinde, IndAG) SELECT ID, 1, DatIngang, DatEinde, True FROM Kern.Partij WHERE ID >= 5000;
INSERT INTO Kern.Partij(Code, Naam, DatIngang, DatEinde, IndVerstrBeperkingMogelijk, IndAG) VALUES ('891603', 'Min. van BZK/RvIG (intern)/Kwaliteitscontrole Aanduiding vervallen reisdocument', 20161013, 20161201, false, true);
INSERT INTO Kern.Partij(Code, Naam, DatIngang, DatEinde, IndVerstrBeperkingMogelijk, IndAG) VALUES ('891702', 'Rijksdienst voor Identiteitsgegevens/RPS (Selectie)', 20160901, NULL, false, true);
INSERT INTO Kern.Partij(Code, Naam, DatIngang, DatEinde, IndVerstrBeperkingMogelijk, IndAG) VALUES ('895001', 'Openbaar lichaam Saba/Afstemming basisadministratie op BRP', 20160901, NULL, false, true);
INSERT INTO Kern.Partij(Code, Naam, DatIngang, DatEinde, IndVerstrBeperkingMogelijk, IndAG) VALUES ('999906', 'RvIG (selectie op A-nummer)', 20160808, NULL, false, true);
INSERT INTO Kern.PartijRol (Partij, Rol, DatIngang, DatEinde, IndAG) SELECT ID, 1, DatIngang, DatEinde, True FROM Kern.Partij P WHERE P.ID >= 5000 AND NOT EXISTS(SELECT * FROM Kern.PartijRol WHERE Partij = P.ID);


--------------------------------------------------------------------------------
-- Stamgegeven: Partij overig
--------------------------------------------------------------------------------
-- Handmatige SQL code

-- Zetten van de verstrekkingsbeperkingmogelijk?: dit wordt eerst op false gezet, en dan voor de enige partijen waarvoor het geldt, op true gezet.
-- Momenteel alleen SILA
UPDATE Kern.Partij SET
   IndVerstrBeperkingMogelijk = True
WHERE 
   Code IN ('850001');

-- Migratievoorziening (199902) een 'BRP partij' zodat deze mailbox onde BRP beheer valt
UPDATE Kern.Partij SET
   DatOvergangNaarBRP = DatIngang
WHERE
   Code IN ('199902');

-- IND (MvVenJ/IND (Vreemdelingen)) is een Bijhoudingsvoorstelorgaan.
INSERT INTO Kern.PartijRol (Partij, DatIngang, DatEinde, IndAG, Rol)
   SELECT ID, DatIngang, DatEinde, true, (SELECT ID FROM Kern.Rol WHERE Naam = 'Bijhoudingsvoorstelorgaan')
   FROM Kern.Partij P
   WHERE P.Code = '700302';


-- His_Partij opnemen
INSERT INTO Kern.His_Partij (Partij, TsReg, Naam, DatIngang, DatEinde, OIN, Srt, IndVerstrBeperkingMogelijk, DatOvergangNaarBRP)
   SELECT ID, Now(), Naam, DatIngang, DatEinde, OIN, Srt, IndVerstrBeperkingMogelijk, DatOvergangNaarBRP
   FROM Kern.Partij;

-- His_VrijBer opnemen
INSERT INTO Kern.His_PartijVrijBer (Partij, TsReg, DatIngangVrijBer)
   SELECT ID, Now(), DatIngangVrijBer
   FROM Kern.Partij
   WHERE IndAGVrijBer; 

-- His_PartijRol opnemen
INSERT INTO Kern.His_PartijRol (PartijRol, TsReg, DatIngang, DatEinde) 
   SELECT ID, Now(), DatIngang, DatEinde 
   FROM Kern.PartijRol;


-- ConvRNIDeelnemer
INSERT INTO Conv.ConvRniDeelnemer (ID, Rubr8811CodeRniDeelnemer, Partij) SELECT 1, '0101', ID FROM Kern.Partij P WHERE P.Code = '250001';
INSERT INTO Conv.ConvRniDeelnemer (ID, Rubr8811CodeRniDeelnemer, Partij) SELECT 2, '0000', ID FROM Kern.Partij P WHERE P.Code = '000000';
INSERT INTO Conv.ConvRniDeelnemer (ID, Rubr8811CodeRniDeelnemer, Partij) SELECT 3, '9999', ID FROM Kern.Partij P WHERE P.Code = '999999';
INSERT INTO Conv.ConvRniDeelnemer (ID, Rubr8811CodeRniDeelnemer, Partij) SELECT 4, '0601', ID FROM Kern.Partij P WHERE P.Code = '891401';
INSERT INTO Conv.ConvRniDeelnemer (ID, Rubr8811CodeRniDeelnemer, Partij) SELECT 5, '0201', ID FROM Kern.Partij P WHERE P.Code = '852102';
INSERT INTO Conv.ConvRniDeelnemer (ID, Rubr8811CodeRniDeelnemer, Partij) SELECT 6, '0301', ID FROM Kern.Partij P WHERE P.Code = '852018';
INSERT INTO Conv.ConvRniDeelnemer (ID, Rubr8811CodeRniDeelnemer, Partij) SELECT 7, '0401', ID FROM Kern.Partij P WHERE P.Code = '609801';



