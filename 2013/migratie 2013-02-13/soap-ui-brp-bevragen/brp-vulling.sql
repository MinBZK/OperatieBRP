INSERT INTO autaut.toestand (id, naam, oms) VALUES 
(1,'Concept', 'Initiële versie, nog niet actief.'),
(2,'Te fiatteren','Ingevoerd, maar nog niet door Stelselbeheerder (dan wel tweede paar ogen bij Stelselbeheerder) gefiatteerd.'),
(3,'Te verbeteren','Bekeken, maar niet correct bevonden.'),
(4,'Definitief','Definitief, bruikbaar voor toegangsbewaking.');

INSERT INTO lev.srtlev (id, naam) VALUES
(1,'BRP_BEVRAGING'),
(2,'BRP_MUTATIE'),
(3,'BRP_SELECTIE');

INSERT INTO lev.srtabonnement(id, naam) VALUES
(1,'Levering');

INSERT INTO ber.richting(id, naam, oms) VALUES
(1,'Ingaand','Naar de centrale voorzieningen van de BRP toe.'),
(2,'Uitgaand','Van de centrale voorzieningen van de BRP af.');

INSERT INTO ber.srtber(id, naam) VALUES
(1,'OpvragenPersoonVraag'),
(2,'OpvragenPersoonAntwoord');

INSERT INTO autaut.srtautorisatiebesluit(id, naam, oms, dataanvgel) VALUES
(1,'Leveringsautorisatie','00000000','20110101');

INSERT INTO autaut.protocolleringsniveau(id, code, naam, oms, dataanvgel) VALUES
(1,1,'Geen beperking','Geen beperking','20110101'),
(2,2,'Beperking','Beperking','20110101'),
(3,3,'Geheim','Geheim','20110101');

INSERT INTO autaut.functie(id,naam) VALUES
(1,'functie-1');

INSERT INTO autaut.certificaat (subject, serial, signature) VALUES
('CN=soap ui, OU=Unknown, O=Unknown, L=Unknown, ST=Unknown, C=Unknown',
1337857100,
'30818902818100b9ef4b5b544c881a4868fefec90a0b9bcb8f06ca34ef71585840baef6519d454aa8a1ab1e88e6497fb909a8202e333df7e8d0242bae54499a63ed152678f07c6be3ae2a2f92a76a9963af99a9400e87dbb5758b31893dd9e430a6175daab81f1857cae2eea6949b72432d0f2cf1e032eacbea673a89474e720095cec1dd7df5f0203010001'
)

INSERT INTO autaut.authenticatiemiddel (partij, rol, functie, certificaattbvondertekening, authenticatiemiddelstatushis) VALUES
(1,1,1,1,'A');

﻿--DELETE FROM autaut.autorisatiebesluit;
INSERT INTO autaut.autorisatiebesluit (id, srt, besluittekst, autoriseerder, indmodelbesluit, indingetrokken, autorisatiebesluitstatushis, bijhautorisatiebesluitstatus, toestand) VALUES
(1,1,'Het mag', 1, false, false, 'A', 'A', 4);

--DELETE FROM autaut.doelbinding;
INSERT INTO autaut.doelbinding (id, levsautorisatiebesluit, geautoriseerde, protocolleringsniveau, tekstdoelbinding, indverstrbeperkinghonoreren, doelbindingstatushis) VALUES
(1,1,1,1,'Is goed', false, 'A');

--DELETE FROM lev.abonnement;
INSERT INTO lev.abonnement(id, doelbinding, srtabonnement, abonnementstatushis) VALUES
(1,1,1,'A');

--DELETE FROM lev.abonnementsrtber;
INSERT INTO lev.abonnementsrtber (id, abonnement, srtber, abonnementsrtberstatushis) VALUES
(1,1,1,'A');

--DELETE FROM kern.dbobject;
INSERT INTO kern.dbobject (id, naam, srt, javaidentifier, dataanvgel) VALUES
(3010,'Pers',1,'Persoon',20120101),
(3237,'PersAdres',1,'PersoonAdres',20120101);

