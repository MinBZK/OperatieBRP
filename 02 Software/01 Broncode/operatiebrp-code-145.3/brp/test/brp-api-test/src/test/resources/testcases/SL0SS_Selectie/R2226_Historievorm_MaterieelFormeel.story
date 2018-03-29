Meta:

@status             Klaar
@usecase            SL.1.HFS
@sleutelwoorden     Selectie
@regels             R2226


Narrative:
Alleen voorkomens worden geleverd die in het systeem geregistreerd waren tot en met "(Selectie) Peilmoment formeel resultaat"
EN
Geldig (R2129) waren op enige periode tot en met "(Selectie) Peilmoment materieel resultaat".
Indien één of beide peilmomenten leeg zijn (of niet voorkomen in het bericht) dan wordt 'Systeemdatum' (R2016) als peilmoment(en) gebruikt.
Indien er sprake is van Datum (deels) onbekend (R1273) dan moet de R1283 - Vergelijken (partiële) datums uitgevoerd worden.

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

!-- | WOONPLAATS    | DAG      | DEG      | TSREG                    |
!-- | Oosterhout    | 20101231 | 20151231 | 2010-12-31T01:00:00.000Z |
!-- | Utrecht       | 20151231 | 20160101 | 2015-12-31T01:00:00.000Z |
!-- | 's-Gravenhage | 20160101 | 20160102 | 2016-01-01T01:00:00.000Z |
!-- | Uithoorn      | 20160102 | NULL     | 2016-01-02T01:00:00.000Z |

!-- Selectie met historievorm 'MaterieelFormeel'
Given leveringsautorisatie uit aut/SelectieHistorievormMaterieelFormeel
Given een selectierun met de volgende selectie taken:
|id |datplanning | status    |peilmommaterieelresultaat |peilmomformeelresultaat |dienstSleutel |
|1  |vandaag     | Uitvoerbaar   |20160101                  |2015-12-31T23:00:00Z   |SelectieHistorievormMaterieelFormeel |

Given persoonsbeelden uit specials:specials/Anne_met_Historie2_xls
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|986096969|2008-12-31 T23:59:00Z


When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen

Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Utrecht']' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Oosterhout']' een node aanwezig in de selectieresultaat persoonbestanden

Then zijn de volgende resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag':
|volgnummer    |pad
|1             |expecteds/expected_R2226/01.xml

Scenario: 2.    Historievorm = MaterieelFormeel, Peilmoment materieel resultaat is datum x (01-01-2016), Peilmoment Formeel resultaat is datum y (2016-01-01)
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

!-- | WOONPLAATS    | DAG      | DEG      | TSREG                    |
!-- | Oosterhout    | 20101231 | 20151231 | 2010-12-31T01:00:00.000Z |
!-- | Utrecht       | 20151231 | 20160101 | 2015-12-31T01:00:00.000Z |
!-- | 's-Gravenhage | 20160101 | 20160102 | 2016-01-01T01:00:00.000Z |
!-- | Uithoorn      | 20160102 | NULL     | 2016-01-02T01:00:00.000Z |

!-- Selectie met historievorm 'MaterieelFormeel'
Given leveringsautorisatie uit aut/SelectieHistorievormMaterieelFormeel
Given een selectierun met de volgende selectie taken:
|id |datplanning | status    |peilmommaterieelresultaat |peilmomformeelresultaat |dienstSleutel |
|1  |vandaag     | Uitvoerbaar   |20160101                  |2016-01-01T23:00:00Z   |SelectieHistorievormMaterieelFormeel |

Given persoonsbeelden uit specials:specials/Anne_met_Historie2_xls
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|986096969|2008-12-31 T23:59:00Z

When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen

Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:adres/brp:woonplaatsnaam[contains(text(),'Gravenhage')]' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Utrecht']' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Oosterhout']' een node aanwezig in de selectieresultaat persoonbestanden

Then zijn de volgende resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag':
|volgnummer    |pad
|1             |expecteds/expected_R2226/02.xml

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

