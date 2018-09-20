--------------------------------------------------------------------------------
-- PostgreSQL SQL-DDL bestand voor Basisregistratie Personen (BRP)            --
-- PostgreSQL - Bericht Statische Stamgegevens                                --
--------------------------------------------------------------------------------
-- 
-- Gegenereerd door BRP Meta Register Release 44.
-- Gegenereerd op: dinsdag 24 nov 2015 13:59:27
-- 
--------------------------------------------------------------------------------

--------------------------------------------------------------------------------
-- Stamgegeven: Bijhoudingsresultaat
--------------------------------------------------------------------------------
INSERT INTO Ber.Bijhresultaat (ID, Naam, Oms) VALUES (1, 'Verwerkt', 'Bijhouding direct verwerkt in BRP');
INSERT INTO Ber.Bijhresultaat (ID, Naam, Oms) VALUES (2, 'Uitgesteld', 'Bijhouding in BRP uitgesteld in verband met fiattering andere partij');
INSERT INTO Ber.Bijhresultaat (ID, Naam, Oms) VALUES (3, 'Direct', 'Bijhouding direct verwerkt in BRP');
INSERT INTO Ber.Bijhresultaat (ID, Naam, Oms) VALUES (4, 'Conform plan', 'Bijhouding verwerkt conform plan');

--------------------------------------------------------------------------------
-- Stamgegeven: Bijhoudingssituatie
--------------------------------------------------------------------------------
INSERT INTO Ber.Bijhsituatie (ID, Naam, Oms) VALUES (1, 'Indiener is bijhoudingspartij', 'De persoon valt onder de bijhoudingsverantwoordelijkheid van de partij die het bijhoudingsvoorstel heeft ingezonden.');
INSERT INTO Ber.Bijhsituatie (ID, Naam, Oms) VALUES (2, 'Automatische fiat', 'De persoon valt onder de bijhoudingsverantwoordelijkheid van een partij die het voorstel automatisch wenst te fiatteren.');
INSERT INTO Ber.Bijhsituatie (ID, Naam, Oms) VALUES (3, 'Aanvullen en opnieuw indienen', 'Het bijhoudingsvoorstel is incompleet (ontbreken Anr/BSN) en kan daardoor niet verwerkt worden. De ontvanger dient het voorstel te completeren en opnieuw in te dienen.');
INSERT INTO Ber.Bijhsituatie (ID, Naam, Oms) VALUES (4, 'Opnieuw indienen', 'De persoon valt onder de bijhoudingsverantwoordelijkheid van een partij die het voorstel handmatig wenst te fiatteren of de partij wenst automatisch te fiatteren maar er is een fiatteringsuitzondering die van toepassing is op het bijhoudingsvoorstel.');
INSERT INTO Ber.Bijhsituatie (ID, Naam, Oms) VALUES (5, 'GBA', 'De persoon valt onder de bijhoudingsverantwoordelijkheid van een partij die valt onder het GBA-regime.');

--------------------------------------------------------------------------------
-- Stamgegeven: Historievorm
--------------------------------------------------------------------------------
INSERT INTO Ber.Historievorm (ID, Naam, Oms) VALUES (1, 'Geen', 'Geen historie; alleen actuele gegevens leveren');
INSERT INTO Ber.Historievorm (ID, Naam, Oms) VALUES (2, 'Materieel', 'De actuele gegevens en de materiele historie hiervan leveren');
INSERT INTO Ber.Historievorm (ID, Naam, Oms) VALUES (3, 'MaterieelFormeel', 'De actuele gegevens en zowel de materiele als de formele historie hiervan leveren');

--------------------------------------------------------------------------------
-- Stamgegeven: Richting
--------------------------------------------------------------------------------
INSERT INTO Ber.Richting (ID, Naam, Oms) VALUES (1, 'Ingaand', 'Naar de centrale voorzieningen van de BRP toe.');
INSERT INTO Ber.Richting (ID, Naam, Oms) VALUES (2, 'Uitgaand', 'Van de centrale voorzieningen van de BRP af.');