INSERT INTO kern.dbobject(id,naam,srt,ouder,javaidentifier,dataanvgel) VALUES
(5398,'AanschrStatusHis',2,3010,'AanschrijvingStatusHis',20120101),
(1968,'AdellijkeTitel',2,3010,'AdellijkeTitel',20120101),
(3013,'ANr',2,3010,'Administratienummer',20120101),
(3573,'Bijhgem',2,3010,'Bijhoudingsgemeente',20120101),
(5406,'BijhgemStatusHis',2,3010,'BijhoudingsgemeenteStatusHis',20120101),
(5404,'BijhverantwoordelijkheidStat',2,3010,'BijhoudingsverantwoordelijkheidStatusHis',20120101),
(3677,'BLGeboorteplaats',2,3010,'BuitenlandseGeboorteplaats',20120101),
(3552,'BLPlaatsOverlijden',2,3010,'BuitenlandsePlaatsOverlijden',20120101),
(3530,'BLRegioGeboorte',2,3010,'BuitenlandseRegioGeboorte',20120101),
(3556,'BLRegioOverlijden',2,3010,'BuitenlandseRegioOverlijden',20120101),
(3018,'BSN',2,3010,'Burgerservicenummer',20120101),
(3562,'DatAanlAanpDeelnEUVerkiezing',2,3010,'DatumAanleidingAanpassingDeelnameEUVerkiezing',20120101),
(3311,'DatAanvAaneenslVerblijfsr',2,3010,'DatumAanvangAaneensluitendVerblijfsrecht',20120101),
(3325,'DatAanvVerblijfsr',2,3010,'DatumAanvangVerblijfsrecht',20120101),
(3564,'DatEindeUitslEUKiesr',2,3010,'DatumEindeUitsluitingEUKiesrecht',20120101),
(3559,'DatEindeUitslNLKiesr',2,3010,'DatumEindeUitsluitingNLKiesrecht',20120101),
(3673,'DatGeboorte',2,3010,'DatumGeboorte',20120101),
(3642,'DatInschrInGem',2,3010,'DatumInschrijvingInGemeente',20120101),
(3570,'DatInschr',2,3010,'DatumInschrijving',20120101),
(3546,'DatOverlijden',2,3010,'DatumOverlijden',20120101),
(3502,'DatVestigingInNederland',2,3010,'DatumVestigingInNederland',20120101),
(3481,'DatVoorzEindeVerblijfsr',2,3010,'DatumVoorzienEindeVerblijfsrecht',20120101),
(5403,'EUVerkiezingenStatusHis',2,3010,'EUVerkiezingenStatusHis',20120101),
(5399,'GeboorteStatusHis',2,3010,'GeboorteStatusHis',20120101),
(3593,'GebrGeslnaamEGP',2,3010,'WijzeVanGebruikGeslachtsnaamEchtgenootGeregistreerdPartner',20120101),
(3675,'GemGeboorte',2,3010,'GemeenteGeboorte',20120101),
(3551,'GemOverlijden',2,3010,'GemeenteOverlijden',20120101),
(3233,'GemPK',2,3010,'GemeentePersoonskaart',20120101),
(3031,'Geslachtsaand',2,3010,'Geslachtsaanduiding',20120101),
(5396,'GeslachtsaandStatusHis',2,3010,'GeslachtsaanduidingStatusHis',20120101),
(3323,'GeslnaamAanschr',2,3010,'GeslachtsnaamAanschrijving',20120101),
(3094,'Geslnaam',2,3010,'Geslachtsnaam',20120101),
(3015,'ID',2,3010,'ID',20120101),
(5395,'IDsStatusHis',2,3010,'IdentificatienummersStatusHis',20120101),
(5408,'ImmigratieStatusHis',2,3010,'ImmigratieStatusHis',20120101),
(3633,'IndAanschrAlgoritmischAfgele',2,3010,'IndicatieAanschrijvingAlgoritmischAfgeleid',20120101),
(3495,'IndAanschrMetAdellijkeTitels',2,3010,'IndicatieAanschrijvenMetAdellijkeTitelsEnOfPredikaten',20120101),
(3914,'IndAlgoritmischAfgeleid',2,3010,'IndicatieAlgoritmischAfgeleid',20120101),
(3320,'IndDeelnEUVerkiezingen',2,3010,'IndicatieDeelnameEUVerkiezingen',20120101),
(3166,'IndGegevensInOnderzoek',2,3010,'IndicatieGegevensInOnderzoek',20120101),
(3592,'IndNreeksAlsGeslnaam',2,3010,'IndicatieNamenreeksAlsGeslachtsnaam',20120101),
(3578,'IndOnverwDocAanw',2,3010,'IndicatieOnverwerktDocumentAanwezig',20120101),
(3313,'IndPKVolledigGeconv',2,3010,'IndicatiePersoonskaartVolledigGeconverteerd',20120101),
(3322,'IndUitslNLKiesr',2,3010,'IndicatieUitsluitingNLKiesrecht',20120101),
(5409,'InschrStatusHis',2,3010,'InschrijvingStatusHis',20120101),
(3543,'LandGeboorte',2,3010,'LandGeboorte',20120101),
(3558,'LandOverlijden',2,3010,'LandOverlijden',20120101),
(3579,'LandVanwaarGevestigd',2,3010,'LandVanwaarGevestigd',20120101),
(3678,'OmsGeboorteloc',2,3010,'OmschrijvingGeboortelocatie',20120101),
(3555,'OmsLocOverlijden',2,3010,'OmschrijvingLocatieOverlijden',20120101),
(5405,'OpschortingStatusHis',2,3010,'OpschortingStatusHis',20120101),
(5400,'OverlijdenStatusHis',2,3010,'OverlijdenStatusHis',20120101),
(5407,'PKStatusHis',2,3010,'PersoonskaartStatusHis',20120101),
(3703,'PredikaatAanschr',2,3010,'PredikaatAanschrijving',20120101),
(1969,'Predikaat',2,3010,'Predikaat',20120101),
(3663,'RdnOpschortingBijhouding',2,3010,'RedenOpschortingBijhouding',20120101),
(5397,'SamengesteldeNaamStatusHis',2,3010,'SamengesteldeNaamStatusHis',20120101),
(3580,'ScheidingstekenAanschr',2,3010,'ScheidingstekenAanschrijving',20120101),
(3253,'Scheidingsteken',2,3010,'Scheidingsteken',20120101),
(1997,'Srt',2,3010,'Soort',20120101),
(3251,'TijdstipLaatsteWijz',2,3010,'TijdstipLaatsteWijziging',20120101),
(5402,'UitslNLKiesrStatusHis',2,3010,'UitsluitingNLKiesrechtStatusHis',20120101),
(3568,'Verantwoordelijke',2,3010,'Verantwoordelijke',20120101),
(3310,'Verblijfsr',2,3010,'Verblijfsrecht',20120101),
(5401,'VerblijfsrStatusHis',2,3010,'VerblijfsrechtStatusHis',20120101),
(3250,'Versienr',2,3010,'Versienummer',20120101),
(3667,'VolgendePers',2,3010,'VolgendePersoon',20120101),
(3319,'VoornamenAanschr',2,3010,'VoornamenAanschrijving',20120101),
(3092,'Voornamen',2,3010,'Voornamen',20120101),
(3355,'VoorvoegselAanschr',2,3010,'VoorvoegselAanschrijving',20120101),
(3309,'Voorvoegsel',2,3010,'Voorvoegsel',20120101),
(3666,'VorigePers',2,3010,'VorigePersoon',20120101),
(3676,'WplGeboorte',2,3010,'WoonplaatsGeboorte',20120101),
(3544,'WplOverlijden',2,3010,'WoonplaatsOverlijden',20120101),
(3301,'AangAdresh',2,3237,'AangeverAdreshouding',20120101),
(5412,'AdreshStatusHis',2,3237,'AdreshoudingStatusHis',20120101),
(3284,'AdresseerbaarObject',2,3237,'AdresseerbaarObject',20120101),
(3267,'AfgekorteNOR',2,3237,'AfgekorteNaamOpenbareRuimte',20120101),
(3291,'BLAdresRegel1',2,3237,'BuitenlandsAdresRegel1',20120101),
(3292,'BLAdresRegel2',2,3237,'BuitenlandsAdresRegel2',20120101),
(3293,'BLAdresRegel3',2,3237,'BuitenlandsAdresRegel3',20120101),
(3709,'BLAdresRegel4',2,3237,'BuitenlandsAdresRegel4',20120101),
(3710,'BLAdresRegel5',2,3237,'BuitenlandsAdresRegel5',20120101),
(3711,'BLAdresRegel6',2,3237,'BuitenlandsAdresRegel6',20120101),
(5414,'BLAdresStatusHis',2,3237,'BuitenlandsAdresStatusHis',20120101),
(3730,'DatAanvAdresh',2,3237,'DatumAanvangAdreshouding',20120101),
(3504,'DatVertrekUitNederland',2,3237,'DatumVertrekUitNederland',20120101),
(3788,'Gem',2,3237,'Gemeente',20120101),
(3265,'Gemdeel',2,3237,'Gemeentedeel',20120101),
(3273,'Huisletter',2,3237,'Huisletter',20120101),
(3271,'Huisnr',2,3237,'Huisnummer',20120101),
(3275,'Huisnrtoevoeging',2,3237,'Huisnummertoevoeging',20120101),
(3239,'ID',2,3237,'ID',20120101),
(3286,'IdentcodeNraand',2,3237,'IdentificatiecodeNummeraanduiding',20120101),
(3289,'Land',2,3237,'Land',20120101),
(3288,'LocOms',2,3237,'LocatieOmschrijving',20120101),
(3278,'LoctovAdres',2,3237,'LocatietovAdres',20120101),
(5413,'NLAdresStatusHis',2,3237,'NederlandsAdresStatusHis',20120101),
(3269,'NOR',2,3237,'NaamOpenbareRuimte',20120101),
(3241,'Pers',2,3237,'Persoon',20120101),
(3281,'Postcode',2,3237,'Postcode',20120101),
(3715,'RdnWijz',2,3237,'RedenWijziging',20120101),
(3263,'Srt',2,3237,'Soort',20120101),
(3282,'Wpl',2,3237,'Woonplaats',20120101);
	

