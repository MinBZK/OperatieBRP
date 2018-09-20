
-- Maak BMR 20 tabellen (bron: BMR)
CREATE TABLE AutAut.Kanaal (
   ID                            Smallint                      NOT NULL    /* KanaalID */,
   Naam                          Varchar(80)  CHECK (Naam <> '' )  NOT NULL    /* NaamEnumeratiewaarde */,
   Oms                           Varchar(250)  CHECK (Oms <> '' )  NOT NULL    /* OmsEnumeratiewaarde */,
   CONSTRAINT R10632 PRIMARY KEY (ID),
   CONSTRAINT R10633 UNIQUE (Naam)
);

CREATE SEQUENCE AutAut.seq_Afleverwijze;
CREATE TABLE AutAut.Afleverwijze (
  ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_Afleverwijze')  /* AfleverwijzeID */,
  ToegangAbonnement             Integer                       NOT NULL    /* ToegangAbonnementID */,
  Kanaal                        Smallint                      NOT NULL    /* KanaalID */,
  Uri                           Varchar(200)  CHECK (Uri <> '' )              /* Uri */,
  DatIngang                     Integer                                   /* Dat */,
  DatEinde                      Integer                                   /* Dat */,
  CONSTRAINT R10626 PRIMARY KEY (ID)
);
ALTER SEQUENCE AutAut.seq_Afleverwijze OWNED BY AutAut.Afleverwijze.ID;

CREATE SEQUENCE AutAut.seq_His_Afleverwijze;
CREATE TABLE AutAut.His_Afleverwijze (
  ID                            Integer                       NOT NULL  DEFAULT nextval('AutAut.seq_His_Afleverwijze')  /* His_AfleverwijzeID */,
  Afleverwijze                  Integer                       NOT NULL    /* AfleverwijzeID */,
  TsReg                         Timestamp with time zone                  /* DatTijd */,
  TsVerval                      Timestamp with time zone                  /* DatTijd */,
  ActieInh                      BigInt                                    /* ActieID */,
  ActieVerval                   BigInt                                    /* ActieID */,
  DatIngang                     Integer                       NOT NULL    /* Dat */,
  DatEinde                      Integer                                   /* Dat */,
  CONSTRAINT R10629 PRIMARY KEY (ID),
  CONSTRAINT R10625 UNIQUE (Afleverwijze, TsReg)
);
ALTER SEQUENCE AutAut.seq_His_Afleverwijze OWNED BY AutAut.His_Afleverwijze.ID;

ALTER TABLE AutAut.Afleverwijze ADD CONSTRAINT FK10602 FOREIGN KEY (ToegangAbonnement) REFERENCES AutAut.ToegangAbonnement (ID);
ALTER TABLE AutAut.Afleverwijze ADD CONSTRAINT FK10613 FOREIGN KEY (Kanaal) REFERENCES AutAut.Kanaal (ID);

ALTER TABLE AutAut.His_Afleverwijze ADD CONSTRAINT FK10618 FOREIGN KEY (Afleverwijze) REFERENCES AutAut.Afleverwijze (ID);
ALTER TABLE AutAut.His_Afleverwijze ADD CONSTRAINT FK10621 FOREIGN KEY (ActieInh) REFERENCES Kern.Actie (ID);
ALTER TABLE AutAut.His_Afleverwijze ADD CONSTRAINT FK10622 FOREIGN KEY (ActieVerval) REFERENCES Kern.Actie (ID);

INSERT INTO AutAut.Kanaal (ID, Naam, Oms) VALUES (1, 'ebMS', 'Het ebMS zoals voorgeschreven door Logius in kader van Digikoppeling');
INSERT INTO AutAut.Kanaal (ID, Naam, Oms) VALUES (2, 'BRP', 'Op XML gebaseerd formaat zoals voor de BRP ontwikkeld');
INSERT INTO AutAut.Kanaal (ID, Naam, Oms) VALUES (3, 'LO3 netwerk', 'Via het LO3 (GBA-V) netwerk');
INSERT INTO AutAut.Kanaal (ID, Naam, Oms) VALUES (4, 'LO3 alternatief medium', 'Via een alternatief medium, zoals gebruikelijk voor afnemers op het GBA-V netwerk');

-- Invullen nieuwe tabellen
INSERT INTO AutAut.Afleverwijze (ID, Toegangabonnement, Kanaal, Uri, DatIngang, DatEinde)
SELECT
  ta.id,
  ta.id,
  2, -- kanaal BRP
  ta.wsdlendpoint,
  ta.datingang,
  ta.dateinde
FROM AutAut.Toegangabonnement ta
--WHERE wsdlendpoint IS NOT NULL
ORDER BY ta.id;

INSERT INTO AutAut.His_Afleverwijze (ID, Afleverwijze, DatIngang, DatEinde, TsReg)
SELECT
  a.id,
  a.id,
  a.datingang,
  a.dateinde,
  CURRENT_TIMESTAMP
FROM autaut.afleverwijze a
ORDER BY a.id;

ALTER TABLE AutAut.Toegangabonnement DROP COLUMN Wsdlendpoint;

-- Extra indexen (bron: Kern_custom-changes)
CREATE INDEX admhnd_tslev_index ON kern.admhnd USING btree (tslev);
CREATE INDEX admhnd_tsreg_index ON kern.admhnd USING btree (tsreg);
CREATE INDEX his_persafnemerindicatie_tsreg_tsverval_index ON autaut.his_persafnemerindicatie USING btree (tsreg, tsverval);

-- Nieuwe versie expressietaal kan alleen met hoofdletter-operators overweg
update autaut.dienst set naderepopulatiebeperking = 'TRUE' where naderepopulatiebeperking = 'true';
update autaut.his_dienst set naderepopulatiebeperking = 'TRUE' where naderepopulatiebeperking = 'true';

