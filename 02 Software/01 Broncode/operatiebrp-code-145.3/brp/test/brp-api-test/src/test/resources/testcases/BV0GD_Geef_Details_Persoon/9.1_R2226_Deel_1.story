Meta:

@status             Klaar
@usecase            BV.0.GD
@sleutelwoorden     Geef Details Persoon
@regels             R2226
@regelversie        5

Narrative:
R2226
Indien in het bericht
Bericht.Historievorm de waarde "MaterieelFormeel" heeft

OF

Bericht.Historievorm is leeg (of komt niet voor in het verzoekbericht)

dan worden alle voorkomens geleverd die:

in het systeem geregistreerd waren tot en met Bericht.Peilmoment formeel resultaat

EN

Geldig (R2129) waren op enige periode tot en met Bericht.Peilmoment materieel resultaat.

Indien één of beide peilmomenten leeg zijn (of niet voorkomen in het bericht) dan wordt 'Systeemdatum' (R2016) als peilmoment(en) gebruikt.

Indien er sprake is van Datum (deels) onbekend (R1273) dan moet de R1283 - Vergelijken (partiële) datums uitgevoerd worden.

Extra note:
-         Voorkomens met historiepatroon ‘Geen’ voldoen als TsReg <= Peilmoment Formeel Resultaat
-         Voorkomens met historiepatroon ‘Formeel’ voldoen als TsReg <= Peilmoment Formeel Resultaat
-         Voorkomens met historiepatroon ‘Materieel’ voldoen als (TsReg <= Peilmoment Formeel Resultaat) EN (DAG <= Peilmoment Materieel Resultaat )


Scenario: 0a. Controle bericht met historievorm= MaterieelFormeel zonder peilmoment
              LT:

!-- Test om te bepalen dat de expected met historievorm materieelformeel gelijk is aan  historievorm

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Anne_met_Historie2_xls

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|986096969
|historievorm|MaterieelFormeel

Then is het antwoordbericht gelijk aan Expected_R2226_Deel1/00_Controle.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 0b. Controle bericht zonder historievorm
              LT:

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|986096969

Then is het antwoordbericht gelijk aan Expected_R2226_Deel1/00_Controle.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 1.    Historievorm = MaterieelFormeel, Peilmoment materieel resultaat is datum x (01-01-2016), Peilmoment Formeel resultaat is datum y (31-12-2015)
                LT: R2226_LT01

                Verwacht Resultaat:
                Alle voorkomens met een F+M  historie patroon waarvoor geldt dat de DAG <= PeilmomentMateriaal reusltaat
                Alle voorkomens met een F historie patroon waarvoor geldt dat de tsreg <= PeilmomentFormeel reusltaat
                Alle voorkomens zonder historie patroon
                - Oosterhout in het bericht; DAG ligt voor peilmoment
                - Utrecht in het bericht; DAG ligt voor peilmoment
                - 's-Gravenhage NIET in bericht; DAG ligt op peilmoment, maar tsreg ligt na peilmoment formeel resultaat
                - Uithoorn NIET in bericht; DAG (20160102) ligt na peilmoment
                - Onderzoek NIET in bericht; wijst naar adres voorkomen Uithoorn en die is niet aanwezig in bericht

| WOONPLAATS    | DAG      | DEG      | TSREG                    | TSVERVAL |
| Oosterhout    | 20101231 | 20151231 | 2010-12-31T01:00:00.000Z |          |
| Utrecht       | 20151231 | 20160101 | 2015-12-31T01:00:00.000Z |          |
| 's-Gravenhage | 20160101 | 20160102 | 2016-01-01T01:00:00.000Z |          |
| Uithoorn      | 20160102 | NULL     | 2016-01-02T01:00:00.000Z |          |

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Anne_met_Historie2_xls
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|986096969|2015-12-25 T16:11:21Z

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|986096969
|historievorm|MaterieelFormeel
|peilmomentMaterieelResultaat|'2016-01-01'
|peilmomentFormeelResultaat|'2015-12-31T23:59:00'

Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 2 groepen 'adres'
Then is er voor xpath //brp:adres[brp:woonplaatsnaam = 'Oosterhout'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:adres[brp:woonplaatsnaam = 'Utrecht'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:adres/brp:woonplaatsnaam[contains(text(),'Gravenhage')] geen node aanwezig in het antwoord bericht
Then is er voor xpath //brp:adres[brp:woonplaatsnaam = 'Uithoorn'] geen node aanwezig in het antwoord bericht
Then is er voor xpath //brp:onderzoeken geen node aanwezig in het antwoord bericht