!-- | WOONPLAATS    | DAG      | DEG      | TSREG                    |
!-- | Oosterhout    | 20101231 | 20151231 | 2010-12-31T01:00:00.000Z |
!-- | Utrecht       | 20151231 | 20160101 | 2015-12-31T01:00:00.000Z |
!-- | 's-Gravenhage | 20160101 | 20160102 | 2016-01-01T01:00:00.000Z |
!-- | Uithoorn      | 20160102 | NULL     | 2016-01-02T01:00:00.000Z |

Given leveringsautorisatie uit aut/SelectieHistorievormMaterieelFormeel
Given een selectierun met de volgende selectie taken:
|id |datplanning | status    |peilmommaterieelresultaat |peilmomformeelresultaat |dienstSleutel |
|1  |vandaag     | Uitvoerbaar   |20160101                  |2016-01-02T23:00:00Z   |SelectieHistorievormMaterieelFormeel |

Given persoonsbeelden uit specials:specials/Anne_met_Historie2_xls
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|986096969|2008-12-31 T23:59:00Z

When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen

Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:adres/brp:woonplaatsnaam[contains(text(),'Gravenhage')]' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Utrecht']' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Oosterhout']' een node aanwezig in de selectieresultaat persoonbestanden

Then zijn de volgende resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag':
|volgnummer    |pad
|1             |expecteds/expected_R2226/03.xml

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

!-- Selectie met historievorm 'MaterieelFormeel'
Given leveringsautorisatie uit aut/SelectieHistorievormMaterieelFormeel
Given een selectierun met de volgende selectie taken:
|id |datplanning | status    |peilmomformeelresultaat |dienstSleutel |
|1  |vandaag     | Uitvoerbaar   |2015-12-31T23:00:00Z   |SelectieHistorievormMaterieelFormeel |

Given persoonsbeelden uit specials:specials/Anne_met_Historie2_xls
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|986096969|2008-12-31 T23:59:00Z

When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen

Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Utrecht']' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Oosterhout']' een node aanwezig in de selectieresultaat persoonbestanden

Then zijn de volgende resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag':
|volgnummer    |pad
|1             |expecteds/expected_R2226/04.xml

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

!-- Selectie met historievorm 'MaterieelFormeel'
Given leveringsautorisatie uit aut/SelectieHistorievormMaterieelFormeel
Given een selectierun met de volgende selectie taken:
|id |datplanning | status    |peilmomformeelresultaat |dienstSleutel |
|1  |vandaag     | Uitvoerbaar   |2016-01-01T23:00:00Z   |SelectieHistorievormMaterieelFormeel |

Given persoonsbeelden uit specials:specials/Anne_met_Historie2_xls
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|986096969|2008-12-31 T23:59:00Z

When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen

Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:adres/brp:woonplaatsnaam[contains(text(),'Gravenhage')]' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Utrecht']' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Oosterhout']' een node aanwezig in de selectieresultaat persoonbestanden

Then zijn de volgende resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag':
|volgnummer    |pad
|1             |expecteds/expected_R2226/05.xml

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

!-- | WOONPLAATS    | DAG      | DEG      | TSREG                    |
!-- | Oosterhout    | 20101231 | 20151231 | 2010-12-31T01:00:00.000Z |
!-- | Utrecht       | 20151231 | 20160101 | 2015-12-31T01:00:00.000Z |
!-- | 's-Gravenhage | 20160101 | 20160102 | 2016-01-01T01:00:00.000Z |
!-- | Uithoorn      | 20160102 | NULL     | 2016-01-02T01:00:00.000Z |

Given leveringsautorisatie uit aut/SelectieHistorievormMaterieelFormeel
Given een selectierun met de volgende selectie taken:
|id |datplanning | status    |peilmomformeelresultaat |dienstSleutel |
|1  |vandaag     | Uitvoerbaar   |2016-01-02T23:00:00Z   |SelectieHistorievormMaterieelFormeel |

Given persoonsbeelden uit specials:specials/Anne_met_Historie2_xls
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|986096969|2008-12-31 T23:59:00Z

When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen

Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Uithoorn']' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:adres/brp:woonplaatsnaam[contains(text(),'Gravenhage')]' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Utrecht']' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Oosterhout']' een node aanwezig in de selectieresultaat persoonbestanden

Then zijn de volgende resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag':
|volgnummer    |pad
|1             |expecteds/expected_R2226/06.xml

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