-- update Expressies (bron: Madelief_I_custom-changes)
SET search_path = autaut, pg_catalog;
delete from abonnementexpressie;
delete from expressie;
INSERT INTO expressie VALUES (1, 'AdresseerbaarObject', '1.5.0-SNAPSHOT', 19, 'RMAP(adressen, v, $v.adresseerbaar_object)');
INSERT INTO expressie VALUES (2, 'IdentcodeNraand', '1.5.0-SNAPSHOT', 19, 'RMAP(adressen, v, $v.identificatiecode_nummeraanduiding)');
INSERT INTO expressie VALUES (3, 'Postcode', '1.5.0-SNAPSHOT', 19, 'RMAP(adressen, v, $v.postcode)');
INSERT INTO expressie VALUES (4, 'Wpl', '1.5.0-SNAPSHOT', 19, 'RMAP(adressen, v, $v.woonplaats)');
INSERT INTO expressie VALUES (5, 'BLAdresRegel2', '1.5.0-SNAPSHOT', 19, 'RMAP(adressen, v, $v.buitenlands_adres_regel_2)');
INSERT INTO expressie VALUES (6, 'BLAdresRegel3', '1.5.0-SNAPSHOT', 19, 'RMAP(adressen, v, $v.buitenlands_adres_regel_3)');
INSERT INTO expressie VALUES (7, 'LocOms', '1.5.0-SNAPSHOT', 19, 'RMAP(adressen, v, $v.locatie_omschrijving)');
INSERT INTO expressie VALUES (8, 'Land', '1.5.0-SNAPSHOT', 19, 'RMAP(adressen, v, $v.land)');
INSERT INTO expressie VALUES (9, 'BLRegioGeboorte', '1.5.0-SNAPSHOT', 19, '$geboorte.buitenlandse_regio');
INSERT INTO expressie VALUES (10, 'DatEindeGel', '1.5.0-SNAPSHOT', 19, '$identificatienummers.datum_einde_geldigheid');
INSERT INTO expressie VALUES (11, 'BLAdresRegel1', '1.5.0-SNAPSHOT', 19, 'RMAP(adressen, v, $v.buitenlands_adres_regel_1)');
INSERT INTO expressie VALUES (12, 'DatAanvGel', '1.5.0-SNAPSHOT', 19, '$identificatienummers.datum_aanvang_geldigheid');
INSERT INTO expressie VALUES (13, 'NOR', '1.5.0-SNAPSHOT', 19, 'RMAP(adressen, v, $v.naam_openbare_ruimte)');
INSERT INTO expressie VALUES (14, 'LandGeboorte', '1.5.0-SNAPSHOT', 19, '$geboorte.land');
INSERT INTO expressie VALUES (15, 'Huisnr', '1.5.0-SNAPSHOT', 19, 'RMAP(adressen, v, $v.huisnummer)');
INSERT INTO expressie VALUES (16, 'Gemdeel', '1.5.0-SNAPSHOT', 19, 'RMAP(adressen, v, $v.gemeentedeel)');
INSERT INTO expressie VALUES (17, 'AfgekorteNOR', '1.5.0-SNAPSHOT', 19, 'RMAP(adressen, v, $v.afgekorte_naam_openbare_ruimte)');
INSERT INTO expressie VALUES (18, 'DatAanvGel', '1.5.0-SNAPSHOT', 19, '$geslachtsaanduiding.datum_aanvang_geldigheid');
INSERT INTO expressie VALUES (19, 'GemOverlijden', '1.5.0-SNAPSHOT', 19, '$overlijden.gemeente');
INSERT INTO expressie VALUES (20, 'DatEindeGel', '1.5.0-SNAPSHOT', 19, '$geslachtsaanduiding.datum_einde_geldigheid');
INSERT INTO expressie VALUES (21, 'LoctovAdres', '1.5.0-SNAPSHOT', 19, 'RMAP(adressen, v, $v.locatie_tov_adres)');
INSERT INTO expressie VALUES (22, 'Huisletter', '1.5.0-SNAPSHOT', 19, 'RMAP(adressen, v, $v.huisletter)');
INSERT INTO expressie VALUES (23, 'DatOverlijden', '1.5.0-SNAPSHOT', 19, '$overlijden.datum');
INSERT INTO expressie VALUES (24, 'DatAanvGel', '1.5.0-SNAPSHOT', 19, '$verblijfstitel.datum_aanvang_geldigheid');
INSERT INTO expressie VALUES (25, 'Huisnrtoevoeging', '1.5.0-SNAPSHOT', 19, 'RMAP(adressen, v, $v.huisnummertoevoeging)');
INSERT INTO expressie VALUES (26, 'WplOverlijden', '1.5.0-SNAPSHOT', 19, '$overlijden.woonplaats');
INSERT INTO expressie VALUES (27, 'BLRegioOverlijden', '1.5.0-SNAPSHOT', 19, '$overlijden.buitenlandse_regio');
INSERT INTO expressie VALUES (28, 'VoornamenAanschr', '1.5.0-SNAPSHOT', 19, '$aanschrijving.voornamen');
INSERT INTO expressie VALUES (29, 'DatAanvGel', '1.5.0-SNAPSHOT', 19, '$samengestelde_naam.datum_aanvang_geldigheid');
INSERT INTO expressie VALUES (30, 'DatEindeUitslNLKiesr', '1.5.0-SNAPSHOT', 19, '$uitsluiting_nl_kiesrecht.datum_einde_uitsluiting_nl_kiesrecht');
INSERT INTO expressie VALUES (31, 'LandOverlijden', '1.5.0-SNAPSHOT', 19, '$overlijden.land');
INSERT INTO expressie VALUES (32, 'DatEindeGel', '1.5.0-SNAPSHOT', 19, '$samengestelde_naam.datum_einde_geldigheid');
INSERT INTO expressie VALUES (33, 'BLPlaatsOverlijden', '1.5.0-SNAPSHOT', 19, '$overlijden.buitenlandse_plaats');
INSERT INTO expressie VALUES (34, 'OmsLocOverlijden', '1.5.0-SNAPSHOT', 19, '$overlijden.omschrijving_locatie');
INSERT INTO expressie VALUES (35, 'IndPKVolledigGeconv', '1.5.0-SNAPSHOT', 19, '$persoonskaart.volledig_geconverteerd');
INSERT INTO expressie VALUES (36, 'DatEindeUitslEUKiesr', '1.5.0-SNAPSHOT', 19, '$eu_verkiezingen.datum_einde_uitsluiting_eu_kiesrecht');
INSERT INTO expressie VALUES (37, 'DatAanvVerblijfstitel', '1.5.0-SNAPSHOT', 19, '$verblijfstitel.datum_aanvang');
INSERT INTO expressie VALUES (38, 'IndUitslNLKiesr', '1.5.0-SNAPSHOT', 19, '$uitsluiting_nl_kiesrecht.uitsluiting_nl_kiesrecht');
INSERT INTO expressie VALUES (39, 'GeslnaamAanschr', '1.5.0-SNAPSHOT', 19, '$aanschrijving.geslachtsnaam');
INSERT INTO expressie VALUES (40, 'IndDeelnEUVerkiezingen', '1.5.0-SNAPSHOT', 19, '$eu_verkiezingen.deelname_eu_verkiezingen');
INSERT INTO expressie VALUES (41, 'DatAanlAanpDeelnEUVerkiezing', '1.5.0-SNAPSHOT', 19, '$eu_verkiezingen.datum_aanleiding_aanpassing_deelname_eu_verkiezing');
INSERT INTO expressie VALUES (42, 'Bijhgem', '1.5.0-SNAPSHOT', 19, '$bijhoudingsgemeente');
INSERT INTO expressie VALUES (43, 'AangAdresh', '1.5.0-SNAPSHOT', 19, 'RMAP(adressen, v, $v.aangever_adreshouding)');
INSERT INTO expressie VALUES (44, 'Bijhaard', '1.5.0-SNAPSHOT', 19, '$bijhoudingsaard');
INSERT INTO expressie VALUES (45, 'DatInschr', '1.5.0-SNAPSHOT', 19, '$inschrijving.datum');
INSERT INTO expressie VALUES (46, 'ScheidingstekenAanschr', '1.5.0-SNAPSHOT', 19, '$aanschrijving.scheidingsteken');
INSERT INTO expressie VALUES (47, 'Verblijfstitel', '1.5.0-SNAPSHOT', 19, '$verblijfstitel');
INSERT INTO expressie VALUES (48, 'Voorvoegsel', '1.5.0-SNAPSHOT', 19, '$samengestelde_naam.voorvoegsel');
INSERT INTO expressie VALUES (49, 'DatEindeGel', '1.5.0-SNAPSHOT', 19, 'RMAP(voornamen, v, $v.datum_einde_geldigheid)');
INSERT INTO expressie VALUES (50, 'DatAanvGel', '1.5.0-SNAPSHOT', 19, 'RMAP(voornamen, v, $v.datum_aanvang_geldigheid)');
INSERT INTO expressie VALUES (51, 'Gem', '1.5.0-SNAPSHOT', 19, 'RMAP(adressen, v, $v.gemeente)');
INSERT INTO expressie VALUES (52, 'IndOnverwDocAanw', '1.5.0-SNAPSHOT', 19, '$bijhoudingsgemeente.onverwerkt_document_aanwezig');
INSERT INTO expressie VALUES (53, 'LandVanwaarGevestigd', '1.5.0-SNAPSHOT', 19, '$immigratie.land_vanwaar_gevestigd');
INSERT INTO expressie VALUES (61, 'DatEindeGel', '1.5.0-SNAPSHOT', 19, '$bijhoudingsgemeente.datum_einde_geldigheid');
INSERT INTO expressie VALUES (62, 'DatAanvGel', '1.5.0-SNAPSHOT', 19, '$bijhoudingsgemeente.datum_aanvang_geldigheid');
INSERT INTO expressie VALUES (63, 'RdnVerk', '1.5.0-SNAPSHOT', 19, 'RMAP(nationaliteiten, v, $v.reden_verkrijging)');
INSERT INTO expressie VALUES (64, 'RdnVerlies', '1.5.0-SNAPSHOT', 19, 'RMAP(nationaliteiten, v, $v.reden_verlies)');
INSERT INTO expressie VALUES (66, 'LengteHouder', '1.5.0-SNAPSHOT', 19, 'RMAP(reisdocumenten, v, $v.lengte_houder)');
INSERT INTO expressie VALUES (67, 'RdnVervallen', '1.5.0-SNAPSHOT', 19, 'RMAP(reisdocumenten, v, $v.reden_vervallen)');
INSERT INTO expressie VALUES (68, 'DatInhingVermissing', '1.5.0-SNAPSHOT', 19, 'RMAP(reisdocumenten, v, $v.datum_inhoudingvermissing)');
INSERT INTO expressie VALUES (69, 'Scheidingsteken', '1.5.0-SNAPSHOT', 19, 'RMAP(geslachtsnaamcomponenten, v, $v.scheidingsteken)');
INSERT INTO expressie VALUES (70, 'DatVoorzeEindeGel', '1.5.0-SNAPSHOT', 19, 'RMAP(reisdocumenten, v, $v.datum_voorziene_einde_geldigheid)');
INSERT INTO expressie VALUES (72, 'AutVanAfgifte', '1.5.0-SNAPSHOT', 19, 'RMAP(reisdocumenten, v, $v.autoriteit_van_afgifte)');
INSERT INTO expressie VALUES (73, 'IndPersNietAangetroffenOpAdr', '1.5.0-SNAPSHOT', 19, 'RMAP(adressen, v, $v.persoon_niet_aangetroffen_op_adres)');
INSERT INTO expressie VALUES (79, 'DatVoorzEindeVerblijfstitel', '1.5.0-SNAPSHOT', 19, '$verblijfstitel.datum_voorzien_einde');
INSERT INTO expressie VALUES (82, 'Versienr', '1.5.0-SNAPSHOT', 19, '$inschrijving.versienummer');
INSERT INTO expressie VALUES (83, 'TsLaatsteWijz', '1.5.0-SNAPSHOT', 19, '$administratief.tijdstip_laatste_wijziging');
INSERT INTO expressie VALUES (84, 'BSN', '1.5.0-SNAPSHOT', 19, '$identificatienummers.burgerservicenummer');
INSERT INTO expressie VALUES (85, 'DatEindeGel', '1.5.0-SNAPSHOT', 19, '$verblijfstitel.datum_einde_geldigheid');
INSERT INTO expressie VALUES (86, 'DatAanvAdresh', '1.5.0-SNAPSHOT', 19, 'RMAP(adressen, v, $v.datum_aanvang_adreshouding)');
INSERT INTO expressie VALUES (87, 'IndTitelsPredikatenBijAansch', '1.5.0-SNAPSHOT', 19, '$aanschrijving.titels_predikaten_bij_aanschrijven');
INSERT INTO expressie VALUES (88, 'BVP', '1.5.0-SNAPSHOT', 19, '$bijzondere_verblijfsrechtelijke_positie.bijzondere_verblijfsrechtelijke_positie');
INSERT INTO expressie VALUES (89, 'Scheidingsteken', '1.5.0-SNAPSHOT', 19, '$samengestelde_naam.scheidingsteken');
INSERT INTO expressie VALUES (90, 'Nr', '1.5.0-SNAPSHOT', 19, 'RMAP(reisdocumenten, v, $v.nummer)');
INSERT INTO expressie VALUES (91, 'DatUitgifte', '1.5.0-SNAPSHOT', 19, 'RMAP(reisdocumenten, v, $v.datum_uitgifte)');
INSERT INTO expressie VALUES (92, 'Srt', '1.5.0-SNAPSHOT', 19, 'RMAP(adressen, v, $v.soort)');
INSERT INTO expressie VALUES (93, 'ANr', '1.5.0-SNAPSHOT', 19, '$identificatienummers.administratienummer');
INSERT INTO expressie VALUES (94, 'Srt', '1.5.0-SNAPSHOT', 19, 'RMAP(reisdocumenten, v, $v.soort)');
INSERT INTO expressie VALUES (95, 'DatVestigingInNederland', '1.5.0-SNAPSHOT', 19, '$immigratie.datum_vestiging_in_nederland');
INSERT INTO expressie VALUES (96, 'DatVertrekUitNederland', '1.5.0-SNAPSHOT', 19, 'RMAP(adressen, v, $v.datum_vertrek_uit_nederland)');
INSERT INTO expressie VALUES (97, 'GemPK', '1.5.0-SNAPSHOT', 19, '$persoonskaart.gemeente');
INSERT INTO expressie VALUES (98, 'RdnWijz', '1.5.0-SNAPSHOT', 19, 'RMAP(adressen, v, $v.reden_wijziging)');
INSERT INTO expressie VALUES (99, 'Naam', '1.5.0-SNAPSHOT', 19, 'RMAP(voornamen, v, $v.naam)');
INSERT INTO expressie VALUES (100, 'DatAanvGel', '1.5.0-SNAPSHOT', 19, '$bijhoudingsaard.datum_aanvang_geldigheid');
INSERT INTO expressie VALUES (101, 'DatEindeGel', '1.5.0-SNAPSHOT', 19, '$bijhoudingsaard.datum_einde_geldigheid');
INSERT INTO expressie VALUES (102, 'Naam', '1.5.0-SNAPSHOT', 19, 'RMAP(geslachtsnaamcomponenten, v, $v.naam)');
INSERT INTO expressie VALUES (103, 'Voorvoegsel', '1.5.0-SNAPSHOT', 19, 'RMAP(geslachtsnaamcomponenten, v, $v.voorvoegsel)');
INSERT INTO expressie VALUES (104, 'Geslachtsaand', '1.5.0-SNAPSHOT', 19, '$geslachtsaanduiding');
INSERT INTO expressie VALUES (105, 'Sorteervolgorde', '1.5.0-SNAPSHOT', 19, '$administratief.sorteervolgorde');
INSERT INTO expressie VALUES (106, 'Volgnr', '1.5.0-SNAPSHOT', 19, 'RMAP(voornamen, v, $v.volgnummer)');
INSERT INTO expressie VALUES (107, 'Volgnr', '1.5.0-SNAPSHOT', 19, 'RMAP(geslachtsnaamcomponenten, v, $v.volgnummer)');
INSERT INTO expressie VALUES (109, 'BLAdresRegel6', '1.5.0-SNAPSHOT', 19, 'RMAP(adressen, v, $v.buitenlands_adres_regel_6)');
INSERT INTO expressie VALUES (111, 'BLAdresRegel5', '1.5.0-SNAPSHOT', 19, 'RMAP(adressen, v, $v.buitenlands_adres_regel_5)');
INSERT INTO expressie VALUES (112, 'BLAdresRegel4', '1.5.0-SNAPSHOT', 19, 'RMAP(adressen, v, $v.buitenlands_adres_regel_4)');
INSERT INTO expressie VALUES (113, 'PredikaatAanschr', '1.5.0-SNAPSHOT', 19, '$aanschrijving.predikaat');
INSERT INTO expressie VALUES (114, 'DatAanvGel', '1.5.0-SNAPSHOT', 19, '$opschorting.datum_aanvang_geldigheid');
INSERT INTO expressie VALUES (115, 'DatEindeGel', '1.5.0-SNAPSHOT', 19, '$opschorting.datum_einde_geldigheid');
INSERT INTO expressie VALUES (116, 'DatGeboorte', '1.5.0-SNAPSHOT', 19, '$geboorte.datum');
INSERT INTO expressie VALUES (117, 'IndAlgoritmischAfgeleid', '1.5.0-SNAPSHOT', 19, '$samengestelde_naam.algoritmisch_afgeleid');
INSERT INTO expressie VALUES (119, 'GemGeboorte', '1.5.0-SNAPSHOT', 19, '$geboorte.gemeente');
INSERT INTO expressie VALUES (120, 'BLPlaatsGeboorte', '1.5.0-SNAPSHOT', 19, '$geboorte.buitenlandse_plaats');
INSERT INTO expressie VALUES (121, 'WplGeboorte', '1.5.0-SNAPSHOT', 19, '$geboorte.woonplaats');
INSERT INTO expressie VALUES (122, 'OmsLocGeboorte', '1.5.0-SNAPSHOT', 19, '$geboorte.omschrijving_locatie');
INSERT INTO expressie VALUES (124, 'DatAanvGel', '1.5.0-SNAPSHOT', 19, '$immigratie.datum_aanvang_geldigheid');
INSERT INTO expressie VALUES (126, 'DatEindeGel', '1.5.0-SNAPSHOT', 19, '$immigratie.datum_einde_geldigheid');
INSERT INTO expressie VALUES (127, 'AdellijkeTitelAanschr', '1.5.0-SNAPSHOT', 19, '$aanschrijving.adellijke_titel');
INSERT INTO expressie VALUES (128, 'Srt', '1.5.0-SNAPSHOT', 19, 'RMAP(indicaties, v, $v.soort)');
INSERT INTO expressie VALUES (129, 'Waarde', '1.5.0-SNAPSHOT', 19, 'RMAP(indicaties, v, $v.waarde)');
INSERT INTO expressie VALUES (130, 'RdnOpschortingBijhouding', '1.5.0-SNAPSHOT', 19, '$opschorting.redenbijhouding');
INSERT INTO expressie VALUES (131, 'DatAanvGelGegevensReisdoc', '1.5.0-SNAPSHOT', 19, 'RMAP(reisdocumenten, v, $v.datum_aanvang_geldigheid_gegevens_reisdocument)');
INSERT INTO expressie VALUES (132, 'Srt', '1.5.0-SNAPSHOT', 19, '$soort');
INSERT INTO expressie VALUES (133, 'AdellijkeTitel', '1.5.0-SNAPSHOT', 19, '$samengestelde_naam.adellijke_titel');
INSERT INTO expressie VALUES (134, 'Predikaat', '1.5.0-SNAPSHOT', 19, '$samengestelde_naam.predikaat');
INSERT INTO expressie VALUES (135, 'DatInschrInGem', '1.5.0-SNAPSHOT', 19, '$bijhoudingsgemeente.datum_inschrijving_in_gemeente');
INSERT INTO expressie VALUES (136, 'DatAanvGel', '1.5.0-SNAPSHOT', 19, 'RMAP(nationaliteiten, v, $v.datum_aanvang_geldigheid)');
INSERT INTO expressie VALUES (137, 'DatEindeGel', '1.5.0-SNAPSHOT', 19, 'RMAP(nationaliteiten, v, $v.datum_einde_geldigheid)');
INSERT INTO expressie VALUES (138, 'Voornamen', '1.5.0-SNAPSHOT', 19, '$samengestelde_naam.voornamen');
INSERT INTO expressie VALUES (139, 'IndAanschrAlgoritmischAfgele', '1.5.0-SNAPSHOT', 19, '$aanschrijving.algoritmisch_afgeleid');
INSERT INTO expressie VALUES (140, 'Geslnaam', '1.5.0-SNAPSHOT', 19, '$samengestelde_naam.geslachtsnaam');
INSERT INTO expressie VALUES (141, 'VoorvoegselAanschr', '1.5.0-SNAPSHOT', 19, '$aanschrijving.voorvoegsel');
INSERT INTO expressie VALUES (142, 'Nation', '1.5.0-SNAPSHOT', 19, 'RMAP(nationaliteiten, v, $v.nationaliteit)');
INSERT INTO expressie VALUES (143, 'DatAanvGel', '1.5.0-SNAPSHOT', 19, 'RMAP(geslachtsnaamcomponenten, v, $v.datum_aanvang_geldigheid)');
INSERT INTO expressie VALUES (144, 'DatEindeGel', '1.5.0-SNAPSHOT', 19, 'RMAP(geslachtsnaamcomponenten, v, $v.datum_einde_geldigheid)');
INSERT INTO expressie VALUES (145, 'DatEindeGel', '1.5.0-SNAPSHOT', 19, 'RMAP(adressen, v, $v.datum_einde_geldigheid)');
INSERT INTO expressie VALUES (146, 'DatAanvGel', '1.5.0-SNAPSHOT', 19, 'RMAP(adressen, v, $v.datum_aanvang_geldigheid)');
INSERT INTO expressie VALUES (147, 'DatEindeGel', '1.5.0-SNAPSHOT', 19, 'RMAP(indicaties, v, $v.datum_einde_geldigheid)');
INSERT INTO expressie VALUES (148, 'DatAanvGel', '1.5.0-SNAPSHOT', 19, 'RMAP(indicaties, v, $v.datum_aanvang_geldigheid)');
INSERT INTO expressie VALUES (149, 'AdellijkeTitel', '1.5.0-SNAPSHOT', 19, 'RMAP(geslachtsnaamcomponenten, v, $v.adellijke_titel)');
INSERT INTO expressie VALUES (150, 'IndNreeks', '1.5.0-SNAPSHOT', 19, '$samengestelde_naam.namenreeks');
INSERT INTO expressie VALUES (151, 'Naamgebruik', '1.5.0-SNAPSHOT', 19, '$aanschrijving.naamgebruik');
INSERT INTO expressie VALUES (153, 'Predikaat', '1.5.0-SNAPSHOT', 19, 'RMAP(geslachtsnaamcomponenten, v, $v.predikaat)');
INSERT INTO expressie VALUES (154, 'Rol', '1.5.0-SNAPSHOT', 19, 'RMAP(betrokkenheden, v, $v.rol)');
INSERT INTO expressie VALUES (118, 'Srt', '1.5.0-SNAPSHOT', 19, 'RMAP(HUWELIJKEN(), h, $h.soort)');
INSERT INTO expressie VALUES (152, 'PersIndicatie', '1.5.0-SNAPSHOT', 19, 'RMAP(FILTER(PLATTE_LIJST(indicaties),i,i.soort="XYZ"), f, $f.soort)');
INSERT INTO expressie VALUES (209, 'soort nr 1', '1.5.0-SNAPSHOT', 19, 'RMAP(FILTER(PLATTE_LIJST(indicaties),i,i.soort="Derde heeft gezag?"), f, $f.soort)');
INSERT INTO expressie VALUES (210, 'soort nr 2', '1.5.0-SNAPSHOT', 19, 'RMAP(FILTER(PLATTE_LIJST(indicaties),i,i.soort="Onder curatele?"), f, $f.soort)');
INSERT INTO expressie VALUES (211, 'soort nr 3', '1.5.0-SNAPSHOT', 19, 'RMAP(FILTER(PLATTE_LIJST(indicaties),i,i.soort="Verstrekkingsbeperking?"), f, $f.soort)');
INSERT INTO expressie VALUES (212, 'soort nr 4', '1.5.0-SNAPSHOT', 19, 'RMAP(FILTER(PLATTE_LIJST(indicaties),i,i.soort="Vastgesteld niet-Nederlander?"), f, $f.soort)');
INSERT INTO expressie VALUES (213, 'soort nr 5', '1.5.0-SNAPSHOT', 19, 'RMAP(FILTER(PLATTE_LIJST(indicaties),i,i.soort="Behandeld als Nederlander?"), f, $f.soort)');
INSERT INTO expressie VALUES (214, 'soort nr 6', '1.5.0-SNAPSHOT', 19, 'RMAP(FILTER(PLATTE_LIJST(indicaties),i,i.soort="Belemmering verstrekking reisdocument?"), f, $f.soort)');
INSERT INTO expressie VALUES (215, 'soort nr 7', '1.5.0-SNAPSHOT', 19, 'RMAP(FILTER(PLATTE_LIJST(indicaties),i,i.soort="Staatloos?"), f, $f.soort)');
INSERT INTO expressie VALUES (217, 'Handmatig toegevoegd', NULL, NULL, 'PLATTE_LIJST(MAP(persoon.betrokkenheden, b, $b.ouderlijk_gezag.ouder_heeft_gezag))');
INSERT INTO expressie VALUES (218, 'Handmatig toegevoegd', NULL, NULL, 'PLATTE_LIJST(MAP(persoon.betrokkenheden, b, $b.ouderlijk_gezag.datum_aanvang_geldigheid))');
INSERT INTO expressie VALUES (219, 'Handmatig toegevoegd', NULL, NULL, 'PLATTE_LIJST(MAP(persoon.betrokkenheden, b, $b.ouderlijk_gezag.datum_einde_geldigheid))');
INSERT INTO expressie VALUES (220, 'Handmatig toegevoegd', NULL, NULL, 'PLATTE_LIJST(MAP(persoon.betrokkenheden, b, $b.ouderschap.ouder))');
INSERT INTO expressie VALUES (221, 'Handmatig toegevoegd', NULL, NULL, 'PLATTE_LIJST(MAP(persoon.betrokkenheden, b, $b.ouderschap.datum_einde_geldigheid))');
INSERT INTO expressie VALUES (222, 'Handmatig toegevoegd', NULL, NULL, 'PLATTE_LIJST(MAP(persoon.betrokkenheden, b, $b.ouderschap.datum_aanvang_geldigheid))');
INSERT INTO expressie VALUES (223, 'Handmatig toegevoegd', NULL, NULL, 'RMAP(GERELATEERDE_BETROKKENHEDEN("KIND", "FAMILIERECHTELIJKE_BETREKKING", "OUDER"), b, $b.ouderlijk_gezag.ouder_heeft_gezag)');
INSERT INTO expressie VALUES (224, 'Handmatig toegevoegd', NULL, NULL, 'RMAP(GERELATEERDE_BETROKKENHEDEN("KIND", "FAMILIERECHTELIJKE_BETREKKING", "OUDER"), b, $b.ouderlijk_gezag.datum_aanvang_geldigheid)');
INSERT INTO expressie VALUES (225, 'Handmatig toegevoegd', NULL, NULL, 'RMAP(GERELATEERDE_BETROKKENHEDEN("KIND", "FAMILIERECHTELIJKE_BETREKKING", "OUDER"), b, $b.ouderlijk_gezag.datum_einde_geldigheid)');
INSERT INTO expressie VALUES (226, 'Handmatig toegevoegd', NULL, NULL, 'RMAP(GERELATEERDE_BETROKKENHEDEN("KIND", "FAMILIERECHTELIJKE_BETREKKING", "OUDER"), b, $b.ouderschap.ouder)');
INSERT INTO expressie VALUES (227, 'Handmatig toegevoegd', NULL, NULL, 'RMAP(GERELATEERDE_BETROKKENHEDEN("KIND", "FAMILIERECHTELIJKE_BETREKKING", "OUDER"), b, $b.ouderschap.datum_einde_geldigheid)');
INSERT INTO expressie VALUES (228, 'Handmatig toegevoegd', NULL, NULL, 'RMAP(GERELATEERDE_BETROKKENHEDEN("KIND", "FAMILIERECHTELIJKE_BETREKKING", "OUDER"), b, $b.ouderschap.datum_aanvang_geldigheid)');
INSERT INTO expressie VALUES (229, 'Handmatig toegevoegd', NULL, NULL, 'RMAP(OUDERS(), o, $o.administratief.tijdstip_laatste_wijziging)');
INSERT INTO expressie VALUES (230, 'Handmatig toegevoegd', NULL, NULL, 'RMAP(OUDERS(), o, $o.administratief.sorteervolgorde)');
INSERT INTO expressie VALUES (231, 'Handmatig toegevoegd', NULL, NULL, 'RMAP(KINDEREN(), k, $k.administratief.tijdstip_laatste_wijziging)');
INSERT INTO expressie VALUES (232, 'Handmatig toegevoegd', NULL, NULL, 'RMAP(KINDEREN(), k, $k.administratief.sorteervolgorde)');
INSERT INTO expressie VALUES (233, 'Handmatig toegevoegd', NULL, NULL, 'RMAP(PARTNERS(), p, $p.administratief.tijdstip_laatste_wijziging)');
INSERT INTO expressie VALUES (234, 'Handmatig toegevoegd', NULL, NULL, 'RMAP(PARTNERS(), p, $p.administratief.sorteervolgorde)');
INSERT INTO expressie VALUES (155, 'Ouder-BLRegioGeboorte', '1.5.0-SNAPSHOT', 19, 'RMAP(OUDERS(), o, $o.geboorte.buitenlandse_regio)');
INSERT INTO expressie VALUES (156, 'Ouder-LandGeboorte', '1.5.0-SNAPSHOT', 19, 'RMAP(OUDERS(), o, $o.geboorte.land)');
INSERT INTO expressie VALUES (157, 'Ouder-Voorvoegsel', '1.5.0-SNAPSHOT', 19, 'RMAP(OUDERS(), o, $o.samengestelde_naam.voorvoegsel)');
INSERT INTO expressie VALUES (158, 'Ouder-BSN', '1.5.0-SNAPSHOT', 19, 'RMAP(OUDERS(), o, $o.identificatienummers.burgerservicenummer)');
INSERT INTO expressie VALUES (159, 'Ouder-Scheidingsteken', '1.5.0-SNAPSHOT', 19, 'RMAP(OUDERS(), o, $o.samengestelde_naam.scheidingsteken)');
INSERT INTO expressie VALUES (160, 'Ouder-ANr', '1.5.0-SNAPSHOT', 19, 'RMAP(OUDERS(), o, $o.identificatienummers.administratienummer)');
INSERT INTO expressie VALUES (161, 'Ouder-Geslachtsaand', '1.5.0-SNAPSHOT', 19, 'RMAP(OUDERS(), o, $o.geslachtsaanduiding)');
INSERT INTO expressie VALUES (162, 'Ouder-DatGeboorte', '1.5.0-SNAPSHOT', 19, 'RMAP(OUDERS(), o, $o.geboorte.datum)');
INSERT INTO expressie VALUES (163, 'Ouder-IndAlgoritmischAfgeleid', '1.5.0-SNAPSHOT', 19, 'RMAP(OUDERS(), o, $o.samengestelde_naam.algoritmisch_afgeleid)');
INSERT INTO expressie VALUES (164, 'Ouder-GemGeboorte', '1.5.0-SNAPSHOT', 19, 'RMAP(OUDERS(), o, $o.geboorte.gemeente)');
INSERT INTO expressie VALUES (165, 'Ouder-BLPlaatsGeboorte', '1.5.0-SNAPSHOT', 19, 'RMAP(OUDERS(), o, $o.geboorte.buitenlandse_plaats)');
INSERT INTO expressie VALUES (166, 'Ouder-WplGeboorte', '1.5.0-SNAPSHOT', 19, 'RMAP(OUDERS(), o, $o.geboorte.woonplaats)');
INSERT INTO expressie VALUES (167, 'Ouder-OmsLocGeboorte', '1.5.0-SNAPSHOT', 19, 'RMAP(OUDERS(), o, $o.geboorte.omschrijving_locatie)');
INSERT INTO expressie VALUES (168, 'Ouder-AdellijkeTitel', '1.5.0-SNAPSHOT', 19, 'RMAP(OUDERS(), o, $o.samengestelde_naam.adellijke_titel)');
INSERT INTO expressie VALUES (169, 'Ouder-Predikaat', '1.5.0-SNAPSHOT', 19, 'RMAP(OUDERS(), o, $o.samengestelde_naam.predikaat)');
INSERT INTO expressie VALUES (170, 'Ouder-Voornamen', '1.5.0-SNAPSHOT', 19, 'RMAP(OUDERS(), o, $o.samengestelde_naam.voornamen)');
INSERT INTO expressie VALUES (171, 'Ouder-Geslnaam', '1.5.0-SNAPSHOT', 19, 'RMAP(OUDERS(), o, $o.samengestelde_naam.geslachtsnaam)');
INSERT INTO expressie VALUES (172, 'Ouder-IndNreeks', '1.5.0-SNAPSHOT', 19, 'RMAP(OUDERS(), o, $o.samengestelde_naam.namenreeks)');
INSERT INTO expressie VALUES (173, 'Kind-BLRegioGeboorte', '1.5.0-SNAPSHOT', 19, 'RMAP(KINDEREN(), k, $k.geboorte.buitenlandse_regio)');
INSERT INTO expressie VALUES (174, 'Kind-LandGeboorte', '1.5.0-SNAPSHOT', 19, 'RMAP(KINDEREN(), k, $k.geboorte.land)');
INSERT INTO expressie VALUES (175, 'Kind-Voorvoegsel', '1.5.0-SNAPSHOT', 19, 'RMAP(KINDEREN(), k, $k.samengestelde_naam.voorvoegsel)');
INSERT INTO expressie VALUES (176, 'Kind-BSN', '1.5.0-SNAPSHOT', 19, 'RMAP(KINDEREN(), k, $k.identificatienummers.burgerservicenummer)');
INSERT INTO expressie VALUES (177, 'Kind-Scheidingsteken', '1.5.0-SNAPSHOT', 19, 'RMAP(KINDEREN(), k, $k.samengestelde_naam.scheidingsteken)');
INSERT INTO expressie VALUES (178, 'Kind-ANr', '1.5.0-SNAPSHOT', 19, 'RMAP(KINDEREN(), k, $k.identificatienummers.administratienummer)');
INSERT INTO expressie VALUES (179, 'Kind-Geslachtsaand', '1.5.0-SNAPSHOT', 19, 'RMAP(KINDEREN(), k, $k.geslachtsaanduiding)');
INSERT INTO expressie VALUES (180, 'Kind-DatGeboorte', '1.5.0-SNAPSHOT', 19, 'RMAP(KINDEREN(), k, $k.geboorte.datum)');
INSERT INTO expressie VALUES (181, 'Kind-IndAlgoritmischAfgeleid', '1.5.0-SNAPSHOT', 19, 'RMAP(KINDEREN(), k, $k.samengestelde_naam.algoritmisch_afgeleid)');
INSERT INTO expressie VALUES (182, 'Kind-GemGeboorte', '1.5.0-SNAPSHOT', 19, 'RMAP(KINDEREN(), k, $k.geboorte.gemeente)');
INSERT INTO expressie VALUES (183, 'Kind-BLPlaatsGeboorte', '1.5.0-SNAPSHOT', 19, 'RMAP(KINDEREN(), k, $k.geboorte.buitenlandse_plaats)');
INSERT INTO expressie VALUES (184, 'Kind-WplGeboorte', '1.5.0-SNAPSHOT', 19, 'RMAP(KINDEREN(), k, $k.geboorte.woonplaats)');
INSERT INTO expressie VALUES (185, 'Kind-OmsLocGeboorte', '1.5.0-SNAPSHOT', 19, 'RMAP(KINDEREN(), k, $k.geboorte.omschrijving_locatie)');
INSERT INTO expressie VALUES (186, 'Kind-AdellijkeTitel', '1.5.0-SNAPSHOT', 19, 'RMAP(KINDEREN(), k, $k.samengestelde_naam.adellijke_titel)');
INSERT INTO expressie VALUES (187, 'Kind-Predikaat', '1.5.0-SNAPSHOT', 19, 'RMAP(KINDEREN(), k, $k.samengestelde_naam.predikaat)');
INSERT INTO expressie VALUES (188, 'Kind-Voornamen', '1.5.0-SNAPSHOT', 19, 'RMAP(KINDEREN(), k, $k.samengestelde_naam.voornamen)');
INSERT INTO expressie VALUES (189, 'Kind-Geslnaam', '1.5.0-SNAPSHOT', 19, 'RMAP(KINDEREN(), k, $k.samengestelde_naam.geslachtsnaam)');
INSERT INTO expressie VALUES (190, 'Kind-IndNreeks', '1.5.0-SNAPSHOT', 19, 'RMAP(KINDEREN(), k, $k.samengestelde_naam.namenreeks)');
INSERT INTO expressie VALUES (191, 'Partner-BLRegioGeboorte', '1.5.0-SNAPSHOT', 19, 'RMAP(PARTNERS(), p, $p.geboorte.buitenlandse_regio)');
INSERT INTO expressie VALUES (192, 'Partner-LandGeboorte', '1.5.0-SNAPSHOT', 19, 'RMAP(PARTNERS(), p, $p.geboorte.land)');
INSERT INTO expressie VALUES (193, 'Partner-Voorvoegsel', '1.5.0-SNAPSHOT', 19, 'RMAP(PARTNERS(), p, $p.samengestelde_naam.voorvoegsel)');
INSERT INTO expressie VALUES (194, 'Partner-BSN', '1.5.0-SNAPSHOT', 19, 'RMAP(PARTNERS(), p, $p.identificatienummers.burgerservicenummer)');
INSERT INTO expressie VALUES (195, 'Partner-Scheidingsteken', '1.5.0-SNAPSHOT', 19, 'RMAP(PARTNERS(), p, $p.samengestelde_naam.scheidingsteken)');
INSERT INTO expressie VALUES (196, 'Partner-ANr', '1.5.0-SNAPSHOT', 19, 'RMAP(PARTNERS(), p, $p.identificatienummers.administratienummer)');
INSERT INTO expressie VALUES (197, 'Partner-Geslachtsaand', '1.5.0-SNAPSHOT', 19, 'RMAP(PARTNERS(), p, $p.geslachtsaanduiding)');
INSERT INTO expressie VALUES (198, 'Partner-DatGeboorte', '1.5.0-SNAPSHOT', 19, 'RMAP(PARTNERS(), p, $p.geboorte.datum)');
INSERT INTO expressie VALUES (199, 'Partner-IndAlgoritmischAfgeleid', '1.5.0-SNAPSHOT', 19, 'RMAP(PARTNERS(), p, $p.samengestelde_naam.algoritmisch_afgeleid)');
INSERT INTO expressie VALUES (200, 'Partner-GemGeboorte', '1.5.0-SNAPSHOT', 19, 'RMAP(PARTNERS(), p, $p.geboorte.gemeente)');
INSERT INTO expressie VALUES (201, 'Partner-BLPlaatsGeboorte', '1.5.0-SNAPSHOT', 19, 'RMAP(PARTNERS(), p, $p.geboorte.buitenlandse_plaats)');
INSERT INTO expressie VALUES (202, 'Partner-WplGeboorte', '1.5.0-SNAPSHOT', 19, 'RMAP(PARTNERS(), p, $p.geboorte.woonplaats)');
INSERT INTO expressie VALUES (203, 'Partner-OmsLocGeboorte', '1.5.0-SNAPSHOT', 19, 'RMAP(PARTNERS(), p, $p.geboorte.omschrijving_locatie)');
INSERT INTO expressie VALUES (204, 'Partner-AdellijkeTitel', '1.5.0-SNAPSHOT', 19, 'RMAP(PARTNERS(), p, $p.samengestelde_naam.adellijke_titel)');
INSERT INTO expressie VALUES (205, 'Partner-Predikaat', '1.5.0-SNAPSHOT', 19, 'RMAP(PARTNERS(), p, $p.samengestelde_naam.predikaat)');
INSERT INTO expressie VALUES (206, 'Partner-Voornamen', '1.5.0-SNAPSHOT', 19, 'RMAP(PARTNERS(), p, $p.samengestelde_naam.voornamen)');
INSERT INTO expressie VALUES (207, 'Partner-Geslnaam', '1.5.0-SNAPSHOT', 19, 'RMAP(PARTNERS(), p, $p.samengestelde_naam.geslachtsnaam)');
INSERT INTO expressie VALUES (208, 'Partner-IndNreeks', '1.5.0-SNAPSHOT', 19, 'RMAP(PARTNERS(), p, $p.samengestelde_naam.namenreeks)');
INSERT INTO expressie VALUES (54, 'OmsLocEinde', '1.5.0-SNAPSHOT', 19, 'RMAP(HUWELIJKEN(), h, $h.omschrijving_locatie_einde)');
INSERT INTO expressie VALUES (55, 'BLRegioEinde', '1.5.0-SNAPSHOT', 19, 'RMAP(HUWELIJKEN(), h, $h.buitenlandse_regio_einde)');
INSERT INTO expressie VALUES (56, 'WplEinde', '1.5.0-SNAPSHOT', 19, 'RMAP(HUWELIJKEN(), h, $h.woonplaats_einde)');
INSERT INTO expressie VALUES (57, 'BLPlaatsEinde', '1.5.0-SNAPSHOT', 19, 'RMAP(HUWELIJKEN(), h, $h.buitenlandse_plaats_einde)');
INSERT INTO expressie VALUES (58, 'DatEinde', '1.5.0-SNAPSHOT', 19, 'RMAP(HUWELIJKEN(), h, $h.datum_einde)');
INSERT INTO expressie VALUES (59, 'GemEinde', '1.5.0-SNAPSHOT', 19, 'RMAP(HUWELIJKEN(), h, $h.gemeente_einde)');
INSERT INTO expressie VALUES (60, 'LandAanv', '1.5.0-SNAPSHOT', 19, 'RMAP(HUWELIJKEN(), h, $h.land_aanvang)');
INSERT INTO expressie VALUES (65, 'LandEinde', '1.5.0-SNAPSHOT', 19, 'RMAP(HUWELIJKEN(), h, $h.land_einde)');
INSERT INTO expressie VALUES (71, 'RdnEinde', '1.5.0-SNAPSHOT', 19, 'RMAP(HUWELIJKEN(), h, $h.reden_einde)');
INSERT INTO expressie VALUES (74, 'BLRegioAanv', '1.5.0-SNAPSHOT', 19, 'RMAP(HUWELIJKEN(), h, $h.buitenlandse_regio_aanvang)');
INSERT INTO expressie VALUES (76, 'OmsLocAanv', '1.5.0-SNAPSHOT', 19, 'RMAP(HUWELIJKEN(), h, $h.omschrijving_locatie_aanvang)');
INSERT INTO expressie VALUES (77, 'BLPlaatsAanv', '1.5.0-SNAPSHOT', 19, 'RMAP(HUWELIJKEN(), h, $h.buitenlandse_plaats_aanvang)');
INSERT INTO expressie VALUES (78, 'WplAanv', '1.5.0-SNAPSHOT', 19, 'RMAP(HUWELIJKEN(), h, $h.woonplaats_aanvang)');
INSERT INTO expressie VALUES (80, 'GemAanv', '1.5.0-SNAPSHOT', 19, 'RMAP(HUWELIJKEN(), h, $h.gemeente_aanvang)');
INSERT INTO expressie VALUES (81, 'DatAanv', '1.5.0-SNAPSHOT', 19, 'RMAP(HUWELIJKEN(), h, $h.datum_aanvang)');