Then is het antwoordbericht gelijk aan Expected_R2226_Deel1/01.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 2.    Historievorm = MaterieelFormeel, Peilmoment materieel resultaat is datum x (01-01-2016), Peilmoment Formeel resultaat is datum y (01-01-2016)
                LT: R2226_LT02
                Verwacht Resultaat
                - Oosterhout in het bericht
                - Utrecht    in het bericht
                - 's-Gravenhage  in bericht
                - Uithoorn niet in bericht
                Toelichting:
                - Oosterhout materieel in resultaat doordat voorkomen ooit geldig is geweest voor Peilmoment materieel resultaat en formeel peilmoment
                - Oosterhout formeel in resultaat doordat tijdstipregistratie voor peilmoment formeel resultaat ligt
                - Utrecht materieel in resultaat doordat voorkomen ooit geldig is geweest voor Peilmoment materieel resultaat en formeel peilmoment
                - Utrecht formeel in resultaat doordat tijdstipregistratie voor peilmoment formeel resultaat ligt
                - 's-Gravenhage materieel in resultaat doordat voorkomen ooit geldig is geweest voor Peilmoment materieel resultaat en formeel peilmoment
                - 's-Gravenhage formeel in resultaat doordat tijdstipregistratie voor peilmoment formeel resultaat ligt
                - Uithoorn niet in bericht, doordat datum aanvang geldigheid > Peilmoment Materieel resultaat EN
                  tijdstip registratie > Peilmoment Formeel resultaat

| WOONPLAATS    | DAG      | DEG      | TSREG                    |
| Oosterhout    | 20101231 | 20151231 | 2010-12-31T01:00:00.000Z |
| Utrecht       | 20151231 | 20160101 | 2015-12-31T01:00:00.000Z |
| 's-Gravenhage | 20160101 | 20160102 | 2016-01-01T01:00:00.000Z |
| Uithoorn      | 20160102 | NULL     | 2016-01-02T01:00:00.000Z |

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Anne_met_Historie2_xls

Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|986096969|2014-12-31 T23:59:00Z

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|986096969
|historievorm|MaterieelFormeel
|peilmomentMaterieelResultaat|'2016-01-01'
|peilmomentFormeelResultaat|'2016-01-01T23:59:00'

Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 3 groepen 'adres'
Then is er voor xpath //brp:adres[brp:woonplaatsnaam = 'Oosterhout'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:adres[brp:woonplaatsnaam = 'Utrecht'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:adres/brp:woonplaatsnaam[contains(text(),'Gravenhage')] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:adres[brp:woonplaatsnaam = 'Uithoorn'] geen node aanwezig in het antwoord bericht

Then is het antwoordbericht gelijk aan Expected_R2226_Deel1/02.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 3.    Historievorm = MaterieelFormeel, Peilmoment materieel resultaat is datum x (01-01-2016), Peilmoment Formeel resultaat is datum y (02-01-2016)
                LT: R2226_LT03
                Verwacht Resultaat
                - Oosterhout in het bericht
                - Utrecht in het bericht
                - 's-Gravenhage in het bericht
                - Uithoorn niet in bericht
                Uitleg waarom:
                - Oosterhout materieel in resultaat doordat voorkomen ooit geldig is geweest voor Peilmoment materieel resultaat en formeel peilmoment
                - Oosterhout formeel in resultaat doordat tijdstipregistratie voor peilmoment formeel resultaat ligt
                - Utrecht materieel in resultaat doordat voorkomen ooit geldig is geweest voor Peilmoment materieel resultaat en formeel peilmoment
                - Utrecht formeel in resultaat doordat tijdstipregistratie voor peilmoment formeel resultaat ligt
                - 's-Gravenhage materieel in resultaat doordat voorkomen ooit geldig is geweest voor Peilmoment materieel resultaat en formeel peilmoment
                - 's-Gravenhage formeel in resultaat doordat tijdstipregistratie voor peilmoment formeel resultaat ligt
                - Uithoorn niet in bericht, doordat datum aanvang geldigheid > Peilmoment Materieel resultaat EN
                  tijdstip registratie = Peilmoment Formeel resultaat