!-- Selectie met historievorm 'MaterieelFormeel'
Given leveringsautorisatie uit aut/SelectieHistorievormMaterieelFormeel
Given een selectierun met de volgende selectie taken:
|id |datplanning | status    |peilmommaterieelresultaat |dienstSleutel |
|1  |vandaag     | Uitvoerbaar   |20151231                  |SelectieHistorievormMaterieelFormeel |

Given persoonsbeelden uit specials:specials/Anne_met_Historie2_xls
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|986096969|2008-12-31 T23:59:00Z

When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen

Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Utrecht']' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Oosterhout']' een node aanwezig in de selectieresultaat persoonbestanden

Then zijn de volgende resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag':
|volgnummer    |pad
|1             |expecteds/expected_R2226/07.xml

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

!-- Selectie met historievorm 'MaterieelFormeel'
Given leveringsautorisatie uit aut/SelectieHistorievormMaterieelFormeel
Given een selectierun met de volgende selectie taken:
|id |datplanning | status    |peilmommaterieelresultaat |dienstSleutel |
|1  |vandaag     | Uitvoerbaar   |20160101                  |SelectieHistorievormMaterieelFormeel |

Given persoonsbeelden uit specials:specials/Anne_met_Historie2_xls
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|986096969|2008-12-31 T23:59:00Z

When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen

Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:adres/brp:woonplaatsnaam[contains(text(),'Gravenhage')]' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Utrecht']' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Oosterhout']' een node aanwezig in de selectieresultaat persoonbestanden

Then zijn de volgende resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag':
|volgnummer    |pad
|1             |expecteds/expected_R2226/08.xml

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

!-- | WOONPLAATS    | DAG      | DEG      | TSREG                    |
!-- | Oosterhout    | 20101231 | 20151231 | 2010-12-31T01:00:00.000Z |
!-- | Utrecht       | 20151231 | 20160101 | 2015-12-31T01:00:00.000Z |
!-- | 's-Gravenhage | 20160101 | 20160102 | 2016-01-01T01:00:00.000Z |
!-- | Uithoorn      | 20160102 | NULL     | 2016-01-02T01:00:00.000Z |

Given leveringsautorisatie uit aut/SelectieHistorievormMaterieelFormeel
Given een selectierun met de volgende selectie taken:
|id |datplanning | status    |peilmommaterieelresultaat |dienstSleutel |
|1  |vandaag     | Uitvoerbaar   |20160102                  |SelectieHistorievormMaterieelFormeel |

Given persoonsbeelden uit specials:specials/Anne_met_Historie2_xls
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|986096969|2008-12-31 T23:59:00Z

When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen

Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Uithoorn']' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:adres/brp:woonplaatsnaam[contains(text(),'Gravenhage')]' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Utrecht']' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Oosterhout']' een node aanwezig in de selectieresultaat persoonbestanden

Then zijn de volgende resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag':
|volgnummer    |pad
|1             |expecteds/expected_R2226/09.xml

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

Given leveringsautorisatie uit aut/SelectieHistorievormMaterieelFormeel
Given een selectierun met de volgende selectie taken:
|id |datplanning | status    |dienstSleutel |
|1  |vandaag     | Uitvoerbaar   |SelectieHistorievormMaterieelFormeel |

Given persoonsbeelden uit specials:specials/Anne_met_Historie2_xls
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|986096969|2008-12-31 T23:59:00Z

When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen

Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Uithoorn']' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:adres/brp:woonplaatsnaam[contains(text(),'Gravenhage')]' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Utrecht']' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Oosterhout']' een node aanwezig in de selectieresultaat persoonbestanden

Then zijn de volgende resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag':
|volgnummer    |pad
|1             |expecteds/expected_R2226/10.xml

Scenario: 11.   Historievorm = MaterieelFormeel, Peilmoment materieel resultaat is LEEG, Peilmoment Formeel resultaat is 2016-01-31
                LT: R2226_LT11
                Verwacht Resultaat
                - Oosterhout in het bericht
                - Utrecht in het bericht (volgens de soepele vergelijking mogelijk waar)
                - 's-Gravenhage in het bericht (volgens de soepele vergelijking mogelijk waar)
                - Uithoorn NIET in het bericht (volgens de soepele vergelijking NIET waar)