--------------------------------------------------------------------------------
-- Stamgegeven: Soort melding
--------------------------------------------------------------------------------
INSERT INTO Ber.SrtMelding (ID, Naam, Oms) VALUES (1, 'Geen', 'Geen meldingen aangetroffen');
INSERT INTO Ber.SrtMelding (ID, Naam, Oms) VALUES (2, 'Informatie', 'Informatieve melding; ter kennisname');
INSERT INTO Ber.SrtMelding (ID, Naam, Oms) VALUES (3, 'Waarschuwing', 'Waarschuwing aangetroffen; mogelijk herstelactie ondernemen');
INSERT INTO Ber.SrtMelding (ID, Naam, Oms) VALUES (4, 'Deblokkeerbaar', 'Foutieve situatie aangetroffen, melding deblokkeerbaar of herstelactie ondernemen');
INSERT INTO Ber.SrtMelding (ID, Naam, Oms) VALUES (5, 'Fout', 'Foutieve situatie aangetroffen; verwerking blokkeert');

--------------------------------------------------------------------------------
-- Stamgegeven: Soort synchronisatie
--------------------------------------------------------------------------------
INSERT INTO Ber.SrtSynchronisatie (ID, Naam, Oms) VALUES (1, 'Mutatiebericht', 'Synchronisatie gewijzigde gegevens in de vorm van een mutatiebericht (delta levering)');
INSERT INTO Ber.SrtSynchronisatie (ID, Naam, Oms) VALUES (2, 'Volledigbericht', 'Synchronisatie (gewijzigde) gegevens in de vorm van een volledigbericht met daarin de volledige persoonsgegevens');

--------------------------------------------------------------------------------
-- Stamgegeven: Verwerkingsresultaat
--------------------------------------------------------------------------------
INSERT INTO Ber.Verwerkingsresultaat (ID, Naam, Oms) VALUES (1, 'Geslaagd', 'Verwerking geslaagd');
INSERT INTO Ber.Verwerkingsresultaat (ID, Naam, Oms) VALUES (2, 'Foutief', 'Verwerking foutief; deblokkeerbare meldingen of fouten aanwezig');

--------------------------------------------------------------------------------
-- Stamgegeven: Verwerkingswijze
--------------------------------------------------------------------------------
INSERT INTO Ber.Verwerkingswijze (ID, Naam, Oms) VALUES (1, 'Bijhouding', 'Bijhouding ter definitieve verwerking aan BRP aangeboden');
INSERT INTO Ber.Verwerkingswijze (ID, Naam, Oms) VALUES (2, 'Prevalidatie', 'Bijhouding ter controle aan BRP aangeboden');