| WOONPLAATS    | DAG      | DEG      | TSREG                    |
| Oosterhout    | 20101231 | 20151231 | 2010-12-31T01:00:00.000Z |
| Utrecht       | 20151231 | 20160101 | 2015-12-31T01:00:00.000Z |
| 's-Gravenhage | 20160101 | 20160102 | 2016-01-01T01:00:00.000Z |
| Uithoorn      | 20160102 | NULL     | 2016-01-02T01:00:00.000Z |

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Anne_met_Historie2_xls

Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|986096969|2014-12-31 T23:59:00Z

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|986096969
|historievorm|MaterieelFormeel
|peilmomentMaterieelResultaat|'2016-01-01'
|peilmomentFormeelResultaat|'2016-01-02T23:59:00'

Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 3 groepen 'adres'
Then is er voor xpath //brp:adres[brp:woonplaatsnaam = 'Oosterhout'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:adres[brp:woonplaatsnaam = 'Utrecht'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:adres/brp:woonplaatsnaam[contains(text(),'Gravenhage')] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:adres[brp:woonplaatsnaam = 'Uithoorn'] geen node aanwezig in het antwoord bericht

Then is het antwoordbericht gelijk aan Expected_R2226_Deel1/03.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 4.   Historievorm = MaterieelFormeel, Peilmoment materieel resultaat is LEEG, Peilmoment Formeel resultaat is datum y (31-12-2015)
                LT: R2226_LT04
                Verwacht Resultaat
                - Oosterhout in het bericht
                - Utrecht in het bericht
                - 's-Gravenhage niet in bericht
                - Uithoorn niet in bericht
                Uitleg waarom:
                - Oosterhout materieel in resultaat doordat voorkomen ooit geldig is geweest voor systeemdatum en formeel peilmoment
                - Oosterhout formeel in resultaat doordat tijdstipregistratie voor peilmoment formeel resultaat ligt
                - Utrecht materieel in resultaat doordat voorkomen ooit geldig is geweest voor systeemdatum en formeel peilmoment
                - Utrecht formeel in resultaat doordat tijdstipregistratie VOOR peilmoment formeel resultaat ligt
                - 's Gravenhage niet in bericht want tsreg ligt na peilmoment
                - Uithoorn niet in bericht want tsreg ligt na peil moment

| WOONPLAATS    | DAG      | DEG      | TSREG                    |
| Oosterhout    | 20101231 | 20151231 | 2010-12-31T01:00:00.000Z |
| Utrecht       | 20151231 | 20160101 | 2015-12-31T01:00:00.000Z |
| 's-Gravenhage | 20160101 | 20160102 | 2016-01-01T01:00:00.000Z |
| Uithoorn      | 20160102 | NULL     | 2016-01-02T01:00:00.000Z |

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Anne_met_Historie2_xls

Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|986096969|2014-12-31 T23:59:00Z

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|986096969
|historievorm|MaterieelFormeel
|peilmomentFormeelResultaat|'2015-12-31T23:59:00'

Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 2 groepen 'adres'
Then is er voor xpath //brp:adres[brp:woonplaatsnaam = 'Oosterhout'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:adres[brp:woonplaatsnaam = 'Utrecht'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:adres/brp:woonplaatsnaam[contains(text(),'Gravenhage')] geen node aanwezig in het antwoord bericht
Then is er voor xpath //brp:adres[brp:woonplaatsnaam = 'Uithoorn'] geen node aanwezig in het antwoord bericht