Given leveringsautorisatie uit aut/SelectieHistorievormMaterieelFormeel
Given een selectierun met de volgende selectie taken:
|id |datplanning | status    |peilmomformeelresultaat |dienstSleutel |
|1  |vandaag     | Uitvoerbaar   |2016-01-31T23:00:00Z   |SelectieHistorievormMaterieelFormeel |

Given persoonsbeelden uit specials:specials/Anne_met_Historie_gedeeltelijk_onb_dat_xls
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|986096969|2008-12-31 T23:59:00Z

When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen

Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:adres/brp:woonplaatsnaam[contains(text(),'Gravenhage')]' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Utrecht']' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Oosterhout']' een node aanwezig in de selectieresultaat persoonbestanden

Then zijn de volgende resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag':
|volgnummer    |pad
|1             |expecteds/expected_R2226/11.xml

Scenario: 12.   Historievorm = MaterieelFormeel, Peilmoment materieel resultaat is 2016-01-31, Peilmoment Formeel resultaat is Leeg
                LT: R2226_LT12
                Verwacht Resultaat
                - Oosterhout in het bericht
                - Utrecht in het bericht (volgens de soepele vergelijking mogelijk waar)
                - 's-Gravenhage in het bericht (volgens de soepele vergelijking mogelijk waar)
                - Uithoorn NIET in het bericht (volgens de soepele vergelijking NIET waar)

Given leveringsautorisatie uit aut/SelectieHistorievormMaterieelFormeel
Given een selectierun met de volgende selectie taken:
|id |datplanning | status    |peilmommaterieelresultaat |dienstSleutel |
|1  |vandaag     | Uitvoerbaar   |20160131                   |SelectieHistorievormMaterieelFormeel |

Given persoonsbeelden uit specials:specials/Anne_met_Historie_gedeeltelijk_onb_dat_xls
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|986096969|2008-12-31 T23:59:00Z

When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen

Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:adres/brp:woonplaatsnaam[contains(text(),'Gravenhage')]' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Utrecht']' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Oosterhout']' een node aanwezig in de selectieresultaat persoonbestanden

Then zijn de volgende resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag':
|volgnummer    |pad
|1             |expecteds/expected_R2226/12.xml

Scenario:  13.  Historievorm = MaterieelFormeel, Peilmoment materieel resultaat is 2016-01-01, Peilmoment Formeel resultaat is LEEG
                LT: R2226_LT13
                Verwacht resultaat:
                - Uithoorn              NIET in bericht
                - Den Haag              in bericht
                - Utrecht in bericht    in bericht
                - Oosterhout            in bericht
                Uitwerking:
                - Uithoorn sinds 2016-02-00 tot heden
                - Den Haag sinds 2016-01-00 tot 2016-02-00 (naamOpenbareRuimte = Spui)
                - Oosterhout sinds 2010-12-31 tot 2016-01-00
                - Utrecht sinds  0000-00-00 tot 2010-12-31

!-- Selectie met historievorm 'Materieel'
Given leveringsautorisatie uit aut/SelectieHistorievormMaterieelFormeel
Given een selectierun met de volgende selectie taken:
|id |datplanning | status    |peilmommaterieelresultaat |dienstSleutel |
|1  |vandaag     | Uitvoerbaar   |20160101                  |SelectieHistorievormMaterieelFormeel |

Given persoonsbeelden uit specials:specials/Anne_met_Historie_geheel_onb_dat_xls
Given tijdstip laatste wijziging GBA systematiek op de persoon :
|bsn|tsLaatsteWijzigingGbaSystematiek
|986096969|2008-12-31 T23:59:00Z

When de selectie wordt gestart
And wacht 10 seconden tot selectie run klaar
Then resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag' met '1' personen

Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Utrecht']' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:woonplaatsnaam[text()='Oosterhout']' een node aanwezig in de selectieresultaat persoonbestanden
Then is voor selectietaak '1' en datumplanning 'vandaag' voor xpath '//brp:naamOpenbareRuimte[text()='Spui']' een node aanwezig in de selectieresultaat persoonbestanden

Then zijn de volgende resultaat files aanwezig voor selectietaak '1' en datumplanning 'vandaag':
|volgnummer    |pad
|1             |expecteds/expected_R2226/13.xml