--DELETE FROM lev.abonnementgegevenselement;
INSERT INTO lev.abonnementgegevenselement (id, abonnement, gegevenselement) VALUES
(1968,1,1968),
(1969,1,1969),
(1997,1,1997),
(3013,1,3013),
(3015,1,3015),
(3018,1,3018),
(3031,1,3031),
(3092,1,3092),
(3094,1,3094),
(3166,1,3166),
(3233,1,3233),
(3250,1,3250),
(3251,1,3251),
(3253,1,3253),
(3309,1,3309),
(3310,1,3310),
(3311,1,3311),
(3313,1,3313),
(3319,1,3319),
(3320,1,3320),
(3322,1,3322),
(3323,1,3323),
(3325,1,3325),
(3355,1,3355),
(3481,1,3481),
(3495,1,3495),
(3502,1,3502),
(3530,1,3530),
(3543,1,3543),
(3544,1,3544),
(3546,1,3546),
(3551,1,3551),
(3552,1,3552),
(3555,1,3555),
(3556,1,3556),
(3558,1,3558),
(3559,1,3559),
(3562,1,3562),
(3564,1,3564),
(3568,1,3568),
(3570,1,3570),
(3573,1,3573),
(3578,1,3578),
(3579,1,3579),
(3580,1,3580),
(3592,1,3592),
(3593,1,3593),
(3633,1,3633),
(3642,1,3642),
(3663,1,3663),
(3666,1,3666),
(3667,1,3667),
(3673,1,3673),
(3675,1,3675),
(3676,1,3676),
(3677,1,3677),
(3678,1,3678),
(3703,1,3703),
(3914,1,3914),
(5395,1,5395),
(5396,1,5396),
(5397,1,5397),
(5398,1,5398),
(5399,1,5399),
(5400,1,5400),
(5401,1,5401),
(5402,1,5402),
(5403,1,5403),
(5404,1,5404),
(5405,1,5405),
(5406,1,5406),
(5407,1,5407),
(5408,1,5408),
(5409,1,5409),
(3301,1,3301),
(5412,1,5412),
(3284,1,3284),
(3267,1,3267),
(3291,1,3291),
(3292,1,3292),
(3293,1,3293),
(3709,1,3709),
(3710,1,3710),
(3711,1,3711),
(5414,1,5414),
(3730,1,3730),
(3504,1,3504),
(3788,1,3788),
(3265,1,3265),
(3273,1,3273),
(3271,1,3271),
(3275,1,3275),
(3239,1,3239),
(3286,1,3286),
(3289,1,3289),
(3288,1,3288),
(3278,1,3278),
(5413,1,5413),
(3269,1,3269),
(3241,1,3241),
(3281,1,3281),
(3715,1,3715),
(3263,1,3263),
(3282,1,3282);	

	
	