Then is het antwoordbericht gelijk aan Expected_R2226_Deel1/04.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 5.   Historievorm = MaterieelFormeel, Peilmoment materieel resultaat is LEEG, Peilmoment Formeel resultaat is datum y (01-01-2016)
                LT: R2226_LT05
                Verwacht Resultaat
                - Oosterhout in het bericht
                - Utrecht in het bericht
                - 's-Gravenhage niet in bericht
                - Uithoorn niet in bericht
                Uitleg waarom:
                - Oosterhout materieel in resultaat doordat voorkomen ooit geldig is geweest voor systeemdatum en formeel peilmoment
                - Oosterhout formeel in resultaat doordat tijdstipregistratie voor peilmoment formeel resultaat ligt
                - Utrecht materieel in resultaat doordat voorkomen ooit geldig is geweest voor systeemdatum en formeel peilmoment
                - Utrecht formeel in resultaat doordat tijdstipregistratie voor peilmoment formeel resultaat ligt
                - 's-Gravenhage materieel in resultaat doordat voorkomen ooit geldig is geweest voor systeemdatum en formeel peilmoment
                - 's-Gravenhage formeel in resultaat doordat tijdstipregistratie VOOR peilmoment formeel resultaat ligt
                - Uithoorn niet in bericht, doordat datum aanvang geldigheid EN tijdstip registratie > Peilmoment Formeel resultaat

| WOONPLAATS    | DAG      | DEG      | TSREG                    |
| Oosterhout    | 20101231 | 20151231 | 2010-12-31T01:00:00.000Z |
| Utrecht       | 20151231 | 20160101 | 2015-12-31T01:00:00.000Z |
| 's-Gravenhage | 20160101 | 20160102 | 2016-01-01T01:00:00.000Z |
| Uithoorn      | 20160102 | NULL     | 2016-01-02T01:00:00.000Z |

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Anne_met_Historie2_xls

Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|986096969|2014-12-31 T23:59:00Z

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|986096969
|historievorm|MaterieelFormeel
|peilmomentFormeelResultaat|'2016-01-01T23:59:00'

Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 3 groepen 'adres'
Then is er voor xpath //brp:adres[brp:woonplaatsnaam = 'Oosterhout'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:adres[brp:woonplaatsnaam = 'Utrecht'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:adres/brp:woonplaatsnaam[contains(text(),'Gravenhage')] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:adres[brp:woonplaatsnaam = 'Uithoorn'] geen node aanwezig in het antwoord bericht

Then is het antwoordbericht gelijk aan Expected_R2226_Deel1/05.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 6.   Historievorm = MaterieelFormeel, Peilmoment materieel resultaat LEEG, Peilmoment Formeel resultaat is datum y (02-01-2016)
                LT: R2226_LT06
                Verwacht Resultaat
                - Oosterhout in het bericht
                - Utrecht in het bericht
                - 's-Gravenhage niet in bericht
                - Uithoorn niet in bericht
                Uitleg waarom:
                - Oosterhout materieel in resultaat doordat voorkomen ooit geldig is geweest voor systeemdatum en formeel peilmoment
                - Oosterhout formeel in resultaat doordat tijdstipregistratie voor peilmoment formeel resultaat ligt
                - Utrecht materieel in resultaat doordat voorkomen ooit geldig is geweest voor systeemdatum en formeel peilmoment
                - Utrecht formeel in resultaat doordat tijdstipregistratie voor peilmoment formeel resultaat ligt
                - 's-Gravenhage materieel in resultaat doordat voorkomen ooit geldig is geweest voor systeemdatum en formeel peilmoment
                - 's-Gravenhage formeel in resultaat doordat tijdstipregistratie voor peilmoment formeel resultaat ligt
                - Uithoorn materieel in resultaat doordat voorkomen ooit geldig is geweest voor systeemdatum op Peilmoment Formeel resultaat