--------------------------------------------------------------------------------
-- Stamgegeven: Soort bericht
--------------------------------------------------------------------------------
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (1, 'bhg_afsRegistreerGeboorte', 0, 99991231, 1, 1, 'bhg_afsRegistreerGeboorte');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (2, 'bhg_afsRegistreerGeboorte_R', 0, 99991231, 1, 1, 'bhg_afsRegistreerGeboorte_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (3, 'bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap', 0, 99991231, 2, 1, 'bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (4, 'bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R', 0, 99991231, 2, 1, 'bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (5, 'bhg_vbaRegistreerVerhuizing', 0, 99991231, 3, 1, 'bhg_vbaRegistreerVerhuizing');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (6, 'bhg_vbaRegistreerVerhuizing_R', 0, 99991231, 3, 1, 'bhg_vbaRegistreerVerhuizing_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (7, 'bhg_vbaCorrigeerAdres', 0, 99991231, 3, 1, 'bhg_vbaCorrigeerAdres');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (8, 'bhg_vbaCorrigeerAdres_R', 0, 99991231, 3, 1, 'bhg_vbaCorrigeerAdres_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (9, 'bhg_ovlRegistreerOverlijden', 0, 99991231, 4, 1, 'bhg_ovlRegistreerOverlijden');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (10, 'bhg_ovlRegistreerOverlijden_R', 0, 99991231, 4, 1, 'bhg_ovlRegistreerOverlijden_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (11, 'bhg_bvgZoekPersoon', 0, 99991231, 5, 1, 'bhg_bvgZoekPersoon');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (12, 'bhg_bvgZoekPersoon_R', 0, 99991231, 5, 1, 'bhg_bvgZoekPersoon_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (13, 'bhg_bvgGeefDetailsPersoon', 0, 99991231, 5, 1, 'bhg_bvgGeefDetailsPersoon');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (14, 'bhg_bvgGeefDetailsPersoon_R', 0, 9991231, 5, 1, 'bhg_bvgGeefDetailsPersoon_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (15, 'bhg_bvgBepaalKandidaatVader', 0, 99991231, 5, 1, 'bhg_bvgBepaalKandidaatVader');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (16, 'bhg_bvgBepaalKandidaatVader_R', 0, 99991231, 5, 1, 'bhg_bvgBepaalKandidaatVader_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (17, 'bhg_bvgGeefPersonenOpAdresMetBetrokkenheden', 0, 9991231, 5, 1, 'bhg_bvgGeefPersonenOpAdresMetBetrokkenheden');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (18, 'bhg_bvgGeefPersonenOpAdresMetBetrokkenheden_R', 0, 99991231, 5, 1, 'bhg_bvgGeefPersonenOpAdresMetBetrokkenheden_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (19, 'bhg_afsRegistreerAdoptie', 0, 99991231, 1, 1, 'bhg_afsRegistreerAdoptie');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (20, 'bhg_afsRegistreerAdoptie_R', 0, 99991231, 1, 1, 'bhg_afsRegistreerAdoptie_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (21, 'bhg_afsActualiseerAfstamming', 0, 99991231, 1, 1, 'bhg_afsActualiseerAfstamming');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (22, 'bhg_afsActualiseerAfstamming_R', 0, 99991231, 1, 1, 'bhg_afsActualiseerAfstamming_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (23, 'lvg_synVerwerkPersoon', 0, 99991231, 8, 1, 'lvg_synVerwerkPersoon');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (24, 'bhg_afsRegistreerErkenning', 0, 99991231, 1, 1, 'bhg_afsRegistreerErkenning');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (25, 'bhg_afsRegistreerErkenning_R', 0, 99991231, 1, 1, 'bhg_afsRegistreerErkenning_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (26, 'bhg_afsCorrigeerAfstamming', 0, 99991231, 1, 1, 'bhg_afsCorrigeerAfstamming');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (27, 'bhg_afsCorrigeerAfstamming_R', 0, 99991231, 1, 1, 'bhg_afsCorrigeerAfstamming_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (28, 'bhg_vbaRegistreerImmigratie', 0, 99991231, 3, 1, 'bhg_vbaRegistreerImmigratie');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (29, 'bhg_vbaRegistreerImmigratie_R', 0, 99991231, 3, 1, 'bhg_vbaRegistreerImmigratie_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (30, 'bhg_otmRegistreerOnderzoek', 0, 99991231, 3, 1, 'bhg_otmRegistreerOnderzoek');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (31, 'bhg_otmRegistreerOnderzoek_R', 0, 99991231, 3, 1, 'bhg_otmRegistreerOnderzoek_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (32, 'bhg_otmRegistreerTerugmelding', 0, 99991231, 3, 1, 'bhg_otmRegistreerTerugmelding');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (33, 'bhg_otmRegistreerTerugmelding_R', 0, 99991231, 3, 1, 'bhg_otmRegistreerTerugmelding_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (34, 'bhg_natRegistreerNationaliteit', 0, 99991231, 6, 1, 'bhg_natRegistreerNationaliteit');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (35, 'bhg_natRegistreerNationaliteit_R', 0, 99991231, 6, 1, 'bhg_natRegistreerNationaliteit_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (36, 'bhg_natCorrigeerNationaliteit', 0, 99991231, 6, 1, 'bhg_natCorrigeerNationaliteit');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (37, 'bhg_natCorrigeerNationaliteit_R', 0, 99991231, 6, 1, 'bhg_natCorrigeerNationaliteit_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (38, 'bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap', 0, 99991231, 2, 1, 'bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (39, 'bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap_R', 0, 99991231, 2, 1, 'bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (40, 'lvg_synRegistreerAfnemerindicatie', 0, 99991231, 13, 1, 'lvg_synRegistreerAfnemerindicatie');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (41, 'lvg_synRegistreerAfnemerindicatie_R', 0, 99991231, 13, 1, 'lvg_synRegistreerAfnemerindicatie_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (42, 'bhg_ovlCorrigeerOverlijden', 0, 99991231, 4, 1, 'bhg_ovlCorrigeerOverlijden');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (43, 'bhg_ovlCorrigeerOverlijden_R', 0, 99991231, 4, 1, 'bhg_ovlCorrigeerOverlijden_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (44, 'bhg_nmgRegistreerNaamGeslacht', 0, 99991231, 7, 1, 'bhg_nmgRegistreerNaamGeslacht');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (45, 'bhg_nmgRegistreerNaamGeslacht_R', 0, 99991231, 7, 1, 'bhg_nmgRegistreerNaamGeslacht_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (46, 'bhg_nmgCorrigeerNaamGeslacht', 0, 99991231, 7, 1, 'bhg_nmgCorrigeerNaamGeslacht');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (47, 'bhg_nmgCorrigeerNaamGeslacht_R', 0, 99991231, 7, 1, 'bhg_nmgCorrigeerNaamGeslacht_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (48, 'bhg_vbaActualiseerVerblijfAdres', 0, 99991231, 3, 1, 'bhg_vbaActualiseerVerblijfAdres');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (49, 'bhg_vbaActualiseerVerblijfAdres_R', 0, 99991231, 3, 1, 'bhg_vbaActualiseerVerblijfAdres_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (50, 'bhg_otmCorrigeerOnderzoek', 0, 99991231, 3, 1, 'bhg_otmCorrigeerOnderzoek');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (51, 'bhg_otmCorrigeerOnderzoek_R', 0, 99991231, 3, 1, 'bhg_otmCorrigeerOnderzoek_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (52, 'bhg_dvmRegistreerMededelingVerzoek', 0, 99991231, 9, 1, 'bhg_dvmRegistreerMededelingVerzoek');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (53, 'bhg_dvmRegistreerMededelingVerzoek_R', 0, 99991231, 9, 1, 'bhg_dvmRegistreerMededelingVerzoek_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (54, 'bhg_dvmCorrigeerMededelingVerzoek', 0, 99991231, 9, 1, 'bhg_dvmCorrigeerMededelingVerzoek');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (55, 'bhg_dvmCorrigeerMededelingVerzoek_R', 0, 99991231, 9, 1, 'bhg_dvmCorrigeerMededelingVerzoek_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (56, 'bhg_rsdRegistreerReisdocument', 0, 99991231, 10, 1, 'bhg_rsdRegistreerReisdocument');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (57, 'bhg_rsdRegistreerReisdocument_R', 0, 99991231, 10, 1, 'bhg_rsdRegistreerReisdocument_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (58, 'bhg_rsdCorrigeerReisdocument', 0, 99991231, 10, 1, 'bhg_rsdCorrigeerReisdocument');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (59, 'bhg_rsdCorrigeerReisdocument_R', 0, 99991231, 10, 1, 'bhg_rsdCorrigeerReisdocument_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (60, 'bhg_vkzRegistreerKiesrecht', 0, 99991231, 11, 1, 'bhg_vkzRegistreerKiesrecht');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (61, 'bhg_vkzRegistreerKiesrecht_R', 0, 99991231, 11, 1, 'bhg_vkzRegistreerKiesrecht_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (62, 'bhg_vkzCorrigeerKiesrecht', 0, 99991231, 11, 1, 'bhg_vkzCorrigeerKiesrecht');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (63, 'bhg_vkzCorrigeerKiesrecht_R', 0, 99991231, 11, 1, 'bhg_vkzCorrigeerKiesrecht_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (64, 'lvg_synGeefSynchronisatiePersoon', 0, 99991231, 8, 1, 'lvg_synGeefSynchronisatiePersoon');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (65, 'lvg_synGeefSynchronisatiePersoon_R', 0, 99991231, 8, 1, 'lvg_synGeefSynchronisatiePersoon_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (66, 'lvg_synGeefSynchronisatieStamgegeven', 0, 99991231, 8, 1, 'lvg_synGeefSynchronisatieStamgegeven');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (67, 'lvg_synGeefSynchronisatieStamgegeven_R', 0, 99991231, 8, 1, 'lvg_synGeefSynchronisatieStamgegeven_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (68, 'bhg_ondRegistreerOnderzoek', 0, 99991231, 14, 1, 'bhg_ondRegistreerOnderzoek');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (69, 'bhg_ondRegistreerOnderzoek_R', 0, 99991231, 14, 1, 'bhg_ondRegistreerOnderzoek_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (70, 'lvg_synVerwerkStamgegeven', 0, 99991231, 8, 2, 'lvg_synVerwerkStamgegeven');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (71, 'lvg_bvgGeefDetailsPersoon', 0, 99991231, 12, 2, 'lvg_bvgGeefDetailsPersoon');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (72, 'lvg_bvgGeefDetailsPersoon_R', 0, 99991231, 12, 2, 'lvg_bvgGeefDetailsPersoon_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (73, 'lvg_bvgGeefMedebewoners', 0, 99991231, 12, 2, 'lvg_bvgGeefMedebewoners');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (74, 'lvg_bvgGeefMedebewoners_R', 0, 99991231, 12, 2, 'lvg_bvgGeefMedebewoners_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (75, 'lvg_bvgZoekPersoon', 0, 99991231, 12, 2, 'lvg_bvgZoekPersoon');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (76, 'lvg_bvgZoekPersoon_R', 0, 99991231, 12, 2, 'lvg_bvgZoekPersoon_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (77, 'bhg_bvgGeefMedebewoners', 0, 99991231, 5, 1, 'bhg_bvgGeefMedebewoners');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (78, 'bhg_bvgGeefMedebewoners_R', 0, 99991231, 5, 1, 'bhg_bvgGeefMedebewoners_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (79, 'Af11', 0, 99991231, NULL, 4, 'Af11');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (80, 'Ag01', 0, 99991231, NULL, 4, 'Ag01');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (81, 'Ag11', 0, 99991231, NULL, 4, 'Ag11');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (82, 'Ag21', 0, 99991231, NULL, 4, 'Ag21');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (83, 'Ag31', 0, 99991231, NULL, 4, 'Ag31');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (84, 'Ap01', 0, 99991231, NULL, 4, 'Ap01');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (85, 'Av01', 0, 99991231, NULL, 4, 'Av01');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (86, 'Gv01', 0, 99991231, NULL, 4, 'Gv01');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (87, 'Gv02', 0, 99991231, NULL, 4, 'Gv02');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (88, 'Ha01', 0, 99991231, NULL, 4, 'Ha01');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (89, 'Hf01', 0, 99991231, NULL, 4, 'Hf01');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (90, 'Hq01', 0, 99991231, NULL, 4, 'Hq01');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (91, 'Ib01', 0, 99991231, NULL, 4, 'Ib01');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (92, 'If01', 0, 99991231, NULL, 4, 'If01');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (93, 'If21', 0, 99991231, NULL, 4, 'If21');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (94, 'If31', 0, 99991231, NULL, 4, 'If31');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (95, 'If41', 0, 99991231, NULL, 4, 'If41');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (96, 'Ii01', 0, 99991231, NULL, 4, 'Ii01');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (97, 'Iv01', 0, 99991231, NULL, 4, 'Iv01');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (98, 'Iv11', 0, 99991231, NULL, 4, 'Iv11');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (99, 'Iv21', 0, 99991231, NULL, 4, 'Iv21');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (100, 'La01', 0, 99991231, NULL, 4, 'La01');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (101, 'Lf01', 0, 99991231, NULL, 4, 'Lf01');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (102, 'Lg01', 0, 99991231, NULL, 4, 'Lg01');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (103, 'Lq01', 0, 99991231, NULL, 4, 'Lq01');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (104, 'Ng01', 0, 99991231, NULL, 4, 'Ng01');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (105, 'Of11', 0, 99991231, NULL, 4, 'Of11');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (106, 'Og11', 0, 99991231, NULL, 4, 'Og11');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (107, 'Pf01', 0, 99991231, NULL, 4, 'Pf01');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (108, 'Pf02', 0, 99991231, NULL, 4, 'Pf02');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (109, 'Pf03', 0, 99991231, NULL, 4, 'Pf03');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (110, 'Qf01', 0, 99991231, NULL, 4, 'Qf01');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (111, 'Qf11', 0, 99991231, NULL, 4, 'Qf11');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (112, 'Qs01', 0, 99991231, NULL, 4, 'Qs01');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (113, 'Qv01', 0, 99991231, NULL, 4, 'Qv01');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (114, 'Sf01', 0, 99991231, NULL, 4, 'Sf01');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (115, 'Sv01', 0, 99991231, NULL, 4, 'Sv01');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (116, 'Sv11', 0, 99991231, NULL, 4, 'Sv11');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (117, 'Tb01', 0, 99991231, NULL, 4, 'Tb01');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (118, 'Tb02', 0, 99991231, NULL, 4, 'Tb02');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (119, 'Tf01', 0, 99991231, NULL, 4, 'Tf01');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (120, 'Tf11', 0, 99991231, NULL, 4, 'Tf11');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (121, 'Tf21', 0, 99991231, NULL, 4, 'Tf21');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (122, 'Tv01', 0, 99991231, NULL, 4, 'Tv01');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (123, 'Vb01', 0, 99991231, NULL, 4, 'Vb01');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (124, 'Wa01', 0, 99991231, NULL, 4, 'Wa01');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (125, 'Wa11', 0, 99991231, NULL, 4, 'Wa11');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (126, 'Wf01', 0, 99991231, NULL, 4, 'Wf01');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (127, 'Xa01', 0, 99991231, NULL, 4, 'Xa01');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (128, 'Xf01', 0, 99991231, NULL, 4, 'Xf01');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (129, 'Xq01', 0, 99991231, NULL, 4, 'Xq01');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (130, 'Null', 0, 99991231, NULL, 4, 'Null');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (131, 'Onbekend', 0, 99991231, NULL, 4, 'Onbekend');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (132, 'bhg_vbaRegistreerVerblijfsrecht', 0, 99991231, 3, 1, 'bhg_vbaRegistreerVerblijfsrecht');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (133, 'bhg_vbaRegistreerVerblijfsrecht_R', 0, 99991231, 3, 1, 'bhg_vbaRegistreerVerblijfsrecht_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (134, 'isc_migRegistreerGeboorte', 0, 99991231, 16, 3, 'isc_migRegistreerGeboorte');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (135, 'isc_migRegistreerGeboorte_R', 0, 99991231, 16, 3, 'isc_migRegistreerGeboorte_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (136, 'isc_migRegistreerOuderschap', 0, 99991231, 16, 3, 'isc_migRegistreerOuderschap');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (137, 'isc_migRegistreerOuderschap_R', 0, 99991231, 16, 3, 'isc_migRegistreerOuderschap_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (138, 'isc_migRegistreerNaamGeslacht', 0, 99991231, 16, 3, 'isc_migRegistreerNaamGeslacht');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (139, 'isc_migRegistreerNaamGeslacht_R', 0, 99991231, 16, 3, 'isc_migRegistreerNaamGeslacht_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (140, 'isc_migRegistreerHuwelijkGeregistreerdPartnerschap', 0, 99991231, 16, 3, 'isc_migRegistreerHuwelijkGeregistreerdPartnerschap');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (141, 'isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R', 0, 99991231, 16, 3, 'isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (142, 'isc_migRegistreerOverlijden', 0, 99991231, 16, 3, 'isc_migRegistreerOverlijden');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (143, 'isc_migRegistreerOverlijden_R', 0, 99991231, 16, 3, 'isc_migRegistreerOverlijden_R');
INSERT INTO Ber.SrtBer (ID, Naam, DatAanvGel, DatEindeGel, Module, Koppelvlak, Identifier) VALUES (144, 'bhg_fiaNotificeerBijhoudingsplan', 0, 99991231, 18, 1, 'bhg_fiaNotificeerBijhoudingsplan');