| WOONPLAATS    | DAG      | DEG      | TSREG                    |
| Oosterhout    | 20101231 | 20151231 | 2010-12-31T01:00:00.000Z |
| Utrecht       | 20151231 | 20160101 | 2015-12-31T01:00:00.000Z |
| 's-Gravenhage | 20160101 | 20160102 | 2016-01-01T01:00:00.000Z |
| Uithoorn      | 20160102 | NULL     | 2016-01-02T01:00:00.000Z |

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Anne_met_Historie2_xls
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|986096969|2014-12-31 T23:59:00Z

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|986096969
|historievorm|MaterieelFormeel
|peilmomentFormeelResultaat|'2016-01-02T23:59:00'

Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 4 groepen 'adres'
Then is er voor xpath //brp:adres[brp:woonplaatsnaam = 'Oosterhout'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:adres[brp:woonplaatsnaam = 'Utrecht'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:adres/brp:woonplaatsnaam[contains(text(),'Gravenhage')] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:adres[brp:woonplaatsnaam = 'Uithoorn'] een node aanwezig in het antwoord bericht

Then is het antwoordbericht gelijk aan Expected_R2226_Deel1/06.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 7.    Historievorm = MaterieelFormeel, Peilmoment materieel resultaat is datum x (31-12-2015), Peilmoment Formeel resultaat is LEEG
                LT: R2226_LT07
                Verwacht Resultaat
                - Oosterhout in het bericht
                - Utrecht in het bericht
                - 's-Gravenhage niet in bericht
                - Uithoorn niet in bericht
                Uitleg waarom:
                - Oosterhout materieel in resultaat doordat voorkomen ooit geldig is geweest voor Peilmoment materieel resultaat en systeemdatum
                - Oosterhout formeel in resultaat doordat tijdstipregistratie voor peilmoment formeel resultaat ligt
                - Utrecht materieel in resultaat doordat voorkomen geldig is op voor Peilmoment materieel resultaat en systeemdatum
                - Utrecht formeel in resultaat doordat tijdstipregistratie voor systeemdatum ligt
                - 's-Gravenhage niet in bericht, doordat datum aanvang geldigheid > Peilmoment Materieel resultaat

| WOONPLAATS    | DAG      | DEG      | TSREG                    |
| Oosterhout    | 20101231 | 20151231 | 2010-12-31T01:00:00.000Z |
| Utrecht       | 20151231 | 20160101 | 2015-12-31T01:00:00.000Z |
| 's-Gravenhage | 20160101 | 20160102 | 2016-01-01T01:00:00.000Z |
| Uithoorn      | 20160102 | NULL     | 2016-01-02T01:00:00.000Z |

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Anne_met_Historie2_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|986096969
|historievorm|MaterieelFormeel
|peilmomentMaterieelResultaat|'2015-12-31'

Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 2 groepen 'adres'
Then is er voor xpath //brp:adres[brp:woonplaatsnaam = 'Oosterhout'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:adres[brp:woonplaatsnaam = 'Utrecht'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:adres/brp:woonplaatsnaam[contains(text(),'Gravenhage')] geen node aanwezig in het antwoord bericht
Then is er voor xpath //brp:adres[brp:woonplaatsnaam = 'Uithoorn'] geen node aanwezig in het antwoord bericht

Then is het antwoordbericht gelijk aan Expected_R2226_Deel1/07.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 8.    Historievorm = MaterieelFormeel, Peilmoment materieel resultaat is datum x (01-01-2016), Peilmoment Formeel resultaat LEEG
                LT: R2226_LT08
                Verwacht Resultaat
                - Oosterhout in het bericht
                - Utrecht in het bericht
                - 's-Gravenhage in bericht
                - Uithoorn niet in bericht
                Uitleg waarom:
                - Oosterhout materieel in resultaat doordat voorkomen ooit geldig is geweest voor Peilmoment materieel resultaat en systeemdatum
                - Oosterhout formeel in resultaat doordat tijdstipregistratie voor peilmoment formeel resultaat ligt
                - Utrecht materieel in resultaat doordat voorkomen geldig is op voor Peilmoment materieel resultaat en systeemdatum
                - Utrecht formeel in resultaat doordat tijdstipregistratie voor systeemdatum ligt
                - 's-Gravenhage materieel in resultaat doordat voorkomen geldig is op voor Peilmoment materieel resultaat en systeemdatum
                - 's-Gravenhage formeel in resultaat doordat tijdstipregistratie voor systeemdatum ligt
                - Uithoorn niet in bericht want datum aanvang geldigheid > Peilmoment materieel resulaat

| WOONPLAATS    | DAG      | DEG      | TSREG                    |
| Oosterhout    | 20101231 | 20151231 | 2010-12-31T01:00:00.000Z |
| Utrecht       | 20151231 | 20160101 | 2015-12-31T01:00:00.000Z |
| 's-Gravenhage | 20160101 | 20160102 | 2016-01-01T01:00:00.000Z |
| Uithoorn      | 20160102 | NULL     | 2016-01-02T01:00:00.000Z |

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Anne_met_Historie2_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|986096969
|historievorm|MaterieelFormeel
|peilmomentMaterieelResultaat|'2016-01-01'

Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 3 groepen 'adres'
Then is er voor xpath //brp:adres[brp:woonplaatsnaam = 'Oosterhout'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:adres[brp:woonplaatsnaam = 'Utrecht'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:adres/brp:woonplaatsnaam[contains(text(),'Gravenhage')] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:adres[brp:woonplaatsnaam = 'Uithoorn'] geen node aanwezig in het antwoord bericht

Then is het antwoordbericht gelijk aan Expected_R2226_Deel1/08.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 9.  Historievorm = MaterieelFormeel, Peilmoment materieel resultaat is datum x (02-01-2016), Peilmoment Formeel resultaat is LEEG
                LT: R2226_LT09
                Verwacht Resultaat
                - Oosterhout in het bericht
                - Utrecht in het bericht
                - 's-Gravenhage in het bericht
                - Uithoorn  in bericht
                Uitleg waarom:
                - Oosterhout materieel in resultaat doordat voorkomen ooit geldig is geweest voor Peilmoment materieel resultaat en systeemdatum
                - Oosterhout formeel in resultaat doordat tijdstipregistratie voor peilmoment formeel resultaat ligt
                - Utrecht materieel in resultaat doordat voorkomen geldig is op voor Peilmoment materieel resultaat en systeemdatum
                - Utrecht formeel in resultaat doordat tijdstipregistratie voor systeemdatum ligt
                - 's-Gravenhage materieel in resultaat doordat voorkomen geldig is op voor Peilmoment materieel resultaat en systeemdatum
                - 's-Gravenhage formeel in resultaat doordat tijdstipregistratie voor systeemdatum ligt
                - Uithoorn materieel in resultaat doordat voorkomen geldig is op voor Peilmoment materieel resultaat en systeemdatum

| WOONPLAATS    | DAG      | DEG      | TSREG                    |
| Oosterhout    | 20101231 | 20151231 | 2010-12-31T01:00:00.000Z |
| Utrecht       | 20151231 | 20160101 | 2015-12-31T01:00:00.000Z |
| 's-Gravenhage | 20160101 | 20160102 | 2016-01-01T01:00:00.000Z |
| Uithoorn      | 20160102 | NULL     | 2016-01-02T01:00:00.000Z |

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Anne_met_Historie2_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|986096969
|historievorm|MaterieelFormeel
|peilmomentMaterieelResultaat|'2016-01-02'

Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 4 groepen 'adres'
Then is er voor xpath //brp:adres[brp:woonplaatsnaam = 'Oosterhout'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:adres[brp:woonplaatsnaam = 'Utrecht'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:adres/brp:woonplaatsnaam[contains(text(),'Gravenhage')] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:adres[brp:woonplaatsnaam = 'Uithoorn'] een node aanwezig in het antwoord bericht

Then is het antwoordbericht gelijk aan Expected_R2226_Deel1/09.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 10.   Historievorm = MaterieelFormeel, Peilmoment materieel resultaat is LEEG, Peilmoment Formeel resultaat is LEEG
                LT: R2226_LT10
                Verwacht Resultaat
                - Oosterhout in het bericht
                - Utrecht in het bericht
                - 's-Gravenhage in het bericht
                - Uithoorn in bericht
                Uitleg waarom:
                - Oosterhout materieel in resultaat doordat voorkomen ooit geldig is geweest voor Peilmoment materieel resultaat en systeemdatum
                - Oosterhout formeel in resultaat doordat tijdstipregistratie voor peilmoment formeel resultaat ligt
                - Utrecht materieel in resultaat doordat voorkomen geldig is voor systeemdatum
                - Utrecht formeel in resultaat doordat tijdstipregistratie voor systeemdatum ligt
                - 's-Gravenhage materieel in resultaat doordat voorkomen geldig is voor systeemdatum
                - 's-Gravenhage formeel in resultaat doordat tijdstipregistratie voor systeemdatum ligt
                - Uithoorn materieel in resultaat doordat voorkomen geldig is voor systeemdatum

| WOONPLAATS    | DAG      | DEG      | TSREG                    |
| Oosterhout    | 20101231 | 20160000 | 2010-12-31T01:00:00.000Z |
| Utrecht       | 20160000 | 20160100 | 2016-01-01T01:00:00.000Z |
| 's-Gravenhage | 20160100 | 20160200 | 2016-01-01T01:00:00.000Z |
| Uithoorn      | 20160200 | NULL     | 2016-02-01T01:00:00.000Z |

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Anne_met_Historie2_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|986096969
|historievorm|MaterieelFormeel

Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 4 groepen 'adres'
Then is er voor xpath //brp:adres[brp:woonplaatsnaam = 'Oosterhout'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:adres[brp:woonplaatsnaam = 'Utrecht'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:adres/brp:woonplaatsnaam[contains(text(),'Gravenhage')] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:adres[brp:woonplaatsnaam = 'Uithoorn'] een node aanwezig in het antwoord bericht

Then is het antwoordbericht gelijk aan Expected_R2226_Deel1/10.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 11.   Historievorm = MaterieelFormeel, Peilmoment materieel resultaat is LEEG, Peilmoment Formeel resultaat is 2016-01-31
                LT: R2226_LT11
                Verwacht Resultaat
                - Oosterhout in het bericht
                - Utrecht in het bericht (volgens de soepele vergelijking mogelijk waar)
                - 's-Gravenhage in het bericht (volgens de soepele vergelijking mogelijk waar)
                - Uithoorn NIET in het bericht (volgens de soepele vergelijking NIET waar)

| WOONPLAATS    | DAG      | DEG      | TSREG                    |
| Oosterhout    | 20101231 | 20160000 | 2010-12-31T01:00:00.000Z |
| Utrecht       | 20160000 | 20160100 | 2016-01-01T01:00:00.000Z |
| 's-Gravenhage | 20160100 | 20160200 | 2016-01-01T01:00:00.000Z |
| Uithoorn      | 20160200 | NULL     | 2016-02-01T01:00:00.000Z |

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Anne_met_Historie_gedeeltelijk_onb_dat_xls
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|986096969|2014-12-31 T23:59:00Z

Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|986096969
|historievorm|MaterieelFormeel
|peilmomentFormeelResultaat|'2016-01-31T23:59:00'

Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 3 groepen 'adres'
Then is er voor xpath //brp:adres[brp:woonplaatsnaam = 'Oosterhout'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:adres[brp:woonplaatsnaam = 'Utrecht'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:adres/brp:woonplaatsnaam[contains(text(),'Gravenhage')] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:adres[brp:woonplaatsnaam = 'Uithoorn'] geen node aanwezig in het antwoord bericht

Then is het antwoordbericht gelijk aan Expected_R2226_Deel1/11.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario: 12.   Historievorm = MaterieelFormeel, Peilmoment materieel resultaat is 2016-01-31, Peilmoment Formeel resultaat is Leeg
                LT: R2226_LT12
                Uitwerking:
                - Oorspronkelijke woonplaats Oosterhout
                - Verhuizing naar Utrecht   op 00-00-2016
                - Verhuizing naar 's-Gravenhage op 00-01-2016
                - Verhuizing naar Uithoorn  op 00-02-2016
                Verwacht Resultaat
                - Oosterhout in het bericht
                - Utrecht in het bericht (volgens de soepele vergelijking mogelijk waar)
                - 's-Gravenhage in het bericht (volgens de soepele vergelijking mogelijk waar)
                - Uithoorn NIET in het bericht (volgens de soepele vergelijking NIET waar)

Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Anne_met_Historie_gedeeltelijk_onb_dat_xls
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|986096969
|historievorm|MaterieelFormeel
|peilmomentMaterieelResultaat|'2016-01-31'

Then heeft het antwoordbericht verwerking Geslaagd

Then heeft het antwoordbericht 3 groepen 'adres'
Then is er voor xpath //brp:adres[brp:woonplaatsnaam = 'Oosterhout'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:adres[brp:woonplaatsnaam = 'Utrecht'] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:adres/brp:woonplaatsnaam[contains(text(),'Gravenhage')] een node aanwezig in het antwoord bericht
Then is er voor xpath //brp:adres[brp:woonplaatsnaam = 'Uithoorn'] geen node aanwezig in het antwoord bericht

Then is het antwoordbericht gelijk aan Expected_R2226_Deel1/12.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario:  13.1 MaterieelFormeel voor groepen met alleen formeel historie patroon (geboorte), Formeel en Materieel peilmoment op 01-01-2016
                LT: R2226_LT14
                Verwacht resultaat:
                - Kind 1 in bericht (geboortedatum 01-01-2010) (TsReg 01-01-2010)
                - Kind 2 niet in bericht (geboortedatum 01-01-2015) (TsReg 01-01-2015)


Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Anne_met_Historie2_xls
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|986096969|2014-12-30 T23:59:00Z
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|986096969
|historievorm|MaterieelFormeel
|peilmomentMaterieelResultaat|'2014-12-31'
|peilmomentFormeelResultaat|'2014-12-31T23:59:00'

Then heeft het antwoordbericht verwerking Geslaagd

!-- R2226_LT14 Alleen kindje 1 in het bericht, dus 4x samengestelde naam
!-- Samengestelde naam 1 is UC_Kenny
!-- Samengestelde naam 2 is Vader-UC_Kenny
!-- Samengestelde naam 3 is Moeder-UC_Kenny
!-- Samengestelde naam 4 is UC_Kindje Anita
!-- Kind Wilma niet in het bericht
Then heeft het antwoordbericht 4 groepen 'samengesteldeNaam'
Then is in antwoordbericht de aanwezigheid van 'voornamen' in 'samengesteldeNaam' nummer 4 ja

Then is het antwoordbericht gelijk aan Expected_R2226_Deel1/13_1.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R

Scenario:  13.2 MaterieelFormeel voor groepen met alleen formeel historie patroon (geboorte), Formeel en Materieel peilmoment op 01-01-2016
                LT: R2226_LT14
                Verwacht resultaat:
                - Kind 1 in bericht (geboortedatum 01-01-2010) (TsReg 01-01-2010)
                - Kind 2 niet in bericht (geboortedatum 01-01-2015) (TsReg 01-01-2015)


Given leveringsautorisatie uit autorisatie/Geen_pop.bep_levering_op_basis_van_afnemerindicatie
Given persoonsbeelden uit specials:specials/Anne_met_Historie2_xls
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|986096969|2009-12-30 T23:59:00Z
Given verzoek geef details persoon:
|key|value
|leveringsautorisatieNaam|'Geen pop.bep. levering op basis van afnemerindicatie'
|zendendePartijNaam|'Gemeente Utrecht'
|bsn|986096969
|historievorm|MaterieelFormeel
|peilmomentMaterieelResultaat|'2009-12-31'
|peilmomentFormeelResultaat|'2009-12-31T23:59:00'

Then heeft het antwoordbericht verwerking Geslaagd

!-- R2226_LT14 Geen kinderen in bericht, dus maar 3x samengestelde naam
!-- Samengestelde naam 1 is UC_Kenny
!-- Samengestelde naam 2 is Vader-UC_Kenny
!-- Samengestelde naam 3 is Moeder-UC_Kenny
!-- UC_Kindje Anita niet in het bericht
!-- Kind Wilma niet in het bericht
Then heeft het antwoordbericht 3 groepen 'samengesteldeNaam'
Then is in antwoordbericht de aanwezigheid van 'voornamen' in 'samengesteldeNaam' nummer 4 nee

Then is het antwoordbericht gelijk aan Expected_R2226_Deel1/13_2.xml voor expressie //brp:lvg_bvgGeefDetailsPersoon_R